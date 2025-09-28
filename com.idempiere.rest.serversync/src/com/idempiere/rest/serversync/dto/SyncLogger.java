package com.idempiere.rest.serversync.dto;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.idempiere.rest.serversync.model.MSSSyncLogDetail;
import com.idempiere.rest.serversync.model.MSSSyncLogSummary;
import com.idempiere.rest.serversync.model.MSSSyncTable;

public class SyncLogger {

    private static final Logger log = Logger.getLogger(SyncLogger.class.getName());

    /**
     * Logs the sync response summary and per-record results.
     *
     * @param synTable the table being synced
     * @param response  the raw JSON response string from the sync endpoint
     */
    public static void logSyncResponse(String response, MSSSyncTable synTable, Properties ctx, String trxName) {
        try {
            JsonObject respJson = JsonParser.parseString(response).getAsJsonObject();

            // === Extract Summary Data ===
            String status = respJson.has("status") ? respJson.get("status").getAsString() : "unknown";
            int successCount = respJson.has("successCount") ? respJson.get("successCount").getAsInt() : 0;
            int failedCount = respJson.has("failedCount") ? respJson.get("failedCount").getAsInt() : 0;

            log.info("=== SYNC RESULT (" + synTable + ") ===");
            log.info("Status: " + status + ", Success: " + successCount + ", Failed: " + failedCount);

            // === Save Summary Row ===
            MSSSyncLogSummary summary =  new MSSSyncLogSummary(ctx, 0, trxName);
            summary.setSS_SyncTable_ID(synTable.getSS_SyncTable_ID());
            summary.setStatus(status);
            summary.setSuccessCount(successCount);
            summary.setFailedCount(failedCount);
            summary.setsyncDate(new Timestamp(System.currentTimeMillis()));
            summary.saveEx();

            int summaryID = summary.getSS_SyncLog_Summary_ID();

            // === Detailed Per-Record Logging & Save ===
            if (respJson.has("results")) {
                JsonArray results = respJson.getAsJsonArray("results");
                for (JsonElement elem : results) {
                    JsonObject rec = elem.getAsJsonObject();

                    boolean success = rec.has("success") && rec.get("success").getAsBoolean();
                    String action = rec.has("action") ? rec.get("action").getAsString() : "unknown";
                    String message = rec.has("message") ? rec.get("message").getAsString() : "";

                    // Build identifier string
                    String identifierStr = "";
                    if (rec.has("identifier")) {
                        JsonObject identifier = rec.getAsJsonObject("identifier");
                        StringBuilder idSb = new StringBuilder();
                        for (String key : identifier.keySet()) {
                            idSb.append(key).append("=").append(identifier.get(key).getAsString()).append(" ");
                        }
                        identifierStr = idSb.toString().trim();
                    }

                    // === Log to console ===
                    if (success) {
                        log.info("✔ [" + action.toUpperCase() + "] " + identifierStr + " - " + message);
                    } else {
                        log.warning("✖ [" + action.toUpperCase() + "] " + identifierStr + " - " + message);
                    }

                    // === Save Detail Row ===
                    MSSSyncLogDetail detail = new MSSSyncLogDetail(ctx, 0, trxName);
                    detail.setSS_SyncLog_Summary_ID(summaryID);
                    detail.setIsSuccess(success);
                    detail.setMessage(action + ":" + message);
                    detail.setIdentifierInfo(identifierStr);
                    detail.saveEx();
                }
            }

        } catch (Exception ex) {
            log.severe("Error while processing sync response for " + synTable + ": " + ex.getMessage());
        }
    }

}

