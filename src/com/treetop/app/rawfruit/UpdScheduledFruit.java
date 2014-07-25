/*
 * Created on August 6, 2010
 */

package com.treetop.app.rawfruit;

import java.util.*;

import com.treetop.SessionVariables;
import com.treetop.viewbeans.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.businessobjects.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.DropDownTriple;
import com.treetop.utilities.html.JavascriptInfo;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.services.ServiceKeyValue;
import com.treetop.services.ServiceDistributionOrder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author twalton
 * 
 * Use as the Available Fruit Header... all information will filter through here
 */
public class UpdScheduledFruit extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   				= "";
	public String displayMessage 				= "";
	public String environment 					= "TST";
	public String addNewLoad					= "N";

	// Must have in Update View Bean
	// Last Updated by Date/Time/User
	public String updateUser 					= "";
	public String updateDate 					= "";
	public String updateTime 					= "";

	public String countDetail					= "0";
	public String countLoads					= "10"; // Change in the Validate when necessary
	public String countLinesPerLoad				= "5";
	public String loadCountNumber				= ""; // will use for loading records when necessary
	
	public String companyNumber 				= "100";
	public String divisionNumber 				= "100";
	public String loadNumber					= "";
	// Schedule Created By Date/Time/User
	public String createDate					= "0";
	public String createDateDisplay				= "";
	public String createTime					= "0";
	public String createTimeDisplay				= "";
	public String createUser					= "";
	public String createUserDisplay				= "";
	// Scheduled Date/Time
	public String scheduledDeliveryDate			= "";
	public DateTime schedDeliveryDate			= new DateTime();
	public String scheduledDeliveryTime			= "0";
	public String scheduledDeliveryTimeError	= "";
	public String hourScheduledDeliveryTime		= "";
	public String minScheduledDeliveryTime		= "";
	// Actual receiving Date/Time
	public String actualReceivingDate			= "0";
	public DateTime actReceivingDate			= new DateTime();
	public String actualReceivingTime			= "0";
	public String actualReceivingTimeError		= "";
	public String hourActualReceivingTime		= "";
	public String minActualReceivingTime		= "";
	public String actualReceivingUser			= "";
	
	public String recLoc						= "";
	public String dock							= "";
	public String haulingCompany	    		= "";
	public String haulerVerification			= "";
	public String truckType						= "";
	public String loadOrchardRun				= "";
	public String comments						= "";
	public String loadReceivedFlag          	= "";
	public String distributionOrder				= "";
	public String distributionOrderError		= "";
	public String transferLoadFlag				= "";
	public String updating						= "";
	
//	Button Values
	public String saveButton 					= "";
	
	//Security
	public String readOnly						= "";
	
	// Drop Down Vectors - for use on the Screen
	public Vector dddReceivingLocation			= new Vector(); // Dual Drop Down (ReceivingLocation (M3 Warehouse) and Dock
	public Vector dddReceivingLocationScreen	= new Vector(); // for display on the screen
	public Vector dddWhseLocation				= new Vector(); // Dual Drop Down (M3 Supplier - RF Warehouse)
	public Vector dddWhseLocationScreen			= new Vector(); // for display on the Screen
	public Vector dddCropVariety				= new Vector(); // Dual Drop Down
	public Vector dddCropVarietyScreen			= new Vector(); // for display on the Screen
	
	public Vector ddHauler						= new Vector();
	public Vector ddLoadType					= new Vector();
	public Vector ddGrade						= new Vector();
	public Vector ddOrganic						= new Vector();
	
	// Fields not updatable used for calculation on the screen
	public InqScheduledFruit inqSched	   		= new InqScheduledFruit();
	public InqAvailableFruit inqAvail			= new InqAvailableFruit();
	public Vector listScheduledFruitDetail 		= new Vector();
	public BeanAvailFruit beanAvailFruit 		= new BeanAvailFruit();
	public Vector listLoads						= new Vector(); // Vector of UpdScheduledFruit (one object for each LOAD)
	public Vector listComments					= new Vector();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
			if (this.environment.trim().equals(""))
			  this.setEnvironment("PRD");
			
			if (this.requestType.trim().equals("schedTransfer"))
			{
				this.setCountLoads("5");
				this.setTransferLoadFlag("Y");
			}
			// Validate the fields... null must be tested - because some are loaded from the populateLoads method
			
			//*****************************************************************
			// Scheduled Delivery Date ****************************************
			 if (this.getScheduledDeliveryDate() == null ||
			     this.getScheduledDeliveryDate().trim().equals(""))
			 {
				 this.setScheduledDeliveryDate("0");
				 if (this.getRequestType().trim().equals("addSchedFruitLoad") ||
					 this.getRequestType().trim().equals("schedTransfer"))
				 {
					DateTime nowDate = UtilityDateTime.getSystemDate();
					this.setSchedDeliveryDate(nowDate);
					this.setScheduledDeliveryDate(nowDate.getDateFormatMMddyyyySlash());
				 }
			 }
			 else
			 {
				 this.setSchedDeliveryDate(UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getScheduledDeliveryDate()));
			 }
			 if (!this.getSchedDeliveryDate().getDateErrorMessage().trim().equals(""))
				 this.setDisplayMessage(this.getDisplayMessage() + this.getSchedDeliveryDate().getDateErrorMessage().trim() + "<br>");
			//***************************************************************** 
			// Scheduled Delivery Time ****************************************
			 StringBuffer timeError = new StringBuffer();
			//--------------------------------------------------------- 
			//  Review/Validate the Hour section of the time
			 if (this.getHourScheduledDeliveryTime() == null ||
				 this.getHourScheduledDeliveryTime().trim().equals(""))
				this.setHourScheduledDeliveryTime("0");
			 else
			 {
			   try{
			   	  timeError.append(validateInteger(this.getHourScheduledDeliveryTime()));
			      if (timeError.toString().trim().equals(""))
			      {
			    	 int hourTime = new Integer(this.getHourScheduledDeliveryTime()).intValue();
			    	 if (hourTime > 24)
			    		timeError.append("Hour Value cannot be greater than 24. ");
			    	}
			    }catch(Exception e)
			    {}
				if (timeError.toString().trim().equals("") &&
					this.getHourScheduledDeliveryTime().length() == 1)
			       this.setHourScheduledDeliveryTime("0" + this.getHourScheduledDeliveryTime().trim());
			 } // end of the else -- for the Hour on the Delivery Time
			//--------------------------------------------------------- 
			//  Review/Validate the Minute section of the time
		    StringBuffer minError = new StringBuffer();
			if (this.getMinScheduledDeliveryTime() == null ||
				this.getMinScheduledDeliveryTime().trim().equals(""))
			   this.setMinScheduledDeliveryTime("0");
			else
		    {
		    	try{
		    		minError.append(validateInteger(this.getMinScheduledDeliveryTime()));
		    		if (minError.toString().trim().equals(""))
		    		{
		    			int minTime = new Integer(this.getMinScheduledDeliveryTime()).intValue();
		    			if (minTime > 59)
		    				minError.append("Hour Value cannot be greater than 59. ");
		    		}
		    	}catch(Exception e)
		    	{}
			    if (minError.toString().trim().equals("") &&
			    	this.getMinScheduledDeliveryTime().length() == 1)
			       this.setMinScheduledDeliveryTime("0" + this.getMinScheduledDeliveryTime().trim());
		    } 
			timeError.append(minError.toString().trim());
			// Delivery Time **************************************************
		    if (timeError.toString().trim().equals(""))
		    	this.setScheduledDeliveryTime(this.getHourScheduledDeliveryTime() + this.getMinScheduledDeliveryTime());
		    else
		    	this.setScheduledDeliveryTimeError(timeError.toString().trim());
		    if (!this.getScheduledDeliveryTimeError().trim().equals(""))
		    	this.setDisplayMessage(this.getDisplayMessage() + this.getScheduledDeliveryTimeError().trim() + "<br>");
		    //***************************************************************** 
			// Hauling Company ************************************************ 
		    if (this.getHaulingCompany() == null)
			  this.setHaulingCompany("");
		    //***************************************************************** 
			// Load Type (Truck Type) *****************************************  
			if (this.getTruckType() == null)
			   this.setTruckType(""); 
			//***************************************************************** 
			// Receiving Location *********************************************
			if (this.getRecLoc() == null || this.getRecLoc().trim().equals("*all"))
			  this.setRecLoc("");
			//***************************************************************** 
			// Dock tied to the Receiving Location ****************************
			if (this.getDock() == null || this.getDock().trim().equals("*all"))
			  this.setDock("");
			//*****************************************************************
			// Distribution Order *********************************************
			if (this.getDistributionOrder() == null)
			  this.setDistributionOrder("");
			if (!this.getDistributionOrder().trim().equals(""))
				this.setDistributionOrderError(ServiceDistributionOrder.verifyDistributionOrder(this.getEnvironment(), this.getDistributionOrder()));
			if (!this.getDistributionOrderError().trim().equals(""))
				this.setDisplayMessage(this.getDisplayMessage() + this.getDistributionOrderError().trim() + "<br>");
			//*****************************************************************
			// Transfer Load Flag *********************************************
			if (this.getTransferLoadFlag() == null)
			  this.setTransferLoadFlag("");
			//*****************************************************************
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/*
	 * Populate the Fields - Then run the Validate within the Detail view bean
	 *    over each row of information, after loading them, and testing for null's
	 *  Set Errors into the Error Fields of the update Detail View Bean
	 */
	public void populateDetails(HttpServletRequest request) {
		try{	
		  //***********************************************************************  
		  // Scheduled Available Fruit
		  //  Build the List of Loads for the screen  
		  if (this.requestType.trim().equals("schedAvailFruit"))
		  { // Load up the initial screen detail
			Vector listDetail = new Vector();
			StringBuffer foundProblem = new StringBuffer();
			// Retrieve Available Fruit Detail information 
			DateTime dtToday = UtilityDateTime.getSystemDate();
			this.setScheduledDeliveryDate(dtToday.getDateFormatMMddyyyySlash());
			int countRecords =  new Integer(this.getCountDetail()).intValue();
			if (countRecords > 0){
//				String puDate = "";
//				String puTime = "";
//				String puHour = "";
//				String puMin  = "";
				for (int x = 1; x < (countRecords + 1); x++){
				  UpdScheduledFruitDetail updDtl = new UpdScheduledFruitDetail();
				  updDtl.setChooseRow(request.getParameter("chooseRow" + x));
				  if (updDtl.getChooseRow() != null && !updDtl.getChooseRow().trim().equals(""))
				  {
					updDtl.setEnvironment(this.getEnvironment());
					updDtl.setCompanyNumber(this.getCompanyNumber());
					updDtl.setDivisionNumber(this.getDivisionNumber());
					
				   	//-------------------------------------------------------------------
					// RETRIEVE from the Checked rows
					//-------------------------------------------------------------------
					// Warehouse -- Actually an M3 Supplier **********************
					updDtl.setWhseNo(request.getParameter("whseNo" + x));
					if (updDtl.getWhseNo() == null)
						updDtl.setWhseNo("");
					// Warehouse Description ********************************************
				    updDtl.setWhseName(request.getParameter("whseName" + x));
					if (updDtl.getWhseName() == null)
					  updDtl.setWhseName("");
					// Warehouse Address number *****************************************
					updDtl.setLocAddNo(request.getParameter("locAddNo" + x));
				    if (updDtl.getLocAddNo() == null)
					  updDtl.setLocAddNo("");
					// Warehouse Address Number Description ******************************
					updDtl.setWhseAddressName(request.getParameter("whseAddressName" + x));
				    if (updDtl.getWhseAddressName() == null)
					  updDtl.setWhseAddressName("");
				    // From Tree Top Location - for a Transfer Load 6/13/11 tw ***********
				    updDtl.setFromRecLoc(request.getParameter("fromRecLoc" + x));
				    if (updDtl.getFromRecLoc() == null)
				       updDtl.setFromRecLoc("");
				    // From Tree Top Dock - for a Transfer Load 6/13/11 tw ***************
				    updDtl.setFromDock(request.getParameter("fromDock" + x));
				    if (updDtl.getFromDock() == null)
				       updDtl.setFromDock("");
				    
				    // 5/10/11 TW -- take out the Pickup Date and Time
//				    //Pick Up Date ******************************************************
//					updDtl.setScheduledPickUpDate(request.getParameter("scheduledPickUpDate" + x));
//					if (updDtl.getScheduledPickUpDate() == null)
//					   updDtl.setScheduledPickUpDate("");
//					if (updDtl.getScheduledPickUpDate().trim().equals("") && !puDate.equals("") && !puDate.equals("0"))
//					{
//						updDtl.setScheduledPickUpDate(puDate);
//						puDate = "0";
//					}
					// Pick Up Time ******************************************************
//					updDtl.setScheduledPickUpTimeHour(request.getParameter("hourscheduledPickUpTime" + x));
//					if (updDtl.getScheduledPickUpTimeHour() == null)
//					  updDtl.setScheduledPickUpTimeHour("");
//					updDtl.setScheduledPickUpTimeMinute(request.getParameter("minscheduledPickUpTime" + x));
//					if (updDtl.getScheduledPickUpTimeMinute() == null)
//					  updDtl.setScheduledPickUpTimeMinute("");
				// Decide if the date and time from the previous record should be included in this record.	
//					if (updDtl.getScheduledPickUpDate().trim().equals("") && !puDate.equals("") && !puDate.equals("0"))
//					{
//						updDtl.setScheduledPickUpDate(puDate);
//						puDate = "0";
//					}
//					if (updDtl.getScheduledPickUpTimeHour().trim().equals("") && !puTime.equals("") && !puDate.equals("0"))
//					{
//						updDtl.setScheduledPickUpTimeHour(puHour);
//						updDtl.setScheduledPickUpTimeMinute(puMin);
//						puDate = "0";
//					}
//					if (puDate.trim().equals(""))
//					{
//					   puDate = updDtl.getScheduledPickUpDate();
//					   puTime = updDtl.getScheduledPickUpTimeHour() + updDtl.getScheduledPickUpTimeMinute();
//					   puHour = updDtl.getScheduledPickUpTimeHour();
//					   puMin  = updDtl.getScheduledPickUpTimeMinute();
//					}
//					if (updDtl.getScheduledPickUpDate().trim().equals(""))
//					  updDtl.setScheduledPickUpDate(this.getScheduledDeliveryDate());
				    // Crop ************************************************************
					updDtl.setCrop(request.getParameter("crop" + x));
					if (updDtl.getCrop() == null || updDtl.getCrop().trim().equals("*all"))
						updDtl.setCrop("");
					// Crop Description ************************************************
					updDtl.setCropDescription(request.getParameter("cropDescription" + x));
					if (updDtl.getCropDescription() == null)
						updDtl.setCropDescription("");
					// Variety ********************************************************
				    updDtl.setVariety(request.getParameter("variety" + x));
					if (updDtl.getVariety() == null || updDtl.getVariety().trim().equals("*all"))
					  updDtl.setVariety("");
					// Variety Description*********************************************
				    updDtl.setVarietyDescription(request.getParameter("varietyDescription" + x));
					if (updDtl.getVarietyDescription() == null)
					  updDtl.setVarietyDescription("");
					// Grade **********************************************************
					updDtl.setGrade(request.getParameter("grade" + x));
				    if (updDtl.getGrade() == null || updDtl.getGrade().trim().equals("*all"))
					  updDtl.setGrade("");
				    // Grade Desciprtion **********************************************
					updDtl.setGradeDescription(request.getParameter("gradeDescription" + x));
				    if (updDtl.getGradeDescription() == null)
					  updDtl.setGradeDescription("");
					// Organic, Conventional, BabyFood? *******************************
					updDtl.setOrganic(request.getParameter("organic" + x));
				    if (updDtl.getOrganic() == null || updDtl.getOrganic().trim().equals("*all"))
					  updDtl.setOrganic("");
				    // Organic, Conventional, BabyFood? Description ********************
					updDtl.setOrganicDescription(request.getParameter("organicDescription" + x));
				    if (updDtl.getOrganicDescription() == null)
					  updDtl.setOrganicDescription("");
				    // Sticker Free Fruit **********************************************
					updDtl.setStickerFree(request.getParameter("stickerFree" + x));
				    if (updDtl.getStickerFree() == null)
					  updDtl.setStickerFree("");
					// Available Bin Quantity *******************************************
					updDtl.setAvailableBins(request.getParameter("availableBins" + x));
				    if (updDtl.getAvailableBins() == null || updDtl.getAvailableBins().trim().equals(""))
					  updDtl.setAvailableBins("0");
				    // Scheduled Bin Quantity *******************************************
					updDtl.setScheduledBins(request.getParameter("scheduledBins" + x));
				    if (updDtl.getScheduledBins() == null || updDtl.getScheduledBins().trim().equals(""))
					  updDtl.setScheduledBins("0");
				    // Balance Bin Quantity *******************************************
					updDtl.setBalanceBins(request.getParameter("balanceBins" + x));
				    if (updDtl.getBalanceBins() == null || updDtl.getBalanceBins().trim().equals(""))
					  updDtl.setBalanceBins("0");
				  
				   //-------------------------------------------------------------------
				    updDtl.validate();
				    this.setDisplayMessage(this.getDisplayMessage().trim() + updDtl.getDisplayMessage());
				   //------------------------------------------------------------------- 
				  	listDetail.addElement(updDtl);
				  } // only if the ChooseRow box has been checked	  
				} // end of the For Loop
				// when first going into the schedule page..
				if (listDetail.size() == 0)
					this.setDisplayMessage("Please Choose Fruit To Schedule before Clicking the Go To Schedule Loads");
				Vector listByLoad = new Vector();
				for (int x = 0; x < (new Integer(this.countLoads)).intValue(); x++)
				{
					listByLoad.addElement(listDetail);
				}
				this.setListScheduledFruitDetail(listByLoad);
				this.setDisplayMessage(this.displayMessage + foundProblem.toString().trim());
			} // end if the if count > 0
		  } // end of the Schedule Available Fruit Load of information
     //******************************************************************************************
     //******************************************************************************************
		  if (this.requestType.trim().equals("addSchedFruit")||
			  this.requestType.trim().equals("schedTransfer") ||
			  this.requestType.trim().equals("updSchedTransfer"))
		  {
			  StringBuffer foundProblem = new StringBuffer();
			  int cntDetail = 0;
			  try{
			     cntDetail = (new Integer(this.getCountDetail())).intValue();
			  }catch(Exception e)
			  {}
			  Vector details = new Vector();
			  if (cntDetail > 0)
			  {
//				  String puDate = "";
//				  String puTime = "";
//				  String puHour = "";
//				  String puMin  = "";
			  	 for (int lineNumber = 0; lineNumber < cntDetail; lineNumber++)
			   	 {
			  		UpdScheduledFruitDetail rowDtl = new UpdScheduledFruitDetail();
		    		rowDtl.setEnvironment(this.getEnvironment());
					rowDtl.setCompanyNumber(this.getCompanyNumber());
					rowDtl.setDivisionNumber(this.getDivisionNumber());
					
					//-------------------------------------------------------------------
					// RETRIEVE based on the information that was checked
					//-------------------------------------------------------------------
					// Warehouse -- Actually an M3 Supplier **********************
					rowDtl.setWhseNo(request.getParameter("whseNo" + this.getLoadCountNumber() + lineNumber));
					// Warehouse Description ********************************************
				    rowDtl.setWhseName(request.getParameter("whseName" + this.getLoadCountNumber() + lineNumber));
					// Warehouse Address number *****************************************
					rowDtl.setLocAddNo(request.getParameter("locAddNo" + this.getLoadCountNumber() + lineNumber));
					// Warehouse Address Number Description ******************************
					rowDtl.setWhseAddressName(request.getParameter("whseAddressName" + this.getLoadCountNumber() + lineNumber));
				    // From Tree Top Location - for a Transfer Load 6/13/11 tw ***********
				    rowDtl.setFromRecLoc(request.getParameter("fromRecLoc" + this.getLoadCountNumber() + lineNumber));
				    // From Tree Top Location Description- for a Transfer Load 7/26/11 tw ***********
				    rowDtl.setFromRecLocDescription(request.getParameter("fromRecLocDescription" + this.getLoadCountNumber() + lineNumber));
				    // From Tree Top Dock - for a Transfer Load 6/13/11 tw ***************
				    rowDtl.setFromDock(request.getParameter("fromDock" + this.getLoadCountNumber() + lineNumber));
				    // From Tree Top Dock Description- for a Transfer Load 7/26/11 tw ***************
				    rowDtl.setFromDockDescription(request.getParameter("fromDockDescription" + this.getLoadCountNumber() + lineNumber));
				    // Tree Top Item Number - for a Transfer Load 7/7/11 tw ***************
				    rowDtl.setItemNumber(request.getParameter("itemNumber" + this.getLoadCountNumber() + lineNumber));
					// 5/10/11 TW - not using Pick up Date and Time
					// Pick Up Date ******************************************************
//					rowDtl.setScheduledPickUpDate(request.getParameter("scheduledPickUpDate" + this.getLoadCountNumber() + lineNumber));
//					if (rowDtl.getScheduledPickUpDate() == null)
//					   rowDtl.setScheduledPickUpDate("");
					// Pick Up Time ******************************************************
//					rowDtl.setScheduledPickUpTimeHour(request.getParameter("hourscheduledPickUpTime" + this.getLoadCountNumber() + lineNumber));
//					if (rowDtl.getScheduledPickUpTimeHour() == null)
//					  rowDtl.setScheduledPickUpTimeHour("");
//					rowDtl.setScheduledPickUpTimeMinute(request.getParameter("minscheduledPickUpTime" + this.getLoadCountNumber() + lineNumber));
//					if (rowDtl.getScheduledPickUpTimeMinute() == null)
//					  rowDtl.setScheduledPickUpTimeMinute("");
					// Decide if the date and time from the previous record should be included in this record.	
//					if (rowDtl.getScheduledPickUpDate().trim().equals("") && !puDate.equals(""))
//					{
//					    String saveDate = rowDtl.getScheduledPickUpDate();
//						rowDtl.setScheduledPickUpDate(puDate);
//						puDate = saveDate;
//					}
//					if (rowDtl.getScheduledPickUpTimeHour().trim().equals("") && !puTime.equals(""))
//					{
//						String saveHour = rowDtl.getScheduledPickUpTimeHour();
//						String saveMin  = rowDtl.getScheduledPickUpTimeMinute();
//						rowDtl.setScheduledPickUpTimeHour(puHour);
//						rowDtl.setScheduledPickUpTimeMinute(puMin);
//						puTime = saveHour + saveMin;
//					}
//					puDate = rowDtl.getScheduledPickUpDate();
//					puTime = rowDtl.getScheduledPickUpTimeHour() + rowDtl.getScheduledPickUpTimeMinute();
//					puHour = rowDtl.getScheduledPickUpTimeHour();
//					puMin  = rowDtl.getScheduledPickUpTimeMinute();
					// Crop ************************************************************
					rowDtl.setCrop(request.getParameter("crop" + this.getLoadCountNumber() + lineNumber));
					// Crop Description ************************************************
					rowDtl.setCropDescription(request.getParameter("cropDescription" + this.getLoadCountNumber() + lineNumber));
					// Variety ********************************************************
				    rowDtl.setVariety(request.getParameter("variety" + this.getLoadCountNumber() + lineNumber));
					// Variety Description*********************************************
				    rowDtl.setVarietyDescription(request.getParameter("varietyDescription" + this.getLoadCountNumber() + lineNumber));
					// Grade **************************************************
					rowDtl.setGrade(request.getParameter("grade" + this.getLoadCountNumber() + lineNumber));
				    // Grade Desciprtion ********************************************
					rowDtl.setGradeDescription(request.getParameter("gradeDescription" + this.getLoadCountNumber() + lineNumber));
					// Organic, Conventional, BabyFood? ***********************
					rowDtl.setOrganic(request.getParameter("organic" + this.getLoadCountNumber() + lineNumber));
				    // Organic, Conventional, BabyFood? Description ***********************
					rowDtl.setOrganicDescription(request.getParameter("organicDescription" + this.getLoadCountNumber() + lineNumber));
					// Available Bin Quantity *******************************************
					rowDtl.setAvailableBins(request.getParameter("availableBins" + this.getLoadCountNumber() + lineNumber));
				    // Scheduled Bin Quantity *******************************************
					rowDtl.setScheduledBins(request.getParameter("scheduledBins" + this.getLoadCountNumber() + lineNumber));
				    // Balance Bin Quantity *******************************************
					rowDtl.setBalanceBins(request.getParameter("balanceBins" + this.getLoadCountNumber() + lineNumber));
				    // Orchard Run **************************************************************
				    rowDtl.setOrchardRun(request.getParameter("orchardRun" + this.getLoadCountNumber() + lineNumber));
				    // Sticker Free **************************************************************
				    rowDtl.setStickerFree(request.getParameter("stickerFree" + this.getLoadCountNumber() + lineNumber));
				    // Pool or Cash -- if checked it means it is a Cash Load *************************
				    rowDtl.setMemberCash(request.getParameter("memberCash"  + this.getLoadCountNumber() + lineNumber));
				    // Cash Price ***************************************************************
				    rowDtl.setCashPrice(request.getParameter("cashPrice"  + this.getLoadCountNumber() + lineNumber));
				    // Entered Bin Quantity *******************************************
					rowDtl.setBinQuantity(request.getParameter("binQuantity" + this.getLoadCountNumber() + lineNumber));
					 
					//--------------------------------------------------------------------
					// VALIDATE -- Call the Validate Method in the UpdAvailableFruitDetail object
					//-------------------------------------------------------------------
					    rowDtl.validate();
					    foundProblem.append(rowDtl.getDisplayMessage());
					    
				    // IF Bin Quantity does NOT equal 0
				    if (!rowDtl.getBinQuantity().trim().equals("0") &&
				    	!rowDtl.getBinQuantity().trim().equals("") &&
				    	rowDtl.getBinQuantityError().trim().equals(""))
				    {
				       rowDtl.setUpdateUser(this.getUpdateUser());
					   // System Date and time
				       DateTime dtNow = UtilityDateTime.getSystemDate();
				       rowDtl.setUpdateDate(dtNow.getDateFormatyyyyMMdd());
				       rowDtl.setUpdateTime(dtNow.getTimeFormathhmmss());
				    }
				    details.addElement(rowDtl);
		    	} // end of the For loop through the detail rows
		    } // if there is detail lines... -- must have detail lines		
			this.setListScheduledFruitDetail(details);  
			this.setDisplayMessage(this.getDisplayMessage().trim() + foundProblem.toString().trim());
	  } // end of the addSchedFruit
	  //******************************************************************************************
	  //******************************************************************************************		 
	  if (this.requestType.trim().equals("updSchedFruit"))  
	  {	  // Will be for ONE load at a time.
		  StringBuffer foundProblem = new StringBuffer();
		  int cntDetail = 0;
		  String countDetails = request.getParameter("countDetail");
		  if (countDetails != null && !countDetails.trim().equals(""))
			  cntDetail = (new Integer(countDetails)).intValue();
		  
		    Vector details = new Vector();
		    if (cntDetail > 0)
		    {
		    	for (int lineNumber = 0; lineNumber < cntDetail; lineNumber++)
		    	{
		    		UpdScheduledFruitDetail rowDtl = new UpdScheduledFruitDetail();
		    		rowDtl.setEnvironment(this.getEnvironment());
					rowDtl.setCompanyNumber(this.getCompanyNumber());
					rowDtl.setDivisionNumber(this.getDivisionNumber());
		    		rowDtl.setLoadNumber(this.getLoadNumber());
					//-------------------------------------------------------------------
					// RETRIEVE // detail information
					//-------------------------------------------------------------------
					// Warehouse -- Actually an M3 Supplier **********************
					rowDtl.setWhseNo(request.getParameter("whseNo" + lineNumber));
					// Warehouse Description ********************************************
				    rowDtl.setWhseName(request.getParameter("whseName" + lineNumber));
					// Warehouse Address number *****************************************
					rowDtl.setLocAddNo(request.getParameter("locAddNo" + lineNumber));
					// Warehouse Address Number Description ******************************
					rowDtl.setWhseAddressName(request.getParameter("whseAddressName" + lineNumber));
					// 5/10/11 no longer using pick up date -- TW
//					 Pick Up Date ******************************************************
//					rowDtl.setScheduledPickUpDate(request.getParameter("scheduledPickUpDate" + lineNumber));
//					if (rowDtl.getScheduledPickUpDate() == null)
//					   rowDtl.setScheduledPickUpDate("0");
					// Pick Up Time ******************************************************
//					rowDtl.setScheduledPickUpTimeHour(request.getParameter("hourscheduledPickUpTime" + this.getLoadCountNumber() + lineNumber));
//					if (rowDtl.getScheduledPickUpTimeHour() == null)
//					  rowDtl.setScheduledPickUpTimeHour("");
//					rowDtl.setScheduledPickUpTimeMinute(request.getParameter("minscheduledPickUpTime" + this.getLoadCountNumber() + lineNumber));
//					if (rowDtl.getScheduledPickUpTimeMinute() == null)
//					  rowDtl.setScheduledPickUpTimeMinute("");
//					rowDtl.setScheduledPickUpTime(request.getParameter("scheduledPickUpTime" + lineNumber));
//					if (rowDtl.getScheduledPickUpTime() == null)
//					  rowDtl.setScheduledPickUpTime("0");
				    // Crop ************************************************************
					rowDtl.setCrop(request.getParameter("crop" + lineNumber));
					// Crop Description ************************************************
					rowDtl.setCropDescription(request.getParameter("cropDescription" + lineNumber));
					// Variety ********************************************************
				    rowDtl.setVariety(request.getParameter("variety" + lineNumber));
					// Variety Description*********************************************
				    rowDtl.setVarietyDescription(request.getParameter("varietyDescription" + lineNumber));
					// Grade **************************************************
					rowDtl.setGrade(request.getParameter("grade" + lineNumber));
				    // Grade Description ********************************************
					rowDtl.setGradeDescription(request.getParameter("gradeDescription" + lineNumber));
					// Organic, Conventional, BabyFood? ***********************
					rowDtl.setOrganic(request.getParameter("organic" + lineNumber));
				    // Organic, Conventional, BabyFood? Description ***********************
					rowDtl.setOrganicDescription(request.getParameter("organicDescription" + lineNumber));
					// Available Bin Quantity *******************************************
					rowDtl.setAvailableBins(request.getParameter("availableBins" + lineNumber));
				    // Scheduled Bin Quantity *******************************************
					rowDtl.setScheduledBins(request.getParameter("scheduledBins" + lineNumber));
				    // Balance Bin Quantity *******************************************
					rowDtl.setBalanceBins(request.getParameter("balanceBins" + lineNumber));
				    // Orchard Run **************************************************************
				    rowDtl.setOrchardRun(request.getParameter("orchardRun" + lineNumber));
				    // Sticker Free *************************************************************
				    rowDtl.setStickerFree(request.getParameter("stickerFree" + lineNumber));
				    // Pool / Cash ***************************************************************
				    rowDtl.setMemberCash(request.getParameter("memberCash"  + lineNumber));
				    // Cash Price ***************************************************************
				    rowDtl.setCashPrice(request.getParameter("cashPrice"  + lineNumber));
				    // Entered Bin Quantity *******************************************
					rowDtl.setBinQuantity(request.getParameter("binQuantity" + lineNumber));
					// --------------------------------------------------------------------
					// VALIDATE -- Call the Validate Method in the UpdAvailableFruitDetail object
					//-------------------------------------------------------------------
				    rowDtl.validate();
				    foundProblem.append(rowDtl.getDisplayMessage());
				    
				    // IF Bin Quantity does NOT equal 0
				    if (!rowDtl.getBinQuantity().trim().equals("0") &&
				    	rowDtl.getBinQuantityError().trim().equals(""))
				    {
				       rowDtl.setUpdateUser(this.getUpdateUser());
					   // System Date and time
				       DateTime dtNow = UtilityDateTime.getSystemDate();
				       rowDtl.setUpdateDate(dtNow.getDateFormatyyyyMMdd());
				       rowDtl.setUpdateTime(dtNow.getTimeFormathhmmss());
				    }
				    details.addElement(rowDtl);
		    	} // end of the For loop through the detail rows
		    } // if there is detail lines... -- must have detail lines
		    this.setListScheduledFruitDetail(details);
		    this.setDisplayMessage(this.getDisplayMessage() + foundProblem.toString().trim());
		  }
		}catch(Exception e){
		   System.out.println("Caught Problem within the UpdAvailableFruit.populateDetails():" + e);	
		}
		return;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String string) {
		requestType = string;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getDisplayMessage() {
		return displayMessage;
	}
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getDivisionNumber() {
	return divisionNumber;
	}
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}
	public String getCountDetail() {
		return countDetail;
	}
	public void setCountDetail(String countDetail) {
		this.countDetail = countDetail;
	}
	public InqScheduledFruit getInqSched() {
		return inqSched;
	}
	public void setInqSched(InqScheduledFruit inqSched) {
		this.inqSched = inqSched;
	}
	public Vector getListScheduledFruitDetail() {
		return listScheduledFruitDetail;
	}
	public void setListScheduledFruitDetail(Vector listScheduledFruitDetail) {
		this.listScheduledFruitDetail = listScheduledFruitDetail;
	}
	public String getCountLoads() {
		return countLoads;
	}
	public void setCountLoads(String countLoads) {
		this.countLoads = countLoads;
	}
	/**
	 *    Will Retrieve Vectors and put them into the correct fields 
	 *      for use in the JSP  
	 *    
	 *     -- This is the method the Service Calls are in
	 *    
	 */
	public void buildDropDownInformation() {
		try
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment(this.getEnvironment());
			crb.setCompanyNumber(this.getCompanyNumber());
			crb.setDivisionNumber(this.getDivisionNumber());
			Vector ddHaul = ServiceAvailableFruit.dropDownHauler(crb);
			DropDownSingle ddSingle = new DropDownSingle();
			ddSingle.setDescription("");
			ddSingle.setValue("SAME");
			ddHaul.insertElementAt(ddSingle, 0);
			this.setDdHauler(ddHaul);
			this.setDdLoadType(ServiceAvailableFruit.dropDownLoadType(crb));
			this.setDddReceivingLocation(ServiceAvailableFruit.dropDownReceivingWarehouse(crb));
			if (!this.requestType.trim().equals("schedAvailFruit"))
			{
			   this.setDddCropVariety(ServiceAvailableFruit.dropDownVariety(crb));
			   this.setDdGrade(ServiceAvailableFruit.dropDownGrade(crb));
			   this.setDdOrganic(ServiceAvailableFruit.dropDownOrganic(crb));
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			System.out.println("buildDropDownInformation Exception :" + e);
		}
		buildDropDownDualVectors();
		return;
	}
	/*
	 * Process Add -- Figure out which records have Quantity
	 *     which makes them available to add.
	 *     
	 *     take the vector listScheduledFruitDetail and generate only a 
	 *     vector of records to be added
	 */
	public void processAdd() {
		Vector returnInformation = new Vector();
		try{	
		   if (this.getListScheduledFruitDetail().size() > 0)
		   {  // only viable if there are records in the list
			   if ((!this.getRequestType().trim().equals("addSchedFruit") &&
				   !this.getLoadNumber().trim().equals("")) ||
				   (this.getRequestType().trim().equals("updSchedTransfer") &&
				   !this.getLoadNumber().trim().equals("")))
				   this.setUpdating("yes");   
				   
			   for (int x = 0; x < this.getListScheduledFruitDetail().size(); x++)
			   {
				    String loadNbr = "";
				    UpdScheduledFruitDetail thisRow = (UpdScheduledFruitDetail) this.getListScheduledFruitDetail().elementAt(x);
					if (!thisRow.getBinQuantity().trim().equals("") && !thisRow.getBinQuantity().trim().equals("0"))
					{
					 // Will need to go get the NEXT load number
					  if (this.getLoadNumber().trim().equals(""))
					  {
					    loadNbr = ServiceAvailableFruit.nextScheduleNo(this.getEnvironment());
					    this.setLoadNumber(loadNbr);
					    if (!this.getComments().trim().equals(""))
						{
						    KeyValue kvAdd = new KeyValue();
						    kvAdd.setEnvironment(this.getEnvironment().trim());
						    kvAdd.setEntryType("ScheduledLoadComment");
						    kvAdd.setKey1(loadNbr);
						    kvAdd.setKey2("");
						    kvAdd.setKey3("");
						    kvAdd.setKey4("");
						    kvAdd.setKey5("");
						    kvAdd.setLastUpdateUser(this.updateUser);
						    kvAdd.setValue(this.getComments());
							kvAdd.setDescription("");
						    kvAdd.setSequence("0");
						    kvAdd.setStatus("");
							kvAdd.setDeleteUser("");
							try{
						      ServiceKeyValue.addKeyValue(kvAdd);
							}catch(Exception e){
						 	  System.out.println("error found in the addKeyValues for a Method:processAdd" + x + ": " + e);
							}
						 }
					  }
					  else
					  {
					    loadNbr = this.getLoadNumber();
					  }
					  thisRow.setLoadNumber(loadNbr);
					  thisRow.setUpdateUser(this.getUpdateUser());
//					  thisRow.setScheduledPickUpDate(thisRow.getSchedPickUpDate().getDateFormatyyyyMMdd());
					  returnInformation.addElement(thisRow);
					 
					} 
			   } // end of the run through the list of LOADS
		   }
		   //------------------------------------
		   // Fix the date to be valid for the file
		   this.setScheduledDeliveryDate(this.getSchedDeliveryDate().getDateFormatyyyyMMdd());
		  
		}catch(Exception e){
		   System.out.println("Caught Problem within the UpdAvailableFruit.processAdd():" + e);	
		}
		this.setListScheduledFruitDetail(returnInformation);
		return;
	}
	public String getLoadNumber() {
		return loadNumber;
	}
	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
	}
	public String getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	public String getSaveButton() {
		return saveButton;
	}
	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
	}
	public BeanAvailFruit getBeanAvailFruit() {
		return beanAvailFruit;
	}
	public void setBeanAvailFruit(BeanAvailFruit beanAvailFruit) {
		this.beanAvailFruit = beanAvailFruit;
	}
	/*
	 * Take the Bean (Business Object) loaded into the BeanAvailFruit field
	 *   Retreive the information out of that bean and put into the appropriate fields
	 *    Creation date: (10/8/2010 TWalton)
	 */
	public void loadFromBeanAvailFruit() {
	try {
			
		// Build the Detail Vector from the Bean Information
		Vector listDetail = new Vector();
		int detailCount = this.beanAvailFruit.getScheduledLoadDetail().size();
		if (detailCount > 0){
			for (int x = 0; x < detailCount; x++){
				UpdScheduledFruitDetail updDetail = new UpdScheduledFruitDetail();
				try{
					ScheduledLoadDetail detail =  (ScheduledLoadDetail) this.beanAvailFruit.getScheduledLoadDetail().elementAt(x);
					if (x == 0)
					{ // Load the Header Information 1 time
						this.setLoadNumber(detail.getScheduleLoadNo());
						this.setCreateDate(detail.getCreateDate());
						this.setCreateDateDisplay(InqScheduledFruit.formatDate(detail.getCreateDate()));
						this.setCreateTime(detail.getCreateTime());
						this.setCreateTimeDisplay(InqScheduledFruit.formatTime(detail.getCreateTime()));
						this.setCreateUser(detail.getCreateUser());
						this.setCreateUserDisplay(InqAvailableFruit.longUser(this.environment, detail.getCreateUser()));
						// Do not need to send around, will put what we want seen on the screen in the fields.
						this.setUpdateDate(InqScheduledFruit.formatDate(detail.getChangeDate()));
						this.setUpdateTime(InqScheduledFruit.formatTime(detail.getChangeTime()));
						this.setUpdateUser(InqAvailableFruit.longUser(this.environment, detail.getChangeUser()));
						
						DateTime deliveryDate = UtilityDateTime.getDateFromyyyyMMdd(detail.getScheduledDeliveryDate());
						if (detail.getScheduledDeliveryDate().trim().equals("0"))
						{
							DateTime nowDate = UtilityDateTime.getSystemDate();
							this.setSchedDeliveryDate(nowDate);
							this.setScheduledDeliveryDate(nowDate.getDateFormatMMddyyyySlash());
						}
						else
						{
						   this.setSchedDeliveryDate(deliveryDate);
						   this.setScheduledDeliveryDate(deliveryDate.getDateFormatMMddyyyySlash());
						}
						
						if (detail.getScheduledDeliveryTime().trim().length() == 1)
							detail.setScheduledDeliveryTime("000" + detail.getScheduledDeliveryTime());
						if (detail.getScheduledDeliveryTime().trim().length() == 3)
							detail.setScheduledDeliveryTime("0" + detail.getScheduledDeliveryTime());
						this.setScheduledDeliveryTime(detail.getScheduledDeliveryTime() + "00");
						this.setHourScheduledDeliveryTime(detail.getScheduledDeliveryTime().substring(0,2));
						this.setMinScheduledDeliveryTime(detail.getScheduledDeliveryTime().substring(2,4));	
						this.setActualReceivingDate(detail.getActualDeliveryDate());
						this.setActualReceivingTime(detail.getActualDeliveryTime());
						this.setRecLoc(detail.getReceivingLocationNo());
						this.setDock(detail.getReceivingDockNo());
						this.setHaulingCompany(detail.getHaulingCompany());
						this.setHaulerVerification(detail.getHaulerVerificationNo());
						this.setTruckType(detail.getLoadType());
						this.setLoadOrchardRun(detail.getOrchardRun());
						this.setComments(detail.getHeaderComment());
						this.setLoadReceivedFlag(InqScheduledFruit.getLongLoadReceivedFlag(detail.getLoadReceivedFlag()));
						this.setDistributionOrder(detail.getDistributionOrder().trim());
						this.setTransferLoadFlag(detail.getTransferFlag());
					}
					updDetail.setLoadNumber(detail.getScheduleLoadNo());
					updDetail.setLoadLineNumber(detail.getScheduledLoadLineNo());
					updDetail.setWhseNo(detail.getWhseNumber().trim());
					updDetail.setWhseName(detail.getWhseDescription());
					updDetail.setLocAddNo(detail.getWhseAddressNumber().trim());
					updDetail.setWhseAddressName(detail.getWarehouse().getWarehouseDescription());
					
					updDetail.setFromRecLoc(detail.getShippingLocationNo().trim());
					updDetail.setFromRecLocDescription(detail.getShippingLocationDesc().trim());
					updDetail.setFromDock(detail.getShippingDockNo().trim());
					updDetail.setFromDockDescription(detail.getShippingDockDesc().trim());
					
					updDetail.setWhseLoadNumber(detail.getWhseLoadNo());
					updDetail.setCrop(detail.getCropCode().trim());
					updDetail.setCropDescription(detail.getCropCodeDesc()); // will need to change to description when ready
					updDetail.setVariety(detail.getVarietyCode().trim());
					updDetail.setVarietyDescription(detail.getVarietyDesc()); // will need to change to description when ready
					updDetail.setGrade(detail.getGradeCode().trim());
					updDetail.setGradeDescription(detail.getGradeDesc()); // will need to change to description when ready
					updDetail.setOrganic(detail.getOrganicCode().trim());
					updDetail.setOrganicDescription(detail.getOrganicDesc()); // will need to change to description when ready
					updDetail.setBinQuantity(detail.getBinCount().trim());
					updDetail.setOrchardRun(detail.getOrchardRunDtlFlag());
					updDetail.setStickerFree(detail.getStickerFreeFlag());
					updDetail.setMemberCash(detail.getMemberOrCash());
					updDetail.setCashPrice(detail.getCashPrice());
					updDetail.setWhseItemNumber(detail.getWhseAssignedItem());
					updDetail.setWhseLotNumber(detail.getWhseAssignedLot());
					updDetail.setItemNumber(detail.getTreeTopItem());
//					DateTime pickUpDate = UtilityDateTime.getDateFromyyyyMMdd(detail.getScheduledPickupDate());
//					if (detail.getScheduledPickupDate().trim().equals("") ||
//					    detail.getScheduledPickupDate().trim().equals("0"))
//					{
//						DateTime nowDate = UtilityDateTime.getSystemDate();
//						updDetail.setScheduledPickUpDate(nowDate.getDateFormatMMddyyyySlash());
//					}
//					else
//					   updDetail.setScheduledPickUpDate(pickUpDate.getDateFormatMMddyyyySlash());
//					updDetail.setScheduledPickUpTime(detail.getScheduledPickupTime());
//					if (updDetail.getScheduledPickUpTime().trim().length() == 1)
//					   updDetail.setScheduledPickUpTime("000" + updDetail.getScheduledPickUpTime());
//					if (updDetail.getScheduledPickUpTime().trim().length() == 3)
//					   updDetail.setScheduledPickUpTime("0" + updDetail.getScheduledPickUpTime());
//					updDetail.setScheduledPickUpTime(updDetail.getScheduledPickUpTime() + "00");
					//updDetail.setScheduledPickUpTimeHour(updDetail.getScheduledPickUpTime().substring(0,2));
					//updDetail.setScheduledPickUpTimeMinute(updDetail.getScheduledPickUpTime().substring(2,4));
					updDetail.setAvailableDate(detail.getAvailableDate());
					updDetail.setPressure(detail.getLastTestedPressure());
					updDetail.setComments(detail.getDetailComment());
					updDetail.setMemberCash(detail.getMemberOrCash());
					
				}catch(Exception e){
					// Do not need to do anything if there is not any detail
				}
				listDetail.addElement(updDetail);
			} // end of the For Loop for Details
	    }// end of the Detail = 0
		this.listScheduledFruitDetail = listDetail;
	} catch (Exception e) {
	   System.out.println("Error Caught in UpdScheduledFruit.loadFromBeanAvailFruit(BeanAvailFruit: " + e);
	}
	return;
	}
	public Vector getDddCropVariety() {
		return dddCropVariety;
	}
	public void setDddCropVariety(Vector dddCropVariety) {
		this.dddCropVariety = dddCropVariety;
	}
	public Vector getDdGrade() {
		return ddGrade;
	}
	public void setDdGrade(Vector ddGrade) {
		this.ddGrade = ddGrade;
	}
	public Vector getDdHauler() {
		return ddHauler;
	}
	public void setDdHauler(Vector ddHauler) {
		this.ddHauler = ddHauler;
	}
	public Vector getDdLoadType() {
		return ddLoadType;
	}
	public void setDdLoadType(Vector ddLoadType) {
		this.ddLoadType = ddLoadType;
	}
	public Vector getDdOrganic() {
		return ddOrganic;
	}
	public void setDdOrganic(Vector ddOrganic) {
		this.ddOrganic = ddOrganic;
	}
	/**
	 *    Will Generate the Vectors of Data to be used on the screen (JSP)
	 *       to make the Drop Down Dual informaiton work.
	 *    Build each Vector with 5 Elements
	 *       Each Element will be a Vector
	 *       [0] - code for the Master drop down in the table
	 *       [1] - code for the Slave drop down in the table
	 *       [2] - code for 1 of the Script's put in the Header - in the JSP
	 *       [3] - code for the second script put in the Header - in the JSP   
	 *    
	 */
	private void buildDropDownDualVectors() {
		try{
		   //*********************************************************************************
		   //  Receiving Location (M3 TT Warheouse) - And Dock	
		   //	Single information  -- Update a Sceduled Load -- Add a Single Load on the Fly
			if (this.requestType.equals("updSchedFruit") ||
				this.requestType.equals("addSchedFruitLoad"))
			{
				Vector allDataNeeded = new Vector();
				Vector returnData = DropDownDual.buildDualDropDownNew(this.getDddReceivingLocation(), "recLoc", "", this.getRecLoc(), "dock", "N", this.getDock(), "B", "B");
				Vector oneElement = new Vector();
				oneElement.addElement((String) returnData.elementAt(0));
				oneElement.addElement("<select name=\"dock\">");
				oneElement.addElement((String) returnData.elementAt(1));
				oneElement.addElement(JavascriptInfo.getDualDropDownNew("recLoc", "dock"));
				allDataNeeded.addElement(oneElement);  
				this.setDddReceivingLocationScreen(allDataNeeded);
			}
			// Multiple Rows of Information -- For use When Scheduling Available Fruit
			if (this.requestType.equals("schedAvailFruit"))
			{ 
				String masterName = "recLoc";
				String slaveName = "dock";
				Vector allDataNeeded = new Vector();
				for (int x = 0; x < new Integer(this.countLoads).intValue(); x++)
				{
					Vector returnData = DropDownDual.buildDualDropDownNew(this.getDddReceivingLocation(), (masterName + x), "", "", (slaveName + x), "N", "", "B", "B");
					Vector oneElement = new Vector();
					oneElement.addElement((String) returnData.elementAt(0));
					oneElement.addElement("<select name=\"" + slaveName + x + "\">");
					oneElement.addElement((String) returnData.elementAt(1));
					oneElement.addElement(JavascriptInfo.getDualDropDownNew((masterName + x), (slaveName + x)));
					allDataNeeded.addElement(oneElement);  
				}
				this.setDddReceivingLocationScreen(allDataNeeded);
			}
			
		}catch(Exception e){
			System.out.println("UpdScheduledFruit:buildDropDownDualVectors():  problem with generating information for receiving location(m3 warehouse) and dock: " + e);
		}
		try{
			// Multiple Rows of Information -- also includes Multiple rows within each Row -- for use when doing a Batch of Transfers
			if (this.requestType.equals("schedTransfer") ||
				this.requestType.equals("updSchedTransfer"))
			{
				//  This is the LOAD section -- How many Loads will be displayed on the screen
				String masterName = "recLoc";
				String masterValue = "";
				String slaveName = "dock";
				String slaveValue = "";
				String lineMasterName = "fromRecLoc";
				String lineSlaveName = "fromDock";
				String cropMasterName = "crop";
				String cropSlaveName = "variety";
				Vector loadData = new Vector();
				Vector cropLoadData = new Vector();
				int linesAlreadyLoaded = 0;
				if (this.requestType.equals("updSchedTransfer"))
				{
					this.setCountLoads("1");
					masterValue = this.getRecLoc();
					slaveValue = this.getDock();
					try{
						linesAlreadyLoaded =  ((Vector) this.beanAvailFruit.getScheduledLoadDetail()).size();
					}catch(Exception e){}
				}
				for (int x = 0; x < new Integer(this.getCountLoads()).intValue(); x++)
				{
					Vector returnData = DropDownDual.buildDualDropDownNew(this.getDddReceivingLocation(), (masterName + x), "", masterValue, (slaveName + x), "N", slaveValue, "B", "B");
					Vector oneElement = new Vector();
					oneElement.addElement((String) returnData.elementAt(0));
					oneElement.addElement("<select name=\"" + slaveName + x + "\">");
					oneElement.addElement((String) returnData.elementAt(1));
					oneElement.addElement(JavascriptInfo.getDualDropDownNew((masterName + x), (slaveName + x)));
					//  Build the Lines within the Load
					Vector listLines = new Vector();
					Vector cropListLines = new Vector();
					for (int y = linesAlreadyLoaded; y < (new Integer(this.getCountLinesPerLoad()).intValue() + linesAlreadyLoaded); y++) // Line (Blank information)
					{
						returnData = DropDownDual.buildDualDropDownNew(this.getDddReceivingLocation(), (lineMasterName + x + y), "", "", (lineSlaveName + x + y), "N", "", "B", "B");
						Vector oneLineElement = new Vector();
						oneLineElement.addElement((String) returnData.elementAt(0));
						oneLineElement.addElement("<select name=\"" + lineSlaveName + x + y + "\">");
						oneLineElement.addElement((String) returnData.elementAt(1));
						oneLineElement.addElement(JavascriptInfo.getDualDropDownNew((lineMasterName + x + y), (lineSlaveName + x + y)));
						listLines.addElement(oneLineElement);  
						
						Vector cropReturnData = DropDownDual.buildDualDropDownNew(this.dddCropVariety, (cropMasterName + x + y), "", "", (cropSlaveName + x + y), "", "", "N", "N");
						Vector cropOneLineElement = new Vector();
						cropOneLineElement.addElement((String) cropReturnData.elementAt(0));
						cropOneLineElement.addElement("<select name=\"" + cropSlaveName + x + y + "\">");
						cropOneLineElement.addElement((String) cropReturnData.elementAt(1));
						cropOneLineElement.addElement(JavascriptInfo.getDualDropDownNew((cropMasterName + x + y), (cropSlaveName + x + y)));
						cropListLines.addElement(cropOneLineElement);  
					}
					oneElement.addElement(listLines);
					loadData.addElement(oneElement);  
					cropLoadData.addElement(cropListLines);
				}
				this.setDddReceivingLocationScreen(loadData);
				this.setDddCropVarietyScreen(cropLoadData);
			}
		}catch(Exception e){
			System.out.println("UpdScheduledFruit:buildDropDownDualVectors():  Transfer Load Problem with generating information for receiving location(m3 warehouse) and dock - Also Crop/Variety Information: " + e);
		}
		try
		{ 
		   if (this.requestType.equals("updSchedFruit") ||
			   this.requestType.equals("addSchedFruitLoad"))
		   {
		   //**************************************************************************************************
		   // Crop and Variety	
			int nextRowNumber = new Integer(this.countDetail).intValue();
			if (this.listScheduledFruitDetail.size() > 0)
				nextRowNumber = this.listScheduledFruitDetail.size();
			String masterName = "crop";
			String slaveName = "variety";
			Vector allDataNeeded = new Vector();
			for (int x = 0; x < new Integer(this.getCountLinesPerLoad()).intValue(); x++)
			{
			  Vector returnData = DropDownDual.buildDualDropDownNew(this.dddCropVariety, (masterName + nextRowNumber), "", "", (slaveName + nextRowNumber), "", "", "N", "N");
			  Vector oneElement = new Vector();
			  oneElement.addElement((String) returnData.elementAt(0));
			  oneElement.addElement("<select name=\"" + slaveName + nextRowNumber + "\">");
			  oneElement.addElement((String) returnData.elementAt(1));
			  oneElement.addElement(JavascriptInfo.getDualDropDownNew((masterName + nextRowNumber), (slaveName + nextRowNumber)));
			  allDataNeeded.addElement(oneElement);  
			  nextRowNumber++;
			}
			this.setDddCropVarietyScreen(allDataNeeded);
		   }	
		}
		catch(Exception e)
		{
			System.out.println("UpdScheduledFruit:buildDropDownDualVectors():  problem with generating information for crop and variety: " + e);
		}
		try
		{
			if (this.requestType.equals("updSchedFruit") ||
				this.requestType.equals("addSchedFruitLoad"))
			{
		   //*****************************************************************************************
		   // List of Raw Fruit Warehouses and Location (M3 - Suppliers and Locations)	
//			String list1 = "inqRegion";
//			String list2 = "inqWhseNo";
//			String list3 = "inqLocAddNo";
			
//			Vector returnData1 = DropDownTriple.buildTripleDropDown(this.getListReport(), list1, "", "", list2, "", "E", list3, "", "E");
//			Vector oneElement1 = new Vector();
//			oneElement1.addElement((String) returnData1.elementAt(0));
//			oneElement1.addElement(JavascriptInfo.getTripleDropDown("inqSched", list1, list2, list3) );
//			oneElement1.addElement((String) returnData1.elementAt(1));
//			oneElement1.addElement((String) returnData1.elementAt(2));
//			oneElement1.addElement((String) returnData1.elementAt(3));
//			this.setTripleDropDownRegion(oneElement1);
			
			int nextRowNumber = new Integer(this.countDetail).intValue();
			if (this.listScheduledFruitDetail.size() > 0)
				nextRowNumber = this.listScheduledFruitDetail.size();
			String masterName = "whseNo";
			String slaveName = "locAddNo";
			Vector allDataNeeded = new Vector();
			for (int x = 0; x < new Integer(this.getCountLinesPerLoad()).intValue(); x++)
			{
			  Vector returnData = DropDownDual.buildDualDropDownNew(this.dddWhseLocation, (masterName + nextRowNumber), "", "", (slaveName + nextRowNumber), "", "", "N", "N");
			  Vector oneElement = new Vector();
			  oneElement.addElement((String) returnData.elementAt(0));
			  oneElement.addElement("<select name=\"" + slaveName + nextRowNumber + "\">");
			  oneElement.addElement((String) returnData.elementAt(1));
			  oneElement.addElement(JavascriptInfo.getDualDropDownNew((masterName + nextRowNumber), (slaveName + nextRowNumber)));
			  allDataNeeded.addElement(oneElement);  
			  nextRowNumber++;
			}
			this.setDddWhseLocationScreen(allDataNeeded);
		   }
		}
		catch(Exception e)
		{
			System.out.println("UpdScheduledFruit:buildDropDownDualVectors():  problem with generating information for M3 Suppliers(Raw Fruit Warehouses) and Locations: " + e);
		}
		return;
	}
	public Vector getDddWhseLocation() {
		return dddWhseLocation;
	}
	public void setDddWhseLocation(Vector dddWhseLocation) {
		this.dddWhseLocation = dddWhseLocation;
	}
	public Vector getDddCropVarietyScreen() {
		return dddCropVarietyScreen;
	}
	public void setDddCropVarietyScreen(Vector dddCropVarietyScreen) {
		this.dddCropVarietyScreen = dddCropVarietyScreen;
	}
	public Vector getDddWhseLocationScreen() {
		return dddWhseLocationScreen;
	}
	public void setDddWhseLocationScreen(Vector dddWhseLocationScreen) {
		this.dddWhseLocationScreen = dddWhseLocationScreen;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getHaulerVerification() {
		return haulerVerification;
	}
	public void setHaulerVerification(String haulerVerification) {
		this.haulerVerification = haulerVerification;
	}
	public String getHaulingCompany() {
		return haulingCompany;
	}
	public void setHaulingCompany(String haulingCompany) {
		this.haulingCompany = haulingCompany;
	}
	public String getTruckType() {
		return truckType;
	}
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
	public String getUpdating() {
		return updating;
	}
	public void setUpdating(String updating) {
		this.updating = updating;
	}
	public String getLoadReceivedFlag() {
		return loadReceivedFlag;
	}
	public void setLoadReceivedFlag(String loadReceivedFlag) {
		this.loadReceivedFlag = loadReceivedFlag;
	}
	public String getScheduledDeliveryDate() {
		return scheduledDeliveryDate;
	}
	public void setScheduledDeliveryDate(String scheduledDeliveryDate) {
		this.scheduledDeliveryDate = scheduledDeliveryDate;
	}
	public String getScheduledDeliveryTime() {
		return scheduledDeliveryTime;
	}
	public void setScheduledDeliveryTime(String scheduledDeliveryTime) {
		this.scheduledDeliveryTime = scheduledDeliveryTime;
	}
	public String getScheduledDeliveryTimeError() {
		return scheduledDeliveryTimeError;
	}
	public void setScheduledDeliveryTimeError(String scheduledDeliveryTimeError) {
		this.scheduledDeliveryTimeError = scheduledDeliveryTimeError;
	}
	public String getHourScheduledDeliveryTime() {
		return hourScheduledDeliveryTime;
	}
	public void setHourScheduledDeliveryTime(String hourScheduledDeliveryTime) {
		this.hourScheduledDeliveryTime = hourScheduledDeliveryTime;
	}
	public String getMinScheduledDeliveryTime() {
		return minScheduledDeliveryTime;
	}
	public void setMinScheduledDeliveryTime(String minScheduledDeliveryTime) {
		this.minScheduledDeliveryTime = minScheduledDeliveryTime;
	}
	public String getLoadOrchardRun() {
		return loadOrchardRun;
	}
	public void setLoadOrchardRun(String loadOrchardRun) {
		this.loadOrchardRun = loadOrchardRun;
	}
	/*
	 * Populate the Fields associated to Multiple Loads -
	 *    Schedule Load Header information...  
	 *    Then run the Validate within the Detail view bean
	 *    over each row of information, after loading them, and testing for null's
	 *  Set Errors into the Error Fields of the update Detail View Bean
	 */
	public void populateLoads(HttpServletRequest request) {
	   try{	
		 //**********************************************************************
		 // for the AddSchedFruit, build a bunch of UpdScheduledFruitObjects, one for each load
		 if (this.requestType.trim().equals("addSchedFruit") ||
		     this.requestType.trim().equals("schedTransfer") ||
		     this.requestType.trim().equals("updSchedTransfer"))
		 {
			 int cntLoads = 0;
			 if (this.requestType.trim().equals("schedTransfer"))
				 cntLoads = 5;
			 else
			 {
			    String countLoads = request.getParameter("countLoads");
			    if (countLoads != null && !countLoads.trim().equals(""))
				    cntLoads = (new Integer(countLoads)).intValue();
			    String countDetail = request.getParameter("countDetail");
				 if (countDetail != null && !countDetail.trim().equals(""))
				    countDetail = "0";
			 }
			 // How many lines of available fruit are there for EACH load
			
			 Vector loads = new Vector();
			 if (cntLoads > 0)
			 {
				for (int loadNumber = 0; loadNumber < cntLoads; loadNumber++)
				{  // get the information for the load... will be then put into EACH line for the Schedule
					// after the information is validated 
					UpdScheduledFruit loadHeader = new UpdScheduledFruit(); 
					loadHeader.setRequestType(this.requestType.trim());
					loadHeader.setEnvironment(this.environment);
					loadHeader.setCompanyNumber(this.companyNumber);
					loadHeader.setDivisionNumber(this.divisionNumber);
					loadHeader.setCountDetail(this.countDetail);
					loadHeader.setLoadCountNumber("" + loadNumber);
					if (this.requestType.trim().equals("updSchedTransfer"))
					   loadHeader.setLoadNumber(this.getLoadNumber());
					// Scheduled Delivery Date ****************************************
					loadHeader.setScheduledDeliveryDate(request.getParameter("scheduledDeliveryDate" + loadNumber));
					// Scheduled Delivery Time ****************************************
					loadHeader.setHourScheduledDeliveryTime(request.getParameter("hourscheduledDeliveryTime" + loadNumber));
					loadHeader.setMinScheduledDeliveryTime(request.getParameter("minscheduledDeliveryTime" + loadNumber));
					// Hauling Company ************************************************
					loadHeader.setHaulingCompany(request.getParameter("haulingCompany" + loadNumber));
					// Load Type (Truck Type) *****************************************
					loadHeader.setTruckType(request.getParameter("truckType"  + loadNumber));
					// Receiving Location *********************************************
					loadHeader.setRecLoc(request.getParameter("recLoc" + loadNumber));
					// Receiving Dock *************************************************
					loadHeader.setDock(request.getParameter("dock" + loadNumber));
					if (this.requestType.trim().equals("updSchedTransfer"))
					{
						this.setRecLoc(loadHeader.getRecLoc().trim());
						this.setDock(loadHeader.getDock().trim());
					}
					// Distribution Order Number ****** 6/13/11 tw **********************
					loadHeader.setDistributionOrder(request.getParameter("distributionOrder" + loadNumber));
					// Transfer Load Flag ************* 6/13/11 tw **********************
					if (this.requestType.trim().equals("schedTransfer") ||
		                this.requestType.trim().equals("updSchedTransfer"))
					    loadHeader.setTransferLoadFlag("Y");
					// Update User *******************************************************
					loadHeader.setUpdateUser(this.getUpdateUser().trim());
					// CREATE USER DATE AND TIME
					if (this.requestType.trim().equals("updSchedTransfer"))
					{
						loadHeader.setCreateDate(this.getCreateDate());
						loadHeader.setCreateTime(this.getCreateTime());
						loadHeader.setCreateUser(this.getCreateUser());
					}
					 // Comment *******************************************
					loadHeader.setComments(request.getParameter("comments" + loadNumber));
				    if (loadHeader.getComments() == null)
					  loadHeader.setComments("");
					// Validate Fields of the Header information
					loadHeader.validate();
					loadHeader.populateDetails(request);  
					this.setDisplayMessage(this.getDisplayMessage().trim() + loadHeader.getDisplayMessage());
					// load into Vector
					loads.addElement(loadHeader);
				}// end of the For Loop for the LOAD
			 }// Must have more than one Load
			 this.setListLoads(loads);
			 
		 }// end of the AddSchedFruit Section
	  }catch(Exception e){
		   System.out.println("Caught Problem within the UpdAvailableFruit.populateLoads():" + e);	
	  }
	  return;
	}
	public Vector getListLoads() {
		return listLoads;
	}
	public void setListLoads(Vector listLoads) {
		this.listLoads = listLoads;
	}
	public String getLoadCountNumber() {
		return loadCountNumber;
	}
	public void setLoadCountNumber(String loadCountNumber) {
		this.loadCountNumber = loadCountNumber;
	}
	public DateTime getSchedDeliveryDate() {
		return schedDeliveryDate;
	}
	public void setSchedDeliveryDate(DateTime schedDeliveryDate) {
		this.schedDeliveryDate = schedDeliveryDate;
	}
	public InqAvailableFruit getInqAvail() {
		return inqAvail;
	}
	public void setInqAvail(InqAvailableFruit inqAvail) {
		this.inqAvail = inqAvail;
	}
	public String getActualReceivingDate() {
		return actualReceivingDate;
	}
	public void setActualReceivingDate(String actualReceivingDate) {
		this.actualReceivingDate = actualReceivingDate;
	}
	public String getActualReceivingTime() {
		return actualReceivingTime;
	}
	public void setActualReceivingTime(String actualReceivingTime) {
		this.actualReceivingTime = actualReceivingTime;
	}
	public String getActualReceivingTimeError() {
		return actualReceivingTimeError;
	}
	public void setActualReceivingTimeError(String actualReceivingTimeError) {
		this.actualReceivingTimeError = actualReceivingTimeError;
	}
	public String getActualReceivingUser() {
		return actualReceivingUser;
	}
	public void setActualReceivingUser(String actualReceivingUser) {
		this.actualReceivingUser = actualReceivingUser;
	}
	public String getHourActualReceivingTime() {
		return hourActualReceivingTime;
	}
	public void setHourActualReceivingTime(String hourActualReceivingTime) {
		this.hourActualReceivingTime = hourActualReceivingTime;
	}
	public String getMinActualReceivingTime() {
		return minActualReceivingTime;
	}
	public void setMinActualReceivingTime(String minActualReceivingTime) {
		this.minActualReceivingTime = minActualReceivingTime;
	}
	public DateTime getActReceivingDate() {
		return actReceivingDate;
	}
	public void setActReceivingDate(DateTime actReceivingDate) {
		this.actReceivingDate = actReceivingDate;
	}
	public String getCreateDateDisplay() {
		return createDateDisplay;
	}
	public void setCreateDateDisplay(String createDateDisplay) {
		this.createDateDisplay = createDateDisplay;
	}
	public String getCreateTimeDisplay() {
		return createTimeDisplay;
	}
	public void setCreateTimeDisplay(String createTimeDisplay) {
		this.createTimeDisplay = createTimeDisplay;
	}
	public String getCreateUserDisplay() {
		return createUserDisplay;
	}
	public void setCreateUserDisplay(String createUserDisplay) {
		this.createUserDisplay = createUserDisplay;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}
	public String getDock() {
		return dock;
	}
	public void setDock(String dock) {
		this.dock = dock;
	}
	public String getRecLoc() {
		return recLoc;
	}
	public void setRecLoc(String recLoc) {
		this.recLoc = recLoc;
	}
	public String getDistributionOrder() {
		return distributionOrder;
	}
	public void setDistributionOrder(String distributionOrder) {
		this.distributionOrder = distributionOrder;
	}
	public String getTransferLoadFlag() {
		return transferLoadFlag;
	}
	public void setTransferLoadFlag(String transferLoadFlag) {
		this.transferLoadFlag = transferLoadFlag;
	}
	public String getCountLinesPerLoad() {
		return countLinesPerLoad;
	}
	public void setCountLinesPerLoad(String countLinesPerLoad) {
		this.countLinesPerLoad = countLinesPerLoad;
	}
	public Vector getDddReceivingLocation() {
		return dddReceivingLocation;
	}
	public void setDddReceivingLocation(Vector dddReceivingLocation) {
		this.dddReceivingLocation = dddReceivingLocation;
	}
	public Vector getDddReceivingLocationScreen() {
		return dddReceivingLocationScreen;
	}
	public void setDddReceivingLocationScreen(Vector dddReceivingLocationScreen) {
		this.dddReceivingLocationScreen = dddReceivingLocationScreen;
	}
	public String getDistributionOrderError() {
		return distributionOrderError;
	}
	public void setDistributionOrderError(String distributionOrderError) {
		this.distributionOrderError = distributionOrderError;
	}
	public String getAddNewLoad() {
		return addNewLoad;
	}
	public void setAddNewLoad(String addNewLoad) {
		this.addNewLoad = addNewLoad;
	}
	
}
