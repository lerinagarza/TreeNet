/**
 * @author Tom Haile
 * August 1, 2012
 * Use in conjunction mainly with ServiceOperationsReporting
 */
package com.treetop.businessobjectapplications;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.Vector;

import com.treetop.businessobjects.AccountData;
import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.ManufacturingFinance;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.businessobjects.Safety;
import com.treetop.businessobjects.Warehouse;

public class BeanOperationsReporting {
	
	private Warehouse warehouse = new Warehouse();
	
	private LinkedHashMap<String, ManufacturingOrderDetail> packagingMOs  = new LinkedHashMap<String, ManufacturingOrderDetail>();
	private LinkedHashMap<String, ManufacturingOrderDetail> processingMOs = new LinkedHashMap<String, ManufacturingOrderDetail>();
	private LinkedHashMap<String, ManufacturingOrderDetail> processingMOsSummary = new LinkedHashMap<String, ManufacturingOrderDetail>();
	private LinkedHashMap<String, ManufacturingOrderDetail> frozenCherryMOs = new LinkedHashMap<String, ManufacturingOrderDetail>();
	private LinkedHashMap<String, ManufacturingOrderDetail> blendingMOs = new LinkedHashMap<String, ManufacturingOrderDetail>();
	private LinkedHashMap<String, ManufacturingOrderDetail> rawFruitMOs = new LinkedHashMap<String, ManufacturingOrderDetail>();
	private LinkedHashMap<String, ManufacturingOrderDetail> processingLabor = new LinkedHashMap<String, ManufacturingOrderDetail>();
	
	private LinkedHashMap<String, ManufacturingFinance> maintenance = new LinkedHashMap<String, ManufacturingFinance>();
	private LinkedHashMap<String, ManufacturingFinance> quality = new LinkedHashMap<String, ManufacturingFinance>();
	private LinkedHashMap<String, ManufacturingFinance> warehousing = new LinkedHashMap<String, ManufacturingFinance>();
	private LinkedHashMap<String, ManufacturingFinance> utilities = new LinkedHashMap<String, ManufacturingFinance>();
	
	private Safety safetyMetrics = new Safety();
	
	private LinkedHashMap<String, AccountData> plantJournals = new LinkedHashMap<String, AccountData>();
	private LinkedHashMap<String, AccountData> inventoryAdjustments = new LinkedHashMap<String, AccountData>();
	private LinkedHashMap<String, ManufacturingFinance> inventoryAdjustmentsEarnings = new LinkedHashMap<String, ManufacturingFinance>();
	
	private LinkedHashMap<String, SortedMap<String, BigDecimal>> plantSummaries = new LinkedHashMap<String, SortedMap<String, BigDecimal>>();
	
	private Hashtable<String, Vector<KeyValue>> weeklyComments = new Hashtable<String, Vector<KeyValue>>();
	
	public LinkedHashMap<String, ManufacturingOrderDetail> getPackagingMOs() {
		return packagingMOs;
	}
	public void setPackagingMOs(LinkedHashMap<String, ManufacturingOrderDetail> packagingMOs) {
		this.packagingMOs = packagingMOs;
	}
	public LinkedHashMap<String, ManufacturingOrderDetail> getProcessingMOs() {
		return processingMOs;
	}
	public void setProcessingMOs(LinkedHashMap<String, ManufacturingOrderDetail> processingMOs) {
		this.processingMOs = processingMOs;
	}
	public LinkedHashMap<String, ManufacturingOrderDetail> getFrozenCherryMOs() {
		return frozenCherryMOs;
	}
	public void setFrozenCherryMOs(LinkedHashMap<String, ManufacturingOrderDetail> frozenCherryMOs) {
		this.frozenCherryMOs = frozenCherryMOs;
	}
	public LinkedHashMap<String, ManufacturingOrderDetail> getBlendingMOs() {
		return blendingMOs;
	}
	public void setBlendingMOs(LinkedHashMap<String, ManufacturingOrderDetail> blendingMOs) {
		this.blendingMOs = blendingMOs;
	}
	public LinkedHashMap<String, ManufacturingOrderDetail> getRawFruitMOs() {
		return rawFruitMOs;
	}
	public void setRawFruitMOs(LinkedHashMap<String, ManufacturingOrderDetail> rawFruitMOs) {
		this.rawFruitMOs = rawFruitMOs;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public LinkedHashMap<String, ManufacturingFinance> getMaintenance() {
		return maintenance;
	}
	public void setMaintenance(LinkedHashMap<String, ManufacturingFinance> maintenance) {
		this.maintenance = maintenance;
	}
	public LinkedHashMap<String, ManufacturingFinance> getQuality() {
		return quality;
	}
	public void setQuality(LinkedHashMap<String, ManufacturingFinance> quality) {
		this.quality = quality;
	}
	public LinkedHashMap<String, ManufacturingFinance> getWarehousing() {
		return warehousing;
	}
	public void setWarehousing(LinkedHashMap<String, ManufacturingFinance> warehousing) {
		this.warehousing = warehousing;
	}
	public LinkedHashMap<String, ManufacturingFinance> getUtilities() {
		return utilities;
	}
	public void setUtilities(LinkedHashMap<String, ManufacturingFinance> utilities) {
		this.utilities = utilities;
	}
	public Safety getSafetyMetrics() {
		return safetyMetrics;
	}
	public void setSafetyMetrics(Safety safetyMetrics) {
		this.safetyMetrics = safetyMetrics;
	}
	public LinkedHashMap<String, AccountData> getPlantJournals() {
		return plantJournals;
	}
	public void setPlantJournals(LinkedHashMap<String, AccountData> plantJournals) {
		this.plantJournals = plantJournals;
	}
	public LinkedHashMap<String, AccountData> getInventoryAdjustments() {
		return inventoryAdjustments;
	}
	public void setInventoryAdjustments(LinkedHashMap<String, AccountData> inventoryAdjustments) {
		this.inventoryAdjustments = inventoryAdjustments;
	}
	public LinkedHashMap<String, ManufacturingOrderDetail> getProcessingLabor() {
		return processingLabor;
	}
	public void setProcessingLabor(LinkedHashMap<String, ManufacturingOrderDetail> processingLabor) {
		this.processingLabor = processingLabor;
	}
	public LinkedHashMap<String, ManufacturingOrderDetail> getProcessingMOsSummary() {
		return processingMOsSummary;
	}
	public void setProcessingMOsSummary(LinkedHashMap<String, ManufacturingOrderDetail> processingMOsSummary) {
		this.processingMOsSummary = processingMOsSummary;
	}
	public LinkedHashMap<String, ManufacturingFinance> getInventoryAdjustmentsEarnings() {
		return inventoryAdjustmentsEarnings;
	}
	public void setInventoryAdjustmentsEarnings(LinkedHashMap<String, ManufacturingFinance> inventoryAdjustmentsEarnings) {
		this.inventoryAdjustmentsEarnings = inventoryAdjustmentsEarnings;
	}
	public LinkedHashMap<String, SortedMap<String, BigDecimal>> getPlantSummaries() {
		return plantSummaries;
	}
	public void setPlantSummaries(LinkedHashMap<String, SortedMap<String, BigDecimal>> plantSummaries) {
		this.plantSummaries = plantSummaries;
	}
	public Hashtable<String, Vector<KeyValue>> getWeeklyComments() {
		return weeklyComments;
	}
	public void setWeeklyComments(Hashtable<String, Vector<KeyValue>> weeklyComments) {
		this.weeklyComments = weeklyComments;
	}
}