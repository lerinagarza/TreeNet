/*
 * Created on March 20, 2008
 *
 */
 
package com.treetop.app.finance;

import java.util.Vector;

import com.treetop.services.ServiceFinance;
import com.treetop.services.ServiceWarehouse;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.businessobjects.DateTime;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto - March 20, 2008
 *
 */
public class BldFinance extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	
	public String goButton         = "";
	public String bldNextYearButton = "";
	public String bldCurrentYearButton = "";
	public String bldBothButton = "";
	
	public String bldFromDate = "";
	public String bldFromDateParmFormat = "";
	public String bldFromDateError = "";
	public String bldToDate = "";
	public String bldToDateParmFormat = "";
	public String bldToDateError = "";
	
	public String rptChosenReport = "";
	public String rptMonth = "";
	public String rptType = ""; // Type of Report Summary Detail
	public String rptRunType = ""; // Posted or Unposted
	public String rptDate = ""; 
	public String rptDateError = "";
	public String rptTime = "";
	public String rptTimeError = "";
	
	public String updateUser = "";
	public String displayMessage = "";

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBeanR1#validate()
	 */
	public void validate() {
		if (requestType.equals("addStateFees"))
		{	
			if (bldFromDate.trim().equals(""))
			{
				displayMessage = "Must have a Valid From Date.&nbsp;&nbsp;";
				bldFromDateError = "Must have a Valid From Date.&nbsp;&nbsp;";
			}
			else
			{	
				DateTime udt = UtilityDateTime.getDateFromMMddyyWithSlash(bldFromDate);
				if (udt.getDateErrorMessage().trim().equals(""))
					bldFromDateParmFormat = udt.getDateFormatyyyyMMdd();
				else
				{
					displayMessage = bldFromDate + " is not a valid date.&nbsp;&nbsp;";
					bldFromDateError = bldFromDate + " is not a valid date.&nbsp;&nbsp;Try Again!";
				}
			}
			if (bldToDate.trim().equals(""))
			{
				displayMessage = "Must have a Valid To Date.&nbsp;&nbsp;";
				bldToDateError = "Must have a Valid To Date.&nbsp;&nbsp;";
			}
			else
			{	
				DateTime udt = UtilityDateTime.getDateFromMMddyyWithSlash(bldToDate);
				if (udt.getDateErrorMessage().trim().equals(""))
					bldToDateParmFormat = udt.getDateFormatyyyyMMdd();
				else
				{
					displayMessage = bldToDate + " is not a valid date.&nbsp;&nbsp;";
					bldFromDateError = bldToDate + " is not a valid date.&nbsp;&nbsp;Try Again!";
				}
			}
		}
		if (requestType.equals("bldStateFees"))
		{	
			if (bldFromDate.trim().equals("") ||
				bldToDate.trim().equals(""))
			{
				DateTime dt = UtilityDateTime.getSystemDate();
				bldFromDate = dt.getDateFormatMMddyySlash();
				bldToDate   = dt.getDateFormatMMddyySlash();
			}
		}	
		if (requestType.equals("bldReportStateFees"))
		{	
			if (rptRunType.trim().equals("P"))
			{
				if (rptDate.trim().equals("") ||
					rptDate.trim().equals("*all"))
					rptDateError = "If 'POSTED' is Chosen a Date and Time Must Also Be Chosen.";
			}
		}		
	}	
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Valid Reports to Choose From:
	 */
	public String buildDropDownChosenReport(String requestType) {
	String returnValue = "";
		if (requestType.equals("rptStateFees") ||
			requestType.equals("bldReportStateFees"))	
		{	
			
			Vector buildList = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setDescription("Distributor Report");
			dds.setValue("DR-3");
			buildList.addElement(dds);
			// 7/16/12 - TWalton -- Commented out Per Ken K.
			//dds = new DropDownSingle();
			//dds.setDescription("Beverage Manufacturer Report");
			//dds.setValue("DR-4");
			//buildList.addElement(dds);
			returnValue = DropDownSingle.buildDropDown(buildList, "rptChosenReport", "", "None", "E", "N");
		}
		return returnValue;
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Months to Choose From:
	 */
	public String buildDropDownMonth() {
		Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
		dds.setDescription("January");
		dds.setValue("January");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("February");
		dds.setValue("February");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("March");
		dds.setValue("March");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("April");
		dds.setValue("April");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("May");
		dds.setValue("May");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("June");
		dds.setValue("June");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("July");
		dds.setValue("July");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("August");
		dds.setValue("August");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("September");
		dds.setValue("September");
		buildList.addElement(dds);	
		dds = new DropDownSingle();
		dds.setDescription("October");
		dds.setValue("October");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("November");
		dds.setValue("November");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("December");
		dds.setValue("December");
		buildList.addElement(dds);
		return DropDownSingle.buildDropDown(buildList, "rptMonth", "", "None", "N", "N");
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Report Type to Choose From:
	 */
	public String buildDropDownReportType() {
		Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
		dds.setDescription("Detail");
		dds.setValue("D");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Summary");
		dds.setValue("S");
		buildList.addElement(dds);
			return DropDownSingle.buildDropDown(buildList, "rptType", "", "None", "N", "N");
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Report Run Type to Choose From:
	 */
	public String buildDropDownRunType() {
		Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
		dds.setDescription("Posted");
		dds.setValue("P");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Unposted");
		dds.setValue("U");
		buildList.addElement(dds);
		return DropDownSingle.buildDropDown(buildList, "rptRunType", "", "None", "N", "N");
	}
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Date and then getting the Appropriate Times 
	 *    along with it.
	 */
	public Vector buildDropDownDateTime() {
		Vector dd = new Vector();
		try
		{
		   // Build the Vector of Utility - DropDownDual	
		   Vector dualDD = new Vector();
		   Vector returnDates = ServiceFinance.returnVectorDateTimeStateFees("");
		   
		   	  // Get the list of Dates
		   if (returnDates.size() > 0)
		   {
		       for (int y = 0; y < returnDates.size(); y++)
		   	   {
		   	      DateTime dt = (DateTime) returnDates.elementAt(y);
		   	      if (dt.getDateErrorMessage().equals(""))
		   	      {	
		   	   	     DropDownDual addOne = new DropDownDual();
		   	   	     addOne.setMasterValue(dt.getDateFormatyyyyMMdd());
		   	   	     addOne.setMasterDescription(dt.getDateFormatMMddyyyySlash());
		   	   	     addOne.setSlaveValue(dt.getTimeFormathhmmss());
		   	   	     
//		   	   	     String time = dt.getTimeFormathhmmss().substring(0, 2) + ":" +
//				                dt.getTimeFormathhmmss().substring(2, 4) + ":" + 
//								dt.getTimeFormathhmmss().substring(4, 6);
		   	   	     addOne.setSlaveDescription(dt.getTimeFormathhmmssColon());
		   	      	 dualDD.addElement(addOne);
		   	      }
		   	   	}
		     }
		   // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(dualDD, "rptDate", "N", "", "rptTime", "N", "N", "N");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	public static Vector<HtmlOption> getDateTimeDDValues() throws Exception {
		Vector returnDates = ServiceFinance.returnVectorDateTimeStateFees("");
		Vector results     = new Vector();
		
		if (returnDates.size() > 0)
		{
			for (int y = 0; y < returnDates.size(); y++)
			{
				DateTime dt = (DateTime) returnDates.elementAt(y);
		   	    if (dt.getDateErrorMessage().equals(""))
		   	    {	
		   	    	DropDownDual addOne = new DropDownDual();
		   	   	    addOne.setMasterValue(dt.getDateFormatyyyyMMdd());
		   	   	    addOne.setMasterDescription(dt.getDateFormatMMddyyyySlash());
		   	   	    addOne.setSlaveValue(dt.getTimeFormathhmmss());
		   	   	    addOne.setSlaveDescription(dt.getTimeFormathhmmssColon());
		   	      	results.addElement(addOne);
		   	    }
		   	}
		}
		return results;
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
	public String getRequestType() {
		return requestType;
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
	 * @return Returns the bldFromDate.
	 */
	public String getBldFromDate() {
		return bldFromDate;
	}
	/**
	 * @param bldFromDate The bldFromDate to set.
	 */
	public void setBldFromDate(String bldFromDate) {
		this.bldFromDate = bldFromDate;
	}
	/**
	 * @return Returns the bldFromDateError.
	 */
	public String getBldFromDateError() {
		return bldFromDateError;
	}
	/**
	 * @param bldFromDateError The bldFromDateError to set.
	 */
	public void setBldFromDateError(String bldFromDateError) {
		this.bldFromDateError = bldFromDateError;
	}
	/**
	 * @return Returns the bldFromDateParmFormat.
	 */
	public String getBldFromDateParmFormat() {
		return bldFromDateParmFormat;
	}
	/**
	 * @param bldFromDateParmFormat The bldFromDateParmFormat to set.
	 */
	public void setBldFromDateParmFormat(String bldFromDateParmFormat) {
		this.bldFromDateParmFormat = bldFromDateParmFormat;
	}
	/**
	 * @return Returns the bldToDate.
	 */
	public String getBldToDate() {
		return bldToDate;
	}
	/**
	 * @param bldToDate The bldToDate to set.
	 */
	public void setBldToDate(String bldToDate) {
		this.bldToDate = bldToDate;
	}
	/**
	 * @return Returns the bldToDateError.
	 */
	public String getBldToDateError() {
		return bldToDateError;
	}
	/**
	 * @param bldToDateError The bldToDateError to set.
	 */
	public void setBldToDateError(String bldToDateError) {
		this.bldToDateError = bldToDateError;
	}
	/**
	 * @return Returns the bldToDateParmFormat.
	 */
	public String getBldToDateParmFormat() {
		return bldToDateParmFormat;
	}
	/**
	 * @param bldToDateParmFormat The bldToDateParmFormat to set.
	 */
	public void setBldToDateParmFormat(String bldToDateParmFormat) {
		this.bldToDateParmFormat = bldToDateParmFormat;
	}
	/**
	 * @return Returns the rptChosenReport.
	 */
	public String getRptChosenReport() {
		return rptChosenReport;
	}
	/**
	 * @param rptChosenReport The rptChosenReport to set.
	 */
	public void setRptChosenReport(String rptChosenReport) {
		this.rptChosenReport = rptChosenReport;
	}
	/**
	 * @return Returns the rptDate.
	 */
	public String getRptDate() {
		return rptDate;
	}
	/**
	 * @param rptDate The rptDate to set.
	 */
	public void setRptDate(String rptDate) {
		this.rptDate = rptDate;
	}
	/**
	 * @return Returns the rptMonth.
	 */
	public String getRptMonth() {
		return rptMonth;
	}
	/**
	 * @param rptMonth The rptMonth to set.
	 */
	public void setRptMonth(String rptMonth) {
		this.rptMonth = rptMonth;
	}
	/**
	 * @return Returns the rptRunType.
	 */
	public String getRptRunType() {
		return rptRunType;
	}
	/**
	 * @param rptRunType The rptRunType to set.
	 */
	public void setRptRunType(String rptRunType) {
		this.rptRunType = rptRunType;
	}
	/**
	 * @return Returns the rptTime.
	 */
	public String getRptTime() {
		return rptTime;
	}
	/**
	 * @param rptTime The rptTime to set.
	 */
	public void setRptTime(String rptTime) {
		this.rptTime = rptTime;
	}
	/**
	 * @return Returns the rptType.
	 */
	public String getRptType() {
		return rptType;
	}
	/**
	 * @param rptType The rptType to set.
	 */
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	/**
	 * @return Returns the rptDateError.
	 */
	public String getRptDateError() {
		return rptDateError;
	}
	/**
	 * @param rptDateError The rptDateError to set.
	 */
	public void setRptDateError(String rptDateError) {
		this.rptDateError = rptDateError;
	}
	/**
	 * @return Returns the rptTimeError.
	 */
	public String getRptTimeError() {
		return rptTimeError;
	}
	/**
	 * @param rptTimeError The rptTimeError to set.
	 */
	public void setRptTimeError(String rptTimeError) {
		this.rptTimeError = rptTimeError;
	}
	public String getGoButton() {
		return goButton;
	}
	public void setGoButton(String goButton) {
		this.goButton = goButton;
	}
	public String getBldBothButton() {
		return bldBothButton;
	}
	public void setBldBothButton(String bldBothButton) {
		this.bldBothButton = bldBothButton;
	}
	public String getBldCurrentYearButton() {
		return bldCurrentYearButton;
	}
	public void setBldCurrentYearButton(String bldCurrentYearButton) {
		this.bldCurrentYearButton = bldCurrentYearButton;
	}
	public String getBldNextYearButton() {
		return bldNextYearButton;
	}
	public void setBldNextYearButton(String bldNextYearButton) {
		this.bldNextYearButton = bldNextYearButton;
	}
}
