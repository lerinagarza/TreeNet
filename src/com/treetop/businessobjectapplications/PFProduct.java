/*
 * Created on August 17, 2009
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Item;
import com.treetop.businessobjects.Warehouse;

/**
 * @author twalto
 *
 * Store Plant Floor Product Related Information --
 *     
 */
public class PFProduct extends Item{
	
	protected   String		productType			= "";
	protected	String		ratePerMinute		= "0";
	protected	String		minimumWeight		= "0";
	protected	String		maximumWeight		= "0";
	protected	String		optionalField1		= "";
	protected	String		optionalField2		= "";
	protected	String		optionalField3		= "";
	protected	String		optionalField4		= "";
	protected	String		optionalField5		= "";
	protected	String		linkToSpec			= "";
	protected	String		targetDuration		= "0";
	protected	String		itemLevel2			= "";
	protected	String		itemLevel3			= "";
	protected	String		crewSizeLevel1		= "0";
	protected	String		crewSizeLevel2		= "0";
	protected	String		crewSizeLevel3		= "0";
	protected	String		crewSize			= "0";
	protected	String		crewSizeOverride	= "0";
	protected	String		costingLevel1		= "0";
	protected	String		costingLevel2		= "0";
	protected	String		costingLevel3		= "0";
	protected	String		costingValue		= "0";
	protected	String		costingOverride		= "0";
	protected	String		materialCost		= "0";
	protected	String 		materialCostOverride = "0";
	protected	String		costingLevel1Date	= "0";
	protected	String		costingLevel2Date	= "0";
	protected	String		costingLevel3Date	= "0";
	protected	String		quantityLevel2		= "0";
	protected	String		quantityLevel3		= "0";
	protected	String 		thruputInput		= "0";
	protected	String		thruputBudget		= "0";
	protected	String		thruputAdditional	= "0";
	protected	String		brixInput			= "0";
	protected	String 		brixStandard		= "0";
	protected	String		brixAdditional		= "0";
	protected	String		brixFactor			= "0";
	protected	String		wastePerBarrel		= "0";
	protected	String		wastePerBin			= "0";
	protected	String		cellGroup			= "";
	protected	String		productParent		= "";
	protected	String		groupSock			= "";
	protected	String		groupProcess		= "";
	
	protected	Warehouse	whseInfo			= new Warehouse();
		
	/**
	 *  // Constructor
	 */
	public PFProduct() {
		super();

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
	public String getBrixAdditional() {
		return brixAdditional;
	}
	public void setBrixAdditional(String brixAdditional) {
		this.brixAdditional = brixAdditional;
	}
	public String getBrixInput() {
		return brixInput;
	}
	public void setBrixInput(String brixInput) {
		this.brixInput = brixInput;
	}
	public String getBrixStandard() {
		return brixStandard;
	}
	public void setBrixStandard(String brixStandard) {
		this.brixStandard = brixStandard;
	}
	public String getCellGroup() {
		return cellGroup;
	}
	public void setCellGroup(String cellGroup) {
		this.cellGroup = cellGroup;
	}
	public String getCostingLevel1() {
		return costingLevel1;
	}
	public void setCostingLevel1(String costingLevel1) {
		this.costingLevel1 = costingLevel1;
	}
	public String getCostingLevel1Date() {
		return costingLevel1Date;
	}
	public void setCostingLevel1Date(String costingLevel1Date) {
		this.costingLevel1Date = costingLevel1Date;
	}
	public String getCostingLevel2() {
		return costingLevel2;
	}
	public void setCostingLevel2(String costingLevel2) {
		this.costingLevel2 = costingLevel2;
	}
	public String getCostingLevel2Date() {
		return costingLevel2Date;
	}
	public void setCostingLevel2Date(String costingLevel2Date) {
		this.costingLevel2Date = costingLevel2Date;
	}
	public String getCostingLevel3() {
		return costingLevel3;
	}
	public void setCostingLevel3(String costingLevel3) {
		this.costingLevel3 = costingLevel3;
	}
	public String getCostingLevel3Date() {
		return costingLevel3Date;
	}
	public void setCostingLevel3Date(String costingLevel3Date) {
		this.costingLevel3Date = costingLevel3Date;
	}
	public String getCrewSize() {
		return crewSize;
	}
	public void setCrewSize(String crewSize) {
		this.crewSize = crewSize;
	}
	public String getCrewSizeLevel1() {
		return crewSizeLevel1;
	}
	public void setCrewSizeLevel1(String crewSizeLevel1) {
		this.crewSizeLevel1 = crewSizeLevel1;
	}
	public String getCrewSizeLevel2() {
		return crewSizeLevel2;
	}
	public void setCrewSizeLevel2(String crewSizeLevel2) {
		this.crewSizeLevel2 = crewSizeLevel2;
	}
	public String getCrewSizeLevel3() {
		return crewSizeLevel3;
	}
	public void setCrewSizeLevel3(String crewSizeLevel3) {
		this.crewSizeLevel3 = crewSizeLevel3;
	}
	public String getItemLevel2() {
		return itemLevel2;
	}
	public void setItemLevel2(String itemLevel2) {
		this.itemLevel2 = itemLevel2;
	}
	public String getItemLevel3() {
		return itemLevel3;
	}
	public void setItemLevel3(String itemLevel3) {
		this.itemLevel3 = itemLevel3;
	}
	public String getLinkToSpec() {
		return linkToSpec;
	}
	public void setLinkToSpec(String linkToSpec) {
		this.linkToSpec = linkToSpec;
	}
	public String getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(String materialCost) {
		this.materialCost = materialCost;
	}
	public String getMaximumWeight() {
		return maximumWeight;
	}
	public void setMaximumWeight(String maximumWeight) {
		this.maximumWeight = maximumWeight;
	}
	public String getMinimumWeight() {
		return minimumWeight;
	}
	public void setMinimumWeight(String minimumWeight) {
		this.minimumWeight = minimumWeight;
	}
	public String getOptionalField1() {
		return optionalField1;
	}
	public void setOptionalField1(String optionalField1) {
		this.optionalField1 = optionalField1;
	}
	public String getOptionalField2() {
		return optionalField2;
	}
	public void setOptionalField2(String optionalField2) {
		this.optionalField2 = optionalField2;
	}
	public String getOptionalField3() {
		return optionalField3;
	}
	public void setOptionalField3(String optionalField3) {
		this.optionalField3 = optionalField3;
	}
	public String getOptionalField4() {
		return optionalField4;
	}
	public void setOptionalField4(String optionalField4) {
		this.optionalField4 = optionalField4;
	}
	public String getOptionalField5() {
		return optionalField5;
	}
	public void setOptionalField5(String optionalField5) {
		this.optionalField5 = optionalField5;
	}
	public String getProductParent() {
		return productParent;
	}
	public void setProductParent(String productParent) {
		this.productParent = productParent;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRatePerMinute() {
		return ratePerMinute;
	}
	public void setRatePerMinute(String ratePerMinute) {
		this.ratePerMinute = ratePerMinute;
	}
	public String getTargetDuration() {
		return targetDuration;
	}
	public void setTargetDuration(String targetDuration) {
		this.targetDuration = targetDuration;
	}
	public String getThruputAdditional() {
		return thruputAdditional;
	}
	public void setThruputAdditional(String thruputAdditional) {
		this.thruputAdditional = thruputAdditional;
	}
	public String getThruputBudget() {
		return thruputBudget;
	}
	public void setThruputBudget(String thruputBudget) {
		this.thruputBudget = thruputBudget;
	}
	public String getThruputInput() {
		return thruputInput;
	}
	public void setThruputInput(String thruputInput) {
		this.thruputInput = thruputInput;
	}
	public String getWastePerBarrel() {
		return wastePerBarrel;
	}
	public void setWastePerBarrel(String wastePerBarrel) {
		this.wastePerBarrel = wastePerBarrel;
	}
	public String getWastePerBin() {
		return wastePerBin;
	}
	public void setWastePerBin(String wastePerBin) {
		this.wastePerBin = wastePerBin;
	}
	public Warehouse getWhseInfo() {
		return whseInfo;
	}
	public void setWhseInfo(Warehouse whseInfo) {
		this.whseInfo = whseInfo;
	}
	public String getQuantityLevel2() {
		return quantityLevel2;
	}
	public void setQuantityLevel2(String quantityLevel2) {
		this.quantityLevel2 = quantityLevel2;
	}
	public String getQuantityLevel3() {
		return quantityLevel3;
	}
	public void setQuantityLevel3(String quantityLevel3) {
		this.quantityLevel3 = quantityLevel3;
	}
	public String getCrewSizeOverride() {
		return crewSizeOverride;
	}
	public void setCrewSizeOverride(String crewSizeOverride) {
		this.crewSizeOverride = crewSizeOverride;
	}
	public String getMaterialCostOverride() {
		return materialCostOverride;
	}
	public void setMaterialCostOverride(String materialCostOverride) {
		this.materialCostOverride = materialCostOverride;
	}
	public String getGroupProcess() {
		return groupProcess;
	}
	public void setGroupProcess(String groupProcess) {
		this.groupProcess = groupProcess;
	}
	public String getGroupSock() {
		return groupSock;
	}
	public void setGroupSock(String groupSock) {
		this.groupSock = groupSock;
	}
	public String getBrixFactor() {
		return brixFactor;
	}
	public void setBrixFactor(String brixFactor) {
		this.brixFactor = brixFactor;
	}
	public String getCostingOverride() {
		return costingOverride;
	}
	public void setCostingOverride(String costingOverride) {
		this.costingOverride = costingOverride;
	}
	public String getCostingValue() {
		return costingValue;
	}
	public void setCostingValue(String costingValue) {
		this.costingValue = costingValue;
	}
}
