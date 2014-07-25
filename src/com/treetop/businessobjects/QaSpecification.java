/*
 * Created on May 27, 2010 
 */

package com.treetop.businessobjects;

/**
 * @author deisen
 *
 * Contains quality specification header information.
 * 	// 11/16/11 TWalton -- adjusted to reflect new changes to the Spec System
 */
public class QaSpecification {
								// from File 	dbprd/QAPGSPHD
								//              dbprd/QAPMDESC	
								//              m3djdprd/MITMAS
								//				dbprd/QAPIFOHD
	
	protected	String			companyNumber			= "";	//SHCONO
	protected	String			divisionNumber			= "";	//SHDIVI
	protected	String			specificationNumber		= "";	//SHSPNO
	protected	String			revisionDate			= "";	//SHRDTE
	protected	String			revisionTime			= "";	//SHRTME
	protected	String			specificationName		= "";	//SHNAME
	protected	String			statusCode				= "";	//SHSTAT
	protected	String			statusDescription		= "";	//DCTEXT
	protected	String			typeCode				= "";	//SHTYPE
	protected	String			typeDescription			= "";	//DCTEXT
	protected	String			groupingCode			= "";	//SHGRUP
	protected	String			groupingDescription		= "";	//DCTEXT
	protected	String			scopeCode				= "";   //SHSCOP
	protected	String			scopeDescription		= "";	//DCTEXT
	protected	String			specificationDescription= "";	//SHDESC
	protected	String			originationUser			= "";	//SHOUSR
	protected	String			approvedByUser			= "";	//SHAUSR
	
	protected	String			itemNumber				= "";   //SHITNO
	protected	String			itemDescription 		= "";  // from MITMAS MMITDS
	protected	String			productionStatus		= "";	//SHPDST
	protected	String			revisionReasonText		= "";	//SHRTXT	
	protected	String			referenceSpecNumber		= "";	//SHRSNO
	protected	String			referenceSpecRevDate	= "";	//SHRSDT
	protected	String			referenceSpecRevTime	= "";	//SHRSTM
	protected	String			formulaNumber			= "";	//SHFONO
	protected	String			formulaRevisionDate		= "";	//SHFODT
	protected	String			formulaRevisionTime		= "";	//SHFOTM
	protected	String			formulaName				= "";	//FHNAME
	protected	String			formulaDescription		= "";	//FHDESC
	protected	String			customerNumber			= "";	//SHCUNO
	protected	String			customerName			= "";	//SHCUNM
	protected	String			customerCode			= "";	//SHCUCD
	
	protected	String			updatedDate				= "";	//SHUDTE
	protected	String			updatedTime				= "";	//SHUTME
	protected	String			updatedUser				= "";	//SHUUSR
	protected	String			createdDate				= "";	//SHCDTE
	protected	String			createdTime				= "";	//SHCTME
	protected	String			createdUser				= "";	//SHCUSR
	protected	String			supercededDate			= "";	//SHSDTE
	protected	String			supercededTime			= "";	//SHSTME
	
	protected 	String 			shelfLifeNotValid		= "";	//SHCORG
	protected 	String 			reconstitutionRatio		= "";	//SHRCON
	protected 	String 			countryOfOrigin			= "";	//SHSLCB
	
	
	/**
	 *  // Constructor
	 */
	public QaSpecification() {
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
	 * @return Returns the grouping code.
	 */
	public String getGroupingCode() {
		return groupingCode;
	}
	/**
	 * @param Sets the grouping code.
	 */
	public void setGroupingCode(String groupingCode) {
		this.groupingCode = groupingCode;
	}
	/**
	 * @return Returns the grouping description.
	 */
	public String getGroupingDescription() {
		return groupingDescription;
	}
	/**
	 * @param Sets the grouping description.
	 */
	public void setGroupingDescription(String groupingDescription) {
		this.groupingDescription = groupingDescription;
	}
	/**
	 * @return Returns the specification number.
	 */
	public String getSpecificationNumber() {
		return specificationNumber;
	}
	/**
	 * @param Sets the specification number.
	 */
	public void setSpecificationNumber(String specificationNumber) {
		this.specificationNumber = specificationNumber;
	}
	/**
	 * @return Returns the specification revision date.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param Sets the specification revision date.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the specification revision time.
	 */
	public String getRevisionTime() {
		return revisionTime;
	}
	/**
	 * @param Sets the specification revision time.
	 */
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	/**
	 * @return Returns the formula number.
	 */
	public String getFormulaNumber() {
		return formulaNumber;
	}
	/**
	 * @param Sets the formula number.
	 */
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}
	/**
	 * @return Returns the formula revision date.
	 */
	public String getFormulaRevisionDate() {
		return formulaRevisionDate;
	}
	/**
	 * @param Sets the formula revision date.
	 */
	public void setFormualRevisionDate(String formulaRevisionDate) {
		this.formulaRevisionDate = formulaRevisionDate;
	}
	/**
	 * @return Returns the formula revision time.
	 */
	public String getFormulaRevisionTime() {
		return formulaRevisionTime;
	}
	/**
	 * @param Sets the formula revision time.
	 */
	public void setFormulaRevisionTime(String formulaRevisionTime) {
		this.formulaRevisionTime = formulaRevisionTime;
	}
	/**
	 * @return Returns the customer number.
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}
	/**
	 * @param Sets the customer number.
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	/**
	 * @return Returns the customer name.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param Sets the customer name.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return Returns the customer code.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param Sets the customer code.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the referenced specification number.
	 */
	public String getReferenceSpecNumber() {
		return referenceSpecNumber;
	}
	/**
	 * @param Sets the referenced specification number.
	 */
	public void setReferenceSpecNumber(String referenceSpecNumber) {
		this.referenceSpecNumber = referenceSpecNumber;
	}
	/**
	 * @return Returns the referenced formula revision date.
	 */
	public String getReferenceSpecRevisionDate() {
		return referenceSpecRevDate;
	}
	/**
	 * @param Sets the referenced formula revision date.
	 */
	public void setReferenceSpecRevDate(String referenceSpecRevDate) {
		this.referenceSpecRevDate = referenceSpecRevDate;
	}
	/**
	 * @return Returns the referenced formula revision time.
	 */
	public String getReferenceSpecRevTime() {
		return referenceSpecRevTime;
	}
	/**
	 * @param Sets the referenced formula revision time.
	 */
	public void setReferenceSpecRevTime(String referenceSpecRevTime) {
		this.referenceSpecRevTime = referenceSpecRevTime;
	}
	/**
	 * @return Returns the specification description text.
	 */
	public String getSpecificationDescription() {
		return specificationDescription;
	}
	/**
	 * @param Sets the specification description text.
	 */
	public void setSpecificationDescription(String specificationDescription) {
		this.specificationDescription = specificationDescription;
	}
	/**
	 * @return Returns the production status description.
	 */
	public String getProductionStatus() {
		return productionStatus;
	}
	/**
	 * @param Sets the production status description.
	 */
	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus;
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
	/**
	 * @param Sets the original created by user profile.
	 */
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getFormulaDescription() {
		return formulaDescription;
	}

	public void setFormulaDescription(String formulaDescription) {
		this.formulaDescription = formulaDescription;
	}

	public String getReferenceSpecRevDate() {
		return referenceSpecRevDate;
	}

	public void setFormulaRevisionDate(String formulaRevisionDate) {
		this.formulaRevisionDate = formulaRevisionDate;
	}

	public String getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
	}

	public String getOriginationUser() {
		return originationUser;
	}

	public void setOriginationUser(String originationUser) {
		this.originationUser = originationUser;
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

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getShelfLifeNotValid() {
		return shelfLifeNotValid;
	}

	public void setShelfLifeNotValid(String shelfLifeNotValid) {
		this.shelfLifeNotValid = shelfLifeNotValid;
	}

	public String getReconstitutionRatio() {
		return reconstitutionRatio;
	}

	public void setReconstitutionRatio(String reconstitutionRatio) {
		this.reconstitutionRatio = reconstitutionRatio;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	
}