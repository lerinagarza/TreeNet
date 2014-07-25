/*
 * Created on Feb 2, 2006
 *
 * For use with the JSP's 
 *    inqAvailableResources
 *    listAvailableResources
 */
package com.treetop.app.resource;

import java.util.*;

import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBean;
import com.treetop.businessobjects.*;

/**
 * @author twalto
 *
 */
public class InqAvailableResources extends BaseViewBean {

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public List errors = null;

	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";

	// JSP Specific Fields for THIS View Bean
	public String inqStartNumber = "01400";
	public String resourceClass = "";
	public String resourceEnd = "";
	public String resource = "";
	
	// Drop Down List
	public String dropDownResourceClass = null;
	
	// List for the List Page JSP
	public Vector listReport = null;
	public ResourcesAvailable listOfResources = null;

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		
		Vector errors = new Vector();
		String problem = "";
		if (requestType.equals("addSendAvailable"))
		{	
			inqStartNumber = resourceEnd;
			if (resourceClass.equals(""))
				problem = "You MUST Choose a Resource Class.";
			if (resourceEnd.equals(""))
				problem = problem + "You MUST Choose a Resource Number";
		}
		if (requestType.equals("listAvailable"))
		{	
			if ((inqStartNumber.equals("") ||
				inqStartNumber.equals("0") ||
				inqStartNumber.equals("01400")) &&
				!resource.equals("") &&
				resource.length() > 2)
				inqStartNumber = resource.substring(2, resource.length());
			
			if ((inqStartNumber.equals("") ||
					inqStartNumber.equals("0") ||
					inqStartNumber.equals("01400")) &&
					!resourceEnd.equals(""))
					inqStartNumber = resourceEnd;

			if (inqStartNumber.equals(""))
				inqStartNumber = "0";
			problem = validateInteger(inqStartNumber);
		}
		if (!problem.equals(""))
		{
		   errors.add(problem);
		}   
		else
		{	
		   try
		   {
		   	 if (inqStartNumber.length() < 5)
		   	 {
		   	 	for (int z = inqStartNumber.length(); z < 5; z++)
		   	 	{
		   	 		inqStartNumber = "0" + inqStartNumber;
		   	 	}
		   	 }
		   }
		   catch(Exception e)
		   {}
		}   

		return errors;
	}

	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		return returnString.toString();
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *    Will return a String to Resend the parameters
	 *    Mainly from the link on the heading Sort, 
	 *   OR if there is another list view.
	 * @return
	 */
	public String buildParameterResend() {
		StringBuffer returnString = new StringBuffer();
		return returnString.toString();
	}
	/**
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @return
	 */
	public String getOrderStyle() {
		return orderStyle;
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
	public void setOrderBy(String string) {
		orderBy = string;
	}

	/**
	 * @param string
	 */
	public void setOrderStyle(String string) {
		orderStyle = string;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @return
	 */
	public String getInqStartNumber() {
		return inqStartNumber;
	}

	/**
	 * @param string
	 */
	public void setInqStartNumber(String string) {
		inqStartNumber = string;
	}

	/**
	 * @return
	 */
	public Vector getListReport() {
		return listReport;
	}

	/**
	 * @param vector
	 */
	public void setListReport(Vector vector) {
		listReport = vector;
	}

	/**
	 * @return Returns the dropDownResourceClass.
	 */
	public String getDropDownResourceClass() {
		return dropDownResourceClass;
	}
	/**
	 * @param dropDownResourceClass The dropDownResourceClass to set.
	 */
	public void setDropDownResourceClass(String dropDownResourceClass) {
		this.dropDownResourceClass = dropDownResourceClass;
	}
	/**
	 * @return Returns the listOfResources.
	 */
	public ResourcesAvailable getListOfResources() {
		return listOfResources;
	}
	/**
	 * @param listOfResources The listOfResources to set.
	 */
	public void setListOfResources(ResourcesAvailable listOfResources) {
		this.listOfResources = listOfResources;
	}
	/**
	 * @return Returns the resourceClass.
	 */
	public String getResourceClass() {
		return resourceClass;
	}
	/**
	 * @param resourceClass The resourceClass to set.
	 */
	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}
	/**
	 * @return Returns the resourceEnd.
	 */
	public String getResourceEnd() {
		return resourceEnd;
	}
	/**
	 * @param resourceEnd The resourceEnd to set.
	 */
	public void setResourceEnd(String resourceEnd) {
		this.resourceEnd = resourceEnd;
	}
	/**
	 * @return Returns the errors.
	 */
	public List getErrors() {
		return errors;
	}
	/**
	 * @param errors The errors to set.
	 */
	public void setErrors(List errors) {
		this.errors = errors;
	}
	/**
	 * @return Returns the resource.
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
}
