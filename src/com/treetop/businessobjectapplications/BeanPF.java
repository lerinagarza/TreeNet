/*
 * Created on August 17, 2009
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
public class BeanPF {

	protected	Vector	listPFGroup 		= new Vector();
	protected	Vector	listPFProduct	    = new Vector();
	protected	Vector	listPFSchedule      = new Vector();
	protected   Vector  listPFTolerance		= new Vector();
	protected	Vector	listPFUOMConversion	= new Vector();
	
	
	/**
	 *  // Constructor
	 */
	public BeanPF() {
		super();

	}


	public Vector getListPFGroup() {
		return listPFGroup;
	}


	public void setListPFGroup(Vector listPFGroup) {
		this.listPFGroup = listPFGroup;
	}


	public Vector getListPFProduct() {
		return listPFProduct;
	}


	public void setListPFProduct(Vector listPFProduct) {
		this.listPFProduct = listPFProduct;
	}


	public Vector getListPFSchedule() {
		return listPFSchedule;
	}


	public void setListPFSchedule(Vector listPFSchedule) {
		this.listPFSchedule = listPFSchedule;
	}


	public Vector getListPFTolerance() {
		return listPFTolerance;
	}


	public void setListPFTolerance(Vector listPFTolerance) {
		this.listPFTolerance = listPFTolerance;
	}


	public Vector getListPFUOMConversion() {
		return listPFUOMConversion;
	}


	public void setListPFUOMConversion(Vector listPFUOMConversion) {
		this.listPFUOMConversion = listPFUOMConversion;
	}
}
