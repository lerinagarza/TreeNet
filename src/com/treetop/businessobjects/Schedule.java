/*
 * Created on Apr 4, 2006
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Production and Forecast Schedule Information.
 */
public class Schedule extends Model {
	
	private	String	schedule	= "";
	private	String	olsn		= "";
	private	String	release		= "";

	/**
	 * @return Returns the olsn.
	 */
	public String getOlsn() {
		return olsn;
	}
	/**
	 * @param olsn The olsn to set.
	 */
	public void setOlsn(String olsn) {
		this.olsn = olsn;
	}
	/**
	 * @return Returns the release.
	 */
	public String getRelease() {
		return release;
	}
	/**
	 * @param release The release to set.
	 */
	public void setRelease(String release) {
		this.release = release;
	}
	/**
	 * @return Returns the schedule.
	 */
	public String getSchedule() {
		return schedule;
	}
	/**
	 * @param schedule The schedule to set.
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
}
