/*
 * Created on Jan 31, 2006
 *
 * Grower Ticket Master Unique Key Information
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Grower Ticket Master Transaction data.
 */
public class FruitReceivingTicketTrans {
	
	String	lotNumber;
	String	transactionNumber;
	String	resource;
	
	/**
	 * 
	 *
	 */
	public FruitReceivingTicketTrans(){
		super();
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
	 * @return Returns the resource.
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return Returns the transactionNumber.
	 */
	public String getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * @param transactionNumber The transactionNumber to set.
	 */
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
}
