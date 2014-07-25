/*
 * Created on May 25, 2010 
 */

package com.treetop.businessobjects;

/**
 * @author deisen
 *
 * Contains quality specification formula detail information.
 *   8/16/11 TW - Added Fields
 * 	
 */

public class QaFormulaDetail extends QaFormula{
	
								// from File 	dbprd/QAPJFODT
	 							//           m3djdprd/MITMAS
								//			 m3djdprd/CIDMAS
	protected	String			identificationCode		= "";	//FDCODE
	protected	String			sequenceNumber			= "";	//FDSEQ#
	protected	String			sauceTargetBrix			= "";	//FDBRIX
	protected	String			sauceBatchQuantity		= "";	//FDQTTY
	protected	String			sauceBatchUOM			= "";	//FDBUOM
	protected	String			itemNumber1				= "";	//FDITNO
	protected	String			itemDescription1		= "";	//FDITDS	
	protected	String			itemQuantity1			= "";	//FDQTY1
	protected	String			itemUnitOfMeasure1		= "";	//FDUOM1
	protected	String			itemNumber2				= "";	//FDITNO2
	protected	String			itemDescription2		= "";	//FDITDS2
	protected	String			itemQuantity2			= "";	//FDQTY2
	protected	String			itemUnitOfMeasure2		= "";	//FDUOM2
	protected	String			itemNumber3				= "";	//FDITNO3
	protected	String			itemDescription3		= "";	//FDITDS3
	protected	String			itemQuantity3			= "";	//FDQTY3
	protected	String			itemUnitOfMeasure3		= "";	//FDUOM3
	protected 	String			supplierNumber			= "";	//FDSUNO
	protected	String			supplierName			= "";	//FDSUNM
	protected	String			referenceSpecNumber		= "";	//FDRSNO
	
	/**
	 *  // Constructor
	 */
	public QaFormulaDetail() {
		super();
	}

	/**
	 * @return Returns the item sequencing number.
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param Sets the item sequencing number.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the first item quantity.
	 */
	public String getItemQuantity1() {
		return itemQuantity1;
	}
	/**
	 * @param Sets the first item quantity.
	 */
	public void setItemQuantity1(String itemQuantity1) {
		this.itemQuantity1 = itemQuantity1;
	}
	/**
	 * @return Returns the first item unit of measure.
	 */
	public String getItemUnitOfMeasure1() {
		return itemUnitOfMeasure1;
	}
	/**
	 * @param Sets the first item unit of measure.
	 */
	public void setItemUnitOfMeasure1(String itemUnitOfMeasure1) {
		this.itemUnitOfMeasure1 = itemUnitOfMeasure1;
	}	
	/**
	 * @return Returns the second item quantity.
	 */
	public String getItemQuantity2() {
		return itemQuantity2;
	}
	/**
	 * @param Sets the second item quantity.
	 */
	public void setItemQuantity2(String itemQuantity2) {
		this.itemQuantity2 = itemQuantity2;
	}
	/**
	 * @return Returns the second item unit of measure.
	 */
	public String getItemUnitOfMeasure2() {
		return itemUnitOfMeasure2;
	}
	/**
	 * @param Sets the second item unit of measure.
	 */
	public void setItemUnitOfMeasure2(String itemUnitOfMeasure2) {
		this.itemUnitOfMeasure2 = itemUnitOfMeasure2;
	}	
	/**
	 * @return Returns the supplier number.
	 */
	public String getSupplierNumber() {
		return supplierNumber;
	}
	/**
	 * @param Sets the supplier number.
	 */
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}
	/**
	 * @return Returns the supplier name.
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param Sets the supplier name.
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return Returns the referenced specification number.
	 */
	public String getReferenceSpecNumber() {
		return referenceSpecNumber;
	}
	/**
	 * @param Sets the referenced specification number.
	 */
	public void setReferenceSpecNumber(String referenceSpecNumber) {
		this.referenceSpecNumber = referenceSpecNumber;
	}

	public String getIdentificationCode() {
		return identificationCode;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}

	public String getItemDescription1() {
		return itemDescription1;
	}

	public void setItemDescription1(String itemDescription1) {
		this.itemDescription1 = itemDescription1;
	}

	public String getItemDescription2() {
		return itemDescription2;
	}

	public void setItemDescription2(String itemDescription2) {
		this.itemDescription2 = itemDescription2;
	}

	public String getItemDescription3() {
		return itemDescription3;
	}

	public void setItemDescription3(String itemDescription3) {
		this.itemDescription3 = itemDescription3;
	}

	public String getItemNumber1() {
		return itemNumber1;
	}

	public void setItemNumber1(String itemNumber1) {
		this.itemNumber1 = itemNumber1;
	}

	public String getItemNumber2() {
		return itemNumber2;
	}

	public void setItemNumber2(String itemNumber2) {
		this.itemNumber2 = itemNumber2;
	}

	public String getItemNumber3() {
		return itemNumber3;
	}

	public void setItemNumber3(String itemNumber3) {
		this.itemNumber3 = itemNumber3;
	}

	public String getItemQuantity3() {
		return itemQuantity3;
	}

	public void setItemQuantity3(String itemQuantity3) {
		this.itemQuantity3 = itemQuantity3;
	}

	public String getItemUnitOfMeasure3() {
		return itemUnitOfMeasure3;
	}

	public void setItemUnitOfMeasure3(String itemUnitOfMeasure3) {
		this.itemUnitOfMeasure3 = itemUnitOfMeasure3;
	}

	public String getSauceBatchQuantity() {
		return sauceBatchQuantity;
	}

	public void setSauceBatchQuantity(String sauceBatchQuantity) {
		this.sauceBatchQuantity = sauceBatchQuantity;
	}

	public String getSauceBatchUOM() {
		return sauceBatchUOM;
	}

	public void setSauceBatchUOM(String sauceBatchUOM) {
		this.sauceBatchUOM = sauceBatchUOM;
	}

	public String getSauceTargetBrix() {
		return sauceTargetBrix;
	}

	public void setSauceTargetBrix(String sauceTargetBrix) {
		this.sauceTargetBrix = sauceTargetBrix;
	}

}