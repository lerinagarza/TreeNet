/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;
import com.treetop.utilities.html.*;


/**
 * @author twalto
 *
 * Store Attribute Related Information --
 *     Could include Attribute Model Information Also
 */
public class Attribute {
	
	protected	String		attributeModel				  = "";
	protected   String		attributeModelName			  = "";
	protected	String		attributeModelDescription 	  = "";
	protected	String		attribute					  = "";
	protected	String		attributeName				  = "";
	protected	String		attributeDescription		  = "";
	protected	Vector<HtmlOption>	attributeOptions	  = new Vector<HtmlOption>();
	protected	String		lowValue					  = "";
	protected	String		highValue					  = "";
	protected	String		fieldType					  = "";
	protected	String		attributeSequence			  = "";
	protected	String		decimalPlaces				  = "";
	protected	String		attributeUOM				  = "";
	protected	String		attributeUOMDescription		  = "";
	
	/**
	 *  // Constructor
	 */
	public Attribute() {
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
	 * @return Returns the attributeDescription.
	 */
	public String getAttributeDescription() {
		return attributeDescription;
	}
	/**
	 * @param attributeDescription The attributeDescription to set.
	 */
	public void setAttributeDescription(String attributeDescription) {
		this.attributeDescription = attributeDescription;
	}
	/**
	 * @return Returns the attributeModel.
	 */
	public String getAttributeModel() {
		return attributeModel;
	}
	/**
	 * @param attributeModel The attributeModel to set.
	 */
	public void setAttributeModel(String attributeModel) {
		this.attributeModel = attributeModel;
	}
	/**
	 * @return Returns the attributeModelDescription.
	 */
	public String getAttributeModelDescription() {
		return attributeModelDescription;
	}
	/**
	 * @param attributeModelDescription The attributeModelDescription to set.
	 */
	public void setAttributeModelDescription(String attributeModelDescription) {
		this.attributeModelDescription = attributeModelDescription;
	}
	/**
	 * @return Returns the attributeName.
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName The attributeName to set.
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	/**
	 * @return Returns the highValue.
	 */
	public String getHighValue() {
		return highValue;
	}
	/**
	 * @param highValue The highValue to set.
	 */
	public void setHighValue(String highValue) {
		this.highValue = highValue;
	}
	/**
	 * @return Returns the lowValue.
	 */
	public String getLowValue() {
		return lowValue;
	}
	/**
	 * @param lowValue The lowValue to set.
	 */
	public void setLowValue(String lowValue) {
		this.lowValue = lowValue;
	}
	/**
	 * @return Returns the attributeModelName.
	 */
	public String getAttributeModelName() {
		return attributeModelName;
	}
	/**
	 * @param attributeModelName The attributeModelName to set.
	 */
	public void setAttributeModelName(String attributeModelName) {
		this.attributeModelName = attributeModelName;
	}
	/**
	 * @return Returns the attributeSequence.
	 */
	public String getAttributeSequence() {
		return attributeSequence;
	}
	/**
	 * @param attributeSequence The attributeSequence to set.
	 */
	public void setAttributeSequence(String attributeSequence) {
		this.attributeSequence = attributeSequence;
	}
	/**
	 * @return Returns the fieldType.
	 */
	public String getFieldType() {
		return fieldType;
	}
	/**
	 * @param fieldType The fieldType to set.
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * @return Returns the decimalPlaces.
	 */
	public String getDecimalPlaces() {
		return decimalPlaces;
	}
	/**
	 * @param decimalPlaces The decimalPlaces to set.
	 */
	public void setDecimalPlaces(String decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	public String getAttributeUOM() {
		return attributeUOM;
	}
	public void setAttributeUOM(String attributeUOM) {
		this.attributeUOM = attributeUOM;
	}
	public String getAttributeUOMDescription() {
		return attributeUOMDescription;
	}
	public void setAttributeUOMDescription(String attributeUOMDescription) {
		this.attributeUOMDescription = attributeUOMDescription;
	}
	public Vector<HtmlOption> getAttributeOptions() {
		return attributeOptions;
	}
	public void setAttributeOptions(Vector<HtmlOption> attributeOptions) {
		this.attributeOptions = attributeOptions;
	}
}
