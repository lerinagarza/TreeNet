/*
 * Created on Aug 8, 2005
 *
 * Currently will start only with Resources in the
 *   Prismepi library1
 * WILL Be used for Both, when the Original Resource Inquiry is
 *    redone.
 */
package com.treetop.app.resource;

import java.util.List;
import java.util.Vector;

import com.treetop.viewbeans.BaseViewBean;

/**
 * @author twalto
 *
 * Will Find Resources
 */
public class InqResourcePart extends BaseViewBean{
	
	// Standard Fields - to be in Every View Bean
	public String requestType       = "";
	public List   errors            = null;	
	// Standard Fields for Inq View Bean
	public String orderBy    	    = "resourcenumber";
	public String orderStyle 	    = "";
	
	// JSP Specific Fields for THIS View Bean
	public String inqResource  			 = "";
	public String inqResourceDescription = "";
	public String inqResourceClass       = "";
	public String inqResourceSubClass    = "";
	public String inqVendorNumber        = "";
	public String inqVendorName          = "";
	
	// List for the List Page JSP
	public Vector listReport             = null;
	
	// Dual Drop Down
	public Vector classSubClassDualDD    = null;

	/* Test to make sure something is filled in to narrow
	 *  down the selection
	 * IF resource is the ONLY thing input 
	 * 		it has to be at least 4 characters long
	 * IF resource description is the ONLY thing input
	 *      it has to be at least 2 characters long
	 */
	public List validate() {
		
		String returnErrors = "";
		if (inqResource.equals("") && 
		    inqResourceDescription.equals("") &&
		    inqResourceClass.equals("") &&
		    inqResourceSubClass.equals("") &&
		    inqVendorNumber.equals("") &&
		    inqVendorName.equals(""))
		    returnErrors = "Please Enter Something To Narrow the Selection.  You have Requested To Much.";
		if (!inqResource.equals("") && 
			inqResourceDescription.equals("") &&
			inqResourceClass.equals("") &&
			inqResourceSubClass.equals("") &&
			inqVendorNumber.equals("") &&
			inqVendorName.equals("") &&
			inqResource.trim().length() < 4)
			returnErrors = "Please Enter Something More To Narrow the Selection.  You have Requested To Much.";
		if (inqResource.equals("") && 
			!inqResourceDescription.equals("") &&
			inqResourceClass.equals("") &&
			inqResourceSubClass.equals("") &&
			inqVendorNumber.equals("") &&
			inqVendorName.equals("") &&
			inqResourceDescription.trim().length() < 3)
			returnErrors = "Please Enter Something More To Narrow the Selection.  You have Requested To Much.";
		
		List returnStuff = null;
		Vector thisList = new Vector();
		thisList.add(returnErrors);
		returnStuff = thisList;
		   
		return returnStuff;
	}
	
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!inqResource.equals(""))
		{
			returnString.append("Resource: ");
			returnString.append(inqResource);
		}
		if (!inqResourceDescription.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Resource Description: ");
			returnString.append(inqResourceDescription);			  
		}
		if (!inqResourceClass.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Class: ");
			returnString.append(inqResourceClass);			  
		}		
		if (!inqResourceSubClass.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Sub - Class: ");
			returnString.append(inqResourceSubClass);			  
		}
		if (!inqVendorNumber.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Vendor Number: ");
			returnString.append(inqVendorNumber);			  
		}		
		if (!inqVendorName.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Vendor Name: ");
			returnString.append(inqVendorName);			  
		}		

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
		if (!inqResource.equals(""))
		{
			returnString.append("&inqResource=");
			returnString.append(inqResource);
		}
		if (!inqResourceDescription.equals(""))
		{
			returnString.append("&inqResourceDescription=");
			returnString.append(inqResourceDescription);
		}	
		if (!inqResourceClass.equals(""))
		{
			returnString.append("&inqResourceClass=");
			returnString.append(inqResourceClass);
		}	
		if (!inqResourceSubClass.equals(""))
		{
			returnString.append("&inqResourceSubClass=");
			returnString.append(inqResourceSubClass);
		}	
		if (!inqVendorNumber.equals(""))
		{
			returnString.append("&inqVendorNumber=");
			returnString.append(inqVendorNumber);
		}	
		if (!inqVendorName.equals(""))
		{
			returnString.append("&inqVendorName=");
			returnString.append(inqVendorName);
		}	

		return returnString.toString();
	}
	

	/**
	 * @return
	 */
	public String getInqResource() {
		return inqResource;
	}

	/**
	 * @return
	 */
	public String getInqResourceClass() {
		return inqResourceClass;
	}

	/**
	 * @return
	 */
	public String getInqResourceDescription() {
		return inqResourceDescription;
	}

	/**
	 * @return
	 */
	public String getInqResourceSubClass() {
		return inqResourceSubClass;
	}

	/**
	 * @return
	 */
	public String getInqVendorNumber() {
		return inqVendorNumber;
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
	public void setInqResource(String string) {
		inqResource = string;
	}

	/**
	 * @param string
	 */
	public void setInqResourceClass(String string) {
		inqResourceClass = string;
	}

	/**
	 * @param string
	 */
	public void setInqResourceDescription(String string) {
		inqResourceDescription = string;
	}

	/**
	 * @param string
	 */
	public void setInqResourceSubClass(String string) {
		inqResourceSubClass = string;
	}

	/**
	 * @param string
	 */
	public void setInqVendorNumber(String string) {
		inqVendorNumber = string;
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
	 * @return
	 */
	public String getInqVendorName() {
		return inqVendorName;
	}

	/**
	 * @param string
	 */
	public void setInqVendorName(String string) {
		inqVendorName = string;
	}

	/**
	 * @return
	 */
	public List getErrors() {
		return errors;
	}

	/**
	 * @param list
	 */
	public void setErrors(List list) {
		errors = list;
	}

	/**
	 * @return
	 */
	public Vector getClassSubClassDualDD() {
		return classSubClassDualDD;
	}

	/**
	 * @param vector
	 */
	public void setClassSubClassDualDD(Vector vector) {
		classSubClassDualDD = vector;
	}

}
