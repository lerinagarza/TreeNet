/*
 * Created on 2011-04-28
 * Author: JHAGLE
 */
package com.treetop.businessobjects;

import java.util.*;
import java.math.*;

/**
 * @author twalto
 *
 * Item information
 */
public class ProductStructureMaterial extends ProductStructure {
	
	protected	String		materialSequence			= "";
	protected	String		operationNumber				= "";	
	protected	String		fromDate					= "";
	protected	String		toDate						= "";
	protected	String		consumedQuantity			= "";
	protected	String		consumedQuantityWithWaste	= "";
	protected	String		consumedUnitOfMeasure		= "";
	protected	String		byProduct					= "";
	protected	String		materialLastModifiedDate			= "";
	protected	String		materialLastModifiedUser			= "";
	
	protected	Item		materialItem					;
	

	/**
	 *  // Constructor
	 */
	public ProductStructureMaterial() {
		super();

	}


	public String getByProduct() {
		return byProduct;
	}


	public void setByProduct(String byProduct) {
		this.byProduct = byProduct;
	}


	public String getConsumedQuantity() {
		return consumedQuantity;
	}


	public void setConsumedQuantity(String consumedQuantity) {
		this.consumedQuantity = consumedQuantity;
	}


	public String getConsumedQuantityWithWaste() {
		return consumedQuantityWithWaste;
	}


	public void setConsumedQuantityWithWaste(String consumedQuantityWithWaste) {
		this.consumedQuantityWithWaste = consumedQuantityWithWaste;
	}


	public String getConsumedUnitOfMeasure() {
		return consumedUnitOfMeasure;
	}


	public void setConsumedUnitOfMeasure(String consumedUnitOfMeasure) {
		this.consumedUnitOfMeasure = consumedUnitOfMeasure;
	}


	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	public Item getMaterialItem() {
		return materialItem;
	}


	public void setMaterialItem(Item materialItem) {
		this.materialItem = materialItem;
	}


	public String getMaterialLastModifiedDate() {
		return materialLastModifiedDate;
	}


	public void setMaterialLastModifiedDate(String materialLastModifiedDate) {
		this.materialLastModifiedDate = materialLastModifiedDate;
	}


	public String getMaterialLastModifiedUser() {
		return materialLastModifiedUser;
	}


	public void setMaterialLastModifiedUser(String materialLastModifiedUser) {
		this.materialLastModifiedUser = materialLastModifiedUser;
	}


	public String getMaterialSequence() {
		return materialSequence;
	}


	public void setMaterialSequence(String materialSequence) {
		this.materialSequence = materialSequence;
	}


	public String getOperationNumber() {
		return operationNumber;
	}


	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}


	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	
}
