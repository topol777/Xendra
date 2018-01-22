package org.compiere.model.valrule;


import org.xendra.annotations.*;
import org.compiere.model.ValRule;

public class VAL_C_ElementValue_CBanco implements ValRule 
{
 	@XendraValRule(Name="C_ElementValue_CBanco",
		AD_Val_Rule_ID=1000012,
		Description="",
		Type="S",
		EntityType="D",
		Identifier="1de9cd21-4be1-272f-e4a6-ee84b77f7c8c",
		Synchronized="2012-09-19 17:23:37.0")
	public static final String Identifier = "1de9cd21-4be1-272f-e4a6-ee84b77f7c8c";

	public static final String getCode() 
{
 	StringBuilder sb = new StringBuilder();
 	sb.append("EXISTS (SELECT * FROM C_AcctSchema_Element ae WHERE C_ElementValue.C_Element_ID=ae.C_Element_ID AND ae.ElementType='AC' AND ae.C_AcctSchema_ID=@C_AcctSchema_ID@) AND C_ElementValue.IsActive='Y' AND C_ElementValue.IsSummary='N' AND C_ElementValue.Value like '10%' AND C_ElementValue.IsBankAccount='Y'");
	return sb.toString();
}
}
