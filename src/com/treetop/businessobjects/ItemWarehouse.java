/*
 * Created on October 15, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Item Warehouse Information
 */
public class ItemWarehouse extends ItemFacility{
	
	protected	String		warehouseNumber			= "";
	protected	String		warehouseName			= "";	
	protected	String		daysShelfLife			= "";

	/**
	 *  // Constructor
	 */
	public ItemWarehouse() {
		super();

	}
	/**
	 * @return Returns the warehouseName.
	 */
	public String getWarehouseName() {
		return warehouseName;
	}
	/**
	 * @param warehouseName The warehouseName to set.
	 */
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	/**
	 * @return Returns the warehouseNumber.
	 */
	public String getWarehouseNumber() {
		return warehouseNumber;
	}
	/**
	 * @param warehouseNumber The warehouseNumber to set.
	 */
	public void setWarehouseNumber(String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}
	/**
	 * @return Returns the daysShelfLife.
	 */
	public String getDaysShelfLife() {
		return daysShelfLife;
	}
	/**
	 * @param daysShelfLife The daysShelfLife to set.
	 */
	public void setDaysShelfLife(String daysShelfLife) {
		this.daysShelfLife = daysShelfLife;
	}
}
