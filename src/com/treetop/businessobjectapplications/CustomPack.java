/*
 * Created on July 28, 2008
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.DateTime;

/**
 * @author twalto
 *
 * Store Custom Pack Related Information --
 *     
 */
public class CustomPack {
          
	protected   String		itemNumber			= "";
	protected	String		itemAlias			= "";
	protected	String		itemDescription		= "";
	protected	String		facilityCode		= "1AS";
	protected	String		onHandQuantity		= "0";
	protected	DateTime	manufactureDate		= null;
	protected	DateTime	receivingDate		= null;
	
	protected	Vector		listOfLots			= new Vector();
		
	/**
	 *  // Constructor
	 */
	public CustomPack() {
		super();

	}
	/**
	 * @return Returns the facilityCode.
	 */
	public String getFacilityCode() {
		return facilityCode;
	}
	/**
	 * @param facilityCode The facilityCode to set.
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * @return Returns the itemAlias.
	 */
	public String getItemAlias() {
		return itemAlias;
	}
	/**
	 * @param itemAlias The itemAlias to set.
	 */
	public void setItemAlias(String itemAlias) {
		this.itemAlias = itemAlias;
	}
	/**
	 * @return Returns the itemDescription.
	 */
	public String getItemDescription() {
		return itemDescription;
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
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the listOfLots.
	 */
	public Vector getListOfLots() {
		return listOfLots;
	}
	/**
	 * @param listOfLots The listOfLots to set.
	 */
	public void setListOfLots(Vector listOfLots) {
		this.listOfLots = listOfLots;
	}
	/**
	 * @return Returns the manufactureDate.
	 */
	public DateTime getManufactureDate() {
		return manufactureDate;
	}
	/**
	 * @param manufactureDate The manufactureDate to set.
	 */
	public void setManufactureDate(DateTime manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	/**
	 * @return Returns the onHandQuantity.
	 */
	public String getOnHandQuantity() {
		return onHandQuantity;
	}
	/**
	 * @param onHandQuantity The onHandQuantity to set.
	 */
	public void setOnHandQuantity(String onHandQuantity) {
		this.onHandQuantity = onHandQuantity;
	}
	/**
	 * @return Returns the receivingDate.
	 */
	public DateTime getReceivingDate() {
		return receivingDate;
	}
	/**
	 * @param receivingDate The receivingDate to set.
	 */
	public void setReceivingDate(DateTime receivingDate) {
		this.receivingDate = receivingDate;
	}
}
