/*
 * Created on August 6, 2010
 */

package com.treetop.app.rawfruit;

import java.util.*;
import com.treetop.viewbeans.*;
import com.treetop.businessobjectapplications.*;

/**
 * @author twalton
 * 
 * Use as the Available Fruit Header... all information will filter through here
 */
public class UpdAvailableFruitDetail extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   		= "";
	public String displayMessage 		= "";
	public String environment 			= "";

	// Must have in Update View Bean
	public String updateUser 			= "";
	public String updateDate 			= "";
	public String updateTime 			= "";

	// Fields to filter on
	public String companyNumber 		= "";
	public String divisionNumber 		= "";
	public String whseNo	 			= "";//Supplier No from CIDADR
	public String locAddNo				= "";//Supplier Addr No from CIDADR
	public String whseName              = "";//Supplier Name from CIDMAS
	public String inventoryType			= "";
	
	public String crop					= "";
	public String cropDescription		= "";
	public String variety				= "";
	public String varietyDescription	= "";
	public String grade					= "";
	public String gradeDescription		= "";
	public String organic				= "";
	public String organicDescription	= "";
	public String stickerFree			= "";
	public String orchardRun			= "";
	public String binQuantity			= "";
	public String binQuantityError		= "";
	public String duplicateKeyFlag		= "";
	
	public String purchaseQuantity		= "0";
	public String purchaseQuantityError = "";
	public String purchasePrice			= "0";
	public String purchasePriceError	= "";
	
	// Fields not updatable used for calculation on the screen 
	public String binQuantitySched		= "";
	public String binQuantityBalance	= "";

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
			StringBuffer foundProblem = new StringBuffer();
			//validate whse no. Use Warehouse service - update whseNoError field from method.
			// if not empty also put it in the dsiplayMessage field
			
			// Bin Quantity **************************************************
		  	try{
		  		if (!this.getBinQuantity().trim().equals("0"))
		  		{
		  			this.setBinQuantityError(validateInteger(this.getBinQuantity()));  
	  			    if (!this.getBinQuantityError().trim().equals(""))
		  				foundProblem.append(this.getBinQuantityError() + "<br>");
		  		}
		  	}catch(Exception e){
		  		this.setBinQuantityError("Problem Found with Bin Quantity. " + e);
		  		foundProblem.append("Problem Found with Bin Quantity<br>");
		  	}
		    // Purchase Quantity **************************************************
		  	try{
		  		if (!this.getPurchaseQuantity().trim().equals("0"))
		  		{
		  			this.setPurchaseQuantityError(validateInteger(this.getPurchaseQuantity()));  
	  			    if (!this.getPurchaseQuantityError().trim().equals(""))
		  				foundProblem.append(this.getPurchaseQuantityError() + "<br>");
		  		}
		  	}catch(Exception e){
		  		this.setPurchaseQuantityError("Problem Found with Purchase Quantity. " + e);
		  		foundProblem.append("Problem Found with Purchase Quantity<br>");
		  	}
		 // Purchase Price **************************************************
		  	try{
		  		if (!this.getPurchasePrice().trim().equals("0"))
		  		{
		  			this.setPurchasePriceError(validateBigDecimal(this.getPurchasePrice()));  
	  			    if (!this.getPurchasePriceError().trim().equals(""))
		  				foundProblem.append(this.getPurchasePriceError() + "<br>");
		  		}
		  	}catch(Exception e){
		  		this.setPurchasePriceError("Problem Found with Purchase Price. " + e);
		  		foundProblem.append("Problem Found with Purchase Price<br>");
		  	}
		  	
		  	// Sticker Free **************************************************************
		    if (this.getStickerFree() == null)
		      this.setStickerFree("");
		    if (this.getStickerFree().trim().equals("on"))
		  	  this.setStickerFree("Y");  
		    
		  	if(!foundProblem.toString().trim().equals(""))
		  	   this.setDisplayMessage(foundProblem.toString().trim());
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
		public String getBinQuantityBalance() {
			return binQuantityBalance;
		}
		public void setBinQuantityBalance(String binQuantityBalance) {
			this.binQuantityBalance = binQuantityBalance;
		}
		public String getBinQuantitySched() {
			return binQuantitySched;
		}
		public void setBinQuantitySched(String binQuantitySched) {
			this.binQuantitySched = binQuantitySched;
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
		public String getOrchardRun() {
			return orchardRun;
		}
		public void setOrchardRun(String orchardRun) {
			this.orchardRun = orchardRun;
		}
		public String getStickerFree() {
			return stickerFree;
		}
		public void setStickerFree(String stickerFree) {
			this.stickerFree = stickerFree;
		}
		public String getLocAddNo() {
			return locAddNo;
		}
		public void setLocAddNo(String locAddNo) {
			this.locAddNo = locAddNo;
		}
		public String getWhseNo() {
			return whseNo;
		}
		public void setWhseNo(String whseNo) {
			this.whseNo = whseNo;
		}
		public String getPurchaseQuantity() {
			return purchaseQuantity;
		}
		public void setPurchaseQuantity(String purchaseQuantity) {
			this.purchaseQuantity = purchaseQuantity;
		}
		public String getPurchasePrice() {
			return purchasePrice;
		}
		public void setPurchasePrice(String purchasePrice) {
			this.purchasePrice = purchasePrice;
		}
		public String getPurchaseQuantityError() {
			return purchaseQuantityError;
		}
		public void setPurchaseQuantityError(String purchaseQuantityError) {
			this.purchaseQuantityError = purchaseQuantityError;
		}
		public String getPurchasePriceError() {
			return purchasePriceError;
		}
		public void setPurchasePriceError(String purchasePriceError) {
			this.purchasePriceError = purchasePriceError;
		}
		public String getInventoryType() {
			return inventoryType;
		}
		public void setInventoryType(String inventoryType) {
			this.inventoryType = inventoryType;
		}
		
}
