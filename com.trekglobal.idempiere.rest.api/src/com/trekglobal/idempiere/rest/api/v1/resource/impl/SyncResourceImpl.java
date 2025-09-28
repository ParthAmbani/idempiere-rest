package com.trekglobal.idempiere.rest.api.v1.resource.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.CrossTenantException;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.trekglobal.idempiere.rest.api.json.IPOSerializer;
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

		List<JsonObject> detailedResults = new ArrayList<>();

		try {
			trx = Trx.get(Trx.createTrxName("SYNC_" + tableName), true);
			log.info(() -> "Starting sync for table: " + tableName);

			Gson gson = new Gson();
			List<JsonObject> records = gson.fromJson(jsonText, new TypeToken<List<JsonObject>>() {
			}.getType());

			for (JsonObject recordJson : records) {
				SyncResult result = createOrUpdate(tableName, recordJson);

				JsonObject resultJson = new JsonObject();
				resultJson.addProperty("success", result.success);
				resultJson.addProperty("action", result.action);
				resultJson.addProperty("message", result.message);
				resultJson.add("identifier", result.identifier);

				detailedResults.add(resultJson);

				if (result.success) {
					success++;
				} else {
					failed++;
				}
			}

			trx.commit();

			JsonObject finalResponse = new JsonObject();
			finalResponse.addProperty("status", "completed");
			finalResponse.addProperty("successCount", success);
			finalResponse.addProperty("failedCount", failed);
			finalResponse.add("results", gson.toJsonTree(detailedResults));

			return Response.ok(finalResponse.toString()).build();

		} catch (Exception e) {
			if (trx != null)
				trx.rollback();
			String message = e.getMessage() == null ? e.toString() : e.getMessage();
			log.log(Level.SEVERE, "Sync failed for table: " + tableName, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{\"status\":\"error\",\"message\":\"" + message + "\"}").build();
		} finally {
			if (trx != null)
				trx.close();
		}
	}

	/**
	 * Creates or updates a single record.
	 * <p>
	 * - If a record with the same key column (or UUID column) exists, updates it. -
	 * Otherwise, creates a new record.
	 *
	 * @param tableName  Table name
	 * @param recordJson JSON object representing a single record
	 * @return Response with record result
	 */
	public SyncResult createOrUpdate(String tableName, JsonObject recordJson) {
		Trx trx = Trx.get(Trx.createTrxName("SYNC_CREATE_UPDATE_" + tableName), true);
		try {
			trx.start();
			MTable table = RestUtils.getTableAndCheckAccess(tableName, true);
			IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));

			// Try to find existing record
			PO po = findExistingRecord(table, recordJson);
			boolean isNew = (po == null);

			if (isNew) {
				po = serializer.fromJson(recordJson, table, null);
				po.setReplication(true);
			} else {
				po.setReplication(true);
				po = serializer.fromJson(recordJson, po, null);
			}
			po.set_TrxName(trx.getTrxName());

			// Security check
			if (!RestUtils.hasRoleUpdateAccess(po.getAD_Client_ID(), po.getAD_Org_ID(), po.get_Table_ID(), 0, true)) {
				log.warning(() -> "Role does not have access to update table: " + tableName);
				trx.rollback();
				return new SyncResult(false, "No access to update table: " + tableName, isNew ? "create" : "update",
						extractIdentifier(table, recordJson));
			}

			try {
				createOrUpdatePO(po, isNew);
				log.info((isNew ? "Created" : "Updated") + " record in " + tableName + " [ID=" + po.get_ID() + "]");
			} catch (CrossTenantException e) {
				trx.rollback();
				return new SyncResult(false, "Foreign key missing: " + e.getFKColumn(), isNew ? "create" : "update",
						extractIdentifier(table, recordJson));
			} catch (Exception ex) {
				trx.rollback();
				return new SyncResult(false, "Save error: " + ex.getMessage(), isNew ? "create" : "update",
						extractIdentifier(table, recordJson));
			}

			trx.commit(true);
			return new SyncResult(true, (isNew ? "Created successfully" : "Updated successfully"),
					isNew ? "create" : "update", extractIdentifier(table, recordJson));

		} catch (Exception ex) {
			trx.rollback();
			log.log(Level.SEVERE, "Error creating/updating record in table: " + tableName, ex);
			return new SyncResult(false, "Server error: " + ex.getMessage(), "unknown",
					extractIdentifier(null, recordJson)); // fallback if table is null
		} finally {
			trx.close();
		}
	}

	/**
	 * Helper method to extract key column + UUID column (if present)
	 */
	private JsonObject extractIdentifier(MTable table, JsonObject recordJson) {
		JsonObject idObj = new JsonObject();
		try {
			if (table != null) {
				String[] keyColumns = table.getKeyColumns();
				if (keyColumns != null && keyColumns.length > 0 && recordJson.has(keyColumns[0])) {
					idObj.add(keyColumns[0], recordJson.get(keyColumns[0]));
				}
				String uuidColumn = table.getUUIDColumnName();
				if (uuidColumn != null && recordJson.has(uuidColumn)) {
					idObj.add(uuidColumn, recordJson.get(uuidColumn));
				}
			}
		} catch (Exception e) {
			// Fail-safe: no identifier found
		}
		return idObj;
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

	public void createOrUpdatePO(PO po, boolean isNew) throws SQLException {
		Trx trx = Trx.get(Trx.createTrxName("SYNC_CREATE_UPDATE_" + po.get_TableName()), true);
		trx.start();
		po.set_TrxName(trx.getTrxName());

		// Build SQL dynamically
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		int columnCount = po.get_ColumnCount();

		if (isNew) {
			// INSERT
			sql.append("INSERT INTO ").append(po.get_TableName()).append(" (");
			StringBuilder placeholders = new StringBuilder();

			for (int i = 0; i < columnCount; i++) {
				String colName = po.get_ColumnName(i);
				Object value = po.get_Value(i);

				if (i > 0) {
					sql.append(", ");
					placeholders.append(", ");
				}

				sql.append(colName);
				placeholders.append("?");
				params.add(value);
			}

			sql.append(") VALUES (").append(placeholders).append(")");

		} else {
			// UPDATE
			sql.append("UPDATE ").append(po.get_TableName()).append(" SET ");
			Object idValue = po.get_ID();

			boolean first = true;
			for (int i = 0; i < columnCount; i++) {
				String colName = po.get_ColumnName(i);
				if (colName.equalsIgnoreCase(po.get_TableName() + "_ID"))
					continue; // skip PK
				Object value = po.get_Value(i);

				if (!first)
					sql.append(", ");
				sql.append(colName).append(" = ?");
				params.add(value);
				first = false;
			}

			sql.append(" WHERE ").append(po.get_TableName()).append("_ID = ?");
			params.add(idValue);
		}

		// Execute SQL
		System.out.println(sql);
		System.out.println(params);
		try {
			int affectedRows = DB.executeUpdateEx(sql.toString(), params.toArray(), null);
			if (affectedRows == 0) {
				throw new AdempiereException("No rows inserted/updated.");
			}
			trx.commit(true);
		} catch (Exception ex) {
			trx.rollback();
			throw ex;
		}
	}

	public class SyncResult {
		public boolean success;
		public String message;
		public String action; // "created" or "updated"
		public JsonObject identifier;

		public SyncResult(boolean success, String message, String action, JsonObject identifier) {
			this.success = success;
			this.message = message;
			this.action = action;
			this.identifier = identifier;
		}
	}
}
