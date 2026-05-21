package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MSSSyncTable extends X_SS_SyncTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4870259010418189442L;

	public MSSSyncTable(Properties ctx, int SS_SyncTable_ID, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncTable_ID, trxName, virtualColumns);
	}

	public MSSSyncTable(Properties ctx, int SS_SyncTable_ID, String trxName) {
		super(ctx, SS_SyncTable_ID, trxName);
	}

	public MSSSyncTable(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MSSSyncTable(Properties ctx, String SS_SyncTable_UU, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncTable_UU, trxName, virtualColumns);
	}

	public MSSSyncTable(Properties ctx, String SS_SyncTable_UU, String trxName) {
		super(ctx, SS_SyncTable_UU, trxName);
	}

	public void clearLog() {
		DB.executeUpdate(
				"DELETE FROM SS_SyncLog_Detail WHERE SS_SyncLog_Summary_ID IN ( SELECT SS_SyncLog_Summary_ID FROM SS_SyncLog_Summary WHERE SS_SyncTable_ID = ?) ",
				getSS_SyncTable_ID(), get_TrxName());

		DB.executeUpdate("DELETE FROM SS_SyncLog_Summary WHERE  SS_SyncTable_ID = ? ", getSS_SyncTable_ID(),
				get_TrxName());
	}

}
