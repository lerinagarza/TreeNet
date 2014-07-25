/*
 * Created on December 16, 2008
 */

package com.treetop.app.rawfruit;

import java.util.Vector;

import com.treetop.businessobjects.RawFruitPayment;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */
public class DtlRawFruitPayment extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Fields Available for Display - Formatted and Ready
	public String itemNumber = "";
	public String itemDescription = "";
	public String lotNumber = "";
	public String crop = "";
	public String paymentSequence = "";
	public String supplier = "";
	public String supplierDescription = "";
	public String supplierInvoiceNumber = "";
	public String receivingDate = "";
	public String purchaseOrder = "";
	public String paymentCode = "";
	public String payCodeHandKeyed = "";
	public String whseTicket = "";
	public String carrierBillOfLading = "";
	public String scaleTicketNumber = "";
	public String facility = "";
	
	public String codeDescriptions = "";

	public String paymentWeight = "";
	public String fruitPricePerTon = "";
	public String fruitPricePerPound = "";
	public String fruitPriceTotal = "";
	
	public Vector listWithhold = new Vector(); // DtlRawFruitSpecialCharges
	public String totalPrice = "0";
	public Vector listCommission = new Vector(); // DtlRawFruitSpecialCharges
	public String totalCommission = "0";
	public Vector listOtherCharges = new Vector(); // DtlRawFruitSpecialCharges
	public String totalOtherCharges = "0";
	public String totalInvoiceAmount = "0";
	public String brix = "0.0";
	public String grapeDueDate = "";
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	/**
	 * @return Returns the listCommission.
	 */
	public Vector getListCommission() {
		return listCommission;
	}
	/**
	 * @param listCommission The listCommission to set.
	 */
	public void setListCommission(Vector listCommission) {
		this.listCommission = listCommission;
	}
	/**
	 * @return Returns the listOtherCharges.
	 */
	public Vector getListOtherCharges() {
		return listOtherCharges;
	}
	/**
	 * @param listOtherCharges The listOtherCharges to set.
	 */
	public void setListOtherCharges(Vector listOtherCharges) {
		this.listOtherCharges = listOtherCharges;
	}
	/**
	 * @return Returns the listWithhold.
	 */
	public Vector getListWithhold() {
		return listWithhold;
	}
	/**
	 * @param listWithhold The listWithhold to set.
	 */
	public void setListWithhold(Vector listWithhold) {
		this.listWithhold = listWithhold;
	}
	/**
	 * @return Returns the totalCommission.
	 */
	public String getTotalCommission() {
		return totalCommission;
	}
	/**
	 * @param totalCommission The totalCommission to set.
	 */
	public void setTotalCommission(String totalCommission) {
		this.totalCommission = totalCommission;
	}
	/**
	 * @return Returns the totalInvoiceAmount.
	 */
	public String getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}
	/**
	 * @param totalInvoiceAmount The totalInvoiceAmount to set.
	 */
	public void setTotalInvoiceAmount(String totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}
	/**
	 * @return Returns the totalOtherCharges.
	 */
	public String getTotalOtherCharges() {
		return totalOtherCharges;
	}
	/**
	 * @param totalOtherCharges The totalOtherCharges to set.
	 */
	public void setTotalOtherCharges(String totalOtherCharges) {
		this.totalOtherCharges = totalOtherCharges;
	}
	/**
	 * @return Returns the totalPrice.
	 */
	public String getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice The totalPrice to set.
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return Returns the carrierBillOfLading.
	 */
	public String getCarrierBillOfLading() {
		return carrierBillOfLading;
	}
	/**
	 * @param carrierBillOfLading The carrierBillOfLading to set.
	 */
	public void setCarrierBillOfLading(String carrierBillOfLading) {
		this.carrierBillOfLading = carrierBillOfLading;
	}
	/**
	 * @return Returns the codeDescriptions.
	 */
	public String getCodeDescriptions() {
		return codeDescriptions;
	}
	/**
	 * @param codeDescriptions The codeDescriptions to set.
	 */
	public void setCodeDescriptions(String codeDescriptions) {
		this.codeDescriptions = codeDescriptions;
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
	 * @return Returns the paymentCode.
	 */
	public String getPaymentCode() {
		return paymentCode;
	}
	/**
	 * @param paymentCode The paymentCode to set.
	 */
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	/**
	 * @return Returns the paymentSequence.
	 */
	public String getPaymentSequence() {
		return paymentSequence;
	}
	/**
	 * @param paymentSequence The paymentSequence to set.
	 */
	public void setPaymentSequence(String paymentSequence) {
		this.paymentSequence = paymentSequence;
	}
	/**
	 * @return Returns the purchaseOrder.
	 */
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	/**
	 * @param purchaseOrder The purchaseOrder to set.
	 */
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	/**
	 * @return Returns the receivingDate.
	 */
	public String getReceivingDate() {
		return receivingDate;
	}
	/**
	 * @param receivingDate The receivingDate to set.
	 */
	public void setReceivingDate(String receivingDate) {
		this.receivingDate = receivingDate;
	}
	/**
	 * @return Returns the scaleTicketNumber.
	 */
	public String getScaleTicketNumber() {
		return scaleTicketNumber;
	}
	/**
	 * @param scaleTicketNumber The scaleTicketNumber to set.
	 */
	public void setScaleTicketNumber(String scaleTicketNumber) {
		this.scaleTicketNumber = scaleTicketNumber;
	}
	/**
	 * @return Returns the supplier.
	 */
	public String getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier The supplier to set.
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * @return Returns the supplierDescription.
	 */
	public String getSupplierDescription() {
		return supplierDescription;
	}
	/**
	 * @param supplierDescription The supplierDescription to set.
	 */
	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}
	/**
	 * @return Returns the supplierInvoiceNumber.
	 */
	public String getSupplierInvoiceNumber() {
		return supplierInvoiceNumber;
	}
	/**
	 * @param supplierInvoiceNumber The supplierInvoiceNumber to set.
	 */
	public void setSupplierInvoiceNumber(String supplierInvoiceNumber) {
		this.supplierInvoiceNumber = supplierInvoiceNumber;
	}
	/**
	 * @return Returns the whseTicket.
	 */
	public String getWhseTicket() {
		return whseTicket;
	}
	/**
	 * @param whseTicket The whseTicket to set.
	 */
	public void setWhseTicket(String whseTicket) {
		this.whseTicket = whseTicket;
	}
	/**
	 * @return Returns the fruitPricePerPound.
	 */
	public String getFruitPricePerPound() {
		return fruitPricePerPound;
	}
	/**
	 * @param fruitPricePerPound The fruitPricePerPound to set.
	 */
	public void setFruitPricePerPound(String fruitPricePerPound) {
		this.fruitPricePerPound = fruitPricePerPound;
	}
	/**
	 * @return Returns the fruitPricePerTon.
	 */
	public String getFruitPricePerTon() {
		return fruitPricePerTon;
	}
	/**
	 * @param fruitPricePerTon The fruitPricePerTon to set.
	 */
	public void setFruitPricePerTon(String fruitPricePerTon) {
		this.fruitPricePerTon = fruitPricePerTon;
	}
	/**
	 * @return Returns the fruitPriceTotal.
	 */
	public String getFruitPriceTotal() {
		return fruitPriceTotal;
	}
	/**
	 * @param fruitPriceTotal The fruitPriceTotal to set.
	 */
	public void setFruitPriceTotal(String fruitPriceTotal) {
		this.fruitPriceTotal = fruitPriceTotal;
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
	public String getPayCodeHandKeyed() {
		return payCodeHandKeyed;
	}
	public void setPayCodeHandKeyed(String payCodeHandKeyed) {
		this.payCodeHandKeyed = payCodeHandKeyed;
	}
	public String getBrix() {
		return brix;
	}
	public void setBrix(String brix) {
		this.brix = brix;
	}
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
	}
	public String getGrapeDueDate() {
		return grapeDueDate;
	}
	public void setGrapeDueDate(String grapeDueDate) {
		this.grapeDueDate = grapeDueDate;
	}
}
