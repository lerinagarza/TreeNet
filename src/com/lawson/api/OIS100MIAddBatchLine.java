/*
 * Created on February 20, 2012
 */
package com.lawson.api;

import java.util.Vector;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with OIS100MI 
 * 			Method:  sndBatchLine -- Add to create lines for the Temporary CO
 * 				Will put information into OXLINE
 * 			Can use program OIS275 in M3 to see data and release to an actual CO
 */
public class OIS100MIAddBatchLine {

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		userProfile					= "";
	
	// API Fields
	protected   String		company						= "";
	protected	String		itemNumber					= "";
	protected	String		warehouse					= "";
	protected	String		quantity					= "";
	protected	String		unitOfMeasure				= "";
	protected	String		itemDescription				= "";
	protected	String		description1				= "";
		
   /**
	* Basic constructor.
	*/
	public OIS100MIAddBatchLine() {
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
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
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
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getDescription1() {
		return description1;
	}
	public void setDescription1(String description1) {
		this.description1 = description1;
	}
}
