package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MSSSyncLogSummary extends X_SS_SyncLog_Summary {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6200535740305029991L;

	public MSSSyncLogSummary(Properties ctx, int SS_SyncLog_Summary_ID, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncLog_Summary_ID, trxName, virtualColumns);
	}

	public MSSSyncLogSummary(Properties ctx, int SS_SyncLog_Summary_ID, String trxName) {
		super(ctx, SS_SyncLog_Summary_ID, trxName);
	}

	public MSSSyncLogSummary(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MSSSyncLogSummary(Properties ctx, String SS_SyncLog_Summary_UU, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncLog_Summary_UU, trxName, virtualColumns);
	}

	public MSSSyncLogSummary(Properties ctx, String SS_SyncLog_Summary_UU, String trxName) {
		super(ctx, SS_SyncLog_Summary_UU, trxName);
	}

}
