package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MSSSyncLogDetail extends X_SS_SyncLog_Detail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3486088657876016821L;

	public MSSSyncLogDetail(Properties ctx, int SS_SyncLog_Detail_ID, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncLog_Detail_ID, trxName, virtualColumns);
	}

	public MSSSyncLogDetail(Properties ctx, int SS_SyncLog_Detail_ID, String trxName) {
		super(ctx, SS_SyncLog_Detail_ID, trxName);
	}

	public MSSSyncLogDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MSSSyncLogDetail(Properties ctx, String SS_SyncLog_Detail_UU, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncLog_Detail_UU, trxName, virtualColumns);
	}

	public MSSSyncLogDetail(Properties ctx, String SS_SyncLog_Detail_UU, String trxName) {
		super(ctx, SS_SyncLog_Detail_UU, trxName);
		// TODO Auto-generated constructor stub
	}

}
