package com.idempiere.rest.serversync.factory;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.idempiere.rest.serversync.model.MSSServerConfig;
import com.idempiere.rest.serversync.model.MSSServerLogin;
import com.idempiere.rest.serversync.model.MSSSyncConfig;
import com.idempiere.rest.serversync.model.MSSSyncLogDetail;
import com.idempiere.rest.serversync.model.MSSSyncLogSummary;
import com.idempiere.rest.serversync.model.MSSSyncTable;

public class SSModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equalsIgnoreCase(MSSServerConfig.Table_Name)) {
			return MSSServerConfig.class;
		} else if (tableName.equalsIgnoreCase(MSSServerLogin.Table_Name)) {
			return MSSServerLogin.class;
		} else if (tableName.equalsIgnoreCase(MSSSyncConfig.Table_Name)) {
			return MSSSyncConfig.class;
		} else if (tableName.equalsIgnoreCase(MSSSyncTable.Table_Name)) {
			return MSSSyncTable.class;
		} else if (tableName.equalsIgnoreCase(MSSSyncLogSummary.Table_Name)) {
			return MSSSyncLogSummary.class;
		} else if (tableName.equalsIgnoreCase(MSSSyncLogDetail.Table_Name)) {
			return MSSSyncLogDetail.class;
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (tableName.equalsIgnoreCase(MSSServerConfig.Table_Name)) {
			return new MSSServerConfig(Env.getCtx(), Record_ID, trxName);
		} else if (tableName.equalsIgnoreCase(MSSServerLogin.Table_Name)) {
			return new MSSServerLogin(Env.getCtx(), Record_ID, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncConfig.Table_Name)) {
			return new MSSSyncConfig(Env.getCtx(), Record_ID, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncTable.Table_Name)) {
			return new MSSSyncTable(Env.getCtx(), Record_ID, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncLogSummary.Table_Name)) {
			return new MSSSyncLogSummary(Env.getCtx(), Record_ID, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncLogDetail.Table_Name)) {
			return new MSSSyncLogDetail(Env.getCtx(), Record_ID, trxName);
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (tableName.equalsIgnoreCase(MSSServerConfig.Table_Name)) {
			return new MSSServerConfig(Env.getCtx(), rs, trxName);
		} else if (tableName.equalsIgnoreCase(MSSServerLogin.Table_Name)) {
			return new MSSServerLogin(Env.getCtx(), rs, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncConfig.Table_Name)) {
			return new MSSSyncConfig(Env.getCtx(), rs, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncTable.Table_Name)) {
			return new MSSSyncTable(Env.getCtx(), rs, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncLogSummary.Table_Name)) {
			return new MSSSyncLogSummary(Env.getCtx(), rs, trxName);
		} else if (tableName.equalsIgnoreCase(MSSSyncLogDetail.Table_Name)) {
			return new MSSSyncLogDetail(Env.getCtx(), rs, trxName);
		}
		return null;
	}

}
