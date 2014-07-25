/*
 * Created on September 4, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.coa;

//import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.services.ServiceDistributionOrder;
import com.treetop.services.ServiceLot;
import com.treetop.services.ServiceSalesOrder;
import com.treetop.utilities.FindAndReplace;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.businessobjectapplications.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdCOA extends BaseViewBeanR1 {
	

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public String environment = "";
	
	public String inqLot = "";
	public String inqSalesOrder = "";
	public String inqDistributionOrder = "";
	public String coaType = "";
	public String lineNumber = "";
	public String lineSuffix = "";
	public String itemNumber = "";
	
	public String company = "100";
	public String division = "100";
	
	public String updateUser = "";	
	// Update the Information Based on Line Item
	public String lineComment = "";
	public String lineSignature = "";
	public String lineSpec = "";
	
	// Use the String when sending information to the Service
	public String numberOfAttributes = "";
	public String lineAttrSequence = "";
	public Vector listAttributeSequence = new Vector();
	public String attributeID = "";
	public Vector listAttributeID = new Vector();
	public Vector listLots = new Vector();

	public BeanCOA returnBean = new BeanCOA();
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
	
	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
	   if (!inqSalesOrder.trim().equals(""))
	   {
		 inqLot = "";
		 inqDistributionOrder = "";
		 coaType = "CO";
	   }
	   if (!inqDistributionOrder.trim().equals(""))
	   {	
		 inqLot = "";
		 coaType = "DO";
	   }
	   if (!inqLot.trim().equals(""))
	   	  coaType = "LOT";
	}
	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void populateAttributeSequence(HttpServletRequest request) {
	    try
		{
	       if (!numberOfAttributes.equals(""))
	       {
	    	 int noa = new Integer(numberOfAttributes).intValue();
	    	 listAttributeSequence = new Vector();
	    	 listAttributeID = new Vector();
	    	 for (int x = 1; x < noa; x++)
	    	 {	
	    	 	if (request.getParameter("seq" + x) == null || 
	    	 		request.getParameter("seq" + x).trim().equals(""))
	    	 	  listAttributeSequence.add("0");
	    	 	else
	       	      listAttributeSequence.add(request.getParameter("seq" + x));
	    	 	
	    	 	if (request.getParameter("attr" + x) == null || 
		    	 	request.getParameter("attr" + x).trim().equals(""))
		    	   listAttributeID.add("");
		    	else
		    	{
			    	// Updated 5/18/10 - Decoder not seeming to work correctly
//			    	System.out.println(request.getParameter("attr" + x));
//			    	System.out.println(FindAndReplace.replace(request.getParameter("attr" + x),"%","%25"));
//			    	String setParm = FindAndReplace.replace(request.getParameter("attr" + x),"%","%25");
			    	
//			    	System.out.println("ISO:" + URLDecoder.decode(setParm, "ISO"));
//			    	System.out.println("US-ASCII" + URLDecoder.decode(setParm, "US-ASCII"));
                // Choose to take the Decoder OFF for this field
		    	   listAttributeID.add(request.getParameter("attr" + x));
//		       	listAttributeID.add(URLDecoder.decode(FindAndReplace.replace(request.getParameter("attr" + x), "%", "%25"), "ISO"));
		    	}
	    	 }
	       }
		}
	    catch(Exception e)
		{
	    	System.out.println("Error found:" + e);
		}
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");

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
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(
		String requestType,
		String resource,
		String resend) {
		// BUILD Edit/More Button Section(Column)  

		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[4];
		String[] urlNames = new String[4];
		String[] newPage = new String[4];
		for (int z = 0; z < 4; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
//		if (requestType.equals("list")) {
//			urlLinks[0] =
//				"/web/CtlResourceNew?requestType=update"
//					+ "&resource="
//					+ resource;
//			urlNames[0] = "Add / Update Resource (" + resource + ")";
//			newPage[0] = "Y";
//		}
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
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
	 * @return Returns the company.
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return Returns the division.
	 */
	public String getDivision() {
		return division;
	}
	/**
	 * @param division The division to set.
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	/**
	 * @return Returns the returnBean.
	 */
	public BeanCOA getReturnBean() {
		return returnBean;
	}
	/**
	 * @param returnBean The returnBean to set.
	 */
	public void setReturnBean(BeanCOA returnBean) {
		this.returnBean = returnBean;
	}
	/**
	 * @return Returns the attributeID.
	 */
	public String getAttributeID() {
		return attributeID;
	}
	/**
	 * @param attributeID The attributeID to set.
	 */
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}
	/**
	 * @return Returns the inqDistributionOrder.
	 */
	public String getInqDistributionOrder() {
		return inqDistributionOrder;
	}
	/**
	 * @param inqDistributionOrder The inqDistributionOrder to set.
	 */
	public void setInqDistributionOrder(String inqDistributionOrder) {
		this.inqDistributionOrder = inqDistributionOrder;
	}
	/**
	 * @return Returns the inqLot.
	 */
	public String getInqLot() {
		return inqLot;
	}
	/**
	 * @param inqLot The inqLot to set.
	 */
	public void setInqLot(String inqLot) {
		this.inqLot = inqLot;
	}
	/**
	 * @return Returns the inqSalesOrder.
	 */
	public String getInqSalesOrder() {
		return inqSalesOrder;
	}
	/**
	 * @param inqSalesOrder The inqSalesOrder to set.
	 */
	public void setInqSalesOrder(String inqSalesOrder) {
		this.inqSalesOrder = inqSalesOrder;
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
	 * @return Returns the lineAttrSequence.
	 */
	public String getLineAttrSequence() {
		return lineAttrSequence;
	}
	/**
	 * @param lineAttrSequence The lineAttrSequence to set.
	 */
	public void setLineAttrSequence(String lineAttrSequence) {
		this.lineAttrSequence = lineAttrSequence;
	}
	/**
	 * @return Returns the lineComment.
	 */
	public String getLineComment() {
		return lineComment;
	}
	/**
	 * @param lineComment The lineComment to set.
	 */
	public void setLineComment(String lineComment) {
		this.lineComment = lineComment;
	}
	/**
	 * @return Returns the lineNumber.
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return Returns the lineSuffix.
	 */
	public String getLineSuffix() {
		return lineSuffix;
	}
	/**
	 * @param lineSuffix The lineSuffix to set.
	 */
	public void setLineSuffix(String lineSuffix) {
		this.lineSuffix = lineSuffix;
	}
	/**
	 * @return Returns the listAttributeID.
	 */
	public Vector getListAttributeID() {
		return listAttributeID;
	}
	/**
	 * @param listAttributeID The listAttributeID to set.
	 */
	public void setListAttributeID(Vector listAttributeID) {
		this.listAttributeID = listAttributeID;
	}
	/**
	 * @return Returns the listAttributeSequence.
	 */
	public Vector getListAttributeSequence() {
		return listAttributeSequence;
	}
	/**
	 * @param listAttributeSequence The listAttributeSequence to set.
	 */
	public void setListAttributeSequence(Vector listAttributeSequence) {
		this.listAttributeSequence = listAttributeSequence;
	}
	/**
	 * @return Returns the listLots.
	 */
	public Vector getListLots() {
		return listLots;
	}
	/**
	 * @param listLots The listLots to set.
	 */
	public void setListLots(Vector listLots) {
		this.listLots = listLots;
	}
	/**
	 * @return Returns the numberOfAttributes.
	 */
	public String getNumberOfAttributes() {
		return numberOfAttributes;
	}
	/**
	 * @param numberOfAttributes The numberOfAttributes to set.
	 */
	public void setNumberOfAttributes(String numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
	}
	/**
	 * @return Returns the lineSignature.
	 */
	public String getLineSignature() {
		return lineSignature;
	}
	/**
	 * @param lineSignature The lineSignature to set.
	 */
	public void setLineSignature(String lineSignature) {
		this.lineSignature = lineSignature;
	}
	/**
	 * @return Returns the lineSpec.
	 */
	public String getLineSpec() {
		return lineSpec;
	}
	/**
	 * @param lineSpec The lineSpec to set.
	 */
	public void setLineSpec(String lineSpec) {
		this.lineSpec = lineSpec;
	}
	/**
	 * @return Returns the coaType.
	 */
	public String getCoaType() {
		return coaType;
	}
	/**
	 * @param coaType The coaType to set.
	 */
	public void setCoaType(String coaType) {
		this.coaType = coaType;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
