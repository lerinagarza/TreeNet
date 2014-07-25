/*
 * Created on Aug 23, 2005
 *
 */
package com.treetop.app.rawfruit;

import java.util.Vector;

import com.treetop.viewbeans.BaseViewBeanR1;

/**
 *   Search for Raw Fruit Load Information
 * @author twalto
 *
 */
public class BldRawFruit extends BaseViewBeanR1  {
	
	// Standard Fields - to be in Every View Bean
	public String requestType    = "";
	public String displayMessage = "";
	public String goButton       = "";
	
	public String updateUser	 = "";


	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
	
	}
		
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *    Will return a String to Resend the parameters
	 *    Mainly from the link on the heading Sort, 
	 *   OR if there is another list view.
	 * @return
	 */
	public String buildParameterResend() {
		return "";
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();		
		return returnString.toString();
	}


	public String getDisplayMessage() {
		return displayMessage;
	}


	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}


	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getGoButton() {
		return goButton;
	}

	public void setGoButton(String goButton) {
		this.goButton = goButton;
	}

	public String getUpdateUser() {
		return updateUser;
	}
}