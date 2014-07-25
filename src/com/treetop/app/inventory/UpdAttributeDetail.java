package com.treetop.app.inventory;

import com.treetop.viewbeans.BaseViewBeanR2;

public class UpdAttributeDetail extends BaseViewBeanR2 {


	private String	itemNumber		= "";
	private String  lotNumber		= "";
	private String  attributeNumber	= "";
	private String	attributeID 	= "";
	private String	valueFrom		= "";
	private String	valueTo			= "";
	private String	fieldType		= "";
	private String	decimalPlaces	= "";
	private String	valueToError	= "";
	
	public void validate() {
		StringBuffer error = new StringBuffer();
		
		if (this.getFieldType().equals("numeric")) {
			//get rid of commas
			String value = this.getValueTo();
			value = value.replaceAll(",", "");
			this.setValueTo(value);
			if (!validateBigDecimal(this.getValueTo()).equals("")) {
				String errMsg = this.getAttributeID() + ": " + this.getValueTo() + " is not a valid number   <br />"; 
				this.setValueToError(errMsg);
				error.append(errMsg);
			}
		}
		this.setErrorMessage(error.toString());
	}

	/**
	 * @return the attributeID
	 */
	public String getAttributeID() {
		return attributeID;
	}

	/**
	 * @param attributeID the attributeID to set
	 */
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}

	/**
	 * @return the valueFrom
	 */
	public String getValueFrom() {
		return valueFrom;
	}

	/**
	 * @param valueFrom the valueFrom to set
	 */
	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}

	/**
	 * @return the valueTo
	 */
	public String getValueTo() {
		return valueTo;
	}

	/**
	 * @param valueTo the valueTo to set
	 */
	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return the decimalPlaces
	 */
	public String getDecimalPlaces() {
		return decimalPlaces;
	}

	/**
	 * @param decimalPlaces the decimalPlaces to set
	 */
	public void setDecimalPlaces(String decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	/**
	 * @return the valueToError
	 */
	public String getValueToError() {
		return valueToError;
	}

	/**
	 * @param valueToError the valueToError to set
	 */
	public void setValueToError(String valueToError) {
		this.valueToError = valueToError;
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

	public String getAttributeNumber() {
		return attributeNumber;
	}

	public void setAttributeNumber(String attributeNumber) {
		this.attributeNumber = attributeNumber;
	}

	

}
