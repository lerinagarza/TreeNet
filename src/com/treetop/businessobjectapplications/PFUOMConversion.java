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
 * Store Plant Floor Product UOM Conversion Related Information --
 *     
 */
public class PFUOMConversion extends PFProduct{
	
	protected   String		unitOfMeasure		= "";
	protected	String		unitDescription		= "";
	protected	String		conversionFactor	= "0";
		
	/**
	 *  // Constructor
	 */
	public PFUOMConversion() {
		super();

	}

	public String getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
}
