/*
 * Created on August 6, 2010
 */

package com.treetop.app.rawfruit;

import java.util.*;

import com.treetop.utilities.UtilityDateTime;
import com.treetop.viewbeans.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.businessobjects.DateTime;
import com.treetop.services.*;

/**
 * @author twalton
 * 
 * Use as the Available Fruit Header... all information will filter through here
 */
public class UpdScheduledFruitDetail extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   			= "";
	public String displayMessage 			= "";
	public String environment 				= "";

	// Must have in Update View Bean
	public String updateUser 				= "";
	public String updateDate 				= "";
	public String updateTime 				= "";

	// Fields to Update the File
	
	public String companyNumber 			= "";
	public String divisionNumber 			= "";
	public String loadNumber				= "";
	public String loadLineNumber  			= "";
	public String whseNo	 				= "";//Supplier No from CIDADR
	public String locAddNo					= "";//Supplier Addr No from CIDADR
	public String crop						= "";
	public String variety					= "";
	public String grade						= "";
	public String organic					= "";
	public String binQuantity				= "";
	public String binQuantityError			= "";
	public String orchardRun				= "";
	public String stickerFree				= "";
	public String cashPrice					= "0";
	public String cashPriceError			= "";
	public String comments					= "";
    public String whseLoadNumber        	= "";
	public String memberCash            	= "";
	public String pressure					= "";
	public String whseLotNumber				= "";
	public String whseItemNumber        	= "";
	public String itemNumber				= ""; //M3 - Tree Top Item Number
	public String itemNumberError			= "";
	// Scheduled Pickup Date/Time/User
	public String scheduledPickUpDate		= "0";
	//public DateTime schedPickUpDate			= new DateTime();
	public String scheduledPickUpTime		= "0";
	public String scheduledPickUpTimeError	= "";
	public String scheduledPickUpTimeHour	= "";
	public String scheduledPickUpTimeMinute	= "";
	
	public String fromRecLoc				= "";
	public String fromDock					= "";
	public String countDetail				= "0";
	
	public String availableDate				= "0"; // date the fruit will be available
	
	// Fields for Descriptions - ease of use
	public String chooseRow					= "";
	public String whseName					= "";
	public String whseAddressName       	= "";//Supplier Name from CIDMAS
	public String cropDescription			= "";
	public String varietyDescription		= "";
	public String gradeDescription			= "";
	public String organicDescription		= "";
	public String fromRecLocDescription		= "";
	public String fromDockDescription		= "";
	
	public String duplicateKeyFlag			= "";
	
	// Fields not updatable used for calculation on the screen 
	public String availableBins				= "";
	public String scheduledBins				= "";
	public String balanceBins				= "";

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
			// Warehouse Number *************************************************
			if (this.getWhseNo() == null)
			   this.setWhseNo("");
			// Warehouse Description ********************************************
			if (this.getWhseName() == null)
			   this.setWhseName("");
			// Warehouse Address number *****************************************
		    if (this.getLocAddNo() == null)
			   this.setLocAddNo("");
			// Warehouse Address Number Description ******************************
			if (this.getWhseAddressName() == null)
			   this.setWhseAddressName("");
		    // From Tree Top Location - for a Transfer Load 6/13/11 tw ***********
		    if (this.getFromRecLoc() == null)
		       this.setFromRecLoc("");
		    // From Tree Top Dock - for a Transfer Load 6/13/11 tw ***************
		    if (this.getFromDock() == null)
		       this.setFromDock("");
			//******************************************************************** 
			// 5/10/11 - Remove Code for Pickup Date and time -- TW
			// Scheduled Pick Up Date ********************************************
//			if (this.getScheduledPickUpDate() == null ||
//				this.getScheduledPickUpDate().trim().equals(""))
//			{
//			   this.setScheduledPickUpDate("0");
//			   if (this.getRequestType().trim().equals("addSchedFruitLoad"))
//			   {
//				  this.setSchedPickUpDate(UtilityDateTime.getSystemDate());
//				  this.setScheduledPickUpDate(this.getSchedPickUpDate().getDateFormatMMddyyyySlash());
//			   }
//			}
//			else
//			{
//			   this.setSchedPickUpDate(UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getScheduledPickUpDate()));
//			   foundProblem.append(this.getSchedPickUpDate().getDateErrorMessage().trim());
//			}
			//***************************************************************** 
			// Scheduled Pick Up Time ****************************************
//			StringBuffer timeError = new StringBuffer();
			//-------------------------------------------------------- 
			//  Review/Validate the Hour section of the time
//			if (this.getScheduledPickUpTimeHour() == null ||
//			   this.getScheduledPickUpTimeHour().trim().equals(""))
//			   this.setScheduledPickUpTimeHour("0");
//			else
//			{
//			   try{
//				  timeError.append(validateInteger(this.getScheduledPickUpTimeHour()));
//				  if (timeError.toString().trim().equals(""))
//				  {
//				   	 int hourTime = new Integer(this.getScheduledPickUpTimeHour()).intValue();
//				     if (hourTime > 24)
//				    	timeError.append("Hour Value cannot be greater than 24. ");
//				  }
//			   }catch(Exception e){}
//			   if (timeError.toString().trim().equals("") &&
//				   this.getScheduledPickUpTimeHour().length() == 1)
//				  this.setScheduledPickUpTimeHour("0" + this.getScheduledPickUpTimeHour().trim());
//			} // end of the else -- for the Hour on the Delivery Time
			// --------------------------------------------------------- 
			//  Review/Validate the Minute section of the time
//		    StringBuffer minError = new StringBuffer();
//			if (this.getScheduledPickUpTimeMinute() == null ||
//				this.getScheduledPickUpTimeMinute().trim().equals(""))
//			   this.setScheduledPickUpTimeMinute("0");
//			else
//		    {
//		       try{
//		    	  minError.append(validateInteger(this.getScheduledPickUpTimeMinute()));
//		    	  if (minError.toString().trim().equals(""))
//		    	  {
//		    	     int minTime = new Integer(this.getScheduledPickUpTimeMinute()).intValue();
//		    	 	 if (minTime > 59)
//		    		 	minError.append("Hour Value cannot be greater than 59. ");
//		    	  }
//		       }catch(Exception e){}
//			      if (minError.toString().trim().equals("") &&
//			    	  this.getScheduledPickUpTimeMinute().length() == 1)
//			         this.setScheduledPickUpTimeMinute("0" + this.getScheduledPickUpTimeMinute().trim());
//		    } 
//			timeError.append(minError.toString().trim());
//			// Scheduled Pick Up Time **************************************************
//		    if (timeError.toString().trim().equals(""))
//		       this.setScheduledPickUpTime(this.getScheduledPickUpTimeHour() + this.getScheduledPickUpTimeMinute());
//		    else{
//		       this.setScheduledPickUpTimeError(timeError.toString() + "<br>");
//		       foundProblem.append(this.getScheduledPickUpTimeError());
//		    }
		    // Item Number *******************************************************
			if (this.getItemNumber() == null)
			   this.setItemNumber("");
			if (!this.getItemNumber().trim().equals(""))
			   this.setItemNumberError(ServiceItem.verifyItem(this.environment, this.getItemNumber()));
			if (!this.getItemNumberError().trim().equals(""))
  				this.setDisplayMessage(this.getDisplayMessage() + this.getItemNumberError() + "<br>");
		    // Crop ************************************************************
			if (this.getCrop() == null)
			   this.setCrop("");
			// Crop Description ************************************************
			if (this.getCropDescription() == null)
			   this.setCropDescription("");
			// Variety ********************************************************
		    if (this.getVariety() == null)
			   this.setVariety("");
			// Variety Description*********************************************
			if (this.getVarietyDescription() == null)
			   this.setVarietyDescription("");
			// Grade **************************************************
			if (this.getGrade() == null)
			   this.setGrade("");
		    // Grade Desciprtion ********************************************
			if (this.getGradeDescription() == null)
			   this.setGradeDescription("");
			// Organic, Conventional, BabyFood? ***********************
			if (this.getOrganic() == null)
			   this.setOrganic("");
		    // Organic, Conventional, BabyFood? Description ***********************
			if (this.getOrganicDescription() == null)
			   this.setOrganicDescription("");
			// Available Bin Quantity *******************************************
			if (this.getAvailableBins() == null)
			  this.setAvailableBins("0");
		    // Scheduled Bin Quantity *******************************************
			if (this.getScheduledBins() == null)
			  this.setScheduledBins("0");
		    // Balance Bin Quantity *******************************************
		    if (this.getBalanceBins() == null)
			  this.setBalanceBins("0");	
		    // Orchard Run **************************************************************
		    if (this.getOrchardRun() == null)
		      this.setOrchardRun("");
		    if (this.getOrchardRun().trim().equals("on"))
		  	  this.setOrchardRun("Y");  
		  	// Sticker Free **************************************************************
		    if (this.getStickerFree() == null)
		      this.setStickerFree("");
		    if (this.getStickerFree().trim().equals("on"))
		  	  this.setStickerFree("Y");  
		    // Member(Pool) Cash  *********************************************************
		    if (this.getMemberCash() == null)
		      this.setMemberCash("");
		    if (this.getMemberCash().trim().equals("on"))
		  	  this.setMemberCash("Y");  
		    // Cash Price ***************************************************************
		    if (this.getCashPrice() == null || this.getCashPrice().trim().equals(""))
		      this.setCashPrice("0");
			try{
		  		if (!this.getCashPrice().trim().equals("0"))
		  			this.setCashPriceError(validateBigDecimal(this.getCashPrice()));  
		  	}catch(Exception e){
		  		this.setCashPriceError("Problem Found with Cash Price. " + e);
		  	}
		  	if (!this.getCashPriceError().trim().equals(""))
  				this.setDisplayMessage(this.getDisplayMessage() + this.getCashPriceError() + "<br>");
		    // Entered Bin Quantity *******************************************
		    if (this.getBinQuantity() == null)
			  this.setBinQuantity("0");	
		    try{
		  		if (!this.getBinQuantity().trim().equals("0"))
		  		{
		  			this.setBinQuantityError(validateInteger(this.getBinQuantity()));  
		  		}
		  	}catch(Exception e){
		  		this.setBinQuantityError("Problem Found with Bin Quantity. " + e);
		  	}
		  	if (!this.getBinQuantityError().trim().equals(""))
	  			this.setDisplayMessage(this.getDisplayMessage() + this.getBinQuantityError() + "<br>");
		}
		catch(Exception e)
		{
		}
		return;
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
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	/**
	 * @return Returns the listReport.
	 */

	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(String requestType,
										 String formulaNumber,
										 String revisionDate, 
										 String revisionTime) {
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[3];
		String[] urlNames = new String[3];
		String[] newPage = new String[3];
		for (int z = 0; z < 3; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}

		return requestType;
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

	/**
		 *    Will Generate the Vectors for ALL Drop Down Lists Needed multiple times
		 */
		public void buildDropDownVectors() {
			try
			{
//			   Vector sentValues = new Vector();
//			   ddChargeCodes = ServiceFinance.dropDownRFChargeCode(sentValues);
//			   ddBinType = ServiceRawFruit.dropDownBinType(sentValues);
//			   sentValues.addElement("ddRFWarehouse");
//			   ddWarehouse = ServiceWarehouse.dropDownWarehouse("PRD", sentValues);
			   		
			}
			catch(Exception e)
			{
				// Catch any problems, and do not display them unless testing
			}
			return;
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
		public String getBinQuantity() {
			return binQuantity;
		}
		public void setBinQuantity(String binQuantity) {
			this.binQuantity = binQuantity;
		}
		public String getCrop() {
			return crop;
		}
		public void setCrop(String crop) {
			this.crop = crop;
		}
		public String getGrade() {
			return grade;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
		public String getOrganic() {
			return organic;
		}
		public void setOrganic(String organic) {
			this.organic = organic;
		}
		public String getVariety() {
			return variety;
		}
		public void setVariety(String variety) {
			this.variety = variety;
		}
		public String getBinQuantityError() {
			return binQuantityError;
		}
		public void setBinQuantityError(String binQuantityError) {
			this.binQuantityError = binQuantityError;
		}
		public String getCropDescription() {
			return cropDescription;
		}
		public void setCropDescription(String cropDescription) {
			this.cropDescription = cropDescription;
		}
		public String getGradeDescription() {
			return gradeDescription;
		}
		public void setGradeDescription(String gradeDescription) {
			this.gradeDescription = gradeDescription;
		}
		public String getOrganicDescription() {
			return organicDescription;
		}
		public void setOrganicDescription(String organicDescription) {
			this.organicDescription = organicDescription;
		}
		public String getVarietyDescription() {
			return varietyDescription;
		}
		public void setVarietyDescription(String varietyDescription) {
			this.varietyDescription = varietyDescription;
		}
		public String getDuplicateKeyFlag() {
			return duplicateKeyFlag;
		}
		public void setDuplicateKeyFlag(String duplicateKeyFlag) {
			this.duplicateKeyFlag = duplicateKeyFlag;
		}
		public String getWhseName() {
			return whseName;
		}
		public void setWhseName(String whseName) {
			this.whseName = whseName;
		}
		public String getCashPrice() {
			return cashPrice;
		}
		public void setCashPrice(String cashPrice) {
			this.cashPrice = cashPrice;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getOrchardRun() {
			return orchardRun;
		}
		public void setOrchardRun(String orchardRun) {
			this.orchardRun = orchardRun;
		}
		public String getWhseAddressName() {
			return whseAddressName;
		}
		public void setWhseAddressName(String whseAddressName) {
			this.whseAddressName = whseAddressName;
		}
		public String getAvailableBins() {
			return availableBins;
		}
		public void setAvailableBins(String availableBins) {
			this.availableBins = availableBins;
		}
		public String getBalanceBins() {
			return balanceBins;
		}
		public void setBalanceBins(String balanceBins) {
			this.balanceBins = balanceBins;
		}
		public String getScheduledBins() {
			return scheduledBins;
		}
		public void setScheduledBins(String scheduledBins) {
			this.scheduledBins = scheduledBins;
		}
		public String getChooseRow() {
			return chooseRow;
		}
		public void setChooseRow(String chooseRow) {
			this.chooseRow = chooseRow;
		}
		public String getLoadNumber() {
			return loadNumber;
		}
		public void setLoadNumber(String loadNumber) {
			this.loadNumber = loadNumber;
		}
		public String getMemberCash() {
			return memberCash;
		}
		public void setMemberCash(String memberCash) {
			this.memberCash = memberCash;
		}
		public String getPressure() {
			return pressure;
		}
		public void setPressure(String pressure) {
			this.pressure = pressure;
		}
		public String getWhseItemNumber() {
			return whseItemNumber;
		}
		public void setWhseItemNumber(String whseItemNumber) {
			this.whseItemNumber = whseItemNumber;
		}
		public String getWhseLoadNumber() {
			return whseLoadNumber;
		}
		public void setWhseLoadNumber(String whseLoadNumber) {
			this.whseLoadNumber = whseLoadNumber;
		}
		public String getCashPriceError() {
			return cashPriceError;
		}
		public void setCashPriceError(String cashPriceError) {
			this.cashPriceError = cashPriceError;
		}
		public String getAvailableDate() {
			return availableDate;
		}
		public void setAvailableDate(String availableDate) {
			this.availableDate = availableDate;
		}
		public String getItemNumber() {
			return itemNumber;
		}
		public void setItemNumber(String itemNumber) {
			this.itemNumber = itemNumber;
		}
		public String getLoadLineNumber() {
			return loadLineNumber;
		}
		public void setLoadLineNumber(String loadLineNumber) {
			this.loadLineNumber = loadLineNumber;
		}
		public String getScheduledPickUpDate() {
			return scheduledPickUpDate;
		}
		public void setScheduledPickUpDate(String scheduledPickUpDate) {
			this.scheduledPickUpDate = scheduledPickUpDate;
		}
		public String getScheduledPickUpTime() {
			return scheduledPickUpTime;
		}
		public void setScheduledPickUpTime(String scheduledPickUpTime) {
			this.scheduledPickUpTime = scheduledPickUpTime;
		}
		public String getScheduledPickUpTimeError() {
			return scheduledPickUpTimeError;
		}
		public void setScheduledPickUpTimeError(String scheduledPickUpTimeError) {
			this.scheduledPickUpTimeError = scheduledPickUpTimeError;
		}
		public String getScheduledPickUpTimeHour() {
			return scheduledPickUpTimeHour;
		}
		public void setScheduledPickUpTimeHour(String scheduledPickUpTimeHour) {
			this.scheduledPickUpTimeHour = scheduledPickUpTimeHour;
		}
		public String getScheduledPickUpTimeMinute() {
			return scheduledPickUpTimeMinute;
		}
		public void setScheduledPickUpTimeMinute(String scheduledPickUpTimeMinute) {
			this.scheduledPickUpTimeMinute = scheduledPickUpTimeMinute;
		}
		public String getWhseLotNumber() {
			return whseLotNumber;
		}
		public void setWhseLotNumber(String whseLotNumber) {
			this.whseLotNumber = whseLotNumber;
		}
		public String getStickerFree() {
			return stickerFree;
		}
		public void setStickerFree(String stickerFree) {
			this.stickerFree = stickerFree;
		}
		public String getWhseNo() {
			return whseNo;
		}
		public void setWhseNo(String whseNo) {
			this.whseNo = whseNo;
		}
		public String getLocAddNo() {
			return locAddNo;
		}
		public void setLocAddNo(String locAddNo) {
			this.locAddNo = locAddNo;
		}
		public String getFromDock() {
			return fromDock;
		}
		public void setFromDock(String fromDock) {
			this.fromDock = fromDock;
		}
		public String getCountDetail() {
			return countDetail;
		}
		public void setCountDetail(String countDetail) {
			this.countDetail = countDetail;
		}
		public String getFromRecLoc() {
			return fromRecLoc;
		}
		public void setFromRecLoc(String fromRecLoc) {
			this.fromRecLoc = fromRecLoc;
		}
		public String getItemNumberError() {
			return itemNumberError;
		}
		public void setItemNumberError(String itemNumberError) {
			this.itemNumberError = itemNumberError;
		}
		public String getFromDockDescription() {
			return fromDockDescription;
		}
		public void setFromDockDescription(String fromDockDescription) {
			this.fromDockDescription = fromDockDescription;
		}
		public String getFromRecLocDescription() {
			return fromRecLocDescription;
		}
		public void setFromRecLocDescription(String fromRecLocDescription) {
			this.fromRecLocDescription = fromRecLocDescription;
		}
		
		
}
