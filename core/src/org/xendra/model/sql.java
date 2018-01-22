package org.xendra.model;

public interface sql {
	static String VALIDATIONTYPE_TableValidation = "SELECT kc.ColumnName as kcolumnName, dc.ColumnName as dcolumnName, "
				+ " t.TableName, rt.whereclause, rt.orderbyclause, rt.isvaluedisplayed, rt.entitytype, rt.identifier, "
				+ " rt.synchronized "
				+ "FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Column kc ON (rt.AD_Key=kc.AD_Column_ID)"
				+ " INNER JOIN AD_Column dc ON (rt.AD_Display=dc.AD_Column_ID)"
				+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID) "
				+ "WHERE rt.AD_Reference_ID=?";
    static String VALIDATIONTYPE_TableIdentifierValidation = "SELECT kc.Identifier as kcolumnName, dc.Identifier as dcolumnName, "
				+ " t.Identifier as tablename, rt.whereclause, rt.orderbyclause, rt.isvaluedisplayed, rt.entitytype, rt.identifier, "
				+ " rt.synchronized "
				+ "FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Column kc ON (rt.AD_Key=kc.AD_Column_ID)"
				+ " INNER JOIN AD_Column dc ON (rt.AD_Display=dc.AD_Column_ID)"
				+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID) "
				+ "WHERE rt.AD_Reference_ID=?";
    
	static String GETINDEX = " SELECT t.relname as table_name,  i.relname as name,  array_to_string(array_agg(a.attname), ', ') as column_names, "
	+ " d.description as comments  FROM  pg_class t JOIN pg_catalog.pg_namespace n ON n.oid = t.relnamespace,"
	+ " pg_class i LEFT JOIN pg_description As d ON (d.objoid = i.oid AND d.objsubid = 0),  pg_index ix,"
	+ " pg_attribute a WHERE "
	+ "	t.oid = ix.indrelid"
	+ "	and i.oid = ix.indexrelid"
	+ "	and a.attrelid = t.oid"
	+ "	and a.attnum = ANY(ix.indkey)"
	+ " and t.relkind = 'r' "
	+ "	AND indisprimary != 't'"          
	+ "	AND n.nspname = 'xendra'"
	+ "	group by "
	+ "	t.relname,"
	+ "	i.relname,"
	+ " d.description"
	+ "	order by"
	+ "	t.relname,"
	+ "	i.relname;";		
	
	static String GETINDEXBYTABLE = "SELECT ci.relname AS name," 
       +" array_to_string(array_agg(pg_catalog.pg_get_indexdef(ci.oid, (i.keys).n, false)), ', ') as column_names,"
       +" d.description as comments, "
       +" indisunique as isunique "
       +" FROM pg_catalog.pg_class ct " 
       +" JOIN pg_catalog.pg_namespace n ON (ct.relnamespace = n.oid) " 
       +" JOIN (SELECT i.indexrelid, i.indrelid, i.indoption, i.indisprimary,"
       +" i.indisunique, i.indisclustered, i.indpred," 
       +" i.indexprs," 
       +" information_schema._pg_expandarray(i.indkey) AS keys" 
       +" FROM pg_catalog.pg_index i) i" 
       +" ON (ct.oid = i.indrelid)" 
       +" JOIN pg_catalog.pg_class ci ON (ci.oid = i.indexrelid)" 
       +" LEFT JOIN pg_description As d ON (d.objoid = ci.oid AND d.objsubid = 0)"
       +" JOIN pg_catalog.pg_am am ON (ci.relam = am.oid)" 
       +" WHERE n.nspname = 'xendra'"
       +" AND ct.relname = ?"    		
       +" AND indisprimary != 't'"
       +" GROUP BY ci.relname , d.description, indisunique";

	static String  GETVIEWS = "SELECT v.oid, s.nspname as schema,  r.rolname as viewowner,v.relname as viewname,"
	+ " pg_get_viewdef(v.oid) as definition, d.description AS comment"
	+ "  FROM " 
	+ " (" 
	+ " 	SELECT pg_class.oid, pg_class.*" 
	+ " 	FROM pg_class"
	+ " 	WHERE pg_class.relkind ='v':: \"char\""
	+ " ) v"
	+ "  JOIN pg_namespace s ON s.oid = v.relnamespace AND s.nspname = 'xendra' "
	+ "  JOIN pg_catalog.pg_roles r ON r.oid = v.relowner "
	+ "  LEFT JOIN pg_description d ON d.objoid = v.oid AND d.classoid = 'pg_class'::regclass::oid AND d.objsubid = 0 "			
	+ "  ORDER by s.nspname, v.relname";
	static String GETOPERATORS = "SELECT op.oprname,op.oprcode as operproc,"
	+ " lt.typname as lefttype," 
	+ " rt.typname as righttype,"
	+ " co.oprname as compop,  "
	+ " pg_get_userbyid(op.oprowner) as owner,"
	+ " obj_description(op.oid) as description"	
	+ "  FROM pg_operator op "
	+ " LEFT OUTER JOIN pg_type lt ON lt.oid=op.oprleft "
	+ " LEFT OUTER JOIN pg_type rt ON rt.oid=op.oprright "
	+ " LEFT OUTER JOIN pg_operator co ON co.oid=op.oprcom "
	+ " WHERE pg_get_userbyid(op.oprowner) = 'xendra' "
	+ "  ORDER BY op.oprname  ";
	
	static String GETFUNCTIONS = " SELECT p.proname as functionname," 
	+ " CASE WHEN p.proretset THEN 'setof ' ELSE '' END || "
	+ "  pg_catalog.format_type(p.prorettype, NULL) as output,"
	+ "  CASE WHEN proallargtypes IS NOT NULL THEN"
	+ "    pg_catalog.array_to_string(ARRAY("
	+ "      SELECT"
	+ "        CASE"
	+ "          WHEN p.proargmodes[s.i] = 'i' THEN ''"
	+ "          WHEN p.proargmodes[s.i] = 'o' THEN 'OUT '"
	+ "          WHEN p.proargmodes[s.i] = 'b' THEN 'INOUT '"
	+ "        END ||"
	+ "        CASE"
	+ "         WHEN COALESCE(p.proargnames[s.i], '') = '' THEN ''"
	+ "          ELSE p.proargnames[s.i] || ' ' "
	+ "        END ||"
	+ "        pg_catalog.format_type(p.proallargtypes[s.i], NULL)"
	+ "      FROM"
	+ "        pg_catalog.generate_series(1,"
	+ " pg_catalog.array_upper(p.proallargtypes, 1)) AS s(i)"
	+ "    ), ', ')"
	+ "  ELSE"
	+ "    pg_catalog.array_to_string(ARRAY("
	+ "      SELECT"
	+ "        CASE"
	+ "          WHEN COALESCE(p.proargnames[s.i+1], '') = '' THEN ''"
	+ "          ELSE p.proargnames[s.i+1] || ' '"
	+ "          END ||"
	+ "        pg_catalog.format_type(p.proargtypes[s.i], NULL)"
	+ "      FROM"
	+ "        pg_catalog.generate_series(0,"
	+ "pg_catalog.array_upper(p.proargtypes, 1)) AS s(i)"
	+ "    ), ', ')"
	+ "  END AS arguments,"
	+ "  r.rolname as owner,"
	+ "  l.lanname as language,"
	+ "  prosrc as sourcecode,"
	+ "  pg_catalog.obj_description(p.oid, 'pg_proc') as comments"
	+ "  FROM pg_catalog.pg_proc p"
	+ "     LEFT JOIN pg_catalog.pg_namespace n ON n.oid = p.pronamespace"
	+ "     LEFT JOIN pg_catalog.pg_language l ON l.oid = p.prolang"
	+ "     JOIN pg_catalog.pg_roles r ON r.oid = p.proowner"
	+ "  WHERE p.prorettype <> 'pg_catalog.cstring'::pg_catalog.regtype"
	+ "      AND (p.proargtypes[0] IS NULL"
	+ "      OR   p.proargtypes[0] <> 'pg_catalog.cstring'::pg_catalog.regtype)"
	+ "      AND NOT p.proisagg"
	+ " AND nspname = 'xendra' and l.lanname != 'c' "
	+ " ORDER BY 1, 2, 3, 4 ;		";		
	
	static String DROPVIEW = "DROP VIEW IF EXISTS xendra.%s CASCADE;";
	static String DROPOPERATOR = "DROP OPERATOR IF EXISTS %s CASCADE;";
	static String CREATEEXTENSION = "CREATE EXTENSION IF NOT EXISTS %s SCHEMA xendra;";
	static String CREATEEXTENSIONUNPACKED = "CREATE EXTENSION $s SCHEMA xendra FROM unpackaged;";
	static String OWNERTOXENDRA = "ALTER TABLE xendra.%s OWNER TO xendra;";
	static String OPERATOR = "operator";
	static String VIEW = "view";
	static String PRODUCTCOSTINGDIFF = "SELECT p.m_product_id,pcqty,trxqty,(pcqty-trxqty) as saldo"
	+"	FROM " 
	+"	("
	+"	 SELECT z.m_product_id,SUM(trxqty) as trxqty,SUM(pcqty) as pcqty"
	+"	 FROM "
	+"	("
	+"	 SELECT m_product_id,SUM(c.movementqty) as trxqty,0 as pcqty FROM m_transaction c"
	+"	 JOIN m_materialpolicy mp ON c.ad_client_id = mp.ad_client_id"
	+"	 JOIN c_period p on movementdate <= p.enddate::date"
	+"	     WHERE c.movementdate >= mp.startdate AND (c.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))"
	+"	  AND p.c_period_id = ?"
	+"	 GROUP BY m_product_id"
	+"	UNION ALL"
	+"	SELECT"
	+"	m_product_id,0.trxqty,pc.costaveragecumqty as pcqty"
	+"	from m_product_costing pc join c_period p on pc.c_period_id = p.c_period_id "
	+"	WHERE p.c_period_id = ?"
	+"	) z "
	+"	GROUP BY m_product_id "
	+"	) as zz "
	+"	JOIN m_product p on zz.m_product_id = p.m_product_id "
	+"	WHERE (trxqty-pcqty) <> 0 "
	+"	ORDER BY saldo ";
}
