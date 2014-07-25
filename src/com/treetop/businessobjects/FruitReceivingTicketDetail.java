/*
 * Created on Jan 30, 2006
 *
 * Grower Fruit Receiving Transaction Information.
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Grower Fruit Receiving Transaction Information.
 */
public class FruitReceivingTicketDetail 
		extends FruitReceivingTicketTrans{
	// a presentation of the Grower Master file GRPDTIKM
	
	String	paymentType;
	String	growerPackerNumber;
	String	warehouse;
	String	location;
	String	accountingYear;
	String	accountingPeriod;
	String	payBinAllowance;
	String	growerWhseTicketNumber;
	String	fruitCommDeductFlag;
	String	fruitGrade;
	String	carrierNumber;
	String	hauling100wtRate;
	String	fuelSurchargeAddOnPct;
	String	haulerBillOfLading;
	String	payForFruit;
	String	payTheHauler;
	String	postTheInventory;
	String	grossWeight;
	String	truckTareWeight;
	String	freightWeight;
	String	lbsOfFruitPerBin;
	String	deferredPaymantDate;
	String	fruitReceivingDate;
	String	fruitPriceDate;
	String	inventoryPostedFlag;
	String	fruitPaidFlag;
	String	haulerPaidFlag;
	String	lastChangeDate;
	String	lastChangeTime;
	String	lastChangeUser;
	String	lastChangeWorkstation;
	String	paymentInProcess;
	String	includeTransactionInReporting;
	String	hasGrdRptBeenManuallyOverridding;
	String	estimateTicket;
	String	receiptAgainstEstimateTicket;
	String	organicFruit;
	String	inventoryWeight;
	String	sellerCullPercentage;
	String	sellerCullWeight;
	String	treeTopCullPercentage;
	String	treeTopCullWeight;
	String	paymentWeight;
	String	cropYearPayment;
	String	cropYearInventory;
	String	currentInventoryRecord;
	String	extraField1;
	String	extraField2;
	String	extraField3;

	
	/**
	 * 
	 *
	 */
	public FruitReceivingTicketDetail(){
		super();
	}
	
	
	/**
	 * @return Returns the accountingPeriod.
	 */
	public String getAccountingPeriod() {
		return accountingPeriod;
	}
	/**
	 * @param accountingPeriod The accountingPeriod to set.
	 */
	public void setAccountingPeriod(String accountingPeriod) {
		this.accountingPeriod = accountingPeriod;
	}
	/**
	 * @return Returns the accountingYear.
	 */
	public String getAccountingYear() {
		return accountingYear;
	}
	/**
	 * @param accountingYear The accountingYear to set.
	 */
	public void setAccountingYear(String accountingYear) {
		this.accountingYear = accountingYear;
	}
	/**
	 * @return Returns the carrierNumber.
	 */
	public String getCarrierNumber() {
		return carrierNumber;
	}
	/**
	 * @param carrierNumber The carrierNumber to set.
	 */
	public void setCarrierNumber(String carrierNumber) {
		this.carrierNumber = carrierNumber;
	}
	/**
	 * @return Returns the cropYearInventory.
	 */
	public String getCropYearInventory() {
		return cropYearInventory;
	}
	/**
	 * @param cropYearInventory The cropYearInventory to set.
	 */
	public void setCropYearInventory(String cropYearInventory) {
		this.cropYearInventory = cropYearInventory;
	}
	/**
	 * @return Returns the cropYearPayment.
	 */
	public String getCropYearPayment() {
		return cropYearPayment;
	}
	/**
	 * @param cropYearPayment The cropYearPayment to set.
	 */
	public void setCropYearPayment(String cropYearPayment) {
		this.cropYearPayment = cropYearPayment;
	}
	/**
	 * @return Returns the currentInventoryRecord.
	 */
	public String getCurrentInventoryRecord() {
		return currentInventoryRecord;
	}
	/**
	 * @param currentInventoryRecord The currentInventoryRecord to set.
	 */
	public void setCurrentInventoryRecord(String currentInventoryRecord) {
		this.currentInventoryRecord = currentInventoryRecord;
	}
	/**
	 * @return Returns the deferredPaymantDate.
	 */
	public String getDeferredPaymantDate() {
		return deferredPaymantDate;
	}
	/**
	 * @param deferredPaymantDate The deferredPaymantDate to set.
	 */
	public void setDeferredPaymantDate(String deferredPaymantDate) {
		this.deferredPaymantDate = deferredPaymantDate;
	}
	/**
	 * @return Returns the estimateTicket.
	 */
	public String getEstimateTicket() {
		return estimateTicket;
	}
	/**
	 * @param estimateTicket The estimateTicket to set.
	 */
	public void setEstimateTicket(String estimateTicket) {
		this.estimateTicket = estimateTicket;
	}
	/**
	 * @return Returns the extraField1.
	 */
	public String getExtraField1() {
		return extraField1;
	}
	/**
	 * @param extraField1 The extraField1 to set.
	 */
	public void setExtraField1(String extraField1) {
		this.extraField1 = extraField1;
	}
	/**
	 * @return Returns the extraField2.
	 */
	public String getExtraField2() {
		return extraField2;
	}
	/**
	 * @param extraField2 The extraField2 to set.
	 */
	public void setExtraField2(String extraField2) {
		this.extraField2 = extraField2;
	}
	/**
	 * @return Returns the extraField3.
	 */
	public String getExtraField3() {
		return extraField3;
	}
	/**
	 * @param extraField3 The extraField3 to set.
	 */
	public void setExtraField3(String extraField3) {
		this.extraField3 = extraField3;
	}
	/**
	 * @return Returns the freightWeight.
	 */
	public String getFreightWeight() {
		return freightWeight;
	}
	/**
	 * @param freightWeight The freightWeight to set.
	 */
	public void setFreightWeight(String freightWeight) {
		this.freightWeight = freightWeight;
	}
	/**
	 * @return Returns the fruitCommDeductFlag.
	 */
	public String getFruitCommDeductFlag() {
		return fruitCommDeductFlag;
	}
	/**
	 * @param fruitCommDeductFlag The fruitCommDeductFlag to set.
	 */
	public void setFruitCommDeductFlag(String fruitCommDeductFlag) {
		this.fruitCommDeductFlag = fruitCommDeductFlag;
	}
	/**
	 * @return Returns the fruitGrade.
	 */
	public String getFruitGrade() {
		return fruitGrade;
	}
	/**
	 * @param fruitGrade The fruitGrade to set.
	 */
	public void setFruitGrade(String fruitGrade) {
		this.fruitGrade = fruitGrade;
	}
	/**
	 * @return Returns the fruitPaidFlag.
	 */
	public String getFruitPaidFlag() {
		return fruitPaidFlag;
	}
	/**
	 * @param fruitPaidFlag The fruitPaidFlag to set.
	 */
	public void setFruitPaidFlag(String fruitPaidFlag) {
		this.fruitPaidFlag = fruitPaidFlag;
	}
	/**
	 * @return Returns the fruitPriceDate.
	 */
	public String getFruitPriceDate() {
		return fruitPriceDate;
	}
	/**
	 * @param fruitPriceDate The fruitPriceDate to set.
	 */
	public void setFruitPriceDate(String fruitPriceDate) {
		this.fruitPriceDate = fruitPriceDate;
	}
	/**
	 * @return Returns the fruitReceivingDate.
	 */
	public String getFruitReceivingDate() {
		return fruitReceivingDate;
	}
	/**
	 * @param fruitReceivingDate The fruitReceivingDate to set.
	 */
	public void setFruitReceivingDate(String fruitReceivingDate) {
		this.fruitReceivingDate = fruitReceivingDate;
	}
	/**
	 * @return Returns the fuelSurchargeAddOnPct.
	 */
	public String getFuelSurchargeAddOnPct() {
		return fuelSurchargeAddOnPct;
	}
	/**
	 * @param fuelSurchargeAddOnPct The fuelSurchargeAddOnPct to set.
	 */
	public void setFuelSurchargeAddOnPct(String fuelSurchargeAddOnPct) {
		this.fuelSurchargeAddOnPct = fuelSurchargeAddOnPct;
	}
	/**
	 * @return Returns the grossWeight.
	 */
	public String getGrossWeight() {
		return grossWeight;
	}
	/**
	 * @param grossWeight The grossWeight to set.
	 */
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * @return Returns the growerPackerNumber.
	 */
	public String getGrowerPackerNumber() {
		return growerPackerNumber;
	}
	/**
	 * @param growerPackerNumber The growerPackerNumber to set.
	 */
	public void setGrowerPackerNumber(String growerPackerNumber) {
		this.growerPackerNumber = growerPackerNumber;
	}
	/**
	 * @return Returns the growerWhseTicketNumber.
	 */
	public String getGrowerWhseTicketNumber() {
		return growerWhseTicketNumber;
	}
	/**
	 * @param growerWhseTicketNumber The growerWhseTicketNumber to set.
	 */
	public void setGrowerWhseTicketNumber(String growerWhseTicketNumber) {
		this.growerWhseTicketNumber = growerWhseTicketNumber;
	}
	/**
	 * @return Returns the hasGrdRptBeenManuallyOverridding.
	 */
	public String getHasGrdRptBeenManuallyOverridding() {
		return hasGrdRptBeenManuallyOverridding;
	}
	/**
	 * @param hasGrdRptBeenManuallyOverridding The hasGrdRptBeenManuallyOverridding to set.
	 */
	public void setHasGrdRptBeenManuallyOverridding(
			String hasGrdRptBeenManuallyOverridding) {
		this.hasGrdRptBeenManuallyOverridding = hasGrdRptBeenManuallyOverridding;
	}
	/**
	 * @return Returns the haulerBillOfLading.
	 */
	public String getHaulerBillOfLading() {
		return haulerBillOfLading;
	}
	/**
	 * @param haulerBillOfLading The haulerBillOfLading to set.
	 */
	public void setHaulerBillOfLading(String haulerBillOfLading) {
		this.haulerBillOfLading = haulerBillOfLading;
	}
	/**
	 * @return Returns the haulerPaidFlag.
	 */
	public String getHaulerPaidFlag() {
		return haulerPaidFlag;
	}
	/**
	 * @param haulerPaidFlag The haulerPaidFlag to set.
	 */
	public void setHaulerPaidFlag(String haulerPaidFlag) {
		this.haulerPaidFlag = haulerPaidFlag;
	}
	/**
	 * @return Returns the hauling100wtRate.
	 */
	public String getHauling100wtRate() {
		return hauling100wtRate;
	}
	/**
	 * @param hauling100wtRate The hauling100wtRate to set.
	 */
	public void setHauling100wtRate(String hauling100wtRate) {
		this.hauling100wtRate = hauling100wtRate;
	}
	/**
	 * @return Returns the includeTransactionInReporting.
	 */
	public String getIncludeTransactionInReporting() {
		return includeTransactionInReporting;
	}
	/**
	 * @param includeTransactionInReporting The includeTransactionInReporting to set.
	 */
	public void setIncludeTransactionInReporting(
			String includeTransactionInReporting) {
		this.includeTransactionInReporting = includeTransactionInReporting;
	}
	/**
	 * @return Returns the inventoryPostedFlag.
	 */
	public String getInventoryPostedFlag() {
		return inventoryPostedFlag;
	}
	/**
	 * @param inventoryPostedFlag The inventoryPostedFlag to set.
	 */
	public void setInventoryPostedFlag(String inventoryPostedFlag) {
		this.inventoryPostedFlag = inventoryPostedFlag;
	}
	/**
	 * @return Returns the inventoryWeight.
	 */
	public String getInventoryWeight() {
		return inventoryWeight;
	}
	/**
	 * @param inventoryWeight The inventoryWeight to set.
	 */
	public void setInventoryWeight(String inventoryWeight) {
		this.inventoryWeight = inventoryWeight;
	}
	/**
	 * @return Returns the lastChangeDate.
	 */
	public String getLastChangeDate() {
		return lastChangeDate;
	}
	/**
	 * @param lastChangeDate The lastChangeDate to set.
	 */
	public void setLastChangeDate(String lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}
	/**
	 * @return Returns the lastChangeTime.
	 */
	public String getLastChangeTime() {
		return lastChangeTime;
	}
	/**
	 * @param lastChangeTime The lastChangeTime to set.
	 */
	public void setLastChangeTime(String lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
	/**
	 * @return Returns the lastChangeUser.
	 */
	public String getLastChangeUser() {
		return lastChangeUser;
	}
	/**
	 * @param lastChangeUser The lastChangeUser to set.
	 */
	public void setLastChangeUser(String lastChangeUser) {
		this.lastChangeUser = lastChangeUser;
	}
	/**
	 * @return Returns the lastChangeWorkstation.
	 */
	public String getLastChangeWorkstation() {
		return lastChangeWorkstation;
	}
	/**
	 * @param lastChangeWorkstation The lastChangeWorkstation to set.
	 */
	public void setLastChangeWorkstation(String lastChangeWorkstation) {
		this.lastChangeWorkstation = lastChangeWorkstation;
	}
	/**
	 * @return Returns the lbsOfFruitPerBin.
	 */
	public String getLbsOfFruitPerBin() {
		return lbsOfFruitPerBin;
	}
	/**
	 * @param lbsOfFruitPerBin The lbsOfFruitPerBin to set.
	 */
	public void setLbsOfFruitPerBin(String lbsOfFruitPerBin) {
		this.lbsOfFruitPerBin = lbsOfFruitPerBin;
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
	/**
	 * @return Returns the organicFruit.
	 */
	public String getOrganicFruit() {
		return organicFruit;
	}
	/**
	 * @param organicFruit The organicFruit to set.
	 */
	public void setOrganicFruit(String organicFruit) {
		this.organicFruit = organicFruit;
	}
	/**
	 * @return Returns the payBinAllowance.
	 */
	public String getPayBinAllowance() {
		return payBinAllowance;
	}
	/**
	 * @param payBinAllowance The payBinAllowance to set.
	 */
	public void setPayBinAllowance(String payBinAllowance) {
		this.payBinAllowance = payBinAllowance;
	}
	/**
	 * @return Returns the payForFruit.
	 */
	public String getPayForFruit() {
		return payForFruit;
	}
	/**
	 * @param payForFruit The payForFruit to set.
	 */
	public void setPayForFruit(String payForFruit) {
		this.payForFruit = payForFruit;
	}
	/**
	 * @return Returns the paymentInProcess.
	 */
	public String getPaymentInProcess() {
		return paymentInProcess;
	}
	/**
	 * @param paymentInProcess The paymentInProcess to set.
	 */
	public void setPaymentInProcess(String paymentInProcess) {
		this.paymentInProcess = paymentInProcess;
	}
	/**
	 * @return Returns the paymentType.
	 */
	public String getPaymentType() {
		return paymentType;
	}
	/**
	 * @param paymentType The paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @return Returns the paymentWeight.
	 */
	public String getPaymentWeight() {
		return paymentWeight;
	}
	/**
	 * @param paymentWeight The paymentWeight to set.
	 */
	public void setPaymentWeight(String paymentWeight) {
		this.paymentWeight = paymentWeight;
	}
	/**
	 * @return Returns the payTheHauler.
	 */
	public String getPayTheHauler() {
		return payTheHauler;
	}
	/**
	 * @param payTheHauler The payTheHauler to set.
	 */
	public void setPayTheHauler(String payTheHauler) {
		this.payTheHauler = payTheHauler;
	}
	/**
	 * @return Returns the postTheInventory.
	 */
	public String getPostTheInventory() {
		return postTheInventory;
	}
	/**
	 * @param postTheInventory The postTheInventory to set.
	 */
	public void setPostTheInventory(String postTheInventory) {
		this.postTheInventory = postTheInventory;
	}
	/**
	 * @return Returns the receiptAgainstEstimateTicket.
	 */
	public String getReceiptAgainstEstimateTicket() {
		return receiptAgainstEstimateTicket;
	}
	/**
	 * @param receiptAgainstEstimateTicket The receiptAgainstEstimateTicket to set.
	 */
	public void setReceiptAgainstEstimateTicket(
			String receiptAgainstEstimateTicket) {
		this.receiptAgainstEstimateTicket = receiptAgainstEstimateTicket;
	}
	/**
	 * @return Returns the sellerCullPercentage.
	 */
	public String getSellerCullPercentage() {
		return sellerCullPercentage;
	}
	/**
	 * @param sellerCullPercentage The sellerCullPercentage to set.
	 */
	public void setSellerCullPercentage(String sellerCullPercentage) {
		this.sellerCullPercentage = sellerCullPercentage;
	}
	/**
	 * @return Returns the sellerCullWeight.
	 */
	public String getSellerCullWeight() {
		return sellerCullWeight;
	}
	/**
	 * @param sellerCullWeight The sellerCullWeight to set.
	 */
	public void setSellerCullWeight(String sellerCullWeight) {
		this.sellerCullWeight = sellerCullWeight;
	}
	/**
	 * @return Returns the treeTopCullPercentage.
	 */
	public String getTreeTopCullPercentage() {
		return treeTopCullPercentage;
	}
	/**
	 * @param treeTopCullPercentage The treeTopCullPercentage to set.
	 */
	public void setTreeTopCullPercentage(String treeTopCullPercentage) {
		this.treeTopCullPercentage = treeTopCullPercentage;
	}
	/**
	 * @return Returns the treeTopCullWeight.
	 */
	public String getTreeTopCullWeight() {
		return treeTopCullWeight;
	}
	/**
	 * @param treeTopCullWeight The treeTopCullWeight to set.
	 */
	public void setTreeTopCullWeight(String treeTopCullWeight) {
		this.treeTopCullWeight = treeTopCullWeight;
	}
	/**
	 * @return Returns the truckTareWeight.
	 */
	public String getTruckTareWeight() {
		return truckTareWeight;
	}
	/**
	 * @param truckTareWeight The truckTareWeight to set.
	 */
	public void setTruckTareWeight(String truckTareWeight) {
		this.truckTareWeight = truckTareWeight;
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
}

