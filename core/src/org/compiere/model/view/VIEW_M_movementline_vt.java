package org.compiere.model.view;


import org.xendra.annotations.*;
import org.apache.commons.lang.text.StrBuilder;
import org.compiere.model.View;

public class VIEW_M_movementline_vt 
{
 	@XendraView(Identifier="c1a98d96-9406-57da-3f28-a6668d6f19f6",
Synchronized="2013-07-09 19:02:34.0",
Name="m_movementline_vt",
Owner="xendra",
Extension="")
	public static final String Identifier = "c1a98d96-9406-57da-3f28-a6668d6f19f6";

	public static final String getComments() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("@Synchronized=2013-07-09 19:02:34.0");
	sb.appendln("@Identifier=c1a98d96-9406-57da-3f28-a6668d6f19f6");
	return sb.toString();
}
	public static final String getDefinition() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("SELECT ml.m_movementline_id, ml.ad_client_id, ml.ad_org_id, ml.isactive, ml.created, ml.createdby, ml.updated, ml.updatedby, ml.m_movement_id, ml.m_locator_id, ml.m_locatorto_id, ml.m_product_id, ml.line, ml.movementqty, ml.description, ml.m_attributesetinstance_id, ml.confirmedqty, ml.scrappedqty, ml.targetqty, ml.processed, ml.m_attributesetinstanceto_id, 'es_PE'::text AS ad_language, getaddressfromlocator(ml.m_locator_id) AS fromlocatoraddress, getaddressfromlocator(ml.m_locatorto_id) AS tolocatoraddress, p.name, p.value, p.c_uom_id FROM (m_movementline ml LEFT JOIN m_product p ON ((ml.m_product_id = p.m_product_id)));");
	return sb.toString();
}
}
