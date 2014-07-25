/*
 * Created on Aug 29, 2006
 *
 */
package com.treetop.businessobjects;

import java.util.*;
import java.math.*;

/**
 * @author twalton
 *
 * Contains values specific to the Resource HAC (Historical Average Cost)
 */
public class ResourceHAC extends Resource {

	String	plant;
	String	processCode;
	String	fiscalYear;
	String	producedResource;
	String	productLineCode;
	String	statusFlag;
	String	carryInUnits;
	String	carryInAmount;
	String	carryInHAC;
	// Vector's built by Period Buckets - Uses String's
	Vector   dollarsProduction;
	Vector   dollarsPaid;
	Vector   dollarsTransfered;
	Vector   dollarsUsageGLPosted;
	Vector   dollarsAdjustementGLPosted;
	Vector   dollarsWriteOff;
	Vector   historicalAverageCost;
	Vector   unitsProduction;
	Vector   unitsReceived;
	Vector   unitsTransfered;
	Vector   unitsUsageGLPosted;
	Vector   unitsAdjustmentOffline;
	
	/**
	 * 
	 */
	public ResourceHAC() {
		super();
	}

	/**
	 * Used to test creation of class.
	**/
	public String toString() {

		return new String(
			"recordType: "
				+ recordType
				+ "\n"
				+ "status"
				+ status
				+ "\n"
				+ "resourceNumber: "
				+ resourceNumber
				+ "\n"
				+ "resourceDescription: "
				+ resourceDescription
				+ "\n"
				+ "plant: "
				+ plant
				+ "\n"
				+ "processCode: "
				+ processCode
				+ "\n"
				+ "fiscalYear: "
				+ fiscalYear
				+ "\n"
				+ "producedResource: "
				+ producedResource
				+ "\n"
				+ "productLineCode: "
				+ productLineCode
				+ "\n"
				+ "statusFlag: "
				+ statusFlag
				+ "\n"
				+ "carryInUnits: "
				+ carryInUnits
				+ "\n"
				+ "carryInAmount: "
				+ carryInAmount
				+ "\n"
				+ "carryInHAC: "
				+ carryInHAC
				+ "\n");
	}

	/**
	 * @return Returns the carryInAmount.
	 */
	public String getCarryInAmount() {
		return carryInAmount;
	}
	/**
	 * @param carryInAmount The carryInAmount to set.
	 */
	public void setCarryInAmount(String carryInAmount) {
		this.carryInAmount = carryInAmount;
	}
	/**
	 * @return Returns the carryInHAC.
	 */
	public String getCarryInHAC() {
		return carryInHAC;
	}
	/**
	 * @param carryInHAC The carryInHAC to set.
	 */
	public void setCarryInHAC(String carryInHAC) {
		this.carryInHAC = carryInHAC;
	}
	/**
	 * @return Returns the carryInUnits.
	 */
	public String getCarryInUnits() {
		return carryInUnits;
	}
	/**
	 * @param carryInUnits The carryInUnits to set.
	 */
	public void setCarryInUnits(String carryInUnits) {
		this.carryInUnits = carryInUnits;
	}
	/**
	 * @return Returns the fiscalYear.
	 */
	public String getFiscalYear() {
		return fiscalYear;
	}
	/**
	 * @param fiscalYear The fiscalYear to set.
	 */
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
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
	 * @return Returns the processCode.
	 */
	public String getProcessCode() {
		return processCode;
	}
	/**
	 * @param processCode The processCode to set.
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	/**
	 * @return Returns the producedResource.
	 */
	public String getProducedResource() {
		return producedResource;
	}
	/**
	 * @param producedResource The producedResource to set.
	 */
	public void setProducedResource(String producedResource) {
		this.producedResource = producedResource;
	}
	/**
	 * @return Returns the productLineCode.
	 */
	public String getProductLineCode() {
		return productLineCode;
	}
	/**
	 * @param productLineCode The productLineCode to set.
	 */
	public void setProductLineCode(String productLineCode) {
		this.productLineCode = productLineCode;
	}
	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}
	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
	 * @return Returns the dollarsAdjustementGLPosted.
	 */
	public Vector getDollarsAdjustementGLPosted() {
		return dollarsAdjustementGLPosted;
	}
	/**
	 * @param dollarsAdjustementGLPosted The dollarsAdjustementGLPosted to set.
	 */
	public void setDollarsAdjustementGLPosted(Vector dollarsAdjustementGLPosted) {
		this.dollarsAdjustementGLPosted = dollarsAdjustementGLPosted;
	}
	/**
	 * @return Returns the dollarsPaid.
	 */
	public Vector getDollarsPaid() {
		return dollarsPaid;
	}
	/**
	 * @param dollarsPaid The dollarsPaid to set.
	 */
	public void setDollarsPaid(Vector dollarsPaid) {
		this.dollarsPaid = dollarsPaid;
	}
	/**
	 * @return Returns the dollarsProduction.
	 */
	public Vector getDollarsProduction() {
		return dollarsProduction;
	}
	/**
	 * @param dollarsProduction The dollarsProduction to set.
	 */
	public void setDollarsProduction(Vector dollarsProduction) {
		this.dollarsProduction = dollarsProduction;
	}
	/**
	 * @return Returns the dollarsTransfered.
	 */
	public Vector getDollarsTransfered() {
		return dollarsTransfered;
	}
	/**
	 * @param dollarsTransfered The dollarsTransfered to set.
	 */
	public void setDollarsTransfered(Vector dollarsTransfered) {
		this.dollarsTransfered = dollarsTransfered;
	}
	/**
	 * @return Returns the dollarsUsageGLPosted.
	 */
	public Vector getDollarsUsageGLPosted() {
		return dollarsUsageGLPosted;
	}
	/**
	 * @param dollarsUsageGLPosted The dollarsUsageGLPosted to set.
	 */
	public void setDollarsUsageGLPosted(Vector dollarsUsageGLPosted) {
		this.dollarsUsageGLPosted = dollarsUsageGLPosted;
	}
	/**
	 * @return Returns the dollarsWriteOff.
	 */
	public Vector getDollarsWriteOff() {
		return dollarsWriteOff;
	}
	/**
	 * @param dollarsWriteOff The dollarsWriteOff to set.
	 */
	public void setDollarsWriteOff(Vector dollarsWriteOff) {
		this.dollarsWriteOff = dollarsWriteOff;
	}
	/**
	 * @return Returns the historicalAverageCost.
	 */
	public Vector getHistoricalAverageCost() {
		return historicalAverageCost;
	}
	/**
	 * @param historicalAverageCost The historicalAverageCost to set.
	 */
	public void setHistoricalAverageCost(Vector historicalAverageCost) {
		this.historicalAverageCost = historicalAverageCost;
	}
	/**
	 * @return Returns the unitsAdjustmentOffline.
	 */
	public Vector getUnitsAdjustmentOffline() {
		return unitsAdjustmentOffline;
	}
	/**
	 * @param unitsAdjustmentOffline The unitsAdjustmentOffline to set.
	 */
	public void setUnitsAdjustmentOffline(Vector unitsAdjustementOffline) {
		this.unitsAdjustmentOffline = unitsAdjustementOffline;
	}
	/**
	 * @return Returns the unitsProduction.
	 */
	public Vector getUnitsProduction() {
		return unitsProduction;
	}
	/**
	 * @param unitsProduction The unitsProduction to set.
	 */
	public void setUnitsProduction(Vector unitsProduction) {
		this.unitsProduction = unitsProduction;
	}
	/**
	 * @return Returns the unitsReceived.
	 */
	public Vector getUnitsReceived() {
		return unitsReceived;
	}
	/**
	 * @param unitsReceived The unitsReceived to set.
	 */
	public void setUnitsReceived(Vector unitsReceived) {
		this.unitsReceived = unitsReceived;
	}
	/**
	 * @return Returns the unitsTransfered.
	 */
	public Vector getUnitsTransfered() {
		return unitsTransfered;
	}
	/**
	 * @param unitsTransfered The unitsTransfered to set.
	 */
	public void setUnitsTransfered(Vector unitsTransfered) {
		this.unitsTransfered = unitsTransfered;
	}
	/**
	 * @return Returns the unitsUsageGLPosted.
	 */
	public Vector getUnitsUsageGLPosted() {
		return unitsUsageGLPosted;
	}
	/**
	 * @param unitsUsageGLPosted The unitsUsageGLPosted to set.
	 */
	public void setUnitsUsageGLPosted(Vector unitsUsageGLPosted) {
		this.unitsUsageGLPosted = unitsUsageGLPosted;
	}
}
