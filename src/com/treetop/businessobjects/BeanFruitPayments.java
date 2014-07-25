/*
 * Created on Feb 24, 2006
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Container class to hold Raw Fruit Payment 
 * Transactions Screen Application data.
 */
public class BeanFruitPayments {
	
	private	LotRawFruitPayment		payment;
	private Carrier					carrier;
	private	GrowerPackerWarehouse	growerPackerWarehouse;
	private	GrowerControlInfo		growerControlInfo;
	private	DescriptiveCodeDried	paymentVarietyCode;
	private DescriptiveCodeDried	inventoryVarietyCode;

	/**
	 * @return Returns the payment.
	 */
	public LotRawFruitPayment getPayment() {
		return payment;
	}
	/**
	 * @param payment The payment to set.
	 */
	public void setPayment(LotRawFruitPayment payment) {
		this.payment = payment;
	}
	/**
	 * @return Returns the carrier.
	 */
	public Carrier getCarrier() {
		return carrier;
	}
	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	/**
	 * @return Returns the growerPackerWarehouse.
	 */
	public GrowerPackerWarehouse getGrowerPackerWarehouse() {
		return growerPackerWarehouse;
	}
	/**
	 * @param growerPackerWarehouse The growerPackerWarehouse to set.
	 */
	public void setGrowerPackerWarehouse(
			GrowerPackerWarehouse growerPackerWarehouse) {
		this.growerPackerWarehouse = growerPackerWarehouse;
	}
	/**
	 * @return Returns the growerControlInfo.
	 */
	public GrowerControlInfo getGrowerControlInfo() {
		return growerControlInfo;
	}
	/**
	 * @param growerControlInfo The growerControlInfo to set.
	 */
	public void setGrowerControlInfo(GrowerControlInfo growerControlInfo) {
		this.growerControlInfo = growerControlInfo;
	}
	/**
	 * @return Returns the paymentVarietyCode.
	 */
	public DescriptiveCodeDried getPaymentVarietyCode() {
		return paymentVarietyCode;
	}
	/**
	 * @param paymentVarietyCode The paymentVarietyCode to set.
	 */
	public void setPaymentVarietyCode(DescriptiveCodeDried paymentVarietyCode) {
		this.paymentVarietyCode = paymentVarietyCode;
	}
	/**
	 * @return Returns the inventoryVarietyCode.
	 */
	public DescriptiveCodeDried getInventoryVarietyCode() {
		return inventoryVarietyCode;
	}
	/**
	 * @param inventoryVarietyCode The inventoryVarietyCode to set.
	 */
	public void setInventoryVarietyCode(
			DescriptiveCodeDried inventoryVarietyCode) {
		this.inventoryVarietyCode = inventoryVarietyCode;
	}
}
