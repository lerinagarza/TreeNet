/*
 * Created on April 21, 2008
 *
 *  To be used for the JSP's 
 * 		updDataSet
 */
package com.treetop.app.dataset;

import java.util.Vector;

import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.businessobjects.DataSet;
import com.treetop.businessobjects.DateTime;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.services.ServiceDataSet;
import com.treetop.services.ServiceUser;

/**
 * @author twalto
 *
 */
public class UpdDataSet extends BaseViewBeanR1 {

	// Standard Fields - to be in Every View Bean
	public String   requestType = "";
	
	// Standard Fields for Inq View Bean
	public String   orderBy = "";
	public String   orderStyle = "";
	
	public String   dataSetName = "";
	public String   dataSetNameError = "";
	public String	dataSetYear = "";
	public String   recordType  = ""; // Forcasted or Sales History
	public String	costYear    = "";
	public String   budgetYear  = "";
	public String   salesYear   = "";
	public String	comment		= "";
	
	public String   updateUser  = "";
	
	public String	updDataSet	= "";
	
	public String displayMessage = "";

	// List for the List Page JSP
	public Vector listReport = null;

	/* 
	 * Validation of Data  
	 * 
	 * Set the Default - When information is BLANK 
	 */
	public void validate() {
	   if (dataSetName.trim().equals("") && !updDataSet.trim().equals(""))
	   {
	   	   dataSetNameError = "You MUST choose a DataSet";
	   	   displayMessage   = "You MUST choose a DataSet";
	   }
	   if (requestType.equals("updAverageCost"))
	   	   recordType = "32";
		return;
	}
	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
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
	 * @return Returns the orderBy.
	 */
	public String getOrderBy() {
		return orderBy;
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
		return orderStyle;
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
		return requestType;
	}
	/**
	 * @param requestType The requestType to set.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	/**
	 * @return Returns the costYear.
	 */
	public String getCostYear() {
		return costYear;
	}
	/**
	 * @param costYear The costYear to set.
	 */
	public void setCostYear(String costYear) {
		this.costYear = costYear;
	}
	/**
	 * @return Returns the dataSetName.
	 */
	public String getDataSetName() {
		return dataSetName;
	}
	/**
	 * @param dataSetName The dataSetName to set.
	 */
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	/**
	 * @return Returns the dataSetYear.
	 */
	public String getDataSetYear() {
		return dataSetYear;
	}
	/**
	 * @param dataSetYear The dataSetYear to set.
	 */
	public void setDataSetYear(String dataSetYear) {
		this.dataSetYear = dataSetYear;
	}
	/**
	 * @return Returns the recordType.
	 */
	public String getRecordType() {
		return recordType;
	}
	/**
	 * @param recordType The recordType to set.
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	/**
	 * @return Returns the updDataSet.
	 */
	public String getUpdDataSet() {
		return updDataSet;
	}
	/**
	 * @param updDataSet The updDataSet to set.
	 */
	public void setUpdDataSet(String updDataSet) {
		this.updDataSet = updDataSet;
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
	 * Build drop down  list of the Data Set File Description and File
	 * 
	 * Creation date: (5/15/2008 -- TWalton)
	 */
	public static String buildDropDownFile(String inFileName, String readOnly) {
		String returnValue = "";
		try {
			Vector listOfDataSetNames = ServiceDataSet.listDataSetNames(new Vector());
			// Read through the Vector,  Create the Drop Down Information (DropDownSingle Utility Class)
			Vector dds = new Vector(); // Drop Down Single Vector
			if (listOfDataSetNames != null && listOfDataSetNames.size() > 0)
			{
				for (int x = 0; x < listOfDataSetNames.size(); x++)
				{
					DataSet ds = (DataSet) listOfDataSetNames.elementAt(x);
					DropDownSingle dd = new DropDownSingle();
					dd.setDescription(ds.getDescription().trim());
					dd.setValue(ds.getFileName().trim());
					dds.addElement(dd);
				}
			}
		    returnValue = DropDownSingle.buildDropDown(dds,
					"dataSetName", inFileName, "Select a Dataset: ", "E",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the dataSetNameError.
	 */
	public String getDataSetNameError() {
		return dataSetNameError;
	}
	/**
	 * @param dataSetNameError The dataSetNameError to set.
	 */
	public void setDataSetNameError(String dataSetNameError) {
		this.dataSetNameError = dataSetNameError;
	}
	/**
	 * Build drop down  list of the Data Set Year
	 * 
	 * Creation date: (5/16/2008 -- TWalton)
	 */
	public static String buildDropDownYear(String inFileName, String readOnly) {
		String returnValue = "";
		try {
			
//  Old way to get years
//			Vector listOfDataSetYears = ServiceDataSet.listDataSetYears(new Vector());
//			// Read through the Vector,  Create the Drop Down Information (DropDownSingle Utility Class)
//			Vector dds = new Vector(); // Drop Down Single Vector
//			if (listOfDataSetYears != null && listOfDataSetYears.size() > 0)
//			{
//				for (int x = 0; x < listOfDataSetYears.size(); x++)
//				{
//					DataSet ds = (DataSet) listOfDataSetYears.elementAt(x);
//					DropDownSingle dd = new DropDownSingle();
//					dd.setDescription(ds.getYearDataSet().trim());
//					dd.setValue(ds.getYearDataSet().trim());
//					dds.addElement(dd);
//				}
//			}
//		    returnValue = DropDownSingle.buildDropDown(dds,
//					"dataSetYear", inFileName, "None", "N",
//					readOnly);
			
			
			//Hard coded years for drop down		 2011-04-15  JH
			Vector dds = new Vector(); // Drop Down Single Vector
			DropDownSingle dd = new DropDownSingle();
			dd.setDescription("2008");
			dd.setValue("2008");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2009");
			dd.setValue("2009");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2010");
			dd.setValue("2010");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2011");
			dd.setValue("2011");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2012");
			dd.setValue("2012");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2013");
			dd.setValue("2013");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2014");
			dd.setValue("2014");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2015");
			dd.setValue("2015");
			dds.addElement(dd);						
			
		    returnValue = DropDownSingle.buildDropDown(dds,
					"dataSetYear", inFileName, "None", "N",
					readOnly);
			
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * Build drop down  list of the Cost Year
	 * 
	 * Creation date: (5/19/2008 -- TWalton)
	 */
	public static String buildDropDownCostingYear(String inFileName, String readOnly) {
		String returnValue = "";
		try {
			Vector dds = new Vector(); // Drop Down Single Vector
			DropDownSingle dd = new DropDownSingle();
			dd.setDescription("2008");
			dd.setValue("2008");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2009");
			dd.setValue("2009");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2010");
			dd.setValue("2010");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2011");
			dd.setValue("2011");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2012");
			dd.setValue("2012");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2013");
			dd.setValue("2013");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2014");
			dd.setValue("2014");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2015");
			dd.setValue("2015");
			dds.addElement(dd);						
			
		    returnValue = DropDownSingle.buildDropDown(dds,
					"costYear", inFileName, "None", "N",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * Build drop down  list of Record Types
	 * 
	 * Creation date: (5/19/2008 -- TWalton)
	 */
	public static String buildDropDownRecordType(String inFileName, String readOnly) {
		String returnValue = "";
		try {
			Vector dds = new Vector(); // Drop Down Single Vector
			DropDownSingle dd = new DropDownSingle();
			dd.setDescription("Both");
			dd.setValue("both");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("Forecasted Only");
			dd.setValue("34");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("Sales History Only");
			dd.setValue("32");
			dds.addElement(dd);
			
		    returnValue = DropDownSingle.buildDropDown(dds,
					"recordType", inFileName, "None", "N",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
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
	 * Retrieve a Vector to build the View of the Audit Log
	 * 
	 * Creation date: (5/20/2008 -- TWalton)
	 */
	public static Vector buildAuditLog(String inFileName) {
		Vector returnList = new Vector();
		try {
			Vector parmList = new Vector();
			parmList.addElement(inFileName);
			returnList = ServiceDataSet.listAuditLog(parmList);
		}
		catch(Exception e)
		{
			System.out.println("Problem occurred when retrieving Audit Log information: ");
		}
		return returnList;
	}
	/**
	 * Retrieve a Vector to build the View of the Audit Log
	 * 
	 * Creation date: (5/20/2008 -- TWalton)
	 */
	public static String getLongUserName(String name) {
		try {
			name = ServiceUser.returnNameFromM3User("", name);
		}
		catch(Exception e)
		{
			System.out.println("Problem occurred when retrieving Name information: ");
		}
		return name;
	}
	/**
	 * @return Returns the budgetYear.
	 */
	public String getBudgetYear() {
		return budgetYear;
	}
	/**
	 * @param budgetYear The budgetYear to set.
	 */
	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}
	/**
	 * @return Returns the salesYear.
	 */
	public String getSalesYear() {
		return salesYear;
	}
	/**
	 * @param salesYear The salesYear to set.
	 */
	public void setSalesYear(String salesYear) {
		this.salesYear = salesYear;
	}
	/**
	 * Build drop down  list of the Budget Year
	 * 
	 * Creation date: (5/20/2008 -- TWalton)
	 */
	public static String buildDropDownBudgetYear(String inFileName, String readOnly) {
		String returnValue = "";
		try {
			Vector dds = new Vector(); // Drop Down Single Vector
			DropDownSingle dd = new DropDownSingle();
			dd.setDescription("2009");
			dd.setValue("2009");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2010");
			dd.setValue("2010");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2011");
			dd.setValue("2011");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2012");
			dd.setValue("2012");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2013");
			dd.setValue("2013");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2014");
			dd.setValue("2014");
			dds.addElement(dd);
			dd = new DropDownSingle();
			dd.setDescription("2015");
			dd.setValue("2015");
			dds.addElement(dd);			
			
		    returnValue = DropDownSingle.buildDropDown(dds,
					"salesYear", inFileName, "None", "N",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * Build drop down  list of the Sales Year
	 * 
	 * Creation date: (5/20/2008 -- TWalton)
	 */
	public static String buildDropDownSalesYear(String inFileName, String readOnly) {
		String returnValue = "";
		try {
			Vector listOfDataSetYears = ServiceDataSet.listDataSetYears(new Vector());
			// Read through the Vector,  Create the Drop Down Information (DropDownSingle Utility Class)
			Vector dds = new Vector(); // Drop Down Single Vector
			if (listOfDataSetYears != null && listOfDataSetYears.size() > 0)
			{
				for (int x = 0; x < listOfDataSetYears.size(); x++)
				{
					DataSet ds = (DataSet) listOfDataSetYears.elementAt(x);
					DropDownSingle dd = new DropDownSingle();
					dd.setDescription(ds.getYearDataSet().trim());
					dd.setValue(ds.getYearDataSet().trim());
					dds.addElement(dd);
				}
			}
		    returnValue = DropDownSingle.buildDropDown(dds,
					"salesYear", inFileName, "None", "N",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
}
