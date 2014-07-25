package com.treetop.businessobjects;

import java.util.ArrayList;
import java.util.List;

public class RawFruitAgreement {
	
	private String supplierNumber = "";
    private String supplierName = "";
	private String entryDate = "";
	private String revisedDate = "";
	private String cropYear = "";
	private String fieldRep = "";
	
	private List<Contact> contacts = new ArrayList<Contact>();
	
	private String carrierName = "";
	private String carrierComments = "";
	
	private Address orchardLocation = new Address();
	private Address supplierLocation = new Address();
	
	
	
	private List<RawFruitAgreementLine> lines = new ArrayList<RawFruitAgreementLine>();

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getRevisedDate() {
		return revisedDate;
	}

	public void setRevisedDate(String revisedDate) {
		this.revisedDate = revisedDate;
	}

	public String getCropYear() {
		return cropYear;
	}

	public void setCropYear(String cropYear) {
		this.cropYear = cropYear;
	}

	public String getFieldRep() {
		return fieldRep;
	}

	public void setFieldRep(String fieldRep) {
		this.fieldRep = fieldRep;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierComments() {
		return carrierComments;
	}

	public void setCarrierComments(String carrierComments) {
		this.carrierComments = carrierComments;
	}

	public Address getOrchardLocation() {
		return orchardLocation;
	}

	public void setOrchardLocation(Address orchardLocation) {
		this.orchardLocation = orchardLocation;
	}

	public Address getSupplierLocation() {
		return supplierLocation;
	}

	public void setSupplierLocation(Address supplierLocation) {
		this.supplierLocation = supplierLocation;
	}

	public List<RawFruitAgreementLine> getLines() {
		return lines;
	}

	public void setLines(List<RawFruitAgreementLine> lines) {
		this.lines = lines;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
