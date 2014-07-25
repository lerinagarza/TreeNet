/*
 * Created on Apr 4, 2006
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Container class to hold Yield-Schedule Labor and 
 * DownTime Hours Screen Application data.
 */
public class BeanScheduleYieldHoursInfo {
	
	private	ScheduleProductionHours 	hours;
	private	Vector						comments;
	private	Resource					resource;

	/**
	 * @return Returns the comments.
	 */
	public Vector getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(Vector comments) {
		this.comments = comments;
	}
	/**
	 * @return Returns the hours.
	 */
	public ScheduleProductionHours getHours() {
		return hours;
	}
	/**
	 * @param hours The hours to set.
	 */
	public void setHours(ScheduleProductionHours hours) {
		this.hours = hours;
	}
	/**
	 * @return Returns the resource.
	 */
	public Resource getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
