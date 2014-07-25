/*
 * Created on Feb 22, 2006
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 *  4/4/12 TWalton - added OriginalQuantity
 *                   
 * Inventory Lot information (number,type,etc)
 */
public class Lot extends Item{
	
	protected	String		lotNumber		= "";
	protected	String		lotType			= "";	
	protected	String		invGroupCode1	= "";
	protected   String		invGroupCode2	= "";
	protected	String		invGroupCode3	= "";
	protected	String		resource		= "";
	protected	String		spec			= "";
	protected	String		variety			= "";
	protected	String		plant			= "";
	protected	String		location		= "";
	protected	String		quantity		= "";
	protected	String		originalQuantity = "";
	protected	String		manufactureDate = "";
	protected	String		expirationDate	= "";
	protected	String		followUpDate	= "";
	protected	String		salesDate		= "";
	protected	String		facility		= "";
	protected	String		facilityName	= "";
	protected	String		warehouse		= "";
	protected	String		warehouseName   = "";
	protected	String		brix			= "";
	protected	String		brixConversion	= "";
	protected	String		lotStatus		= "";
	protected	String		allocable		= "";
	protected	String		reference2		= "";
	protected	String		remark			= "";
	protected	String		tempOrder		= "";
	protected	String		attributeNumber = "";
	protected	String		OSLot			= ""; // Ocean Spray Lot Number defined by using supplier and manufacturer serial number

	/**
	 * 
	 */
	public Lot() {
		super();

	}

	/**
	 * @return Returns the invGroupCode1.
	 */
	public String getInvGroupCode1() {
		return invGroupCode1;
	}
	/**
	 * @param invGroupCode1 The invGroupCode1 to set.
	 */
	public void setInvGroupCode1(String invGroupCode1) {
		this.invGroupCode1 = invGroupCode1;
	}
	/**
	 * @return Returns the invGroupCode2.
	 */
	public String getInvGroupCode2() {
		return invGroupCode2;
	}
	/**
	 * @param invGroupCode2 The invGroupCode2 to set.
	 */
	public void setInvGroupCode2(String invGroupCode2) {
		this.invGroupCode2 = invGroupCode2;
	}
	/**
	 * @return Returns the invGroupCode3.
	 */
	public String getInvGroupCode3() {
		return invGroupCode3;
	}
	/**
	 * @param invGroupCode3 The invGroupCode3 to set.
	 */
	public void setInvGroupCode3(String invGroupCode3) {
		this.invGroupCode3 = invGroupCode3;
	}
	/**
	 * @return Returns the loaction.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param loaction The loaction to set.
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
	 * @return Returns the lotType.
	 */
	public String getLotType() {
		return lotType;
	}
	/**
	 * @param lotType The lotType to set.
	 */
	public void setLotType(String lotType) {
		this.lotType = lotType;
	}
	/**
	 * @return Returns the plant.
	 */
	public String getPlant() {
		return plant;
	}
	/**
	 * @param plant The plant to set.
	 */
	public void setPlant(String plant) {
		this.plant = plant;
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
	/**
	 * @return Returns the resource.
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return Returns the spec.
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec The spec to set.
	 */
	public void setSpec(String spec) {
		this.spec = spec;
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
	 * @return Returns the manufactureDate.
	 */
	public String getManufactureDate() {
		return manufactureDate;
	}
	/**
	 * @param manufactureDate The manufactureDate to set.
	 */
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
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
	 * @return Returns the brix.
	 */
	public String getBrix() {
		return brix;
	}
	/**
	 * @param brix The brix to set.
	 */
	public void setBrix(String brix) {
		this.brix = brix;
	}
	/**
	 * @return Returns the brixConversion.
	 */
	public String getBrixConversion() {
		return brixConversion;
	}
	/**
	 * @param brixConversion The brixConversion to set.
	 */
	public void setBrixConversion(String brixConversion) {
		this.brixConversion = brixConversion;
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
	 * @return Returns the facilityName.
	 */
	public String getFacilityName() {
		return facilityName;
	}
	/**
	 * @param facilityName The facilityName to set.
	 */
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	/**
	 * @return Returns the lotStatus.
	 */
	public String getLotStatus() {
		return lotStatus;
	}
	/**
	 * @param lotStatus The lotStatus to set.
	 */
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	public String getAllocable() {
		return allocable;
	}

	public void setAllocable(String allocable) {
		this.allocable = allocable;
	}

	public String getReference2() {
		return reference2;
	}

	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOriginalQuantity() {
		return originalQuantity;
	}

	public void setOriginalQuantity(String originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	public String getTempOrder() {
		return tempOrder;
	}

	public void setTempOrder(String tempOrder) {
		this.tempOrder = tempOrder;
	}

	public String getAttributeNumber() {
		return attributeNumber;
	}

	public void setAttributeNumber(String attributeNumber) {
		this.attributeNumber = attributeNumber;
	}

	public String getOSLot() {
		return OSLot;
	}

	public void setOSLot(String oSLot) {
		OSLot = oSLot;
	}
}
