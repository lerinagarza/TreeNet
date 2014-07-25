/*
 * Created on December 30, 2008
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 * General Facility Information
 */
public class Facility{
	
	protected	String		facility			= ""; 
	protected	String		facilityDescription	= ""; 
	
	/**
	 *  // Constructor
	 */
	public Facility() {
		super();

	}
	/**
	 * @return Returns the facility.
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility The facility to set.
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return Returns the facilityDescription.
	 */
	public String getFacilityDescription() {
		return facilityDescription;
	}
	/**
	 * @param facilityDescription The facilityDescription to set.
	 */
	public void setFacilityDescription(String facilityDescription) {
		this.facilityDescription = facilityDescription;
	}
}
