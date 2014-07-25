/*
 * Created on October 23, 2008
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.*;

/**
 * @author twalto
 *
 * Bean to combine everything to send to the servlet
 */
public class BeanSpecification {

	protected	Vector	specNEW = new Vector(); // cast as SpecificationNEW
	
	protected	SpecificationFormula formulaClass = new SpecificationFormula();
	protected	SpecificationNEW	specClass = new SpecificationNEW();
	protected	Item				itemClass = new Item();
	protected	Vector				listTests = new Vector(); // cast SpecificationTestProcess
	protected	Vector				listProcesses = new Vector(); // cast SpecificationTestProcess
	/**
	 *  // Constructor
	 */
	public BeanSpecification() {
		super();

	}
/**
 * @return Returns the specNEW.
 */
public Vector getSpecNEW() {
	return specNEW;
}
/**
 * @param specNEW The specNEW to set.
 */
public void setSpecNEW(Vector specNEW) {
	this.specNEW = specNEW;
}
	/**
	 * @return Returns the listProcesses.
	 */
	public Vector getListProcesses() {
		return listProcesses;
	}
	/**
	 * @param listProcesses The listProcesses to set.
	 */
	public void setListProcesses(Vector listProcesses) {
		this.listProcesses = listProcesses;
	}
	/**
	 * @return Returns the listTests.
	 */
	public Vector getListTests() {
		return listTests;
	}
	/**
	 * @param listTests The listTests to set.
	 */
	public void setListTests(Vector listTests) {
		this.listTests = listTests;
	}
	/**
	 * @return Returns the specClass.
	 */
	public SpecificationNEW getSpecClass() {
		return specClass;
	}
	/**
	 * @param specClass The specClass to set.
	 */
	public void setSpecClass(SpecificationNEW specClass) {
		this.specClass = specClass;
	}
	/**
	 * @return Returns the itemClass.
	 */
	public Item getItemClass() {
		return itemClass;
	}
	/**
	 * @param itemClass The itemClass to set.
	 */
	public void setItemClass(Item itemClass) {
		this.itemClass = itemClass;
	}
	/**
	 * @return Returns the formulaClass.
	 */
	public SpecificationFormula getFormulaClass() {
		return formulaClass;
	}
	/**
	 * @param formulaClass The formulaClass to set.
	 */
	public void setFormulaClass(SpecificationFormula formulaClass) {
		this.formulaClass = formulaClass;
	}
}
