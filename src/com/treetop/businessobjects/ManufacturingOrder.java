/*
 * Created on September 15, 2009
 *
 */
package com.treetop.businessobjects;

import java.util.*;
import java.math.*;


/**
 * @author twalto
 * 
 * Manufacturing Order Information
 */

/* modified 2012/0801 WTH
 * modified 2012/1024 TW -- Added object Manufacturing Order Detail to extend this object
 * 
 */
public class ManufacturingOrder {
	
	protected   String		companyNo					= "";
	protected	String		orderNumber					= "";
	protected	String		orderStatus					= "";
	protected	String		line						= "";
	protected	String		shift						= "";
	protected	String		defaultLocation				= "";
	protected	DateTime	actualStartDate				= null;
	protected	Warehouse	warehouse					= null;
	protected	Item		item						= null;
	
	protected	BigDecimal  orderQuantity				= BigDecimal.ZERO; 
	
	private		BigDecimal	production					= BigDecimal.ZERO; // Actual
	private		BigDecimal	usageRawFruit				= BigDecimal.ZERO; // Actual
	
	private     BigDecimal  productionFsAtStd			= BigDecimal.ZERO; // Actual
	private     BigDecimal  productionLbAtStd			= BigDecimal.ZERO; // Actual
	private     BigDecimal  productionGlAtStd           = BigDecimal.ZERO; // Actual
	
	private		BigDecimal	standardBatchSize			= BigDecimal.ZERO; 
	private		BigDecimal	numberOfBatches				= BigDecimal.ZERO;
	
	/**
	 *  // Constructor
	 */
	public ManufacturingOrder() {
		super();

	}

	public String getCompanyNo() {
		return companyNo;
	}


	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getDefaultLocation() {
		return defaultLocation;
	}

	public void setDefaultLocation(String defaultLocation) {
		this.defaultLocation = defaultLocation;
	}

	public DateTime getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(DateTime actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getProduction() {
		return production;
	}

	public void setProduction(BigDecimal production) {
		this.production = production;
	}

	public BigDecimal getUsageRawFruit() {
		return usageRawFruit;
	}

	public void setUsageRawFruit(BigDecimal usageRawFruit) {
		this.usageRawFruit = usageRawFruit;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public BigDecimal getProductionFsAtStd() {
		return productionFsAtStd;
	}

	public void setProductionFsAtStd(BigDecimal productionFsAtStd) {
		this.productionFsAtStd = productionFsAtStd;
	}

	public BigDecimal getProductionLbAtStd() {
		return productionLbAtStd;
	}

	public void setProductionLbAtStd(BigDecimal productionLbAtStd) {
		this.productionLbAtStd = productionLbAtStd;
	}

	public BigDecimal getStandardBatchSize() {
		return standardBatchSize;
	}

	public void setStandardBatchSize(BigDecimal standardBatchSize) {
		this.standardBatchSize = standardBatchSize;
	}

	public BigDecimal getNumberOfBatches() {
		return numberOfBatches;
	}

	public void setNumberOfBatches(BigDecimal numberOfBatches) {
		this.numberOfBatches = numberOfBatches;
	}

	public BigDecimal getProductionGlAtStd() {
		return productionGlAtStd;
	}

	public void setProductionGlAtStd(BigDecimal productionGlAtStd) {
		this.productionGlAtStd = productionGlAtStd;
	}
	

}
