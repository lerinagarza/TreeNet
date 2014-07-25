/*
 * Created on April 2, 2012
 */

package com.treetop.controller.contractmanufacturing;

import java.util.Vector;
import com.treetop.businessobjectapplications.BeanContractManufacturing;
import com.treetop.viewbeans.BaseViewBeanR2;

/**
 * @author twalto
 * 
 * Inquire on Contract Manufacturing Billing Information
 */
public class InqBilling extends BaseViewBeanR2 {
	// Standard Fields - to be in Every View Bean
	private String billingType						= "OSA";// Currently hard coded for Ocean Spray Alliance
	private String manufacturingOrderNumber			= "";
	private String manufacturingOrderNumberError	= "";
	
	private Vector listMOs							= new Vector();

	// Standard Fields for Inq View Bean
	private String orderBy 		   = "";
	private String orderStyle 	   = "";	
	
	//Button Values
	private String submit           = "";
	
	private BeanContractManufacturing beanInfo	= new BeanContractManufacturing();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		   if (this.getEnvironment().trim().equals(""))
			   this.setEnvironment("PRD");
		   
	//	   System.out.println("InqBilling.validate: Will need to make sure the MO qualifies as a Contract Manufacturing MO");
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 *  Use this method to Display -- however appropriate for
	 *     the JSP the Parameters Chosen
	 *   
	 *    original method Creation date: (10/25/2010 -- TWalton)
	 *    this specific method Creation date: 4/2/12 TWalton
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
//		String valueDisplay = "Method";
//		if (this.getRequestType().trim().equals("listProcedure"))
//			valueDisplay = "Procedure";
//		if (this.getRequestType().trim().equals("listPolicy"))
//			valueDisplay = "Policy";
//		if (!this.getInqStatus().trim().equals(""))
//		{
//			returnString.append("Status: ");
//			returnString.append(this.getInqStatus().trim());			  
//		}
		return returnString.toString();
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *    Will return a String to Resend the parameters
	 *    Mainly from the link on the heading Sort, 
	 *   OR if there is another list view.
	 *   
	 * @return - String
	 * 
	 * original method Creation date: (10/25/2010 -- TWalton)
	 *    this specific method Creation date: 4/2/12 TWalton
	 */
	public String buildParameterResend() {
			
		StringBuffer returnString = new StringBuffer();
//		if (!this.getInqStatus().trim().equals(""))
//		{
//			returnString.append("&inqStatus=");
//			returnString.append(this.getInqStatus().trim());
//		}
		return returnString.toString();
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public String getManufacturingOrderNumber() {
		return manufacturingOrderNumber;
	}
	public void setManufacturingOrderNumber(String manufacturingOrderNumber) {
		this.manufacturingOrderNumber = manufacturingOrderNumber;
	}
	public String getManufacturingOrderNumberError() {
		return manufacturingOrderNumberError;
	}
	public void setManufacturingOrderNumberError(
			String manufacturingOrderNumberError) {
		this.manufacturingOrderNumberError = manufacturingOrderNumberError;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public BeanContractManufacturing getBeanInfo() {
		return beanInfo;
	}
	public void setBeanInfo(BeanContractManufacturing beanInfo) {
		this.beanInfo = beanInfo;
	}
	public Vector getListMOs() {
		return listMOs;
	}
	public void setListMOs(Vector listMOs) {
		this.listMOs = listMOs;
	}

}
