package org.compiere.model.view;


import org.xendra.annotations.*;
import org.apache.commons.lang.text.StrBuilder;
import org.compiere.model.View;

public class VIEW_Rv_payment 
{
 	@XendraView(Identifier="32e7618d-6c72-519c-cd3c-9f65fdd90ee0",
Synchronized="2013-07-24 17:58:18.0",
Name="rv_payment",
Owner="xendra",
Extension="")
	public static final String Identifier = "32e7618d-6c72-519c-cd3c-9f65fdd90ee0";

	public static final String getComments() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("@Synchronized=2013-07-24 17:58:18.0");
	sb.appendln("@Identifier=32e7618d-6c72-519c-cd3c-9f65fdd90ee0");
	return sb.toString();
}
	public static final String getDefinition() 
{
 	StrBuilder sb = new StrBuilder();
 	sb.appendln("SELECT c_payment.c_payment_id, c_payment.ad_client_id, c_payment.ad_org_id, c_payment.isactive, c_payment.created, c_payment.createdby, c_payment.updated, c_payment.updatedby, c_payment.documentno, c_payment.datetrx, c_payment.isreceipt, c_payment.c_doctype_id, c_payment.trxtype, c_payment.c_bankaccount_id, c_payment.c_bpartner_id, c_payment.c_invoice_id, c_payment.c_bp_bankaccount_id, c_payment.c_paymentbatch_id, c_payment.tendertype, c_payment.creditcardtype, c_payment.creditcardnumber, c_payment.creditcardvv, c_payment.creditcardexpmm, c_payment.creditcardexpyy, c_payment.micr, c_payment.routingno, c_payment.accountno, c_payment.checkno, c_payment.a_name, c_payment.a_street, c_payment.a_city, c_payment.a_state, c_payment.a_zip, c_payment.a_ident_dl, c_payment.a_ident_ssn, c_payment.a_email, c_payment.voiceauthcode, c_payment.orig_trxid, c_payment.ponum, c_payment.c_currency_id, c_payment.c_conversiontype_id, CASE c_payment.isreceipt WHEN 'Y'::bpchar THEN c_payment.payamt ELSE (c_payment.payamt * ((-1))::numeric) END AS payamt, CASE c_payment.isreceipt WHEN 'Y'::bpchar THEN c_payment.discountamt ELSE (c_payment.discountamt * ((-1))::numeric) END AS discountamt, CASE c_payment.isreceipt WHEN 'Y'::bpchar THEN c_payment.writeoffamt ELSE (c_payment.writeoffamt * ((-1))::numeric) END AS writeoffamt, CASE c_payment.isreceipt WHEN 'Y'::bpchar THEN c_payment.taxamt ELSE (c_payment.taxamt * ((-1))::numeric) END AS taxamt, CASE c_payment.isreceipt WHEN 'Y'::bpchar THEN c_payment.overunderamt ELSE (c_payment.overunderamt * ((-1))::numeric) END AS overunderamt, CASE c_payment.isreceipt WHEN 'Y'::bpchar THEN 1 ELSE (-1) END AS multiplierap, paymentallocated(c_payment.c_payment_id, c_payment.c_currency_id) AS allocatedamt, paymentavailable(c_payment.c_payment_id) AS availableamt, c_payment.isoverunderpayment, c_payment.isapproved, c_payment.r_pnref, c_payment.r_result, c_payment.r_respmsg, c_payment.r_authcode, c_payment.r_avsaddr, c_payment.r_avszip, c_payment.r_info, c_payment.processing, c_payment.oprocessing, c_payment.docstatus, c_payment.docaction, c_payment.isprepayment, c_payment.c_charge_id, c_payment.isreconciled, c_payment.isallocated, c_payment.isonline, c_payment.processed, c_payment.posted, c_payment.c_campaign_id, c_payment.c_project_id, c_payment.c_activity_id FROM c_payment;");
	return sb.toString();
}
}
