package com.trekglobal.idempiere.rest.api.v1.resource.impl;

import java.util.List;
import java.util.Set;

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
 * SyncResource implementation that accepts a JSON array and creates/updates PO records.
 */
public class SyncResourceImpl implements SyncResource {

	public static final String PO_BEFORE_REST_SAVE = "idempiere-rest/po/beforeSave";
	public static final String PO_AFTER_REST_SAVE = "idempiere-rest/po/afterSave";

    @Override
    public Response sync(String tableName, String jsonText) {
        Trx trx = null;
        int success = 0;
        int failed = 0;

        try {
            trx = Trx.get(Trx.createTrxName("SYNC_" + tableName), true);

            // Parse JSON array
            Gson gson = new Gson();
            List<JsonObject> records = gson.fromJson(jsonText, new TypeReference<List<JsonObject>>() {}.getType());

            for (JsonObject recordJson : records) {
                try {
                    Response resp = create(tableName, recordJson);
                    if (resp.getStatus() == Status.CREATED.getStatusCode()) {
                        success++;
                    } else {
                        failed++;
                        System.err.println("Failed record: " + resp.getEntity());
                    }
                } catch (Exception recEx) {
                    failed++;
                    System.err.println("Exception while syncing record: " + recEx.getMessage());
                    recEx.printStackTrace(System.err);
                }
            }

            trx.commit();
            String result = String.format("{\"status\":\"success\",\"synced\":%d,\"failed\":%d}", success, failed);
            return Response.ok(result).build();

        } catch (Exception e) {
            if (trx != null) trx.rollback();
            String message = e.getMessage() == null ? e.toString() : e.getMessage();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\",\"message\":\"" + message + "\"}")
                    .build();
        } finally {
            if (trx != null) trx.close();
        }
    }

    /**
     * Create a single record from JSON object
     */
    public Response create(String tableName, JsonObject recordJson) {
        Trx trx = Trx.get(Trx.createTrxName("CREATE_" + tableName), true);
        try {
            MTable table = RestUtils.getTableAndCheckAccess(tableName, true);

            trx.start();
            IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));
            PO po = serializer.fromJson(recordJson, table, null);
			Set<String> jsonFields = recordJson.keySet();
			if (table.getKeyColumns() != null && table.getKeyColumns().length > 0
					&& jsonFields.contains(table.getKeyColumns()[0])) {
				int poID = (int) TypeConverterUtils.fromJsonValue(table.getColumn(table.getKeyColumns()[0]),
						recordJson.get(table.getKeyColumns()[0]), null);
				po.set_ValueNoCheck(table.getKeyColumns()[0], poID);
			}
			po.setReplication(true);
            if (!RestUtils.hasRoleUpdateAccess(po.getAD_Client_ID(), po.getAD_Org_ID(), po.get_Table_ID(), 0, true)) {
                return ResponseUtils.getResponseError(Status.FORBIDDEN, "Update error", "Role does not have access", "");
            }

            po.set_TrxName(trx.getTrxName());
            fireRestSaveEvent(po, PO_BEFORE_REST_SAVE, true);

            try {
                po.validForeignKeysEx();
                po.saveEx();
                fireRestSaveEvent(po, PO_AFTER_REST_SAVE, true);
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

            return Response.status(Status.CREATED).entity(responseJson.toString()).build();

        } catch (Exception ex) {
            trx.rollback();
            return ResponseUtils.getResponseErrorFromException(ex, "Server error");
        } finally {
            trx.close();
        }
    }

//    private void populatePOFromJson(PO po, Map<String, Object> data) {
//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            String columnName = entry.getKey();
//            Object value = entry.getValue();
//
//            if (po.get_ColumnIndex(columnName) >= 0 && value != null) {
//                if (value instanceof String) {
//                    String s = (String) value;
//                    try {
//                        Timestamp ts = Timestamp.valueOf(s); // yyyy-MM-dd HH:mm:ss
//                        po.set_ValueNoCheck(columnName, ts);
//                        continue;
//                    } catch (IllegalArgumentException | DateTimeParseException ignore) {
//                        try {
//                            Timestamp tsIso = Timestamp.from(java.time.Instant.parse(s));
//                            po.set_ValueNoCheck(columnName, tsIso);
//                            continue;
//                        } catch (Exception ignore2) { }
//                    }
//                }
//                po.set_ValueNoCheck(columnName, value);
//            }
//        }
//
//        if (po.get_ColumnIndex("AD_Client_ID") >= 0) {
//            Object clientVal = po.get_Value("AD_Client_ID");
//            if (clientVal == null || (clientVal instanceof Number && ((Number) clientVal).intValue() == 0)) {
//                po.set_ValueNoCheck("AD_Client_ID", Env.getAD_Client_ID(Env.getCtx()));
//            }
//        }
//
//        if (po.get_ColumnIndex("Created") >= 0 && po.get_Value("Created") == null) {
//            po.set_ValueNoCheck("Created", new Timestamp(System.currentTimeMillis()));
//        }
//    }
//
//    private void copyValuesToExisting(PO source, PO existing) {
//        for (int i = 0; i < source.get_ColumnCount(); i++) {
//            String col = source.get_ColumnName(i);
//            Object val = source.get_Value(col);
//            if (val != null) {
//                existing.set_ValueNoCheck(col, val);
//            }
//        }
//    }

//    private PO findExistingRecord(MTable table, Map<String, Object> recordData, String trxName) {
//        String idColumn = table.getTableName() + "_ID";
//        Object idObj = recordData.get(idColumn);
//        if (idObj instanceof Number) {
//            int id = ((Number) idObj).intValue();
//            if (id > 0) {
//                return table.getPO(id, trxName);
//            }
//        }
//
//        if (table.getColumnIndex("UUID") >= 0) {
//            Object uuidObj = recordData.get("UUID");
//            if (uuidObj instanceof String && !((String) uuidObj).trim().isEmpty()) {
//                String uuid = ((String) uuidObj).trim();
//                return new Query(Env.getCtx(), table.getTableName(), "UUID = ?", trxName)
//                        .setParameters(uuid)
//                        .first();
//            }
//        }
//
//        return null;
//    }
    
    /**
	 * Fire the PO_BEFORE_REST_SAVE/PO_AFTER_REST_SAVE event, to catch and manipulate the object before the model beforeSave/afterSave
	 * @param po
	 */
	private void fireRestSaveEvent(PO po, String topic, boolean isNew) {
		Event event = EventManager.newEvent(topic,
				new EventProperty(EventManager.EVENT_DATA, po), new EventProperty("tableName", po.get_TableName()),
				new EventProperty("isNew", isNew));
		EventManager.getInstance().sendEvent(event);
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) event.getProperty(IEventManager.EVENT_ERROR_MESSAGES);
		if (errors != null && !errors.isEmpty())
			throw new AdempiereException(errors.get(0));
	}
}
