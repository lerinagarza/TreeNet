/*
 * Created on Aug 23, 2005
 *
 */
package com.treetop.app.resource;

import java.util.List;
import java.util.Vector;

import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBean;

/**
 *   Search for GTIN's - Global Trade Item Numbers
 * @author twalto
 *
 */
public class InqResource extends BaseViewBean  {
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

	// Standard Fields - to be in Every View Bean
	public String requestType              = "";
	public List   errors                   = null;	
	
	public String inqResource              = "";
	public String inqResourceDescription   = "";
	public String inqUnitOfMeasure         = "";
	public String inqResourceClass         = "";
	public String inqResourceSubClass      = "";
	public String inqOwner                 = "";
	public String inqIsUCCNetResource      = "";
	public String inqIsPublishedToUCCNet   = "";
	public String inqIsBestBy			   = "";
	public String inqMarketingStatus       = "";
	public String inqShowCost              = "";
	
	public String inqNewItemProjectOwner   = "";
	public String inqNewItemSetupDueDate   = "";
	public String inqNewItemProductionDate = "";	
	public String inqNewItemManufacturer   = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy    	    = "";
	public String orderStyle 	    = "";
	
	// List for the List Page JSP
	public Vector listReport             = null;
	
	
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
		return returnString.toString();
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(String requestType,
	                                     String gtin,
	                                     String resend) {
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[5];
		String[] urlNames = new String[5];
		String[] newPage  = new String[5];
		for (int z = 0; z < 5; z++)
		{
		   urlLinks[z] = "";
		   urlNames[z] = "";
		   newPage[z]  = ""; 	    
		}
		if (requestType.equals("list"))
		{
			urlLinks[0] = "/web/CtlGTIN?requestType=detail" +
						  "&gtinNumber=" + gtin.trim();
			urlNames[0] = "Details of " + gtin.trim();
			newPage[0]  = "Y";
			urlLinks[1] = "/web/CtlGTIN?requestType=update" +
						  "&gtinNumber=" + gtin.trim();
			urlNames[1] = "Update " + gtin.trim();
			newPage[1]  = "Y";	
			urlLinks[2] = "/web/CtlGTIN?requestType=add";
			urlNames[2] = "Add New GTIN";
			newPage[2]  = "Y";	
			urlLinks[3] = "/web/CtlGTIN?requestType=copy" + 
						  "&gtinNumber=" + gtin.trim();
			urlNames[3] = "Copy " + gtin.trim() +
						 " & Create New";
			newPage[3]  = "Y";			
			urlLinks[4] = "JavaScript:deleteTrans('/web/CtlGTIN?requestType=delete" +
					   "&gtinNumber=" + gtin.trim() +
					   resend + "')";
			urlNames[4] = "Delete " + gtin.trim();
			newPage[4]  = "N"; 
			
											
		}	
		
//		<a href="/web/servlet/com.treetop.servlets.CtlUCCNet?requestType=bindResource&globalTradeItemNumber=<%= thisrow.getGlobalTradeItemNumber().trim() %>&<%= parmResend %>">Add Resource to GTIN</a>
//		 <br/>
//		<a href="/web/servlet/com.treetop.servlets.CtlUCCNet?requestType=removeResource&globalTradeItemNumber=<%= thisrow.getGlobalTradeItemNumber().trim() %>&resource=<%= theresource.trim() %>&<%= parmResend %>">Remove Resource from GTIN</a>		
		
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

}
