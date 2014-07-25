/*
 * Created on May 25, 2010 
 */

package com.treetop.businessobjects;

/**
 * @author deisen
 *
 * Contains quality specification formula header information.
 * 	
 */

public class QaFormula {
								// from File 	dbprd/QAPIFOHD
								//              dbprd/QAPMDESC
	
	protected	String			companyNumber			= "";	//FHCONO
	protected	String			divisionNumber			= "";	//FHDIVI
	protected	String			statusCode				= "";	//FHSTAT
	protected	String			statusDescription		= "";	//DCTEXT
	protected	String			typeCode				= "";	//FHTYPE
	protected	String			typeDescription			= "";	//DCTEXT
	protected	String			originationUser			= "";	//FHOHSR
	protected	String			groupingCode			= "";	//FHGRUP
	protected	String			groupingDescription		= "";	//DCTEXT
	protected	String			scopeCode				= "";   //FHSCOP
	protected	String			scopeDescription		= "";	//DCTEXT
	protected	String			formulaNumber			= "";	//FHFONO
	protected	String			revisionDate			= "";	//FHRDTE
	protected	String			revisionTime			= "";	//FHRTME
	protected	String			formulaName				= "";	//FHNAME
	protected	String			formulaDescription		= "";	//FHDESC
	protected	String			lineTankItem			= "";	//FHLTNO
	protected	String			lineTankItemDescription = "";  // from MITMAS MMITDS
	protected   String    		batchQuantity			= "";	//FHQTTY
	protected	String			batchUnitOfMeasure	 	= "";	//FHUNMS
	protected	String			targetBrix 			 	= "";	//FHBRIX
	protected	String			customerNumber			= "";	//FHCUNO
	protected	String			customerName			= "";	//FHCUNM
	protected	String			customerCode			= "";	//FHCUCD
	protected	String			customerOrSupplierItemNumber = "";	//FHCUIT
	protected	String			referenceFormulaNumber	= "";	//FHRFNO
	protected	String			referenceFormulaRevDate	= "";	//FHRFDT
	protected	String			referenceFormulaRevTime	= "";	//FHRFTM
	protected	String			productionStatus		= "";	//FHPDST	
	protected	String			revisionReasonText		= "";	//FHRTXT	
	protected	String			updatedDate				= "";	//FHUDTE
	protected	String			updatedTime				= "";	//FHUTME
	protected	String			updatedUser				= "";	//FHUUSR
	protected	String			createdDate				= "";	//FHCDTE
	protected	String			createdTime				= "";	//FHCTME
	protected	String			createdUser				= "";	//FHCUSR
	protected	String			supercededDate			= "";	//FHSDTE
	protected	String			supercededTime			= "";	//FHSTME
	protected	String			approvedByUser			= "";	//FHAUSR
	protected	String			fruitOrigin				= "";	//FHORIG
	protected	String			batchQuantityPreBlend	= "";	//FHPQTY
	protected	String			batchUOMPreBlend		= "";   //FHPUOM
	
	/**
	 *  // Constructor
	 */
	public QaFormula() {
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
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param Sets the formula revision date.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the formula revision time.
	 */
	public String getRevisionTime() {
		return revisionTime;
	}
	/**
	 * @param Sets the formula revision time.
	 */
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	/**
	 * @return Returns the line tank item number.
	 */
	public String getLineTankItem() {
		return lineTankItem;
	}
	/**
	 * @param Sets the line tank item number.
	 */
	public void setLineTankItem(String lineTankItem) {
		this.lineTankItem = lineTankItem;
	}
	/**
	 * @return Returns the batch quantity.
	 */
	public String getBatchQuantity() {
		return batchQuantity;
	}
	/**
	 * @param Sets the batch quantity.
	 */
	public void setBatchQuantity(String batchQuantity) {
		this.batchQuantity = batchQuantity;
	}
	/**
	 * @return Returns the batch unit of measure.
	 */
	public String getBatchUnitOfMeasure() {
		return batchUnitOfMeasure;
	}
	/**
	 * @param Sets the batch unit of measure.
	 */
	public void setBatchUnitOfMeasure(String batchUnitOfMeasure) {
		this.batchUnitOfMeasure = batchUnitOfMeasure;
	}
	/**
	 * @return Returns the target degree of brix.
	 */
	public String getTargetBrix() {
		return targetBrix;
	}
	/**
	 * @param Sets the target degree of brix.
	 */
	public void setTargetBrix(String targetBrix) {
		this.targetBrix = targetBrix;
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
	 * @return Returns the referenced formula number.
	 */
	public String getReferenceFormulaNumber() {
		return referenceFormulaNumber;
	}
	/**
	 * @param Sets the referenced formula number.
	 */
	public void setReferenceFormulaNumber(String referenceFormulaNumber) {
		this.referenceFormulaNumber = referenceFormulaNumber;
	}
	/**
	 * @return Returns the referenced formula revision date.
	 */
	public String getReferenceFormulaRevDate() {
		return referenceFormulaRevDate;
	}
	/**
	 * @param Sets the referenced formula revision date.
	 */
	public void setReferenceFormulaRevDate(String referenceFormulaRevDate) {
		this.referenceFormulaRevDate = referenceFormulaRevDate;
	}
	/**
	 * @return Returns the referenced formula revision time.
	 */
	public String getReferenceFormulaRevTime() {
		return referenceFormulaRevTime;
	}
	/**
	 * @param Sets the referenced formula revision time.
	 */
	public void setReferenceFormulaRevTime(String referenceFormulaRevTime) {
		this.referenceFormulaRevTime = referenceFormulaRevTime;
	}
	/**
	 * @return Returns the formula description text.
	 */
	public String getFormulaDescription() {
		return formulaDescription;
	}
	/**
	 * @param Sets the formula description text.
	 */
	public void setFormulaDescription(String formulaDescription) {
		this.formulaDescription = formulaDescription;
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

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public String getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
	}

	public String getBatchQuantityPreBlend() {
		return batchQuantityPreBlend;
	}

	public void setBatchQuantityPreBlend(String batchQuantityPreBlend) {
		this.batchQuantityPreBlend = batchQuantityPreBlend;
	}

	public String getBatchUOMPreBlend() {
		return batchUOMPreBlend;
	}

	public void setBatchUOMPreBlend(String batchUOMPreBlend) {
		this.batchUOMPreBlend = batchUOMPreBlend;
	}

	public String getFruitOrigin() {
		return fruitOrigin;
	}

	public void setFruitOrigin(String fruitOrigin) {
		this.fruitOrigin = fruitOrigin;
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

	public String getLineTankItemDescription() {
		return lineTankItemDescription;
	}

	public void setLineTankItemDescription(String lineTankItemDescription) {
		this.lineTankItemDescription = lineTankItemDescription;
	}

	public String getCustomerOrSupplierItemNumber() {
		return customerOrSupplierItemNumber;
	}

	public void setCustomerOrSupplierItemNumber(String customerOrSupplierItemNumber) {
		this.customerOrSupplierItemNumber = customerOrSupplierItemNumber;
	}
	
}