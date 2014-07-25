/*
 * Created on May 15, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * DataSet Related information
 */
public class DataSet {
	
	protected	String		fileName		= "";
	protected	String		dataSetID		= "";	
	protected	String		name			= "";
	protected   String		description		= "";
	protected	String		yearFrom		= "";
	protected	String		yearTo			= "";
	protected	String		yearDataSet		= "";
	protected	String		completionStatus = "";
	protected	String		comment			= "";
	protected	String		recordType		= "";
	protected	String		updateDate		= "";
	protected	String		updateTime		= "";
	protected	String		updateUser		= "";
	
	/**
	 * 
	 */
	public DataSet() {
		super();

	}
	/**
	 * @return Returns the dataSetID.
	 */
	public String getDataSetID() {
		return dataSetID;
	}
	/**
	 * @param dataSetID The dataSetID to set.
	 */
	public void setDataSetID(String dataSetID) {
		this.dataSetID = dataSetID;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return Returns the completionStatus.
	 */
	public String getCompletionStatus() {
		return completionStatus;
	}
	/**
	 * @param completionStatus The completionStatus to set.
	 */
	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}
	/**
	 * @return Returns the recordType.
	 */
	public String getRecordType() {
		return recordType;
	}
	/**
	 * @param recordType The recordType to set.
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	/**
	 * @return Returns the yearFrom.
	 */
	public String getYearFrom() {
		return yearFrom;
	}
	/**
	 * @param yearFrom The yearFrom to set.
	 */
	public void setYearFrom(String yearFrom) {
		this.yearFrom = yearFrom;
	}
	/**
	 * @return Returns the yearTo.
	 */
	public String getYearTo() {
		return yearTo;
	}
	/**
	 * @param yearTo The yearTo to set.
	 */
	public void setYearTo(String yearTo) {
		this.yearTo = yearTo;
	}
	/**
	 * @return Returns the yearDataSet.
	 */
	public String getYearDataSet() {
		return yearDataSet;
	}
	/**
	 * @param yearDataSet The yearDataSet to set.
	 */
	public void setYearDataSet(String yearDataSet) {
		this.yearDataSet = yearDataSet;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return Returns the updateTime.
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime The updateTime to set.
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
