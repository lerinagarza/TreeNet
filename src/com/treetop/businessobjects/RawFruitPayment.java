/*
 * Created on November 11, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit Payment Information
 * 	// Generally Loaded from file: GRPCPMT
 */
public class RawFruitPayment extends RawFruitLot{
	
	protected	String		paymentSequenceNumber		= ""; //G5YSEQ#
	protected	String		paymentBins25Box			= ""; //G525B
	protected	String		paymentBins30Box			= ""; //G530B
	protected	String		paymentWeight				= ""; //G5TBW
	protected	String		paymentWeightHandKeyed		= ""; //G5TBWO
	protected	String		cmvFruitPrice				= ""; //G5CMV -- Cost Type 3
	protected	String		fruitPrice					= ""; //G5PRICE --Retrieved Price
	protected	String		fruitPriceHandKeyed			= ""; //G5PRICEM
	protected	String		fruitPriceAdjustment		= ""; //G5ADJP
	protected	String		paymentUser					= ""; //G5USER
	protected	String		paymentDate					= ""; //G5DATE
	protected	String		paymentTime					= ""; //G5TIME
	protected	String		paymentMovexVoucherNumber			= ""; //G5VONO
	protected	String		paymentMovexJournalNumber			= ""; //G5JRNO
	protected	String		paymentMovexJournalNumberSequence	= ""; //G5JSNO
	protected	String		paymentPostingFlags			= ""; //G5POSTF
	protected	String		paymentWriteUpNumber		= ""; //G5WUP
	
	protected	String		payCodeHandKeyed			= ""; //G5M3PCD	
	
	protected	RawFruitPayCode	payCode		= new RawFruitPayCode();
		// Includes:
			//Payment Type:	G5CPTP
			//Run Type:		G5CRTP
			//Category:		G5CAT
			//Payment Code	G5PCODE
			//Crop			G5CROP
			//Organic Conv	G5CNVOR
			//Variety		G5VAR
	
	protected	Vector		listSpecialCharges			= new Vector(); // Raw Fruit Special Charges
	
	/**
	 *  // Constructor
	 */
	public RawFruitPayment() {
		super();

	}
	/**
	 * @return Returns the cmvFruitPrice.
	 */
	public String getCmvFruitPrice() {
		return cmvFruitPrice;
	}
	/**
	 * @param cmvFruitPrice The cmvFruitPrice to set.
	 */
	public void setCmvFruitPrice(String cmvFruitPrice) {
		this.cmvFruitPrice = cmvFruitPrice;
	}
	/**
	 * @return Returns the payCode.
	 */
	public RawFruitPayCode getPayCode() {
		return payCode;
	}
	/**
	 * @param payCode The payCode to set.
	 */
	public void setPayCode(RawFruitPayCode payCode) {
		this.payCode = payCode;
	}
	/**
	 * @return Returns the paymentBins25Box.
	 */
	public String getPaymentBins25Box() {
		return paymentBins25Box;
	}
	/**
	 * @param paymentBins25Box The paymentBins25Box to set.
	 */
	public void setPaymentBins25Box(String paymentBins25Box) {
		this.paymentBins25Box = paymentBins25Box;
	}
	/**
	 * @return Returns the paymentBins30Box.
	 */
	public String getPaymentBins30Box() {
		return paymentBins30Box;
	}
	/**
	 * @param paymentBins30Box The paymentBins30Box to set.
	 */
	public void setPaymentBins30Box(String paymentBins30Box) {
		this.paymentBins30Box = paymentBins30Box;
	}
	/**
	 * @return Returns the paymentSequenceNumber.
	 */
	public String getPaymentSequenceNumber() {
		return paymentSequenceNumber;
	}
	/**
	 * @param paymentSequenceNumber The paymentSequenceNumber to set.
	 */
	public void setPaymentSequenceNumber(String paymentSequenceNumber) {
		this.paymentSequenceNumber = paymentSequenceNumber;
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
	 * @return Returns the fruitPrice.
	 */
	public String getFruitPrice() {
		return fruitPrice;
	}
	/**
	 * @param fruitPrice The fruitPrice to set.
	 */
	public void setFruitPrice(String fruitPrice) {
		this.fruitPrice = fruitPrice;
	}
	/**
	 * @return Returns the fruitPriceAdjustment.
	 */
	public String getFruitPriceAdjustment() {
		return fruitPriceAdjustment;
	}
	/**
	 * @param fruitPriceAdjustment The fruitPriceAdjustment to set.
	 */
	public void setFruitPriceAdjustment(String fruitPriceAdjustment) {
		this.fruitPriceAdjustment = fruitPriceAdjustment;
	}
	/**
	 * @return Returns the fruitPriceHandKeyed.
	 */
	public String getFruitPriceHandKeyed() {
		return fruitPriceHandKeyed;
	}
	/**
	 * @param fruitPriceHandKeyed The fruitPriceHandKeyed to set.
	 */
	public void setFruitPriceHandKeyed(String fruitPriceHandKeyed) {
		this.fruitPriceHandKeyed = fruitPriceHandKeyed;
	}
	/**
	 * @return Returns the paymentDate.
	 */
	public String getPaymentDate() {
		return paymentDate;
	}
	/**
	 * @param paymentDate The paymentDate to set.
	 */
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	/**
	 * @return Returns the paymentTime.
	 */
	public String getPaymentTime() {
		return paymentTime;
	}
	/**
	 * @param paymentTime The paymentTime to set.
	 */
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	/**
	 * @return Returns the paymentUser.
	 */
	public String getPaymentUser() {
		return paymentUser;
	}
	/**
	 * @param paymentUser The paymentUser to set.
	 */
	public void setPaymentUser(String paymentUser) {
		this.paymentUser = paymentUser;
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
		public String getPaymentPostingFlags() {
			return paymentPostingFlags;
		}
		public void setPaymentPostingFlags(String paymentPostingFlags) {
			this.paymentPostingFlags = paymentPostingFlags;
		}
		public String getPaymentMovexJournalNumber() {
			return paymentMovexJournalNumber;
		}
		public void setPaymentMovexJournalNumber(String paymentMovexJournalNumber) {
			this.paymentMovexJournalNumber = paymentMovexJournalNumber;
		}
		public String getPaymentMovexJournalNumberSequence() {
			return paymentMovexJournalNumberSequence;
		}
		public void setPaymentMovexJournalNumberSequence(
				String paymentMovexJournalNumberSequence) {
			this.paymentMovexJournalNumberSequence = paymentMovexJournalNumberSequence;
		}
		public String getPaymentMovexVoucherNumber() {
			return paymentMovexVoucherNumber;
		}
		public void setPaymentMovexVoucherNumber(String paymentMovexVoucherNumber) {
			this.paymentMovexVoucherNumber = paymentMovexVoucherNumber;
		}
		public String getPayCodeHandKeyed() {
			return payCodeHandKeyed;
		}
		public void setPayCodeHandKeyed(String payCodeHandKeyed) {
			this.payCodeHandKeyed = payCodeHandKeyed;
		}
		public String getPaymentWeightHandKeyed() {
			return paymentWeightHandKeyed;
		}
		public void setPaymentWeightHandKeyed(String paymentWeightHandKeyed) {
			this.paymentWeightHandKeyed = paymentWeightHandKeyed;
		}
		public String getPaymentWriteUpNumber() {
			return paymentWriteUpNumber;
		}
		public void setPaymentWriteUpNumber(String paymentWriteUpNumber) {
			this.paymentWriteUpNumber = paymentWriteUpNumber;
		}
}
