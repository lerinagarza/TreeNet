/*
 * Created on March 25, 2008
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
public class BeanItem {

	protected	Item	itemClass   = new Item();
	protected   Vector  comments	= new Vector();	//cast as KeyValue
	protected   Vector  functions   = new Vector(); //cast as TicklerFunctionDetail
	protected   Vector  itemUrls 	= new Vector(); //cast as KeyValue
	protected	Vector	itemVariance = new Vector(); // cast as ItemVariance
	protected	Vector	itemVariancePast = new Vector(); // cast as ItemVariance
	protected	Vector	itemVarianceFuture = new Vector(); // cast as ItemVariance
	protected	Vector	productStructure = new Vector(); // cast as productStructureMaterial
	/**
	 *  // Constructor
	 */
	public BeanItem() {
		super();

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
	 * @return Returns the comments.
	 */
	public Vector getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(Vector comments) {
		this.comments = comments;
	}
	/**
	 * @return Returns the functions.
	 */
	public Vector getFunctions() {
		return functions;
	}
	/**
	 * @param functions The functions to set.
	 */
	public void setFunctions(Vector functions) {
		this.functions = functions;
	}
	/**
	 * @return Returns the itemUrls.
	 */
	public Vector getItemUrls() {
		return itemUrls;
	}
	/**
	 * @param itemUrls The itemUrls to set.
	 */
	public void setItemUrls(Vector itemUrls) {
		this.itemUrls = itemUrls;
	}
	/**
	 * @return Returns the itemVariance.
	 */
	public Vector getItemVariance() {
		return itemVariance;
	}
	/**
	 * @param itemVariance The itemVariance to set.
	 */
	public void setItemVariance(Vector itemVariance) {
		this.itemVariance = itemVariance;
	}
	/**
	 * @return Returns the itemVarianceFuture.
	 */
	public Vector getItemVarianceFuture() {
		return itemVarianceFuture;
	}
	/**
	 * @param itemVarianceFuture The itemVarianceFuture to set.
	 */
	public void setItemVarianceFuture(Vector itemVarianceFuture) {
		this.itemVarianceFuture = itemVarianceFuture;
	}
	/**
	 * @return Returns the itemVariancePast.
	 */
	public Vector getItemVariancePast() {
		return itemVariancePast;
	}
	/**
	 * @param itemVariancePast The itemVariancePast to set.
	 */
	public void setItemVariancePast(Vector itemVariancePast) {
		this.itemVariancePast = itemVariancePast;
	}
	public Vector getProductStructure() {
		return productStructure;
	}
	public void setProductStructure(Vector productStructure) {
		this.productStructure = productStructure;
	}
	
	
	
}
