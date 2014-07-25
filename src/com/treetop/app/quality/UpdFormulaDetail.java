/*
 * Created on April 28, 2010
 *
 */

package com.treetop.app.quality;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjectapplications.BeanItem;
import com.treetop.services.*;
import com.treetop.SessionVariables;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.utilities.html.*;
import com.treetop.utilities.*;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalto
 * 
 * Formula Detail Information this object will be used as a vector in UpdQAFormula
 * 
 */
public class UpdFormulaDetail extends BaseViewBeanR2  {
	
	// Must have in Update View Bean
	public String updateUser 			= "";
	
	// Generate not updateable
	public String formulaNumber 		= "";
	public String revisionDate 			= "";
	public String revisionTime 			= "";
	public String status 				= "";
	public String recordType			= "";
	
	// Fields Available for Update
	public String removeLine			= "";
	public String detailSequence 		= "0";
	public String detailSequenceError 	= ""; // test for numeric
	public String itemNumber1 			= "";
	public String itemNumber1Error 		= ""; // test for valid item
	public String itemDescription1 		= "";
	public String quantity1				= "0";
	public String quantity1Error		= ""; // test for numeric
	public String unitOfMeasure1		= ""; // test for valid UOM from M3
	public String unitOfMeasure1Error	= ""; // Must have a UOM if the Quantity is entered
	// Use for Cinnamon PreBlend information for Applesauce
	public String itemNumber2 			= "";
	public String itemNumber2Error 		= ""; // test for valid item
	public String itemDescription2 		= "";
	public String quantity2				= "0";
	public String quantity2Error		= ""; // test for numeric
	public String unitOfMeasure2		= ""; // test for valid UOM from M3
	public String unitOfMeasure2Error	= ""; // Must have a UOM if the Quantity is entered
	public String itemNumber3 			= "";
	public String itemNumber3Error 		= ""; // test for valid item
	public String itemDescription3 		= "";
	public String quantity3				= "0";
	public String quantity3Error		= ""; // test for numeric
	public String unitOfMeasure3		= ""; // test for valid UOM from M3
	public String unitOfMeasure3Error	= ""; // Must have a UOM if the Quantity is entered
	public String sauceBatchQty			= "0";
	public String sauceBatchQtyError	= "";
	public String sauceBatchQtyUOM		= "";
	public String sauceBatchQtyUOMError = "";
	public String sauceTargetBrix		= "0";
	public String sauceTargetBrixError	= "";
	
	public String referenceSpec 		= "0";
	public String referenceSpecError	= ""; // test for a valid spec
	public String supplierNumber 		= "";
	public String supplierNumberError	= ""; // test for a valid supplier
	public String supplierName 			= "";
	

	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * 
	 */
	public void validate() {
		//-------------------------------------------------------------------
		StringBuffer foundProblem = new StringBuffer();
		CommonRequestBean crb = new CommonRequestBean();
		crb.setCompanyNumber(this.getCompany());
		crb.setDivisionNumber(this.getDivision());
		crb.setEnvironment(this.getEnvironment());
		// Sequence Number **************************************************
	  	try{
	  		if (!this.getDetailSequence().trim().equals(""))
	  		{
	  			this.setDetailSequenceError(validateInteger(this.getDetailSequence()));  
	  			if (!this.getDetailSequenceError().trim().equals(""))
	  				foundProblem.append(this.getDetailSequenceError().trim() + "<br>");
	  		}
	  	}catch(Exception e){
	  		this.setDetailSequenceError("Problem Found with Sequence Number. " + e);
	  		foundProblem.append("Problem Found with Sequence Number<br>");
	  	}
	  	// Item Spec **************************************************
	  	try{
	  		if (!this.getReferenceSpec().trim().equals("") &&
	  			!this.getReferenceSpec().trim().equals("0"))
	  		{
	  			// will need to validate there is an active spec
//	  			this.setReferenceSpecError(ServiceQuality.verifySpecification(this.environment, this.referenceSpec));  
	  			if (!this.getReferenceSpecError().trim().equals(""))
	  				foundProblem.append(this.getReferenceSpecError().trim() + "<br>");
	  		}
	  		else
	  		{
	  			this.setReferenceSpec("0");
	  		}
	  	}catch(Exception e){
	  		this.setReferenceSpecError("Problem Found with Spec Number. " + e);
	  		foundProblem.append("Problem Found with Spec<br>");
	  	}
		// Item Number (1)************************************************
	  	try{
	  		if (!this.getItemNumber1().trim().equals(""))
	  		{
	  			crb.setIdLevel1(this.getItemNumber1().trim());
	  			// will need to validate that this is an M3 item
	  			this.setItemNumber1Error(ServiceItem.verifyItem(crb));
	  			if (!this.getItemNumber1Error().trim().equals(""))
	  				foundProblem.append(this.getItemNumber1Error() + "<br>");
	  		}
	  	}catch(Exception e){
	  		this.setItemNumber1Error("Problem Found with Item Number. " + e);
	  		foundProblem.append("Problem Found with Item Number<br>");
	  	}
		// Quantities for 1st Item  **************************************
	  	BigDecimal testQty = new BigDecimal("0");
	  	try{ // 1st Quantity and Unit of Measure
	  		if (this.getQuantity1().trim().equals(""))
	  			this.setQuantity1("0");
	  		if (!this.getQuantity1().trim().equals("0"))
	  		{
	  			this.setQuantity1Error(validateBigDecimal(this.getQuantity1().trim()));  
		  	  	if (!this.getQuantity1Error().trim().trim().equals(""))
		  		   foundProblem.append(this.getQuantity1Error().trim() + "<br>");
		  	  	else
		  	  	   testQty = new BigDecimal(this.getQuantity1().trim());
	  			if (testQty.compareTo(new BigDecimal("0")) > 0 && 
					this.getUnitOfMeasure1().trim().equals(""))
	  			{
	  				 this.setUnitOfMeasure1Error("If a Quantity is entered a Unit of Measure must be entered, please choose one");
					 foundProblem.append(this.getUnitOfMeasure1Error() + "<br>");
				}
			}  	  	
		}catch(Exception e){
	  		this.setQuantity1Error("Problem Found with First Quantity (1). " + e);
	  		foundProblem.append("Problem Found with First Quantity (1)<br>");
	  	}
//		 Item Number (2)************************************************
	  	try{
	  		if (!this.getItemNumber2().trim().equals(""))
	  		{
	  			crb.setIdLevel1(this.getItemNumber2().trim());
	  			// will need to validate that this is an M3 item
	  			this.setItemNumber2Error(ServiceItem.verifyItem(crb));
	  			if (!this.getItemNumber2Error().trim().equals(""))
	  				foundProblem.append(this.getItemNumber2Error() + "<br>");
	  		}
	  	}catch(Exception e){
	  		this.setItemNumber2Error("Problem Found with Item Number. " + e);
	  		foundProblem.append("Problem Found with Item Number<br>");
	  	}
		// Quantities for 2nd Item  **************************************
	  	try{ // 2nd Quantity and Unit of Measure
	  		if (this.getQuantity2().trim().equals(""))
	  			this.setQuantity2("0");
	  		testQty = new BigDecimal("0");
	  		if (!this.getQuantity2().trim().equals("0"))
	  		{
	  			this.setQuantity2Error(validateBigDecimal(this.getQuantity2().trim()));  
		  	  	if (!this.getQuantity2Error().trim().trim().equals(""))
		  		   foundProblem.append(this.getQuantity2Error().trim() + "<br>");
		  	  	else
		  	  	   testQty = new BigDecimal(this.getQuantity2().trim());
	  			if (testQty.compareTo(new BigDecimal("0")) > 0 && 
					this.getUnitOfMeasure2().trim().equals(""))
	  			{
	  				 this.setUnitOfMeasure2Error("If a Quantity is entered a Unit of Measure must be entered, please choose one");
					 foundProblem.append(this.getUnitOfMeasure2Error() + "<br>");
				}
			}  	  	
		}catch(Exception e){
	  		this.setQuantity2Error("Problem Found with Second Quantity (2). " + e);
	  		foundProblem.append("Problem Found with Second Quantity (2)<br>");
	  	}
//		 Item Number (3)************************************************
	  	try{
	  		if (!this.getItemNumber3().trim().equals(""))
	  		{
	  			crb.setIdLevel1(this.getItemNumber3().trim());
	  			// will need to validate that this is an M3 item
	  			this.setItemNumber3Error(ServiceItem.verifyItem(crb));
	  			if (!this.getItemNumber3Error().trim().equals(""))
	  				foundProblem.append(this.getItemNumber3Error() + "<br>");
	  		}
	  	}catch(Exception e){
	  		this.setItemNumber3Error("Problem Found with Item Number. " + e);
	  		foundProblem.append("Problem Found with Item Number<br>");
	  	}
		// Quantities for 3rd Item  **************************************
	  	try{ // 3rd Quantity and Unit of Measure
	  		if (this.getQuantity3().trim().equals(""))
	  			this.setQuantity3("0");
	  		testQty = new BigDecimal("0");
	  		if (!this.getQuantity3().trim().equals("0"))
	  		{
	  			this.setQuantity3Error(validateBigDecimal(this.getQuantity3().trim()));  
		  	  	if (!this.getQuantity3Error().trim().trim().equals(""))
		  		   foundProblem.append(this.getQuantity3Error().trim() + "<br>");
		  	  	else
		  	  	   testQty = new BigDecimal(this.getQuantity3().trim());
	  			if (testQty.compareTo(new BigDecimal("0")) > 0 && 
					this.getUnitOfMeasure3().trim().equals(""))
	  			{
	  				 this.setUnitOfMeasure3Error("If a Quantity is entered a Unit of Measure must be entered, please choose one");
					 foundProblem.append(this.getUnitOfMeasure3Error() + "<br>");
				}
			}  	  	
		}catch(Exception e){
	  		this.setQuantity3Error("Problem Found with Third Quantity (3). " + e);
	  		foundProblem.append("Problem Found with Third Quantity (3)<br>");
	  	}	  	
	  
	  	// Supplier Number ************************************************
	  	try{
	  		if (!this.supplierNumber.trim().equals(""))
	  		{
	  		    crb.setIdLevel1(this.supplierNumber);
	  			// will need to validate that this is an M3 supplier
	  		    this.setSupplierNumberError(ServiceSupplier.verifySupplier(crb));
	  			if (!this.supplierNumberError.trim().equals(""))
	  				foundProblem.append(this.supplierNumberError + "<br>");
	  		}
	  	}catch(Exception e){
	  		this.setSupplierNumberError("Problem Found with Supplier Number. " + e);
	  		foundProblem.append("Problem Found with Supplier Number<br>");
	  	}
	  
	  
	  	if (!foundProblem.toString().trim().equals(""))
	  		this.setDisplayMessage(foundProblem.toString().trim());
		return;
	}
	public String getFormulaNumber() {
		return formulaNumber;
	}
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplierNumber() {
		return supplierNumber;
	}
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;	
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;	
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getDetailSequence() {
		return detailSequence;
	}
	public void setDetailSequence(String detailSequence) {
		this.detailSequence = detailSequence;
	}
	public String getDetailSequenceError() {
		return detailSequenceError;
	}
	public void setDetailSequenceError(String detailSequenceError) {
		this.detailSequenceError = detailSequenceError;
	}
	public String getUnitOfMeasure1() {
		return unitOfMeasure1;
	}
	public void setUnitOfMeasure1(String unitOfMeasure1) {
		this.unitOfMeasure1 = unitOfMeasure1;
	}
	public String getQuantity1Error() {
		return quantity1Error;
	}
	public void setQuantity1Error(String quantity1Error) {
		this.quantity1Error = quantity1Error;
	}
	public String getQuantity1() {
		return quantity1;
	}
	public void setQuantity1(String quantity1) {
		this.quantity1 = quantity1;
	}
	public String getUnitOfMeasure2() {
		return unitOfMeasure2;
	}
	public void setUnitOfMeasure2(String unitOfMeasure2) {
		this.unitOfMeasure2 = unitOfMeasure2;
	}
	public String getQuantity2Error() {
		return quantity2Error;
	}
	public void setQuantity2Error(String quantity2Error) {
		this.quantity2Error = quantity2Error;
	}
	public String getQuantity2() {
		return quantity2;
	}
	public void setQuantity2(String quantity2) {
		this.quantity2 = quantity2;
	}
	public String getReferenceSpec() {
		return referenceSpec;
	}
	public void setReferenceSpec(String referenceSpec) {
		this.referenceSpec = referenceSpec;
	}
	public String getReferenceSpecError() {
		return referenceSpecError;
	}
	public void setReferenceSpecError(String referenceSpecError) {
		this.referenceSpecError = referenceSpecError;
	}
	public String getSupplierNumberError() {
		return supplierNumberError;
	}
	public void setSupplierNumberError(String supplierNumberError) {
		this.supplierNumberError = supplierNumberError;
	}
	public String getRemoveLine() {
		return removeLine;
	}
	public void setRemoveLine(String removeLine) {
		this.removeLine = removeLine;
	}
	public String getUnitOfMeasure1Error() {
		return unitOfMeasure1Error;
	}
	public void setUnitOfMeasure1Error(String unitOfMeasure1Error) {
		this.unitOfMeasure1Error = unitOfMeasure1Error;
	}
	public String getUnitOfMeasure2Error() {
		return unitOfMeasure2Error;
	}
	public void setUnitOfMeasure2Error(String unitOfMeasure2Error) {
		this.unitOfMeasure2Error = unitOfMeasure2Error;
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
	public String getItemNumber1Error() {
		return itemNumber1Error;
	}
	public void setItemNumber1Error(String itemNumber1Error) {
		this.itemNumber1Error = itemNumber1Error;
	}
	public String getItemNumber2() {
		return itemNumber2;
	}
	public void setItemNumber2(String itemNumber2) {
		this.itemNumber2 = itemNumber2;
	}
	public String getItemNumber2Error() {
		return itemNumber2Error;
	}
	public void setItemNumber2Error(String itemNumber2Error) {
		this.itemNumber2Error = itemNumber2Error;
	}
	public String getItemNumber3() {
		return itemNumber3;
	}
	public void setItemNumber3(String itemNumber3) {
		this.itemNumber3 = itemNumber3;
	}
	public String getItemNumber3Error() {
		return itemNumber3Error;
	}
	public void setItemNumber3Error(String itemNumber3Error) {
		this.itemNumber3Error = itemNumber3Error;
	}
	public String getQuantity3() {
		return quantity3;
	}
	public void setQuantity3(String quantity3) {
		this.quantity3 = quantity3;
	}
	public String getQuantity3Error() {
		return quantity3Error;
	}
	public void setQuantity3Error(String quantity3Error) {
		this.quantity3Error = quantity3Error;
	}
	public String getSauceBatchQty() {
		return sauceBatchQty;
	}
	public void setSauceBatchQty(String sauceBatchQty) {
		this.sauceBatchQty = sauceBatchQty;
	}
	public String getSauceTargetBrix() {
		return sauceTargetBrix;
	}
	public void setSauceTargetBrix(String sauceTargetBrix) {
		this.sauceTargetBrix = sauceTargetBrix;
	}
	public String getUnitOfMeasure3() {
		return unitOfMeasure3;
	}
	public void setUnitOfMeasure3(String unitOfMeasure3) {
		this.unitOfMeasure3 = unitOfMeasure3;
	}
	public String getUnitOfMeasure3Error() {
		return unitOfMeasure3Error;
	}
	public void setUnitOfMeasure3Error(String unitOfMeasure3Error) {
		this.unitOfMeasure3Error = unitOfMeasure3Error;
	}
	public String getSauceBatchQtyError() {
		return sauceBatchQtyError;
	}
	public void setSauceBatchQtyError(String sauceBatchQtyError) {
		this.sauceBatchQtyError = sauceBatchQtyError;
	}
	public String getSauceTargetBrixError() {
		return sauceTargetBrixError;
	}
	public void setSauceTargetBrixError(String sauceTargetBrixError) {
		this.sauceTargetBrixError = sauceTargetBrixError;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getSauceBatchQtyUOM() {
		return sauceBatchQtyUOM;
	}
	public void setSauceBatchQtyUOM(String sauceBatchQtyUOM) {
		this.sauceBatchQtyUOM = sauceBatchQtyUOM;
	}
	public String getSauceBatchQtyUOMError() {
		return sauceBatchQtyUOMError;
	}
	public void setSauceBatchQtyUOMError(String sauceBatchQtyUOMError) {
		this.sauceBatchQtyUOMError = sauceBatchQtyUOMError;
	}
	/*
	 * Update and Replace the Detail information with the new
	 *    Formula, Revision Date and Revision Time
	 */
	public static UpdFormula replaceRecordID(UpdFormula applicationData) {
		Vector newListPreBlend = new Vector();
		if (!applicationData.getListPreBlendDetail().isEmpty())
		{
			for (int x = 0; x < applicationData.getListPreBlendDetail().size(); x++)
			{
				UpdFormulaDetail thisRecord = (UpdFormulaDetail) applicationData.getListPreBlendDetail().elementAt(x);
				thisRecord.setFormulaNumber(applicationData.getFormulaNumber());
				thisRecord.setRevisionDate(applicationData.getRevisionDate());
				thisRecord.setRevisionTime(applicationData.getRevisionTime());
				thisRecord.setRecordType("PREBL");
				newListPreBlend.addElement(thisRecord);
			}
		}
		applicationData.setListPreBlendDetail(newListPreBlend);
		Vector newListProduction = new Vector();
		if (!applicationData.getListProductionDetail().isEmpty())
		{
			for (int x = 0; x < applicationData.getListProductionDetail().size(); x++)
			{
				UpdFormulaDetail thisRecord = (UpdFormulaDetail) applicationData.getListProductionDetail().elementAt(x);
				thisRecord.setFormulaNumber(applicationData.getFormulaNumber());
				thisRecord.setRevisionDate(applicationData.getRevisionDate());
				thisRecord.setRevisionTime(applicationData.getRevisionTime());
				thisRecord.setRecordType("PROD");
				newListProduction.addElement(thisRecord);
			}
		}
		applicationData.setListProductionDetail(newListProduction);
		Vector newListSauce = new Vector();
		if (!applicationData.getListPreBlendSauceDetail().isEmpty())
		{
			for (int x = 0; x < applicationData.getListPreBlendSauceDetail().size(); x++)
			{
				UpdFormulaDetail thisRecord = (UpdFormulaDetail) applicationData.getListPreBlendSauceDetail().elementAt(x);
				thisRecord.setFormulaNumber(applicationData.getFormulaNumber());
				thisRecord.setRevisionDate(applicationData.getRevisionDate());
				thisRecord.setRevisionTime(applicationData.getRevisionTime());
				thisRecord.setRecordType("SAUCE");
				newListSauce.addElement(thisRecord);
			}
		}
		applicationData.setListPreBlendSauceDetail(newListSauce);
		return applicationData;
	}
}
