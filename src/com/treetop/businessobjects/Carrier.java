/*
 * Created on Feb 21, 2006
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Carrier Information
 */
public class Carrier {
	
	String		name = "";
	String		number = "";

	/**
	 * 
	 */
	public Carrier() {
		super();
	}
	

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the number.
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}
}
