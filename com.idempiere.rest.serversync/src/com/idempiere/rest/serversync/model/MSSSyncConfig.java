package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MSSSyncConfig extends X_SS_SyncConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3347556827487338230L;

	public MSSSyncConfig(Properties ctx, int SS_SyncConfig_ID, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncConfig_ID, trxName, virtualColumns);
	}

	public MSSSyncConfig(Properties ctx, int SS_SyncConfig_ID, String trxName) {
		super(ctx, SS_SyncConfig_ID, trxName);
	}

	public MSSSyncConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MSSSyncConfig(Properties ctx, String SS_SyncConfig_UU, String trxName, String... virtualColumns) {
		super(ctx, SS_SyncConfig_UU, trxName, virtualColumns);
	}

	public MSSSyncConfig(Properties ctx, String SS_SyncConfig_UU, String trxName) {
		super(ctx, SS_SyncConfig_UU, trxName);
	}

	public List<MSSSyncTable> getsyncTable() {
		return new Query(getCtx(), MSSSyncTable.Table_Name, MSSSyncTable.COLUMNNAME_SS_SyncConfig_ID + " = ? ",
				get_TrxName()).setOnlyActiveRecords(true).setParameters(getSS_SyncConfig_ID()).list();
	}

}
