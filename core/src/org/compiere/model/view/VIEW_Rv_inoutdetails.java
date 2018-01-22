package org.compiere.model.view;


import org.xendra.annotations.*;
import org.apache.commons.lang.text.StrBuilder;
import org.compiere.model.View;

public class VIEW_Rv_inoutdetails 
{
 	@XendraView(Identifier="2b205641-59eb-c851-9a38-6c6a972a3ee8",
Synchronized="2013-07-09 19:02:34.0",
Name="rv_inoutdetails",
Owner="xendra",
Extension="")
	public static final String Identifier = "2b205641-59eb-c851-9a38-6c6a972a3ee8";

	public static final String getComments() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("@Synchronized=2013-07-09 19:02:34.0");
	sb.appendln("@Identifier=2b205641-59eb-c851-9a38-6c6a972a3ee8");
	return sb.toString();
}
	public static final String getDefinition() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("SELECT h.ad_client_id, h.ad_org_id, l.isactive, l.created, l.createdby, l.updated, l.updatedby, h.m_inout_id, h.issotrx, h.documentno, h.docaction, h.docstatus, h.posted, h.processed, h.c_doctype_id, h.description, h.c_order_id, h.dateordered, h.movementtype, h.movementdate, h.dateacct, h.c_bpartner_id, h.c_bpartner_location_id, h.ad_user_id, h.salesrep_id, h.m_warehouse_id, h.poreference, h.deliveryrule, h.freightcostrule, h.freightamt, h.deliveryviarule, h.m_shipper_id, h.priorityrule, h.dateprinted, h.nopackages, h.pickdate, h.shipdate, h.trackingno, h.ad_orgtrx_id, h.c_project_id, h.c_campaign_id, h.c_activity_id, h.user1_id, h.user2_id, h.datereceived, h.isapproved, h.isindispute, l.m_inoutline_id, l.line, l.description AS linedescription, l.c_orderline_id, l.m_locator_id, l.m_product_id, p.value, l.c_uom_id, l.m_attributesetinstance_id, productattribute(l.m_attributesetinstance_id) AS productattribute, pasi.m_attributeset_id, pasi.m_lot_id, pasi.guaranteedate, pasi.lot, pasi.serno, l.movementqty, l.qtyentered, l.isdescription, l.confirmedqty, l.pickedqty, l.scrappedqty, l.targetqty, loc.value AS locatorvalue, loc.x, loc.y, loc.z FROM ((((m_inout h JOIN m_inoutline l ON ((h.m_inout_id = l.m_inout_id))) LEFT JOIN m_locator loc ON ((l.m_locator_id = loc.m_locator_id))) LEFT JOIN m_product p ON ((l.m_product_id = p.m_product_id))) LEFT JOIN m_attributesetinstance pasi ON ((l.m_attributesetinstance_id = pasi.m_attributesetinstance_id)));");
	return sb.toString();
}
}
