/*
 * Created on July 14, 2010
 *
 */
package com.treetop.businessobjects;


/**
 * @author thaile
 *
 * Additional Information relating to the Sales Order 
 */
public class DistributionOrderDetail extends DistributionOrder {
	
	protected	String		orderResponsible			= "";
	protected	String		lastModifiedBy				= "";
	protected	String		actualShipDate				= "";

	/**
	 *  // Constructor
	 */
	public DistributionOrderDetail() {
		super();

	}
	/**
	 * @return Returns the lastModifiedBy.
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	/**
	 * @param lastModifiedBy The lastModifiedBy to set.
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	/**
	 * @return Returns the orderResponsible.
	 */
	public String getOrderResponsible() {
		return orderResponsible;
	}
	/**
	 * @param orderResponsible The orderResponsible to set.
	 */
	public void setOrderResponsible(String orderResponsible) {
		this.orderResponsible = orderResponsible;
	}
	public String getActualShipDate() {
		return actualShipDate;
	}
	public void setActualShipDate(String actualShipDate) {
		this.actualShipDate = actualShipDate;
	}
}
