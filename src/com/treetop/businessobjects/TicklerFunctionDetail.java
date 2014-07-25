/*
 * Created on Dec 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Tickler: Assign A date and user to A Business Process.
 * 	Group: A collection of Business Processes like New Item.
 * 	Function: A segment of a Group.
 *  FunctionDetail: Specific dates, users, status.
 */
public class TicklerFunctionDetail extends TicklerFunction {
	
	String			idKeyValue;
	String			respPerson;
	String			status;
	String			targetDate;
	String			completionDate;
	Integer			completionTime;
	String			completionUser;
	String			initialNotification;
	String			idKeyDescription;
	/**
	 * @return
	 */
	public String getCompletionDate() {
		return completionDate;
	}

	/**
	 * @return
	 */
	public Integer getCompletionTime() {
		return completionTime;
	}

	/**
	 * @return
	 */
	public String getCompletionUser() {
		return completionUser;
	}

	/**
	 * @return
	 */
	public String getIdKeyValue() {
		return idKeyValue;
	}

	/**
	 * @return
	 */
	public String getRespPerson() {
		return respPerson;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public String getTargetDate() {
		return targetDate;
	}

	/**
	 * @param string
	 */
	public void setCompletionDate(String string) {
		completionDate = string;
	}

	/**
	 * @param integer
	 */
	public void setCompletionTime(Integer integer) {
		completionTime = integer;
	}

	/**
	 * @param string
	 */
	public void setCompletionUser(String string) {
		completionUser = string;
	}

	/**
	 * @param string
	 */
	public void setIdKeyValue(String string) {
		idKeyValue = string;
	}

	/**
	 * @param string
	 */
	public void setRespPerson(String string) {
		respPerson = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setTargetDate(String string) {
		targetDate = string;
	}

	/**
	 * @return Returns the initialNotification.
	 */
	public String getInitialNotification() {
		return initialNotification;
	}
	/**
	 * @param initialNotification The initialNotification to set.
	 */
	public void setInitialNotification(String initialNotification) {
		this.initialNotification = initialNotification;
	}

	public String getIdKeyDescription() {
		return idKeyDescription;
	}

	public void setIdKeyDescription(String idKeyDescription) {
		this.idKeyDescription = idKeyDescription;
	}
}
