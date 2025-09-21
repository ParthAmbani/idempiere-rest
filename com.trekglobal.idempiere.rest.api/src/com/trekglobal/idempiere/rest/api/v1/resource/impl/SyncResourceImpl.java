package com.trekglobal.idempiere.rest.api.v1.resource.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adempiere.base.event.EventManager;
import org.adempiere.base.event.EventProperty;
import org.adempiere.base.event.IEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.CrossTenantException;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Trx;
import org.osgi.service.event.Event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.trekglobal.idempiere.rest.api.json.IPOSerializer;
import com.trekglobal.idempiere.rest.api.json.ResponseUtils;
import com.trekglobal.idempiere.rest.api.json.RestUtils;
import com.trekglobal.idempiere.rest.api.json.TypeConverterUtils;
import com.trekglobal.idempiere.rest.api.v1.resource.SyncResource;

/**
 * Implementation of SyncResource.
 * <p>
 * This resource accepts a JSON array of records and performs create/update
 * operations on iDempiere POs for a given table.
 * </p>
 * <p>
 * If the record already exists (based on primary key or UUID column), it will
 * update the record instead of inserting a new one.
 * </p>
 */
public class SyncResourceImpl implements SyncResource {

    private static final Logger log = Logger.getLogger(SyncResourceImpl.class.getName());

    public static final String PO_BEFORE_REST_SAVE = "idempiere-rest/po/beforeSave";
    public static final String PO_AFTER_REST_SAVE = "idempiere-rest/po/afterSave";

    /**
     * Sync endpoint - processes a batch of records for a table.
     *
     * @param tableName The name of the iDempiere table
     * @param jsonText  The JSON array representing records
     * @return Response with sync result summary
     */
    @Override
    public Response sync(String tableName, String jsonText) {
        Trx trx = null;
        int failed = 0;
        int success = 0;

        try {
            trx = Trx.get(Trx.createTrxName("SYNC_" + tableName), true);
            log.info(() -> "Starting sync for table: " + tableName);

            // Parse JSON array
            Gson gson = new Gson();
            List<JsonObject> records = gson.fromJson(jsonText, new TypeReference<List<JsonObject>>() {}.getType());

            for (JsonObject recordJson : records) {
                try {
                    Response resp = createOrUpdate(tableName, recordJson);
                    if (resp.getStatus() == Status.CREATED.getStatusCode()
                            || resp.getStatus() == Status.OK.getStatusCode()) {
                        success++;
                    } else {
                        failed++;
                        log.warning(() -> "Failed record: " + resp.getEntity());
                    }
                } catch (Exception recEx) {
                    failed++;
                    log.log(Level.SEVERE, "Exception while syncing record: " + recordJson, recEx);
                }
            }

            trx.commit();
            String result = String.format("{\"status\":\"success\",\"synced\":%d,\"failed\":%d}", success, failed);
            log.info("Sync completed for " + tableName + ". Success: " + success + ", Failed: " + failed);
            return Response.ok(result).build();

        } catch (Exception e) {
            if (trx != null)
                trx.rollback();
            String message = e.getMessage() == null ? e.toString() : e.getMessage();
            log.log(Level.SEVERE, "Sync failed for table: " + tableName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\",\"message\":\"" + message + "\"}")
                    .build();
        } finally {
            if (trx != null)
                trx.close();
        }
    }

    /**
     * Creates or updates a single record.
     * <p>
     * - If a record with the same key column (or UUID column) exists, updates it.
     * - Otherwise, creates a new record.
     *
     * @param tableName  Table name
     * @param recordJson JSON object representing a single record
     * @return Response with record result
     */
    public Response createOrUpdate(String tableName, JsonObject recordJson) {
        Trx trx = Trx.get(Trx.createTrxName("SYNC_CREATE_UPDATE_" + tableName), true);
        try {
            trx.start();
            MTable table = RestUtils.getTableAndCheckAccess(tableName, true);
            IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));

            // Try to find existing record
            PO po = findExistingRecord(table, recordJson);
            boolean isNew = (po == null);

            if (isNew) {
                // Create new PO if not found
                po = serializer.fromJson(recordJson, table, null);
            } else {
                // Update existing PO
            	po = serializer.fromJson(recordJson, po, null);
            }

            po.setReplication(true);
            po.set_TrxName(trx.getTrxName());

            // Security check
            if (!RestUtils.hasRoleUpdateAccess(po.getAD_Client_ID(), po.getAD_Org_ID(), po.get_Table_ID(), 0, true)) {
                log.warning(() -> "Role does not have access to update table: " + tableName);
                return ResponseUtils.getResponseError(Status.FORBIDDEN, "Update error", "Role does not have access", "");
            }

            // Fire beforeSave event
            fireRestSaveEvent(po, PO_BEFORE_REST_SAVE, isNew);

            try {
                po.validForeignKeysEx();
                po.saveEx();
                log.info((isNew ? "Created" : "Updated") + " record in " + tableName + " [ID=" + po.get_ID() + "]");
                fireRestSaveEvent(po, PO_AFTER_REST_SAVE, isNew);
            } catch (CrossTenantException e) {
                trx.rollback();
                return ResponseUtils.getResponseError(Status.INTERNAL_SERVER_ERROR, "Save error",
                        "Foreign ID " + e.getFKValue() + " not found in ", String.valueOf(e.getFKColumn()));
            } catch (Exception ex) {
                trx.rollback();
                return ResponseUtils.getResponseErrorFromException(ex, "Save error");
            }

            trx.commit(true);
            po.load(trx.getTrxName());
            JsonObject responseJson = serializer.toJson(po, null);

            return Response.status(isNew ? Status.CREATED : Status.OK).entity(responseJson.toString()).build();

        } catch (Exception ex) {
            trx.rollback();
            log.log(Level.SEVERE, "Error creating/updating record in table: " + tableName, ex);
            return ResponseUtils.getResponseErrorFromException(ex, "Server error");
        } finally {
            trx.close();
        }
    }

    /**
     * Attempts to find an existing PO record based on primary key or UUID.
     *
     * @param table      Table reference
     * @param recordJson Input JSON record
     * @return Existing PO if found, otherwise null
     */
    private PO findExistingRecord(MTable table, JsonObject recordJson) {
        try {
            String[] keyColumns = table.getKeyColumns();
            if (keyColumns != null && keyColumns.length > 0 && recordJson.has(keyColumns[0])) {
                int poID = (int) TypeConverterUtils.fromJsonValue(table.getColumn(keyColumns[0]),
                        recordJson.get(keyColumns[0]), null);
                if (poID > 0) {
                    PO existingPO = table.getPO(poID, null);
                    if (existingPO != null && existingPO.get_ID() > 0) {
                        log.fine(() -> "Found existing PO by ID: " + poID);
                        return existingPO;
                    }
                }
            }

            String uuidColumn = table.getUUIDColumnName();
            if (uuidColumn != null && recordJson.has(uuidColumn)) {
                String uuidValue = recordJson.get(uuidColumn).getAsString();
                PO existingPO = table.getPO(uuidColumn + "=?", new Object[] { uuidValue }, null);
                if (existingPO != null && existingPO.get_ID() > 0) {
                    log.fine(() -> "Found existing PO by UUID: " + uuidValue);
                    return existingPO;
                }
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Error while checking for existing record in " + table.getTableName(), e);
        }
        return null;
    }

    /**
     * Fires the PO_BEFORE_REST_SAVE/PO_AFTER_REST_SAVE event.
     *
     * @param po    The PO object being saved
     * @param topic Event topic
     * @param isNew Whether this is a new record
     */
    private void fireRestSaveEvent(PO po, String topic, boolean isNew) {
        Event event = EventManager.newEvent(topic,
                new EventProperty(EventManager.EVENT_DATA, po),
                new EventProperty("tableName", po.get_TableName()),
                new EventProperty("isNew", isNew));
        EventManager.getInstance().sendEvent(event);

        @SuppressWarnings("unchecked")
        List<String> errors = (List<String>) event.getProperty(IEventManager.EVENT_ERROR_MESSAGES);
        if (errors != null && !errors.isEmpty()) {
            throw new AdempiereException(errors.get(0));
        }
    }
}
