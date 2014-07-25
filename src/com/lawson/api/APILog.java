/*
 * Created on June 16, 2008
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with ServiceAPILog
 *      Using this Class, we do not have to worry if we change number of Parms
 */
public class APILog {

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		apiName						= "";
	protected	String		apiMethod					= "";
	protected	String		inputData					= "";
	protected	String		outputData					= "";
	protected	String		userProfile					= "";
		
   /**
	* Basic constructor.
	*/
	public APILog() {
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
	 * @return Returns the apiMethod.
	 */
	public String getApiMethod() {
		return apiMethod;
	}
	/**
	 * @param apiMethod The apiMethod to set.
	 */
	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	/**
	 * @return Returns the apiName.
	 */
	public String getApiName() {
		return apiName;
	}
	/**
	 * @param apiName The apiName to set.
	 */
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	/**
	 * @return Returns the inputData.
	 */
	public String getInputData() {
		return inputData;
	}
	/**
	 * @param inputData The inputData to set.
	 */
	public void setInputData(String inputData) {
		this.inputData = inputData;
	}
	/**
	 * @return Returns the outputData.
	 */
	public String getOutputData() {
		return outputData;
	}
	/**
	 * @param outputData The outputData to set.
	 */
	public void setOutputData(String outputData) {
		this.outputData = outputData;
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
}
