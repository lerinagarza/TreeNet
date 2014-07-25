/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Resource information (number,description,etc)
 */
public class Resource {
	
	protected	String		recordType				= "";
	protected	String		status					= "";
	protected	String		resourceNumber			= "";
	protected   String		resourceType			= "";
	protected	String		ownerCode				= "";
	protected	String		resourceDescription		= "";

	
	
	/**
	 * 
	 */
	public Resource() {
		super();
	}

	/**
	 * @return
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @return
	 */
	public String getResourceDescription() {
		return resourceDescription;
	}

	/**
	 * @return
	 */
	public String getResourceNumber() {
		return resourceNumber;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param string
	 */
	public void setRecordType(String string) {
		recordType = string;
	}

	/**
	 * @param string
	 */
	public void setResourceDescription(String string) {
		resourceDescription = string;
	}

	/**
	 * @param string
	 */
	public void setResourceNumber(String string) {
		resourceNumber = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @return
	 */
	public String getOwnerCode() {
		return ownerCode;
	}

	/**
	 * @return
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param string
	 */
	public void setOwnerCode(String string) {
		ownerCode = string;
	}

	/**
	 * @param string
	 */
	public void setResourceType(String string) {
		resourceType = string;
	}

}
