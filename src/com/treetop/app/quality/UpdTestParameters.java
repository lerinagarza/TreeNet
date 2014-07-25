/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;


import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjects.SpecificationTestProcess;
import com.treetop.businessobjects.Attribute;
import com.treetop.services.ServiceAttribute;
import com.treetop.viewbeans.BaseViewBeanR2;

/**
 * @author twalto
 */

public class UpdTestParameters extends BaseViewBeanR2 {

	// Must have in Update View Bean
	public String updateUser 				= "";
	public String updateDate				= "";
	public String updateTime				= "";
	
	public String applicationType			= ""; //SPEC or FORMULA
	public String recordID	 				= ""; //could be a Spec or a Formula
	public String revisionDate 				= "";
	public String revisionTime 				= "";
	
	
	public String recordType 				= ""; //TEST or PROC or MICRO
	public String attributeGroup			= "";
	public String attributeID 				= "";
	public String attributeIDError 			= ""; //must be a valid M3 attribute
	public String attributeIDSequence 		= "0";
	public String attributeIDSequenceError 	= ""; // must be a number
	public String attributeFieldType		= "";
	public String unitOfMeasure 			= "";
	public String target 					= ""; // Will be stored as an Alpha
	public String targetError 				= ""; // must be a number - if Attribute is a Numeric Attribute
	public String minimum 					= ""; // Will be stored as an Alpha
	public String minimumError 				= ""; // must be a number - if Attribute is a Numeric Attribute
	public String maximum 					= ""; // Will be stored as an Alpha
	public String maximumError 				= ""; // must be a number - if Attribute is a Numeric Attribute
	public String defaultOnCOA				= "";
	
	public String method 					= "0";
	public String methodError				= ""; // must be a valid Method
	
	// For the Test Analytical Section ONLY
	public String testValue 				= "0.000";
	public String testValueError 			= "";
	public String testValueUOM 				= "";
	
//	public SpecificationTestProcess testProcessInfo = new SpecificationTestProcess();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		StringBuffer foundProblem = new StringBuffer();
		
		
		
		// Attribute ID Sequence Number ***********************************
		try {
			
			if (!this.getAttributeIDSequence().trim().equals("") &&
				!this.getAttributeIDSequence().trim().equals("0"))
			{
				this.setAttributeIDSequenceError(validateInteger(this.getAttributeIDSequence()));
				if (!this.getAttributeIDSequenceError().trim().equals(""))
				  foundProblem.append(this.getAttributeIDSequenceError().trim() + "<br>");
			}
			
		} catch(Exception e) {
	  		this.setAttributeIDSequenceError("Problem Found with Sequence Number. " + e);
	  		foundProblem.append("Problem Found with Sequence Number<br>");
	  	}
		
		
		
		
		 // Attribute ID *****************************************************
		Attribute attributeDetail = new Attribute();
		try {
			
			if (!this.getAttributeID().trim().equals("") &&
				 !this.getApplicationType().trim().equals("FORMULA")) {
				
				attributeDetail = ServiceAttribute.findAttribute(this.getEnvironment(), this.getAttributeID());
				
				if (attributeDetail.getAttribute().trim().equals("")) {
				   foundProblem.append("Attribute " + this.getAttributeID() + " Not Found<br>");
				}
				
				if (attributeDetail.getFieldType().equals("numeric")) {
					
					//validate values, BLANKS are allowed
					
					// target
					if (!this.getTarget().equals("")) {
						this.setTargetError(validateBigDecimal(this.getTarget()));
						if (!this.getTargetError().trim().equals("")) {
							foundProblem.append(this.getTargetError().trim() + "<br>");
						}
					}
					
					
					// minimum
					if (!this.getMinimum().equals("")) {
						this.setMinimumError(validateBigDecimal(this.getMinimum()));
						if (!this.getMinimumError().trim().equals("")) {
							foundProblem.append(this.getMinimumError().trim() + "<br>");
						}
					}
					
					
					// maximum
					if (!this.getMaximum().equals("")) {
						this.setMaximumError(validateBigDecimal(this.getMaximum()));
						if (!this.getMaximumError().trim().equals("")) {
							foundProblem.append(this.getMaximumError().trim() + "<br>");
						}
					}
					
					
					// test
					if (!this.getTestValue().equals("")) {
						this.setTestValueError(validateBigDecimal(this.getTestValue()));
						if (!this.getTestValueError().trim().equals("")) {
							foundProblem.append(this.getMaximumError().trim() + "<br>");
						}
					}
					
					
				}
			
			
			}
			
		} catch(Exception e) {
	  		this.setAttributeIDError("Problem found with Attribute ID. " + e);
	  		foundProblem.append("Problem Found with Attribute ID<br>");
	  	}

		
		
		
		this.setDisplayMessage(foundProblem.toString().trim());
		return;
	}
	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return Returns the maximum.
	 */
	public String getMaximum() {
		return maximum;
	}
	/**
	 * @param maximum The maximum to set.
	 */
	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}
	/**
	 * @return Returns the maximumError.
	 */
	public String getMaximumError() {
		return maximumError;
	}
	/**
	 * @param maximumError The maximumError to set.
	 */
	public void setMaximumError(String maximumError) {
		this.maximumError = maximumError;
	}
	/**
	 * @return Returns the unit of measure.
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	/**
	 * @param unitOfMeasure. The unit of measure. to set.
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return Returns the minimum.
	 */
	public String getMinimum() {
		return minimum;
	}
	/**
	 * @param minimum The minimum to set.
	 */
	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}
	/**
	 * @return Returns the minimumError.
	 */
	public String getMinimumError() {
		return minimumError;
	}
	/**
	 * @param minimumError The minimumError to set.
	 */
	public void setMinimumError(String minimumError) {
		this.minimumError = minimumError;
	}
	/**
	 * @return Returns the revisionDate.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param revisionDate The revisionDate to set.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return Returns the targetError.
	 */
	public String getTargetError() {
		return targetError;
	}
	/**
	 * @param targetError The targetError to set.
	 */
	public void setTargetError(String targetError) {
		this.targetError = targetError;
	}
	/**
	 * @return Returns the testValue.
	 */
	public String getTestValue() {
		return testValue;
	}
	/**
	 * @param testValue The testValue to set.
	 */
	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}
	/**
	 * @return Returns the testValueError.
	 */
	public String getTestValueError() {
		return testValueError;
	}
	/**
	 * @param testValueError The testValueError to set.
	 */
	public void setTestValueError(String testValueError) {
		this.testValueError = testValueError;
	}
	/**
	 * @return Returns the testValueUOM.
	 */
	public String getTestValueUOM() {
		return testValueUOM;
	}
	/**
	 * @param testValueUOM The testValueUOM to set.
	 */
	public void setTestValueUOM(String testValueUOM) {
		this.testValueUOM = testValueUOM;
	}
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getAttributeID() {
		return attributeID;
	}
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}
	public String getAttributeIDError() {
		return attributeIDError;
	}
	public void setAttributeIDError(String attributeIDError) {
		this.attributeIDError = attributeIDError;
	}
	public String getAttributeIDSequence() {
		return attributeIDSequence;
	}
	public void setAttributeIDSequence(String attributeIDSequence) {
		this.attributeIDSequence = attributeIDSequence;
	}
	public String getAttributeIDSequenceError() {
		return attributeIDSequenceError;
	}
	public void setAttributeIDSequenceError(String attributeIDSequenceError) {
		this.attributeIDSequenceError = attributeIDSequenceError;
	}
	public String getAttributeGroup() {
		return attributeGroup;
	}
	public void setAttributeGroup(String attributeGroup) {
		this.attributeGroup = attributeGroup;
	}
	public String getMethodError() {
		return methodError;
	}
	public void setMethodError(String methodError) {
		this.methodError = methodError;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getRecordID() {
		return recordID;
	}
	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
	/*
	 * Test and Validate fields for Varieties of the Specification or the Formula
	 *    will retrieve and load them one line at a time.
	 *     
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 */
	public static Vector replaceRecordID(String appType, 
										 Vector applicationData) {
		Vector returnVector = new Vector(); // Return the Same Information sent in on the applicationData -- either for Formula or Spec
		String newRecordID = "";
		String newRevisionDate = "";
		String newRevisionTime = "";
		Vector listTests = new Vector();
		Vector newListTests = new Vector();
		UpdFormula thisFormula = new UpdFormula();
		int howManyLists = 1;
		String attrGroup = "";
		if (appType.trim().equals("FORMULA"))
		{
			thisFormula = (UpdFormula) applicationData.elementAt(0);
			newRecordID = thisFormula.getFormulaNumber();
			newRevisionDate = thisFormula.getRevisionDate();
			newRevisionTime = thisFormula.getRevisionTime();
			listTests = thisFormula.getListRawFruitAttributes();
		}
		UpdSpecification thisSpecification = new UpdSpecification();
		if (appType.trim().equals("SPEC"))
		{
			thisSpecification = (UpdSpecification) applicationData.elementAt(0);
			newRecordID = thisSpecification.getSpecNumber();
			newRevisionDate = thisSpecification.getRevisionDate();
			newRevisionTime = thisSpecification.getRevisionTime();
			listTests = thisSpecification.getListAnalyticalTests();
			howManyLists = 4;
		}
		for (int z = 0; z < howManyLists; z++)
		{
		  if (appType.trim().equals("SPEC"))
		  {
			  if (z != 0)
			  {
			     listTests = new Vector();
			  }
			  if (z == 1)
			  {
			    listTests = thisSpecification.getListMicroTests();
			    attrGroup = "MICRO";
			  }
			  if (z == 2)
			  {
				listTests = thisSpecification.getListProcessParameters();
				attrGroup = "PROC";
			  }
			  if (z == 3)
			  {
				listTests = thisSpecification.getListAdditivesAndPreservatives();
				attrGroup = "ADD";
			  }
		  }
		  if (z != 0)
  		     newListTests = new Vector();
		  if (!listTests.isEmpty())
		  {
			for (int x = 0; x < listTests.size(); x++)
			{
				UpdTestParameters thisRecord = (UpdTestParameters) listTests.elementAt(x);
				if (!thisRecord.getAttributeID().trim().equals(""))
				{
					// 10/30/13 TWalton - Remove the FLVR Code -- allow Alpha Attributes
//					if (thisRecord.getAttributeID().trim().equals("FLVR"))
//					{
//						if (!thisRecord.getTargetAlpha().trim().equals("") ||
//							!thisRecord.getMinimumAlpha().trim().equals("") ||
//							!thisRecord.getMaximumAlpha().trim().equals(""))
//						{
//							thisRecord.setAttributeGroup(attrGroup);
//							thisRecord.setRecordID(newRecordID);
//							thisRecord.setRevisionDate(newRevisionDate);
//							thisRecord.setRevisionTime(newRevisionTime);
//							thisRecord.setApplicationType(appType);
//							thisRecord.setRecordType(attrGroup);
//							newListTests.addElement(thisRecord);
//						}
//						
//					}else
//					{
						thisRecord.setAttributeGroup(attrGroup);
						thisRecord.setRecordID(newRecordID);
						thisRecord.setRevisionDate(newRevisionDate);
						thisRecord.setRevisionTime(newRevisionTime);
						thisRecord.setApplicationType(appType);
						thisRecord.setRecordType(attrGroup);
						newListTests.addElement(thisRecord);
//					}
				}
			}
		  }
		  if (appType.trim().equals("FORMULA"))
		     thisFormula.setListRawFruitAttributes(newListTests);
		  if (appType.trim().equals("SPEC"))
		  {
			if (z == 0)  
			  thisSpecification.setListAnalyticalTests(newListTests);
			if (z == 1)
			  thisSpecification.setListMicroTests(newListTests);
			if (z == 2)
			  thisSpecification.setListProcessParameters(newListTests);
			if (z == 3)
			  thisSpecification.setListAdditivesAndPreservatives(newListTests);
		  }
		}
	    if (appType.trim().equals("FORMULA"))
		  returnVector.addElement(thisFormula);
		if (appType.trim().equals("SPEC"))
		  returnVector.addElement(thisSpecification);
		return returnVector;
	}
	/*
	 * Test and Validate fields for Test Attributes and Process Parameters
	 *    For either the Specification or the Formula
	 *    will retrieve and load them one line at a time.
	 *     
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 */
	public static Vector populateTests(HttpServletRequest request,
									   Vector application,
									   String screenType, 
									   int recordCount) {
		Vector returnVector = new Vector(); // element 0 - string message  element 1 - Update Object to retrieve the tests from
		Vector list = new Vector();
		
		StringBuffer foundProblem = new StringBuffer();
		if (recordCount > 0)
		{
		  for (int x = 0; x < (recordCount + 1); x++)	
		  { 
			  UpdTestParameters utp = new UpdTestParameters();
			  String appType = (String) application.elementAt(0);
			  if (appType.trim().equals("FORMULA"))
			  {
				  UpdFormula thisOne = (UpdFormula) application.elementAt(1);
				  utp.setCompany(thisOne.getCompany());
				  utp.setDivision(thisOne.getDivision());
				  utp.setEnvironment(thisOne.getEnvironment());
				  utp.setApplicationType(appType);
				  utp.setRecordID(thisOne.getFormulaNumber());
				  utp.setRevisionDate(thisOne.getRevisionDate());
				  utp.setRevisionTime(thisOne.getRevisionTime()); 
				  utp.setAttributeIDSequence(x + "");
				  utp.setMethod("0");
			  }
			  if (appType.trim().equals("SPEC"))
			  {
				  UpdSpecification thisOne = (UpdSpecification) application.elementAt(1);
				  utp.setCompany(thisOne.getCompany());
				  utp.setDivision(thisOne.getDivision());
				  utp.setEnvironment(thisOne.getEnvironment());
				  utp.setApplicationType(appType);
				  utp.setRecordID(thisOne.getSpecNumber());
				  utp.setRevisionDate(thisOne.getRevisionDate());
				  utp.setRevisionTime(thisOne.getRevisionTime());
				  // Attribute ID Sequence************************************************
				  utp.setAttributeIDSequence(request.getParameter(screenType + "attributeIDSequence" + x));
				  if (utp.getAttributeIDSequence() == null)
					 utp.setAttributeIDSequence(x + "");
				  try{
					  String errorIDSeq = utp.validateInteger(utp.getAttributeIDSequence());
					  if (!errorIDSeq.trim().equals(""))
						  utp.setAttributeIDSequence(x + "");
				  }catch(Exception e)
				  {
					  utp.setAttributeIDSequence(x + "");
				  }
			  }
			  //--------------------------------------------------------------------
			  // RETRIEVE -- and build the vector
			  //-------------------------------------------------------------------
			  // Attribute ID ******************************************************
			  utp.setAttributeID(request.getParameter(screenType + "attributeID" + x));
			  if (utp.getAttributeID() == null)
				 utp.setAttributeID("");
			  // Unit of Measure *****************************************************
			  // 1/27/12 Not Needed, going to use the value from the Analytical Test Chosen
			  //utp.setUnitOfMeasure(request.getParameter(screenType + "unitOfMeasure" + x));
			  //if (utp.getUnitOfMeasure() == null)
			  //	 utp.setUnitOfMeasure("");
			  // Target **************************************************************
			  utp.setTarget(request.getParameter(screenType + "target" + x));
			  if (utp.getTarget() == null)
				 utp.setTarget("");
//			  // Target Alpha *********************************************************
//			  utp.setTargetAlpha(request.getParameter(screenType + "targetAlpha" + x));
//			  if (utp.getTargetAlpha() == null || utp.getTargetAlpha().trim().equals(""))
//				 utp.setTargetAlpha("");
//			  else
//				 utp.setTarget("0");
			  // Minimum ************************************************************
			  utp.setMinimum(request.getParameter(screenType + "minimum" + x));
			  if (utp.getMinimum() == null)
				 utp.setMinimum("");  
//			// Minimum Alpha ********************************************************
//			  utp.setMinimumAlpha(request.getParameter(screenType + "minimumAlpha" + x));
//			  if (utp.getMinimumAlpha() == null || utp.getMinimumAlpha().trim().equals(""))
//				 utp.setMinimumAlpha("");
//			  else
//				 utp.setMinimum("0");
			  // Maximum ************************************************************
			  utp.setMaximum(request.getParameter(screenType + "maximum" + x));
			  if (utp.getMaximum() == null)
				 utp.setMaximum("");  
//			  utp.setMaximumAlpha(request.getParameter(screenType + "maximumAlpha" + x));
//			  if (utp.getMaximumAlpha() == null || utp.getMaximumAlpha().trim().equals(""))
//				 utp.setMaximumAlpha("");
//			  else
//				 utp.setMaximum("0");
			  
			  if (appType.trim().equals("SPEC"))
			  {
				  if (!screenType.trim().equals("PROC"))
				  {
					  // Test Parameter Value ***********************************************
					  utp.setTestValue(request.getParameter(screenType + "testValue" + x));
					  if (utp.getTestValue() == null || utp.getTestValue().trim().equals(""))
						 utp.setTestValue("0");
					  // Test Parameter Unit of Measure **************************************
					  utp.setTestValueUOM(request.getParameter(screenType + "testValueUOM" + x));
					  if (utp.getTestValueUOM() == null)
						 utp.setTestValueUOM(""); 
				  }
				  // Method ********************************************************
				  utp.setMethod(request.getParameter(screenType + "method" + x));
				  if (utp.getMethod() == null || utp.getMethod().trim().equals(""))
					 utp.setMethod("0");
				  if (!screenType.trim().equals("TEST"))
					utp.setRecordType(screenType);
			  }
			  // Default onto COA - is box Checked
			  utp.setDefaultOnCOA(request.getParameter(screenType + "defaultOnCOA" + x));
			  if (utp.getDefaultOnCOA() == null)
				 utp.setDefaultOnCOA("");
			  else
				 utp.setDefaultOnCOA("Y");
			  utp.validate();
			  
			  if (!utp.getDisplayMessage().trim().equals(""))
			  		foundProblem.append(utp.getDisplayMessage());
			  if (!utp.getAttributeID().trim().equals(""))
			     list.addElement(utp);
			  
		  } // end of the for loop
		  returnVector.addElement(foundProblem.toString());
		  returnVector.addElement(list);
	   }
		return returnVector;
	}
	public String getDefaultOnCOA() {
		return defaultOnCOA;
	}
	public void setDefaultOnCOA(String defaultOnCOA) {
		this.defaultOnCOA = defaultOnCOA;
	}
	public String getAttributeFieldType() {
		return attributeFieldType;
	}
	public void setAttributeFieldType(String attributeFieldType) {
		this.attributeFieldType = attributeFieldType;
	}

}
