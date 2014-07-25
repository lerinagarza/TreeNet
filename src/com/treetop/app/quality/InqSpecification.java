/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;

import java.util.Arrays;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceWarehouse;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.FindAndReplace;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;
import com.treetop.utilities.html.SelectionCriteria;
import com.treetop.viewbeans.BaseViewBeanR2;

/**
 * @author twalto
 */
public class InqSpecification extends BaseViewBeanR2 {
	
	// Standard Fields - to be in Every View Bean
//	public String requestType   	 = "";
//	public String displayMessage 	 = "";
//	public String environment 		 = "";
	public String inqTemplate		 = "QaSpecificationHeader";	
			// header based on what will be DISPLAYED on the list page
			//  if we want to display detail information on the list page, 
			//  we will change this to Detail
	
	public String inqSpecNumber      		= "";
	public String inqSpecNumberError 		= "";
	public String inqSpecDescription 		= "";
	public String inqSpecDescriptionError 	= "";
	public String inqSpecType		 		= "";
	public String inqItemNumber      		= "";
	public String inqItemNumberError 		= "";
	public String inqItemType        		= "";
	public String inqProductSize     		= "";
	public String inqProductGroup    		= "";
	public String inqFormula         		= "";
	public String inqFormulaError	 		= "";
	public String inqFormulaName     		= "";
	public String inqFormulaNameError		= "";
	public String inqStatus    		 		= "AC";
	public String inqGroup			 		= "";
	public String inqScope			 		= "";
	public String inqOrigination	 		= "";
	public String inqApprovedBy		 		= "";
	
	public String inqWarehouse		 		= "";
	
//	 Are you personally allowed to UPDATE the Formula?
	public String securityLevel         	= "0"; 
		// 0 - See Only Active
	    // 1 - See all Formula's
	    // 2 - Update Formula's
	
	// Standard Fields for Inq View Bean
	public String orderBy 					= "";
	public String orderStyle 				= "";
		
	public Vector listReport 				= null;
	public Vector dropDownItemType 			= null;
	public Vector dropDownProductGroup 		= null;
	public Vector dropDownUser1 			= null;
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		if (this.getEnvironment().trim().equals(""))
			this.setEnvironment("PRD");
		
		
		if (!this.getInqSpecDescription().equals(""))
		{
			this.setInqSpecDescription(FindAndReplace.remove(this.getInqSpecDescription(), "'"));
		}
		if (!this.getInqFormulaName().equals(""))
		{
			this.setInqFormulaName(FindAndReplace.remove(this.getInqFormulaName(), "'"));
		}
		
		
		return;
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
	 *   Method created 1/12/11  TWalton
	 * Will Return to the Screen, String which is the code for the drop down list
	 */
	public String buildDropDownItemType(String readOnly) {
		String dropDown = "";
		try
		{
			dropDown = DropDownSingle.buildDropDown(this.dropDownItemType, "inqItemType", this.inqItemType, "", "B", readOnly);	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *   Method created 1/12/11  TWalton
	 * Will Return to the Screen, String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownProductGroup(String readOnly) {
		String dropDown = "";
		try
		{
			dropDown = DropDownSingle.buildDropDown(this.dropDownProductGroup, "inqProductGroup", this.inqProductGroup, "", "B", readOnly);	
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
	public String buildDropDownProductSize(String readOnly) {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("inqSpecs");
			Vector buildList = ServiceItem.dropDownUser1(sendParms);
	
			dropDown = DropDownSingle.buildDropDown(buildList, "inqProductSize", this.inqProductSize, "", "B", readOnly);	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	
	public String buildDropDownSpecItemProductionWarehouse(String selectedValue) {
		String dropDown = "";
		try
		{
			Vector<HtmlOption> options = ServiceWarehouse.dropDownSpecItemProductionWarehouse("PRD");
			String name = "inqWarehouse";
			String id = "inqWarehouse";
			String defaultValue = "*all";
			boolean readOnly = false;
			String cssClass = "";
			dropDown = DropDownSingle.buildDropDown(
					options, name, id, selectedValue, defaultValue, 
					readOnly, cssClass, DescriptionType.VALUE_DESCRIPTION);
			
			
		} catch (Exception e) {
			
		}
		return dropDown;
	}
	
	/**
	* Determine Security for CPG Specifications
	*    Send in:
	* 		request
	* 		response
	*       Update's the Class to reflect the change in field - Security Level
	*            0 - See Only Active
	*            1 - See all Method's
	*            2 - Update Method's  
	*            
	*              // Currently using on 0 and 2
	*
	* Creation date: (5/25/2010 -- TWalton)
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
			          this.setSecurityLevel("2");
			    }
			 }
			 catch(Exception e)
			 {}
			 if (this.getSecurityLevel().equals("0"))
			 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				           if (groupsR[xr].equals("125"))
				              this.setSecurityLevel("2");
				     }
				   }
				   catch(Exception e)
				   {}
			 }  
			 // for testing purposes... do not have 2 be the security level
			// this.setSecurityLevel("0");
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqSpecification.determineSecurity(request, response)" + e);
		}
	    return;
	}
	
	public String buildSelectionCriteria() {
		SelectionCriteria sc = new SelectionCriteria();
		sc.addValue("Specification Number", this.getInqSpecNumber());
		sc.addValue("Specification Description", this.getInqSpecDescription());
		sc.addValue("Formula Number", this.getInqFormula());
		sc.addValue("Formula Description (Name)", this.getInqFormulaName());
		sc.addValue("Warehouse", this.getInqWarehouse());
		sc.addValue("Item Number", this.getInqItemNumber());
		sc.addValue("Item Type", this.getInqItemType());
		sc.addValue("Product Size", this.getInqProductSize());
		sc.addValue("Product Group", this.getInqProductGroup());
		sc.addValue("Type", this.getInqSpecType());
		sc.addValue("Status", this.getInqStatus());
		sc.addValue("Scope", this.getInqScope());
		sc.addValue("Origination", this.getInqOrigination());
		sc.addValue("Approved By", this.getInqApprovedBy());
		
		return sc.toString();
	}
	
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		// Header Data
		if (!this.inqSpecNumber.equals(""))
		{
			returnString.append("Specification Number: ");
			returnString.append(inqSpecNumber);			  
		}
		if (!this.inqSpecDescription.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Specification Description: ");
			returnString.append(inqSpecDescription);
		}
		if (!this.inqFormula.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Formula: ");
			returnString.append(this.inqFormula);
		}						
		if (!this.inqFormulaName.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Formula Name: ");
			returnString.append(this.inqFormulaName);
		}			
		if (!this.inqStatus.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Record Status: ");
			returnString.append(this.inqStatus);
		}				
		
		if (!this.inqWarehouse.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Warehouse: ");
			returnString.append(this.inqWarehouse);			  
		}
		
		// Detail Data
		if (!this.inqItemNumber.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Item Number: ");
			returnString.append(this.inqItemNumber);			  
		}
//		if (!inqItemDescription.equals(""))
	//	{
		//	if (!returnString.toString().equals(""))
			//  returnString.append("<br>");
//			returnString.append("Item Description: ");
	//		returnString.append(inqItemDescription);
		//}
		if (!this.inqItemType.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Item Type: ");
			returnString.append(this.inqItemType);
		}	
		if (!this.inqProductSize.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Product Size: ");
			returnString.append(this.inqProductSize);
		}								
		if (!this.inqProductGroup.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Product Group: ");
			returnString.append(this.inqProductGroup);
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
		
		// Header Information
		if (!this.inqSpecNumber.equals(""))
		{// Specification Number
			returnString.append("&inqSpecNumber=");
			returnString.append(this.inqSpecNumber);
		}
		if (!this.inqSpecDescription.equals(""))
		{
			returnString.append("&inqSpecDescription=");
			returnString.append(this.inqSpecDescription);
		}
		if (!this.inqFormula.equals(""))
		{
			returnString.append("&inqFormula=");
			returnString.append(this.inqFormula);
		}			
		if (!this.inqFormulaName.equals(""))
		{
			returnString.append("&inqFormulaName");
			returnString.append(this.inqFormulaName);
		}
		if (!this.inqStatus.equals(""))
		{
		   returnString.append("&inqStatus=");
		   returnString.append(this.inqStatus);
		}
		
		// Detail Information
		if (!this.inqItemNumber.equals(""))
		{// Item Number
			returnString.append("&inqItemNumber=");
			returnString.append(this.inqItemNumber);
		}
//		if (!inqItemDescription.equals(""))
	//	{
		//	returnString.append("&inqItemDescription=");
			//returnString.append(inqItemDescription);
		//}
		if (!this.inqItemType.equals(""))
		{
			returnString.append("&inqItemType=");
			returnString.append(this.inqItemType);
		}	
		if (!this.inqProductSize.equals(""))
		{
			returnString.append("&inqProductSize=");
			returnString.append(this.inqProductSize);
		}
		if (!this.inqProductGroup.equals(""))
		{
			returnString.append("&inqProductGroup=");
			returnString.append(this.inqProductGroup);
		}
		if (!this.inqSpecType.equals(""))
		{
			returnString.append("&inqSpecType=");
			returnString.append(this.inqSpecType);
		}
		if (!this.inqScope.equals(""))
		{
			returnString.append("&inqScope=");
			returnString.append(this.inqScope);
		}
		if (!this.inqOrigination.equals(""))
		{
			returnString.append("&inqOrigination=");
			returnString.append(this.inqOrigination);
		}
		if (!this.inqApprovedBy.equals(""))
		{
			returnString.append("&inqApprovedBy=");
			returnString.append(this.inqApprovedBy);
		}
		if (!this.inqWarehouse.equals(""))
		{
			returnString.append("&inqWarehouse=");
			returnString.append(this.inqWarehouse);
		}
		
		return returnString.toString();
	}
	/**
	*  This method should be in EVERY Inquiry View Bean
	*   Will create the vectors and generate the code for
	*    MORE Button.
	*     Send in A vector of all the fields needed:
	*     [0] - requestType
	*     [1] - securityLevel
	*     [2] - environment
	*     [3] - SpecificationNumber
	*     [4] - revisionDate
	*     [5] - revisionTime
	* @return
	*/
	public static String buildMoreButton(Vector receivedParms, HttpServletRequest request, HttpServletResponse response) {
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[7];
		String[] urlNames = new String[7];
		String[] newPage = new String[7];
		for (int z = 0; z < 7; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		String requestType = (String) receivedParms.elementAt(0);
		String securityLevel = (String) receivedParms.elementAt(1);
		if (!requestType.trim().equals("dtlSpec"))
        { // Do not need if this is the Detail Page Already
        	urlLinks[0] = "/web/CtlQuality?requestType=dtlSpec"
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&specNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
		   urlNames[0] = "View Details of Specification " + receivedParms.elementAt(3);
		   newPage[0] = "Y";	
		   
		   
		   /**
		    * Build Product Data Sheets only available for Jodee or IS
		    */
		   //TODO adjust security around building product data sheets
		   String profile = SessionVariables.getSessionttiProfile(request, response);
		   String[] roles = SessionVariables.getSessionttiUserRoles(request, response);
		   if (profile.equals("JHURST") || Arrays.asList(roles).contains("8")) {

		   urlLinks[6] = "/web/CtlQuality?requestType=buildProductDataSheet"
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&specNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
		   urlNames[6] = "Build Product Data Sheet " + receivedParms.elementAt(3);
		   newPage[6] = "Y";
		   }
		   
        }
		if (securityLevel.trim().equals("2"))
		{
			urlLinks[1] = "/web/CtlQuality?requestType=updSpec"
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&specNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[1] = "Update This Specification " + receivedParms.elementAt(3);
			newPage[1] = "Y";
        //	ADD a new Specification
			urlLinks[2] = "/web/CtlQuality?requestType=addSpec"
				+ "&environment=" + receivedParms.elementAt(2);
			urlNames[2] = "Add a New Specification - Start Blank";
			newPage[2] = "Y";
			urlLinks[3] = "/web/CtlQuality?requestType=copyNewSpec"
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&specNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[3] = "Copy " + receivedParms.elementAt(3) + " Create NEW Specification";
			newPage[3] = "Y";
			urlLinks[4] = "/web/CtlQuality?requestType=reviseSpec"
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&specNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[4] = "Create Revision of " + receivedParms.elementAt(3);
			newPage[4] = "Y";	
//			urlLinks[5] = "/web/CtlQuality?requestType=deleteMethod"
//				+ "&environment=" + receivedParms.elementAt(2)
//				+ "&methodNumber=" + receivedParms.elementAt(3)
//				+ "&revisionDate=" + receivedParms.elementAt(4)
//				+ "&revisionTime=" + receivedParms.elementAt(5);
//			urlNames[5] = "Delete this Revision " + receivedParms.elementAt(3);
//			newPage[5] = "Y";	
			
		}
			
		
		return HTMLHelpers.buttonMoreV3(urlLinks, urlNames, newPage);
		
		
		
	}
	public String getInqItemNumberError() {
		return inqItemNumberError;
	}
	public void setInqItemNumberError(String inqItemNumberError) {
		this.inqItemNumberError = inqItemNumberError;
	}
	public String getInqSpecDescription() {
		return inqSpecDescription;
	}
	public void setInqSpecDescription(String inqSpecDescription) {
		this.inqSpecDescription = inqSpecDescription;
	}
	public String getInqSpecNumber() {
		return inqSpecNumber;
	}
	public void setInqSpecNumber(String inqSpecNumber) {
		this.inqSpecNumber = inqSpecNumber;
	}
	public String getInqSpecNumberError() {
		return inqSpecNumberError;
	}
	public void setInqSpecNumberError(String inqSpecNumberError) {
		this.inqSpecNumberError = inqSpecNumberError;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getInqTemplate() {
		return inqTemplate;
	}
	public void setInqTemplate(String inqTemplate) {
		this.inqTemplate = inqTemplate;
	}
	public String getInqFormulaError() {
		return inqFormulaError;
	}
	public void setInqFormulaError(String inqFormulaError) {
		this.inqFormulaError = inqFormulaError;
	}
	public Vector getDropDownItemType() {
		return dropDownItemType;
	}
	public void setDropDownItemType(Vector dropDownItemType) {
		this.dropDownItemType = dropDownItemType;
	}
	public Vector getDropDownProductGroup() {
		return dropDownProductGroup;
	}
	public void setDropDownProductGroup(Vector dropDownProductGroup) {
		this.dropDownProductGroup = dropDownProductGroup;
	}
	public Vector getDropDownUser1() {
		return dropDownUser1;
	}
	public void setDropDownUser1(Vector dropDownUser1) {
		this.dropDownUser1 = dropDownUser1;
	}
	public String getInqSpecType() {
		return inqSpecType;
	}
	public void setInqSpecType(String inqSpecType) {
		this.inqSpecType = inqSpecType;
	}
	public String getInqGroup() {
		return inqGroup;
	}
	public void setInqGroup(String inqGroup) {
		this.inqGroup = inqGroup;
	}
	public String getInqScope() {
		return inqScope;
	}
	public void setInqScope(String inqScope) {
		this.inqScope = inqScope;
	}
	public String getInqOrigination() {
		return inqOrigination;
	}
	public void setInqOrigination(String inqOrigination) {
		this.inqOrigination = inqOrigination;
	}
	public String getInqApprovedBy() {
		return inqApprovedBy;
	}
	public void setInqApprovedBy(String inqApprovedBy) {
		this.inqApprovedBy = inqApprovedBy;
	}

	public String getInqWarehouse() {
		return inqWarehouse;
	}

	public void setInqWarehouse(String inqWarehouse) {
		this.inqWarehouse = inqWarehouse;
	}

	public String getInqSpecDescriptionError() {
		return inqSpecDescriptionError;
	}

	public void setInqSpecDescriptionError(String inqSpecDescriptionError) {
		this.inqSpecDescriptionError = inqSpecDescriptionError;
	}

	public String getInqFormulaNameError() {
		return inqFormulaNameError;
	}

	public void setInqFormulaNameError(String inqFormulaNameError) {
		this.inqFormulaNameError = inqFormulaNameError;
	}

}
