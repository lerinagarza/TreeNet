/*
 * Created on September 4, 2007
 *
 * Will tie Directly to the Inquiry Page
 *   Choosing something 
 */
package com.treetop.app.coa;

import java.util.List;
import java.util.Vector;

import com.treetop.services.*;

import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InqCOA extends BaseViewBeanR1 {

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
	public String environment = "";
	
	// Inquiry Fields from the Inquiry Page
	public String inqLot = "";
	public String inqLotError = "";
	public String inqSalesOrder = "";
	public String inqSalesOrderError = "";
	public String inqDistributionOrder = "";
	public String inqDistributionOrderError = "";
	
	// use to get specific information
	public String lineNumber = "";
	public String lineSuffix = "";
	
	public String displayErrors = "";
	
	public String updateUser = "";

	// List for the List Page JSP
	public Vector listReport = null;

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
	  if (requestType.equals("build") ||
	  	  requestType.equals("preview"))
	  {	
		if (inqLot.trim().equals("") &&
			inqSalesOrder.trim().equals("") &&
			inqDistributionOrder.trim().equals(""))
		   displayErrors = "Please enter information before pressing Go. ";	
		else
		{
		   if (!inqSalesOrder.trim().equals(""))
		   {
			 inqLot = "";
			 inqDistributionOrder = "";
		   }
		   if (!inqDistributionOrder.trim().equals(""))
			 inqLot = "";
		   if (!inqSalesOrder.trim().equals(""))
		   {
		   	  try
			  {
		   	  	inqSalesOrderError = ServiceSalesOrder.verifySalesOrder(this.environment, inqSalesOrder.trim(), "salesordershipped");
		  // 	  	System.out.println(inqSalesOrderError);
			  }
		   	  catch(Exception e)
			  {
//		   	 Just Catch the error, do not need to do anything with it.
			  }
		   }
		   if (!inqDistributionOrder.trim().equals(""))
		   {
		   	  try
			  {
		   	  	inqDistributionOrderError = ServiceDistributionOrder.verifyDistributionOrder(this.environment, inqDistributionOrder.trim());
		   	 	//System.out.println(inqDistributionOrderError);
			  }
		   	  catch(Exception e)
			  {
//		   	 Just Catch the error, do not need to do anything with it.
			  }		   	
		   }
		   if (!inqLot.trim().equals(""))
		   {
		   	  try
			  {
		   	  	inqLotError = ServiceLot.verifyM3LotNumber(this.environment, inqLot.trim());
		   //	  	System.out.println(inqDistributionOrderError);
			  }
		   	  catch(Exception e)
			  {
//		   	 Just Catch the error, do not need to do anything with it.		   	  	
			  }
		   }
		   if (!inqSalesOrderError.trim().equals("") ||
		   	   !inqDistributionOrderError.trim().equals("") ||
			   !inqLotError.trim().equals(""))
		   	 displayErrors = "Please retry with another number. ";
		}
	  }		
	  return;
	}

	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!inqLot.equals("")) {
			returnString.append("Lot: ");
			returnString.append(inqLot);
		}
		if (!inqSalesOrder.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("SalesOrder: ");
			returnString.append(inqSalesOrder);
		}
		if (!inqDistributionOrder.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Distribution Order: ");
			returnString.append(inqDistributionOrder);
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
		if (!inqLot.equals("")) {
			returnString.append("&inqLot=");
			returnString.append(inqLot);
		}
		if (!inqSalesOrder.equals("")) {
			returnString.append("&inqSalesOrder=");
			returnString.append(inqSalesOrder);
		}
		if (!inqDistributionOrder.equals("")) {
			returnString.append("&inqDistributionOrder=");
			returnString.append(inqDistributionOrder);
		}
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
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
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
	 * @return Returns the displayErrors.
	 */
	public String getDisplayErrors() {
		return displayErrors;
	}
	/**
	 * @param displayErrors The displayErrors to set.
	 */
	public void setDisplayErrors(String displayErrors) {
		this.displayErrors = displayErrors;
	}
	/**
	 * @return Returns the inqDistributionOrderError.
	 */
	public String getInqDistributionOrderError() {
		return inqDistributionOrderError;
	}
	/**
	 * @param inqDistributionOrderError The inqDistributionOrderError to set.
	 */
	public void setInqDistributionOrderError(String inqDistributionOrderError) {
		this.inqDistributionOrderError = inqDistributionOrderError;
	}
	/**
	 * @return Returns the inqLotError.
	 */
	public String getInqLotError() {
		return inqLotError;
	}
	/**
	 * @param inqLotError The inqLotError to set.
	 */
	public void setInqLotError(String inqLotError) {
		this.inqLotError = inqLotError;
	}
	/**
	 * @return Returns the inqSalesOrderError.
	 */
	public String getInqSalesOrderError() {
		return inqSalesOrderError;
	}
	/**
	 * @param inqSalesOrderError The inqSalesOrderError to set.
	 */
	public void setInqSalesOrderError(String inqSalesOrderError) {
		this.inqSalesOrderError = inqSalesOrderError;
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
