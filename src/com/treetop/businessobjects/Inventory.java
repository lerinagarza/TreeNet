/*
 * Created on April 18, 2008 
 */
package com.treetop.businessobjects;

/**
 * @author David Teri Walton
 *
 * Inventory Fields
 */
public class Inventory {
	
	protected	String		facility					= "";
	protected	String		facilityDescription			= "";
	protected	String		company						= "";
	protected	String		warehouse					= "";
	protected	String		warehouseDescription		= "";
	protected	String		location					= "";
	protected	String		itemType					= "";
	protected	String		itemTypeDescription			= "";
	protected	String		itemNumber					= "";
	protected	String		itemDescription				= "";
	protected	String		itemGroup					= "";
	protected	String		lotNumber					= "";
	protected	String		lotRef1						= "";
	protected	String		lotRef2						= "";
	protected	String		attributeNumber				= "";
	protected	String		quantityOnHand				= "";
	protected	String		lastSalesDate				= "";
	protected	String		priorityDate				= "";
	protected	String		status						= "";
	protected	String		checkedValue				= "";
	protected	String		comment						= "";
	
	// Specifically for Raw Fruit Related Inventory?
	protected	String		quantityInTons				= "";
	protected	String		numberOfBins				= "";
	protected	String		grade						= "";
	protected	String		conventionalOrganic			= "";
	protected	String		variety						= "";
	protected	String		countryOfOrigin				= "";
	protected	String		runType						= "";
	protected	String		receiptDate					= "";
	protected   Supplier	supplier					= new Supplier();
	
	/**
	 * Basic constructor.
	 */
	public Inventory() {
		super();
	}
	/**
	 * @return Returns the facility.
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility The facility to set.
	 */
	public void setFacility(String facility) {
		this.facility = facility;
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
	 * @return Returns the quantityOnHand.
	 */
	public String getQuantityOnHand() {
		return quantityOnHand;
	}
	/**
	 * @param quantityOnHand The quantityOnHand to set.
	 */
	public void setQuantityOnHand(String quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
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
	 * @return Returns the lastSalesDate.
	 */
	public String getLastSalesDate() {
		return lastSalesDate;
	}
	/**
	 * @param lastSalesDate The lastSalesDate to set.
	 */
	public void setLastSalesDate(String lastSalesDate) {
		this.lastSalesDate = lastSalesDate;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return Returns the lotNumber.
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 * @return Returns the lotRef1.
	 */
	public String getLotRef1() {
		return lotRef1;
	}
	/**
	 * @param lotRef1 The lotRef1 to set.
	 */
	public void setLotRef1(String lotRef1) {
		this.lotRef1 = lotRef1;
	}
	/**
	 * @return Returns the lotRef2.
	 */
	public String getLotRef2() {
		return lotRef2;
	}
	/**
	 * @param lotRef2 The lotRef2 to set.
	 */
	public void setLotRef2(String lotRef2) {
		this.lotRef2 = lotRef2;
	}
	/**
	 * @return Returns the priorityDate.
	 */
	public String getPriorityDate() {
		return priorityDate;
	}
	/**
	 * @param priorityDate The priorityDate to set.
	 */
	public void setPriorityDate(String priorityDate) {
		this.priorityDate = priorityDate;
	}
	/**
	 * @return Returns the facilityDescription.
	 */
	public String getFacilityDescription() {
		return facilityDescription;
	}
	/**
	 * @param facilityDescription The facilityDescription to set.
	 */
	public void setFacilityDescription(String facilityDescription) {
		this.facilityDescription = facilityDescription;
	}
	/**
	 * @return Returns the itemType.
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @param itemType The itemType to set.
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * @return Returns the itemTypeDescription.
	 */
	public String getItemTypeDescription() {
		return itemTypeDescription;
	}
	/**
	 * @param itemTypeDescription The itemTypeDescription to set.
	 */
	public void setItemTypeDescription(String itemTypeDescription) {
		this.itemTypeDescription = itemTypeDescription;
	}
	/**
	 * @return Returns the itemGroup.
	 */
	public String getItemGroup() {
		return itemGroup;
	}
	/**
	 * @param itemGroup The itemGroup to set.
	 */
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
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
	 * @return Returns the checkedValue.
	 */
	public String getCheckedValue() {
		return checkedValue;
	}
	/**
	 * @param checkedValue The checkedValue to set.
	 */
	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the conventionalOrganic.
	 */
	public String getConventionalOrganic() {
		return conventionalOrganic;
	}
	/**
	 * @param conventionalOrganic The conventionalOrganic to set.
	 */
	public void setConventionalOrganic(String conventionalOrganic) {
		this.conventionalOrganic = conventionalOrganic;
	}
	/**
	 * @return Returns the grade.
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade The grade to set.
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return Returns the receiptDate.
	 */
	public String getReceiptDate() {
		return receiptDate;
	}
	/**
	 * @param receiptDate The receiptDate to set.
	 */
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	/**
	 * @return Returns the supplier.
	 */
	public Supplier getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier The supplier to set.
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	/**
	 * @return Returns the variety.
	 */
	public String getVariety() {
		return variety;
	}
	/**
	 * @param variety The variety to set.
	 */
	public void setVariety(String variety) {
		this.variety = variety;
	}
	/**
	 * @return Returns the numberOfBins.
	 */
	public String getNumberOfBins() {
		return numberOfBins;
	}
	/**
	 * @param numberOfBins The numberOfBins to set.
	 */
	public void setNumberOfBins(String numberOfBins) {
		this.numberOfBins = numberOfBins;
	}

	/**
	 * @return Returns the quantityInTons.
	 */
	public String getQuantityInTons() {
		return quantityInTons;
	}
	/**
	 * @param quantityInTons The quantityInTons to set.
	 */
	public void setQuantityInTons(String quantityInTons) {
		this.quantityInTons = quantityInTons;
	}
	public String getAttributeNumber() {
		return attributeNumber;
	}
	public void setAttributeNumber(String attributeNumber) {
		this.attributeNumber = attributeNumber;
	}
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
}
