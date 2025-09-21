package com.idempiere.rest.serversync.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.compiere.model.MProcessPara;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.idempiere.rest.serversync.config.IdempiereConfig;
import com.idempiere.rest.serversync.dto.AuthRequest;
import com.idempiere.rest.serversync.dto.AuthResponse;
import com.idempiere.rest.serversync.dto.RefreshTokenRequest;
import com.idempiere.rest.serversync.dto.RefreshTokenResponse;
import com.idempiere.rest.serversync.model.MSSServerConfig;
import com.idempiere.rest.serversync.model.MSSServerLogin;
import com.idempiere.rest.serversync.model.MSSSyncConfig;
import com.idempiere.rest.serversync.model.MSSSyncTable;
import com.idempiere.rest.serversync.service.IdempiereAuthService;
import com.idempiere.rest.serversync.service.IdempiereRestClient;
import com.trekglobal.idempiere.rest.api.json.IPOSerializer;

/**
 * SyncModelProcess ---------------- This process fetches PO records from the
 * local iDempiere instance and synchronizes them to a remote iDempiere server
 * via REST API.
 *
 * Steps: 1. Authenticate to the remote server using token/refresh logic. 2.
 * Fetch records from configured tables, filtered by last sync date. 3.
 * Serialize records to JSON. 4. POST records to remote endpoint. 5. Update sync
 * timestamp for successfully synced tables.
 * 
 * Author: Parth Ambani
 */
public class SyncTableModel extends SvrProcess {

	private static final Logger log = Logger.getLogger(SyncTableModel.class.getName());

	/** Parameter: Sync Config ID */
	private int p_SyncConfig_ID = 0;

	/**
	 * Prepare process parameters
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter p : para) {
			String name = p.getParameterName();
			if (p.getParameter() == null)
				continue;

			if (MSSSyncConfig.COLUMNNAME_SS_SyncConfig_ID.equals(name)) {
				p_SyncConfig_ID = ((BigDecimal) p.getParameter()).intValue();
			} else {
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), p);
			}
		}
		log.info("SyncModelProcess prepared with SyncConfig_ID=" + p_SyncConfig_ID);
	}

	/**
	 * Main execution of the process
	 */
	@Override
	protected String doIt() throws Exception {
		try {
			// Load sync configuration
			MSSSyncConfig syncConfig = (MSSSyncConfig) MTable.get(getCtx(), MSSSyncConfig.Table_Name)
					.getPO(p_SyncConfig_ID, get_TrxName());
			MSSServerConfig serverConfig = (MSSServerConfig) syncConfig.getSS_ServerConfig();
			MSSServerLogin serverLogin = (MSSServerLogin) syncConfig.getSS_ServerLogin();

			IdempiereConfig config = new IdempiereConfig(serverConfig.getHostAddress(), serverConfig.getHostPort());
			IdempiereAuthService authService = new IdempiereAuthService(config);

			// ✅ Step 1: Obtain valid token (login or refresh if needed)
			String token = refreshOrLoginToken(syncConfig, serverLogin, authService);

			// ✅ Step 2: Loop through tables to sync
			List<MSSSyncTable> tables = syncConfig.getsyncTable();
			for (MSSSyncTable table : tables) {
				try {
					syncRecords(table, token, config, serverLogin);
				} catch (Exception e) {
					log.log(Level.SEVERE, "Error syncing table: " + table.getAD_Table().getTableName(), e);
				}
			}

			log.info("✅ All tables processed. Final Token in use: " + token);
			return "Sync process completed successfully.";

		} catch (Exception e) {
			log.log(Level.SEVERE, "❌ SyncModelProcess failed: " + e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Refreshes or logs in to obtain a valid token for REST API. Handles
	 * token/refresh expiration logic and updates MSSSyncConfig accordingly.
	 */
	private String refreshOrLoginToken(MSSSyncConfig syncConfig, MSSServerLogin serverLogin,
			IdempiereAuthService authService) throws Exception {

		String token = syncConfig.getToken();
		String refreshToken = syncConfig.getRefreshToken();
		Timestamp tokenExpiry = syncConfig.getTokenExpiry();
		Timestamp refreshExpiry = syncConfig.getRefreshTokenExpiry();
		Timestamp now = new Timestamp(System.currentTimeMillis());

		// Use existing token if valid
		if (token != null && tokenExpiry != null && now.before(tokenExpiry)) {
			log.info("✅ Using existing token (valid until " + tokenExpiry + ")");
			return token;
		}

		// Refresh token if possible
		if (refreshToken != null && refreshExpiry != null && now.before(refreshExpiry)) {
			log.info("🔄 Refreshing token (refresh token valid until " + refreshExpiry + ")");
			RefreshTokenRequest refreshRequest = new RefreshTokenRequest(refreshToken, serverLogin.getSS_Client_ID(),
					syncConfig.getUserID());
			RefreshTokenResponse refreshResponse = authService.refreshToken(refreshRequest);

			token = refreshResponse.getToken();
			refreshToken = refreshResponse.getRefresh_token();

			Timestamp newTokenExpiry = new Timestamp(now.getTime() + 60 * 60 * 1000); // 1 hour
			Timestamp newRefreshExpiry = new Timestamp(now.getTime() + 24 * 60 * 60 * 1000); // 24 hours

			syncConfig.setToken(token);
			syncConfig.setRefreshToken(refreshToken);
			syncConfig.setTokenExpiry(newTokenExpiry);
			syncConfig.setRefreshTokenExpiry(newRefreshExpiry);
			syncConfig.saveEx();

			log.info("✅ Token refreshed and saved. Valid until: " + newTokenExpiry);
			return token;
		}

		// Login to obtain a new token
		log.info("🔑 Logging in to obtain new token...");
		AuthRequest authRequest = new AuthRequest(serverLogin.getUserName(), serverLogin.getPassword(),
				serverLogin.getSS_Client_ID(), serverLogin.getSS_Role_ID(), serverLogin.getSS_Org_ID(),
				serverLogin.getAD_Language());

		AuthResponse authResponse = authService.authenticate(authRequest);
		token = authResponse.getToken();
		refreshToken = authResponse.getRefresh_token();
		int userID = authResponse.getUserId();

		Timestamp newTokenExpiry = new Timestamp(now.getTime() + 60 * 60 * 1000); // 1 hour
		Timestamp newRefreshExpiry = new Timestamp(now.getTime() + 24 * 60 * 60 * 1000); // 24 hours

		syncConfig.setUserID(userID);
		syncConfig.setToken(token);
		syncConfig.setRefreshToken(refreshToken);
		syncConfig.setTokenExpiry(newTokenExpiry);
		syncConfig.setRefreshTokenExpiry(newRefreshExpiry);
		syncConfig.saveEx();

		log.info("✅ New token obtained and saved. Valid until: " + newTokenExpiry);
		return token;
	}

	/**
	 * Fetch PO records and sync them to remote iDempiere server. Handles create
	 * (full payload) and update (only updateable fields) separately.
	 *
	 * @param synTable    Table sync configuration
	 * @param token       Auth token
	 * @param config      iDempiere REST config
	 * @param serverLogin Server login info
	 */
	private void syncRecords(MSSSyncTable synTable, String token, IdempiereConfig config, MSSServerLogin serverLogin)
			throws Exception {

		String tableName = synTable.getAD_Table().getTableName();
		Timestamp syncDate = synTable.getsyncDate();

		List<PO> createdRecords;
		List<PO> updatedRecords;

		// === FIRST SYNC: send all records as create ===
		if (syncDate == null) {
			String whereClause = "AD_Client_ID = ?";
			Object[] params = new Object[] { serverLogin.getSS_Client_ID() };
			createdRecords = new Query(getCtx(), tableName, whereClause, get_TrxName()).setParameters(params)
					.setOnlyActiveRecords(true).list();
			updatedRecords = null; // no updates yet
			log.info("First sync detected. Sending " + createdRecords.size() + " records as create.");
		} else {
			// === Subsequent sync: split created vs updated ===
			// Created
			String whereCreated = "AD_Client_ID = ? AND Created >= ?";
			Object[] paramsCreated = new Object[] { serverLogin.getSS_Client_ID(), syncDate };
			createdRecords = new Query(getCtx(), tableName, whereCreated, get_TrxName()).setParameters(paramsCreated)
					.setOnlyActiveRecords(true).list();

			// Updated
			String whereUpdated = "AD_Client_ID = ? AND Updated >= ? AND Created < ?";
			Object[] paramsUpdated = new Object[] { serverLogin.getSS_Client_ID(), syncDate, syncDate };
			updatedRecords = new Query(getCtx(), tableName, whereUpdated, get_TrxName()).setParameters(paramsUpdated)
					.setOnlyActiveRecords(true).list();

			log.info("Found " + createdRecords.size() + " new records and " + updatedRecords.size()
					+ " updated records for table: " + tableName);
		}

		IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));
		IdempiereRestClient client = new IdempiereRestClient(token);

		// --- Process Created Records (send full payload) ---
		if (createdRecords != null && !createdRecords.isEmpty()) {
			JsonArray payloadCreate = new JsonArray();
			for (PO po : createdRecords) {
				try {
					JsonObject jsonPayload = serializer.toJson(po);
					// Include key column
					String[] keyColumns = po.get_KeyColumns();
					if (keyColumns != null && keyColumns.length == 1) {
						jsonPayload.addProperty(keyColumns[0], po.get_ID());
					}
					// Include UUID if exists
					String uidColumn = po.getUUIDColumnName();
					if (po.get_ColumnIndex(uidColumn) >= 0) {
						String uid = po.get_ValueAsString(uidColumn);
						if (!Util.isEmpty(uid, true)) {
							jsonPayload.addProperty(uidColumn, uid);
						}
					}
					payloadCreate.add(jsonPayload);
				} catch (Exception e) {
					log.log(Level.SEVERE,
							"Failed to serialize PO (create) for table " + tableName + " [ID=" + po.get_ID() + "]", e);
				}
			}

			if (payloadCreate.size() > 0) {
				String endpoint = config.getEndpoint("sync/" + tableName);
				log.info("Posting " + payloadCreate.size() + " created records to " + endpoint);
				try {
					String response = client.post(endpoint, payloadCreate.toString());
					log.info("Create sync successful for " + tableName + ". Response: " + response);
				} catch (Exception ex) {
					log.log(Level.SEVERE, "Create sync failed for table " + tableName, ex);
				}
			}
		}

		// --- Process Updated Records (send only updateable fields) ---
		if (updatedRecords != null && !updatedRecords.isEmpty()) {
			JsonArray payloadUpdate = new JsonArray();
			for (PO po : updatedRecords) {
				try {
					JsonObject jsonPayload = new JsonObject();
					// Include key column for reference
					String[] keyColumns = po.get_KeyColumns();
					if (keyColumns != null && keyColumns.length == 1) {
						jsonPayload.addProperty(keyColumns[0], po.get_ID());
					}
					// Add only updateable fields
					for (int i = 0; i < po.get_ColumnCount(); i++) {
						String columnName = po.get_ColumnName(i);
						if (MTable.get(getCtx(), tableName).getColumn(columnName).isVirtualColumn()
								|| MTable.get(getCtx(), tableName).getColumn(columnName)
										.getAD_Reference_ID() == DisplayType.Button)
							continue;
						if (MTable.get(getCtx(), tableName).getColumn(columnName).isUpdateable()) {
							Object value = po.get_Value(i);
							if (value != null) {
								jsonPayload.addProperty(columnName, value.toString());
							}
						}
					}
					payloadUpdate.add(jsonPayload);
				} catch (Exception e) {
					log.log(Level.SEVERE,
							"Failed to serialize PO (update) for table " + tableName + " [ID=" + po.get_ID() + "]", e);
				}
			}

			if (payloadUpdate.size() > 0) {
				String endpoint = config.getEndpoint("sync/" + tableName);
				log.info("Posting " + payloadUpdate.size() + " updated records to " + endpoint);
				try {
					String response = client.post(endpoint, payloadUpdate.toString());
					log.info("Update sync successful for " + tableName + ". Response: " + response);
				} catch (Exception ex) {
					log.log(Level.SEVERE, "Update sync failed for table " + tableName, ex);
				}
			}
		}

		// --- Update sync timestamp ---
		synTable.setsyncDate(new Timestamp(System.currentTimeMillis()));
		if (!synTable.save()) {
			log.warning("Failed to update syncDate for table: " + tableName);
		} else {
			log.info("Updated syncDate for table: " + tableName);
		}
	}

}
