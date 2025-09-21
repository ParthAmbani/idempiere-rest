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

/** Generated Model for SS_ServerConfig
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="SS_ServerConfig")
public class X_SS_ServerConfig extends PO implements I_SS_ServerConfig, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250914L;

    /** Standard Constructor */
    public X_SS_ServerConfig (Properties ctx, int SS_ServerConfig_ID, String trxName)
    {
      super (ctx, SS_ServerConfig_ID, trxName);
      /** if (SS_ServerConfig_ID == 0)
        {
			setHostAddress (null);
			setHostPort (0);
			setName (null);
			setSS_ServerConfig_ID (0);
			setisUseHTTPS (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_SS_ServerConfig (Properties ctx, int SS_ServerConfig_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_ServerConfig_ID, trxName, virtualColumns);
      /** if (SS_ServerConfig_ID == 0)
        {
			setHostAddress (null);
			setHostPort (0);
			setName (null);
			setSS_ServerConfig_ID (0);
			setisUseHTTPS (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_SS_ServerConfig (Properties ctx, String SS_ServerConfig_UU, String trxName)
    {
      super (ctx, SS_ServerConfig_UU, trxName);
      /** if (SS_ServerConfig_UU == null)
        {
			setHostAddress (null);
			setHostPort (0);
			setName (null);
			setSS_ServerConfig_ID (0);
			setisUseHTTPS (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_SS_ServerConfig (Properties ctx, String SS_ServerConfig_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, SS_ServerConfig_UU, trxName, virtualColumns);
      /** if (SS_ServerConfig_UU == null)
        {
			setHostAddress (null);
			setHostPort (0);
			setName (null);
			setSS_ServerConfig_ID (0);
			setisUseHTTPS (false);
// N
        } */
    }

    /** Load Constructor */
    public X_SS_ServerConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_SS_ServerConfig[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Host Address.
		@param HostAddress Host Address URL or DNS
	*/
	public void setHostAddress (String HostAddress)
	{
		set_Value (COLUMNNAME_HostAddress, HostAddress);
	}

	/** Get Host Address.
		@return Host Address URL or DNS
	  */
	public String getHostAddress()
	{
		return (String)get_Value(COLUMNNAME_HostAddress);
	}

	/** Set Host port.
		@param HostPort Host Communication Port
	*/
	public void setHostPort (int HostPort)
	{
		set_Value (COLUMNNAME_HostPort, Integer.valueOf(HostPort));
	}

	/** Get Host port.
		@return Host Communication Port
	  */
	public int getHostPort()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HostPort);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Server Config.
		@param SS_ServerConfig_ID Server Config
	*/
	public void setSS_ServerConfig_ID (int SS_ServerConfig_ID)
	{
		if (SS_ServerConfig_ID < 1)
			set_ValueNoCheck (COLUMNNAME_SS_ServerConfig_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_SS_ServerConfig_ID, Integer.valueOf(SS_ServerConfig_ID));
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

	/** Set SS_ServerConfig_UU.
		@param SS_ServerConfig_UU SS_ServerConfig_UU
	*/
	public void setSS_ServerConfig_UU (String SS_ServerConfig_UU)
	{
		set_Value (COLUMNNAME_SS_ServerConfig_UU, SS_ServerConfig_UU);
	}

	/** Get SS_ServerConfig_UU.
		@return SS_ServerConfig_UU	  */
	public String getSS_ServerConfig_UU()
	{
		return (String)get_Value(COLUMNNAME_SS_ServerConfig_UU);
	}

	/** Set Use HTTPS .
		@param isUseHTTPS Indicates whether to use HTTPS protocol for server communication.
	*/
	public void setisUseHTTPS (boolean isUseHTTPS)
	{
		set_Value (COLUMNNAME_isUseHTTPS, Boolean.valueOf(isUseHTTPS));
	}

	/** Get Use HTTPS .
		@return Indicates whether to use HTTPS protocol for server communication.
	  */
	public boolean isUseHTTPS()
	{
		Object oo = get_Value(COLUMNNAME_isUseHTTPS);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}