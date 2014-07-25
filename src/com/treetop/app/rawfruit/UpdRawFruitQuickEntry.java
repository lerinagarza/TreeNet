/*
 * Created on November 5, 2008
 */

package com.treetop.app.rawfruit;

import java.math.BigDecimal;

import com.treetop.services.ServiceRawFruit;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitQuickEntry extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	
	// Must have in Update View Bean
	public String updateUser = "";
	
	
	public String lotNumber = "";
	public String lotNumberError = "";
	
	public String warehouse = "";
	
	public String supplier = "";
	public String supplierError = "";
	
	public String itemNumber = "";
	public String itemNumberError = "";
	public String itemCrop = "";
	
	public String getItemNumberError() {
		return itemNumberError;
	}




	public void setItemNumberError(String itemNumberError) {
		this.itemNumberError = itemNumberError;
	}




	public String variety = "";
	public String runType = "";
	
	public String numberOfBins = "";
	public String numberOfBinsError = "";
	
	
	
	
	// Fields Available for Update
	

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try {	
			StringBuffer err = new StringBuffer();
		
			//if a lot number is entered, supplier and number of bins are required fields
			if (!this.getLotNumber().equals("")) {

				if (this.getSupplier().equals("")) {
					this.setSupplierError("Required");
					err.append("Supplier required.  ");
				} else {
					
					
					
				}
				
				if (this.getItemNumber().equals("")) {
					this.setItemNumberError("Required");
					err.append("Item number is required.  ");
				}
				
				if (this.getNumberOfBins().equals("")) {
					this.setNumberOfBinsError("Required");
					err.append("Number of bins required.  ");
				} else {
					try {
						BigDecimal value = new BigDecimal(this.getNumberOfBins());
					} catch (Exception e) {
						this.setNumberOfBinsError("Enter a valid number");
						err.append("Number of bins invalid.  ");
					}
				}
				
			}
		
			if (!err.toString().equals("")) {
				this.setDisplayMessage("<br>" + "Lot " + this.getLotNumber() + ":" + err.toString());
			}
			
		} catch(Exception e) {
			
		}
		
		return;
	}




	public String getRequestType() {
		return requestType;
	}




	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}




	public String getDisplayMessage() {
		return displayMessage;
	}




	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}




	public String getUpdateUser() {
		return updateUser;
	}




	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}




	public String getLotNumber() {
		return lotNumber;
	}




	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}




	public String getWarehouse() {
		return warehouse;
	}




	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}




	public String getSupplier() {
		return supplier;
	}




	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}




	public String getItemNumber() {
		return itemNumber;
	}




	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}




	public String getVariety() {
		return variety;
	}




	public void setVariety(String variety) {
		this.variety = variety;
	}




	public String getRunType() {
		return runType;
	}




	public void setRunType(String runType) {
		this.runType = runType;
	}




	public String getNumberOfBins() {
		return numberOfBins;
	}




	public void setNumberOfBins(String numberOfBins) {
		this.numberOfBins = numberOfBins;
	}




	public String getLotNumberError() {
		return lotNumberError;
	}




	public void setLotNumberError(String lotNumberError) {
		this.lotNumberError = lotNumberError;
	}




	public String getNumberOfBinsError() {
		return numberOfBinsError;
	}




	public void setNumberOfBinsError(String numberOfBinsError) {
		this.numberOfBinsError = numberOfBinsError;
	}




	public String getSupplierError() {
		return supplierError;
	}




	public void setSupplierError(String supplierError) {
		this.supplierError = supplierError;
	}




	public String getItemCrop() {
		return itemCrop;
	}




	public void setItemCrop(String itemCrop) {
		this.itemCrop = itemCrop;
	}
	
	
}
