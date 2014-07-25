/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Store Attribute Value Related Information --
 *     Sometimes for Item Related, Lot Related or  
 *     just the values assigned to an attribute
 */
public class AttributeValue extends Attribute {
	
	protected	Lot			lotObject					  = new Lot();
	protected	String		itemNumber					  = "";
	protected	String		value						  = "";
	
	/**
	 *  // Constructor
	 */
	public AttributeValue() {
		super();

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
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return Returns the lotObject.
	 */
	public Lot getLotObject() {
		return lotObject;
	}
	/**
	 * @param lotObject The lotObject to set.
	 */
	public void setLotObject(Lot lotObject) {
		this.lotObject = lotObject;
	}
}
