/*
 * Created on March 12, 2008
 *
 */
 
package com.treetop.app.finance;

import com.treetop.services.ServiceCostTypes;
import java.util.Vector;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.businessobjects.DateTime;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto - March 12, 2008
 *
 */
public class UpdFinance extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	
	public String fromCostType = "";
	public String fromCostDate = "";
	public String fromFiscalYear = "";
	public String toCostType = "";
	public String toCostDate = "";
	public String toCostDateFileFormat = "";
	public String page1 = "";
	public String itemType = "";
	public String itemNumber = "";
	
	public String shouldOverride = "";
	public String retryCompanyCost = "";
	
	public String displayMessage  = "";
	
	public String updateUser = "";

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBeanR1#validate()
	 */
	public void validate() {
		if (toCostDate.trim().equals(""))
		{
		   DateTime udt = UtilityDateTime.getSystemDate();
		   toCostDate = udt.getDateFormatMMddyySlash();
		}
		else
		{
			DateTime udt = UtilityDateTime.getDateFromMMddyyWithSlash(toCostDate);
			if (udt.getDateErrorMessage().trim().equals(""))
			   toCostDateFileFormat = udt.getDateFormatyyyyMMdd();
			else
				displayMessage = toCostDate + " is not a valid date.";
		}
		// Test to see if Cost Type and Date Combination Already Exist
		//   If they do then send back the information down to the screen along with 
		//       a way to say Override or Start Again
		try
		{
			
			if (shouldOverride.trim().equals("") &&
				requestType.equals("updateCostType") &&
				retryCompanyCost.trim().equals(""))
			{	
		       String testForRecords = ServiceCostTypes.verifyDateCostType("", toCostDateFileFormat, toCostType, itemNumber);
		       if (testForRecords.trim().equals(""))
		   	      shouldOverride = "TEST";
			}
		}
		catch(Exception e)
		{}
	}	
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a CostType and then getting the Appropriate Dates 
	 *    along with it.
	 */
	public Vector buildDropDownDates() {
		Vector dd = new Vector();
		try
		{
		   // Build the Vector of Utility - DropDownDual	
		   Vector dualDD = new Vector();
		   String costType = "1";
		   for (int x = 0; x < 9; x++)
		   {	
		   	  // Figure out which Cost Type we want to send
		   	if (x == 1)
		   	  	costType = "2";
		   	if (x == 2)
		   	  	costType = "3";
		   	if (x == 3)
		   	  	costType = "4";
		   	if (x == 4)
		   	  	costType = "5";
		   	if (x == 5)
		   	  	costType = "6";
		   	if (x == 6)
		   	  	costType = "7";
		   	if (x == 7)
		   	  	costType = "8";
		   	if (x == 8)
		   	  	costType = "9";
		   	  // Get the list of Dates
		   	   Vector returnDates = ServiceCostTypes.returnVectorDatesByCostType("", costType);
		   	   if (returnDates.size() > 0)
		   	   {
		   	   	   for (int y = 0; y < returnDates.size(); y++)
		   	   	   {
		   	   	   	  DropDownDual addOne = new DropDownDual();
		   	   	   	  addOne.setMasterValue(costType);
		   	   	   	  String desc = "";
		   	   	   	  if (costType.trim().equals("1"))
		   	   	   	  	desc = "Cost Type 1";
		   	   	   	  if (costType.trim().equals("2"))
		   	   	   	  	desc = "Cost Type 2";
		   	   	   	  if (costType.trim().equals("3"))
		   	   	   	  	desc = "Standard - Cost Type 3";
		   	   	   	  if (costType.trim().equals("4"))
		   	   	   	  	desc = "Cost Type 4";
		   	   	   	  if (costType.trim().equals("5"))
		   	   	   	  	desc = "Cost Type 5";
		   	   	   	  if (costType.trim().equals("6"))
		   	   	   	  	desc = "Cost Type 6";
		   	   	   	  if (costType.trim().equals("7"))
		   	   	   	  	desc = "Cost Type 7";
		   	   	   	  if (costType.trim().equals("8"))
		   	   	   	  	desc = "Cost Type 8";
		   	   	   	  if (costType.trim().equals("9"))
		   	   	   	  	desc = "Company Cost - Cost Type 9";
		   	   	   	  addOne.setMasterDescription(desc);
		   	   	   	  addOne.setSlaveValue((String) returnDates.elementAt(y));
		   	   	   	  DateTime dt = UtilityDateTime.getDateFromyyyyMMdd((String) returnDates.elementAt(y));
		   	   	   	  if (dt.getDateErrorMessage().trim().equals(""))
		   	   	   	  	addOne.setSlaveDescription(dt.getDateFormatMMddyyyySlash());
		   	   	   	  else
		   	   	   	    addOne.setSlaveDescription((String) returnDates.elementAt(y));
		   	   	   	  dualDD.addElement(addOne);
		   	   	   }
		   	   }
		   }
		   // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(dualDD, "fromCostType", "",  "3", "fromCostDate", "N", "B", "N");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Valid TO Cost Types:
	 *   Default Value will by 9 - Cost Type 9
	 */
	public String buildDropDownToCostType() {
		
		Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
	//	dds.setDescription("7 - Play Field");
	//	dds.setValue("7");
	//	buildList.addElement(dds);
	//	dds = new DropDownSingle();
	//	dds.setDescription("8 - Play Field");
	//	dds.setValue("8");
	//	buildList.addElement(dds);
	//	dds = new DropDownSingle();
		dds.setDescription("9 - Company Cost");
		dds.setValue("9");
		buildList.addElement(dds);
		return DropDownSingle.buildDropDown(buildList, "toCostType", "9", "None", "N", "N");
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Valid Fiscal Year's to choose from:
	 *   Default Value will be the first one
	 */
	public String buildDropDownFromFiscalYear() {
		
		Vector buildList = new Vector();
		try{
			buildList = ServiceCostTypes.dropDownFiscalYear(new Vector());
		}catch(Exception e)
		{	}
		return DropDownSingle.buildDropDown(buildList, "fromFiscalYear", "", "None", "N", "N");
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
	 * @return Returns the fromCostDate.
	 */
	public String getFromCostDate() {
		return fromCostDate;
	}
	/**
	 * @param fromCostDate The fromCostDate to set.
	 */
	public void setFromCostDate(String fromCostDate) {
		this.fromCostDate = fromCostDate;
	}
	/**
	 * @return Returns the fromCostType.
	 */
	public String getFromCostType() {
		return fromCostType;
	}
	/**
	 * @param fromCostType The fromCostType to set.
	 */
	public void setFromCostType(String fromCostType) {
		this.fromCostType = fromCostType;
	}
	/**
	 * @return Returns the toCostDate.
	 */
	public String getToCostDate() {
		return toCostDate;
	}
	/**
	 * @param toCostDate The toCostDate to set.
	 */
	public void setToCostDate(String toCostDate) {
		this.toCostDate = toCostDate;
	}
	/**
	 * @return Returns the toCostType.
	 */
	public String getToCostType() {
		return toCostType;
	}
	/**
	 * @param toCostType The toCostType to set.
	 */
	public void setToCostType(String toCostType) {
		this.toCostType = toCostType;
	}
	/**
	 * @return Returns the toCostDateFileFormat.
	 */
	public String getToCostDateFileFormat() {
		return toCostDateFileFormat;
	}
	/**
	 * @param toCostDateFileFormat The toCostDateFileFormat to set.
	 */
	public void setToCostDateFileFormat(String toCostDateFileFormat) {
		this.toCostDateFileFormat = toCostDateFileFormat;
	}
	/**
	 * @return Returns the shouldOverride.
	 */
	public String getShouldOverride() {
		return shouldOverride;
	}
	/**
	 * @param shouldOverride The shouldOverride to set.
	 */
	public void setShouldOverride(String shouldOverride) {
		this.shouldOverride = shouldOverride;
	}
	/**
	 * @return Returns the retryCompanyCost.
	 */
	public String getRetryCompanyCost() {
		return retryCompanyCost;
	}
	/**
	 * @param retryCompanyCost The retryCompanyCost to set.
	 */
	public void setRetryCompanyCost(String retryCompanyCost) {
		this.retryCompanyCost = retryCompanyCost;
	}
	/**
	 * @return Returns the page1.
	 */
	public String getPage1() {
		return page1;
	}
	/**
	 * @param page1 The page1 to set.
	 */
	public void setPage1(String page1) {
		this.page1 = page1;
	}
	/**
	 * @return Returns the fromFiscalYear.
	 */
	public String getFromFiscalYear() {
		return fromFiscalYear;
	}
	/**
	 * @param fromFiscalYear The fromFiscalYear to set.
	 */
	public void setFromFiscalYear(String fromFiscalYear) {
		this.fromFiscalYear = fromFiscalYear;
	}
	/**
	 * @return Returns the itemType.
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @param itemType The itemType to set.
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * Will Return to the Screen, a Drop Down List
	 *     Of Valid Fiscal Year's to choose from:
	 *   Default Value will be the first one
	 */
	public String buildDropDownItemType(String inItem) {
		
		Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
		dds.setDescription("100 - CPG");
		dds.setValue("100");
		buildList.addElement(dds);
		dds = new DropDownSingle();
		dds.setDescription("110 - Custom Pack");
		dds.setValue("110");
		buildList.addElement(dds);	
		dds = new DropDownSingle();
		dds.setDescription("120 - Dried/Frozen");
		dds.setValue("120");
		buildList.addElement(dds);	
		dds = new DropDownSingle();
		dds.setDescription("125 - Preps");
		dds.setValue("125");
		buildList.addElement(dds);	
		dds = new DropDownSingle();
		dds.setDescription("130 - Fresh Slice");
		dds.setValue("130");
		buildList.addElement(dds);		
		dds = new DropDownSingle();
		dds.setDescription("140 - Puree");
		dds.setValue("140");
		buildList.addElement(dds);	
		dds = new DropDownSingle();
		dds.setDescription("150 - Concentrate");
		dds.setValue("150");
		buildList.addElement(dds);		
		dds = new DropDownSingle();
		dds.setDescription("210 - Processed Fruit");
		dds.setValue("210");
		buildList.addElement(dds);	
		return DropDownSingle.buildDropDown(buildList, "itemType", inItem, "None", "N", "N");
	}
	/**
	 * @return Returns the itemNumber.
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
}
