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

import java.io.*;
import java.util.*;

/**
 * 	Value Object
 *	
 *  @author Jorg Janke
 *  @version $Id: VO.java 508 2007-11-24 23:06:53Z el_man $
 */
public class VO
	implements Map, Serializable
{
	/**
	 * 	Constructor
	 */
	public VO ()
	{
		super ();
	}	//	VO
	
//	private static final long serialVersionUID = 8683452581122892189L;

	/**	Keys		*/
	private ArrayList<String> m_keys = new ArrayList<String>();
	/**	Values		*/
	private ArrayList<String> m_values = new ArrayList<String>();
	
	/**
	 * 	Get Size
	 *	@return size
	 */
	public int size ()
	{
		return m_keys.size();
	}	//	size

	/**
	 * 	Is Empty
	 *	@return true if empty
	 */
	public boolean isEmpty ()
	{
		return m_keys.isEmpty();
	}	//	isEmpty

	/**
	 * 	Contains Key
	 *	@param key key
	 *	@return true if contains
	 */
	public boolean containsKey (Object key)
	{
		if (key == null)
			return false;
		return m_keys.contains(key);
	}	//	containsKey

	/**
	 * 	Contains Value
	 *	@param value value
	 *	@return true if contains value
	 */
	public boolean containsValue (Object value)
	{
		if (value == null)
			return false;
		return m_values.contains(value);
	}	//	containsValue

	/**
	 * 	Get Value with Key
	 *	@param key key
	 *	@return value or null
	 */
	public synchronized Object get (Object key)
	{
		if (key == null)
			return null;
		int index = m_keys.indexOf(key);
		if (index != -1)
			return m_values.get(index);
		return null;
	}	//	get

	/**
	 * 	Put key/value
	 *	@param key key
	 *	@param value value
	 *	@return previous value or null
	 */
	public synchronized Object put (Object key, Object value)
	{
		if (key == null)
			return null;
		if (value == null)
			return remove(key);
		//
		String stringKey = key.toString();
		String stringValue = value.toString(); 
		int index = m_keys.indexOf(key);
		if (index != -1)
			return m_values.set (index, stringValue);
		m_values.add(stringKey);
		m_values.add(stringValue);
		return null;
	}	//	put

	/**
	 * 	Remove
	 *	@param key key
	 *	@return previous value or null
	 */
	public synchronized Object remove (Object key)
	{
		if (key == null)
			return null;
		int index = m_keys.indexOf(key);
		Object old = null;
		if (index != -1)
		{
			old = m_values.get(index);
			m_keys.remove(index);
			m_values.remove(index);
		}
		return old;
	}	//	remove

	/**
	 * 	Put All
	 *	@param t map
	 */
	public void putAll (Map t)
	{
		Iterator it = t.keySet().iterator();
		while (it.hasNext())
		{
			Object key = it.next();
			Object value = t.get(key);
			put(key, value);
		}
	}	//	putAll

	/**
	 * 	Clear keys/values
	 */
	public void clear ()
	{
		m_keys.clear();
		m_values.clear();
	}	//	clear

	/**
	 * 	Get Key Set
	 *	@return key set
	 */
	public Set<String> keySet ()
	{
		HashSet<String> set = new HashSet<String>(m_keys);
		return set;
	}	//	keySet

	/**
	 * 	Get Values
	 *	@return values as collection
	 */
	public Collection values ()
	{
		return m_values;
	}	//	values

	/**
	 * 	Get Values Set
	 *	@return values set
	 */
	public Set<String> entrySet ()
	{
		HashSet<String> set = new HashSet<String>(m_values);
		return set;
	}	//	entrySet
	
	
	
	
}	//	VO
