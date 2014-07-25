package com.lawson.api;

import java.math.BigDecimal;

import com.treetop.app.inventory.UpdAttributeDetail;

public class ATS101MISetAttrValue {

	// Additional Information
	protected String sentFromProgram	= "";
	protected String environment		= "";
	protected String userProfile		= "";
	
	protected String company 			= "";
	protected String attributeNumber 	= "";
	protected String attributeID 		= "";
	protected String fromValue 			= "";
	protected String toValue 			= "";
	
	protected String targeValue 		= "";
	protected String attributeValue		= "";
	protected String registrationDate	= "";
	protected String registrationTime	= "";
	
	/**
	 * Default constructor
	 */
	public ATS101MISetAttrValue() {
		
	}
	
	/**
	 * Constructor from UpdAttributeDetail
	 * @param uad
	 */
	public ATS101MISetAttrValue(UpdAttributeDetail uad, String attributeNumber) {
		this.setCompany("100");
		this.setAttributeNumber(attributeNumber);
		this.setAttributeID(uad.getAttributeID());
		
		String fieldType = uad.getFieldType();
		if (fieldType.equals("numeric")) {
			int decimalPlaces = Integer.parseInt(uad.getDecimalPlaces());
			
			BigDecimal from = null;
			try {
				from = new BigDecimal(uad.getValueFrom()).setScale(decimalPlaces,BigDecimal.ROUND_HALF_UP);
			} catch (Exception e) {}
			
			if (from != null) {
				this.setFromValue(from.toString());
			}
			
			BigDecimal to = null;
			try {
				to = new BigDecimal(uad.getValueTo()).setScale(decimalPlaces,BigDecimal.ROUND_HALF_UP);
			} catch (Exception e) {}
			if (to != null) {
				this.setAttributeValue(to.toString());			
			}
			
		} else {
			this.setFromValue(uad.getValueFrom());
			this.setAttributeValue(uad.getValueTo());
		}
		
	}
	
	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		
		for (int i=0; i<123; i++) {
			out.insert(0, " ");
		}
		
		out.insert(0, "SetAttrValue");
		out.insert(15, this.getCompany());
		out.insert(18, this.getAttributeNumber());
		out.insert(35, this.getAttributeID());
		//out.insert(50, this.getFromValue());
		//out.insert(65, this.getToValue());
		//out.insert(80, this.getTargeValue());
		out.insert(95, this.getAttributeValue());
		//out.insert(110, this.getRegistrationDate());
		//out.insert(118, this.getRegistrationTime());
		
		out.setLength(123);
		return out.toString();
	}
	
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAttributeNumber() {
		return attributeNumber;
	}
	public void setAttributeNumber(String attributeNumber) {
		this.attributeNumber = attributeNumber;
	}
	public String getAttributeID() {
		return attributeID;
	}
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}
	public String getFromValue() {
		return fromValue;
	}
	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}
	public String getToValue() {
		return toValue;
	}
	public void setToValue(String toValue) {
		this.toValue = toValue;
	}
	public String getTargeValue() {
		return targeValue;
	}
	public void setTargeValue(String targeValue) {
		this.targeValue = targeValue;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(String registrationTime) {
		this.registrationTime = registrationTime;
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
	
	
	
}

