/*
 * Created on Sept 12, 2008
 *
 */

package com.treetop.app.item;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.Item;
import com.treetop.businessobjects.ItemVariance;
import com.treetop.businessobjects.DateTime;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.html.*;
import com.treetop.utilities.UtilityDateTime;

/**
 * @author twalto
 * 
 * Will use for Update of Variances by Item
 *   ONE Variance at a time - to be updated
 */
public class UpdItemVariance extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	public String goButton = "";
	
	public String item = "";
	public String itemError = "";
	public String itemDescription = "";
	public String varianceText = "";
	public String dateIssued = "0";
	public String originalDateIssued = "0";
	public String dateExpired = "0";
	public String originalDateExpired = "0";
	public String varianceComment = "";
	public String allowUpdate = "";
	public String recordStatus = "";
	public String updDate = "";
	public String updTime = "";
	public String updUser = "";

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		if (!item.trim().equals(""))
		{
			try
			{
		   		itemError = ServiceItem.verifyItem("PRD", item.trim());
		   	//  	System.out.println(inqSalesOrderError);
			}
		   	catch(Exception e)
			{
//		   	 Just Catch the error, do not need to do anything with it.
			}
		 }	
		if (!dateIssued.equals("0") &&
			(dateIssued.length() == 9 ||
			 dateIssued.length() == 10))
		{ // Setting it to a yyyyMMDD format
			try
			{
			   DateTime dtI = UtilityDateTime.getDateFromMMddyyyyWithSlash(dateIssued);
			   dateIssued = dtI.getDateFormatyyyyMMdd();
			}
			catch(Exception e)
			{}
		}
		if (!dateExpired.equals("0") &&
			(dateExpired.length() == 9 ||
			 dateExpired.length() == 10))
		{ // Setting it to a yyyyMMDD format
			try
			{
			   DateTime dtE = UtilityDateTime.getDateFromMMddyyyyWithSlash(dateExpired);
			   dateExpired = dtE.getDateFormatyyyyMMdd();
			}
			catch(Exception e)
			{}
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
	 * @return Returns the item.
	 */
	public String getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * @return Returns the itemError.
	 */
	public String getItemError() {
		return itemError;
	}
	/**
	 * @param itemError The itemError to set.
	 */
	public void setItemError(String itemError) {
		this.itemError = itemError;
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
	 * @return Returns the dateExpired.
	 */
	public String getDateExpired() {
		return dateExpired;
	}
	/**
	 * @param dateExpired The dateExpired to set.
	 */
	public void setDateExpired(String dateExpired) {
		this.dateExpired = dateExpired;
	}
	/**
	 * @return Returns the dateIssued.
	 */
	public String getDateIssued() {
		return dateIssued;
	}
	/**
	 * @param dateIssued The dateIssued to set.
	 */
	public void setDateIssued(String dateIssued) {
		this.dateIssued = dateIssued;
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
	 * @return Returns the recordStatus.
	 */
	public String getRecordStatus() {
		return recordStatus;
	}
	/**
	 * @param recordStatus The recordStatus to set.
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	/**
	 * @return Returns the varianceComment.
	 */
	public String getVarianceComment() {
		return varianceComment;
	}
	/**
	 * @param varianceComment The varianceComment to set.
	 */
	public void setVarianceComment(String varianceComment) {
		this.varianceComment = varianceComment;
	}
	/**
	 * @return Returns the varianceText.
	 */
	public String getVarianceText() {
		return varianceText;
	}
	/**
	 * @param varianceText The varianceText to set.
	 */
	public void setVarianceText(String varianceText) {
		this.varianceText = varianceText;
	}
	/**
	 * @return Returns the itemDescription.
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription The itemDescription to set.
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 *      Method created 9/16/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public static String buildDropDownStatus(String inStatus, String readOnly) {
		String dropDown = "";
		try
		{
			Vector buildList1 = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setDescription("Pending");
			dds.setValue("PENDING");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Active");
			dds.setValue("ACTIVE");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("In-Active");
			dds.setValue("INACTIVE");
			buildList1.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Delete");
			dds.setValue("DELETE");
			buildList1.addElement(dds);
			
			dropDown = DropDownSingle.buildDropDown(buildList1, "recordStatus", inStatus, "None", "N", readOnly);	
			
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 * @return Returns the updDate.
	 */
	public String getUpdDate() {
		return updDate;
	}
	/**
	 * @param updDate The updDate to set.
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	/**
	 * @return Returns the updTime.
	 */
	public String getUpdTime() {
		return updTime;
	}
	/**
	 * @param updTime The updTime to set.
	 */
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	/**
	 * @return Returns the updUser.
	 */
	public String getUpdUser() {
		return updUser;
	}
	/**
	 * @param updUser The updUser to set.
	 */
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update Creation date: (10/9/2008 TWalton)
	 */
	public void loadFromItemVariance(ItemVariance iv) {
		try {
			itemDescription = iv.getItemDescription();
			varianceText = iv.getVarianceText();
			dateIssued = iv.getDateIssued();
			originalDateIssued = iv.getDateIssued();
			dateExpired = iv.getDateExpired();
			originalDateExpired = iv.getDateExpired();
			varianceComment = iv.getVarianceComment();
			allowUpdate = iv.getAllowUpdate();
			recordStatus = iv.getRecordStatus();
			updDate = iv.getUpdDate();
			updTime = iv.getUpdTime();
			updUser = iv.getUpdUser();
			
		} catch (Exception e) {
	
		}
		return;
	}
	/**
	 * @return Returns the originalDateExpired.
	 */
	public String getOriginalDateExpired() {
		return originalDateExpired;
	}
	/**
	 * @param originalDateExpired The originalDateExpired to set.
	 */
	public void setOriginalDateExpired(String originalDateExpired) {
		this.originalDateExpired = originalDateExpired;
	}
	/**
	 * @return Returns the originalDateIssued.
	 */
	public String getOriginalDateIssued() {
		return originalDateIssued;
	}
	/**
	 * @param originalDateIssued The originalDateIssued to set.
	 */
	public void setOriginalDateIssued(String originalDateIssued) {
		this.originalDateIssued = originalDateIssued;
	}
}
