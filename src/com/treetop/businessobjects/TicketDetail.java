package com.treetop.businessobjects;

/**
 * @author deisen
 *
 * Receiving Ticket Detail Information
 */

public class TicketDetail {

	protected	String		lotNumber			 = "";
	protected	String		itemNumber			 = "";
	protected	String		tagNumber			 = "";
	protected	String		growerName			 = "";
	
	/**
	 *  // Constructor
	 */

	public TicketDetail() {
		
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getGrowerName() {
		return growerName;
	}

	public void setGrowerName(String growerName) {
		this.growerName = growerName;
	}	
		
}
