/*
 * Created on January 7, 2009
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Method Related Information
 */
public class SpecificationMethod {
	
	protected	String			methodNumber			= "";	
	protected   String			methodName				= "";
	protected	String			revisionDate			= "";
	protected   String			supercedesDate			= "";
	protected	String			recordStatus			= "";
	protected	String			details					= "";
	protected	String			comment					= "";
	
	/**
	 *  // Constructor
	 */
	public SpecificationMethod() {
		super();
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the details.
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details The details to set.
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @return Returns the recordStatus.
	 */
	public String getRecordStatus() {
		return recordStatus;
	}
	/**
	 * @param recordStatus The recordStatus to set.
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	/**
	 * @return Returns the revisionDate.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param revisionDate The revisionDate to set.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the supercedesDate.
	 */
	public String getSupercedesDate() {
		return supercedesDate;
	}
	/**
	 * @param supercedesDate The supercedesDate to set.
	 */
	public void setSupercedesDate(String supercedesDate) {
		this.supercedesDate = supercedesDate;
	}
	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return Returns the methodNumber.
	 */
	public String getMethodNumber() {
		return methodNumber;
	}
	/**
	 * @param methodNumber The methodNumber to set.
	 */
	public void setMethodNumber(String methodNumber) {
		this.methodNumber = methodNumber;
	}
}
