/*
 * Created on September 21, 2009
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Raw Fruit Payment Information
 * 	// Generally Loaded from file: HHPTDATA in qgpl
 */
public class TransactionError extends Transaction{
	
	protected	String		errorCode		= "";
	protected	String		errorText		= ""; 
	protected	String		errorBeforeAfter= "";
	protected	String		senderAddress	= "";
	
	/**
	 *  // Constructor
	 */
	public TransactionError() {
		super();

	}

	public String getErrorBeforeAfter() {
		return errorBeforeAfter;
	}

	public void setErrorBeforeAfter(String errorBeforeAfter) {
		this.errorBeforeAfter = errorBeforeAfter;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
}
