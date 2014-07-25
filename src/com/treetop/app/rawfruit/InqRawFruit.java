/*
 * Created on Aug 23, 2005
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
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.services.ServiceRawFruit;
import com.treetop.services.ServiceWarehouse;

/**
 *   Search for Raw Fruit Load Information
 * @author twalto
 *
 */
public class InqRawFruit extends BaseViewBeanR1  {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

// Standard Fields for Inq View Bean
	public String orderBy    	    = "scaleTicketNumber";
	public String orderStyle 	    = "";	
	public String startRecord		= "";
	
	public String allowUpdate		= "N";
	
// Fields to be Used for Search INQ to LIST	
	public String inqScaleTicketFrom = ""; 
	public String inqScaleTicketTo = "";
	public String inqReceivingDateFrom = "";
	public String inqReceivingDateFromyyyymmdd = "";
	public String inqReceivingDateTo = "";
	public String inqReceivingDateToyyyymmdd = "";
	public String inqWarehouseTicketFrom = "";
	public String inqWarehouseTicketTo = "";
	public String inqCarrierFrom = "";
	public String inqCarrierTo = "";
	public String inqCarrierBOLFrom = "";
	public String inqCarrierBOLTo = "";
	public String inqFromLocation = "";
	public String inqBinBulk = "";
	public String inqFacility = "";
	public String inqWarehouse = "";
	public String inqLot = "";
	public String inqSupplier = "";
	public String inqScheduledLoadNumber = "";
	public String inqWriteUpNumber = "";
	
	public String getList = "";
	
	// if Deleting a LOAD from the LIST PAGE
	public String deleteLoad = "";
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String receivingDate = "";
	public String updateUser = "";
// Return information to the Screen	
	public Vector listReport = null;	

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
		if (!inqReceivingDateFrom.equals(""))
		{// Receiving Date From
			try
			{
				String workWithDate = inqReceivingDateFrom.substring((inqReceivingDateFrom.indexOf("/") + 1));
				String day = workWithDate.substring(0, inqReceivingDateFrom.indexOf("/"));
				if (day.length() == 1)
					inqReceivingDateFrom = 0 + inqReceivingDateFrom;
				DateTime dtFrom = UtilityDateTime.getDateFromMMddyyWithSlash(inqReceivingDateFrom);
				this.setInqReceivingDateFromyyyymmdd(dtFrom.getDateFormatyyyyMMdd());
			}
			catch(Exception e)
			{
				/// leave blank
			}
		}
		if (!inqReceivingDateTo.equals(""))
		{// Receiving Date To
			try
			{
				String workWithDate = inqReceivingDateTo.substring((inqReceivingDateTo.indexOf("/") + 1));
				String day = workWithDate.substring(0, inqReceivingDateTo.indexOf("/"));
				if (day.length() == 1)
					inqReceivingDateTo = 0 + inqReceivingDateTo;
				DateTime dtFrom = UtilityDateTime.getDateFromMMddyyWithSlash(inqReceivingDateTo);
				this.setInqReceivingDateToyyyymmdd(dtFrom.getDateFormatyyyyMMdd());
			}
			catch(Exception e)
			{
				/// leave blank
			}
		}
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
		if (!inqScaleTicketFrom.equals(""))
		{// Scale Ticket From
			returnString.append("&inqScaleTicketFrom=");
			returnString.append(inqScaleTicketFrom);
		}
		if (!inqScaleTicketTo.equals(""))
		{// Scale Ticket To
			returnString.append("&inqScaleTicketTo=");
			returnString.append(inqScaleTicketTo);
		}
		if (!inqReceivingDateFrom.equals(""))
		{// Receiving Date From
			returnString.append("&inqReceivingDateFrom=");
			returnString.append(inqReceivingDateFrom);
		}
		if (!inqReceivingDateTo.equals(""))
		{// Receiving Date To
			returnString.append("&inqReceivingDateTo=");
			returnString.append(inqReceivingDateTo);
		}
		if (!inqWarehouseTicketFrom.equals(""))
		{// Warehouse Ticket (Document) From
			returnString.append("&inqWarehouseTicketFrom=");
			returnString.append(inqWarehouseTicketFrom);
		}
		if (!inqWarehouseTicketTo.equals(""))
		{// Warehouse Ticket (Document) To
			returnString.append("&inqWarehouseTicketTo=");
			returnString.append(inqWarehouseTicketTo);
		}
		if (!inqCarrierFrom.equals(""))
		{// Carrier From
			returnString.append("&inqCarrierFrom=");
			returnString.append(inqCarrierFrom);
		}
		if (!inqCarrierTo.equals(""))
		{// Carrier To
			returnString.append("&inqCarrierTo=");
			returnString.append(inqCarrierTo);
		}
		if (!inqCarrierBOLFrom.equals(""))
		{// Carrier BOL From
			returnString.append("&inqCarrierBOLFrom=");
			returnString.append(inqCarrierBOLFrom);
		}
		if (!inqCarrierBOLTo.equals(""))
		{// Carrier BOL To
			returnString.append("&inqCarrierBOLTo=");
			returnString.append(inqCarrierBOLTo);
		}	
		if (!inqFacility.equals(""))
		{// Receiving Facility
			returnString.append("&inqFacility=");
			returnString.append(inqFacility);
		}	
		if (!inqWarehouse.equals(""))
		{// Receiving Warehouse
			returnString.append("&inqWarehouse=");
			returnString.append(inqWarehouse);
		}	
		if (!inqFromLocation.equals(""))
		{// From Location
			returnString.append("&inqFromLocation=");
			returnString.append(inqFromLocation);
		}
		if (!inqBinBulk.equals(""))
		{// Bin or Bulk Load Determination
			returnString.append("&inqBinBulk=");
			returnString.append(inqBinBulk);
		}	
		if (!inqLot.equals(""))
		{// Lot Number
			returnString.append("&inqLot=");
			returnString.append(inqLot);
		}	
		if (!inqSupplier.equals(""))
		{// Supplier Number
			returnString.append("&inqSupplier=");
			returnString.append(inqSupplier);
		}	
		if (!inqScheduledLoadNumber.equals(""))
		{// Scheduled Load Number
			returnString.append("&inqScheduledLoadNumber=");
			returnString.append(inqScheduledLoadNumber);
		}		
		return returnString.toString();
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();		
		if (!inqScaleTicketFrom.equals(""))
		{// Scale Ticket From
			returnString.append("FROM: Scale Ticket Number=");
			returnString.append(inqScaleTicketFrom + "<br>");
		}
		if (!inqScaleTicketTo.equals(""))
		{// Scale Ticket To
			returnString.append("TO: Scale Ticket Number=");
			returnString.append(inqScaleTicketTo + "<br>");
		}
		if (!inqReceivingDateFrom.equals(""))
		{// Receiving Date From
			returnString.append("FROM: Receiving Date=");
			returnString.append(inqReceivingDateFrom + "<br>");
		}
		if (!inqReceivingDateTo.equals(""))
		{// Receiving Date To
			returnString.append("TO: Receiving Date=");
			returnString.append(inqReceivingDateTo + "<br>");
		}
		if (!inqWarehouseTicketFrom.equals(""))
		{// Warehouse Ticket (Document) From
			returnString.append("FROM: Warehouse Document Number=");
			returnString.append(inqWarehouseTicketFrom + "<br>");
		}
		if (!inqWarehouseTicketTo.equals(""))
		{// Warehouse Ticket (Document) To
			returnString.append("TO: Warehouse Document Number=");
			returnString.append(inqWarehouseTicketTo + "<br>");
		}
		if (!inqCarrierFrom.equals(""))
		{// Carrier From
			returnString.append("FROM: Carrier=");
			returnString.append(inqCarrierFrom + "<br>");
		}
		if (!inqCarrierTo.equals(""))
		{// Carrier To
			returnString.append("TO: Carrier=");
			returnString.append(inqCarrierTo + "<br>");
		}
		if (!inqCarrierBOLFrom.equals(""))
		{// Carrier BOL From
			returnString.append("FROM: Carrier Bill of Lading=");
			returnString.append(inqCarrierBOLFrom + "<br>");
		}
		if (!inqCarrierBOLTo.equals(""))
		{// Carrier BOL To
			returnString.append("TO: Carrier Bill of Lading=");
			returnString.append(inqCarrierBOLTo + "<br>");
		}		
		if (!inqFacility.equals(""))
		{// Receiving Facility
			returnString.append("Receiving Facility=");
			returnString.append(inqFacility + "<br>");
		}		
		if (!inqWarehouse.equals(""))
		{// Receiving Warehouse
			returnString.append("Receiving Warehouse=");
			returnString.append(inqWarehouse + "<br>");
		}		
		if (!inqFromLocation.equals(""))
		{// From Location
			returnString.append("From Location=");
			returnString.append(inqFromLocation + "<br>");
		}
		if (!inqBinBulk.equals(""))
		{// Bin or Bulk Load Determination
			returnString.append("Bin or Bulk Load=");
			returnString.append(inqBinBulk + "<br>");
		}	
		if (!inqLot.equals(""))
		{// Lot Number Determination
			returnString.append("Lot Number=");
			returnString.append(inqLot + "<br>");
		}		
		if (!inqSupplier.equals(""))
		{// Payment Supplier Determination
			returnString.append("Payment Supplier=");
			returnString.append(inqSupplier + "<br>");
		}	
		if (!inqScheduledLoadNumber.equals(""))
		{// Scheduled Load Number
			returnString.append("Scheduled Load Number=");
			returnString.append(inqScheduledLoadNumber + "<br>");
		}		
		return returnString.toString();
	}


	public String getDisplayMessage() {
		return displayMessage;
	}


	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}


	public String getInqBinBulk() {
		return inqBinBulk;
	}


	public void setInqBinBulk(String inqBinBulk) {
		this.inqBinBulk = inqBinBulk;
	}


	public String getInqCarrierBOLFrom() {
		return inqCarrierBOLFrom;
	}


	public void setInqCarrierBOLFrom(String inqCarrierBOLFrom) {
		this.inqCarrierBOLFrom = inqCarrierBOLFrom;
	}


	public String getInqCarrierBOLTo() {
		return inqCarrierBOLTo;
	}


	public void setInqCarrierBOLTo(String inqCarrierBOLTo) {
		this.inqCarrierBOLTo = inqCarrierBOLTo;
	}


	public String getInqCarrierFrom() {
		return inqCarrierFrom;
	}


	public void setInqCarrierFrom(String inqCarrierFrom) {
		this.inqCarrierFrom = inqCarrierFrom;
	}


	public String getInqCarrierTo() {
		return inqCarrierTo;
	}


	public void setInqCarrierTo(String inqCarrierTo) {
		this.inqCarrierTo = inqCarrierTo;
	}


	public String getInqFromLocation() {
		return inqFromLocation;
	}


	public void setInqFromLocation(String inqFromLocation) {
		this.inqFromLocation = inqFromLocation;
	}


	public String getInqReceivingDateFrom() {
		return inqReceivingDateFrom;
	}


	public void setInqReceivingDateFrom(String inqReceivingDateFrom) {
		this.inqReceivingDateFrom = inqReceivingDateFrom;
	}


	public String getInqScaleTicketFrom() {
		return inqScaleTicketFrom;
	}


	public void setInqScaleTicketFrom(String inqScaleTicketFrom) {
		this.inqScaleTicketFrom = inqScaleTicketFrom;
	}


	public String getInqScaleTicketTo() {
		return inqScaleTicketTo;
	}


	public void setInqScaleTicketTo(String inqScaleTicketTo) {
		this.inqScaleTicketTo = inqScaleTicketTo;
	}


	public String getInqWarehouseTicketFrom() {
		return inqWarehouseTicketFrom;
	}


	public void setInqWarehouseTicketFrom(String inqWarehouseTicketFrom) {
		this.inqWarehouseTicketFrom = inqWarehouseTicketFrom;
	}


	public String getInqWarehouseTicketTo() {
		return inqWarehouseTicketTo;
	}


	public void setInqWarehouseTicketTo(String inqWarehouseTicketTo) {
		this.inqWarehouseTicketTo = inqWarehouseTicketTo;
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


	public String getInqReceivingDateTo() {
		return inqReceivingDateTo;
	}


	public void setInqReceivingDateTo(String inqReceivingDateTo) {
		this.inqReceivingDateTo = inqReceivingDateTo;
	}


	/**
	 * Will Return to the Screen, a String of the different Types of Loads
	 */
	public static String buildDropDownTypeOfLoad(String inValue, 
											     String readOnly) {
		String dd = new String();
		try
		{
			Vector buildList = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setDescription("Show All");
			dds.setValue("");
			buildList.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Show Bulk Only");
			dds.setValue("Bulk");
			buildList.addElement(dds);
			dds = new DropDownSingle();
			dds.setDescription("Show Bin Only");
			dds.setValue("Bin");
			buildList.addElement(dds);
			
			dd = DropDownSingle.buildDropDown(buildList, "inqBinBulk", inValue, "None", "N", "N");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	public String getInqReceivingDateFromyyyymmdd() {
		return inqReceivingDateFromyyyymmdd;
	}

	public void setInqReceivingDateFromyyyymmdd(String inqReceivingDateFromyyyymmdd) {
		this.inqReceivingDateFromyyyymmdd = inqReceivingDateFromyyyymmdd;
	}

	public String getInqReceivingDateToyyyymmdd() {
		return inqReceivingDateToyyyymmdd;
	}

	public void setInqReceivingDateToyyyymmdd(String inqReceivingDateToyyyymmdd) {
		this.inqReceivingDateToyyyymmdd = inqReceivingDateToyyyymmdd;
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
		if (allowUpd.equals("Y"))
		{
		   urlLinks[0] = "/web/CtlRawFruit?requestType=update"
					+ "&scaleTicket=" + scaleTicket
					+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
		   urlNames[0] = "Update Scale Ticket " + scaleTicket;
		   newPage[0] = "Y";
		}
		urlLinks[1] = "/web/CtlRawFruit?requestType=reportPO"
			+ "&scaleTicket=" + scaleTicket 
			+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
		urlNames[1] = "PO/Lot Entry Report ";
		newPage[1] = "Y";
		urlLinks[2] = "/web/CtlRawFruit?requestType=reportFreight"
			+ "&scaleTicket=" + scaleTicket
			+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
		urlNames[2] = "Freight Calculation Report ";
		newPage[2] = "Y";
		urlLinks[3] = "/web/CtlRawFruit?requestType=reportInvoice"
			+ "&scaleTicket=" + scaleTicket
			+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence
			+ "&lotNumber=";
		urlNames[3] = "Payment/Invoice Report ";
		newPage[3] = "Y";	
		if (allowUpd.equals("Y"))
		{
		   urlLinks[4] = "/web/CtlRawFruit?requestType=deleteScaleTicket"
			+ "&scaleTicket=" + scaleTicket
			+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence
			+ "&receivingDate=" + receivingDate + allFields;
		   urlNames[4] = "Delete this Load (Scale Ticket) ";
		   newPage[4] = "N";	
		   urlLinks[5] = "/web/CtlRawFruit?requestType=copyPositive"
			+ "&scaleTicket=" + scaleTicket
			+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence
			+ allFields;
		   urlNames[5] = "Copy this Scale Ticket ";
		   newPage[5] = "N";	
		   urlLinks[6] = "/web/CtlRawFruit?requestType=copyNegative"
			+ "&scaleTicket=" + scaleTicket
			+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence
			+ allFields;
		   urlNames[6] = "Reverse (COPY) this Scale Ticket";
		   newPage[6] = "N";
		}
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
	}

	public String getDeleteLoad() {
		return deleteLoad;
	}

	public void setDeleteLoad(String deleteLoad) {
		this.deleteLoad = deleteLoad;
	}

	public String getGetList() {
		return getList;
	}

	public void setGetList(String getList) {
		this.getList = getList;
	}

	public String getScaleTicket() {
		return scaleTicket;
	}

	public void setScaleTicket(String scaleTicket) {
		this.scaleTicket = scaleTicket;
	}

	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}

	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}

	public String getReceivingDate() {
		return receivingDate;
	}

	public void setReceivingDate(String receivingDate) {
		this.receivingDate = receivingDate;
	}

	public String getInqFacility() {
		return inqFacility;
	}

	public void setInqFacility(String inqFacility) {
		this.inqFacility = inqFacility;
	}

	public String getInqWarehouse() {
		return inqWarehouse;
	}

	public void setInqWarehouse(String inqWarehouse) {
		this.inqWarehouse = inqWarehouse;
	}

	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Facility and then getting the Appropriate Warehouses
	 *    along with it.
	 */
	public static Vector buildDropDownFacilityWarehouse(String inFacility, String inWarehouse) {
		Vector dd = new Vector();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddRFLoad");
		   Vector getRecords = ServiceWarehouse.dualDropDownWarehouseFacility("PRD", sentValues);
		   
	      // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(getRecords, "inqFacility", "*all", inFacility, "inqWarehouse", "*all", inWarehouse, "B", "B");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("8"))
			          returnString = "Y";
			    }
			 }
			 catch(Exception e)
			 {}
			 if (returnString.equals("N"))
			 {
			   try
			   {
			     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
			     for (int xr = 0; xr < groupsR.length; xr++)
			     {
			           if (groupsR[xr].equals("112")) //Raw Fruit Receiving Update
			              returnString = "Y";
			     }
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

	public String getInqLot() {
		return inqLot;
	}

	public void setInqLot(String inqLot) {
		this.inqLot = inqLot;
	}

	public String getInqSupplier() {
		return inqSupplier;
	}

	public void setInqSupplier(String inqSupplier) {
		this.inqSupplier = inqSupplier;
	}

	/**
	 * Will Return to the Screen, a String DropDownList Locations
	 */
	public static String buildDropDownFromLocation(String inValue, 
											       String readOnly) {
		String dd = new String();
		try
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("PRD");
			crb.setIdLevel3("Long Description");
			Vector buildList = ServiceRawFruit.dropDownLocation(crb);
			
			dd = DropDownSingle.buildDropDown(buildList, "inqFromLocation", inValue, "Choose One", "N", "N");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	public String getInqScheduledLoadNumber() {
		return inqScheduledLoadNumber;
	}

	public void setInqScheduledLoadNumber(String inqScheduledLoadNumber) {
		this.inqScheduledLoadNumber = inqScheduledLoadNumber;
	}

	public String getInqWriteUpNumber() {
		return inqWriteUpNumber;
	}

	public void setInqWriteUpNumber(String inqWriteUpNumber) {
		this.inqWriteUpNumber = inqWriteUpNumber;
	}
}