/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.businessobjects.DateTime;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;

/**
 * @author twalto
 * 
 * Retrieve and Display a SPECIFIC Method
 */
public class DtlMethod extends BaseViewBeanR2 {
	
	public String securityLevel			= "";

	public String methodNumber      	= "";
	public String revisionDate     	 	= "";
	public String revisionTime      	= "";
	
	public BeanQuality dtlBean 			= new BeanQuality();
		
	public Vector listReport            = null;
	public Vector listComments			= new Vector();
	
//	 Lists for Methods
	public Vector listTheory			= new Vector(); // comment - from UpdKeyValues
	public Vector listTheoryUrl			= new Vector(); // url - from UpdKeyValues
	public Vector listEquipment			= new Vector(); // comment1 - from UpdKeyValues
	public Vector listEquipmentUrl		= new Vector(); // url1 - from UpdKeyValues
	public Vector listReagents			= new Vector(); // comment4 - from UpdKeyValues
	public Vector listReagentsUrl		= new Vector(); // url4 - from UpdKeyValues
	public Vector listCaution			= new Vector(); // comment5 - from UpdKeyValues
	public Vector listCautionUrl		= new Vector(); // url5 - from UpdKeyValues
	public Vector listFrequency			= new Vector(); // comment6 - from UpdKeyValues
	public Vector listFrequencyUrl		= new Vector(); // url6 - from UpdKeyValues
	public Vector listExamples			= new Vector(); // comment7 - from UpdKeyValues
	public Vector listExamplesUrl		= new Vector(); // url7 - from UpdKeyValues
	public Vector listCalculations		= new Vector(); // comment8 - from UpdKeyValues
	public Vector listCalculationsUrl	= new Vector(); // url8 - from UpdKeyValues
	public Vector listInterpretation	= new Vector(); // comment9 - from UpdKeyValues
	public Vector listInterpretationUrl = new Vector(); // url9 - from UpdKeyValues
	public Vector listReporting			= new Vector(); // comment11 - from UpdKeyValues
	public Vector listReportingUrl		= new Vector(); // url11 - from UpdKeyValues
	
	// Lists for Procedures
	public Vector listPurpose			= new Vector(); // comment5 - from UpdKeyValues
	public Vector listPurposeUrl		= new Vector(); // url5 - from UpdKeyValues
	public Vector listScope				= new Vector(); // comment6 - from UpdKeyValues
	public Vector listScopeUrl			= new Vector(); // url6 - from UpdKeyValues
	public Vector listResponsibility 	= new Vector(); // comment7 - from UpdKeyValues
	public Vector listResponsibilityUrl = new Vector(); // url7 - from UpdKeyValues
	public Vector listDefinitions		= new Vector(); // comment1 - from UpdKeyValues
	public Vector listDefinitionsUrl	= new Vector(); // url1 - from UpdKeyValues
	
	// Lists for Policies
	public Vector listAuthorization 	= new Vector(); // comment1 - from UpdKeyValues
	public Vector listAuthorizationUrl 	= new Vector(); // url1 - from UpdKeyValues
	// Lists for Both - Methods and Procedures
	public Vector listProcedure			= new Vector(); // comment2 - from UpdKeyValues
	public Vector listProcedureUrl		= new Vector(); // url2 - from UpdKeyValues
	
	// Lists for Both Procedures and Policies
	public Vector listPolicy			= new Vector(); // comment4 - from UpdKeyValues
	public Vector listPolicyUrl			= new Vector(); // url4 - from UpdKeyValues
	
	// Lists for Both Methods and Policies
	public Vector listActions			= new Vector(); // comment10 - from UpdKeyValues
	public Vector listActionsUrl		= new Vector(); // url10 - from UpdKeyValues
	
	// Lists for All - Methods - Procedures - Policies
	// Will show on the screen as Comments
	public Vector listAdditional		= new Vector(); // comment3 - from UpdKeyValues
	public Vector listAdditionalUrl		= new Vector(); // url3 - from UpdKeyValues
	
	public DateTime  supersedesDate		= new DateTime();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}
	public String getMethodNumber() {
		return methodNumber;
	}
	public void setMethodNumber(String methodNumber) {
		this.methodNumber = methodNumber;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public BeanQuality getDtlBean() {
		return dtlBean;
	}
	public void setDtlBean(BeanQuality dtlBean) {
		this.dtlBean = dtlBean;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Vector getListEquipment() {
		return listEquipment;
	}
	public void setListEquipment(Vector listEquipment) {
		this.listEquipment = listEquipment;
	}
	public Vector getListProcedure() {
		return listProcedure;
	}
	public void setListProcedure(Vector listProcedure) {
		this.listProcedure = listProcedure;
	}
	public Vector getListTheory() {
		return listTheory;
	}
	public void setListTheory(Vector listTheory) {
		this.listTheory = listTheory;
	}
	public Vector getListAdditional() {
		return listAdditional;
	}
	public void setListAdditional(Vector listAdditional) {
		this.listAdditional = listAdditional;
	}
	public Vector getListPolicy() {
		return listPolicy;
	}
	public void setListPolicy(Vector listPolicy) {
		this.listPolicy = listPolicy;
	}
	public Vector getListPurpose() {
		return listPurpose;
	}
	public void setListPurpose(Vector listPurpose) {
		this.listPurpose = listPurpose;
	}
	public Vector getListResponsibility() {
		return listResponsibility;
	}
	public void setListResponsibility(Vector listResponsibility) {
		this.listResponsibility = listResponsibility;
	}
	public Vector getListScope() {
		return listScope;
	}
	public void setListScope(Vector listScope) {
		this.listScope = listScope;
	}
	public Vector getListActions() {
		return listActions;
	}
	public void setListActions(Vector listActions) {
		this.listActions = listActions;
	}
	public Vector getListAuthorization() {
		return listAuthorization;
	}
	public void setListAuthorization(Vector listAuthorization) {
		this.listAuthorization = listAuthorization;
	}
	public Vector getListCalculations() {
		return listCalculations;
	}
	public void setListCalculations(Vector listCalculations) {
		this.listCalculations = listCalculations;
	}
	public Vector getListCaution() {
		return listCaution;
	}
	public void setListCaution(Vector listCaution) {
		this.listCaution = listCaution;
	}
	public Vector getListDefinitions() {
		return listDefinitions;
	}
	public void setListDefinitions(Vector listDefinitions) {
		this.listDefinitions = listDefinitions;
	}
	public Vector getListExamples() {
		return listExamples;
	}
	public void setListExamples(Vector listExamples) {
		this.listExamples = listExamples;
	}
	public Vector getListFrequency() {
		return listFrequency;
	}
	public void setListFrequency(Vector listFrequency) {
		this.listFrequency = listFrequency;
	}
	public Vector getListInterpretation() {
		return listInterpretation;
	}
	public void setListInterpretation(Vector listInterpretation) {
		this.listInterpretation = listInterpretation;
	}
	public Vector getListReagents() {
		return listReagents;
	}
	public void setListReagents(Vector listReagents) {
		this.listReagents = listReagents;
	}
	public Vector getListReporting() {
		return listReporting;
	}
	public void setListReporting(Vector listReporting) {
		this.listReporting = listReporting;
	}
	public Vector getListActionsUrl() {
		return listActionsUrl;
	}
	public void setListActionsUrl(Vector listActionsUrl) {
		this.listActionsUrl = listActionsUrl;
	}
	public Vector getListAdditionalUrl() {
		return listAdditionalUrl;
	}
	public void setListAdditionalUrl(Vector listAdditionalUrl) {
		this.listAdditionalUrl = listAdditionalUrl;
	}
	public Vector getListAuthorizationUrl() {
		return listAuthorizationUrl;
	}
	public void setListAuthorizationUrl(Vector listAuthorizationUrl) {
		this.listAuthorizationUrl = listAuthorizationUrl;
	}
	public Vector getListCalculationsUrl() {
		return listCalculationsUrl;
	}
	public void setListCalculationsUrl(Vector listCalculationsUrl) {
		this.listCalculationsUrl = listCalculationsUrl;
	}
	public Vector getListCautionUrl() {
		return listCautionUrl;
	}
	public void setListCautionUrl(Vector listCautionUrl) {
		this.listCautionUrl = listCautionUrl;
	}
	public Vector getListDefinitionsUrl() {
		return listDefinitionsUrl;
	}
	public void setListDefinitionsUrl(Vector listDefinitionsUrl) {
		this.listDefinitionsUrl = listDefinitionsUrl;
	}
	public Vector getListEquipmentUrl() {
		return listEquipmentUrl;
	}
	public void setListEquipmentUrl(Vector listEquipmentUrl) {
		this.listEquipmentUrl = listEquipmentUrl;
	}
	public Vector getListExamplesUrl() {
		return listExamplesUrl;
	}
	public void setListExamplesUrl(Vector listExamplesUrl) {
		this.listExamplesUrl = listExamplesUrl;
	}
	public Vector getListFrequencyUrl() {
		return listFrequencyUrl;
	}
	public void setListFrequencyUrl(Vector listFrequencyUrl) {
		this.listFrequencyUrl = listFrequencyUrl;
	}
	public Vector getListInterpretationUrl() {
		return listInterpretationUrl;
	}
	public void setListInterpretationUrl(Vector listInterpretationUrl) {
		this.listInterpretationUrl = listInterpretationUrl;
	}
	public Vector getListPolicyUrl() {
		return listPolicyUrl;
	}
	public void setListPolicyUrl(Vector listPolicyUrl) {
		this.listPolicyUrl = listPolicyUrl;
	}
	public Vector getListProcedureUrl() {
		return listProcedureUrl;
	}
	public void setListProcedureUrl(Vector listProcedureUrl) {
		this.listProcedureUrl = listProcedureUrl;
	}
	public Vector getListPurposeUrl() {
		return listPurposeUrl;
	}
	public void setListPurposeUrl(Vector listPurposeUrl) {
		this.listPurposeUrl = listPurposeUrl;
	}
	public Vector getListReagentsUrl() {
		return listReagentsUrl;
	}
	public void setListReagentsUrl(Vector listReagentsUrl) {
		this.listReagentsUrl = listReagentsUrl;
	}
	public Vector getListReportingUrl() {
		return listReportingUrl;
	}
	public void setListReportingUrl(Vector listReportingUrl) {
		this.listReportingUrl = listReportingUrl;
	}
	public Vector getListResponsibilityUrl() {
		return listResponsibilityUrl;
	}
	public void setListResponsibilityUrl(Vector listResponsibilityUrl) {
		this.listResponsibilityUrl = listResponsibilityUrl;
	}
	public Vector getListScopeUrl() {
		return listScopeUrl;
	}
	public void setListScopeUrl(Vector listScopeUrl) {
		this.listScopeUrl = listScopeUrl;
	}
	public Vector getListTheoryUrl() {
		return listTheoryUrl;
	}
	public void setListTheoryUrl(Vector listTheoryUrl) {
		this.listTheoryUrl = listTheoryUrl;
	}
	public DateTime getSupersedesDate() {
		return supersedesDate;
	}
	public void setSupersedesDate(DateTime supersedesDate) {
		this.supersedesDate = supersedesDate;
	}
}
