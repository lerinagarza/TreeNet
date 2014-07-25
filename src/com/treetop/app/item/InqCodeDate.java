/*
 * Created on October 14, 2008
 *
 */

package com.treetop.app.item;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.GetDate;
import com.treetop.SessionVariables;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.ItemWarehouse;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownDual;

/**
 * @author twalto
 * 
 * Created for use in Code Date for an Item
 */
public class InqCodeDate extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";

	public String inqItem = "";
	public String inqItemError = "";
	public String inqFacility = "";
	public String inqWarehouse = "";
	public String inqDateSelected = "";
	
	public String bestByValue = "";
	
	public ItemWarehouse itemWhse = new ItemWarehouse();
	public Vector listReport = null;
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
	   	if (!inqItem.trim().equals(""))
		{
			try
			{
				   inqItemError = ServiceItem.verifyItem("PRD", inqItem.trim());
		   	//  	System.out.println(itemError);
			}
		   	catch(Exception e)
			{
//		   	 Just Catch the error, do not need to do anything with it.
			}
		 }	
	   	 else
	   	 {
	   	 	if (!requestType.equals("inq") &&
	   	 		!requestType.trim().equals(""))
	   	 	{
	   	 		inqItemError = "You must choose an Item Number!";
	   	 		requestType = "inq";
	   	 	}
	   	 }
		 if (!inqItemError.trim().equals(""))
			displayMessage = inqItemError.trim();
		 if (inqDateSelected.trim().equals(""))
		 {
		 	try
			{
		 		DateTime dt = UtilityDateTime.getSystemDate();
		 		inqDateSelected = dt.getDateFormatMMddyyyySlash();
			}
		 	catch(Exception e)
			{}
		 }

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
	 * @return Returns the inqItemError.
	 */
	public String getInqItemError() {
		return inqItemError;
	}
	/**
	 * @param inqItemError The inqItemError to set.
	 */
	public void setInqItemError(String inqItemError) {
		this.inqItemError = inqItemError;
	}
	/**
	 * @return Returns the inqDateSelected.
	 */
	public String getInqDateSelected() {
		return inqDateSelected;
	}
	/**
	 * @param inqDateSelected The inqDateSelected to set.
	 */
	public void setInqDateSelected(String inqDateSelected) {
		this.inqDateSelected = inqDateSelected;
	}
	/**
	 * @return Returns the inqFacility.
	 */
	public String getInqFacility() {
		return inqFacility;
	}
	/**
	 * @param inqFacility The inqFacility to set.
	 */
	public void setInqFacility(String inqFacility) {
		this.inqFacility = inqFacility;
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
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Facility and then getting the Appropriate Warehouses 
	 *    along with it.
	 */
	public Vector buildDropDownFacilityWarehouse() {
		Vector dd = new Vector();
		try
		{
		   // Build the Vector of Utility - DropDownDual	
		   Vector dualDD = new Vector();
		   DropDownDual addOne = new DropDownDual();
		   addOne.setMasterValue("301");
		   addOne.setMasterDescription("Selah Campus");
		   addOne.setSlaveValue("209");
		   addOne.setSlaveDescription("Selah Plant");
		   dualDD.addElement(addOne);
		   addOne = new DropDownDual();
		   addOne.setMasterValue("305");
		   addOne.setMasterDescription("Rialto Campus");
		   addOne.setSlaveValue("205");
		   addOne.setSlaveDescription("Rialto Plant");
		   dualDD.addElement(addOne);
		   addOne = new DropDownDual();
		   addOne.setMasterValue("309");
		   addOne.setMasterDescription("Prosser Campus");
		   addOne.setSlaveValue("469");
		   addOne.setSlaveDescription("Prosser Plant");
		   dualDD.addElement(addOne);		   
		   
		   // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(dualDD, "inqFacility", "N",  "", "inqWarehouse", "N", "B", "B");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Build the production code date.
	 *  Will process ALL the values related to Code Date
	 * Creation date: (12/30/2003 8:24:29 AM)
	 *  10/16/08 TWalton - Moved from the Resource Class
	 */
	public void buildBestByValue() {
		// Best By Information
		try {
		 if (!itemWhse.getBestByItem().equals(""))
		 {
		    DateTime dt = UtilityDateTime.getDateFromMMddyyyyWithSlash(inqDateSelected);
		    Integer i = new Integer(itemWhse.getDaysShelfLife());
		    DateTime newDT = UtilityDateTime.addDaysToDate(dt, i.intValue());
			    	                                                       
		    bestByValue = "BEST BY " + newDT.getDateFormatMMddyySlash();
//		    System.out.println("Print Out Best By: " + bestByValue);
		 }
		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.app.item.InqCodeDate." +
				               "buildBestByValue(): " + e);
		}		
		return; 
	}
	/**
 * @return Returns the itemWhse.
 */
public ItemWarehouse getItemWhse() {
	return itemWhse;
}
/**
 * @param itemWhse The itemWhse to set.
 */
public void setItemWhse(ItemWarehouse itemWhse) {
	this.itemWhse = itemWhse;
}
	/**
	 * @return Returns the bestByValue.
	 */
	public String getBestByValue() {
		return bestByValue;
	}
	/**
	 * @param bestByValue The bestByValue to set.
	 */
	public void setBestByValue(String bestByValue) {
		this.bestByValue = bestByValue;
	}
}
