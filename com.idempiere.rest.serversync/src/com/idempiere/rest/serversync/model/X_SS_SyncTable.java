/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.idempiere.rest.serversync.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for SS_SyncTable
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="SS_SyncTable")
public class X_SS_SyncTable extends PO implements I_SS_SyncTable, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250921L;

    /** Standard Constructor */
    public X_SS_SyncTable (Properties ctx, int SS_SyncTable_ID, String trxName)
    {
      super (ctx, SS_SyncTable_ID, trxName);
      /** if (SS_SyncTable_ID == 0)
        {
			setSS_SyncTable_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncTable (Properties ctx, int SS_SyncTable_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncTable_ID, trxName, virtualColumns);
      /** if (SS_SyncTable_ID == 0)
        {
			setSS_SyncTable_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncTable (Properties ctx, String SS_SyncTable_UU, String trxName)
    {
      super (ctx, SS_SyncTable_UU, trxName);
      /** if (SS_SyncTable_UU == null)
        {
			setSS_SyncTable_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncTable (Properties ctx, String SS_SyncTable_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncTable_UU, trxName, virtualColumns);
      /** if (SS_SyncTable_UU == null)
        {
			setSS_SyncTable_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SS_SyncTable (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_SS_SyncTable[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_ID)
			.getPO(getAD_Table_ID(), get_TrxName());
	}

	/** Set Table.
		@param AD_Table_ID Database Table information
	*/
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_SS_SyncConfig getSS_SyncConfig() throws RuntimeException
	{
		return (I_SS_SyncConfig)MTable.get(getCtx(), I_SS_SyncConfig.Table_ID)
			.getPO(getSS_SyncConfig_ID(), get_TrxName());
	}

	/** Set Synchronize Config.
		@param SS_SyncConfig_ID Synchronize Config
	*/
	public void setSS_SyncConfig_ID (int SS_SyncConfig_ID)
	{
		if (SS_SyncConfig_ID < 1)
			set_ValueNoCheck (COLUMNNAME_SS_SyncConfig_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_SS_SyncConfig_ID, Integer.valueOf(SS_SyncConfig_ID));
	}

	/** Get Synchronize Config.
		@return Synchronize Config	  */
	public int getSS_SyncConfig_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_SyncConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Synchronize Table.
		@param SS_SyncTable_ID Synchronize Table
	*/
	public void setSS_SyncTable_ID (int SS_SyncTable_ID)
	{
		if (SS_SyncTable_ID < 1)
			set_ValueNoCheck (COLUMNNAME_SS_SyncTable_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_SS_SyncTable_ID, Integer.valueOf(SS_SyncTable_ID));
	}

	/** Get Synchronize Table.
		@return Synchronize Table	  */
	public int getSS_SyncTable_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_SyncTable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SS_SyncTable_UU.
		@param SS_SyncTable_UU SS_SyncTable_UU
	*/
	public void setSS_SyncTable_UU (String SS_SyncTable_UU)
	{
		set_Value (COLUMNNAME_SS_SyncTable_UU, SS_SyncTable_UU);
	}

	/** Get SS_SyncTable_UU.
		@return SS_SyncTable_UU	  */
	public String getSS_SyncTable_UU()
	{
		return (String)get_Value(COLUMNNAME_SS_SyncTable_UU);
	}

	/** Set sync Date.
		@param syncDate sync Date
	*/
	public void setsyncDate (Timestamp syncDate)
	{
		set_Value (COLUMNNAME_syncDate, syncDate);
	}

	/** Get sync Date.
		@return sync Date	  */
	public Timestamp getsyncDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_syncDate);
	}
}