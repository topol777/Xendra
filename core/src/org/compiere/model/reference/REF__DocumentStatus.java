package org.compiere.model.reference;


import org.xendra.annotations.*;
import org.compiere.model.Reference;

public class REF__DocumentStatus implements Reference 
{
 	@XendraRefItem(Name="In Progress",
		Value="IP",
		Description="",
		Identifier="9220f11c-4e3c-32c1-a0b2-c828d9bcb3e6")
	/** In Progress = IP */
	public static final String InProgress = "IP";
	@XendraRefItem(Name="Waiting Confirmation",
		Value="WC",
		Description="",
		Identifier="b72d6fa8-77f8-ad1a-8b8e-e2844bb54b1b")
	/** Waiting Confirmation = WC */
	public static final String WaitingConfirmation = "WC";
	@XendraRefItem(Name="Drafted",
		Value="DR",
		Description="",
		Identifier="0686db79-3edf-5c86-2648-f569985f4e99")
	/** Drafted = DR */
	public static final String Drafted = "DR";
	@XendraRefItem(Name="Completed",
		Value="CO",
		Description="",
		Identifier="f00fe5d1-bf4f-3275-ae64-9800b722aaf2")
	/** Completed = CO */
	public static final String Completed = "CO";
	@XendraRefItem(Name="Approved",
		Value="AP",
		Description="",
		Identifier="3852c949-1af4-327d-3dd4-511a4d5dd494")
	/** Approved = AP */
	public static final String Approved = "AP";
	@XendraRefItem(Name="Waiting Payment",
		Value="WP",
		Description="",
		Identifier="22d9bd4d-f43a-7ed5-c920-04d70ce31de1")
	/** Waiting Payment = WP */
	public static final String WaitingPayment = "WP";
	@XendraRefItem(Name="Placed",
		Value="PL",
		Description="",
		Identifier="b9d3845d-d4d6-0829-ec0b-1ffe90802ad9")
	/** Placed = PL */
	public static final String Placed = "PL";
	@XendraRefItem(Name="Returned",
		Value="RT",
		Description="",
		Identifier="c39fcc2c-6642-4725-56b6-b76ce9b0d781")
	/** Returned = RT */
	public static final String Returned = "RT";
	@XendraRefItem(Name="Discount",
		Value="DS",
		Description="",
		Identifier="17c606fd-a76b-e4d5-47db-77f828b80e09")
	/** Discount = DS */
	public static final String Discount = "DS";
	@XendraRefItem(Name="Protested",
		Value="PD",
		Description="",
		Identifier="9f76228b-5514-2fb7-7d1d-81db62fb7be3")
	/** Protested = PD */
	public static final String Protested = "PD";
	@XendraRefItem(Name="Not Approved",
		Value="NA",
		Description="",
		Identifier="1772d6bb-fdcd-dd2a-a27f-19e446a36b2d")
	/** Not Approved = NA */
	public static final String NotApproved = "NA";
	@XendraRefItem(Name="Voided",
		Value="VO",
		Description="",
		Identifier="b2e68605-1635-f40f-1e0a-4232ae100642")
	/** Voided = VO */
	public static final String Voided = "VO";
	@XendraRefItem(Name="Invalid",
		Value="IN",
		Description="",
		Identifier="7a6d10e5-60c2-ffb9-5bc8-8817c8046446")
	/** Invalid = IN */
	public static final String Invalid = "IN";
	@XendraRefItem(Name="Reversed",
		Value="RE",
		Description="",
		Identifier="165c3e8c-e68b-0123-8be5-3d6ec58dbbd1")
	/** Reversed = RE */
	public static final String Reversed = "RE";
	@XendraRefItem(Name="Closed",
		Value="CL",
		Description="",
		Identifier="a87e694b-d30f-3872-b998-d08d5d2183bf")
	/** Closed = CL */
	public static final String Closed = "CL";
	@XendraRefItem(Name="Unknown",
		Value="??",
		Description="",
		Identifier="94e46be6-137c-35d0-d24d-aca6e692a03f")
	/** Unknown = ?? */
	public static final String Unknown = "??";
	@XendraRefItem(Name="Portfolio",
		Value="PT",
		Description="",
		Identifier="a2698f42-3f7b-a9af-0541-46bf7e82a3f1")
	/** Portfolio = PT */
	public static final String Portfolio = "PT";
	@XendraRefItem(Name="Warranty",
		Value="WR",
		Description="",
		Identifier="82aec84a-a2e3-9eab-df89-f9fae74397bc")
	/** Warranty = WR */
	public static final String Warranty = "WR";
	@XendraRefItem(Name="To Collect",
		Value="TC",
		Description="",
		Identifier="3d8279f9-ca4b-1ce1-fc84-9553d1632ea2")
	/** To Collect = TC */
	public static final String ToCollect = "TC";
	@XendraRefItem(Name="Collect",
		Value="CT",
		Description="",
		Identifier="7cfd5f09-a2d6-cf16-3f13-a7b26bea8b2c")
	/** Collect = CT */
	public static final String Collect = "CT";
	@XendraRefItem(Name="Receivables",
		Value="RV",
		Description="",
		Identifier="d9958116-3d39-8304-648c-c5b2ec68798a")
	/** Receivables = RV */
	public static final String Receivables = "RV";
	@XendraRefItem(Name="Apply",
		Value="AY",
		Description="",
		Identifier="855e7f42-cab8-fd32-3dbe-3bb3f40e3e45")
	/** Apply = AY */
	public static final String Apply = "AY";
	@XendraRef(Name="_Document Status",
		AD_Reference_ID="0",
		ValidationType="L",
		Description="Document Status list",
		Help="",
		VFormat="LL",
		EntityType="D",
		IsOrderByValue=false,
		Synchronized="2012-07-12 00:00:00.0",
		Extension="",
		Identifier="7627125d-fefe-e6bc-bb7d-4a6c772172dc")
	public static final String Identifier = "7627125d-fefe-e6bc-bb7d-4a6c772172dc";

}
