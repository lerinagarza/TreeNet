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
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.businessobjectapplications.BeanInventory;
import com.treetop.businessobjects.Inventory;
import com.treetop.utilities.html.*;
import com.treetop.app.inventory.InqInventory;

/**
 * @author twalto
 *
 *   Use to search for Inventories
 *        List for FIFO Inventory - based on Customer Order  
 */
public class UpdInventory extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   	= "";
	public String displayMessage 	= "";
	public String environment		= "";
	// Must have in Update View Bean
	public String updateUser = "";
	public String userAuthorization = "";
	
	//Parameters for Request
	public String customPackCode = "";
	public String inqStatusFrom = "";
	public String inqStatusTo   = "";
	
	// Additional Update Fields
	public String expirationDate    = "";
	public String followUpDate      = "";
	public String salesDate	        = "";
	public String transactionReason = "";
	public String holdReason		= "";
	public String lotRef2			= "";
	public String remark			= "";
	public String taggingType		= "";
	public String disposition		= ""; // Owner
	
	
	// Includes Item, Lot, checked? and Classification of Inventory
	public Vector listLots = new Vector();
	
	public String displayErrors = "";
	
	public String goButton = "";
	public String getList  = ""; // This is used to go back and reselect the listing
	
	public BeanInventory beanInventory = null;

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {

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
	 * @return Returns the goButton.
	 */
	public String getGoButton() {
		return goButton;
	}
	/**
	 * @param goButton The goButton to set.
	 */
	public void setGoButton(String goButton) {
		this.goButton = goButton;
	}
	/**
	 * @return Returns the customPackCode.
	 */
	public String getCustomPackCode() {
		return customPackCode;
	}
	/**
	 * @param customPackCode The customPackCode to set.
	 */
	public void setCustomPackCode(String customPackCode) {
		this.customPackCode = customPackCode;
	}
	/**
	 * Return a Vector of Dates and Times, to display
	 *    on the insertSnapshot.jsp -- to know the last date and time it was done.
	 * 
	 * Creation date: (7/25/2008 -- TWalton)
	 */
	public static Vector snapshotDates(String customPackCode) {
		Vector returnValue = new Vector();;
		try {
			InqInventory sendValues = new InqInventory();
			sendValues.setInqCustomPackCode(customPackCode);
			returnValue = ServiceInventory.inventorySnapshotDates(sendValues);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * @return Returns the listLots.
	 */
	public Vector getListLots() {
		return listLots;
	}
	/**
	 * @param listLots The listLots to set.
	 */
	public void setListLots(Vector listLots) {
		this.listLots = listLots;
	}
	/**
	 * @return Returns the getList.
	 */
	public String getGetList() {
		return getList;
	}
	/**
	 * @param getList The getList to set.
	 */
	public void setGetList(String getList) {
		this.getList = getList;
	}
	/*
	 * Bring in information directly relating to the List of Lot Numbers
	 *    Process the incoming parameters relating to these lots
	 *    Add information to the Inventory Objects... 
	 *    Review the information in the BeanInventory and put the
	 *    new/changed information BACK to the BeanInventory field 
	 */
	public void updateListLots(HttpServletRequest request) {
		try
		{
			if (this.beanInventory.getByItemVectorOfInventory() != null &&
				this.beanInventory.getByItemVectorOfInventory().size() > 0)
			{
				Vector newListOfLots = new Vector();
				for (int x = 0; x < this.beanInventory.getByItemVectorOfInventory().size(); x++)
				{
				   Inventory i = (Inventory) this.beanInventory.getByItemVectorOfInventory().elementAt(x);
				   try
				   {
				     i.setCheckedValue(request.getParameter(x + i.getLotNumber())); 	
				   }
				   catch(Exception e)
				   {}
				   newListOfLots.add(i);	
				}
				this.beanInventory.setByItemVectorOfInventory(newListOfLots);
			}
		}
		catch(Exception e)
		{}
		return;
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
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getFollowUpDate() {
		return followUpDate;
	}
	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}
	public String getHoldReason() {
		return holdReason;
	}
	public void setHoldReason(String holdReason) {
		this.holdReason = holdReason;
	}
	public String getLotRef2() {
		return lotRef2;
	}
	public void setLotRef2(String lotRef2) {
		this.lotRef2 = lotRef2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public String getTransactionReason() {
		return transactionReason;
	}
	public void setTransactionReason(String transactionReason) {
		this.transactionReason = transactionReason;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 */
	public static String buildDropDownTransactionReason(String inValue, 
										  String readOnly) {
		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddTransactionReason");
		   Vector getRecords = ServiceInventory.listDropDown(sentValues);
		   	// Call the Build utility to build the actual code for the drop down.
		     dd = DropDownSingle.buildDropDown(getRecords, "transactionReason", inValue, "Choose One", "B", readOnly);
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
		   sentValues.addElement("ddTaggingType");
		   Vector getRecords = ServiceInventory.listDropDown(sentValues);
		   	// Call the Build utility to build the actual code for the drop down.
		     dd = DropDownSingle.buildDropDown(getRecords, "taggingType", inValue, "None", "B", readOnly);
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
	 *    
	 *    Disposition will be Owner Code -- should be that value
	 */
	public static String buildDropDownDisposition(String inValue, 
										          String readOnly) {
		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddDisposition");
		   Vector getRecords = ServiceInventory.listDropDown(sentValues);
		   	// Call the Build utility to build the actual code for the drop down.
		     dd = DropDownSingle.buildDropDown(getRecords, "disposition", inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			System.out.println("Caught Error: " + e);
		}
		return dd;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getTaggingType() {
		return taggingType;
	}
	public void setTaggingType(String taggingType) {
		this.taggingType = taggingType;
	}
	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getUserAuthorization() {
		return userAuthorization;
	}
	public void setUserAuthorization(String userAuthorization) {
		this.userAuthorization = userAuthorization;
	}
}
