package com.lawson.api;

import com.treetop.app.planning.UpdPlanning;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.UtilityDateTime;

public class PMS170MIMassDelete {
	
	// Additional Information
	protected	String		sentFromProgram		= "";
	protected	String		environment			= "";
	protected	String		userProfile			= "";
	
	//API IN Fields
	protected 	String		company 			= "";
	protected 	String		fromDate	 		= "";
	protected 	String		toDate	 			= "";
	protected 	String		facility 			= "";
	protected 	String		orderStatus			= "";
	protected 	String		itemType 			= "";
	
	/**
	 * Default constructor
	 */
	public PMS170MIMassDelete() {
		
	}
	
	/**
	 * Constructor to build new PMS170MIMassDelete object from UpdPlanning
	 * @param up
	 */
	public PMS170MIMassDelete(UpdPlanning up) {
		this.setEnvironment(up.getEnvironment());
		this.setCompany(up.getCompany());
		this.setFromDate(up.getFromDate());
		this.setToDate(up.getToDate());
		this.setFacility(up.getFacility());
		this.setOrderStatus(up.getOrderStatus());
		this.setItemType(up.getItemType());
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer rec = new StringBuffer();
		for (int x = 0; x < 46; x++) {
			rec.insert(0, " ");
		}
		//build string from object
		rec.insert(0, "MassDelete");
		rec.insert(15, this.getCompany());
		
		//need to parse out the date to YYYYMMDD format
		DateTime from = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getFromDate());
		from.setDateFormatMMddyyyySlash(this.getFromDate());
		rec.insert(18, from.getDateFormatyyyyMMdd());
		
		DateTime to = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getToDate());
		to.setDateFormatMMddyyyySlash(this.getFromDate());
		rec.insert(28, to.getDateFormatyyyyMMdd());

		rec.insert(38, this.getFacility());
		rec.insert(41, this.getOrderStatus());
		rec.insert(43, this.getItemType());
		rec.setLength(46);
		return rec.toString();
	}
	
	/**
	 * @return the sentFromProgram
	 */
	public String getSentFromProgram() {
		return sentFromProgram;
	}
	/**
	 * @param sentFromProgram the sentFromProgram to set
	 */
	public void setSentFromProgram(String sentFromProgram) {
		this.sentFromProgram = sentFromProgram;
	}
	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	/**
	 * @return the userProfile
	 */
	public String getUserProfile() {
		return userProfile;
	}
	/**
	 * @param userProfile the userProfile to set
	 */
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	
	
	
}
