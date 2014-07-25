package com.treetop.businessobjects;

import java.util.Vector;

/**
 * @author deisen
 *
 * Receiving Peach Tickets Information
 */

public class PeachTicket {

	protected	String		supplierNumber		 = "";
	protected	String      supplierName         = "";
	protected	String		loadNumber			 = "";
	protected	String		lotNumber			 = "";
	protected	String		itemNumber			 = "";
	protected   String      itemDescription      = "";
	protected	String		receivingUser		 = "";
	protected	String		createUser			 = "";
	protected	String		createDate			 = "";
	protected	String		createTime			 = "";
	protected	String		updateUser			 = "";
	protected	String		updateDate			 = "";
	protected	String		updateTime			 = "";
	
	protected Vector<TicketDetail> tagDetail = new Vector<TicketDetail>();
	
	/**
	 *  // Constructor
	 */

	public PeachTicket() {
		
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getLoadNumber() {
		return loadNumber;
	}

	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
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

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getReceivingUser() {
		return receivingUser;
	}

	public void setReceivingUser(String receivingUser) {
		this.receivingUser = receivingUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Vector<TicketDetail> getTagDetail() {
		return tagDetail;
	}

	public void setTagDetail(Vector<TicketDetail> tagDetail) {
		this.tagDetail = tagDetail;
	}	
		
}

