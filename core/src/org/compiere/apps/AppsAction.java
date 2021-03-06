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
package org.compiere.apps;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import org.compiere.swing.*;
import org.compiere.util.*;

/**
 *  Application Action.
 *		Creates Action with MenuItem and Button
 *		The ActionCommand is translated for display
 *		If translated text contains &, the next character is the Mnemonic
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: AppsAction.java 5430 2014-08-20 07:01:26Z xapiens $
 */
public final class AppsAction extends AbstractAction
{
	/**
	 *  Application Action
	 *
	 *  @param   action base action command - used as AD_Message for Text and Icon name
	 *  @param   accelerator optional keystroke for accelerator
	 *  @param   toggle is toggle action (maintains state)
	 */
	public AppsAction (String action, KeyStroke accelerator, boolean toggle)
	{
		this (null, action, accelerator, null, toggle, null);
	}
	
	public AppsAction (String action, KeyStroke accelerator, String text)
	{
		this (null, action, accelerator, text, false, null);
	}
	/**
	 *  Application Action
	 *
	 *  @param   action base action command - used as AD_Message for Text and Icon name
	 *  @param   accelerator optional keystroke for accelerator
	 *  @param   text text, if null defered from action
	 */
	public AppsAction (String action, KeyStroke accelerator, String text, Dimension dim)
	{
		this (null, action, accelerator, text, false, dim);
	}	//	AppsAction
	
	public AppsAction(BufferedImage img, String action, KeyStroke accelerator,
			String string, Dimension dimension) {
		this (img, action, accelerator, string, false, dimension);
	}

	/**
	 *  Application Action
	 *
	 *  @param   action base action command - used as AD_Message for Text and Icon name
	 *  @param   accelerator optional keystroke for accelerator
	 *  @param   toolTipText text, if null defered from action
	 *  @param   toggle is toggle action (maintains state)
	 */
	public AppsAction (BufferedImage img, String action, KeyStroke accelerator, String toolTipText, boolean toggle, Dimension dim)
	{
		super();
		m_action = action;
		m_accelerator = accelerator;
		m_toggle = toggle;

		//	Data
		if (toolTipText == null)
			toolTipText = Msg.getMsg(Env.getCtx(), action);
		int pos = toolTipText.indexOf('&');
		if (pos != -1  && toolTipText.length() > pos)	//	We have a nemonic - creates ALT-_
		{
			Character ch = new Character(toolTipText.toUpperCase().charAt(pos+1));
			if (ch != ' ')
			{
				toolTipText = toolTipText.substring(0, pos) + toolTipText.substring(pos+1);
				putValue(Action.MNEMONIC_KEY, new Integer(ch.hashCode()));
			}
		}
		//		
		Icon small = getIcon(action, true);
		Icon large = getIcon(action, false);
		Icon largePressed = null;

		//  ToggleIcons have the pressed name with X
		if (m_toggle)
		{
			m_smallPressed = getIcon(action+"X", true);
			if (m_smallPressed == null)
				m_smallPressed = small;
			largePressed = getIcon(action+"X", false);
			if (largePressed == null)
				largePressed = large;
		}

		//	Attributes
		putValue(Action.NAME, toolTipText);					//	Display
		putValue(Action.SMALL_ICON, small);                 //  Icon
		putValue(Action.SHORT_DESCRIPTION, toolTipText);	//	Tooltip
		putValue(Action.ACTION_COMMAND_KEY, m_action);      //  ActionCammand
		putValue(Action.ACCELERATOR_KEY, accelerator);      //  KeyStroke
	//	putValue(Action.MNEMONIC_KEY, new Integer(0));      //  Mnemonic
	//	putValue(Action.DEFAULT, text);						//	Not Used

		//	Create Button
		if (toggle)
		{
			m_button = new CToggleButton(this);
			m_button.setSelectedIcon(largePressed);
		}
		else
			m_button = new CButton(this);
		m_button.setName(action);
		//	Correcting Action items
		if (img != null)
		{
			m_button.setIcon(new ImageIcon(img));
		}
		else if (large != null)
		{
			m_button.setIcon(large);
			m_button.setText(null);
		}
		m_button.setActionCommand(m_action);
		m_button.setMargin(BUTTON_INSETS);
		if (dim == null)
			m_button.setSize(BUTTON_SIZE);
		else
			m_button.setPreferredSize(dim);
		//
		if (accelerator != null)
		{
			m_button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(accelerator, action);
			m_button.getActionMap().put(action, this);
		}
	}	//	Action

	/** Button Size     			*/
	public static final Dimension	BUTTON_SIZE = new Dimension(28,28);
	/** Button Insets   			*/
	public static final Insets		BUTTON_INSETS = new Insets(0, 0, 0, 0);
	/** CButton or CToggelButton	*/
	private AbstractButton 	m_button;
	/**	Menu						*/
	private JMenuItem		m_menu;

	private String			m_action = null;
	private KeyStroke		m_accelerator = null;
	private Icon 			m_smallPressed = null;
	private ActionListener	m_delegate = null;
	private boolean 		m_toggle = false;
	private boolean			m_pressed = false;

	/**
	 *	Get Icon with name action
	 *  @param name name
	 *  @param small small
	 *  @return Icon
	 */
	private ImageIcon getIcon(String name, boolean small)
	{
		String fullName = name + (small ? "16" : "24");
		return Env.getImageIcon2(fullName);
	}	//	getIcon

	/**
	 *	Get Name/ActionCommand
	 *  @return ActionName
	 */
	public String getName()
	{
		return m_action;
	}	//	getName

	/**
	 *	Return Button
	 *  @return Button
	 */
	public AbstractButton getButton()
	{
		return m_button;
	}	//	getButton
	
	public void setButton(AbstractButton button)
	{
		m_button = button;
	}

	/**
	 *	Return MenuItem
	 *  @return MenuItem
	 */
	public JMenuItem getMenuItem()
	{
		if (m_menu == null)
		{
			if (m_toggle)
			{
				m_menu = new CCheckBoxMenuItem(this);
				m_menu.setSelectedIcon(m_smallPressed);
			}
			else
				m_menu = new CMenuItem(this);
			m_menu.setAccelerator(m_accelerator);
			m_menu.setActionCommand(m_action);
		}
		return m_menu;
	}	//	getMenuItem

	/**
	 *	Set Delegate to receive the actionPerformed calls
	 *  @param al listener
	 */
	public void setDelegate(ActionListener al)
	{
		m_delegate = al;
	}	//	setDelegate

	/**
	 *	Toggle
	 *  @param pressed pressed
	 */
	public void setPressed (boolean pressed)
	{
		if (!m_toggle)
			return;
		m_pressed = pressed;

		//	Set Button
		if (m_button != null)
			m_button.setSelected(pressed);
		
		//	Set Menu
		if (m_menu != null)
			m_menu.setSelected(pressed);
	}	//	setPressed

	/**
	 *	IsPressed
	 *  @return true if pressed
	 */
	public boolean isPressed()
	{
		return m_pressed;
	}	//	isPressed

	/**
	 * 	Get Mnemonic character
	 *	@return character
	 */
	public Character getMnemonic()
	{
		Object oo = getValue(Action.MNEMONIC_KEY);
		if (oo instanceof Integer)
			return (char)((Integer)oo).intValue();
		return null;
	}	//	getMnemonic
	
	/**
	 *	ActionListener
	 *  @param e Event
	 */
	public void actionPerformed(ActionEvent e)
	{
	//	log.info( "AppsAction.actionPerformed", e.getActionCommand());
		//	Toggle Items
		if (m_toggle)
			setPressed(!m_pressed);
		//	Inform
		if (m_delegate != null)
			m_delegate.actionPerformed(e);
	}	//	actionPerformed

	/**
	 *	Dispose
	 */
	public void dispose()
	{
		m_button = null;
		m_menu = null;
	}	//	dispose

	/**
	 *  String Info
	 *  @return String Representation
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("AppsAction[");
		sb.append(m_action);
		Object oo = getValue(Action.ACCELERATOR_KEY);
		if (oo != null)
			sb.append(",Accelerator=").append(oo);
		oo = getMnemonic();
		if (oo != null)
			sb.append(",MnemonicKey=").append(oo);
		sb.append("]");
		return sb.toString();
	}   //  toString

}	//	AppsAction
