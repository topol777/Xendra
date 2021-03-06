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


import java.math.*;
import java.util.logging.*;

import org.compiere.model.MJournalBatch;
import org.compiere.util.DisplayType;

import org.xendra.annotations.XendraProcess;
import org.xendra.annotations.XendraProcessParameter;

/**
 *  Copy GL Batch Journal/Lines
 *
 *	@author Jorg Janke
 *	@version $Id: CopyFromJournal.java 5583 2015-08-05 14:11:58Z xapiens $
 */
@XendraProcess(value="GL_JournalBatch CopyFrom",
name="Copy Details",
description="Copy Journal/Lines from other Journal Batch",
help="",
Identifier="72d8842d-9686-8b3a-151b-908e77e81156",
classname="org.compiere.process.CopyFromJournal",
updated="2015-06-20 10:10:12")	
public class CopyFromJournal extends SvrProcess
{
	@XendraProcessParameter(Name="Journal Batch",
			                ColumnName="GL_JournalBatch_ID",
			                Description="General Ledger Journal Batch",
			                Help="The General Ledger Journal Batch identifies a group of journals to be processed as a group.",
			                AD_Reference_ID=DisplayType.Search,
			                SeqNo=10,
			                ReferenceValueID="",
			                ValRuleID="",
			                FieldLength=0,
			                IsMandatory=true,
			                IsRange=false,
			                DefaultValue="",
			                DefaultValue2="",
			                vFormat="",
			                valueMin="",
			                valueMax="",
			                DisplayLogic="",
			                ReadOnlyLogic="",
			                Identifier="f534b064-92b5-fb84-a6cc-9de414c2e719")	
	private int		m_GL_JournalBatch_ID = 0;

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
			else if (name.equals("GL_JournalBatch_ID"))
				m_GL_JournalBatch_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		int To_GL_JournalBatch_ID = getRecord_ID();
		log.info("doIt - From GL_JournalBatch_ID=" + m_GL_JournalBatch_ID + " to " + To_GL_JournalBatch_ID);
		if (To_GL_JournalBatch_ID == 0)
			throw new IllegalArgumentException("Target GL_JournalBatch_ID == 0");
		if (m_GL_JournalBatch_ID == 0)
			throw new IllegalArgumentException("Source GL_JournalBatch_ID == 0");
		MJournalBatch from = new MJournalBatch (getCtx(), m_GL_JournalBatch_ID, get_TrxName());
		MJournalBatch to = new MJournalBatch (getCtx(), To_GL_JournalBatch_ID, get_TrxName());
		//
		int no = to.copyDetailsFrom (from);
		//
		return "@Copied@=" + no;
	}	//	doIt

}
