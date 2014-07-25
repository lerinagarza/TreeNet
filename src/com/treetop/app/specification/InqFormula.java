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
public class InqFormula extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	public String inqFormulaNumber = "";
	public String inqFormulaName = "";
	public String inqRevisionDate = "";
	public String inqProductionStatus = "";
	public String inqRecordStatus = "";
	
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
	 * @return Returns the inqFormulaName.
	 */
	public String getInqFormulaName() {
		return inqFormulaName;
	}
	/**
	 * @param inqFormulaName The inqFormulaName to set.
	 */
	public void setInqFormulaName(String inqFormulaName) {
		this.inqFormulaName = inqFormulaName;
	}
	/**
	 * @return Returns the inqFormulaNumber.
	 */
	public String getInqFormulaNumber() {
		return inqFormulaNumber;
	}
	/**
	 * @param inqFormulaNumber The inqFormulaNumber to set.
	 */
	public void setInqFormulaNumber(String inqFormulaNumber) {
		this.inqFormulaNumber = inqFormulaNumber;
	}
	/**
	 * @return Returns the inqProductionStatus.
	 */
	public String getInqProductionStatus() {
		return inqProductionStatus;
	}
	/**
	 * @param inqProductionStatus The inqProductionStatus to set.
	 */
	public void setInqProductionStatus(String inqProductionStatus) {
		this.inqProductionStatus = inqProductionStatus;
	}
	/**
	 * @return Returns the inqRecordStatus.
	 */
	public String getInqRecordStatus() {
		return inqRecordStatus;
	}
	/**
	 * @param inqRecordStatus The inqRecordStatus to set.
	 */
	public void setInqRecordStatus(String inqRecordStatus) {
		this.inqRecordStatus = inqRecordStatus;
	}
	/**
	 * @return Returns the inqRevisionDate.
	 */
	public String getInqRevisionDate() {
		return inqRevisionDate;
	}
	/**
	 * @param inqRevisionDate The inqRevisionDate to set.
	 */
	public void setInqRevisionDate(String inqRevisionDate) {
		this.inqRevisionDate = inqRevisionDate;
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
}
