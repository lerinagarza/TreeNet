/*
 * Created on Nov 17, 2005
 * 
 *  Currently used to House URL's -- for images and documents
 *  Future uses for Comments
 */
package com.treetop.app.function;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.TicklerFunctionDetail;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.viewbeans.BaseViewBean;
import com.treetop.services.ServiceKeyValue;
import com.treetop.data.UserFile;

/**
 * @author twalto
 *
 */
public class DtlFunction extends BaseViewBean {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public String saveButton = null;
	public String userProfile = "";

	//UPD JSP Fields
	public String group = "";
	public String keyValue = "";
	public String functionNumber = "";
	
	public TicklerFunctionDetail dtlInformation = null;

	/*
	 * Use the Other Validate's ...
	 *   Will also populate information
	 */
	public List validate() {

		return null;
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
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @param string
	 */
	public void setSaveButton(String string) {
		saveButton = string;
	}

	/**
	 * @param string
	 */
	public void setUserProfile(String string) {
		userProfile = string;
	}

	/**
	 * @return Returns the dtlInformation.
	 */
	public TicklerFunctionDetail getDtlInformation() {
		return dtlInformation;
	}
	/**
	 * @param dtlInformation The dtlInformation to set.
	 */
	public void setDtlInformation(TicklerFunctionDetail dtlInformation) {
		this.dtlInformation = dtlInformation;
	}
	/**
	 * @return Returns the functionNumber.
	 */
	public String getFunctionNumber() {
		return functionNumber;
	}
	/**
	 * @param functionNumber The functionNumber to set.
	 */
	public void setFunctionNumber(String functionNumber) {
		this.functionNumber = functionNumber;
	}
	/**
	 * @return Returns the group.
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @param group The group to set.
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @return Returns the keyValue.
	 */
	public String getKeyValue() {
		return keyValue;
	}
	/**
	 * @param keyValue The keyValue to set.
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
}
