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

import org.compiere.model.persistence.X_GL_Category;
import org.compiere.model.reference.REF_GLCategoryType;
import org.compiere.util.*;

/**
 *	GL Category
 *	
 *  @author Jorg Janke
 *  @version $Id: MGLCategory.java 5583 2015-08-05 14:11:58Z xapiens $
 */
public class MGLCategory extends X_GL_Category
{
	/**
	 * 	Get MGLCategory from Cache
	 *	@param ctx context
	 *	@param GL_Category_ID id
	 *	@return MGLCategory
	 */
	public static MGLCategory get (Properties ctx, int GL_Category_ID)
	{
		Integer key = new Integer (GL_Category_ID);
		MGLCategory retValue = (MGLCategory)s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MGLCategory (ctx, GL_Category_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get Default Category
	 *	@param ctx context
	 *	@param CategoryType optional CategoryType (ignored, if not exists)
	 *	@return GL Category or null
	 */
	public static MGLCategory getDefault (Properties ctx, String CategoryType)
	{
		MGLCategory retValue = null;
		String sql = "SELECT * FROM GL_Category "
			+ "WHERE AD_Client_ID=? AND IsDefault='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, Env.getAD_Client_ID(ctx));
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MGLCategory temp = new MGLCategory (ctx, rs, null);
				if (CategoryType != null && CategoryType.equals(temp.getCategoryType()))
				{
					retValue = temp;
					break;
				}
				if (retValue == null)
					retValue = temp;
			}
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
		return retValue;
	}	//	getDefault

	/**
	 * 	Get Default System Category
	 *	@param ctx context
	 *	@return GL Category
	 */
	public static MGLCategory getDefaultSystem (Properties ctx)
	{
		MGLCategory retValue = getDefault(ctx, REF_GLCategoryType.SystemGenerated);
		if (retValue == null 
			|| !retValue.getCategoryType().equals(REF_GLCategoryType.SystemGenerated))
		{
			retValue = new MGLCategory(ctx, 0, null);
			retValue.setName("Default System");
			retValue.setCategoryType(REF_GLCategoryType.SystemGenerated);
			retValue.setIsDefault(true);
			if (!retValue.save())
				throw new IllegalStateException("Could not save default system GL Category");
		}
		return retValue;
	}	//	getDefaultSystem

	
	/**	Logger						*/
	private static CLogger s_log = CLogger.getCLogger (MGLCategory.class);
	/**	Cache						*/
	private static CCache<Integer, MGLCategory> s_cache 
		= new CCache<Integer, MGLCategory> ("GL_Category", 5);
	

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param GL_Category_ID id
	 *	@param trxName transaction
	 */
	public MGLCategory (Properties ctx, int GL_Category_ID, String trxName)
	{
		super (ctx, GL_Category_ID, trxName);
		if (GL_Category_ID == 0)
		{
		//	setName (null);
			setCategoryType (REF_GLCategoryType.Manual);
			setIsDefault (false);
		}
	}	//	MGLCategory

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MGLCategory (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MGLCategory
	
}	//	MGLCategory
