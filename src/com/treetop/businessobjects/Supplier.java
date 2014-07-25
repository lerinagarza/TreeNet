/*
 * Created on November 11, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Supplier Information (Movex, M3)
 */
public class Supplier {
	
	protected	String		supplierNumber		= "";	
	protected   String		supplierName		= "";
	
	/**
	 *  // Constructor
	 */
	public Supplier() {
		super();
	}
	/**
	 * @return Returns the supplierName.
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName The supplierName to set.
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return Returns the supplierNumber.
	 */
	public String getSupplierNumber() {
		return supplierNumber;
	}
	/**
	 * @param supplierNumber The supplierNumber to set.
	 */
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}
}
