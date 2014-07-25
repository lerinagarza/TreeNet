/*
 * Created on May 9, 2008
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;
import com.treetop.businessobjects.*;

/**
 * @author twalto
 *
 * Bean to combine everything to send to the servlet
 */
public class BeanInventory {

	protected	Vector	listSOLineItems	        = new Vector();
	protected	Vector	byItemVectorOfInventory = new Vector();
	protected	Vector	listOfInventory			= new Vector();
	protected	Vector	listOfLots				= new Vector();
	
	protected	Vector<AttributeValue> listAttributes = new Vector<AttributeValue>();
	protected	String	returnMessage			= "";
	
	protected	ManufacturingOrder	moHeader	= new ManufacturingOrder();
	
	/**
	 *  // Constructor
	 */
	public BeanInventory() {
		super();

	}
	/**
	 * @return Returns the listSOLineItems.
	 */
	public Vector getListSOLineItems() {
		return listSOLineItems;
	}
	/**
	 * @param listSOLineItems The listSOLineItems to set.
	 */
	public void setListSOLineItems(Vector listSOLineItems) {
		this.listSOLineItems = listSOLineItems;
	}
	/**
	 * @return Returns the byItemVectorOfInventory.
	 */
	public Vector getByItemVectorOfInventory() {
		return byItemVectorOfInventory;
	}
	/**
	 * @param byItemVectorOfInventory The byItemVectorOfInventory to set.
	 */
	public void setByItemVectorOfInventory(Vector byItemVectorOfInventory) {
		this.byItemVectorOfInventory = byItemVectorOfInventory;
	}
	/**
	 * @return Returns the returnMessage.
	 */
	public String getReturnMessage() {
		return returnMessage;
	}
	/**
	 * @param returnMessage The returnMessage to set.
	 */
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	/**
	 * @return Returns the listOfInventory.
	 */
	public Vector getListOfInventory() {
		return listOfInventory;
	}
	/**
	 * @param listOfInventory The listOfInventory to set.
	 */
	public void setListOfInventory(Vector listOfInventory) {
		this.listOfInventory = listOfInventory;
	}
	public Vector getListOfLots() {
		return listOfLots;
	}
	public void setListOfLots(Vector listOfLots) {
		this.listOfLots = listOfLots;
	}
	public ManufacturingOrder getMoHeader() {
		return moHeader;
	}
	public void setMoHeader(ManufacturingOrder moHeader) {
		this.moHeader = moHeader;
	}
	public Vector<AttributeValue> getListAttributes() {
		return listAttributes;
	}
	public void setListAttributes(Vector<AttributeValue> listAttributes) {
		this.listAttributes = listAttributes;
	}
}
