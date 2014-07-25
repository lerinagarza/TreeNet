/*
 * Created on June 26, 2009
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with PPS370MI 
 * 			Method:  FinishEntry -- Processes the Batch
 */
public class PPS370MIFinishEntry {

	// Additional Information
	protected	String		sentFromProgram		= "";
	protected	String		environment			= "";
	protected	String		userProfile			= "";
	
	// API IN Fields
	protected	String		messageNumber		= "";
	
	// API OUT Fields
	protected	String		errorMessage		= "";
		
   /**
	* Basic constructor.
	*/
	public PPS370MIFinishEntry() {
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
}
