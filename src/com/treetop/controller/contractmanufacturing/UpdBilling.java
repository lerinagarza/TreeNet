package com.treetop.controller.contractmanufacturing;

import java.util.Vector;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Lot;
import com.treetop.businessobjects.ManufacturingOrder;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.viewbeans.BaseViewBeanR2;

public class UpdBilling extends BaseViewBeanR2 {

	private String billingType						= "";
	private String manufacturingOrderNumber			= "";
	private String manufacturingOrderNumberError	= "";
	private String transactionDate					= "";
	private String transactionDateError				= "";
	private String transactionDateFileFormat		= ""; // yyyymmdd
	private String updateUser						= "";
	
	private String warehouseNumber					= "";
	private String itemNumber						= "";
	private String itemDescription					= "";
	private String unitOfMeasure					= "";
	private String productionDate					= "";
	private String productionQuantity				= "";
	private String customerNumber					= "";
	private String orderFacility					= "";
	private String orderType						= "";
	private String tempCONumber						= "";
	private int	   itemDecimals                     = 0;
	
	private	Vector<Lot>	listLots = new Vector<Lot>();
	
	private String submit							= "";
	private String status							= "";
	
	public String getSubmit() {
		return submit;
	}


	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void validate() {
		if (this.getEnvironment().trim().equals("")) {
			this.setEnvironment("PRD");
		}
		if (!this.getSubmit().trim().equals(""))
		{
		    if (this.getManufacturingOrderNumber().trim().equals("")) {
			   this.setManufacturingOrderNumberError("You must enter a valid Manufacturing Order Number.");
		    }
			if (!this.getTransactionDate().trim().equals(""))
			{ 
				DateTime testDate = UtilityDateTime.getDateFromMMddyyWithSlash(this.getTransactionDate());
				if (testDate.getDateErrorMessage().trim().equals(""))
					this.setTransactionDateFileFormat(testDate.getDateFormatyyyyMMdd());
				else
					this.setTransactionDateError(testDate.getDateErrorMessage());
				
			}else{  // if Date not entered use Today's Date
			  DateTime dtnow = UtilityDateTime.getSystemDate();
			  this.setTransactionDate(dtnow.getDateFormatMMddyySlash());
			  this.setTransactionDateFileFormat(dtnow.getDateFormatyyyyMMdd());
			}
				
		}
		if (!this.getManufacturingOrderNumberError().trim().equals("") ||
			!this.getTransactionDateError().trim().equals(""))
		{
			this.setDisplayMessage(this.getManufacturingOrderNumberError() + "&#160;<br>" + this.getTransactionDateError());
		}
	}

	
	public String getManufacturingOrderNumber() {
		return manufacturingOrderNumber;
	}

	public void setManufacturingOrderNumber(String manufacturingOrderNumber) {
		this.manufacturingOrderNumber = manufacturingOrderNumber;
	}

	public String getManufacturingOrderNumberError() {
		return manufacturingOrderNumberError;
	}

	public void setManufacturingOrderNumberError(
			String manufacturingOrderNumberError) {
		this.manufacturingOrderNumberError = manufacturingOrderNumberError;
	}

	public String getBillingType() {
		return billingType;
	}


	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}


	public String getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}


	public String getTransactionDateError() {
		return transactionDateError;
	}


	public void setTransactionDateError(String transactionDateError) {
		this.transactionDateError = transactionDateError;
	}


	public String getTransactionDateFileFormat() {
		return transactionDateFileFormat;
	}


	public void setTransactionDateFileFormat(String transactionDateFileFormat) {
		this.transactionDateFileFormat = transactionDateFileFormat;
	}


	public String getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	public String getWarehouseNumber() {
		return warehouseNumber;
	}


	public void setWarehouseNumber(String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}


	public String getItemNumber() {
		return itemNumber;
	}


	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}


	public String getItemDescription() {
		return itemDescription;
	}


	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}


	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}


	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}


	public String getProductionDate() {
		return productionDate;
	}


	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}


	public String getProductionQuantity() {
		return productionQuantity;
	}


	public void setProductionQuantity(String productionQuantity) {
		this.productionQuantity = productionQuantity;
	}


	public String getCustomerNumber() {
		return customerNumber;
	}


	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}


	public String getOrderFacility() {
		return orderFacility;
	}


	public void setOrderFacility(String orderFacility) {
		this.orderFacility = orderFacility;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public String getTempCONumber() {
		return tempCONumber;
	}


	public void setTempCONumber(String tempCONumber) {
		this.tempCONumber = tempCONumber;
	}


	public int getItemDecimals() {
		return itemDecimals;
	}


	public void setItemDecimals(int itemDecimals) {
		this.itemDecimals = itemDecimals;
	}


	public Vector<Lot> getListLots() {
		return listLots;
	}


	public void setListLots(Vector<Lot> listLots) {
		this.listLots = listLots;
	}	
	
}
