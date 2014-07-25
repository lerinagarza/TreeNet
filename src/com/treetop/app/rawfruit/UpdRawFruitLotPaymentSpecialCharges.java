/*
 * Created on November 24, 2008
 */

package com.treetop.app.rawfruit;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanRawFruit;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.RawFruitSpecialCharges;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.*;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitLotPaymentSpecialCharges extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String receivingDate = "";
	public String scaleSequence = "";
	public String poNumber = "";
	public String poLineNumber = "";
	public String lotNumber = "";
	public String lotSequenceNumber = "";
	public String paymentSequence = "";
	// Fields Available for Update
	public String paymentSpecialChargesSequence = "";
	public String supplier = "";
	public String supplierError = "";
	public String accountingOption = "";
	public String ratePerPound = "";
	public String ratePerPoundError = "";
	
	public RawFruitSpecialCharges specialChargesInfo = new RawFruitSpecialCharges();
	
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
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
	 * @return Returns the lotSequenceNumber.
	 */
	public String getLotSequenceNumber() {
		return lotSequenceNumber;
	}
	/**
	 * @param lotSequenceNumber The lotSequenceNumber to set.
	 */
	public void setLotSequenceNumber(String lotSequenceNumber) {
		this.lotSequenceNumber = lotSequenceNumber;
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
	 * @return Returns the poNumber.
	 */
	public String getPoNumber() {
		return poNumber;
	}
	/**
	 * @param poNumber The poNumber to set.
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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
	 * @return Returns the scaleSequence.
	 */
	public String getScaleSequence() {
		return scaleSequence;
	}
	/**
	 * @param scaleSequence The scaleSequence to set.
	 */
	public void setScaleSequence(String scaleSequence) {
		this.scaleSequence = scaleSequence;
	}
	/**
	 * @return Returns the scaleTicket.
	 */
	public String getScaleTicket() {
		return scaleTicket;
	}
	/**
	 * @param scaleTicket The scaleTicket to set.
	 */
	public void setScaleTicket(String scaleTicket) {
		this.scaleTicket = scaleTicket;
	}
	/**
	 * @return Returns the accountingOption.
	 */
	public String getAccountingOption() {
		return accountingOption;
	}
	/**
	 * @param accountingOption The accountingOption to set.
	 */
	public void setAccountingOption(String accountingOption) {
		this.accountingOption = accountingOption;
	}
	/**
	 * @return Returns the paymentSpecialChargesSequence.
	 */
	public String getPaymentSpecialChargesSequence() {
		return paymentSpecialChargesSequence;
	}
	/**
	 * @param paymentSpecialChargesSequence The paymentSpecialChargesSequence to set.
	 */
	public void setPaymentSpecialChargesSequence(
			String paymentSpecialChargesSequence) {
		this.paymentSpecialChargesSequence = paymentSpecialChargesSequence;
	}
	/**
	 * @return Returns the specialChargesInfo.
	 */
	public RawFruitSpecialCharges getSpecialChargesInfo() {
		return specialChargesInfo;
	}
	/**
	 * @param specialChargesInfo The specialChargesInfo to set.
	 */
	public void setSpecialChargesInfo(RawFruitSpecialCharges specialChargesInfo) {
		this.specialChargesInfo = specialChargesInfo;
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
	 * @return Returns the supplierError.
	 */
	public String getSupplierError() {
		return supplierError;
	}
	/**
	 * @param supplierError The supplierError to set.
	 */
	public void setSupplierError(String supplierError) {
		this.supplierError = supplierError;
	}
	/**
	 * @return Returns the ratePerPound.
	 */
	public String getRatePerPound() {
		return ratePerPound;
	}
	/**
	 * @param ratePerPound The ratePerPound to set.
	 */
	public void setRatePerPound(String ratePerPound) {
		this.ratePerPound = ratePerPound;
	}
	/**
	 * @return Returns the ratePerPoundError.
	 */
	public String getRatePerPoundError() {
		return ratePerPoundError;
	}
	/**
	 * @param ratePerPoundError The ratePerPoundError to set.
	 */
	public void setRatePerPoundError(String ratePerPoundError) {
		this.ratePerPoundError = ratePerPoundError;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
	public String getPoLineNumber() {
		return poLineNumber;
	}
	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
}
