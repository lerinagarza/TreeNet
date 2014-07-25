/*
 * Created on October 9, 2007
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
public class BeanCOA {

	protected	Vector	listCOADetailAttributes = new Vector();
	protected	Vector	listAttrValues		    = new Vector();
	protected	Vector	listSOLineItems	        = new Vector();
	protected   Vector  listDOLineItems			= new Vector();
	
	
	/**
	 *  // Constructor
	 */
	public BeanCOA() {
		super();

	}
	/**
	 * @return Returns the listAttrValues.
	 */
	public Vector getListAttrValues() {
		return listAttrValues;
	}
	/**
	 * @param listAttrValues The listAttrValues to set.
	 */
	public void setListAttrValues(Vector listAttrValues) {
		this.listAttrValues = listAttrValues;
	}
	/**
	 * @return Returns the listCOADetailAttributes
	 */
	public Vector getListCOADetailAttributes() {
		return listCOADetailAttributes;
	}
	/**
	 * @param listcoaDetailAttri The listCOADetailAttributes to set.
	 */
	public void setListCOADetailAttributes(Vector listCOADetailAttributes) {
		this.listCOADetailAttributes = listCOADetailAttributes;
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
	 * @return Returns the listDOLineItems.
	 */
	public Vector getListDOLineItems() {
		return listDOLineItems;
	}
	/**
	 * @param listDOLineItems The listDOLineItems to set.
	 */
	public void setListDOLineItems(Vector listDOLineItems) {
		this.listDOLineItems = listDOLineItems;
	}
}
