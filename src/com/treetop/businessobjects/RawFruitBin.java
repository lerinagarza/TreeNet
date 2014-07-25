/*
 * Created on November 11, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit Bin Information
 * 			// Generally Loaded from file: GRPCBINS
 * 		
 */
public class RawFruitBin extends RawFruitLoad{
	
	protected	String		binType			= ""; //G2BINTYP
	protected	String		binDescription 	= ""; //G2BINDSC
	protected	String		binWeight		= ""; //G2BINW
	protected	String		binQuantity		= ""; //G2BINCNT
	protected	String		binTotalWeight	= ""; //G2TWBIN
	protected	String		binUser			= ""; //G2USER
	protected	String		binDate			= ""; //G2DATE
	protected	String		binTime			= ""; //G2TIME
	
	/**
	 *  // Constructor
	 */
	public RawFruitBin() {
		super();

	}
	/**
	 * @return Returns the binDescription.
	 */
	public String getBinDescription() {
		return binDescription;
	}
	/**
	 * @param binDescription The binDescription to set.
	 */
	public void setBinDescription(String binDescription) {
		this.binDescription = binDescription;
	}
	/**
	 * @return Returns the binQuantity.
	 */
	public String getBinQuantity() {
		return binQuantity;
	}
	/**
	 * @param binQuantity The binQuantity to set.
	 */
	public void setBinQuantity(String binQuantity) {
		this.binQuantity = binQuantity;
	}
	/**
	 * @return Returns the binType.
	 */
	public String getBinType() {
		return binType;
	}
	/**
	 * @param binType The binType to set.
	 */
	public void setBinType(String binType) {
		this.binType = binType;
	}
	/**
	 * @return Returns the binWeight.
	 */
	public String getBinWeight() {
		return binWeight;
	}
	/**
	 * @param binWeight The binWeight to set.
	 */
	public void setBinWeight(String binWeight) {
		this.binWeight = binWeight;
	}
	/**
	 * @return Returns the binDate.
	 */
	public String getBinDate() {
		return binDate;
	}
	/**
	 * @param binDate The binDate to set.
	 */
	public void setBinDate(String binDate) {
		this.binDate = binDate;
	}
	/**
	 * @return Returns the binTime.
	 */
	public String getBinTime() {
		return binTime;
	}
	/**
	 * @param binTime The binTime to set.
	 */
	public void setBinTime(String binTime) {
		this.binTime = binTime;
	}
	/**
	 * @return Returns the binTotalWeight.
	 */
	public String getBinTotalWeight() {
		return binTotalWeight;
	}
	/**
	 * @param binTotalWeight The binTotalWeight to set.
	 */
	public void setBinTotalWeight(String binTotalWeight) {
		this.binTotalWeight = binTotalWeight;
	}
	/**
	 * @return Returns the binUser.
	 */
	public String getBinUser() {
		return binUser;
	}
	/**
	 * @param binUser The binUser to set.
	 */
	public void setBinUser(String binUser) {
		this.binUser = binUser;
	}
}
