/*
 * Created on April 4, 2012
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.Lot;
import com.treetop.businessobjects.ManufacturingOrder;

/**
 * @author Teri Walton
 *   Created to replace the BeanCustomPack (eventually)
 *   
 * Store Contract Manufacturing Information --
 *    Use in conjunction mainly with ServiceContractManufacturing 
 *     
 */
public class BeanContractManufacturing {

	protected   ManufacturingOrder	moHeader	 = new ManufacturingOrder();
	protected   String 				tempCONumber = "";
	// List lots tied to the specific MO Header
	protected	Vector<Lot>	    listLots = new Vector<Lot>();
	protected	Vector<String>	listAttr = new Vector<String>();
	
	/**
	 *  // Constructor
	 */
	public BeanContractManufacturing() {
		super();

	}

	public ManufacturingOrder getMoHeader() {
		return moHeader;
	}

	public void setMoHeader(ManufacturingOrder moHeader) {
		this.moHeader = moHeader;
	}

	public Vector<Lot> getListLots() {
		return listLots;
	}

	public void setListLots(Vector<Lot> listLots) {
		this.listLots = listLots;
	}

	public Vector<String> getListAttr() {
		return listAttr;
	}

	public void setListAttr(Vector<String> listAttr) {
		this.listAttr = listAttr;
	}

	public String getTempCONumber() {
		return tempCONumber;
	}

	public void setTempCONumber(String tempCONumber) {
		this.tempCONumber = tempCONumber;
	}
}
