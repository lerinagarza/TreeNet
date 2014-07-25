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
public class SalesOrder {
	
	protected	String		orderNumber					= "";
	protected	String		customerPONumber			= "";
	protected	String		customerNumber				= "";
	protected   String      customerName				= "";
	protected	String		customerPlanTo				= ""; // OKFRE1
	protected	String		invoiceNumber				= "";
	protected   String      orderType					= "";
	protected   String      market						= ""; // OKCFC3
	protected   String		shipDate					= ""; //OAORDT
	/**
	 *  // Constructor
	 */
	public SalesOrder() {
		super();

	}
	/**
	 * @return Returns the customerNumber.
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}
	/**
	 * @param customerNumber The customerNumber to set.
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	/**
	 * @return Returns the customerPONumber.
	 */
	public String getCustomerPONumber() {
		return customerPONumber;
	}
	/**
	 * @param customerPONumber The customerPONumber to set.
	 */
	public void setCustomerPONumber(String customerPONumber) {
		this.customerPONumber = customerPONumber;
	}
	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @return Returns the orderNumber.
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber The orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return Returns the orderType.
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType The orderType to set.
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return Returns the shipDate.
	 */
	public String getShipDate() {
		return shipDate;
	}
	/**
	 * @param shipDate The shipDate to set.
	 */
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	/**
	 * @return Returns the market.
	 */
	public String getMarket() {
		return market;
	}
	/**
	 * @param market The market to set.
	 */
	public void setMarket(String market) {
		this.market = market;
	}
	/**
	 * @return Returns the customerPlanTo.
	 */
	public String getCustomerPlanTo() {
		return customerPlanTo;
	}
	/**
	 * @param customerPlanTo The customerPlanTo to set.
	 */
	public void setCustomerPlanTo(String customerPlanTo) {
		this.customerPlanTo = customerPlanTo;
	}
}
