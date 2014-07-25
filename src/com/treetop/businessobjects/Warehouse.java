/*
 * Created on December 30, 2008
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 * General Warehouse Information
 */
public class Warehouse extends Facility{
	
	protected	String		warehouse				= ""; 
	protected	String		warehouseDescription	= "";
	protected	String		address1				= "";
	protected	String		address2				= "";
	protected	String		address3				= "";
	protected	String		address4				= "";
	protected	String		city					= "";
	protected	String		state					= "";
	protected	String		zip						= "";

	
	/**
	 *  // Constructor
	 */
	public Warehouse() {
		super();

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
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
