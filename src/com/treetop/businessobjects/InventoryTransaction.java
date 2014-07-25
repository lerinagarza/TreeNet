/*
 * Created on Feb 6, 2006 
 */
package com.treetop.businessobjects;

/**
 * @author David Eisenheim
 *
 * Prepare an inventory transaction for posting.
 */
public class InventoryTransaction {
	
	// class data for DCSFIN.
	
	String 		processorId;		// TMPID
	String		command;			// TMITRT		
	String		auditNumber;		// TMITAU
	String		number1;			// TMITPC
	String		number2;			// TMITCJ
	Integer		timeHours;			// TMITRH
	String		timeColonHrsMin;	// TMITRC
	Integer		timeMinutes;		// TMITRM
	String		timeColonMinSec;	// TMITCN
	Integer		timeSeconds;		// TMITRS
	Integer		dateDay;			// TMITRD
	Integer		dateMonth;			// TMITMT
	Integer		dateYear;			// TMITRY
	String		workstationId;		// TMITER
	String		transactionType;	// TMITRN
	String		transactionData;	// TMIDTA	Contains each specific transaction type

	/**
	 * Basic constructor.
	 */
	public InventoryTransaction() {
		super();
	}

	/**
	 * Returns the auditNumber.
	 */
	public String getAuditNumber() {
		return auditNumber;
	}
	/**
	 * Set the auditNumber.
	 */
	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}
	/**
	 * Returns the command.
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * Set the command.
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * Returns the day portion of the date.
	 */
	public Integer getDateDay() {
		return dateDay;
	}
	/**
	 * Set the day portion of the date.
	 */
	public void setDateDay(Integer dateDay) {
		this.dateDay = dateDay;
	}
	/**
	 * Returns the month portion of the date.
	 */
	public Integer getDateMonth() {
		return dateMonth;
	}
	/**
	 * Set the month portion of the date.
	 */
	public void setDateMonth(Integer dateMonth) {
		this.dateMonth = dateMonth;
	}
	/**
	 * Returns the year portion of the date.
	 */
	public Integer getDateYear() {
		return dateYear;
	}
	/**
	 * Set the year portion of the date.
	 */
	public void setDateYear(Integer dateYear) {
		this.dateYear = dateYear;
	}
	/**
	 * Returns the first number.
	 */
	public String getNumber1() {
		return number1;
	}
	/**
	 * Set the first number.
	 */
	public void setNumber1(String number1) {
		this.number1 = number1;
	}
	/**
	 * Returns the second number.
	 */
	public String getNumber2() {
		return number2;
	}
	/**
	 * Set the second number.
	 */
	public void setNumber2(String number2) {
		this.number2 = number2;
	}
	/**
	 * Returns the processor identification.
	 */
	public String getProcessorId() {
		return processorId;
	}
	/**
	 * Set the processor identification.
	 */
	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}
	/**
	 * Returns the colon between the hours and minutes.
	 */
	public String getTimeColonHrsMin() {
		return timeColonHrsMin;
	}
	/**
	 * Set the colon between the hours and minutes.
	 */
	public void setTimeColonHrsMin(String timeColonHrsMin) {
		this.timeColonHrsMin = timeColonHrsMin;
	}
	/**
	 * Returns the colon between the minutes and seconds.
	 */
	public String getTimeColonMinSec() {
		return timeColonMinSec;
	}
	/**
	 * Set the colon between the minutes and seconds.
	 */
	public void setTimeColonMinSec(String timeColonMinSec) {
		this.timeColonMinSec = timeColonMinSec;
	}
	/**
	 * Returns the time of day hours.
	 */
	public Integer getTimeHours() {
		return timeHours;
	}
	/**
	 * Set the time of day hours.
	 */
	public void setTimeHours(Integer timeHours) {
		this.timeHours = timeHours;
	}
	/**
	 * Returns the time of day minutes.
	 */
	public Integer getTimeMinutes() {
		return timeMinutes;
	}
	/**
	 * Set the time of day minutes.
	 */
	public void setTimeMinutes(Integer timeMinutes) {
		this.timeMinutes = timeMinutes;
	}
	/**
	 * Returns the time of day seconds.
	 */
	public Integer getTimeSeconds() {
		return timeSeconds;
	}
	/**
	 * Set the time of day seconds.
	 */
	public void setTimeSeconds(Integer timeSeconds) {
		this.timeSeconds = timeSeconds;
	}
	/**
	 * Returns the data for a specific transaction type.
	 */
	public String getTransactionData() {
		return transactionData;
	}
	/**
	 * Set the data for a specific transaction type.
	 */
	public void setTransactionData(String transactionData) {
		this.transactionData = transactionData;
	}
	/**
	 * Returns the type of transaction code.
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * set the type of transaction code.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * Returns the workstation identification.
	 */
	public String getWorkstationId() {
		return workstationId;
	}
	/**
	 * Set the workstation identification.
	 */
	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}
}
