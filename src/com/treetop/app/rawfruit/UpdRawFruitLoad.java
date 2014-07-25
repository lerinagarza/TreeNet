/*
 * Created on November 5, 2008
 */

package com.treetop.app.rawfruit;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanRawFruit;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.RawFruitBin;
import com.treetop.businessobjects.RawFruitLoad;
import com.treetop.businessobjects.RawFruitPO;
import com.treetop.businessobjects.RawFruitLot;
import com.treetop.businessobjects.Lot;
import com.treetop.businessobjects.Supplier;
import com.treetop.data.UserFile;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.services.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitLoad extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	// Fields Available for Update
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String receivingDate = "";
	public String receivingDateError = "";
	public String getReceivingDateError() {
		return receivingDateError;
	}


	public void setReceivingDateError(String receivingDateError) {
		this.receivingDateError = receivingDateError;
	}

	public String receivingTime = "";
	public String receivingTimeError = "";
	public String hourreceivingTime = "";
	public String minreceivingTime = "";
	public String loadType = "";
	public String grossWeight = "";
	public String grossWeightError = "";
	public String tareWeight = "";
	public String tareWeightError = "";
	public String loadFullBins = "";
	public String loadFullBinsError = "";
	public String loadFullBinsDetail = "";
	public String loadFullBinsTieError = ""; // Does not tie to Lot Detail
	public String loadEmptyBins = "";
	public String loadEmptyBinsError = "";
	public String loadBinsTieError = ""; // Does not tie to the Bin Type Detail
	public String bulkLoad = "";
	public String carrierSupplier = "";
	public String carrierSupplierError = "";
	public String carrierBillOfLading = "";
	public String flatRateFlag = "";
	public String loadFreightRate = "";
	public String loadFreightRateError = "";
	public String loadFreightRateCode = "";// M3 Charge Code
	public String loadSurcharge = "";
	public String loadSurchargeError = "";
	public String loadSurchargeCode = ""; // M3 Charge Code
	public String minimumWeightFlag = "";
	public String minimumWeightValue = "";
	public String fromLocation = "";
	public String whseTicket = "";
	public String warehouse = "";
	public String facility = "";
	public String correctionCount = "";
	public String inspectedBy = ""; // User Profile
	
	public String scheduledLoadNumber 		= "";
	public String scheduledLoadNumberError  = "";
	public String handlingCodeLong 			= "";
	public String handlingCode 				= ""; // Not Updateable, found using handlingCodeLong
	public String costCenter 				= ""; // Not Updateable, found using handlingCodeLong
	
	// Fields to Use for Delete Option ONLY
	public String scaleSequence = "";
	public String poNumber = "";
	public String lotSequenceNumber = "";
	public String lotNumber = "";
	
	// Lists
	public Vector listBins 	= new Vector();
	public String countBins = "";
	public Vector listPOs	= new Vector();
	public String countPO   = "";
	
	public Vector listQuickEntries = new Vector();
	public String countQuickEntries = "";
	
	// Drop Down Lists
	public Vector ddWarehouse 			= new Vector();
	public Vector ddChargeCodes 		= new Vector();
	public Vector ddBinType 			= new Vector();
	public Vector ddHandlingCodeLong 	= new Vector();
	public Vector ddInspectedBy			= new Vector();
	public Vector ddFromLocation		= new Vector();
	
	//Button Values
	public String addPO = "";
	public String saveButton = "";
	public String quickEntry = "";
	
	public BeanRawFruit updBean = new BeanRawFruit();
		
	public Vector listReport = null;

	public void validateQuickEntry() {
		
		StringBuffer error = new StringBuffer();
		if (!this.getSaveButton().equals("")) {
			
			if (this.getReceivingDate().equals("")) {
				error.append("Receiving Date is required.  ");
				this.setReceivingDateError("Required");
			}
			
		}
		
		if (!error.toString().equals("")) {
			this.setDisplayMessage(this.getDisplayMessage() + "<br>" + error.toString());
		}
		
		
	}
	
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
			//-------------------------------------------------------------------
			// Gross Weight - Check to make sure Integer
			if (this.grossWeight.trim().equals(""))
				this.setGrossWeight("0");
			if (!this.grossWeight.trim().equals("0"))
				this.setGrossWeightError(validateInteger(this.grossWeight));
			//-------------------------------------------------------------------
			// Truck Tare Weight - Check to make sure Integer
			if (this.tareWeight.trim().equals(""))
				this.setTareWeight("0");
			if (!this.tareWeight.trim().equals("0"))
				this.setTareWeightError(validateInteger(this.tareWeight));
			//-------------------------------------------------------------------
			// Full Bins - Check to make sure Integer
			if (this.loadFullBins.trim().equals(""))
				this.setLoadFullBins("0");
			if (!this.loadFullBins.trim().equals("0"))
				this.setLoadFullBinsError(validateInteger(this.loadFullBins));
			//-------------------------------------------------------------------
			// Empty Bins - Check to make sure Integer
			if (this.loadEmptyBins.trim().equals(""))
				this.setLoadEmptyBins("0");
			if (!this.loadEmptyBins.trim().equals("0"))
				this.setLoadEmptyBinsError(validateInteger(this.loadEmptyBins));
			//-------------------------------------------------------------------
			// Freight Rate - Check to make sure BigDecimal
			if (this.loadFreightRate.trim().equals(""))
				this.setLoadFreightRate("0");
			if (!this.loadFreightRate.trim().equals("0"))
				this.setLoadFreightRateError(validateBigDecimal(this.loadFreightRate));
			//-------------------------------------------------------------------
			// Load Surcharge - Check to make sure BigDecimal
			if (this.loadSurcharge.trim().equals(""))
				this.setLoadSurcharge("0");
			if (!this.loadSurcharge.trim().equals("0"))
				this.setLoadSurchargeError(validateBigDecimal(this.loadSurcharge));
			//-------------------------------------------------------------------
			// Carrier Supplier -- Validate that it is a VALID Supplier
			if (!this.getCarrierSupplier().trim().equals(""))
			{
				try
				{
					this.setCarrierSupplierError(ServiceSupplier.verifySupplier("PRD", this.getCarrierSupplier()));
				}
				catch(Exception e)
				{
					this.setCarrierSupplierError(this.getCarrierSupplier() + " is not Valid.  ");
				}
			}
			//-------------------------------------------------------------------
			// Freight Rate - Code Value must be a Number
			if (this.loadFreightRateCode.trim().equals(""))
				this.setLoadFreightRateCode("0");
//			 Freight Rate - Code Value must be a Number
			if (this.loadSurchargeCode.trim().equals(""))
				this.setLoadSurchargeCode(("0"));
			//-------------------------------------------------------------------
			// Handling Code Long
			if (!this.getHandlingCodeLong().trim().equals(""))
			{
				try
				{
					Vector sendIn = new Vector();
					sendIn.addElement(this.getHandlingCodeLong());
					RawFruitLoad rfl = ServiceRawFruit.findHandlingCode(sendIn);
					this.setHandlingCode(rfl.getDim5());
					this.setCostCenter(rfl.getCostCenter());
				}
				catch(Exception e)
				{
					
				}
			}
			//-------------------------------------------------------------------
			// Scheduled Load Number -- Validate that it is a VALID load Number
			//  Must be Pending Or Received
			if (!this.getScheduledLoadNumber().trim().equals("") &&
				!this.getScheduledLoadNumber().trim().equals("0"))
			{
				try
				{
					CommonRequestBean crb = new CommonRequestBean();
					crb.setEnvironment("PRD");
					crb.setIdLevel1(this.getScheduledLoadNumber());
					this.setScheduledLoadNumberError(ServiceAvailableFruit.verifyScheduleLoadNo(crb));
				}
				catch(Exception e)
				{
					this.setScheduledLoadNumberError(this.getScheduledLoadNumber() + " is not Valid.  ");
				}
			}
			//***************************************************************** 
			// Receiving Time ****************************************
			StringBuffer timeError = new StringBuffer();
			String setDefault = "N";
			  //------------------------------------- 
			  //  Review/Validate the Hour section of the time
			  if (this.getHourreceivingTime() == null ||
				  this.getHourreceivingTime().trim().equals(""))
			  {
				 setDefault = "Y";
			     this.setHourreceivingTime("0");
			  }
			  else{
			     try{
				    timeError.append(validateInteger(this.getHourreceivingTime()));
				    if (timeError.toString().trim().equals(""))
				    {
				   	   int hourTime = new Integer(this.getHourreceivingTime()).intValue();
				       if (hourTime > 24)
				    	  timeError.append("Hour Value cannot be greater than 24. ");
				    }
			     }catch(Exception e){}
			     if (timeError.toString().trim().equals("") &&
				     this.getHourreceivingTime().length() == 1)
				    this.setHourreceivingTime("0" + this.getHourreceivingTime().trim());
			  } // end of the else -- for the Hour on the Receiving Time
			  // --------------------------------------------------------- 
			  //  Review/Validate the Minute section of the time
		      StringBuffer minError = new StringBuffer();
			  if (this.getMinreceivingTime() == null ||
				  this.getMinreceivingTime().trim().equals(""))
			     this.setMinreceivingTime("0");
			  else
		      {
		       try{
		    	  minError.append(validateInteger(this.getMinreceivingTime()));
		    	  if (minError.toString().trim().equals(""))
		    	  {
		    	     int minTime = new Integer(this.getMinreceivingTime()).intValue();
		    	 	 if (minTime > 59)
		    		 	minError.append("Hour Value cannot be greater than 59. ");
		    	  }
		       }catch(Exception e){}
			      if (minError.toString().trim().equals("") &&
			    	  this.getMinreceivingTime().length() == 1)
			         this.setMinreceivingTime("0" + this.getMinreceivingTime().trim());
		      } 
			timeError.append(minError.toString().trim());
			if (timeError.toString().trim().equals("") &&
				setDefault.trim().equals("Y"))
			{
				  DateTime dateNow = UtilityDateTime.getSystemDate();
				  this.setHourreceivingTime(dateNow.getTimeFormathhmm().substring(0, 2));
				  this.setMinreceivingTime(dateNow.getTimeFormathhmm().substring(2,4));
				  this.setReceivingTime(dateNow.getTimeFormathhmmss());
			}
			else
				this.setReceivingTime(this.getHourreceivingTime() + this.getMinreceivingTime() + "00");
			if (!timeError.toString().trim().equals(""))
				this.setReceivingTimeError(timeError.toString());
//			// Scheduled Pick Up Time **************************************************
//		    if (timeError.toString().trim().equals(""))
//		       this.setScheduledPickUpTime(this.getScheduledPickUpTimeHour() + this.getScheduledPickUpTimeMinute());
//		    else{
//		       this.setScheduledPickUpTimeError(timeError.toString() + "<br>");
//		       foundProblem.append(this.getScheduledPickUpTimeError());
//		    }
			
			//-------------------------------------------------------------------
			// Set up the Display Message Piece -- FOR the ENTIRE PAGE
			StringBuffer sb = new StringBuffer();
			if (!this.getGrossWeightError().trim().equals(""))
			   sb.append(this.getGrossWeightError() + "<br>");
			if (!this.getTareWeightError().trim().equals(""))
			   sb.append(this.getTareWeightError() + "<br>");
			if (!this.getLoadFullBinsError().trim().equals(""))
			   sb.append(this.getLoadFullBinsError() + "<br>");
			if (!this.getLoadEmptyBinsError().trim().equals(""))
			   sb.append(this.getLoadEmptyBinsError() + "<br>");
			if (!this.getLoadFreightRateError().trim().equals(""))
			   sb.append(this.getLoadFreightRateError() + "<br>");
			if (!this.getLoadSurchargeError().trim().equals(""))
			   sb.append(this.getLoadSurchargeError() + "<br>");
			if (!this.getCarrierSupplierError().trim().equals(""))
			   sb.append(this.getCarrierSupplierError() + "<br>");
			if (!this.getScheduledLoadNumberError().trim().equals(""))
			   sb.append(this.getScheduledLoadNumberError() + "<br>");
			if (!this.getReceivingTimeError().trim().equals(""))
			   sb.append(this.getReceivingTimeError() + "<br>");
				
			if (!sb.toString().trim().equals(""))
			   this.setDisplayMessage(sb.toString());
		
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
	 * @return Returns the addPO.
	 */
	public String getAddPO() {
		return addPO;
	}
	/**
	 * @param addPO The addPO to set.
	 */
	public void setAddPO(String addPO) {
		this.addPO = addPO;
	}
	/**
	 * @return Returns the grossWeight.
	 */
	public String getGrossWeight() {
		return grossWeight;
	}
	/**
	 * @param grossWeight The grossWeight to set.
	 */
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * @return Returns the grossWeightError.
	 */
	public String getGrossWeightError() {
		return grossWeightError;
	}
	/**
	 * @param grossWeightError The grossWeightError to set.
	 */
	public void setGrossWeightError(String grossWeightError) {
		this.grossWeightError = grossWeightError;
	}
	/**
	 * @return Returns the tareWeight.
	 */
	public String getTareWeight() {
		return tareWeight;
	}
	/**
	 * @param tareWeight The tareWeight to set.
	 */
	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}
	/**
	 * @return Returns the tareWeightError.
	 */
	public String getTareWeightError() {
		return tareWeightError;
	}
	/**
	 * @param tareWeightError The tareWeightError to set.
	 */
	public void setTareWeightError(String tareWeightError) {
		this.tareWeightError = tareWeightError;
	}
	/**
	 * @return Returns the bulkLoad.
	 */
	public String getBulkLoad() {
		return bulkLoad;
	}
	/**
	 * @param bulkLoad The bulkLoad to set.
	 */
	public void setBulkLoad(String bulkLoad) {
		this.bulkLoad = bulkLoad;
	}
	/**
	 * @return Returns the carrierBillOfLading.
	 */
	public String getCarrierBillOfLading() {
		return carrierBillOfLading;
	}
	/**
	 * @param carrierBillOfLading The carrierBillOfLading to set.
	 */
	public void setCarrierBillOfLading(String carrierBillOfLading) {
		this.carrierBillOfLading = carrierBillOfLading;
	}
	/**
	 * @return Returns the carrierSupplier.
	 */
	public String getCarrierSupplier() {
		return carrierSupplier;
	}
	/**
	 * @param carrierSupplier The carrierSupplier to set.
	 */
	public void setCarrierSupplier(String carrierSupplier) {
		this.carrierSupplier = carrierSupplier;
	}
	/**
	 * @return Returns the loadFreightRate.
	 */
	public String getLoadFreightRate() {
		return loadFreightRate;
	}
	/**
	 * @param loadFreightRate The loadFreightRate to set.
	 */
	public void setLoadFreightRate(String loadFreightRate) {
		this.loadFreightRate = loadFreightRate;
	}
	/**
	 * @return Returns the loadSurcharge.
	 */
	public String getLoadSurcharge() {
		return loadSurcharge;
	}
	/**
	 * @param loadSurcharge The loadSurcharge to set.
	 */
	public void setLoadSurcharge(String loadSurcharge) {
		this.loadSurcharge = loadSurcharge;
	}
	/**
	 * @return Returns the carrierSupplierError.
	 */
	public String getCarrierSupplierError() {
		return carrierSupplierError;
	}
	/**
	 * @param carrierSupplierError The carrierSupplierError to set.
	 */
	public void setCarrierSupplierError(String carrierSupplierError) {
		this.carrierSupplierError = carrierSupplierError;
	}
	/**
	 * @return Returns the loadFreightRateError.
	 */
	public String getLoadFreightRateError() {
		return loadFreightRateError;
	}
	/**
	 * @param loadFreightRateError The loadFreightRateError to set.
	 */
	public void setLoadFreightRateError(String loadFreightRateError) {
		this.loadFreightRateError = loadFreightRateError;
	}
	/**
	 * @return Returns the loadSurchargeError.
	 */
	public String getLoadSurchargeError() {
		return loadSurchargeError;
	}
	/**
	 * @param loadSurchargeError The loadSurchargeError to set.
	 */
	public void setLoadSurchargeError(String loadSurchargeError) {
		this.loadSurchargeError = loadSurchargeError;
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
	 * @return Returns the loadEmptyBins.
	 */
	public String getLoadEmptyBins() {
		return loadEmptyBins;
	}
	/**
	 * @param loadEmptyBins The loadEmptyBins to set.
	 */
	public void setLoadEmptyBins(String loadEmptyBins) {
		this.loadEmptyBins = loadEmptyBins;
	}
	/**
	 * @return Returns the loadEmptyBinsError.
	 */
	public String getLoadEmptyBinsError() {
		return loadEmptyBinsError;
	}
	/**
	 * @param loadEmptyBinsError The loadEmptyBinsError to set.
	 */
	public void setLoadEmptyBinsError(String loadEmptyBinsError) {
		this.loadEmptyBinsError = loadEmptyBinsError;
	}
	/**
	 * @return Returns the loadFullBins.
	 */
	public String getLoadFullBins() {
		return loadFullBins;
	}
	/**
	 * @param loadFullBins The loadFullBins to set.
	 */
	public void setLoadFullBins(String loadFullBins) {
		this.loadFullBins = loadFullBins;
	}
	/**
	 * @return Returns the loadFullBinsError.
	 */
	public String getLoadFullBinsError() {
		return loadFullBinsError;
	}
	/**
	 * @param loadFullBinsError The loadFullBinsError to set.
	 */
	public void setLoadFullBinsError(String loadFullBinsError) {
		this.loadFullBinsError = loadFullBinsError;
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
	/**
	 * @return Returns the loadType.
	 */
	public String getLoadType() {
		return loadType;
	}
	/**
	 * @param loadType The loadType to set.
	 */
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	/**
	 * @return Returns the listBins.
	 */
	public Vector getListBins() {
		return listBins;
	}
	/**
	 * @param listBins The listBins to set.
	 */
	public void setListBins(Vector listBins) {
		this.listBins = listBins;
	}
	/**
	 * @return Returns the listPOs.
	 */
	public Vector getListPOs() {
		return listPOs;
	}
	/**
	 * @param listPOs The listPOs to set.
	 */
	public void setListPOs(Vector listPOs) {
		this.listPOs = listPOs;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (11/17/2008 TWalton)
	 */
	public void loadFromBeanRawFruit(BeanRawFruit brf) {
		try {
			RawFruitLoad rfl = brf.getRfLoad();
			this.scaleTicket = rfl.getScaleTicketNumber();
			this.scaleTicketCorrectionSequence = rfl.getScaleTicketCorrectionSequenceNumber();
			// TEST Information
			int numberOfFullBins = 0;
			int numberOfTotalBins = 0;
			int numberOfBins25BX = 0;
			int numberOfBins30BX = 0;
			int numberOfBinsByType = 0;
			// Reset the Date to work on the Screen
			try
			{
				// Date Time Issue with Receiving Date
				DateTime recDate = UtilityDateTime.getDateFromyyyyMMdd(rfl.getReceivingDate());
				this.receivingDate = recDate.getDateFormatMMddyyyySlash();
				
				// date Time Issue with Receiving Time
				DateTime recTime = UtilityDateTime.getTimeFromhhmmss(rfl.getReceivingTime());
				this.setHourreceivingTime(recTime.getTimeFormathhmm().substring(0,2));
				this.setMinreceivingTime(recTime.getTimeFormathhmm().substring(2,4));
				this.setReceivingTime(rfl.getReceivingTime());
			}
			catch(Exception e)
			{}
			this.loadType = rfl.getLoadType();
			this.grossWeight = rfl.getLoadGrossWeight();
			this.tareWeight = rfl.getLoadTareWeight();
			this.loadFullBins = rfl.getLoadFullBins();
			this.loadEmptyBins = rfl.getLoadEmptyBins();
			this.loadFreightRate = rfl.getFreightRate();
			this.loadFreightRateCode = rfl.getFreightRateCode();
			this.flatRateFlag = rfl.getFlatRateFreightFlag();
			this.loadSurcharge = rfl.getFreightSurcharge();
			this.loadSurchargeCode = rfl.getFreightSurchargeCode();
			this.fromLocation = rfl.getFromLocation();
			this.whseTicket = rfl.getWhseTicket();
			this.bulkLoad = rfl.getFlagBulkBin();
			this.inspectedBy = rfl.getInspectedBy();
			this.scheduledLoadNumber = rfl.getScheduledLoadNumber();
			this.handlingCodeLong = rfl.getHandlingCode();
			this.handlingCode = rfl.getDim5();
			this.costCenter = rfl.getCostCenter();
			try
			{
				numberOfFullBins = new Integer(rfl.getLoadFullBins()).intValue();
				int numberOfEmptyBins = new Integer(rfl.getLoadEmptyBins()).intValue();
				numberOfTotalBins = numberOfFullBins + numberOfEmptyBins;
			}
			catch(Exception e)
			{}
			try
			{
			   this.carrierSupplier = rfl.getCarrier().getSupplierNumber();
			}
			catch(Exception e)
			{}
			try
			{
			   this.warehouse = rfl.getWarehouseFacility().getWarehouse();
			   this.facility  = rfl.getWarehouseFacility().getFacility();
			}
			catch(Exception e)
			{}
			this.correctionCount = rfl.getCorrectionCount();
			this.carrierBillOfLading = rfl.getCarrierBOL();
			this.minimumWeightFlag = rfl.getMinimumWeightFlag();
			this.minimumWeightValue = rfl.getMinimumWeightValue();
			
			// Build the Bins Vector from the Bean Information
			Vector listBinsFromFile = new Vector();
			int binCount = rfl.getListBins().size();
			if (binCount > 0)
			{
				for (int bin = 0; bin < binCount; bin++)
				{
					UpdRawFruitBins urfbins = new UpdRawFruitBins();
					try
					{
						RawFruitBin rfb =  (RawFruitBin) rfl.getListBins().elementAt(bin);
						urfbins.setBinInfo(rfb);
						urfbins.setBinType(rfb.getBinType());	
						urfbins.setNumberOfBins(rfb.getBinQuantity());
						numberOfBinsByType = numberOfBinsByType + new Integer(rfb.getBinQuantity()).intValue();
					}
					catch(Exception e)
					{}
					listBinsFromFile.addElement(urfbins);
				}
			}
			this.listBins = listBinsFromFile;
			// Build the Bins Vector from the Bean Information
			Vector listPOFromFile = new Vector();
			int poCount = rfl.getListPOs().size();
			if (poCount > 0)
			{
				for (int po = 0; po < poCount; po++)
				{
					UpdRawFruitPO urfpo = new UpdRawFruitPO();
					try
					{
						RawFruitPO rfpo =  (RawFruitPO) rfl.getListPOs().elementAt(po);
						urfpo.setPoInfo(rfpo);
						urfpo.setPoNumber(rfpo.getPoNumber());
						urfpo.setSequenceNumber(rfpo.getScaleSequence());	
						urfpo.setPoWarehouse(rfpo.getWarehouseFacility().getWarehouse());
						urfpo.setSupplier(rfpo.getPoSupplier().getSupplierNumber());
						if (rfpo.getListLots().size() > 0 && this.getBulkLoad().trim().equals(""))
						{
							for (int lt = 0; lt < rfpo.getListLots().size(); lt++)
							{
								RawFruitLot rflt = (RawFruitLot) rfpo.getListLots().elementAt(lt);
								try
								{
								   numberOfBins25BX = numberOfBins25BX + new Integer(rflt.getLotTotalBins25Box()).intValue();
								   numberOfBins30BX = numberOfBins30BX + new Integer(rflt.getLotTotalBins30Box()).intValue();
								}
								catch(Exception e)
								{	}
							}
						}
					}
					catch(Exception e)
					{}
					listPOFromFile.addElement(urfpo);
				}
			}
			this.listPOs = listPOFromFile;
			
			if(this.getBulkLoad().trim().equals(""))
			{
			   if (numberOfTotalBins != numberOfBinsByType)
			      this.loadBinsTieError = "The Total Number of Bins Entered by Type, does NOT equal the Total Number Of Bins By Load!  Please review!!! ";
			   this.loadFullBinsDetail = "&nbsp;25Bx: <b>" + numberOfBins25BX + "</b>&nbsp;bins --&nbsp;30Bx: <b>" + numberOfBins30BX + "</b>&nbsp;bins";
			   if (numberOfTotalBins != (numberOfBins25BX + numberOfBins30BX))
				  this.loadFullBinsTieError = "The Number of Full Bins Entered by Lot, does NOT equal the Number Of Full Bins By Load!  Please review!!! ";
			}  
			
		} catch (Exception e) {
	
		}
		return;
	}
	/*
	 * Test and Validate fields for Bins, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void populateBins(HttpServletRequest request) {
		try
		{	
//			Vector listB = new Vector();
			StringBuffer foundProblem = new StringBuffer();
			// Retrieve Bin information relating to the Scale Ticket
			int countRecords = 0;
			countRecords = new Integer(this.getCountBins()).intValue();
			if (countRecords > 0)
			{
			   for (int x = 0; x < countRecords; x++)	
			   {
			   	   UpdRawFruitBins urfb = new UpdRawFruitBins();
			   	   urfb.setBinType(request.getParameter(x + "binType"));
			   	   if (urfb.getBinType() == null)
			   	   	  urfb.setBinType("");
			   	   urfb.setNumberOfBins(request.getParameter(x + "numberOfBins"));
			   	   if (urfb.getNumberOfBins() == null)
			   	   	  urfb.setNumberOfBins("");
			   	   if (!urfb.getBinType().trim().equals("") ||
			   	   	   !urfb.getNumberOfBins().trim().equals(""))
			   	   {
			   	   	  urfb.setUpdateUser(this.updateUser);
			   	   	  urfb.setScaleTicket(this.scaleTicket);
			   	   	  urfb.setScaleTicketCorrectionSequence(this.scaleTicketCorrectionSequence);
			   	   	  //------------------------------------------------------
			   	      //--Validate:  Bin Type
			   	      try
				      {
			   	      	if (urfb.getBinType().trim().equals(""))
			   	      	{
			   	      		urfb.setBinTypeError("Bin Type Must Be Entered");
			   	      		foundProblem.append("Bin Type Must Be Entered<br>");
			   	      	}
			   	      	else
			   	      	{
			   	      		urfb.setBinTypeError(ServiceRawFruit.verifyBinType(urfb.getBinType()));
			   	      		if (!urfb.getBinTypeError().trim().equals(""))
			   	      			foundProblem.append(urfb.getBinTypeError());
			   	      	}
				      }
			   	      catch(Exception e)
				      {
			   	      	urfb.setBinTypeError("Problem Found with Bin Type. " + e);
		   	      		foundProblem.append("Problem Found with Bin Type<br>");
				      }
			   	   	  //------------------------------------------------------
			   	      //--Validate:  Bin Count
			   	      try
				      {
			   	      	if (urfb.getNumberOfBins().trim().equals("") ||
			   	      		urfb.getNumberOfBins().trim().equals("0"))
			   	      	{
			   	      		urfb.setNumberOfBinsError("Number of Bins Must Be Entered");
			   	      		foundProblem.append("Number of Bins Must Be Entered<br>");
			   	      	}
			   	      	else
			   	      	{
			   	      		urfb.setNumberOfBinsError(validateInteger(urfb.getNumberOfBins()));
			   	      		if (!urfb.getNumberOfBinsError().trim().equals(""))
			   	      			foundProblem.append(urfb.getNumberOfBinsError());
			   	      	}
				      }
			   	      catch(Exception e)
				      {
			   	      	urfb.setNumberOfBinsError("Problem Found with Number of Bins. " + e);
		   	      		foundProblem.append("Problem Found with Number of Bins<br>");
				      }
			   	      listBins.addElement(urfb);
			   	   }
			   }
			   this.setListBins(listBins);
			   this.setDisplayMessage(this.displayMessage + foundProblem.toString().trim());
			}
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 * @return Returns the countBins.
	 */
	public String getCountBins() {
		return countBins;
	}
	/**
	 * @param countBins The countBins to set.
	 */
	public void setCountBins(String countBins) {
		this.countBins = countBins;
	}
	/**
	 * @return Returns the countPO.
	 */
	public String getCountPO() {
		return countPO;
	}
	/**
	 * @param countPO The countPO to set.
	 */
	public void setCountPO(String countPO) {
		this.countPO = countPO;
	}
	/**
		 *  This method should be in EVERY Inquiry View Bean
		 *   Will create the vectors and generate the code for
		 *    MORE Button.
		 * @return
		 */
		public static String buildReportButton(String scaleTicket,
											   String scaleCorrectionSequence,
											   String lotNumber) {
			// BUILD Edit/More Button Section(Column)  
			String[] urlLinks = new String[3];
			String[] urlNames = new String[3];
			String[] newPage = new String[3];
			for (int z = 0; z < 3; z++) {
				urlLinks[z] = "";
				urlNames[z] = "";
				newPage[z] = "";
			}
			urlLinks[0] = "/web/CtlRawFruit?requestType=reportPO"
					+ "&scaleTicket=" + scaleTicket
					+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
				urlNames[0] = "PO/Lot Entry Report ";
				newPage[0] = "Y";
			urlLinks[1] = "/web/CtlRawFruit?requestType=reportFreight"
					+ "&scaleTicket=" + scaleTicket
					+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence;
				urlNames[1] = "Freight Calculation Report ";
				newPage[1] = "Y";
			urlLinks[2] = "/web/CtlRawFruit?requestType=reportInvoice"
					+ "&scaleTicket=" + scaleTicket
					+ "&scaleTicketCorrectionSequence=" + scaleCorrectionSequence
					+ "&lotNumber=" + lotNumber;
				urlNames[2] = "Payment/Invoice Report ";
				newPage[2] = "Y";	
				
			return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
		}
	/**
	 * @return Returns the fromLocation.
	 */
	public String getFromLocation() {
		return fromLocation;
	}
	/**
	 * @param fromLocation The fromLocation to set.
	 */
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	/**
	 * @return Returns the whseTicket.
	 */
	public String getWhseTicket() {
		return whseTicket;
	}
	/**
	 * @param whseTicket The whseTicket to set.
	 */
	public void setWhseTicket(String whseTicket) {
		this.whseTicket = whseTicket;
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(String requestType,
										 String scaleTicket,
										 String scaleTicketCorrectionSequence, 
										 String receivingDate,
										 String scaleSequence,
										 String poNumber,
										 String lotSequence,
										 String lotNumber) {
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[3];
		String[] urlNames = new String[3];
		String[] newPage = new String[3];
		for (int z = 0; z < 3; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		if (requestType.equals("updateLot")) {
			urlLinks[0] = "/web/CtlRawFruit?requestType=updateLot"
					+ "&scaleTicket=" + scaleTicket
					+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
					+ "&receivingDate=" + receivingDate
					+ "&scaleSequence=" + scaleSequence
					+ "&poNumber=" + poNumber
					+ "&lotSequenceNumber=" + lotSequence
					+ "&lotNumber=" + lotNumber;
			urlNames[0] = "Update Lot Number " + lotNumber;
			newPage[0] = "N";
			urlLinks[1] = "/web/CtlRawFruit?requestType=reportInvoice"
				+ "&scaleTicket=" + scaleTicket
				+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
				+ "&lotNumber=" + lotNumber;
			urlNames[1] = "Payment/Invoice Report ";
			newPage[1] = "Y";	
			urlLinks[2] = "/web/CtlRawFruit?requestType=deleteLot"
				+ "&scaleTicket=" + scaleTicket
				+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
				+ "&receivingDate=" + receivingDate
				+ "&scaleSequence=" + scaleSequence
				+ "&poNumber=" + poNumber
				+ "&lotSequenceNumber=" + lotSequence
				+ "&lotNumber=" + lotNumber;
		urlNames[2] = "Delete Lot Number " + lotNumber;
		newPage[2] = "N";
		}
		if (requestType.equals("updatePO")) {
			urlLinks[0] = "/web/CtlRawFruit?requestType=deletePO"
					+ "&scaleTicket=" + scaleTicket
					+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
					+ "&receivingDate=" + receivingDate
					+ "&scaleSequence=" + scaleSequence
					+ "&poNumber=" + poNumber;
			urlNames[0] = "Delete Sequence Number " + scaleSequence;
			newPage[0] = "N";
		}
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
	}
	/**
	 * @return Returns the loadFreightRateCode.
	 */
	public String getLoadFreightRateCode() {
		return loadFreightRateCode;
	}
	/**
	 * @param loadFreightRateCode The loadFreightRateCode to set.
	 */
	public void setLoadFreightRateCode(String loadFreightRateCode) {
		this.loadFreightRateCode = loadFreightRateCode;
	}
	/**
	 * @return Returns the loadSurchargeCode.
	 */
	public String getLoadSurchargeCode() {
		return loadSurchargeCode;
	}
	/**
	 * @param loadSurchargeCode The loadSurchargeCode to set.
	 */
	public void setLoadSurchargeCode(String loadSurchargeCode) {
		this.loadSurchargeCode = loadSurchargeCode;
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
	 *    Will Generate the Vectors for ALL Drop Down Lists Needed multiple times
	 */
	public void buildDropDownVectors() {
		try
		{
		   Vector sentValues = new Vector();
		   ddChargeCodes = ServiceFinance.dropDownRFChargeCode(sentValues);
		   ddBinType = ServiceRawFruit.dropDownBinType(sentValues);
		   ddHandlingCodeLong = ServiceRawFruit.dropDownHandlingCode(sentValues);
		   sentValues.addElement("ddRFWarehouse");
		   ddWarehouse = ServiceWarehouse.dropDownWarehouse("PRD", sentValues);
		   UserFile thisUser = new UserFile();
		   ddInspectedBy = new Vector();
			Vector userList = thisUser.findUsersByGroup("112");
			for (int x = 0; x < userList.size(); x++)
			{
			    thisUser = (UserFile) userList.elementAt(x);
				DropDownSingle addOne = new DropDownSingle();
				addOne.setDescription(thisUser.getUserNameLong());
				addOne.setValue(thisUser.getUserName());
				ddInspectedBy.add(addOne);
			}
		   CommonRequestBean crb = new CommonRequestBean();
		   crb.setEnvironment("PRD");
		   crb.setIdLevel3("Long Description");
		   this.setDdFromLocation(ServiceRawFruit.dropDownLocation(crb));
		  
		   
		} catch(Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownWarehouse(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddWarehouse, fieldName, inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	public String buildDropDownQEWarehouse(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
			
		   dd = DropDownSingle.buildDropDown(ddWarehouse, fieldName, inValue, " ", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownInspectedBy(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddInspectedBy, fieldName, inValue, "None", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/*
	 * Test and Validate fields for POs, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void populatePOs(HttpServletRequest request) {
		try
		{	
			Vector listP = new Vector();
			StringBuffer foundProblem = new StringBuffer();
			// Retrieve Bin information relating to the Scale Ticket
			int countRecords = 0;
			countRecords = new Integer(this.getCountPO()).intValue();
			if (countRecords > 0)
			{
			   for (int x = 1; x <= countRecords; x++)	
			   {
			   	   UpdRawFruitPO urfpo = new UpdRawFruitPO();
			   	   urfpo.setSequenceNumber(request.getParameter(x + "sequenceNumber"));
			   	   if (urfpo.getSequenceNumber() == null)
			   	   	  urfpo.setSequenceNumber("0");
			   	   urfpo.setPoNumber(request.getParameter(x + "poNumber"));
			   	   if (urfpo.getPoNumber() == null || urfpo.getPoNumber().trim().equals(""))
			   	   	  urfpo.setPoNumber("0");
			       urfpo.setPoWarehouse(request.getParameter(x + "poWarehouse"));
			   	   if (urfpo.getPoWarehouse() == null)
			   	   	  urfpo.setPoWarehouse("");
			       urfpo.setSupplier(request.getParameter(x + "supplier"));
			   	   if (urfpo.getSupplier() == null)
			   	   	  urfpo.setSupplier("");			   	   
			   	   if (!urfpo.getPoNumber().equals("0") ||
			   	   	   !urfpo.getPoWarehouse().trim().equals("") ||
			   	   	   !urfpo.getSupplier().trim().equals(""))
			   	   {
			   	   	  urfpo.setUpdateUser(this.updateUser);
			   	   	  urfpo.setScaleTicket(this.scaleTicket);
			   	   	  urfpo.setScaleTicketCorrectionSequence(this.scaleTicketCorrectionSequence);
//			   	 ----------------------------------------------------------------
				   	  //--Validate: Supplier
				   	  try
					  {
				   	   	 if (!urfpo.getSupplier().trim().equals(""))
				   	   	 {	
				   	   	    urfpo.setSupplierError(ServiceSupplier.verifySupplier("PRD", urfpo.getSupplier()));
				   	      	if (!urfpo.getSupplierError().trim().equals(""))
				   	      	  foundProblem.append(urfpo.getSupplierError() + "<br>");
				   	     }
					   }
				   	   catch(Exception e)
					   {
				   	      urfpo.setSupplierError("Problem Found with Supplier. " + e);
			   	      	  foundProblem.append("Problem Found with Supplier<br>");
					   }
			   	       listP.addElement(urfpo);
			   	   }
			   }
			   this.setListPOs(listP);
			   this.setDisplayMessage(this.displayMessage + foundProblem.toString().trim());
			}
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	
	
	public void populateQuickEntries(HttpServletRequest request) {
		try
		{	
			Vector listQ = new Vector();
			StringBuffer foundProblem = new StringBuffer();
			
			// Retrieve Quick Entry information relating to the Scale Ticket
			int countRecords = new Integer(this.getCountQuickEntries()).intValue();
			if (countRecords > 0) {
				
				int totalBinCount = 0;
				
				for (int x = 0; x < countRecords; x++) {
					
					
					UpdRawFruitQuickEntry urfqe = new UpdRawFruitQuickEntry();

					
					urfqe.setLotNumber(request.getParameter(x + "lotNumber"));
					if (urfqe.getLotNumber() == null) {
						urfqe.setLotNumber("");
					}

					urfqe.setWarehouse(request.getParameter(x + "warehouse"));
					if (urfqe.getWarehouse() == null || urfqe.getWarehouse().equals("")) {
						urfqe.setWarehouse("");
					}
					
					urfqe.setSupplier(request.getParameter(x + "supplier"));
					if (urfqe.getSupplier() == null) {
						urfqe.setSupplier("");
					}
					
					urfqe.setItemNumber(request.getParameter(x + "itemNumber"));
					if (urfqe.getItemNumber() == null) {
						urfqe.setItemNumber("");
					}
					
					urfqe.setVariety(request.getParameter(x + "variety"));
					if (urfqe.getVariety() == null) {
						urfqe.setVariety("");
					}
					
					urfqe.setRunType(request.getParameter(x + "runType"));
					if (urfqe.getRunType() == null) {
						urfqe.setRunType("");
					}
					
					urfqe.setNumberOfBins(request.getParameter(x + "numberOfBins"));
					if (urfqe.getNumberOfBins() == null) {
						urfqe.setNumberOfBins("");
					}
					
					String binCount = urfqe.getNumberOfBins();
					int bins = 0;
					try {
						bins = Integer.parseInt(binCount);
					} catch (Exception e) {}
					totalBinCount += bins;
			
					urfqe.validate();
					
					if (!urfqe.getDisplayMessage().equals("")) {
						foundProblem.append(urfqe.getDisplayMessage());
					}
					
					listQ.addElement(urfqe);

			   }	//end loop
				
				this.setLoadFullBins(String.valueOf(totalBinCount));
				
			   this.setListQuickEntries(listQ);
			   this.setDisplayMessage(this.displayMessage + foundProblem.toString().trim());
			}
			
		} catch(Exception e) {
			
		}

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
	 * @return Returns the ddWarehouse.
	 */
	public Vector getDdWarehouse() {
		return ddWarehouse;
	}
	/**
	 * @param ddWarehouse The ddWarehouse to set.
	 */
	public void setDdWarehouse(Vector ddWarehouse) {
		this.ddWarehouse = ddWarehouse;
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
	public String getFlatRateFlag() {
		return flatRateFlag;
	}
	public void setFlatRateFlag(String flatRateFlag) {
		this.flatRateFlag = flatRateFlag;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownBinType(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddBinType, fieldName, inValue, "Choose One", "B", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	public Vector getDdBinType() {
		return ddBinType;
	}
	public void setDdBinType(Vector ddBinType) {
		this.ddBinType = ddBinType;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public String getMinimumWeightFlag() {
		return minimumWeightFlag;
	}
	public void setMinimumWeightFlag(String minimumWeightFlag) {
		this.minimumWeightFlag = minimumWeightFlag;
	}
	public String getMinimumWeightValue() {
		return minimumWeightValue;
	}
	public void setMinimumWeightValue(String minimumWeightValue) {
		this.minimumWeightValue = minimumWeightValue;
	}
	public String getLoadBinsTieError() {
		return loadBinsTieError;
	}
	public void setLoadBinsTieError(String loadBinsTieError) {
		this.loadBinsTieError = loadBinsTieError;
	}
	public String getLoadFullBinsDetail() {
		return loadFullBinsDetail;
	}
	public void setLoadFullBinsDetail(String loadFullBinsDetail) {
		this.loadFullBinsDetail = loadFullBinsDetail;
	}
	public String getLoadFullBinsTieError() {
		return loadFullBinsTieError;
	}
	public void setLoadFullBinsTieError(String loadFullBinsTieError) {
		this.loadFullBinsTieError = loadFullBinsTieError;
	}
	public String getCorrectionCount() {
		return correctionCount;
	}
	public void setCorrectionCount(String correctionCount) {
		this.correctionCount = correctionCount;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getHandlingCode() {
		return handlingCode;
	}
	public void setHandlingCode(String handlingCode) {
		this.handlingCode = handlingCode;
	}
	public String getHandlingCodeLong() {
		return handlingCodeLong;
	}
	public void setHandlingCodeLong(String handlingCodeLong) {
		this.handlingCodeLong = handlingCodeLong;
	}
	public String getScheduledLoadNumber() {
		return scheduledLoadNumber;
	}
	public void setScheduledLoadNumber(String scheduledLoadNumber) {
		this.scheduledLoadNumber = scheduledLoadNumber;
	}
	public String getScheduledLoadNumberError() {
		return scheduledLoadNumberError;
	}
	public void setScheduledLoadNumberError(String scheduledLoadNumberError) {
		this.scheduledLoadNumberError = scheduledLoadNumberError;
	}
	public Vector getDdHandlingCodeLong() {
		return ddHandlingCodeLong;
	}
	public void setDdHandlingCodeLong(Vector ddHandlingCodeLong) {
		this.ddHandlingCodeLong = ddHandlingCodeLong;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownHandlingCode(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(this.ddHandlingCodeLong, fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	public String getReceivingTime() {
		return receivingTime;
	}
	public void setReceivingTime(String receivingTime) {
		this.receivingTime = receivingTime;
	}
	public String getReceivingTimeError() {
		return receivingTimeError;
	}
	public void setReceivingTimeError(String receivingTimeError) {
		this.receivingTimeError = receivingTimeError;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}
	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}
	public String getHourreceivingTime() {
		return hourreceivingTime;
	}
	public void setHourreceivingTime(String hourreceivingTime) {
		this.hourreceivingTime = hourreceivingTime;
	}
	public String getMinreceivingTime() {
		return minreceivingTime;
	}
	public void setMinreceivingTime(String minreceivingTime) {
		this.minreceivingTime = minreceivingTime;
	}
	public Vector getDdInspectedBy() {
		return ddInspectedBy;
	}
	public void setDdInspectedBy(Vector ddInspectedBy) {
		this.ddInspectedBy = ddInspectedBy;
	}
	public Vector getDdFromLocation() {
		return ddFromLocation;
	}
	public void setDdFromLocation(Vector ddFromLocation) {
		this.ddFromLocation = ddFromLocation;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownFromLocation(String fieldName, String inValue, String readOnly) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(this.getDdFromLocation(), fieldName, inValue, "Choose One", "N", readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	public String getQuickEntry() {
		return quickEntry;
	}
	public void setQuickEntry(String quickEntry) {
		this.quickEntry = quickEntry;
	}
	public Vector getListQuickEntries() {
		return listQuickEntries;
	}
	public void setListQuickEntries(Vector listQuickEntries) {
		this.listQuickEntries = listQuickEntries;
	}
	public String getCountQuickEntries() {
		return countQuickEntries;
	}
	public void setCountQuickEntries(String countQuickEntries) {
		this.countQuickEntries = countQuickEntries;
	}
	
	public void loadQuickEntries() {
		
		BeanRawFruit brf = this.getUpdBean();
		Vector listQe = new Vector();
		
		for (int x = 0; x < brf.getListRFPO().size() && x < 10; x++)
		{
			RawFruitPO  rfpo = (RawFruitPO) brf.getListRFPO().elementAt(x); 
			
			if (rfpo.getPoSupplier().getSupplierNumber() != null &&
				rfpo.getPoSupplier().getSupplierNumber().trim() != "")
			{
			
				for (int y = 0; y < rfpo.getListLots().size(); y++) 
				{
					UpdRawFruitQuickEntry qe = new UpdRawFruitQuickEntry();
					
					RawFruitLot rfl = (RawFruitLot) rfpo.getListLots().elementAt(y);
					
					if (rfl.getLotNumber() != null && !rfl.getLotNumber().trim().equals(""))
					{
						qe.setSupplier(rfpo.getPoSupplier().getSupplierNumber().trim());
						qe.setLotNumber(rfl.getLotNumber());
						qe.setItemNumber(rfl.getLotInformation().getItemNumber());
						
						if (rfl.getLotInformation().getWarehouse() != null && rfl.getLotInformation().getWarehouse().equals(""))
							qe.setWarehouse(rfl.getWarehouseFacility().getWarehouse());
						else
							qe.setWarehouse(rfl.getLotInformation().getWarehouse());
						
						qe.setVariety(rfl.getVariety());
						qe.setRunType(rfl.getRunType());
						qe.setNumberOfBins(rfl.getLotAcceptedBins25Box());
						listQe.addElement(qe);
					}
				}
			}
		}
		
		this.setListQuickEntries(listQe);
	}
}


