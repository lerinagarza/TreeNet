/*
 * Created on Jul 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
package com.treetop.app.gtin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.CheckDate;
import com.treetop.SessionVariables;
import com.treetop.SystemDate;
import com.treetop.viewbeans.BaseViewBean;
import com.treetop.services.ServiceGTIN;
import com.treetop.services.ServiceItem;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpersInput;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdGTIN extends BaseViewBean {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public List errors = null;
	public String saveButton = null;
	public String userProfile = "";

	// Fields from the JSP to Request Information
	//  Main JSP
	public String gtinNumber = "";
	public String gtinNumberError = "";
	public String gtinA = ""; //Section of palletGTIN
	public String gtinB = ""; //Section of palletGTIN
	public String gtinC = ""; //Section of palletGTIN
	public String gtinD = ""; //Section of palletGTIN	
	
	public String eanUCCCode = "";
	public String eanUCCCodeError = "";
	public String eanUCCCodeA = "";
	public String eanUCCCodeB = "";
	public String eanUCCCodeC = "";
	public String eanUCCCodeD = "";
	public String eanUCCType = "UP";
	public String shortDescription = "";
	public String additionalTradeItemDescription = "";
	public String longDescription = "";
	public String functionalName = "";
	public String brandName = "";
	public String classificationCategoryCode = "";
	public String informationProviderName = "Tree Top Inc.";
	public String informationProvider = "0028700000014";
	// Dimension JSP
	public String height = "0";
	public String heightError = "";
	public String width = "0";
	public String widthError = "";
	public String depth = "0";
	public String depthError = "";
	public String linearUOM = "";
	public String volume = "0";
	public String volumeError = "";
	public String volumeUOM = "";
	// Weight and Content JSP
	public String netContent = "0";
	public String netContentError = "";
	public String netContentUOM = "";
	public String netWeight = "0";
	public String netWeightError = "";
	public String grossWeight = "0";
	public String grossWeightError = "";
	public String weightUOM = "";
	// Relationships JSP
	public String tiud = "";
	public String qtyNextLowerLevelTradeItem = "0";
	public String qtyNextLowerLevelTradeItemError = "";
	public String qtyChildren = "0";
	public String qtyChildrenError = "";
	public String qtyChildrenUnits = "0";
	public String qtyCompleteLayers = "0";
	public String qtyCompleteLayersError = "";
	public String qtyItemsPerCompleteLayer = "0";
	public String qtyItemsPerCompleteLayerError = "";
	// True and False JSP
	public String isConsumerUnit = "";
	public String isConsumerUnitError = "";
	public String isOrderableUnit = "";
	public String isBaseUnit = "";
	public String isDispatchUnit = "";
	public String isInvoiceUnit = "";
	public String isVariableUnit = "";
	public String isRecyclable = "";
	public String isReturnable = "";
	public String hasExpireDate = "";
	public String hasGreenDot = "";
	public String hasIngredients = "";
	public String isNetContentDeclarationIndicated = "";
	public String hasBatchNumber = "";
	public String isNonSoldReturnable = "";
	public String isItemRecyclable = "";
	// Optional Data JSP
	public String deliveryMethodIndicator = ""; // 1/17/11 - TW taken off of screen not used
	public String barcodeSymbology = "";// 1/17/11 - TW taken off of screen not used
	public String couponFamilyCode = "";// 1/17/11 - TW taken off of screen not used
	public String variant = "";// 1/17/11 - TW taken off of screen not used
	public String effectiveDate = "";
	public String effectiveDateError = "";
	public String subBrand = "";// 1/17/11 - TW taken off of screen not used
	public String additionalClassCategoryCode = "";
	public String additionalClassCategoryDesc = "";
	// Miscellaneous Fields
	public String catalogueItemState = "";
	public String countryOfOrigin = "";
	public String isInformationPrivate = "";
	public String targetMarketCode = "";// 1/17/11 - TW taken off of screen not used
	public String targetMarketCountryCode = "US";
	public String targetMarketSubDivCode = "";// 1/17/11 - TW taken off of screen not used
	public String publishToUCCNet = "";
	public String publishTypeToUCCNet = "";
	public String publicationDate = "";
	public String publicationDateError = "";
	public String tradeItemEffectiveDate = "";// 1/17/11 - TW taken off of screen not used
	public String startAvailabilityDate = "";
	public String startAvailabilityDateError = "";
	//Hidden Fields
	public String classificationCategoryName = "IGNORE";
	public String classificationCategoryDefinition = "UDEX";
	public String additionalClassificationAgencyName = "UDEX";
	//Record Status Data
	public String lastChangeDate = "";
	public String lastUpdateDate = "";
	public String lastUpdateTime = "";
	public String lastUpdateUser = "";
	public String lastUpdateWorkstation = "";
	// Comment Section
	public Vector listAllComments = null;
	public Vector listAllUrls     = null;
	public String comment = "";
	public String extra1 = "";
	public String extra2 = "";

	/**
	 * @return Returns the listAllURLs.
	 */
	public Vector getListAllURLs() {
		return listAllUrls;
	}
	/**
	 * @param listAllURLs The listAllURLs to set.
	 */
	public void setListAllURLs(Vector listAllURLs) {
		this.listAllUrls = listAllURLs;
	}
	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		Vector returnList = new Vector();
		String stopSave = "";
		if (requestType.equals("add")
			&& saveButton != null
			&& gtinNumber.equals("")) {
			//--------------------------------
			//  GTIN, Global Trade Item Number
			//--------------------------------
			String gtinCombine = gtinA + gtinB + gtinC;
			try {
				if (gtinCombine.length() == 13) {
					gtinCombine = ServiceGTIN.checkDigit14(gtinCombine);
					gtinD = gtinCombine.substring(13, 14);
					gtinNumber = gtinCombine;
				} else {
					gtinNumberError =
						"Make sure the GTIN is entered correctly. All fields are required.";
					stopSave = "Y";
				}
			} catch (Exception e) {
			}
		}
		//--------------------------------
		//  EAN UCC Code
		//    11/3/11 - TWalton added code to allow enter and check of a Check Digit for the EAN/UCC code number
		//--------------------------------
		if (this.saveButton != null)
		{
			this.eanUCCCode  = this.getEanUCCCodeA() + this.getEanUCCCodeB() + this.getEanUCCCodeC();
			if (this.getEanUCCCode().trim().equals("") &&
				this.getGtinNumber().length() == 14)
		       this.eanUCCCode = this.getGtinNumber().substring(2, this.getGtinNumber().length());
			if (this.getEanUCCCode().length() >  0)
			{
				this.eanUCCCodeError = addFormatToErrorForDisplay(validateBigDecimal(this.getEanUCCCode().trim()));
				try {
					if (this.getEanUCCCode().length() == 11 || 
						this.getEanUCCCode().length() == 10)
					   this.eanUCCCode = ServiceItem.checkDigitUPC(this.getEanUCCCode());
				} catch (Exception e) {
					this.eanUCCCode  = this.getEanUCCCodeA() + this.getEanUCCCodeB() + this.getEanUCCCodeC() + this.getEanUCCCodeD();
				}
				if (!this.getEanUCCCodeError().trim().equals(""))
					stopSave = "Y";
			}
		}
		
		//--------------------------------
		//  Height
		//--------------------------------
		if (height.trim().equals(""))
			height = "0";
//		if (!validateBigDecimal(height).equals("")) {
//			heightError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(height)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Width
		//--------------------------------
		if (width.trim().equals(""))
			width = "0";
//		if (!validateBigDecimal(width).equals("")) {
//			widthError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(width)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Depth - Length
		//--------------------------------
		if (depth.trim().equals(""))
			depth = "0";
//		if (!validateBigDecimal(depth).equals("")) {
//			depthError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(depth)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Volume
		//--------------------------------
		if (volume.trim().equals(""))
			volume = "0";
//		if (!validateBigDecimal(volume).equals("")) {
//			volumeError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(volume)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		// Net Content
		//--------------------------------
		if (netContent.trim().equals(""))
		   netContent = "0";
//		if (!validateBigDecimal(netContent).equals("")) {
//			netContentError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(netContent)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		// Net Weight
		//--------------------------------
		if (netWeight.trim().equals(""))
			netWeight = "0";
//		if (!validateBigDecimal(netWeight).equals("")) {
//			netWeightError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(netWeight)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Gross weight
		//--------------------------------
		if (grossWeight.trim().equals(""))
			grossWeight = "0";
//		if (!validateBigDecimal(grossWeight).equals("")) {
//			grossWeightError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateBigDecimal(grossWeight)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Quantity Next Lower Level Trade Item
		//--------------------------------
//		if (!validateInteger(qtyNextLowerLevelTradeItem).equals("")) {
//			qtyNextLowerLevelTradeItemError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateInteger(qtyNextLowerLevelTradeItem)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Quantity Children
		//--------------------------------
//		if (!validateInteger(qtyChildren).equals("")) {
//			qtyChildrenError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateInteger(qtyChildren)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Quantity Complete Layers
		//--------------------------------
//		if (!validateInteger(qtyCompleteLayers).equals("")) {
//			qtyCompleteLayersError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateInteger(qtyCompleteLayers)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Quantity Items per Complete Layer
		//--------------------------------
//		if (!validateInteger(qtyItemsPerCompleteLayer).equals("")) {
//			qtyItemsPerCompleteLayerError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateInteger(qtyItemsPerCompleteLayer)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Effective Date
		//--------------------------------
//		if (!validateDate(effectiveDate).equals("")) {
//			effectiveDateError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateDate(effectiveDate)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Publication Date
		//--------------------------------
//		if (!validateDate(publicationDate).equals("")) {
//			publicationDateError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateDate(publicationDate)
//					+ "</b></font>";
//			stopSave = "Y";
//		}
		//--------------------------------
		//  Start Availablity Date
		//--------------------------------
//		if (!validateDate(startAvailabilityDate).equals("")) {
//			startAvailabilityDateError =
//				"<font style=\"color:#990000\"><b>"
//					+ validateDate(startAvailabilityDate)
//					+ "</b></font>";
//			stopSave = "Y";
//		}

		// TEST HARD ERROR -- Meaning that it will NOT have saved.	
		if (!stopSave.equals(""))
			returnList.add(
				"Changes to GTIN "
					+ gtinNumber
					+ " have NOT been SAVED.  Please Correct the errors.");

		//ERRORS WHICH WILL NOT STOP THE SAVE
		//--------------------------------
		//  EAN UCC Code
		//--------------------------------
//		if (tiud.equals("BASE_UNIT_OR_EACH")
//			|| tiud.equals("CASE")
//			|| tiud.equals("DISPLAY_SHIPPER"))
//			eanUCCCodeError =
//				"<font style=\"color:#990000\">* Possibly Required - Double Check.</font>";
		
		
		//----------------------------------
		//  Is Item a Consumer Unit
		//----------------------------------
//		if (tiud.equals("PALLET")
//			|| tiud.equals("MIXED MODULE")
//			|| tiud.equals("PREPACK_ASSORTMENT") 
//			|| tiud.equals("PREPACK"))
//			{
//				isConsumerUnitError =
//				"<font style=\"color:#990000\">Based on TIUD of " +
//					tiud +
//				" This must be FALSE</font>";
//				isConsumerUnit = "F";
//			}

		return returnList;
	}
	/**
	* Build drop down lists.
	*    Send in the Type of Drop Down List you want:
	*      linearUOM, volumeUOM, netContentUOM,
	*      weightUOM, tiud, classificationCode,
	*      countryOfOrigin, catalogueItemState,
	*      tmcc, tmsdc, shortAndFunctional,
	*      brand
	*
	* Creation date: (07/06/2004 4:20:29 PM)
	*   Moved from UCCNet 11/14/2005 TWalton
	*/
	public static String buildDropDown(
		String type,
		String value,
		String readOnly) {
		Vector ddList = new Vector();
		String name = "";
		String display = "B";
		String select = "Select One";
		// Get Filled Vector of DropDownSingle Class
		if (type.equals("linearUOM")) {
			ddList = listLinearUOM();
			name = "linearUOM";
		}
		if (type.equals("volumeUOM")) {
			ddList = listVolumeUOM();
			name = "volumeUOM";
		}
		if (type.equals("netContentUOM") ||
			type.equals("netContentUnitOfMeasure")) {
			ddList = listNetContentUOM();
			name = type;
		}
		if (type.equals("weightUOM") ||
			type.equals("weightUnitOfMeasure")) {
			ddList = listWeightUOM();
			name = type;
		}
		if (type.equals("tiud") ||
		    type.equals("inqTIUD")) {
			ddList = listTIUD();
			if (type.equals("tiud"))
			  name = "tiud";
			else
			{
				name = "inqTIUD";
				select = "*All";
			}
		}
		if (type.equals("classificationCategoryCode")) {
			ddList = listClassificationCode();
			name = "classificationCategoryCode";
		}
		if (type.equals("countryOfOrigin")) {
			ddList = listCountryOfOrigin();
			name = "countryOfOrigin";
		}
		if (type.equals("catalogueItemState")) {
			ddList = listCatalogueItemState();
			name = "catalogueItemState";
			display = "N";
		}
		if (type.equals("shortDescription") ||
		    type.equals("functionalName")) {
			ddList = listShortAndFunctional();
			display = "N";
			if (type.equals("shortDescription"))
			  name = "shortDescription";
			else
			  name = "functionalName";
		}
		if (type.equals("brandName") ||
		    type.equals("inqBrandName")) {
			ddList = listBrand();
			if (type.equals("brandName"))
			{
			  name = "brandName";
			  display = "N";
			}  
			else
			{
				name = "inqBrandName";
				select = "*All";
			}
		}
		if (type.equals("publishTypeToUCCNet")) {
			ddList = listPublishType();
			name = "publishTypeToUCCNet";
		}
        return DropDownSingle.buildDropDown(ddList, name, value, select, display, readOnly, "");

	}
	/**
	* Build Vector of DropDownSingle for Brand.
	*
	* Creation date: (07/06/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	*/
	private static Vector listBrand() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Tree Top");
		thisClass.setValue("Tree Top");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();// 10/13/11 TWalton -- added Grower's Best
		thisClass.setDescription("Growers Best");
		thisClass.setValue("Growers Best");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Kirkland Signature");
		thisClass.setValue("Kirkland Signature");
		returnList.add(thisClass);
		// 11/2/12 - TWalton -- Added New Planet Organics from Email from Terri Soden
		thisClass = new DropDownSingle();
		thisClass.setDescription("New Planet Organics");
		thisClass.setValue("New Planet Organics");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Seneca");
		thisClass.setValue("Seneca");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Top Harvest");
		thisClass.setValue("Top Harvest");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Wallula Organics");
		thisClass.setValue("Wallula Organics");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Wal-Mart");
		thisClass.setValue("Wal-Mart");
		returnList.add(thisClass);
		
		return returnList;

	}
	/**
	 * Build Vector of DropDownSingle for CatalogueItemState.
	 *
	 * Creation date: (09/14/2004 2:50:29 PM)
	 *    moved from UCCNet  11/14/2005  TWalton)
	 */
	private static Vector listCatalogueItemState() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Registered");
		thisClass.setValue("Registered");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Canceled");
		thisClass.setValue("Canceled");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("In_progress");
		thisClass.setValue("In_progress");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Discontinued");
		thisClass.setValue("Discontinued");
		returnList.add(thisClass);

		return returnList;
	}
	/**
	 * Build Vector of DropDownSingle for Classification Code.
	 *
	 * Creation date: (08/17/2004 2:50:29 PM)
	 *    moved from UCCNet  11/14/2005  TWalton)
	 */
	private static Vector listClassificationCode() {
		Vector returnList = new Vector();

		// Changed the way the information needs to be formatted
		// 1/17/11 - TWalton - applied change
		
		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("All single strength juice");
		//thisClass.setValue("UDEX.17.0144.0483");
		thisClass.setValue("1701440483");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Applesauce");
		//thisClass.setValue("UDEX.02.0037.0025");
		thisClass.setValue("0200370025");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("All fruit bars");
		//thisClass.setValue("UDEX.08.0365.0171");
		thisClass.setValue("0803650171");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Frozen");
		//thisClass.setValue("UDEX.17.0143.0520");
		thisClass.setValue("1701430520");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Juice/nectar drinks - ready to drink");
		//thisClass.setValue("UDEX.17.0144.0484");
		thisClass.setValue("1701440484");
		returnList.add(thisClass);

		return returnList;

	}
	/**
	* Build Vector of DropDownSingle for Country Of Origin.
	*
	* Creation date: (10/28/2004 4:20:29 PM)
	 *    moved from UCCNet  11/14/2005  TWalton)
	 */
	private static Vector listCountryOfOrigin() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("United States of America");
		thisClass.setValue("US");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Canada");
		thisClass.setValue("CA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Argentina");
		thisClass.setValue("AR");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Australia");
		thisClass.setValue("AU");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Austria");
		thisClass.setValue("AT");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Belize");
		thisClass.setValue("BE");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Brazil");
		thisClass.setValue("BR");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Chile");
		thisClass.setValue("CL");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("China");
		thisClass.setValue("CN");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Ecuador");
		thisClass.setValue("EC");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Germany");
		thisClass.setValue("DE");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Hungary");
		thisClass.setValue("HU");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Italy");
		thisClass.setValue("IT");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Mexico");
		thisClass.setValue("MX");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("New Zealand");
		thisClass.setValue("NZ");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Poland");
		thisClass.setValue("PL");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("South Africa");
		thisClass.setValue("ZA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Spain");
		thisClass.setValue("ES");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Taiwan");
		thisClass.setValue("TW");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Turkey");
		thisClass.setValue("TR");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Venezuela");
		thisClass.setValue("VE");
		returnList.add(thisClass);

		return returnList;
	}
	/**
	* Build Vector of Drop Down Single for EAN UCC Type.
	*
	* Creation date: (10/26/2004 4:20:29 PM)
	 *    moved from UCCNet  11/14/2005  TWalton)
	 */
	private static Vector listEanUccType() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("EAN.UCC-13(7-5-1)");
		thisClass.setValue("EN");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("U.P.C. Shipping Containier Code(1-2-5-5)");
		thisClass.setValue("U2");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("U.P.C./EAN Case Code (2-5-5)");
		thisClass.setValue("UA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("U.P.C./EAN Consumer Package Code (2-5-5)");
		thisClass.setValue("UD");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("U.P.C./EAN Module Code (2-5-5)");
		thisClass.setValue("UE");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Drug U.P.C. Consumer Package Code (1-4-6-1)");
		thisClass.setValue("UG");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription(
			"Drug U.P.C. Shipping Container Code (1-2-4-6-1)");
		thisClass.setValue("UH");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("U.P.C. Consumer Package Code (1-5-5)");
		thisClass.setValue("UI");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("EAN.UCC-14 (1-2-5-5-1)");
		thisClass.setValue("UK");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("U.P.C. Case Code Number (1-1-5-5)");
		thisClass.setValue("UN");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("UCC-12 (1-5-5-1)");
		thisClass.setValue("UP");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("EAN.UCC-8");
		thisClass.setValue("EO");
		returnList.add(thisClass);

		return returnList;
	}
	/**
	* Build Vector of DropDownSingle for Linear UOM.
	*
	* Creation date: (10/27/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	*/
	private static Vector listLinearUOM() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Centimeter");
		thisClass.setValue("CM");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Feet");
		thisClass.setValue("FT");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Inches");
		thisClass.setValue("IN");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Meter");
		thisClass.setValue("MR");
		returnList.add(thisClass);

		return returnList;

	}
	/**
	* Build Vector of DropDownSingle for Net Content UOM.
	*
	* Creation date: (07/06/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	*/
	private static Vector listNetContentUOM() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Count");
		thisClass.setValue("1N");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Bag");
		thisClass.setValue("BG");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Barrel");
		thisClass.setValue("BR");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Case");
		thisClass.setValue("CA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Each [lowest consumer selling unit]");
		thisClass.setValue("EA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Fluid Ounce");
		thisClass.setValue("FO");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Gallon");
		thisClass.setValue("GA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Gram");
		thisClass.setValue("GR");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Kilogram");
		thisClass.setValue("KG");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Pounds");
		thisClass.setValue("LB");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Liter");
		thisClass.setValue("LT");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Milliliter");
		thisClass.setValue("ML");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Ounces");
		thisClass.setValue("OZ");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Pounds Equivalent");
		thisClass.setValue("PE");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Pounds Gross");
		thisClass.setValue("PG");

		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Pounds Net");
		thisClass.setValue("PN");
		returnList.add(thisClass);
		return returnList;
	}
	/**
	* Build Vector DropDownSingle for Short And Functional.
	*
	* Creation date: (07/06/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	* 
	*/
	private static Vector listShortAndFunctional() {
		Vector returnList = new Vector();
//  Values can only be 30 characters long
		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("100% Apple Juice Shelf Stable");
		thisClass.setValue("100% Apple Juice Shelf Stable");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("100% Apple Cider Shelf Stable");
		thisClass.setValue("100% Apple Cider Shelf Stable");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("100% Fruit Juice Shelf Stable");
		thisClass.setValue("100% Fruit Juice Shelf Stable");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("100% Fruit Bar");
		thisClass.setValue("100% Fruit Bar");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Fruit and Veg Bar");
		thisClass.setValue("Fruit and Veg Bar");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("100% Juice Var Pk Shelf Stable");
		thisClass.setValue("100% Juice Var Pk Shelf Stable");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Applesauce Variety Pack");
		thisClass.setValue("Applesauce Variety Pack");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Juice and Juice Drink Var Pck");
		thisClass.setValue("Juice and Juice Drink Var Pck");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Frozen Conc 100% Apple Juice");
		thisClass.setValue("Frozen Conc 100% Apple Juice");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("100% Juice Blend Shelf Stable");
		thisClass.setValue("100% Juice Blend Shelf Stable");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("100% Juice Blends Frozen");
		thisClass.setValue("100% Juice Blends Frozen");
		returnList.add(thisClass);		
		thisClass = new DropDownSingle();
		thisClass.setDescription("Applesauce");
		thisClass.setValue("Applesauce");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("25% Cran Jce Cktl Shelf Stable");
		thisClass.setValue("25% Cran Jce Cktl Shlf Stable");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Juice Drink");
		thisClass.setValue("Juice Drink");		
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Functional Beverage");
		thisClass.setValue("Functional Beverage");		
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Fruit Bases");
		thisClass.setValue("Fruit Bases");		
		returnList.add(thisClass);
		return returnList;

	}
	/**
	* Build Vector of DropDownSingle for Trade Item Unit Descriptor.
	*
	* Creation date: (07/06/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	*/
	private static Vector listTIUD() {
		Vector returnList = new Vector();

		// 1/18/11 TWalton - Change to reflect new (2009) changes to the Sync options
		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Each");
		//thisClass.setValue("BASE_UNIT_OR_EACH");
		thisClass.setValue("EA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Case");
		//thisClass.setValue("CASE");
		thisClass.setValue("CA");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Display");
		//thisClass.setValue("DISPLAY_SHIPPER");
		thisClass.setValue("DS");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Mod Pallet");
		//thisClass.setValue("MIXED_MODULE");
		thisClass.setValue("MX");
		returnList.add(thisClass);
		//thisClass = new DropDownSingle();
		//thisClass.setDescription("Multi-pack");
		//thisClass.setValue("MULTIPACK");
		//returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Package");
		//thisClass.setValue("PACK_OR_INNER_PACK");
		thisClass.setValue("PK");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Pallet");
		//thisClass.setValue("PALLET");
		thisClass.setValue("PL");
		returnList.add(thisClass);
		//thisClass = new DropDownSingle();
		//thisClass.setDescription("Pre-pack");
		//thisClass.setValue("PREPACK");
		//returnList.add(thisClass);
		//thisClass = new DropDownSingle();
		//thisClass.setDescription("Assorted Pre-pack");
		//thisClass.setValue("PREPACK_ASSORTMENT");
		//returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Set-pack");
		//thisClass.setValue("SETPACK");
		thisClass.setValue("AP");
		returnList.add(thisClass);

		return returnList;
	}
	/**
	* Build Vector for DropDownSingle for Volume Units.
	*
	* Creation date: (10/27/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	*/
	private static Vector listVolumeUOM() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Cubic Centimeter");
		thisClass.setValue("CC");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Cubic Feet");
		thisClass.setValue("CF");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Cubic Inches");
		thisClass.setValue("CI");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Cubic Meter");
		thisClass.setValue("CR");
		returnList.add(thisClass);

		return returnList;
	}
	/**
	* Build Vector of DropDownSingle for Weight Units.
	*
	* Creation date: (10/27/2004 4:20:29 PM)
	*    moved from UCCNet  11/14/2005  TWalton)
	*/
	private static Vector listWeightUOM() {
		Vector returnList = new Vector();

		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Gram");
		thisClass.setValue("GR");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Kilogram");
		thisClass.setValue("KG");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Pounds");
		thisClass.setValue("LB");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Milligram");
		thisClass.setValue("ME");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Ounces");
		thisClass.setValue("OZ");
		returnList.add(thisClass);

		return returnList;
	}

	/*  
	 *  Send in a Business Object Bean
	 *  Use the fields from this Bean to set into a
	 *    View Bean for Update
	 * Creation date: (11/1/2005 TWalton)
	 */
	public void loadUpdGTINFromBeanGTIN(BeanGTIN inBean) {
		try {
			this.gtinNumber = inBean.getGtinDetail().getGtinNumber();
			
			// 11/3/11 TWalton - change to use same strategy as Label and Case UPC Codes in the New Item Process
			//this.eanUCCCode = inBean.getGtinDetail().getEanUCCCode();
			String eanUCCCodeBase = inBean.getGtinDetail().getEanUCCCode();
			if (eanUCCCodeBase.trim().equals("") &&
			   this.getGtinNumber().length() == 14)
			  eanUCCCodeBase = this.getGtinNumber().substring(2, this.getGtinNumber().length());
	    	if (eanUCCCodeBase.length() == 11 || 
	    		eanUCCCodeBase.length() == 10)
	    	{
	    	   try{
	    	      eanUCCCodeBase = ServiceItem.checkDigitUPC(this.getEanUCCCode());
	    	   }catch(Exception e){
	    		   eanUCCCodeBase = "";
	    	   }
	    	}
	    	if (eanUCCCodeBase.length() < 12)
			{
				for (int x = eanUCCCodeBase.length(); x < 12; x++)
				{
				   eanUCCCodeBase = eanUCCCodeBase + " ";
				}
			}
		    if (eanUCCCodeBase.length() == 12){
				this.eanUCCCode  = eanUCCCodeBase.substring(0,11);
				this.eanUCCCodeA = eanUCCCodeBase.substring(0, 1);
				this.eanUCCCodeB = eanUCCCodeBase.substring(1, 6);
				this.eanUCCCodeC = eanUCCCodeBase.substring(6, 11);
				this.eanUCCCodeD = eanUCCCodeBase.substring(11, 12);
			}
			
			this.eanUCCType = inBean.getGtinDetail().getEanUCCType();
			this.shortDescription = inBean.getGtinDetail().getGtinDescription();
			this.additionalTradeItemDescription = inBean.getGtinDetail().getAdditionalTradeItemDescription();
			this.longDescription = inBean.getGtinDetail().getGtinLongDescription();
			this.functionalName = inBean.getGtinDetail().getFunctionalName();
			this.brandName = inBean.getGtinDetail().getBrandName();
			this.classificationCategoryCode = inBean.getGtinDetail().getClassificationCategoryCode();
			this.informationProviderName = inBean.getGtinDetail().getNameOfInformationProvider();
			this.informationProvider = inBean.getGtinDetail().getInformationProvider();

			// WILL WANT TO SEPARATE BY JSP WHEN ITEMS ON PAGES ARE DECIDED
			//Dimension Section
			this.height = inBean.getGtinDetail().getHeight();
			this.width = inBean.getGtinDetail().getWidth();
			this.depth = inBean.getGtinDetail().getDepth();
			this.linearUOM = inBean.getGtinDetail().getLinearUnitOfMeasure();
			this.volume = inBean.getGtinDetail().getVolume();
			this.volumeUOM = inBean.getGtinDetail().getVolumeUnitOfMeasure();
			//Weight & Content JSP
			this.netContent = inBean.getGtinDetail().getNetContent();
			this.netContentUOM = inBean.getGtinDetail().getNetContentUnitOfMeasure();
			this.netWeight = inBean.getGtinDetail().getNetWeight();
			this.grossWeight = inBean.getGtinDetail().getGrossWeight();
			this.weightUOM = inBean.getGtinDetail().getWeightUnitOfMeasure();
			//Relationships JSP
			this.tiud = inBean.getGtinDetail().getTradeItemUnitDescriptor();
			this.qtyNextLowerLevelTradeItem =
				inBean.getGtinDetail().getQtyOfNextLowerLevelTradeItem();
			this.qtyChildren = inBean.getGtinDetail().getQtyChildren();
			this.qtyChildrenUnits = inBean.getGtinDetail().getQtyChildrenUnits();
			this.qtyCompleteLayers = inBean.getGtinDetail().getQtyCompleteLayers();
			this.qtyItemsPerCompleteLayer = inBean.getGtinDetail().getQtyItemsPerCompleteLayer();
			// True and False JSP	
			this.isConsumerUnit = inBean.getGtinDetail().getIsConsumerUnit();
			this.isOrderableUnit = inBean.getGtinDetail().getIsOrderableUnit();
			this.isBaseUnit = inBean.getGtinDetail().getIsBaseUnit();
			this.isDispatchUnit = inBean.getGtinDetail().getIsDispatchUnit();
			this.isInvoiceUnit = inBean.getGtinDetail().getIsInvoiceUnit();
			this.isVariableUnit = inBean.getGtinDetail().getIsVariableUnit();
			this.isRecyclable = inBean.getGtinDetail().getIsPackagingMarkedRecyclable();
			this.isReturnable = inBean.getGtinDetail().getIsPackagingMarkedReturnable();
			this.hasExpireDate = inBean.getGtinDetail().getIsPackagingMarkedWithExpirationDate();
			this.hasGreenDot = inBean.getGtinDetail().getIsPackagingMarkedWithGreenDot();
			this.hasIngredients = inBean.getGtinDetail().getIsPackagingMarkedWithIngredients();
			this.isNetContentDeclarationIndicated = inBean.getGtinDetail().getIsNetContentDeclarationIndicated();
			this.hasBatchNumber = inBean.getGtinDetail().getHasBatchNumber();
			this.isNonSoldReturnable = inBean.getGtinDetail().getIsNonSoldReturnable();
			this.isItemRecyclable = inBean.getGtinDetail().getIsItemMarkedRecyclable();
			//Optional Data JSP
			this.deliveryMethodIndicator = inBean.getGtinDetail().getDeliveryMethodIndicator();
			this.barcodeSymbology = inBean.getGtinDetail().getIsBarcodeSymbologyDerivable();
			this.couponFamilyCode = inBean.getGtinDetail().getCouponFamilyCode();
			this.variant = inBean.getGtinDetail().getVariant();
			this.effectiveDate = inBean.getGtinDetail().getEffectiveDate();
			this.subBrand = inBean.getGtinDetail().getSubBrand();
			this.additionalClassCategoryCode = inBean.getGtinDetail().getAdditionalClassificationCategoryCode();
			this.additionalClassCategoryDesc = inBean.getGtinDetail().getAdditionalClassificationCategoryDesc();
			//Misc Fields
			this.catalogueItemState = inBean.getGtinDetail().getCatalogItemState();
			this.countryOfOrigin = inBean.getGtinDetail().getCountryOfOrigin();
			this.isInformationPrivate = inBean.getGtinDetail().getIsInformationPrivate();
			this.targetMarketCountryCode = inBean.getGtinDetail().getTargetMarketCountryCode();
			this.targetMarketSubDivCode = inBean.getGtinDetail().getTargetMarketSubdivCode();
			this.publishToUCCNet = inBean.getGtinDetail().getPublishToUCCNet();
			this.publicationDate = inBean.getGtinDetail().getPublicationDate();
			this.tradeItemEffectiveDate = inBean.getGtinDetail().getTradeItemEffectiveDate();
			this.startAvailabilityDate = inBean.getGtinDetail().getStartAvailabilityDate();
			// Additional Hidden Fields
			this.additionalClassificationAgencyName = inBean.getGtinDetail().getAdditionalClassificationAgencyName();
			this.classificationCategoryName = inBean.getGtinDetail().getClassificationCategoryName();
			this.classificationCategoryDefinition = inBean.getGtinDetail().getClassificationCategoryDefinition();
			// Additional Fields not Sent In
			this.publishTypeToUCCNet = inBean.getGtinDetail().getStatus();
			this.lastUpdateUser = inBean.getGtinDetail().getLastUpdateUser();
			this.lastUpdateWorkstation = inBean.getGtinDetail().getLastUpdateWorkstation();
			this.lastUpdateDate = inBean.getGtinDetail().getLastUpdateDate();
			this.lastChangeDate = inBean.getGtinDetail().getLastChangeDate();
			this.lastUpdateTime = inBean.getGtinDetail().getLastUpdateTime();

			this.listAllComments = inBean.getGtinDetail().getComments();
			this.listAllUrls     = inBean.getGtinDetail().getUrls();

		} catch (Exception e) {

		}
		return;
	}

	/**
	 * Send in the Field Name and the Value 
	 *    Should Be True False or Blank
	 * Return the True False Radio Button Set as a String
	 * 
	 * Creation date: (9/8/2005)
	 */
	public static String buildRadioTrueFalse(
		String fieldName,
		String value,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();
		String checkedTrue = "";
		String checkedFalse = "";
		if (!value.equals("")) {
			if (value.equals("T"))
				checkedTrue = "Y";
			if (value.equals("F"))
				checkedFalse = "Y";
		}
		returnString.append(
			HTMLHelpersInput.inputRadioButton(
				fieldName,
				"T",
				checkedTrue,
				readOnly));
		returnString.append("&nbsp;True&nbsp;&nbsp;");
		returnString.append(
			HTMLHelpersInput.inputRadioButton(
				fieldName,
				"F",
				checkedFalse,
				readOnly));
		returnString.append("&nbsp;False");
		return returnString.toString();
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
	 * @return
	 */
	public String getGtinNumber() {
		return gtinNumber;
	}

	/**
	 * @param string
	 */
	public void setGtinNumber(String string) {
		gtinNumber = string;
	}

	/**
	 * @return
	 */
	public String getAdditionalClassCategoryCode() {
		return additionalClassCategoryCode;
	}

	/**
	 * @return
	 */
	public String getAdditionalClassCategoryDesc() {
		return additionalClassCategoryDesc;
	}

	/**
	 * @return
	 */
	public String getAdditionalTradeItemDescription() {
		return additionalTradeItemDescription;
	}

	/**
	 * @return
	 */
	public String getBarcodeSymbology() {
		return barcodeSymbology;
	}

	/**
	 * @return
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @return
	 */
	public String getCatalogueItemState() {
		return catalogueItemState;
	}

	/**
	 * @return
	 */
	public String getClassificationCategoryCode() {
		return classificationCategoryCode;
	}

	/**
	 * @return
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	/**
	 * @return
	 */
	public String getCouponFamilyCode() {
		return couponFamilyCode;
	}

	/**
	 * @return
	 */
	public String getDeliveryMethodIndicator() {
		return deliveryMethodIndicator;
	}

	/**
	 * @return
	 */
	public String getDepth() {
		return depth;
	}

	/**
	 * @return
	 */
	public String getEanUCCCode() {
		return eanUCCCode;
	}

	/**
	 * @return
	 */
	public String getEanUCCType() {
		return eanUCCType;
	}

	/**
	 * @return
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @return
	 */
	public String getFunctionalName() {
		return functionalName;
	}

	/**
	 * @return
	 */
	public String getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @return
	 */
	public String getHasBatchNumber() {
		return hasBatchNumber;
	}

	/**
	 * @return
	 */
	public String getHasExpireDate() {
		return hasExpireDate;
	}

	/**
	 * @return
	 */
	public String getHasGreenDot() {
		return hasGreenDot;
	}

	/**
	 * @return
	 */
	public String getHasIngredients() {
		return hasIngredients;
	}

	/**
	 * @return
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @return
	 */
	public String getInformationProvider() {
		return informationProvider;
	}

	/**
	 * @return
	 */
	public String getInformationProviderName() {
		return informationProviderName;
	}

	/**
	 * @return
	 */
	public String getIsBaseUnit() {
		return isBaseUnit;
	}

	/**
	 * @return
	 */
	public String getIsConsumerUnit() {
		return isConsumerUnit;
	}

	/**
	 * @return
	 */
	public String getIsDispatchUnit() {
		return isDispatchUnit;
	}

	/**
	 * @return
	 */
	public String getIsInformationPrivate() {
		return isInformationPrivate;
	}

	/**
	 * @return
	 */
	public String getIsInvoiceUnit() {
		return isInvoiceUnit;
	}

	/**
	 * @return
	 */
	public String getIsItemRecyclable() {
		return isItemRecyclable;
	}

	/**
	 * @return
	 */
	public String getIsNetContentDeclarationIndicated() {
		return isNetContentDeclarationIndicated;
	}

	/**
	 * @return
	 */
	public String getIsNonSoldReturnable() {
		return isNonSoldReturnable;
	}

	/**
	 * @return
	 */
	public String getIsOrderableUnit() {
		return isOrderableUnit;
	}

	/**
	 * @return
	 */
	public String getIsRecyclable() {
		return isRecyclable;
	}

	/**
	 * @return
	 */
	public String getIsReturnable() {
		return isReturnable;
	}

	/**
	 * @return
	 */
	public String getIsVariableUnit() {
		return isVariableUnit;
	}

	/**
	 * @return
	 */
	public String getLinearUOM() {
		return linearUOM;
	}

	/**
	 * @return
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @return
	 */
	public String getNetContent() {
		return netContent;
	}

	/**
	 * @return
	 */
	public String getNetContentUOM() {
		return netContentUOM;
	}

	/**
	 * @return
	 */
	public String getNetWeight() {
		return netWeight;
	}

	/**
	 * @return
	 */
	public String getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @return
	 */
	public String getPublishToUCCNet() {
		return publishToUCCNet;
	}

	/**
	 * @return
	 */
	public String getQtyChildren() {
		return qtyChildren;
	}

	/**
	 * @return
	 */
	public String getQtyChildrenUnits() {
		return qtyChildrenUnits;
	}

	/**
	 * @return
	 */
	public String getQtyCompleteLayers() {
		return qtyCompleteLayers;
	}

	/**
	 * @return
	 */
	public String getQtyItemsPerCompleteLayer() {
		return qtyItemsPerCompleteLayer;
	}

	/**
	 * @return
	 */
	public String getQtyNextLowerLevelTradeItem() {
		return qtyNextLowerLevelTradeItem;
	}

	/**
	 * @return
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @return
	 */
	public String getStartAvailabilityDate() {
		return startAvailabilityDate;
	}

	/**
	 * @return
	 */
	public String getSubBrand() {
		return subBrand;
	}

	/**
	 * @return
	 */
	public String getTargetMarketCountryCode() {
		return targetMarketCountryCode;
	}

	/**
	 * @return
	 */
	public String getTargetMarketSubDivCode() {
		return targetMarketSubDivCode;
	}

	/**
	 * @return
	 */
	public String getTiud() {
		return tiud;
	}

	/**
	 * @return
	 */
	public String getTradeItemEffectiveDate() {
		return tradeItemEffectiveDate;
	}

	/**
	 * @return
	 */
	public String getVariant() {
		return variant;
	}

	/**
	 * @return
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @return
	 */
	public String getVolumeUOM() {
		return volumeUOM;
	}

	/**
	 * @return
	 */
	public String getWeightUOM() {
		return weightUOM;
	}

	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param string
	 */
	public void setAdditionalClassCategoryCode(String string) {
		additionalClassCategoryCode = string;
	}

	/**
	 * @param string
	 */
	public void setAdditionalClassCategoryDesc(String string) {
		additionalClassCategoryDesc = string;
	}

	/**
	 * @param string
	 */
	public void setAdditionalTradeItemDescription(String string) {
		additionalTradeItemDescription = string;
	}

	/**
	 * @param string
	 */
	public void setBarcodeSymbology(String string) {
		barcodeSymbology = string;
	}

	/**
	 * @param string
	 */
	public void setBrandName(String string) {
		brandName = string;
	}

	/**
	 * @param string
	 */
	public void setCatalogueItemState(String string) {
		catalogueItemState = string;
	}

	/**
	 * @param string
	 */
	public void setClassificationCategoryCode(String string) {
		classificationCategoryCode = string;
	}

	/**
	 * @param string
	 */
	public void setCountryOfOrigin(String string) {
		countryOfOrigin = string;
	}

	/**
	 * @param string
	 */
	public void setCouponFamilyCode(String string) {
		couponFamilyCode = string;
	}

	/**
	 * @param string
	 */
	public void setDeliveryMethodIndicator(String string) {
		deliveryMethodIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setDepth(String string) {
		depth = string;
	}

	/**
	 * @param string
	 */
	public void setEanUCCCode(String string) {
		eanUCCCode = string;
	}

	/**
	 * @param string
	 */
	public void setEanUCCType(String string) {
		eanUCCType = string;
	}

	/**
	 * @param string
	 */
	public void setEffectiveDate(String string) {
		effectiveDate = string;
	}

	/**
	 * @param string
	 */
	public void setFunctionalName(String string) {
		functionalName = string;
	}

	/**
	 * @param string
	 */
	public void setGrossWeight(String string) {
		grossWeight = string;
	}

	/**
	 * @param string
	 */
	public void setHasBatchNumber(String string) {
		hasBatchNumber = string;
	}

	/**
	 * @param string
	 */
	public void setHasExpireDate(String string) {
		hasExpireDate = string;
	}

	/**
	 * @param string
	 */
	public void setHasGreenDot(String string) {
		hasGreenDot = string;
	}

	/**
	 * @param string
	 */
	public void setHasIngredients(String string) {
		hasIngredients = string;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string) {
		height = string;
	}

	/**
	 * @param string
	 */
	public void setInformationProvider(String string) {
		informationProvider = string;
	}

	/**
	 * @param string
	 */
	public void setInformationProviderName(String string) {
		informationProviderName = string;
	}

	/**
	 * @param string
	 */
	public void setIsBaseUnit(String string) {
		isBaseUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsConsumerUnit(String string) {
		isConsumerUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsDispatchUnit(String string) {
		isDispatchUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsInformationPrivate(String string) {
		isInformationPrivate = string;
	}

	/**
	 * @param string
	 */
	public void setIsInvoiceUnit(String string) {
		isInvoiceUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsItemRecyclable(String string) {
		isItemRecyclable = string;
	}

	/**
	 * @param string
	 */
	public void setIsNetContentDeclarationIndicated(String string) {
		isNetContentDeclarationIndicated = string;
	}

	/**
	 * @param string
	 */
	public void setIsNonSoldReturnable(String string) {
		isNonSoldReturnable = string;
	}

	/**
	 * @param string
	 */
	public void setIsOrderableUnit(String string) {
		isOrderableUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsRecyclable(String string) {
		isRecyclable = string;
	}

	/**
	 * @param string
	 */
	public void setIsReturnable(String string) {
		isReturnable = string;
	}

	/**
	 * @param string
	 */
	public void setIsVariableUnit(String string) {
		isVariableUnit = string;
	}

	/**
	 * @param string
	 */
	public void setLinearUOM(String string) {
		linearUOM = string;
	}

	/**
	 * @param string
	 */
	public void setLongDescription(String string) {
		longDescription = string;
	}

	/**
	 * @param string
	 */
	public void setNetContent(String string) {
		netContent = string;
	}

	/**
	 * @param string
	 */
	public void setNetContentUOM(String string) {
		netContentUOM = string;
	}

	/**
	 * @param string
	 */
	public void setNetWeight(String string) {
		netWeight = string;
	}

	/**
	 * @param string
	 */
	public void setPublicationDate(String string) {
		publicationDate = string;
	}

	/**
	 * @param string
	 */
	public void setPublishToUCCNet(String string) {
		publishToUCCNet = string;
	}

	/**
	 * @param string
	 */
	public void setQtyChildren(String string) {
		qtyChildren = string;
	}

	/**
	 * @param string
	 */
	public void setQtyChildrenUnits(String string) {
		qtyChildrenUnits = string;
	}

	/**
	 * @param string
	 */
	public void setQtyCompleteLayers(String string) {
		qtyCompleteLayers = string;
	}

	/**
	 * @param string
	 */
	public void setQtyItemsPerCompleteLayer(String string) {
		qtyItemsPerCompleteLayer = string;
	}

	/**
	 * @param string
	 */
	public void setQtyNextLowerLevelTradeItem(String string) {
		qtyNextLowerLevelTradeItem = string;
	}

	/**
	 * @param string
	 */
	public void setShortDescription(String string) {
		shortDescription = string;
	}

	/**
	 * @param string
	 */
	public void setStartAvailabilityDate(String string) {
		startAvailabilityDate = string;
	}

	/**
	 * @param string
	 */
	public void setSubBrand(String string) {
		subBrand = string;
	}

	/**
	 * @param string
	 */
	public void setTargetMarketCountryCode(String string) {
		targetMarketCountryCode = string;
	}

	/**
	 * @param string
	 */
	public void setTargetMarketSubDivCode(String string) {
		targetMarketSubDivCode = string;
	}

	/**
	 * @param string
	 */
	public void setTiud(String string) {
		tiud = string;
	}

	/**
	 * @param string
	 */
	public void setTradeItemEffectiveDate(String string) {
		tradeItemEffectiveDate = string;
	}

	/**
	 * @param string
	 */
	public void setVariant(String string) {
		variant = string;
	}

	/**
	 * @param string
	 */
	public void setVolume(String string) {
		volume = string;
	}

	/**
	 * @param string
	 */
	public void setVolumeUOM(String string) {
		volumeUOM = string;
	}

	/**
	 * @param string
	 */
	public void setWeightUOM(String string) {
		weightUOM = string;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	/**
	 * @return
	 */
	public String getAdditionalClassificationAgencyName() {
		return additionalClassificationAgencyName;
	}

	/**
	 * @return
	 */
	public String getClassificationCategoryDefinition() {
		return classificationCategoryDefinition;
	}

	/**
	 * @return
	 */
	public String getClassificationCategoryName() {
		return classificationCategoryName;
	}

	/**
	 * @return
	 */
	public List getErrors() {
		return errors;
	}

	/**
	 * @param string
	 */
	public void setAdditionalClassificationAgencyName(String string) {
		additionalClassificationAgencyName = string;
	}

	/**
	 * @param string
	 */
	public void setClassificationCategoryDefinition(String string) {
		classificationCategoryDefinition = string;
	}

	/**
	 * @param string
	 */
	public void setClassificationCategoryName(String string) {
		classificationCategoryName = string;
	}

	/**
	 * @param list
	 */
	public void setErrors(List list) {
		errors = list;
	}

	/**
	 * @return
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @return
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @return
	 */
	public String getLastUpdateWorkstation() {
		return lastUpdateWorkstation;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateDate(String string) {
		lastUpdateDate = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateTime(String string) {
		lastUpdateTime = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateUser(String string) {
		lastUpdateUser = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateWorkstation(String string) {
		lastUpdateWorkstation = string;
	}

	/**
	 * @return
	 */
	public String getLastChangeDate() {
		return lastChangeDate;
	}

	/**
	 * @param string
	 */
	public void setLastChangeDate(String string) {
		lastChangeDate = string;
	}

	/**
	 * @return
	 */
	public String getSaveButton() {
		return saveButton;
	}

	/**
	 * @param string
	 */
	public void setSaveButton(String string) {
		saveButton = string;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return
	 */
	public String getExtra1() {
		return extra1;
	}

	/**
	 * @return
	 */
	public String getExtra2() {
		return extra2;
	}

	/**
	 * @return
	 */
	public Vector getListAllComments() {
		return listAllComments;
	}

	/**
	 * @return
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @param string
	 */
	public void setExtra1(String string) {
		extra1 = string;
	}

	/**
	 * @param string
	 */
	public void setExtra2(String string) {
		extra2 = string;
	}

	/**
	 * @param vector
	 */
	public void setListAllComments(Vector vector) {
		listAllComments = vector;
	}

	/**
	 * @param string
	 */
	public void setUserProfile(String string) {
		userProfile = string;
	}

	/**
	 * @return
	 */
	public String getTargetMarketCode() {
		return targetMarketCode;
	}

	/**
	 * @param string
	 */
	public void setTargetMarketCode(String string) {
		targetMarketCode = string;
	}

	/**
	 * @return
	 */
	public String getEanUCCCodeError() {
		return eanUCCCodeError;
	}

	/**
	 * @param string
	 */
	public void setEanUCCCodeError(String string) {
		eanUCCCodeError = string;
	}

	/**
	 * @return
	 */
	public String getDepthError() {
		return depthError;
	}

	/**
	 * @return
	 */
	public String getEffectiveDateError() {
		return effectiveDateError;
	}

	/**
	 * @return
	 */
	public String getGrossWeightError() {
		return grossWeightError;
	}

	/**
	 * @return
	 */
	public String getHeightError() {
		return heightError;
	}

	/**
	 * @return
	 */
	public String getNetContentError() {
		return netContentError;
	}

	/**
	 * @return
	 */
	public String getNetWeightError() {
		return netWeightError;
	}

	/**
	 * @return
	 */
	public String getPublicationDateError() {
		return publicationDateError;
	}

	/**
	 * @return
	 */
	public String getQtyChildrenError() {
		return qtyChildrenError;
	}

	/**
	 * @return
	 */
	public String getQtyCompleteLayersError() {
		return qtyCompleteLayersError;
	}

	/**
	 * @return
	 */
	public String getQtyItemsPerCompleteLayerError() {
		return qtyItemsPerCompleteLayerError;
	}

	/**
	 * @return
	 */
	public String getQtyNextLowerLevelTradeItemError() {
		return qtyNextLowerLevelTradeItemError;
	}

	/**
	 * @return
	 */
	public String getStartAvailabilityDateError() {
		return startAvailabilityDateError;
	}

	/**
	 * @return
	 */
	public String getVolumeError() {
		return volumeError;
	}

	/**
	 * @return
	 */
	public String getWidthError() {
		return widthError;
	}

	/**
	 * @param string
	 */
	public void setDepthError(String string) {
		depthError = string;
	}

	/**
	 * @param string
	 */
	public void setEffectiveDateError(String string) {
		effectiveDateError = string;
	}

	/**
	 * @param string
	 */
	public void setGrossWeightError(String string) {
		grossWeightError = string;
	}

	/**
	 * @param string
	 */
	public void setHeightError(String string) {
		heightError = string;
	}

	/**
	 * @param string
	 */
	public void setNetContentError(String string) {
		netContentError = string;
	}

	/**
	 * @param string
	 */
	public void setNetWeightError(String string) {
		netWeightError = string;
	}

	/**
	 * @param string
	 */
	public void setPublicationDateError(String string) {
		publicationDateError = string;
	}

	/**
	 * @param string
	 */
	public void setQtyChildrenError(String string) {
		qtyChildrenError = string;
	}

	/**
	 * @param string
	 */
	public void setQtyCompleteLayersError(String string) {
		qtyCompleteLayersError = string;
	}

	/**
	 * @param string
	 */
	public void setQtyItemsPerCompleteLayerError(String string) {
		qtyItemsPerCompleteLayerError = string;
	}

	/**
	 * @param string
	 */
	public void setQtyNextLowerLevelTradeItemError(String string) {
		qtyNextLowerLevelTradeItemError = string;
	}

	/**
	 * @param string
	 */
	public void setStartAvailabilityDateError(String string) {
		startAvailabilityDateError = string;
	}

	/**
	 * @param string
	 */
	public void setVolumeError(String string) {
		volumeError = string;
	}

	/**
	 * @param string
	 */
	public void setWidthError(String string) {
		widthError = string;
	}

	/**
	 * @return
	 */
	public String getGtinA() {
		return gtinA;
	}

	/**
	 * @return
	 */
	public String getGtinB() {
		return gtinB;
	}

	/**
	 * @return
	 */
	public String getGtinC() {
		return gtinC;
	}

	/**
	 * @return
	 */
	public String getGtinD() {
		return gtinD;
	}

	/**
	 * @param string
	 */
	public void setGtinA(String string) {
		gtinA = string;
	}

	/**
	 * @param string
	 */
	public void setGtinB(String string) {
		gtinB = string;
	}

	/**
	 * @param string
	 */
	public void setGtinC(String string) {
		gtinC = string;
	}

	/**
	 * @param string
	 */
	public void setGtinD(String string) {
		gtinD = string;
	}

	/**
	 * @return
	 */
	public String getGtinNumberError() {
		return gtinNumberError;
	}

	/**
	 * @param string
	 */
	public void setGtinNumberError(String string) {
		gtinNumberError = string;
	}

	/**
	 * @return
	 */
	public String getIsConsumerUnitError() {
		return isConsumerUnitError;
	}

	/**
	 * @param string
	 */
	public void setIsConsumerUnitError(String string) {
		isConsumerUnitError = string;
	}
	/**
	* Determine Security for each page(Section) of the GTIN Update
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       String[]
	*          [0] - is Administrator          -- Y/N
	* 		   [1] - Can Update General Setup  -- Y/N
	*          [2] - Can Update Physical Spec  -- Y/N
	*          [3] - Can Update Classification -- Y/N 
	*
	* Creation date: (12/22/2005 10:45:29 AM -- TWalton)
	*/
	public static String[] getSecurity(
			HttpServletRequest request,
			HttpServletResponse response) {
			
		String[] returnString = new String[4];
		returnString[0] = "N"; // Administrator 
		returnString[1] = "N"; // General Setup
		returnString[2] = "N"; // Physical Spec
		returnString[3] = "N"; // Classification
		
		try
		{
			 try
			 {
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("8"))
			          returnString[0] = "Y";
			    }
			 }
			 catch(Exception e)
			 {}
			 if (returnString[0].equals("N"))
			 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				           if (groupsR[xr].equals("41")) // Administrator
				              returnString[0] = "Y";
				           if (groupsR[xr].equals("44")) // Main Setup
				           	  returnString[1] = "Y";
				           if (groupsR[xr].equals("42")) // Physical Attributes
				           	  returnString[2] = "Y";
				           if (groupsR[xr].equals("43")) // Item & Classification
				           	  returnString[3] = "Y";
				     }
				   }
				   catch(Exception e)
				   {}
			 }  
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in UpdGTIN.getSecurity(request, response)" + e);
		}
	    return returnString;
	}
	/**
	 * Send in a GTIN,
	 *    Test to see if it is in the FILE
	 *    IF not in the file, add it to the 
	 *    file with PLEASE UPDATE in the desc.
	 * 
	 * Creation date: (3/13/2006)
	 */
	public static String testAndAddGTIN(
		String gtinValue) {
		 StringBuffer returnString = new StringBuffer();
		 if (gtinValue.length() == 14)
		 {	
            GTIN thisGTIN = new GTIN();		
		    try
		    {
			  thisGTIN = ServiceGTIN.buildGtin(gtinValue);
		    }
		    catch(Exception e)
		    {
		    }
		    if (thisGTIN == null ||
		  	    thisGTIN.getGtinNumber() == null ||
			    thisGTIN.getGtinDescription() == null)
		    {// YOU MUST ADD THIS GTIN -- AS NEW
		    	try
				{
		    		UpdGTIN thisOne = new UpdGTIN();
		    		thisOne.setGtinNumber(gtinValue);
		    		thisOne.setLongDescription("PLEASE UPDATE");
		    		thisOne.setShortDescription("PLEASE UPDATE");
		    		ServiceGTIN.addGTIN(thisOne);
				}
		    	catch(Exception e)
				{
		    		System.out.println("problem:" + e);
		    	}
		    }		 
		 }
		return returnString.toString();
	}
	public String getPublishTypeToUCCNet() {
		return publishTypeToUCCNet;
	}
	public void setPublishTypeToUCCNet(String publishTypeToUCCNet) {
		this.publishTypeToUCCNet = publishTypeToUCCNet;
	}
	/**
	* Build Vector of DropDownSingle for How something can be Published to the world.
	*
	* Creation date: (1/18/2011 TWalton)
	*/
	private static Vector listPublishType() {
		Vector returnList = new Vector();
	
		DropDownSingle thisClass = new DropDownSingle();
		thisClass.setDescription("Add");
		thisClass.setValue("A");
		returnList.add(thisClass);
		thisClass = new DropDownSingle();
		thisClass.setDescription("Modify");
		thisClass.setValue("M");
		returnList.add(thisClass);
	
		return returnList;
	}
	public String getEanUCCCodeA() {
		return eanUCCCodeA;
	}
	public void setEanUCCCodeA(String eanUCCCodeA) {
		this.eanUCCCodeA = eanUCCCodeA;
	}
	public String getEanUCCCodeB() {
		return eanUCCCodeB;
	}
	public void setEanUCCCodeB(String eanUCCCodeB) {
		this.eanUCCCodeB = eanUCCCodeB;
	}
	public String getEanUCCCodeC() {
		return eanUCCCodeC;
	}
	public void setEanUCCCodeC(String eanUCCCodeC) {
		this.eanUCCCodeC = eanUCCCodeC;
	}
	public String getEanUCCCodeD() {
		return eanUCCCodeD;
	}
	public void setEanUCCCodeD(String eanUCCCodeD) {
		this.eanUCCCodeD = eanUCCCodeD;
	}
	/**
	* Format the Error Message to be displayd on the page.
	*
	* Creation date: (02/17/2006 TWalton)
	*/
	private static String addFormatToErrorForDisplay(String errorString) {
		StringBuffer returnString = new StringBuffer();
		if (!errorString.equals(""))
		{	
			returnString.append("<font style=\"color:#990000\"><b>");
			returnString.append(errorString);
			returnString.append("</b></font>");
		}	
	    return returnString.toString();
	}
}
