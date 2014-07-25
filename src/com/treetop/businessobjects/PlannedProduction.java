/*
 * Created on May 27, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Planning - Planned Production -- 
 *      Summary of Orders put into Periods (buckets)
 */
public class PlannedProduction {
	
	protected	String		company						= "";
	protected	String		warehouse					= "";
	protected	String		warehouseDescription		= "";
	protected	String		itemNumber					= "";
	protected	String		itemDescription				= "";
	protected	String		itemGroup					= "";
	protected	String		basicUnitOfMeasure			= "";
	
	protected	String		workCenter					= "";
	protected	String		orderQuantity				= "";
	
	protected	String		periodNumber				= "";
	protected	String		periodBeginDate				= ""; // Always a Sunday
	protected	String		periodType					= ""; // month, week, year
	
	protected	String		safetyStock					= ""; 
	protected	String		inspectionInventory			= "";
	protected	String		onHandInventory				= "";
	
	
	/**
	 *  // Constructor
	 */
	public PlannedProduction() {
		super();

	}
	/**
	 * @return Returns the company.
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
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
	 * @return Returns the warehouseDescription.
	 */
	public String getWarehouseDescription() {
		return warehouseDescription;
	}
	/**
	 * @param warehouseDescription The warehouseDescription to set.
	 */
	public void setWarehouseDescription(String warehouseDescription) {
		this.warehouseDescription = warehouseDescription;
	}
	/**
	 * @return Returns the itemDescription.
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription The itemDescription to set.
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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
}
