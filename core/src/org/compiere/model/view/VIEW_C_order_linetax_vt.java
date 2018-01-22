package org.compiere.model.view;


import org.xendra.annotations.*;
import org.apache.commons.lang.text.StrBuilder;
import org.compiere.model.View;

public class VIEW_C_order_linetax_vt 
{
 	@XendraView(Identifier="ad2dc08c-8b99-ebec-076f-32b4b6c85a4d",
Synchronized="2013-07-09 19:02:34.0",
Name="c_order_linetax_vt",
Owner="xendra",
Extension="")
	public static final String Identifier = "ad2dc08c-8b99-ebec-076f-32b4b6c85a4d";

	public static final String getComments() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("@Synchronized=2013-07-09 19:02:34.0");
	sb.appendln("@Identifier=ad2dc08c-8b99-ebec-076f-32b4b6c85a4d");
	return sb.toString();
}
	public static final String getDefinition() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("((SELECT ol.ad_client_id, ol.ad_org_id, ol.isactive, ol.created, ol.createdby, ol.updated, ol.updatedby, uom.ad_language, ol.c_order_id, ol.c_orderline_id, ol.c_tax_id, t.taxindicator, ol.c_bpartner_id, ol.c_bpartner_location_id, bp.name AS bpname, bpl.c_location_id, ol.line, p.m_product_id, CASE WHEN ((ol.qtyordered <> (0)::numeric) OR (ol.m_product_id IS NOT NULL)) THEN ol.qtyordered ELSE NULL::numeric END AS qtyordered, CASE WHEN ((ol.qtyentered <> (0)::numeric) OR (ol.m_product_id IS NOT NULL)) THEN ol.qtyentered ELSE NULL::numeric END AS qtyentered, CASE WHEN ((ol.qtyentered <> (0)::numeric) OR (ol.m_product_id IS NOT NULL)) THEN uom.uomsymbol ELSE NULL::character varying END AS uomsymbol, COALESCE(c.name, (((p.name)::text || (productattribute(ol.m_attributesetinstance_id))::text))::character varying, ol.description) AS name, CASE WHEN (COALESCE(c.name, pt.name, p.name) IS NOT NULL) THEN ol.description ELSE NULL::character varying END AS description, COALESCE(pt.documentnote, p.documentnote) AS documentnote, p.upc, p.sku, COALESCE(pp.vendorproductno, p.value) AS productvalue, ra.description AS resourcedescription, CASE WHEN ((i.isdiscountprinted = 'Y'::bpchar) AND (ol.pricelist <> (0)::numeric)) THEN ol.pricelist ELSE NULL::numeric END AS pricelist, CASE WHEN (((i.isdiscountprinted = 'Y'::bpchar) AND (ol.pricelist <> (0)::numeric)) AND (ol.qtyentered <> (0)::numeric)) THEN ((ol.pricelist * ol.qtyordered) / ol.qtyentered) ELSE NULL::numeric END AS priceenteredlist, CASE WHEN (((i.isdiscountprinted = 'Y'::bpchar) AND (ol.pricelist > ol.priceactual)) AND (ol.pricelist <> (0)::numeric)) THEN (((ol.pricelist - ol.priceactual) / ol.pricelist) * (100)::numeric) ELSE NULL::numeric END AS discount, CASE WHEN ((ol.priceactual <> (0)::numeric) OR (ol.m_product_id IS NOT NULL)) THEN ol.priceactual ELSE NULL::numeric END AS priceactual, CASE WHEN ((ol.priceentered <> (0)::numeric) OR (ol.m_product_id IS NOT NULL)) THEN ol.priceentered ELSE NULL::numeric END AS priceentered, CASE WHEN ((ol.linenetamt <> (0)::numeric) OR (ol.m_product_id IS NOT NULL)) THEN ol.linenetamt ELSE NULL::numeric END AS linenetamt, pt.description AS productdescription, p.imageurl, ol.c_campaign_id, ol.c_project_id, ol.c_activity_id, ol.c_projectphase_id, ol.c_projecttask_id FROM ((((((((((c_orderline ol JOIN c_uom_trl uom ON ((ol.c_uom_id = uom.c_uom_id))) JOIN c_order i ON ((ol.c_order_id = i.c_order_id))) LEFT JOIN m_product p ON ((ol.m_product_id = p.m_product_id))) LEFT JOIN m_product_trl pt ON (((ol.m_product_id = pt.m_product_id) AND ((uom.ad_language)::text = (pt.ad_language)::text)))) LEFT JOIN s_resourceassignment ra ON ((ol.s_resourceassignment_id = ra.s_resourceassignment_id))) LEFT JOIN c_charge c ON ((ol.c_charge_id = c.c_charge_id))) LEFT JOIN c_bpartner_product pp ON (((ol.m_product_id = pp.m_product_id) AND (i.c_bpartner_id = pp.c_bpartner_id)))) JOIN c_bpartner bp ON ((ol.c_bpartner_id = bp.c_bpartner_id))) JOIN c_bpartner_location bpl ON ((ol.c_bpartner_location_id = bpl.c_bpartner_location_id))) LEFT JOIN c_tax_trl t ON (((ol.c_tax_id = t.c_tax_id) AND ((uom.ad_language)::text = (t.ad_language)::text)))) UNION SELECT ol.ad_client_id, ol.ad_org_id, ol.isactive, ol.created, ol.createdby, ol.updated, ol.updatedby, uom.ad_language, ol.c_order_id, ol.c_orderline_id, ol.c_tax_id, NULL::character varying AS taxindicator, NULL::numeric AS c_bpartner_id, NULL::numeric AS c_bpartner_location_id, NULL::character varying AS bpname, NULL::numeric AS c_location_id, (ol.line + (b.line / (100)::numeric)) AS line, p.m_product_id, (ol.qtyordered * b.bomqty) AS qtyordered, (ol.qtyentered * b.bomqty) AS qtyentered, uom.uomsymbol, COALESCE(pt.name, p.name) AS name, b.description, COALESCE(pt.documentnote, p.documentnote) AS documentnote, p.upc, p.sku, p.value AS productvalue, NULL::character varying AS resourcedescription, NULL::numeric AS pricelist, NULL::numeric AS priceenteredlist, NULL::numeric AS discount, NULL::numeric AS priceactual, NULL::numeric AS priceentered, NULL::numeric AS linenetamt, pt.description AS productdescription, p.imageurl, ol.c_campaign_id, ol.c_project_id, ol.c_activity_id, ol.c_projectphase_id, ol.c_projecttask_id FROM (((((m_product_bom b JOIN c_orderline ol ON ((b.m_product_id = ol.m_product_id))) JOIN m_product bp ON (((((bp.m_product_id = ol.m_product_id) AND (bp.isbom = 'Y'::bpchar)) AND (bp.isverified = 'Y'::bpchar)) AND (bp.isinvoiceprintdetails = 'Y'::bpchar)))) JOIN m_product p ON ((b.m_productbom_id = p.m_product_id))) JOIN c_uom_trl uom ON ((p.c_uom_id = uom.c_uom_id))) JOIN m_product_trl pt ON (((b.m_productbom_id = pt.m_product_id) AND ((uom.ad_language)::text = (pt.ad_language)::text))))) UNION SELECT o.ad_client_id, o.ad_org_id, o.isactive, o.created, o.createdby, o.updated, o.updatedby, l.ad_language, o.c_order_id, NULL::numeric AS c_orderline_id, NULL::numeric AS c_tax_id, NULL::character varying AS taxindicator, NULL::numeric AS c_bpartner_id, NULL::numeric AS c_bpartner_location_id, NULL::character varying AS bpname, NULL::numeric AS c_location_id, NULL::numeric AS line, NULL::numeric AS m_product_id, NULL::numeric AS qtyordered, NULL::numeric AS qtyentered, NULL::character varying AS uomsymbol, NULL::character varying AS name, NULL::character varying AS description, NULL::character varying AS documentnote, NULL::character varying AS upc, NULL::character varying AS sku, NULL::character varying AS productvalue, NULL::character varying AS resourcedescription, NULL::numeric AS pricelist, NULL::numeric AS priceenteredlist, NULL::numeric AS discount, NULL::numeric AS priceactual, NULL::numeric AS priceentered, NULL::numeric AS linenetamt, NULL::character varying AS productdescription, NULL::character varying AS imageurl, NULL::numeric AS c_campaign_id, NULL::numeric AS c_project_id, NULL::numeric AS c_activity_id, NULL::numeric AS c_projectphase_id, NULL::numeric AS c_projecttask_id FROM c_order o, ad_language l WHERE ((l.isbaselanguage = 'N'::bpchar) AND (l.issystemlanguage = 'Y'::bpchar))) UNION SELECT ot.ad_client_id, ot.ad_org_id, ot.isactive, ot.created, ot.createdby, ot.updated, ot.updatedby, t.ad_language, ot.c_order_id, NULL::numeric AS c_orderline_id, ot.c_tax_id, t.taxindicator, NULL::numeric AS c_bpartner_id, NULL::numeric AS c_bpartner_location_id, NULL::character varying AS bpname, NULL::numeric AS c_location_id, NULL::numeric AS line, NULL::numeric AS m_product_id, NULL::numeric AS qtyordered, NULL::numeric AS qtyentered, NULL::character varying AS uomsymbol, t.name, NULL::character varying AS description, NULL::character varying AS documentnote, NULL::character varying AS upc, NULL::character varying AS sku, NULL::character varying AS productvalue, NULL::character varying AS resourcedescription, NULL::numeric AS pricelist, NULL::numeric AS priceenteredlist, NULL::numeric AS discount, CASE WHEN (ot.istaxincluded = 'Y'::bpchar) THEN ot.taxamt ELSE ot.taxbaseamt END AS priceactual, CASE WHEN (ot.istaxincluded = 'Y'::bpchar) THEN ot.taxamt ELSE ot.taxbaseamt END AS priceentered, CASE WHEN (ot.istaxincluded = 'Y'::bpchar) THEN NULL::numeric ELSE ot.taxamt END AS linenetamt, NULL::character varying AS productdescription, NULL::character varying AS imageurl, NULL::numeric AS c_campaign_id, NULL::numeric AS c_project_id, NULL::numeric AS c_activity_id, NULL::numeric AS c_projectphase_id, NULL::numeric AS c_projecttask_id FROM (c_ordertax ot JOIN c_tax_trl t ON ((ot.c_tax_id = t.c_tax_id)));");
	return sb.toString();
}
}
