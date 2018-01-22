package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.compiere.model.persistence.X_C_Invoice;
import org.compiere.model.persistence.X_C_PrinterDocumentFormat;
import org.compiere.print.PrintData;
import org.compiere.print.PrintDataElement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.xendra.Constants;
import org.xendra.printdocument.ParseFormat;
import org.xendra.printdocument.PrintWorker;
import org.xendra.printdocument.PrintWorkerPO;

public class MPrinterDocumentFormat extends X_C_PrinterDocumentFormat {

	private Boolean compiled = false;
	private String m_processMsg;

	public MPrinterDocumentFormat(Properties ctx, int C_PrinterDocumentFormat_ID, String trxName) {
		super(ctx, C_PrinterDocumentFormat_ID, trxName);		
	}

	public MPrinterDocumentFormat(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);		
	}
	public String toString()
	{
	StringBuffer sb = new StringBuffer (this.getName());
	return sb.toString();
	}
	public String compile()
	{
		String error = "";
		HashMap properties = getProperties();
		String scompiled = (String) properties.get(ParseFormat.COMPILED);
		if (scompiled == null)
			scompiled = "N";
		compiled  = scompiled.equals("Y");
		if (!compiled)
		{
			error = ParseFormat.getInstance().parse(this);
			if (error.length() == 0)
			{
				properties = getProperties();
				properties.put(ParseFormat.COMPILED, "Y");
				setProperties(properties);
				if (save())
				{
					compiled = true;
				}
			}
		}
		return error;
	}


	public String get_ID(String table) {
		if (table == null)
			table = "";
		String id = String.format("%s_ID", table);
		return id;
	}

	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
	public PrintWorker getPrintWorker(MQuery query) {
		m_processMsg = compile(); 
		if (m_processMsg.length() > 0)
		{
			return null;
		}
		PrintWorker pw = new PrintWorker();			
		m_processMsg = pw.setPrinterDocumentFormat_ID(getC_PrinterDocumentFormat_ID());
		pw.setJobName("JobName");
		setQuerys(pw);
		pw = setData(pw, query);
		pw = setFunction(pw);
		return pw;
	}

	public PrintWorker setFunction(PrintWorker pw)
	{
		String functions = (String) getProperties().get(ParseFormat.FUNCTIONS);
		if (functions == null)
		{
			return pw;
		}
		functions = functions.replace("{", "");
		functions = functions.replace("}", "");		
		StringTokenizer st = new StringTokenizer( functions , "," );		
		while ( st.hasMoreTokens()) {			
			String token = (String) st.nextElement();
			StringTokenizer st2 = new StringTokenizer( functions , "=" );
			String funcvar = "";
			String funcval = "";
			while (st2.hasMoreTokens())
			{
				if (funcvar.length() == 0)
					funcvar = st2.nextToken();
				else
					funcval = st2.nextToken();
			}
			if (funcvar.length()>0 && funcval.length() > 0)
			{
				String retval = getFunctionValue(pw, funcval);
				pw.AddProperty(funcvar, retval);
			}			
		}			
		return pw;
	}
	
	public String getFunctionValue(PrintWorker pw, String funcval) 
	{
		String defStr = "";
		String func = parseContext(pw, funcval);	//	replace variables
		if (func.equals(""))
		{
			log.log(Level.WARNING, "(" + funcval + ") - function parse failed");
		}
		else
		{
			try
			{
				StringTokenizer st = new StringTokenizer( func , "()" );	
				String function = "";
				String value = "";
				while ( st.hasMoreTokens()) {			
					String token = (String) st.nextElement();
					if (function.length() == 0)
						function = token;
					else
						value = token;					
				}				
				if (function.length() > 0 && value.length() > 0)
				{
					if (function.equals("getAmtInWords"))
						defStr = Msg.getAmtInWords(Env.getLanguage(Env.getCtx()), value);
				}
			}			
			catch (Exception e)
			{
				log.log(Level.WARNING, "(" + funcval + ") " + func, e);
			}
		}
		if (defStr != null && defStr.length() > 0)
		{
			log.fine("[SQL] " + funcval + "=" + defStr);				
		}
		return defStr;
	}
	
	public String parseContext (PrintWorker pw, String value)
	{
		if (value == null || value.length() == 0)
			return "";

		String token;
		String inStr = new String(value);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				return "";						//	no second tag
			}
			token = inStr.substring(0, j);
			String ctxInfo = null;
			ctxInfo = (String) pw.getProperty().get(token);
			if (ctxInfo.length() == 0)
				return "";						//	token not found
			else
			{				
				outStr.append(ctxInfo);				// replace context with Context
			}

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		outStr.append(inStr);						// add the rest of the string
		return outStr.toString();
	}	//	parseContext
	
	public void setQuerys(PrintWorker pw)
	{
	}
	
	private HashMap getQuerys()
	{
		HashMap querymap = new HashMap();
		String querys = (String) getProperties().get(ParseFormat.QUERYS);
		if (querys == null)
			return querymap;
		querys = querys.replace("{", "");
		querys = querys.replace("}", "");
		StringTokenizer st = new StringTokenizer( querys, ",");
		while (st.hasMoreTokens()) {
			String token = (String) st.nextElement();
			StringTokenizer st2 = new StringTokenizer( token, "=");
			String qvar = "";
			String qval = "";
			if (st2.hasMoreTokens())
			{
				qvar = st2.nextToken();				
				qval = token.substring(qvar.length()+1);
				qvar = qvar.trim();
				if (qvar.length()>0 && qval.length() > 0)
				if (!querymap.containsKey(qvar))				
					querymap.put(qvar, qval);					
			}
		}		
		return querymap;
	}	
	
	public HashMap getIndexQuery(HashMap querys, List<String> fields, String relationid)
	{
		HashMap mapindex = new HashMap();
		if (querys == null)
			return null;
		if (!fields.contains(relationid))
			fields.add(relationid);
		Iterator it = querys.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry map = (Entry) it.next();
			String var = (String) map.getKey();
			String sql = (String) map.getValue();
			List<String> tmpindexes = parseIndexContext(sql);
			for (String string:tmpindexes)
			{
				for (String field:fields)
				{
					if (field.compareToIgnoreCase(string) == 0)
					{
						List<Vector> vectors = null;
						if (mapindex.containsKey(field))
							vectors = (List<Vector>) mapindex.get(field);
						else
							vectors = new ArrayList<Vector>();
						Vector vector = new Vector();							
						sql = sql.replaceAll(string, field);
						vector.add(var);
						vector.add(sql);
						vectors.add(vector);
						mapindex.put(field, vectors);						
					}
				}
			}
		}
		return mapindex;
	}
	
	public List<String> parseIndexContext(String sql) 
	{
		List<String> index = new ArrayList<String>();
		int pos = sql.indexOf("@");
		int end = sql.indexOf("@", pos+1);
		while (pos >= 0 &&  end >= 0)
		{
			String token = sql.substring( pos + 1, end);
			index.add(token);
			if (end + 1 < sql.length())
			{
				sql = sql.substring(end + 1);
				pos = sql.indexOf("@");
				end = sql.indexOf("@", pos+1);
			}
			else
			{
				pos = -1;
			}
		}		
		return index;
	}
	
	public PrintWorker setData(PrintWorker pw, MQuery query)
	{
		HashMap querys = getQuerys();				
		boolean isheader = true;
		String maintable = getHeaderTable();	
		while (maintable.length() > 0)
		{
			List<String> fields = getFields(maintable);			
			List<String> relations = getTables(maintable);  
			PrintWorkerPO pow = new PrintWorkerPO(Env.getLanguage(Env.getCtx()),pw, null);			
			pow.addrelations(getTables(maintable));
			if (isheader)
			{
				///pow.fill(Env.getCtx(), query, maintable, fields);
				PrintData pd = pow.getPrintDataInfo (Env.getCtx(), query, maintable, fields);
				pow.loadPrintData(pd);				
				pd.setRowIndex(0);
				pow.load(pd, true, fields);
				HashMap indexquery = getIndexQuery(querys, fields, pow.getName_ID());
				if (indexquery.size() > 0)					
					fillqueryindex(pw, pd, indexquery,isheader);					
				for (String relation:relations)
				{			
					List<String> relfields = getFields(relation);							
					KeyNamePair keyrelation = pow.getrelation(pd, relation);
					MQuery m_query = new MQuery();
					m_query.addRestriction(keyrelation.getName(), MQuery.EQUAL, keyrelation.getKey());
					PrintWorkerPO powchild = new PrintWorkerPO(Env.getLanguage(Env.getCtx()),pw, null);
					if (isheader)
						powchild.fill(Env.getCtx(), m_query, relation, relfields);
					else
						powchild.fillline(Env.getCtx(), m_query, relation, relfields);
				}				
			}
			else
			{				
				PrintData pd = pow.getPrintDataInfo (Env.getCtx(), query, maintable, fields);
				pow.loadPrintData(pd);				
				boolean goahead = pd.setRowIndex(0);
				while (goahead)
				{
					pow.load(pd, false, fields);				
					HashMap indexquery = getIndexQuery(querys, fields, pow.getName_ID());
					if (indexquery.size() > 0)					
						fillqueryindex(pw, pd, indexquery,isheader);					
					//pow.fill(pd, fields, isheader);
					for (String relation:relations)
					{			
						List<String> relfields = getFields(relation);						
						KeyNamePair keyrelation = pow.getrelation(pd, relation);
						MQuery m_query = new MQuery();
						m_query.addRestriction(keyrelation.getName(), MQuery.EQUAL, keyrelation.getKey());											
						PrintWorkerPO powchild = new PrintWorkerPO(Env.getLanguage(Env.getCtx()),pw, null);
						if (isheader)
							powchild.fill(Env.getCtx(), m_query, relation, relfields);
						else
							powchild.fillline(Env.getCtx(), m_query, relation, relfields);
					}										
					goahead = pd.setRowNext();
				}
				//int ID = pow.fillline(Env.getCtx(), query, maintable, fields); 
				//while (ID > 0)
				//{
				//}
			}
			if (maintable.equals(getLineTable()))
				maintable = "";
			else
			{
				//int Record_ID = ((Integer)query.getCode(0)).intValue();
				query = new MQuery();
				query.addRestriction(pow.getName_ID(), MQuery.EQUAL, pow.get_ID());
				maintable = getLineTable();
				isheader = false;
			}
		}
		return pw;		
	}

	private void fillqueryindex(PrintWorker pw, PrintData pd, HashMap indexquery, boolean header) {
		Iterator it = indexquery.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry map = (Entry) it.next();
			String index = (String) map.getKey();
			PrintDataElement pde = (PrintDataElement) pd.getNode(index);
			if (pde != null)
			{
				Object value = pde.getValueKey();
				List<Vector> vectors =  (List<Vector>) map.getValue();
				for (Vector vector:vectors)
				{
					String token = (String) vector.firstElement();
					String sql = (String) vector.lastElement();
					sql = sql.replaceAll(String.format("@%s@", index), String.valueOf(value));
					Object result = DB.getSQLValueString(null, sql);			
					if (header)
						pw.AddProperty(token, result);
					else
						pw.AddPropertyLine(token, result);
				}
			}
		}
	}

	public String getHeaderTable() {
		String table = (String) getProperties().get(ParseFormat.HEADER);
		if (table == null)
			table = "";
		return table;
	}
	public String getLineTable() {
		String table = (String) getProperties().get(ParseFormat.LINES);
		if (table == null)
			table = "";
		return table;
	}

	private HashMap<String, List<String>> getHashMapFromHstore(String contentskey) {
		HashMap<String, List<String>> tablefields = new HashMap<String, List<String>>();
		String allfields = (String) getProperties().get(contentskey);
		allfields = allfields.replace("{", "");
		allfields = allfields.replace("}", "");
		StringTokenizer st = new StringTokenizer( allfields , "]," );		

		String tablename = "";
		while ( st.hasMoreTokens()) {			
			String token = (String) st.nextElement();
			if (token.contains("=["))
			{
				tablename = "";
				StringTokenizer st2 = new StringTokenizer( token , "=[" );
				while (st2.hasMoreTokens()) {				
					String token2 = (String) st2.nextElement();
					if (tablename.length() == 0)
						tablename = token2.trim();
					else
					{
						List<String> fields = tablefields.get(tablename);
						if (fields == null)
							fields = new ArrayList<String>();
						fields.add(token2.trim());
						tablefields.put(tablename, fields);
					}
				}
			}
			else
			{	
				List<String> fields = tablefields.get(tablename);
				if (fields == null)
					fields = new ArrayList<String>();
				fields.add(token.trim());
				tablefields.put(tablename, fields);				
			}
		}		
		return tablefields;
	}
	
	private List<String> getFields(String table) {		
		HashMap<String, List<String>> fields = getHashMapFromHstore(ParseFormat.FIELDS);
		List<String> fieldlist = fields.get(table);
		if (fieldlist == null)
			fieldlist = new ArrayList<String>();
		return fieldlist;
	}
	
	private List<String> getTables(String table) {
		HashMap<String, List<String>> tables = getHashMapFromHstore(ParseFormat.RELATIONS);
		List<String> tablelist = tables.get(table);
		if (tablelist == null)
			tablelist = new ArrayList<String>();		
		return tablelist;
	}


}
