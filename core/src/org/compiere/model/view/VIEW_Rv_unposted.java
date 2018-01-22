package org.compiere.model.view;


import org.xendra.annotations.*;
import org.apache.commons.lang.text.StrBuilder;
import org.compiere.model.View;

public class VIEW_Rv_unposted 
{
 	@XendraView(Identifier="bcf3ce12-7fb2-94cc-34f9-d280813baeca",
Synchronized="2013-07-09 19:02:34.0",
Name="rv_unposted",
Owner="xendra",
Extension="")
	public static final String Identifier = "bcf3ce12-7fb2-94cc-34f9-d280813baeca";

	public static final String getComments() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("@Synchronized=2013-07-09 19:02:34.0");
	sb.appendln("@Identifier=bcf3ce12-7fb2-94cc-34f9-d280813baeca");
	return sb.toString();
}
	public static final String getDefinition() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("WITH jortableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'GL_Journal'::text)), prjtableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'C_ProjectIssue'::text)), invtableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'C_Invoice'::text)), iotableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'M_InOut'::text)), ivttableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'M_inventory'::text)), movtableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'M_Movement'::text)), prodtableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'M_Production'::text)), cashtableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'C_Cash'::text)), paytableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'c_payment'::text)), allochdrid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'C_AllocationHdr'::text)), boetableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'C_BOEStatement'::text)), rettableid AS (SELECT (ad_table.ad_table_id)::integer AS ad_table_id FROM ad_table WHERE ((ad_table.tablename)::text = 'C_RetentionStatement'::text)) ((((((((((SELECT gl_journal.ad_client_id, gl_journal.ad_org_id, gl_journal.created, gl_journal.createdby, gl_journal.updated, gl_journal.updatedby, gl_journal.isactive, gl_journal.documentno, gl_journal.datedoc, gl_journal.dateacct, jortableid.ad_table_id, gl_journal.gl_journal_id AS record_id, 'N'::text AS issotrx, gl_journal.docstatus FROM gl_journal, jortableid WHERE ((gl_journal.posted <> 'Y'::bpchar) AND (gl_journal.docstatus <> 'VO'::bpchar)) UNION SELECT pi.ad_client_id, pi.ad_org_id, pi.created, pi.createdby, pi.updated, pi.updatedby, pi.isactive, (((p.name)::text || '_'::text) || (pi.line)::text) AS documentno, pi.movementdate AS datedoc, pi.movementdate AS dateacct, prjtableid.ad_table_id, pi.c_projectissue_id AS record_id, 'N'::text AS issotrx, ''::bpchar AS docstatus FROM prjtableid, (c_projectissue pi JOIN c_project p ON ((pi.c_project_id = p.c_project_id))) WHERE (pi.posted <> 'Y'::bpchar)) UNION SELECT c_invoice.ad_client_id, c_invoice.ad_org_id, c_invoice.created, c_invoice.createdby, c_invoice.updated, c_invoice.updatedby, c_invoice.isactive, c_invoice.documentno, c_invoice.dateinvoiced AS datedoc, c_invoice.dateacct, invtableid.ad_table_id, c_invoice.c_invoice_id AS record_id, c_invoice.issotrx, c_invoice.docstatus FROM c_invoice, invtableid WHERE ((c_invoice.posted <> 'Y'::bpchar) AND (c_invoice.docstatus <> 'VO'::bpchar))) UNION SELECT m_inout.ad_client_id, m_inout.ad_org_id, m_inout.created, m_inout.createdby, m_inout.updated, m_inout.updatedby, m_inout.isactive, m_inout.documentno, m_inout.movementdate AS datedoc, m_inout.dateacct, iotableid.ad_table_id, m_inout.m_inout_id AS record_id, m_inout.issotrx, m_inout.docstatus FROM m_inout, iotableid WHERE ((m_inout.posted <> 'Y'::bpchar) AND (m_inout.docstatus <> 'VO'::bpchar))) UNION SELECT m_inventory.ad_client_id, m_inventory.ad_org_id, m_inventory.created, m_inventory.createdby, m_inventory.updated, m_inventory.updatedby, m_inventory.isactive, m_inventory.documentno, m_inventory.movementdate AS datedoc, m_inventory.movementdate AS dateacct, ivttableid.ad_table_id, m_inventory.m_inventory_id AS record_id, 'N'::text AS issotrx, m_inventory.docstatus FROM m_inventory, ivttableid WHERE ((m_inventory.posted <> 'Y'::bpchar) AND (m_inventory.docstatus <> 'VO'::bpchar))) UNION SELECT m_movement.ad_client_id, m_movement.ad_org_id, m_movement.created, m_movement.createdby, m_movement.updated, m_movement.updatedby, m_movement.isactive, m_movement.documentno, m_movement.movementdate AS datedoc, m_movement.movementdate AS dateacct, movtableid.ad_table_id, m_movement.m_movement_id AS record_id, 'N'::text AS issotrx, m_movement.docstatus FROM m_movement, movtableid WHERE ((m_movement.posted <> 'Y'::bpchar) AND (m_movement.docstatus <> 'VO'::bpchar))) UNION SELECT m_production.ad_client_id, m_production.ad_org_id, m_production.created, m_production.createdby, m_production.updated, m_production.updatedby, m_production.isactive, m_production.name AS documentno, m_production.movementdate AS datedoc, m_production.movementdate AS dateacct, prodtableid.ad_table_id, m_production.m_production_id AS record_id, 'N'::text AS issotrx, ''::bpchar AS docstatus FROM m_production, prodtableid WHERE ((m_production.posted <> 'Y'::bpchar) AND (m_production.docstatus <> 'VO'::bpchar))) UNION SELECT c_cash.ad_client_id, c_cash.ad_org_id, c_cash.created, c_cash.createdby, c_cash.updated, c_cash.updatedby, c_cash.isactive, c_cash.name AS documentno, c_cash.statementdate AS datedoc, c_cash.dateacct, cashtableid.ad_table_id, c_cash.c_cash_id AS record_id, 'N'::text AS issotrx, c_cash.docstatus FROM c_cash, cashtableid WHERE ((c_cash.posted <> 'Y'::bpchar) AND (c_cash.docstatus <> 'VO'::bpchar))) UNION SELECT c_payment.ad_client_id, c_payment.ad_org_id, c_payment.created, c_payment.createdby, c_payment.updated, c_payment.updatedby, c_payment.isactive, c_payment.documentno, c_payment.datetrx AS datedoc, c_payment.datetrx AS dateacct, paytableid.ad_table_id, c_payment.c_payment_id AS record_id, 'N'::text AS issotrx, c_payment.docstatus FROM c_payment, paytableid WHERE ((c_payment.posted <> 'Y'::bpchar) AND (c_payment.docstatus <> 'VO'::bpchar))) UNION ALL SELECT c_allocationhdr.ad_client_id, c_allocationhdr.ad_org_id, c_allocationhdr.created, c_allocationhdr.createdby, c_allocationhdr.updated, c_allocationhdr.updatedby, c_allocationhdr.isactive, c_allocationhdr.documentno, c_allocationhdr.datetrx AS datedoc, c_allocationhdr.datetrx AS dateacct, allochdrid.ad_table_id, c_allocationhdr.c_allocationhdr_id AS record_id, 'N'::text AS issotrx, c_allocationhdr.docstatus FROM c_allocationhdr, allochdrid WHERE ((c_allocationhdr.posted <> 'Y'::bpchar) AND (c_allocationhdr.docstatus <> 'VO'::bpchar))) UNION ALL SELECT c_boestatement.ad_client_id, c_boestatement.ad_org_id, c_boestatement.created, c_boestatement.createdby, c_boestatement.updated, c_boestatement.updatedby, c_boestatement.isactive, c_boestatement.documentno, c_boestatement.statusdate AS datedoc, c_boestatement.dateacct, boetableid.ad_table_id, c_boestatement.c_boestatement_id AS record_id, 'N'::text AS issotrx, c_boestatement.docstatus FROM c_boestatement, boetableid WHERE ((c_boestatement.posted <> 'Y'::bpchar) AND ((c_boestatement.docstatus)::bpchar <> 'VO'::bpchar))) UNION ALL SELECT c_retentionstatement.ad_client_id, c_retentionstatement.ad_org_id, c_retentionstatement.created, c_retentionstatement.createdby, c_retentionstatement.updated, c_retentionstatement.updatedby, c_retentionstatement.isactive, c_retentionstatement.documentno, c_retentionstatement.datedoc, c_retentionstatement.dateacct, rettableid.ad_table_id, c_retentionstatement.c_retentionstatement_id AS record_id, 'N'::text AS issotrx, c_retentionstatement.docstatus FROM c_retentionstatement, rettableid WHERE ((c_retentionstatement.posted <> 'Y'::bpchar) AND ((c_retentionstatement.docstatus)::bpchar <> 'VO'::bpchar));");
	return sb.toString();
}
}