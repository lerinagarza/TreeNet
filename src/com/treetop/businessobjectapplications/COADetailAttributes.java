/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.Attribute;

/**
 * @author twalto
 *
 * Store Attribute Related Information --
 *     Could include Attribute Model Information Also
 */
public class COADetailAttributes extends COADetail {

	protected	String 			attribute	  		= "";
	protected	String			sequence		  	= "";
	protected	Attribute		attributeClass		= new Attribute();
	
	/**
	 *  // Constructor
	 */
	public COADetailAttributes() {
		super();

	}
	/**
	 * @return Returns the attribute.
	 */
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute The attribute to set.
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
	 * @return Returns the sequence.
	 */
	public String getSequence() {
		return sequence;
	}
	/**
	 * @param sequence The sequence to set.
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return Returns the attributeClass.
	 */
	public Attribute getAttributeClass() {
		return attributeClass;
	}
	/**
	 * @param attributeClass The attributeClass to set.
	 */
	public void setAttributeClass(Attribute attributeClass) {
		this.attributeClass = attributeClass;
	}
}
