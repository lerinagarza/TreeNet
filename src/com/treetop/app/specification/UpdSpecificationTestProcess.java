/*
 * Created on January 7, 2009
 */

package com.treetop.app.specification;


import com.treetop.businessobjects.SpecificationTestProcess;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */

public class UpdSpecificationTestProcess extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	public String itemNumber = "";
	public String revisionDate = "";
	
	public String valueID = "";
	public String idSequence = "";
	public String idSequenceError = "";
	public String target = "0.0000";
	public String targetError = "";
	public String minimum = "0.0000";
	public String minimumError = "";
	public String maximum = "9.9999";
	public String maximumError = "";
	public String method = "";
	
	// For the Test Analytical Section ONLY
	public String testValue = "0.000";
	public String testValueError = "";
	public String testValueUOM = "";
	
	public SpecificationTestProcess testProcessInfo = new SpecificationTestProcess();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
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
	/**
	 * @return Returns the idSequence.
	 */
	public String getIdSequence() {
		return idSequence;
	}
	/**
	 * @param idSequence The idSequence to set.
	 */
	public void setIdSequence(String idSequence) {
		this.idSequence = idSequence;
	}
	/**
	 * @return Returns the idSequenceError.
	 */
	public String getIdSequenceError() {
		return idSequenceError;
	}
	/**
	 * @param idSequenceError The idSequenceError to set.
	 */
	public void setIdSequenceError(String idSequenceError) {
		this.idSequenceError = idSequenceError;
	}
	/**
	 * @return Returns the maximum.
	 */
	public String getMaximum() {
		return maximum;
	}
	/**
	 * @param maximum The maximum to set.
	 */
	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}
	/**
	 * @return Returns the maximumError.
	 */
	public String getMaximumError() {
		return maximumError;
	}
	/**
	 * @param maximumError The maximumError to set.
	 */
	public void setMaximumError(String maximumError) {
		this.maximumError = maximumError;
	}
	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return Returns the minimum.
	 */
	public String getMinimum() {
		return minimum;
	}
	/**
	 * @param minimum The minimum to set.
	 */
	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}
	/**
	 * @return Returns the minimumError.
	 */
	public String getMinimumError() {
		return minimumError;
	}
	/**
	 * @param minimumError The minimumError to set.
	 */
	public void setMinimumError(String minimumError) {
		this.minimumError = minimumError;
	}
	/**
	 * @return Returns the revisionDate.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param revisionDate The revisionDate to set.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return Returns the targetError.
	 */
	public String getTargetError() {
		return targetError;
	}
	/**
	 * @param targetError The targetError to set.
	 */
	public void setTargetError(String targetError) {
		this.targetError = targetError;
	}
	/**
	 * @return Returns the testProcessInfo.
	 */
	public SpecificationTestProcess getTestProcessInfo() {
		return testProcessInfo;
	}
	/**
	 * @param testProcessInfo The testProcessInfo to set.
	 */
	public void setTestProcessInfo(SpecificationTestProcess testProcessInfo) {
		this.testProcessInfo = testProcessInfo;
	}
	/**
	 * @return Returns the testValue.
	 */
	public String getTestValue() {
		return testValue;
	}
	/**
	 * @param testValue The testValue to set.
	 */
	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}
	/**
	 * @return Returns the testValueError.
	 */
	public String getTestValueError() {
		return testValueError;
	}
	/**
	 * @param testValueError The testValueError to set.
	 */
	public void setTestValueError(String testValueError) {
		this.testValueError = testValueError;
	}
	/**
	 * @return Returns the testValueUOM.
	 */
	public String getTestValueUOM() {
		return testValueUOM;
	}
	/**
	 * @param testValueUOM The testValueUOM to set.
	 */
	public void setTestValueUOM(String testValueUOM) {
		this.testValueUOM = testValueUOM;
	}
	/**
	 * @return Returns the valueID.
	 */
	public String getValueID() {
		return valueID;
	}
	/**
	 * @param valueID The valueID to set.
	 */
	public void setValueID(String valueID) {
		this.valueID = valueID;
	}
}
