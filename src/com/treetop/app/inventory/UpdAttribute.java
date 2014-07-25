package com.treetop.app.inventory;

import com.treetop.utilities.GeneralUtility;
import com.treetop.view.PopulatedVector;
import com.treetop.viewbeans.BaseViewBeanR2;
import java.util.*;
import java.math.*;

import javax.servlet.http.HttpServletRequest;

public class UpdAttribute extends BaseViewBeanR2 {

	private String				submit 				= "";
	private	String				authorization		= "";
	private String				userProfile			= "";
	private PopulatedVector		attributeDetails	= new PopulatedVector(UpdAttributeDetail.class);
	private PopulatedVector		attributeNumbers	= new PopulatedVector(UpdAttributeDetail.class);
	

	/**
	 * Default constructor
	 */
	public UpdAttribute () {
		
	}
	
	/**
	 * Constructor to automatically populate and validate view bean
	 * @param request
	 */
	public UpdAttribute(HttpServletRequest request) {
		this.populate(request);
	}
	
	public void validate() {
		StringBuffer error = new StringBuffer();
		
		Vector details = this.getAttributeDetails();
		
		//validate UpdAttributeDetail objects
		for (int i=0; i<details.size(); i++) {
			UpdAttributeDetail detail = (UpdAttributeDetail) details.elementAt(i);
			detail.validate();
			error.append(detail.getErrorMessage());
		}
		
		this.setErrorMessage(error.toString());
	}

	/**
	 * @return the submit
	 */
	public String getSubmit() {
		return submit;
	}

	/**
	 * @param submit the submit to set
	 */
	public void setSubmit(String submit) {
		this.submit = submit;
	}

	/**
	 * @return the attributeDetails
	 */
	public PopulatedVector getAttributeDetails() {
		return attributeDetails;
	}

	/**
	 * @param attributeDetails the attributeDetails to set
	 */
	public void setAttributeDetails(PopulatedVector attributeDetails) {
		this.attributeDetails = attributeDetails;
	}

	/**
	 * @return the attributeNumbers
	 */
	public PopulatedVector getAttributeNumbers() {
		return attributeNumbers;
	}

	/**
	 * @param attributeNumbers the attributeNumbers to set
	 */
	public void setAttributeNumbers(PopulatedVector attributeNumbers) {
		this.attributeNumbers = attributeNumbers;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	



}
