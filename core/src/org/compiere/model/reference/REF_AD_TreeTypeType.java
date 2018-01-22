package org.compiere.model.reference;


import org.xendra.annotations.*;
import org.compiere.model.Reference;

public class REF_AD_TreeTypeType implements Reference 
{
 	@XendraRefItem(Name="CM Container",
		Value="CC",
		Description="",
		Identifier="de81208d-4216-2064-d2f0-5a7a6ce6129b")
	/** CM Container = CC */
	public static final String CMContainer = "CC";
	@XendraRefItem(Name="CM Container Stage",
		Value="CS",
		Description="",
		Identifier="8bbf4655-bf04-be9a-caba-1ad7d1258e32")
	/** CM Container Stage = CS */
	public static final String CMContainerStage = "CS";
	@XendraRefItem(Name="CM Template",
		Value="CT",
		Description="",
		Identifier="54669c00-7e5f-c63f-6453-9a686f43ed8a")
	/** CM Template = CT */
	public static final String CMTemplate = "CT";
	@XendraRefItem(Name="CM Media",
		Value="CM",
		Description="",
		Identifier="0b57281b-4593-7352-29d6-5f288689d38c")
	/** CM Media = CM */
	public static final String CMMedia = "CM";
	@XendraRefItem(Name="Sales Region",
		Value="SR",
		Description="",
		Identifier="23c63fc9-8591-0602-37bf-5e93fd094bf5")
	/** Sales Region = SR */
	public static final String SalesRegion = "SR";
	@XendraRefItem(Name="Product Category",
		Value="PC",
		Description="",
		Identifier="5d649261-54dc-2140-215a-5676268c220c")
	/** Product Category = PC */
	public static final String ProductCategory = "PC";
	@XendraRefItem(Name="Campaign",
		Value="MC",
		Description="",
		Identifier="12b704ff-c52b-0df5-5225-cdb5fdd0ecff")
	/** Campaign = MC */
	public static final String Campaign = "MC";
	@XendraRefItem(Name="User 1",
		Value="U1",
		Description="",
		Identifier="20128670-77ef-fdd0-1538-115238a02b81")
	/** User 1 = U1 */
	public static final String User1 = "U1";
	@XendraRefItem(Name="User 2",
		Value="U2",
		Description="",
		Identifier="0d58d665-3951-e661-a765-6a88d038e61d")
	/** User 2 = U2 */
	public static final String User2 = "U2";
	@XendraRefItem(Name="User 3",
		Value="U3",
		Description="",
		Identifier="84c6ebe5-c95d-85ce-5bf8-c840fa881cdc")
	/** User 3 = U3 */
	public static final String User3 = "U3";
	@XendraRefItem(Name="User 4",
		Value="U4",
		Description="",
		Identifier="0a60d38e-6e33-7d2c-df6d-c12a3f1b47cb")
	/** User 4 = U4 */
	public static final String User4 = "U4";
	@XendraRefItem(Name="Menu",
		Value="MM",
		Description="",
		Identifier="09a20b4b-d98f-570e-ad3e-8c6b4056ea54")
	/** Menu = MM */
	public static final String Menu = "MM";
	@XendraRefItem(Name="Element Value",
		Value="EV",
		Description="Account, etc.",
		Identifier="6df539aa-4eda-5957-4ebc-15e7b2d86341")
	/** Element Value = EV */
	public static final String ElementValue = "EV";
	@XendraRefItem(Name="Product",
		Value="PR",
		Description="",
		Identifier="5f7d5916-e86a-7a3c-907a-a2da98ab0e38")
	/** Product = PR */
	public static final String Product = "PR";
	@XendraRefItem(Name="BPartner",
		Value="BP",
		Description="Business Partner",
		Identifier="158f0ce7-1aef-ba65-fd62-66fd8ad231da")
	/** BPartner = BP */
	public static final String BPartner = "BP";
	@XendraRefItem(Name="Accounting Subject",
		Value="AS",
		Description="",
		Identifier="15aa0bba-6f19-986a-29d9-479706e19c3e")
	/** Accounting Subject = AS */
	public static final String AccountingSubject = "AS";
	@XendraRefItem(Name="Organization",
		Value="OO",
		Description="",
		Identifier="0487d89b-86e3-854b-7639-2b9898219b45")
	/** Organization = OO */
	public static final String Organization = "OO";
	@XendraRefItem(Name="BoM",
		Value="BB",
		Description="Bill of Materials",
		Identifier="585ef568-20b7-710b-a726-7170148a0cc5")
	/** BoM = BB */
	public static final String BoM = "BB";
	@XendraRefItem(Name="Project",
		Value="PJ",
		Description="",
		Identifier="b54ff643-abd8-ccc7-9a96-3b29eea75979")
	/** Project = PJ */
	public static final String Project = "PJ";
	@XendraRefItem(Name="Activity",
		Value="AY",
		Description="",
		Identifier="1ac0d466-efd3-f281-20cd-14d683ebb252")
	/** Activity = AY */
	public static final String Activity = "AY";
	@XendraRef(Name="AD_TreeType Type",
		AD_Reference_ID="0",
		ValidationType="L",
		Description="Tree Type list",
		Help="Determines which element to use as the base for the information",
		VFormat="LL",
		EntityType="D",
		IsOrderByValue=false,
		Synchronized="2012-07-12 00:00:00.0",
		Extension="",
		Identifier="0860f346-1ab3-c4d9-3364-a5d1e32d4d6a")
	public static final String Identifier = "0860f346-1ab3-c4d9-3364-a5d1e32d4d6a";

}
