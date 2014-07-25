/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.resource;

import java.util.List;
import java.util.Vector;

import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.viewbeans.BaseViewBean;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InqReserveNumbers extends BaseViewBean {

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public List errors = null;

	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";

	// JSP Specific Fields for THIS View Bean
	public String inqResource = "";
	public String inqResourceDescription = "";
	public String inqProjectOwner = "";
	public String inqDueDateFrom = "";
	public String inqDueDateTo = "";
	public String inqProductionDateFrom = "";
	public String inqProductionDateTo = "";
	public String inqResourceType = "";
	public String inqResourceDescriptionLong = "";

	// List for the List Page JSP
	public Vector listReport = null;

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
		if (!inqResourceType.equals("")) {
			returnString.append("Resource Type: ");
			returnString.append(UpdNewItem.displayResourceType(inqResourceType));
		}
		if (!inqResource.equals("")) {
			returnString.append("Resource: ");
			returnString.append(inqResource);
		}
		if (!inqResourceDescription.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Resource Description: ");
			returnString.append(inqResourceDescription);
		}
		if (!inqProjectOwner.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Project Owner: ");
			returnString.append(inqProjectOwner);
		}
		if (!inqDueDateFrom.equals("") || !inqDueDateTo.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");

			returnString.append("Due Date:");

			if (!inqDueDateFrom.equals("") && !inqDueDateTo.equals("")) {
				returnString.append(inqDueDateFrom);
				returnString.append(" TO ");
				returnString.append(inqDueDateTo);
			}
			if (!inqDueDateFrom.equals("") && inqDueDateTo.equals("")) {
				returnString.append(" FROM ");
				returnString.append(inqDueDateFrom);
			}
			if (inqDueDateFrom.equals("") && !inqDueDateTo.equals("")) {
				returnString.append(" TO ");
				returnString.append(inqDueDateTo);
			}
		}
		if (!inqProductionDateFrom.equals("")
			|| !inqProductionDateTo.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");

			returnString.append("Production Date:");

			if (!inqProductionDateFrom.equals("")
				&& !inqProductionDateTo.equals("")) {
				returnString.append(inqProductionDateFrom);
				returnString.append(" TO ");
				returnString.append(inqProductionDateTo);
			}
			if (!inqProductionDateFrom.equals("")
				&& inqProductionDateTo.equals("")) {
				returnString.append(" FROM ");
				returnString.append(inqProductionDateFrom);
			}
			if (inqProductionDateFrom.equals("")
				&& !inqProductionDateTo.equals("")) {
				returnString.append(" TO ");
				returnString.append(inqProductionDateTo);
			}
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
		if (!inqProductionDateTo.equals("")) {
			returnString.append("&inqResourceType=");
			returnString.append(inqResourceType);
		}
		if (!inqResource.equals("")) {
			returnString.append("&inqResource=");
			returnString.append(inqResource);
		}
		if (!inqResourceDescription.equals("")) {
			returnString.append("&inqResourceDescription=");
			returnString.append(inqResourceDescription);
		}
		if (!inqProjectOwner.equals("")) {
			returnString.append("&inqProjectOwner=");
			returnString.append(inqProjectOwner);
		}
		if (!inqDueDateFrom.equals("")) {
			returnString.append("&inqDueDateFrom=");
			returnString.append(inqDueDateFrom);
		}
		if (!inqDueDateTo.equals("")) {
			returnString.append("&inqDueDateTo=");
			returnString.append(inqDueDateTo);
		}
		if (!inqProductionDateFrom.equals("")) {
			returnString.append("&inqProductionDateFrom=");
			returnString.append(inqProductionDateFrom);
		}
		if (!inqProductionDateTo.equals("")) {
			returnString.append("&inqProductionDateTo=");
			returnString.append(inqProductionDateTo);
		}
		return returnString.toString();
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(
		String requestType,
		String resource,
		String resend) {
		// BUILD Edit/More Button Section(Column)  

		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[4];
		String[] urlNames = new String[4];
		String[] newPage = new String[4];
		for (int z = 0; z < 4; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		if (requestType.equals("list")) {
			urlLinks[0] =
				"/web/CtlResourceNew?requestType=update"
					+ "&resource="
					+ resource;
			urlNames[0] = "Add / Update Resource (" + resource + ")";
			newPage[0] = "Y";
		}
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
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
	 * @return
	 */
	public String getInqResourceDescription() {
		return inqResourceDescription;
	}

	/**
	 * @param string
	 */
	public void setInqResource(String string) {
		inqResource = string;
	}

	/**
	 * @param string
	 */
	public void setInqResourceDescription(String string) {
		inqResourceDescription = string;
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
	public String getInqProjectOwner() {
		return inqProjectOwner;
	}

	/**
	 * @param string
	 */
	public void setInqProjectOwner(String string) {
		inqProjectOwner = string;
	}

	/**
	 * @return
	 */
	public String getInqDueDateFrom() {
		return inqDueDateFrom;
	}

	/**
	 * @return
	 */
	public String getInqDueDateTo() {
		return inqDueDateTo;
	}

	/**
	 * @return
	 */
	public String getInqProductionDateFrom() {
		return inqProductionDateFrom;
	}

	/**
	 * @return
	 */
	public String getInqProductionDateTo() {
		return inqProductionDateTo;
	}

	/**
	 * @param string
	 */
	public void setInqDueDateFrom(String string) {
		inqDueDateFrom = string;
	}

	/**
	 * @param string
	 */
	public void setInqDueDateTo(String string) {
		inqDueDateTo = string;
	}

	/**
	 * @param string
	 */
	public void setInqProductionDateFrom(String string) {
		inqProductionDateFrom = string;
	}

	/**
	 * @param string
	 */
	public void setInqProductionDateTo(String string) {
		inqProductionDateTo = string;
	}

	/**
	 * @return Returns the inqResourceDescriptionLong.
	 */
	public String getInqResourceDescriptionLong() {
		return inqResourceDescriptionLong;
	}
	/**
	 * @param inqResourceDescriptionLong The inqResourceDescriptionLong to set.
	 */
	public void setInqResourceDescriptionLong(String inqResourceDescriptionLong) {
		this.inqResourceDescriptionLong = inqResourceDescriptionLong;
	}
	/**
	 * @return Returns the inqResourceType.
	 */
	public String getInqResourceType() {
		return inqResourceType;
	}
	/**
	 * @param inqResourceType The inqResourceType to set.
	 */
	public void setInqResourceType(String inqResourceType) {
		this.inqResourceType = inqResourceType;
	}
}
