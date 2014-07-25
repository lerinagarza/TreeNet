/*
 * Created on October 22,2009
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 * General Brix Information -- usually from BrixFile
 */
public class Brix{
	
	protected	String		brix			    = ""; 
	protected	String		poundsFSperGallon	= ""; 
	protected	String		errorMessage		= "";
	
	/**
	 *  // Constructor
	 */
	public Brix() {
		super();

	}

	public String getBrix() {
		return brix;
	}

	public void setBrix(String brix) {
		this.brix = brix;
	}

	public String getPoundsFSperGallon() {
		return poundsFSperGallon;
	}

	public void setPoundsFSperGallon(String poundsFSperGallon) {
		this.poundsFSperGallon = poundsFSperGallon;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
