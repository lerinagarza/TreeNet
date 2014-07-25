/*
 * Created on November 11, 2008
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
public class BeanRawFruit {

	protected	RawFruitLoad	rfLoad 	= new RawFruitLoad();
	protected   Vector  		comments	= new Vector();	//cast as KeyValue
	protected	Vector			listRFPO 	= new Vector(); // Vector of RawFruitPO
	protected	Vector			listRFBin	= new Vector(); // Vector of RawFruitBin
	protected	Vector			listRFLot	= new Vector(); // Vector of RawFruitLot
	
	protected	RawFruitLot		rfLot	= new RawFruitLot();
	protected	Vector			listLotPayment = new Vector(); // Vector of RawFruitPayment
	protected	Vector			listLotPaymentSpecialCharges	= 	new Vector(); // Vector of RawFruitSpecialCharges
	// for use on the List page
	protected	Vector			listLoads  = new Vector(); // Vector of RawFruitLoad
	
	/**
	 *  // Constructor
	 */
	public BeanRawFruit() {
		super();

	}
	/**
	 * @return Returns the listLotPayment.
	 */
	public Vector getListLotPayment() {
		return listLotPayment;
	}
	/**
	 * @param listLotPayment The listLotPayment to set.
	 */
	public void setListLotPayment(Vector listLotPayment) {
		this.listLotPayment = listLotPayment;
	}
	/**
	 * @return Returns the listLotPaymentSpecialCharges.
	 */
	public Vector getListLotPaymentSpecialCharges() {
		return listLotPaymentSpecialCharges;
	}
	/**
	 * @param listLotPaymentSpecialCharges The listLotPaymentSpecialCharges to set.
	 */
	public void setListLotPaymentSpecialCharges(
			Vector listLotPaymentSpecialCharges) {
		this.listLotPaymentSpecialCharges = listLotPaymentSpecialCharges;
	}
	/**
	 * @return Returns the listRFBin.
	 */
	public Vector getListRFBin() {
		return listRFBin;
	}
	/**
	 * @param listRFBin The listRFBin to set.
	 */
	public void setListRFBin(Vector listRFBin) {
		this.listRFBin = listRFBin;
	}
	/**
	 * @return Returns the listRFLot.
	 */
	public Vector getListRFLot() {
		return listRFLot;
	}
	/**
	 * @param listRFLot The listRFLot to set.
	 */
	public void setListRFLot(Vector listRFLot) {
		this.listRFLot = listRFLot;
	}
	/**
	 * @return Returns the listRFPO.
	 */
	public Vector getListRFPO() {
		return listRFPO;
	}
	/**
	 * @param listRFPO The listRFPO to set.
	 */
	public void setListRFPO(Vector listRFPO) {
		this.listRFPO = listRFPO;
	}
	/**
	 * @return Returns the rfLoad.
	 */
	public RawFruitLoad getRfLoad() {
		return rfLoad;
	}
	/**
	 * @param rfLoad The rfLoad to set.
	 */
	public void setRfLoad(RawFruitLoad rfLoad) {
		this.rfLoad = rfLoad;
	}
	/**
	 * @return Returns the rfLot.
	 */
	public RawFruitLot getRfLot() {
		return rfLot;
	}
	/**
	 * @param rfLot The rfLot to set.
	 */
	public void setRfLot(RawFruitLot rfLot) {
		this.rfLot = rfLot;
	}
	/**
	 * @return Returns the comments.
	 */
	public Vector getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(Vector comments) {
		this.comments = comments;
	}
	public Vector getListLoads() {
		return listLoads;
	}
	public void setListLoads(Vector listLoads) {
		this.listLoads = listLoads;
	}
}
