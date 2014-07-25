/*
 * Created on August 17, 2009
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Item;
import com.treetop.businessobjects.Warehouse;

/**
 * @author twalto
 *
 * Store Plant Floor Product Tolerances Related Information --
 *     
 */
public class PFTolerance extends PFProduct{
	
	protected   String		groupName			= "";
	protected	String		groupDescription	= "";
	protected   String		productGroupName	= "";
	protected	String		valueMinimum		= "0";
	protected	String		valueLowerFail		= "0";
	protected	String		valueLowerWarning	= "0";
	protected	String		valueStandard		= "0";
	protected	String		valueMaximum		= "0";
	protected	String		valueUpperFail		= "0";
	protected	String		valueUpperWarning	= "0";
	
	/**
	 *  // Constructor
	 */
	public PFTolerance() {
		super();

	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getValueLowerFail() {
		return valueLowerFail;
	}

	public void setValueLowerFail(String valueLowerFail) {
		this.valueLowerFail = valueLowerFail;
	}

	public String getValueLowerWarning() {
		return valueLowerWarning;
	}

	public void setValueLowerWarning(String valueLowerWarning) {
		this.valueLowerWarning = valueLowerWarning;
	}

	public String getValueMaximum() {
		return valueMaximum;
	}

	public void setValueMaximum(String valueMaximum) {
		this.valueMaximum = valueMaximum;
	}

	public String getValueMinimum() {
		return valueMinimum;
	}

	public void setValueMinimum(String valueMinimum) {
		this.valueMinimum = valueMinimum;
	}

	public String getValueStandard() {
		return valueStandard;
	}

	public void setValueStandard(String valueStandard) {
		this.valueStandard = valueStandard;
	}

	public String getValueUpperFail() {
		return valueUpperFail;
	}

	public void setValueUpperFail(String valueUpperFail) {
		this.valueUpperFail = valueUpperFail;
	}

	public String getValueUpperWarning() {
		return valueUpperWarning;
	}

	public void setValueUpperWarning(String valueUpperWarning) {
		this.valueUpperWarning = valueUpperWarning;
	}
}
