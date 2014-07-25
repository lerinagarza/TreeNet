package com.treetop.businessobjects;

public class PhoneNumber {

	private String type = "";
	private String phoneNumber = "";
	
	public PhoneNumber(String type, String phoneNumber) {
		this.setType(type);
		this.setPhoneNumber(phoneNumber);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return this.getType() + " " + this.getPhoneNumber();
	}
	
}
