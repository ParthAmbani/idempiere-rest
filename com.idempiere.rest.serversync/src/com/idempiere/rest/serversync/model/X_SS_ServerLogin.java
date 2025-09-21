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

/** Generated Model for SS_ServerLogin
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="SS_ServerLogin")
public class X_SS_ServerLogin extends PO implements I_SS_ServerLogin, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250914L;

    /** Standard Constructor */
    public X_SS_ServerLogin (Properties ctx, int SS_ServerLogin_ID, String trxName)
    {
      super (ctx, SS_ServerLogin_ID, trxName);
      /** if (SS_ServerLogin_ID == 0)
        {
			setAD_Language (null);
			setPassword (null);
			setSS_Client_ID (0);
			setSS_Org_ID (0);
			setSS_Role_ID (0);
			setSS_ServerLogin_ID (0);
			setUserName (null);
        } */
    }

    /** Standard Constructor */
    public X_SS_ServerLogin (Properties ctx, int SS_ServerLogin_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_ServerLogin_ID, trxName, virtualColumns);
      /** if (SS_ServerLogin_ID == 0)
        {
			setAD_Language (null);
			setPassword (null);
			setSS_Client_ID (0);
			setSS_Org_ID (0);
			setSS_Role_ID (0);
			setSS_ServerLogin_ID (0);
			setUserName (null);
        } */
    }

    /** Standard Constructor */
    public X_SS_ServerLogin (Properties ctx, String SS_ServerLogin_UU, String trxName)
    {
      super (ctx, SS_ServerLogin_UU, trxName);
      /** if (SS_ServerLogin_UU == null)
        {
			setAD_Language (null);
			setPassword (null);
			setSS_Client_ID (0);
			setSS_Org_ID (0);
			setSS_Role_ID (0);
			setSS_ServerLogin_ID (0);
			setUserName (null);
        } */
    }

    /** Standard Constructor */
    public X_SS_ServerLogin (Properties ctx, String SS_ServerLogin_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_ServerLogin_UU, trxName, virtualColumns);
      /** if (SS_ServerLogin_UU == null)
        {
			setAD_Language (null);
			setPassword (null);
			setSS_Client_ID (0);
			setSS_Org_ID (0);
			setSS_Role_ID (0);
			setSS_ServerLogin_ID (0);
			setUserName (null);
        } */
    }

    /** Load Constructor */
    public X_SS_ServerLogin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_SS_ServerLogin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AD_Language AD_Reference_ID=106 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Language.
		@param AD_Language Language for this entity
	*/
	public void setAD_Language (String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language()
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Password.
		@param Password Password of any length (case sensitive)
	*/
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword()
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set Login Client ID.
		@param SS_Client_ID Login Client ID
	*/
	public void setSS_Client_ID (int SS_Client_ID)
	{
		if (SS_Client_ID < 1)
			set_Value (COLUMNNAME_SS_Client_ID, null);
		else
			set_Value (COLUMNNAME_SS_Client_ID, Integer.valueOf(SS_Client_ID));
	}

	/** Get Login Client ID.
		@return Login Client ID	  */
	public int getSS_Client_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_Client_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Login Org ID.
		@param SS_Org_ID Login Org ID
	*/
	public void setSS_Org_ID (int SS_Org_ID)
	{
		if (SS_Org_ID < 1)
			set_Value (COLUMNNAME_SS_Org_ID, null);
		else
			set_Value (COLUMNNAME_SS_Org_ID, Integer.valueOf(SS_Org_ID));
	}

	/** Get Login Org ID.
		@return Login Org ID	  */
	public int getSS_Org_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Login Role ID.
		@param SS_Role_ID Login Role ID
	*/
	public void setSS_Role_ID (int SS_Role_ID)
	{
		if (SS_Role_ID < 1)
			set_Value (COLUMNNAME_SS_Role_ID, null);
		else
			set_Value (COLUMNNAME_SS_Role_ID, Integer.valueOf(SS_Role_ID));
	}

	/** Get Login Role ID.
		@return Login Role ID	  */
	public int getSS_Role_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SS_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Server Login.
		@param SS_ServerLogin_ID Server Login
	*/
	public void setSS_ServerLogin_ID (int SS_ServerLogin_ID)
	{
		if (SS_ServerLogin_ID < 1)
			set_ValueNoCheck (COLUMNNAME_SS_ServerLogin_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_SS_ServerLogin_ID, Integer.valueOf(SS_ServerLogin_ID));
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

	/** Set SS_ServerLogin_UU.
		@param SS_ServerLogin_UU SS_ServerLogin_UU
	*/
	public void setSS_ServerLogin_UU (String SS_ServerLogin_UU)
	{
		set_Value (COLUMNNAME_SS_ServerLogin_UU, SS_ServerLogin_UU);
	}

	/** Get SS_ServerLogin_UU.
		@return SS_ServerLogin_UU	  */
	public String getSS_ServerLogin_UU()
	{
		return (String)get_Value(COLUMNNAME_SS_ServerLogin_UU);
	}

	/** Set User Name.
		@param UserName User Name
	*/
	public void setUserName (String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get User Name.
		@return User Name	  */
	public String getUserName()
	{
		return (String)get_Value(COLUMNNAME_UserName);
	}
}