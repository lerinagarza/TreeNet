/*
 * Created on Aug 10, 2010
 *
 */
package com.treetop.app.rawfruit;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.services.ServiceWarehouse;

/**
 *   Search for Scheduled Raw Fruit Load Information
 * @author twalto
 *
 */
public class InqScheduledRawFruit extends BaseViewBeanR1  {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";
	public String environment 	= "TST";

// Standard Fields for Inq View Bean
	public String orderBy    	    = "";
	public String orderStyle 	    = "";	
	public String startRecord		= "";
	
	public String allowUpdate		= "N";
	
// Fields to be Used for Search INQ to LIST	
	public String userProfile					= "";
	public String inqCompanyNumber 				= "100";
	public String inqDivisionNumber 			= "100";
	public String inqWhseNumber					= "1613";
	public String inqWhseAddressNumber			= "101";
	public String inqReceivingLocation			= ""; 
	public String inqDeliveryDateFrom			= "";
	public String inqDeliveryDateTo				= "";
	public String inqHaulingCompany				= "";
	
// Return information to the Screen	
	public Vector listReport = null;	

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
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
		
		if (!this.inqWhseNumber.equals(""))
		{// Warehouse Number - Vendor receiving fruit From
			returnString.append("&inqWhseNumber=");
			returnString.append(this.inqWhseNumber);
		}
		if (!this.inqWhseAddressNumber.equals(""))
		{// Address Number associated to the Warehouse (Vendor for Fruit Receiving)
			returnString.append("&inqWhseAddressNumber=");
			returnString.append(this.inqWhseAddressNumber);
		}
		if (!this.inqReceivingLocation.equals(""))
		{// Receiving Location for the Scheduled Load
			returnString.append("&inqReceivingLocation=");
			returnString.append(this.inqReceivingLocation);
		}
		if (!this.inqHaulingCompany.equals(""))
		{// Hauling Company 
			returnString.append("&inqHaulingCompany=");
			returnString.append(this.inqHaulingCompany);
		}
		if (!this.inqDeliveryDateFrom.equals(""))
		{// Delivery Date From
			returnString.append("&inqDeliveryDateFrom=");
			returnString.append(this.inqDeliveryDateFrom);
		}
		if (!this.inqDeliveryDateTo.equals(""))
		{// Delivery Date To
			returnString.append("&inqDeliveryDateTo=");
			returnString.append(this.inqDeliveryDateTo);
		}
		return returnString.toString();
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();		
		
		if (!this.inqWhseNumber.equals(""))
		{// Warehouse Number - Vendor of Raw Fruit Warehouses
			returnString.append("Warehouse Number=");
			returnString.append(this.inqWhseNumber + "<br>");
		}
		if (!this.inqWhseAddressNumber.equals(""))
		{// Address Number associated to the Vendor (Raw Fruit Warehouse)
			returnString.append("Warehouse Location=");
			returnString.append(this.inqWhseAddressNumber + "<br>");
		}
		if (!this.inqReceivingLocation.equals(""))
		{// Receiving Location
			returnString.append("Receiving Location of the Raw Fruit=");
			returnString.append(this.inqReceivingLocation + "<br>");
		}
		if (!this.inqHaulingCompany.equals(""))
		{// Receiving Date To
			returnString.append("Hauling Company=");
			returnString.append(this.inqHaulingCompany + "<br>");
		}
		if (!this.inqDeliveryDateFrom.equals(""))
		{// Delivery Date From
			returnString.append("FROM: Delivery Date=");
			returnString.append(this.inqDeliveryDateFrom + "<br>");
		}
		if (!this.inqDeliveryDateTo.equals(""))
		{// Delivery Date To
			returnString.append("TO: Delivery Date=");
			returnString.append(this.inqDeliveryDateTo + "<br>");
		}
		return returnString.toString();
	}


	public String getDisplayMessage() {
		return displayMessage;
	}


	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}


	public Vector getListReport() {
		return listReport;
	}


	public void setListReport(Vector listReport) {
		this.listReport = listReport;
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


	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	public String getStartRecord() {
		return startRecord;
	}


	public void setStartRecord(String startRecord) {
		this.startRecord = startRecord;
	}


	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(String requestType,
										 String scaleTicket,
										 String scaleCorrectionSequence,
										 String receivingDate,
										 String allFields,
										 String allowUpd) {
		
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[7];
		String[] urlNames = new String[7];
		String[] newPage = new String[7];
		for (int z = 0; z < 7; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
//		if (allowUpd.equals("Y"))
	//	{
		//   urlLinks[0] = "/web/CtlRawFruit?requestType=update"
			//		+ "&scaleTicket=" + scaleTicket
				//	+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
//		   urlNames[0] = "Update Scale Ticket " + scaleTicket;
	//	   newPage[0] = "Y";
		//}
//		urlLinks[1] = "/web/CtlRawFruit?requestType=reportPO"
	//		+ "&scaleTicket=" + scaleTicket 
		//	+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
		//urlNames[1] = "PO/Lot Entry Report ";
//		newPage[1] = "Y";
	//	urlLinks[2] = "/web/CtlRawFruit?requestType=reportFreight"
		//	+ "&scaleTicket=" + scaleTicket
			//+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
//		urlNames[2] = "Freight Calculation Report ";
	//	newPage[2] = "Y";
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
	}

	/**
	* Determine Security For Adjustments to the More Button
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       String N/Y
	* Creation date: (8/5/2009 -- TWalton)
	*/
	public static String getSecurity(HttpServletRequest request,
									  HttpServletResponse response) {
			
		String returnString = "N";  
		
		try
		{
			 try
			 {
//			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
	//		    for (int xr = 0; xr < rolesR.length; xr++)
		//	    {
			//       if (rolesR[xr].equals("8"))
			  //        returnString = "Y";
			    //}
			 }
			 catch(Exception e)
			 {}
			 if (returnString.equals("N"))
			 {
				   try
				   {
//				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
	//			     for (int xr = 0; xr < groupsR.length; xr++)
		//		     {
			//	           if (groupsR[xr].equals("112")) //Raw Fruit Receiving Update
				//              returnString = "Y";
				  //   }
				   }
				   catch(Exception e)
				   {}
			 }  
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqRawFruit.getSecurity(request, response)" + e);
		}
	    return returnString;
	}

	public String getAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public String getInqCompanyNumber() {
		return inqCompanyNumber;
	}

	public void setInqCompanyNumber(String inqCompanyNumber) {
		this.inqCompanyNumber = inqCompanyNumber;
	}

	public String getInqDeliveryDateFrom() {
		return inqDeliveryDateFrom;
	}

	public void setInqDeliveryDateFrom(String inqDeliveryDateFrom) {
		this.inqDeliveryDateFrom = inqDeliveryDateFrom;
	}

	public String getInqDeliveryDateTo() {
		return inqDeliveryDateTo;
	}

	public void setInqDeliveryDateTo(String inqDeliveryDateTo) {
		this.inqDeliveryDateTo = inqDeliveryDateTo;
	}

	public String getInqDivisionNumber() {
		return inqDivisionNumber;
	}

	public void setInqDivisionNumber(String inqDivisionNumber) {
		this.inqDivisionNumber = inqDivisionNumber;
	}

	public String getInqHaulingCompany() {
		return inqHaulingCompany;
	}

	public void setInqHaulingCompany(String inqHaulingCompany) {
		this.inqHaulingCompany = inqHaulingCompany;
	}

	public String getInqReceivingLocation() {
		return inqReceivingLocation;
	}

	public void setInqReceivingLocation(String inqReceivingLocation) {
		this.inqReceivingLocation = inqReceivingLocation;
	}

	public String getInqWhseAddressNumber() {
		return inqWhseAddressNumber;
	}

	public void setInqWhseAddressNumber(String inqWhseAddressNumber) {
		this.inqWhseAddressNumber = inqWhseAddressNumber;
	}

	public String getInqWhseNumber() {
		return inqWhseNumber;
	}

	public void setInqWhseNumber(String inqWhseNumber) {
		this.inqWhseNumber = inqWhseNumber;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
}