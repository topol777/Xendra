/******************************************************************************
 * Product: Xendra ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.model.persistence.X_AD_Alert;
import org.compiere.model.persistence.X_AD_AlertRecipient;
import org.compiere.model.persistence.X_AD_AlertRule;
import org.compiere.util.*;

/**
 *	Alert Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MAlert.java 5822 2016-06-15 22:56:06Z xapiens $
 */
public class MAlert extends X_AD_Alert
{
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Alert_ID id
	 *	@param trxName transaction
	 */
	public MAlert (Properties ctx, int AD_Alert_ID, String trxName)
	{
		super (ctx, AD_Alert_ID, trxName);
		if (AD_Alert_ID == 0)
		{
		//	setAD_AlertProcessor_ID (0);
		//	setName (null);
		//	setAlertMessage (null);
		//	setAlertSubject (null);
			setEnforceClientSecurity (true);	// Y
			setEnforceRoleSecurity (true);	// Y
			setIsValid (true);	// Y
		}	
	}	//	MAlert

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAlert (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAlert

	
	/**	The Rules						*/
	private MAlertRule[]		m_rules	= null;
	/**	The Recipients					*/
	private MAlertRecipient[]	m_recipients = null;

	/**
	 * 	Get Rules
	 *	@param reload reload data
	 *	@return array of rules
	 */
	public MAlertRule[] getRules (boolean reload)
	{
		if (m_rules != null && !reload)
			return m_rules;
		String where  = "AD_Alert_ID=?";
		List<MAlertRule> list = new Query(Env.getCtx(),X_AD_AlertRule.Table_Name, where, null)
			.setParameters(getAD_Alert_ID()).list();
		m_rules = new MAlertRule[list.size ()];
		list.toArray (m_rules);
		return m_rules;
	}	//	getRules
	
	/**
	 * 	Get Recipients
	 *	@param reload reload data
	 *	@return array of recipients
	 */
	public MAlertRecipient[] getRecipients (boolean reload)
	{
		if (m_recipients != null && !reload)
			return m_recipients;
		String where = "AD_Alert_ID=?";
		List<MAlertRecipient> list = new Query(Env.getCtx(), X_AD_AlertRecipient.Table_Name, where, null)
			.setParameters(getAD_Alert_ID()).list();
		//
		m_recipients = new MAlertRecipient[list.size ()];
		list.toArray (m_recipients);
		return m_recipients;
	}	//	getRecipients

	/**
	 * 	Get First Role if exist
	 *	@return AD_Role_ID or -1
	 */
	public int getFirstAD_Role_ID()
	{
		getRecipients(false);
		for (int i = 0; i < m_recipients.length; i++)
		{
			if (m_recipients[i].getAD_Role_ID() != -1)
				return m_recipients[i].getAD_Role_ID();
		}
		return -1;
	}	//	getForstAD_Role_ID
	
	/**
	 * 	Get First User Role if exist
	 *	@return AD_Role_ID or -1
	 */
	public int getFirstUserAD_Role_ID()
	{
		getRecipients(false);
		int AD_User_ID = getFirstAD_User_ID();
		if (AD_User_ID != -1)
		{
			MUserRoles[] urs = MUserRoles.getOfUser(getCtx(), AD_User_ID);
			for (int i = 0; i < urs.length; i++)
			{
				if (urs[i].isActive())
					return urs[i].getAD_Role_ID();
			}
		}
		return -1;
	}	//	getFirstUserAD_Role_ID

	/**
	 * 	Get First User if exist
	 *	@return AD_User_ID or -1
	 */
	public int getFirstAD_User_ID()
	{
		getRecipients(false);
		for (int i = 0; i < m_recipients.length; i++)
		{
			if (m_recipients[i].getAD_User_ID() != -1)
				return m_recipients[i].getAD_User_ID();
		}
		return -1;
	}	//	getFirstAD_User_ID
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MAlert[");
		sb.append(get_ID())
			.append("-").append(getName())
			.append(",Valid=").append(isValid());
		if (m_rules != null)
			sb.append(",Rules=").append(m_rules.length);
		if (m_recipients != null)
			sb.append(",Recipients=").append(m_recipients.length);
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MAlert
