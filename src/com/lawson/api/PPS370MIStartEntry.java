/*
 * Created on June 25, 2009
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with PPS370MI 
 * 			Method:  StartEntry -- Start the Batch
 */
public class PPS370MIStartEntry {

	// Additional Information
	protected	String		sentFromProgram		= "";
	protected	String		environment			= "";
	protected	String		userProfile			= "";
	
	// API IN Fields
	protected	String		batchOrigin			= "";
	
	// API OUT Fields
	protected 	String		messageNumber		= "";
	protected	String		errorMessage		= "";
		
   /**
	* Basic constructor.
	*/
	public PPS370MIStartEntry() {
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
	public String getBatchOrigin() {
		return batchOrigin;
	}
	public void setBatchOrigin(String batchOrigin) {
		this.batchOrigin = batchOrigin;
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
}
