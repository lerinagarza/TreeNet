/*
 * Created on September 15, 2009
 *
 */
package com.treetop.businessobjects;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author twalto
 * 
 * Manufacturing Order Information
 */

/* modified 2012/0801 WTH
 * modified 2102/1024 - Separated from Manufacturing Order 
 * 						Changed all fields to private from protected
 * 
 */
public class ManufacturingOrderDetail extends ManufacturingOrder {
	
	public enum YieldType {YIELD, RECOVERY, LBPERTON}
	
	private		TreeSet<String> orderNumbers			= new TreeSet<String>();
	
	private		BigDecimal  productionForecast			= BigDecimal.ZERO; // Budget or Forecast	
	private		BigDecimal  productionPlanned			= BigDecimal.ZERO; // Weekly Planned MO's	
	private		BigDecimal  productionPercentagePlanned = BigDecimal.ZERO; // Tie Planned to Actual
	
	private		BigDecimal	yieldActual					= BigDecimal.ZERO;
	private		BigDecimal	yieldStandard				= BigDecimal.ZERO;
	private		BigDecimal	recoveryActual				= BigDecimal.ZERO;
	private		BigDecimal	recoveryStandard			= BigDecimal.ZERO;
	private		BigDecimal  yieldSlurryActual			= BigDecimal.ZERO;
	private		BigDecimal  yieldSlurryStandard			= BigDecimal.ZERO;
	private		BigDecimal  yieldElimsActual			= BigDecimal.ZERO;
	private		BigDecimal  yieldElimsStandard			= BigDecimal.ZERO;
	
	
	private		YieldType	yieldType					= null;
	
	private		BigDecimal  usageRawFruitForecast		= BigDecimal.ZERO; // Budget or Forecast	
	private		BigDecimal  usageRawFruitPlanned		= BigDecimal.ZERO; // Weekly Planned MO's	
	private		BigDecimal  usageRawFruitStandard		= BigDecimal.ZERO; // Standard from MO's
	private		BigDecimal  usageRawFruitActual			= BigDecimal.ZERO; // Actual from MO's
	
	private		BigDecimal	usageActualYield			= BigDecimal.ZERO;
	private		BigDecimal	usageStandardYield			= BigDecimal.ZERO;
	
	private		BigDecimal	usageActual					= BigDecimal.ZERO;
	private		BigDecimal	usageActualLbAtStd			= BigDecimal.ZERO;
	private		BigDecimal	usageActualFsAtStd			= BigDecimal.ZERO;
	private 	BigDecimal	usageActualElims			= BigDecimal.ZERO;
	private		BigDecimal	usageActualSlurry			= BigDecimal.ZERO;
	private		BigDecimal	usageActualGross			= BigDecimal.ZERO;
	private		BigDecimal	usageActualWashdown			= BigDecimal.ZERO;
	private		BigDecimal	usageActualBlendIn			= BigDecimal.ZERO;
	
	
	private		BigDecimal	usageStandard				= BigDecimal.ZERO;
	private		BigDecimal	usageStandardLbAtStd		= BigDecimal.ZERO;
	private		BigDecimal	usageStandardFsAtStd		= BigDecimal.ZERO;
	private 	BigDecimal	usageStandardElims			= BigDecimal.ZERO;
	private		BigDecimal	usageStandardSlurry			= BigDecimal.ZERO;
	private		BigDecimal	usageStandardGross			= BigDecimal.ZERO;
	private		BigDecimal	usageStandardWashdown		= BigDecimal.ZERO;
	private		BigDecimal	usageStandardBlendIn		= BigDecimal.ZERO;
	
	private		BigDecimal	laborHoursActual			= BigDecimal.ZERO;
	private		BigDecimal	laborHoursStandard			= BigDecimal.ZERO;
	private		BigDecimal  laborEarned                 = BigDecimal.ZERO;
	private		BigDecimal  laborActual                 = BigDecimal.ZERO;
	private		BigDecimal  laborVariance				= BigDecimal.ZERO;
	private		BigDecimal  laborVarianceWithBenefits	= BigDecimal.ZERO;
	
	private		BigDecimal  unitsPerManHourStandard		= BigDecimal.ZERO;
	private		BigDecimal  unitsPerManHourActual		= BigDecimal.ZERO;
	
	private		BigDecimal	costVarianceUsage			= BigDecimal.ZERO;
	private		BigDecimal	costVarianceSubstitution	= BigDecimal.ZERO;
	private		BigDecimal	costVarianceTotal			= BigDecimal.ZERO;
	
	private		BigDecimal	costVarianceActualQty		= BigDecimal.ZERO;
	private		BigDecimal	costVarianceStandardQty		= BigDecimal.ZERO;
	private		BigDecimal	costVarianceStandardCost	= BigDecimal.ZERO;
	private		BigDecimal	costVarianceActualCost		= BigDecimal.ZERO;
	
	private		BigDecimal	costVarianceSupplies 		= BigDecimal.ZERO;
	private		BigDecimal	costVarianceSlurry	 		= BigDecimal.ZERO;
	private		BigDecimal	costVarianceRework	 		= BigDecimal.ZERO;
	
	private		BigDecimal	holdForNonConformance		= BigDecimal.ZERO;
	
	

	
	/**
	 *  // Constructor
	 */
	public ManufacturingOrderDetail() {
		super();

	}
	
	
	public void addValues(ManufacturingOrderDetail obj) throws Exception {

		this.setProduction(this.getProduction().add(obj.getProduction()));
		this.setProductionForecast(this.getProductionForecast().add(obj.getProductionForecast()));
		this.setProductionPlanned(this.getProductionPlanned().add(obj.getProductionPlanned()));
		this.setLaborHoursStandard(this.getLaborHoursStandard().add(obj.getLaborHoursStandard()));
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

	public BigDecimal getLaborEarned() {
		return laborEarned;
	}

	public void setLaborEarned(BigDecimal laborEarned) {
		this.laborEarned = laborEarned;
	}

	public BigDecimal getLaborActual() {
		return laborActual;
	}

	public void setLaborActual(BigDecimal laborActual) {
		this.laborActual = laborActual;
	}

	public BigDecimal getLaborVariance() {
		return laborVariance;
	}

	public void setLaborVariance(BigDecimal laborVariance) {
		this.laborVariance = laborVariance;
	}

	public BigDecimal getLaborHoursActual() {
		return laborHoursActual;
	}

	public void setLaborHoursActual(BigDecimal laborHoursActual) {
		this.laborHoursActual = laborHoursActual;
	}

	public BigDecimal getHoldForNonConformance() {
		return holdForNonConformance;
	}

	public void setHoldForNonConformance(BigDecimal holdForNonConformance) {
		this.holdForNonConformance = holdForNonConformance;
	}

	public BigDecimal getProductionForecast() {
		return productionForecast;
	}

	public void setProductionForecast(BigDecimal productionForecast) {
		this.productionForecast = productionForecast;
	}

	public BigDecimal getProductionPlanned() {
		return productionPlanned;
	}

	public void setProductionPlanned(BigDecimal productionPlanned) {
		this.productionPlanned = productionPlanned;
	}

	public BigDecimal getProductionPercentagePlanned() {
		return productionPercentagePlanned;
	}

	public void setProductionPercentagePlanned(
			BigDecimal productionPercentagePlanned) {
		this.productionPercentagePlanned = productionPercentagePlanned;
	}

	public BigDecimal getUsageRawFruitForecast() {
		return usageRawFruitForecast;
	}

	public void setUsageRawFruitForecast(BigDecimal usageRawFruitForecast) {
		this.usageRawFruitForecast = usageRawFruitForecast;
	}

	public BigDecimal getUsageRawFruitPlanned() {
		return usageRawFruitPlanned;
	}

	public void setUsageRawFruitPlanned(BigDecimal usageRawFruitPlanned) {
		this.usageRawFruitPlanned = usageRawFruitPlanned;
	}

	public BigDecimal getUsageActualYield() {
		return usageActualYield;
	}

	public void setUsageActualYield(BigDecimal usageActualYield) {
		this.usageActualYield = usageActualYield;
	}

	public BigDecimal getYieldActual() {
		return yieldActual;
	}

	public void setYieldActual(BigDecimal yieldActual) {
		this.yieldActual = yieldActual;
	}

	public BigDecimal getRecoveryActual() {
		return recoveryActual;
	}

	public void setRecoveryActual(BigDecimal recoveryActual) {
		this.recoveryActual = recoveryActual;
	}

	public YieldType getYieldType() {
		return yieldType;
	}

	public void setYieldType(YieldType yieldType) {
		this.yieldType = yieldType;
	}

	public BigDecimal getUsageRawFruitStandard() {
		return usageRawFruitStandard;
	}

	public void setUsageRawFruitStandard(BigDecimal usageRawFruitStandard) {
		this.usageRawFruitStandard = usageRawFruitStandard;
	}

	public BigDecimal getUsageRawFruitActual() {
		return usageRawFruitActual;
	}

	public void setUsageRawFruitActual(BigDecimal usageRawFruitActual) {
		this.usageRawFruitActual = usageRawFruitActual;
	}

	public BigDecimal getCostVarianceUsage() {
		return costVarianceUsage;
	}

	public void setCostVarianceUsage(BigDecimal costVarianceUsage) {
		this.costVarianceUsage = costVarianceUsage;
	}

	public BigDecimal getCostVarianceSubstitution() {
		return costVarianceSubstitution;
	}

	public void setCostVarianceSubstitution(BigDecimal costVarianceSubstitution) {
		this.costVarianceSubstitution = costVarianceSubstitution;
	}

	public BigDecimal getCostVarianceTotal() {
		return costVarianceTotal;
	}

	public void setCostVarianceTotal(BigDecimal costVarianceTotal) {
		this.costVarianceTotal = costVarianceTotal;
	}

	public BigDecimal getUsageStandard() {
		return usageStandard;
	}

	public void setUsageStandard(BigDecimal usageStandard) {
		this.usageStandard = usageStandard;
	}

	public BigDecimal getUsageStandardLbAtStd() {
		return usageStandardLbAtStd;
	}

	public void setUsageStandardLbAtStd(BigDecimal usageStandardLbAtStd) {
		this.usageStandardLbAtStd = usageStandardLbAtStd;
	}

	public BigDecimal getUsageStandardFsAtStd() {
		return usageStandardFsAtStd;
	}

	public void setUsageStandardFsAtStd(BigDecimal usageStandardFsAtStd) {
		this.usageStandardFsAtStd = usageStandardFsAtStd;
	}

	public BigDecimal getUsageActual() {
		return usageActual;
	}

	public void setUsageActual(BigDecimal usageActual) {
		this.usageActual = usageActual;
	}

	public BigDecimal getUsageActualLbAtStd() {
		return usageActualLbAtStd;
	}

	public void setUsageActualLbAtStd(BigDecimal usageActualLbAtStd) {
		this.usageActualLbAtStd = usageActualLbAtStd;
	}

	public BigDecimal getUsageActualFsAtStd() {
		return usageActualFsAtStd;
	}

	public void setUsageActualFsAtStd(BigDecimal usageActualFsAtStd) {
		this.usageActualFsAtStd = usageActualFsAtStd;
	}

	public BigDecimal getCostVarianceSupplies() {
		return costVarianceSupplies;
	}

	public void setCostVarianceSupplies(BigDecimal costVarianceSupplies) {
		this.costVarianceSupplies = costVarianceSupplies;
	}

	public BigDecimal getLaborVarianceWithBenefits() {
		return laborVarianceWithBenefits;
	}

	public void setLaborVarianceWithBenefits(BigDecimal laborVarianceWithBenefits) {
		this.laborVarianceWithBenefits = laborVarianceWithBenefits;
	}

	public BigDecimal getCostVarianceActualQty() {
		return costVarianceActualQty;
	}

	public void setCostVarianceActualQty(BigDecimal costVarianceActualQty) {
		this.costVarianceActualQty = costVarianceActualQty;
	}

	public BigDecimal getCostVarianceStandardQty() {
		return costVarianceStandardQty;
	}

	public void setCostVarianceStandardQty(BigDecimal costVarianceStandardQty) {
		this.costVarianceStandardQty = costVarianceStandardQty;
	}

	public BigDecimal getCostVarianceStandardCost() {
		return costVarianceStandardCost;
	}

	public void setCostVarianceStandardCost(BigDecimal costVarianceStandardCost) {
		this.costVarianceStandardCost = costVarianceStandardCost;
	}

	public BigDecimal getCostVarianceActualCost() {
		return costVarianceActualCost;
	}

	public void setCostVarianceActualCost(BigDecimal costVarianceActualCost) {
		this.costVarianceActualCost = costVarianceActualCost;
	}

	public BigDecimal getLaborHoursStandard() {
		return laborHoursStandard;
	}

	public void setLaborHoursStandard(BigDecimal laborHoursStandard) {
		this.laborHoursStandard = laborHoursStandard;
	}

	public TreeSet<String> getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(TreeSet<String> orderNumbers) {
		this.orderNumbers = orderNumbers;
	}


	public BigDecimal getUsageStandardYield() {
		return usageStandardYield;
	}


	public void setUsageStandardYield(BigDecimal usageStandardYield) {
		this.usageStandardYield = usageStandardYield;
	}


	public BigDecimal getYieldStandard() {
		return yieldStandard;
	}


	public void setYieldStandard(BigDecimal yieldStandard) {
		this.yieldStandard = yieldStandard;
	}


	public BigDecimal getRecoveryStandard() {
		return recoveryStandard;
	}


	public void setRecoveryStandard(BigDecimal recoveryStandard) {
		this.recoveryStandard = recoveryStandard;
	}


	public BigDecimal getCostVarianceRework() {
		return costVarianceRework;
	}


	public void setCostVarianceRework(BigDecimal costVarianceRework) {
		this.costVarianceRework = costVarianceRework;
	}


	public BigDecimal getUnitsPerManHourStandard() {
		return unitsPerManHourStandard;
	}


	public void setUnitsPerManHourStandard(BigDecimal unitsPerManHourStandard) {
		this.unitsPerManHourStandard = unitsPerManHourStandard;
	}


	public BigDecimal getUnitsPerManHourActual() {
		return unitsPerManHourActual;
	}


	public void setUnitsPerManHourActual(BigDecimal unitsPerManHourActual) {
		this.unitsPerManHourActual = unitsPerManHourActual;
	}


	public BigDecimal getUsageActualElims() {
		return usageActualElims;
	}


	public void setUsageActualElims(BigDecimal usageActualElims) {
		this.usageActualElims = usageActualElims;
	}


	public BigDecimal getUsageActualSlurry() {
		return usageActualSlurry;
	}


	public void setUsageActualSlurry(BigDecimal usageActualSlurry) {
		this.usageActualSlurry = usageActualSlurry;
	}


	public BigDecimal getUsageStandardElims() {
		return usageStandardElims;
	}


	public void setUsageStandardElims(BigDecimal usageStandardElims) {
		this.usageStandardElims = usageStandardElims;
	}


	public BigDecimal getUsageStandardSlurry() {
		return usageStandardSlurry;
	}


	public void setUsageStandardSlurry(BigDecimal usageStandardSlurry) {
		this.usageStandardSlurry = usageStandardSlurry;
	}


	public BigDecimal getYieldSlurryActual() {
		return yieldSlurryActual;
	}


	public void setYieldSlurryActual(BigDecimal yieldSlurryActual) {
		this.yieldSlurryActual = yieldSlurryActual;
	}


	public BigDecimal getYieldSlurryStandard() {
		return yieldSlurryStandard;
	}


	public void setYieldSlurryStandard(BigDecimal yieldSlurryStandard) {
		this.yieldSlurryStandard = yieldSlurryStandard;
	}


	public BigDecimal getYieldElimsActual() {
		return yieldElimsActual;
	}


	public void setYieldElimsActual(BigDecimal yieldElimsActual) {
		this.yieldElimsActual = yieldElimsActual;
	}


	public BigDecimal getYieldElimsStandard() {
		return yieldElimsStandard;
	}


	public void setYieldElimsStandard(BigDecimal yieldElimsStandard) {
		this.yieldElimsStandard = yieldElimsStandard;
	}


	public BigDecimal getUsageActualGross() {
		return usageActualGross;
	}


	public void setUsageActualGross(BigDecimal usageActualGross) {
		this.usageActualGross = usageActualGross;
	}


	public BigDecimal getUsageStandardGross() {
		return usageStandardGross;
	}


	public void setUsageStandardGross(BigDecimal usageStandardGross) {
		this.usageStandardGross = usageStandardGross;
	}


	public BigDecimal getUsageActualWashdown() {
		return usageActualWashdown;
	}


	public void setUsageActualWashdown(BigDecimal usageActualWashdown) {
		this.usageActualWashdown = usageActualWashdown;
	}


	public BigDecimal getUsageStandardWashdown() {
		return usageStandardWashdown;
	}


	public void setUsageStandardWashdown(BigDecimal usageStandardWashdown) {
		this.usageStandardWashdown = usageStandardWashdown;
	}


	public BigDecimal getUsageActualBlendIn() {
		return usageActualBlendIn;
	}


	public void setUsageActualBlendIn(BigDecimal usageActualBlendIn) {
		this.usageActualBlendIn = usageActualBlendIn;
	}


	public BigDecimal getUsageStandardBlendIn() {
		return usageStandardBlendIn;
	}


	public void setUsageStandardBlendIn(BigDecimal usageStandardBlendIn) {
		this.usageStandardBlendIn = usageStandardBlendIn;
	}


	public BigDecimal getCostVarianceSlurry() {
		return costVarianceSlurry;
	}


	public void setCostVarianceSlurry(BigDecimal costVarianceSlurry) {
		this.costVarianceSlurry = costVarianceSlurry;
	}


}
