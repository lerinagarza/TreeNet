/*
 * Created on Feb 22, 2006
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Inventory Lot Raw Fruit Payment information.
 * 
 */
public class LotRawFruitPayment extends Lot {
	
	protected	String		haulerBillOfLading	= "";
	protected	String		totalReceivingWeight	= "";	
	protected	String		totalBinsReceived		= "";
	protected   String		paymentWeight			= "";
	protected	String		packerWarehouse		= "";
	protected	String		paymentReceivingDate	= "";
	protected	String		fruitPaidFlag			= "";
	protected	String		haulPaidFlag			= "";
	protected	String		invTransNumber			= "";
	protected	String		freightTransNumber	= "";
	protected	String		paymentTransNumber	= "";
	protected	String		paymentIndustryGrade	= "";
	protected	String		paymentCarrierNumber	= "";
	protected   String      hauling100weight     = "";
   protected   String      fuelSurcharge        = "";
   protected   String      binWeight            = "";
   protected   String      freightWeight        = "";
	/**
	 * 
	 */
	public LotRawFruitPayment() {
		super();

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
	 * @return Returns the totalBinReceived.
	 */
	public String getTotalBinsReceived() {
		return totalBinsReceived;
	}
	/**
	 * @return Returns the totalReceivingWeight.
	 */
	public String getTotalReceivingWeight() {
		return totalReceivingWeight;
	}
	/**
	 * @param totalReceivingWeight The totalReceivingWeight to set.
	 */
	public void setTotalReceivingWeight(String totalReceivingWeight) {
		this.totalReceivingWeight = totalReceivingWeight;
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
	 * @return Returns the haulPaidFlag.
	 */
	public String getHaulPaidFlag() {
		return haulPaidFlag;
	}
	/**
	 * @param haulPaidFlag The haulPaidFlag to set.
	 */
	public void setHaulPaidFlag(String haulPaidFlag) {
		this.haulPaidFlag = haulPaidFlag;
	}
	/**
	 * @return Returns the packerWarehouse.
	 */
	public String getPackerWarehouse() {
		return packerWarehouse;
	}
	/**
	 * @param packerWarehouse The packerWarehouse to set.
	 */
	public void setPackerWarehouse(String packerWarehouse) {
		this.packerWarehouse = packerWarehouse;
	}
	/**
	 * @return Returns the paymentIndustryGrade.
	 */
	public String getPaymentIndustryGrade() {
		return paymentIndustryGrade;
	}
	/**
	 * @param paymentIndustryGrade The paymentIndustryGrade to set.
	 */
	public void setPaymentIndustryGrade(String paymentIndustryGrade) {
		this.paymentIndustryGrade = paymentIndustryGrade;
	}
	/**
	 * @return Returns the paymentReceivingDate.
	 */
	public String getPaymentReceivingDate() {
		return paymentReceivingDate;
	}
	/**
	 * @param paymentReceivingDate The paymentReceivingDate to set.
	 */
	public void setPaymentReceivingDate(String paymentReceivingDate) {
		this.paymentReceivingDate = paymentReceivingDate;
	}
	/**
	 * @param totalBinsReceived The totalBinsReceived to set.
	 */
	public void setTotalBinsReceived(String totalBinsReceived) {
		this.totalBinsReceived = totalBinsReceived;
	}

	/**
	 * @return Returns the freightTransNumber.
	 */
	public String getFreightTransNumber() {
		return freightTransNumber;
	}
	/**
	 * @param freightTransNumber The freightTransNumber to set.
	 */
	public void setFreightTransNumber(String freightTransNumber) {
		this.freightTransNumber = freightTransNumber;
	}
	/**
	 * @return Returns the invTransNumber.
	 */
	public String getInvTransNumber() {
		return invTransNumber;
	}
	/**
	 * @param invTransNumber The invTransNumber to set.
	 */
	public void setInvTransNumber(String invTransNumber) {
		this.invTransNumber = invTransNumber;
	}
	/**
	 * @return Returns the paymentTransNumber.
	 */
	public String getPaymentTransNumber() {
		return paymentTransNumber;
	}
	/**
	 * @param paymentTransNumber The paymentTransNumber to set.
	 */
	public void setPaymentTransNumber(String paymentTransNumber) {
		this.paymentTransNumber = paymentTransNumber;
	}
	/**
	 * @return Returns the paymentCarrierNumber.
	 */
	public String getPaymentCarrierNumber() {
		return paymentCarrierNumber;
	}
	/**
	 * @param paymentCarrierNumber The paymentCarrierNumber to set.
	 */
	public void setPaymentCarrierNumber(String paymentCarrierNumber) {
		this.paymentCarrierNumber = paymentCarrierNumber;
	}
/**
 * @return Returns the fuelSurcharge.
 */
public String getFuelSurcharge() {
	return fuelSurcharge;
}
/**
 * @param fuelSurcharge The fuelSurcharge to set.
 */
public void setFuelSurcharge(String fuelSurcharge) {
	this.fuelSurcharge = fuelSurcharge;
}
	/**
	 * @return Returns the hauling100weight.
	 */
	public String getHauling100weight() {
		return hauling100weight;
	}
	/**
	 * @param hauling100weight The hauling100weight to set.
	 */
	public void setHauling100weight(String hauling100weight) {
		this.hauling100weight = hauling100weight;
	}
/**
 * @return Returns the binWeight.
 */
public String getBinWeight() {
	return binWeight;
}
/**
 * @param binWeight The binWeight to set.
 */
public void setBinWeight(String binWeight) {
	this.binWeight = binWeight;
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
}
