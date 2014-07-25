/*
 * Created on November 11, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit Load Information
 *     // Generally Loaded from file: GRPCSCALE
 */
public class RawFruitLoad{
	
	protected	String		scaleTicketNumber	= ""; //G1SCALE#
	protected	String 		scaleTicketCorrectionSequenceNumber = ""; // G1CSEQ1
	protected	String		correctionCount		= ""; //G1CCNT
	protected	String		receivingDate		= ""; //G1RDATE
	protected	String		receivingTime		= ""; //G1RTIME -- Added 6/27/12 TW
	protected	String		loadType			= ""; //G1LOADT - Receipt Transfer
	protected	String		loadGrossWeight		= ""; //G1GRWGT
	protected	String		loadTareWeight		= ""; //G1LWGT
	protected	String		loadNetWeight		= ""; //G1NLWGT
	protected	String		binTareWeight		= ""; //G1BWGT
	protected	String		loadFruitNetWeight	= ""; //G1AFWGT accepted fruit
	protected	String		loadFullBins		= ""; //G1FBINS
	protected	String		loadEmptyBins		= ""; //G1EBINS
	protected	String		loadTotalBins		= ""; //G1TBINS
	protected	String		loadTotalBoxes		= ""; //G1TBOXES
	protected	String		loadAveWeightPerBox = ""; //G1LBSBX
	protected	String		flagBulkBin			= ""; //G1BNBLK
	protected	String		carrierBOL			= ""; //G1CBOL
	protected	String		freightRate			= ""; //G1FRATE
	protected	String		flatRateFreightFlag = ""; //G1FRTFL
	protected	String		freightSurcharge	= ""; //G1FSCHG
	protected	String		fromLocation		= ""; //G1FRLOC
	protected	String		fromLocationLong	= ""; 
	protected	String		whseTicket			= ""; //G1WHTIK
	protected	String		minimumWeightFlag	= ""; //G1MIMW
	protected	String		minimumWeightValue	= ""; //G1MNLB
	protected	String		loadComplete		= ""; //G1COMP
	protected	String		freightRateCode		= ""; //G1FTCCD
	protected	String		freightSurchargeCode= ""; //G1FLCCD
	protected	String		loadUser			= ""; //G1USER
	protected	String		loadDate			= ""; //G1DATE
	protected	String 		loadTime			= ""; //G1TIME
	protected	String		m3FreightVoucher	= ""; //G1VONO
	protected	String		m3FreightJournal	= ""; //G1JRNO
	protected	String		m3FreightJournalSeq	= ""; //G1JSNO
	protected	String		handlingCode		= ""; //G1HCOD
	protected	String		dim5				= ""; //G1DIM5
	protected	String		costCenter			= ""; //G1CCTR
	protected	String		postingFlags		= ""; //G1POSTF
	protected	String		scheduledLoadNumber = ""; //G1TRACK
	protected	String		inspectedBy			= ""; //G1INSP
	protected	String		missingItemWhse		= "";
	
	protected	Warehouse	warehouseFacility	= new Warehouse(); // G1WHSE - G1FACI
	protected	Supplier	carrier				= new Supplier(); //G1SCSUPP number and name
	protected	Vector		listBins			= new Vector();
	protected	Vector		listPOs				= new Vector();
	
	/**
	 *  // Constructor
	 */
	public RawFruitLoad() {
		super();

	}
	/**
	 * @return Returns the binTareWeight.
	 */
	public String getBinTareWeight() {
		return binTareWeight;
	}
	/**
	 * @param binTareWeight The binTareWeight to set.
	 */
	public void setBinTareWeight(String binTareWeight) {
		this.binTareWeight = binTareWeight;
	}
	/**
	 * @return Returns the carrier.
	 */
	public Supplier getCarrier() {
		return carrier;
	}
	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(Supplier carrier) {
		this.carrier = carrier;
	}
	/**
	 * @return Returns the carrierBOL.
	 */
	public String getCarrierBOL() {
		return carrierBOL;
	}
	/**
	 * @param carrierBOL The carrierBOL to set.
	 */
	public void setCarrierBOL(String carrierBOL) {
		this.carrierBOL = carrierBOL;
	}
	/**
	 * @return Returns the flagBulkBin.
	 */
	public String getFlagBulkBin() {
		return flagBulkBin;
	}
	/**
	 * @param flagBulkBin The flagBulkBin to set.
	 */
	public void setFlagBulkBin(String flagBulkBin) {
		this.flagBulkBin = flagBulkBin;
	}
	/**
	 * @return Returns the freightSurcharge.
	 */
	public String getFreightSurcharge() {
		return freightSurcharge;
	}
	/**
	 * @param freightSurcharge The freightSurcharge to set.
	 */
	public void setFreightSurcharge(String freightSurcharge) {
		this.freightSurcharge = freightSurcharge;
	}
	/**
	 * @return Returns the listBins.
	 */
	public Vector getListBins() {
		return listBins;
	}
	/**
	 * @param listBins The listBins to set.
	 */
	public void setListBins(Vector listBins) {
		this.listBins = listBins;
	}
	/**
	 * @return Returns the listPOs.
	 */
	public Vector getListPOs() {
		return listPOs;
	}
	/**
	 * @param listPOs The listPOs to set.
	 */
	public void setListPOs(Vector listPOs) {
		this.listPOs = listPOs;
	}
	/**
	 * @return Returns the loadAveWeightPerBox.
	 */
	public String getLoadAveWeightPerBox() {
		return loadAveWeightPerBox;
	}
	/**
	 * @param loadAveWeightPerBox The loadAveWeightPerBox to set.
	 */
	public void setLoadAveWeightPerBox(String loadAveWeightPerBox) {
		this.loadAveWeightPerBox = loadAveWeightPerBox;
	}
	/**
	 * @return Returns the loadEmptyBins.
	 */
	public String getLoadEmptyBins() {
		return loadEmptyBins;
	}
	/**
	 * @param loadEmptyBins The loadEmptyBins to set.
	 */
	public void setLoadEmptyBins(String loadEmptyBins) {
		this.loadEmptyBins = loadEmptyBins;
	}
	/**
	 * @return Returns the loadFruitNetWeight.
	 */
	public String getLoadFruitNetWeight() {
		return loadFruitNetWeight;
	}
	/**
	 * @param loadFruitNetWeight The loadFruitNetWeight to set.
	 */
	public void setLoadFruitNetWeight(String loadFruitNetWeight) {
		this.loadFruitNetWeight = loadFruitNetWeight;
	}
	/**
	 * @return Returns the loadFullBins.
	 */
	public String getLoadFullBins() {
		return loadFullBins;
	}
	/**
	 * @param loadFullBins The loadFullBins to set.
	 */
	public void setLoadFullBins(String loadFullBins) {
		this.loadFullBins = loadFullBins;
	}
	/**
	 * @return Returns the loadGrossWeight.
	 */
	public String getLoadGrossWeight() {
		return loadGrossWeight;
	}
	/**
	 * @param loadGrossWeight The loadGrossWeight to set.
	 */
	public void setLoadGrossWeight(String loadGrossWeight) {
		this.loadGrossWeight = loadGrossWeight;
	}
	/**
	 * @return Returns the loadNetWeight.
	 */
	public String getLoadNetWeight() {
		return loadNetWeight;
	}
	/**
	 * @param loadNetWeight The loadNetWeight to set.
	 */
	public void setLoadNetWeight(String loadNetWeight) {
		this.loadNetWeight = loadNetWeight;
	}
	/**
	 * @return Returns the loadTareWeight.
	 */
	public String getLoadTareWeight() {
		return loadTareWeight;
	}
	/**
	 * @param loadTareWeight The loadTareWeight to set.
	 */
	public void setLoadTareWeight(String loadTareWeight) {
		this.loadTareWeight = loadTareWeight;
	}
	/**
	 * @return Returns the loadTotalBins.
	 */
	public String getLoadTotalBins() {
		return loadTotalBins;
	}
	/**
	 * @param loadTotalBins The loadTotalBins to set.
	 */
	public void setLoadTotalBins(String loadTotalBins) {
		this.loadTotalBins = loadTotalBins;
	}
	/**
	 * @return Returns the loadTotalBoxes.
	 */
	public String getLoadTotalBoxes() {
		return loadTotalBoxes;
	}
	/**
	 * @param loadTotalBoxes The loadTotalBoxes to set.
	 */
	public void setLoadTotalBoxes(String loadTotalBoxes) {
		this.loadTotalBoxes = loadTotalBoxes;
	}
	/**
	 * @return Returns the receivingDate.
	 */
	public String getReceivingDate() {
		return receivingDate;
	}
	/**
	 * @param receivingDate The receivingDate to set.
	 */
	public void setReceivingDate(String receivingDate) {
		this.receivingDate = receivingDate;
	}
	/**
	 * @return Returns the scaleTicketNumber.
	 */
	public String getScaleTicketNumber() {
		return scaleTicketNumber;
	}
	/**
	 * @param scaleTicketNumber The scaleTicketNumber to set.
	 */
	public void setScaleTicketNumber(String scaleTicketNumber) {
		this.scaleTicketNumber = scaleTicketNumber;
	}
	/**
	 * @return Returns the freightRate.
	 */
	public String getFreightRate() {
		return freightRate;
	}
	/**
	 * @param freightRate The freightRate to set.
	 */
	public void setFreightRate(String freightRate) {
		this.freightRate = freightRate;
	}
	/**
	 * @return Returns the loadComplete.
	 */
	public String getLoadComplete() {
		return loadComplete;
	}
	/**
	 * @param loadComplete The loadComplete to set.
	 */
	public void setLoadComplete(String loadComplete) {
		this.loadComplete = loadComplete;
	}
	/**
	 * @return Returns the loadDate.
	 */
	public String getLoadDate() {
		return loadDate;
	}
	/**
	 * @param loadDate The loadDate to set.
	 */
	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}
	/**
	 * @return Returns the loadTime.
	 */
	public String getLoadTime() {
		return loadTime;
	}
	/**
	 * @param loadTime The loadTime to set.
	 */
	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}
	/**
	 * @return Returns the loadType.
	 */
	public String getLoadType() {
		return loadType;
	}
	/**
	 * @param loadType The loadType to set.
	 */
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	/**
	 * @return Returns the loadUser.
	 */
	public String getLoadUser() {
		return loadUser;
	}
	/**
	 * @param loadUser The loadUser to set.
	 */
	public void setLoadUser(String loadUser) {
		this.loadUser = loadUser;
	}
	/**
	 * @return Returns the fromLocation.
	 */
	public String getFromLocation() {
		return fromLocation;
	}
	/**
	 * @param fromLocation The fromLocation to set.
	 */
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	/**
	 * @return Returns the whseTicket.
	 */
	public String getWhseTicket() {
		return whseTicket;
	}
	/**
	 * @param whseTicket The whseTicket to set.
	 */
	public void setWhseTicket(String whseTicket) {
		this.whseTicket = whseTicket;
	}
	/**
	 * @return Returns the freightRateCode.
	 */
	public String getFreightRateCode() {
		return freightRateCode;
	}
	/**
	 * @param freightRateCode The freightRateCode to set.
	 */
	public void setFreightRateCode(String freightRateCode) {
		this.freightRateCode = freightRateCode;
	}
	/**
	 * @return Returns the freightSurchargeCode.
	 */
	public String getFreightSurchargeCode() {
		return freightSurchargeCode;
	}
	/**
	 * @param freightSurchargeCode The freightSurchargeCode to set.
	 */
	public void setFreightSurchargeCode(String freightSurchargeCode) {
		this.freightSurchargeCode = freightSurchargeCode;
	}
	public String getFlatRateFreightFlag() {
		return flatRateFreightFlag;
	}
	public void setFlatRateFreightFlag(String flatRateFreightFlag) {
		this.flatRateFreightFlag = flatRateFreightFlag;
	}
	public String getM3FreightJournal() {
		return m3FreightJournal;
	}
	public void setM3FreightJournal(String freightJournal) {
		m3FreightJournal = freightJournal;
	}
	public String getM3FreightJournalSeq() {
		return m3FreightJournalSeq;
	}
	public void setM3FreightJournalSeq(String freightJournalSeq) {
		m3FreightJournalSeq = freightJournalSeq;
	}
	public String getM3FreightVoucher() {
		return m3FreightVoucher;
	}
	public void setM3FreightVoucher(String freightVoucher) {
		m3FreightVoucher = freightVoucher;
	}
	public String getPostingFlags() {
		return postingFlags;
	}
	public void setPostingFlags(String postingFlags) {
		this.postingFlags = postingFlags;
	}
	public String getScaleTicketCorrectionSequenceNumber() {
		return scaleTicketCorrectionSequenceNumber;
	}
	public void setScaleTicketCorrectionSequenceNumber(
			String scaleTicketCorrectionSequenceNumber) {
		this.scaleTicketCorrectionSequenceNumber = scaleTicketCorrectionSequenceNumber;
	}
	public String getMinimumWeightFlag() {
		return minimumWeightFlag;
	}
	public void setMinimumWeightFlag(String minimumWeightFlag) {
		this.minimumWeightFlag = minimumWeightFlag;
	}
	public String getMinimumWeightValue() {
		return minimumWeightValue;
	}
	public void setMinimumWeightValue(String minimumWeightValue) {
		this.minimumWeightValue = minimumWeightValue;
	}
	public Warehouse getWarehouseFacility() {
		return warehouseFacility;
	}
	public void setWarehouseFacility(Warehouse warehouseFacility) {
		this.warehouseFacility = warehouseFacility;
	}
	public String getCorrectionCount() {
		return correctionCount;
	}
	public void setCorrectionCount(String correctionCount) {
		this.correctionCount = correctionCount;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getDim5() {
		return dim5;
	}
	public void setDim5(String dim5) {
		this.dim5 = dim5;
	}
	public String getHandlingCode() {
		return handlingCode;
	}
	public void setHandlingCode(String handlingCode) {
		this.handlingCode = handlingCode;
	}
	public String getScheduledLoadNumber() {
		return scheduledLoadNumber;
	}
	public void setScheduledLoadNumber(String scheduledLoadNumber) {
		this.scheduledLoadNumber = scheduledLoadNumber;
	}
	public String getReceivingTime() {
		return receivingTime;
	}
	public void setReceivingTime(String receivingTime) {
		this.receivingTime = receivingTime;
	}
	public String getInspectedBy() {
		return inspectedBy;
	}
	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}
	public String getFromLocationLong() {
		return fromLocationLong;
	}
	public void setFromLocationLong(String fromLocationLong) {
		this.fromLocationLong = fromLocationLong;
	}
	public String getMissingItemWhse() {
		return missingItemWhse;
	}
	public void setMissingItemWhse(String missingItemWhse) {
		this.missingItemWhse = missingItemWhse;
	}
}
