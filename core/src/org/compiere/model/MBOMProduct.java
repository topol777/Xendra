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

import org.compiere.model.persistence.X_M_BOMProduct;
import org.compiere.model.reference.REF_M_BOMProductType;
import org.compiere.model.reference.REF_M_BOMType;
import org.compiere.util.*;

/**
 * 	BOM Product/Component Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MBOMProduct.java 5583 2015-08-05 14:11:58Z xapiens $
 */
public class MBOMProduct extends X_M_BOMProduct
{
	/**
	 * 	Get Products of BOM
	 *	@param bom bom
	 *	@return array of BOM Products
	 */
	public static MBOMProduct[] getOfBOM (MBOM bom) 
	{
		ArrayList<MBOMProduct> list = new ArrayList<MBOMProduct>();
		String sql = "SELECT * FROM M_BOMProduct WHERE M_BOM_ID=? ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, bom.get_TrxName());
			pstmt.setInt (1, bom.getM_BOM_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MBOMProduct (bom.getCtx(), rs, bom.get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		MBOMProduct[] retValue = new MBOMProduct[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfProduct

	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MBOMProduct.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_BOMProduct_ID id
	 *	@param trxName trx
	 */
	public MBOMProduct (Properties ctx, int M_BOMProduct_ID, String trxName)
	{
		super (ctx, M_BOMProduct_ID, trxName);
		if (M_BOMProduct_ID == 0)
		{
		//	setM_BOM_ID (0);
			setBOMProductType (REF_M_BOMProductType.StandardProduct);	// S
			setBOMQty (Env.ONE);
			setIsPhantom (false);
			setLeadTimeOffset (0);
		//	setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_BOMProduct WHERE M_BOM_ID=@M_BOM_ID@
		}
	}	//	MBOMProduct

	/**
	 * 	Parent Constructor
	 *	@param bom product
	 */
	public MBOMProduct (MBOM bom)
	{
		this (bom.getCtx(), 0, bom.get_TrxName());
		m_bom = bom;
	}	//	MBOMProduct

	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MBOMProduct (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MBOMProduct

	/**	BOM Parent				*/
	private MBOM		m_bom = null;
	
	/**
	 * 	Get Parent
	 *	@return parent
	 */
	private MBOM getBOM()
	{
		if (m_bom == null && getM_BOM_ID() != 0)
			m_bom = MBOM.get(getCtx(), getM_BOM_ID());
		return m_bom;
	}	//	getBOM
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true/false
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//	Product
		if (getBOMProductType().equals(REF_M_BOMProductType.OutsideProcessing))
		{
			if (getM_ProductBOM_ID() != 0)
				setM_ProductBOM_ID(0);
		}
		else if (getM_ProductBOM_ID() == 0)
		{
			log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@ @M_ProductBOM_ID@"));
			return false;
		}
		//	Operation
		if (getM_ProductOperation_ID() == 0)
		{
			if (getSeqNo() != 0)
				setSeqNo(0);
		}
		else if (getSeqNo() == 0)
		{
			log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@ @SeqNo@"));
			return false;
		}
		//	Product Attribute Instance
		if (getM_AttributeSetInstance_ID() != 0)
		{
			getBOM();
			if (m_bom != null 
				&& REF_M_BOMType.Make_To_Order.equals(m_bom.getBOMType()))
				;
			else
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), 
					"Reset @M_AttributeSetInstance_ID@: Not Make-to-Order"));
				setM_AttributeSetInstance_ID(0);
				return false;
			}
		}
		//	Alternate
		if ((getBOMProductType().equals(REF_M_BOMProductType.Alternative)
			|| getBOMProductType().equals(REF_M_BOMProductType.AlternativeDefault))
			&& getM_BOMAlternative_ID() == 0)
		{
			log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@ @M_BOMAlternative_ID@"));
			return false;
		}
		//	Operation
		if (getM_ProductOperation_ID() != 0)
		{
			if (getSeqNo() == 0)
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@ @SeqNo@"));
				return false;
			}
		}
		else	//	no op
		{
			if (getSeqNo() != 0)
				setSeqNo(0);
			if (getLeadTimeOffset() != 0)
				setLeadTimeOffset(0);
		}
		
		//	Set Line Number
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM M_BOMProduct WHERE M_BOM_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getM_BOM_ID());
			setLine (ii);
		}

		return true;
	}	//	beforeSave
	
	
}	//	MBOMProduct
