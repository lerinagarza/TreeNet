/*
 * Created on July 19, 2010
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Available Fruit By Warehouse Information
 * 		-- Generally loaded from file (GRPYWINV or GRPYSCHL) and GRPYGROUP, CIDMAS
 */
public class AvailFruitByWhse{
	
	protected	String		whseNumber			   = "";	//INVWHSE or SCLWHSE
	protected	String		whseAddressNumber	   = "";	//INVDID  or SCLDID	
	protected   String      whseDescription        = "";    //IDSUNM - cidmas
	protected	String		changeUser			   = "";	//INVCHGU or SCLCHGU
	protected	String		changeDate			   = "0";	//INVCHGD or SCLCHGD
	protected	String		changeTime			   = "0";	//INVCHGT or SCLCHGT
	protected   Warehouse	warehouse			   = new Warehouse();
	protected   String      availScheduledQtyTotal = "0";   //in use with AvailableFruitByWhseDetail
	protected   String      allScheduledQtyTotal   = "0";   //in use with ScheduledLoadDetail
	protected 	String		inventoryType		   = "";	//INVITY
	
	/**
	 *  // Constructor
	 */
	public AvailFruitByWhse() {
		super();

	}


	public String getWhseNumber() {
		return whseNumber;
	}


	public void setWhseNumber(String whseNumber) {
		this.whseNumber = whseNumber;
	}


	public String getChangeDate() {
		return changeDate;
	}


	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}


	public String getChangeTime() {
		return changeTime;
	}


	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}


	public String getChangeUser() {
		return changeUser;
	}


	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}


	public Warehouse getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}


	public String getWhseAddressNumber() {
		return whseAddressNumber;
	}


	public void setWhseAddressNumber(String whseAddrNo) {
		this.whseAddressNumber = whseAddrNo;
	}


	public String getWhseDescription() {
		return whseDescription;
	}


	public void setWhseDescription(String whseDescription) {
		this.whseDescription = whseDescription;
	}


	public String getAllScheduledQtyTotal() {
		return allScheduledQtyTotal;
	}


	public void setAllScheduledQtyTotal(String allScheduledQtyTotal) {
		this.allScheduledQtyTotal = allScheduledQtyTotal;
	}


	public String getAvailScheduledQtyTotal() {
		return availScheduledQtyTotal;
	}


	public void setAvailScheduledQtyTotal(String availScheduledQtyTotal) {
		this.availScheduledQtyTotal = availScheduledQtyTotal;
	}


	public String getInventoryType() {
		return inventoryType;
	}


	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}
	

}
