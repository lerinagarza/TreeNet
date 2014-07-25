/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Item information
 */
public class ItemVariance extends Item{
	
	protected	String		varianceText				= "";
	protected	String		dateIssued					= "";	
	protected	String		dateExpired					= "";
	protected   String		varianceComment				= "";
	protected   String		allowUpdate					= "";
	protected	String		recordStatus				= "";
	protected	String		updDate						= "";
	protected	String		updTime						= "";
	protected	String		updUser						= "";

	/**
	 *  // Constructor
	 */
	public ItemVariance() {
		super();

	}
	/**
	 * @return Returns the allowUpdate.
	 */
	public String getAllowUpdate() {
		return allowUpdate;
	}
	/**
	 * @param allowUpdate The allowUpdate to set.
	 */
	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	/**
	 * @return Returns the dateExpired.
	 */
	public String getDateExpired() {
		return dateExpired;
	}
	/**
	 * @param dateExpired The dateExpired to set.
	 */
	public void setDateExpired(String dateExpired) {
		this.dateExpired = dateExpired;
	}
	/**
	 * @return Returns the dateIssued.
	 */
	public String getDateIssued() {
		return dateIssued;
	}
	/**
	 * @param dateIssued The dateIssued to set.
	 */
	public void setDateIssued(String dateIssued) {
		this.dateIssued = dateIssued;
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
	 * @return Returns the varianceComment.
	 */
	public String getVarianceComment() {
		return varianceComment;
	}
	/**
	 * @param varianceComment The varianceComment to set.
	 */
	public void setVarianceComment(String varianceComment) {
		this.varianceComment = varianceComment;
	}
	/**
	 * @return Returns the varianceText.
	 */
	public String getVarianceText() {
		return varianceText;
	}
	/**
	 * @param varianceText The varianceText to set.
	 */
	public void setVarianceText(String varianceText) {
		this.varianceText = varianceText;
	}
	/**
	 * @return Returns the updDate.
	 */
	public String getUpdDate() {
		return updDate;
	}
	/**
	 * @param updDate The updDate to set.
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	/**
	 * @return Returns the updTime.
	 */
	public String getUpdTime() {
		return updTime;
	}
	/**
	 * @param updTime The updTime to set.
	 */
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	/**
	 * @return Returns the updUser.
	 */
	public String getUpdUser() {
		return updUser;
	}
	/**
	 * @param updUser The updUser to set.
	 */
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
}
