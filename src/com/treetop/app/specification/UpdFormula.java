/*
 * Created on May 5, 2008
 * 
 */

package com.treetop.app.specification;

import java.util.Vector;

import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 * 
 */
public class UpdFormula extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	public String formulaNumber = "";
	public String formulaName = "";
	public String revisionDate = "";
	public String revisionTime = "";
	public String supercedeDate = "";
	public String productionStatus = "";
	public String recordStatus = "";
	public String comments = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
		
	public Vector listReport = null;

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		return;
	}
	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
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
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}
	/**
	 * @return Returns the orderBy.
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy The orderBy to set.
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return Returns the orderStyle.
	 */
	public String getOrderStyle() {
		return orderStyle;
	}
	/**
	 * @param orderStyle The orderStyle to set.
	 */
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	/**
	 * @return Returns the productionStatus.
	 */
	public String getProductionStatus() {
		return productionStatus;
	}
	/**
	 * @param productionStatus The productionStatus to set.
	 */
	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus;
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
	 * @return Returns the requestType.
	 */
	public String getRequestType() {
		return requestType;
	}
	/**
	 * @param requestType The requestType to set.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	 * @return Returns the revisionTime.
	 */
	public String getRevisionTime() {
		return revisionTime;
	}
	/**
	 * @param revisionTime The revisionTime to set.
	 */
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	/**
	 * @return Returns the supercedeDate.
	 */
	public String getSupercedeDate() {
		return supercedeDate;
	}
	/**
	 * @param supercedeDate The supercedeDate to set.
	 */
	public void setSupercedeDate(String supercedeDate) {
		this.supercedeDate = supercedeDate;
	}
}
