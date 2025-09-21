package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MSSServerConfig extends X_SS_ServerConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658235072792714396L;

	public MSSServerConfig(Properties ctx, int SS_ServerConfig_ID, String trxName, String... virtualColumns) {
		super(ctx, SS_ServerConfig_ID, trxName, virtualColumns);
	}

	public MSSServerConfig(Properties ctx, int SS_ServerConfig_ID, String trxName) {
		super(ctx, SS_ServerConfig_ID, trxName);
	}

	public MSSServerConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MSSServerConfig(Properties ctx, String SS_ServerConfig_UU, String trxName, String... virtualColumns) {
		super(ctx, SS_ServerConfig_UU, trxName, virtualColumns);
	}

	public MSSServerConfig(Properties ctx, String SS_ServerConfig_UU, String trxName) {
		super(ctx, SS_ServerConfig_UU, trxName);
	}

}
