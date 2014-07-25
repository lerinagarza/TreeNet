/*
 * Created on October 15, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Item Facility Information
 */
public class ItemFacility extends Item{
	
	protected	String		facilityNumber			= "";
	protected	String		facilityName			= "";	

	/**
	 *  // Constructor
	 */
	public ItemFacility() {
		super();

	}
	/**
	 * @return Returns the facilityName.
	 */
	public String getFacilityName() {
		return facilityName;
	}
	/**
	 * @param facilityName The facilityName to set.
	 */
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	/**
	 * @return Returns the facilityNumber.
	 */
	public String getFacilityNumber() {
		return facilityNumber;
	}
	/**
	 * @param facilityNumber The facilityNumber to set.
	 */
	public void setFacilityNumber(String facilityNumber) {
		this.facilityNumber = facilityNumber;
	}
}
