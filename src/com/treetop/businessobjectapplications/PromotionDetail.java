/*
 * Created on January 18, 2008
 *
 */
package com.treetop.businessobjectapplications;


/**
 * @author twalto
 *
 * Store Promotion Related Information --
 */
public class PromotionDetail {

	protected	String		allowanceCode			= "";
	protected	String		allowanceCodeDescription= "";
	protected	String 		company	  				= "";
	protected	String 		division				= "";
	protected	String 		fiscalEffectiveDate		= "";
	protected	String 		fiscalExpirationDate	= "";	
	protected	String		itemDescription			= "";
	protected	String 		itemNumber 				= "";
	protected	String 		itemPrice				= "";
	protected	String 		lumpSumAmount			= "";	
	protected	String		merchandisingCode		= "";
	protected	String		merchandisingDescription= "";
	protected	String 		methodOfPayment			= "";
	protected	String		methodOfPaymentDescription = "";
	protected	String 		orderDate				= "";
	protected	String 		orderDiscountAmount 	= "";
	protected	String 		orderDiscountIdentity	= "";	
	protected	String 		orderDiscountRate		= "";	
	protected	String 		orderNumber				= "";
	protected	String 		orderQuantity			= "";
	protected	String 		planKey					= "";
	protected	String 		planToNumber			= "";
	protected   String		planType				= "";
	protected	String		planTypeDescription		= "";
	protected	String 		promotionDateFrom		= "";
	protected	String 		promotionDateTo			= "";
	protected	String 		promotionNumber			= "";
	protected	String 		ratePerUnit				= "";
	protected	String		recordID				= "";
	protected	String 		typeOfFund				= "";
	protected	String 		typeOfPayment			= "";
	protected	String 		typeOfPromotion			= "";
	protected   String		invaildPrice            = "";

	/**
	 *  // Constructor
	 */
	public PromotionDetail() {
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
	 * @return Returns the fiscalEffectiveDate.
	 */
	public String getFiscalEffectiveDate() {
		return this.fiscalEffectiveDate;
	}
	/**
	 * @param fiscalEffectiveDate The fiscalEffectiveDate to set.
	 */
	public void setFiscalEffectiveDate(String fiscalEffectiveDate) {
		this.fiscalEffectiveDate = fiscalEffectiveDate;
	}
	/**
	 * @return Returns the fiscalExpirationDate.
	 */
	public String getFiscalExpirationDate() {
		return this.fiscalExpirationDate;
	}
	/**
	 * @param fiscalExpirationDate The fiscalExpirationDate to set.
	 */
	public void setFiscalExpirationDate(String fiscalExpirationDate) {
		this.fiscalExpirationDate = fiscalExpirationDate;
	}
	/**
	 * @return Returns the itemDescription.
	 */
	public String getItemDescription() {
		return this.itemDescription;
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
		return this.itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the itemPrice.
	 */
	public String getItemPrice() {
		return this.itemPrice;
	}
	/**
	 * @param itemPrice The itemPrice to set.
	 */
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	/**
	 * @return Returns the lumpSumAmount.
	 */
	public String getLumpSumAmount() {
		return this.lumpSumAmount;
	}
	/**
	 * @param lumpSumAmount The lumpSumAmount to set.
	 */
	public void setLumpSumAmount(String lumpSumAmount) {
		this.lumpSumAmount = lumpSumAmount;
	}
	/**
	 * @return Returns the merchandisingCode.
	 */
	public String getMerchandisingCode() {
		return this.merchandisingCode;
	}
	/**
	 * @param merchandisingCode The merchandisingCode to set.
	 */
	public void setMerchandisingCode(String merchandisingCode) {
		this.merchandisingCode = merchandisingCode;
	}
	/**
	 * @return Returns the methodOfPayment.
	 */
	public String getMethodOfPayment() {
		return this.methodOfPayment;
	}
	/**
	 * @param methodOfPayment The methodOfPayment to set.
	 */
	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}
	/**
	 * @return Returns the orderDiscountAmount.
	 */
	public String getOrderDiscountAmount() {
		return this.orderDiscountAmount;
	}
	/**
	 * @param orderDiscountAmount The orderDiscountAmount to set.
	 */
	public void setOrderDiscountAmount(String orderDiscountAmount) {
		this.orderDiscountAmount = orderDiscountAmount;
	}
	/**
	 * @return Returns the orderDiscountIdentity.
	 */
	public String getOrderDiscountIdentity() {
		return this.orderDiscountIdentity;
	}
	/**
	 * @param orderDiscountIdentity The orderDiscountIdentity to set.
	 */
	public void setOrderDiscountIdentity(String orderDiscountIdentity) {
		this.orderDiscountIdentity = orderDiscountIdentity;
	}
	/**
	 * @return Returns the orderDiscountRate.
	 */
	public String getOrderDiscountRate() {
		return this.orderDiscountRate;
	}
	/**
	 * @param orderDiscountRate The orderDiscountRate to set.
	 */
	public void setOrderDiscountRate(String orderDiscountRate) {
		this.orderDiscountRate = orderDiscountRate;
	}
	/**
	 * @return Returns the orderQuantity.
	 */
	public String getOrderQuantity() {
		return this.orderQuantity;
	}
	/**
	 * @param orderQuantity The orderQuantity to set.
	 */
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	/**
	 * @return Returns the planKey.
	 */
	public String getPlanKey() {
		return this.planKey;
	}
	/**
	 * @param planKey The planKey to set.
	 */
	public void setPlanKey(String planKey) {
		this.planKey = planKey;
	}
	/**
	 * @return Returns the planToNumber.
	 */
	public String getPlanToNumber() {
		return this.planToNumber;
	}
	/**
	 * @param planToNumber The planToNumber to set.
	 */
	public void setPlanToNumber(String planToNumber) {
		this.planToNumber = planToNumber;
	}
	/**
	 * @return Returns the promotionDateFrom.
	 */
	public String getPromotionDateFrom() {
		return this.promotionDateFrom;
	}
	/**
	 * @param promotionDateFrom The promotionDateFrom to set.
	 */
	public void setPromotionDateFrom(String promotionDateFrom) {
		this.promotionDateFrom = promotionDateFrom;
	}
	/**
	 * @return Returns the promotionDateTo.
	 */
	public String getPromotionDateTo() {
		return this.promotionDateTo;
	}
	/**
	 * @param promotionDateTo The promotionDateTo to set.
	 */
	public void setPromotionDateTo(String promotionDateTo) {
		this.promotionDateTo = promotionDateTo;
	}
	/**
	 * @return Returns the promotionNumber.
	 */
	public String getPromotionNumber() {
		return this.promotionNumber;
	}
	/**
	 * @param promotionNumber The promotionNumber to set.
	 */
	public void setPromotionNumber(String promotionNumber) {
		this.promotionNumber = promotionNumber;
	}
	/**
	 * @return Returns the ratePerUnit.
	 */
	public String getRatePerUnit() {
		return this.ratePerUnit;
	}
	/**
	 * @param ratePerUnit The ratePerUnit to set.
	 */
	public void setRatePerUnit(String ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}
	/**
	 * @return Returns the typeOfFund.
	 */
	public String getTypeOfFund() {
		return this.typeOfFund;
	}
	/**
	 * @param typeOfFund The typeOfFund to set.
	 */
	public void setTypeOfFund(String typeOfFund) {
		this.typeOfFund = typeOfFund;
	}
	/**
	 * @return Returns the typeOfPayment.
	 */
	public String getTypeOfPayment() {
		return this.typeOfPayment;
	}
	/**
	 * @param typeOfPayment The typeOfPayment to set.
	 */
	public void setTypeOfPayment(String typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}
	/**
	 * @return Returns the typeOfPromotion.
	 */
	public String getTypeOfPromotion() {
		return this.typeOfPromotion;
	}
	/**
	 * @param typeOfPromotion The typeOfPromotion to set.
	 */
	public void setTypeOfPromotion(String typeOfPromotion) {
		this.typeOfPromotion = typeOfPromotion;
	}
	/**
	 * @return Returns the allowanceCode.
	 */
	public String getAllowanceCode() {
		return allowanceCode;
	}
	/**
	 * @param allowanceCode The allowanceCode to set.
	 */
	public void setAllowanceCode(String allowanceCode) {
		this.allowanceCode = allowanceCode;
	}
	/**
	 * @return Returns the allowanceCodeDescription.
	 */
	public String getAllowanceCodeDescription() {
		return allowanceCodeDescription;
	}
	/**
	 * @param allowanceCodeDescription The allowanceCodeDescription to set.
	 */
	public void setAllowanceCodeDescription(String allowanceCodeDescription) {
		this.allowanceCodeDescription = allowanceCodeDescription;
	}
	/**
	 * @return Returns the merchandisingDescription.
	 */
	public String getMerchandisingDescription() {
		return merchandisingDescription;
	}
	/**
	 * @param merchandisingDescription The merchandisingDescription to set.
	 */
	public void setMerchandisingDescription(String merchandisingDescription) {
		this.merchandisingDescription = merchandisingDescription;
	}
	/**
	 * @return Returns the planType.
	 */
	public String getPlanType() {
		return planType;
	}
	/**
	 * @param planType The planType to set.
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	/**
	 * @return Returns the planTypeDescription.
	 */
	public String getPlanTypeDescription() {
		return planTypeDescription;
	}
	/**
	 * @param planTypeDescription The planTypeDescription to set.
	 */
	public void setPlanTypeDescription(String planTypeDescription) {
		this.planTypeDescription = planTypeDescription;
	}
	/**
	 * @return Returns the methodOfPaymentDescription.
	 */
	public String getMethodOfPaymentDescription() {
		return methodOfPaymentDescription;
	}
	/**
	 * @param methodOfPaymentDescription The methodOfPaymentDescription to set.
	 */
	public void setMethodOfPaymentDescription(String methodOfPaymentDescription) {
		this.methodOfPaymentDescription = methodOfPaymentDescription;
	}
	/**
	 * @return Returns the orderDate.
	 */
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate The orderDate to set.
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return Returns the orderNumber.
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber The orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return Returns the recordID.
	 */
	public String getRecordID() {
		return recordID;
	}
	/**
	 * @param recordID The recordID to set.
	 */
	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
	public String getInvaildPrice() {
		return invaildPrice;
	}
	public void setInvaildPrice(String invaildPrice) {
		this.invaildPrice = invaildPrice;
	}
	
}
