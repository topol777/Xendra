/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 * Portions created by Jorg Janke are Copyright (C) 1999-2005 Jorg Janke, parts
 * created by ComPiere are Copyright (C) ComPiere, Inc.;   All Rights Reserved.
 * Portions created by Carlos Ruiz are Copyright (C) 2005 QSS Ltda.
 * Contributor(s): Carlos Ruiz (globalqss)
 *****************************************************************************/
package org.compiere.process;

import java.sql.*;
import java.util.logging.*;

import org.compiere.util.*;
import org.xendra.annotations.*;
/**
 *	Copy and overwrite Accounts to products of this category
 *	
 *  @author Carlos Ruiz (globalqss)
 *  @version $Id: M_Product_Category_Acct_Copy.java 5583 2015-08-05 14:11:58Z xapiens $
 */
@XendraProcess(value="M_Product_Category_Acct_Copy",
name="Copy Accounts",
description="Copy and overwrite Accounts to Products of this category",
help="If you copy and overwrite the current default values, you may have to repeat previous updates (e.g. set the revenue account, ...)",
Identifier="6b9c8610-e042-0dc8-9c0e-8912d5c39d4c",
classname="org.compiere.process.M_Product_Category_Acct_Copy",
updated="2015-06-20 10:10:12")
public class M_Product_Category_Acct_Copy extends SvrProcess
{

	/*
	 * NOTE: The original oracle procedure M_Product_Category_Acct_Copy had a
	 *       Product_Category_ID parameter for Direct Call not implemented in this class
	 */
	
	/** The Product Category			*/
	@XendraProcessParameter(Name="Product Category",
			                ColumnName="M_Product_Category_ID",
			                Description="Category of a Product",
			                Help="Identifies the category which this product belongs to.  Product categories are used for pricing and selection.",
			                AD_Reference_ID=DisplayType.TableDir,
			                SeqNo=10,
			                ReferenceValueID="",
			                ValRuleID="",
			                FieldLength=0,
			                IsMandatory=true,
			                IsRange=false,
			                DefaultValue="@M_Product_Category_ID@",
			                DefaultValue2="",
			                vFormat="",
			                valueMin="",
			                valueMax="",
			                DisplayLogic="",
			                ReadOnlyLogic="",
			                Identifier="1f3fcf20-27e8-ac3b-b9fa-4910b9b83602")	
	private int		m_Product_Category_ID = -1;
	@XendraProcessParameter(Name="Accounting Schema",
                ColumnName="C_AcctSchema_ID",
                Description="Rules for accounting",
                Help="An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar",
                AD_Reference_ID=DisplayType.TableDir,
                SeqNo=20,
                ReferenceValueID="",
                ValRuleID="",
                FieldLength=0,
                IsMandatory=true,
                IsRange=false,
                DefaultValue="@C_AcctSchema_ID@",
                DefaultValue2="",
                vFormat="",
                valueMin="",
                valueMax="",
                DisplayLogic="",
                ReadOnlyLogic="",
                Identifier="d5cb4fd9-19eb-3757-4e39-9c856ee33d21")
	private int m_AcctSchema_ID = 0;
	/** The Record						*/
	private int		p_Record_ID = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;			
			else if (name.equals("M_Product_Category_ID"))
				m_Product_Category_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		String sql;
		String sqlupd;
		String sqlins;
		int cntu = 0;
		int cnti = 0;
		int totu = 0;
		int toti = 0;

		log.info("Copy and overwrite Accounts to products of this category");

		sql = "SELECT P_Revenue_Acct, P_Expense_Acct, P_Asset_Acct, P_CoGs_Acct, "
			+   "P_PurchasePriceVariance_Acct, P_InvoicePriceVariance_Acct, P_TradeDiscountRec_Acct, "
			+   "P_TradeDiscountGrant_Acct, C_AcctSchema_ID, M_Product_Category_ID "
		    + "FROM M_Product_Category_Acct "
		    + "WHERE M_Product_Category_ID = " + m_Product_Category_ID;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				// Update existing Products
				sqlupd = "UPDATE M_Product_Acct "
			           + "SET P_Revenue_Acct= " + rs.getInt("P_REVENUE_ACCT") + ", " 
			           +     "P_Expense_Acct= " + rs.getInt("P_EXPENSE_ACCT") + ", "
			           +     "P_Asset_Acct= " + rs.getInt("P_ASSET_ACCT") + ", "
			           +     "P_CoGs_Acct= " + rs.getInt("P_COGS_ACCT") + ", "
			           +     "P_PurchasePriceVariance_Acct= " + rs.getInt("P_PURCHASEPRICEVARIANCE_ACCT") + ", "
			           +     "P_InvoicePriceVariance_Acct= " + rs.getInt("P_INVOICEPRICEVARIANCE_ACCT") + ", "
			           +     "P_TradeDiscountRec_Acct= " + rs.getInt("P_TRADEDISCOUNTREC_ACCT") + ", "
			           +     "P_TradeDiscountGrant_Acct= " + rs.getInt("P_TRADEDISCOUNTGRANT_ACCT") + ", "
			           +     "Updated=CURRENT_TIMESTAMP, "
			           +     "UpdatedBy=0 "
			           + "WHERE M_Product_Acct.C_AcctSchema_ID= " + rs.getInt("C_ACCTSCHEMA_ID") + " " 
			           +   "AND EXISTS (SELECT * FROM M_Product p "
			           +     "WHERE p.M_Product_ID=M_Product_Acct.M_Product_ID "
			           +       "AND p.M_Product_Category_ID= " + rs.getInt("M_PRODUCT_CATEGORY_ID") + ")";
				cntu = DB.executeUpdate(sqlupd, get_TrxName());
				totu += cntu;
				// Insert new Products
				sqlins = "INSERT INTO M_Product_Acct "
			           +   "(M_Product_ID, C_AcctSchema_ID, "
			           +   "AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
			           +   "P_Revenue_Acct, P_Expense_Acct, P_Asset_Acct, P_CoGs_Acct, "
			           +   "P_PurchasePriceVariance_Acct, P_InvoicePriceVariance_Acct, "
			           +   "P_TradeDiscountRec_Acct, P_TradeDiscountGrant_Acct) "
			           + "SELECT p.M_Product_ID, " + rs.getInt("C_ACCTSCHEMA_ID") + ", "
			           +   "p.AD_Client_ID, p.AD_Org_ID, 'Y', CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 0, "
			           +   rs.getInt("P_REVENUE_ACCT") + ", " 
					   +   rs.getInt("P_EXPENSE_ACCT") + ", " 
					   +   rs.getInt("P_ASSET_ACCT") + ", " 
					   +   rs.getInt("P_COGS_ACCT") + ", " 
			           +   rs.getInt("P_PURCHASEPRICEVARIANCE_ACCT") + ", " 
					   +   rs.getInt("P_INVOICEPRICEVARIANCE_ACCT") + ", "
			           +   rs.getInt("P_TRADEDISCOUNTREC_ACCT") + ", " 
					   +   rs.getInt("P_TRADEDISCOUNTGRANT_ACCT") + " "
			           + "FROM	M_Product p "
			           + "WHERE	p.M_Product_Category_ID=" + rs.getInt("M_PRODUCT_CATEGORY_ID") + " "
			           +   "AND NOT EXISTS (SELECT * FROM M_Product_Acct pa "
			           +     "WHERE pa.M_Product_ID=p.M_Product_ID "
			           +       "AND pa.C_AcctSchema_ID=" + rs.getInt("C_ACCTSCHEMA_ID") + ")";
				cnti = DB.executeUpdate(sqlins, get_TrxName());
				toti += cnti;
				log.info("Product = " + cntu + " / " + cnti);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
			log.fine("Committing ...");
			DB.commit(true, get_TrxName());
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "adding missing elements", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}

		return "@Created@=" + cnti + ", @Updated@=" + cntu;		
	}	//	doIt
	
}	//	M_Product_Category_Acct_Copy
