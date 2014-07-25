package com.lawson.api;

import java.math.BigDecimal;

import com.treetop.app.inventory.UpdAttributeDetail;

public class ATS101MIaddItmLotAttrTx {

	// Additional Information
	protected String sentFromProgram	= "";
	protected String environment		= "";
	protected String userProfile		= "";
	
	protected String company 			= "";
	protected String attributeID 		= "";
	protected String itemNumber			= "";
	protected String lotNumber			= "";
	protected String textBlock			= ""; // Field Currently Not Used
	protected String language			= ""; // Field Currently Not Used
	protected String additionalTextLine = "";
	
	protected String attributeNumber	= "";
	
	/**
	 * Default constructor
	 */
	public ATS101MIaddItmLotAttrTx() {
		
	}
	
	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		
		for (int i=0; i<132; i++) {
			out.insert(0, " ");
		}
		
		out.insert(0, "addItmLotAttrTx");
		out.insert(15, this.getCompany());
		out.insert(18, this.getAttributeID());
		out.insert(33, this.getItemNumber());
		out.insert(48, this.getLotNumber());
		//out.insert(60, this.getTextBlock());
		//out.insert(71, this.getLanguage());
		out.insert(72, this.getAdditionalTextLine());
		
		out.setLength(132);
		return out.toString();
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAttributeID() {
		return attributeID;
	}
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
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

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getTextBlock() {
		return textBlock;
	}

	public void setTextBlock(String textBlock) {
		this.textBlock = textBlock;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAdditionalTextLine() {
		return additionalTextLine;
	}

	public void setAdditionalTextLine(String additionalTextLine) {
		this.additionalTextLine = additionalTextLine;
	}

	public String getAttributeNumber() {
		return attributeNumber;
	}

	public void setAttributeNumber(String attributeNumber) {
		this.attributeNumber = attributeNumber;
	}
	
	
	
}

