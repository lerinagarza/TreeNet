/*
 * Created on Jan 30, 2006
 *
 * Grower Fruit Receiving Payment Posting 
 * Transaction Information.
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Grower Fruit Receiving Payment Posting 
 * Transaction Information.
 */
public class FruitReceivingPaymentTrans 
	extends FruitReceivingTicketTrans {

	// a presentation of the Grower file GRPBGLCH
	
	String		glNaturalCode;
	String		glAccountNumber;
	String		glChargeRate;
	String		warehousePacker;
	String		amount;
	String		systemGeneratedCharge;
	String		year;
	String		period;
	String		voucherNumber;
	String		batchNumber;
	String		checkNumber;
	String		payeeNumber;
	String		miscComponents;
	

	/**
	 * 
	 *
	 */
	public FruitReceivingPaymentTrans(){
		super();
	}
	
	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the batchNumber.
	 */
	public String getBatchNumber() {
		return batchNumber;
	}
	/**
	 * @param batchNumber The batchNumber to set.
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	/**
	 * @return Returns the checkNumber.
	 */
	public String getCheckNumber() {
		return checkNumber;
	}
	/**
	 * @param checkNumber The checkNumber to set.
	 */
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	/**
	 * @return Returns the glAccountNumber.
	 */
	public String getGlAccountNumber() {
		return glAccountNumber;
	}
	/**
	 * @param glAccountNumber The glAccountNumber to set.
	 */
	public void setGlAccountNumber(String glAccountNumber) {
		this.glAccountNumber = glAccountNumber;
	}
	/**
	 * @return Returns the glChargeRate.
	 */
	public String getGlChargeRate() {
		return glChargeRate;
	}
	/**
	 * @param glChargeRate The glChargeRate to set.
	 */
	public void setGlChargeRate(String glChargeRate) {
		this.glChargeRate = glChargeRate;
	}
	/**
	 * @return Returns the glNaturalCode.
	 */
	public String getGlNaturalCode() {
		return glNaturalCode;
	}
	/**
	 * @param glNaturalCode The glNaturalCode to set.
	 */
	public void setGlNaturalCode(String glNaturalCode) {
		this.glNaturalCode = glNaturalCode;
	}
	/**
	 * @return Returns the miscComponents.
	 */
	public String getMiscComponents() {
		return miscComponents;
	}
	/**
	 * @param miscComponents The miscComponents to set.
	 */
	public void setMiscComponents(String miscComponents) {
		this.miscComponents = miscComponents;
	}
	/**
	 * @return Returns the payeeNumber.
	 */
	public String getPayeeNumber() {
		return payeeNumber;
	}
	/**
	 * @param payeeNumber The payeeNumber to set.
	 */
	public void setPayeeNumber(String payeeNumber) {
		this.payeeNumber = payeeNumber;
	}
	/**
	 * @return Returns the period.
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 * @param period The period to set.
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	/**
	 * @return Returns the systemGeneratedCharge.
	 */
	public String getSystemGeneratedCharge() {
		return systemGeneratedCharge;
	}
	/**
	 * @param systemGeneratedCharge The systemGeneratedCharge to set.
	 */
	public void setSystemGeneratedCharge(String systemGeneratedCharge) {
		this.systemGeneratedCharge = systemGeneratedCharge;
	}
	/**
	 * @return Returns the voucherNumber.
	 */
	public String getVoucherNumber() {
		return voucherNumber;
	}
	/**
	 * @param voucherNumber The voucherNumber to set.
	 */
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	/**
	 * @return Returns the warehousePacker.
	 */
	public String getWarehousePacker() {
		return warehousePacker;
	}
	/**
	 * @param warehousePacker The warehousePacker to set.
	 */
	public void setWarehousePacker(String warehousePacker) {
		this.warehousePacker = warehousePacker;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}
}
