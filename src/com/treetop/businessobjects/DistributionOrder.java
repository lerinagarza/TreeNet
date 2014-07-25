/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Sales Order / Distribution Order Information
 */
public class DistributionOrder {
	
	protected   String		companyNo					= "";
	protected	String		orderNumber					= "";
	protected   String      orderType					= "";
	protected	String		facility					= "";
	protected	Warehouse	fromWarehouse				= null;
	protected	Warehouse	toWarehouse					= null;
	protected	String		shipDate					= "";
	protected	String		receiveDate					= "";
	
	
	/**
	 *  // Constructor
	 */
	public DistributionOrder() {
		super();

	}


	public String getCompanyNo() {
		return companyNo;
	}


	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}


	public String getFacility() {
		return facility;
	}


	public void setFacility(String facility) {
		this.facility = facility;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public Warehouse getToWarehouse() {
		return toWarehouse;
	}


	public void setToWarehouse(Warehouse toWarehouse) {
		this.toWarehouse = toWarehouse;
	}


	public Warehouse getFromWarehouse() {
		return fromWarehouse;
	}


	public void setFromWarehouse(Warehouse warehouse) {
		this.fromWarehouse = warehouse;
	}
	
	public String getShipDate() {
		return shipDate;
	}


	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	
	public String getReceiveDate() {
		return receiveDate;
	}


	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

}
