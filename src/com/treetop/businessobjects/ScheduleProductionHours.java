/*
 * Created on Apr 4, 2006
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Labor and Downtime Hours assigned to a Schedule.
 */
public class ScheduleProductionHours extends Schedule {
	
	private	String	actualLaborHours	= "";
	private	String	actualDowntimeHours	= "";

	/**
	 * @return Returns the actualDowntimeHours.
	 */
	public String getActualDowntimeHours() {
		return actualDowntimeHours;
	}
	/**
	 * @param actualDowntimeHours The actualDowntimeHours to set.
	 */
	public void setActualDowntimeHours(String actualDowntimeHours) {
		this.actualDowntimeHours = actualDowntimeHours;
	}
	/**
	 * @return Returns the actualLaborHours.
	 */
	public String getActualLaborHours() {
		return actualLaborHours;
	}
	/**
	 * @param actualLaborHours The actualLaborHours to set.
	 */
	public void setActualLaborHours(String actualLaborHours) {
		this.actualLaborHours = actualLaborHours;
	}
}
