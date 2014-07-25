/*
 * Created on September 2, 2010
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
import com.treetop.data.UserFile;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.services.ServiceUser;
import com.treetop.services.ServiceWarehouse;

/**
 *   Search for Scheduled Raw Fruit Load Information
 * @author twalto
 *
 */
public class InqAvailableFruit extends BaseViewBeanR1  {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   				= "";
	public String displayMessage 				= "";
	public String environment 					= "";
	public String displayView					= "";

// Standard Fields for Inq View Bean
	public String orderBy    	    			= "crop";
	public String orderStyle 	    			= "";	
	public String startRecord					= "";
	
	public String allowUpdate					= "N";
	
// Fields to be Used to Filter Data by User 	
	public String userProfile					= "";
	public String[] roles						= new String[0];
	
//	 Fields to be Used for Search INQ to LIST		
	public String inqCompanyNumber 				= "100";
	public String inqDivisionNumber 			= "100";
	public String inqRegion						= ""; // North South
	public String inqWhseNo						= ""; // Warehouse Number
	public String inqWhseNo2					= ""; // Triple Drop Down
	public String inqLocAddNo					= ""; // Location Address number
	public String inqCrop						= "";
	public String inqVariety					= "";
	public String inqGrade						= "";
	public String inqOrganic					= "";
	public String inqStickerFree				= "";
	public String inqOrchardRun					= "";
	public String inqFieldRepresentative		= "";
	public String inqFruitAvailToPurchase		= "N";
	
	public Vector tripleDropDownRegion			= new Vector();
	public String singleDropDownLocation		= "";
	public String whseNo						= "";
	public String whseDescription				= "";
	public Vector dualDropDownCropVariety		= new Vector();
	public String singleDropDownGrade			= "";
	public String singleDropDownOrganic			= "";
	public String singleFieldRepresentative		= "";
	
// Return information to the Screen	
	public BeanAvailFruit beanAvailFruit 		= null;
	public Vector listReport 					= null;	

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
		if (this.getEnvironment() == null ||
			this.getEnvironment().trim().equals(""))
			this.setEnvironment("PRD");
		
		if (this.getInqStickerFree() == null)
		  this.setInqStickerFree("");
		if (this.getInqStickerFree().trim().equals("on"))
		  this.setInqStickerFree("Y");  
		
		if (this.getInqRegion() == null ||
			this.getInqRegion().trim().equals("Make a selection"))
		   this.setInqRegion("");
		
		if ((this.getInqWhseNo2() != null &
			!this.getInqWhseNo2().trim().equals("Make a selection")) &&
			!this.getInqWhseNo2().trim().equals(""))
		   this.setInqWhseNo(this.getInqWhseNo2());
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
		
		if (!this.environment.equals(""))
		{// Environment
			returnString.append("&environment=");
			returnString.append(this.environment);
		}
		if (!this.inqRegion.equals(""))
		{ // North South Designation
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
		if (!this.inqCrop.equals(""))
		{// Crop
			returnString.append("&inqCrop=");
			returnString.append(this.inqCrop);
		}
		if (!this.inqVariety.equals(""))
		{// Variety
			returnString.append("&inqVariety=");
			returnString.append(this.inqVariety);
		} 
		if (!this.inqGrade.equals(""))
		{// Grade
			returnString.append("&inqGrade=");
			returnString.append(this.inqGrade);
		}
		if (!this.inqOrganic.equals(""))
		{// Organic
			returnString.append("&inqOrganic=");
			returnString.append(this.inqOrganic);
		} 
		if (!this.inqFieldRepresentative.equals(""))
		{// Organic
			returnString.append("&inqFieldRepresentative=");
			returnString.append(this.inqFieldRepresentative);
		} 
		if (!this.inqStickerFree.equals(""))
		{// Sticker Free Option
			returnString.append("&inqStickerFree=");
			returnString.append(this.inqStickerFree);
		} 
		if (this.inqFruitAvailToPurchase.equals("Y"))
		{// Fruit Available to Purchase
			returnString.append("&inqFruitAvailToPurchase=Y");
		} 
		return returnString.toString();
	}
	/* Use this method to Display -- however appropriate for
	 *    the JSP the Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();		
		
		if (!this.inqRegion.equals(""))
		{// Region
			returnString.append("Region=");
			returnString.append(this.inqRegion + "<br>");
		}
		if (!this.inqWhseNo.equals(""))
		{// Warehouse Number - Vendor of Raw Fruit Warehouses
			returnString.append("Warehouse Number=");
			returnString.append(this.inqWhseNo + "<br>");
		}
		if (!this.inqLocAddNo.equals(""))
		{// Address Number associated to the Vendor (Raw Fruit Warehouse)
			returnString.append("Warehouse Location=");
			returnString.append(this.inqLocAddNo + "<br>");
		}
		if (!this.inqCrop.equals(""))
		{// Crop 
			returnString.append("Crop=");
			returnString.append(this.inqCrop + "<br>");
		}
		if (!this.inqVariety.equals(""))
		{// Variety
			returnString.append("Variety=");
			returnString.append(this.inqVariety + "<br>");
		}
		if (!this.inqGrade.equals(""))
		{// Grade
			returnString.append("Grade=");
			returnString.append(this.inqGrade + "<br>");
		}
		if (!this.inqOrganic.equals(""))
		{// Organic / Conventional / BabyFood
			returnString.append("Organic=");
			returnString.append(this.inqOrganic + "<br>");
		}
		if (!this.inqFieldRepresentative.equals(""))
		{
			returnString.append("Field Representative=");
			returnString.append(this.inqFieldRepresentative + "<br>");
		}
		if (!this.inqStickerFree.equals(""))
		{
			returnString.append("Sticker Free = ");
			returnString.append(this.inqStickerFree + "<br>");
		}
		if (this.inqFruitAvailToPurchase.equals("Y"))
		{
			returnString.append("Fruit Available to Purchase = Y");
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

	public void setInqCompanyNumber(String inqCompanyNumber) {
		this.inqCompanyNumber = inqCompanyNumber;
	}

	public void setInqDivisionNumber(String inqDivisionNumber) {
		this.inqDivisionNumber = inqDivisionNumber;
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
	public void setInqCrop(String inqCrop) {
		this.inqCrop = inqCrop;
	}
	public void setInqGrade(String inqGrade) {
		this.inqGrade = inqGrade;
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
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public String getWhseDescription() {
		return whseDescription;
	}
	public void setWhseDescription(String whseDescription) {
		this.whseDescription = whseDescription;
	}
	/**
	 *    Will Generate a Vector of Data to be used on the screen (JSP)
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
			// roll throught the vector to see if it is ONE warehouse
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
//				String masterName = "whseNumber";
//				String slaveName = "whseAddressNumber";
//				String slaveAll = "N";
			    String list1 = "region";
			    String list2 = "whseNo2";
			    String list3 = "locAddNo";
			    String formName = "updAvail";
				if (this.getRequestType().trim().equals("inqAvailFruit") ||
					this.getRequestType().trim().equals("inqAvailFruitAll"))
				{
//					masterName = "inqWhseNumber";
//					slaveName = "inqWhseAddressNumber";
//					slaveAll = "";
					list1 = "inqRegion";
					list2 = "inqWhseNo2";
					list3 = "inqLocAddNo";
					formName = "inqAvail";
				}
				//Vector returnData = DropDownDual.buildDualDropDownNew(this.getListReport(), masterName, "", "", slaveName, slaveAll, "", "N", "N");
				Vector returnData1 = DropDownTriple.buildTripleDropDown(this.getListReport(), list1, "", "", list2, "", "E", list3, "", "E");
				Vector oneElement1 = new Vector();
				oneElement1.addElement((String) returnData1.elementAt(0));
				oneElement1.addElement(JavascriptInfo.getTripleDropDown(formName, list1, list2, list3) );
				oneElement1.addElement((String) returnData1.elementAt(1));
				oneElement1.addElement((String) returnData1.elementAt(2));
				oneElement1.addElement((String) returnData1.elementAt(3));
				this.setTripleDropDownRegion(oneElement1);
				
//				Vector oneElement = new Vector();
//				oneElement.addElement((String) returnData.elementAt(0));
//				oneElement.addElement("<select name=\"" + slaveName + "\">");
//				oneElement.addElement((String) returnData.elementAt(1));
//				oneElement.addElement(JavascriptInfo.getDualDropDownNew(masterName, slaveName));
//				this.setDualDropDownWarehouse(oneElement);  
			}
			if (this.getRequestType().trim().equals("inqAvailFruit") ||
				this.getRequestType().trim().equals("inqAvailFruitAll"))
			{
				try
				{
					CommonRequestBean crb = new CommonRequestBean();
					crb.setEnvironment(this.getEnvironment());
					crb.setCompanyNumber(this.getInqCompanyNumber());
					crb.setDivisionNumber(this.getInqDivisionNumber());
					Vector dddCropVariety = ServiceAvailableFruit.dropDownVariety(crb);
					Vector ddGrade = ServiceAvailableFruit.dropDownGrade(crb);
					Vector ddOrganic = ServiceAvailableFruit.dropDownOrganic(crb);
					Vector ddFieldRep = ServiceAvailableFruit.dropDownFieldRep(crb);
					String masterName = "inqCrop";
					String slaveName = "inqVariety";
					Vector returnData = DropDownDual.buildDualDropDownNew(dddCropVariety, masterName, "", "", slaveName, "", "", "N", "N");
					Vector oneElement = new Vector();
					oneElement.addElement((String) returnData.elementAt(0));
					oneElement.addElement("<select name=\"" + slaveName + "\">");
					oneElement.addElement((String) returnData.elementAt(1));
					oneElement.addElement(JavascriptInfo.getDualDropDownNew(masterName, slaveName));
					this.setDualDropDownCropVariety(oneElement);  
					this.setSingleDropDownGrade(DropDownSingle.buildDropDown(ddGrade, "inqGrade", this.inqGrade, "", "N", "N"));
					this.setSingleDropDownOrganic(DropDownSingle.buildDropDown(ddOrganic, "inqOrganic", this.inqOrganic, "", "N", "N"));
					this.setSingleFieldRepresentative(DropDownSingle.buildDropDown(ddFieldRep, "inqFieldRepresentative", this.inqFieldRepresentative, "", "N", "N"));
					
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
	public String getSingleDropDownLocation() {
		return singleDropDownLocation;
	}
	public void setSingleDropDownLocation(String singleDropDownLocation) {
		this.singleDropDownLocation = singleDropDownLocation;
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
	public BeanAvailFruit getBeanAvailFruit() {
		return beanAvailFruit;
	}
	public void setBeanAvailFruit(BeanAvailFruit beanAvailFruit) {
		this.beanAvailFruit = beanAvailFruit;
	}
	/**
	 *  This method will return a Whole name for the screen instead of the Profile
	 *    -- specific for this Entire Application ---
	 *    // Will be used by ALL screens in this Application
	 * @return
	 */
	public static String longUser(String environment, String formatUser) {
	    try{
	    	UserFile uf = new UserFile(formatUser);
			if (!uf.getUserNameLong().trim().equals(""))
			  formatUser = uf.getUserNameLong().trim();
			else
			  formatUser = ServiceUser.returnNameFromM3User(environment, formatUser);
	    }catch(Exception e)
	    {
	    	//Will return the inDate if an exception is caught
	    }
		return formatUser;
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
		   }  	
	    }catch(Exception e)
	    {
	    	//Will return the inDate if an exception is caught
	    }
		return inTime;
	}
	public String getInqFieldRepresentative() {
		return inqFieldRepresentative;
	}
	public void setInqFieldRepresentative(String inqFieldRepresentative) {
		this.inqFieldRepresentative = inqFieldRepresentative;
	}
	public String getInqCompanyNumber() {
		return inqCompanyNumber;
	}
	public String getInqCrop() {
		return inqCrop;
	}
	public String getInqDivisionNumber() {
		return inqDivisionNumber;
	}
	public String getInqGrade() {
		return inqGrade;
	}
	public String getInqOrganic() {
		return inqOrganic;
	}
	public String getSingleFieldRepresentative() {
		return singleFieldRepresentative;
	}
	public void setSingleFieldRepresentative(String singleFieldRepresentative) {
		this.singleFieldRepresentative = singleFieldRepresentative;
	}
	public String getDisplayView() {
		return displayView;
	}
	public void setDisplayView(String displayView) {
		this.displayView = displayView;
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
	public String getInqRegion() {
		return inqRegion;
	}
	public void setInqRegion(String inqRegion) {
		this.inqRegion = inqRegion;
	}
	public Vector getTripleDropDownRegion() {
		return tripleDropDownRegion;
	}
	public void setTripleDropDownRegion(Vector tripleDropDownRegion) {
		this.tripleDropDownRegion = tripleDropDownRegion;
	}
	public String getInqLocAddNo() {
		return inqLocAddNo;
	}
	public void setInqLocAddNo(String inqLocAddNo) {
		this.inqLocAddNo = inqLocAddNo;
	}
	public String getInqWhseNo() {
		return inqWhseNo;
	}
	public void setInqWhseNo(String inqWhseNo) {
		this.inqWhseNo = inqWhseNo;
	}
	public String getWhseNo() {
		return whseNo;
	}
	public void setWhseNo(String whseNo) {
		this.whseNo = whseNo;
	}
	
	public String getInqWhseNo2() {
		return inqWhseNo2;
	}
	public void setInqWhseNo2(String inqWhseNo2) {
		this.inqWhseNo2 = inqWhseNo2;
	}
	public String getInqFruitAvailToPurchase() {
		return inqFruitAvailToPurchase;
	}
	public void setInqFruitAvailToPurchase(String inqFruitAvailToPurchase) {
		this.inqFruitAvailToPurchase = inqFruitAvailToPurchase;
	}
		
}