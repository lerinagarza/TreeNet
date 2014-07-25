/*
 * Created on March 19, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Planning - Planned Order Information
 */
public class PlannedOrder {
	
	protected	String		company						= "";
	protected	String		warehouse					= "";
	protected	String		warehouseDescription		= "";
	protected	String		producedItemType			= "";
	protected	String		producedItemNumber			= "";
	protected	String		producedItemDescription		= "";
	protected	String		producedItemBasicUOM		= "";
	protected	String		producedQuantity			= "";
	protected	String		consumedItemType			= "";
	protected	String		consumedItemNumber			= "";
	protected	String		consumedItemDescription		= "";
	protected	String		consumedItemBasicUOM		= "";
	protected	String		consumedQuantity			= "";
	protected	String		itemNumber					= "";
	protected	String		itemDescription				= "";
	protected	String		quantity 					= "";
	protected	String		planningDate				= "";
	protected	String		status						= "";
	protected	String		orderCategory				= "";
	protected	String		orderNumber					= "";
	protected	String		orderLine					= "";
	
	/**
	 *  // Constructor
	 */
	public PlannedOrder() {
		super();

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
	 * @return Returns the orderCategory.
	 */
	public String getOrderCategory() {
		return orderCategory;
	}
	/**
	 * @param orderCategory The orderCategory to set.
	 */
	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}
	/**
	 * @return Returns the orderLine.
	 */
	public String getOrderLine() {
		return orderLine;
	}
	/**
	 * @param orderLine The orderLine to set.
	 */
	public void setOrderLine(String orderLine) {
		this.orderLine = orderLine;
	}
	/**
	 * @return Returns the planningDate.
	 */
	public String getPlanningDate() {
		return planningDate;
	}
	/**
	 * @param planningDate The planningDate to set.
	 */
	public void setPlanningDate(String planningDate) {
		this.planningDate = planningDate;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return Returns the consumedItemDescription.
	 */
	public String getConsumedItemDescription() {
		return consumedItemDescription;
	}
	/**
	 * @param consumedItemDescription The consumedItemDescription to set.
	 */
	public void setConsumedItemDescription(String consumedItemDescription) {
		this.consumedItemDescription = consumedItemDescription;
	}
	/**
	 * @return Returns the consumedItemNumber.
	 */
	public String getConsumedItemNumber() {
		return consumedItemNumber;
	}
	/**
	 * @param consumedItemNumber The consumedItemNumber to set.
	 */
	public void setConsumedItemNumber(String consumedItemNumber) {
		this.consumedItemNumber = consumedItemNumber;
	}
	/**
	 * @return Returns the consumedItemType.
	 */
	public String getConsumedItemType() {
		return consumedItemType;
	}
	/**
	 * @param consumedItemType The consumedItemType to set.
	 */
	public void setConsumedItemType(String consumedItemType) {
		this.consumedItemType = consumedItemType;
	}
	/**
	 * @return Returns the producedItemDescription.
	 */
	public String getProducedItemDescription() {
		return producedItemDescription;
	}
	/**
	 * @param producedItemDescription The producedItemDescription to set.
	 */
	public void setProducedItemDescription(String producedItemDescription) {
		this.producedItemDescription = producedItemDescription;
	}
	/**
	 * @return Returns the producedItemNumber.
	 */
	public String getProducedItemNumber() {
		return producedItemNumber;
	}
	/**
	 * @param producedItemNumber The producedItemNumber to set.
	 */
	public void setProducedItemNumber(String producedItemNumber) {
		this.producedItemNumber = producedItemNumber;
	}
	/**
	 * @return Returns the producedItemType.
	 */
	public String getProducedItemType() {
		return producedItemType;
	}
	/**
	 * @param producedItemType The producedItemType to set.
	 */
	public void setProducedItemType(String producedItemType) {
		this.producedItemType = producedItemType;
	}
	/**
	 * @return Returns the consumedItemBasicUOM.
	 */
	public String getConsumedItemBasicUOM() {
		return consumedItemBasicUOM;
	}
	/**
	 * @param consumedItemBasicUOM The consumedItemBasicUOM to set.
	 */
	public void setConsumedItemBasicUOM(String consumedItemBasicUOM) {
		this.consumedItemBasicUOM = consumedItemBasicUOM;
	}
	/**
	 * @return Returns the consumedQuantity.
	 */
	public String getConsumedQuantity() {
		return consumedQuantity;
	}
	/**
	 * @param consumedQuantity The consumedQuantity to set.
	 */
	public void setConsumedQuantity(String consumedQuantity) {
		this.consumedQuantity = consumedQuantity;
	}
	/**
	 * @return Returns the producedItemBasicUOM.
	 */
	public String getProducedItemBasicUOM() {
		return producedItemBasicUOM;
	}
	/**
	 * @param producedItemBasicUOM The producedItemBasicUOM to set.
	 */
	public void setProducedItemBasicUOM(String producedItemBasicUOM) {
		this.producedItemBasicUOM = producedItemBasicUOM;
	}
	/**
	 * @return Returns the producedQuantity.
	 */
	public String getProducedQuantity() {
		return producedQuantity;
	}
	/**
	 * @param producedQuantity The producedQuantity to set.
	 */
	public void setProducedQuantity(String producedQuantity) {
		this.producedQuantity = producedQuantity;
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
	/**
	 * @return Returns the quantity.
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
