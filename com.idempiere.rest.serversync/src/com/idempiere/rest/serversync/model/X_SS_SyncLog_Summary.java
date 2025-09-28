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

/** Generated Model for SS_SyncLog_Summary
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="SS_SyncLog_Summary")
public class X_SS_SyncLog_Summary extends PO implements I_SS_SyncLog_Summary, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250928L;

    /** Standard Constructor */
    public X_SS_SyncLog_Summary (Properties ctx, int SS_SyncLog_Summary_ID, String trxName)
    {
      super (ctx, SS_SyncLog_Summary_ID, trxName);
      /** if (SS_SyncLog_Summary_ID == 0)
        {
			setSS_SyncLog_Summary_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncLog_Summary (Properties ctx, int SS_SyncLog_Summary_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncLog_Summary_ID, trxName, virtualColumns);
      /** if (SS_SyncLog_Summary_ID == 0)
        {
			setSS_SyncLog_Summary_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncLog_Summary (Properties ctx, String SS_SyncLog_Summary_UU, String trxName)
    {
      super (ctx, SS_SyncLog_Summary_UU, trxName);
      /** if (SS_SyncLog_Summary_UU == null)
        {
			setSS_SyncLog_Summary_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncLog_Summary (Properties ctx, String SS_SyncLog_Summary_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncLog_Summary_UU, trxName, virtualColumns);
      /** if (SS_SyncLog_Summary_UU == null)
        {
			setSS_SyncLog_Summary_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SS_SyncLog_Summary (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_SS_SyncLog_Summary[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Failed Count.
		@param FailedCount Failed Count
	*/
	public void setFailedCount (int FailedCount)
	{
		set_Value (COLUMNNAME_FailedCount, Integer.valueOf(FailedCount));
	}

	/** Get Failed Count.
		@return Failed Count	  */
	public int getFailedCount()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FailedCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sync Table Log Summary .
		@param SS_SyncLog_Summary_ID Sync Table Log Summary 
	*/
	public void setSS_SyncLog_Summary_ID (int SS_SyncLog_Summary_ID)
	{
		if (SS_SyncLog_Summary_ID < 1)
			set_ValueNoCheck (COLUMNNAME_SS_SyncLog_Summary_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_SS_SyncLog_Summary_ID, Integer.valueOf(SS_SyncLog_Summary_ID));
	}

	/** Get Sync Table Log Summary .
		@return Sync Table Log Summary 	  */
	public int getSS_SyncLog_Summary_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_SyncLog_Summary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SS_SyncLog_Summary_UU.
		@param SS_SyncLog_Summary_UU SS_SyncLog_Summary_UU
	*/
	public void setSS_SyncLog_Summary_UU (String SS_SyncLog_Summary_UU)
	{
		set_Value (COLUMNNAME_SS_SyncLog_Summary_UU, SS_SyncLog_Summary_UU);
	}

	/** Get SS_SyncLog_Summary_UU.
		@return SS_SyncLog_Summary_UU	  */
	public String getSS_SyncLog_Summary_UU()
	{
		return (String)get_Value(COLUMNNAME_SS_SyncLog_Summary_UU);
	}

	public I_SS_SyncTable getSS_SyncTable() throws RuntimeException
	{
		return (I_SS_SyncTable)MTable.get(getCtx(), I_SS_SyncTable.Table_ID)
			.getPO(getSS_SyncTable_ID(), get_TrxName());
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

	/** Set Status.
		@param Status Status of the currently running check
	*/
	public void setStatus (String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus()
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Set Success Count.
		@param SuccessCount Success Count
	*/
	public void setSuccessCount (int SuccessCount)
	{
		set_Value (COLUMNNAME_SuccessCount, Integer.valueOf(SuccessCount));
	}

	/** Get Success Count.
		@return Success Count	  */
	public int getSuccessCount()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SuccessCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
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