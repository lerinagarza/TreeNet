package com.treetop.businessobjects;

import java.math.BigDecimal;
import java.util.Vector;

public class Safety {

	private BigDecimal daysWithoutTimeLossAccident		= BigDecimal.ZERO;
	private BigDecimal daysWithoutMedicalAccident		= BigDecimal.ZERO;
	private BigDecimal safetyIndex						= BigDecimal.ZERO;
	
	private BigDecimal hoursWorked						= BigDecimal.ZERO;
	
	private BigDecimal medicalCases						= BigDecimal.ZERO;
	private BigDecimal restrictedDutyCases				= BigDecimal.ZERO;
	private BigDecimal lostTimeCases					= BigDecimal.ZERO;
	
	private BigDecimal frequency						= BigDecimal.ZERO;
	private BigDecimal frequencyRate					= BigDecimal.ZERO;
	private BigDecimal totalRecordable					= BigDecimal.ZERO;
	private BigDecimal totalRecordableRate				= BigDecimal.ZERO;
	private BigDecimal daysAwayFromWork					= BigDecimal.ZERO;
	
	private BigDecimal severityRate						= BigDecimal.ZERO;
	
	private String     startingDate                     = new String();
	private String     endingDate 	                    = new String();
	
	private Vector<String>     yearStartDate            = new Vector<String>();
	private Vector<String>     yearEndDate              = new Vector<String>();
	private Vector<String>     weekStartDate            = new Vector<String>();
	private Vector<String>     weekEndDate              = new Vector<String>();
	private Vector<String>     monthStartDate           = new Vector<String>();
	private Vector<String>     monthEndDate             = new Vector<String>();	
	

	public BigDecimal getDaysWithoutTimeLossAccident() {
		return daysWithoutTimeLossAccident;
	}

	public void setDaysWithoutTimeLossAccident(BigDecimal daysWithoutTimeLossAccident) {
		this.daysWithoutTimeLossAccident = daysWithoutTimeLossAccident;
	}

	public BigDecimal getDaysWithoutMedicalAccident() {
		return daysWithoutMedicalAccident;
	}

	public void setDaysWithoutMedicalAccident(BigDecimal daysWithoutMedicalAccident) {
		this.daysWithoutMedicalAccident = daysWithoutMedicalAccident;
	}

	public BigDecimal getSafetyIndex() {
		return safetyIndex;
	}

	public void setSafetyIndex(BigDecimal safetyIndex) {
		this.safetyIndex = safetyIndex;
	}

	public BigDecimal getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(BigDecimal hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public BigDecimal getMedicalCases() {
		return medicalCases;
	}

	public void setMedicalCases(BigDecimal medicalCases) {
		this.medicalCases = medicalCases;
	}

	public BigDecimal getRestrictedDutyCases() {
		return restrictedDutyCases;
	}

	public void setRestrictedDutyCases(BigDecimal restrictedDutyCases) {
		this.restrictedDutyCases = restrictedDutyCases;
	}

	public BigDecimal getLostTimeCases() {
		return lostTimeCases;
	}

	public void setLostTimeCases(BigDecimal lostTimeCases) {
		this.lostTimeCases = lostTimeCases;
	}

	public BigDecimal getFrequency() {
		return frequency;
	}

	public void setFrequency(BigDecimal frequency) {
		this.frequency = frequency;
	}

	public BigDecimal getFrequencyRate() {
		return frequencyRate;
	}

	public void setFrequencyRate(BigDecimal frequencyRate) {
		this.frequencyRate = frequencyRate;
	}

	public BigDecimal getTotalRecordable() {
		return totalRecordable;
	}

	public void setTotalRecordable(BigDecimal totalRecordable) {
		this.totalRecordable = totalRecordable;
	}

	public BigDecimal getTotalRecordableRate() {
		return totalRecordableRate;
	}

	public void setTotalRecordableRate(BigDecimal totalRecordableRate) {
		this.totalRecordableRate = totalRecordableRate;
	}

	public BigDecimal getDaysAwayFromWork() {
		return daysAwayFromWork;
	}

	public void setDaysAwayFromWork(BigDecimal daysAwayFromWork) {
		this.daysAwayFromWork = daysAwayFromWork;
	}

	public BigDecimal getSeverityRate() {
		return severityRate;
	}

	public void setSeverityRate(BigDecimal severityRate) {
		this.severityRate = severityRate;
	}

	public String getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	public Vector<String> getYearStartDate() {
		return yearStartDate;
	}

	public void setYearStartDate(Vector<String> yearStartDate) {
		this.yearStartDate = yearStartDate;
	}

	public Vector<String> getYearEndDate() {
		return yearEndDate;
	}

	public void setYearEndDate(Vector<String> yearEndDate) {
		this.yearEndDate = yearEndDate;
	}

	public Vector<String> getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Vector<String> weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public Vector<String> getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(Vector<String> weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	public Vector<String> getMonthStartDate() {
		return monthStartDate;
	}

	public void setMonthStartDate(Vector<String> monthStartDate) {
		this.monthStartDate = monthStartDate;
	}

	public Vector<String> getMonthEndDate() {
		return monthEndDate;
	}

	public void setMonthEndDate(Vector<String> monthEndDate) {
		this.monthEndDate = monthEndDate;
	}
	
}
