/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Container class to hold Resource Screen Application data.
 * 
 */
public class BeanResource {
	
	ResourceNew		resourceNewClass;

	/**
	 * 
	 */
	public BeanResource() {
		super();
	}

	/**
	 * @return
	 */
	public ResourceNew getResourceNewClass() {
		return resourceNewClass;
	}

	/**
	 * @param resource
	 */
	public void setResourceClass(ResourceNew resource) {
		resourceNewClass = resource;
	}

}
