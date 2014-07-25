/*
 * Created on Oct 5, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.treenet;

import java.util.List;
import java.util.Vector;

import com.treetop.viewbeans.BaseViewBean;
import com.treetop.*;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdTreeNetUser  extends BaseViewBean {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public List errors = null;
	// Must have in Update View Bean
	public String userProfile = "";
	public String saveValue = "";

	// FIELDS
	public String userAS400ID  = "";
	public String userLongName = "";
	public String userNumber   = "";
	public String userEmail    = "";
	
	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		Vector errorList = new Vector();
		if (!userEmail.trim().equals(""))
		{
			String thisError = ValidateFields.validateEMail(userEmail.trim());
			if (!thisError.trim().equals(""))
			   errorList.addElement(thisError);
		}
		return errorList;
	}

	/**
	 * @param errors
	 */
	public void setErrors(List errors) {
		
	}

	/**
	 * @return
	 */
	public List getErrors() {
		return errors;
	}

	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @return
	 */
	public String getSaveValue() {
		return saveValue;
	}

	/**
	 * @return
	 */
	public String getUserAS400ID() {
		return userAS400ID;
	}

	/**
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @return
	 */
	public String getUserNumber() {
		return userNumber;
	}

	/**
	 * @return
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @param string
	 */
	public void setSaveValue(String string) {
		saveValue = string;
	}

	/**
	 * @param string
	 */
	public void setUserAS400ID(String string) {
		userAS400ID = string;
	}

	/**
	 * @param string
	 */
	public void setUserEmail(String string) {
		userEmail = string;
	}

	/**
	 * @param string
	 */
	public void setUserNumber(String string) {
		userNumber = string;
	}

	/**
	 * @param string
	 */
	public void setUserProfile(String string) {
		userProfile = string;
	}

	/**
	 * @return
	 */
	public String getUserLongName() {
		return userLongName;
	}

	/**
	 * @param string
	 */
	public void setUserLongName(String string) {
		userLongName = string;
	}

}
