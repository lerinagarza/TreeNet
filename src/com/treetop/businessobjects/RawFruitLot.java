/*
 * Created on November 11, 2008
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 * Raw Fruit Lot Information 
 * 	// Generally Loaded from file: GRPCTICK
 */
public class RawFruitLot extends RawFruitPO{
	
	protected	String		lotSequence				= ""; //G4TSEQ#
	protected	String		harvestYear				= ""; //G4HYEAR
	protected	String		grapeDueDate			= "";
	protected 	String		supplierLoadNumber		= ""; //G4SLOAD
	protected	String		crop					= ""; //G4CROP
	protected	String		organicConventional		= ""; //G4CNVOR
	protected	String		runType					= ""; //G4RUNT
	protected	String		intendedUse				= ""; //G4IUSE
	protected	String		variety					= ""; //G4VAR
	protected   String		countryOfOrigin			= ""; //G4CTYO
	protected	String		additionalVariety		= ""; //G4AVAR
	protected	String		cullsPercent			= ""; //G4CULLP
	protected	String		cullsPounds				= ""; //G4CULLW
	protected	String		calculatedBrixPrice		= ""; //G4BRIXP
	protected	String		lotAcceptedBins25Box	= ""; //G4A25B
	protected	String		lotAcceptedBins30Box	= ""; //G4A30B
	protected	String		lotAcceptedWeight		= ""; //G4ABW
	protected	String		flagAcceptedWeightManual= ""; //G4ABWO
	protected	String		lotAcceptedComments		= ""; //G4ABINC
	protected	String		lotRejectedBins25Box	= ""; //G4R25B
	protected	String		lotRejectedBins30Box	= ""; //G4R30B
	protected	String		lotRejectedWeight		= ""; //G4RBW
	protected	String		flagRejectedWeightManual= ""; //G4RBWO
	protected	String		lotRejectedComments		= ""; //G4RBINC
	protected	String		lotTotalBins25Box		= ""; //G4T25B
	protected	String		lotTotalBins30Box		= ""; //G4T30B
	protected	String		lotTotalWeight			= ""; //G4TBW
	protected	String		averageWeightPerBin		= ""; //G4AVGW
	protected	String		lotUser					= ""; //G4USER
	protected	String		lotDate					= ""; //G4DATE
	protected	String		lotTime					= ""; //G4TIME
	protected	String		lotPostingFlags			= ""; //G4POSTF
	protected	String		lotWriteUpNumber		= ""; //G4WUP -- 9/23/13 TWalton
	
	protected	String		lotNumber				= ""; //G4LOT
	protected	Lot			lotInformation			= new Lot();
				// Facility:  	G4FACI
				// Warehouse: 	G4WHSE
				// Lot:			G4LOT
				// Location:	G4LOC
				// Item:		G4ITEM
				// Brix:		G4BRIX
	protected   String      itemWhseNotValid        = "";
	protected	Supplier	lotSupplier				= new Supplier(); //G4SUPP
	protected	Warehouse	lotWarehouseFacility 	= new Warehouse();
				// Whse & Facility along with Descriptions of Each
	
	protected	Vector		listPayments			= new Vector(); // vector of RawFruitPayment
	
	/**
	 *  // Constructor
	 */
	public RawFruitLot() {
		super();

	}
	/**
	 * @return Returns the averageWeightPerBin.
	 */
	public String getAverageWeightPerBin() {
		return averageWeightPerBin;
	}
	/**
	 * @param averageWeightPerBin The averageWeightPerBin to set.
	 */
	public void setAverageWeightPerBin(String averageWeightPerBin) {
		this.averageWeightPerBin = averageWeightPerBin;
	}
	/**
	 * @return Returns the calculatedBrixPrice.
	 */
	public String getCalculatedBrixPrice() {
		return calculatedBrixPrice;
	}
	/**
	 * @param calculatedBrixPrice The calculatedBrixPrice to set.
	 */
	public void setCalculatedBrixPrice(String calculatedBrixPrice) {
		this.calculatedBrixPrice = calculatedBrixPrice;
	}
	/**
	 * @return Returns the cullsPercent.
	 */
	public String getCullsPercent() {
		return cullsPercent;
	}
	/**
	 * @param cullsPercent The cullsPercent to set.
	 */
	public void setCullsPercent(String cullsPercent) {
		this.cullsPercent = cullsPercent;
	}
	/**
	 * @return Returns the cullsPounds.
	 */
	public String getCullsPounds() {
		return cullsPounds;
	}
	/**
	 * @param cullsPounds The cullsPounds to set.
	 */
	public void setCullsPounds(String cullsPounds) {
		this.cullsPounds = cullsPounds;
	}
	/**
	 * @return Returns the crop.
	 */
	public String getCrop() {
		return crop;
	}
	/**
	 * @param crop The crop to set.
	 */
	public void setCrop(String crop) {
		this.crop = crop;
	}
	/**
	 * @return Returns the flagAcceptedWeightManual.
	 */
	public String getFlagAcceptedWeightManual() {
		return flagAcceptedWeightManual;
	}
	/**
	 * @param flagAcceptedWeightManual The flagAcceptedWeightManual to set.
	 */
	public void setFlagAcceptedWeightManual(String flagAcceptedWeightManual) {
		this.flagAcceptedWeightManual = flagAcceptedWeightManual;
	}
	/**
	 * @return Returns the flagRejectedWeightManual.
	 */
	public String getFlagRejectedWeightManual() {
		return flagRejectedWeightManual;
	}
	/**
	 * @param flagRejectedWeightManual The flagRejectedWeightManual to set.
	 */
	public void setFlagRejectedWeightManual(String flagRejectedWeightManual) {
		this.flagRejectedWeightManual = flagRejectedWeightManual;
	}
	/**
	 * @return Returns the harvestYear.
	 */
	public String getHarvestYear() {
		return harvestYear;
	}
	/**
	 * @param harvestYear The harvestYear to set.
	 */
	public void setHarvestYear(String harvestYear) {
		this.harvestYear = harvestYear;
	}
	/**
	 * @return Returns the listPayments.
	 */
	public Vector getListPayments() {
		return listPayments;
	}
	/**
	 * @param listPayments The listPayments to set.
	 */
	public void setListPayments(Vector listPayments) {
		this.listPayments = listPayments;
	}
	/**
	 * @return Returns the lotAcceptedBins25Box.
	 */
	public String getLotAcceptedBins25Box() {
		return lotAcceptedBins25Box;
	}
	/**
	 * @param lotAcceptedBins25Box The lotAcceptedBins25Box to set.
	 */
	public void setLotAcceptedBins25Box(String lotAcceptedBins25Box) {
		this.lotAcceptedBins25Box = lotAcceptedBins25Box;
	}
	/**
	 * @return Returns the lotAcceptedBins30Box.
	 */
	public String getLotAcceptedBins30Box() {
		return lotAcceptedBins30Box;
	}
	/**
	 * @param lotAcceptedBins30Box The lotAcceptedBins30Box to set.
	 */
	public void setLotAcceptedBins30Box(String lotAcceptedBins30Box) {
		this.lotAcceptedBins30Box = lotAcceptedBins30Box;
	}
	/**
	 * @return Returns the lotAcceptedWeight.
	 */
	public String getLotAcceptedWeight() {
		return lotAcceptedWeight;
	}
	/**
	 * @param lotAcceptedWeight The lotAcceptedWeight to set.
	 */
	public void setLotAcceptedWeight(String lotAcceptedWeight) {
		this.lotAcceptedWeight = lotAcceptedWeight;
	}
	/**
	 * @return Returns the lotInformation.
	 */
	public Lot getLotInformation() {
		return lotInformation;
	}
	/**
	 * @param lotInformation The lotInformation to set.
	 */
	public void setLotInformation(Lot lotInformation) {
		this.lotInformation = lotInformation;
	}
	/**
	 * @return Returns the lotRejectedBins25Box.
	 */
	public String getLotRejectedBins25Box() {
		return lotRejectedBins25Box;
	}
	/**
	 * @param lotRejectedBins25Box The lotRejectedBins25Box to set.
	 */
	public void setLotRejectedBins25Box(String lotRejectedBins25Box) {
		this.lotRejectedBins25Box = lotRejectedBins25Box;
	}
	/**
	 * @return Returns the lotRejectedBins30Box.
	 */
	public String getLotRejectedBins30Box() {
		return lotRejectedBins30Box;
	}
	/**
	 * @param lotRejectedBins30Box The lotRejectedBins30Box to set.
	 */
	public void setLotRejectedBins30Box(String lotRejectedBins30Box) {
		this.lotRejectedBins30Box = lotRejectedBins30Box;
	}
	/**
	 * @return Returns the lotRejectedWeight.
	 */
	public String getLotRejectedWeight() {
		return lotRejectedWeight;
	}
	/**
	 * @param lotRejectedWeight The lotRejectedWeight to set.
	 */
	public void setLotRejectedWeight(String lotRejectedWeight) {
		this.lotRejectedWeight = lotRejectedWeight;
	}
	/**
	 * @return Returns the lotSupplier.
	 */
	public Supplier getLotSupplier() {
		return lotSupplier;
	}
	/**
	 * @param lotSupplier The lotSupplier to set.
	 */
	public void setLotSupplier(Supplier lotSupplier) {
		this.lotSupplier = lotSupplier;
	}
	/**
	 * @return Returns the lotTotalWeight.
	 */
	public String getLotTotalWeight() {
		return lotTotalWeight;
	}
	/**
	 * @param lotTotalWeight The lotTotalWeight to set.
	 */
	public void setLotTotalWeight(String lotTotalWeight) {
		this.lotTotalWeight = lotTotalWeight;
	}
	/**
	 * @return Returns the organicConventional.
	 */
	public String getOrganicConventional() {
		return organicConventional;
	}
	/**
	 * @param organicConventional The organicConventional to set.
	 */
	public void setOrganicConventional(String organicConventional) {
		this.organicConventional = organicConventional;
	}
	/**
	 * @return Returns the supplierLoadNumber.
	 */
	public String getSupplierLoadNumber() {
		return supplierLoadNumber;
	}
	/**
	 * @param supplierLoadNumber The supplierLoadNumber to set.
	 */
	public void setSupplierLoadNumber(String supplierLoadNumber) {
		this.supplierLoadNumber = supplierLoadNumber;
	}
	/**
	 * @return Returns the variety.
	 */
	public String getVariety() {
		return variety;
	}
	/**
	 * @param variety The variety to set.
	 */
	public void setVariety(String variety) {
		this.variety = variety;
	}
	/**
	 * @return Returns the lotDate.
	 */
	public String getLotDate() {
		return lotDate;
	}
	/**
	 * @param lotDate The lotDate to set.
	 */
	public void setLotDate(String lotDate) {
		this.lotDate = lotDate;
	}
	/**
	 * @return Returns the lotSequence.
	 */
	public String getLotSequence() {
		return lotSequence;
	}
	/**
	 * @param lotSequence The lotSequence to set.
	 */
	public void setLotSequence(String lotSequence) {
		this.lotSequence = lotSequence;
	}
	/**
	 * @return Returns the lotTime.
	 */
	public String getLotTime() {
		return lotTime;
	}
	/**
	 * @param lotTime The lotTime to set.
	 */
	public void setLotTime(String lotTime) {
		this.lotTime = lotTime;
	}
	/**
	 * @return Returns the lotTotalBins25Box.
	 */
	public String getLotTotalBins25Box() {
		return lotTotalBins25Box;
	}
	/**
	 * @param lotTotalBins25Box The lotTotalBins25Box to set.
	 */
	public void setLotTotalBins25Box(String lotTotalBins25Box) {
		this.lotTotalBins25Box = lotTotalBins25Box;
	}
	/**
	 * @return Returns the lotTotalBins30Box.
	 */
	public String getLotTotalBins30Box() {
		return lotTotalBins30Box;
	}
	/**
	 * @param lotTotalBins30Box The lotTotalBins30Box to set.
	 */
	public void setLotTotalBins30Box(String lotTotalBins30Box) {
		this.lotTotalBins30Box = lotTotalBins30Box;
	}
	/**
	 * @return Returns the lotUser.
	 */
	public String getLotUser() {
		return lotUser;
	}
	/**
	 * @param lotUser The lotUser to set.
	 */
	public void setLotUser(String lotUser) {
		this.lotUser = lotUser;
	}
	/**
	 * @return Returns the lotNumber.
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 * @return Returns the lotAcceptedComments.
	 */
	public String getLotAcceptedComments() {
		return lotAcceptedComments;
	}
	/**
	 * @param lotAcceptedComments The lotAcceptedComments to set.
	 */
	public void setLotAcceptedComments(String lotAcceptedComments) {
		this.lotAcceptedComments = lotAcceptedComments;
	}
	/**
	 * @return Returns the lotRejectedComments.
	 */
	public String getLotRejectedComments() {
		return lotRejectedComments;
	}
	/**
	 * @param lotRejectedComments The lotRejectedComments to set.
	 */
	public void setLotRejectedComments(String lotRejectedComments) {
		this.lotRejectedComments = lotRejectedComments;
	}
	/**
	 * @return Returns the runType.
	 */
	public String getRunType() {
		return runType;
	}
	/**
	 * @param runType The runType to set.
	 */
	public void setRunType(String runType) {
		this.runType = runType;
	}
	/**
	 * @return Returns the intendedUse.
	 */
	public String getIntendedUse() {
		return intendedUse;
	}
	/**
	 * @param intendedUse The intendedUse to set.
	 */
	public void setIntendedUse(String intendedUse) {
		this.intendedUse = intendedUse;
	}
	/**
	 * @return Returns the additionalVariety.
	 */
	public String getAdditionalVariety() {
		return additionalVariety;
	}
	/**
	 * @param additionalVariety The additionalVariety to set.
	 */
	public void setAdditionalVariety(String additionalVariety) {
		this.additionalVariety = additionalVariety;
	}
	/**
	 * @return Returns the countryOfOrigin.
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	/**
	 * @param countryOfOrigin The countryOfOrigin to set.
	 */
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	public Warehouse getLotWarehouseFacility() {
		return lotWarehouseFacility;
	}
	public void setLotWarehouseFacility(Warehouse lotWarehouseFacility) {
		this.lotWarehouseFacility = lotWarehouseFacility;
	}
	public String getLotPostingFlags() {
		return lotPostingFlags;
	}
	public void setLotPostingFlags(String lotPostingFlags) {
		this.lotPostingFlags = lotPostingFlags;
	}
	public String getGrapeDueDate() {
		return grapeDueDate;
	}
	public void setGrapeDueDate(String grapeDueDate) {
		this.grapeDueDate = grapeDueDate;
	}
	public String getLotWriteUpNumber() {
		return lotWriteUpNumber;
	}
	public void setLotWriteUpNumber(String lotWriteUpNumber) {
		this.lotWriteUpNumber = lotWriteUpNumber;
	}
	public String getItemWhseNotValid() {
		return itemWhseNotValid;
	}
	public void setItemWhseNotValid(String itemWhseNotValid) {
		this.itemWhseNotValid = itemWhseNotValid;
	}
}
