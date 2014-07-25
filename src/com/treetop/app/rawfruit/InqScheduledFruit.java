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
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.services.ServiceKeyValue;
import com.treetop.services.ServiceWarehouse;

/**
 *   Search for Scheduled Raw Fruit Load Information
 * @author twalto
 *
 */
public class InqScheduledFruit extends BaseViewBeanR1  {

	// Standard Fields - to be in Every View Bean
	public String requestType   					= "";
	public String displayMessage 					= "";
	public String environment 						= "";
	public String originalRequestType				= "";

// Standard Fields for Inq View Bean
	public String orderBy    	    				= "";
	public String orderStyle 	    				= "";	
	public String startRecord						= "";
	
	public String allowUpdate						= "N";
	
//	 Fields to be Used to Filter Data by User 	
	public String userProfile						= "";
	public String[] roles							= new String[0];
	
// Fields to be Used for Search INQ to LIST	
	public String inqCompanyNumber 					= "100";
	public String inqDivisionNumber 				= "100";
	public String inqScheduledLoadNumber			= "";
	public String inqRegion							= "";
	public String inqWhseNo							= "";
	public String inqLocAddNo						= "";
	public String inqFacility						= "";
	public String inqRecLoc							= "";
	public String inqDock							= "";
	public String inqShipFacility					= "";
	public String inqShipLoc						= "";
	public String inqShipDock						= "";
	public String inqDeliveryDateFrom				= "";
	public String inqDeliveryDateTo					= "";
	public String inqHaulingCompany					= "";
	public String inqCrop							= "";
	public String inqVariety						= "";
	public String inqGrade							= "";
	public String inqOrganic						= "";
	public String inqStickerFree					= "";
	public String inqCashFruit						= "";
	public String inqOrchardRun						= "";
	public String inqStatus							= "";
	public String inqFieldRepresentative			= "";
	public String inqTransfer						= "";
	public String inqOrderBySupplier				= "";  // tw Added 6/6/12
	public String inqBulkOnly						= "";  // tw added 2/27/13
	
	public String inqShowPrice						= "";
	public String inqShowComments					= "";
	
	// Lists used to create Drop Down Lists
	public Vector tripleDropDownRegion				= new Vector();
	public Vector dualDropDownWarehouse				= new Vector();
	public String singleDropDownLocation			= "";
	public String whseNo							= "";
	public String whseDescription					= "";
	public String singleDropDownFacility			= "";
	public String singleDropDownShippingFacility	= "";
	public Vector dualDropDownReceivingLocation		= new Vector();
	public Vector dualDropDownShippingLocation		= new Vector();
	public String singleDropDownReceivingLocation	= "";
	public String singleDropDownHaulingCompany	    = "";
	public Vector dualDropDownCropVariety			= new Vector(); // Dual Drop Down
	public String singleDropDownGrade				= "";
	public String singleDropDownOrganic				= "";
	public String singleDropDownStatus				= "";
	public String singleDropDownFieldRepresentative = "";
	public String singleDropDownTransfer			= "";
	
// Return information to the Screen	
	public BeanAvailFruit beanAvailFruit 			= null;
	public Vector listReport 						= null;	

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
	  try{
		// Take and reformat the date's
		if (!this.inqDeliveryDateFrom.trim().equals(""))
		{
			DateTime tdFrom = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.inqDeliveryDateFrom);
			this.inqDeliveryDateFrom = tdFrom.getDateFormatyyyyMMdd();
		}
		if (!this.inqDeliveryDateTo.trim().equals(""))
		{
			DateTime tdTo = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.inqDeliveryDateTo);
			this.inqDeliveryDateTo = tdTo.getDateFormatyyyyMMdd();
		}
		if (this.getInqRegion() == null ||
			this.getInqRegion().trim().equals("Make a selection") ||
			this.getInqRegion().trim().equals("ORCHARD RUN"))
		  this.setInqRegion("");

		if (this.getInqWhseNo() == null ||
			this.getInqWhseNo().trim().equals("Make a selection"))
		  this.setInqWhseNo("");
		// Test for a number at the end of the value
		if (!this.getInqWhseNo().trim().equals(""))
		{
			int xLastIndex = this.getInqWhseNo().lastIndexOf("-*-");
			if (xLastIndex > 0)
			{
				this.setInqWhseNo(this.getInqWhseNo().substring((xLastIndex + 3)));
			}
		}
		if (this.getInqLocAddNo() == null ||
			this.getInqLocAddNo().trim().equals("Make a selection"))
		  this.setInqLocAddNo("");
		// Test for a number at the end of the value
		if (!this.getInqLocAddNo().trim().equals(""))
		{
			int xLastIndex = this.getInqLocAddNo().lastIndexOf("-*-");
			if (xLastIndex > 0)
			{
				this.setInqLocAddNo(this.getInqLocAddNo().substring((xLastIndex + 3)));
			}
		}
		if (this.getEnvironment().trim().equals(""))
			this.setEnvironment("PRD");
	  }catch(Exception e)
	  {}
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
		
		if (!this.inqScheduledLoadNumber.equals(""))
		{// Scheduled Load Number
			returnString.append("&inqScheduledLoadNumber=");
			returnString.append(this.inqScheduledLoadNumber);
		}
		if (!this.inqRegion.equals(""))
		{// Region - North South
			returnString.append("&inqRegion=");
			returnString.append(this.inqRegion);
		}
		if (!this.inqWhseNo.equals(""))
		{// Warehouse Number - Vendor receiving fruit From
			returnString.append("&inqWhseNo=");
			returnString.append(this.inqWhseNo);
		}
		if (!this.inqLocAddNo.equals(""))
		{// Address Number associated to the Warehouse (Vendor for Fruit Receiving)
			returnString.append("&inqLocAddNo=");
			returnString.append(this.inqLocAddNo);
		}
		if (!this.inqFacility.equals(""))
		{// Facility Tied to the Receiving Location (warehouse)
			returnString.append("&inqFacility=");
			returnString.append(this.inqFacility);
		}
		if (!this.inqRecLoc.equals(""))
		{// Receiving Location for the Scheduled Load - Warehouse in M3
			returnString.append("&inqRecLoc=");
			returnString.append(this.inqRecLoc);
		}
		if (!this.inqDock.equals(""))
		{// Dock assigned to the Receiving Location
			returnString.append("&inqDock=");
			returnString.append(this.inqDock);
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
		if (!this.inqStatus.equals(""))
		{// Status
			returnString.append("&inqStatus=");
			returnString.append(this.inqStatus);
		}
		if (!this.inqCrop.equals(""))
		{// Fruit Crop
			returnString.append("&inqCrop=");
			returnString.append(this.inqCrop);
		}
		if (!this.inqVariety.equals(""))
		{// Fruit Variety
			returnString.append("&inqVariety=");
			returnString.append(this.inqVariety);
		}
		if (!this.inqGrade.equals(""))
		{// Fruit Grade
			returnString.append("&inqGrade=");
			returnString.append(this.inqGrade);
		}
		if (!this.inqOrganic.equals(""))
		{// Fruit Type
			returnString.append("&inqOrganic=");
			returnString.append(this.inqOrganic);
		}
		if (!this.inqStickerFree.equals(""))
		{// Sticker Free
			returnString.append("&inqStickerFree=");
			returnString.append(this.inqStickerFree);
		}
		if (!this.inqCashFruit.equals(""))
		{// Cash Fruit
			returnString.append("&inqCashFruit=");
			returnString.append(this.inqCashFruit);
		}
		if (!this.inqShowPrice.equals(""))
		{// Show Price
			returnString.append("&inqShowPrice=");
			returnString.append(this.inqShowPrice);
		}
		if (!this.inqFieldRepresentative.equals(""))
		{// Field Representative
			returnString.append("&inqFieldRepresentative=");
			returnString.append(this.inqFieldRepresentative);
		}
		if (!this.inqShowComments.equals(""))
		{// Show Comments
			returnString.append("&inqShowComments=");
			returnString.append(this.inqShowComments);
		}
		if (!this.inqTransfer.equals(""))
		{// Filter for the Transfer Loads
			returnString.append("&inqTransfer=");
			returnString.append(this.inqTransfer);
		}
		if (!this.inqShipFacility.equals(""))
		{// Shipping Facility
			returnString.append("&inqShipFacility=");
			returnString.append(this.inqShipFacility);
		}
		if (!this.inqShipLoc.equals(""))
		{// Shipping Location for the Scheduled Load - Warehouse in M3
			returnString.append("&inqShipLoc=");
			returnString.append(this.inqShipLoc);
		}
		if (!this.inqShipDock.equals(""))
		{// Dock assigned to the Shipping Location
			returnString.append("&inqShipDock=");
			returnString.append(this.inqShipDock);
		}
		if (!this.inqOrderBySupplier.equals("")) // tw Added 6/6/12
		{
			returnString.append("&inqOrderBySupplier=");
			returnString.append(this.inqOrderBySupplier);
		}
		if (!this.inqBulkOnly.equals("")) // tw Added 2/27/13
		{
			returnString.append("&inqBulkOnly=Y");
		}
		return returnString.toString();
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();		
		
		if (!this.inqScheduledLoadNumber.equals(""))
		{// Scheduled Load Number
			returnString.append("Scheduled Load Number= ");
			returnString.append(this.inqScheduledLoadNumber + "<br>");
		}
		if (!this.inqRegion.equals(""))
		{// Region tied to the Warehouse Number - Vendor of Raw Fruit Warehouses
			returnString.append("Region= ");
			returnString.append(this.inqRegion + "<br>");
		}
		if (!this.inqWhseNo.equals(""))
		{// Warehouse Number - Vendor of Raw Fruit Warehouses
			returnString.append("Warehouse Number= ");
			returnString.append(this.inqWhseNo + "<br>");
		}
		if (!this.inqLocAddNo.equals(""))
		{// Address Number associated to the Vendor (Raw Fruit Warehouse)
			returnString.append("Warehouse Location= ");
			returnString.append(this.inqLocAddNo + "<br>");
		}
		if (!this.inqFacility.equals(""))
		{// Facility Tied to Receiving Location
			returnString.append("Facility= ");
			returnString.append(this.inqFacility + "<br>");
		}
		if (!this.inqRecLoc.equals(""))
		{// Receiving Location M3 Warehouse
			returnString.append("Receiving Location of the Raw Fruit= ");
			returnString.append(this.inqRecLoc + "<br>");
		}
		if (!this.inqDock.equals(""))
		{// Dock assigned to the Receiving Location
			returnString.append("Dock= ");
			returnString.append(this.inqDock + "<br>");
		}
		if (!this.inqHaulingCompany.equals(""))
		{// Receiving Date To
			returnString.append("Hauling Company= ");
			returnString.append(this.inqHaulingCompany + "<br>");
		}
		if (!this.inqDeliveryDateFrom.equals(""))
		{// Delivery Date From
			returnString.append("FROM: Delivery Date= ");
			returnString.append(this.inqDeliveryDateFrom + "<br>");
		}
		if (!this.inqDeliveryDateTo.equals(""))
		{// Delivery Date To
			returnString.append("TO: Delivery Date= ");
			returnString.append(this.inqDeliveryDateTo + "<br>");
		}
		if (!this.inqStatus.equals(""))
		{// Status
			returnString.append("Status= ");
			returnString.append(this.inqStatus + "<br>");
		}
		if (!this.inqCrop.equals(""))
		{// Crop
			returnString.append("Crop= ");
			returnString.append(this.inqCrop + "<br>");
		}
		if (!this.inqVariety.equals(""))
		{// Variety
			returnString.append("Variety= ");
			returnString.append(this.inqVariety + "<br>");
		}
		if (!this.inqGrade.equals(""))
		{// Fruit Grade
			returnString.append("Grade= ");
			returnString.append(this.inqGrade + "<br>");
		}
		if (!this.inqOrganic.equals(""))
		{// Fruit Type -- Organic / Conventional / BabyFood
			returnString.append("Fruit Type= ");
			returnString.append(this.inqOrganic + "<br>");
		}
		if (!this.inqStickerFree.equals(""))
		{// Is the Fruit Sticker Free?
			returnString.append("Sticker Free= ");
			returnString.append(this.inqStickerFree + "<br>");
		}
		if (!this.inqCashFruit.equals("")) // added 1/29/13 TWalton
		{// Is Cash Fruit?
			returnString.append("Cash Fruit= ");
			returnString.append(this.inqCashFruit + "<br>");
		}
		if (!this.inqShowPrice.equals(""))
		{// Display the Price on the list Page
			returnString.append("Show Price= ");
			returnString.append(this.inqShowPrice + "<br>");
		}
		if (!this.inqFieldRepresentative.equals(""))
		{// Field Representative
			returnString.append("Field Representative= ");
			returnString.append(this.inqFieldRepresentative + "<br>");
		}
		if (!this.inqShowComments.equals(""))
		{ // Display the Comments on the List Page
			returnString.append("Show Comments= ");
			returnString.append(this.inqShowComments + "<br>");
		}
		if (!this.inqTransfer.equals(""))
		{ // Filter for Transfer Load
			returnString.append("Transfer Filter= ");
			returnString.append(this.inqTransfer + "<br>");
		}
		if (!this.inqShipFacility.equals(""))
		{ // shipping Facility
			returnString.append("Shipping Facility= ");
			returnString.append(this.inqShipFacility + "<br>");
		}
		if (!this.inqShipLoc.equals(""))
		{// Shipping Location M3 Warehouse
			returnString.append("Shipping Location of the Raw Fruit= ");
			returnString.append(this.inqShipLoc + "<br>");
		}
		if (!this.inqShipDock.equals(""))
		{// Dock assigned to the Shipping Location
			returnString.append("Shipping Dock= ");
			returnString.append(this.inqShipDock + "<br>");
		}
		if (!this.inqOrderBySupplier.equals("")) // tw Added 6/6/12
		{
			returnString.append("Order by Supplier=");
			returnString.append(this.inqOrderBySupplier + "<br>");
		}
		if (!this.inqBulkOnly.equals("")) // tw Added 2/27/13
		{
			returnString.append("Bulk Only?=");
			returnString.append(this.inqBulkOnly + "<br>");
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
										 String environment,
										 String loadNumber,
										 String whseNumber,
										 String whseLocationNumber,
										 String inqInformation,
										 String allowUpd,
										 String transferLoad) {
		
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[9];
		String[] urlNames = new String[9];
		String[] newPage = new String[9];
		for (int z = 0; z < 9; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		 // detail Page
	    urlLinks[0] = "/web/CtlRawFruit?requestType=dtlSchedFruit"
			+ "&environment=" + environment 
			+ "&loadNumber=" + loadNumber;
	    urlNames[0] = "Detail of Load " + loadNumber;
	    newPage[0] = "Y";
		if (allowUpd.equals("Y"))
		{ 
	   // update Load
			if (transferLoad.trim().equals("Y"))
			{
				urlLinks[1] = "/web/CtlRawFruit?requestType=updSchedTransfer"
					+ "&environment=" + environment 
					+ "&loadNumber=" + loadNumber
					+ inqInformation;
			}
			else
			{
			   urlLinks[1] = "/web/CtlRawFruit?requestType=updSchedFruit"
					+ "&environment=" + environment 
					+ "&loadNumber=" + loadNumber
					+ inqInformation;
			}
			urlNames[1] = "Update Scheduled Load " + loadNumber;
			if (transferLoad.trim().equals("Y"))
				newPage[1] = "Y";
			else
			    newPage[1] = "N";
	    // Receive the Load
			urlLinks[2] = "/web/CtlRawFruit?requestType=receiveSchedLoad"
					+ "&environment=" + environment
					+ "&loadNumber=" + loadNumber
					+ inqInformation;
			urlNames[2] = "Load Has Been Received";
			newPage[2] = "N";
			//	Receive the load and GO TO actual Receiving Screen
			// Add an option to directly kick out to NEW receiving screen
			urlLinks[3] = "/web/CtlRawFruit?requestType=goToReceiveSchedLoad"
					+ "&environment=" + environment
					+ "&loadNumber=" + loadNumber
					+ "&scheduledLoadNumber=" + loadNumber;
			
			urlNames[3] = "Receive this Load and go to Fruit Receiving Screen";
			newPage[3] = "Y";	
	    // add new Load
			// 11/24/10 TWalton - chose to put the add a load in the options at the top of the screen
		//	urlLinks[3] = "/web/CtlRawFruit?requestType=addSchedFruitLoad"
		//			+ "&environment=" + environment
		//			+ inqInformation;
		//	urlNames[3] = "Add New Load";
		//	newPage[3] = "N";
		
		}
		if (allowUpd.equals("R"))
		{// UN-Receive a Load
			urlLinks[4] = "/web/CtlRawFruit?requestType=unreceiveSchedLoad"
				+ "&environment=" + environment 
				+ "&loadNumber=" + loadNumber
				+ inqInformation;
		    urlNames[4] = "Change load to NOT Received " + loadNumber;
		    newPage[4] = "N";
		}
		if (allowUpd.equals("Y"))
		{ 
	    // cancel Load
			urlLinks[5] = "/web/CtlRawFruit?requestType=cancelSchedLoad"
					+ "&environment=" + environment 
					+ "&loadNumber=" + loadNumber
					+ inqInformation;
			urlNames[5] = "Cancel Scheduled Load " + loadNumber;
			newPage[5] = "N";
	    // delete load
			urlLinks[6] = "/web/CtlRawFruit?requestType=deleteSchedLoad"
					+ "&environment=" + environment 
					+ "&loadNumber=" + loadNumber
					+ inqInformation;
			urlNames[6] = "Delete Scheduled Load " + loadNumber;
			newPage[6] = "N";
	  }
//		 11/24/10 TWalton - chose to put the add a load in the options at the top of the screen
	//    urlLinks[6] = "/web/CtlRawFruit?requestType=inqAvailFruit"
	//		+ "&environment=" + environment;
	//	urlNames[6] = "Schedule Available Fruit ";
	//	newPage[6] = "Y";
		if (allowUpd.equals("C"))
		{ 
			// 4/4/13 -- TWalton -- added section to Un-Cancel a Scheduled Load
			urlLinks[7] = "/web/CtlRawFruit?requestType=unCancelSchedLoad"
				+ "&environment=" + environment 
				+ "&environment=" + environment 
				+ "&loadNumber=" + loadNumber
				+ inqInformation;
			urlNames[7] = "Un-Cancel this Load ";
			newPage[7] = "Y";
		}
		
		urlLinks[8] = "/web/CtlRawFruit?requestType=updAvailFruit"
			+ "&environment=" + environment 
			+ "&whseNo=" + whseNumber
			+ "&locAddNo=" + whseLocationNumber;
		urlNames[8] = "Review Warehouse Available Fruit ";
		newPage[8] = "Y";
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
				           if (groupsR[xr].equals("113")) //Raw Fruit Receiving Update
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

	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	/**
	 *    Will Retrieve Information to be used for Drop Down Lists on the Screen
	 *    Generate a Vector of Data to be used on the screen (JSP)
	 *       to make the Drop Down Dual informaiton work.
	 *       Each Element
	 *       [0] - code for the Master drop down in the table
	 *       [1] - code for the Slave drop down in the table
	 *       [2] - code for 1 of the Script's put in the Header - in the JSP
	 *       [3] - code for the second script put in the Header - in the JSP   
	 *    
	 */
	public void buildDropDownInformation() {
		try
		{
			
			// Decide if it is ONE warehouse.
		    int warehouseCount = 1;
//			// roll throught the vector to see if it is ONE warehouse
			String saveWarehouse = "";
			Vector singleDD = new Vector();
			for (int x = 0; x < this.listReport.size(); x++)
			{
				DropDownTriple ddt = (DropDownTriple) this.listReport.elementAt(x);
				if ((saveWarehouse.trim().equals("") ||
					 saveWarehouse.trim().equals(ddt.getList2Value())) &&
					 warehouseCount == 1)
				{
					DropDownSingle dds = new DropDownSingle();
					dds.setValue(ddt.getList3Value());
					dds.setDescription(ddt.getList3Description().trim());
					singleDD.addElement(dds);
				}
				else
					warehouseCount++;
				saveWarehouse = ddt.getList2Value().trim();
			}
			if (warehouseCount == 1)
			{
				DropDownTriple ddt = (DropDownTriple) this.listReport.elementAt(0);
				this.setWhseNo(ddt.getList2Value());
				this.setWhseDescription(ddt.getList2Description());
				if (this.getRequestType().trim().equals("inqAvailFruit") ||
					this.getRequestType().trim().equals("inqAvailFruitAll"))
				  this.setSingleDropDownLocation(DropDownSingle.buildDropDown(singleDD, "inqLocAddNo", this.getInqLocAddNo().trim(), "None", "B", "N"));
				else
				  this.setSingleDropDownLocation(DropDownSingle.buildDropDown(singleDD, "locAddNo", this.getWhseNo().trim(), "None", "B", "N"));
			}
			else
			{
				if (this.getRequestType().trim().equals("inqSchedFruit"))
				{
					String list1 = "inqRegion";
					String list2 = "inqWhseNo";
					String list3 = "inqLocAddNo";
					
					Vector returnData1 = DropDownTriple.buildTripleDropDown(this.getListReport(), list1, "", "", list2, "", "E", list3, "", "E");
					Vector oneElement1 = new Vector();
					oneElement1.addElement((String) returnData1.elementAt(0));
					oneElement1.addElement(JavascriptInfo.getTripleDropDown("inqSched", list1, list2, list3) );
					oneElement1.addElement((String) returnData1.elementAt(1));
					oneElement1.addElement((String) returnData1.elementAt(2));
					oneElement1.addElement((String) returnData1.elementAt(3));
					this.setTripleDropDownRegion(oneElement1);
					
				}
		//		else
		//		{
		//			String masterName = "whseNumber";
		//			String slaveName = "whseAddressNumber";
		//		  Vector returnData = DropDownDual.buildDualDropDownNew(this.getListReport(), masterName, "", "", slaveName, "", "", "N", "N");
		//		  Vector oneElement = new Vector();
		//		  oneElement.addElement((String) returnData.elementAt(0));
		//		  oneElement.addElement("<select name=\"" + slaveName + "\">");
		//		  oneElement.addElement((String) returnData.elementAt(1));
		//		  oneElement.addElement(JavascriptInfo.getDualDropDownNew(masterName, slaveName));
		//		  this.setDualDropDownWarehouse(oneElement);
		//		}
				
			}
			if (this.getRequestType().trim().equals("inqSchedFruit"))
			{
				try
				{
					CommonRequestBean crb = new CommonRequestBean();
					crb.setEnvironment(this.getEnvironment());
					crb.setCompanyNumber(this.getInqCompanyNumber());
					crb.setDivisionNumber(this.getInqDivisionNumber());
					Vector ddHaul = ServiceAvailableFruit.dropDownHauler(crb);
					DropDownSingle ddSingle = new DropDownSingle();
					ddSingle.setDescription("");
					ddSingle.setValue("SAME");
					ddHaul.insertElementAt(ddSingle, 0);
					this.setSingleDropDownHaulingCompany(DropDownSingle.buildDropDown(ddHaul, "inqHaulingCompany", this.getInqHaulingCompany(), "", "E", "N"));
					Vector returnDropDownFacility = ServiceAvailableFruit.dropDownFacility(crb);
					this.setSingleDropDownFacility(DropDownSingle.buildDropDown(returnDropDownFacility, "inqFacility", this.getInqFacility(), "", "B", "N"));
					this.setSingleDropDownShippingFacility(DropDownSingle.buildDropDown(returnDropDownFacility, "inqShipFacility", this.getInqShipFacility(), "", "B", "N"));
					Vector returnDropDownWarehouse = ServiceAvailableFruit.dropDownReceivingWarehouse(crb);
					Vector listWhse = DropDownDual.buildDualDropDownNew(returnDropDownWarehouse, "inqRecLoc", "", "", "inqDock", "", "", "B", "N");
					Vector buildScreenWhseData = new Vector();
					buildScreenWhseData.addElement((String) listWhse.elementAt(0));
					buildScreenWhseData.addElement("<select name=\"inqDock\">");
					buildScreenWhseData.addElement((String) listWhse.elementAt(1));
					buildScreenWhseData.addElement(JavascriptInfo.getDualDropDownNew("inqRecLoc", "inqDock"));
					this.setDualDropDownReceivingLocation(buildScreenWhseData);
					Vector listShipWhse = DropDownDual.buildDualDropDownNew(returnDropDownWarehouse, "inqShipLoc", "", "", "inqShipDock", "", "", "B", "N");
					Vector buildScreenShipWhseData = new Vector();
					buildScreenShipWhseData.addElement((String) listShipWhse.elementAt(0));
					buildScreenShipWhseData.addElement("<select name=\"inqShipDock\">");
					buildScreenShipWhseData.addElement((String) listShipWhse.elementAt(1));
					buildScreenShipWhseData.addElement(JavascriptInfo.getDualDropDownNew("inqShipLoc", "inqShipDock"));
					this.setDualDropDownShippingLocation(buildScreenShipWhseData);
					this.setSingleDropDownGrade(DropDownSingle.buildDropDown(ServiceAvailableFruit.dropDownGrade(crb), "inqGrade", this.getInqGrade(), "", "N", "N"));
					this.setSingleDropDownOrganic(DropDownSingle.buildDropDown(ServiceAvailableFruit.dropDownOrganic(crb), "inqOrganic", this.getInqOrganic(), "", "N", "N"));
					this.setSingleDropDownFieldRepresentative(DropDownSingle.buildDropDown(ServiceAvailableFruit.dropDownFieldRep(crb), "inqFieldRepresentative", this.getInqFieldRepresentative(), "", "N", "N"));
					Vector buildList = new Vector();
					DropDownSingle dds = new DropDownSingle();
					dds.setDescription("Show All");
					dds.setValue("all");
					buildList.addElement(dds);
					dds = new DropDownSingle();
					dds.setDescription("Pending Loads");
					dds.setValue("");
					buildList.addElement(dds);
					dds = new DropDownSingle();
					dds.setDescription("Received Loads");
					dds.setValue("R");
					buildList.addElement(dds);
					dds = new DropDownSingle();
					dds.setDescription("Cancelled Loads");
					dds.setValue("C");
					buildList.addElement(dds);
					dds = new DropDownSingle();
					dds.setDescription("Deleted Loads");
					dds.setValue("D");
					buildList.addElement(dds);
					this.setSingleDropDownStatus(DropDownSingle.buildDropDown(buildList, "inqStatus", "", "None", "N", "N"));
					buildList = new Vector();
					dds = new DropDownSingle();
					dds.setDescription("Show All");
					dds.setValue("");
					buildList.addElement(dds);
					dds = new DropDownSingle();
					dds.setDescription("Do Not Include Transfer Loads");
					dds.setValue("N");
					buildList.addElement(dds);
					dds = new DropDownSingle();
					dds.setDescription("Only Show Transfer Loads");
					dds.setValue("Y");
					buildList.addElement(dds);
					this.setSingleDropDownTransfer(DropDownSingle.buildDropDown(buildList, "inqTransfer", "", "None", "N", "N"));
					// Dual Drop Downs for Crop/Variety
					Vector listData = DropDownDual.buildDualDropDownNew(ServiceAvailableFruit.dropDownVariety(crb), "inqCrop", "", "", "inqVariety", "", "", "N", "N");
					Vector buildScreenData = new Vector();
					buildScreenData.addElement((String) listData.elementAt(0));
					buildScreenData.addElement("<select name=\"inqVariety\">");
					buildScreenData.addElement((String) listData.elementAt(1));
					buildScreenData.addElement(JavascriptInfo.getDualDropDownNew("inqCrop", "inqVariety"));
					this.setDualDropDownCropVariety(buildScreenData);
				}catch(Exception e)
				{}
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return;
	}
	public String getSingleDropDownHaulingCompany() {
		return singleDropDownHaulingCompany;
	}
	public void setSingleDropDownHaulingCompany(String singleDropDownHaulingCompany) {
		this.singleDropDownHaulingCompany = singleDropDownHaulingCompany;
	}
	public String getSingleDropDownLocation() {
		return singleDropDownLocation;
	}
	public void setSingleDropDownLocation(String singleDropDownLocation) {
		this.singleDropDownLocation = singleDropDownLocation;
	}
	public String getSingleDropDownReceivingLocation() {
		return singleDropDownReceivingLocation;
	}
	public void setSingleDropDownReceivingLocation(
			String singleDropDownReceivingLocation) {
		this.singleDropDownReceivingLocation = singleDropDownReceivingLocation;
	}
	public String getWhseDescription() {
		return whseDescription;
	}
	public void setWhseDescription(String whseDescription) {
		this.whseDescription = whseDescription;
	}
	public BeanAvailFruit getBeanAvailFruit() {
		return beanAvailFruit;
	}
	public void setBeanAvailFruit(BeanAvailFruit beanAvailFruit) {
		this.beanAvailFruit = beanAvailFruit;
	}
	public String getInqCrop() {
		return inqCrop;
	}
	public void setInqCrop(String inqCrop) {
		this.inqCrop = inqCrop;
	}
	public String getInqGrade() {
		return inqGrade;
	}
	public void setInqGrade(String inqGrade) {
		this.inqGrade = inqGrade;
	}
	public String getInqOrganic() {
		return inqOrganic;
	}
	public void setInqOrganic(String inqOrganic) {
		this.inqOrganic = inqOrganic;
	}
	public String getInqVariety() {
		return inqVariety;
	}
	public void setInqVariety(String inqVariety) {
		this.inqVariety = inqVariety;
	}
	public Vector getDualDropDownCropVariety() {
		return dualDropDownCropVariety;
	}
	public void setDualDropDownCropVariety(Vector dualDropDownCropVariety) {
		this.dualDropDownCropVariety = dualDropDownCropVariety;
	}
	public String getSingleDropDownGrade() {
		return singleDropDownGrade;
	}
	public void setSingleDropDownGrade(String singleDropDownGrade) {
		this.singleDropDownGrade = singleDropDownGrade;
	}
	public String getSingleDropDownOrganic() {
		return singleDropDownOrganic;
	}
	public void setSingleDropDownOrganic(String singleDropDownOrganic) {
		this.singleDropDownOrganic = singleDropDownOrganic;
	}
	public String getInqStatus() {
		return inqStatus;
	}
	public void setInqStatus(String inqStatus) {
		this.inqStatus = inqStatus;
	}
	/**
	* Get Long Description of the Load Received Flag
	*    Send in: String (code)
	*    Return: String Long Name for the Code
	* Creation date: (11/17/2010 -- TWalton)
	*/
	public static String getLongLoadReceivedFlag(String inValue) {
			
		String outValue = inValue;
		try{
			if (inValue.trim().equals("C"))
				outValue = "Cancelled";
			if (inValue.trim().equals("D"))
				outValue = "Deleted";
			if (inValue.trim().equals("R"))
				outValue = "Received";	
			
		}catch(Exception e)
		{}
	    return outValue;
	}
	public String getSingleDropDownStatus() {
		return singleDropDownStatus;
	}
	public void setSingleDropDownStatus(String singleDropDownStatus) {
		this.singleDropDownStatus = singleDropDownStatus;
	}
	/**
	 *  This method will return a formatted date for the screen
	 *    -- specific for this Entire Application ---
	 *    // Will be used by ALL screens in this Application
	 * @return
	 */
	public static String formatDate(String inDate) {
	    try{
	       if (inDate.trim().equals("0"))
	    	  inDate = "";
	  	   if (!inDate.trim().equals(""))
		   {
		     DateTime dt1 = UtilityDateTime.getDateFromyyyyMMdd(inDate);
			 inDate = dt1.getDateFormatMMddyyyySlash();
		   }  	
	    }catch(Exception e)
	    {
	    	//Will return the inDate if an exception is caught
	    }
		return inDate;
	}
	/**
	 *  This method will return a formatted time for the screen
	 *    -- specific for this Entire Application ---
	 *    // Will be used by ALL screens in this Application
	 * @return
	 */
	public static String formatTime(String inTime) {
	    try{
	       if (inTime.trim().equals("0"))
	    	  inTime = "";
	  	   if (!inTime.trim().equals(""))
		   {
	  		 DateTime tm1 = UtilityDateTime.getTimeFromhhmmss(inTime);
			 inTime = tm1.getTimeFormathhmmssColon();
			 inTime = inTime.substring(0, 5);
		   }  	
	    }catch(Exception e)
	    {
	    	//Will return the inDate if an exception is caught
	    }
		return inTime;
	}
	public String getOriginalRequestType() {
		return originalRequestType;
	}
	public void setOriginalRequestType(String originalRequestType) {
		this.originalRequestType = originalRequestType;
	}
	public String getInqFieldRepresentative() {
		return inqFieldRepresentative;
	}
	public void setInqFieldRepresentative(String inqFieldRepresentative) {
		this.inqFieldRepresentative = inqFieldRepresentative;
	}
	public String getSingleDropDownFieldRepresentative() {
		return singleDropDownFieldRepresentative;
	}
	public void setSingleDropDownFieldRepresentative(
			String singleDropDownFieldRepresentative) {
		this.singleDropDownFieldRepresentative = singleDropDownFieldRepresentative;
	}
	public String getInqShowPrice() {
		return inqShowPrice;
	}
	public void setInqShowPrice(String inqShowPrice) {
		this.inqShowPrice = inqShowPrice;
	}
	public String getInqOrchardRun() {
		return inqOrchardRun;
	}
	public void setInqOrchardRun(String inqOrchardRun) {
		this.inqOrchardRun = inqOrchardRun;
	}
	public String getInqStickerFree() {
		return inqStickerFree;
	}
	public void setInqStickerFree(String inqStickerFree) {
		this.inqStickerFree = inqStickerFree;
	}
	public Vector getTripleDropDownRegion() {
		return tripleDropDownRegion;
	}
	public void setTripleDropDownRegion(Vector tripleDropDownRegion) {
		this.tripleDropDownRegion = tripleDropDownRegion;
	}
	public Vector getDualDropDownWarehouse() {
		return dualDropDownWarehouse;
	}
	public void setDualDropDownWarehouse(Vector dualDropDownWarehouse) {
		this.dualDropDownWarehouse = dualDropDownWarehouse;
	}
	public String getInqLocAddNo() {
		return inqLocAddNo;
	}
	public void setInqLocAddNo(String inqLocAddNo) {
		this.inqLocAddNo = inqLocAddNo;
	}
	public String getInqRegion() {
		return inqRegion;
	}
	public void setInqRegion(String inqRegion) {
		this.inqRegion = inqRegion;
	}
	public String getInqWhseNo() {
		return inqWhseNo;
	}
	public void setInqWhseNo(String inqWhseNo) {
		this.inqWhseNo = inqWhseNo;
	}
	public String getInqDock() {
		return inqDock;
	}
	public void setInqDock(String inqDock) {
		this.inqDock = inqDock;
	}
	public String getInqFacility() {
		return inqFacility;
	}
	public void setInqFacility(String inqFacility) {
		this.inqFacility = inqFacility;
	}
	public String getInqRecLoc() {
		return inqRecLoc;
	}
	public void setInqRecLoc(String inqRecLoc) {
		this.inqRecLoc = inqRecLoc;
	}
	public String getInqScheduledLoadNumber() {
		return inqScheduledLoadNumber;
	}
	public void setInqScheduledLoadNumber(String inqScheduledLoadNumber) {
		this.inqScheduledLoadNumber = inqScheduledLoadNumber;
	}
	public Vector getDualDropDownReceivingLocation() {
		return dualDropDownReceivingLocation;
	}
	public void setDualDropDownReceivingLocation(
			Vector dualDropDownReceivingLocation) {
		this.dualDropDownReceivingLocation = dualDropDownReceivingLocation;
	}
	public String getSingleDropDownFacility() {
		return singleDropDownFacility;
	}
	public void setSingleDropDownFacility(String singleDropDownFacility) {
		this.singleDropDownFacility = singleDropDownFacility;
	}
	public String getWhseNo() {
		return whseNo;
	}
	public void setWhseNo(String whseNo) {
		this.whseNo = whseNo;
	}
	public String getInqShowComments() {
		return inqShowComments;
	}
	public void setInqShowComments(String inqShowComments) {
		this.inqShowComments = inqShowComments;
	}
	/**
	 *  This method will retrive a list of comments associated
	 *    to the load it is referring to.
	 *    
	 *  This will be used on the List Page, IF showComments is marked yes
	 *  
	 * @return Vector
	 */
	public Vector retrieveCommentsForLoad(String loadNumber) {
		Vector returnComments = new Vector();
		try
		{
		   KeyValue kv = new KeyValue();
		   kv.setEntryType("ScheduledLoadComment");
		   kv.setEnvironment(this.getEnvironment());
		   kv.setKey1(loadNumber.trim());
		   returnComments = ServiceKeyValue.buildKeyValueList(kv);
		}catch(Exception e){
			returnComments = new Vector();
		}
		return returnComments;
	}
	public String getInqTransfer() {
		return inqTransfer;
	}
	public void setInqTransfer(String inqTransfer) {
		this.inqTransfer = inqTransfer;
	}
	public String getSingleDropDownTransfer() {
		return singleDropDownTransfer;
	}
	public void setSingleDropDownTransfer(String singleDropDownTransfer) {
		this.singleDropDownTransfer = singleDropDownTransfer;
	}
	public String getInqShipDock() {
		return inqShipDock;
	}
	public void setInqShipDock(String inqShipDock) {
		this.inqShipDock = inqShipDock;
	}
	public String getInqShipFacility() {
		return inqShipFacility;
	}
	public void setInqShipFacility(String inqShipFacility) {
		this.inqShipFacility = inqShipFacility;
	}
	public String getInqShipLoc() {
		return inqShipLoc;
	}
	public void setInqShipLoc(String inqShipLoc) {
		this.inqShipLoc = inqShipLoc;
	}
	public Vector getDualDropDownShippingLocation() {
		return dualDropDownShippingLocation;
	}
	public void setDualDropDownShippingLocation(Vector dualDropDownShippingLocation) {
		this.dualDropDownShippingLocation = dualDropDownShippingLocation;
	}
	public String getSingleDropDownShippingFacility() {
		return singleDropDownShippingFacility;
	}
	public void setSingleDropDownShippingFacility(
			String singleDropDownShippingFacility) {
		this.singleDropDownShippingFacility = singleDropDownShippingFacility;
	}
	public String getInqOrderBySupplier() {
		return inqOrderBySupplier;
	}
	public void setInqOrderBySupplier(String inqOrderBySupplier) {
		this.inqOrderBySupplier = inqOrderBySupplier;
	}
	public String getInqCashFruit() {
		return inqCashFruit;
	}
	public void setInqCashFruit(String inqCashFruit) {
		this.inqCashFruit = inqCashFruit;
	}
	public String getInqBulkOnly() {
		return inqBulkOnly;
	}
	public void setInqBulkOnly(String inqBulkOnly) {
		this.inqBulkOnly = inqBulkOnly;
	}
}