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
package org.compiere.process;

import java.sql.*;
import java.math.*;
import java.util.logging.*;

import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.util.*;

import org.xendra.annotations.XendraProcess;
/**
 *	Synchronize Column with Database
 *	
 *  @author Victor P???rez, Jorg Janke
 *  @version $Id: ColumnSync.java 5583 2015-08-05 14:11:58Z xapiens $
 */
@XendraProcess(value="AD_Column Sync",
name="Synchronize Column",
description="Change database table definition from application dictionary",
help="When selected, the database column definition is updated based on your entries in the Column definition of the Application Dictionary. Note that not all changes are supported by the database and may result in an error.",
Identifier="00cedefe-c3aa-d75e-3bd8-dac3ace4e8fa",
classname="org.compiere.process.ColumnSync",
updated="2015-06-20 10:10:12")
public class ColumnSync extends SvrProcess
{
	/** The Column				*/
	private int			p_AD_Column_ID = 0;

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
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_AD_Column_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		log.info("C_Column_ID=" + p_AD_Column_ID);
		if (p_AD_Column_ID == 0)
			throw new XendraUserError("@No@ @AD_Column_ID@");
		MColumn column = new MColumn (getCtx(), p_AD_Column_ID, get_TrxName());
		if (column.get_ID() == 0)
			throw new XendraUserError("@NotFound@ @AD_Column_ID@ " + p_AD_Column_ID);
		
		MTable table = MTable.get(getCtx(), column.getAD_Table_ID());
		if (table.get_ID() == 0)
			throw new XendraUserError("@NotFound@ @AD_Table_ID@ " + column.getAD_Table_ID());
		
		//	Find Column in Database
		DatabaseMetaData md = DB.getConnectionRO().getMetaData();
		String catalog = DB.getDatabase().getCatalog();
		String schema = DB.getDatabase().getSchema();
		String tableName = table.getTableName();
		tableName = tableName.toLowerCase();
		int noColumns = 0;
		String sql = null;
		//
		ResultSet rs = md.getColumns(catalog, schema, tableName, null);
		while (rs.next())
		{
			noColumns++;
			String columnName = rs.getString ("COLUMN_NAME");
			if (!columnName.equalsIgnoreCase(column.getColumnName()))
				continue;
			
			//	update existing column
			boolean notNull = DatabaseMetaData.columnNoNulls == rs.getInt("NULLABLE");
			sql = column.getSQLModify(table, column.isMandatory() != notNull);
			break;
		}
		rs.close();
		rs = null;
		
		//	No Table
		if (noColumns == 0)
			sql = table.getSQLCreate ();
		//	No existing column
		else if (sql == null)
			sql = column.getSQLAdd(table);
		
		int no = 0;
		if (sql.indexOf(DB.SQLSTATEMENT_SEPARATOR) == -1)
		{
			no = DB.executeUpdate(sql, false, get_TrxName());
			addLog (0, null, new BigDecimal(no), sql);
		}
		else
		{
			String statements[] = sql.split(DB.SQLSTATEMENT_SEPARATOR);
			for (int i = 0; i < statements.length; i++)
			{
				int count = DB.executeUpdate(statements[i], false, get_TrxName());
				addLog (0, null, new BigDecimal(count), statements[i]);
				no += count;
			}
		}

		if (no == -1)
		{
			String msg = "@Error@ ";
			ValueNamePair pp = CLogger.retrieveError();
			if (pp != null)
				msg = pp.getName() + " - ";
			msg += sql;
			throw new XendraUserError (msg);
		}
		return sql;		
	}	//	doIt

}	//	ColumnSync
