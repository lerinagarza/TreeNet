/*
 * Created on February 17, 2012
 */
package com.lawson.api;

import java.util.Vector;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with OIS100MI 
 * 			Method:  addBatchHead -- Add to create Temporary CO
 * 				Will put information into OXHEAD
 * 			Can use program OIS275 in M3 to see data and release to an actual CO
 */
public class OIS100MIAddBatchHead {

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		userProfile					= "";
	
	// API Fields
	protected   String		company						= "";
	protected	String		customerNumber				= "";
	protected	String		orderType					= "";
	protected	String		requestedDeliveryDate		= "";
	protected	String		facility					= "";
	protected	String		customerOrderNumber			= ""; // USE Tree Top MO
	protected	String		customersPODate				= "";
	protected	String		customerAgreement			= "";
	protected	String		orderDate					= "";
	protected	String		warehouse					= "";
	
	// Vector that additional API will have to process
	protected	Vector<OIS100MIAddBatchLine>	listLines	= new Vector<OIS100MIAddBatchLine>();
		
   /**
	* Basic constructor.
	*/
	public OIS100MIAddBatchHead() {
		super();
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
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	public void setRequestedDeliveryDate(String requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public String getCustomersPODate() {
		return customersPODate;
	}
	public void setCustomersPODate(String customersPODate) {
		this.customersPODate = customersPODate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getCustomerOrderNumber() {
		return customerOrderNumber;
	}
	public void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}
	public String getCustomerAgreement() {
		return customerAgreement;
	}
	public void setCustomerAgreement(String customerAgreement) {
		this.customerAgreement = customerAgreement;
	}
	public Vector<OIS100MIAddBatchLine> getListLines() {
		return listLines;
	}
	public void setListLines(Vector<OIS100MIAddBatchLine> listLines) {
		this.listLines = listLines;
	}
}
