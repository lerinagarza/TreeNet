/*
 * Created on June 5, 2009
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with PPS370MI 
 * 			Method:  AddHead -- Add a Header Record with All the Information
 */
public class PPS370MIAddHead {

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		userProfile					= "";
	
	// API IN Fields
	protected	String		messageNumber				= "";
	protected	String		facility					= "";
	protected	String		warehouse					= "";
	protected	String		supplierNumber				= "";
	protected	String		requestedDeliveryDate		= "";
	protected	String		purchaseOrderHeadReference	= "";
	protected	String		orderType					= "";
	protected	String		communicationCode			= "";
	protected	String		orderDate					= "";
	protected	String		language					= "GB";
	protected	String		currency					= "USD";
	protected	String		paymentTerms				= "";
	protected	String		paymentMethodAP				= "";
	protected	String		deliveryMethod				= "";
	protected	String		deliveryTerms				= "";
	protected	String		freightTerms				= "";
	protected	String		packagingTerms				= "";
	protected	String		yourReference				= "";
	protected	String		payee						= "";
	protected	String		ourReferenceNumber			= "";
	protected	String		referenceType				= "";
	protected	String		recipientAgreementType1		= "";
	protected	String		requisitionBy				= "";
	protected	String		buyer						= "";
	protected	String		monitoringActivityList		= "";
	protected	String		facimileTransmissionNumber	= "";
	protected	String		lastReplyDate				= "";
	protected	String		termsText					= "";
	protected	String		dueDate						= "";
	protected	String		currencyTerms				= "";
	protected	String		agreedRate					= "";
	protected	String		projectNumber				= "";
	protected	String		projectElement				= "";
	protected	String		harborOrAirport				= "";
	protected	String		userDefined1				= "";
	protected	String		userDefined2				= "";
	protected	String		userDefined3				= "";
	protected	String		userDefined4				= "";
	protected	String		userDefined5				= "";
	
	// API OUT Fields
	protected 	String		purchaseOrderNumber			= "";
	protected	String		errorMessage				= "";
		
   /**
	* Basic constructor.
	*/
	public PPS370MIAddHead() {
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
	public String getAgreedRate() {
		return agreedRate;
	}
	public void setAgreedRate(String agreedRate) {
		this.agreedRate = agreedRate;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getCommunicationCode() {
		return communicationCode;
	}
	public void setCommunicationCode(String communicationCode) {
		this.communicationCode = communicationCode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyTerms() {
		return currencyTerms;
	}
	public void setCurrencyTerms(String currencyTerms) {
		this.currencyTerms = currencyTerms;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getDeliveryTerms() {
		return deliveryTerms;
	}
	public void setDeliveryTerms(String deliveryTerms) {
		this.deliveryTerms = deliveryTerms;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public String getFacimileTransmissionNumber() {
		return facimileTransmissionNumber;
	}
	public void setFacimileTransmissionNumber(String facimileTransmissionNumber) {
		this.facimileTransmissionNumber = facimileTransmissionNumber;
	}
	public String getFreightTerms() {
		return freightTerms;
	}
	public void setFreightTerms(String freightTerms) {
		this.freightTerms = freightTerms;
	}
	public String getHarborOrAirport() {
		return harborOrAirport;
	}
	public void setHarborOrAirport(String harborOrAirport) {
		this.harborOrAirport = harborOrAirport;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLastReplyDate() {
		return lastReplyDate;
	}
	public void setLastReplyDate(String lastReplyDate) {
		this.lastReplyDate = lastReplyDate;
	}
	public String getMonitoringActivityList() {
		return monitoringActivityList;
	}
	public void setMonitoringActivityList(String monitoringActivityList) {
		this.monitoringActivityList = monitoringActivityList;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOurReferenceNumber() {
		return ourReferenceNumber;
	}
	public void setOurReferenceNumber(String ourReferenceNumber) {
		this.ourReferenceNumber = ourReferenceNumber;
	}
	public String getPackagingTerms() {
		return packagingTerms;
	}
	public void setPackagingTerms(String packagingTerms) {
		this.packagingTerms = packagingTerms;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getPaymentMethodAP() {
		return paymentMethodAP;
	}
	public void setPaymentMethodAP(String paymentMethodAP) {
		this.paymentMethodAP = paymentMethodAP;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
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
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getRecipientAgreementType1() {
		return recipientAgreementType1;
	}
	public void setRecipientAgreementType1(String recipientAgreementType1) {
		this.recipientAgreementType1 = recipientAgreementType1;
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
	public String getRequisitionBy() {
		return requisitionBy;
	}
	public void setRequisitionBy(String requisitionBy) {
		this.requisitionBy = requisitionBy;
	}
	public String getSupplierNumber() {
		return supplierNumber;
	}
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}
	public String getTermsText() {
		return termsText;
	}
	public void setTermsText(String termsText) {
		this.termsText = termsText;
	}
	public String getUserDefined1() {
		return userDefined1;
	}
	public void setUserDefined1(String userDefined1) {
		this.userDefined1 = userDefined1;
	}
	public String getUserDefined2() {
		return userDefined2;
	}
	public void setUserDefined2(String userDefined2) {
		this.userDefined2 = userDefined2;
	}
	public String getUserDefined3() {
		return userDefined3;
	}
	public void setUserDefined3(String userDefined3) {
		this.userDefined3 = userDefined3;
	}
	public String getUserDefined4() {
		return userDefined4;
	}
	public void setUserDefined4(String userDefined4) {
		this.userDefined4 = userDefined4;
	}
	public String getUserDefined5() {
		return userDefined5;
	}
	public void setUserDefined5(String userDefined5) {
		this.userDefined5 = userDefined5;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getYourReference() {
		return yourReference;
	}
	public void setYourReference(String yourReference) {
		this.yourReference = yourReference;
	}
}
