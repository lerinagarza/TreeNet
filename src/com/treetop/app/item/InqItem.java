/*
 * Created on September 11, 2008
 *
 */

package com.treetop.app.item;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanItem;
import com.treetop.businessobjects.Item;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.html.*;

/**
 * @author twalto
 * 
 * Created for use in Item and Item Variance Information
 */
public class InqItem extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";
	public String environment = "PRD";

	// Must have in Update View Bean
	public String updateUser = "";
	public String allowUpdate = "N";
	
	public String inqItem = "";
	public String inqItemError = "";
	
	public String showPending = "";
		
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
	   	 	if (!requestType.equals("inqVariance") &&
	   	 		!requestType.trim().equals(""))
	   	 	{
	   	 		inqItemError = "You must choose an Item Number!";
	   	 		requestType = "inqVariance";
	   	 	}
	   	 }
		 if (!inqItemError.trim().equals(""))
			displayMessage = inqItemError.trim();

		return;
	}
	/**
	 * Get the Security to see if Update is allowed
	 * Creation date: (9/11/2008 -- TWalton)
	 */
	public void setUpdateSecurity(HttpServletRequest request,
						   		  HttpServletResponse response,
								  String appType) {
		try {
			String[] rolesR = SessionVariables.getSessionttiUserRoles(request,
					response);
			for (int xr = 0; xr < rolesR.length; xr++) {
				if (rolesR[xr].equals("8"))
				{	
					this.allowUpdate = "Y";
					xr = rolesR.length;
				}
			}
		} catch (Exception e) {
		}
		if (appType.equals("variance"))
		{
			try
			{
			   String[] groups = SessionVariables.getSessionttiUserGroups(request, response);
			   for (int xr = 0; xr < groups.length; xr++) {
				 if (groups[xr].equals("109"))
				 {	
					this.allowUpdate = "Y";
					xr = groups.length;
				 }
			   }
			} catch (Exception e) {
			}
		}
		return;
	}
	/**
	 * @return Returns the allowUpdate.
	 */
	public String getAllowUpdate() {
		return allowUpdate;
	}
	/**
	 * @param allowUpdate The allowUpdate to set.
	 */
	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
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
	 * @return Returns the showPending.
	 */
	public String getShowPending() {
		return showPending;
	}
	/**
	 * @param showPending The showPending to set.
	 */
	public void setShowPending(String showPending) {
		this.showPending = showPending;
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
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
