/*
 * Created on March 27, 2008
 *
 */
package com.treetop.businessobjectapplications;


/**
 * @author twalto
 *
 * Store Brokerage Recipient Status Header Information --
 */
public class BrokerageHeader {

	protected	String 		company	  				= ""; // OFCONO
	protected	String 		division				= ""; // OFDIVI
	protected	String 		agreementID				= ""; // OFAGID
	protected	String 		validFromDate			= ""; // OFSTDE	
	protected	String		recipientAgreementBonus	= ""; // OFBREC
	protected	String 		year	 				= ""; // OFYEA4
	protected	String 		period					= ""; // OFPERI
	protected	String 		generatingValue			= ""; // OFABOV	
	protected	String		payingAmount			= ""; // OFABOA
	protected	String		currency				= ""; // OFCUCD
	protected	String 		creditedBonusAmount		= ""; // OFPBOA
	protected	String		paymentDate				= ""; // OFPYDT
	protected	String 		reservedBonusAmount		= ""; // OFALBO
	protected	String 		reservedAmountLocalCurr = ""; // OFALBL
	protected	String 		status					= ""; // OFSTAT	
	protected	String 		entryDate				= ""; // OFRGDT	
	protected	String 		entryTime				= ""; // OFRGTM
	protected	String 		changeDate				= ""; // OFLMDT
	protected	String 		changeNumber			= ""; // OFCHNO
	protected	String 		changedBy				= ""; // OFCHID
	
	/**
	 *  // Constructor
	 */
	public BrokerageHeader() {
		super();

	}
	/**
	 * @return Returns the company.
	 */
	public String getCompany() {
		return this.company;
	}
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return Returns the division.
	 */
	public String getDivision() {
		return this.division;
	}
	/**
	 * @param division The division to set.
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	/**
	 * @return Returns the agreementID.
	 */
	public String getAgreementID() {
		return agreementID;
	}
	/**
	 * @param agreementID The agreementID to set.
	 */
	public void setAgreementID(String agreementID) {
		this.agreementID = agreementID;
	}
	/**
	 * @return Returns the changeDate.
	 */
	public String getChangeDate() {
		return changeDate;
	}
	/**
	 * @param changeDate The changeDate to set.
	 */
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	/**
	 * @return Returns the changedBy.
	 */
	public String getChangedBy() {
		return changedBy;
	}
	/**
	 * @param changedBy The changedBy to set.
	 */
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	/**
	 * @return Returns the changeNumber.
	 */
	public String getChangeNumber() {
		return changeNumber;
	}
	/**
	 * @param changeNumber The changeNumber to set.
	 */
	public void setChangeNumber(String changeNumber) {
		this.changeNumber = changeNumber;
	}
	/**
	 * @return Returns the creditedBonusAmount.
	 */
	public String getCreditedBonusAmount() {
		return creditedBonusAmount;
	}
	/**
	 * @param creditedBonusAmount The creditedBonusAmount to set.
	 */
	public void setCreditedBonusAmount(String creditedBonusAmount) {
		this.creditedBonusAmount = creditedBonusAmount;
	}
	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return Returns the entryDate.
	 */
	public String getEntryDate() {
		return entryDate;
	}
	/**
	 * @param entryDate The entryDate to set.
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	/**
	 * @return Returns the entryTime.
	 */
	public String getEntryTime() {
		return entryTime;
	}
	/**
	 * @param entryTime The entryTime to set.
	 */
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	/**
	 * @return Returns the generatingValue.
	 */
	public String getGeneratingValue() {
		return generatingValue;
	}
	/**
	 * @param generatingValue The generatingValue to set.
	 */
	public void setGeneratingValue(String generatingValue) {
		this.generatingValue = generatingValue;
	}
	/**
	 * @return Returns the payingAmount.
	 */
	public String getPayingAmount() {
		return payingAmount;
	}
	/**
	 * @param payingAmount The payingAmount to set.
	 */
	public void setPayingAmount(String payingAmount) {
		this.payingAmount = payingAmount;
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
	 * @return Returns the recipientAgreementBonus.
	 */
	public String getRecipientAgreementBonus() {
		return recipientAgreementBonus;
	}
	/**
	 * @param recipientAgreementBonus The recipientAgreementBonus to set.
	 */
	public void setRecipientAgreementBonus(String recipientAgreementBonus) {
		this.recipientAgreementBonus = recipientAgreementBonus;
	}
	/**
	 * @return Returns the reservedAmountLocalCurr.
	 */
	public String getReservedAmountLocalCurr() {
		return reservedAmountLocalCurr;
	}
	/**
	 * @param reservedAmountLocalCurr The reservedAmountLocalCurr to set.
	 */
	public void setReservedAmountLocalCurr(String reservedAmountLocalCurr) {
		this.reservedAmountLocalCurr = reservedAmountLocalCurr;
	}
	/**
	 * @return Returns the reservedBonusAmount.
	 */
	public String getReservedBonusAmount() {
		return reservedBonusAmount;
	}
	/**
	 * @param reservedBonusAmount The reservedBonusAmount to set.
	 */
	public void setReservedBonusAmount(String reservedBonusAmount) {
		this.reservedBonusAmount = reservedBonusAmount;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the validFromDate.
	 */
	public String getValidFromDate() {
		return validFromDate;
	}
	/**
	 * @param validFromDate The validFromDate to set.
	 */
	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
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
