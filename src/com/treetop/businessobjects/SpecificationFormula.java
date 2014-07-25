/*
 * Created on October 27, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Formula Related Information
 */
public class SpecificationFormula {
	
	protected	String			formulaNumber			= "";	
	protected   String			formulaName				= "";
	protected	String			revisionDate			= "";
	protected   String			supercedesDate			= "";
	protected	String			recordStatus			= "";
	protected	String			details					= "";
	protected	String			comment					= "";
	
	/**
	 *  // Constructor
	 */
	public SpecificationFormula() {
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
	 * @return Returns the formulaName.
	 */
	public String getFormulaName() {
		return formulaName;
	}
	/**
	 * @param formulaName The formulaName to set.
	 */
	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}
	/**
	 * @return Returns the formulaNumber.
	 */
	public String getFormulaNumber() {
		return formulaNumber;
	}
	/**
	 * @param formulaNumber The formulaNumber to set.
	 */
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
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
}
