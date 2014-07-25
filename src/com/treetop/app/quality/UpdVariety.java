/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;


import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.HTMLHelpersMasking;
import com.treetop.utilities.html.JavascriptInfo;
import com.treetop.viewbeans.BaseViewBeanR2;
import java.math.*;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 * @author twalto
 */

public class UpdVariety extends BaseViewBeanR2 {

	// Must have in Update View Bean
	public String updateUser 			= "";
	
	public String applicationType		= ""; //SPEC or FORMULA
	public String recordID	 			= ""; //could be a Spec or a Formula
	public String revisionDate 			= "";
	public String revisionTime 			= "";
	
	public String removeEntry			= "";
	
	public String cropModel 			= "";
	public String cropModelDescription  = "";
	public String attributeID 			= "";
	public String variety 				= "";
	public String varietyDescription    = "";
	public String includeOrExclude 		= ""; // Flag I or X
	public String percentage 			= "";
	public String percentageError		= "";
	public String minimumPercent 		= "";
	public String minimumPercentError	= "";
	public String maximumPercent 		= "";
	public String maximumPercentError	= "";
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		StringBuffer foundProblem = new StringBuffer();
		try {	
			// Variety **********************************************************

			if (this.getVariety().trim().equals("")) {
				// MUST have a Variety to be a valid entry
				this.setRemoveEntry("X");
			}
	
			
		} catch(Exception e) {}
		
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
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getCropModel() {
		return cropModel;
	}
	public void setCropModel(String cropModel) {
		this.cropModel = cropModel;
	}
	public String getAttributeID() {
		return attributeID;
	}
	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}
	public String getVariety() {
		return variety;
	}
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public String getMaximumPercent() {
		return maximumPercent;
	}
	public void setMaximumPercent(String maximumPercent) {
		this.maximumPercent = maximumPercent;
	}
	public String getMinimumPercent() {
		return minimumPercent;
	}
	public void setMinimumPercent(String minimumPercent) {
		this.minimumPercent = minimumPercent;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getIncludeOrExclude() {
		return includeOrExclude;
	}
	public void setIncludeOrExclude(String includeOrExclude) {
		this.includeOrExclude = includeOrExclude;
	}
	public String getMaximumPercentError() {
		return maximumPercentError;
	}
	public void setMaximumPercentError(String maximumPercentError) {
		this.maximumPercentError = maximumPercentError;
	}
	public String getMinimumPercentError() {
		return minimumPercentError;
	}
	public void setMinimumPercentError(String minimumPercentError) {
		this.minimumPercentError = minimumPercentError;
	}
	public String getRemoveEntry() {
		return removeEntry;
	}
	public void setRemoveEntry(String removeEntry) {
		this.removeEntry = removeEntry;
	}
	public String getVarietyDescription() {
		return varietyDescription;
	}
	public void setVarietyDescription(String varietyDescription) {
		this.varietyDescription = varietyDescription;
	}
	public String getCropModelDescription() {
		return cropModelDescription;
	}
	public void setCropModelDescription(String cropModelDescription) {
		this.cropModelDescription = cropModelDescription;
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
	public static Vector populateVariety(HttpServletRequest request,
										 Vector application,
										 String screenType, 
										 int recordCount) {
		Vector returnVector = new Vector(); // element 0 - string message  element 1 - Vector of varieties
		Vector list = new Vector();
		
		StringBuffer foundProblem = new StringBuffer();
		if (recordCount > 0)
		{
		  for (int x = 0; x < (recordCount + 1); x++)	
		  { 
			  UpdVariety uv = new UpdVariety();
			  String appType = (String) application.elementAt(0);
			  if (appType.trim().equals("FORMULA"))
			  {
				  UpdFormula thisOne = (UpdFormula) application.elementAt(1);
				  uv.setCompany(thisOne.getCompany());
				  uv.setDivision(thisOne.getDivision());
				  uv.setEnvironment(thisOne.getEnvironment());
				  uv.setApplicationType(appType);
				  uv.setRecordID(thisOne.getFormulaNumber());
				  uv.setRevisionDate(thisOne.getRevisionDate());
				  uv.setRevisionTime(thisOne.getRevisionTime()); 
			  }
			  if (appType.trim().equals("SPEC"))
			  {
				  UpdSpecification thisOne = (UpdSpecification) application.elementAt(1);
				  uv.setCompany(thisOne.getCompany());
				  uv.setDivision(thisOne.getDivision());
				  uv.setEnvironment(thisOne.getEnvironment());
				  uv.setApplicationType(appType);
				  uv.setRecordID(thisOne.getSpecNumber());
				  uv.setRevisionDate(thisOne.getRevisionDate());
				  uv.setRevisionTime(thisOne.getRevisionTime());
			  }
			  //--------------------------------------------------------------------
			  // RETRIEVE -- and build the vector
			  //-------------------------------------------------------------------
			  // Remove Entry ******************************************************
			  uv.setRemoveEntry(request.getParameter(screenType + "removeEntry" + x));
			  if (uv.getRemoveEntry() == null)
				 uv.setRemoveEntry("");
			  // Crop Model *******************************************************
			  uv.setCropModel(request.getParameter(screenType + "cropModel" + x));
			  if (uv.getCropModel() == null || uv.getCropModel().trim().equals("*all"))
				 uv.setCropModel("");
			  // Crop Model Description **********************************************
			  uv.setCropModelDescription(request.getParameter(screenType + "cropModelDescription" + x));
			  if (uv.getCropModelDescription() == null || uv.getCropModelDescription().trim().equals("*all"))
				 uv.setCropModelDescription("");
			  // Attribute ID *******************************************************
			  uv.setAttributeID("VAR");
			  // Variety ************************************************************
			  uv.setVariety(request.getParameter(screenType + "variety" + x));
			  if (uv.getVariety() == null)
				 uv.setVariety("");  
			  // Variety Description *************************************************
			  uv.setVarietyDescription(request.getParameter(screenType + "varietyDescription" + x));
			  if (uv.getVarietyDescription() == null)
				 uv.setVarietyDescription(""); 
			  // Include or Exclude *************************************************
			  uv.setIncludeOrExclude(screenType);
			  
			  
			  if (uv.getIncludeOrExclude().trim().equals("I"))
			  {
				  // Percentage *********************************************************
				  uv.setPercentage(request.getParameter(screenType + "percentage" + x));
//				  if (uv.getPercentage() == null || uv.getPercentage().trim().equals("")) wth allow blanks
				  if (uv.getPercentage() == null)
//					  uv.setPercentage("0"); wth allow blanks 
					  uv.setPercentage("");
				  // Minimum Percent *****************************************************
				  uv.setMinimumPercent(request.getParameter(screenType + "minimumPercent" + x));
//				  if (uv.getMinimumPercent() == null || uv.getMinimumPercent().trim().equals("")) //wth allow blanks
				  if (uv.getMinimumPercent() == null)
//					  uv.setMinimumPercent("0"); //wth allow blanks
					  uv.setMinimumPercent("");
				  // Maximum Percent *****************************************************
				  uv.setMaximumPercent(request.getParameter(screenType + "maximumPercent" + x));
//				  if (uv.getMaximumPercent() == null || uv.getMaximumPercent().trim().equals("")) //wth allow blanks
				  if (uv.getMaximumPercent() == null)
//					  uv.setMaximumPercent("0"); //wth allow blanks
					  uv.setMaximumPercent("");
			  }
			  else
			  {
				  uv.setPercentage("");
				  uv.setMinimumPercent("");
				  uv.setMaximumPercent("");
			  }
			  //--------------------------------------------------------------------
			  // VALIDATE
			  //-------------------------------------------------------------------
			  	uv.validate();
			  	if (!uv.getDisplayMessage().trim().equals(""))
			  		foundProblem.append(uv.getDisplayMessage());
			  	if (!uv.getCropModel().trim().equals("") &&
			  		!uv.getVariety().trim().equals("") &&
			  		uv.getRemoveEntry().trim().equals("")) 
			  	   list.addElement(uv);
		  } // end of the for loop
		  returnVector.addElement(foundProblem.toString());
		  returnVector.addElement(list);
	   }
		return returnVector;
	}
	/**
	 *    Will Generate the Vectors of Data to be used on the screen (JSP)
	 *       to make the Drop Down Dual informaiton work.
	 *       recordType = I for Include or X for Exclude
	 *       recordCount = How many lines to display
	 *       Each Element will be a Vector of
	 *       [0] - code for the Master drop down in the table
	 *       [1] - code for the Slave drop down in the table
	 *       [2] - code for 1 of the Script's put in the Header - in the JSP
	 *       [3] - code for the second script put in the Header - in the JSP   
	 *    
	 */
	public static Vector buildDropDownDualCropVarietyVectors(String recordType, int recordCount) {
		String masterName = recordType + "cropModel";
		String slaveName  = recordType + "variety";
		Vector allDataNeeded = new Vector();
		try
		{
			for (int lineCnt = 0; lineCnt < recordCount; lineCnt++)
			{
//				UpdVariety uv = (UpdVariety) listOfRecords.elementAt(lineCnt);
				Vector returnData = DropDownDual.buildDualDropDownNew(GeneralQuality.getListCropVariety(), (masterName + lineCnt), "", "", (slaveName + lineCnt), "", "", "N", "N");
				Vector oneElement = new Vector();
				oneElement.addElement((String) returnData.elementAt(0));
				oneElement.addElement("<select name=\"" + slaveName + lineCnt + "\">");
				oneElement.addElement((String) returnData.elementAt(1));
				oneElement.addElement(JavascriptInfo.getDualDropDownNew((masterName + lineCnt), (slaveName + lineCnt)));
				allDataNeeded.addElement(oneElement);  
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return allDataNeeded;
	}
	public String getPercentageError() {
		return percentageError;
	}
	public void setPercentageError(String percentageError) {
		this.percentageError = percentageError;
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
		Vector listInclude = new Vector();
		Vector newListInclude = new Vector();
		Vector listExclude = new Vector();
		Vector newListExclude = new Vector();
		UpdFormula thisFormula = new UpdFormula();
		if (appType.trim().equals("FORMULA"))
		{
			thisFormula = (UpdFormula) applicationData.elementAt(0);
			newRecordID = thisFormula.getFormulaNumber();
			newRevisionDate = thisFormula.getRevisionDate();
			newRevisionTime = thisFormula.getRevisionTime();
			listInclude = thisFormula.getListVarietiesIncluded();
			listExclude = thisFormula.getListVarietiesExcluded();
		}
		UpdSpecification thisSpecification = new UpdSpecification();
		if (appType.trim().equals("SPEC"))
		{
			thisSpecification = (UpdSpecification) applicationData.elementAt(0);
			newRecordID = thisSpecification.getSpecNumber();
			newRevisionDate = thisSpecification.getRevisionDate();
			newRevisionTime = thisSpecification.getRevisionTime();
			listInclude = thisSpecification.getListVarietiesIncluded();
			listExclude = thisSpecification.getListVarietiesExcluded();
		}
		if (!listInclude.isEmpty())
		{
			for (int x = 0; x < listInclude.size(); x++)
			{
				UpdVariety thisRecord = (UpdVariety) listInclude.elementAt(x);
				thisRecord.setRecordID(newRecordID);
				thisRecord.setRevisionDate(newRevisionDate);
				thisRecord.setRevisionTime(newRevisionTime);
				thisRecord.setApplicationType(appType);
				thisRecord.setAttributeID("VAR");
				newListInclude.addElement(thisRecord);
			}
		}
		if (!listExclude.isEmpty())
		{
			for (int x = 0; x < listExclude.size(); x++)
			{
				UpdVariety thisRecord = (UpdVariety) listExclude.elementAt(x);
				thisRecord.setRecordID(newRecordID);
				thisRecord.setRevisionDate(newRevisionDate);
				thisRecord.setRevisionTime(newRevisionTime);
				thisRecord.setApplicationType(appType);
				thisRecord.setAttributeID("VAR");
				newListExclude.addElement(thisRecord);
			}
		}
		if (appType.trim().equals("FORMULA"))
		{
			thisFormula.setListVarietiesIncluded(newListInclude);
			thisFormula.setListVarietiesExcluded(newListExclude);
			returnVector.addElement(thisFormula);
		}
		if (appType.trim().equals("SPEC"))
		{
			thisSpecification.setListVarietiesIncluded(newListInclude);
			thisSpecification.setListVarietiesExcluded(newListExclude);
			returnVector.addElement(thisSpecification);
		}	
		return returnVector;
	}
}
