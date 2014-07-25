/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.businessobjects.*;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.services.ServiceQuality;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;

/**
 * @author twalto
 * 
 * Use as the Method Header... all information will filter through here
 */
public class UpdMethod extends BaseViewBeanR2 {

	// Must have in Update View Bean
	public String updateUser 		= "";
	public String updateDate		= "";
	public String updateTime		= "";
	
	// Generate not updateable
	public String methodNumber 		= "";
	public String revisionUser 		= "";
	public String revisionDate 		= "";
	public String revisionTime 		= "";
	public DateTime supersedesDate 				= new DateTime();
	
	// Fields Available for Update
	public String status 			= "";
	public String originalStatus 	= "";
	public String methodName		= "";
	public String methodDescription = "";
	public String recordType		= ""; // MTH or PRC (Method Or Procedure
	public String revisionReason 	= "";
	public String revisionReasonError = "";
	public String originationUser	= "";
	public String groupCode			= "";
	public String scopeCode			= "";
	public String approvedByUser	= "";
	
	// additional values sent -- push through on a new revision
	public String creationDate 		= "";
	public String creationTime 		= "";
	public String creationUser 		= "";
	
	// Lists for Methods
	public Vector listTheory		= new Vector(); // comment - from UpdKeyValues
	public Vector listTheoryUrl		= new Vector(); // url - from UpdKeyValues
	public Vector listEquipment		= new Vector(); // comment1 - from UpdKeyValues
	public Vector listEquipmentUrl	= new Vector(); // url1 - from UpdKeyValues
	public Vector listReagents		= new Vector(); // comment4 - from UpdKeyValues
	public Vector listReagentsUrl	= new Vector(); // url4 - from UpdKeyValues
	public Vector listCaution		= new Vector(); // comment5 - from UpdKeyValues
	public Vector listCautionUrl	= new Vector(); // url5 - from UpdKeyValues
	public Vector listFrequency		= new Vector(); // comment6 - from UpdKeyValues
	public Vector listFrequencyUrl	= new Vector(); // url6 - from UpdKeyValues
	public Vector listExamples		= new Vector(); // comment7 - from UpdKeyValues
	public Vector listExamplesUrl	= new Vector(); // url7 - from UpdKeyValues
	public Vector listCalculations	= new Vector(); // comment8 - from UpdKeyValues
	public Vector listCalculationsUrl   = new Vector(); // url8 - from UpdKeyValues
	public Vector listInterpretation= new Vector(); // comment9 - from UpdKeyValues
	public Vector listInterpretationUrl = new Vector(); // url9 - from UpdKeyValues
	public Vector listReporting		= new Vector(); // comment11 - from UpdKeyValues
	public Vector listReportingUrl	= new Vector(); // url11 - from UpdKeyValues
	
	// Lists for Procedures
	public Vector listPurpose		= new Vector(); // comment5 - from UpdKeyValues
	public Vector listPurposeUrl	= new Vector(); // url5 - from UpdKeyValues
	public Vector listScope			= new Vector(); // comment6 - from UpdKeyValues
	public Vector listScopeUrl		= new Vector(); // url6 - from UpdKeyValues
	public Vector listResponsibility = new Vector(); // comment7 - from UpdKeyValues
	public Vector listResponsibilityUrl = new Vector(); // url7 - from UpdKeyValues
	public Vector listDefinitions	= new Vector(); // comment1 - from UpdKeyValues
	public Vector listDefinitionsUrl= new Vector(); // url1 - from UpdKeyValues
	
	// Lists for Policies
	public Vector listAuthorization = new Vector(); // comment1 - from UpdKeyValues
	public Vector listAuthorizationUrl = new Vector(); // url1 - from UpdKeyValues
	
	// Lists for Both - Methods and Procedures
	public Vector listProcedure		= new Vector(); // comment2 - from UpdKeyValues
	public Vector listProcedureUrl	= new Vector(); // url2 - from UpdKeyValues
	
	// Lists for Both Procedures and Policies
	public Vector listPolicy		= new Vector(); // comment4 - from UpdKeyValues
	public Vector listPolicyUrl		= new Vector(); // url4 - from UpdKeyValues
	
	// Lists for Both Methods and Policies
	public Vector listActions		= new Vector(); // comment10 - from UpdKeyValues
	public Vector listActionsUrl	= new Vector(); // url10 - from UpdKeyValues
	
	// Lists for All - Methods - Procedures - Policies
	// Will show on the screen as Comments
	public Vector listAdditional	= new Vector(); // comment3 - from UpdKeyValues
	public Vector listAdditionalUrl	= new Vector(); // url3 - from UpdKeyValues
	
	//Button Values
	public String saveButton 		= "";
	
	public BeanQuality updBean 		= new BeanQuality();
		
	public Vector listReport 		= null;

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
			if (this.getRequestType().trim().equals("addMethod") ||
				this.getRequestType().trim().equals("updMethod") ||
				this.getRequestType().trim().equals("copyNewMethod") ||
				this.getRequestType().trim().equals("reviseMethod"))
			   this.setRecordType("MTH");
			if (this.getRequestType().trim().equals("addProcedure") ||
				this.getRequestType().trim().equals("updProcedure") ||
				this.getRequestType().trim().equals("copyNewProcedure") ||
				this.getRequestType().trim().equals("reviseProcedure"))
			   this.setRecordType("PRC");
			if (this.getRequestType().trim().equals("addPolicy") ||
				this.getRequestType().trim().equals("updPolicy") ||
				this.getRequestType().trim().equals("copyNewPolicy") ||
				this.getRequestType().trim().equals("revisePolicy"))
			   this.setRecordType("POL");
			if (!this.saveButton.trim().equals(""))
			{
			   if (this.revisionReason.trim().equals(""))
			     this.setRevisionReasonError("Revision Reason MUST be filled in");
			}
			
			if (!this.getRevisionReasonError().trim().equals(""))
			   this.setDisplayMessage(this.getRevisionReasonError().trim());	
			   
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
	/**
	 * @return Returns the saveButton.
	 */
	public String getSaveButton() {
		return saveButton;
	}
	/**
	 * @param saveButton The saveButton to set.
	 */
	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getRevisionReason() {
		return revisionReason;
	}
	public void setRevisionReason(String revisionReason) {
		this.revisionReason = revisionReason;
	}
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getRevisionUser() {
		return revisionUser;
	}
	public void setRevisionUser(String revisionUser) {
		this.revisionUser = revisionUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOriginalStatus() {
		return originalStatus;
	}
	public void setOriginalStatus(String originalStatus) {
		this.originalStatus = originalStatus;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodDescription() {
		return methodDescription;
	}
	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
	}
	public String getMethodNumber() {
		return methodNumber;
	}
	public void setMethodNumber(String methodNumber) {
		this.methodNumber = methodNumber;
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
	public BeanQuality getUpdBean() {
		return updBean;
	}
	public void setUpdBean(BeanQuality updBean) {
		this.updBean = updBean;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (7/20/2010 TWalton)
	 */
	public void loadFromBeanQuality(BeanQuality bq) {
		try {
			QaMethod header 					= bq.getMethod();
			this.methodNumber  					= header.getMethodNumber();
			this.revisionDate   				= header.getRevisionDate();
			this.revisionTime   				= header.getRevisionTime();
			this.setCompany(header.getCompanyNumber());
			this.setDivision(header.getDivisionNumber());
			this.status 						= header.getStatusCode(); 
			this.originalStatus					= header.getStatusCode();
			this.methodName						= header.getMethodName();
			this.methodDescription 				= header.getMethodDescription();
			this.revisionReason 				= header.getRevisionReasonText();
			this.recordType						= header.getTypeCode();
			this.originationUser				= header.getOriginationUser();
			this.groupCode						= header.getGroupingCode();
			this.scopeCode						= header.getScopeCode();
			this.approvedByUser					= header.getApprovedByUser();
			
			this.creationDate 					= header.getCreatedDate();
			this.creationTime 					= header.getCreatedTime();
			this.creationUser 					= header.getCreatedUser();
			
			this.updateDate						= header.getUpdatedDate();
			this.updateTime						= header.getUpdatedTime();
			this.updateUser						= header.getUpdatedUser();
			
		} catch (Exception e) {
		   System.out.println("Error Caught in UpdQAMethod.loadFromBeanQuality(BeanQuality: " + e);
		}
		return;
	}
	public Vector getListAdditional() {
		return listAdditional;
	}
	public void setListAdditional(Vector listAdditional) {
		this.listAdditional = listAdditional;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
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
	public Vector getListDefinitions() {
		return listDefinitions;
	}
	public void setListDefinitions(Vector listDefinitions) {
		this.listDefinitions = listDefinitions;
	}
	public Vector getListAuthorization() {
		return listAuthorization;
	}
	public void setListAuthorization(Vector listAuthorization) {
		this.listAuthorization = listAuthorization;
	}
	public Vector getListEquipmentUrl() {
		return listEquipmentUrl;
	}
	public void setListEquipmentUrl(Vector listEquipmentUrl) {
		this.listEquipmentUrl = listEquipmentUrl;
	}
	public Vector getListTheoryUrl() {
		return listTheoryUrl;
	}
	public void setListTheoryUrl(Vector listTheoryUrl) {
		this.listTheoryUrl = listTheoryUrl;
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
	public String getApprovedByUser() {
		return approvedByUser;
	}
	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
	}	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getScopeCode() {
		return scopeCode;
	}
	public void setScopeCode(String scopeCode) {
		this.scopeCode = scopeCode;
	}
	public String getOriginationUser() {
		return originationUser;
	}
	public void setOriginationUser(String originationUser) {
		this.originationUser = originationUser;
	}
	public String getRevisionReasonError() {
		return revisionReasonError;
	}
	public void setRevisionReasonError(String revisionReasonError) {
		this.revisionReasonError = revisionReasonError;
	}
	public DateTime getSupersedesDate() {
		return supersedesDate;
	}
	public void setSupersedesDate(DateTime supersedesDate) {
		this.supersedesDate = supersedesDate;
	}
}
