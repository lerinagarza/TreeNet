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
public class GTINView extends GTIN{
	
	String		viewSeq;
	String		viewLevel;
	String		viewChild;
	String		lastUpdateDate;
	String		lastUpdateTime;
	String		lastUpdateUser;
	
	/**
	 * 
	 */
	public GTINView() {
		super();
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

	/**
	 * @return
	 */
	public String getViewChild() {
		return viewChild;
	}

	/**
	 * @return
	 */
	public String getViewLevel() {
		return viewLevel;
	}

	/**
	 * @return
	 */
	public String getViewSeq() {
		return viewSeq;
	}

	/**
	 * @param string
	 */
	public void setViewChild(String string) {
		viewChild = string;
	}

	/**
	 * @param string
	 */
	public void setViewLevel(String string) {
		viewLevel = string;
	}

	/**
	 * @param string
	 */
	public void setViewSeq(String string) {
		viewSeq = string;
	}

}
