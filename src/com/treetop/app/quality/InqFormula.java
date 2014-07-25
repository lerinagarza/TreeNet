/*
 * Created on May 12, 2010
 */

package com.treetop.app.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.DateTime;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.services.ServiceUser;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
import com.treetop.data.UserFile;

/**
 * @author twalto
 * 
 *         Inquire on a Formula, find a formula
 */
public class InqFormula extends BaseViewBeanR2 {
	// Standard Fields - to be in Every View Bean
	public String inqTemplate = "QaFormulaHeader";

	public String inqFormulaNumber = "";
	public String inqFormulaNumberError = "";
	public String inqFormulaName = "";
	public String inqFormulaDescription = "";
	public String inqFormulaType = "";
	public String inqStatus = "AC";
	public String inqGroup = "";
	public String inqScope = "";
	public String inqOrigination = "";
	public String inqApprovedBy = "";

	public String inqItemNumber = "";
	public String inqItemNumberError = "";
	public String inqItemDescription = "";
	public String inqCustomerNumber = "";
	public String inqCustomerNumberError = "";
	public String inqCustomerName = "";
	public String inqSupplierNumber = "";
	public String inqSupplierNumberError = "";
	public String inqSupplierName = "";
	public String inqProductionStatus = "";

	// Are you personally allowed to UPDATE the Formula?
	public String securityLevel = "0";
	// 0 - See Only Active
	// 1 - See all Formula's
	// 2 - Update Formula's

	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
	public int startRecord = 0;
	public int subfileSize = 25;

	// Button Values
	public String saveButton = "";

	// public BeanQuality updBean = new BeanQuality();
	public Vector listReport = null;

	/*
	 * Test and Validate fields, after loading them. Set Errors into the Error
	 * Fields of this View Bean IF there are any.
	 */
	public void validate() {
		try {
			if (this.getEnvironment().trim().equals(""))
				this.setEnvironment("PRD");
		} catch (Exception e) {

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
	 * @param listReport
	 *            The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}

	/**
	 * This method should be in EVERY Inquiry View Bean Will create the vectors
	 * and generate the code for MORE Button. Send in A vector of all the fields
	 * needed: [0] - requestType [1] - securityLevel [2] - environment [3] -
	 * formulaNumber [4] - revisionDate [5] - revisionTime
	 * 
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
		String requestType = (String) receivedParms.elementAt(0);
		String securityLevel = (String) receivedParms.elementAt(1);
		if (!requestType.trim().equals("dtlFormula")) { // Do not need if this
														// is the Detail Page
														// Already
			urlLinks[0] = "/web/CtlQuality?requestType=dtlFormula" + "&environment=" + receivedParms.elementAt(2)
					+ "&formulaNumber=" + receivedParms.elementAt(3) + "&revisionDate=" + receivedParms.elementAt(4)
					+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[0] = "View Details of Formula " + receivedParms.elementAt(3);
			newPage[0] = "Y";
		}
		if (securityLevel.trim().equals("2")) {
			urlLinks[1] = "/web/CtlQuality?requestType=updFormula" + "&environment=" + receivedParms.elementAt(2)
					+ "&formulaNumber=" + receivedParms.elementAt(3) + "&revisionDate=" + receivedParms.elementAt(4)
					+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[1] = "Update This Formula " + receivedParms.elementAt(3);
			newPage[1] = "Y";
			// ADD a new Formula
			urlLinks[2] = "/web/CtlQuality?requestType=addFormula" + "&environment=" + receivedParms.elementAt(2);
			urlNames[2] = "Add a New Formula - Start Blank";
			newPage[2] = "Y";
			urlLinks[3] = "/web/CtlQuality?requestType=copyNewFormula" + "&environment=" + receivedParms.elementAt(2)
					+ "&formulaNumber=" + receivedParms.elementAt(3) + "&revisionDate=" + receivedParms.elementAt(4)
					+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[3] = "Copy " + receivedParms.elementAt(3) + " Create NEW Formula";
			newPage[3] = "Y";
			urlLinks[4] = "/web/CtlQuality?requestType=reviseFormula" + "&environment=" + receivedParms.elementAt(2)
					+ "&formulaNumber=" + receivedParms.elementAt(3) + "&revisionDate=" + receivedParms.elementAt(4)
					+ "&revisionTime=" + receivedParms.elementAt(5);
			urlNames[4] = "Create Revision of " + receivedParms.elementAt(3);
			newPage[4] = "Y";
			// urlLinks[5] = "/web/CtlQuality?requestType=deleteFormula"
			// + "&environment=" + receivedParms.elementAt(2)
			// + "&formulaNumber=" + receivedParms.elementAt(3)
			// + "&revisionDate=" + receivedParms.elementAt(4)
			// + "&revisionTime=" + receivedParms.elementAt(5);
			// urlNames[5] = "Delete this Revision " +
			// receivedParms.elementAt(3);
			// newPage[5] = "Y";
		}

		return HTMLHelpers.buttonMoreV3(urlLinks, urlNames, newPage);
	}

	/**
	 * @return Returns the saveButton.
	 */
	public String getSaveButton() {
		return saveButton;
	}

	/**
	 * @param saveButton
	 *            The saveButton to set.
	 */
	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
	}

	public String getInqFormulaDescription() {
		return inqFormulaDescription;
	}

	public void setInqFormulaDescription(String inqFormulaDescription) {
		this.inqFormulaDescription = inqFormulaDescription;
	}

	public String getInqFormulaNumber() {
		return inqFormulaNumber;
	}

	public void setInqFormulaNumber(String inqFormulaNumber) {
		this.inqFormulaNumber = inqFormulaNumber;
	}

	public String getInqFormulaNumberError() {
		return inqFormulaNumberError;
	}

	public void setInqFormulaNumberError(String inqFormulaNumberError) {
		this.inqFormulaNumberError = inqFormulaNumberError;
	}

	public String getInqItemNumber() {
		return inqItemNumber;
	}

	public void setInqItemNumber(String inqItemNumber) {
		this.inqItemNumber = inqItemNumber;
	}

	public String getInqItemDescription() {
		return inqItemDescription;
	}

	public void setInqItemDescription(String inqItemDescription) {
		this.inqItemDescription = inqItemDescription;
	}

	public String getInqSupplierNumber() {
		return inqSupplierNumber;
	}

	public void setInqSupplierNumber(String inqSupplierNumber) {
		this.inqSupplierNumber = inqSupplierNumber;
	}

	public String getInqSupplierName() {
		return inqSupplierName;
	}

	public void setInqSupplierName(String inqSupplierName) {
		this.inqSupplierName = inqSupplierName;
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

	
	public String buildSelectionCriteria() {
		SelectionCriteria sc = new SelectionCriteria();
		
		sc.addValue("Formula Number", this.getInqFormulaName());
		sc.addValue("Formula Description", this.getInqFormulaDescription());
		sc.addValue("Item Number", this.getInqItemNumber());
		sc.addValue("Item Description", this.getInqItemDescription());
		sc.addValue("Customer Number", this.getInqCustomerNumber());
		sc.addValue("Customer Name", this.getInqCustomerName());
		sc.addValue("Supplier Number", this.getInqSupplierNumber());
		sc.addValue("Supplier Name", this.getInqSupplierName());
		sc.addValue("Type", this.getInqFormulaType());
		sc.addValue("Status", this.getInqStatus());
		sc.addValue("Scope", this.getInqScope());
		sc.addValue("Origination", this.getInqOrigination());
		sc.addValue("Approved By", this.getInqApprovedBy());
				
		return sc.toString();
	}
	
	/*
	 * Use this method to Display -- however appropriate for the JSP the
	 * Parameters Chosen
	 * 
	 * Creation date: (5/25/2010 -- TWalton)
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!this.getInqStatus().equals("")) {
			returnString.append("Status: ");
			returnString.append(this.getInqStatus());
		}
		if (!this.getInqFormulaNumber().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Formula Number: ");
			returnString.append(this.getInqFormulaNumber());
		}
		if (!this.getInqFormulaName().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Formula Name: ");
			returnString.append(this.getInqFormulaName());
		}
		if (!this.getInqFormulaDescription().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Formula Description: ");
			returnString.append(this.getInqFormulaDescription());
		}
		if (!this.getInqScope().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Scope: ");
			returnString.append(this.getInqScope());
		}
		if (!this.getInqOrigination().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Origination: ");
			returnString.append(this.getInqOrigination());
		}
		if (!this.getInqApprovedBy().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Approved By: ");
			returnString.append(this.getInqApprovedBy());
		}
		if (!this.getInqItemNumber().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Item Number: ");
			returnString.append(this.getInqItemNumber());
		}
		if (!this.getInqItemDescription().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Item Description: ");
			returnString.append(this.getInqItemDescription());
		}
		if (!this.getInqCustomerNumber().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Customer Number: ");
			returnString.append(this.getInqCustomerNumber());
		}
		if (!this.getInqCustomerName().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Customer Name: ");
			returnString.append(this.getInqCustomerName());
		}
		if (!this.getInqSupplierNumber().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Supplier Number: ");
			returnString.append(this.getInqSupplierNumber());
		}
		if (!this.getInqSupplierName().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Supplier Name: ");
			returnString.append(this.getInqSupplierName());
		}
		if (!this.getInqProductionStatus().equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Production Status: ");
			returnString.append(this.getInqProductionStatus());
		}
		return returnString.toString();
	}

	/**
	 * This method should be in EVERY Inquiry View Bean Will return a String to
	 * Resend the parameters Mainly from the link on the heading Sort, OR if
	 * there is another list view.
	 * 
	 * @return - String
	 * 
	 *         Creation date: (5/25/2010 -- TWalton)
	 */
	public String buildParameterResend() {
		StringBuffer returnString = new StringBuffer();
		
		returnString.append("&environment=" + this.getEnvironment());
		
		//Formula
		if (!this.getInqFormulaNumber().equals("")) {
			returnString.append("&inqFormulaNumber=");
			returnString.append(this.getInqFormulaNumber());
		}
		if (!this.getInqFormulaName().equals("")) {
			returnString.append("&inqFormulaName=");
			returnString.append(this.getInqFormulaName());
		}
		if (!this.getInqFormulaDescription().equals("")) {
			returnString.append("&inqFormulaDescription=");
			returnString.append(this.getInqFormulaDescription());
		}
		
		//Item
		if (!this.getInqItemNumber().equals("")) {
			returnString.append("&inqItemNumber=");
			returnString.append(this.getInqItemNumber());
		}
		if (!this.getInqItemDescription().equals("")) {
			returnString.append("&inqItemDescription=");
			returnString.append(this.getInqItemDescription());
		}
		
		//Customer
		if (!this.getInqCustomerNumber().equals("")) {
			returnString.append("&inqCustomerNumber=");
			returnString.append(this.getInqCustomerNumber());
		}
		if (!this.getInqCustomerName().equals("")) {
			returnString.append("&inqCustomerName=");
			returnString.append(this.getInqCustomerName());
		}
		
		//Supplier
		if (!this.getInqSupplierNumber().equals("")) {
			returnString.append("&inqSupplierNumber=");
			returnString.append(this.getInqSupplierNumber());
		}
		if (!this.getInqSupplierName().equals("")) {
			returnString.append("&inqSupplierName=");
			returnString.append(this.getInqSupplierName());
		}
		
		//Formula Type
		if (!this.getInqFormulaType().equals("")) {
			returnString.append("&inqFormulaType=");
			returnString.append(this.getInqFormulaType());
		}
		
		//Status
		if (!this.getInqStatus().equals("")) {
			returnString.append("&inqStatus=");
			returnString.append(this.getInqStatus());
		}
		
		//Scope
		if (!this.getInqScope().equals("")) {
			returnString.append("&inqScope=");
			returnString.append(this.getInqScope());
		}
		
		//Origination
		if (!this.getInqOrigination().equals("")) {
			returnString.append("&inqOrigination=");
			returnString.append(this.getInqOrigination());
		}
		
		//Approved By
		if (!this.getInqApprovedBy().equals("")) {
			returnString.append("&inqApprovedBy=");
			returnString.append(this.getInqApprovedBy());
		}
		
		//Production Status
		if (!this.getInqProductionStatus().equals("")) {
			returnString.append("&inqProductionStatus=");
			returnString.append(this.getInqProductionStatus());
		}
		
		return returnString.toString();
	}

	/**
	 * Determine Security for Method's Send in: request response Return:
	 * Update's the Class to reflect the change in field - Security Level 0 -
	 * See Only Active 1 - See all Formula's 2 - Update Formula's
	 * 
	 * // Currently using on 0 and 2
	 * 
	 * Creation date: (5/25/2010 -- TWalton)
	 */
	public void determineSecurity(HttpServletRequest request, HttpServletResponse response) {
		try {
			try {
				String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
				for (int xr = 0; xr < rolesR.length; xr++) {
					if (rolesR[xr].equals("8"))
						this.setSecurityLevel("2");
				}
			} catch (Exception e) {
			}
			if (this.getSecurityLevel().equals("0")) {
				try {
					String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
					for (int xr = 0; xr < groupsR.length; xr++) {
						if (groupsR[xr].equals("125"))
							this.setSecurityLevel("2");
					}
				} catch (Exception e) {
				}
			}
			// for testing purposes... do not have 2 be the security level
			// this.setSecurityLevel("0");

		} catch (Exception e) {
			System.out.println("Exception Occurred in InqSpecification.determineSecurity(request, response)" + e);
		}
		return;
	}

	public String getInqItemNumberError() {
		return inqItemNumberError;
	}

	public void setInqItemNumberError(String inqItemNumberError) {
		this.inqItemNumberError = inqItemNumberError;
	}

	public String getInqSupplierNumberError() {
		return inqSupplierNumberError;
	}

	public void setInqSupplierNumberError(String inqSupplierNumberError) {
		this.inqSupplierNumberError = inqSupplierNumberError;
	}

	public String getInqCustomerName() {
		return inqCustomerName;
	}

	public void setInqCustomerName(String inqCustomerName) {
		this.inqCustomerName = inqCustomerName;
	}

	public String getInqCustomerNumber() {
		return inqCustomerNumber;
	}

	public void setInqCustomerNumber(String inqCustomerNumber) {
		this.inqCustomerNumber = inqCustomerNumber;
	}

	public String getInqCustomerNumberError() {
		return inqCustomerNumberError;
	}

	public void setInqCustomerNumberError(String inqCustomerNumberError) {
		this.inqCustomerNumberError = inqCustomerNumberError;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getSubfileSize() {
		return subfileSize;
	}

	public void setSubfileSize(int subfileSize) {
		this.subfileSize = subfileSize;
	}

	public String getInqProductionStatus() {
		return inqProductionStatus;
	}

	public void setInqProductionStatus(String inqProductionStatus) {
		this.inqProductionStatus = inqProductionStatus;
	}

	public String getInqTemplate() {
		return inqTemplate;
	}

	public void setInqTemplate(String inqTemplate) {
		this.inqTemplate = inqTemplate;
	}

	public String getInqApprovedBy() {
		return inqApprovedBy;
	}

	public void setInqApprovedBy(String inqApprovedBy) {
		this.inqApprovedBy = inqApprovedBy;
	}

	public String getInqFormulaName() {
		return inqFormulaName;
	}

	public void setInqFormulaName(String inqFormulaName) {
		this.inqFormulaName = inqFormulaName;
	}

	public String getInqOrigination() {
		return inqOrigination;
	}

	public void setInqOrigination(String inqOrigination) {
		this.inqOrigination = inqOrigination;
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

	public String getInqGroup() {
		return inqGroup;
	}

	public void setInqGroup(String inqGroup) {
		this.inqGroup = inqGroup;
	}

	public String getInqFormulaType() {
		return inqFormulaType;
	}

	public void setInqFormulaType(String inqFormulaType) {
		this.inqFormulaType = inqFormulaType;
	}
}
