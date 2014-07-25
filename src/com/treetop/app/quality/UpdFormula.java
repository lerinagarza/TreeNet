/*
 * Created on April 28, 2010
 */

package com.treetop.app.quality;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.data.UserFile;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.services.*;

/**
 * @author twalto
 * 
 * Use as the Formula Header... all information will filter through here
 */
public class UpdFormula extends BaseViewBeanR2 {

	// Must have in Update View Bean
	public String updateUser 					= "";
	public String updateDate 					= "";
	public String updateTime 					= "";
	
	// Generate not updateable
	public String formulaNumber 				= "";
	public String revisionDate 					= "";
	public String revisionTime 					= "";
	public DateTime supersedesDate 				= new DateTime();
	
	// Fields Available for Update
	public String originalStatus				= "";
	public String status 						= "";
	public String formulaType 					= "";
	public String formulaTypeError				= "";
	public String formulaName					= "";
	public String formulaNameError				= "";
	public String formulaDescription 			= "";
	public String formulaDescriptionError		= "";
	public String revisionReason 				= "";
	public String revisionReasonError			= "";
	public String productionStatus 				= "";
	public String originationUser				= "";
	public String groupCode						= "";
	public String scopeCode						= "";
	public String approvedByUser				= "";
	public String lineTankItem 					= "";
	public String lineTankItemError 			= "";
	public String customerNumber 				= "";
	public String customerNumberError 			= "";
	public String customerName 					= "";
	public String customerCode					= "";
	public String customerCodeError				= "";
	public String customerOrSupplierItemNumber	= "";
	public String batchSize						= "0";
	public String batchSizeError				= "";
	public String batchUOM						= "";
	public String batchUOMError					= "";
	public String targetBrix					= "0";
	public String targetBrixError				= "";
	
	public String fruitOrigin					= "";
	public String batchSizePreBlend				= "0";
	public String batchSizePreBlendError		= "";
	public String batchPreBlendUOM				= "";
	public String batchPreBlendUOMError			= "";
	
	// additional values sent -- push through on a new revision
	public String creationDate 					= "";
	public String creationTime 					= "";
	public String creationUser 					= "";
	public String referenceFormulaNumber 		= "0";
	public String referenceFormulaRevisionDate  = "0";
	public String referenceFormulaRevisionTime  = "0";
	
	// Lists
	public Vector<UpdFormulaDetail> listPreBlendDetail		= new Vector<UpdFormulaDetail>(); 
	public String countPreBlendDetail 			= "0";
	public Vector<UpdFormulaDetail> listProductionDetail	= new Vector<UpdFormulaDetail>();
	public String countProductionDetail 		= "0";
	public Vector<UpdFormulaDetail> listPreBlendSauceDetail	= new Vector<UpdFormulaDetail>();
	public String countPreBlendSauceDetail 		= "0";
	
//	 Also will need to add Vector of varieties that CAN be included
	public Vector<UpdVariety> listVarietiesIncluded = new Vector<UpdVariety>();
	public String countVarietiesIncluded 		= "0";
//	 Also will need to add Vector of varieties that MUST be excluded
	public Vector<UpdVariety> listVarietiesExcluded = new Vector<UpdVariety>();
	public String countVarietiesExcluded 		= "0";	
	
	// Raw Fruit Attribute Detail Information
	public Vector<UpdTestParameters> listRawFruitAttributes = new Vector<UpdTestParameters>();
	public String countRawFruitAttributes   	= "0";
	
	// Will be processed through the Comment Program
	public Vector listComments					= new Vector();
	public Vector listDetails					= new Vector();
	public Vector listCalculations				= new Vector();
	public Vector listBlendingInstructions		= new Vector();
	public Vector listKeyLabelStatements		= new Vector();
	public Vector listIngredientStatement		= new Vector();
	public Vector listRFAdditionalInfo			= new Vector();
	
	
	//Button Values
	public String saveButton 					= "";
	
	public BeanQuality updBean 					= new BeanQuality();
		
	public Vector listReport 					= null;

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		  if (!this.saveButton.trim().equals(""))
		  {
		//----------------------------------------------------------------------  
		// VALIDATE NEEDED/REQUIRED FIELDS   
		//----------------------------------------------------------------------	
		    StringBuffer foundProblem = new StringBuffer();
		   
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment(this.getEnvironment());
			crb.setCompanyNumber(this.getCompany());
			crb.setDivisionNumber(this.getDivision());
			
			// Only test these IF the status is moving to ACTIVE from PENDING
			if (this.getStatus().trim().equals("AC") &&
				this.getOriginalStatus().trim().equals("PD") &&
				!this.saveButton.trim().equals(""))
			{
				//-------------------------------------------------------------------
				// Formula Name
				if (this.getFormulaName().trim().equals(""))
				   this.setFormulaNameError("MUST enter a Formula Number");
				if (!this.getFormulaNameError().trim().equals(""))
					foundProblem.append(this.getFormulaNameError() + "<br>");
				//-------------------------------------------------------------------
				// Formula Type
				if (this.getFormulaType().trim().equals(""))
				   this.setFormulaTypeError("MUST enter a Formula Type");
				if (!this.getFormulaTypeError().trim().equals(""))
					foundProblem.append(this.getFormulaTypeError() + "<br>");
				//-------------------------------------------------------------------
				// Formula Description
				if (this.getFormulaDescription().trim().equals(""))
				   this.setFormulaDescriptionError("MUST enter a Formula Name");
				if (!this.getFormulaDescriptionError().trim().equals(""))
				   foundProblem.append(this.getFormulaDescriptionError() + "<br>");
				//------------------------------------------------------------------
				// Revision Reason
			    if (this.revisionReason.trim().equals(""))
			       this.setRevisionReasonError("Revision Reason MUST be filled in");
				if (!this.getRevisionReasonError().trim().equals(""))
				   foundProblem.append(this.getRevisionReasonError().trim());
				
				if (!foundProblem.toString().trim().equals(""))
					this.setStatus(this.getOriginalStatus());
			}
			//-------------------------------------------------------------------
			// Customer Number
			if (!this.getCustomerNumber().trim().equals(""))
			{
			   if (!this.getCustomerCode().trim().equals(""))
			   {
			      crb.setIdLevel1(this.customerNumber);
			      if (this.customerCode.trim().equals("SAM"))
			        crb.setIdLevel2(this.customerCode);
			      else
			    	crb.setIdLevel2("M3");
			      this.setCustomerNumberError(ServiceCustomer.verifyCustomerByNumber(crb));
		          if (!this.getCustomerNumberError().trim().equals(""))
			       	foundProblem.append(this.getCustomerNumberError() + "<br>");
			   }else{
				   this.setCustomerCodeError("If a Customer Number is entered a Customer Code MUST be filled in, please choose one");
				   if (!this.getCustomerCodeError().trim().equals(""))
			        	foundProblem.append(this.getCustomerCodeError() + "<br>");
			   }
			}
			//-------------------------------------------------------------------
			// Batch Size
			if (this.getBatchSize().trim().equals(""))
			   this.setBatchSize("0");
			BigDecimal bdSize = new BigDecimal("0");
			if (!this.getBatchSize().trim().equals("0"))
			{	
		  	  try{
		  			// will need to validate that this is an valid Quantity
		  		  this.setBatchSizeError(validateBigDecimal(this.batchSize));  
		  		  if (!this.getBatchSizeError().trim().equals(""))
		  			foundProblem.append(this.getBatchSizeError() + "<br>");
		  		  else
		  			bdSize = new BigDecimal(this.getBatchSize());
		  	  }catch(Exception e){
		  		this.setBatchSizeError("Problem Found with Batch Size. " + e);
		  		foundProblem.append("Problem Found with Batch Size<br>");
		  	  }
		  	  if (bdSize.compareTo(new BigDecimal("0")) > 0 && 
		  		  this.getBatchUOM().trim().equals(""))
		  	  {
		  		 this.setBatchUOMError("If a Batch Size is entered a Unit of Measure must be entered, please choose one");
				 if (!this.getBatchUOMError().trim().equals(""))
			       	foundProblem.append(this.getBatchUOMError() + "<br>"); 
		  	  }
			}
			//-------------------------------------------------------------------
			// Target Brix for this Formula
			if (this.getTargetBrix().trim().equals(""))
				this.setTargetBrix("0");
			if (!this.getTargetBrix().trim().equals("0"))
			{	
		  	  try{
		  			// will need to validate that this is an valid Quantity
		  			this.setTargetBrixError(validateBigDecimal(this.targetBrix));  
		  			if (!this.getTargetBrixError().trim().equals(""))
		  				foundProblem.append(this.getTargetBrixError() + "<br>");
		  	  }catch(Exception e){
		  		this.setBatchSizeError("Problem Found with Formula Target Brix. " + e);
		  		foundProblem.append("Problem Found with Formula Target Brix<br>");
		  	  }
			}
			//-------------------------------------------------------------------
			// Batch Size for PreBlend
			if (this.getBatchSizePreBlend().trim().equals(""))
			   this.setBatchSizePreBlend("0");
			bdSize = new BigDecimal("0");
			if (!this.getBatchSizePreBlend().trim().equals("0"))
			{	
		  	  try{
		  		  // will need to validate that this is an valid Quantity
		  		 this.setBatchSizePreBlendError(validateBigDecimal(this.batchSizePreBlend));  
		  	  	 if (!this.getBatchSizePreBlendError().trim().equals(""))
		  			foundProblem.append(this.getBatchSizePreBlendError() + "<br>");
		  	  	 else
		  	  		bdSize = new BigDecimal(this.getBatchSizePreBlend());
		  	  }catch(Exception e){
		  		this.setBatchSizePreBlendError("Problem Found with Batch Size. " + e);
		  		foundProblem.append("Problem Found with Batch Size for Pre Blend<br>");
		  	  }
		  	  if (bdSize.compareTo(new BigDecimal("0")) > 0 && 
		  		  this.getBatchPreBlendUOM().trim().equals(""))
		  	  {
		  		 this.setBatchPreBlendUOMError("If a Batch Size is entered a Unit of Measure must be entered, please choose one");
				 if (!this.getBatchPreBlendUOMError().trim().equals(""))
			       	foundProblem.append(this.getBatchPreBlendUOMError() + "<br>"); 
		  	  }
			}
			
		    if (!foundProblem.toString().trim().equals(""))
		    {
		    	this.setDisplayMessage(foundProblem.toString());
		    	if (this.getStatus().trim().equals("AC") &&
					this.getOriginalStatus().trim().equals("PD") &&
					!this.saveButton.trim().equals(""))
		    	   this.setStatus("PD");
		    }
		  }		
		}
		catch(Exception e)
		{
			System.out.println("what is the exception?" + e);
		}
		return;
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
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
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
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getFormulaDescription() {
		return formulaDescription;
	}
	public void setFormulaDescription(String formulaDescription) {
		this.formulaDescription = formulaDescription;
	}
	public String getFormulaNumber() {
		return formulaNumber;
	}
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getRevisionReason() {
		return revisionReason;
	}
	public void setRevisionReason(String revisionReason) {
		this.revisionReason = revisionReason;
	}
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLineTankItem() {
		return lineTankItem;
	}
	public void setLineTankItem(String lineTankItem) {
		this.lineTankItem = lineTankItem;
	}
	public String getLineTankItemError() {
		return lineTankItemError;
	}
	public void setLineTankItemError(String lineTankItemError) {
		this.lineTankItemError = lineTankItemError;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getCustomerNumberError() {
		return customerNumberError;
	}
	public void setCustomerNumberError(String customerNumberError) {
		this.customerNumberError = customerNumberError;
	}
	public String getFormulaType() {
		return formulaType;
	}
	public void setFormulaType(String formulaType) {
		this.formulaType = formulaType;
	}
	public String getReferenceFormulaNumber() {
		return referenceFormulaNumber;
	}
	public void setReferenceFormulaNumber(String referenceFormulaNumber) {
		this.referenceFormulaNumber = referenceFormulaNumber;
	}
	public String getReferenceFormulaRevisionDate() {
		return referenceFormulaRevisionDate;
	}
	public void setReferenceFormulaRevisionDate(String referenceFormulaRevisionDate) {
		this.referenceFormulaRevisionDate = referenceFormulaRevisionDate;
	}
	public String getReferenceFormulaRevisionTime() {
		return referenceFormulaRevisionTime;
	}
	public void setReferenceFormulaRevisionTime(String referenceFormulaRevisionTime) {
		this.referenceFormulaRevisionTime = referenceFormulaRevisionTime;
	}
	public String getTargetBrix() {
		return targetBrix;
	}
	public void setTargetBrix(String targetBrix) {
		this.targetBrix = targetBrix;
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
	public String getProductionStatus() {
		return productionStatus;
	}
	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus;
	}
	/*
	 * Test and Validate fields for Details of the Formula, 
	 *     after loading them.
	 *     this will over the sections:
	 *        Pre Blend Formulation
	 *        Production Formula
	 *        Cinnamon Pre-blend for Applesauce
	 *        
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 */
	public void populateDetails(HttpServletRequest request) {
		try
		{	
			for (int section = 0; section < 3; section++)
			{
				Vector listDetail = new Vector();
				StringBuffer foundProblem = new StringBuffer();
				int countRecords = 0;
				String screenType = "";
				if (section == 0) // This will be the PreBlend Formulation Section 
				{
					countRecords = new Integer(this.getCountPreBlendDetail()).intValue();
					screenType = "preBlend";
				}
				if (section == 1) // This will be the Production Formula Section 
				{
					countRecords = new Integer(this.getCountProductionDetail()).intValue();
					screenType = "production";
				}
				if (section == 2) // This will be the Applesauce PreBlend Section 
				{
					countRecords = new Integer(this.getCountPreBlendSauceDetail()).intValue();
					screenType = "sauce";
				}
				
				if (countRecords > 0)
				{
				  for (int x = 1; x < (countRecords + 1); x++)	
				  {
					if (request.getParameter(screenType + "removeLine" + x) == null ||
						request.getParameter(screenType + "removeLine" + x).trim().equals(""))  
					{	
						UpdFormulaDetail ufd = new UpdFormulaDetail();
						if (screenType.trim().equals("preBlend"))
						   ufd.setRecordType("PREBL");
						if (screenType.trim().equals("production"))
						   ufd.setRecordType("PROD");
						if (screenType.trim().equals("sauce"))
						   ufd.setRecordType("SAUCE");
						//--------------------------------------------------------------------
						// RETRIEVE
						//-------------------------------------------------------------------
						 // Sequence Number **************************************************
						 ufd.setDetailSequence(request.getParameter(screenType + "detailSequence" + x));
						 if (ufd.getDetailSequence() == null || ufd.getDetailSequence().trim().equals(""))
						   ufd.setDetailSequence(x + "0");
						 // Item Number (1)*******************************************************
						 ufd.setItemNumber1(request.getParameter(screenType + "itemNumber1" + x));
						 if (ufd.getItemNumber1() == null)
						   ufd.setItemNumber1("");
						 // Item Description (1)**************************************************
						 ufd.setItemDescription1(request.getParameter(screenType + "itemDescription1" + x));
						 if (ufd.getItemDescription1() == null)
						   ufd.setItemDescription1("");
						 // Quantity (1) *******************************************************
						 ufd.setQuantity1(request.getParameter(screenType + "quantity1" + x));
						 if (ufd.getQuantity1() == null || ufd.getQuantity1().trim().equals(""))
						   ufd.setQuantity1("0");
						 // Unit of Measure (1) *************************************************
						 ufd.setUnitOfMeasure1(request.getParameter(screenType + "unitOfMeasure1" + x));
						 if (ufd.getUnitOfMeasure1() == null)
						   ufd.setUnitOfMeasure1("");
						 // Supplier Number (M3) **********************************************
						 ufd.setSupplierNumber(request.getParameter(screenType + "supplierNumber" + x));
						 if (ufd.getSupplierNumber() == null)
						   ufd.setSupplierNumber("");
						 // Supplier Name *******************************************************
						 ufd.setSupplierName(request.getParameter(screenType + "supplierName" + x));
						 if (ufd.getSupplierName() == null)
						   ufd.setSupplierName("");
						 //--------------
						 // Sauce ONLY fields
						 if (screenType.trim().equals("sauce"))
						 {
							// Starting Sauce Brix *************************************************
							ufd.setSauceTargetBrix(request.getParameter(screenType + "sauceTargetBrix" + x));
							if (ufd.getSauceTargetBrix() == null || ufd.getSauceTargetBrix().trim().equals(""))
							  ufd.setSauceTargetBrix("0");
							// Sauce Batch Quantity *************************************************
							ufd.setSauceBatchQty(request.getParameter(screenType + "sauceBatchQty" + x));
							if (ufd.getSauceBatchQty() == null || ufd.getSauceBatchQty().trim().equals(""))
							  ufd.setSauceBatchQty("0");
							// Item Number (2)*******************************************************
							ufd.setItemNumber2(request.getParameter(screenType + "itemNumber2" + x));
							if (ufd.getItemNumber2() == null)
							  ufd.setItemNumber2("");
							// Item Description (2)**************************************************
							ufd.setItemDescription2(request.getParameter(screenType + "itemDescription2" + x));
							if (ufd.getItemDescription2() == null)
							  ufd.setItemDescription2("");
							// Quantity (2) *******************************************************
							ufd.setQuantity2(request.getParameter(screenType + "quantity2" + x));
							if (ufd.getQuantity2() == null || ufd.getQuantity2().trim().equals(""))
							  ufd.setQuantity2("0");
							// Unit of Measure (2) *************************************************
							ufd.setUnitOfMeasure2(request.getParameter(screenType + "unitOfMeasure2" + x));
							if (ufd.getUnitOfMeasure2() == null)
							  ufd.setUnitOfMeasure2("");	 
							// Item Number (3)*******************************************************
							ufd.setItemNumber3(request.getParameter(screenType + "itemNumber3" + x));
							if (ufd.getItemNumber3() == null)
							  ufd.setItemNumber3("");
							// Item Description (3)**************************************************
							ufd.setItemDescription3(request.getParameter(screenType + "itemDescription3" + x));
							if (ufd.getItemDescription3() == null)
							  ufd.setItemDescription3("");
							// Quantity (3) *******************************************************
							ufd.setQuantity3(request.getParameter(screenType + "quantity3" + x));
							if (ufd.getQuantity3() == null || ufd.getQuantity3().trim().equals(""))
							  ufd.setQuantity3("0");
							// Unit of Measure (3) *************************************************
							ufd.setUnitOfMeasure3(request.getParameter(screenType + "unitOfMeasure3" + x));
							if (ufd.getUnitOfMeasure3() == null)
							  ufd.setUnitOfMeasure3("");						 
						 }
						// will only allow it to stay in the list if there is data in one of the fields
						// else it does not get included in the list
						 //*** must have one -- Item, Description Qty, Supplier Name
						  // OR won't build the record - or validate the entries
						  if (!ufd.getItemNumber1().trim().equals("") ||
							  !ufd.getItemNumber2().trim().equals("") ||
							  !ufd.getItemNumber3().trim().equals("") ||
							  !ufd.getItemDescription1().trim().equals("") ||
							  !ufd.getItemDescription2().trim().equals("") ||
							  !ufd.getItemDescription2().trim().equals("") ||
						      !ufd.getQuantity1().trim().equals("0") ||
						      !ufd.getQuantity2().trim().equals("0") ||
						      !ufd.getQuantity3().trim().equals("0"))
						  {
						  // Basic Fields *****************************************************
							 ufd.setCompany(this.getCompany());
							 ufd.setDivision(this.getDivision());
							 ufd.setEnvironment(this.getEnvironment());
						  	 ufd.setUpdateUser(this.getUpdateUser());
						  	 ufd.setFormulaNumber(this.getFormulaNumber());
						  	 ufd.setRevisionDate(this.getRevisionDate());
						  	 ufd.setRevisionTime(this.getRevisionTime());
						  //--------------------------------------------------------------------
						  // VALIDATE
						  //-------------------------------------------------------------------
						  	 ufd.validate();
						  	 if (!ufd.getDisplayMessage().trim().equals(""))
						  		 foundProblem.append(ufd.getDisplayMessage());
						  	 listDetail.addElement(ufd);		
						  }// end of the allow a record to be added to the LIST
					}// if it does not say removeLine
				  } // for the records
				}// are there records to view?
				if (screenType.trim().equals("preBlend"))
				   this.setListPreBlendDetail(listDetail);
				if (screenType.trim().equals("production"))
				   this.setListProductionDetail(listDetail);
				if (screenType.trim().equals("sauce"))
				   this.setListPreBlendSauceDetail(listDetail);
				this.setDisplayMessage(this.getDisplayMessage() + foundProblem.toString().trim());	
			}// end of the For "section"
		}
		catch(Exception e)
		{
		   System.out.println("Caught Problem within the UpdQAFormula.populateDetails():" + e);	
		}
		return;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (7/20/2010 TWalton)
	 */
	public void loadFromBeanQuality(BeanQuality bq) {
		try {
		    this.updBean						= bq;
			QaFormula header = bq.getFormula();
			this.formulaNumber  				= header.getFormulaNumber();
			this.revisionDate   				= header.getRevisionDate();
			this.revisionTime   				= header.getRevisionTime();
			this.setCompany(header.getCompanyNumber());
			this.setDivision(header.getDivisionNumber());
			this.status 						= header.getStatusCode(); 
			this.originalStatus					= header.getStatusCode();
			this.formulaName					= header.getFormulaName();
			this.formulaDescription 			= header.getFormulaDescription();
			this.revisionReason 				= header.getRevisionReasonText();
			this.productionStatus 				= header.getProductionStatus();
			this.lineTankItem 					= header.getLineTankItem();
			this.customerCode					= header.getCustomerCode();
			this.customerNumber					= header.getCustomerNumber();
			this.customerName 					= header.getCustomerName();
			this.formulaType					= header.getTypeCode();
			this.originationUser				= header.getOriginationUser();
			this.groupCode						= header.getGroupingCode();
			this.scopeCode						= header.getScopeCode();
			this.approvedByUser					= header.getApprovedByUser();
			this.fruitOrigin					= header.getFruitOrigin();
			
			this.targetBrix						= header.getTargetBrix();
			this.batchSize						= header.getBatchQuantity();
			this.batchUOM						= header.getBatchUnitOfMeasure();
			
			this.batchSizePreBlend				= header.getBatchQuantityPreBlend();
			this.batchPreBlendUOM				= header.getBatchUOMPreBlend();
				
			this.creationDate 					= header.getCreatedDate();
			this.creationTime 					= header.getCreatedTime();
			this.creationUser 					= header.getCreatedUser();
			
			this.updateDate						= header.getUpdatedDate();
			this.updateTime						= header.getUpdatedTime();
			this.updateUser						= header.getUpdatedUser();
			
			this.referenceFormulaNumber 		= header.getReferenceFormulaNumber();
			this.referenceFormulaRevisionDate 	= header.getReferenceFormulaRevDate();
			this.referenceFormulaRevisionTime 	= header.getReferenceFormulaRevTime();
		//  Detail Sections //
		   //-------------------------------------------------------------
		   // Pre-Blend Formulation
		   //-------------------------------------------------------------	
			  Vector<UpdFormulaDetail> preBlend = new Vector<UpdFormulaDetail>();
			  if (bq.getFormulaPreBlend().size() > 0){
				 for (int x = 0; x < bq.getFormulaPreBlend().size(); x++){
					UpdFormulaDetail updDetail = new UpdFormulaDetail();
					try{
						QaFormulaDetail formDetail =  (QaFormulaDetail) bq.getFormulaPreBlend().elementAt(x);
						updDetail.setFormulaNumber(this.getFormulaNumber().trim());
						updDetail.setRevisionDate(this.getRevisionDate().trim());
						updDetail.setRevisionTime(this.getRevisionTime().trim());
						updDetail.setDetailSequence(formDetail.getSequenceNumber().trim());
						updDetail.setItemNumber1(formDetail.getItemNumber1().trim());
						updDetail.setItemDescription1(formDetail.getItemDescription1().trim());
						updDetail.setQuantity1(formDetail.getItemQuantity1().trim());
						updDetail.setUnitOfMeasure1(formDetail.getItemUnitOfMeasure1().trim());
						updDetail.setSupplierNumber(formDetail.getSupplierNumber().trim());
						updDetail.setSupplierName(formDetail.getSupplierName().trim());
					}catch(Exception e)
					{}
					preBlend.addElement(updDetail);
				 }
			  }
			  this.setListPreBlendDetail(preBlend);
		   //-------------------------------------------------------------
		   // Production Formula
		   //-------------------------------------------------------------			
			  Vector<UpdFormulaDetail> productionBlend = new Vector<UpdFormulaDetail>();
			  if (bq.getFormulaProduction().size() > 0){
				 for (int x = 0; x < bq.getFormulaProduction().size(); x++){
					UpdFormulaDetail updDetail = new UpdFormulaDetail();
					try{
						QaFormulaDetail formDetail =  (QaFormulaDetail) bq.getFormulaProduction().elementAt(x);
						updDetail.setFormulaNumber(this.getFormulaNumber().trim());
						updDetail.setRevisionDate(this.getRevisionDate().trim());
						updDetail.setRevisionTime(this.getRevisionTime().trim());
						updDetail.setDetailSequence(formDetail.getSequenceNumber().trim());
						updDetail.setItemNumber1(formDetail.getItemNumber1().trim());
						updDetail.setItemDescription1(formDetail.getItemDescription1().trim());
						updDetail.setQuantity1(formDetail.getItemQuantity1().trim());
						updDetail.setUnitOfMeasure1(formDetail.getItemUnitOfMeasure1().trim());
						updDetail.setSupplierNumber(formDetail.getSupplierNumber().trim());
						updDetail.setSupplierName(formDetail.getSupplierName().trim());
					}catch(Exception e)
					{}
					productionBlend.addElement(updDetail);
				 }
			  }
			  this.setListProductionDetail(productionBlend);			  
		   //-------------------------------------------------------------
		   // Fruit Varieties Include and Exclude
		   //-------------------------------------------------------------
			  Vector<UpdVariety> includeVariety = new Vector<UpdVariety>();
			  if (bq.getVarietiesIncluded().size() > 0){
				 for (int x = 0; x < bq.getVarietiesIncluded().size(); x++){
					UpdVariety updVar = new UpdVariety();
					try{
						QaFruitVariety formVar =  (QaFruitVariety) bq.getVarietiesIncluded().elementAt(x);
						updVar.setRecordID(this.getFormulaNumber().trim());
						updVar.setRevisionDate(this.getRevisionDate().trim());
						updVar.setRevisionTime(this.getRevisionTime().trim());
						updVar.setCropModel(formVar.getAttributeCropModel().trim());
						updVar.setCropModelDescription(formVar.getAttributeCropModelDescription().trim());
						updVar.setVariety(formVar.getFruitVarietyValue().trim());
						updVar.setVarietyDescription(formVar.getFruitVarietyDescription().trim());
						updVar.setPercentage(formVar.getIncludePercentage().trim());
						updVar.setMinimumPercent(formVar.getIncludeMinimumPercent().trim());
						updVar.setMaximumPercent(formVar.getIncludeMaximumPercent().trim());
					}catch(Exception e)
					{}
					includeVariety.addElement(updVar);
				 }
			  }
			  this.setListVarietiesIncluded(includeVariety);	  
		  //-------------------------------------------------------------
		  // Fruit Varieties Include and Exclude
		  //-------------------------------------------------------------
			  Vector<UpdVariety> excludeVariety = new Vector<UpdVariety>();
			  if (bq.getVarietiesExcluded().size() > 0){
				 for (int x = 0; x < bq.getVarietiesExcluded().size(); x++){
					UpdVariety updVar = new UpdVariety();
					try{
						QaFruitVariety formVar =  (QaFruitVariety) bq.getVarietiesExcluded().elementAt(x);
						updVar.setRecordID(this.getFormulaNumber().trim());
						updVar.setRevisionDate(this.getRevisionDate().trim());
						updVar.setRevisionTime(this.getRevisionTime().trim());
						updVar.setCropModel(formVar.getAttributeCropModel().trim());
						updVar.setCropModelDescription(formVar.getAttributeCropModelDescription().trim());
						updVar.setVariety(formVar.getFruitVarietyValue().trim());
						updVar.setVarietyDescription(formVar.getFruitVarietyDescription().trim());
					}catch(Exception e)
					{}
					excludeVariety.addElement(updVar);
				 }
			  }
			  this.setListVarietiesExcluded(excludeVariety);	  
		   //-------------------------------------------------------------
		   // Raw Fruit Details - Tests
		   //-------------------------------------------------------------
		      Vector<UpdTestParameters> rfTests = new Vector<UpdTestParameters>();
		      if (bq.getFormulaRawFruitTests().size() > 0)
		      {
			    for (int x = 0; x < bq.getFormulaRawFruitTests().size(); x++)
			    {
				   UpdTestParameters utp = new UpdTestParameters();
				   QaTestParameters qtp = (QaTestParameters) bq.getFormulaRawFruitTests().elementAt(x);
				   utp.setRecordID(this.getFormulaNumber().trim());
				   utp.setRevisionDate(this.getRevisionDate().trim());
				   utp.setRevisionTime(this.getRevisionTime().trim());
				   utp.setAttributeID(qtp.getAttributeIdentity().trim());
				   utp.setAttributeIDSequence(qtp.getSequenceNumber().trim());
				   if (utp.getAttributeID().trim().equals("Water Addition"))
					   utp.setUnitOfMeasure("GALLONS");
				   else{
					   if (utp.getAttributeID().trim().equals("Decay"))
						   utp.setUnitOfMeasure("PERCENT");
					   else
				           utp.setUnitOfMeasure(qtp.getUnitOfMeasure().trim());
				   }
				   utp.setTarget(qtp.getTargetValue());
				   utp.setMinimum(qtp.getMinimumStandard());
				   utp.setMaximum(qtp.getMaximumStandard());
				   utp.setMethod(qtp.getMethodNumber().trim());
				   utp.setDefaultOnCOA(qtp.getDefaultOnCOA().trim());
				   rfTests.add(utp);
			    }
		      } else {
			    UpdTestParameters utp = new UpdTestParameters();
			    utp.setAttributeID("Pressure");
			    utp.setAttributeIDSequence("10");
			    rfTests.add(utp);
			    utp = new UpdTestParameters();
			    utp.setAttributeID("Decay");
			    utp.setAttributeIDSequence("20");
			    rfTests.add(utp);
//			    utp = new UpdTestParameters();
//			    utp.setAttributeID("Acid");
//			    utp.setAttributeIDSequence("30");
//			    rfTests.add(utp);
			    utp = new UpdTestParameters();
			    utp.setAttributeID("Water Addition");
			    utp.setAttributeIDSequence("40");
			    rfTests.add(utp);
		      }
		      this.setListRawFruitAttributes(rfTests);
		   //-------------------------------------------------------------
		   //Cinnamon Pre-Blend for Applesauce
		   //-------------------------------------------------------------
		      Vector<UpdFormulaDetail> saucePreBlend = new Vector<UpdFormulaDetail>();
			  if (bq.getFormulaPreBlendSauce().size() > 0){
				 for (int x = 0; x < bq.getFormulaPreBlendSauce().size(); x++){
					UpdFormulaDetail updDetail = new UpdFormulaDetail();
					try{
						QaFormulaDetail formDetail =  (QaFormulaDetail) bq.getFormulaPreBlendSauce().elementAt(x);
						updDetail.setFormulaNumber(this.getFormulaNumber().trim());
						updDetail.setRevisionDate(this.getRevisionDate().trim());
						updDetail.setRevisionTime(this.getRevisionTime().trim());
						updDetail.setDetailSequence(formDetail.getSequenceNumber().trim());
						updDetail.setSauceTargetBrix(formDetail.getSauceTargetBrix().trim());
						updDetail.setItemNumber1(formDetail.getItemNumber1().trim());
						updDetail.setItemDescription1(formDetail.getItemDescription1().trim());
						updDetail.setQuantity1(formDetail.getItemQuantity1().trim());
						updDetail.setUnitOfMeasure1(formDetail.getItemUnitOfMeasure1().trim());
						updDetail.setItemNumber2(formDetail.getItemNumber2().trim());
						updDetail.setItemDescription2(formDetail.getItemDescription2().trim());
						updDetail.setQuantity2(formDetail.getItemQuantity2().trim());
						updDetail.setUnitOfMeasure2(formDetail.getItemUnitOfMeasure2().trim());
						updDetail.setItemNumber3(formDetail.getItemNumber3().trim());
						updDetail.setItemDescription3(formDetail.getItemDescription3().trim());
						updDetail.setQuantity3(formDetail.getItemQuantity3().trim());
						updDetail.setUnitOfMeasure3(formDetail.getItemUnitOfMeasure3().trim());
						updDetail.setSauceBatchQty(formDetail.getSauceBatchQuantity().trim());
					}catch(Exception e)
					{}
					saucePreBlend.addElement(updDetail);
				 }
			  }
			  this.setListPreBlendSauceDetail(saucePreBlend);	
			
		} catch (Exception e) {
		   System.out.println("Error Caught in UpdFormula.loadFromBeanQuality(BeanQuality: " + e);
		}
		return;
	}
		public String getCustomerCode() {
			return customerCode;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		public BeanQuality getUpdBean() {
			return updBean;
		}
		public void setUpdBean(BeanQuality updBean) {
			this.updBean = updBean;
		}
		public String getOriginalStatus() {
			return originalStatus;
		}
		public void setOriginalStatus(String originalStatus) {
			this.originalStatus = originalStatus;
		}
		public String getFormulaDescriptionError() {
			return formulaDescriptionError;
		}
		public void setFormulaDescriptionError(String formulaDescriptionError) {
			this.formulaDescriptionError = formulaDescriptionError;
		}
		public String getBatchSize() {
			return batchSize;
		}
		public void setBatchSize(String batchSize) {
			this.batchSize = batchSize;
		}
		public String getBatchUOM() {
			return batchUOM;
		}
		public void setBatchUOM(String batchUOM) {
			this.batchUOM = batchUOM;
		}
		public String getBatchSizeError() {
			return batchSizeError;
		}
		public void setBatchSizeError(String batchSizeError) {
			this.batchSizeError = batchSizeError;
		}
		public String getCustomerCodeError() {
			return customerCodeError;
		}
		public void setCustomerCodeError(String customerCodeError) {
			this.customerCodeError = customerCodeError;
		}
		public String getBatchUOMError() {
			return batchUOMError;
		}
		public void setBatchUOMError(String batchUOMError) {
			this.batchUOMError = batchUOMError;
		}
		public Vector getListCalculations() {
			return listCalculations;
		}
		public void setListCalculations(Vector listCalculations) {
			this.listCalculations = listCalculations;
		}
		public Vector getListComments() {
			return listComments;
		}
		public void setListComments(Vector listComments) {
			this.listComments = listComments;
		}
		public Vector getListDetails() {
			return listDetails;
		}
		public void setListDetails(Vector listDetails) {
			this.listDetails = listDetails;
		}
		public String getTargetBrixError() {
			return targetBrixError;
		}
		public void setTargetBrixError(String targetBrixError) {
			this.targetBrixError = targetBrixError;
		}
		public String getApprovedByUser() {
			return approvedByUser;
		}
		public void setApprovedByUser(String approvedByUser) {
			this.approvedByUser = approvedByUser;
		}
		public String getFormulaName() {
			return formulaName;
		}
		public void setFormulaName(String formulaName) {
			this.formulaName = formulaName;
		}
		public String getGroupCode() {
			return groupCode;
		}
		public void setGroupCode(String groupCode) {
			this.groupCode = groupCode;
		}
		public String getOriginationUser() {
			return originationUser;
		}
		public void setOriginationUser(String originationUser) {
			this.originationUser = originationUser;
		}
		public String getScopeCode() {
			return scopeCode;
		}
		public void setScopeCode(String scopeCode) {
			this.scopeCode = scopeCode;
		}
		public String getBatchPreBlendUOM() {
			return batchPreBlendUOM;
		}
		public void setBatchPreBlendUOM(String batchPreBlendUOM) {
			this.batchPreBlendUOM = batchPreBlendUOM;
		}
		public String getBatchPreBlendUOMError() {
			return batchPreBlendUOMError;
		}
		public void setBatchPreBlendUOMError(String batchPreBlendUOMError) {
			this.batchPreBlendUOMError = batchPreBlendUOMError;
		}
		public String getBatchSizePreBlend() {
			return batchSizePreBlend;
		}
		public void setBatchSizePreBlend(String batchSizePreBlend) {
			this.batchSizePreBlend = batchSizePreBlend;
		}
		public String getBatchSizePreBlendError() {
			return batchSizePreBlendError;
		}
		public void setBatchSizePreBlendError(String batchSizePreBlendError) {
			this.batchSizePreBlendError = batchSizePreBlendError;
		}
		public String getFruitOrigin() {
			return fruitOrigin;
		}
		public void setFruitOrigin(String fruitOrigin) {
			this.fruitOrigin = fruitOrigin;
		}
		public String getCountPreBlendDetail() {
			return countPreBlendDetail;
		}
		public void setCountPreBlendDetail(String countPreBlendDetail) {
			this.countPreBlendDetail = countPreBlendDetail;
		}
		public String getCountPreBlendSauceDetail() {
			return countPreBlendSauceDetail;
		}
		public void setCountPreBlendSauceDetail(String countPreBlendSauceDetail) {
			this.countPreBlendSauceDetail = countPreBlendSauceDetail;
		}
		public String getCountProductionDetail() {
			return countProductionDetail;
		}
		public void setCountProductionDetail(String countProductionDetail) {
			this.countProductionDetail = countProductionDetail;
		}
		public Vector getListBlendingInstructions() {
			return listBlendingInstructions;
		}
		public void setListBlendingInstructions(Vector listBlendingInstructions) {
			this.listBlendingInstructions = listBlendingInstructions;
		}
		public Vector getListIngredientStatement() {
			return listIngredientStatement;
		}
		public void setListIngredientStatement(Vector listIngredientStatement) {
			this.listIngredientStatement = listIngredientStatement;
		}
		public Vector getListKeyLabelStatements() {
			return listKeyLabelStatements;
		}
		public void setListKeyLabelStatements(Vector listKeyLabelStatements) {
			this.listKeyLabelStatements = listKeyLabelStatements;
		}
		public Vector<UpdFormulaDetail> getListPreBlendDetail() {
			return listPreBlendDetail;
		}
		public void setListPreBlendDetail(Vector<UpdFormulaDetail> listPreBlendDetail) {
			this.listPreBlendDetail = listPreBlendDetail;
		}
		public Vector<UpdFormulaDetail> getListPreBlendSauceDetail() {
			return listPreBlendSauceDetail;
		}
		public void setListPreBlendSauceDetail(
				Vector<UpdFormulaDetail> listPreBlendSauceDetail) {
			this.listPreBlendSauceDetail = listPreBlendSauceDetail;
		}
		public Vector<UpdFormulaDetail> getListProductionDetail() {
			return listProductionDetail;
		}
		public void setListProductionDetail(
				Vector<UpdFormulaDetail> listProductionDetail) {
			this.listProductionDetail = listProductionDetail;
		}
		public String getRevisionReasonError() {
			return revisionReasonError;
		}
		public void setRevisionReasonError(String revisionReasonError) {
			this.revisionReasonError = revisionReasonError;
		}
		public String getCountVarietiesExcluded() {
			return countVarietiesExcluded;
		}
		public void setCountVarietiesExcluded(String countVarietiesExcluded) {
			this.countVarietiesExcluded = countVarietiesExcluded;
		}
		public String getCountVarietiesIncluded() {
			return countVarietiesIncluded;
		}
		public void setCountVarietiesIncluded(String countVarietiesIncluded) {
			this.countVarietiesIncluded = countVarietiesIncluded;
		}
		public Vector<UpdVariety> getListVarietiesExcluded() {
			return listVarietiesExcluded;
		}
		public void setListVarietiesExcluded(Vector<UpdVariety> listVarietiesExcluded) {
			this.listVarietiesExcluded = listVarietiesExcluded;
		}
		public Vector<UpdVariety> getListVarietiesIncluded() {
			return listVarietiesIncluded;
		}
		public void setListVarietiesIncluded(Vector<UpdVariety> listVarietiesIncluded) {
			this.listVarietiesIncluded = listVarietiesIncluded;
		}
		public String getCountRawFruitAttributes() {
			return countRawFruitAttributes;
		}
		public void setCountRawFruitAttributes(String countRawFruitAttributes) {
			this.countRawFruitAttributes = countRawFruitAttributes;
		}
		public Vector<UpdTestParameters> getListRawFruitAttributes() {
			return listRawFruitAttributes;
		}
		public void setListRawFruitAttributes(
				Vector<UpdTestParameters> listRawFruitAttributes) {
			this.listRawFruitAttributes = listRawFruitAttributes;
		}
		public Vector getListRFAdditionalInfo() {
			return listRFAdditionalInfo;
		}
		public void setListRFAdditionalInfo(Vector listRFAdditionalInfo) {
			this.listRFAdditionalInfo = listRFAdditionalInfo;
		}
		public String getFormulaNameError() {
			return formulaNameError;
		}
		public void setFormulaNameError(String formulaNameError) {
			this.formulaNameError = formulaNameError;
		}
		public String getFormulaTypeError() {
			return formulaTypeError;
		}
		public void setFormulaTypeError(String formulaTypeError) {
			this.formulaTypeError = formulaTypeError;
		}
		public DateTime getSupersedesDate() {
			return supersedesDate;
		}
		public void setSupersedesDate(DateTime supersedesDate) {
			this.supersedesDate = supersedesDate;
		}
		public String getCustomerOrSupplierItemNumber() {
			return customerOrSupplierItemNumber;
		}
		public void setCustomerOrSupplierItemNumber(String customerOrSupplierItemNumber) {
			this.customerOrSupplierItemNumber = customerOrSupplierItemNumber;
		}
}
