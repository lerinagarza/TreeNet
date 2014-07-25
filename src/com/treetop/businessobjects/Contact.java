package com.treetop.businessobjects;

import java.util.ArrayList;
import java.util.List;

public class Contact {

	private String id = "";
	private String name = "";
	private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return this.getId() + " " + this.getName() + " " + this.getPhoneNumbers().toString();
	}
	
}
