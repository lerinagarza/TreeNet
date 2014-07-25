/*
 * Created on December 16, 2008
 */

package com.treetop.app.rawfruit;

import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */
public class DtlRawFruitSpecialCharges extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Fields Available for Update
	public String accountingCode = "";
	public String accountingCodeDescription = "";
	public String perTon = "";
	public String perPound = "";
	public String weight = "";
	public String totalCost = "";
	public String supplier = "";
	public String supplierName = "";
	
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
	 * @return Returns the accountingCode.
	 */
	public String getAccountingCode() {
		return accountingCode;
	}
	/**
	 * @param accountingCode The accountingCode to set.
	 */
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}
	/**
	 * @return Returns the accountingCodeDescription.
	 */
	public String getAccountingCodeDescription() {
		return accountingCodeDescription;
	}
	/**
	 * @param accountingCodeDescription The accountingCodeDescription to set.
	 */
	public void setAccountingCodeDescription(String accountingCodeDescription) {
		this.accountingCodeDescription = accountingCodeDescription;
	}
	/**
	 * @return Returns the perPound.
	 */
	public String getPerPound() {
		return perPound;
	}
	/**
	 * @param perPound The perPound to set.
	 */
	public void setPerPound(String perPound) {
		this.perPound = perPound;
	}
	/**
	 * @return Returns the perTon.
	 */
	public String getPerTon() {
		return perTon;
	}
	/**
	 * @param perTon The perTon to set.
	 */
	public void setPerTon(String perTon) {
		this.perTon = perTon;
	}
	/**
	 * @return Returns the totalCost.
	 */
	public String getTotalCost() {
		return totalCost;
	}
	/**
	 * @param totalCost The totalCost to set.
	 */
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	/**
	 * @return Returns the weight.
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
