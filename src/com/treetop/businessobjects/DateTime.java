/*
 * Created on March 5, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Date and Time Information
 */
public class DateTime {
	
	protected	String		m3FiscalYear				= "";	
	protected   String		m3FiscalPeriod				= "";
	protected   String		m3FiscalWeek				= "";
	
	protected	String		century						= "";
	protected	String		year						= "";
	protected	String		month						= "";
	protected	String		day							= "";
	
	protected	String		dateFormatMMddyy			= "";
	protected	String		dateFormatMMddyySlash		= "";
	protected	String		dateFormatMMddyyyy			= "";
	protected	String		dateFormatMMddyyyySlash 	= "";
	protected	String		dateFormatyyyyMMdd			= "";
	protected	String		dateFormatyyyyMMddDash		= "";
	protected	String		dateFormatMonthNameddyyyy	= "";
	protected	String		dateFormatddMMMyySlash		= "";
	protected	String		dateFormatyyddd				= "";
	protected	String		dateFormatJulian			= "";
	protected	String		dateFormat100Year			= "";
	
	protected	String		dayOfWeek					= "";
	protected	String		dayOfWeekShort				= "";
	protected	String		dayOfWeekNumber				= "";

	protected	String		timeFormathhmmss			= "";
	protected	String		timeFormathhmmssColon		= "";
	protected	String		timeFormathhmm				= "";
	protected	String		timeFormathhmmColon			= "";
	protected	String		timeFormatAMPM				= "";
	
	protected	String		dateErrorMessage			= "";

	/**
	 *  // Constructor
	 */
	public DateTime() {
		super();

	}
	/**
	 * @return Returns the dateFormat100Year.
	 */
	public String getDateFormat100Year() {
		return dateFormat100Year;
	}
	/**
	 * @param dateFormat100Year The dateFormat100Year to set.
	 */
	public void setDateFormat100Year(String dateFormat100Year) {
		this.dateFormat100Year = dateFormat100Year;
	}
	/**
	 * @return Returns the dateFormatddMMMyySlash.
	 */
	public String getDateFormatddMMMyySlash() {
		return dateFormatddMMMyySlash;
	}
	/**
	 * @param dateFormatddMMMyySlash The dateFormatddMMMyySlash to set.
	 */
	public void setDateFormatddMMMyySlash(String dateFormatddMMMyySlash) {
		this.dateFormatddMMMyySlash = dateFormatddMMMyySlash;
	}
	/**
	 * @return Returns the dateFormatJulian.
	 */
	public String getDateFormatJulian() {
		return dateFormatJulian;
	}
	/**
	 * @param dateFormatJulian The dateFormatJulian to set.
	 */
	public void setDateFormatJulian(String dateFormatJulian) {
		this.dateFormatJulian = dateFormatJulian;
	}
	/**
	 * @return Returns the dateFormatMMddyy.
	 */
	public String getDateFormatMMddyy() {
		return dateFormatMMddyy;
	}
	/**
	 * @param dateFormatMMddyy The dateFormatMMddyy to set.
	 */
	public void setDateFormatMMddyy(String dateFormatMMddyy) {
		this.dateFormatMMddyy = dateFormatMMddyy;
	}
	/**
	 * @return Returns the dateFormatMMddyySlash.
	 */
	public String getDateFormatMMddyySlash() {
		return dateFormatMMddyySlash;
	}
	/**
	 * @param dateFormatMMddyySlash The dateFormatMMddyySlash to set.
	 */
	public void setDateFormatMMddyySlash(String dateFormatMMddyySlash) {
		this.dateFormatMMddyySlash = dateFormatMMddyySlash;
	}
	/**
	 * @return Returns the dateFormatMMddyyyy.
	 */
	public String getDateFormatMMddyyyy() {
		return dateFormatMMddyyyy;
	}
	/**
	 * @param dateFormatMMddyyyy The dateFormatMMddyyyy to set.
	 */
	public void setDateFormatMMddyyyy(String dateFormatMMddyyyy) {
		this.dateFormatMMddyyyy = dateFormatMMddyyyy;
	}
	/**
	 * @return Returns the dateFormatMMddyyyySlash.
	 */
	public String getDateFormatMMddyyyySlash() {
		return dateFormatMMddyyyySlash;
	}
	/**
	 * @param dateFormatMMddyyyySlash The dateFormatMMddyyyySlash to set.
	 */
	public void setDateFormatMMddyyyySlash(String dateFormatMMddyyyySlash) {
		this.dateFormatMMddyyyySlash = dateFormatMMddyyyySlash;
	}
	/**
	 * @return Returns the dateFormatMonthNameddyyyy.
	 */
	public String getDateFormatMonthNameddyyyy() {
		return dateFormatMonthNameddyyyy;
	}
	/**
	 * @param dateFormatMonthNameddyyyy The dateFormatMonthNameddyyyy to set.
	 */
	public void setDateFormatMonthNameddyyyy(String dateFormatMonthNameddyyyy) {
		this.dateFormatMonthNameddyyyy = dateFormatMonthNameddyyyy;
	}
	/**
	 * @return Returns the dateFormatyyddd.
	 */
	public String getDateFormatyyddd() {
		return dateFormatyyddd;
	}
	/**
	 * @param dateFormatyyddd The dateFormatyyddd to set.
	 */
	public void setDateFormatyyddd(String dateFormatyyddd) {
		this.dateFormatyyddd = dateFormatyyddd;
	}
	/**
	 * @return Returns the dateFormatyyyyMMdd.
	 */
	public String getDateFormatyyyyMMdd() {
		return dateFormatyyyyMMdd;
	}
	/**
	 * @param dateFormatyyyyMMdd The dateFormatyyyyMMdd to set.
	 */
	public void setDateFormatyyyyMMdd(String dateFormatyyyyMMdd) {
		this.dateFormatyyyyMMdd = dateFormatyyyyMMdd;
	}
	/**
	 * @return Returns the dateFormatyyyyMMddDash.
	 */
	public String getDateFormatyyyyMMddDash() {
		return dateFormatyyyyMMddDash;
	}
	/**
	 * @param dateFormatyyyyMMddDash The dateFormatyyyyMMddDash to set.
	 */
	public void setDateFormatyyyyMMddDash(String dateFormatyyyyMMddDash) {
		this.dateFormatyyyyMMddDash = dateFormatyyyyMMddDash;
	}
	/**
	 * @return Returns the dayOfWeek.
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	/**
	 * @param dayOfWeek The dayOfWeek to set.
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	/**
	 * @return Returns the dayOfWeekNumber.
	 */
	public String getDayOfWeekNumber() {
		return dayOfWeekNumber;
	}
	/**
	 * @param dayOfWeekNumber The dayOfWeekNumber to set.
	 */
	public void setDayOfWeekNumber(String dayOfWeekNumber) {
		this.dayOfWeekNumber = dayOfWeekNumber;
	}
	/**
	 * @return Returns the dayOfWeekShort.
	 */
	public String getDayOfWeekShort() {
		return dayOfWeekShort;
	}
	/**
	 * @param dayOfWeekShort The dayOfWeekShort to set.
	 */
	public void setDayOfWeekShort(String dayOfWeekShort) {
		this.dayOfWeekShort = dayOfWeekShort;
	}
	/**
	 * @return Returns the m3FiscalPeriod.
	 */
	public String getM3FiscalPeriod() {
		return m3FiscalPeriod;
	}
	/**
	 * @param fiscalPeriod The m3FiscalPeriod to set.
	 */
	public void setM3FiscalPeriod(String fiscalPeriod) {
		m3FiscalPeriod = fiscalPeriod;
	}
	/**
	 * @return Returns the m3FiscalYear.
	 */
	public String getM3FiscalYear() {
		return m3FiscalYear;
	}
	/**
	 * @param fiscalYear The m3FiscalYear to set.
	 */
	public void setM3FiscalYear(String fiscalYear) {
		m3FiscalYear = fiscalYear;
	}
	/**
	 * @return Returns the timeFormathhmmss.
	 */
	public String getTimeFormathhmmss() {
		return timeFormathhmmss;
	}
	/**
	 * @param timeFormathhmmss The timeFormathhmmss to set.
	 */
	public void setTimeFormathhmmss(String timeFormathhmmss) {
		this.timeFormathhmmss = timeFormathhmmss;
	}
	/**
	 * @return Returns the timeFormathhmmssColon.
	 */
	public String getTimeFormathhmmssColon() {
		return timeFormathhmmssColon;
	}
	/**
	 * @param timeFormathhmmssColon The timeFormathhmmssColon to set.
	 */
	public void setTimeFormathhmmssColon(String timeFormathhmmssColon) {
		this.timeFormathhmmssColon = timeFormathhmmssColon;
	}
	/**
	 * @return Returns the dateErrorMessage.
	 */
	public String getDateErrorMessage() {
		return dateErrorMessage;
	}
	/**
	 * @param dateErrorMessage The dateErrorMessage to set.
	 */
	public void setDateErrorMessage(String dateErrorMessage) {
		this.dateErrorMessage = dateErrorMessage;
	}
	/**
	 * @return Returns the timeFormatAMPM.
	 */
	public String getTimeFormatAMPM() {
		return timeFormatAMPM;
	}
	/**
	 * @param timeFormatAMPM The timeFormatAMPM to set.
	 */
	public void setTimeFormatAMPM(String timeFormatAMPM) {
		this.timeFormatAMPM = timeFormatAMPM;
	}
	/**
	 * @return Returns the century.
	 */
	public String getCentury() {
		return century;
	}
	/**
	 * @param century The century to set.
	 */
	public void setCentury(String century) {
		this.century = century;
	}
	/**
	 * @return Returns the day.
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day The day to set.
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}
	public String getTimeFormathhmm() {
		return timeFormathhmm;
	}
	public void setTimeFormathhmm(String timeFormathhmm) {
		this.timeFormathhmm = timeFormathhmm;
	}
	public String getTimeFormathhmmColon() {
		return timeFormathhmmColon;
	}
	public void setTimeFormathhmmColon(String timeFormathhmmColon) {
		this.timeFormathhmmColon = timeFormathhmmColon;
	}
	public String getM3FiscalWeek() {
		return m3FiscalWeek;
	}
	public void setM3FiscalWeek(String m3FiscalWeek) {
		this.m3FiscalWeek = m3FiscalWeek;
	}
}
