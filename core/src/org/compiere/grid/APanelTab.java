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
package org.compiere.grid;

import org.compiere.apps.*;

/**
 *	Application Panel Tab Interface.
 *  Interface for CPanels displayed as a Tab in APanel
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: APanelTab.java 508 2007-11-24 23:06:53Z el_man $
 */
public interface APanelTab
{
	/**
	 * 	Load Data
	 *  Called when tab is displayed.
	 */
	public void loadData();

	/**
	 * 	Save Data
	 *  Called when tab is swiched to another tab.
	 */
	public void saveData();

	/**
	 * 	Register APanel
	 * 	@param panel panel
	 */
	public void registerAPanel (APanel panel);

	/**
	 * 	Unregister APanel
	 */
	public void unregisterPanel ();

}	//	APanelTab
