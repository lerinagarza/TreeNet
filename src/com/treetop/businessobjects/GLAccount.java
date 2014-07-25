/*
 * Created on Feb 17, 2006
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Resource information (number,description,etc)
 */
public class GLAccount {
	
	protected	String		accountNumber	= "";
	protected	String		pageNumber		= "";	
	protected	String		description		= "";
	protected   String		accountType		= "";
	protected	String		activeFlag		= "";
	protected	String		userField01		= "";
	protected	String		userField02		= "";
	protected	String		userField03		= "";
	protected	String		userField04		= "";

	/**
	 * 
	 */
	public GLAccount() {
		super();
	}

	/**
	 * @return Returns the accountNumber.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber The accountNumber to set.
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return Returns the accountType.
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @return Returns the activeFlag.
	 */
	public String getActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag The activeFlag to set.
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public String getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return Returns the userField01.
	 */
	public String getUserField01() {
		return userField01;
	}
	/**
	 * @param userField01 The userField01 to set.
	 */
	public void setUserField01(String userField01) {
		this.userField01 = userField01;
	}
	/**
	 * @return Returns the userField02.
	 */
	public String getUserField02() {
		return userField02;
	}
	/**
	 * @param userField02 The userField02 to set.
	 */
	public void setUserField02(String userField02) {
		this.userField02 = userField02;
	}
	/**
	 * @return Returns the userField03.
	 */
	public String getUserField03() {
		return userField03;
	}
	/**
	 * @param userField03 The userField03 to set.
	 */
	public void setUserField03(String userField03) {
		this.userField03 = userField03;
	}
	/**
	 * @return Returns the userField04.
	 */
	public String getUserField04() {
		return userField04;
	}
	/**
	 * @param userField04 The userField04 to set.
	 */
	public void setUserField04(String userField04) {
		this.userField04 = userField04;
	}
}

