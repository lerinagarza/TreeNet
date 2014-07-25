/*
 * Created on June 11, 2008
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with MMS850MI 
 * 			Method:  addRclLotSts -- Add Reclassify Lot Status
 */
public class MMS850MIAddRclLotSts {

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		userProfile					= "";
	
	// API Fields
	protected	String		processFlag					= "*EXE";
	protected	String		partner						= "RECLASSIFY";
	protected	String		messageType					= "RECLAS";  // can also be INCUB
	protected	String		warehouse					= "";
	protected	String		itemNumber					= "";
	protected	String		lotNumber					= "";
	protected	String		allocatable					= "";
	protected	String		statusBalanceID				= "";
	
	protected 	String 		expirationDate    			= "";
	protected 	String 		followUpDate      			= "";
	protected	String 		salesDate	       			= "";
	protected 	String 		transactionReason 			= "";
	protected 	String 		lotRef2						= "";
	protected 	String 		remark						= "";
	
		
   /**
	* Basic constructor.
	*/
	public MMS850MIAddRclLotSts() {
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
	 * @return Returns the allocatable.
	 */
	public String getAllocatable() {
		return allocatable;
	}
	/**
	 * @param allocatable The allocatable to set.
	 */
	public void setAllocatable(String allocatable) {
		this.allocatable = allocatable;
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
	 * @return Returns the statusBalanceID.
	 */
	public String getStatusBalanceID() {
		return statusBalanceID;
	}
	/**
	 * @param statusBalanceID The statusBalanceID to set.
	 */
	public void setStatusBalanceID(String statusBalanceID) {
		this.statusBalanceID = statusBalanceID;
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
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getFollowUpDate() {
		return followUpDate;
	}
	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}
	public String getLotRef2() {
		return lotRef2;
	}
	public void setLotRef2(String lotRef2) {
		this.lotRef2 = lotRef2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public String getTransactionReason() {
		return transactionReason;
	}
	public void setTransactionReason(String transactionReason) {
		this.transactionReason = transactionReason;
	}
}
