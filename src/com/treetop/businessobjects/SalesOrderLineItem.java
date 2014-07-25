/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Additional Information relating to the Sales Order - Line Items
 */
public class SalesOrderLineItem extends SalesOrderDetail {
	
	protected	Item	itemClass = new Item();
	protected	String	lineNumber = "";
	protected	String	suffix = "";
	protected   String	orderQuantity = "";
		// Vector of Lot Classes -- By Item Number
	protected	Vector	lots	= new Vector();
	/**
	 *  // Constructor
	 */
	public SalesOrderLineItem() {
		super();

	}
	/**
	 * @return Returns the itemClass.
	 */
	public Item getItemClass() {
		return itemClass;
	}
	/**
	 * @param itemClass The itemClass to set.
	 */
	public void setItemClass(Item itemClass) {
		this.itemClass = itemClass;
	}
	/**
	 * @return Returns the lots.
	 */
	public Vector getLots() {
		return lots;
	}
	/**
	 * @param lots The lots to set.
	 */
	public void setLots(Vector lots) {
		this.lots = lots;
	}
	/**
	 * @return Returns the lineNumber.
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return Returns the suffix.
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix The suffix to set.
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	/**
	 * @return Returns the orderQuantity.
	 */
	public String getOrderQuantity() {
		return orderQuantity;
	}
	/**
	 * @param orderQuantity The orderQuantity to set.
	 */
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
}
