/*
 * Created on Aug 23, 2005
 *
 */
package com.treetop.app.gtin;

import java.util.List;
import java.util.Vector;

import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBean;

/**
 *   Search for GTIN's - Global Trade Item Numbers
 * @author twalto
 *
 */
public class InqGTIN extends BaseViewBean  {
	
	// Standard Fields - to be in Every View Bean
	public String requestType       = "";
	public List   errors            = null;	
	
	// Standard Fields for Inq View Bean
	public String orderBy    	    = "";
	public String orderStyle 	    = "";
	
	// JSP Specific Fields for THIS View Bean
	public String inqGTIN				 = ""; // Global Trade Item Number
	public String inqResource  			 = "";
	public String inqUPCCode			 = "";
	public String inqTIUD				 = ""; // Trade Item Unit Descriptor
	public String inqBrandName 			 = "";
	public String inqPublish             = "all";
	public String inqPublishType		 = "";
	public String inqShowTree			 = ""; // Family Tree members only
	public String inqDescriptionLong	 = "";
	
	// List for the List Page JSP
	public Vector listReport             = null;
	
		
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *    Will return a String to Resend the parameters
	 *    Mainly from the link on the heading Sort, 
	 *   OR if there is another list view.
	 * @return
	 */
	public String buildParameterResend() {
		StringBuffer returnString = new StringBuffer();
		if (!this.inqGTIN.equals(""))
		{// Global Trade Item Number
			returnString.append("&inqGTIN=");
			returnString.append(this.inqGTIN);
		}
		if (!this.inqResource.equals(""))
		{
			returnString.append("&inqResource=");
			returnString.append(this.inqResource);
		}
		if (!this.inqUPCCode.equals(""))
		{
			returnString.append("&inqUPCCode=");
			returnString.append(this.inqUPCCode);
		}	
		if (!this.inqTIUD.equals(""))
		{// Trade Item Unit Descriptor
			returnString.append("&inqTIUD=");
			returnString.append(this.inqTIUD);
		}
		if (!this.inqBrandName.equals(""))
		{
			returnString.append("&inqBrandName=");
			returnString.append(this.inqBrandName);
		}
		if (!this.inqPublish.equals("all"))
		{
			returnString.append("&inqPublish=");
			returnString.append(this.inqPublish);
		}	
		if (!this.inqPublishType.equals(""))
		{
			returnString.append("&inqPublishType=");
			returnString.append(this.inqPublishType);
		}		
		if (!this.inqDescriptionLong.equals(""))
		{
			returnString.append("&inqDescriptionLong=");
			returnString.append(this.inqDescriptionLong);
		}
		if (this.inqShowTree != null &&
			!this.inqShowTree.equals(""))
		{
			returnString.append("&inqShowTree=");
			returnString.append(this.inqShowTree);
		}
		return returnString.toString();
	}


	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		
		return null;
	}

	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!this.inqGTIN.equals(""))
		{
			returnString.append("GTIN Number: ");
			returnString.append(this.inqGTIN);			  
			if (this.inqShowTree != null && 
				!this.inqShowTree.equals(""))
				returnString.append("<br>&nbsp;Show Family Tree");
		}
		if (!this.inqResource.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Item: ");
			returnString.append(this.inqResource);
		}
		if (!this.inqUPCCode.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("UPC Code: ");
			returnString.append(this.inqUPCCode);
		}	
		if (!this.inqPublish.equals("all"))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Load: ");
			if (this.inqPublish.trim().equals(""))
				returnString.append("Pending");
			if (this.inqPublish.trim().equals("Y"))
				returnString.append("Yes");
			if (this.inqPublish.trim().equals("N"))
				returnString.append("No");
					
		}	
		if (!this.inqPublishType.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Load Type: ");
			if (this.inqPublishType.trim().equals("A"))
				returnString.append("Add");
			if (this.inqPublishType.trim().equals("M"))
				returnString.append("Modify");
		}			
		if (!this.inqTIUD.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("<acronym title=\"Trade Item Unit Descriptor\">Product Type:</acronym> ");
			returnString.append(this.inqTIUD);
		}
		if (!this.inqBrandName.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("Brand: ");
			returnString.append(this.inqBrandName);
		}						
		if (!this.inqDescriptionLong.equals(""))
		{
			if (!returnString.toString().equals(""))
			  returnString.append("<br>");
			returnString.append("GTIN Name: ");
			returnString.append(this.inqDescriptionLong);
		}						
		
		return returnString.toString();
	}
	/**
	 *  Drop down List of VIEW Options for the List Page
	 * @return
	 */
	public static String buildDropDownView(String selectedView)
	{	
		if (selectedView == null)
		  selectedView = "";
		Vector ddList = new Vector();	
			DropDownSingle thisClass = new DropDownSingle();
			thisClass.setDescription("Standard");
			thisClass.setValue("");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("Description");
			thisClass.setValue("description");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("Dimension");
			thisClass.setValue("dimension");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("Weight And Content");
			thisClass.setValue("weightAndContent");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("Relationship");
			thisClass.setValue("relationship");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("True And False Questions");
			thisClass.setValue("trueAndFalse");
			ddList.add(thisClass);
			thisClass = new DropDownSingle();
			thisClass.setDescription("Miscellaneous");
			thisClass.setValue("miscellaneous");
			ddList.add(thisClass);

	        return DropDownSingle.buildDropDown(ddList, "displayView", selectedView, "None", "N", "N");
	}
	/**
	 * @return
	 */
	public String getDropDownTIUD() {
		return UpdGTIN.buildDropDown("inqTIUD", inqTIUD, "N");
	}	

	/**
	 * @return
	 */
	public String getDropDownBrandName() {
		return UpdGTIN.buildDropDown("inqBrandName", inqBrandName, "N");
	}	
	

	/**
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @return
	 */
	public String getOrderStyle() {
		return orderStyle;
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
	public void setOrderBy(String string) {
		orderBy = string;
	}

	/**
	 * @param string
	 */
	public void setOrderStyle(String string) {
		orderStyle = string;
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
	public String getInqResource() {
		return inqResource;
	}

	/**
	 * @param string
	 */
	public void setInqResource(String string) {
		inqResource = string;
	}

	/**
	 * @return
	 */
	public Vector getListReport() {
		return listReport;
	}

	/**
	 * @param vector
	 */
	public void setListReport(Vector vector) {
		listReport = vector;
	}

	/**
	 * @return
	 */
	public String getInqBrandName() {
		return inqBrandName;
	}

	/**
	 *  Global Trade Item Number
	 * @return
	 */
	public String getInqGTIN() {
		return inqGTIN;
	}

	/**
	 *   Trade Item Unit Descriptor
	 * @return
	 */
	public String getInqTIUD() {
		return inqTIUD;
	}

	/**
	 * @return
	 */
	public String getInqUPCCode() {
		return inqUPCCode;
	}

	/**
	 * @param string
	 */
	public void setInqBrandName(String string) {
		inqBrandName = string;
	}

	/**
	 *  Global Trade Item Number
	 * @param string
	 */
	public void setInqGTIN(String string) {
		inqGTIN = string;
	}

	/**
	 *   Trade Item Unit Descriptor
	 * @param string
	 */
	public void setInqTIUD(String string) {
		inqTIUD = string;
	}

	/**
	 * @param string
	 */
	public void setInqUPCCode(String string) {
		inqUPCCode = string;
	}


	/**
	 * Build a Drop Down List for Display on the 
	 *   Inquiry page  --
	 * TWalton 1/31/06
	 */
	public String getDropDownPublish() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("<select name=\"inqPublish\">");
		returnString.append("<option value=\"all\">*All");
		returnString.append("<option value=\"N\">No");
		returnString.append("<option value=\"Y\">Yes ");
		returnString.append("<option value=\"\">Pending");
		returnString.append("</select>");
		return returnString.toString();	

	}
	/**
	 * Build a Drop Down List for Display on the 
	 *   Inquiry page  --
	 * TWalton 1/19/11
	 */
	public String getDropDownPublishType() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("<select name=\"inqPublishType\">");
		returnString.append("<option value=\"\">*All");
		returnString.append("<option value=\"A\">Add");
		returnString.append("<option value=\"M\">Modify ");
		returnString.append("</select>");
		return returnString.toString();	

	}
	/**
	 * @return Returns the inqPublish.
	 */
	public String getInqPublish() {
		return inqPublish;
	}
	/**
	 * @param inqPublish The inqPublish to set.
	 */
	public void setInqPublish(String inqPublish) {
		this.inqPublish = inqPublish;
	}


	/**
	 * @return Returns the inqDescriptionLong.
	 */
	public String getInqDescriptionLong() {
		return inqDescriptionLong;
	}
	/**
	 * @param inqDescriptionLong The inqDescriptionLong to set.
	 */
	public void setInqDescriptionLong(String inqDescriptionLong) {
		this.inqDescriptionLong = inqDescriptionLong;
	}
	/**
	 * @return Returns the inqShowTree.
	 */
	public String getInqShowTree() {
		return inqShowTree;
	}
	/**
	 * @param inqShowTree The inqShowTree to set.
	 */
	public void setInqShowTree(String inqShowTree) {
		this.inqShowTree = inqShowTree;
	}
	/**
		 *  This method should be in EVERY Inquiry View Bean
		 *   Will create the vectors and generate the code for
		 *    MORE Button.
		 * @return
		 */
		public static String buildMoreButton(
			String requestType,
			String gtin,
			String resend) {
			// BUILD Edit/More Button Section(Column)  
			String[] urlLinks = new String[6];
			String[] urlNames = new String[6];
			String[] newPage = new String[6];
			for (int z = 0; z < 6; z++) {
				urlLinks[z] = "";
				urlNames[z] = "";
				newPage[z] = "";
			}
			if (requestType.equals("list")) {
				urlLinks[0] =
					"/web/CtlGTIN?requestType=detail"
						+ "&gtinNumber="
						+ gtin.trim();
				urlNames[0] = "Details of " + gtin.trim();
				newPage[0] = "Y";
			}
			if (requestType.equals("list") ||
			    requestType.equals("detail")) 
			{
				urlLinks[1] =
					"/web/CtlGTIN?requestType=update"
						+ "&gtinNumber="
						+ gtin.trim();
				urlNames[1] = "Update " + gtin.trim();
				newPage[1] = "Y";
			}
			if (requestType.equals("list") ||
				requestType.equals("detail")) 
			{
				urlLinks[2] = "/web/CtlGTIN?requestType=add";
				urlNames[2] = "Add a New GTIN";
				newPage[2] = "Y";
			}
			if (requestType.equals("list") ||
				requestType.equals("detail")) 
			{
	
				urlLinks[3] =
					"/web/CtlGTIN?requestType=updateTies"
						+ "&parentGTIN="
						+ gtin.trim();
				urlNames[3] = "Maintain Children of " + gtin.trim();
				newPage[3] = "Y";
			}
	//		if (requestType.equals("list") ||
	//			requestType.equals("detail")) 
	//		{
	
	//			urlLinks[4] =
	//				"/web/CtlGTIN?requestType=copy" + "&gtinNumber=" + gtin.trim();
	//			urlNames[4] = "Copy " + gtin.trim() + " & Create New";
	//			newPage[4] = "Y";
	//		}
	//		if (requestType.equals("list")) 
	//		{
	//
	//			urlLinks[5] =
	//				"JavaScript:deleteTrans('/web/CtlGTIN?requestType=delete"
	//					+ "&gtinNumber="
	//					+ gtin.trim()
	//					+ resend
	//					+ "')";
	//			urlNames[5] = "Delete " + gtin.trim();
	//			newPage[5] = "N";
	
	//		}
	
			//		<a href="/web/servlet/com.treetop.servlets.CtlUCCNet?requestType=bindResource&globalTradeItemNumber=<%= thisrow.getGlobalTradeItemNumber().trim() %>&<%= parmResend %>">Add Resource to GTIN</a>
			//		 <br/>
			//		<a href="/web/servlet/com.treetop.servlets.CtlUCCNet?requestType=removeResource&globalTradeItemNumber=<%= thisrow.getGlobalTradeItemNumber().trim() %>&resource=<%= theresource.trim() %>&<%= parmResend %>">Remove Resource from GTIN</a>		
	
			return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
		}


	public String getInqPublishType() {
		return inqPublishType;
	}


	public void setInqPublishType(String inqPublishType) {
		this.inqPublishType = inqPublishType;
	}
}