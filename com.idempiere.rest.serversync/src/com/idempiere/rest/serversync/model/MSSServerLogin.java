package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MSSServerLogin extends X_SS_ServerLogin {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976269758958590383L;

	public MSSServerLogin(Properties ctx, int SS_ServerLogin_ID, String trxName, String... virtualColumns) {
		super(ctx, SS_ServerLogin_ID, trxName, virtualColumns);
	}

	public MSSServerLogin(Properties ctx, int SS_ServerLogin_ID, String trxName) {
		super(ctx, SS_ServerLogin_ID, trxName);
	}

	public MSSServerLogin(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MSSServerLogin(Properties ctx, String SS_ServerLogin_UU, String trxName, String... virtualColumns) {
		super(ctx, SS_ServerLogin_UU, trxName, virtualColumns);
	}

	public MSSServerLogin(Properties ctx, String SS_ServerLogin_UU, String trxName) {
		super(ctx, SS_ServerLogin_UU, trxName);
	}

}
