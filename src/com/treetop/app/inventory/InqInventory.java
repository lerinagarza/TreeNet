/*
 * Created on May 9, 2008
 *
 *  Will use to search for any inventory
 */

package com.treetop.app.inventory;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.businessobjects.Inventory;
import com.treetop.businessobjectapplications.BeanInventory;
import com.treetop.utilities.html.*;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;

/**
 * @author twalto
 *
 *   Use to search for Inventories
 *        List for FIFO Inventory - based on Customer Order  
 */
public class InqInventory extends BaseViewBeanR2 {
	
	//static fields to hold drop down vectors
	private static Vector listFacilityWarehouse = null;
	private static Vector listItemType = null;
	private static Vector listTaggingType = null;
	private static Vector listOwnerCode = null;
	private static Vector listRFItemGroup = null;
	
	// Standard Fields - to be in Every View Bean
	public String environment = "";
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	public String facilityFilter = ""; 
	
	public String inqItem = "";
	public String inqItemError = "";
	public String inqItemType = ""; // from drop down

	public String inqCustomerOrder = "";
	public String inqCustomerOrderError = "";
	public String inqCustomPackCode = "";
	
	public String inqStatus = "";
	public String inqStatusFrom = "";
	public String inqStatusTo = "";
	
	public String inqLot1 = "";
	public String inqLot1Error = "";
	public String inqLot2 = "";
	public String inqLot2Error = "";
	public String inqLot3 = "";
	public String inqLot3Error = "";
	public String inqLot4 = "";
	public String inqLot4Error = "";
	public String inqLot5 = "";
	public String inqLot5Error = "";
	public String inqLotFrom = "";
	public String inqLotTo = "";
	
	public String inqFacility = ""; //From Drop Down
	public String inqWhse = ""; // From Drop Down
	
	//added for Yard Inventory
	public String inqRunType = "";
	public String inqOrganicConventional = "";
	public String inqCOO = "";
	public String inqTGRADE = "";
	public String inqVariety = "";
	public String inqLocation = "";
	public String inqRFItemGroup = "";
	
	public String inqLotRef1 = "";
	public String inqLotRef2 = "";
	public String inqOwnerCode = ""; //From Drop Down
	public String inqTaggingType = ""; //From Drop Down
	public String inqRemark = "";
	
	public String inqLotsInInventory = "";
	
	public String inqShowDetails = "";
	public String inqShowDescriptions = "";
	
	public String displayErrors = "";

	public BeanInventory beanInventory = null;
	
	public Vector listReport = null;

	
	/**
	 * Default constructor
	 */
	public InqInventory() {
		
	}
	
	/**
	 * Constructor with request
	 * automatically populates
	 * @param request
	 */
	public InqInventory(HttpServletRequest request) {
		this.populate(request);
	}
	
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
		  inqItemError = ServiceItem.verifyItem(this.getEnvironment(), inqItem.trim());
		   	//  	System.out.println(inqItemError);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }	
	  if (!inqCustomerOrder.trim().equals(""))
	  {
		try
		{
		  inqCustomerOrderError = ServiceSalesOrder.verifySalesOrder(this.getEnvironment(), inqCustomerOrder.trim(), "");
		   	//  	System.out.println(inqCustomerOrderError);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }	
	  if (!inqLot1.trim().equals(""))
	  {
		try
		{
		  inqLot1Error = ServiceLot.verifyM3LotNumber(this.getEnvironment(), inqLot1);
		   	//  	System.out.println(inqLot1Error);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }		
	  if (!inqLot2.trim().equals(""))
	  {
		try
		{
		  inqLot2Error = ServiceLot.verifyM3LotNumber(this.getEnvironment(), inqLot2);
		   	//  	System.out.println(inqLot2Error);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }		
	  if (!inqLot3.trim().equals(""))
	  {
		try
		{
		  inqLot3Error = ServiceLot.verifyM3LotNumber(this.getEnvironment(), inqLot3);
		   	//  	System.out.println(inqLot3Error);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }	
	  if (!inqLot4.trim().equals(""))
	  {
		try
		{
		  inqLot4Error = ServiceLot.verifyM3LotNumber(this.getEnvironment(), inqLot4);
		   	//  	System.out.println(inqLot4Error);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }		
	  if (!inqLot5.trim().equals(""))
	  {
		try
		{
		  inqLot5Error = ServiceLot.verifyM3LotNumber(this.getEnvironment(), inqLot5);
		   	//  	System.out.println(inqLot5Error);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }	
	  if (!inqStatus.trim().equals(""))
	  {
		try
		{
		   if (inqStatus.length() == 2)
		   {
		   	  inqStatusFrom = inqStatus.substring(0,1);
		   	  inqStatusTo   = inqStatus.substring(1,2);
		   }
		   	//  	System.out.println(inqStatus);
		}
		catch(Exception e)
		{
//		   	 Just Catch the error, do not need to do anything with it.
		}
	  }		
	  StringBuffer allErrors = new StringBuffer();
	  if (!inqItemError.trim().equals(""))
	  	allErrors.append(inqItemError.trim());
	  if (!inqCustomerOrderError.trim().equals(""))
	  {
	  	if (!allErrors.toString().trim().equals(""))
	  	  allErrors.append("<br>");
	  	allErrors.append(inqCustomerOrderError);
	  }
	  if (!inqLot1Error.trim().equals(""))
	  {
	  	if (!allErrors.toString().trim().equals(""))
	  	  allErrors.append("<br>");
	  	allErrors.append(inqLot1Error);
	  }	  
	  if (!inqLot2Error.trim().equals(""))
	  {
	  	if (!allErrors.toString().trim().equals(""))
	  	  allErrors.append("<br>");
	  	allErrors.append(inqLot2Error);
	  }	
	  if (!inqLot3Error.trim().equals(""))
	  {
	  	if (!allErrors.toString().trim().equals(""))
	  	  allErrors.append("<br>");
	  	allErrors.append(inqLot3Error);
	  }	
	  if (!inqLot4Error.trim().equals(""))
	  {
	  	if (!allErrors.toString().trim().equals(""))
	  	  allErrors.append("<br>");
	  	allErrors.append(inqLot4Error);
	  }	  
	  if (!inqLot5Error.trim().equals(""))
	  {
	  	if (!allErrors.toString().trim().equals(""))
	  	  allErrors.append("<br>");
	  	allErrors.append(inqLot5Error);
	  }	  
	  if (allErrors.toString().trim().equals(""))
	  {
	  	if (requestType.equals("updLotReclass"))
	  	{
	  		// for this specific application make sure that SOMETHING is filled in
	  		//   Review ALL Sent in Fields
	  		if (inqFacility.trim().equals("") &&
	  			inqWhse.trim().equals("") &&
				inqItemType.trim().equals("") &&
				inqItem.trim().equals("") &&
				inqLotRef1.trim().equals("") &&
				inqLot1.trim().equals("") &&
				inqLot2.trim().equals("") &&
				inqLot3.trim().equals("") &&
				inqLot4.trim().equals("") &&
				inqLot5.trim().equals("") &&
				inqLotFrom.trim().equals("") &&
				inqLotTo.trim().equals(""))
	  		{	
	  		   allErrors.append("Please Narrow Down Your Selection!&nbsp;");
	  		   displayMessage = allErrors.toString().trim();
	  		}
	  	}
	  }
	  displayErrors = allErrors.toString().trim();
	  if (requestType.equals("updLotReclass") &&
	  	  displayMessage.trim().equals("") &&
		  !displayErrors.trim().equals(""))
	  {
	  	displayMessage = "There is a problem with your selection.  Please review your entries.";
	  }
	  return;
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
	public void setRequestType(String string) {
		requestType = string;
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
	 * @return Returns the beanInventory.
	 */
	public BeanInventory getBeanInventory() {
		return beanInventory;
	}
	/**
	 * @param beanInventory The beanInventory to set.
	 */
	public void setBeanInventory(BeanInventory beanInventory) {
		this.beanInventory = beanInventory;
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
	 * @return Returns the inqCustomerOrder.
	 */
	public String getInqCustomerOrder() {
		return inqCustomerOrder;
	}
	/**
	 * @param inqCustomerOrder The inqCustomerOrder to set.
	 */
	public void setInqCustomerOrder(String inqCustomerOrder) {
		this.inqCustomerOrder = inqCustomerOrder;
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
	 * @return Returns the inqCustomerOrderError.
	 */
	public String getInqCustomerOrderError() {
		return inqCustomerOrderError;
	}
	/**
	 * @param inqCustomerOrderError The inqCustomerOrderError to set.
	 */
	public void setInqCustomerOrderError(String inqCustomerOrderError) {
		this.inqCustomerOrderError = inqCustomerOrderError;
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
	 * @return Returns the inqShowDetails.
	 */
	public String getInqShowDetails() {
		return inqShowDetails;
	}
	/**
	 * @param inqShowDetails The inqShowDetails to set.
	 */
	public void setInqShowDetails(String inqShowDetails) {
		this.inqShowDetails = inqShowDetails;
	}
	/**
	 * @return Returns the inqCustomPackCode.
	 */
	public String getInqCustomPackCode() {
		return inqCustomPackCode;
	}
	/**
	 * @param inqCustomPackCode The inqCustomPackCode to set.
	 */
	public void setInqCustomPackCode(String inqCustomPackCode) {
		this.inqCustomPackCode = inqCustomPackCode;
	}
	/**
	 *      Method created 8/26/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public static String buildDropDownStatus(String inStatus) {
		String dropDown = "";
		try
		{
			Vector buildList1 = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setDescription("Inspection(1) To Inspection(1)");
			dds.setValue("11");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Inspection(1) To Released(2)");
			dds.setValue("12");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Inspection(1) To Rejected(3)");
			dds.setValue("13");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Released(2) To Inspection(1)");
			dds.setValue("21");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Released(2) To Released(2)");
			dds.setValue("22");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Released(2) To Rejected(3)");
			dds.setValue("23");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Rejected(3) To Inspection(1)");
			dds.setValue("31");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Rejected(3) To Released(2)");
			dds.setValue("32");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Rejected(3) To Rejected(3)");
			dds.setValue("33");
			buildList1.addElement(dds);
			
			dropDown = DropDownSingle.buildDropDown(buildList1, "inqStatus", inStatus, "None", "N", "N");	
			
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	
	/**
	 * returns drop down of each inventory status
	 * @param inStatus
	 * @return
	 */
	public static String buildDropDownStatusList(String inStatus) {
		String dropDown = "";
		try
		{
			Vector buildList1 = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setDescription("*all");
			dds.setValue("");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Inspection (1)");
			dds.setValue("1");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Released (2)");
			dds.setValue("2");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Rejected (3)");
			dds.setValue("3");
			buildList1.addElement(dds);
	
			dropDown = DropDownSingle.buildDropDown(buildList1, "inqStatus", inStatus, "None", "N", "N", "50%");	
			
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
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
	 * @return Returns the inqLot1.
	 */
	public String getInqLot1() {
		return inqLot1;
	}
	/**
	 * @param inqLot1 The inqLot1 to set.
	 */
	public void setInqLot1(String inqLot1) {
		this.inqLot1 = inqLot1;
	}
	/**
	 * @return Returns the inqLot1Error.
	 */
	public String getInqLot1Error() {
		return inqLot1Error;
	}
	/**
	 * @param inqLot1Error The inqLot1Error to set.
	 */
	public void setInqLot1Error(String inqLot1Error) {
		this.inqLot1Error = inqLot1Error;
	}
	/**
	 * @return Returns the inqLot2.
	 */
	public String getInqLot2() {
		return inqLot2;
	}
	/**
	 * @param inqLot2 The inqLot2 to set.
	 */
	public void setInqLot2(String inqLot2) {
		this.inqLot2 = inqLot2;
	}
	/**
	 * @return Returns the inqLot2Error.
	 */
	public String getInqLot2Error() {
		return inqLot2Error;
	}
	/**
	 * @param inqLot2Error The inqLot2Error to set.
	 */
	public void setInqLot2Error(String inqLot2Error) {
		this.inqLot2Error = inqLot2Error;
	}
	/**
	 * @return Returns the inqLot3.
	 */
	public String getInqLot3() {
		return inqLot3;
	}
	/**
	 * @param inqLot3 The inqLot3 to set.
	 */
	public void setInqLot3(String inqLot3) {
		this.inqLot3 = inqLot3;
	}
	/**
	 * @return Returns the inqLot3Error.
	 */
	public String getInqLot3Error() {
		return inqLot3Error;
	}
	/**
	 * @param inqLot3Error The inqLot3Error to set.
	 */
	public void setInqLot3Error(String inqLot3Error) {
		this.inqLot3Error = inqLot3Error;
	}
	/**
	 * @return Returns the inqLot4.
	 */
	public String getInqLot4() {
		return inqLot4;
	}
	/**
	 * @param inqLot4 The inqLot4 to set.
	 */
	public void setInqLot4(String inqLot4) {
		this.inqLot4 = inqLot4;
	}
	/**
	 * @return Returns the inqLot4Error.
	 */
	public String getInqLot4Error() {
		return inqLot4Error;
	}
	/**
	 * @param inqLot4Error The inqLot4Error to set.
	 */
	public void setInqLot4Error(String inqLot4Error) {
		this.inqLot4Error = inqLot4Error;
	}
	/**
	 * @return Returns the inqLot5.
	 */
	public String getInqLot5() {
		return inqLot5;
	}
	/**
	 * @param inqLot5 The inqLot5 to set.
	 */
	public void setInqLot5(String inqLot5) {
		this.inqLot5 = inqLot5;
	}
	/**
	 * @return Returns the inqLot5Error.
	 */
	public String getInqLot5Error() {
		return inqLot5Error;
	}
	/**
	 * @param inqLot5Error The inqLot5Error to set.
	 */
	public void setInqLot5Error(String inqLot5Error) {
		this.inqLot5Error = inqLot5Error;
	}
	/**
	 * @return Returns the inqLotFrom.
	 */
	public String getInqLotFrom() {
		return inqLotFrom;
	}
	/**
	 * @param inqLotFrom The inqLotFrom to set.
	 */
	public void setInqLotFrom(String inqLotFrom) {
		this.inqLotFrom = inqLotFrom;
	}
	/**
	 * @return Returns the inqLotRef1.
	 */
	public String getInqLotRef1() {
		return inqLotRef1;
	}
	/**
	 * @param inqLotRef1 The inqLotRef1 to set.
	 */
	public void setInqLotRef1(String inqLotRef1) {
		this.inqLotRef1 = inqLotRef1;
	}
	/**
	 * @return Returns the inqLotTo.
	 */
	public String getInqLotTo() {
		return inqLotTo;
	}
	/**
	 * @param inqLotTo The inqLotTo to set.
	 */
	public void setInqLotTo(String inqLotTo) {
		this.inqLotTo = inqLotTo;
	}
	/**
	 * @return Returns the inqStatus.
	 */
	public String getInqStatus() {
		return inqStatus;
	}
	/**
	 * @param inqStatus The inqStatus to set.
	 */
	public void setInqStatus(String inqStatus) {
		this.inqStatus = inqStatus;
	}
	/**
	 * @return Returns the inqStatusFrom.
	 */
	public String getInqStatusFrom() {
		return inqStatusFrom;
	}
	/**
	 * @param inqStatusFrom The inqStatusFrom to set.
	 */
	public void setInqStatusFrom(String inqStatusFrom) {
		this.inqStatusFrom = inqStatusFrom;
	}
	/**
	 * @return Returns the inqStatusTo.
	 */
	public String getInqStatusTo() {
		return inqStatusTo;
	}
	/**
	 * @param inqStatusTo The inqStatusTo to set.
	 */
	public void setInqStatusTo(String inqStatusTo) {
		this.inqStatusTo = inqStatusTo;
	}
	/**
	 * @return Returns the inqWhse.
	 */
	public String getInqWhse() {
		return inqWhse;
	}
	/**
	 * @param inqWhse The inqWhse to set.
	 */
	public void setInqWhse(String inqWhse) {
		this.inqWhse = inqWhse;
	}
	/**
	 * @return Returns the inqItemType.
	 */
	public String getInqItemType() {
		return inqItemType;
	}
	/**
	 * @param inqItemType The inqItemType to set.
	 */
	public void setInqItemType(String inqItemType) {
		this.inqItemType = inqItemType;
	}
	/**
	 *      Method created 8/27/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *      the ddInfo will be used if something special is needed with the Drop Down List
	 *     STATIC method can be called from anywhere.
	 */
	public static String buildDropDownFacility(InqInventory ddInfo) {
		String dropDown = "&nbsp;";
		try
		{
			//  Go to the ServiceInventory and get a listing of Valid Inventory Related Facilities
			Vector sendParms = new Vector();
			sendParms.addElement("ddFacility");
			sendParms.addElement(ddInfo); 

			Vector listFacility = ServiceInventory.listDropDown(sendParms);
			if (listFacility != null && 
				listFacility.size() > 0)
			{
				Vector buildList = new Vector();
				DropDownSingle dds = new DropDownSingle();
				for (int x = 0; x < listFacility.size(); x++)
				{
					dds = new DropDownSingle();
					Inventory i = (Inventory) listFacility.elementAt(x);
					dds.setDescription(i.getFacilityDescription());
					dds.setValue(i.getFacility());
					buildList.addElement(dds);	
				}
			   dropDown = DropDownSingle.buildDropDown(buildList, "inqFacility", ddInfo.getInqFacility(), "", "", "N");
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			 System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *      Method created 8/27/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *      the ddInfo will be used if something special is needed with the Drop Down List
	 *     STATIC method can be called from anywhere.
	 */
	public static String buildDropDownWarehouse(InqInventory ddInfo) {
		String dropDown = "&nbsp;";
		try
		{
			//  Go to the ServiceInventory and get a listing of Valid Inventory Related Facilities
			Vector sendParms = new Vector();
			sendParms.addElement("ddWarehouse");
			sendParms.addElement(ddInfo); 
			Vector listWarehouse = ServiceInventory.listDropDown(sendParms);
			if (listWarehouse != null && 
				listWarehouse.size() > 0)
			{
				Vector buildList = new Vector();
				DropDownSingle dds = new DropDownSingle();
				for (int x = 0; x < listWarehouse.size(); x++)
				{
					dds = new DropDownSingle();
					Inventory i = (Inventory) listWarehouse.elementAt(x);
					dds.setDescription(i.getWarehouseDescription());
					dds.setValue(i.getWarehouse());
					buildList.addElement(dds);	
				}
			   dropDown = DropDownSingle.buildDropDown(buildList, "inqWhse", ddInfo.getInqWhse(), "", "", "N");
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			 System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *      Method created 8/27/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *      the ddInfo will be used if something special is needed with the Drop Down List
	 *     STATIC method can be called from anywhere.
	 */
	public static String buildDropDownItemType(InqInventory ddInfo) {
		String dropDown = "&nbsp;";
		try
		{
			//  Go to the ServiceInventory and get a listing of Valid Inventory Related Facilities
			Vector sendParms = new Vector();
			sendParms.addElement("ddItemType");
			sendParms.addElement(ddInfo); 
			
			if (listItemType == null) {
				listItemType = ServiceInventory.listDropDown(sendParms);
			}
			
			if (listItemType != null && 
				listItemType.size() > 0)
			{
				Vector buildList = new Vector();
				DropDownSingle dds = new DropDownSingle();
				for (int x = 0; x < listItemType.size(); x++)
				{
					dds = new DropDownSingle();
					Inventory i = (Inventory) listItemType.elementAt(x);
					dds.setDescription(i.getItemTypeDescription());
					dds.setValue(i.getItemType());
					buildList.addElement(dds);	
				}
			   dropDown = DropDownSingle.buildDropDown(buildList, "inqItemType", ddInfo.getInqItemType(), "", "", "N");
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			 System.out.println("Error" + e);
		}
		return dropDown;
	}
	
	public String buildSelectionCriteria() {
		SelectionCriteria sc = new SelectionCriteria();
		
		sc.addValue("Item Number", this.getInqItem());
		sc.addValue("Item Type", this.getInqItemType());
		sc.addValue("Customer Order Number", this.getInqCustomerOrder());
		sc.addValue("Custom Pack Code", this.getInqCustomPackCode());
		sc.addValue("Show Details", this.getInqShowDetails());
		sc.addValue("From Status", this.getInqStatusFrom());
		sc.addValue("To Status", this.getInqStatusTo());
		sc.addValue("Facility", this.getInqFacility());
		sc.addValue("Warehouse", this.getInqWhse());
		
		sc.addValue("Lot 1", this.getInqLot1());
		sc.addValue("Lot 2", this.getInqLot2());
		sc.addValue("Lot 3", this.getInqLot3());
		sc.addValue("Lot 4", this.getInqLot4());
		sc.addValue("Lot 5", this.getInqLot5());
		sc.addValue("Lot From", this.getInqLotFrom());
		sc.addValue("Lot To", this.getInqLotTo());
		sc.addValue("Lot Ref 1", this.getInqLotRef1());
		sc.addValue("Lot Ref 2", this.getInqLotRef2());
		
		sc.addValue("Owner Code", this.getInqOwnerCode());
		sc.addValue("Tagging Type", this.getInqTaggingType());
		sc.addValue("Hold Comments", this.getInqRemark());
		
		sc.addValue("Run Type", this.getInqRunType());
		sc.addValue("Organic / Conventional", this.getInqOrganicConventional());
		sc.addValue("Country of Origin", this.getInqCOO());
		sc.addValue("TGRADE Value", this.getInqTGRADE());
		sc.addValue("Location", this.getInqVariety());
		
		return sc.toString();
	}
	
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!inqItem.equals(""))
		{
			returnString.append("Item Number: ");
			returnString.append(inqItem);			  
		}
		if (!inqItemType.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Item Type: ");
			returnString.append(inqItemType);
		}
		if (!inqCustomerOrder.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Customer Order Number: ");
			returnString.append(inqCustomerOrder);
		}	
		if (!inqCustomPackCode.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Custom Pack Code: ");
			returnString.append(inqCustomPackCode);
		}		
		if (!inqShowDetails.equals(""))
		{
			if (!returnString.toString().equals(""))
				  returnString.append("<br>");
				returnString.append("Show the Details");
		}
		if (!inqStatusFrom.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("From Status: ");
			returnString.append(inqStatusFrom);
		}
		if (!inqStatusTo.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("To Status: ");
			returnString.append(inqStatusTo);
		}						
		if (!inqFacility.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Facility: ");
			returnString.append(inqFacility);
		}		
		if (!inqWhse.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Warehouse: ");
			returnString.append(inqWhse);
		}		
		if (!inqLot1.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot 1: ");
			returnString.append(inqLot1);
		}
		if (!inqLot2.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot 2: ");
			returnString.append(inqLot2);
		}	
		if (!inqLot3.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot 3: ");
			returnString.append(inqLot3);
		}	
		if (!inqLot4.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot 4: ");
			returnString.append(inqLot4);
		}	
		if (!inqLot5.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot 5: ");
			returnString.append(inqLot5);
		}	
		if (!inqLotFrom.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot From: ");
			returnString.append(inqLotFrom);
		}
		if (!inqLotTo.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot To: ");
			returnString.append(inqLotTo);
		}	
		if (!inqLotRef1.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot Ref 1: ");
			returnString.append(inqLotRef1);
		}	
		if (!inqLotRef2.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Lot Ref 2: ");
			returnString.append(inqLotRef2);
		}	
		if (!inqOwnerCode.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Owner Code: ");
			returnString.append(inqOwnerCode);
		}	
		if (!inqTaggingType.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Tagging Type: ");
			returnString.append(inqTaggingType);
		}	
		if (!inqRemark.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Remark: ");
			returnString.append(inqRemark);
		}	
		if (!inqRunType.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Run Type: ");
			returnString.append(inqRunType);
		}	
		if (!inqOrganicConventional.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Organic / Conventional: ");
			returnString.append(inqOrganicConventional);
		}	
		if (!inqCOO.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Country of Origin: ");
			returnString.append(inqCOO);
		}	
		if (!inqTGRADE.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("TGRADE Value: ");
			returnString.append(inqTGRADE);
		}	
		if (!inqVariety.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Variety: ");
			returnString.append(inqVariety);
		}	
		if (!inqLocation.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Location: ");
			returnString.append(inqLocation);
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
		if (!inqItem.equals(""))
		{// Item Number
			returnString.append("&inqItem=");
			returnString.append(inqItem);
		}
		if (!inqItemType.equals(""))
		{
			returnString.append("&inqItemType=");
			returnString.append(inqItemType);
		}
		if (!inqCustomerOrder.equals(""))
		{
			returnString.append("&inqCustomerOrder=");
			returnString.append(inqCustomerOrder);
		}	
		if (!inqCustomPackCode.equals(""))
		{// Trade Item Unit Descriptor
			returnString.append("&inqCustomPackCode=");
			returnString.append(inqCustomPackCode);
		}
		if (!inqStatus.equals(""))
		{
			returnString.append("&inqStatus=");
			returnString.append(inqStatus);
		}
		if (!inqStatusFrom.equals("all"))
		{
			returnString.append("&inqStatusFrom=");
			returnString.append(inqStatusFrom);
		}			
		if (!inqStatusTo.equals(""))
		{
			returnString.append("&inqStatusTo=");
			returnString.append(inqStatusTo);
		}
		if (inqShowDetails != null &&
			!inqShowDetails.equals(""))
		{
			returnString.append("&inqShowDetails=");
			returnString.append(inqShowDetails);
		}
		if (!inqFacility.equals(""))
		{
			returnString.append("&inqFacility=");
			returnString.append(inqFacility);
		}
		if (!inqWhse.equals(""))
		{
			returnString.append("&inqWhse=");
			returnString.append(inqWhse);
		}
		if (!inqLotRef1.equals(""))
		{
			returnString.append("&inqLotRef1=");
			returnString.append(inqLotRef1);
		}
		if (!inqLotRef2.equals(""))
		{
			returnString.append("&inqLotRef2=");
			returnString.append(inqLotRef2);
		}
		if (!inqOwnerCode.equals(""))
		{
			returnString.append("&inqOwnerCode=");
			returnString.append(inqOwnerCode);
		}
		if (!inqTaggingType.equals(""))
		{
			returnString.append("&inqTaggingType=");
			returnString.append(inqTaggingType);
		}
		if (!inqRemark.equals(""))
		{
			returnString.append("&inqRemark=");
			returnString.append(inqRemark);
		}
		if (!inqLot1.equals(""))
		{
			returnString.append("&inqLot1=");
			returnString.append(inqLot1);
		}
		if (!inqLot2.equals(""))
		{
			returnString.append("&inqLot2=");
			returnString.append(inqLot2);
		}
		if (!inqLot3.equals(""))
		{
			returnString.append("&inqLot3=");
			returnString.append(inqLot3);
		}
		if (!inqLot4.equals(""))
		{
			returnString.append("&inqLot4=");
			returnString.append(inqLot4);
		}
		if (!inqLot5.equals(""))
		{
			returnString.append("&inqLot5=");
			returnString.append(inqLot5);
		}
		if (!inqLotFrom.equals(""))
		{
			returnString.append("&inqLotFrom=");
			returnString.append(inqLotFrom);
		}
		if (!inqLotTo.equals(""))
		{
			returnString.append("&inqLotTo=");
			returnString.append(inqLotTo);
		}
		if (!inqShowDescriptions.equals(""))
		{
			returnString.append("&inqShowDescriptions=");
			returnString.append(inqShowDescriptions);
		}
		if (!inqRunType.equals(""))
		{
			returnString.append("&inqRunType=");
			returnString.append(inqRunType);
		}
		if (!inqOrganicConventional.equals(""))
		{
			returnString.append("&inqOrganicConventional=");
			returnString.append(inqOrganicConventional);
		}
		if (!inqCOO.equals(""))
		{
			returnString.append("&inqCOO=");
			returnString.append(inqCOO);
		}
		if (!inqTGRADE.equals(""))
		{
			returnString.append("&inqTGRADE=");
			returnString.append(inqTGRADE);
		}
		if (!inqVariety.equals(""))
		{
			returnString.append("&inqVariety=");
			returnString.append(inqVariety);
		}
		if (!inqLocation.equals(""))
		{
			returnString.append("&inqLocation=");
			returnString.append(inqLocation);
		}
		return returnString.toString();
	}
	/**
	 * @return Returns the inqShowDescriptions.
	 */
	public String getInqShowDescriptions() {
		return inqShowDescriptions;
	}
	/**
	 * @param inqShowDescriptions The inqShowDescriptions to set.
	 */
	public void setInqShowDescriptions(String inqShowDescriptions) {
		this.inqShowDescriptions = inqShowDescriptions;
	}
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Facility and then getting the Appropriate Warehouses
	 *    along with it.
	 */
	@Deprecated  //use getFacilityWarehouseDDValues()
	public static Vector buildDropDownFacilityWarehouse(String inFacility, String inWarehouse) {
		Vector dd = new Vector();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddRFInventory");
		   Vector getRecords = ServiceWarehouse.dualDropDownWarehouseFacility("PRD", sentValues);
		   
	      // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(getRecords, "inqFacility", "*all", inFacility, "inqWhse", "*all", inWarehouse, "B", "B");
		   
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	public static Vector<HtmlOption> getFacilityWarehouseDDValues() {
		Vector<String> sentValues = new Vector<String>();
		sentValues.addElement("ddRFInventory");
		return ServiceWarehouse.dualDropDownWarehouseFacility("PRD", sentValues);
	}
	
	
	/**
	 *      Method created 9/8/08  TWalton
	 * Will Return to the Screen,
	 *    String which will be the Description of the Status
	 */
	public static String returnStatusDescription(String inStatus) {
		String returnString = "";
		try
		{
			if (inStatus.trim().equals("1"))
			   returnString = "1 - Incubation";
			if (inStatus.trim().equals("2"))
			   returnString = "2 - Released";
			if (inStatus.trim().equals("3"))
			   returnString = "3 - Rejected";
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return returnString;
	}
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Facility and then getting the Appropriate Warehouses
	 *    along with it.
	 */
	public static Vector buildDualDropDownFacilityWarehouse(String inFacility, String inWarehouse, String facilityFilter) {
		Vector dd = new Vector();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("dddInventory");
		   sentValues.addElement(facilityFilter);
		   if (listFacilityWarehouse == null) {
			   listFacilityWarehouse = ServiceWarehouse.dualDropDownWarehouseFacility("PRD", sentValues);
		   }
		   
	      // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(listFacilityWarehouse, "inqFacility", "*all", inFacility, "inqWhse", "*all", inWarehouse, "B", "B");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Facility and then getting the Appropriate Warehouses
	 *    along with it.
	 */
	public static Vector getListDualDropDownFacilityWarehouse(String facilityFilter) {

		try {
		   Vector sentValues = new Vector();
		   sentValues.addElement("dddInventory");
		   sentValues.addElement(facilityFilter);
		   if (listFacilityWarehouse == null) {
			   listFacilityWarehouse = ServiceWarehouse.dualDropDownWarehouseFacility("PRD", sentValues);
		   }
		 
		} catch(Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		
		return listFacilityWarehouse;
	}
	
	
	
	public String getInqLotRef2() {
		return inqLotRef2;
	}
	public void setInqLotRef2(String inqLotRef2) {
		this.inqLotRef2 = inqLotRef2;
	}
	public String getInqOwnerCode() {
		return inqOwnerCode;
	}
	public void setInqOwnerCode(String inqOwnerCode) {
		this.inqOwnerCode = inqOwnerCode;
	}
	public String getInqRemark() {
		return inqRemark;
	}
	public void setInqRemark(String inqRemark) {
		this.inqRemark = inqRemark;
	}
	public String getInqTaggingType() {
		return inqTaggingType;
	}
	public void setInqTaggingType(String inqTaggingType) {
		this.inqTaggingType = inqTaggingType;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 *    
	 *    Disposition will be Owner Code -- should be that value
	 */
	public static String buildDropDownOwner(String inValue, 
										    String readOnly) {
		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddOwner");
		   if (listOwnerCode == null) {
			   listOwnerCode = ServiceInventory.listDropDown(sentValues);
		   }
		   
		   	// Call the Build utility to build the actual code for the drop down.
		     dd = DropDownSingle.buildDropDown(listOwnerCode, "inqOwnerCode", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			System.out.println("Caught Error: " + e);
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 */
	public static String buildDropDownTaggingType(String inValue, 
										  		  String readOnly) {
		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddTaggingTypeLimit");
		   
		   if (listTaggingType == null) {
			   listTaggingType = ServiceInventory.listDropDown(sentValues);
		   }
		   	// Call the Build utility to build the actual code for the drop down.
		     dd = DropDownSingle.buildDropDown(listTaggingType, "inqTaggingType", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			System.out.println("Caught Error: " + e);
		}
		return dd;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public static Vector getListFacilityWarehouse() {
		return listFacilityWarehouse;
	}
	public static void setListFacilityWarehouse(Vector listFacilityWarehouse) {
		InqInventory.listFacilityWarehouse = listFacilityWarehouse;
	}
	public static Vector getListItemType() {
		return listItemType;
	}
	public static void setListItemType(Vector listItemType) {
		InqInventory.listItemType = listItemType;
	}
	public static Vector getListTaggingType() {
		return listTaggingType;
	}
	public static void setListTaggingType(Vector listTaggingType) {
		InqInventory.listTaggingType = listTaggingType;
	}
	public static Vector getListOwnerCode() {
		return listOwnerCode;
	}
	public static void setListOwnerCode(Vector listOwnerCode) {
		InqInventory.listOwnerCode = listOwnerCode;
	}

	public String getInqRunType() {
		return inqRunType;
	}

	public void setInqRunType(String inqRunType) {
		this.inqRunType = inqRunType;
	}

	public String getInqOrganicConventional() {
		return inqOrganicConventional;
	}

	public void setInqOrganicConventional(String inqOrganicConventional) {
		this.inqOrganicConventional = inqOrganicConventional;
	}

	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * 
	 */
	public static String buildDropDownConvOrganic(String inValue, String readOnly) {
		String dd = new String();
		try
		{
			Vector sentValues = new Vector();
			sentValues.addElement("ddOrganic");
			Vector ddOrganic = ServiceInventory.listDropDown(sentValues);
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddOrganic, "inqOrganicConventional", inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 */
	public static String buildDropDownRunType(String inValue, String readOnly) {
		String dd = new String();
		try
		{
			Vector sentValues = new Vector();
			sentValues.addElement("ddRunType");
			Vector ddRunType = ServiceInventory.listDropDown(sentValues);
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddRunType, "inqRunType", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 */
	public static String buildDropDownCOO(String inValue, String readOnly) {
		String dd = new String();
		try
		{
			Vector sentValues = new Vector();
			sentValues.addElement("ddCOO");
			Vector ddCOO = ServiceInventory.listDropDown(sentValues);
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddCOO, "inqCOO", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 */
	public static String buildDropDownTGRADE(String inValue, String readOnly) {
		String dd = new String();
		try
		{
			Vector sentValues = new Vector();
			sentValues.addElement("ddTGRADE");
			Vector ddTGRADE = ServiceInventory.listDropDown(sentValues);
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddTGRADE, "inqTGRADE", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 */
	public static String buildDropDownVariety(String inValue, String readOnly) {
		String dd = new String();
		try
		{
			Vector sentValues = new Vector();
			sentValues.addElement("ddVariety");
			Vector ddVariety = ServiceInventory.listDropDown(sentValues);
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddVariety, "inqVariety", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	public String getInqCOO() {
		return inqCOO;
	}

	public void setInqCOO(String inqCOO) {
		this.inqCOO = inqCOO;
	}

	public String getInqTGRADE() {
		return inqTGRADE;
	}

	public void setInqTGRADE(String inqTGRADE) {
		this.inqTGRADE = inqTGRADE;
	}

	public String getInqVariety() {
		return inqVariety;
	}

	public void setInqVariety(String inqVariety) {
		this.inqVariety = inqVariety;
	}

	public String getInqLocation() {
		return inqLocation;
	}

	public void setInqLocation(String inqLocation) {
		this.inqLocation = inqLocation;
	}

	/**
	 * @return the inqLotsInInventory
	 */
	public String getInqLotsInInventory() {
		return inqLotsInInventory;
	}

	/**
	 * @param inqLotsInInventory the inqLotsInInventory to set
	 */
	public void setInqLotsInInventory(String inqLotsInInventory) {
		this.inqLotsInInventory = inqLotsInInventory;
	}

	public String getFacilityFilter() {
		return facilityFilter;
	}

	public void setFacilityFilter(String facilityFilter) {
		this.facilityFilter = facilityFilter;
	}

	public String getInqRFItemGroup() {
		return inqRFItemGroup;
	}

	public void setInqRFItemGroup(String inqRFItemGroup) {
		this.inqRFItemGroup = inqRFItemGroup;
	}

	/**
	 *      Method created 1/29/13  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *      the ddInfo will be used if something special is needed with the Drop Down List
	 *     STATIC method can be called from anywhere.
	 */
	public static String buildDropDownRFItemGroup() {
		String dropDown = "&nbsp;";
		try
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("PRD");
			crb.setIdLevel1("rawFruit");
			
			if (listRFItemGroup == null) {
				listRFItemGroup = ServiceItem.dropDownItemGroup(crb);
			}
			
			if (listRFItemGroup != null && 
				listRFItemGroup.size() > 0)
			{
				dropDown = DropDownSingle.buildDropDown(listRFItemGroup, "inqRFItemGroup", "", "", "", "N");
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			 System.out.println("Error" + e);
		}
		return dropDown;
	}

	public static Vector getListRFItemGroup() {
		return listRFItemGroup;
	}

	public static void setListRFItemGroup(Vector listRFItemGroup) {
		InqInventory.listRFItemGroup = listRFItemGroup;
	}

}
