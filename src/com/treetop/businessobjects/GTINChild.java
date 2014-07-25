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
 * Contains values specific to a GTIN  Child.
 */
public class GTINChild extends GTIN{
	
	String		childSeq;
	String		childQty;
	String		lastUpdateDate;
	String		lastUpdateTime;
	String		lastUpdateUser;
	

	/**
	 * 
	 */
	public GTINChild() {
		super();
	}

	/**
	 * @return
	 */
	public String getChildQty() {
		return childQty;
	}

	/**
	 * @return
	 */
	public String getChildSeq() {
		return childSeq;
	}

	/**
	 * @param string
	 */
	public void setChildQty(String string) {
		childQty = string;
	}

	/**
	 * @param string
	 */
	public void setChildSeq(String string) {
		childSeq = string;
	}

	/**
	 * @return
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @return
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateDate(String string) {
		lastUpdateDate = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateTime(String string) {
		lastUpdateTime = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateUser(String string) {
		lastUpdateUser = string;
	}

}
