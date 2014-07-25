/*
 * Created on August 17, 2009
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Item;
import com.treetop.businessobjects.Warehouse;

/**
 * @author twalto
 *
 * Store Plant Floor Product Group Related Information --
 *     
 */
public class PFGroup extends PFProduct{
	
	protected   String		groupName		= "";
	protected	String		process			= "process"; // process or delete
		
	/**
	 *  // Constructor
	 */
	public PFGroup() {
		super();

	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
}
