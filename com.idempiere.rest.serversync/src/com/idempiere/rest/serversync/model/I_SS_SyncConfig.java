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
package com.idempiere.rest.serversync.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for SS_SyncConfig
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_SS_SyncConfig 
{

    /** TableName=SS_SyncConfig */
    public static final String Table_Name = "SS_SyncConfig";

    /** AD_Table_ID=1000033 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name RefreshToken */
    public static final String COLUMNNAME_RefreshToken = "RefreshToken";

	/** Set Refresh Token	  */
	public void setRefreshToken (String RefreshToken);

	/** Get Refresh Token	  */
	public String getRefreshToken();

    /** Column name RefreshTokenExpiry */
    public static final String COLUMNNAME_RefreshTokenExpiry = "RefreshTokenExpiry";

	/** Set Refresh Token Expiry	  */
	public void setRefreshTokenExpiry (Timestamp RefreshTokenExpiry);

	/** Get Refresh Token Expiry	  */
	public Timestamp getRefreshTokenExpiry();

    /** Column name SS_ServerConfig_ID */
    public static final String COLUMNNAME_SS_ServerConfig_ID = "SS_ServerConfig_ID";

	/** Set Server Config	  */
	public void setSS_ServerConfig_ID (int SS_ServerConfig_ID);

	/** Get Server Config	  */
	public int getSS_ServerConfig_ID();

	public I_SS_ServerConfig getSS_ServerConfig() throws RuntimeException;

    /** Column name SS_ServerLogin_ID */
    public static final String COLUMNNAME_SS_ServerLogin_ID = "SS_ServerLogin_ID";

	/** Set Server Login	  */
	public void setSS_ServerLogin_ID (int SS_ServerLogin_ID);

	/** Get Server Login	  */
	public int getSS_ServerLogin_ID();

	public I_SS_ServerLogin getSS_ServerLogin() throws RuntimeException;

    /** Column name SS_SyncConfig_ID */
    public static final String COLUMNNAME_SS_SyncConfig_ID = "SS_SyncConfig_ID";

	/** Set Synchronize Config	  */
	public void setSS_SyncConfig_ID (int SS_SyncConfig_ID);

	/** Get Synchronize Config	  */
	public int getSS_SyncConfig_ID();

    /** Column name SS_SyncConfig_UU */
    public static final String COLUMNNAME_SS_SyncConfig_UU = "SS_SyncConfig_UU";

	/** Set SS_SyncConfig_UU	  */
	public void setSS_SyncConfig_UU (String SS_SyncConfig_UU);

	/** Get SS_SyncConfig_UU	  */
	public String getSS_SyncConfig_UU();

    /** Column name Token */
    public static final String COLUMNNAME_Token = "Token";

	/** Set Token	  */
	public void setToken (String Token);

	/** Get Token	  */
	public String getToken();

    /** Column name TokenExpiry */
    public static final String COLUMNNAME_TokenExpiry = "TokenExpiry";

	/** Set Token Expiry	  */
	public void setTokenExpiry (Timestamp TokenExpiry);

	/** Get Token Expiry	  */
	public Timestamp getTokenExpiry();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UserID */
    public static final String COLUMNNAME_UserID = "UserID";

	/** Set User ID.
	  * User ID or account number
	  */
	public void setUserID (int UserID);

	/** Get User ID.
	  * User ID or account number
	  */
	public int getUserID();
}
