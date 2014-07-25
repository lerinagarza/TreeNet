/*
 * Created on December 3, 2007
 *
 *  To be used for the JSP's 
 * 		displayPromo
 */
package com.treetop.app.promotion;

import java.util.Vector;

import com.treetop.services.ServiceSalesOrder;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 *
 */
public class InqPromotion extends BaseViewBeanR1 {

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
	
	// Inquiry Fields from the Inquiry Page
	public String inqCompany = "";
	public String inqDivision = "";
	public String inqPromotion = "";
	public String inqWarningMessage = "";
	public String inqSalesOrder = "";
	public String inqSalesOrderError = "";
	public String inqShowAll = "";

	// List for the List Page JSP
	public Vector listReport = null;

	/* 
	 * Validation of Data  
	 * 
	 * Set the Default - When information is BLANK 
	 */
	public void validate() {
		
		if (this.inqCompany.trim().equals(""))
			this.inqCompany = "100";
		if (this.inqDivision.trim().equals(""))
			this.inqDivision = "100";
		if (!inqSalesOrder.trim().equals(""))
		{
			try
			{
		   		inqSalesOrderError = ServiceSalesOrder.verifySalesOrder("PRD", inqSalesOrder.trim(), "");
		   	//  	System.out.println(inqSalesOrderError);
			}
		   	catch(Exception e)
			{
//		   	 Just Catch the error, do not need to do anything with it.
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
		if (!this.inqCompany.equals("")) {
			returnString.append("Company: <b>");
			returnString.append(this.inqCompany);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");
		}
		if (!this.inqDivision.equals("")) {
		//	if (!returnString.toString().equals(""))
		//		returnString.append("<br>");
			returnString.append("Division: <b>");
			returnString.append(this.inqDivision);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");
		}
		if (!this.inqDivision.equals("")) {
		//	if (!returnString.toString().equals(""))
		//		returnString.append("<br>");
			returnString.append("Sales Order Number: <b>");
			returnString.append(this.inqSalesOrder);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");			
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
		if (!this.inqCompany.equals("")) {
			returnString.append("&inqCompany=");
			returnString.append(this.inqCompany);
		}
		if (!this.inqDivision.equals("")) {
			returnString.append("&inqDivision=");
			returnString.append(this.inqDivision);
		}
		return returnString.toString();
	}
	/**
	 * @return Returns the inqCompany.
	 */
	public String getInqCompany() {
		return this.inqCompany;
	}
	/**
	 * @param inqCompany The inqCompany to set.
	 */
	public void setInqCompany(String inqCompany) {
		this.inqCompany = inqCompany;
	}
	/**
	 * @return Returns the inqDivision.
	 */
	public String getInqDivision() {
		return this.inqDivision;
	}
	/**
	 * @param inqDivision The inqDivision to set.
	 */
	public void setInqDivision(String inqDivision) {
		this.inqDivision = inqDivision;
	}
	/**
	 * @return Returns the inqPromotion.
	 */
	public String getInqPromotion() {
		return this.inqPromotion;
	}
	/**
	 * @param inqPromotion The inqPromotion to set.
	 */
	public void setInqPromotion(String inqPromotion) {
		this.inqPromotion = inqPromotion;
	}
	/**
	 * @return Returns the inqWarningMessage.
	 */
	public String getInqWarningMessage() {
		return this.inqWarningMessage;
	}
	/**
	 * @param inqWarningMessage The inqWarningMessage to set.
	 */
	public void setInqWarningMessage(String inqWarningMessage) {
		this.inqWarningMessage = inqWarningMessage;
	}
	/**
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return this.listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}
	/**
	 * @return Returns the orderBy.
	 */
	public String getOrderBy() {
		return this.orderBy;
	}
	/**
	 * @param orderBy The orderBy to set.
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return Returns the orderStyle.
	 */
	public String getOrderStyle() {
		return this.orderStyle;
	}
	/**
	 * @param orderStyle The orderStyle to set.
	 */
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	/**
	 * @return Returns the requestType.
	 */
	public String getRequestType() {
		return this.requestType;
	}
	/**
	 * @param requestType The requestType to set.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	/**
	 * @return Returns the inqSalesOrder.
	 */
	public String getInqSalesOrder() {
		return this.inqSalesOrder;
	}
	/**
	 * @param inqSalesOrder The inqSalesOrder to set.
	 */
	public void setInqSalesOrder(String inqSalesOrder) {
		this.inqSalesOrder = inqSalesOrder;
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
	 * @return Returns the inqShowAll.
	 */
	public String getInqShowAll() {
		return inqShowAll;
	}
	/**
	 * @param inqShowAll The inqShowAll to set.
	 */
	public void setInqShowAll(String inqShowAll) {
		this.inqShowAll = inqShowAll;
	}
}
