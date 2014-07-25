/*
 * Created on September 21, 2009
 *
 */
package com.treetop.businessobjectapplications;

import java.io.File;
import java.util.Vector;

/**
 * @author twalto
 *
 * Bean to combine everything to send to the servlet
 */
public class BeanTransaction {

	protected	String errorMessage = "";

  // Vector of Business Object PlannedOrder, Loaded into Sections
	
	protected	Vector 	listTransactionErrors = new Vector();
	
	private		File	spoolFileOutput	= null;
	
	/**
	 *  // Constructor
	 */
	public BeanTransaction() {
		super();
	}
	/**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage The errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Vector getListTransactionErrors() {
		return listTransactionErrors;
	}
	public void setListTransactionErrors(Vector listTransactionErrors) {
		this.listTransactionErrors = listTransactionErrors;
	}
	public File getSpoolFileOutput() {
		return spoolFileOutput;
	}
	public void setSpoolFileOutput(File spoolFileOutput) {
		this.spoolFileOutput = spoolFileOutput;
	}
}
