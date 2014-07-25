/*
 * Created on Dec 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;
/**
 * @author thaile
 *
 * Tickler: Assign A date and user to A Business Process.
 * 	Group: A collection of Business Processes like New Item.
 * 	Function: A segment of a Group.
 */
public class TicklerFunction extends TicklerGroup {
	
	Integer		number;
	String		description;
	String		functionSequence;
	String		phaseName;
	String		phaseSequence;
	Integer		phaseNumberOfDays;
	Integer		leadTimeDays;
	String		reminderText;
	String		processDocument;
	String		verifyLink;
	String		defaultRespPerson;
	String		estTargetDate;
	String		ManagerApproval;
	String 		ticklerResponsible;
	Vector		dependantFunctions = new Vector(); //cast FunctionDetail

	/**
	 * @return
	 */
	public String getDefaultRespPerson() {
		return defaultRespPerson;
	}

	/**
	 * @return
	 */
	public Vector getDependantFunctions() {
		return dependantFunctions;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public String getEstTargetDate() {
		return estTargetDate;
	}

	/**
	 * @return
	 */
	public String getFunctionSequence() {
		return functionSequence;
	}

	/**
	 * @return
	 */
	public Integer getLeadTimeDays() {
		return leadTimeDays;
	}

	/**
	 * @return
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @return
	 */
	public String getPhaseName() {
		return phaseName;
	}

	/**
	 * @return
	 */
	public Integer getPhaseNumberOfDays() {
		return phaseNumberOfDays;
	}

	/**
	 * @return
	 */
	public String getPhaseSequence() {
		return phaseSequence;
	}

	/**
	 * @return
	 */
	public String getProcessDocument() {
		return processDocument;
	}

	/**
	 * @return
	 */
	public String getReminderText() {
		return reminderText;
	}

	/**
	 * @return
	 */
	public String getVerifyLink() {
		return verifyLink;
	}

	/**
	 * @param string
	 */
	public void setDefaultRespPerson(String string) {
		defaultRespPerson = string;
	}

	/**
	 * @param vector
	 */
	public void setDependantFunctions(Vector vector) {
		dependantFunctions = vector;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param string
	 */
	public void setEstTargetDate(String string) {
		estTargetDate = string;
	}

	/**
	 * @param string
	 */
	public void setFunctionSequence(String string) {
		functionSequence = string;
	}

	/**
	 * @param integer
	 */
	public void setLeadTimeDays(Integer integer) {
		leadTimeDays = integer;
	}

	/**
	 * @param integer
	 */
	public void setNumber(Integer integer) {
		number = integer;
	}

	/**
	 * @param string
	 */
	public void setPhaseName(String string) {
		phaseName = string;
	}

	/**
	 * @param integer
	 */
	public void setPhaseNumberOfDays(Integer integer) {
		phaseNumberOfDays = integer;
	}

	/**
	 * @param string
	 */
	public void setPhaseSequence(String string) {
		phaseSequence = string;
	}

	/**
	 * @param string
	 */
	public void setProcessDocument(String string) {
		processDocument = string;
	}

	/**
	 * @param string
	 */
	public void setReminderText(String string) {
		reminderText = string;
	}

	/**
	 * @param string
	 */
	public void setVerifyLink(String string) {
		verifyLink = string;
	}

	/**
	 * @return
	 */
	public String getManagerApproval() {
		return ManagerApproval;
	}

	/**
	 * @param string
	 */
	public void setManagerApproval(String string) {
		ManagerApproval = string;
	}

	public String getTicklerResponsible() {
		return ticklerResponsible;
	}

	public void setTicklerResponsible(String ticklerResponsible) {
		this.ticklerResponsible = ticklerResponsible;
	}

}
