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
 * Store Plant Floor Product Production Schedule Related Information --
 *     
 */
public class PFSchedule extends PFProduct{
	
	protected   String		manufacturingOrder		= "";
	protected	String		salesOrder				= "";
	protected	String		batchNumber				= "";
	protected	String		planAssetName			= "";
	protected	String		planSequenceNumber		= "0";
	protected	String		planStartDate			= "";
	protected	String		planRatePerMin			= "0";
	protected	String		planQuantity			= "0";
	protected	String		customerReference		= "";
	protected	String		specialInstructions		= "";
	protected	String		runStatus				= "";
	protected	String		optionalField1			= "";
	protected	String		optionalField2			= "";
	protected	String		optionalField3			= "";
	protected	String		optionalField4			= "";
	protected	String		optionalField5			= "";
	protected	String		deadlineDate			= "";
	protected	String		workOrderID				= "";
	protected	String		planLocationID			= "";
	protected	String		planCycles				= "0";
	protected	String		activityType			= "";
	protected	String		targetDuration			= "0";
		
	/**
	 *  // Constructor
	 */
	public PFSchedule() {
		super();

	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getManufacturingOrder() {
		return manufacturingOrder;
	}

	public void setManufacturingOrder(String manufacturingOrder) {
		this.manufacturingOrder = manufacturingOrder;
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

	public String getPlanAssetName() {
		return planAssetName;
	}

	public void setPlanAssetName(String planAssetName) {
		this.planAssetName = planAssetName;
	}

	public String getPlanCycles() {
		return planCycles;
	}

	public void setPlanCycles(String planCycles) {
		this.planCycles = planCycles;
	}

	public String getPlanLocationID() {
		return planLocationID;
	}

	public void setPlanLocationID(String planLocationID) {
		this.planLocationID = planLocationID;
	}

	public String getPlanQuantity() {
		return planQuantity;
	}

	public void setPlanQuantity(String planQuantity) {
		this.planQuantity = planQuantity;
	}

	public String getPlanRatePerMin() {
		return planRatePerMin;
	}

	public void setPlanRatePerMin(String planRatePerMin) {
		this.planRatePerMin = planRatePerMin;
	}

	public String getPlanSequenceNumber() {
		return planSequenceNumber;
	}

	public void setPlanSequenceNumber(String planSequenceNumber) {
		this.planSequenceNumber = planSequenceNumber;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getTargetDuration() {
		return targetDuration;
	}

	public void setTargetDuration(String targetDuration) {
		this.targetDuration = targetDuration;
	}

	public String getWorkOrderID() {
		return workOrderID;
	}

	public void setWorkOrderID(String workOrderID) {
		this.workOrderID = workOrderID;
	}

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}
}
