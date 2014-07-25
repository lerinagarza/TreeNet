/*
 * Created on June 26, 2009
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with PPS370MI 
 * 			Method:  AddLine -- Add a line, use with AddHead
 */
public class PPS370MIAddLine {

	// Additional Information
	protected	String		sentFromProgram						= "";
	protected	String		environment							= "";
	protected	String		userProfile							= "";
	
	// API IN Fields
	protected	String		messageNumber						= "";
	protected	String		purchaseOrderNumber					= "";
	protected	String		purchaseOrderHeadReference 			= "";
	protected	String		purchaseOrderLine					= "";
	protected	String		purchaseOrderLineReference			= "";
	protected	String		itemNumber							= "";
	protected	String		orderQuantityAltUOM					= "";
	protected	String		facility							= "";
	protected	String		warehouse				 			= "";
	protected	String		supplierNumber						= "";
	protected	String		requestedDeliveryDate				= "";
	protected	String		supplierItemNumber					= "";
	protected	String		purchaseOrderItemName				= "";
	protected	String		purchaseOrderItemDescripion			= "";
	protected	String		manufacturer 						= "";
	protected	String		revisionNumber1						= "";
	protected	String		revisionNumber2						= "";
	protected	String		externalInstruction					= "";
	protected	String		purchasePrice						= "";
	protected	String		discount1							= "";
	protected	String		discount2				 			= "";
	protected	String		discount3							= "";
	protected	String		purchaseOrderUOM					= "";
	protected	String		purchasePriceUOM					= "";
	protected	String		purchasePriceQuantity				= "";
	protected	String		purchasePriceText					= "";
	protected	String		referenceOrderCategory	 			= "";
	protected	String		referenceOrderNumber				= "";
	protected	String		referenceOrderLine					= "";
	protected	String		lineSuffix							= "";
	protected	String		ourReferenceNumber					= "";
	protected	String		referenceType						= "";
	protected	String		priority				 			= "";
	protected	String		monitoringActivityList				= "";
	protected	String		requisitonBy						= "";
	protected	String		buyer								= "";
	protected	String		technicalSupervisor					= "";
	protected	String		goodsReceivingMethod				= "";
	protected	String		recipient				 			= "";
	protected	String		packaging							= "";
	protected	String		vatCode								= "";
	protected	String		userDefinedAccountingControlObject	= "";
	protected	String		costCenter							= "";
	protected	String		customsStatisticalNumber			= "";
	protected	String		laborCodeTradeStatistics		 	= "";
	protected	String		businessTypeTradeStatistics			= "";
	protected	String		projectNumber						= "";
	protected	String		projectElement						= "";
	protected	String		customsProcedureImport				= "";
	protected	String		harborOrAirport						= "";
	protected	String		taxCodeCustomerAddress			 	= "";
	protected	String		timeHoursAndMinutes					= "";
	protected	String		milestoneChain						= "";
	protected	String		unpack								= "";
	protected	String		userDefinedField1					= "";
	protected	String		userDefinedField2					= "";
	protected	String		userDefinedField3					= "";
	protected	String		userDefinedField4					= "";
	protected	String		userDefinedField5					= "";
	
	// API OUT Fields
	protected 	String		purchaseOrderNumberOUT				= "";
	protected	String		purchaseOrderLineOUT 				= "";
	protected	String		errorMessage						= "";
		
   /**
	* Basic constructor.
	*/
	public PPS370MIAddLine() {
		super();
	}
	/**
	 * @return Returns the sentFromProgram.
	 */
	public String getSentFromProgram() {
		return sentFromProgram;
	}
	/**
	 * @param sentFromProgram The sentFromProgram to set.
	 */
	public void setSentFromProgram(String sentFromProgram) {
		this.sentFromProgram = sentFromProgram;
	}
	/**
	 * @return Returns the environment.
	 */
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param environment The environment to set.
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	/**
	 * @return Returns the userProfile.
	 */
	public String getUserProfile() {
		return userProfile;
	}
	/**
	 * @param userProfile The userProfile to set.
	 */
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getMessageNumber() {
		return messageNumber;
	}
	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getBusinessTypeTradeStatistics() {
		return businessTypeTradeStatistics;
	}
	public void setBusinessTypeTradeStatistics(String businessTypeTradeStatistics) {
		this.businessTypeTradeStatistics = businessTypeTradeStatistics;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getDiscount1() {
		return discount1;
	}
	public void setDiscount1(String discount1) {
		this.discount1 = discount1;
	}
	public String getDiscount2() {
		return discount2;
	}
	public void setDiscount2(String discount2) {
		this.discount2 = discount2;
	}
	public String getDiscount3() {
		return discount3;
	}
	public void setDiscount3(String discount3) {
		this.discount3 = discount3;
	}
	public String getExternalInstruction() {
		return externalInstruction;
	}
	public void setExternalInstruction(String externalInstruction) {
		this.externalInstruction = externalInstruction;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public String getGoodsReceivingMethod() {
		return goodsReceivingMethod;
	}
	public void setGoodsReceivingMethod(String goodsReceivingMethod) {
		this.goodsReceivingMethod = goodsReceivingMethod;
	}
	public String getHarborOrAirport() {
		return harborOrAirport;
	}
	public void setHarborOrAirport(String harborOrAirport) {
		this.harborOrAirport = harborOrAirport;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getLaborCodeTradeStatistics() {
		return laborCodeTradeStatistics;
	}
	public void setLaborCodeTradeStatistics(String laborCodeTradeStatistics) {
		this.laborCodeTradeStatistics = laborCodeTradeStatistics;
	}
	public String getLineSuffix() {
		return lineSuffix;
	}
	public void setLineSuffix(String lineSuffix) {
		this.lineSuffix = lineSuffix;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getMilestoneChain() {
		return milestoneChain;
	}
	public void setMilestoneChain(String milestoneChain) {
		this.milestoneChain = milestoneChain;
	}
	public String getMonitoringActivityList() {
		return monitoringActivityList;
	}
	public void setMonitoringActivityList(String monitoringActivityList) {
		this.monitoringActivityList = monitoringActivityList;
	}
	public String getOrderQuantityAltUOM() {
		return orderQuantityAltUOM;
	}
	public void setOrderQuantityAltUOM(String orderQuantityAltUOM) {
		this.orderQuantityAltUOM = orderQuantityAltUOM;
	}
	public String getOurReferenceNumber() {
		return ourReferenceNumber;
	}
	public void setOurReferenceNumber(String ourReferenceNumber) {
		this.ourReferenceNumber = ourReferenceNumber;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getProjectElement() {
		return projectElement;
	}
	public void setProjectElement(String projectElement) {
		this.projectElement = projectElement;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getPurchaseOrderHeadReference() {
		return purchaseOrderHeadReference;
	}
	public void setPurchaseOrderHeadReference(String purchaseOrderHeadReference) {
		this.purchaseOrderHeadReference = purchaseOrderHeadReference;
	}
	public String getPurchaseOrderItemDescripion() {
		return purchaseOrderItemDescripion;
	}
	public void setPurchaseOrderItemDescripion(String purchaseOrderItemDescripion) {
		this.purchaseOrderItemDescripion = purchaseOrderItemDescripion;
	}
	public String getPurchaseOrderItemName() {
		return purchaseOrderItemName;
	}
	public void setPurchaseOrderItemName(String purchaseOrderItemName) {
		this.purchaseOrderItemName = purchaseOrderItemName;
	}
	public String getPurchaseOrderLine() {
		return purchaseOrderLine;
	}
	public void setPurchaseOrderLine(String purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
	}
	public String getPurchaseOrderLineOUT() {
		return purchaseOrderLineOUT;
	}
	public void setPurchaseOrderLineOUT(String purchaseOrderLineOUT) {
		this.purchaseOrderLineOUT = purchaseOrderLineOUT;
	}
	public String getPurchaseOrderLineReference() {
		return purchaseOrderLineReference;
	}
	public void setPurchaseOrderLineReference(String purchaseOrderLineReference) {
		this.purchaseOrderLineReference = purchaseOrderLineReference;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getPurchaseOrderNumberOUT() {
		return purchaseOrderNumberOUT;
	}
	public void setPurchaseOrderNumberOUT(String purchaseOrderNumberOUT) {
		this.purchaseOrderNumberOUT = purchaseOrderNumberOUT;
	}
	public String getPurchaseOrderUOM() {
		return purchaseOrderUOM;
	}
	public void setPurchaseOrderUOM(String purchaseOrderUOM) {
		this.purchaseOrderUOM = purchaseOrderUOM;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getPurchasePriceQuantity() {
		return purchasePriceQuantity;
	}
	public void setPurchasePriceQuantity(String purchasePriceQuantity) {
		this.purchasePriceQuantity = purchasePriceQuantity;
	}
	public String getPurchasePriceText() {
		return purchasePriceText;
	}
	public void setPurchasePriceText(String purchasePriceText) {
		this.purchasePriceText = purchasePriceText;
	}
	public String getPurchasePriceUOM() {
		return purchasePriceUOM;
	}
	public void setPurchasePriceUOM(String purchasePriceUOM) {
		this.purchasePriceUOM = purchasePriceUOM;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getReferenceOrderCategory() {
		return referenceOrderCategory;
	}
	public void setReferenceOrderCategory(String referenceOrderCategory) {
		this.referenceOrderCategory = referenceOrderCategory;
	}
	public String getReferenceOrderLine() {
		return referenceOrderLine;
	}
	public void setReferenceOrderLine(String referenceOrderLine) {
		this.referenceOrderLine = referenceOrderLine;
	}
	public String getReferenceOrderNumber() {
		return referenceOrderNumber;
	}
	public void setReferenceOrderNumber(String referenceOrderNumber) {
		this.referenceOrderNumber = referenceOrderNumber;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public String getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	public void setRequestedDeliveryDate(String requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}
	public String getRequisitonBy() {
		return requisitonBy;
	}
	public void setRequisitonBy(String requisitonBy) {
		this.requisitonBy = requisitonBy;
	}
	public String getRevisionNumber1() {
		return revisionNumber1;
	}
	public void setRevisionNumber1(String revisionNumber1) {
		this.revisionNumber1 = revisionNumber1;
	}
	public String getRevisionNumber2() {
		return revisionNumber2;
	}
	public void setRevisionNumber2(String revisionNumber2) {
		this.revisionNumber2 = revisionNumber2;
	}
	public String getSupplierItemNumber() {
		return supplierItemNumber;
	}
	public void setSupplierItemNumber(String supplierItemNumber) {
		this.supplierItemNumber = supplierItemNumber;
	}
	public String getSupplierNumber() {
		return supplierNumber;
	}
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}
	public String getTaxCodeCustomerAddress() {
		return taxCodeCustomerAddress;
	}
	public void setTaxCodeCustomerAddress(String taxCodeCustomerAddress) {
		this.taxCodeCustomerAddress = taxCodeCustomerAddress;
	}
	public String getTechnicalSupervisor() {
		return technicalSupervisor;
	}
	public void setTechnicalSupervisor(String technicalSupervisor) {
		this.technicalSupervisor = technicalSupervisor;
	}
	public String getTimeHoursAndMinutes() {
		return timeHoursAndMinutes;
	}
	public void setTimeHoursAndMinutes(String timeHoursAndMinutes) {
		this.timeHoursAndMinutes = timeHoursAndMinutes;
	}
	public String getUnpack() {
		return unpack;
	}
	public void setUnpack(String unpack) {
		this.unpack = unpack;
	}
	public String getUserDefinedAccountingControlObject() {
		return userDefinedAccountingControlObject;
	}
	public void setUserDefinedAccountingControlObject(
			String userDefinedAccountingControlObject) {
		this.userDefinedAccountingControlObject = userDefinedAccountingControlObject;
	}
	public String getUserDefinedField1() {
		return userDefinedField1;
	}
	public void setUserDefinedField1(String userDefinedField1) {
		this.userDefinedField1 = userDefinedField1;
	}
	public String getUserDefinedField2() {
		return userDefinedField2;
	}
	public void setUserDefinedField2(String userDefinedField2) {
		this.userDefinedField2 = userDefinedField2;
	}
	public String getUserDefinedField3() {
		return userDefinedField3;
	}
	public void setUserDefinedField3(String userDefinedField3) {
		this.userDefinedField3 = userDefinedField3;
	}
	public String getUserDefinedField4() {
		return userDefinedField4;
	}
	public void setUserDefinedField4(String userDefinedField4) {
		this.userDefinedField4 = userDefinedField4;
	}
	public String getUserDefinedField5() {
		return userDefinedField5;
	}
	public void setUserDefinedField5(String userDefinedField5) {
		this.userDefinedField5 = userDefinedField5;
	}
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getCustomsProcedureImport() {
		return customsProcedureImport;
	}
	public void setCustomsProcedureImport(String customsProcedureImport) {
		this.customsProcedureImport = customsProcedureImport;
	}
	public String getCustomsStatisticalNumber() {
		return customsStatisticalNumber;
	}
	public void setCustomsStatisticalNumber(String customsStatisticalNumber) {
		this.customsStatisticalNumber = customsStatisticalNumber;
	}
}
