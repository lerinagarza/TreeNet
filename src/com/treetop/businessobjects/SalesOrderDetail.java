/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Additional Information relating to the Sales Order 
 */
public class SalesOrderDetail extends SalesOrder {
	
	protected	String		orderResponsible			= "";
	protected	String		orderSoldBy					= "";
	protected	String		lastModifiedBy				= "";
	protected	String		shippingWarehouse			= "";
	protected	String		plannedShipDate				= "";
	protected	String		actualShipDate				= "";
	protected	String		address1					= "";
	protected	String		address2					= "";
	protected	String		address3					= "";
	protected	String		address4					= "";
	protected	String		shipToCity					= "";
	protected	String		shipToState					= "";
	protected	String		shipToZip					= "";

	/**
	 *  // Constructor
	 */
	public SalesOrderDetail() {
		super();

	}
	/**
	 * @return Returns the lastModifiedBy.
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	/**
	 * @param lastModifiedBy The lastModifiedBy to set.
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	/**
	 * @return Returns the shipToCity.
	 */
	public String getShipToCity() {
		return shipToCity;
	}
	/**
	 * @param shipToCity The shipToCity to set.
	 */
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	/**
	 * @return Returns the shipToState.
	 */
	public String getShipToState() {
		return shipToState;
	}
	/**
	 * @param shipToState The shipToState to set.
	 */
	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}
	/**
	 * @return Returns the shipToZip.
	 */
	public String getShipToZip() {
		return shipToZip;
	}
	/**
	 * @param shipToZip The shipToZip to set.
	 */
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	/**
	 * @return Returns the actualShipDate.
	 */
	public String getActualShipDate() {
		return actualShipDate;
	}
	/**
	 * @param actualShipDate The actualShipDate to set.
	 */
	public void setActualShipDate(String actualShipDate) {
		this.actualShipDate = actualShipDate;
	}
	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return Returns the address3.
	 */
	public String getAddress3() {
		return address3;
	}
	/**
	 * @param address3 The address3 to set.
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	/**
	 * @return Returns the address4.
	 */
	public String getAddress4() {
		return address4;
	}
	/**
	 * @param address4 The address4 to set.
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	/**
	 * @return Returns the orderResponsible.
	 */
	public String getOrderResponsible() {
		return orderResponsible;
	}
	/**
	 * @param orderResponsible The orderResponsible to set.
	 */
	public void setOrderResponsible(String orderResponsible) {
		this.orderResponsible = orderResponsible;
	}
	/**
	 * @return Returns the orderSoldBy.
	 */
	public String getOrderSoldBy() {
		return orderSoldBy;
	}
	/**
	 * @param orderSoldBy The orderSoldBy to set.
	 */
	public void setOrderSoldBy(String orderSoldBy) {
		this.orderSoldBy = orderSoldBy;
	}
	/**
	 * @return Returns the shippingWarehouse.
	 */
	public String getShippingWarehouse() {
		return shippingWarehouse;
	}
	/**
	 * @param shippingWarehouse The shippingWarehouse to set.
	 */
	public void setShippingWarehouse(String shippingWarehouse) {
		this.shippingWarehouse = shippingWarehouse;
	}
	/**
	 * @return Returns the plannedShipDate.
	 */
	public String getPlannedShipDate() {
		return plannedShipDate;
	}
	/**
	 * @param plannedShipDate The plannedShipDate to set.
	 */
	public void setPlannedShipDate(String plannedShipDate) {
		this.plannedShipDate = plannedShipDate;
	}
}
