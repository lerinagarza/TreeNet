/*
 * Created on January 7, 2009
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
public class DtlMethod extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	public String methodNumber = "";
	public String methodName = "";
	public String revisionDate = "";
	public String revisionTime = "";
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
