/*
 * Created on May 6, 2008
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
public class UpdMethod extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	public String methodID = "";
	public String methodCategory = "";
	public String methodTitle = "";
	public String revisionDate = "";
	public String revisionTime = "";
	public String supercedeDate = "";
	public String methodDetails = "";
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
	/**
	 * @return Returns the methodCategory.
	 */
	public String getMethodCategory() {
		return methodCategory;
	}
	/**
	 * @param methodCategory The methodCategory to set.
	 */
	public void setMethodCategory(String methodCategory) {
		this.methodCategory = methodCategory;
	}
	/**
	 * @return Returns the methodDetails.
	 */
	public String getMethodDetails() {
		return methodDetails;
	}
	/**
	 * @param methodDetails The methodDetails to set.
	 */
	public void setMethodDetails(String methodDetails) {
		this.methodDetails = methodDetails;
	}
	/**
	 * @return Returns the methodID.
	 */
	public String getMethodID() {
		return methodID;
	}
	/**
	 * @param methodID The methodID to set.
	 */
	public void setMethodID(String methodID) {
		this.methodID = methodID;
	}
	/**
	 * @return Returns the methodTitle.
	 */
	public String getMethodTitle() {
		return methodTitle;
	}
	/**
	 * @param methodTitle The methodTitle to set.
	 */
	public void setMethodTitle(String methodTitle) {
		this.methodTitle = methodTitle;
	}
}
