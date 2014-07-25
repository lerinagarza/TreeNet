/*
 * Created on November 5, 2008
 *
 */

package com.treetop.app.rawfruit;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.treetop.businessobjectapplications.BeanItem;
import com.treetop.businessobjectapplications.BeanRawFruit;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Lot;
import com.treetop.businessobjects.RawFruitLot;
import com.treetop.businessobjects.RawFruitPO;
import com.treetop.businessobjects.RawFruitPayCode;
import com.treetop.businessobjects.RawFruitPayment;
import com.treetop.businessobjects.RawFruitSpecialCharges;
import com.treetop.businessobjects.Supplier;
import com.treetop.services.*;
import com.treetop.SessionVariables;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.utilities.html.*;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;
import com.treetop.utilities.*;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitLot extends BaseViewBeanR1  {
	
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String receivingDate = "";
	// Fields Available for Update
	public String scaleSequence = "";
	public String poNumber = "";
	public String poLineNumber = "";
	public String lotNumber = "";
	public String lotNumberError = "";
	public String originalLotNumber = "";
	public String lotSequenceNumber = "0";
	public String facility = "";
	public String warehouse = "";
	public String location = "";
	public String harvestYear = "";
	public String supplierNumber = "";
	public String supplierNumberError = "";
	public String writeUpNumber = "";
	public String writeUpNumberError = "";
	public String supplierDeliveryNote = "";
	public String crop = "";
	public String organic = "";
	public String intendedUse = "";
	public String variety = "";
	public String countryOfOrigin = "USA";
	public String additionalVariety = "";
	public String itemNumber = "75000";
	public String itemNumberError = "";
	public String cullsPounds = "0";
	public String cullsPoundsError = "";
	public String cullsPercent = "0";
	public String cullsPercentError = "";
	public String brix = "";
	public String brixError = "";
	public String brixPrice = "0";
	public String runType = "";
	public String acceptedBins25 = "";
	public String acceptedBins25Error = "";
	public String acceptedBins30 = "";
	public String acceptedBins30Error = "";
	public String acceptedBinsComment = "";
	public String rejectedBins25 = "";
	public String rejectedBins25Error = "";
	public String rejectedBins30 = "";
	public String rejectedBins30Error = "";
	public String rejectedBinsComment = "";
	public String acceptedWeight = "";
	public String acceptedWeightError = "";
	public String acceptedWeightKeyed = "";
	public String rejectedWeight = "";
	public String rejectedWeightError = "";
	public String rejectedWeightKeyed = "";
	
	public Vector ddChargeCodes = new Vector(); // Vector will be built only ONCE
	public Vector ddPaymentType = new Vector(); // Vector will be built only ONCE
	public Vector ddRunType = new Vector(); // Vector will be built only ONCE
	public Vector ddCategory = new Vector(); // Vector will be built only ONCE
	public Vector ddCrop = new Vector(); // Vector will be built only ONCE
	public Vector ddOrganic = new Vector(); // Vector will be built only ONCE
	public Vector ddVariety = new Vector(); // Vector will be built only ONCE
	
	public static Vector ddQEItem				= new Vector();
	public static Vector ddQEAttributeModel	= new Vector();
	public static Vector ddQEVariety			= new Vector();
	public static Vector ddQERunType			= new Vector();
	
	public String backToLoad = "";
	public String saveButton = "";
	
	public BeanRawFruit updBean = new BeanRawFruit();
	public RawFruitPO rfPO = new RawFruitPO();
	
	public Vector listPayment = new Vector(); // Vector of UpdRawFruitLotPayment
	public String countPayments = "0";
	public String nextLotSequence = "";
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		//-------------------------------------------------------------------
		// Lot Number
		if (this.lotNumber.trim().equals(""))
			this.setLotNumberError("Lot Number MUST Be Chosen");
		else
		{
			if (this.scaleTicketCorrectionSequence.equals("0"))
			{
			   int findDash = this.lotNumber.indexOf("-");
			   if (findDash >= 0)
			      this.setLotNumberError("Please do not use a Dash in the Lot Number");
			}
		}
		//-------------------------------------------------------------------
		// Accepted Bins -- 25 Boxes Per Bin - Check to make sure Integer
		if (this.acceptedBins25.trim().equals(""))
			this.setAcceptedBins25("0");
		if (!this.acceptedBins25.trim().equals("0"))
			this.setAcceptedBins25Error(validateInteger(this.acceptedBins25));
		//-------------------------------------------------------------------
		// Accepted Bins -- 30 Boxes Per Bin - Check to make sure Integer
		if (this.acceptedBins30.trim().equals(""))
			this.setAcceptedBins30("0");
		if (!this.acceptedBins30.trim().equals("0"))
			this.setAcceptedBins30Error(validateInteger(this.acceptedBins30));
		//-------------------------------------------------------------------
		// Rejected Bins -- 25 Boxes Per Bin - Check to make sure Integer
		if (this.rejectedBins25.trim().equals(""))
			this.setRejectedBins25("0");
		if (!this.rejectedBins25.trim().equals("0"))
			this.setRejectedBins25Error(validateInteger(this.rejectedBins25));
		//-------------------------------------------------------------------
		// Rejected Bins -- 30 Boxes Per Bin - Check to make sure Integer
		if (this.rejectedBins30.trim().equals(""))
			this.setRejectedBins30("0");
		if (!this.rejectedBins30.trim().equals("0"))
			this.setRejectedBins30Error(validateInteger(this.rejectedBins30));
		//-------------------------------------------------------------------
		// Accepted Weight - Check to make sure Integer
		if (this.acceptedWeight.trim().equals(""))
			this.setAcceptedWeight("0");
		if (!this.acceptedWeight.trim().equals("0"))
			this.setAcceptedWeightError(validateInteger(this.acceptedWeight));
		//-------------------------------------------------------------------
		// Rejected Weight - Check to make sure Integer
		if (this.rejectedWeight.trim().equals(""))
			this.setRejectedWeight("0");
		if (!this.rejectedWeight.trim().equals("0"))
			this.setRejectedWeightError(validateInteger(this.rejectedWeight));		
		//-------------------------------------------------------------------
		// Culls Pounds  - Check to make sure BigDecimal
		if (this.cullsPounds.trim().equals(""))
			this.setCullsPounds("0");
		if (!this.cullsPounds.trim().equals("0"))
			this.setCullsPoundsError(validateBigDecimal(this.cullsPounds));
		//-------------------------------------------------------------------
		// Culls Percent  - Check to make sure BigDecimal
		if (this.cullsPercent.trim().equals(""))
			this.setCullsPounds("0");
		if (!this.cullsPercent.trim().equals("0"))
			this.setCullsPercentError(validateBigDecimal(this.cullsPercent));		
		//-------------------------------------------------------------------
		// Brix - Check to make sure BigDecimal
		if (this.brix.trim().equals(""))
			this.setBrix("0");
		if (!this.brix.trim().equals("0"))
			this.setBrixError(validateBigDecimal(this.brix));
//		-------------------------------------------------------------------
		// Run - Machine run - Orchard Run
		if (this.runType.trim().equals("MR OR"))
			this.setRunType("MR+OR");
		//-------------------------------------------------------------------
		// Supplier Number -- Validate that it is a VALID Supplier
//		if (!this.getSupplierNumber().trim().equals(""))
//		{
//			try
//			{
//				this.setSupplierNumberError(ServiceSupplier.verifySupplier("PRD", this.getSupplierNumber()));
//			}
//			catch(Exception e)
//			{
//				this.setSupplierNumberError(this.getSupplierNumber() + " is not Valid.  ");
//			}
//		}
		//-------------------------------------------------------------------
		// Item Number -- Validate that it is a VALID Item
		if (!this.getItemNumber().trim().equals(""))
		{
			try
			{
				this.setItemNumberError(ServiceItem.verifyItem("PRD", this.getItemNumber()));
				if (this.getItemNumberError().trim().equals("") &&
					this.getCrop().trim().equals("") &&
					this.getVariety().trim().equals(""))
				{
					try
					{
						BeanItem ib = ServiceItem.buildNewItem("", this.getItemNumber());
						//Find Crop
						Vector sendValues = new Vector();
						sendValues.addElement(ib.getItemClass().getAttributeModel());
						sendValues.addElement("CROP");
						Vector retrieveRecords = ServiceAttribute.dropDownAttributeValues(sendValues);
						if(retrieveRecords.size() == 1)
						{
							this.setCrop(((DropDownSingle) retrieveRecords.elementAt(0)).getValue());
						}
						//Find Variety
						retrieveRecords = new Vector();
						sendValues = new Vector();
						sendValues.addElement(ib.getItemClass().getAttributeModel());
						sendValues.addElement("VAR");
						retrieveRecords = ServiceAttribute.dropDownAttributeValues(sendValues);
						if(retrieveRecords.size() == 1)
						{
							this.setVariety(((DropDownSingle) retrieveRecords.elementAt(0)).getValue());
						}
					}
					catch(Exception e)
					{	}
				}
			}
			catch(Exception e)
			{
				this.setItemNumberError(this.getItemNumber() + " is not Valid.  ");
			}
		}		
		//-------------------------------------------------------------------
		// Lot Number -- Validate that it is a VALID Item
		if (this.getLotNumberError().trim().equals("") &&
			!this.getLotNumber().trim().equals(""))
		{
			try
			{
				Vector sendParms = new Vector();
				sendParms.addElement(this.getLotNumber());
				sendParms.addElement(this.getScaleTicket());
				sendParms.addElement(this.getScaleSequence());
				sendParms.addElement(this.getLotSequenceNumber());
				this.setLotNumberError(ServiceLot.verifyRawFruitLotNumber("PRD", sendParms));
			}
			catch(Exception e)
			{
				this.setLotNumberError(this.getLotNumber() + " is not Valid.  ");
			}
		}		
		//------------------------------------------------------------
		// IF going back to the Load, MUST Update the Lot First
		if (this.getSaveButton().equals("") && !this.getBackToLoad().equals(""))
			this.setSaveButton("Process");
		//------------------------------------------------------------
		// Set up the Display Message Piece -- FOR the ENTIRE PAGE
		StringBuffer sb = new StringBuffer();
		if (!this.getLotNumberError().trim().equals(""))
		   sb.append(this.getLotNumberError() + "<br>");
		if (!this.getAcceptedBins25Error().trim().equals(""))
		   sb.append(this.getAcceptedBins25Error() + "<br>");
		if (!this.getAcceptedBins30Error().trim().equals(""))
		   sb.append(this.getAcceptedBins30Error() + "<br>");
		if (!this.getRejectedBins25Error().trim().equals(""))
		   sb.append(this.getRejectedBins25Error() + "<br>");
		if (!this.getRejectedBins30Error().trim().equals(""))
		   sb.append(this.getRejectedBins30Error() + "<br>");
		if (!this.getAcceptedWeightError().trim().equals(""))
		   sb.append(this.getAcceptedWeightError() + "<br>");
		if (!this.getRejectedWeightError().trim().equals(""))
		   sb.append(this.getRejectedWeightError() + "<br>");
		if (!this.getCullsPoundsError().trim().equals(""))
		   sb.append(this.getCullsPoundsError() + "<br>");
		if (!this.getCullsPercentError().trim().equals(""))
		   sb.append(this.getCullsPercentError() + "<br>");
		if (!this.getBrixError().trim().equals(""))
		   sb.append(this.getBrixError() + "<br>");		
//		if (!this.getSupplierNumberError().trim().equals(""))
//		   sb.append(this.getSupplierNumberError() + "<br>");
		if (!this.getItemNumberError().trim().equals("") &&
			!this.getItemNumber().trim().equals("75000"))
		   sb.append(this.getItemNumberError() + "<br>");
		if (!sb.toString().trim().equals(""))
		   this.setDisplayMessage(sb.toString());	
		
		return;
	}
	/**
	 * @return Returns the lotNumber.
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 * @return Returns the originalLotNumber.
	 */
	public String getOriginalLotNumber() {
		return originalLotNumber;
	}
	/**
	 * @param originalLotNumber The originalLotNumber to set.
	 */
	public void setOriginalLotNumber(String originalLotNumber) {
		this.originalLotNumber = originalLotNumber;
	}
	/**
	 * @return Returns the crop.
	 */
	public String getCrop() {
		return crop;
	}
	/**
	 * @param crop The crop to set.
	 */
	public void setCrop(String crop) {
		this.crop = crop;
	}
	/**
	 * @return Returns the facility.
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility The facility to set.
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return Returns the harvestYear.
	 */
	public String getHarvestYear() {
		return harvestYear;
	}
	/**
	 * @param harvestYear The harvestYear to set.
	 */
	public void setHarvestYear(String harvestYear) {
		this.harvestYear = harvestYear;
	}
	/**
	 * @return Returns the intendedUse.
	 */
	public String getIntendedUse() {
		return intendedUse;
	}
	/**
	 * @param intendedUse The intendedUse to set.
	 */
	public void setIntendedUse(String intendedUse) {
		this.intendedUse = intendedUse;
	}
	/**
	 * @return Returns the itemNumber.
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return Returns the organic.
	 */
	public String getOrganic() {
		return organic;
	}
	/**
	 * @param organic The organic to set.
	 */
	public void setOrganic(String organic) {
		this.organic = organic;
	}
	/**
	 * @return Returns the receivingDate.
	 */
	public String getReceivingDate() {
		return receivingDate;
	}
	/**
	 * @param receivingDate The receivingDate to set.
	 */
	public void setReceivingDate(String receivingDate) {
		this.receivingDate = receivingDate;
	}
	/**
	 * @return Returns the runType.
	 */
	public String getRunType() {
		return runType;
	}
	/**
	 * @param runType The runType to set.
	 */
	public void setRunType(String runType) {
		this.runType = runType;
	}
	/**
	 * @return Returns the supplierNumber.
	 */
	public String getSupplierNumber() {
		return supplierNumber;
	}
	/**
	 * @param supplierNumber The supplierNumber to set.
	 */
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}
	/**
	 * @return Returns the variety.
	 */
	public String getVariety() {
		return variety;
	}
	/**
	 * @param variety The variety to set.
	 */
	public void setVariety(String variety) {
		this.variety = variety;
	}
	/**
	 * @return Returns the warehouse.
	 */
	public String getWarehouse() {
		return warehouse;
	}
	/**
	 * @param warehouse The warehouse to set.
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Choosing a Facility and then getting the Appropriate Times 
	 *    along with it.
	 */
	public static Vector buildDropDownFacilityWarehouse(String inFacility, String inWarehouse) {
		Vector dd = new Vector();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement("ddRFWhseFacility");
		   Vector getRecords = ServiceWarehouse.dualDropDownWarehouseFacility("PRD", sentValues);
		   
	      // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(getRecords, "facility", "N", inFacility, "warehouse", "N", inWarehouse, "B", "B");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a Dual Drop Down Vector
	 *    Which will allow a Dual Drop Down on the Screen for
	 *    Will Display a Valid listing for Harvest Year
	 */
	public static String buildDropDownHarvestYear(String inHarvestYear) {
		String dd = new String();
		try
		{
		   if (inHarvestYear.equals(""))
		   {
		   	  DateTime dt = UtilityDateTime.getSystemDate();
		   	  inHarvestYear = dt.getM3FiscalYear();
		   	  try
			  {
		   	  	 inHarvestYear = ((new Integer(dt.getM3FiscalYear())).intValue() - 1) + "";
			  }
		   	  catch(Exception e)
			  {}
		   }
		   Vector sentValues = new Vector();
		   sentValues.addElement("listAllYears");
		   Vector getRecords = UtilityDateTime.dropDownYear(sentValues);
		   
	      // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(getRecords, "harvestYear", inHarvestYear, "None", "N", "N");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * @return Returns the supplierDeliveryNote.
	 */
	public String getSupplierDeliveryNote() {
		return supplierDeliveryNote;
	}
	/**
	 * @param supplierDeliveryNote The supplierDeliveryNote to set.
	 */
	public void setSupplierDeliveryNote(String supplierDeliveryNote) {
		this.supplierDeliveryNote = supplierDeliveryNote;
	}
	/**
	 * @return Returns the poNumber.
	 */
	public String getPoNumber() {
		return poNumber;
	}
	/**
	 * @param poNumber The poNumber to set.
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	/**
	 * @return Returns the scaleSequence.
	 */
	public String getScaleSequence() {
		return scaleSequence;
	}
	/**
	 * @param scaleSequence The scaleSequence to set.
	 */
	public void setScaleSequence(String scaleSequence) {
		this.scaleSequence = scaleSequence;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing for Item Group which is used as Crop
	 */
	public static String buildDropDownLotCrop(String attributeModel,
										      String inCrop, 
										      String readOnly) {
		String dd = new String();
		try
		{
		      Vector sentValues = new Vector();
			  sentValues.addElement(attributeModel);
			  sentValues.addElement("CROP");
			  Vector getRecords = ServiceAttribute.dropDownAttributeValues(sentValues);
			  String chooseInfo = "Choose One";
			  if (getRecords.size() == 1)
			  	 chooseInfo = "None";
			  if (getRecords.size() == 0)
			     dd = "No Available Values for CROP in Attribute Model: " + attributeModel;
			  else
		      // Call the Build utility to build the actual code for the drop down.
			     dd = DropDownSingle.buildDropDown(getRecords, "crop", inCrop, chooseInfo, "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * @return Returns the ddChargeCodes.
	 */
	public Vector getDdChargeCodes() {
		return ddChargeCodes;
	}
	/**
	 * @param ddChargeCodes The ddChargeCodes to set.
	 */
	public void setDdChargeCodes(Vector ddChargeCodes) {
		this.ddChargeCodes = ddChargeCodes;
	}
	/**
	 *    Will Generate the Vectors for ALL Drop Down Lists Needed multiple times
	 */
	public void buildDropDownVectors() {
		try
		{
		   Vector sentValues = new Vector();
		   ddChargeCodes = ServiceFinance.dropDownRFChargeCode(sentValues);
		   sentValues.addElement("payCode");
		   ddPaymentType = ServiceRawFruit.dropDownPaymentType(sentValues);
		   ddCategory = ServiceRawFruit.dropDownCategory(sentValues);
		   ddRunType = ServiceRawFruit.dropDownMachineOrchard(sentValues);
		   ddOrganic = ServiceRawFruit.dropDownConvOrganic(sentValues);
		   ddCrop = ServiceRawFruit.dropDownCrop(sentValues);
		   ddVariety = ServiceRawFruit.dropDownVariety(sentValues);
		   
		   CommonRequestBean qeCrb = new CommonRequestBean("PRD");
		   Vector listQEDDs = ServiceRawFruit.buildLotItemVarietyRuntype(qeCrb);
		   ddQEItem = (Vector) listQEDDs.elementAt(0);
		   ddQEAttributeModel = (Vector) listQEDDs.elementAt(1);
		   ddQEVariety = (Vector) listQEDDs.elementAt(2);
		   ddQERunType = (Vector) listQEDDs.elementAt(3);
		   
		   
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownChargeCode(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddChargeCodes, fieldName, inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing for Item Group which is used as Crop
	 */
	public static String buildDropDownIntendedUse(String attributeModel,
												  String inValue, 
												  String readOnly) {
		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement(attributeModel);
		   sentValues.addElement("IDUSE");
		   Vector getRecords = ServiceAttribute.dropDownAttributeValues(sentValues);
		   String chooseInfo = "Choose One";
		   if (getRecords.size() == 1)
			 chooseInfo = "None";
		   if (getRecords.size() == 0)
		     dd = "No Available Values for IDUSE (Intended Use) in Attribute Model: " + attributeModel;
		  else
		  	// Call the Build utility to build the actual code for the drop down.
		  	 dd = DropDownSingle.buildDropDown(getRecords, "intendedUse", inValue, chooseInfo, "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	public static String buildDropDownRun(
			  String attributeModel,
			  String inValue, 
			  String readOnly) {
		return buildDropDownRun("runType", attributeModel, inValue, readOnly);
	}
	
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 */
	public static String buildDropDownRun(String name,
										  String attributeModel,
										  String inValue, 
										  String readOnly) {
		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   sentValues.addElement(attributeModel);
		   sentValues.addElement("RUN");
		   Vector getRecords = ServiceAttribute.dropDownAttributeValues(sentValues);
		   String chooseInfo = "Choose One";
		   if (getRecords.size() == 1)
			 chooseInfo = "None";
		   if (getRecords.size() == 0)
		     dd = "No Available Values for RUN (Run Type) in Attribute Model: " + attributeModel;
		   else
		   	// Call the Build utility to build the actual code for the drop down.
		     dd = DropDownSingle.buildDropDown(getRecords, name, inValue, chooseInfo, "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	public static String buildDropDownLotVariety(
			 String attributeModel,
		     String inValue, 
		     String readOnly) {
		return buildDropDownLotVariety("variety", attributeModel, inValue, readOnly);
	}
	
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 */
	public static String buildDropDownLotVariety(String name,
												 String attributeModel,
											     String inValue, 
											     String readOnly) {
		String dd = new String();
		try
		{
		      Vector sentValues = new Vector();
			  sentValues.addElement(attributeModel);
			  sentValues.addElement("VAR");
			  Vector getRecords = ServiceAttribute.dropDownAttributeValues(sentValues);
			  String chooseInfo = "Choose One";
			  if (getRecords.size() == 1)
			  	 chooseInfo = "None";
			  if (getRecords.size() == 0)
			     dd = "No Available Values for VAR (Variety) in Attribute Model: " + attributeModel;
			  else
			  	// Call the Build utility to build the actual code for the drop down.
			  	dd = DropDownSingle.buildDropDown(getRecords, name, inValue, chooseInfo, "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownCategory(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddCategory, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownPaymentType(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddPaymentType, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownRunType(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddRunType, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * @return Returns the ddCategory.
	 */
	public Vector getDdCategory() {
		return ddCategory;
	}
	/**
	 * @param ddCategory The ddCategory to set.
	 */
	public void setDdCategory(Vector ddCategory) {
		this.ddCategory = ddCategory;
	}
	/**
	 * @return Returns the ddPaymentType.
	 */
	public Vector getDdPaymentType() {
		return ddPaymentType;
	}
	/**
	 * @param ddPaymentType The ddPaymentType to set.
	 */
	public void setDdPaymentType(Vector ddPaymentType) {
		this.ddPaymentType = ddPaymentType;
	}
	/**
	 * @return Returns the ddRunType.
	 */
	public Vector getDdRunType() {
		return ddRunType;
	}
	/**
	 * @param ddRunType The ddRunType to set.
	 */
	public void setDdRunType(Vector ddRunType) {
		this.ddRunType = ddRunType;
	}
	/**
	 * @return Returns the supplierNumberError.
	 */
	public String getSupplierNumberError() {
		return supplierNumberError;
	}
	/**
	 * @param supplierNumberError The supplierNumberError to set.
	 */
	public void setSupplierNumberError(String supplierNumberError) {
		this.supplierNumberError = supplierNumberError;
	}
	/**
	 * @return Returns the itemNumberError.
	 */
	public String getItemNumberError() {
		return itemNumberError;
	}
	/**
	 * @param itemNumberError The itemNumberError to set.
	 */
	public void setItemNumberError(String itemNumberError) {
		this.itemNumberError = itemNumberError;
	}
	/**
	 * @return Returns the acceptedWeight.
	 */
	public String getAcceptedWeight() {
		return acceptedWeight;
	}
	/**
	 * @param acceptedWeight The acceptedWeight to set.
	 */
	public void setAcceptedWeight(String acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
	/**
	 * @return Returns the acceptedWeightError.
	 */
	public String getAcceptedWeightError() {
		return acceptedWeightError;
	}
	/**
	 * @param acceptedWeightError The acceptedWeightError to set.
	 */
	public void setAcceptedWeightError(String acceptedWeightError) {
		this.acceptedWeightError = acceptedWeightError;
	}
	/**
	 * @return Returns the acceptedWeightKeyed.
	 */
	public String getAcceptedWeightKeyed() {
		return acceptedWeightKeyed;
	}
	/**
	 * @param acceptedWeightKeyed The acceptedWeightKeyed to set.
	 */
	public void setAcceptedWeightKeyed(String acceptedWeightKeyed) {
		this.acceptedWeightKeyed = acceptedWeightKeyed;
	}
	/**
	 * @return Returns the rejectedWeight.
	 */
	public String getRejectedWeight() {
		return rejectedWeight;
	}
	/**
	 * @param rejectedWeight The rejectedWeight to set.
	 */
	public void setRejectedWeight(String rejectedWeight) {
		this.rejectedWeight = rejectedWeight;
	}
	/**
	 * @return Returns the rejectedWeightError.
	 */
	public String getRejectedWeightError() {
		return rejectedWeightError;
	}
	/**
	 * @param rejectedWeightError The rejectedWeightError to set.
	 */
	public void setRejectedWeightError(String rejectedWeightError) {
		this.rejectedWeightError = rejectedWeightError;
	}
	/**
	 * @return Returns the rejectedWeightKeyed.
	 */
	public String getRejectedWeightKeyed() {
		return rejectedWeightKeyed;
	}
	/**
	 * @param rejectedWeightKeyed The rejectedWeightKeyed to set.
	 */
	public void setRejectedWeightKeyed(String rejectedWeightKeyed) {
		this.rejectedWeightKeyed = rejectedWeightKeyed;
	}
	/**
	 * @return Returns the acceptedBins25.
	 */
	public String getAcceptedBins25() {
		return acceptedBins25;
	}
	/**
	 * @param acceptedBins25 The acceptedBins25 to set.
	 */
	public void setAcceptedBins25(String acceptedBins25) {
		this.acceptedBins25 = acceptedBins25;
	}
	/**
	 * @return Returns the acceptedBins25Error.
	 */
	public String getAcceptedBins25Error() {
		return acceptedBins25Error;
	}
	/**
	 * @param acceptedBins25Error The acceptedBins25Error to set.
	 */
	public void setAcceptedBins25Error(String acceptedBins25Error) {
		this.acceptedBins25Error = acceptedBins25Error;
	}
	/**
	 * @return Returns the acceptedBins30.
	 */
	public String getAcceptedBins30() {
		return acceptedBins30;
	}
	/**
	 * @param acceptedBins30 The acceptedBins30 to set.
	 */
	public void setAcceptedBins30(String acceptedBins30) {
		this.acceptedBins30 = acceptedBins30;
	}
	/**
	 * @return Returns the acceptedBins30Error.
	 */
	public String getAcceptedBins30Error() {
		return acceptedBins30Error;
	}
	/**
	 * @param acceptedBins30Error The acceptedBins30Error to set.
	 */
	public void setAcceptedBins30Error(String acceptedBins30Error) {
		this.acceptedBins30Error = acceptedBins30Error;
	}
	/**
	 * @return Returns the rejectedBins25.
	 */
	public String getRejectedBins25() {
		return rejectedBins25;
	}
	/**
	 * @param rejectedBins25 The rejectedBins25 to set.
	 */
	public void setRejectedBins25(String rejectedBins25) {
		this.rejectedBins25 = rejectedBins25;
	}
	/**
	 * @return Returns the rejectedBins25Error.
	 */
	public String getRejectedBins25Error() {
		return rejectedBins25Error;
	}
	/**
	 * @param rejectedBins25Error The rejectedBins25Error to set.
	 */
	public void setRejectedBins25Error(String rejectedBins25Error) {
		this.rejectedBins25Error = rejectedBins25Error;
	}
	/**
	 * @return Returns the rejectedBins30.
	 */
	public String getRejectedBins30() {
		return rejectedBins30;
	}
	/**
	 * @param rejectedBins30 The rejectedBins30 to set.
	 */
	public void setRejectedBins30(String rejectedBins30) {
		this.rejectedBins30 = rejectedBins30;
	}
	/**
	 * @return Returns the rejectedBins30Error.
	 */
	public String getRejectedBins30Error() {
		return rejectedBins30Error;
	}
	/**
	 * @param rejectedBins30Error The rejectedBins30Error to set.
	 */
	public void setRejectedBins30Error(String rejectedBins30Error) {
		this.rejectedBins30Error = rejectedBins30Error;
	}
	/**
	 * @return Returns the brix.
	 */
	public String getBrix() {
		return brix;
	}
	/**
	 * @param brix The brix to set.
	 */
	public void setBrix(String brix) {
		this.brix = brix;
	}
	/**
	 * @return Returns the brixError.
	 */
	public String getBrixError() {
		return brixError;
	}
	/**
	 * @param brixError The brixError to set.
	 */
	public void setBrixError(String brixError) {
		this.brixError = brixError;
	}
	/**
	 * @return Returns the acceptedBinsComment.
	 */
	public String getAcceptedBinsComment() {
		return acceptedBinsComment;
	}
	/**
	 * @param acceptedBinsComment The acceptedBinsComment to set.
	 */
	public void setAcceptedBinsComment(String acceptedBinsComment) {
		this.acceptedBinsComment = acceptedBinsComment;
	}
	/**
	 * @return Returns the rejectedBinsComment.
	 */
	public String getRejectedBinsComment() {
		return rejectedBinsComment;
	}
	/**
	 * @param rejectedBinsComment The rejectedBinsComment to set.
	 */
	public void setRejectedBinsComment(String rejectedBinsComment) {
		this.rejectedBinsComment = rejectedBinsComment;
	}
	/**
	 * @return Returns the lotSequenceNumber.
	 */
	public String getLotSequenceNumber() {
		return lotSequenceNumber;
	}
	/**
	 * @param lotSequenceNumber The lotSequenceNumber to set.
	 */
	public void setLotSequenceNumber(String lotSequenceNumber) {
		this.lotSequenceNumber = lotSequenceNumber;
	}
	/**
	 * @return Returns the scaleTicket.
	 */
	public String getScaleTicket() {
		return scaleTicket;
	}
	/**
	 * @param scaleTicket The scaleTicket to set.
	 */
	public void setScaleTicket(String scaleTicket) {
		this.scaleTicket = scaleTicket;
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
	 * @return Returns the requestType.
	 */
	public String getRequestType() {
		return requestType;
	}
	/**
	 * @param requestType The requestType to set.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	 * @return Returns the updBean.
	 */
	public BeanRawFruit getUpdBean() {
		return updBean;
	}
	/**
	 * @param updBean The updBean to set.
	 */
	public void setUpdBean(BeanRawFruit updBean) {
		this.updBean = updBean;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (11/17/2008 TWalton)
	 */
	public void loadFromBeanRawFruit(BeanRawFruit brf) {
		try {
			RawFruitLot rfl = brf.getRfLot();
			this.scaleTicket = rfl.getScaleTicketNumber();
			// Reset the Date to work on the Screen
			try
			{
				// Date Time Issue with Receiving Date
				DateTime recDate = UtilityDateTime.getDateFromyyyyMMdd(rfl.getReceivingDate());
				this.receivingDate = recDate.getDateFormatMMddyyyySlash();
			}
			catch(Exception e)
			{}
			this.scaleSequence = rfl.getScaleSequence();
			this.poNumber = rfl.getPoNumber();
			this.poLineNumber = rfl.getPoLineNumber();
			this.lotNumber = rfl.getLotNumber();
			this.originalLotNumber = rfl.getLotNumber();
			this.lotSequenceNumber = rfl.getLotSequence();
			try
			{
				Lot lot = rfl.getLotInformation();
				this.facility = lot.getFacility();
				this.warehouse = lot.getWarehouse();
				this.location = lot.getLocation();
				this.brix = lot.getBrix();
				this.itemNumber = lot.getItemNumber();
			}
			catch(Exception e)
			{}
			if (!this.itemNumber.trim().equals("75000") &&
				!this.itemNumber.trim().equals(""))
			   this.itemNumberError = "";
			this.harvestYear = rfl.getHarvestYear();
			try
			{
			   Supplier sup = rfl.getLotSupplier();	
			   this.supplierNumber = sup.getSupplierNumber();
			}
			catch(Exception e)
			{}
			this.brixPrice = rfl.getCalculatedBrixPrice();
			this.supplierDeliveryNote = rfl.getSupplierLoadNumber();
			this.crop = rfl.getCrop();
			this.organic = rfl.getOrganicConventional();
			this.intendedUse = rfl.getIntendedUse();
			this.variety = rfl.getVariety();
			this.additionalVariety = rfl.getAdditionalVariety();
			this.countryOfOrigin = rfl.getCountryOfOrigin();
			this.cullsPounds = rfl.getCullsPounds();
			this.cullsPercent = rfl.getCullsPercent();
			this.runType = rfl.getRunType();
			this.acceptedBins25 = rfl.getLotAcceptedBins25Box();
			this.acceptedBins30 = rfl.getLotAcceptedBins30Box();
			this.acceptedBinsComment = rfl.getLotAcceptedComments().trim();
			this.rejectedBins25 = rfl.getLotRejectedBins25Box();
			this.rejectedBins30 = rfl.getLotRejectedBins30Box();
			this.rejectedBinsComment = rfl.getLotRejectedComments().trim();
			this.acceptedWeight = rfl.getLotAcceptedWeight();
			this.acceptedWeightKeyed = rfl.getFlagAcceptedWeightManual().trim();
			this.rejectedWeight = rfl.getLotRejectedWeight();
			this.rejectedWeightKeyed = rfl.getFlagRejectedWeightManual().trim();
			this.writeUpNumber = rfl.getLotWriteUpNumber().trim();
		
			// Build the Payment Vector from the Bean Information
			//  AND Include the Special Charges for Each Payment
			Vector listPaymentsForLot = new Vector();
			int paymentCount = rfl.getListPayments().size();
			if (paymentCount > 0)
			{
				for (int pay = 0; pay < paymentCount; pay++)
				{
					UpdRawFruitLotPayment urflPayment = new UpdRawFruitLotPayment();
					try
					{
					   RawFruitPayment rfp = (RawFruitPayment) rfl.getListPayments().elementAt(pay);
					   RawFruitPayCode rfpc = rfp.getPayCode();
					   urflPayment.setPaymentInfo(rfp);
					   urflPayment.setPaymentType(rfpc.getPaymentType());
					   urflPayment.setPaymentSequence(rfp.getPaymentSequenceNumber());
					   urflPayment.setRunType(rfpc.getRunType());
					   urflPayment.setCategory(rfpc.getCategory());
					   urflPayment.setVariety(rfpc.getVariety());
					   urflPayment.setCrop(rfpc.getCrop());
					   urflPayment.setOrganic(rfpc.getConvOrganic());
					   urflPayment.setNumberOfBins25Box(rfp.getPaymentBins25Box());
					   urflPayment.setNumberOfBins30Box(rfp.getPaymentBins30Box());
					   urflPayment.setPaymentWeight(rfp.getPaymentWeight());
					   urflPayment.setPaymentWeightManuallyEntered(rfp.getPaymentWeightHandKeyed());
					   urflPayment.setPrice(rfp.getFruitPriceHandKeyed());
					   urflPayment.setPayCodeHandKeyed(rfp.getPayCodeHandKeyed());
					   urflPayment.setWriteUpNumber(rfp.getPaymentWriteUpNumber().trim());
					
					   //Also Included in each Payment should be the Special Charges
					   Vector listSpecialCharges = new Vector();
					   int scCount = rfp.getListSpecialCharges().size();
					   if (scCount > 0)
					   {
					   	  for (int sc = 0; sc < scCount; sc++)
					   	  {
					   	  	UpdRawFruitLotPaymentSpecialCharges urflpSpecialCharges = new UpdRawFruitLotPaymentSpecialCharges();
					   	  	try
							{
					   	  		RawFruitSpecialCharges rfsc = (RawFruitSpecialCharges) rfp.getListSpecialCharges().elementAt(sc);
					   	  		urflpSpecialCharges.setSpecialChargesInfo(rfsc);
					   	  		urflpSpecialCharges.setPaymentSpecialChargesSequence(rfsc.getSpecialChargesSequenceNumber());
					   	  		urflpSpecialCharges.setSupplier(rfsc.getSupplierSpecialCharges().getSupplierNumber());
					   	  		urflpSpecialCharges.setAccountingOption(rfsc.getAccountingOption());
					   	  		urflpSpecialCharges.setRatePerPound(rfsc.getRate());
							}
					   	  	catch(Exception e)
							{}
					   	  	listSpecialCharges.addElement(urflpSpecialCharges);
					   	  }
					   }
					   urflPayment.setListSpecialCharges(listSpecialCharges);
					}
					catch(Exception e)
					{}
					listPaymentsForLot.addElement(urflPayment);
				}
			}
			this.listPayment = listPaymentsForLot;

		} catch (Exception e) {
	
		}
		return;
	}
	/**
	 * @return Returns the listPayment.
	 */
	public Vector getListPayment() {
		return listPayment;
	}
	/**
	 * @param listPayment The listPayment to set.
	 */
	public void setListPayment(Vector listPayment) {
		this.listPayment = listPayment;
	}
	/**
	 * @return Returns the countPayments.
	 */
	public String getCountPayments() {
		return countPayments;
	}
	/**
	 * @param countPayments The countPayments to set.
	 */
	public void setCountPayments(String countPayments) {
		this.countPayments = countPayments;
	}
	/*
	 * Test and Validate fields for Payments, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 *   Within this Process will ALSO have to run Populate SpecialCharges
	 * 
	 */
	public void populatePayments(HttpServletRequest request) {
		try
		{	
			Vector listP = new Vector();
			StringBuffer foundProblem = new StringBuffer();
			// Retrieve Payment information relating to the A Lot Number
			int countRecords = 0;
			countRecords = new Integer(this.getCountPayments()).intValue();
			if (countRecords > 0)
			{
			   for (int x = 0; x < countRecords; x++)	
			   {
			   	   UpdRawFruitLotPayment urflp = new UpdRawFruitLotPayment();
			   	   // Payment Type comes from a Drop Down List
			   	   urflp.setPaymentType(request.getParameter(x + "paymentType"));
			   	   if (urflp.getPaymentType() == null)
			   	   	  urflp.setPaymentType("");
			   	   // Run Type also Comes from a Drop Down List
			   	   urflp.setRunType(request.getParameter(x + "runType"));
			   	   if (urflp.getRunType() == null)
			   	   	  urflp.setRunType("");
			   	   // Category also Comes from a Drop Down List
			   	   urflp.setCategory(request.getParameter(x + "category"));
			   	   if (urflp.getCategory() == null)
			   	   	  urflp.setCategory("");
			   	   // Crop also Comes from a Drop Down List
			   	   urflp.setCrop(request.getParameter(x + "crop"));
			   	   if (urflp.getCrop() == null)
			   	   	  urflp.setCrop("");
			   	   if (this.getCrop().equals("GRAPE") &&
			   		   urflp.getCrop().equals(""))
			   		   urflp.setCrop(this.getCrop());
			   		  
			   	   // Organic or Conventional also Comes from a Drop Down List
			   	   urflp.setOrganic(request.getParameter(x + "organic"));
			   	   if (urflp.getOrganic() == null)
			   	   	  urflp.setOrganic("");
			   	   // Variety also Comes from a Drop Down List
			   	   urflp.setVariety(request.getParameter(x + "variety"));
			   	   if (urflp.getVariety() == null)
			   	   	  urflp.setVariety("");
//			   	Override the Pay Code
			   	   urflp.setPayCodeHandKeyed(request.getParameter(x + "payCodeHandKeyed"));
			   	   if (urflp.getPayCodeHandKeyed() == null)
			   	   	  urflp.setPayCodeHandKeyed("");
			   	   urflp.setNumberOfBins25Box(request.getParameter(x + "numberAt25PerBin"));
			   	   if (urflp.getNumberOfBins25Box() == null ||
			   	   	   urflp.getNumberOfBins25Box().trim().equals(""))
			   	   	  urflp.setNumberOfBins25Box("0");
			   	   urflp.setNumberOfBins30Box(request.getParameter(x + "numberAt30PerBin"));
			   	   if (urflp.getNumberOfBins30Box() == null ||
			   	   	   urflp.getNumberOfBins30Box().trim().equals(""))
			   	   	  urflp.setNumberOfBins30Box("0");
			   	   urflp.setPaymentWeight(request.getParameter(x + "paymentWeight"));
			   	   if (urflp.getPaymentWeight() == null ||
				   	   urflp.getPaymentWeight().trim().equals(""))
				   	  urflp.setPaymentWeight("0");
			   	   urflp.setPaymentWeightManuallyEntered(request.getParameter(x + "paymentWeightManuallyEntered"));
			   	   if (urflp.getPaymentWeightManuallyEntered() == null)
				   	  urflp.setPaymentWeightManuallyEntered(""); 
			   	   // 9/23/13 TWalton Added Write Up Number Data
			   	   urflp.setWriteUpNumber(request.getParameter(x + "writeUpNumber"));
			   	   if (urflp.getWriteUpNumber() == null ||
				   	   urflp.getWriteUpNumber().trim().equals(""))
			   	   {
			   		  if (this.getWriteUpNumber().trim().equals("")) 
			   		    urflp.setWriteUpNumber("");
			   		  else
			   			urflp.setWriteUpNumber(this.getWriteUpNumber().trim());
			   	   }
			   	   
			   	   urflp.setPrice(request.getParameter(x + "price"));
			   	   if (urflp.getPrice() == null ||
			   	   	   urflp.getPrice().trim().equals(""))
			   	   	  urflp.setPrice("0");
			   	   urflp.setCountSpecialCharges(request.getParameter(x + "countSpecialCharges"));
			   	   if (urflp.getCountSpecialCharges() == null)
			   	   	  urflp.setCountSpecialCharges("0");
			   	   // DETERMINE if this record needs to be saved for update
			   	   if (((!urflp.getPaymentType().trim().equals("") ||
			   	   	    !urflp.getRunType().trim().equals("") ||
					    !urflp.getCategory().trim().equals("") ||
					    !urflp.getCrop().trim().equals("") ||
			   	   	    !urflp.getNumberOfBins25Box().trim().equals("0") ||
			   	   	    !urflp.getNumberOfBins30Box().trim().equals("0") ||
					    !urflp.getPrice().trim().equals("0")) &&
					    !this.getCrop().equals("GRAPE")) ||
					    (this.getCrop().equals("GRAPE") &&
					    !urflp.getNumberOfBins25Box().trim().equals("0")))
			   	   {
			   	   	  urflp.setUpdateUser(this.updateUser);
			   	   	  urflp.setScaleTicket(this.scaleTicket);
			   	   	  urflp.setScaleTicketCorrectionSequence(this.scaleTicketCorrectionSequence);
			   	   	  urflp.setReceivingDate(this.receivingDate);
			   	   	  urflp.setScaleSequence(this.scaleSequence);
			   	   	  urflp.setPoNumber(this.poNumber);
			   	   	  urflp.setPoLineNumber(this.poLineNumber);
			   	   	  urflp.setLotNumber(this.lotNumber);
			   	   	  urflp.setLotSequenceNumber(this.lotSequenceNumber);
			   	   	  urflp.setItemNumber(this.itemNumber);
			   	   	  urflp.setPaymentSequence(x + "");
			   	   	  urflp.setBrix(this.brix);
			   	   	  
			   	   	  //------------------------------------------------------
			   	      //--Validate:  Number of Bins 25 Box
			   	      try
				      {
			   	      	  urflp.setNumberOfBins25BoxError(validateInteger(urflp.getNumberOfBins25Box()));
			   	      		if (!urflp.getNumberOfBins25BoxError().trim().equals(""))
			   	      			foundProblem.append(urflp.getNumberOfBins25BoxError() + "<br>");
				      }
			   	      catch(Exception e)
				      {
			   	         urflp.setNumberOfBins25BoxError("Problem Found with Number of Bins. " + e);
		   	      		 foundProblem.append("Problem Found with Number of Bins<br>");
				      }
			   	      //------------------------------------------------------
			   	      //--Validate:  Number of Bins 30 Box
			   	      try
				      {
			   	      	  urflp.setNumberOfBins30BoxError(validateInteger(urflp.getNumberOfBins30Box()));
			   	      		if (!urflp.getNumberOfBins30BoxError().trim().equals(""))
			   	      			foundProblem.append(urflp.getNumberOfBins30BoxError() + "<br>");
				      }
			   	      catch(Exception e)
				      {
			   	         urflp.setNumberOfBins30BoxError("Problem Found with Number of Bins. " + e);
		   	      		 foundProblem.append("Problem Found with Number of Bins<br>");
				      }
			   	      //------------------------------------------------------
			   	      //--Validate:  Payment Weight
			   	      if (!urflp.getPaymentWeightManuallyEntered().equals(""))
			   	      {
			   	    	  try
			   	    	  {
			   	    		  urflp.setPaymentWeight(FindAndReplace.remove(urflp.getPaymentWeight(), ","));
			   	    		  int checkNegative = urflp.getPaymentWeight().indexOf("-");
			   	    		  if ((checkNegative + 1) == urflp.getPaymentWeight().length())
			   	    			 urflp.setPaymentWeight("-" + urflp.getPaymentWeight().substring(0, checkNegative)); 
			   	    		  urflp.setPaymentWeightError(validateInteger(urflp.getPaymentWeight()));
			   	    		  if (!urflp.getPaymentWeightError().trim().equals(""))
			   	    			  foundProblem.append(urflp.getPaymentWeightError() + "<br>");
			   	    	  }
			   	    	  catch(Exception e)
			   	    	  {
			   	    		  urflp.setPaymentWeightError("Problem Found with Payment Weight. " + e);
			   	    		  foundProblem.append("Problem Found with Payment Weight<br>");
			   	    	  }
			   	      }
			   	      else
			   	    	  urflp.setPaymentWeight("0");
			   	      //------------------------------------------------------
			   	      //--Validate:  Price
			   	      try
				      {
			   	      	  urflp.setPriceError(validateBigDecimal(urflp.getPrice()));
			   	      		if (!urflp.getPriceError().trim().equals(""))
			   	      			foundProblem.append(urflp.getPriceError() + "<br>");
				      }
			   	      catch(Exception e)
				      {
			   	         urflp.setNumberOfBins30BoxError("Problem Found with Price. " + e);
		   	      		 foundProblem.append("Problem Found with Price<br>");
				      }
			   	      //------------------------------------------------------
			   	      //--Validate:  Override Pay Code
			   	      if (!urflp.getPayCodeHandKeyed().equals(""))
			   	      {
			   	    	  try
			   	    	  {
			   	    		 // Need to test the pay code, make sure it is valid
			   	    		  urflp.setPayCodeHandKeyedError(ServiceRawFruit.verifyPayCode(urflp.getPayCodeHandKeyed()));
			   	    		  if (!urflp.getPayCodeHandKeyedError().trim().equals(""))
			   	    			  foundProblem.append(urflp.getPayCodeHandKeyedError() + "<br>");
			   	    	  }
			   	    	  catch(Exception e)
			   	    	  {
			   	    		  urflp.setPaymentWeightError("Problem Found with Raw Fruit Payment Code. " + e);
			   	    		  foundProblem.append("Problem Found with Raw Fruit Payment Code<br>");
			   	    	  }
			   	      }
			   	      //***********************************************************************************************
			   	      // Retrieve and Validate the Special Charges for EACH Payment
			   	      Vector listSC = new Vector();
			   	      // Add A special Charge for the Pool Information... 
			   	      //           IF a payment is POOL, add as the 1st Special Charge Line 
			   	      
			   	      try
					  {
			   	         int countSCRecords = 0;
					     countSCRecords = new Integer(urflp.getCountSpecialCharges()).intValue();
					     if (countSCRecords > 0)
					     {
					        for (int sc = 0; sc < countSCRecords; sc++)	
					        {
					   	       UpdRawFruitLotPaymentSpecialCharges urflpsc = new UpdRawFruitLotPaymentSpecialCharges();
					   	       // Retrieve ALL the Parameters
					   	       // Supplier
					   	       urflpsc.setSupplier(request.getParameter(x + "supplier" + sc));
					   	       if (urflpsc.getSupplier() == null)
					   	       	 urflpsc.setSupplier("");
					   	       // Accounting Option
					   	       urflpsc.setAccountingOption(request.getParameter(x + "accountingOption" + sc));
					   	       if (urflpsc.getAccountingOption() == null ||
					   	       	   urflpsc.getAccountingOption().trim().equals(""))
					   	       	 urflpsc.setAccountingOption("0");
					   	       // Rate Per Pound
					   	       urflpsc.setRatePerPound(request.getParameter(x + "ratePerPound" + sc));
					   	       if (urflpsc.getRatePerPound() == null ||
					   	       	   urflpsc.getRatePerPound().trim().equals(""))
					   	       	 urflpsc.setRatePerPound("0");
					   	       // DETERMINE if this record needs to be saved for update
						   	   if (!urflpsc.getRatePerPound().trim().equals("0"))
						   	   {
						   	   	  urflpsc.setUpdateUser(this.updateUser);
						   	   	  urflpsc.setScaleTicket(this.scaleTicket);
						   	   	  urflpsc.setScaleTicketCorrectionSequence(this.scaleTicketCorrectionSequence);
						   	   	  urflpsc.setReceivingDate(this.receivingDate);
						   	   	  urflpsc.setScaleSequence(this.scaleSequence);
						   	   	  urflpsc.setPoNumber(this.poNumber);
						   	   	  urflpsc.setPoLineNumber(this.poLineNumber);
						   	   	  urflpsc.setLotNumber(this.lotNumber);
						   	   	  urflpsc.setLotSequenceNumber(this.lotSequenceNumber);
						   	   	  urflpsc.setPaymentSequence(x + "");
						   	   	  urflpsc.setPaymentSpecialChargesSequence(sc + "");
						   	     //----------------------------------------------------------------
						   	   	 //--Validate: Supplier
						   	      try
							      {
						   	      	 if (!urflpsc.getSupplier().trim().equals(""))
						   	      	 {	
						   	      	    urflpsc.setSupplierError(ServiceSupplier.verifySupplier("PRD", urflpsc.getSupplier()));
						   	      		if (!urflpsc.getSupplierError().trim().equals(""))
						   	      		  foundProblem.append(urflpsc.getSupplierError() + "<br>");
						   	      	 }
							      }
						   	      catch(Exception e)
							      {
						   	         urflpsc.setSupplierError("Problem Found with Supplier. " + e);
					   	      		 foundProblem.append("Problem Found with Supplier<br>");
							      }
							   	 //----------------------------------------------------------------
							   	 //--Validate: Rate Per Pound
							   	  try
								  {
							   	   	 if (!urflpsc.getRatePerPound().trim().equals(""))
							   	   	 {	
							   	   	    urflpsc.setRatePerPoundError(validateBigDecimal(urflpsc.getRatePerPound()));
							   	   		if (!urflpsc.getRatePerPoundError().trim().equals(""))
							   	   		  foundProblem.append(urflpsc.getRatePerPoundError() + "<br>");
							   	   	 }
								  }
							   	  catch(Exception e)
								  {
							   	     urflpsc.setSupplierError("Problem Found with Supplier. " + e);
						   	         foundProblem.append("Problem Found with Supplier<br>");
								  }
							   	  if (urflp.getPaymentType().equals("Pool") ||
							   		  (urflp.getPaymentType().equals("Cash") &&
							   		   !urflpsc.getAccountingOption().equals("30")))
							   	     listSC.addElement(urflpsc);
						   	   }
					        }
					     }
					     if (listSC.size() == 0)
				         {
						       // Add A special Charge for the Pool Information... 
						       //   IF a payment is POOL, add as the 1st Special Charge Line
					    	   // ONLY if no special charges have been entered
					    	 if (foundProblem.toString().trim().equals("") &&
					    	     urflp.getPaymentType().trim().equals("Pool") &&
					    	     !urflp.getVariety().trim().equals("") &&
					    	     !urflp.getOrganic().trim().equals("") &&
					    	     !urflp.getCrop().trim().equals("") &&
					    	     !urflp.getRunType().trim().equals("") &&
					    	     !urflp.getCategory().trim().equals("") &&
					    	     !urflp.getItemNumber().trim().equals(""))
							 {
					    		 DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(urflp.getReceivingDate());
					    		 RawFruitPayCode rfpc = new RawFruitPayCode();
								 try
								 {
									 Vector sendParms = new Vector();
									 sendParms.addElement(urflp.getVariety());
									 sendParms.addElement(urflp.getOrganic());
									 sendParms.addElement(urflp.getCrop());
									 sendParms.addElement(urflp.getRunType());
									 sendParms.addElement(urflp.getCategory());
									 sendParms.addElement(urflp.getPaymentType());
									 sendParms.addElement(urflp.getItemNumber());
									 sendParms.addElement(dt1.getDateFormatyyyyMMdd());
									 rfpc = ServiceRawFruit.findPayCode(sendParms);
								  }
								  catch(Exception e)
								  {
								  	 System.out.println("Debug the Payment Code Retrieval: " + e);
								  } 
					    		  if (!rfpc.getWithholdPerTon().equals("0"))
					    		  {
					    			  
					    			  UpdRawFruitLotPaymentSpecialCharges urflpsc = new UpdRawFruitLotPaymentSpecialCharges();
							   	       // Retrieve ALL the Parameters
							   	       // Supplier
							   	       urflpsc.setSupplier(this.supplierNumber);
							   	       // Accounting Option
							   	       urflpsc.setAccountingOption("30");
							   	       // Rate Per Pound
							   	       urflpsc.setRatePerPound("-" + rfpc.getWithholdPerTon());
							   	       // DETERMINE if this record needs to be saved for update
							   	   	   urflpsc.setUpdateUser(this.updateUser);
								   	   urflpsc.setScaleTicket(this.scaleTicket);
								   	   urflpsc.setScaleTicketCorrectionSequence(this.scaleTicketCorrectionSequence);
								   	   urflpsc.setReceivingDate(this.receivingDate);
								   	   urflpsc.setScaleSequence(this.scaleSequence);
								   	   urflpsc.setPoNumber(this.poNumber);
								   	   urflpsc.setPoLineNumber(this.poLineNumber);
								   	   urflpsc.setLotNumber(this.lotNumber);
								   	   urflpsc.setLotSequenceNumber(this.lotSequenceNumber);
								   	   urflpsc.setPaymentSequence(x + "");
								   	   urflpsc.setPaymentSpecialChargesSequence("1");
								   	   listSC.addElement(urflpsc);
					    		  }
					    	 }
					     }
					  }
			   	      catch(Exception eSC)
					  {
			   	      }
			   	      urflp.setListSpecialCharges(listSC);
			   	      //***********************************************************************************************
			   	      // Set the whole thing into the Vector
			   	      listP.addElement(urflp);
			   	   }
			   }
			   this.setListPayment(listP);
			   this.setDisplayMessage(this.displayMessage + foundProblem.toString().trim());
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught: "  + e);
		}
		return;
	}
	/**
	 * @return Returns the nextLotSequence.
	 */
	public String getNextLotSequence() {
		return nextLotSequence;
	}
	/**
	 * @param nextLotSequence The nextLotSequence to set.
	 */
	public void setNextLotSequence(String nextLotSequence) {
		this.nextLotSequence = nextLotSequence;
	}
	/**
	 * @return Returns the lotNumberError.
	 */
	public String getLotNumberError() {
		return lotNumberError;
	}
	/**
	 * @param lotNumberError The lotNumberError to set.
	 */
	public void setLotNumberError(String lotNumberError) {
		this.lotNumberError = lotNumberError;
	}
	/**
	 * @return Returns the cullsPounds.
	 */
	public String getCullsPounds() {
		return cullsPounds;
	}
	/**
	 * @param cullsPounds The cullsPounds to set.
	 */
	public void setCullsPounds(String cullsPounds) {
		this.cullsPounds = cullsPounds;
	}
	/**
	 * @return Returns the cullsPoundsError.
	 */
	public String getCullsPoundsError() {
		return cullsPoundsError;
	}
	/**
	 * @param cullsPoundsError The cullsPoundsError to set.
	 */
	public void setCullsPoundsError(String cullsPoundsError) {
		this.cullsPoundsError = cullsPoundsError;
	}
	/**
	 * @return Returns the backToLoad.
	 */
	public String getBackToLoad() {
		return backToLoad;
	}
	/**
	 * @param backToLoad The backToLoad to set.
	 */
	public void setBackToLoad(String backToLoad) {
		this.backToLoad = backToLoad;
	}
	/**
	 * @return Returns the ddCrop.
	 */
	public Vector getDdCrop() {
		return ddCrop;
	}
	/**
	 * @param ddCrop The ddCrop to set.
	 */
	public void setDdCrop(Vector ddCrop) {
		this.ddCrop = ddCrop;
	}
	/**
	 * @return Returns the ddOrganic.
	 */
	public Vector getDdOrganic() {
		return ddOrganic;
	}
	/**
	 * @param ddOrganic The ddOrganic to set.
	 */
	public void setDdOrganic(Vector ddOrganic) {
		this.ddOrganic = ddOrganic;
	}
	/**
	 * @return Returns the ddVariety.
	 */
	public Vector getDdVariety() {
		return ddVariety;
	}
	/**
	 * @param ddVariety The ddVariety to set.
	 */
	public void setDdVariety(Vector ddVariety) {
		this.ddVariety = ddVariety;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownConvOrganic(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddOrganic, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownCrop(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddCrop, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownVariety(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddVariety, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * @return Returns the additionalVariety.
	 */
	public String getAdditionalVariety() {
		return additionalVariety;
	}
	/**
	 * @param additionalVariety The additionalVariety to set.
	 */
	public void setAdditionalVariety(String additionalVariety) {
		this.additionalVariety = additionalVariety;
	}
	/**
	 * @return Returns the countryOfOrigin.
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	/**
	 * @param countryOfOrigin The countryOfOrigin to set.
	 */
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 */
	public static String buildDropDownLotAdditionalVariety(String attributeModel,
											               String inValue, 
											               String readOnly) {
		String dd = new String();
		try
		{
		      Vector sentValues = new Vector();
			  sentValues.addElement(attributeModel);
			  sentValues.addElement("VAR");
			  Vector getRecords = ServiceAttribute.dropDownAttributeValues(sentValues);
			  String chooseInfo = "Choose One";
			  if (getRecords.size() == 1)
			  	 chooseInfo = "None";
			  if (getRecords.size() == 0)
			     dd = "No Available Values for VAR (Variety) in Attribute Model: " + attributeModel;
			  else
			  	// Call the Build utility to build the actual code for the drop down.
			  	dd = DropDownSingle.buildDropDown(getRecords, "additionalVariety", inValue, chooseInfo, "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing of Country of Origin
	 */
	public static String buildDropDownLotCountryOfOrigin(String attributeModel,
											             String inValue, 
											             String readOnly) {
		String dd = new String();
		try
		{
		      Vector sentValues = new Vector();
			  sentValues.addElement(attributeModel);
			  sentValues.addElement("COO");
			  Vector getRecords = ServiceAttribute.dropDownAttributeValues(sentValues);
			  String chooseInfo = "Choose One";
			  if (getRecords.size() == 1)
			  	 chooseInfo = "None";
			  if (getRecords.size() == 0)
			     dd = "No Available Values for COO (Country of Origin) in Attribute Model: " + attributeModel;
			  else
			  	// Call the Build utility to build the actual code for the drop down.
			  	dd = DropDownSingle.buildDropDown(getRecords, "countryOfOrigin", inValue, chooseInfo, "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
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
	/**
	 * @return Returns the rfPO.
	 */
	public RawFruitPO getRfPO() {
		return rfPO;
	}
	/**
	 * @param rfPO The rfPO to set.
	 */
	public void setRfPO(RawFruitPO rfPO) {
		this.rfPO = rfPO;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
	public String getCullsPercent() {
		return cullsPercent;
	}
	public void setCullsPercent(String cullsPercent) {
		this.cullsPercent = cullsPercent;
	}
	public String getCullsPercentError() {
		return cullsPercentError;
	}
	public void setCullsPercentError(String cullsPercentError) {
		this.cullsPercentError = cullsPercentError;
	}
	public String getPoLineNumber() {
		return poLineNumber;
	}
	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing 
	 */
	public static String buildDropDownGrapeBrix(String organic, 
												String inValue,
											    String readOnly) {
		String dd = new String();
		try
		{
		      Vector sentValues = new Vector();
			  sentValues.addElement(organic);
			  Vector getRecords = ServiceRawFruit.dropDownGrapeBrix(sentValues);
			  String chooseInfo = "Choose One";
			  	// Call the Build utility to build the actual code for the drop down.
			  	dd = DropDownSingle.buildDropDown(getRecords, "brix", inValue, chooseInfo, "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	public String getBrixPrice() {
		return brixPrice;
	}
	public void setBrixPrice(String brixPrice) {
		this.brixPrice = brixPrice;
	}
	
	/**
	 * Overloader with default input name
	 * @param inItem
	 * @param readOnly
	 * @return
	 */
	public static String buildDropDownItem(String inItem, 
			   String readOnly) {
		return buildDropDownItem("itemNumber", inItem, readOnly);
	}
	
	/**
	 * Will Return to the Screen, a Drop Down Single Vector
	 *    Which will allow a Drop Down on the Screen for
	 *    Will Display a Valid listing for Items
	 *    
	 *    1/21/13 -- TWalton 
	 *    -- 5/15/13 -- Added Filter of Responsible and Status to list TWalton
	 */
	public static String buildDropDownItem(String name, 
										   String inItem, 
										   String readOnly) {
		String dd = new String();
		try
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("PRD");
			crb.setCompanyNumber("100");
			crb.setIdLevel1("200");
			crb.setIdLevel2("DBENNE");
			crb.setIdLevel3("20");
			Vector getRecords = ServiceItem.dropDownItemsByType(crb);
		    String chooseInfo = "None";
			dd = DropDownSingle.buildDropDown(getRecords, name, inItem, chooseInfo, "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	
	public static String buildDropDownQEItem(
			String name, 
			String id, 
			String cssClass,
			String selectedValue, 
			String defaultValue,
			boolean readOnly,
			DescriptionType descriptionType) {
		
		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(ddQEItem);
		
		return select.toString();
		
	}
	
	public static String buildDropDownQEAttributeModel(
			String name, 
			String id, 
			String cssClass,
			String selectedValue, 
			String defaultValue,
			boolean readOnly,
			DescriptionType descriptionType) {
		
		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(ddQEAttributeModel);
		
		return select.toString();
		
	}
	
	public static String buildDropDownQEVariety(
			String name, 
			String id, 
			String cssClass,
			String selectedValue, 
			String defaultValue,
			boolean readOnly,
			DescriptionType descriptionType) {
		
		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);		
		select.setOptions(ddQEVariety);
	
		
		return select.toString();
		
	}
	
	public static String buildDropDownQERunType(
			String name, 
			String id, 
			String cssClass,
			String selectedValue, 
			String defaultValue,
			boolean readOnly,
			DescriptionType descriptionType) {
		
		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(ddQERunType);
	
		
		return select.toString();
		
	}
	
	public String getWriteUpNumber() {
		return writeUpNumber;
	}
	public void setWriteUpNumber(String writeUpNumber) {
		this.writeUpNumber = writeUpNumber;
	}
	public String getWriteUpNumberError() {
		return writeUpNumberError;
	}
	public void setWriteUpNumberError(String writeUpNumberError) {
		this.writeUpNumberError = writeUpNumberError;
	}
}
