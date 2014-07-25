/*
 * Created on September 15, 2009
 */

package com.treetop.app.inventory;

import java.util.Vector;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Lot;
import com.treetop.services.*;
import com.treetop.utilities.*;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */
public class UpdProductionLot extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	public String environment = "";
	
	// main Fields on Page
	public String manufacturingOrder = "";
	public String itemNumber = "";
	public String productionLine = ""; 
	public String shift = ""; 
	public String billOfLading = "";
	
	// Loop/Subfile on Page
	public String lot = "";
	public String lotError = "";
	public String warehouse = "";
	public String warehouseError = "";
	public String defaultWarehouse = "";
	public String location = "";
	public String locationError = "";
	public String locationFrom = "";
	public String warehouseFrom = "";
	public String defaultLocation = "";
	public String quantity = "0";
	public String quantityError = "";
	public String transferCode = "";
	public String manufactureDate = "";
	public String manufactureDateError = "";
	public DateTime manufactureDateObject = new DateTime();

	public Lot lotInfo = new Lot();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 */
	public void validate() {
		try
		{	
			 // ------------------------------------------------------------------------
			 //  Lot Number - must NOT be in MILOMA
				if (this.lot.trim().equals(""))
				   this.setWarehouseError("Must have Lot");
				else
				{
					//Take this LOT and MAKE SURE it is upper case:
					this.setLot(this.lot.trim().toUpperCase());
					
					
					
					/* Not ready to get rid of "F" lots yet
					if (this.getLot().substring(0,1).equals("F")) {
						this.setLotError("Lot " + this.getLot() + " should not begin with an \"F\".  Please choose another lot number");
					}
					*/
					
					
				   // When testing for a lot you WANT it to have an error.
					// because you want to make sure this lot is NOT in the MILOMA file YET
					String testForLot = ServiceLot.verifyM3LotNumber(this.getEnvironment(), this.lot.trim(), this.getItemNumber());
					if (testForLot.equals(""))
					   this.setLotError("This Lot Number " + this.lot + " and Item Number " + this.itemNumber + " is already a Valid M3 Lot Number, Please Choose a Different Number");
				}
			 //------------------------------------------------------------------------
			 //  Warehouse
				if (this.warehouse.trim().equals(""))
				   this.setWarehouse(this.getDefaultWarehouse());
				
				this.setWarehouseError(ServiceWarehouse.verifyWarehouse(this.getEnvironment(), this.getWarehouse()));
			//------------------------------------------------------------------------
			// Location
			if (this.location.trim().equals(""))
				this.setLocationError("Must have a Valid Location");
			else
			{
				if (this.getWarehouseError().trim().equals(""))
				  this.setLocationError(ServiceWarehouse.verifyLocation(this.getEnvironment(), this.getWarehouse(), this.getLocation()));
				else
				   this.setLocationError("Must have Location as well as a Valid Warehouse");
			}
//			------------------------------------------------------------------------
			//  Production Line Default 1 if not there
			if (this.productionLine.trim().equals("") ||
				this.productionLine.trim().equals("0"))
				this.setProductionLine("1");
			   	
//			------------------------------------------------------------------------
			//  Shift Default 1 if not there
			if (this.shift.trim().equals("") ||
				this.shift.trim().equals("0"))
				this.setShift("1");
			   				
			//------------------------------------------------------------------------
			//  Quantity
			if (this.quantity.trim().equals(""))
				this.setQuantity("0");
			if (!this.quantity.trim().equals("0"))
				this.setQuantityError(validateInteger(this.quantity));
			
			// Manufacture Date
			if (!this.manufactureDate.trim().equals(""))
			{
				this.setManufactureDateError(validateDate(this.manufactureDate.trim()));
				if (this.getManufactureDateError().trim().equals(""))
				{
				   this.setManufactureDateObject(UtilityDateTime.getDateFromMMddyyyyWithSlash(this.manufactureDate.trim()));
				   DateTime today = UtilityDateTime.getSystemDate();
				   if (new Integer(this.getManufactureDateObject().getDateFormatyyyyMMdd()).intValue() >
				       new Integer(today.getDateFormatyyyyMMdd()).intValue())
				      this.setManufactureDateError("This date is in the Future, please enter a valid date");
				}
			}
//			-----------------------------------------------------------------------
			// Set the Error of the LOT Section
			StringBuffer lotError = new StringBuffer();
			lotError.append(this.getLotError());
			if (!lotError.toString().trim().equals("") &&
				!this.getWarehouseError().trim().equals(""))
				lotError.append("<br>");
			lotError.append(this.getWarehouseError().trim());
			if (!lotError.toString().trim().equals("") &&
				!this.getLocationError().trim().equals(""))
				lotError.append("<br>");
			lotError.append(this.getLocationError().trim());
			if (!lotError.toString().trim().equals("") &&
				!this.getQuantityError().trim().equals(""))
			   lotError.append("<br>");
			lotError.append(this.getQuantityError().trim());
			if (!lotError.toString().trim().equals("") &&
				!this.getManufactureDateError().trim().equals(""))
				   lotError.append("<br>");
			lotError.append(this.getManufactureDateError().trim());
				
			this.setDisplayMessage(lotError.toString().trim());
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location.toUpperCase();
	}
	public String getLocationError() {
		return locationError;
	}
	public void setLocationError(String locationError) {
		this.locationError = locationError;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getLotError() {
		return lotError;
	}
	public void setLotError(String lotError) {
		this.lotError = lotError;
	}
	public Lot getLotInfo() {
		return lotInfo;
	}
	public void setLotInfo(Lot lotInfo) {
		this.lotInfo = lotInfo;
	}
	public String getManufacturingOrder() {
		return manufacturingOrder;
	}
	public void setManufacturingOrder(String manufacturingOrder) {
		this.manufacturingOrder = manufacturingOrder;
	}
	public String getProductionLine() {
		return productionLine;
	}
	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getQuantityError() {
		return quantityError;
	}
	public void setQuantityError(String quantityError) {
		this.quantityError = quantityError;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getWarehouseError() {
		return warehouseError;
	}
	public void setWarehouseError(String warehouseError) {
		this.warehouseError = warehouseError;
	}
	public String getDefaultLocation() {
		return defaultLocation;
	}
	public void setDefaultLocation(String defaultLocation) {
		this.defaultLocation = defaultLocation;
	}
	public String getDefaultWarehouse() {
		return defaultWarehouse;
	}
	public void setDefaultWarehouse(String defaultWarehouse) {
		this.defaultWarehouse = defaultWarehouse;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	public String getLocationFrom() {
		return locationFrom;
	}
	public void setLocationFrom(String locationFrom) {
		this.locationFrom = locationFrom;
	}
	public String getWarehouseFrom() {
		return warehouseFrom;
	}
	public void setWarehouseFrom(String warehouseFrom) {
		this.warehouseFrom = warehouseFrom;
	}
	public String getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public DateTime getManufactureDateObject() {
		return manufactureDateObject;
	}
	public void setManufactureDateObject(DateTime manufactureDateObject) {
		this.manufactureDateObject = manufactureDateObject;
	}
	public String getBillOfLading() {
		return billOfLading;
	}
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}
	public String getManufactureDateError() {
		return manufactureDateError;
	}
	public void setManufactureDateError(String manufactureDateError) {
		this.manufactureDateError = manufactureDateError;
	}
}
