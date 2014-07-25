/*
 * Created on January 15, 2008
 *
 * Will tie to the Build Screen for the COA 
 * 		Update - the Display for a COA 
 * 		
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.coa;

import java.util.List;
import java.util.Vector;

import com.treetop.businessobjectapplications.*;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceSalesOrder;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.*;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BuildCOA extends BaseViewBeanR1 {

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
	
	public String coaType = "";
	public String company = "100";
	public String division = "100";
	public String environment = "";
	
	// Inquiry Fields from the Inquiry Page
	public String inqLot = "";
	public String inqSalesOrder = "";
	public String inqDistributionOrder = "";
	
	public String buildAttn1 = "";
	public String buildAttn2 = "";
	public String buildAttn3 = "";
	public String buildAttn4 = "";
	public String buildCOO   = "";
	public String buildCOAComment = "";
	public String buildDateFormat = "";
	public String buildEmail1 = "";
	public String buildEmail1Error = "";
	public String buildEmail2 = "";
	public String buildEmail2Error = "";
	public String buildEmail3 = "";
	public String buildEmail3Error = "";
	public String buildEmail4 = "";
	public String buildEmail4Error = "";
	public String buildEmail5 = "";
	public String buildEmail5Error = "";
	public String buildEmail6 = "";
	public String buildEmail6Error = "";
	public String buildEmail7 = "";
	public String buildEmail7Error = "";
	public String buildEmail8 = "";
	public String buildEmail8Error = "";
	public String buildFax1 = "";
	public String buildFax1Error = "";
	public String buildFax2 = "";
	public String buildFax2Error = "";
	public String buildFax3 = "";
	public String buildFax3Error = "";
	public String buildFax4 = "";
	public String buildFax4Error = "";
	public String buildShowAmount = "";
	public String buildShowAttributeModel = "";
	public String buildShowCustomerSpec = "";
	public String buildShowAverage = "";
	public String buildShowMinMax = "";
	public String buildShowTarget = "";
	public String buildSignature = "";
	
	public String saveChanges = "";
	
	public String displayErrors = "";
	
	public String updateUser = "";

	// List for the List Page JSP
	public Vector listReport = null;

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
		if (!this.buildEmail1.trim().equals(""))
		   this.buildEmail1Error = ValidateFields.validateEMail(this.buildEmail1.trim());
		if (!this.buildEmail2.trim().equals(""))
		   this.buildEmail2Error = ValidateFields.validateEMail(this.buildEmail2.trim());	
		if (!this.buildEmail3.trim().equals(""))
		   this.buildEmail3Error = ValidateFields.validateEMail(this.buildEmail3.trim());
		if (!this.buildEmail4.trim().equals(""))
		   this.buildEmail4Error = ValidateFields.validateEMail(this.buildEmail4.trim());			
		if (!this.buildEmail5.trim().equals(""))
		   this.buildEmail5Error = ValidateFields.validateEMail(this.buildEmail5.trim());
		if (!this.buildEmail6.trim().equals(""))
		   this.buildEmail6Error = ValidateFields.validateEMail(this.buildEmail6.trim());	
		if (!this.buildEmail7.trim().equals(""))
		   this.buildEmail7Error = ValidateFields.validateEMail(this.buildEmail7.trim());
		if (!this.buildEmail8.trim().equals(""))
		   this.buildEmail8Error = ValidateFields.validateEMail(this.buildEmail8.trim());			
		if (!this.buildEmail1Error.trim().equals("") ||
			!this.buildEmail2Error.trim().equals("") ||	
			!this.buildEmail3Error.trim().equals("") ||
			!this.buildEmail4Error.trim().equals("") ||
			!this.buildEmail5Error.trim().equals("") ||
			!this.buildEmail6Error.trim().equals("") ||
			!this.buildEmail7Error.trim().equals(""))
			displayErrors = "An error was found on the format of an Email Address. ";
		
			
		// Set the Click Boxes to 1 
		if (!this.buildShowAttributeModel.trim().equals(""))
			this.buildShowAttributeModel = "1";
		if (!this.buildShowCustomerSpec.trim().equals(""))
			this.buildShowCustomerSpec = "1";
		if (!this.buildShowAverage.trim().equals(""))
			this.buildShowAverage = "1";
		if (!this.buildShowMinMax.trim().equals(""))
			this.buildShowMinMax = "1";
		if (!this.buildShowTarget.trim().equals(""))
			this.buildShowTarget = "1";
		
		if (!this.inqLot.equals(""))
		   this.coaType = "LOT";
		if (!this.inqDistributionOrder.equals(""))
		   this.coaType = "DO";
		if (!this.inqSalesOrder.equals(""))
		   this.coaType = "CO";
				
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
		return ServiceSalesOrder.editLengthSalesOrder(inqSalesOrder);
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
	 * @return Returns the buildAttn1.
	 */
	public String getBuildAttn1() {
		return buildAttn1;
	}
	/**
	 * @param buildAttn1 The buildAttn1 to set.
	 */
	public void setBuildAttn1(String buildAttn1) {
		this.buildAttn1 = buildAttn1;
	}
	/**
	 * @return Returns the buildAttn2.
	 */
	public String getBuildAttn2() {
		return buildAttn2;
	}
	/**
	 * @param buildAttn2 The buildAttn2 to set.
	 */
	public void setBuildAttn2(String buildAttn2) {
		this.buildAttn2 = buildAttn2;
	}
	/**
	 * @return Returns the buildAttn3.
	 */
	public String getBuildAttn3() {
		return buildAttn3;
	}
	/**
	 * @param buildAttn3 The buildAttn3 to set.
	 */
	public void setBuildAttn3(String buildAttn3) {
		this.buildAttn3 = buildAttn3;
	}
	/**
	 * @return Returns the buildAttn4.
	 */
	public String getBuildAttn4() {
		return buildAttn4;
	}
	/**
	 * @param buildAttn4 The buildAttn4 to set.
	 */
	public void setBuildAttn4(String buildAttn4) {
		this.buildAttn4 = buildAttn4;
	}
	/**
	 * @return Returns the buildCOAComment.
	 */
	public String getBuildCOAComment() {
		return buildCOAComment;
	}
	/**
	 * @param buildCOAComment The buildCOAComment to set.
	 */
	public void setBuildCOAComment(String buildCOAComment) {
		this.buildCOAComment = buildCOAComment;
	}
	/**
	 * @return Returns the buildDateFormat.
	 */
	public String getBuildDateFormat() {
		return buildDateFormat;
	}
	/**
	 * @param buildDateFormat The buildDateFormat to set.
	 */
	public void setBuildDateFormat(String buildDateFormat) {
		this.buildDateFormat = buildDateFormat;
	}
	/**
	 * @return Returns the buildEmail1.
	 */
	public String getBuildEmail1() {
		return buildEmail1;
	}
	/**
	 * @param buildEmail1 The buildEmail1 to set.
	 */
	public void setBuildEmail1(String buildEmail1) {
		this.buildEmail1 = buildEmail1;
	}
	/**
	 * @return Returns the buildEmail1Error.
	 */
	public String getBuildEmail1Error() {
		return buildEmail1Error;
	}
	/**
	 * @param buildEmail1Error The buildEmail1Error to set.
	 */
	public void setBuildEmail1Error(String buildEmail1Error) {
		this.buildEmail1Error = buildEmail1Error;
	}
	/**
	 * @return Returns the buildEmail2.
	 */
	public String getBuildEmail2() {
		return buildEmail2;
	}
	/**
	 * @param buildEmail2 The buildEmail2 to set.
	 */
	public void setBuildEmail2(String buildEmail2) {
		this.buildEmail2 = buildEmail2;
	}
	/**
	 * @return Returns the buildEmail2Error.
	 */
	public String getBuildEmail2Error() {
		return buildEmail2Error;
	}
	/**
	 * @param buildEmail2Error The buildEmail2Error to set.
	 */
	public void setBuildEmail2Error(String buildEmail2Error) {
		this.buildEmail2Error = buildEmail2Error;
	}
	/**
	 * @return Returns the buildEmail3.
	 */
	public String getBuildEmail3() {
		return buildEmail3;
	}
	/**
	 * @param buildEmail3 The buildEmail3 to set.
	 */
	public void setBuildEmail3(String buildEmail3) {
		this.buildEmail3 = buildEmail3;
	}
	/**
	 * @return Returns the buildEmail3Error.
	 */
	public String getBuildEmail3Error() {
		return buildEmail3Error;
	}
	/**
	 * @param buildEmail3Error The buildEmail3Error to set.
	 */
	public void setBuildEmail3Error(String buildEmail3Error) {
		this.buildEmail3Error = buildEmail3Error;
	}
	/**
	 * @return Returns the buildEmail4.
	 */
	public String getBuildEmail4() {
		return buildEmail4;
	}
	/**
	 * @param buildEmail4 The buildEmail4 to set.
	 */
	public void setBuildEmail4(String buildEmail4) {
		this.buildEmail4 = buildEmail4;
	}
	/**
	 * @return Returns the buildEmail4Error.
	 */
	public String getBuildEmail4Error() {
		return buildEmail4Error;
	}
	/**
	 * @param buildEmail4Error The buildEmail4Error to set.
	 */
	public void setBuildEmail4Error(String buildEmail4Error) {
		this.buildEmail4Error = buildEmail4Error;
	}
	/**
	 * @return Returns the buildEmail5.
	 */
	public String getBuildEmail5() {
		return buildEmail5;
	}
	/**
	 * @param buildEmail5 The buildEmail5 to set.
	 */
	public void setBuildEmail5(String buildEmail5) {
		this.buildEmail5 = buildEmail5;
	}
	/**
	 * @return Returns the buildEmail5Error.
	 */
	public String getBuildEmail5Error() {
		return buildEmail5Error;
	}
	/**
	 * @param buildEmail5Error The buildEmail5Error to set.
	 */
	public void setBuildEmail5Error(String buildEmail5Error) {
		this.buildEmail5Error = buildEmail5Error;
	}
	/**
	 * @return Returns the buildEmail6.
	 */
	public String getBuildEmail6() {
		return buildEmail6;
	}
	/**
	 * @param buildEmail6 The buildEmail6 to set.
	 */
	public void setBuildEmail6(String buildEmail6) {
		this.buildEmail6 = buildEmail6;
	}
	/**
	 * @return Returns the buildEmail6Error.
	 */
	public String getBuildEmail6Error() {
		return buildEmail6Error;
	}
	/**
	 * @param buildEmail6Error The buildEmail6Error to set.
	 */
	public void setBuildEmail6Error(String buildEmail6Error) {
		this.buildEmail6Error = buildEmail6Error;
	}
	/**
	 * @return Returns the buildEmail7.
	 */
	public String getBuildEmail7() {
		return buildEmail7;
	}
	/**
	 * @param buildEmail7 The buildEmail7 to set.
	 */
	public void setBuildEmail7(String buildEmail7) {
		this.buildEmail7 = buildEmail7;
	}
	/**
	 * @return Returns the buildEmail7Error.
	 */
	public String getBuildEmail7Error() {
		return buildEmail7Error;
	}
	/**
	 * @param buildEmail7Error The buildEmail7Error to set.
	 */
	public void setBuildEmail7Error(String buildEmail7Error) {
		this.buildEmail7Error = buildEmail7Error;
	}
	/**
	 * @return Returns the buildEmail8.
	 */
	public String getBuildEmail8() {
		return buildEmail8;
	}
	/**
	 * @param buildEmail8 The buildEmail8 to set.
	 */
	public void setBuildEmail8(String buildEmail8) {
		this.buildEmail8 = buildEmail8;
	}
	/**
	 * @return Returns the buildEmail8Error.
	 */
	public String getBuildEmail8Error() {
		return buildEmail8Error;
	}
	/**
	 * @param buildEmail8Error The buildEmail8Error to set.
	 */
	public void setBuildEmail8Error(String buildEmail8Error) {
		this.buildEmail8Error = buildEmail8Error;
	}
	/**
	 * @return Returns the buildFax1.
	 */
	public String getBuildFax1() {
		return buildFax1;
	}
	/**
	 * @param buildFax1 The buildFax1 to set.
	 */
	public void setBuildFax1(String buildFax1) {
		this.buildFax1 = buildFax1;
	}
	/**
	 * @return Returns the buildFax1Error.
	 */
	public String getBuildFax1Error() {
		return buildFax1Error;
	}
	/**
	 * @param buildFax1Error The buildFax1Error to set.
	 */
	public void setBuildFax1Error(String buildFax1Error) {
		this.buildFax1Error = buildFax1Error;
	}
	/**
	 * @return Returns the buildFax2.
	 */
	public String getBuildFax2() {
		return buildFax2;
	}
	/**
	 * @param buildFax2 The buildFax2 to set.
	 */
	public void setBuildFax2(String buildFax2) {
		this.buildFax2 = buildFax2;
	}
	/**
	 * @return Returns the buildFax2Error.
	 */
	public String getBuildFax2Error() {
		return buildFax2Error;
	}
	/**
	 * @param buildFax2Error The buildFax2Error to set.
	 */
	public void setBuildFax2Error(String buildFax2Error) {
		this.buildFax2Error = buildFax2Error;
	}
	/**
	 * @return Returns the buildFax3.
	 */
	public String getBuildFax3() {
		return buildFax3;
	}
	/**
	 * @param buildFax3 The buildFax3 to set.
	 */
	public void setBuildFax3(String buildFax3) {
		this.buildFax3 = buildFax3;
	}
	/**
	 * @return Returns the buildFax3Error.
	 */
	public String getBuildFax3Error() {
		return buildFax3Error;
	}
	/**
	 * @param buildFax3Error The buildFax3Error to set.
	 */
	public void setBuildFax3Error(String buildFax3Error) {
		this.buildFax3Error = buildFax3Error;
	}
	/**
	 * @return Returns the buildFax4.
	 */
	public String getBuildFax4() {
		return buildFax4;
	}
	/**
	 * @param buildFax4 The buildFax4 to set.
	 */
	public void setBuildFax4(String buildFax4) {
		this.buildFax4 = buildFax4;
	}
	/**
	 * @return Returns the buildFax4Error.
	 */
	public String getBuildFax4Error() {
		return buildFax4Error;
	}
	/**
	 * @param buildFax4Error The buildFax4Error to set.
	 */
	public void setBuildFax4Error(String buildFax4Error) {
		this.buildFax4Error = buildFax4Error;
	}
	/**
	 * @return Returns the buildShowAmount.
	 */
	public String getBuildShowAmount() {
		return buildShowAmount;
	}
	/**
	 * @param buildShowAmount The buildShowAmount to set.
	 */
	public void setBuildShowAmount(String buildShowAmount) {
		this.buildShowAmount = buildShowAmount;
	}
	/**
	 * @return Returns the buildShowAttributeModel.
	 */
	public String getBuildShowAttributeModel() {
		return buildShowAttributeModel;
	}
	/**
	 * @param buildShowAttributeModel The buildShowAttributeModel to set.
	 */
	public void setBuildShowAttributeModel(String buildShowAttributeModel) {
		this.buildShowAttributeModel = buildShowAttributeModel;
	}
	/**
	 * @return Returns the buildShowAverage.
	 */
	public String getBuildShowAverage() {
		return buildShowAverage;
	}
	/**
	 * @param buildShowAverage The buildShowAverage to set.
	 */
	public void setBuildShowAverage(String buildShowAverage) {
		this.buildShowAverage = buildShowAverage;
	}
	/**
	 * @return Returns the buildShowMinMax.
	 */
	public String getBuildShowMinMax() {
		return buildShowMinMax;
	}
	/**
	 * @param buildShowMinMax The buildShowMinMax to set.
	 */
	public void setBuildShowMinMax(String buildShowMinMax) {
		this.buildShowMinMax = buildShowMinMax;
	}
	/**
	 * @return Returns the buildShowTarget.
	 */
	public String getBuildShowTarget() {
		return buildShowTarget;
	}
	/**
	 * @param buildShowTarget The buildShowTarget to set.
	 */
	public void setBuildShowTarget(String buildShowTarget) {
		this.buildShowTarget = buildShowTarget;
	}
	/**
	 * @return Returns the buildSignature.
	 */
	public String getBuildSignature() {
		return buildSignature;
	}
	/**
	 * @param buildSignature The buildSignature to set.
	 */
	public void setBuildSignature(String buildSignature) {
		this.buildSignature = buildSignature;
	}

	/**
	 * @return Returns the buildCOO.
	 */
	public String getBuildCOO() {
		return buildCOO;
	}
	/**
	 * @param buildCOO The buildCOO to set.
	 */
	public void setBuildCOO(String buildCOO) {
		this.buildCOO = buildCOO;
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
	/**
	 * @return Returns the buildShowCustomerSpec.
	 */
	public String getBuildShowCustomerSpec() {
		return buildShowCustomerSpec;
	}
	/**
	 * @param buildShowCustomerSpec The buildShowCustomerSpec to set.
	 */
	public void setBuildShowCustomerSpec(String buildShowCustomerSpec) {
		this.buildShowCustomerSpec = buildShowCustomerSpec;
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
	 * @return Returns the saveChanges.
	 */
	public String getSaveChanges() {
		return saveChanges;
	}
	/**
	 * @param saveChanges The saveChanges to set.
	 */
	public void setSaveChanges(String saveChanges) {
		this.saveChanges = saveChanges;
	}
	/*  
	 *  Send in a Business Object Bean
	 *  Use the fields from this Bean to set into a
	 *    View Bean for Update
	 * Creation date: (2/1/2008 TWalton)
	 */
	public void loadUpdateFromBeanCOA(BeanCOA inBean) {
		try {
			if (inBean.getListCOADetailAttributes().size() > 0)
			{	
			COADetailAttributes updInfo = (COADetailAttributes) inBean.getListCOADetailAttributes().elementAt(0);
			coaType = updInfo.getCoaType();
			buildAttn1 = updInfo.getAttention1();
			buildAttn2 = updInfo.getAttention2();
			buildAttn3 = updInfo.getAttention3();
			buildAttn4 = updInfo.getAttention4();
			buildCOO = updInfo.getCountryOfOrigin();
			buildCOAComment = updInfo.getComment();
			buildDateFormat = updInfo.getDateFormat();
			buildEmail1 = updInfo.getEmail1();
			buildEmail2 = updInfo.getEmail2();
			buildEmail3 = updInfo.getEmail3();
			buildEmail4 = updInfo.getEmail4();
			buildEmail5 = updInfo.getEmail5();
			buildEmail6 = updInfo.getEmail6();
			buildEmail7 = updInfo.getEmail7();
			buildEmail8 = updInfo.getEmail8();
			buildFax1 = updInfo.getFax1();
			buildFax2 = updInfo.getFax2();
			buildFax3 = updInfo.getFax3();
			buildFax4 = updInfo.getFax4();
			buildShowAmount = updInfo.getShowUnits();
			buildShowAttributeModel = updInfo.getShowAttributeModel();
			buildShowCustomerSpec = updInfo.getShowSpec();
			buildShowAverage = updInfo.getShowAverage();
			buildShowMinMax = updInfo.getShowMinMax();
			buildShowTarget = updInfo.getShowTarget();
		    buildSignature = updInfo.getSignatureQA();	
			}

		} catch (Exception e) {
			System.out.println("e: " + e);
		}
		return;
	}

	/**
	 * Return a String 
	 *    The HTML Build Drop Down List
	 *
	 * Creation date: (2/5/2008 - TWalton)
	 * @return String
	 */
	public String buildDropDownQASignature(String theQAPerson,
										   String whseShippedFrom) 
	{
		// 9/11/12 --  Adjust the List to be Alphabetic
		//Use Whse Shipped from to Determine Default for the QA person
	    String startOn = theQAPerson;
		Vector ddList = new Vector(); //Vector of DropDownSingle Classes
		DropDownSingle addOne = new DropDownSingle();
		   addOne.setDescription("Choose a QA Manager");
		   addOne.setValue("");
		   ddList.addElement(addOne);
		addOne = new DropDownSingle();		
			addOne.setDescription("Alba Osorio");
			addOne.setValue("AOSORI");
			if (startOn.trim().equals("") &&
				whseShippedFrom.equals("290")) // Default for Woodburn
			  startOn = "AOSORI";
			ddList.addElement(addOne);   
		addOne = new DropDownSingle();		
			addOne.setDescription("Brett Feigner");
			addOne.setValue("BFEIGN");
			if (startOn.trim().equals("") &&
			    whseShippedFrom.equals("490")) // Default for Fresh Slice 
			  startOn = "BFEIGN";
			ddList.addElement(addOne);
		 addOne = new DropDownSingle();		
			addOne.setDescription("Dawn McElreath"); // Medford but not Default
			addOne.setValue("DMCELR");
			ddList.addElement(addOne);	
		 addOne = new DropDownSingle();		
		 // 1/31/13 - TW - Changed last name to Lemaster
			//addOne.setDescription("Emily Stengle");
		 // 05/07/14 - WTH - Changed lasr name to Faris
			//addOne.setDescription("Emily Lemaster");
		 	addOne.setDescription("Emily Faris");
			//addOne.setValue("ESTENG");
			addOne.setValue("ELEMAS"); //wth leave profile as ELAMAS not EFARIS
			if (startOn.trim().equals("") &&
				whseShippedFrom.equals("469")) // Default for Prosser
			  //startOn = "ESTENG";
			  startOn = "ELEMAS";
			ddList.addElement(addOne);	
		// added 5/27/11	
		 addOne = new DropDownSingle();		
			addOne.setDescription("Harsimran Singh Randhawa"); // Oxnard but not Default
			addOne.setValue("HSINGH");
			ddList.addElement(addOne);				
		// added 11/9/11 TW	
		 addOne = new DropDownSingle();		
			addOne.setDescription("Julie Casagrande");
			addOne.setValue("JCASAG");
			if (startOn.trim().equals("") &&
				whseShippedFrom.equals("251")) // Default for Oxnard
			  startOn = "JCASAG";
			ddList.addElement(addOne);		
		 addOne = new DropDownSingle();
			addOne.setDescription("Kim Beausoleil");
			addOne.setValue("KBEAUS");
			if (startOn.trim().equals("") &&
				whseShippedFrom.equals("230")) // Default for Wenatchee
			  startOn = "KBEAUS";
			ddList.addElement(addOne);	
		 addOne = new DropDownSingle();		
			addOne.setDescription("Kristin Erickson");
			addOne.setValue("KERICK");
			ddList.addElement(addOne);	
		 addOne = new DropDownSingle();		
			addOne.setDescription("Laurie Crollard");
			addOne.setValue("LCROLL");
			if (startOn.trim().equals("") &&
				whseShippedFrom.equals("240")) // Default for Ross 
			  startOn = "LCROLL";
			ddList.addElement(addOne);	
		 addOne = new DropDownSingle();
			addOne.setDescription("Mike Frausto");
			addOne.setValue("MFRAUS"); // Ross but not Default
			ddList.addElement(addOne);			
		 addOne = new DropDownSingle();		
			addOne.setDescription("Scott Lemke");
			addOne.setValue("SLEMKE"); // Wenatchee but Not Default
			ddList.addElement(addOne);
			//  9/17/12 Added Vicki - TW
		addOne = new DropDownSingle();
			addOne.setDescription("Vicki Guy");
			addOne.setValue("VGUY");
			if (startOn.trim().equals("") &&
				whseShippedFrom.equals("280")) // Default for Medford
			  startOn = "VGUY";
			ddList.addElement(addOne);		
		 addOne = new DropDownSingle();		
			addOne.setDescription("Greg Melroy");
			addOne.setValue("GMELRO");
			ddList.addElement(addOne);			
			
		addOne = new DropDownSingle();
			addOne.setDescription("Macrina Osorio");
			addOne.setValue("MOSORI");
			ddList.addElement(addOne);
//		addOne = new DropDownSingle();		
//			addOne.setDescription("Brigette Fresz");
//			addOne.setValue("BFRESZ");
//			if (startOn.trim().equals("") &&
//				whseShippedFrom.equals("280"))
//			  startOn = "BFRESZ";
//			ddList.addElement(addOne);
			// commented out no longer with the company 11/9/11
//		addOne = new DropDownSingle();		
//			addOne.setDescription("Fiorella Cerpa");
//			addOne.setValue("FCERPA");
//			if (startOn.trim().equals("") &&
//				whseShippedFrom.equals("251"))
//			  startOn = "FCERPA";
//			ddList.addElement(addOne);
	//	addOne = new DropDownSingle();			
	//		addOne.setDescription("Bela Chandra");
	//		addOne.setValue("BCHAND");
	//		if (startOn.trim().equals("") &&
	//			whseShippedFrom.equals("205"))
	//		  startOn = "BCHAND";
	//		ddList.addElement(addOne);
//			if (startOn.trim().equals("") &&
//				whseShippedFrom.equals("280"))
//			  startOn = "SLEMKE";
		
	  	return DropDownSingle.buildDropDown(ddList, "buildSignature", startOn, "None", "N", "N");  
	}

	/**
	 * Return a String 
	 *    The HTML Build Drop Down List
	 *
	 * Creation date: (2/6/2008 - TWalton)
	 * @return String
	 */
	public String buildDropDownShowAmount(String showAmount) 
	{
		//Use Whse Shipped from to Determine Default for the QA person
	    String startOn = showAmount;
		Vector ddList = new Vector(); //Vector of DropDownSingle Classes
		DropDownSingle addOne = new DropDownSingle();
			addOne.setDescription("Show Only Cases");
			addOne.setValue("0");
			ddList.addElement(addOne);
		addOne = new DropDownSingle();
			addOne.setDescription("Show Cases and Pounds");
			addOne.setValue("1");
			ddList.addElement(addOne);
		addOne = new DropDownSingle();
			addOne.setDescription("Show Cases and Kilograms");
			addOne.setValue("2");
			ddList.addElement(addOne);			
		
	  	return DropDownSingle.buildDropDown(ddList, "buildShowAmount", startOn, "None", "N", "N");  
	}

	/**
	 * Convert the date from an 8 digit integer to
	 *    xx/xx/xxxx
	 * Creation date: (12/19/2003 9:18:36 AM)
	 * 	Moved to the View Bean 2/25/08 TWalton
	 * @return java.lang.String
	 */
	public static String convertDate(String dateIn,
		                             Integer formatValue) 
	{
	   String dateOut  = "";
	   String thisDate = dateIn;
	   try
	   {
	      if(dateIn.length() == 8)
	      {	   
	         String year     = dateIn.substring(0, 4);
	         String month    = dateIn.substring(4, 6);
	         String day      = dateIn.substring(6, 8);
	         thisDate = year + "-" + month +"-" + day;
	      }
	   
		  if (formatValue.intValue() == 0)
		     formatValue = new Integer(5);
	      java.sql.Date thisDateIn  = java.sql.Date.valueOf(thisDate);
		  String[]      getFormats  = GetDate.getDates(thisDateIn);    	
	      dateOut                   = getFormats[formatValue.intValue()];
	   }
	   catch(Exception e)
	   {
		   
	   }
		
		return dateOut;
	}

	/**
	 * Convert the UOM from the Basic UOM of the Item
	 *    to the UOM you send in with the Qty
	 * Creation date: (2/25/2003 TWalton)
	 * @return java.lang.String
	 */
	public static String convertUOM(String itemNumber,
									String uomTo,
									String quantity) 
	{
		String rtnValue = "0";
		try
		{
		  rtnValue = ServiceItem.convertItemUOM("", itemNumber, uomTo, quantity);	
		}
		catch(Exception e)
		{}
	   return rtnValue;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
