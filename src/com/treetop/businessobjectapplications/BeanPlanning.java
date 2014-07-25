/*
 * Created on March 19, 2008
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

/**
 * @author twalto
 *
 * Bean to combine everything to send to the servlet
 */
public class BeanPlanning {

	protected	String errorMessage = "";

  // Vector of Business Object PlannedOrder, Loaded into Sections
	
	protected	Vector 	rawFruit209Main = new Vector(); // Selah 
	protected	Vector 	rawFruit220Main = new Vector();	// Cashmere 
	protected	Vector 	rawFruit230Main = new Vector(); // Wenatchee
	protected	Vector 	rawFruit240Main = new Vector(); // Ross	
	protected	Vector 	rawFruit490Main = new Vector();	// Fresh Slice 
	protected	Vector 	rawFruit469Main = new Vector(); // Prosser
	
	protected	Vector  rfInventory = new Vector();
	protected	Vector  rfPurchaseOrders = new Vector();
	
	/**
	 *  // Constructor
	 */
	public BeanPlanning() {
		super();
	}
	/**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage The errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
/**
 * @return Returns the rawFruit209Main.
 */
public Vector getRawFruit209Main() {
	return rawFruit209Main;
}
/**
 * @param rawFruit209Main The rawFruit209Main to set.
 */
public void setRawFruit209Main(Vector rawFruit209Main) {
	this.rawFruit209Main = rawFruit209Main;
}
	/**
	 * @return Returns the rawFruit220Main.
	 */
	public Vector getRawFruit220Main() {
		return rawFruit220Main;
	}
	/**
	 * @param rawFruit220Main The rawFruit220Main to set.
	 */
	public void setRawFruit220Main(Vector rawFruit220Main) {
		this.rawFruit220Main = rawFruit220Main;
	}
	/**
	 * @return Returns the rawFruit230Main.
	 */
	public Vector getRawFruit230Main() {
		return rawFruit230Main;
	}
	/**
	 * @param rawFruit230Main The rawFruit230Main to set.
	 */
	public void setRawFruit230Main(Vector rawFruit230Main) {
		this.rawFruit230Main = rawFruit230Main;
	}
	/**
	 * @return Returns the rawFruit240Main.
	 */
	public Vector getRawFruit240Main() {
		return rawFruit240Main;
	}
	/**
	 * @param rawFruit240Main The rawFruit240Main to set.
	 */
	public void setRawFruit240Main(Vector rawFruit240Main) {
		this.rawFruit240Main = rawFruit240Main;
	}
	/**
	 * @return Returns the rawFruit469Main.
	 */
	public Vector getRawFruit469Main() {
		return rawFruit469Main;
	}
	/**
	 * @param rawFruit469Main The rawFruit469Main to set.
	 */
	public void setRawFruit469Main(Vector rawFruit469Main) {
		this.rawFruit469Main = rawFruit469Main;
	}
	/**
	 * @return Returns the rawFruit490Main.
	 */
	public Vector getRawFruit490Main() {
		return rawFruit490Main;
	}
	/**
	 * @param rawFruit490Main The rawFruit490Main to set.
	 */
	public void setRawFruit490Main(Vector rawFruit490Main) {
		this.rawFruit490Main = rawFruit490Main;
	}
	/**
	 * @return Returns the rfInventory.
	 */
	public Vector getRfInventory() {
		return rfInventory;
	}
	/**
	 * @param rfInventory The rfInventory to set.
	 */
	public void setRfInventory(Vector rfInventory) {
		this.rfInventory = rfInventory;
	}
	/**
	 * @return Returns the rfPurchaseOrders.
	 */
	public Vector getRfPurchaseOrders() {
		return rfPurchaseOrders;
	}
	/**
	 * @param rfPurchaseOrders The rfPurchaseOrders to set.
	 */
	public void setRfPurchaseOrders(Vector rfPurchaseOrders) {
		this.rfPurchaseOrders = rfPurchaseOrders;
	}
}
