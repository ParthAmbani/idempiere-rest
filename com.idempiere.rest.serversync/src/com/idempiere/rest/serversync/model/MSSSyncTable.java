package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MSSSyncTable extends X_SS_SyncTable{

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

}
