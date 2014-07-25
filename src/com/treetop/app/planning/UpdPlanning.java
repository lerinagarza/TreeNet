/*
 * Created on July 7, 2008
 *
 *  To be used for the JSP's 
 * 		updForecast
 */
package com.treetop.app.planning;

import java.util.Vector;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.businessobjectapplications.BeanPlanning;
import com.treetop.businessobjects.DateTime;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.services.ServicePlanning;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceWarehouse;

/**
 * @author twalto
 *
 */
public class UpdPlanning extends BaseViewBeanR2 {

	// Standard Fields for Inq View Bean
	private String   	orderBy = "";
	private String   	orderStyle = "";

	private String		userProfile = "";

	private String   	updateButton = "";


	private String   	facility = "";
	private String   	itemType = "";
	private String   	orderStatus = "";
	private String 		submit = "";


	private String		fromDate = "";
	private String		toDate = "";

	// List for the List Page JSP
	private Vector listReport = null;

	private static Vector<HtmlOption>	itemTypes = null;
	private static Vector<HtmlOption>	facilities = null;
	private static Vector<HtmlOption>	orderStatuses = null;

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 *  Empty constructor for those who need to use this class
	 *   and don't have a request to pass
	 */	
	public UpdPlanning(){}


	/**
	 *  Constructor -- takes the request and runs appropriate instantiation  
	 *    based on requestType (moves some standard ops out of the servlet,
	 *   for reusability of code)
	 *   
	 */	
	public UpdPlanning(HttpServletRequest request){

		this.populate(request);
		this.validate();

	}


	public static Vector<HtmlOption> dropDownOrderStatuses(){
		try
		{
			if (orderStatuses == null){
				Vector<HtmlOption> options = new Vector<HtmlOption>();
				HtmlOption option = new HtmlOption("00","Database error");
				options.addElement(option);
				option = new HtmlOption("05","Planned order, no material explosion");
				options.addElement(option);
				option = new HtmlOption("10","Planned order");
				options.addElement(option);
				option = new HtmlOption("15","Firmed quantity planned order");
				options.addElement(option);
				option = new HtmlOption("20","Firmed planned order");
				options.addElement(option);
				option = new HtmlOption("30","Release date is within lead time");
				options.addElement(option);
				option = new HtmlOption("59","Change in progress");
				options.addElement(option);
				option = new HtmlOption("60","Released");
				options.addElement(option);
				option = new HtmlOption("90","Flagged for deletion");
				options.addElement(option);
				orderStatuses = options;
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return orderStatuses;		
	}
	/**
	 *  This will populate 
	 *    Will return a String to Resend the parameters
	 *    Mainly from the link on the heading Sort, 
	 *   OR if there is another list view.
	 * @return
	 */
	public static Vector<HtmlOption> dropDownItemType() {
		try
		{
			if (itemTypes == null){
				CommonRequestBean crb = new CommonRequestBean();
				itemTypes = ServiceItem.dropDownItemTypeHtmlOption(crb);
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return itemTypes;
	}
	public static Vector<HtmlOption> dropDownFacility() {
		try
		{
			if (facilities == null){
				CommonRequestBean crb = new CommonRequestBean();
				facilities = ServiceWarehouse.dropDownProductionFacility(crb);
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return facilities;
	}
	/* 
	 * Validation of Data  
	 * 
	 * Set the Default - When information is BLANK 
	 */
	public void validate() {
		
		StringBuffer error = new StringBuffer();
		if (!this.getSubmit().trim().equals("")) {
			
			//Default PRD as environment if not specified
			if(this.getEnvironment().trim().equals("")) {
				this.setEnvironment("PRD");
			}
			
			
			//Check to see if there is at least one parameter passed in
			// 11/19/12 - TWALTON -- removed all parameters
//			if (this.getFacility().equals("") &&
//				this.getFromDate().equals("") &&
//				this.getToDate().equals("") &&
//				this.getItemType().equals("") &&
//				this.getOrderStatus().equals("")) {
//				error.append("Please input at least one parameter");
//			}
	
			
		}

		if (!error.toString().trim().equals("")) {
			this.setErrorMessage(error.toString());
		}

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
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the orderStyle
	 */
	public String getOrderStyle() {
		return orderStyle;
	}
	/**
	 * @param orderStyle the orderStyle to set
	 */
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	/**
	 * @return the userProfile
	 */
	public String getUserProfile() {
		return userProfile;
	}
	/**
	 * @param userProfile the userProfile to set
	 */
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	/**
	 * @return the updateButton
	 */
	public String getUpdateButton() {
		return updateButton;
	}
	/**
	 * @param updateButton the updateButton to set
	 */
	public void setUpdateButton(String updateButton) {
		this.updateButton = updateButton;
	}

	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	 * @return the listReport
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport the listReport to set
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}	


}
