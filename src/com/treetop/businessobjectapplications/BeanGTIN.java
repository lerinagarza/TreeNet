/*
 * Created on Aug 4, 2005
 *
 *   Changes:
 *    Date        Name      Comments
 *  ---------   --------   -------------
 *   6/4/08     TWalton	   Moved from businessobjects, into businessobjectapplications
 * 						   Changed Resource Information to be Item Information
 */
package com.treetop.businessobjectapplications;

import java.util.*;
import com.treetop.businessobjects.*;

/**
 * @author thaile
 *
 * Container class to hold GTIN Screen Application data.
 * 
 */
public class BeanGTIN {
	
	private 		GTIN			gtin;			//GTIN
	private			Vector      	children;		//GTINChild
	private			Vector			views;			//GTINView
	private			GTINDetail		gtinDetail;		//GTINDetail
	private			Item			item;			//Item
	
	// detail only 
	private			Vector			childItems;		//Item
	
	// extra line

	/**
	 * 
	 */
	public BeanGTIN() {
		super();
	}

	/**
	 * @return
	 */
	public GTIN getGtin() {
		return gtin;
	}

	/**
	 * @param resource
	 */
	public void setGtin(GTIN gtinIn) {
		gtin = gtinIn;
	}

	/**
	 * @return
	 */
	public Vector getChildren() {
		return children;
	}

	/**
	 * @param vector
	 */
	public void setChildren(Vector vector) {
		children = vector;
	}

	/**
	 * @return
	 */
	public Vector getViews() {
		return views;
	}

	/**
	 * @param vector
	 */
	public void setViews(Vector vector) {
		views = vector;
	}

	/**
	 * @return
	 */
	public GTINDetail getGtinDetail() {
		return gtinDetail;
	}

	/**
	 * @param detail
	 */
	public void setGtinDetail(GTINDetail detail) {
		gtinDetail = detail;
	}
	/**
	 * @return Returns the childItems.
	 */
	public Vector getChildItems() {
		return childItems;
	}
	/**
	 * @param childItems The childItems to set.
	 */
	public void setChildItems(Vector childItems) {
		this.childItems = childItems;
	}
	/**
	 * @return Returns the item.
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(Item item) {
		this.item = item;
	}
}
