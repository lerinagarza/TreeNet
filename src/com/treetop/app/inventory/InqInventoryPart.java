/*
 * Created on Aug 11, 2005
 *
 * Currently will start only with Inventories in the
 *   Prismepi library
  */
package com.treetop.app.inventory;

import java.util.List;
import java.util.Vector;

import com.treetop.viewbeans.BaseViewBean;

/**
 * @author twalto
 *
 * Will Find Inventories
 */
public class InqInventoryPart extends BaseViewBean{
	
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
	public String inqPlant				 = "";
	public String inqLocation			 = "";
	public String inqLot				 = "";
	public String showZeroQuantity       = "";
	
	// List for the List Page JSP
	public Vector listReport             = null;
	
	// Dual Drop Down
	public Vector classSubClassDualDD    = null;
	public String plantDD                = null;


	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		return null;
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
		if (!showZeroQuantity.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Show Zero Quantity");		  
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
		return returnString.toString();
	}
	

	/**
	 * @return
	 */
	public String getInqLocation() {
		return inqLocation;
	}

	/**
	 * @return
	 */
	public String getInqLot() {
		return inqLot;
	}

	/**
	 * @return
	 */
	public String getInqPlant() {
		return inqPlant;
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
	public String getInqVendorName() {
		return inqVendorName;
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
	public Vector getListReport() {
		return listReport;
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
	public void setInqLocation(String string) {
		inqLocation = string;
	}

	/**
	 * @param string
	 */
	public void setInqLot(String string) {
		inqLot = string;
	}

	/**
	 * @param string
	 */
	public void setInqPlant(String string) {
		inqPlant = string;
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
	public void setInqVendorName(String string) {
		inqVendorName = string;
	}

	/**
	 * @param string
	 */
	public void setInqVendorNumber(String string) {
		inqVendorNumber = string;
	}

	/**
	 * @param vector
	 */
	public void setListReport(Vector vector) {
		listReport = vector;
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
	public String getShowZeroQuantity() {
		return showZeroQuantity;
	}

	/**
	 * @param string
	 */
	public void setShowZeroQuantity(String string) {
		showZeroQuantity = string;
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

	/**
	 * @return
	 */
	public String getPlantDD() {
		return plantDD;
	}

	/**
	 * @param string
	 */
	public void setPlantDD(String string) {
		plantDD = string;
	}

}
