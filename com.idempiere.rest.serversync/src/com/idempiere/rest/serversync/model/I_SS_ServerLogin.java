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

/** Generated Interface for SS_ServerLogin
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_SS_ServerLogin 
{

    /** TableName=SS_ServerLogin */
    public static final String Table_Name = "SS_ServerLogin";

    /** AD_Table_ID=1000032 */
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

    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/** Set Language.
	  * Language for this entity
	  */
	public void setAD_Language (String AD_Language);

	/** Get Language.
	  * Language for this entity
	  */
	public String getAD_Language();

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

    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/** Set Password.
	  * Password of any length (case sensitive)
	  */
	public void setPassword (String Password);

	/** Get Password.
	  * Password of any length (case sensitive)
	  */
	public String getPassword();

    /** Column name SS_Client_ID */
    public static final String COLUMNNAME_SS_Client_ID = "SS_Client_ID";

	/** Set Login Client ID	  */
	public void setSS_Client_ID (int SS_Client_ID);

	/** Get Login Client ID	  */
	public int getSS_Client_ID();

    /** Column name SS_Org_ID */
    public static final String COLUMNNAME_SS_Org_ID = "SS_Org_ID";

	/** Set Login Org ID	  */
	public void setSS_Org_ID (int SS_Org_ID);

	/** Get Login Org ID	  */
	public int getSS_Org_ID();

    /** Column name SS_Role_ID */
    public static final String COLUMNNAME_SS_Role_ID = "SS_Role_ID";

	/** Set Login Role ID	  */
	public void setSS_Role_ID (int SS_Role_ID);

	/** Get Login Role ID	  */
	public int getSS_Role_ID();

    /** Column name SS_ServerLogin_ID */
    public static final String COLUMNNAME_SS_ServerLogin_ID = "SS_ServerLogin_ID";

	/** Set Server Login	  */
	public void setSS_ServerLogin_ID (int SS_ServerLogin_ID);

	/** Get Server Login	  */
	public int getSS_ServerLogin_ID();

    /** Column name SS_ServerLogin_UU */
    public static final String COLUMNNAME_SS_ServerLogin_UU = "SS_ServerLogin_UU";

	/** Set SS_ServerLogin_UU	  */
	public void setSS_ServerLogin_UU (String SS_ServerLogin_UU);

	/** Get SS_ServerLogin_UU	  */
	public String getSS_ServerLogin_UU();

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

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set User Name	  */
	public void setUserName (String UserName);

	/** Get User Name	  */
	public String getUserName();
}
