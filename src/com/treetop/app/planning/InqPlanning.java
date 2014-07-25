/*
 * Created on May 28, 2008
 *
 *  To be used for the JSP's 
 * 		inqPlannedProduction
 *      listPlannedProduction
 */
package com.treetop.app.planning;

import java.util.Vector;

import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.businessobjectapplications.BeanPlanning;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.PlannedProduction;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.services.ServicePlanning;

/**
 * @author twalto
 *
 */
public class InqPlanning extends BaseViewBeanR1 {

	// Standard Fields - to be in Every View Bean
	public String   requestType = "";
	
	// Standard Fields for Inq View Bean
	public String   orderBy = "";
	public String   orderStyle = "";
	
	// Inquiry Fields from the Inquiry Page
	public String   inqCompany   	= "";
	public String	inqWarehouse 	= ""; // Built from DropDown List
	public String	inqItem			= "";
	public String	inqWorkCenter	= ""; // Look at possible Drop Down List
	public String	inqItemGroup	= ""; // Build Drop Down (based on Valid Items)
	
	public String	inqPeriodType	= ""; // Month or Week - Master of Dual Drop Down
                 // Use inqStartPeriod as the Sunday of Each Week for the Weekly
				//                     OR the Sunday starting each Month 4-4-5
	public String	inqStartPeriod  = ""; // Default in Current Week
	public String	inqPeriodCount	= ""; // Default in 12 
	
	public String	listPlanning	= ""; // The Go Get the list Button
	
	// Build these two together based on incoming parameters
	public Vector listBeginningDates = new Vector(); // size same as inqPeriodCount
	public Vector listPeriodNumbers  = new Vector(); // size same as inqPeriodCount
	
	public String displayMessage = "";

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
//		if (inqStartDate.trim().equals(""))
//		{	
//			if (requestType.equals("listPlanRawFruit"))
//			{
//				displayMessage = "Must have a Start Date";
//				inqStartDateError = "Please Choose a Start Date";
//				requestType = "inqPlanRawFruit";
//			}
//		}
//		else
//		{
//			 // Test to see if it is a Valid Date
//			  // Test to see if it is a Sunday - MUST be a Sunday
//				try
//				{
//					DateTime dt = UtilityDateTime.getDateFromMMddyyWithSlash(inqStartDate.trim());
//					inqStartDateFileFormat = dt.getDateFormatyyyyMMdd();
//					if (dt.getDateErrorMessage().trim().equals(""))
//					{
//						if (!dt.getDayOfWeek().trim().equals("Sunday"))
//						{
//							displayMessage = "Start Date Must be a Sunday";
//							inqStartDateError = "Start Date Must be a Sunday";
//							requestType = "inqPlanRawFruit";	
//						}
//						else
//						{
//							DateTime newDateTime = UtilityDateTime.addDaysToDate(dt, 6);
//							inqEndDate = newDateTime.getDateFormatMMddyySlash();
//							inqEndDateFileFormat = newDateTime.getDateFormatyyyyMMdd();
//							sunday = dt;
//							saturday = newDateTime;
//							monday = UtilityDateTime.addDaysToDate(dt, 1);
//							tuesday = UtilityDateTime.addDaysToDate(dt, 2);
//							wednesday = UtilityDateTime.addDaysToDate(dt, 3);
//							thursday = UtilityDateTime.addDaysToDate(dt, 4);
//							friday = UtilityDateTime.addDaysToDate(dt, 5);							
//						}
//					}
//					else
//					{
//						displayMessage = "Must have a Valid Start Date";
//						inqStartDateError = dt.getDateErrorMessage();
//						requestType = "inqPlanRawFruit";
//					}
//				}
//			   	catch(Exception e)
//				{
//					displayMessage = "Must have a Valid Start Date";
//					inqStartDateError = "Please Choose a Valid Start Date";
//					requestType = "inqPlanRawFruit";
//				}		
//		}
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
		if (!this.inqWarehouse.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Warehouse: <b>");
			returnString.append(this.inqWarehouse);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");			
		}
		if (!this.inqItem.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Item Number: <b>");
			returnString.append(this.inqItem);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");
		}
		if (!this.inqItemGroup.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Item Group: <b>");
			returnString.append(this.inqItemGroup);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");			
		}
		if (!this.inqPeriodType.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Period Type: <b>");
			returnString.append(this.inqPeriodType);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");			
		}
		if (!this.inqStartPeriod.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Start Period: <b>");
			returnString.append(this.inqStartPeriod);
			returnString.append("</b>&nbsp;&nbsp;&nbsp;");			
		}
		if (!this.inqPeriodCount.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Periods to Display: <b>");
			returnString.append(this.inqPeriodCount);
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
		// ONLY use if needed
		//if (!this.inqCompany.equals("")) {
		//	returnString.append("&inqCompany=");
		//	returnString.append(this.inqCompany);
		//}
		//if (!this.inqStartDate.equals("")) {
		//	returnString.append("&inqStartDate=");
		//	returnString.append(this.inqStartDate);
		//}
		//if (!this.inqShowDescription.equals("")) {
		//	returnString.append("&inqShowDescription=");
		//	returnString.append(this.inqShowDescription);
		//}
		//if (!this.inqWarehouse.equals("")) {
		//	returnString.append("&inqWarehouse=");
		//	returnString.append(this.inqWarehouse);
		//}
		return returnString.toString();
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Warehouses to Choose From:
	 */
	public String buildDropDownProductionWarehouse(String whse) {
		Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
		dds.setDescription("Cashmere");
		dds.setValue("220");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Prosser");
		dds.setValue("469");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Rialto");
		dds.setValue("205");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Ross");
		dds.setValue("240");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Ross - Fresh Slice");
		dds.setValue("490");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Selah");
		dds.setValue("209");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("Wenatchee");
		dds.setValue("230");
		buildList.addElement(dds);
		return DropDownSingle.buildDropDown(buildList, "inqWarehouse", whse, "", "E", "N");
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Warehouses to Choose From:
	 */
	  public String buildDropDownPlannedWarehouse(String whse) {
		
		Vector buildList = new Vector();
		try
		{
			// Retrieve a Vector of Vectors 
			// the inside Vector will have Whse and Name as elements
			buildList = ServicePlanning.listDropDownPlannedWarehouse(new Vector());
		}
		catch(Exception e)
		{
		   // Send back note (message) if there is a problem.
		}
		String returnValue = "";
		if (buildList != null && buildList.size() > 0)
		{
			Vector buildList2 = new Vector();
			for (int x = 0; x < buildList.size(); x++)
			{
				try
				{
				Vector elementValue = (Vector) buildList.elementAt(x);
				DropDownSingle dds = new DropDownSingle();
				dds.setDescription((String) elementValue.elementAt(1));
				dds.setValue((String) elementValue.elementAt(0));
				buildList2.addElement(dds);
				}
				catch(Exception e)
				{}
			}
			if (buildList2.size() > 0)
			   returnValue = DropDownSingle.buildDropDown(buildList2, "inqWarehouse", whse, "", "B", "N");
		}
		else
			returnValue = "There are No Orders Planned.";

		return returnValue; 
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Item Groups to Choose From:
	 */
	  public String buildDropDownPlannedItemGroup(String itemGroup) {
		
		Vector buildList = new Vector();
		try
		{
			// Retrieve a Vector of Vectors 
			// the inside Vector will have Whse and Name as elements
			buildList = ServicePlanning.listDropDownItemGroup(new Vector());
		}
		catch(Exception e)
		{
		   // Send back note (message) if there is a problem.
		}
		String returnValue = "";
		if (buildList != null && buildList.size() > 0)
		{
			Vector buildList2 = new Vector();
			for (int x = 0; x < buildList.size(); x++)
			{
				try
				{
					Vector elementValue = (Vector) buildList.elementAt(x);
					DropDownSingle dds = new DropDownSingle();
					dds.setDescription((String) elementValue.elementAt(1));
					dds.setValue((String) elementValue.elementAt(0));
					buildList2.addElement(dds);
				}
				catch(Exception e)
				{}
			}
			if (buildList2.size() > 0)
			   returnValue = DropDownSingle.buildDropDown(buildList2, "inqItemGroup", itemGroup, "", "B", "N");
		}
		else
			returnValue = "There are No Orders Planned.";

		return returnValue; 
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
	 * @return Returns the inqWarehouse.
	 */
	public String getInqWarehouse() {
		return inqWarehouse;
	}
	/**
	 * @param inqWarehouse The inqWarehouse to set.
	 */
	public void setInqWarehouse(String inqWarehouse) {
		this.inqWarehouse = inqWarehouse;
	}
	/**
	 * @return Returns the inqItem.
	 */
	public String getInqItem() {
		return inqItem;
	}
	/**
	 * @param inqItem The inqItem to set.
	 */
	public void setInqItem(String inqItem) {
		this.inqItem = inqItem;
	}
	/**
	 * @return Returns the inqItemGroup.
	 */
	public String getInqItemGroup() {
		return inqItemGroup;
	}
	/**
	 * @param inqItemGroup The inqItemGroup to set.
	 */
	public void setInqItemGroup(String inqItemGroup) {
		this.inqItemGroup = inqItemGroup;
	}
	/**
	 * @return Returns the inqPeriodCount.
	 */
	public String getInqPeriodCount() {
		return inqPeriodCount;
	}
	/**
	 * @param inqPeriodCount The inqPeriodCount to set.
	 */
	public void setInqPeriodCount(String inqPeriodCount) {
		this.inqPeriodCount = inqPeriodCount;
	}
	/**
	 * @return Returns the inqPeriodType.
	 */
	public String getInqPeriodType() {
		return inqPeriodType;
	}
	/**
	 * @param inqPeriodType The inqPeriodType to set.
	 */
	public void setInqPeriodType(String inqPeriodType) {
		this.inqPeriodType = inqPeriodType;
	}
				/**
				 * @return Returns the inqStartPeriod.
				 */
				public String getInqStartPeriod() {
					return inqStartPeriod;
				}
				/**
				 * @param inqStartPeriod The inqStartPeriod to set.
				 */
				public void setInqStartPeriod(String inqStartPeriod) {
					this.inqStartPeriod = inqStartPeriod;
				}
	/**
	 * @return Returns the inqWorkCenter.
	 */
	public String getInqWorkCenter() {
		return inqWorkCenter;
	}
	/**
	 * @param inqWorkCenter The inqWorkCenter to set.
	 */
	public void setInqWorkCenter(String inqWorkCenter) {
		this.inqWorkCenter = inqWorkCenter;
	}
	/**
	 * @return Returns the listBeginningDates.
	 */
	public Vector getListBeginningDates() {
		return listBeginningDates;
	}
	/**
	 * @param listBeginningDates The listBeginningDates to set.
	 */
	public void setListBeginningDates(Vector listBeginningDates) {
		this.listBeginningDates = listBeginningDates;
	}
	/**
	 * @return Returns the listPeriodNumbers.
	 */
	public Vector getListPeriodNumbers() {
		return listPeriodNumbers;
	}
	/**
	 * @param listPeriodNumbers The listPeriodNumbers to set.
	 */
	public void setListPeriodNumbers(Vector listPeriodNumbers) {
		this.listPeriodNumbers = listPeriodNumbers;
	}
}
