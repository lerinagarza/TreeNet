/*
 * Created on January 18, 2008
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.SalesOrder;

/**
 * @author twalto
 *
 * Bean to combine everything to send to the servlet
 */
public class BeanPromotion {

	protected	Vector	listPromotionDetail = new Vector();
	protected   SalesOrder soClass = new SalesOrder();
	
	/**
	 *  // Constructor
	 */
	public BeanPromotion() {
		super();

	}
	/**
	 * @return Returns the listPromotionDetail.
	 */
	public Vector getListPromotionDetail() {
		return this.listPromotionDetail;
	}
	/**
	 * @param listPromotionDetail The listPromotionDetail to set.
	 */
	public void setListPromotionDetail(Vector listPromotionDetail) {
		this.listPromotionDetail = listPromotionDetail;
	}
	/**
	 * @return Returns the soClass.
	 */
	public SalesOrder getSoClass() {
		return soClass;
	}
	/**
	 * @param soClass The soClass to set.
	 */
	public void setSoClass(SalesOrder soClass) {
		this.soClass = soClass;
	}
}
