/*
 * Created on October 31, 2012
 */
package com.lawson.api;

/**
 * @author Teri Walton
 *
 * Use in Conjunction with MMS025MI 
 * 			Method:  dltAlias -- Add An Alias into M3 - from outside of M3
 */

public class MMS025MIDeleteAlias{

	// Additional Information
	protected	String		sentFromProgram				= "";
	protected	String		environment					= "";
	protected	String		userProfile					= "";
	
	// API Fields
	protected	String		company						= "";
	protected	String		aliasCategory				= ""; // MUST HAVE
	protected	String		aliasQualifier				= ""; // Not Needed with Alias Category 1
	protected	String		aliasNumber					= "";
	protected	String		itemNumber					= ""; 
	
		
   /**
	* Basic constructor.
	*/
	public MMS025MIDeleteAlias() {
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
	public String getAliasCategory() {
		return aliasCategory;
	}
	public void setAliasCategory(String aliasCategory) {
		this.aliasCategory = aliasCategory;
	}
	public String getAliasNumber() {
		return aliasNumber;
	}
	public void setAliasNumber(String aliasNumber) {
		this.aliasNumber = aliasNumber;
	}
	public String getAliasQualifier() {
		return aliasQualifier;
	}
	public void setAliasQualifier(String aliasQualifier) {
		this.aliasQualifier = aliasQualifier;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getSentFromProgram() {
		return sentFromProgram;
	}
	public void setSentFromProgram(String sentFromProgram) {
		this.sentFromProgram = sentFromProgram;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
}
