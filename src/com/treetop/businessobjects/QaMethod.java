/*
 * Created on June 1, 2010 
 */

package com.treetop.businessobjects;

/**
 * @author deisen
 *
 * Contains quality specification method header information.
 * 	
 */

public class QaMethod {
								// from File 	dbprd/QAPKMEHD
								//              dbprd/QAPMDESC
	
	protected	String			companyNumber			= "";	//MHCONO
	protected	String			divisionNumber			= "";	//MHDIVI
	protected	String			statusCode				= "";	//MHSTAT
	protected	String			statusDescription		= "";	//DCTEXT
	protected	String			typeCode				= "";	//MHTYPE
	protected	String			typeDescription			= "";	//DCTEXT
	protected	String			originationUser			= "";	//MHORIG
	protected	String			groupingCode			= "";	//MHGRUP
	protected	String			groupingDescription		= "";	//DCTEXT
	protected	String			scopeCode				= "";   //MHSCOP
	protected	String			scopeDescription		= "";	//DCTEXT
	protected	String			methodNumber			= "";	//MHMENO
	protected	String			revisionDate			= "";	//MHRDTE
	protected	String			revisionTime			= "";	//MHRTME
	protected	String			methodName				= "";	//MHNAME
	protected	String			methodDescription		= "";	//MHDESC
	protected	String			revisionReasonText		= "";	//MHRTXT
	protected	String			updatedDate				= "";	//MHUDTE
	protected	String			updatedTime				= "";	//MHUTME
	protected	String			updatedUser				= "";	//MHUUSR
	protected	String			createdDate				= "";	//MHCDTE
	protected	String			createdTime				= "";	//MHCTME
	protected	String			createdUser				= "";	//MHCUSR
	protected	String			supercededDate			= "";	//MHSDTE
	protected	String			supercededTime			= "";	//MHSTME
	protected	String			approvedByUser			= "";   //MHAUSR
	
	/**
	 *  // Constructor
	 */
	public QaMethod() {
		super();
	}

	/**
	 * @return Returns the company number.
	 */
	public String getCompanyNumber() {
		return companyNumber;
	}
	/**
	 * @param Sets the company number.
	 */
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	/**
	 * @return Returns the division number.
	 */
	public String getDivisionNumber() {
		return divisionNumber;
	}
	/**
	 * @param Sets the division number.
	 */
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}
	/**
	 * @return Returns the status code.
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param Sets the status code.
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return Returns the status description.
	 */
	public String getStatusDescription() {
		return statusDescription;
	}
	/**
	 * @param Sets the status description.
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	/**
	 * @return Returns the type code.
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @param Sets the type code.
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @return Returns the type description.
	 */
	public String getTypeDescription() {
		return typeDescription;
	}
	/**
	 * @param Sets the type description.
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	/**
	 * @return Returns the method number.
	 */
	public String getMethodNumber() {
		return methodNumber;
	}
	/**
	 * @param Sets the method number.
	 */
	public void setMethodNumber(String methodNumber) {
		this.methodNumber = methodNumber;
	}
	/**
	 * @return Returns the method revision date.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param Sets the method revision date.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the method revision time.
	 */
	public String getRevisionTime() {
		return revisionTime;
	}
	/**
	 * @param Sets the method revision time.
	 */
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	/**
	 * @return Returns the method name (reference).
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param Sets the method name (reference).
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return Returns the method description text.
	 */
	public String getMethodDescription() {
		return methodDescription;
	}
	/**
	 * @param Sets the method description text.
	 */
	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
	}
	/**
	 * @return Returns the revision reason text.
	 */
	public String getRevisionReasonText() {
		return revisionReasonText;
	}
	/**
	 * @param Sets the revision reason text.
	 */
	public void setRevisionReasonText(String revisionReasonText) {
		this.revisionReasonText = revisionReasonText;
	}
	/**
	 * @return Returns the last update date.
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param Sets the last update date.
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	/**
	 * @return Returns the last update time.
	 */
	public String getUpdatedTime() {
		return updatedTime;
	}
	/**
	 * @param Sets the last update time.
	 */
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	/**
	 * @return Returns the last update user profile.
	 */
	public String getUpdatedUser() {
		return updatedUser;
	}
	/**
	 * @param Sets the last update user profile.
	 */
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	/**
	 * @return Returns the original creation date.
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param Sets the original creation date.
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return Returns the original creation time.
	 */
	public String getCreatedTime() {
		return createdTime;
	}
	/**
	 * @param Sets the original creation time.
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @return Returns the original created by user profile.
	 */
	public String getCreatedUser() {
		return createdUser;
	}
	/**
	 * @param Sets the original created by user profile.
	 */
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	/**
	 * @return Returns the superceded date.
	 */
	public String getSupercededDate() {
		return supercededDate;
	}
	/**
	 * @param Sets the superceded date.
	 */
	public void setSupercededDate(String supercededDate) {
		this.supercededDate = supercededDate;
	}
	/**
	 * @return Returns the superceded time.
	 */
	public String getSupercededTime() {
		return supercededTime;
	}
	/**
	 * @param Sets the superceded time.
	 */
	public void setSupercededTime(String supercededTime) {
		this.supercededTime = supercededTime;
	}

	public String getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
	}

	public String getGroupingCode() {
		return groupingCode;
	}

	public void setGroupingCode(String groupingCode) {
		this.groupingCode = groupingCode;
	}

	public String getGroupingDescription() {
		return groupingDescription;
	}

	public void setGroupingDescription(String groupingDescription) {
		this.groupingDescription = groupingDescription;
	}

	public String getScopeCode() {
		return scopeCode;
	}

	public void setScopeCode(String scopeCode) {
		this.scopeCode = scopeCode;
	}

	public String getScopeDescription() {
		return scopeDescription;
	}

	public void setScopeDescription(String scopeDescription) {
		this.scopeDescription = scopeDescription;
	}

	public String getOriginationUser() {
		return originationUser;
	}

	public void setOriginationUser(String originationUser) {
		this.originationUser = originationUser;
	}
	
}