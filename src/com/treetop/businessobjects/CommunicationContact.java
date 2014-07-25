package com.treetop.businessobjects;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.*;

public class CommunicationContact extends InternetAddress {
	
	// from File 	dbprd/xxxxxxxx
	//              DBPRD/DCPALL
	
	private	String			contactNumber			= "";	//xxxxxx
	private	String			contactEmail			= "";	//
	private	String			contactName				= "";	//
	private	String			contactType				= "";	//
	private	String			lastSavedDate			= "";	//
	private	String			lastSavedTime			= "";	//
	private	String			lastSavedUser			= "";	//
		
	private String 			contactEmailDisplayName	= "";
	
	/**
	 * Default constructor
	 */
	public CommunicationContact() {
		
	}
	
	/**
	 * Constructor from InternetAddress
	 * @param address
	 */
	public CommunicationContact(InternetAddress address) {
		this.setContactName(address.getPersonal());
		this.setContactEmail(address.getAddress());
	}
	
	private void updateFields() {
		try {
			this.setAddress(this.getContactEmail());
			this.setPersonal(this.getContactName(), "UTF-8");
			this.setContactEmailDisplayName(this.toString());
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		}
	}

	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
		updateFields();
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
		updateFields();
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getLastSavedDate() {
		return lastSavedDate;
	}
	public void setLastSavedDate(String lastSavedDate) {
		this.lastSavedDate = lastSavedDate;
	}
	public String getLastSavedTime() {
		return lastSavedTime;
	}
	public void setLastSavedTime(String lastSavedTime) {
		this.lastSavedTime = lastSavedTime;
	}
	public String getLastSavedUser() {
		return lastSavedUser;
	}
	public void setLastSavedUser(String lastSavedUser) {
		this.lastSavedUser = lastSavedUser;
	}

	public String getContactEmailDisplayName() {
		return this.toString();
	}
	public void setContactEmailDisplayName(String contactEmailDisplayName) {
		this.contactEmailDisplayName = contactEmailDisplayName;
	}

	
	
}
