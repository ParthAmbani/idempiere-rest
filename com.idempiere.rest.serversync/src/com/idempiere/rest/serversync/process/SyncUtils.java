package com.idempiere.rest.serversync.process;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adempiere.exceptions.AdempiereException;

import com.idempiere.rest.serversync.dto.AuthRequest;
import com.idempiere.rest.serversync.dto.AuthResponse;
import com.idempiere.rest.serversync.dto.RefreshTokenRequest;
import com.idempiere.rest.serversync.dto.RefreshTokenResponse;
import com.idempiere.rest.serversync.model.MSSServerLogin;
import com.idempiere.rest.serversync.model.MSSSyncConfig;
import com.idempiere.rest.serversync.model.MSSSyncLogSummary;
import com.idempiere.rest.serversync.model.MSSSyncTable;
import com.idempiere.rest.serversync.service.IdempiereAuthService;

public class SyncUtils {
	private static final Logger log = Logger.getLogger(SyncUtils.class.getName());

	/**
	 * Refreshes or logs in to obtain a valid token for REST API. Handles
	 * token/refresh expiration logic and updates MSSSyncConfig.
	 */
	public static String refreshOrLoginToken(MSSSyncConfig syncConfig, MSSServerLogin serverLogin,
			IdempiereAuthService authService, MSSSyncTable synTable) throws Exception {

		try {

			String token = syncConfig.getToken();
			String refreshToken = syncConfig.getRefreshToken();

			Timestamp tokenExpiry = syncConfig.getTokenExpiry();
			Timestamp refreshExpiry = syncConfig.getRefreshTokenExpiry();

			Timestamp now = new Timestamp(System.currentTimeMillis());

			// =====================================================
			// 1. USE EXISTING TOKEN
			// =====================================================
			if (token != null && tokenExpiry != null && now.before(tokenExpiry)) {

				log.info("Using existing token (valid until " + tokenExpiry + ")");

				logSummary(synTable, "Using existing token for user " + syncConfig.getUserID());

				return token;
			}

			// =====================================================
			// 2. REFRESH TOKEN
			// =====================================================
			if (refreshToken != null && refreshExpiry != null && now.before(refreshExpiry)) {

				try {

					log.info("Refreshing token (valid until " + refreshExpiry + ")");

					logSummary(synTable, "Refreshing token for user " + syncConfig.getUserID());

					RefreshTokenRequest refreshRequest = new RefreshTokenRequest(refreshToken,
							serverLogin.getSS_Client_ID(), syncConfig.getUserID());

					RefreshTokenResponse refreshResponse = authService.refreshToken(refreshRequest);

					// Validate response
					if (refreshResponse == null || refreshResponse.getToken() == null
							|| refreshResponse.getRefresh_token() == null) {

						throw new RuntimeException("Invalid refresh token response");
					}

					token = refreshResponse.getToken();
					refreshToken = refreshResponse.getRefresh_token();

					Timestamp newTokenExpiry = new Timestamp(now.getTime() + 60 * 60 * 1000);

					Timestamp newRefreshExpiry = new Timestamp(now.getTime() + 24 * 60 * 60 * 1000);

					syncConfig.setToken(token);
					syncConfig.setRefreshToken(refreshToken);
					syncConfig.setTokenExpiry(newTokenExpiry);
					syncConfig.setRefreshTokenExpiry(newRefreshExpiry);

					syncConfig.saveEx();

					log.info("Token refreshed successfully");

					logSummary(synTable, "Token refreshed successfully for user " + syncConfig.getUserID());

					return token;

				} catch (Exception refreshEx) {

					String refreshError = "Token refresh failed for user " + syncConfig.getUserID() + " : "
							+ refreshEx.getMessage();

					log.log(Level.SEVERE, refreshError, refreshEx);

					logSummary(synTable, refreshError);

					// Clear invalid tokens
					try {

						syncConfig.setToken(null);
						syncConfig.setRefreshToken(null);
						syncConfig.setTokenExpiry(null);
						syncConfig.setRefreshTokenExpiry(null);

						syncConfig.saveEx();

					} catch (Exception clearEx) {

						String clearError = "Failed to clear invalid tokens for user " + syncConfig.getUserID() + " : "
								+ clearEx.getMessage();

						log.log(Level.SEVERE, clearError, clearEx);

						logSummary(synTable, clearError);
					}

					log.info("Falling back to fresh login...");
				}
			}

			// =====================================================
			// 3. LOGIN
			// =====================================================
			log.info("Logging in to obtain new token...");

			logSummary(synTable, "Logging in to obtain new token for user " + syncConfig.getUserID());

			AuthRequest authRequest = new AuthRequest(serverLogin.getUserName(), serverLogin.getPassword(),
					serverLogin.getSS_Client_ID(), serverLogin.getSS_Role_ID(), serverLogin.getSS_Org_ID(),
					serverLogin.getAD_Language());

			AuthResponse authResponse = authService.authenticate(authRequest);

			// Validate response
			if (authResponse == null || authResponse.getToken() == null || authResponse.getRefresh_token() == null) {

				throw new RuntimeException("Authentication failed: Invalid response");
			}

			token = authResponse.getToken();
			refreshToken = authResponse.getRefresh_token();

			int userID = authResponse.getUserId();

			Timestamp newTokenExpiry = new Timestamp(now.getTime() + 60 * 60 * 1000);

			Timestamp newRefreshExpiry = new Timestamp(now.getTime() + 24 * 60 * 60 * 1000);

			syncConfig.setUserID(userID);
			syncConfig.setToken(token);
			syncConfig.setRefreshToken(refreshToken);
			syncConfig.setTokenExpiry(newTokenExpiry);
			syncConfig.setRefreshTokenExpiry(newRefreshExpiry);

			syncConfig.saveEx();

			log.info("New token obtained successfully");

			logSummary(synTable, "New token obtained successfully for user " + syncConfig.getUserID());

			return token;

		} catch (Exception ex) {

			String finalError = "Authentication process failed for user " + syncConfig.getUserID() + " : "
					+ ex.getMessage();

			log.log(Level.SEVERE, finalError, ex);

			logSummary(synTable, finalError);

			throw new AdempiereException(finalError, ex);
		}
	}

	public static void logSummary(MSSSyncTable synTable, String msg) {
		if (synTable != null) {
			MSSSyncLogSummary summary = new MSSSyncLogSummary(synTable.getCtx(), 0, synTable.get_TrxName());
			summary.setSS_SyncTable_ID(synTable.getSS_SyncTable_ID());
			summary.setStatus(msg);
			summary.setSuccessCount(0);
			summary.setFailedCount(0);
			summary.setsyncDate(new Timestamp(System.currentTimeMillis()));
			summary.saveEx();
		}
	}

}
