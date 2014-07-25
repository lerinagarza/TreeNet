/*
 * Created on Dec 2, 2005
 *
 */
 
package com.treetop.app.email;

import java.util.List;
import java.util.Vector;

import com.treetop.ValidateFields;
import com.treetop.viewbeans.BaseViewBean;
import com.treetop.data.UserFile;
import com.treetop.utilities.html.*;

/**
 * @author twalto - Dec 2, 2005
 *
 */
public class SendEmail extends BaseViewBean {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public List   errors      = null;
	public String saveButton  = null;
	public String userProfile = "";
	
	public String emailTo = "";
	public String emailToError = "";
	public String emailFrom = "";
	public String emailFromError = "";
	public String emailSubject = "";
	public String body = "";
	public String additionalBody = "";

	/* Test and Validate fields, after loading them.
	 *    Send back List of errors, IF there are any.
	 * 
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		//------------------------------------------------------------
		// TO EMAIL
		if (emailTo.length() <= 6)
		{
			try
			{
			  UserFile thisUser = new UserFile(emailTo.toUpperCase());
			  if (thisUser != null &&
			      thisUser.getUserEmail() != null &&
			      !thisUser.getUserEmail().equals(""))
			     emailTo = thisUser.getUserEmail();
			}
			catch(Exception e)
			{
				emailToError = "This User Cannot be Found, Please Enter The Complete Email Address.";
			}
		}
		if (emailToError.equals(""))
		  emailToError = ValidateFields.validateEMail(emailTo);
		//----------------------------------------------------------------
        // FROM EMAIL
		if (emailFrom.length() <= 6)
		{
			try
			{
			  UserFile thisUser = new UserFile(emailFrom.toUpperCase());
			  if (thisUser != null &&
				  thisUser.getUserEmail() != null &&
				  !thisUser.getUserEmail().equals(""))
				 emailFrom = thisUser.getUserEmail();
			}
			catch(Exception e)
			{
				emailFromError = "This User Cannot be Found, Please Enter The Complete Email Address.";
			}
		}
		if (emailFromError.equals(""))
		  emailFromError = ValidateFields.validateEMail(emailFrom);
		return null;
	}
	/**
	 * @param list
	 */
	public void setErrors(List list) {
		errors = list;
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
	public void setUserProfile(String string) {
		userProfile = string;
	}

	/**
	 * @return
	 */
	public String getAdditionalBody() {
		return additionalBody;
	}

	/**
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @return
	 */
	public String getEmailFrom() {
		return emailFrom;
	}

	/**
	 * @return
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @return
	 */
	public String getEmailTo() {
		return emailTo;
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
	public String getSaveButton() {
		return saveButton;
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
	public void setAdditionalBody(String string) {
		additionalBody = string;
	}

	/**
	 * @param string
	 */
	public void setBody(String string) {
		body = string;
	}

	/**
	 * @param string
	 */
	public void setEmailFrom(String string) {
		emailFrom = string;
	}

	/**
	 * @param string
	 */
	public void setEmailSubject(String string) {
		emailSubject = string;
	}

	/**
	 * @param string
	 */
	public void setEmailTo(String string) {
		emailTo = string;
	}

	/**
	 * @param string
	 */
	public void setSaveButton(String string) {
		saveButton = string;
	}

	/**
	 * @return
	 */
	public String getEmailFromError() {
		return emailFromError;
	}

	/**
	 * @return
	 */
	public String getEmailToError() {
		return emailToError;
	}

	/**
	 * @param string
	 */
	public void setEmailFromError(String string) {
		emailFromError = string;
	}

	/**
	 * @param string
	 */
	public void setEmailToError(String string) {
		emailToError = string;
	}

}
