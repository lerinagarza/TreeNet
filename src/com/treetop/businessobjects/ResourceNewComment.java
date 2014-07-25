/*
 * Created on Aug 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Contains values specific to the Resource Reservation 
 * System.
 */
public class ResourceNewComment extends Resource {


	String updateDate;
	String updateTime;
	String updateUser;
	String comment;
	String extraField1;
	String extraField2;

	/**
	 * 
	 */
	public ResourceNewComment() {
		super();
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return
	 */
	public String getExtraField1() {
		return extraField1;
	}

	/**
	 * @return
	 */
	public String getExtraField2() {
		return extraField2;
	}

	/**
	 * @return
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @return
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @param string
	 */
	public void setExtraField1(String string) {
		extraField1 = string;
	}

	/**
	 * @param string
	 */
	public void setExtraField2(String string) {
		extraField2 = string;
	}

	/**
	 * @param string
	 */
	public void setUpdateDate(String string) {
		updateDate = string;
	}

	/**
	 * @param string
	 */
	public void setUpdateTime(String string) {
		updateTime = string;
	}

	/**
	 * @param string
	 */
	public void setUpdateUser(String string) {
		updateUser = string;
	}

}
