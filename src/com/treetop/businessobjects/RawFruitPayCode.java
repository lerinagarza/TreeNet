/*
 * Created on November 10, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit Grower Payment Information Information
 * 		-- Generally loaded from file GRPKCODE
 *      -- Will also be loaded from GRPJBPRC
 */
public class RawFruitPayCode{
	
	protected	String		payCode			= "";
	protected	String		variety			= "";	
	protected	String		convOrganic		= "";
	protected	String		crop			= "";
	protected	String		runType			= "";
	protected	String		category		= "";
	protected	String		paymentType		= "";
	protected	String		itemNumber		= "";
	protected	String		price			= "0";
	protected	String		pricePerTon		= "0";
	protected	String		pricePerPound	= "0";
	protected	String		withholdPerTon  = "0";
	protected	String		withholdPerPound = "0";
	protected	String		brix			= "0.0";
	protected	String		asOfDate		= "";
	
	/**
	 *  // Constructor
	 */
	public RawFruitPayCode() {
		super();

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
	 * @return Returns the convOrganic.
	 */
	public String getConvOrganic() {
		return convOrganic;
	}
	/**
	 * @param convOrganic The convOrganic to set.
	 */
	public void setConvOrganic(String convOrganic) {
		this.convOrganic = convOrganic;
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
	 * @return Returns the payCode.
	 */
	public String getPayCode() {
		return payCode;
	}
	/**
	 * @param payCode The payCode to set.
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
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
	 * @return Returns the pricePerPound.
	 */
	public String getPricePerPound() {
		return pricePerPound;
	}
	/**
	 * @param pricePerPound The pricePerPound to set.
	 */
	public void setPricePerPound(String pricePerPound) {
		this.pricePerPound = pricePerPound;
	}
	/**
	 * @return Returns the pricePerTon.
	 */
	public String getPricePerTon() {
		return pricePerTon;
	}
	/**
	 * @param pricePerTon The pricePerTon to set.
	 */
	public void setPricePerTon(String pricePerTon) {
		this.pricePerTon = pricePerTon;
	}
	public String getWithholdPerPound() {
		return withholdPerPound;
	}
	public void setWithholdPerPound(String withholdPerPound) {
		this.withholdPerPound = withholdPerPound;
	}
	public String getWithholdPerTon() {
		return withholdPerTon;
	}
	public void setWithholdPerTon(String withholdPerTon) {
		this.withholdPerTon = withholdPerTon;
	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getBrix() {
		return brix;
	}
	public void setBrix(String brix) {
		this.brix = brix;
	}
}
