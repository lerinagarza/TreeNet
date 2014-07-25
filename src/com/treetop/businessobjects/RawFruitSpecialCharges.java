/*
 * Created on November 11, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit Special Charges Information
 * 		-- Generally loaded from file GRPCOTHC
 */
public class RawFruitSpecialCharges extends RawFruitPayment{
	
	protected	String		specialChargesSequenceNumber	= ""; //G6OSEQ#
	protected	String		rate							= ""; //G6RATE
	protected	String		accountingOption				= ""; //G6CCODE
	protected	String		accountingOptionDescription		= ""; //FUXTX40
	protected	String		specialChargesUser				= ""; //G6USER
	protected	String		specialChargesDate				= ""; //G6DATE
	protected	String		specialChargesTime				= ""; //G6TIME
	protected	String		specialChargesMovexVoucherNumber = ""; //G6VONO
	protected	String		specialChargesMovexJournalNumber = ""; //G6JRNO
	protected	String		specialChargesMovexJournalNumberSequence = ""; //G6JSNO
	protected	String		specialChargesPostingFlags		= ""; //G6POSTF
	
	protected	Supplier	supplierSpecialCharges 			= new Supplier(); //G6SUPP
	
	/**
	 *  // Constructor
	 */
	public RawFruitSpecialCharges() {
		super();

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
	 * @return Returns the rate.
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}
	/**
	 * @return Returns the supplierSpecialCharges.
	 */
	public Supplier getSupplierSpecialCharges() {
		return supplierSpecialCharges;
	}
	/**
	 * @param supplierSpecialCharges The supplierSpecialCharges to set.
	 */
	public void setSupplierSpecialCharges(Supplier supplierSpecialCharges) {
		this.supplierSpecialCharges = supplierSpecialCharges;
	}
	/**
	 * @return Returns the accountingOptionDescription.
	 */
	public String getAccountingOptionDescription() {
		return accountingOptionDescription;
	}
	/**
	 * @param accountingOptionDescription The accountingOptionDescription to set.
	 */
	public void setAccountingOptionDescription(
			String accountingOptionDescription) {
		this.accountingOptionDescription = accountingOptionDescription;
	}
	/**
	 * @return Returns the specialChargesSequenceNumber.
	 */
	public String getSpecialChargesSequenceNumber() {
		return specialChargesSequenceNumber;
	}
	/**
	 * @param specialChargesSequenceNumber The specialChargesSequenceNumber to set.
	 */
	public void setSpecialChargesSequenceNumber(
			String specialChargesSequenceNumber) {
		this.specialChargesSequenceNumber = specialChargesSequenceNumber;
	}
	/**
	 * @return Returns the specialChargesDate.
	 */
	public String getSpecialChargesDate() {
		return specialChargesDate;
	}
	/**
	 * @param specialChargesDate The specialChargesDate to set.
	 */
	public void setSpecialChargesDate(String specialChargesDate) {
		this.specialChargesDate = specialChargesDate;
	}
	/**
	 * @return Returns the specialChargesTime.
	 */
	public String getSpecialChargesTime() {
		return specialChargesTime;
	}
	/**
	 * @param specialChargesTime The specialChargesTime to set.
	 */
	public void setSpecialChargesTime(String specialChargesTime) {
		this.specialChargesTime = specialChargesTime;
	}
	/**
	 * @return Returns the specialChargesUser.
	 */
	public String getSpecialChargesUser() {
		return specialChargesUser;
	}
	/**
	 * @param specialChargesUser The specialChargesUser to set.
	 */
	public void setSpecialChargesUser(String specialChargesUser) {
		this.specialChargesUser = specialChargesUser;
	}
	public String getSpecialChargesMovexJournalNumber() {
		return specialChargesMovexJournalNumber;
	}
	public void setSpecialChargesMovexJournalNumber(
			String specialChargesMovexJournalNumber) {
		this.specialChargesMovexJournalNumber = specialChargesMovexJournalNumber;
	}
	public String getSpecialChargesMovexJournalNumberSequence() {
		return specialChargesMovexJournalNumberSequence;
	}
	public void setSpecialChargesMovexJournalNumberSequence(
			String specialChargesMovexJournalNumberSequence) {
		this.specialChargesMovexJournalNumberSequence = specialChargesMovexJournalNumberSequence;
	}
	public String getSpecialChargesMovexVoucherNumber() {
		return specialChargesMovexVoucherNumber;
	}
	public void setSpecialChargesMovexVoucherNumber(
			String specialChargesMovexVoucherNumber) {
		this.specialChargesMovexVoucherNumber = specialChargesMovexVoucherNumber;
	}
	public String getSpecialChargesPostingFlags() {
		return specialChargesPostingFlags;
	}
	public void setSpecialChargesPostingFlags(String specialChargesPostingFlags) {
		this.specialChargesPostingFlags = specialChargesPostingFlags;
	}
}
