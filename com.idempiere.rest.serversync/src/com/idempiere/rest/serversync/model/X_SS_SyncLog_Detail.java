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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for SS_SyncLog_Detail
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="SS_SyncLog_Detail")
public class X_SS_SyncLog_Detail extends PO implements I_SS_SyncLog_Detail, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250928L;

    /** Standard Constructor */
    public X_SS_SyncLog_Detail (Properties ctx, int SS_SyncLog_Detail_ID, String trxName)
    {
      super (ctx, SS_SyncLog_Detail_ID, trxName);
      /** if (SS_SyncLog_Detail_ID == 0)
        {
			setIsSuccess (false);
// N
			setSS_SyncLog_Detail_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncLog_Detail (Properties ctx, int SS_SyncLog_Detail_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncLog_Detail_ID, trxName, virtualColumns);
      /** if (SS_SyncLog_Detail_ID == 0)
        {
			setIsSuccess (false);
// N
			setSS_SyncLog_Detail_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncLog_Detail (Properties ctx, String SS_SyncLog_Detail_UU, String trxName)
    {
      super (ctx, SS_SyncLog_Detail_UU, trxName);
      /** if (SS_SyncLog_Detail_UU == null)
        {
			setIsSuccess (false);
// N
			setSS_SyncLog_Detail_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncLog_Detail (Properties ctx, String SS_SyncLog_Detail_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncLog_Detail_UU, trxName, virtualColumns);
      /** if (SS_SyncLog_Detail_UU == null)
        {
			setIsSuccess (false);
// N
			setSS_SyncLog_Detail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SS_SyncLog_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_SS_SyncLog_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Identifier Info.
		@param IdentifierInfo Identifier Info
	*/
	public void setIdentifierInfo (String IdentifierInfo)
	{
		set_Value (COLUMNNAME_IdentifierInfo, IdentifierInfo);
	}

	/** Get Identifier Info.
		@return Identifier Info	  */
	public String getIdentifierInfo()
	{
		return (String)get_Value(COLUMNNAME_IdentifierInfo);
	}

	/** Set Success.
		@param IsSuccess Success
	*/
	public void setIsSuccess (boolean IsSuccess)
	{
		set_Value (COLUMNNAME_IsSuccess, Boolean.valueOf(IsSuccess));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess()
	{
		Object oo = get_Value(COLUMNNAME_IsSuccess);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Message.
		@param Message Message
	*/
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return Message
	  */
	public String getMessage()
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set Sync Log Detail.
		@param SS_SyncLog_Detail_ID Sync Log Detail
	*/
	public void setSS_SyncLog_Detail_ID (int SS_SyncLog_Detail_ID)
	{
		if (SS_SyncLog_Detail_ID < 1)
			set_ValueNoCheck (COLUMNNAME_SS_SyncLog_Detail_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_SS_SyncLog_Detail_ID, Integer.valueOf(SS_SyncLog_Detail_ID));
	}

	/** Get Sync Log Detail.
		@return Sync Log Detail	  */
	public int getSS_SyncLog_Detail_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_SyncLog_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SS_SyncLog_Detail_UU.
		@param SS_SyncLog_Detail_UU SS_SyncLog_Detail_UU
	*/
	public void setSS_SyncLog_Detail_UU (String SS_SyncLog_Detail_UU)
	{
		set_Value (COLUMNNAME_SS_SyncLog_Detail_UU, SS_SyncLog_Detail_UU);
	}

	/** Get SS_SyncLog_Detail_UU.
		@return SS_SyncLog_Detail_UU	  */
	public String getSS_SyncLog_Detail_UU()
	{
		return (String)get_Value(COLUMNNAME_SS_SyncLog_Detail_UU);
	}

	public I_SS_SyncLog_Summary getSS_SyncLog_Summary() throws RuntimeException
	{
		return (I_SS_SyncLog_Summary)MTable.get(getCtx(), I_SS_SyncLog_Summary.Table_ID)
			.getPO(getSS_SyncLog_Summary_ID(), get_TrxName());
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
}