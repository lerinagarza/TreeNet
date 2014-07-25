/*
 * Created on December 30, 2008
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 * General Warehouse Information
 */
public class User {
	
	protected	String		userID		= ""; 
	protected	String		userName	= "";
	protected	String		outQ		= "";
	protected	String		outQDescr	= "";


	
	/**
	 *  // Constructor
	 *  @param Sting UserID
	 */
	public User(String user) {
		super();
		this.setUserID(user);

	}
	
	/**
	 *  // Constructor
	 */
	public User() {
		super();
	}



	public String getOutQ() {
		return outQ;
	}



	public void setOutQ(String outQ) {
		this.outQ = outQ;
	}



	public String getUserID() {
		return userID;
	}



	public void setUserID(String userID) {
		this.userID = userID;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOutQDescr() {
		return outQDescr;
	}

	public void setOutQDescr(String outQDescr) {
		this.outQDescr = outQDescr;
	}
	
}
