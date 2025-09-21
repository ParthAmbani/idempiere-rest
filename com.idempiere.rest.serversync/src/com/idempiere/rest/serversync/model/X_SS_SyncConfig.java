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
import org.compiere.util.KeyNamePair;

/** Generated Model for SS_SyncConfig
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="SS_SyncConfig")
public class X_SS_SyncConfig extends PO implements I_SS_SyncConfig, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250914L;

    /** Standard Constructor */
    public X_SS_SyncConfig (Properties ctx, int SS_SyncConfig_ID, String trxName)
    {
      super (ctx, SS_SyncConfig_ID, trxName);
      /** if (SS_SyncConfig_ID == 0)
        {
			setSS_ServerConfig_ID (0);
			setSS_ServerLogin_ID (0);
			setSS_SyncConfig_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncConfig (Properties ctx, int SS_SyncConfig_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncConfig_ID, trxName, virtualColumns);
      /** if (SS_SyncConfig_ID == 0)
        {
			setSS_ServerConfig_ID (0);
			setSS_ServerLogin_ID (0);
			setSS_SyncConfig_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncConfig (Properties ctx, String SS_SyncConfig_UU, String trxName)
    {
      super (ctx, SS_SyncConfig_UU, trxName);
      /** if (SS_SyncConfig_UU == null)
        {
			setSS_ServerConfig_ID (0);
			setSS_ServerLogin_ID (0);
			setSS_SyncConfig_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_SS_SyncConfig (Properties ctx, String SS_SyncConfig_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_SyncConfig_UU, trxName, virtualColumns);
      /** if (SS_SyncConfig_UU == null)
        {
			setSS_ServerConfig_ID (0);
			setSS_ServerLogin_ID (0);
			setSS_SyncConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SS_SyncConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_SS_SyncConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Refresh Token.
		@param RefreshToken Refresh Token
	*/
	public void setRefreshToken (String RefreshToken)
	{
		set_Value (COLUMNNAME_RefreshToken, RefreshToken);
	}

	/** Get Refresh Token.
		@return Refresh Token	  */
	public String getRefreshToken()
	{
		return (String)get_Value(COLUMNNAME_RefreshToken);
	}

	/** Set Refresh Token Expiry.
		@param RefreshTokenExpiry Refresh Token Expiry
	*/
	public void setRefreshTokenExpiry (Timestamp RefreshTokenExpiry)
	{
		set_Value (COLUMNNAME_RefreshTokenExpiry, RefreshTokenExpiry);
	}

	/** Get Refresh Token Expiry.
		@return Refresh Token Expiry	  */
	public Timestamp getRefreshTokenExpiry()
	{
		return (Timestamp)get_Value(COLUMNNAME_RefreshTokenExpiry);
	}

	public I_SS_ServerConfig getSS_ServerConfig() throws RuntimeException
	{
		return (I_SS_ServerConfig)MTable.get(getCtx(), I_SS_ServerConfig.Table_ID)
			.getPO(getSS_ServerConfig_ID(), get_TrxName());
	}

	/** Set Server Config.
		@param SS_ServerConfig_ID Server Config
	*/
	public void setSS_ServerConfig_ID (int SS_ServerConfig_ID)
	{
		if (SS_ServerConfig_ID < 1)
			set_Value (COLUMNNAME_SS_ServerConfig_ID, null);
		else
			set_Value (COLUMNNAME_SS_ServerConfig_ID, Integer.valueOf(SS_ServerConfig_ID));
	}

	/** Get Server Config.
		@return Server Config	  */
	public int getSS_ServerConfig_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_ServerConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_SS_ServerLogin getSS_ServerLogin() throws RuntimeException
	{
		return (I_SS_ServerLogin)MTable.get(getCtx(), I_SS_ServerLogin.Table_ID)
			.getPO(getSS_ServerLogin_ID(), get_TrxName());
	}

	/** Set Server Login.
		@param SS_ServerLogin_ID Server Login
	*/
	public void setSS_ServerLogin_ID (int SS_ServerLogin_ID)
	{
		if (SS_ServerLogin_ID < 1)
			set_Value (COLUMNNAME_SS_ServerLogin_ID, null);
		else
			set_Value (COLUMNNAME_SS_ServerLogin_ID, Integer.valueOf(SS_ServerLogin_ID));
	}

	/** Get Server Login.
		@return Server Login	  */
	public int getSS_ServerLogin_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_ServerLogin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getSS_ServerLogin_ID()));
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

	/** Set SS_SyncConfig_UU.
		@param SS_SyncConfig_UU SS_SyncConfig_UU
	*/
	public void setSS_SyncConfig_UU (String SS_SyncConfig_UU)
	{
		set_Value (COLUMNNAME_SS_SyncConfig_UU, SS_SyncConfig_UU);
	}

	/** Get SS_SyncConfig_UU.
		@return SS_SyncConfig_UU	  */
	public String getSS_SyncConfig_UU()
	{
		return (String)get_Value(COLUMNNAME_SS_SyncConfig_UU);
	}

	/** Set Token.
		@param Token Token
	*/
	public void setToken (String Token)
	{
		set_ValueNoCheck (COLUMNNAME_Token, Token);
	}

	/** Get Token.
		@return Token	  */
	public String getToken()
	{
		return (String)get_Value(COLUMNNAME_Token);
	}

	/** Set Token Expiry.
		@param TokenExpiry Token Expiry
	*/
	public void setTokenExpiry (Timestamp TokenExpiry)
	{
		set_ValueNoCheck (COLUMNNAME_TokenExpiry, TokenExpiry);
	}

	/** Get Token Expiry.
		@return Token Expiry	  */
	public Timestamp getTokenExpiry()
	{
		return (Timestamp)get_Value(COLUMNNAME_TokenExpiry);
	}

	/** Set User ID.
		@param UserID User ID or account number
	*/
	public void setUserID (int UserID)
	{
		set_Value (COLUMNNAME_UserID, Integer.valueOf(UserID));
	}

	/** Get User ID.
		@return User ID or account number
	  */
	public int getUserID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}