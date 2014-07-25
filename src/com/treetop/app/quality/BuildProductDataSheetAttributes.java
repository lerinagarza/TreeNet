/*
 * Created on December 18, 2013
 */

package com.treetop.app.quality;


import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjects.SpecificationTestProcess;
import com.treetop.viewbeans.BaseViewBeanR2;

/**
 * @author twalto
 */

public class BuildProductDataSheetAttributes extends BaseViewBeanR2 {

	public String recordType 				= ""; //TEST or PROC or MICRO
	public String selectAttribute			= "";
	public String attributeID 				= "";
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		return;
	}
	/*
	 * Check to see if ANY attributes were chosen to display on the Product Data Sheet
	 *    For either the Specification or the Formula
	 *    will retrieve and load them one line at a time.
	 *     
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 */
	public static Vector populateTests(HttpServletRequest request,
									   String screenType, 
									   int recordCount) {
		// The return vector will be a vector of BuildProductDataSheetAttributes 
		Vector<BuildProductDataSheetAttributes> returnVector = new Vector<BuildProductDataSheetAttributes>();
		
		StringBuffer foundProblem = new StringBuffer();
		if (recordCount > 0)
		{
		  for (int x = 0; x < (recordCount + 1); x++)	
		  { 
			  BuildProductDataSheetAttributes bPDSA = new BuildProductDataSheetAttributes();
			  bPDSA.setRecordType(screenType);
			  // Attribute ID ******************************************************
			  bPDSA.setAttributeID(request.getParameter(screenType + "attributeID" + x));
			  if (bPDSA.getAttributeID() == null)
				 bPDSA.setAttributeID("");
			  // Attribute ID ******************************************************
			  bPDSA.setSelectAttribute(request.getParameter(screenType + "selectAttribute" + x));
			  if (bPDSA.getSelectAttribute() == null)
				 bPDSA.setSelectAttribute("");

			     returnVector.addElement(bPDSA);
		  } // end of the for loop
	   }
		return returnVector;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getSelectAttribute() {
		return selectAttribute;
	}
	public void setSelectAttribute(String selectAttribute) {
		this.selectAttribute = selectAttribute;
	}
	public String getAttributeID() {
		return attributeID;
	}
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}
	
}
