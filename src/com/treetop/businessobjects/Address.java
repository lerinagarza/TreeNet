package com.treetop.businessobjects;

public class Address {
	
	private String addressId = "";
	
	private String addressName = "";
	private String street1 = "";
	private String street2 = "";
	private String city = "";
	private String state = "";
	private String zip = "";
	
	private double latitude = 0;
	private double longitude = 0;
	
	private String drivingDirections = "";

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getDrivingDirections() {
		return drivingDirections;
	}

	public void setDrivingDirections(String drivingDirections) {
		this.drivingDirections = drivingDirections;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		
		return this.getAddressId() + " " +
			this.getAddressName() + " " +
			this.getStreet1() + " " + 
			this.getStreet2() + 
			this.getCity() + " " + 
			this.getState() + " " + 
			this.getZip() + " " +
			this.getLatitude() + ", " +
			this.getLongitude() + " " +
			this.getDrivingDirections();
		
	}


	
}
