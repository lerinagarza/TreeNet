/*
 * Created on March 21, 2005
 *
 * View Class for use with the specification inquiry Page 
 * 	   Makes sending information to the class for the
 *     list page easy.
 * 
 */
package com.treetop.data;

import java.math.*;
import java.util.*;

/**
 * @author twalto
 *
 * Use this class to send selection criteria to for
 *   the list page
 * 
 */
public class SpecificationViewInquiry {

	
	// Specification
	private String		specificationCode;
	private String      specificationDate;
	private String      specificationType;
	private String      generalDescription;
	private String      statusCode;						
	private	String		resourceNumber;
	private String      companyNumber;
	private String      customerNumber;
	private String		fruitVariety;
	//Inventory
	private String      classification;
	// Customer
	private String		customerStatus;
	// Additional Class Fields.	
	private String      orderByField;
	private String      orderByStyle;
	private Integer     startAt;
	private String      showInventory;
	private String      showCustomers;
	private String      showAllRevisions;	
	private String      fruitVarietyList;	
	private String      requestType;		
	// Attribute / Analytical Codes
	private Vector      attributeCodes;		
	
	
	/**
	 * 
	 */
	public SpecificationViewInquiry() {
		super();
	}
	
	
	/**
	 * 	This method tests sets and gets into thie view class
	 *    which is used for Inq. - List Information
	 */

	public static void main(String[] args) {

		// Test instantiation of class.
	
		try {
//			SpecificationViewInquiry v1 = new SpecificationViewInquiry();
//			v1.setLotNumber("x");
//			
//			System.out.println(v1.getLotNumber());
			
		} catch (Exception e) {
			System.out.println("Catch At SpecificationViewInquiry.main()");
		}
		
	}
	
	
	/**
	 * @return
	 */
	public String getCompanyNumber() {
		return companyNumber;
	}

	/**
	 * @return
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @return
	 */
	public String getCustomerStatus() {
		return customerStatus;
	}

	/**
	 * @return
	 */
	public String getFruitVariety() {
		return fruitVariety;
	}

		/**
		 * @return
		 */
		public String getOrderByField() {
			return orderByField;
		}

	/**
	 * @return
	 */
	public String getOrderByStyle() {
		return orderByStyle;
	}

	/**
	 * @return
	 */
	public String getResourceNumber() {
		return resourceNumber;
	}

	/**
	 * @return
	 */
	public String getShowAllRevisions() {
		return showAllRevisions;
	}

	/**
	 * @return
	 */
	public String getShowInventory() {
		return showInventory;
	}

	/**
	 * @return
	 */
	public String getSpecificationCode() {
		return specificationCode;
	}

	/**
	 * @return
	 */
	public Integer getStartAt() {
		return startAt;
	}

	/**
	 * @return
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param string
	 */
	public void setCompanyNumber(String string) {
		companyNumber = string;
	}

	/**
	 * @param string
	 */
	public void setCustomerNumber(String string) {
		customerNumber = string;
	}

	/**
	 * @param string
	 */
	public void setCustomerStatus(String string) {
		customerStatus = string;
	}

	/**
	 * @param string
	 */
	public void setFruitVariety(String string) {
		fruitVariety = string;
	}

		/**
		 * @param string
		 */
		public void setOrderByField(String string) {
			orderByField = string;
		}

	/**
	 * @param string
	 */
	public void setOrderByStyle(String string) {
		orderByStyle = string;
	}

	/**
	 * @param string
	 */
	public void setResourceNumber(String string) {
		resourceNumber = string;
	}

	/**
	 * @param string
	 */
	public void setShowAllRevisions(String string) {
		showAllRevisions = string;
	}

	/**
	 * @param string
	 */
	public void setShowInventory(String string) {
		showInventory = string;
	}

	/**
	 * @param string
	 */
	public void setSpecificationCode(String string) {
		specificationCode = string;
	}

	/**
	 * @param integer
	 */
	public void setStartAt(Integer integer) {
		startAt = integer;
	}

	/**
	 * @param string
	 */
	public void setStatusCode(String string) {
		statusCode = string;
	}

	/**
	 * @return
	 */
	public String getFruitVarietyList() {
		return fruitVarietyList;
	}

	/**
	 * @param string
	 */
	public void setFruitVarietyList(String string) {
		fruitVarietyList = string;
	}

	/**
	 * @return
	 */
	public String getSpecificationType() {
		return specificationType;
	}

	/**
	 * @param string
	 */
	public void setSpecificationType(String string) {
		specificationType = string;
	}
	/**
	 * Used to test creation of class.
	**/	
	public String toString() {
		
		return new String(
		"classification: " 		+ classification		+ "\n" +
		"companyNumber: "      	+ companyNumber 		+ "\n" +
		"customerNumber:" 		+ customerNumber 		+ "\n" +
		"customerStatus: "    	+ customerStatus    	+ "\n" +
		"fruitVariety: "    	+ fruitVariety     		+ "\n" +
		"orderByField: "    	+ orderByField  	    + "\n" +	
		"orderByStyle: "        + orderByStyle     		+ "\n" + 
		"resourceNumber: " 		+ resourceNumber		+ "\n" +
		"showAllRevisions: " 	+ showAllRevisions 		+ "\n" +
		"showInventory: " 		+ showInventory			+ "\n" +
		"specificationCode: " 	+ specificationCode 	+ "\n" +
		"specificationDate: "	+ specificationDate		+ "\n" +
		"specificationType: " 	+ specificationType 	+ "\n" +
		"startAt: " 			+ startAt				+ "\n" +
		"statusCode: " 			+ statusCode 			+ "\n");																					
	}
	/**
	 * @return
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * @param string
	 */
	public void setClassification(String string) {
		classification = string;
	}

	/**
	 * @return
	 */
	public String getShowCustomers() {
		return showCustomers;
	}

	/**
	 * @param string
	 */
	public void setShowCustomers(String string) {
		showCustomers = string;
	}

	/**
	 * @return
	 */
	public String getSpecificationDate() {
		return specificationDate;
	}

	/**
	 * @param string
	 */
	public void setSpecificationDate(String string) {
		specificationDate = string;
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
	 * @return
	 */
	public Vector getAttributeCodes() {
		return attributeCodes;
	}

	/**
	 * @param vector
	 */
	public void setAttributeCodes(Vector vector) {
		attributeCodes = vector;
	}

	/**
	 * @return
	 */
	public String getGeneralDescription() {
		return generalDescription;
	}

	/**
	 * @param string
	 */
	public void setGeneralDescription(String string) {
		generalDescription = string;
	}

}
