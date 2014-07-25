/*
 * Created on May 6, 2008
 * 
 */

package com.treetop.app.specification;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.html.*;
import com.treetop.utilities.*;
import com.treetop.businessobjects.*;

/**
 * @author twalto
 * 
 * 
 */
public class InqSpecification extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	public String inqItemNumber = "";
	public String inqItemDescription = "";
	public String inqItemType = "";
	public String inqProductSize = "";
	public String inqProductGroup = "";
	public String inqFormula = "";
	public String inqFormulaName = "";
	public String inqRecordStatus = "ACTIVE";
	
	// Are you personally allowed to UPDATE the Specifications?
	public String allowUpdate = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
		
	public Vector listReport = null;

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
	 * @return Returns the inqRecordStatus.
	 */
	public String getInqRecordStatus() {
		return inqRecordStatus;
	}
	/**
	 * @param inqRecordStatus The inqRecordStatus to set.
	 */
	public void setInqRecordStatus(String inqRecordStatus) {
		this.inqRecordStatus = inqRecordStatus;
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
	 * @return Returns the inqFormula.
	 */
	public String getInqFormula() {
		return inqFormula;
	}
	/**
	 * @param inqFormula The inqFormula to set.
	 */
	public void setInqFormula(String inqFormula) {
		this.inqFormula = inqFormula;
	}
	/**
	 * @return Returns the inqFormulaName.
	 */
	public String getInqFormulaName() {
		return inqFormulaName;
	}
	/**
	 * @param inqFormulaName The inqFormulaName to set.
	 */
	public void setInqFormulaName(String inqFormulaName) {
		this.inqFormulaName = inqFormulaName;
	}
	/**
	 * @return Returns the inqItemDescription.
	 */
	public String getInqItemDescription() {
		return inqItemDescription;
	}
	/**
	 * @param inqItemDescription The inqItemDescription to set.
	 */
	public void setInqItemDescription(String inqItemDescription) {
		this.inqItemDescription = inqItemDescription;
	}
	/**
	 * @return Returns the inqItemNumber.
	 */
	public String getInqItemNumber() {
		return inqItemNumber;
	}
	/**
	 * @param inqItemNumber The inqItemNumber to set.
	 */
	public void setInqItemNumber(String inqItemNumber) {
		this.inqItemNumber = inqItemNumber;
	}
	/**
	 * @return Returns the inqProductGroup.
	 */
	public String getInqProductGroup() {
		return inqProductGroup;
	}
	/**
	 * @param inqProductGroup The inqProductGroup to set.
	 */
	public void setInqProductGroup(String inqProductGroup) {
		this.inqProductGroup = inqProductGroup;
	}
	/**
	 * @return Returns the inqProductSize.
	 */
	public String getInqProductSize() {
		return inqProductSize;
	}
	/**
	 * @param inqProductSize The inqProductSize to set.
	 */
	public void setInqProductSize(String inqProductSize) {
		this.inqProductSize = inqProductSize;
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
	 *   Method created 10/21/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownItemType() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqCPGSpecs");
			Vector buildList = ServiceItem.dropDownItemType(sendParms);

			dropDown = DropDownSingle.buildDropDown(buildList, "inqItemType", inqItemType, "", "B", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *   Method created 12/22/08  THaile
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public static String buildDropDownItemInqIngSpec() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqINGSpecs");
			Vector buildList = ServiceItem.dropDownItemInqIngSpec(sendParms);

			dropDown = DropDownSingle.buildDropDown(buildList, "inqresource", "", "", "B", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *   Method created 12/23/08  THaile
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public static String buildDropDownCustomerInqIngSpec() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqINGSpecs");
			Vector buildList = ServiceCustomer.dropDownCustomerInqIngSpec(sendParms);

			dropDown = DropDownSingle.buildDropDown(buildList, "inqcustomer", "", "", "B", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}	
	/**
	 *   Method created 10/21/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownProductGroup() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqCPGSpecs");
			Vector buildList = ServiceItem.dropDownProductGroup(sendParms);
	
			dropDown = DropDownSingle.buildDropDown(buildList, "inqProductGroup", inqProductGroup, "", "B", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *   Method created 10/21/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownProductSize() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqCPGSpecs");
			Vector buildList = ServiceItem.dropDownUser1(sendParms);
	
			dropDown = DropDownSingle.buildDropDown(buildList, "inqProductSize", inqProductSize, "", "B", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *   Method created 10/21/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownFormula() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqCPGSpecs");
			
			Vector buildList = ServiceSpecificationFormula.dropDownFormula(sendParms);
	
			dropDown = DropDownSingle.buildDropDown(buildList, "inqFormula", inqFormula, "", "B", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
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
	* Determine Security for CPG Specifications
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       Set the information in allowUpdate Field 
	*
	* Creation date: (10/22/2008 -- TWalton)
	*/
	public void determineSecurity(HttpServletRequest request,
								  HttpServletResponse response) {
		try
		{
			 try
			 {
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("8"))
			          this.allowUpdate = "Y";
			    }
			 }
			 catch(Exception e)
			 {}
			 if (!this.allowUpdate.equals("Y"))
			 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				           if (groupsR[xr].equals("110"))
				              this.allowUpdate = "Y";
				     }
				   }
				   catch(Exception e)
				   {}
			 }  
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqSpecification.determineSecurity(request, response)" + e);
		}
	    return;
	}
	/**
	 *   Method created 10/22/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownStatus() {
		String dropDown = "";
		try
		{
			Vector ddList = new Vector();	
			DropDownSingle thisClass = new DropDownSingle();
			thisClass.setDescription("Active");
			thisClass.setValue("ACTIVE");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("InActive");
			thisClass.setValue("INACTIVE");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("Pending");
			thisClass.setValue("PENDING");
			ddList.add(thisClass);
	
			dropDown = DropDownSingle.buildDropDown(ddList, "inqRecordStatus", inqRecordStatus, "", "N", "N");	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!inqItemNumber.equals(""))
		{
			returnString.append("Item Number: ");
			returnString.append(inqItemNumber);			  
		}
		if (!inqItemDescription.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Item Description: ");
			returnString.append(inqItemDescription);
		}
		if (!inqItemType.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Item Type: ");
			returnString.append(inqItemType);
		}	
		if (!inqProductSize.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Product Size: ");
			returnString.append(inqProductSize);
		}								
		if (!inqProductGroup.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Product Group: ");
			returnString.append(inqProductGroup);
		}
		if (!inqFormula.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Formula: ");
			returnString.append(inqFormula);
		}						
		if (!inqFormulaName.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Formula Name: ");
			returnString.append(inqFormulaName);
		}						
		if (!inqRecordStatus.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Record Status: ");
			returnString.append(inqRecordStatus);
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
		if (!inqItemNumber.equals(""))
		{// Item Number
			returnString.append("&inqItemNumber=");
			returnString.append(inqItemNumber);
		}
		if (!inqItemDescription.equals(""))
		{
			returnString.append("&inqItemDescription=");
			returnString.append(inqItemDescription);
		}
		if (!inqItemType.equals(""))
		{
			returnString.append("&inqItemType=");
			returnString.append(inqItemType);
		}	
		if (!inqProductSize.equals(""))
		{
			returnString.append("&inqProductSize=");
			returnString.append(inqProductSize);
		}
		if (!inqProductGroup.equals(""))
		{
			returnString.append("&inqProductGroup=");
			returnString.append(inqProductGroup);
		}
		if (!inqFormula.equals(""))
		{
			returnString.append("&inqFormula=");
			returnString.append(inqFormula);
		}			
		if (!inqFormulaName.equals(""))
		{
			returnString.append("&inqFormulaName");
			returnString.append(inqFormulaName);
		}
		returnString.append("&inqRecordStatus=");
		returnString.append(inqRecordStatus);
		
		return returnString.toString();
	}
	/**
	*  This method should be in EVERY Inquiry View Bean
	*   Will create the vectors and generate the code for
	*    MORE Button.
	* @return
	*/
	public static String buildMoreButton(String requestType,
										 String item,
										 String revisionDate,
										 String status,
										 String allowUpdate,
										 String allparms) {
		
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[6];
		String[] urlNames = new String[6];
		String[] newPage = new String[6];
		for (int z = 0; z < 6; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		// need to include a dtlCPGSpec type of more button
		if (requestType.equals("listCPGSpec")) {
			urlLinks[0] = "/web/CtlSpecificationNEW?requestType=dtlCPGSpec&itemNumber="+ item.trim() + "&revisionDate=" + revisionDate;
				urlNames[0] = "See Detailed Spec";
				newPage[0] = "Y";
			}
		if (allowUpdate.trim().equals("Y"))
		{ // Only if you are allowed to Update will you see the Update Links	
		   if (requestType.equals("listCPGSpec")) {
	          urlLinks[1] = "/web/CtlSpecificationNEW?requestType=updCPGSpec&itemNumber="+ item.trim() + "&revisionDate=" + revisionDate;
			  urlNames[1] = "Update Detailed Spec";
			  newPage[1] = "Y";
			  urlLinks[2] = "/web/CtlSpecificationNEW?requestType=addCPGSpec&itemNumberCopy="+ item.trim() + "&revisionDateCopy=" + revisionDate;
			  urlNames[2] = "Copy This Spec";
			  newPage[2] = "Y";
			  if (status.equals("PENDING"))
			  {
				  urlLinks[3] = "/web/CtlSpecificationNEW?requestType=deleteCPGSpec&itemNumber="+ item.trim() + "&revisionDate=" + revisionDate + "&status=" + status + allparms;
				  urlNames[3] = "Delete This Spec";
				  newPage[3] = "Y";
			  }
			  urlLinks[4] = "/web/CtlSpecificationNEW?requestType=addCPGSpec";
			  urlNames[4] = "Add a New Spec";
			  newPage[4] = "Y";
			}
		}
	
			return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
		}
	/**
	*  This method will take in a date field,  return it in mm/dd/yyyy format
	* @return
	*/
	public static String formatDateFromyyyymmdd(String dateIn) {
		String returnValue = dateIn;
	    try
		{
	    	DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(dateIn);
	    	returnValue = dt.getDateFormatMMddyyyySlash();
		}
	    catch(Exception e)
		{}
		return returnValue;
	}
}
