/**
 * 
 */
package com.treetop.view;

import java.lang.reflect.Type;
import java.util.Vector;

/**
 * @author jhagle
 *
 */
public class PopulatedVector extends Vector {

	private Class containingClass = null;
	
	/**
	 * Default constructor
	 */
	public PopulatedVector() {
		super();
	}
	
	/**
	 * Constructor with defined containing class
	 * @param c
	 */
	public PopulatedVector(Class c) {
		super();
		this.setContainingClass(c);
	}

	/**
	 * @return the containingClass
	 */
	public Class getContainingClass() {
		return containingClass;
	}

	/**
	 * @param containingClass the containingClass to set
	 */
	public void setContainingClass(Class containingClass) {
		this.containingClass = containingClass;
	}
	
}
