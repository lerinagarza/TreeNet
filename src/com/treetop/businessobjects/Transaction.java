/*
 * Created on February 12, 2009
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Transaction Information (Movex, M3)
 */
public class Transaction {
	
	protected	String		company			= "100";	
	protected   String		itemNumber		= "";
	protected	String		itemDescription	= "";
	protected	String		lotNumber		= "";
	protected	String		transactionType = "";
	protected	String		orderType		= "";
	protected	String		orderNumber		= "";
	protected	String		orderLineNumber = "";
	protected	String		userProfile		= "";
	protected	String		userLongName	= "";
	protected	String		transactionQuantity = "0";
	protected	Warehouse	warehouseInfo	= new Warehouse();
	protected	DateTime	transactionDate = new DateTime();
	
	/**
	 *  // Constructor
	 */
	public Transaction() {
		super();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
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

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getOrderLineNumber() {
		return orderLineNumber;
	}

	public void setOrderLineNumber(String orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}

	public String getTransactionQuantity() {
		return transactionQuantity;
	}

	public void setTransactionQuantity(String transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}

	public String getUserLongName() {
		return userLongName;
	}

	public void setUserLongName(String userLongName) {
		this.userLongName = userLongName;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public Warehouse getWarehouseInfo() {
		return warehouseInfo;
	}

	public void setWarehouseInfo(Warehouse warehouseInfo) {
		this.warehouseInfo = warehouseInfo;
	}

	public DateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(DateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
}
