/*
 * Created on Jan 30, 2006
 *
 * Grower Fruit Receiving Bins 
 * Transaction Information.
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Grower Fruit Receiving Payment Bins 
 * Transaction Information.
 */
public class FruitReceivingBinTrans
	extends FruitReceivingTicketTrans {

	// a presentation of the Grower file GRPBGLCH
	
	String	binType;
	String	binTypeDescription;
	String	emptyBinWeight;
	String	fullBinCount;
	String	emptyBinCount;

	/**
	 * 
	 *
	 */
	public FruitReceivingBinTrans(){
		super();
	}
	
	/**
	 * @return Returns the binType.
	 */
	public String getBinType() {
		return binType;
	}
	/**
	 * @param binType The binType to set.
	 */
	public void setBinType(String binType) {
		this.binType = binType;
	}
	/**
	 * @return Returns the binTypeDescription.
	 */
	public String getBinTypeDescription() {
		return binTypeDescription;
	}
	/**
	 * @param binTypeDescription The binTypeDescription to set.
	 */
	public void setBinTypeDescription(String binTypeDescription) {
		this.binTypeDescription = binTypeDescription;
	}
	/**
	 * @return Returns the emptyBinCount.
	 */
	public String getEmptyBinCount() {
		return emptyBinCount;
	}
	/**
	 * @param emptyBinCount The emptyBinCount to set.
	 */
	public void setEmptyBinCount(String emptyBinCount) {
		this.emptyBinCount = emptyBinCount;
	}
	/**
	 * @return Returns the emptyBinWeight.
	 */
	public String getEmptyBinWeight() {
		return emptyBinWeight;
	}
	/**
	 * @param emptyBinWeight The emptyBinWeight to set.
	 */
	public void setEmptyBinWeight(String emptyBinWeight) {
		this.emptyBinWeight = emptyBinWeight;
	}
	/**
	 * @return Returns the fullBinWeight.
	 */
	public String getFullBinCount() {
		return fullBinCount;
	}
	/**
	 * @param fullBinWeight The fullBinWeight to set.
	 */
	public void setFullBinCount(String fullBinCount) {
		this.fullBinCount = fullBinCount;
	}
}
