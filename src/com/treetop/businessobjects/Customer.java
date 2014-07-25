/*
 * Created on December 22, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Customer information
 */
public class Customer {
	
	protected   String      company				= "";
	protected	String		division			= "";
	protected 	String		status				= "";
	protected	String		number				= "";
	protected	String		group				= "";
	protected	String		type				= "";
	protected	String		searchKey			= "";
	protected	String		name				= "";	
	protected	String		district			= "";
	protected 	String		market				= "";
	

	/**
	 *  // Constructor
	 */
	public Customer() {
		super();

	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getSearchKey() {
		return searchKey;
	}


	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMarket() {
		return market;
	}


	public void setMarket(String market) {
		this.market = market;
	}
	
	

}
