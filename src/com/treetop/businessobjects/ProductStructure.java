/*
 * Created on 2011-04-28
 * Author: JHAGLE
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Item information
 */
public class ProductStructure {
	
	protected	String		facility					= "";
	protected	String		mainWarehouse				= "";
	protected	String		structureType				= "";	
	protected	String		structureStatus						= "";
	protected	String		structureResponsible					= "";
	protected	String		structureLastModifiedDate			= "";
	
	protected	Item		producedItem					;
	

	/**
	 *  // Constructor
	 */
	public ProductStructure() {
		super();

	}


	public String getFacility() {
		return facility;
	}


	public void setFacility(String facility) {
		this.facility = facility;
	}


	public Item getProducedItem() {
		return producedItem;
	}


	public void setProducedItem(Item producedItem) {
		this.producedItem = producedItem;
	}


	public String getStructureLastModifiedDate() {
		return structureLastModifiedDate;
	}


	public void setStructureLastModifiedDate(String structureLastModifiedDate) {
		this.structureLastModifiedDate = structureLastModifiedDate;
	}


	public String getStructureResponsible() {
		return structureResponsible;
	}


	public void setStructureResponsible(String structureResponsible) {
		this.structureResponsible = structureResponsible;
	}


	public String getStructureStatus() {
		return structureStatus;
	}


	public void setStructureStatus(String structureStatus) {
		this.structureStatus = structureStatus;
	}


	public String getStructureType() {
		return structureType;
	}


	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}


	public String getMainWarehouse() {
		return mainWarehouse;
	}


	public void setMainWarehouse(String mainWarehouse) {
		this.mainWarehouse = mainWarehouse;
	}


	
	
	
}
