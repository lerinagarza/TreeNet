/*
 * Created on November 11, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit PO Information
 * 		// Generally Loaded from file: GRPCPO
 */
public class RawFruitPO extends RawFruitLoad{
	
	protected	String		scaleSequence		= ""; //G3SEQ#
	protected	String		poNumber			= ""; //G3PO#
	protected	String		poLineNumber		= ""; //G3PLIN#
	protected	String		poCreate			= ""; //G3CPO
	protected	String		poAcceptedWeight	= ""; //G3AWGT
	protected	String		poRejectedWeight	= ""; //G3RWGT
	protected	String		poTotalWeight		= ""; //G3TWGT
	protected	String		poAcceptedBins		= ""; //G3ABINS
	protected	String		poRejectedBins		= ""; //G3RBINS
	protected	String		poTotalBins			= ""; //G3TBINS
	protected	String		poComplete			= ""; //G3COMP
	protected	String		poUser				= ""; //G3USER
	protected	String		poDate				= ""; //G3DATE
	protected	String		poTime				= ""; //G3TIME
	protected	String		poUpdateUser		= ""; 		
	protected	String		poPostingFlags		= ""; //G3POSTF
	
	protected	Warehouse	poWarehouseFacilty	= new Warehouse();
			// G3FACI
			// G3WHSE
	
	protected	Supplier	poSupplier				= new Supplier(); //G3SUPP
	
	protected	Vector		listLots			= new Vector(); // of RawFruitLot Values
	
	/**
	 *  // Constructor
	 */
	public RawFruitPO() {
		super();

	}
	/**
	 * @return Returns the poAcceptedBins.
	 */
	public String getPoAcceptedBins() {
		return poAcceptedBins;
	}
	/**
	 * @param poAcceptedBins The poAcceptedBins to set.
	 */
	public void setPoAcceptedBins(String poAcceptedBins) {
		this.poAcceptedBins = poAcceptedBins;
	}
	/**
	 * @return Returns the poAcceptedWeight.
	 */
	public String getPoAcceptedWeight() {
		return poAcceptedWeight;
	}
	/**
	 * @param poAcceptedWeight The poAcceptedWeight to set.
	 */
	public void setPoAcceptedWeight(String poAcceptedWeight) {
		this.poAcceptedWeight = poAcceptedWeight;
	}
	/**
	 * @return Returns the poNumber.
	 */
	public String getPoNumber() {
		return poNumber;
	}
	/**
	 * @param poNumber The poNumber to set.
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	/**
	 * @return Returns the poRejectedBins.
	 */
	public String getPoRejectedBins() {
		return poRejectedBins;
	}
	/**
	 * @param poRejectedBins The poRejectedBins to set.
	 */
	public void setPoRejectedBins(String poRejectedBins) {
		this.poRejectedBins = poRejectedBins;
	}
	/**
	 * @return Returns the poRejectedWeight.
	 */
	public String getPoRejectedWeight() {
		return poRejectedWeight;
	}
	/**
	 * @param poRejectedWeight The poRejectedWeight to set.
	 */
	public void setPoRejectedWeight(String poRejectedWeight) {
		this.poRejectedWeight = poRejectedWeight;
	}
	/**
	 * @return Returns the poTotalWeight.
	 */
	public String getPoTotalWeight() {
		return poTotalWeight;
	}
	/**
	 * @param poTotalWeight The poTotalWeight to set.
	 */
	public void setPoTotalWeight(String poTotalWeight) {
		this.poTotalWeight = poTotalWeight;
	}
	/**
	 * @return Returns the scaleSequence.
	 */
	public String getScaleSequence() {
		return scaleSequence;
	}
	/**
	 * @param scaleSequence The scaleSequence to set.
	 */
	public void setScaleSequence(String scaleSequence) {
		this.scaleSequence = scaleSequence;
	}
	/**
	 * @return Returns the listLots.
	 */
	public Vector getListLots() {
		return listLots;
	}
	/**
	 * @param listLots The listLots to set.
	 */
	public void setListLots(Vector listLots) {
		this.listLots = listLots;
	}
	/**
	 * @return Returns the poTotalBins.
	 */
	public String getPoTotalBins() {
		return poTotalBins;
	}
	/**
	 * @param poTotalBins The poTotalBins to set.
	 */
	public void setPoTotalBins(String poTotalBins) {
		this.poTotalBins = poTotalBins;
	}
	/**
	 * @return Returns the poComplete.
	 */
	public String getPoComplete() {
		return poComplete;
	}
	/**
	 * @param poComplete The poComplete to set.
	 */
	public void setPoComplete(String poComplete) {
		this.poComplete = poComplete;
	}
	/**
	 * @return Returns the poCreate.
	 */
	public String getPoCreate() {
		return poCreate;
	}
	/**
	 * @param poCreate The poCreate to set.
	 */
	public void setPoCreate(String poCreate) {
		this.poCreate = poCreate;
	}
	/**
	 * @return Returns the poDate.
	 */
	public String getPoDate() {
		return poDate;
	}
	/**
	 * @param poDate The poDate to set.
	 */
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	/**
	 * @return Returns the poLineNumber.
	 */
	public String getPoLineNumber() {
		return poLineNumber;
	}
	/**
	 * @param poLineNumber The poLineNumber to set.
	 */
	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
	/**
	 * @return Returns the poTime.
	 */
	public String getPoTime() {
		return poTime;
	}
	/**
	 * @param poTime The poTime to set.
	 */
	public void setPoTime(String poTime) {
		this.poTime = poTime;
	}
	/**
	 * @return Returns the poUser.
	 */
	public String getPoUser() {
		return poUser;
	}
	/**
	 * @param poUser The poUser to set.
	 */
	public void setPoUser(String poUser) {
		this.poUser = poUser;
	}
			/**
			 * @return Returns the poSupplier.
			 */
			public Supplier getPoSupplier() {
				return poSupplier;
			}
			/**
			 * @param poSupplier The poSupplier to set.
			 */
			public void setPoSupplier(Supplier poSupplier) {
				this.poSupplier = poSupplier;
			}
	/**
	 * @return Returns the poUpdateUser.
	 */
	public String getPoUpdateUser() {
		return poUpdateUser;
	}
	/**
	 * @param poUpdateUser The poUpdateUser to set.
	 */
	public void setPoUpdateUser(String poUpdateUser) {
		this.poUpdateUser = poUpdateUser;
	}
	public Warehouse getPoWarehouseFacilty() {
		return poWarehouseFacilty;
	}
	public void setPoWarehouseFacilty(Warehouse poWarehouseFacilty) {
		this.poWarehouseFacilty = poWarehouseFacilty;
	}
	public String getPoPostingFlags() {
		return poPostingFlags;
	}
	public void setPoPostingFlags(String poPostingFlags) {
		this.poPostingFlags = poPostingFlags;
	}
}
