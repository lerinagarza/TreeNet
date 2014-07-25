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
import com.treetop.businessobjects.RawFruitPayment;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.*;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitLotPayment extends BaseViewBeanR1 {
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
	public String variety = "";
	public String organic = "";
	public String crop = "";
	public String itemNumber = "";
	public String brix = "";
	
	public String writeUpNumber = "";
	public String writeUpNumberError = "";
	
	// Fields Available for Update
	public String paymentType = "";
	public String paymentSequence = "";
	public String runType = "";
	public String category = "";
	public String numberOfBins25Box = "";
	public String numberOfBins25BoxError = "";
	public String numberOfBins30Box = "";
	public String numberOfBins30BoxError = "";
	public String price = "";
	public String priceError = "";
	
	public String paymentWeight = "";
	public String paymentWeightError = "";
	public String paymentWeightManuallyEntered = "";
	public String payCodeHandKeyed = "";
	public String payCodeHandKeyedError = "";
	public RawFruitPayment paymentInfo = new RawFruitPayment();
	
	public Vector listSpecialCharges = new Vector(); //List of UpdRawFruitLotPaymentSpecialCharges
	public String countSpecialCharges = "";
	
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
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return Returns the listSpecialCharges.
	 */
	public Vector getListSpecialCharges() {
		return listSpecialCharges;
	}
	/**
	 * @param listSpecialCharges The listSpecialCharges to set.
	 */
	public void setListSpecialCharges(Vector listSpecialCharges) {
		this.listSpecialCharges = listSpecialCharges;
	}
	/**
	 * @return Returns the numberOfBins25Box.
	 */
	public String getNumberOfBins25Box() {
		return numberOfBins25Box;
	}
	/**
	 * @param numberOfBins25Box The numberOfBins25Box to set.
	 */
	public void setNumberOfBins25Box(String numberOfBins25Box) {
		this.numberOfBins25Box = numberOfBins25Box;
	}
	/**
	 * @return Returns the numberOfBins25BoxError.
	 */
	public String getNumberOfBins25BoxError() {
		return numberOfBins25BoxError;
	}
	/**
	 * @param numberOfBins25BoxError The numberOfBins25BoxError to set.
	 */
	public void setNumberOfBins25BoxError(String numberOfBins25BoxError) {
		this.numberOfBins25BoxError = numberOfBins25BoxError;
	}
	/**
	 * @return Returns the numberOfBins30Box.
	 */
	public String getNumberOfBins30Box() {
		return numberOfBins30Box;
	}
	/**
	 * @param numberOfBins30Box The numberOfBins30Box to set.
	 */
	public void setNumberOfBins30Box(String numberOfBins30Box) {
		this.numberOfBins30Box = numberOfBins30Box;
	}
	/**
	 * @return Returns the numberOfBins30BoxError.
	 */
	public String getNumberOfBins30BoxError() {
		return numberOfBins30BoxError;
	}
	/**
	 * @param numberOfBins30BoxError The numberOfBins30BoxError to set.
	 */
	public void setNumberOfBins30BoxError(String numberOfBins30BoxError) {
		this.numberOfBins30BoxError = numberOfBins30BoxError;
	}
	/**
	 * @return Returns the paymentInfo.
	 */
	public RawFruitPayment getPaymentInfo() {
		return paymentInfo;
	}
	/**
	 * @param paymentInfo The paymentInfo to set.
	 */
	public void setPaymentInfo(RawFruitPayment paymentInfo) {
		this.paymentInfo = paymentInfo;
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
	 * @return Returns the price.
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return Returns the priceError.
	 */
	public String getPriceError() {
		return priceError;
	}
	/**
	 * @param priceError The priceError to set.
	 */
	public void setPriceError(String priceError) {
		this.priceError = priceError;
	}
	/**
	 * @return Returns the runType.
	 */
	public String getRunType() {
		return runType;
	}
	/**
	 * @param runType The runType to set.
	 */
	public void setRunType(String runType) {
		this.runType = runType;
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
	 * @return Returns the countSpecialCharges.
	 */
	public String getCountSpecialCharges() {
		return countSpecialCharges;
	}
	/**
	 * @param countSpecialCharges The countSpecialCharges to set.
	 */
	public void setCountSpecialCharges(String countSpecialCharges) {
		this.countSpecialCharges = countSpecialCharges;
	}
	/**
	 * @return Returns the crop.
	 */
	public String getCrop() {
		return crop;
	}
	/**
	 * @param crop The crop to set.
	 */
	public void setCrop(String crop) {
		this.crop = crop;
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
	 * @return Returns the organic.
	 */
	public String getOrganic() {
		return organic;
	}
	/**
	 * @param organic The organic to set.
	 */
	public void setOrganic(String organic) {
		this.organic = organic;
	}
	/**
	 * @return Returns the variety.
	 */
	public String getVariety() {
		return variety;
	}
	/**
	 * @param variety The variety to set.
	 */
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
	public String getPaymentWeight() {
		return paymentWeight;
	}
	public void setPaymentWeight(String paymentWeight) {
		this.paymentWeight = paymentWeight;
	}
	public String getPaymentWeightManuallyEntered() {
		return paymentWeightManuallyEntered;
	}
	public void setPaymentWeightManuallyEntered(String paymentWeightManuallyEntered) {
		this.paymentWeightManuallyEntered = paymentWeightManuallyEntered;
	}
	public String getPaymentWeightError() {
		return paymentWeightError;
	}
	public void setPaymentWeightError(String paymentWeightError) {
		this.paymentWeightError = paymentWeightError;
	}
	public String getPayCodeHandKeyed() {
		return payCodeHandKeyed;
	}
	public void setPayCodeHandKeyed(String payCodeHandKeyed) {
		this.payCodeHandKeyed = payCodeHandKeyed;
	}
	public String getPayCodeHandKeyedError() {
		return payCodeHandKeyedError;
	}
	public void setPayCodeHandKeyedError(String payCodeHandKeyedError) {
		this.payCodeHandKeyedError = payCodeHandKeyedError;
	}
	public String getPoLineNumber() {
		return poLineNumber;
	}
	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
	public String getBrix() {
		return brix;
	}
	public void setBrix(String brix) {
		this.brix = brix;
	}
	public String getWriteUpNumber() {
		return writeUpNumber;
	}
	public void setWriteUpNumber(String writeUpNumber) {
		this.writeUpNumber = writeUpNumber;
	}
	public String getWriteUpNumberError() {
		return writeUpNumberError;
	}
	public void setWriteUpNumberError(String writeUpNumberError) {
		this.writeUpNumberError = writeUpNumberError;
	}
}
