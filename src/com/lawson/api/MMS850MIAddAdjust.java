/*
 * Created on February 21, 2012
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with MMS850MI 
 * 			Method:  addAdjust -- Adjust inventory ON and OFF -- Cycle count
 *                 Type 90 transaction in M3
 */
public class MMS850MIAddAdjust {

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		userProfile					= "";
	
	// API Fields
	protected	String		processFlag					= "*EXE";
	protected	String		company						= "";
	protected	String		dateGenerated				= "";
	protected	String		timeGenerated				= "";
	protected	String		partner						= "INBOUND";
	protected	String		messageType					= "ADDINV"; 
	protected	String		warehouse					= "";
	protected	String		location					= "";
	protected	String		itemNumber					= "";
	protected	String		lotNumber					= "";
	protected	String		quantity					= "";
	protected	String		unitOfMeasure				= "";
	protected	String		qualifierDate				= ""; //transaction Date
	protected	String		qualifierTime				= ""; //transaction Time
	protected	String		status						= "";
		
   /**
	* Basic constructor.
	*/
	public MMS850MIAddAdjust() {
		super();
	}
	/**
	 * @return Returns the itemNumber.
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the warehouse.
	 */
	public String getWarehouse() {
		return warehouse;
	}
	/**
	 * @param warehouse The warehouse to set.
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	/**
	 * @return Returns the lotNumber.
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 * @return Returns the messageType.
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType The messageType to set.
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @return Returns the partner.
	 */
	public String getPartner() {
		return partner;
	}
	/**
	 * @param partner The partner to set.
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}
	/**
	 * @return Returns the processFlag.
	 */
	public String getProcessFlag() {
		return processFlag;
	}
	/**
	 * @param processFlag The processFlag to set.
	 */
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDateGenerated() {
		return dateGenerated;
	}
	public void setDateGenerated(String dateGenerated) {
		this.dateGenerated = dateGenerated;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getQualifierDate() {
		return qualifierDate;
	}
	public void setQualifierDate(String qualifierDate) {
		this.qualifierDate = qualifierDate;
	}
	public String getQualifierTime() {
		return qualifierTime;
	}
	public void setQualifierTime(String qualifierTime) {
		this.qualifierTime = qualifierTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTimeGenerated() {
		return timeGenerated;
	}
	public void setTimeGenerated(String timeGenerated) {
		this.timeGenerated = timeGenerated;
	}
}
