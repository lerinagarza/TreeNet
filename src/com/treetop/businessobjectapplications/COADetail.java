/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;
import com.treetop.businessobjects.*;

/**
 * @author twalto
 *
 * Store Attribute Related Information --
 *     Could include Attribute Model Information Also
 */
public class COADetail extends COA {

	protected	String 	itemNumber	  		= "";
	protected	String	itemComment		 	= "";
	protected	String	itemSignature		= "";
	protected	String	itemSpec			= "";
	protected	String  lineNumber			= "";
	protected	String	lineSuffix			= "";
	
	/**
	 *  // Constructor
	 */
	public COADetail() {
		super();

	}
	/**
	 * @return Returns the itemNumber.
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the itemComment.
	 */
	public String getItemComment() {
		return itemComment;
	}
	/**
	 * @param itemComment The itemComment to set.
	 */
	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}
	/**
	 * @return Returns the itemSignature.
	 */
	public String getItemSignature() {
		return itemSignature;
	}
	/**
	 * @param itemSignature The itemSignature to set.
	 */
	public void setItemSignature(String itemSignature) {
		this.itemSignature = itemSignature;
	}
	/**
	 * @return Returns the itemSpec.
	 */
	public String getItemSpec() {
		return itemSpec;
	}
	/**
	 * @param itemSpec The itemSpec to set.
	 */
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	/**
	 * @return Returns the lineNumber.
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return Returns the lineSuffix.
	 */
	public String getLineSuffix() {
		return lineSuffix;
	}
	/**
	 * @param lineSuffix The lineSuffix to set.
	 */
	public void setLineSuffix(String lineSuffix) {
		this.lineSuffix = lineSuffix;
	}
}
