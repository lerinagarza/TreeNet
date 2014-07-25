/*
 * Created on May 25, 2010
 * 
 */

package com.treetop.app.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanItem;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.businessobjects.QaSpecification;
import com.treetop.businessobjects.QaSpecificationPackaging;
import com.treetop.businessobjects.QaTestParameters;
import com.treetop.businessobjects.QaFruitVariety;
import com.treetop.businessobjects.DateTime;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceQuality;
import com.treetop.services.ServiceAttribute;
import com.treetop.services.ServiceCustomer;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.html.*;

/**
 * @author twalto
 * 
 */
public class UpdSpecification extends BaseViewBeanR2 {
	
	// Must have in Update View Bean
	public String updateUser 					= "";
	public String updateDate 					= "0";
	public String updateTime 					= "0";
	public String saveButton 					= ""; // Comment Sections
	public String submit						= ""; // Update of Specification
	public String readOnly 						= "N";
	
	// Generate cannot update
	public String specNumber 					= "";
	public String revisionDate 					= "";
	public String revisionTime 					= "";
	public DateTime supersedesDate 				= new DateTime();
	
//	 additional values sent -- push through on a new revision
	public String creationDate 					= "0";
	public String creationTime 					= "0";
	public String creationUser 					= "";
	public String referenceSpecNumber 			= "0";
	public String referenceSpecRevisionDate 	= "0";
	public String referenceSpecRevisionTime  	= "0";	
	
	// Fields Available for Update
	public String originalStatus 				= "";
	public String status 						= "";
	public String specType 						= "";
	public String specTypeError					= ""; // For display of issues with Type
	public String specName						= "";
	public String specNameError					= ""; // For display of issues with Name
	public String specDescription				= "";
	public String specDescriptionError			= ""; // For display of issues with Description
	public String originationUser				= "";
	public String groupCode						= "";
	public String scopeCode						= "";
	public String approvedByUser				= "";	
	public String revisionReason				= "";
	public String revisionReasonError			= ""; // For display of issues with Reason
	public String productionStatus				= "";
	public String itemNumber					= ""; 
	public String itemNumberError				= ""; // For display of issues with Item
	public String formulaNumber 				= "0";
	public String formulaNumberError			= "";
	public String customerCode					= "";
	public String customerCodeError				= "";
	public String customerNumber				= "";
	public String customerNumberError			= "";
	public String customerName					= "";
	
	public String cutSize						= "";
	public String cutSize2						= "";
	public String screenSize					= "";
	public String foreignMaterialDetection		= "";
	
	public String kosherStatusCode				= ""; 
	public String kosherSymbol					= "";
	public String kosherSymbolRequired			= ""; //Y or N
	public String inlineSockRequired			= "";
	public String cipType						= "";
	public String testBrix						= "0"; // Analytical Testing section
	public String testBrixError					= "";
	public String storageRecommendation			= "";
	public String containerTamperSeal			= ""; //Y or N
	public String containerCodeLocation			= "";
	public String containerCodeFontSize			= "";
	public String cartonCodeLocation			= "";
	public String cartonCodeFontSize			= "";
	public String caseCodeFontSize				= "";
	public String caseShowBarCode				= ""; //Y or N
	public String palletLabelType				= "";
	public String palletLabelLocation			= "";
	public String stretchWrapRequired			= ""; //Y or N
	public String stretchWrapType				= "";
	public String stretchWrapTypeError			= ""; // display issues dealing with stretch wrap type
	public String stretchWrapWidth				= "0";
	public String stretchWrapWidthError			= ""; // display issues dealing with stretch wrap width
	public String stretchWrapWidthUOM			= "";
	public String stretchWrapWidthUOMError		= ""; // display issues dealing with unit of measure for stretch wrap width
	public String stretchWrapGauge				= "0";
	public String stretchWrapGaugeError			= ""; // display issues dealing with stretch wrap gauge
	public String stretchWrapGaugeUOM			= "";
	public String stretchWrapGaugeUOMError		= ""; // display issues dealing with unit of measure for stretch wrap gauge
	public String shrinkWrapRequired			= ""; //Y or N
	public String shrinkWrapType				= "";
	public String shrinkWrapTypeError			= ""; // display issues dealing with shrink wrap type
	public String shrinkWrapWidth				= "0";
	public String shrinkWrapWidthError			= ""; // display issues dealing with shrink wrap width
	public String shrinkWrapWidthUOM			= "";
	public String shrinkWrapWidthUOMError		= ""; // display issues dealing with unit of measure for shrink wrap width	
	public String shrinkWrapThickness			= "0"; 
	public String shrinkWrapThicknessError		= ""; // display issues dealing with shrink wrap width
	public String shrinkWrapThicknessUOM		= "";
	public String shrinkWrapThicknessUOMError	= ""; // display issues dealing with unit of measure for shrink wrap thickness	
	public String slipSheetRequired				= ""; //Y or N
	public String slipSheetRequiredError		= ""; // display issues dealing with Slip Sheets
	public String slipSheetBottom				= "";
	public String slipSheetLayer1				= "";
	public String slipSheetLayer2				= "";
	public String slipSheetLayer3				= "";
	public String slipSheetLayer4				= "";
	public String slipSheetLayer5				= "";
	public String slipSheetLayer6				= "";
	public String slipSheetLayer7				= "";
	public String slipSheetLayer8				= "";
	public String slipSheetLayer9      			= "";
	public String slipSheetLayer10				= "";
	public String slipSheetTop					= "";
	public String palletRequirement				= "";
	
	public String unitsPerPallet				= "0";
	public String unitsPerPalletError			= ""; // will display issues dealing with Units per Pallet
	public String unitsPerLayer					= "0";
	public String unitsPerLayerError			= ""; // will display issues dealing with Units per Layer
	
	public String shelfLifeNotValid				= "";
	public String reconstitutionRatio			= "";
	public String countryOfOrigin				= "";
	
	
	// will be stored in the comment file with other URLS
	public String nutritionPanelURL				= ""; //SpecRevisionUrl1
	public String nutritionPanelURLRemove		= "";
	public String palletPatternURL				= ""; //SpecRevisionUrl2
	public String palletPatternURLRemove		= ""; 
	public String exampleLabelURL				= ""; //SpecRevisionUrl3 
	public String exampleLabelURLRemove			= "";
	
		
	// Vector of Values for Drop Down Lists - Built ONLY ONCE
	public Vector<DropDownSingle> ddMethod 					= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddFormula					= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddAnalyticalTest 			= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddAdditivesPreservatives	= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddMicroTest				= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddProcessParameter 		= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddKosherStatus			= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddInlineSock				= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddCIPType					= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddStretchWrapType			= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddShrinkWrapType			= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddContainerCodeLocation	= new Vector<DropDownSingle>();	
	public Vector<DropDownSingle> ddPalletLabelType			= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddPalletLabelLocation		= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddStorageCondition		= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddPalletRequirement		= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddCutSize					= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddScreenSize				= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddForeignMatDetection		= new Vector<DropDownSingle>();
	public Vector<DropDownSingle> ddCartonCodeLocation		= new Vector<DropDownSingle>();
	
	// Dealing with UpdTestParameters
	public Vector<UpdTestParameters> listAnalyticalTests 			= new Vector<UpdTestParameters>();
	public String countAnalyticalTests 								= "0";
	public Vector<UpdTestParameters> listMicroTests 				= new Vector<UpdTestParameters>();
	public String countMicroTests 									= "0";
	public Vector<UpdTestParameters> listProcessParameters 			= new Vector<UpdTestParameters>(); 
	public String countProcessParameters 							= "0";
	public Vector<UpdTestParameters> listAdditivesAndPreservatives 	= new Vector<UpdTestParameters>(); 
	public String countAdditivesAndPreservatives					= "0";
	
	// Dealing with UpdVariety
	public Vector<UpdVariety> listVarietiesIncluded 				= new Vector<UpdVariety>(); 
	public String countVarietiesIncluded 							= "0";
	public Vector<UpdVariety> listVarietiesExcluded 				= new Vector<UpdVariety>();
	public String countVarietiesExcluded 							= "";	
		
    // Will be processed through the Comment program
	public Vector listSpecUrls 							= new Vector();
	
	public Vector listComments 							= new Vector(); // revision information
	public Vector listAnalyticalTestComments			= new Vector(); // comment1
	public Vector listProcessParameterComments			= new Vector(); // comment2
	public Vector listMicroTestComments					= new Vector(); // comment3
	public Vector listAdditivesAndPreservativeComments	= new Vector(); // comment4
	public Vector listContainerPrintByLine				= new Vector(); // comment5
	public Vector listContainerPrintAdditional			= new Vector(); // comment6
	public Vector listCasePrintByLine					= new Vector(); // comment7
	public Vector listCasePrintAdditional				= new Vector(); // comment8
	public Vector listPalletPrintByLine					= new Vector(); // comment9
	public Vector listPalletPrintAdditional				= new Vector(); // comment10
	public Vector listLabelPrintByLine					= new Vector(); // comment11
	public Vector listLabelPrintAdditional				= new Vector(); // comment12
	public Vector listShelfLifeRequirements				= new Vector(); // comment13
	public Vector listStorageRequirements				= new Vector(); // comment14 - may remove?
	public Vector listFinishedPalletAdditional			= new Vector(); // comment15
	public Vector listFruitVarietiesAdditional			= new Vector(); // comment16
	public Vector listShippingRequirements				= new Vector(); // comment17
	public Vector listCOARequirements					= new Vector(); // comment18 
	public Vector listCartonPrintByLine					= new Vector(); // comment19
	public Vector listCartonPrintAdditional				= new Vector(); // comment20
	public Vector listProductDescription				= new Vector(); // comment21
	public Vector listIngredientStatement				= new Vector(); // comment22
	public Vector listIntendedUse						= new Vector(); // comment23
	public Vector listForeignMatter						= new Vector(); // comment24
	public Vector listCodingRequirementsAdditional		= new Vector(); // comment25
	
	public BeanQuality updBean 							= new BeanQuality();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		  if (this.getEnvironment().trim().equals(""))
			this.setEnvironment("PRD");
		  if (!this.submit.trim().equals(""))
		  {
		//----------------------------------------------------------------------  
		// VALIDATE NEEDED/REQUIRED FIELDS   
		//----------------------------------------------------------------------	
			  StringBuffer foundProblem = new StringBuffer();
			  
			  CommonRequestBean crb = new CommonRequestBean();
			  crb.setEnvironment(this.getEnvironment());
			  crb.setCompanyNumber(this.getCompany());
			  crb.setDivisionNumber(this.getDivision());
			  //***********************************************************************
			  // Must HAVE when moving from Pending to Active
			  if (this.getStatus().trim().equals("AC") &&
				  this.getOriginalStatus().trim().equals("PD"))
			  {
				  //------------------------------------------------------------------
				  // Item Number
				  if (this.getItemNumber().trim().equals(""))
				  {	  
					 this.setItemNumberError("MUST enter an Item Number before Changing to Active");
				     foundProblem.append(this.getItemNumberError() + "<br>");
				  }
				  //-------------------------------------------------------------------
				  // Specification Name
				  if (this.getSpecName().trim().equals(""))
				  {	  
					 this.setSpecNameError("MUST enter a Specification Number before Changing to Active");
				     foundProblem.append(this.getSpecNameError() + "<br>");
				  }
				  //-------------------------------------------------------------------
				  // Specification Description
				  if (this.getSpecDescription().trim().equals(""))
				  {
					  this.setSpecDescriptionError("MUST enter a Specification Name before Changing to Active");
				     foundProblem.append(this.getSpecDescriptionError() + "<br>");
				  }
				  //------------------------------------------------------------------
				  // Revision Reason
				  if (this.revisionReason.trim().equals(""))
				  {
				     this.setRevisionReasonError("Revision Reason MUST be filled in before Changing to Active");
					 foundProblem.append(this.getRevisionReasonError().trim());
				  }
				  if (!foundProblem.toString().trim().equals(""))
					 this.setStatus(this.getOriginalStatus());
				}
			  //***********************************************************************
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
					  foundProblem.append(this.getCustomerCodeError() + "<br>");
				  }
			  }	
			  //-------------------------------------------------------------------
			  // Formula
			  if (!this.getFormulaNumber().trim().equals("") && !this.getFormulaNumber().trim().equals("0"))
			  {
				 this.setFormulaNumberError(validateInteger(this.formulaNumber));  
		  		 if (this.getFormulaNumberError().trim().equals(""))
		  		 { 
				    crb.setIdLevel1(this.formulaNumber);
				    this.setFormulaNumberError(ServiceQuality.verifyFormula(crb));
		  		 }
				 if (!this.getFormulaNumberError().trim().equals(""))
				    foundProblem.append(this.getFormulaNumberError() + "<br>");
			  }	
			  else
				 this.setFormulaNumber("0");
			//-------------------------------------------------------------------
			  // Item Number
			  if (!this.getItemNumber().trim().equals(""))
			  {
				 crb.setIdLevel1(this.getItemNumber()); 
				 this.setItemNumberError(ServiceItem.verifyItem(crb));  
				 if (this.getSpecDescription().trim().equals("") &&
					 this.getItemNumberError().trim().equals(""))
				 {
					 // Populate the Spec Description with the item description
					 try{
					    BeanItem bi = ServiceItem.buildNewItem(this.getEnvironment(), this.getItemNumber());
					    this.setSpecDescription(bi.getItemClass().getItemDescription().trim());
					 }catch(Exception e){}
				 }
			  }
			  if (!this.getItemNumberError().trim().equals(""))
			    foundProblem.append(this.getItemNumberError() + "<br>");
			  //------------------------------------------------------------------------------------
			  // Is Slip Sheet Required?
			  if (!this.getSlipSheetRequired().trim().equals("")) // was the box checked
				 this.setSlipSheetRequired("Y");
			  //------------------------------------------------------------------------------------
			  // Additional Slip Sheet Information
			  if (!this.getSlipSheetBottom().trim().equals("")) // was the box checked
				 this.setSlipSheetBottom("Y");
			  if (!this.getSlipSheetTop().trim().equals("")) // was the box checked
				 this.setSlipSheetTop("Y");
			  if (!this.getSlipSheetLayer1().trim().equals("")) // was the box checked
			     this.setSlipSheetLayer1("Y");
			  if (!this.getSlipSheetLayer2().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer2("Y");
			  if (!this.getSlipSheetLayer3().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer3("Y");
			  if (!this.getSlipSheetLayer4().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer4("Y");
			  if (!this.getSlipSheetLayer5().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer5("Y");
			  if (!this.getSlipSheetLayer6().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer6("Y");
			  if (!this.getSlipSheetLayer7().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer7("Y");
			  if (!this.getSlipSheetLayer8().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer8("Y");
			  if (!this.getSlipSheetLayer9().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer9("Y");
			  if (!this.getSlipSheetLayer10().trim().equals("")) // was the box checked
				 this.setSlipSheetLayer10("Y");
			  if (!this.getSlipSheetRequired().trim().equals("") &&
				  this.getSlipSheetBottom().trim().equals("") &&
				  this.getSlipSheetTop().trim().equals("") &&
				  this.getSlipSheetLayer1().trim().equals("") &&
				  this.getSlipSheetLayer2().trim().equals("") &&
				  this.getSlipSheetLayer3().trim().equals("") &&
				  this.getSlipSheetLayer4().trim().equals("") &&
				  this.getSlipSheetLayer5().trim().equals("") &&
				  this.getSlipSheetLayer6().trim().equals("") &&
				  this.getSlipSheetLayer7().trim().equals("") &&
				  this.getSlipSheetLayer8().trim().equals("") &&
				  this.getSlipSheetLayer9().trim().equals("") &&
				  this.getSlipSheetLayer10().trim().equals(""))
			  {
				 this.setSlipSheetRequiredError("Please choose at least ONE location that the Slip Sheet is Required");
			     foundProblem.append(this.getSlipSheetRequiredError() + "<br>");
			  }
			  //-----------------------------------------------------------------------------------
			  if (!this.getShelfLifeNotValid().trim().equals(""))
				 this.setShelfLifeNotValid("Y"); // was the box checked
			  //------------------------------------------------------------------------------------
				 
			  if (this.getStretchWrapWidth().trim().equals(""))
				this.setStretchWrapWidth("0");
			  if (this.getStretchWrapGauge().trim().equals(""))
				this.setStretchWrapGauge("0");
			  
			  // Is Stretch Wrap Required?
			 
			  if (!this.getStretchWrapRequired().trim().equals("")) // was the box checked
			  {	  
				 this.setStretchWrapRequired("Y");
				 // Make sure Type is filled out
				 if (this.getStretchWrapType().trim().equals(""))
				 {
				    this.setStretchWrapTypeError("Stretch Wrap Type is Required");
				    foundProblem.append(this.getStretchWrapTypeError().trim() + "<br>");
				 }
				 // Make sure Width is filled out
				 if (this.getStretchWrapWidth().trim().equals("") ||
					 this.getStretchWrapWidth().trim().equals("0"))
				 {
				 	this.setStretchWrapWidthError("Stretch Wrap Width is Required");
				 	foundProblem.append(this.getStretchWrapWidthError().trim() + "<br>");
				 }else{
					if (this.getStretchWrapWidthUOM().trim().equals(""))
					{
						this.setStretchWrapWidthUOMError("Stretch Wrap Width Must have a Unit of Measure");
				 	    foundProblem.append(this.getStretchWrapWidthUOMError().trim() + "<br>");
					}
				 }
				 // Make sure gauge is filled out
				 if (this.getStretchWrapGauge().trim().equals("") ||
					 this.getStretchWrapGauge().trim().equals("0"))
				 {
				 	this.setStretchWrapGaugeError("Stretch Wrap Gauge is Required");
				 	foundProblem.append(this.getStretchWrapGaugeError().trim() + "<br>");
				 }else{
						if (this.getStretchWrapGaugeUOM().trim().equals(""))
						{
							this.setStretchWrapGaugeUOMError("Stretch Wrap Gauge Must have a Unit of Measure");
					 	    foundProblem.append(this.getStretchWrapGaugeUOMError().trim() + "<br>");
						}
					 }
			  }	 
			  
			  //------------------------------------------------------------------------------------
			  if (this.getShrinkWrapWidth().trim().equals(""))
				 this.setShrinkWrapWidth("0");
			  if (this.getShrinkWrapThickness().trim().equals(""))
				 this.setShrinkWrapThickness("0");
			  
			  // Is Shrink Wrap Required?
			  if (!this.getShrinkWrapRequired().trim().equals("")) // was the box checked
			  {
				 this.setShrinkWrapRequired("Y");
			  
			     // Make sure Type is filled out
				 if (this.getShrinkWrapType().trim().equals(""))
				 {
				    this.setShrinkWrapTypeError("Shrink Wrap Type is Required");
				    foundProblem.append(this.getShrinkWrapTypeError().trim() + "<br>");
				 }
				 if (this.getShrinkWrapWidth().trim().equals("") ||
					 this.getShrinkWrapWidth().trim().equals("0"))
				 {
				 	this.setShrinkWrapWidthError("Shrink Wrap Width is Required");
				 	foundProblem.append(this.getShrinkWrapWidthError().trim() + "<br>");
				 }else{
					if (this.getShrinkWrapWidthUOM().trim().equals(""))
					{
						this.setShrinkWrapWidthUOMError("Shrink Wrap Width Must have a Unit of Measure");
				 	    foundProblem.append(this.getShrinkWrapWidthUOMError().trim() + "<br>");
					}
				 }
				 if (this.getShrinkWrapThickness().trim().equals("") ||
					 this.getShrinkWrapThickness().trim().equals("0"))
				 {
				 	this.setShrinkWrapThicknessError("Shrink Wrap Thickness is Required");
				 	foundProblem.append(this.getShrinkWrapThicknessError().trim() + "<br>");
				 }else{
					if (this.getShrinkWrapThicknessUOM().trim().equals(""))
					{
						this.setShrinkWrapThicknessUOMError("Shrink Wrap Thickness Must have a Unit of Measure");
				 	    foundProblem.append(this.getShrinkWrapThicknessUOMError().trim() + "<br>");
					}
				 }
			  }
			//------------------------------------------------------------------------------------
			  if (this.getUnitsPerPallet().trim().equals(""))
				 this.setUnitsPerPallet("0");
			  if (!this.getUnitsPerPallet().trim().equals("0"))
				 this.setUnitsPerPalletError(validateInteger(this.getUnitsPerPallet()));
			  if (!this.getUnitsPerPalletError().trim().equals(""))
			     foundProblem.append(this.getUnitsPerPalletError().trim());
				 
			  if (this.getUnitsPerLayer().trim().equals(""))
				 this.setUnitsPerLayer("0");
			  if (!this.getUnitsPerLayer().trim().equals("0"))
				 this.setUnitsPerLayerError(validateInteger(this.getUnitsPerLayer()));
			  if (!this.getUnitsPerLayerError().trim().equals(""))
			     foundProblem.append(this.getUnitsPerLayerError().trim());
			  //------------------------------------------------------------------------------------
			  // Show Barcode on Case
			  if (!this.getCaseShowBarCode().trim().equals("")) // was the box checked
				 this.setCaseShowBarCode("Y");
			  //------------------------------------------------------------------------------------
			  // Is Kosher Symbol required on Label
			  if (!this.getKosherSymbolRequired().trim().equals("")) // was the box checked
				 this.setKosherSymbolRequired("Y");
			  //------------------------------------------------------------------------------------
			  // Test Brix -- is it a valid number?
			  if (!this.getTestBrix().trim().equals("")) // was the box checked
			  {
				 this.setTestBrixError(validateBigDecimal(this.getTestBrix()));
			  }
			  else
				  this.setTestBrix("0");
			  
			  if (!foundProblem.toString().trim().equals(""))
				  this.setDisplayMessage(foundProblem.toString());
		   }		
		}
		catch(Exception e)
		{
			
		}
		return;
	}

	/**
	 * @return Returns the formulaNumber.
	 */
	public String getFormulaNumber() {
		return formulaNumber;
	}
	/**
	 * @param formulaNumber The formulaNumber to set.
	 */
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}
	/**
	 * @return Returns the revisionDate.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param revisionDate The revisionDate to set.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the readOnly.
	 */
	public String getReadOnly() {
		return readOnly;
	}
	/**
	 * @param readOnly The readOnly to set.
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 *    Will Generate the Vectors for ALL Drop Down Lists Needed multiple times
	 */
	public void buildDropDownVectors() {
//		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   
		   CommonRequestBean crb = new CommonRequestBean();
		   crb.setEnvironment(this.getEnvironment());
		   crb.setCompanyNumber(this.getCompany());
		   crb.setDivisionNumber(this.getDivision());
		   crb.setIdLevel2("2");
		   crb.setIdLevel1("ANLYT");
		   this.setDdAnalyticalTest(ServiceAttribute.dropDownAttributes(crb));
		   crb.setIdLevel1("MICRO");
		   this.setDdMicroTest(ServiceAttribute.dropDownAttributes(crb));
		   crb.setIdLevel1("PROC");
		   this.setDdProcessParameter(ServiceAttribute.dropDownAttributes(crb));
		   crb.setIdLevel1("ADD");
		   this.setDdAdditivesPreservatives(ServiceAttribute.dropDownAttributes(crb));		   
		   crb.setIdLevel1("method");
		   this.setDdMethod(ServiceQuality.dropDownMethod(crb));
		   crb.setIdLevel1("formula");
		   this.setDdFormula(ServiceQuality.dropDownFormula(crb));
		   crb.setIdLevel1("Stretch Wrap Type");
		   this.setDdStretchWrapType(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Shrink Wrap Type");
		   this.setDdShrinkWrapType(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Cont Code Location");
		   this.setDdContainerCodeLocation(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Cart Code Location");
		   this.setDdCartonCodeLocation(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("PL Label Type");
		   this.setDdPalletLabelType(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("PL Label Location");
		   this.setDdPalletLabelLocation(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Storage Cond");
		   this.setDdStorageCondition(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Pallet Require");
		   this.setDdPalletRequirement(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Cut Size");
		   this.setDdCutSize(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Screen Size");
		   this.setDdScreenSize(ServiceQuality.dropDownGenericSingle(crb));
		   crb.setIdLevel1("Foreign Detection");
		   this.setDdForeignMatDetection(ServiceQuality.dropDownGenericSingle(crb));
		   this.setDdKosherStatus(ServiceQuality.dropDownKosherStatus(crb));
		   this.setDdInlineSock(ServiceQuality.dropDownInlineSock(crb));
		   this.setDdCIPType(ServiceQuality.dropDownCIPType(crb));
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
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
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getSpecNumber() {
		return specNumber;
	}
	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
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
	public String getReferenceSpecNumber() {
		return referenceSpecNumber;
	}
	public void setReferenceSpecNumber(String referenceSpecNumber) {
		this.referenceSpecNumber = referenceSpecNumber;
	}
	public String getReferenceSpecRevisionDate() {
		return referenceSpecRevisionDate;
	}
	public void setReferenceSpecRevisionDate(String referenceSpecRevisionDate) {
		this.referenceSpecRevisionDate = referenceSpecRevisionDate;
	}
	public String getReferenceSpecRevisionTime() {
		return referenceSpecRevisionTime;
	}
	public void setReferenceSpecRevisionTime(String referenceSpecRevisionTime) {
		this.referenceSpecRevisionTime = referenceSpecRevisionTime;
	}
	public String getSpecDescription() {
		return specDescription;
	}
	public void setSpecDescription(String specDescription) {
		this.specDescription = specDescription;
	}
	public String getSpecType() {
		return specType;
	}
	public void setSpecType(String specType) {
		this.specType = specType;
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
	public String getOriginalStatus() {
		return originalStatus;
	}
	public void setOriginalStatus(String originalStatus) {
		this.originalStatus = originalStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (2/14/2011 TWalton)
	 *    11/22/11 TWalton - adjusted to be more streamline
	 */
	public void loadFromBeanQuality(BeanQuality bq) {
		try {
			QaSpecificationPackaging specData = bq.getSpecPackaging();
			this.setUpdBean(bq);
			this.setCompany(specData.getCompanyNumber().trim());
			this.setDivision(specData.getDivisionNumber().trim());
			this.setStatus(specData.getStatusCode().trim()); 
			this.setOriginalStatus(specData.getStatusCode().trim());
			this.setSpecNumber(specData.getSpecificationNumber().trim());
			this.setRevisionDate(specData.getRevisionDate().trim());
			this.setRevisionTime(specData.getRevisionTime().trim());
			this.setSpecName(specData.getSpecificationName().trim());
			this.setSpecDescription(specData.getSpecificationDescription().trim());
			this.setSpecType(specData.getTypeCode().trim());
			this.setOriginationUser(specData.getOriginationUser().trim());
			this.setGroupCode(specData.getGroupingCode().trim());
			this.setScopeCode(specData.getScopeCode().trim());
			this.setApprovedByUser(specData.getApprovedByUser().trim());
			
			this.setRevisionReason(specData.getRevisionReasonText().trim());
			this.setProductionStatus(specData.getProductionStatus().trim());
			this.setItemNumber(specData.getItemNumber().trim());
			this.setFormulaNumber(specData.getFormulaNumber().trim());
			this.setCustomerCode(specData.getCustomerCode().trim());
			this.setCustomerNumber(specData.getCustomerNumber().trim());
			this.setCustomerName(specData.getCustomerName().trim());
			
			this.setCreationDate(specData.getCreatedDate().trim());
			this.setCreationTime(specData.getCreatedTime().trim());
			this.setCreationUser(specData.getCreatedUser().trim());
					
			this.setUpdateDate(specData.getUpdatedDate().trim());
			this.setUpdateTime(specData.getUpdatedTime().trim());
			this.setUpdateUser(specData.getUpdatedUser().trim());
				
			this.setReferenceSpecNumber(specData.getReferenceSpecNumber().trim());
			this.setReferenceSpecRevisionDate(specData.getReferenceSpecRevisionDate().trim());
			this.setReferenceSpecRevisionTime(specData.getReferenceSpecRevTime().trim());
			
			this.setKosherStatusCode(specData.getKosherStatus().trim());
			this.setKosherSymbol(specData.getKosherSymbol().trim());
			this.setKosherSymbolRequired(specData.getKosherSymbolRequired().trim());
			this.setInlineSockRequired(specData.getInlineSockRequired().trim());
			this.setCipType(specData.getCipType().trim());
			this.setCutSize(specData.getCutSizeCode().trim());
			this.setCutSize2(specData.getCutSizeCode2().trim());
			this.setScreenSize(specData.getScreenSizeCode().trim());
			this.setForeignMaterialDetection(specData.getForeignMaterialsDetectionCode().trim());
			this.setTestBrix(specData.getTestBrix().trim());
			
			this.setStorageRecommendation(specData.getStorageRecommendation().trim());
			this.setContainerTamperSeal(specData.getContainerTamperSeal().trim());
			this.setContainerCodeLocation(specData.getContainerCodeLocation().trim());
			this.setContainerCodeFontSize(specData.getContainerCodeFontSize().trim());
			this.setCartonCodeLocation(specData.getCartonCodeLocation().trim());
			this.setCartonCodeFontSize(specData.getCartonCodeFontSize().trim());
			this.setCaseCodeFontSize(specData.getUnitCodeFontSize().trim());
			this.setCaseShowBarCode(specData.getUnitShowBarCode().trim());
			this.setPalletLabelType(specData.getPalletLabelType().trim());
			this.setPalletLabelLocation(specData.getPalletLabelLocation().trim());
			this.setStretchWrapRequired(specData.getStretchWrapRequired().trim());
			this.setStretchWrapType(specData.getStretchWrapType().trim());
			this.setStretchWrapWidth(HTMLHelpersMasking.maskBigDecimal(specData.getStretchWrapWidth().trim(),3));
			this.setStretchWrapWidthUOM(specData.getStretchWrapWidthUOM().trim());
			this.setStretchWrapGauge(HTMLHelpersMasking.maskBigDecimal(specData.getStretchWrapGauge().trim(),3));
			this.setStretchWrapGaugeUOM(specData.getStretchWrapGaugeUOM().trim());
			this.setShrinkWrapRequired(specData.getShrinkWrapRequired().trim());
			this.setShrinkWrapType(specData.getShrinkWrapType().trim());
			this.setShrinkWrapWidth(HTMLHelpersMasking.maskBigDecimal(specData.getShrinkWrapWidth().trim(),3));
			this.setShrinkWrapWidthUOM(specData.getShrinkWrapWidthUOM().trim());
			this.setShrinkWrapThickness(HTMLHelpersMasking.maskBigDecimal(specData.getShrinkWrapThickness().trim(),3));
			this.setShrinkWrapThicknessUOM(specData.getShrinkWrapThicknessUOM().trim());
			this.setSlipSheetRequired(specData.getSlipSheetRequired().trim());
			this.setSlipSheetBottom(specData.getSlipSheetBottom().trim());
			this.setSlipSheetLayer1(specData.getSlipSheetLayer1().trim());
			this.setSlipSheetLayer2(specData.getSlipSheetLayer2().trim());
			this.setSlipSheetLayer3(specData.getSlipSheetLayer3().trim());
			this.setSlipSheetLayer4(specData.getSlipSheetLayer4().trim());
			this.setSlipSheetLayer5(specData.getSlipSheetLayer5().trim());
			this.setSlipSheetLayer6(specData.getSlipSheetLayer6().trim());
			this.setSlipSheetLayer7(specData.getSlipSheetLayer7().trim());
			this.setSlipSheetLayer8(specData.getSlipSheetLayer8().trim());
			this.setSlipSheetLayer9(specData.getSlipSheetLayer9().trim());
			this.setSlipSheetLayer10(specData.getSlipSheetLayer10().trim());
			this.setSlipSheetTop(specData.getSlipSheetTop().trim());
			this.setPalletRequirement(specData.getPalletRequirement().trim());
			this.setUnitsPerPallet(HTMLHelpersMasking.maskBigDecimalNoComma(specData.getUnitsPerPallet().trim(), 0));
			this.setUnitsPerLayer(HTMLHelpersMasking.maskBigDecimalNoComma(specData.getUnitsPerLayer().trim(), 0));
			
			this.setCountryOfOrigin(specData.getCountryOfOrigin().trim());
			this.setReconstitutionRatio(specData.getReconstitutionRatio().trim());
			this.setShelfLifeNotValid(specData.getShelfLifeNotValid().trim());
			
			// Build the Analytical Tests and the Process Parameter Vectors from the Bean Information
			//   Also Micro Tests and Additive and Preservative Information
			String needFlavor = "";
			for (int count = 0; count < 4; count++)
			{
				Vector list = new Vector();
				int cnt = 0;
				if (count == 0)
					cnt = bq.getSpecAnalyticalTests().size();
				if (count == 1)
					cnt = bq.getSpecProcessParameters().size();
				if (count == 2)
					cnt = bq.getSpecMicroTests().size();
				if (count == 3)
					cnt = bq.getSpecAdditiveAndPreserve().size();
				if (cnt > 0){
			   for (int x = 0; x < cnt; x++){
				  UpdTestParameters updRecord = new UpdTestParameters();
				  try
				  {
					 QaTestParameters line =  new QaTestParameters();
					 if (count == 0)
						line =  (QaTestParameters) bq.getSpecAnalyticalTests().elementAt(x);
					 if (count == 1)
						line = (QaTestParameters) bq.getSpecProcessParameters().elementAt(x);
					 if (count == 2)
						line = (QaTestParameters) bq.getSpecMicroTests().elementAt(x);
					 if (count == 3)
						line = (QaTestParameters) bq.getSpecAdditiveAndPreserve().elementAt(x);
					 
					 updRecord.setRecordID(this.getSpecNumber().trim());
				 	 updRecord.setRevisionDate(this.getRevisionDate().trim());
					 updRecord.setRevisionTime(this.getRevisionTime().trim());
					 updRecord.setEnvironment(this.getEnvironment().trim());
					 updRecord.setRecordType(""); // may need to add later
					 updRecord.setAttributeGroup(line.getAttributeGroup().trim());
					 updRecord.setAttributeID(line.getAttributeIdentity().trim());
					 updRecord.setAttributeIDSequence(line.getSequenceNumber().trim());
					 
					String attributeType = line.getAttributeType();
					if (attributeType.equals("1")) {
						attributeType = "alpha";
					} else if (attributeType.equals("2")) {
						attributeType = "numeric";
					} else if (attributeType.equals("3")) {
						attributeType = "date";
					} else if (attributeType.equals("4")) {
						attributeType = "text";
					}
					
					updRecord.setAttributeFieldType(attributeType);
					 
					 // 10/31/13 TWalton - Take out all Alpha Field Comments
//					 if (updRecord.getAttributeID().trim().equals("FLVR"))
//					 {
//						 updRecord.setTargetAlpha(line.getTargetValue().trim());
//						 updRecord.setMinimumAlpha(line.getMinimumStandard().trim());
//						 updRecord.setMaximumAlpha(line.getMaximumStandard().trim());
//						 needFlavor = "no";
//					 }else
//					 {
						 updRecord.setUnitOfMeasure(line.getUnitOfMeasure().trim());
						 
						 if (line.getDecimalPlaces().trim().equals(""))
							 line.setDecimalPlaces("0");
						 if (line.getApplicationType().trim().equals("2")){
							 // Numeric Attribute
							 if (!line.getTargetValue().trim().equals(""))
								 updRecord.setTarget(HTMLHelpersMasking.maskBigDecimalNoComma(line.getTargetValue().trim(), new Integer(line.getDecimalPlaces()).intValue()));
							 if (!line.getMinimumStandard().trim().equals(""))
							 	 updRecord.setMinimum(HTMLHelpersMasking.maskBigDecimalNoComma(line.getMinimumStandard().trim(), new Integer(line.getDecimalPlaces()).intValue()));
							 if (!line.getMaximumStandard().trim().equals(""))
							 	 updRecord.setMaximum(HTMLHelpersMasking.maskBigDecimalNoComma(line.getMaximumStandard().trim(), new Integer(line.getDecimalPlaces()).intValue()));
						 }else{
							 updRecord.setTarget(line.getTargetValue().trim());
							 updRecord.setMinimum(line.getMinimumStandard().trim());
							 updRecord.setMaximum(line.getMaximumStandard().trim());
						 }
						 
						 updRecord.setDefaultOnCOA(line.getDefaultOnCOA().trim());
						 updRecord.setTestValueUOM(line.getTestedAtUnitOfMeasure().trim());
						 updRecord.setTestValue(line.getTestedAtValue().trim());
//					 }
					 updRecord.setMethod(line.getMethodNumber().trim());
					 
				  }catch(Exception e){}
				  // add element to the list
				  list.addElement(updRecord); 
			   } // End of the For Loop
			} // end of the If there are details
				
			// 9/26/13 - Moved the Addition of Blank Lines to the JSP	
			// Add Blank FLAVOR Alpha to the Analytical Tests, if needed
//			if (count == 0 && needFlavor.trim().equals(""))
//			{
//				UpdTestParameters newUTP = new UpdTestParameters();
//				newUTP.setAttributeID("FLVR");
//				list.addElement(newUTP);
//			}
				
			// Add a BLANK Line for the Update Page
//			for (int y = 0; y < 5; y++)
//			{
//			   list.addElement(new UpdTestParameters());
//			}
			if (count == 0)
			  this.setListAnalyticalTests(list);
			if (count == 1)
			  this.setListProcessParameters(list);
			if (count == 2)
			  this.setListMicroTests(list);
			if (count == 3)
			  this.setListAdditivesAndPreservatives(list);
		  }// end of the for loop			
					
		   //-------------------------------------------------------------
		   // Fruit Varieties Include 
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
						updVar.setEnvironment(this.getEnvironment().trim());
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
		  // Fruit Varieties Exclude
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
						updVar.setEnvironment(this.getEnvironment().trim());
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
		} catch (Exception e) {
			System.out.println("Error Caught in UpdSpecification.loadFromBeanQuality(BeanQuality: " + e);
		}
		return;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
	public String getRevisionReason() {
		return revisionReason;
	}
	public void setRevisionReason(String revisionReason) {
		this.revisionReason = revisionReason;
	}
	public String getProductionStatus() {
		return productionStatus;
	}
	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus;
	}
	public String getCustomerNumberError() {
		return customerNumberError;
	}
	public void setCustomerNumberError(String customerNumberError) {
		this.customerNumberError = customerNumberError;
	}
	public String getSpecDescriptionError() {
		return specDescriptionError;
	}
	public void setSpecDescriptionError(String specDescriptionError) {
		this.specDescriptionError = specDescriptionError;
	}
	public String getCustomerCodeError() {
		return customerCodeError;
	}
	public void setCustomerCodeError(String customerCodeError) {
		this.customerCodeError = customerCodeError;
	}
	public String getFormulaNumberError() {
		return formulaNumberError;
	}
	public void setFormulaNumberError(String formulaNumberError) {
		this.formulaNumberError = formulaNumberError;
	}
	public String getApprovedByUser() {
		return approvedByUser;
	}
	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
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
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getCaseCodeFontSize() {
		return caseCodeFontSize;
	}
	public void setCaseCodeFontSize(String caseCodeFontSize) {
		this.caseCodeFontSize = caseCodeFontSize;
	}
	public String getCaseShowBarCode() {
		return caseShowBarCode;
	}
	public void setCaseShowBarCode(String caseShowBarCode) {
		this.caseShowBarCode = caseShowBarCode;
	}
	public String getCipType() {
		return cipType;
	}
	public void setCipType(String cipType) {
		this.cipType = cipType;
	}
	public String getContainerCodeFontSize() {
		return containerCodeFontSize;
	}
	public void setContainerCodeFontSize(String containerCodeFontSize) {
		this.containerCodeFontSize = containerCodeFontSize;
	}
	public String getContainerCodeLocation() {
		return containerCodeLocation;
	}
	public void setContainerCodeLocation(String containerCodeLocation) {
		this.containerCodeLocation = containerCodeLocation;
	}
	public String getContainerTamperSeal() {
		return containerTamperSeal;
	}
	public void setContainerTamperSeal(String containerTamperSeal) {
		this.containerTamperSeal = containerTamperSeal;
	}
	public String getCountAdditivesAndPreservatives() {
		return countAdditivesAndPreservatives;
	}
	public void setCountAdditivesAndPreservatives(
			String countAdditivesAndPreservatives) {
		this.countAdditivesAndPreservatives = countAdditivesAndPreservatives;
	}
	public String getCountAnalyticalTests() {
		return countAnalyticalTests;
	}
	public void setCountAnalyticalTests(String countAnalyticalTests) {
		this.countAnalyticalTests = countAnalyticalTests;
	}
	public String getCountMicroTests() {
		return countMicroTests;
	}
	public void setCountMicroTests(String countMicroTests) {
		this.countMicroTests = countMicroTests;
	}
	public String getCountProcessParameters() {
		return countProcessParameters;
	}
	public void setCountProcessParameters(String countProcessParameters) {
		this.countProcessParameters = countProcessParameters;
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
	public String getInlineSockRequired() {
		return inlineSockRequired;
	}
	public void setInlineSockRequired(String inlineSockRequired) {
		this.inlineSockRequired = inlineSockRequired;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemNumberError() {
		return itemNumberError;
	}
	public void setItemNumberError(String itemNumberError) {
		this.itemNumberError = itemNumberError;
	}
	public String getKosherStatusCode() {
		return kosherStatusCode;
	}
	public void setKosherStatusCode(String kosherStatusCode) {
		this.kosherStatusCode = kosherStatusCode;
	}
	public String getKosherSymbolRequired() {
		return kosherSymbolRequired;
	}
	public void setKosherSymbolRequired(String kosherSymbolRequired) {
		this.kosherSymbolRequired = kosherSymbolRequired;
	}
	public Vector getListAdditivesAndPreservativeComments() {
		return listAdditivesAndPreservativeComments;
	}
	public void setListAdditivesAndPreservativeComments(
			Vector listAdditivesAndPreservativeComments) {
		this.listAdditivesAndPreservativeComments = listAdditivesAndPreservativeComments;
	}
	public Vector<UpdTestParameters> getListAdditivesAndPreservatives() {
		return listAdditivesAndPreservatives;
	}
	public void setListAdditivesAndPreservatives(
			Vector<UpdTestParameters> listAdditivesAndPreservatives) {
		this.listAdditivesAndPreservatives = listAdditivesAndPreservatives;
	}
	public Vector getListAnalyticalTestComments() {
		return listAnalyticalTestComments;
	}
	public void setListAnalyticalTestComments(Vector listAnalyticalTestComments) {
		this.listAnalyticalTestComments = listAnalyticalTestComments;
	}
	public Vector<UpdTestParameters> getListAnalyticalTests() {
		return listAnalyticalTests;
	}
	public void setListAnalyticalTests(Vector<UpdTestParameters> listAnalyticalTests) {
		this.listAnalyticalTests = listAnalyticalTests;
	}
	public Vector getListCasePrintAdditional() {
		return listCasePrintAdditional;
	}
	public void setListCasePrintAdditional(Vector listCasePrintAdditional) {
		this.listCasePrintAdditional = listCasePrintAdditional;
	}
	public Vector getListCasePrintByLine() {
		return listCasePrintByLine;
	}
	public void setListCasePrintByLine(Vector listCasePrintByLine) {
		this.listCasePrintByLine = listCasePrintByLine;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}
	public Vector getListContainerPrintAdditional() {
		return listContainerPrintAdditional;
	}
	public void setListContainerPrintAdditional(Vector listContainerPrintAdditional) {
		this.listContainerPrintAdditional = listContainerPrintAdditional;
	}
	public Vector getListContainerPrintByLine() {
		return listContainerPrintByLine;
	}
	public void setListContainerPrintByLine(Vector listContainerPrintByLine) {
		this.listContainerPrintByLine = listContainerPrintByLine;
	}
	public Vector getListLabelPrintAdditional() {
		return listLabelPrintAdditional;
	}
	public void setListLabelPrintAdditional(Vector listLabelPrintAdditional) {
		this.listLabelPrintAdditional = listLabelPrintAdditional;
	}
	public Vector getListLabelPrintByLine() {
		return listLabelPrintByLine;
	}
	public void setListLabelPrintByLine(Vector listLabelPrintByLine) {
		this.listLabelPrintByLine = listLabelPrintByLine;
	}
	public Vector getListMicroTestComments() {
		return listMicroTestComments;
	}
	public void setListMicroTestComments(Vector listMicroTestComments) {
		this.listMicroTestComments = listMicroTestComments;
	}
	public Vector<UpdTestParameters> getListMicroTests() {
		return listMicroTests;
	}
	public void setListMicroTests(Vector<UpdTestParameters> listMicroTests) {
		this.listMicroTests = listMicroTests;
	}
	public Vector getListPalletPrintAdditional() {
		return listPalletPrintAdditional;
	}
	public void setListPalletPrintAdditional(Vector listPalletPrintAdditional) {
		this.listPalletPrintAdditional = listPalletPrintAdditional;
	}
	public Vector getListPalletPrintByLine() {
		return listPalletPrintByLine;
	}
	public void setListPalletPrintByLine(Vector listPalletPrintByLine) {
		this.listPalletPrintByLine = listPalletPrintByLine;
	}
	public Vector getListProcessParameterComments() {
		return listProcessParameterComments;
	}
	public void setListProcessParameterComments(Vector listProcessParameterComments) {
		this.listProcessParameterComments = listProcessParameterComments;
	}
	public Vector<UpdTestParameters> getListProcessParameters() {
		return listProcessParameters;
	}
	public void setListProcessParameters(
			Vector<UpdTestParameters> listProcessParameters) {
		this.listProcessParameters = listProcessParameters;
	}
	public Vector getListShelfLifeRequirements() {
		return listShelfLifeRequirements;
	}
	public void setListShelfLifeRequirements(Vector listShelfLifeRequirements) {
		this.listShelfLifeRequirements = listShelfLifeRequirements;
	}
	public Vector getListSpecUrls() {
		return listSpecUrls;
	}
	public void setListSpecUrls(Vector listSpecUrls) {
		this.listSpecUrls = listSpecUrls;
	}
	public Vector getListStorageRequirements() {
		return listStorageRequirements;
	}
	public void setListStorageRequirements(Vector listStorageRequirements) {
		this.listStorageRequirements = listStorageRequirements;
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
	public String getPalletLabelLocation() {
		return palletLabelLocation;
	}
	public void setPalletLabelLocation(String palletLabelLocation) {
		this.palletLabelLocation = palletLabelLocation;
	}
	public String getPalletLabelType() {
		return palletLabelType;
	}
	public void setPalletLabelType(String palletLabelType) {
		this.palletLabelType = palletLabelType;
	}
	public String getPalletRequirement() {
		return palletRequirement;
	}
	public void setPalletRequirement(String palletRequirement) {
		this.palletRequirement = palletRequirement;
	}
	public String getRevisionReasonError() {
		return revisionReasonError;
	}
	public void setRevisionReasonError(String revisionReasonError) {
		this.revisionReasonError = revisionReasonError;
	}
	public String getShrinkWrapRequired() {
		return shrinkWrapRequired;
	}
	public void setShrinkWrapRequired(String shrinkWrapRequired) {
		this.shrinkWrapRequired = shrinkWrapRequired;
	}
	public String getShrinkWrapThickness() {
		return shrinkWrapThickness;
	}
	public void setShrinkWrapThickness(String shrinkWrapThickness) {
		this.shrinkWrapThickness = shrinkWrapThickness;
	}
	public String getShrinkWrapThicknessError() {
		return shrinkWrapThicknessError;
	}
	public void setShrinkWrapThicknessError(String shrinkWrapThicknessError) {
		this.shrinkWrapThicknessError = shrinkWrapThicknessError;
	}
	public String getShrinkWrapType() {
		return shrinkWrapType;
	}
	public void setShrinkWrapType(String shrinkWrapType) {
		this.shrinkWrapType = shrinkWrapType;
	}
	public String getShrinkWrapTypeError() {
		return shrinkWrapTypeError;
	}
	public void setShrinkWrapTypeError(String shrinkWrapTypeError) {
		this.shrinkWrapTypeError = shrinkWrapTypeError;
	}
	public String getShrinkWrapWidth() {
		return shrinkWrapWidth;
	}
	public void setShrinkWrapWidth(String shrinkWrapWidth) {
		this.shrinkWrapWidth = shrinkWrapWidth;
	}
	public String getShrinkWrapWidthError() {
		return shrinkWrapWidthError;
	}
	public void setShrinkWrapWidthError(String shrinkWrapWidthError) {
		this.shrinkWrapWidthError = shrinkWrapWidthError;
	}
	public String getSlipSheetRequired() {
		return slipSheetRequired;
	}
	public void setSlipSheetRequired(String slipSheetRequired) {
		this.slipSheetRequired = slipSheetRequired;
	}
	public String getSpecNameError() {
		return specNameError;
	}
	public void setSpecNameError(String specNameError) {
		this.specNameError = specNameError;
	}
	public String getStorageRecommendation() {
		return storageRecommendation;
	}
	public void setStorageRecommendation(String storageRecommendation) {
		this.storageRecommendation = storageRecommendation;
	}
	public String getStretchWrapGauge() {
		return stretchWrapGauge;
	}
	public void setStretchWrapGauge(String stretchWrapGauge) {
		this.stretchWrapGauge = stretchWrapGauge;
	}
	public String getStretchWrapGaugeError() {
		return stretchWrapGaugeError;
	}
	public void setStretchWrapGaugeError(String stretchWrapGaugeError) {
		this.stretchWrapGaugeError = stretchWrapGaugeError;
	}
	public String getStretchWrapRequired() {
		return stretchWrapRequired;
	}
	public void setStretchWrapRequired(String stretchWrapRequired) {
		this.stretchWrapRequired = stretchWrapRequired;
	}
	public String getStretchWrapType() {
		return stretchWrapType;
	}
	public void setStretchWrapType(String stretchWrapType) {
		this.stretchWrapType = stretchWrapType;
	}
	public String getStretchWrapTypeError() {
		return stretchWrapTypeError;
	}
	public void setStretchWrapTypeError(String stretchWrapTypeError) {
		this.stretchWrapTypeError = stretchWrapTypeError;
	}
	public String getStretchWrapWidth() {
		return stretchWrapWidth;
	}
	public void setStretchWrapWidth(String stretchWrapWidth) {
		this.stretchWrapWidth = stretchWrapWidth;
	}
	public String getStretchWrapWidthError() {
		return stretchWrapWidthError;
	}
	public void setStretchWrapWidthError(String stretchWrapWidthError) {
		this.stretchWrapWidthError = stretchWrapWidthError;
	}
	public BeanQuality getUpdBean() {
		return updBean;
	}
	public void setUpdBean(BeanQuality updBean) {
		this.updBean = updBean;
	}
	public Vector<DropDownSingle> getDdAnalyticalTest() {
		return ddAnalyticalTest;
	}
	public void setDdAnalyticalTest(Vector<DropDownSingle> ddAnalyticalTest) {
		this.ddAnalyticalTest = ddAnalyticalTest;
	}
	public Vector<DropDownSingle> getDdFormula() {
		return ddFormula;
	}
	public void setDdFormula(Vector<DropDownSingle> ddFormula) {
		this.ddFormula = ddFormula;
	}
	public Vector<DropDownSingle> getDdMethod() {
		return ddMethod;
	}
	public void setDdMethod(Vector<DropDownSingle> ddMethod) {
		this.ddMethod = ddMethod;
	}
	public Vector<DropDownSingle> getDdMicroTest() {
		return ddMicroTest;
	}
	public void setDdMicroTest(Vector<DropDownSingle> ddMicroTest) {
		this.ddMicroTest = ddMicroTest;
	}
	public Vector<DropDownSingle> getDdProcessParameter() {
		return ddProcessParameter;
	}
	public void setDdProcessParameter(Vector<DropDownSingle> ddProcessParameter) {
		this.ddProcessParameter = ddProcessParameter;
	}
	public Vector<DropDownSingle> getDdAdditivesPreservatives() {
		return ddAdditivesPreservatives;
	}
	public void setDdAdditivesPreservatives(
			Vector<DropDownSingle> ddAdditivesPreservatives) {
		this.ddAdditivesPreservatives = ddAdditivesPreservatives;
	}
	public Vector<DropDownSingle> getDdKosherStatus() {
		return ddKosherStatus;
	}
	public void setDdKosherStatus(Vector<DropDownSingle> ddKosherStatus) {
		this.ddKosherStatus = ddKosherStatus;
	}
	public Vector<DropDownSingle> getDdInlineSock() {
		return ddInlineSock;
	}
	public void setDdInlineSock(Vector<DropDownSingle> ddInlineSock) {
		this.ddInlineSock = ddInlineSock;
	}
	public Vector<DropDownSingle> getDdCIPType() {
		return ddCIPType;
	}
	public void setDdCIPType(Vector<DropDownSingle> ddCIPType) {
		this.ddCIPType = ddCIPType;
	}
	public String getPalletPatternURL() {
		return palletPatternURL;
	}
	public void setPalletPatternURL(String palletPatternURL) {
		this.palletPatternURL = palletPatternURL;
	}
	public String getNutritionPanelURL() {
		return nutritionPanelURL;
	}
	public void setNutritionPanelURL(String nutritionPanelURL) {
		this.nutritionPanelURL = nutritionPanelURL;
	}
	public Vector<DropDownSingle> getDdStretchWrapType() {
		return ddStretchWrapType;
	}
	public void setDdStretchWrapType(Vector<DropDownSingle> ddStretchWrapType) {
		this.ddStretchWrapType = ddStretchWrapType;
	}
	public Vector<DropDownSingle> getDdShrinkWrapType() {
		return ddShrinkWrapType;
	}
	public void setDdShrinkWrapType(Vector<DropDownSingle> ddShrinkWrapType) {
		this.ddShrinkWrapType = ddShrinkWrapType;
	}
	public Vector<DropDownSingle> getDdContainerCodeLocation() {
		return ddContainerCodeLocation;
	}
	public void setDdContainerCodeLocation(
			Vector<DropDownSingle> ddContainerCodeLocation) {
		this.ddContainerCodeLocation = ddContainerCodeLocation;
	}
	public Vector<DropDownSingle> getDdPalletLabelType() {
		return ddPalletLabelType;
	}
	public void setDdPalletLabelType(Vector<DropDownSingle> ddPalletLabelType) {
		this.ddPalletLabelType = ddPalletLabelType;
	}
	public Vector<DropDownSingle> getDdPalletLabelLocation() {
		return ddPalletLabelLocation;
	}
	public void setDdPalletLabelLocation(
			Vector<DropDownSingle> ddPalletLabelLocation) {
		this.ddPalletLabelLocation = ddPalletLabelLocation;
	}
	public Vector<DropDownSingle> getDdStorageCondition() {
		return ddStorageCondition;
	}
	public void setDdStorageCondition(Vector<DropDownSingle> ddStorageCondition) {
		this.ddStorageCondition = ddStorageCondition;
	}
	public Vector<DropDownSingle> getDdPalletRequirement() {
		return ddPalletRequirement;
	}
	public void setDdPalletRequirement(Vector<DropDownSingle> ddPalletRequirement) {
		this.ddPalletRequirement = ddPalletRequirement;
	}
	public String getPalletPatternURLRemove() {
		return palletPatternURLRemove;
	}
	public void setPalletPatternURLRemove(String palletPatternURLRemove) {
		this.palletPatternURLRemove = palletPatternURLRemove;
	}
	public String getNutritionPanelURLRemove() {
		return nutritionPanelURLRemove;
	}
	public void setNutritionPanelURLRemove(String nutritionPanelURLRemove) {
		this.nutritionPanelURLRemove = nutritionPanelURLRemove;
	}
	public String getTestBrix() {
		return testBrix;
	}
	public void setTestBrix(String testBrix) {
		this.testBrix = testBrix;
	}
	public String getTestBrixError() {
		return testBrixError;
	}
	public void setTestBrixError(String testBrixError) {
		this.testBrixError = testBrixError;
	}
	public String getSpecTypeError() {
		return specTypeError;
	}
	public void setSpecTypeError(String specTypeError) {
		this.specTypeError = specTypeError;
	}
	public String getKosherSymbol() {
		return kosherSymbol;
	}
	public void setKosherSymbol(String kosherSymbol) {
		this.kosherSymbol = kosherSymbol;
	}
	public String getSlipSheetBottom() {
		return slipSheetBottom;
	}
	public void setSlipSheetBottom(String slipSheetBottom) {
		this.slipSheetBottom = slipSheetBottom;
	}
	public String getSlipSheetLayer1() {
		return slipSheetLayer1;
	}
	public void setSlipSheetLayer1(String slipSheetLayer1) {
		this.slipSheetLayer1 = slipSheetLayer1;
	}
	public String getSlipSheetLayer2() {
		return slipSheetLayer2;
	}
	public void setSlipSheetLayer2(String slipSheetLayer2) {
		this.slipSheetLayer2 = slipSheetLayer2;
	}
	public String getSlipSheetLayer3() {
		return slipSheetLayer3;
	}
	public void setSlipSheetLayer3(String slipSheetLayer3) {
		this.slipSheetLayer3 = slipSheetLayer3;
	}
	public String getSlipSheetLayer4() {
		return slipSheetLayer4;
	}
	public void setSlipSheetLayer4(String slipSheetLayer4) {
		this.slipSheetLayer4 = slipSheetLayer4;
	}
	public String getSlipSheetLayer5() {
		return slipSheetLayer5;
	}
	public void setSlipSheetLayer5(String slipSheetLayer5) {
		this.slipSheetLayer5 = slipSheetLayer5;
	}
	public String getSlipSheetLayer6() {
		return slipSheetLayer6;
	}
	public void setSlipSheetLayer6(String slipSheetLayer6) {
		this.slipSheetLayer6 = slipSheetLayer6;
	}
	public String getSlipSheetTop() {
		return slipSheetTop;
	}
	public void setSlipSheetTop(String slipSheetTop) {
		this.slipSheetTop = slipSheetTop;
	}
	public String getSlipSheetRequiredError() {
		return slipSheetRequiredError;
	}
	public void setSlipSheetRequiredError(String slipSheetRequiredError) {
		this.slipSheetRequiredError = slipSheetRequiredError;
	}
	public Vector getListFinishedPalletAdditional() {
		return listFinishedPalletAdditional;
	}
	public void setListFinishedPalletAdditional(Vector listFinishedPalletAdditional) {
		this.listFinishedPalletAdditional = listFinishedPalletAdditional;
	}

	public Vector getListFruitVarietiesAdditional() {
		return listFruitVarietiesAdditional;
	}

	public void setListFruitVarietiesAdditional(Vector listFruitVarietiesAdditional) {
		this.listFruitVarietiesAdditional = listFruitVarietiesAdditional;
	}

	public String getStretchWrapWidthUOM() {
		return stretchWrapWidthUOM;
	}

	public void setStretchWrapWidthUOM(String stretchWrapWidthUOM) {
		this.stretchWrapWidthUOM = stretchWrapWidthUOM;
	}

	public String getStretchWrapWidthUOMError() {
		return stretchWrapWidthUOMError;
	}

	public void setStretchWrapWidthUOMError(String stretchWrapWidthUOMError) {
		this.stretchWrapWidthUOMError = stretchWrapWidthUOMError;
	}

	public String getStretchWrapGaugeUOM() {
		return stretchWrapGaugeUOM;
	}

	public void setStretchWrapGaugeUOM(String stretchWrapGaugeUOM) {
		this.stretchWrapGaugeUOM = stretchWrapGaugeUOM;
	}

	public String getStretchWrapGaugeUOMError() {
		return stretchWrapGaugeUOMError;
	}

	public void setStretchWrapGaugeUOMError(String stretchWrapGaugeUOMError) {
		this.stretchWrapGaugeUOMError = stretchWrapGaugeUOMError;
	}

	public String getShrinkWrapWidthUOM() {
		return shrinkWrapWidthUOM;
	}

	public void setShrinkWrapWidthUOM(String shrinkWrapWidthUOM) {
		this.shrinkWrapWidthUOM = shrinkWrapWidthUOM;
	}

	public String getShrinkWrapWidthUOMError() {
		return shrinkWrapWidthUOMError;
	}

	public void setShrinkWrapWidthUOMError(String shrinkWrapWidthUOMError) {
		this.shrinkWrapWidthUOMError = shrinkWrapWidthUOMError;
	}

	public String getShrinkWrapThicknessUOM() {
		return shrinkWrapThicknessUOM;
	}

	public void setShrinkWrapThicknessUOM(String shrinkWrapThicknessUOM) {
		this.shrinkWrapThicknessUOM = shrinkWrapThicknessUOM;
	}

	public String getShrinkWrapThicknessUOMError() {
		return shrinkWrapThicknessUOMError;
	}

	public void setShrinkWrapThicknessUOMError(String shrinkWrapThicknessUOMError) {
		this.shrinkWrapThicknessUOMError = shrinkWrapThicknessUOMError;
	}

	public String getCutSize() {
		return cutSize;
	}

	public void setCutSize(String cutSize) {
		this.cutSize = cutSize;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getUnitsPerPallet() {
		return unitsPerPallet;
	}

	public void setUnitsPerPallet(String unitsPerPallet) {
		this.unitsPerPallet = unitsPerPallet;
	}

	public String getUnitsPerLayer() {
		return unitsPerLayer;
	}

	public void setUnitsPerLayer(String unitsPerLayer) {
		this.unitsPerLayer = unitsPerLayer;
	}

	public String getUnitsPerPalletError() {
		return unitsPerPalletError;
	}

	public void setUnitsPerPalletError(String unitsPerPalletError) {
		this.unitsPerPalletError = unitsPerPalletError;
	}

	public String getUnitsPerLayerError() {
		return unitsPerLayerError;
	}

	public void setUnitsPerLayerError(String unitsPerLayerError) {
		this.unitsPerLayerError = unitsPerLayerError;
	}

	public Vector<DropDownSingle> getDdCutSize() {
		return ddCutSize;
	}

	public void setDdCutSize(Vector<DropDownSingle> ddCutSize) {
		this.ddCutSize = ddCutSize;
	}

	public Vector<DropDownSingle> getDdScreenSize() {
		return ddScreenSize;
	}

	public void setDdScreenSize(Vector<DropDownSingle> ddScreenSize) {
		this.ddScreenSize = ddScreenSize;
	}

	public String getForeignMaterialDetection() {
		return foreignMaterialDetection;
	}

	public void setForeignMaterialDetection(String foreignMaterialDetection) {
		this.foreignMaterialDetection = foreignMaterialDetection;
	}

	public Vector<DropDownSingle> getDdForeignMatDetection() {
		return ddForeignMatDetection;
	}

	public void setDdForeignMatDetection(
			Vector<DropDownSingle> ddForeignMatDetection) {
		this.ddForeignMatDetection = ddForeignMatDetection;
	}

	public Vector getListShippingRequirements() {
		return listShippingRequirements;
	}

	public void setListShippingRequirements(Vector listShippingRequirements) {
		this.listShippingRequirements = listShippingRequirements;
	}

	public Vector getListCOARequirements() {
		return listCOARequirements;
	}

	public void setListCOARequirements(Vector listCOARequirements) {
		this.listCOARequirements = listCOARequirements;
	}

	public String getCutSize2() {
		return cutSize2;
	}

	public void setCutSize2(String cutSize2) {
		this.cutSize2 = cutSize2;
	}

	public String getCartonCodeLocation() {
		return cartonCodeLocation;
	}

	public void setCartonCodeLocation(String cartonCodeLocation) {
		this.cartonCodeLocation = cartonCodeLocation;
	}

	public String getCartonCodeFontSize() {
		return cartonCodeFontSize;
	}

	public void setCartonCodeFontSize(String cartonCodeFontSize) {
		this.cartonCodeFontSize = cartonCodeFontSize;
	}

	public Vector<DropDownSingle> getDdCartonCodeLocation() {
		return ddCartonCodeLocation;
	}

	public void setDdCartonCodeLocation(Vector<DropDownSingle> ddCartonCodeLocation) {
		this.ddCartonCodeLocation = ddCartonCodeLocation;
	}

	public Vector getListCartonPrintByLine() {
		return listCartonPrintByLine;
	}

	public void setListCartonPrintByLine(Vector listCartonPrintByLine) {
		this.listCartonPrintByLine = listCartonPrintByLine;
	}

	public Vector getListCartonPrintAdditional() {
		return listCartonPrintAdditional;
	}

	public void setListCartonPrintAdditional(Vector listCartonPrintAdditional) {
		this.listCartonPrintAdditional = listCartonPrintAdditional;
	}

	public String getExampleLabelURL() {
		return exampleLabelURL;
	}

	public void setExampleLabelURL(String exampleLabelURL) {
		this.exampleLabelURL = exampleLabelURL;
	}

	public String getExampleLabelURLRemove() {
		return exampleLabelURLRemove;
	}

	public void setExampleLabelURLRemove(String exampleLabelURLRemove) {
		this.exampleLabelURLRemove = exampleLabelURLRemove;
	}

	public String getShelfLifeNotValid() {
		return shelfLifeNotValid;
	}

	public void setShelfLifeNotValid(String shelfLifeNotValid) {
		this.shelfLifeNotValid = shelfLifeNotValid;
	}

	public String getReconstitutionRatio() {
		return reconstitutionRatio;
	}

	public void setReconstitutionRatio(String reconstitutionRatio) {
		this.reconstitutionRatio = reconstitutionRatio;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public Vector getListProductDescription() {
		return listProductDescription;
	}

	public void setListProductDescription(Vector listProductDescription) {
		this.listProductDescription = listProductDescription;
	}

	public Vector getListIngredientStatement() {
		return listIngredientStatement;
	}

	public void setListIngredientStatement(Vector listIngredientStatement) {
		this.listIngredientStatement = listIngredientStatement;
	}

	public Vector getListIntendedUse() {
		return listIntendedUse;
	}

	public void setListIntendedUse(Vector listIntendedUse) {
		this.listIntendedUse = listIntendedUse;
	}

	public Vector getListForeignMatter() {
		return listForeignMatter;
	}

	public void setListForeignMatter(Vector listForeignMatter) {
		this.listForeignMatter = listForeignMatter;
	}

	public Vector getListCodingRequirementsAdditional() {
		return listCodingRequirementsAdditional;
	}

	public void setListCodingRequirementsAdditional(
			Vector listCodingRequirementsAdditional) {
		this.listCodingRequirementsAdditional = listCodingRequirementsAdditional;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public DateTime getSupersedesDate() {
		return supersedesDate;
	}

	public void setSupersedesDate(DateTime supercedesDate) {
		this.supersedesDate = supercedesDate;
	}

	public String getSlipSheetLayer7() {
		return slipSheetLayer7;
	}

	public void setSlipSheetLayer7(String slipSheetLayer7) {
		this.slipSheetLayer7 = slipSheetLayer7;
	}

	public String getSlipSheetLayer8() {
		return slipSheetLayer8;
	}

	public void setSlipSheetLayer8(String slipSheetLayer8) {
		this.slipSheetLayer8 = slipSheetLayer8;
	}

	public String getSlipSheetLayer9() {
		return slipSheetLayer9;
	}

	public void setSlipSheetLayer9(String slipSheetLayer9) {
		this.slipSheetLayer9 = slipSheetLayer9;
	}

	public String getSlipSheetLayer10() {
		return slipSheetLayer10;
	}

	public void setSlipSheetLayer10(String slipSheetLayer10) {
		this.slipSheetLayer10 = slipSheetLayer10;
	}
}
