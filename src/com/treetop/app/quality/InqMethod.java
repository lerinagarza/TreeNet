/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.utilities.html.SelectionCriteria;
import com.treetop.viewbeans.BaseViewBeanR2;

/**
 * @author twalto
 * 
 * Inquire on a Method, search for Methods
 */
public class InqMethod extends BaseViewBeanR2 {
	// Standard Fields - to be in Every View Bean
	public String inqTemplate			 = "QaMethodHeader";
	
	public String inqMethodNumber       = "";
	public String inqMethodNumberError  = "";
	public String inqMethodName			= "";
	public String inqMethodDescription  = "";
	public String inqStatus	 			= "AC";
	public String inqGroup				= "";
	public String inqScope				= "";
	public String inqOrigination		= "";
	public String inqApprovedBy			= "";
	public String inqBodyTextLine       = "";
	
//	 Are you personally allowed to UPDATE the Method?
	public String securityLevel         = "0"; 
		// 0 - See Only Active
	    // 1 - See all Method's
	    // 2 - Update Method's
		
	// Standard Fields for Inq View Bean
	public String orderBy 				= "";
	public String orderStyle 			= "";	
	
	//Button Values
	public String saveButton            = "";
	
//	public BeanRawFruit updBean = new BeanRawFruit();
		
	public Vector listReport            = null;

	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		   if (this.getEnvironment().trim().equals(""))
			   this.setEnvironment("PRD");
		}
		catch(Exception e)
		{
			
		}
		return;
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
	 * @return Returns the saveButton.
	 */
	public String getSaveButton() {
		return saveButton;
	}
	/**
	 * @param saveButton The saveButton to set.
	 */
	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
	}
	public String getInqMethodDescription() {
		return inqMethodDescription;
	}
	public void setInqMethodDescription(String inqMethodDescription) {
		this.inqMethodDescription = inqMethodDescription;
	}
	public String getInqMethodNumber() {
		return inqMethodNumber;
	}
	public void setInqMethodNumber(String inqMethodNumber) {
		this.inqMethodNumber = inqMethodNumber;
	}
	public String getInqMethodNumberError() {
		return inqMethodNumberError;
	}
	public void setInqMethodNumberError(String inqMethodNumberError) {
		this.inqMethodNumberError = inqMethodNumberError;
	}
	public String getInqBodyTextLine() {
		return inqBodyTextLine;
	}
	public void setInqBodyTextLine(String inqBodyTextLine) {
		this.inqBodyTextLine = inqBodyTextLine;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	/**
	 *  Use this method to Display -- however appropriate for
	 *     the JSP the Parameters Chosen
	 *   
	 *    Creation date: (10/25/2010 -- TWalton)
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		String valueDisplay = "Method";
		if (this.getRequestType().trim().equals("listProcedure"))
			valueDisplay = "Procedure";
		if (this.getRequestType().trim().equals("listPolicy"))
			valueDisplay = "Policy";
		if (!this.getInqStatus().trim().equals(""))
		{
			returnString.append("Status: ");
			returnString.append(this.getInqStatus().trim());			  
		}
		if (!this.getInqMethodNumber().trim().equals(""))
		{
			if (!returnString.toString().equals(""))
			   returnString.append("<br>");
			returnString.append(valueDisplay + " Number: ");
			returnString.append(this.getInqMethodNumber().trim());			  
		}
		if (!this.getInqMethodDescription().trim().equals(""))
		{
			if (!returnString.toString().equals(""))
			   returnString.append("<br>");
			returnString.append(valueDisplay + "Description: ");
			returnString.append(this.getInqMethodDescription().trim());
		}
		if (!this.getInqMethodName().trim().equals(""))
		{
			if (!returnString.toString().equals(""))
			   returnString.append("<br>");
			returnString.append(valueDisplay + "Name: ");
			returnString.append(this.getInqMethodName().trim());
		}
		if (!this.getInqScope().trim().equals(""))
		{
			if (!returnString.toString().equals(""))
			   returnString.append("<br>");
			returnString.append("Scope: ");
			returnString.append(this.getInqScope().trim());
		}
		if (!this.getInqOrigination().trim().equals(""))
		{
			if (!returnString.toString().equals(""))
			   returnString.append("<br>");
			returnString.append("Origination: ");
			returnString.append(this.getInqOrigination().trim());
		}
		if (!this.getInqApprovedBy().trim().equals(""))
		{
			if (!returnString.toString().equals(""))
			   returnString.append("<br>");
			returnString.append("Approved By: ");
			returnString.append(this.getInqApprovedBy().trim());
		}
		return returnString.toString();
	}
	
	public String buildSelectionCriteria() {
		
		SelectionCriteria sc = new SelectionCriteria();
		
		sc.addValue("Method Number", this.getInqMethodNumber());
		sc.addValue("Method Description", this.getInqMethodDescription());
		sc.addValue("Status", this.getInqStatus());
		sc.addValue("Scope", this.getInqScope());
		sc.addValue("Origination", this.getInqOrigination());
		sc.addValue("Approved By", this.getInqApprovedBy());
	
		return sc.toString();
	}
	
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *    Will return a String to Resend the parameters
	 *    Mainly from the link on the heading Sort, 
	 *   OR if there is another list view.
	 *   
	 * @return - String
	 * 
	 * Creation date: (10/25/2010 -- TWalton)
	 */
	public String buildParameterResend() {
			
		StringBuffer returnString = new StringBuffer();
		if (!this.getInqStatus().trim().equals(""))
		{
			returnString.append("&inqStatus=");
			returnString.append(this.getInqStatus().trim());
		}
		if (!this.getInqMethodNumber().trim().equals(""))
		{
			returnString.append("&inqMethodNumber=");
			returnString.append(this.getInqMethodNumber().trim());
		}
		if (!this.getInqMethodDescription().trim().equals(""))
		{
			returnString.append("&inqMethodDescription=");
			returnString.append(this.getInqMethodDescription().trim());
		}
		if (!this.getInqMethodName().trim().equals(""))
		{
			returnString.append("&inqMethodName=");
			returnString.append(this.getInqMethodName().trim());
		}
		if (!this.getInqScope().trim().equals(""))
		{
			returnString.append("&inqScope=");
			returnString.append(this.getInqScope().trim());
		}
		if (!this.getInqOrigination().trim().equals(""))
		{
			returnString.append("&inqOrigination=");
			returnString.append(this.getInqOrigination().trim());
		}
		if (!this.getInqApprovedBy().trim().equals(""))
		{
			returnString.append("&inqApprovedBy=");
			returnString.append(this.getInqApprovedBy().trim());
		}
		return returnString.toString();
	}
	/**
	* Determine Security for Method's
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       Update's the Class to reflect the change in field - Security Level
	*            0 - See Only Active
	*            1 - See all Method's
	*            2 - Update Method's    
	*            
	*            // Currently using on 0 and 2
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
			 //this.setSecurityLevel("0");
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqSpecification.determineSecurity(request, response)" + e);
		}
	    return;
	}
	public String getInqTemplate() {
		return inqTemplate;
	}
	public void setInqTemplate(String inqTemplate) {
		this.inqTemplate = inqTemplate;
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 *    Send in A vector of all the fields needed:
	 *     [0] - requestType
	 *     [1] - securityLevel
	 *     [2] - environment
	 *     [3] - MethodNumber
	 *     [4] - revisionDate
	 *     [5] - revisionTime
	 * @return
	 */
	public static String buildMoreButton(Vector receivedParms) {
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[6];
		String[] urlNames = new String[6];
		String[] newPage = new String[6];
		for (int z = 0; z < 6; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		String nameDetail = "Method";
		String requestType = (String) receivedParms.elementAt(0);
		String securityLevel = (String) receivedParms.elementAt(1);
		if (requestType.trim().equals("dtlProcedure") ||
			requestType.trim().equals("listProcedure"))
		   nameDetail = "Procedure";
		if (requestType.trim().equals("dtlPolicy") ||
			requestType.trim().equals("listPolicy"))
		   nameDetail = "Policy";
			
        if (!requestType.trim().equals("dtlMethod"))
        { // Do not need if this is the Detail Page Already
        	urlLinks[0] = "/web/CtlQuality?requestType=dtl" + nameDetail
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&methodNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
		   urlNames[0] = "View Details of " + nameDetail + " " + receivedParms.elementAt(3);
		   newPage[0] = "Y";	
        }
        if (securityLevel.trim().equals("2"))
        {
        	urlLinks[1] = "/web/CtlQuality?requestType=upd" + nameDetail
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&methodNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
        	urlNames[1] = "Update This " + nameDetail + " " + receivedParms.elementAt(3);
        	newPage[1] = "Y";
        //	ADD a new Method
        	urlLinks[2] = "/web/CtlQuality?requestType=add" + nameDetail
        		+ "&environment=" + receivedParms.elementAt(2);
        	urlNames[2] = "Add a New " + nameDetail + " - Start Blank";
        	newPage[2] = "Y";
        	urlLinks[3] = "/web/CtlQuality?requestType=copyNew" + nameDetail
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&methodNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
        	urlNames[3] = "Copy " + receivedParms.elementAt(3) + " Create NEW " + nameDetail;
        	newPage[3] = "Y";
        	urlLinks[4] = "/web/CtlQuality?requestType=revise" + nameDetail
				+ "&environment=" + receivedParms.elementAt(2)
				+ "&methodNumber=" + receivedParms.elementAt(3)
				+ "&revisionDate=" + receivedParms.elementAt(4)
				+ "&revisionTime=" + receivedParms.elementAt(5);
        	urlNames[4] = "Create Revision of " + receivedParms.elementAt(3);
        	newPage[4] = "Y";	
		// when this is ready will have to add in the Procedures as well
//		urlLinks[5] = "/web/CtlQuality?requestType=deleteMethod"
//			+ "&environment=" + receivedParms.elementAt(2)
//			+ "&methodNumber=" + receivedParms.elementAt(3)
//			+ "&revisionDate=" + receivedParms.elementAt(4)
//			+ "&revisionTime=" + receivedParms.elementAt(5);
//		urlNames[5] = "Delete this Revision " + receivedParms.elementAt(3);
//		newPage[5] = "Y";
        }
		
		return HTMLHelpers.buttonMoreV3(urlLinks, urlNames, newPage);
	}
	public String getInqMethodName() {
		return inqMethodName;
	}
	public void setInqMethodName(String inqMethodName) {
		this.inqMethodName = inqMethodName;
	}
	public String getInqApprovedBy() {
		return inqApprovedBy;
	}
	public void setInqApprovedBy(String inqApprovedBy) {
		this.inqApprovedBy = inqApprovedBy;
	}
	public String getInqScope() {
		return inqScope;
	}
	public void setInqScope(String inqScope) {
		this.inqScope = inqScope;
	}
	public String getInqStatus() {
		return inqStatus;
	}
	public void setInqStatus(String inqStatus) {
		this.inqStatus = inqStatus;
	}
	public String getInqOrigination() {
		return inqOrigination;
	}
	public void setInqOrigination(String inqOrigination) {
		this.inqOrigination = inqOrigination;
	}
	public String getInqGroup() {
		return inqGroup;
	}
	public void setInqGroup(String inqGroup) {
		this.inqGroup = inqGroup;
	}
}
