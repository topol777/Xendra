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
package org.compiere.grid.ed;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.border.*;

import org.compiere.model.GridField;
import org.compiere.swing.*;

/**
 *	Editor Interface for single Row Editors (also used as TableCellEditors).
 *  <p>
 *  Editors fire VetoableChange to inform about new entered values
 *  and listen to propertyChange (MField.PROPERTY) to receive new values
 *  or to (MField.ATTRIBUTE) in changes of Background or Editability
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VEditor.java 508 2007-11-24 23:06:53Z el_man $
 */
public interface VEditor extends CEditor, PropertyChangeListener
{
	/**
	 *	Get Column Name
	 * 	@return column name
	 */
	public String getName();

	/**
	 *	Set Column Name
	 * 	@patam columnName name
	 */
	public void setName(String columnName);

	/**
	 *	Change Listener Interface
	 *  @param listener
	 */
	public void addVetoableChangeListener(VetoableChangeListener listener);
	/**
	 *	Change Listener Interface
	 *  @param listener
	 */
	public void removeVetoableChangeListener(VetoableChangeListener listener);
	/**
	 *  Action Listener
	 *  @param listener
	 */
	public void addActionListener(ActionListener listener);
//	public void removeActionListener(ActionListener listener);

	/**
	 *  Used to set border for table editors
	 *  @param border
	 */
	public void setBorder(Border border);

	/**
	 *  Set Font
	 *  @param font
	 */
	public void setFont(Font font);

	/**
	 *	Set Foreground
	 *  @param color
	 */
	public void setForeground(Color color);

	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField
	 */
	public void setField (GridField mField);

	/**
	 *  Dispose
	 */
	public void dispose();

}	//	VEditor
