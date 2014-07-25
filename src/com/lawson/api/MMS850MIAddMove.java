/*
 * Created on July 28, 2011
 */
package com.lawson.api;

/**
 * @author David Eisenheim
 *
 * Use in Conjunction with MMS850MI 
 * 			Method:  addMove 
 */

public class MMS850MIAddMove {

	// Additional information
	protected	String		sentFromProgram		= "";
	protected	String		environment			= "";
	protected	String		userProfile			= "";
	
	// API transaction fields
	protected	String		process				= "*EXE";
	protected	String		company				= "";
	protected	String		transactionDate		= "";
	protected	String		transactionTime		= "";	
	protected	String		partner				= "WHI";
	protected	String		messageType			= "WHI";
	protected	String		warehouse			= "";
	protected	String		warehouseLocation	= "";	
	protected	String		itemNumber			= "";	
	protected	String		lotNumber			= "";
	protected	String		receivingNumber     = "";
	protected	String		quantity			= "";	
	protected	String		toLocation	 		= "";
	protected	String		remark		 		= "";
	protected	String		lotReference1		= "";
	protected	String		lotReference2		= "";
	
	protected	String		response			= "";
	
		
   /**
	* Basic constructor.
	*/
	public MMS850MIAddMove() {
		super();
	}	
	
	public String getSentFromProgram() {
		return sentFromProgram;
	}
	public void setSentFromProgram(String sentFromProgram) {
		this.sentFromProgram = sentFromProgram;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}	
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}			
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;		
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}
	public String getWarehouseLocation() {
		return warehouseLocation;
	}	
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}	
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getReceivingNumber() {
		return receivingNumber;
	}
	public void setReceivingNumber(String receivingNumber) {
		this.receivingNumber = receivingNumber;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}	
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getToLocation() {
		return toLocation;
	}	
	public String getLotReference1() {
		return lotReference1;
	}
	public void setLotReference1(String lotReference1) {
		this.lotReference1 = lotReference1;
	}
	public String getLotReference2() {
		return lotReference2;
	}
	public void setLotReference2(String lotReference2) {
		this.lotReference2 = lotReference2;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}