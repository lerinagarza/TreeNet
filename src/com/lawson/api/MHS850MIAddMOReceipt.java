/*
 * Created on July 15, 2011
 */
package com.lawson.api;

/**
 * @author David Eisenheim
 *
 * Use in Conjunction with MHS850MI 
 * 			Method:  addMOReceipt 
 */

public class MHS850MIAddMOReceipt{

	// Additional information
	protected	String		sentFromProgram		= "";
	protected	String		environment			= "";
	protected	String		userProfile			= "";
	
	// API transaction fields
	protected	String		process				= "*EXE";
	protected	String		company				= "";	
	protected	String		warehouse			= "";	
	protected	String		partner				= "WHI";
	protected	String		messageType			= "WHI";		
	protected	String		itemNumber			= "";
	protected	String		location			= "";
	protected	String		lotNumber			= "";
	protected	String		quantityReceived	= "";
	protected	String		orderNumber			= "";
	protected	String		lotReference1		= "";
	protected	String		lotReference2		= "";
	protected	String		reportingDate		= "";
	protected	String		reportingTime		= "";
	
		
   /**
	* Basic constructor.
	*/
	public MHS850MIAddMOReceipt() {
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
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
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
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getQuantityReceived() {
		return quantityReceived;
	}
	public void setQuantityReceived(String quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	public String getReportingDate() {
		return reportingDate;
	}
	public void setReportingDate(String reportingDate) {
		this.reportingDate = reportingDate;
	}
	public String getReportingTime() {
		return reportingTime;
	}
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}
	
}