/*
 * Created on July 20, 2010
 */

package com.treetop.app.rawfruit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.math.*;

import com.treetop.viewbeans.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.businessobjects.*;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
import com.treetop.app.rawfruit.*;

/**
 * @author thaile
 * 
 * Use as the Available Fruit Header... all information will filter through here
 */
public class UpdAvailableFruit extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   				= "";
	public String displayMessage 				= "";
	public String environment 					= "";
	
	// Must have in Update View Bean
	public String updateUser 					= "";
	public String updateDate 					= "";
	public String updateTime 					= "";

	
	// Fields to filter on
	public String companyNumber 				= "100";
	public String divisionNumber 				= "100";
	public String whseNo						= ""; 
	public String whseNo2						= ""; // use for the triple drop down list
	public String whseNoError					= "";
	public String locAddNo						= "";
	public String locAddNoError					= "";
	public String whseName                      = "";//Supplier Name from CIDMAS
	public String inventoryType					= "";
	
	// Lists
	public Vector listAvailFruitDetail			= new Vector(); // stores UpdAvailFruitDetail objects
	public String countDetail					= "0";
	public Vector listComments					= new Vector();
	
	//Button Values
	public String saveButton 					= "";
	
	//Security
	public String readOnly						= "";
	
	public BeanAvailFruit beanAvailFruit		= new BeanAvailFruit();
	
//	 Drop Down Lists
	public Vector dddCropVariety				= new Vector(); // Dual Drop Down
	public Vector ddGrade						= new Vector();
	public Vector ddOrganic						= new Vector();
	public Vector listLocation					= new Vector();
	
	public Vector dualDropDown10Blank           = new Vector(); // Build 10 vectors of information needed to process Drop Down Dual, Drop down lists
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
			//validate whse no. Use Warehouse service - update whseNoError field from method.
			if (this.getEnvironment() == null ||
				this.getEnvironment().trim().equals(""))
				this.setEnvironment("PRD");
			// if not empty also put it in the dsiplayMessage field
			
			if ((this.getWhseNo2() != null &&
				!this.getWhseNo2().trim().equals("Make a selection"))&&
				!this.getWhseNo2().trim().equals(""))
				this.setWhseNo(this.getWhseNo2());
			if ((this.getWhseNo() == null ||
				this.getWhseNo().trim().equals("Make a selection")) &&
				this.getWhseNo().trim().equals(""))
			  this.setWhseNo("");
			// Test for a number at the end of the value
			if (!this.getWhseNo().trim().equals(""))
			{
				int xLastIndex = this.getWhseNo().lastIndexOf("-*-");
				if (xLastIndex > 0)
				{
					this.setWhseNo(this.getWhseNo().substring((xLastIndex + 3)));
				}
			}else{
				this.setWhseNoError("Please Choose a Warehouse");
			}
			if (this.getLocAddNo() == null ||
				this.getLocAddNo().trim().equals("Make a selection"))
				  this.setLocAddNo("");
				// Test for a number at the end of the value
			if (!this.getLocAddNo().trim().equals(""))
			{
				int xLastIndex = this.getLocAddNo().lastIndexOf("-*-");
				if (xLastIndex > 0)
				{
					this.setLocAddNo(this.getLocAddNo().substring((xLastIndex + 3)));
				}
			}else{
				this.setLocAddNoError("Please Choose a Location");
			}
			
			if (!this.getWhseNoError().trim().equals("") ||
				!this.getLocAddNoError().trim().equals(""))
			{
				this.setDisplayMessage(this.getWhseNoError().trim() + "&nbsp;&nbsp;" + this.getLocAddNoError().trim());
			}
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
	/**
	 * @return Returns the saveButton.
	 */
	public String getSaveButton() {
		return saveButton;
	}
	/**
	 * @param saveButton The saveButton to set.
	 */
	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
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
	 *    Crop - Variety -- Dual Drop Down
	 *    Grade
	 *    Organic
	 */
	public void buildDropDownVectors() {
		try
		{
		   CommonRequestBean crb = new CommonRequestBean();
		   crb.setEnvironment(this.getEnvironment());
		   crb.setCompanyNumber(this.getCompanyNumber());
		   crb.setDivisionNumber(this.getDivisionNumber());
		   this.dddCropVariety = ServiceAvailableFruit.dropDownVariety(crb);
		   this.ddGrade = ServiceAvailableFruit.dropDownGrade(crb);
		   this.ddOrganic = ServiceAvailableFruit.dropDownOrganic(crb);
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
		public String getCountDetail() {
			return countDetail;
		}
		public void setCountDetail(String countDetail) {
			this.countDetail = countDetail;
		}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update on the Screen 
	 *    Creation date: (7/20/2010 TWalton)
	 */
	public void loadFromBeanAvailFruit(BeanAvailFruit baf) {
		try {
			AvailFruitByWhse header 		= baf.getAvailFruitByWhse();
			this.whseNo		  				= header.getWhseNumber();
			this.locAddNo		 			= header.getWhseAddressNumber();
			this.whseName					= header.getWhseDescription();
		// Build the Detail Vector from the Bean Information
			Vector listDetail = new Vector();
			int detailCount = baf.getAvailFruitByWhseDetail().size();
			boolean checkUpdateInfo = false;
			if (detailCount > 0){
				for (int x = 0; x < detailCount; x++){
					UpdAvailableFruitDetail updDetail = new UpdAvailableFruitDetail();
					try{
						AvailFruitByWhseDetail detail =  (AvailFruitByWhseDetail) baf.getAvailFruitByWhseDetail().elementAt(x);
						if (checkUpdateInfo == false)
						{
							if (!detail.getWhseLoadNo().trim().equals("9999999999999"))
							{
							   this.updateDate  				= InqAvailableFruit.formatDate(detail.getChangeDate());
							   this.updateTime					= InqAvailableFruit.formatTime(detail.getChangeTime());
							   this.updateUser					= InqAvailableFruit.longUser(this.getEnvironment(), detail.getChangeUser());
							   checkUpdateInfo = true;
							}
						}
						updDetail.setInventoryType(detail.getInventoryType().trim());
						updDetail.setWhseNo(detail.getWhseNumber().trim());
						updDetail.setLocAddNo(detail.getWhseAddressNumber().trim());
						updDetail.setCrop(detail.getCropCode().trim());
						updDetail.setCropDescription(detail.getCropCodeDesc().trim()); 
						updDetail.setVariety(detail.getVarietyCode().trim());
						updDetail.setVarietyDescription(detail.getVarietyDesc().trim());
						updDetail.setGrade(detail.getGradeCode().trim());
						updDetail.setGradeDescription(detail.getGradeDesc().trim()); 
						updDetail.setOrganic(detail.getOrganicCode().trim());
						updDetail.setOrganicDescription(detail.getOrganicDesc().trim()); 
						updDetail.setStickerFree(detail.getStickerFree().trim());
						updDetail.setDuplicateKeyFlag(detail.getDuplicated().trim());
						updDetail.setBinQuantity(detail.getBinCount().trim());
						updDetail.setPurchaseQuantity(detail.getPurchaseQuantity().trim());
						updDetail.setPurchasePrice(detail.getPurchasePrice().trim());
						String schedQty = detail.getScheduledQty();
						if (schedQty.trim().equals(""))
							schedQty = "0";
						updDetail.setBinQuantitySched(schedQty);
						try
						{
							BigDecimal balanceCount = (new BigDecimal(detail.getBinCount())).subtract(new BigDecimal(schedQty));
							updDetail.setBinQuantityBalance(balanceCount.toString());
							
						} catch(Exception e)
						{}
						
						// using the BinQuantity and the Scheduled inventory the Balance
						//    can be determined for the screen
					}catch(Exception e){
						// Do not need to do anything if there is not any detail
					}
					listDetail.addElement(updDetail);
				} // end of the For Loop for Details
		    }// end of the Detail = 0
			this.listAvailFruitDetail = listDetail;
			if (this.getUpdateDate().trim().equals(""))
				this.setUpdateUser("");
		} catch (Exception e) {
		   System.out.println("Error Caught in UpdAvailFruit.loadFromBeanAvailFruit(BeanAvailFruit: " + e);
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
			Vector listDetail = new Vector();
			StringBuffer foundProblem = new StringBuffer();
			// Retrieve Available Fruit Detail information 
			int countRecords =  new Integer(this.getCountDetail()).intValue();
			if (countRecords > 0){
				for (int x = 1; x < (countRecords + 1); x++){
					UpdAvailableFruitDetail updDtl = new UpdAvailableFruitDetail();
					updDtl.setEnvironment(this.getEnvironment());
					updDtl.setCompanyNumber(this.getCompanyNumber());
					updDtl.setDivisionNumber(this.getDivisionNumber());
				    //-------------------------------------------------------------------
					// RETRIEVE
					//-------------------------------------------------------------------
					// Crop ***************************************************
					updDtl.setCrop(request.getParameter("crop" + x));
					if (updDtl.getCrop() == null || updDtl.getCrop().trim().equals("*all"))
						updDtl.setCrop("");
					// Variety ************************************************
				    updDtl.setVariety(request.getParameter("variety" + x));
					if (updDtl.getVariety() == null || updDtl.getVariety().trim().equals("*all"))
					  updDtl.setVariety("");
					// Grade **************************************************
					updDtl.setGrade(request.getParameter("grade" + x));
				    if (updDtl.getGrade() == null || updDtl.getGrade().trim().equals("*all"))
					  updDtl.setGrade("");
					// Organic, Conventional, BabyFood? ***********************
					updDtl.setOrganic(request.getParameter("organic" + x));
				    if (updDtl.getOrganic() == null || updDtl.getOrganic().trim().equals("*all"))
					  updDtl.setOrganic("");
				    // Sticker Free *******************************************
					updDtl.setStickerFree(request.getParameter("stickerFree" + x));
				    if (updDtl.getStickerFree() == null)
					  updDtl.setStickerFree("");
					// Bin Quantity *******************************************
					updDtl.setBinQuantity(request.getParameter("binQuantity" + x));
				    if (updDtl.getBinQuantity() == null || updDtl.getBinQuantity().trim().equals(""))
					  updDtl.setBinQuantity("0");
				    // Purchase Quantity *******************************************
					updDtl.setPurchaseQuantity(request.getParameter("purchaseQuantity" + x));
				    if (updDtl.getPurchaseQuantity() == null || updDtl.getPurchaseQuantity().trim().equals(""))
					  updDtl.setPurchaseQuantity("0");	  
				    // Purchase Price *******************************************
					updDtl.setPurchasePrice(request.getParameter("purchasePrice" + x));
				    if (updDtl.getPurchasePrice() == null || updDtl.getPurchasePrice().trim().equals(""))
					  updDtl.setPurchasePrice("0");	  
				    //*** Bin Quantity must NOT equal 0
				    //       won't build the record if the bin quantity is 0
					if (!updDtl.getBinQuantity().trim().equals("0")){
					   // Basic Fields **********************************************
					    updDtl.setUpdateUser(this.getUpdateUser());
					   updDtl.setUpdateDate(this.getUpdateDate());
					   updDtl.setUpdateTime(this.getUpdateTime());
					   updDtl.setWhseNo(this.getWhseNo());
					   updDtl.setLocAddNo(this.getLocAddNo());
					   updDtl.setInventoryType(this.getInventoryType());
					   //--------------------------------------------------------------------
					   // VALIDATE -- Call the Validate Method in the UpdAvailableFruitDetail object
					   //-------------------------------------------------------------------
					   updDtl.validate();
					   foundProblem.append(updDtl.getDisplayMessage());
					   //-------------------------------------------------------------------
					  listDetail.addElement(updDtl);
				    } // only if there is not a 0 in the quantity
				} // end of the For Loop
				this.setListAvailFruitDetail(listDetail);
				this.setDisplayMessage(this.displayMessage + foundProblem.toString().trim());
			} // end if the if count > 0
		}catch(Exception e){
		   System.out.println("Caught Problem within the UpdAvailableFruit.populateDetails():" + e);	
		}
		return;
	}
			public Vector getListAvailFruitDetail() {
				return listAvailFruitDetail;
			}
			public void setListAvailFruitDetail(Vector listAvailFruitDetail) {
				this.listAvailFruitDetail = listAvailFruitDetail;
			}
			public String getReadOnly() {
				return readOnly;
			}
			public void setReadOnly(String readOnly) {
				this.readOnly = readOnly;
			}
			public Vector getDdGrade() {
				return ddGrade;
			}
			public void setDdGrade(Vector ddGrade) {
				this.ddGrade = ddGrade;
			}
			public Vector getDdOrganic() {
				return ddOrganic;
			}
			public void setDdOrganic(Vector ddOrganic) {
				this.ddOrganic = ddOrganic;
			}
			public BeanAvailFruit getBeanAvailFruit() {
				return beanAvailFruit;
			}
			public void setBeanAvailFruit(BeanAvailFruit beanAvailFruit) {
				this.beanAvailFruit = beanAvailFruit;
			}
			public Vector getDddCropVariety() {
				return dddCropVariety;
			}
			public void setDddCropVariety(Vector dddCropVariety) {
				this.dddCropVariety = dddCropVariety;
			}
			public Vector getDualDropDown10Blank() {
				return dualDropDown10Blank;
			}
			public void setDualDropDown10Blank(Vector dualDropDown10Blank) {
				this.dualDropDown10Blank = dualDropDown10Blank;
			}
	/**
	 *    Will Generate the Vectors of Data to be used on the screen (JSP)
	 *       to make the Drop Down Dual informaiton work.
	 *    Build a Vector with 10 Elements
	 *       Each Element will be a Vector
	 *       [0] - code for the Master drop down in the table
	 *       [1] - code for the Slave drop down in the table
	 *       [2] - code for 1 of the Script's put in the Header - in the JSP
	 *       [3] - code for the second script put in the Header - in the JSP   
	 *    
	 */
	public void build10DropDownDualVectors() {
		try
		{
			int nextRowNumber = this.listAvailFruitDetail.size();
			String masterName = "crop";
			String slaveName = "variety";
			Vector allDataNeeded = new Vector();
			
			for (int x = 0; x < 10; x++)
			{
			  nextRowNumber++;
			  Vector returnData = DropDownDual.buildDualDropDownNew(this.dddCropVariety, (masterName + nextRowNumber), "", "", (slaveName + nextRowNumber), "", "", "N", "N");
			  Vector oneElement = new Vector();
			  oneElement.addElement((String) returnData.elementAt(0));
			  oneElement.addElement("<select name=\"" + slaveName + nextRowNumber + "\">");
			  oneElement.addElement((String) returnData.elementAt(1));
			  oneElement.addElement(JavascriptInfo.getDualDropDownNew((masterName + nextRowNumber), (slaveName + nextRowNumber)));
			  allDataNeeded.addElement(oneElement);  
			}
			this.setDualDropDown10Blank(allDataNeeded);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return;
	}
	public String getWhseName() {
		return whseName;
	}
	public void setWhseName(String whseName) {
		this.whseName = whseName;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
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
	public Vector getListLocation() {
		return listLocation;
	}
	public void setListLocation(Vector listLocation) {
		this.listLocation = listLocation;
	}
	public String getLocAddNoError() {
		return locAddNoError;
	}
	public void setLocAddNoError(String locAddNoError) {
		this.locAddNoError = locAddNoError;
	}
	public String getWhseNoError() {
		return whseNoError;
	}
	public void setWhseNoError(String whseNoError) {
		this.whseNoError = whseNoError;
	}
	public String getWhseNo2() {
		return whseNo2;
	}
	public void setWhseNo2(String whseNo2) {
		this.whseNo2 = whseNo2;
	}
	public String getInventoryType() {
		return inventoryType;
	}
	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}
}
