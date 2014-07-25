/*
 * Created on Aug 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Contains Global Trade Item Number (GTIN) information.
 * 	
 */
public class GTIN {
				// from File 	prisme01/MSPRUCCN
	String	recordID;					//MSRRCD
	String	status;						//MSRSTS
	String	gtinNumber;					//MSRGTN
	String	gtinDescription;			//MSRSDS
	String	tradeItemUnitDescriptor;	//MSRHLV
	String	brandName;					//MSRBRD
	String	gtinLongDescription;		//MSRLDS
	String	publishToUCCNet;			//MSRPUB
	String	isBaseUnit;					//MSRBSU
	String  isOrderableUnit;			//MSRORU

	
	/**
	 * @return Returns the isBaseUnit.
	 */
	public String getIsBaseUnit() {
		return isBaseUnit;
	}
	/**
	 * @param isBaseUnit The isBaseUnit to set.
	 */
	public void setIsBaseUnit(String isBaseUnit) {
		this.isBaseUnit = isBaseUnit;
	}
	/**
	 * 
	 *
	 */
	public GTIN(){
		super();
	}

	/**
	 * @return
	 */
	public String getGtinDescription() {
		return gtinDescription;
	}

	/**
	 * @return
	 */
	public String getGtinNumber() {
		return gtinNumber;
	}

	/**
	 * @return
	 */
	public String getTradeItemUnitDescriptor() {
		return tradeItemUnitDescriptor;
	}

	/**
	 * @param string
	 */
	public void setGtinDescription(String string) {
		gtinDescription = string;
	}

	/**
	 * @param string
	 */
	public void setGtinNumber(String string) {
		gtinNumber = string;
	}

	/**
	 * @param string
	 */
	public void setTradeItemUnitDescriptor(String string) {
		tradeItemUnitDescriptor = string;
	}

	/**
	 * @return
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @return
	 */
	public String getGtinLongDescription() {
		return gtinLongDescription;
	}

	/**
	 * @return
	 */
	public String getPublishToUCCNet() {
		return publishToUCCNet;
	}

				/**
				 * @return
				 */
				public String getRecordID() {
					return recordID;
				}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param string
	 */
	public void setBrandName(String string) {
		brandName = string;
	}

	/**
	 * @param string
	 */
	public void setGtinLongDescription(String string) {
		gtinLongDescription = string;
	}

	/**
	 * @param string
	 */
	public void setPublishToUCCNet(String string) {
		publishToUCCNet = string;
	}

	/**
	 * @param string
	 */
	public void setRecordID(String string) {
		recordID = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}
	public String getIsOrderableUnit() {
		return isOrderableUnit;
	}
	public void setIsOrderableUnit(String isOrderableUnit) {
		this.isOrderableUnit = isOrderableUnit;
	}


}
