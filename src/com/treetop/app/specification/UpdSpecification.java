/*
 * Created on May 6, 2008
 * 
 */

package com.treetop.app.specification;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanSpecification;
import com.treetop.businessobjects.SpecificationNEW;
import com.treetop.businessobjects.SpecificationTestProcess;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.html.*;

/**
 * @author twalto
 * 
 */
public class UpdSpecification extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";
	
	public String updateUser = "";

	public String itemNumber = "";
	public String itemNumberError = "";
	public String itemNumberOriginal = "";
	public String itemNumberCopy = "";
	public String labBookID = "";
	public String revisionDate = "";
	public String revisionDateError = "";
	public String revisionDateOriginal = "";
	public String revisionDateCopy = "";
	public String formulaNumber = "";
	public String comments = "";
	
	public String recordStatus = "";
	public String recordStatusOriginal = "";
	public String readOnlyStatus = "Y";
	
	public String slipSheetInformation = "";
	public String stretchWrap = "";
	public String stretchWrapDescription = "";
	public String shrinkWrap = "";
	public String shrinkWrapDescription = "";
	public String codingInformation = "";
	public String caseCodePrint = "";
	public String casePrintLine1 = "";
	public String casePrintLine2 = "";
	public String casePrintGeneral = "";
	public String storageConditions = "";
	public String specialRequirements = "";
	public String packComments = "";
	
	public String length = "";
	public String lengthError = "";
	public String width = "";
	public String widthError = "";
	public String height = "";
	public String heightError = "";
	
	public String readOnly = "N";
	
	// Vector of Values for Drop Down Lists - Built ONLY ONCE
	public Vector ddMethod = new Vector();
	public Vector ddAnalyticalTest = new Vector();
	public Vector ddProcessParameter = new Vector();
	
	// Will need to add Vector of Tests
	public Vector listAnalyticalTests = new Vector(); // Vector of UpdSpecificationTests
	public String countAnalyticalTests = "";
	// Also will need to add Vector of processes
	public Vector listProcessParameters = new Vector(); // Vector of UpdSpecificationTests
	public String countProcessParameters = "";
	
	// Standard Fields for Inq View Bean
	public String orderBy = "";
	public String orderStyle = "";
	public String saveButton = "";
		
	public BeanSpecification beanSpec = new BeanSpecification();

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		if (this.requestType.equals("addCPGSpec") &&
			!this.saveButton.equals(""))
		{
			// Test to see if the Item Is Valid
			try
			{
				this.setItemNumberError(ServiceItem.verifyItem("PRD", this.getItemNumber()));
				if (this.itemNumberError.equals(""))
				{
					// Test to See if for this item, there is ALREADY a Pending Spec
					if ((ServiceSpecification.verifyCPGSpec(this.getItemNumber(), "PENDING")).equals(""))
						this.setItemNumberError("There is Already a Pending Specification for this Item: " + this.getItemNumber());
				}
				this.setDisplayMessage(this.getItemNumberError());
			}
			catch(Exception e)
			{}
		}
		return;
	}
	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	/**
	 * @return Returns the orderBy.
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy The orderBy to set.
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return Returns the orderStyle.
	 */
	public String getOrderStyle() {
		return orderStyle;
	}
	/**
	 * @param orderStyle The orderStyle to set.
	 */
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	/**
	 * @return Returns the requestType.
	 */
	public String getRequestType() {
		return requestType;
	}
	/**
	 * @param requestType The requestType to set.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	 * @return Returns the casePrintGeneral.
	 */
	public String getCasePrintGeneral() {
		return casePrintGeneral;
	}
	/**
	 * @param casePrintGeneral The casePrintGeneral to set.
	 */
	public void setCasePrintGeneral(String casePrintGeneral) {
		this.casePrintGeneral = casePrintGeneral;
	}
	/**
	 * @return Returns the casePrintLine1.
	 */
	public String getCasePrintLine1() {
		return casePrintLine1;
	}
	/**
	 * @param casePrintLine1 The casePrintLine1 to set.
	 */
	public void setCasePrintLine1(String casePrintLine1) {
		this.casePrintLine1 = casePrintLine1;
	}
	/**
	 * @return Returns the casePrintLine2.
	 */
	public String getCasePrintLine2() {
		return casePrintLine2;
	}
	/**
	 * @param casePrintLine2 The casePrintLine2 to set.
	 */
	public void setCasePrintLine2(String casePrintLine2) {
		this.casePrintLine2 = casePrintLine2;
	}
	/**
	 * @return Returns the codingInformation.
	 */
	public String getCodingInformation() {
		return codingInformation;
	}
	/**
	 * @param codingInformation The codingInformation to set.
	 */
	public void setCodingInformation(String codingInformation) {
		this.codingInformation = codingInformation;
	}
	/**
	 * @return Returns the formulaNumber.
	 */
	public String getFormulaNumber() {
		return formulaNumber;
	}
	/**
	 * @param formulaNumber The formulaNumber to set.
	 */
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}
	/**
	 * @return Returns the labBookID.
	 */
	public String getLabBookID() {
		return labBookID;
	}
	/**
	 * @param labBookID The labBookID to set.
	 */
	public void setLabBookID(String labBookID) {
		this.labBookID = labBookID;
	}
	/**
	 * @return Returns the packComments.
	 */
	public String getPackComments() {
		return packComments;
	}
	/**
	 * @param packComments The packComments to set.
	 */
	public void setPackComments(String packComments) {
		this.packComments = packComments;
	}
	/**
	 * @return Returns the recordStatus.
	 */
	public String getRecordStatus() {
		return recordStatus;
	}
	/**
	 * @param recordStatus The recordStatus to set.
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
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
	 * @return Returns the shrinkWrap.
	 */
	public String getShrinkWrap() {
		return shrinkWrap;
	}
	/**
	 * @param shrinkWrap The shrinkWrap to set.
	 */
	public void setShrinkWrap(String shrinkWrap) {
		this.shrinkWrap = shrinkWrap;
	}
	/**
	 * @return Returns the shrinkWrapDescription.
	 */
	public String getShrinkWrapDescription() {
		return shrinkWrapDescription;
	}
	/**
	 * @param shrinkWrapDescription The shrinkWrapDescription to set.
	 */
	public void setShrinkWrapDescription(String shrinkWrapDescription) {
		this.shrinkWrapDescription = shrinkWrapDescription;
	}
	/**
	 * @return Returns the slipSheetInformation.
	 */
	public String getSlipSheetInformation() {
		return slipSheetInformation;
	}
	/**
	 * @param slipSheetInformation The slipSheetInformation to set.
	 */
	public void setSlipSheetInformation(String slipSheetInformation) {
		this.slipSheetInformation = slipSheetInformation;
	}
	/**
	 * @return Returns the specialRequirements.
	 */
	public String getSpecialRequirements() {
		return specialRequirements;
	}
	/**
	 * @param specialRequirements The specialRequirements to set.
	 */
	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}
	/**
	 * @return Returns the storageConditions.
	 */
	public String getStorageConditions() {
		return storageConditions;
	}
	/**
	 * @param storageConditions The storageConditions to set.
	 */
	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}
	/**
	 * @return Returns the stretchWrap.
	 */
	public String getStretchWrap() {
		return stretchWrap;
	}
	/**
	 * @param stretchWrap The stretchWrap to set.
	 */
	public void setStretchWrap(String stretchWrap) {
		this.stretchWrap = stretchWrap;
	}
	/**
	 * @return Returns the stretchWrapDescription.
	 */
	public String getStretchWrapDescription() {
		return stretchWrapDescription;
	}
	/**
	 * @param stretchWrapDescription The stretchWrapDescription to set.
	 */
	public void setStretchWrapDescription(String stretchWrapDescription) {
		this.stretchWrapDescription = stretchWrapDescription;
	}
	/**
	 * @return Returns the caseCodePrint.
	 */
	public String getCaseCodePrint() {
		return caseCodePrint;
	}
	/**
	 * @param caseCodePrint The caseCodePrint to set.
	 */
	public void setCaseCodePrint(String caseCodePrint) {
		this.caseCodePrint = caseCodePrint;
	}
	/**
	 * @return Returns the countAnalyticalTests.
	 */
	public String getCountAnalyticalTests() {
		return countAnalyticalTests;
	}
	/**
	 * @param countAnalyticalTests The countAnalyticalTests to set.
	 */
	public void setCountAnalyticalTests(String countAnalyticalTests) {
		this.countAnalyticalTests = countAnalyticalTests;
	}
	/**
	 * @return Returns the countProcessParameters.
	 */
	public String getCountProcessParameters() {
		return countProcessParameters;
	}
	/**
	 * @param countProcessParameters The countProcessParameters to set.
	 */
	public void setCountProcessParameters(String countProcessParameters) {
		this.countProcessParameters = countProcessParameters;
	}
	/**
	 * @return Returns the listAnalyticalTests.
	 */
	public Vector getListAnalyticalTests() {
		return listAnalyticalTests;
	}
	/**
	 * @param listAnalyticalTests The listAnalyticalTests to set.
	 */
	public void setListAnalyticalTests(Vector listAnalyticalTests) {
		this.listAnalyticalTests = listAnalyticalTests;
	}
	/**
	 * @return Returns the listProcessParameters.
	 */
	public Vector getListProcessParameters() {
		return listProcessParameters;
	}
	/**
	 * @param listProcessParameters The listProcessParameters to set.
	 */
	public void setListProcessParameters(Vector listProcessParameters) {
		this.listProcessParameters = listProcessParameters;
	}
	/**
	 * @return Returns the height.
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * @return Returns the length.
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (1/6/2009 TWalton)
	 */
	public void loadFromBeanSpecification(BeanSpecification bs) {
	   try {
	   	
	   	SpecificationNEW thisSpec = bs.getSpecClass();
	   	// Load the Fields
		this.itemNumber = thisSpec.getItemWhse().getItemNumber();
		this.itemNumberOriginal = thisSpec.getItemWhse().getItemNumber();
		this.labBookID = thisSpec.getLabBookID();
		this.revisionDate = InqSpecification.formatDateFromyyyymmdd(thisSpec.getRevisionDate());
		this.revisionDateOriginal = InqSpecification.formatDateFromyyyymmdd(thisSpec.getRevisionDate());
		this.formulaNumber = thisSpec.getFormulaNumber();
		this.comments = thisSpec.getHeadComment();
		this.recordStatus = thisSpec.getSpecStatus();
		this.recordStatusOriginal = thisSpec.getSpecStatus();
	 // Can ONLY update Records in Pending Status,
	     // IF in Active Status, can only change back to pending, or to In-Active
		if (!this.recordStatus.equals("PENDING"))
			this.readOnly = "Y";
		
		this.slipSheetInformation = thisSpec.getSlipSheetInfo();
		this.stretchWrap = thisSpec.getStretchWrap();
		this.stretchWrapDescription = thisSpec.getStretchWrapInfo();
		this.shrinkWrap = thisSpec.getShrinkWrap();
		this.shrinkWrapDescription = thisSpec.getShrinkWrapInfo();
		this.codingInformation = thisSpec.getCodingInfo();
		this.caseCodePrint = thisSpec.getCaseCodePrint();
		this.casePrintLine1 = thisSpec.getCasePrintLine1();
		this.casePrintLine2 = thisSpec.getCasePrintLine2();
		this.casePrintGeneral = thisSpec.getCasePrintGeneral();
		this.storageConditions = thisSpec.getStorageConditions();
		this.specialRequirements = thisSpec.getSpecialRequirements();
		this.packComments = thisSpec.getPackComment();
		this.length = thisSpec.getLength();
		this.width = thisSpec.getWidth();
		this.height = thisSpec.getHeight();
		
		this.beanSpec = bs;
		
		// Build the Analytical Tests Vector from the Bean Information
		Vector listTests = new Vector();
		int testCount = bs.getListTests().size();
		if (testCount > 0)
		{
			for (int tst = 0; tst < testCount; tst++)
			{
				UpdSpecificationTestProcess bsTest = new UpdSpecificationTestProcess();
				try
				{
				   SpecificationTestProcess stp = (SpecificationTestProcess) bs.getListTests().elementAt(tst);
				   bsTest.setItemNumber(stp.getItemWhse().getItemNumber());
				   bsTest.setRevisionDate(stp.getRevisionDate());
				   bsTest.setValueID(stp.getTestProcess().trim());
				   bsTest.setIdSequence(stp.getPrintSeqNumber());
				   bsTest.setTarget(stp.getTarget());
				   bsTest.setMinimum(stp.getMinValue());
				   bsTest.setMaximum(stp.getMaxValue());
				   bsTest.setMethod(stp.getMethod());
				   bsTest.setTestValue(stp.getTestValue());
				   bsTest.setTestValueUOM(stp.getTestUOM());
				   bsTest.setTestProcessInfo(stp);
				}
				catch(Exception e)
				{}
				listTests.addElement(bsTest);
			}
		}
		this.listAnalyticalTests = listTests;
		// Build the Analytical Tests Vector from the Bean Information
		Vector listProcess = new Vector();
		int processCount = bs.getListProcesses().size();
		if (processCount > 0)
		{
			for (int proc = 0; proc < processCount; proc++)
			{
				UpdSpecificationTestProcess bsProcess = new UpdSpecificationTestProcess();
				try
				{
				   SpecificationTestProcess stp = (SpecificationTestProcess) bs.getListProcesses().elementAt(proc);
				   bsProcess.setItemNumber(stp.getItemWhse().getItemNumber());
				   bsProcess.setRevisionDate(stp.getRevisionDate());
				   bsProcess.setValueID(stp.getTestProcess().trim());
				   bsProcess.setIdSequence(stp.getPrintSeqNumber());
				   bsProcess.setTarget(stp.getTarget());
				   bsProcess.setMinimum(stp.getMinValue());
				   bsProcess.setMaximum(stp.getMaxValue());
				   bsProcess.setMethod(stp.getMethod());
				   bsProcess.setTestProcessInfo(stp);
				}
				catch(Exception e)
				{}
				listProcess.addElement(bsProcess);
			}
		}
		this.listProcessParameters = listProcess;		
		
		} catch (Exception e) {
	
		}
		return;
	}
	/**
	 * @return Returns the beanSpec.
	 */
	public BeanSpecification getBeanSpec() {
		return beanSpec;
	}
	/**
	 * @param beanSpec The beanSpec to set.
	 */
	public void setBeanSpec(BeanSpecification beanSpec) {
		this.beanSpec = beanSpec;
	}
	/**
	 * @return Returns the itemNumberError.
	 */
	public String getItemNumberError() {
		return itemNumberError;
	}
	/**
	 * @param itemNumberError The itemNumberError to set.
	 */
	public void setItemNumberError(String itemNumberError) {
		this.itemNumberError = itemNumberError;
	}
	/**
	 *   Method created 1/7/09  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public String buildDropDownFormula() {
		String dropDown = "";
		try
		{
			Vector sendParms = new Vector();
			sendParms.addElement("");
			Vector buildList = ServiceSpecificationFormula.dropDownFormula(sendParms);
	
			dropDown = DropDownSingle.buildDropDown(buildList, "formulaNumber", this.formulaNumber, "", "B", this.readOnly);	
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}
	/**
	 *   Method created 10/22/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public Vector buildDropDownStatus() {
		// add 1 element for the Drop Down
		//  1 Element for the Mouseover on the Status Section 
		//  SO it can explain WHY you can only have one, and WHAT you can do
		Vector returnInfo = new Vector();
		try
		{
			Vector ddList = new Vector();	
			DropDownSingle thisClass = new DropDownSingle();
			StringBuffer mouseoverValue = new StringBuffer();
			if (this.recordStatus.equals("PENDING"))
			{
				thisClass.setDescription("Pending");
				thisClass.setValue("PENDING");
				ddList.add(thisClass);
				thisClass = new DropDownSingle();
				thisClass.setDescription("Active");
				thisClass.setValue("ACTIVE");
				ddList.add(thisClass);
				mouseoverValue.append("If you choose Active, then any specifications for this item that are currently Active will be moved to INACTIVE."); 
			}
			if (this.recordStatus.equals("ACTIVE"))
			{
				try
				{
					String returnValue = ServiceSpecification.verifyCPGSpec(this.itemNumber, "PENDING");
					if (!returnValue.equals(""))
					{
						thisClass.setDescription("Pending");
						thisClass.setValue("PENDING");
						ddList.add(thisClass);
						thisClass = new DropDownSingle();	
					}
					else
					{
						mouseoverValue.append("There is Currently a Specification in Pending Status, if you want to move this one Back to Pending Status, you must Delete the one Currently in Pending Status.");
					}
				}
				catch(Exception e)
				{}
				thisClass.setDescription("Active");
				thisClass.setValue("ACTIVE");
				ddList.add(thisClass);
				thisClass = new DropDownSingle();
				thisClass.setDescription("Inactive");
				thisClass.setValue("INACTIVE");
				ddList.add(thisClass);
			}
			if (this.recordStatus.equals("INACTIVE"))
			{
				try
				{
					String returnValue = ServiceSpecification.verifyCPGSpec(this.itemNumber, "ACTIVE");
					if (!returnValue.equals(""))
					{
						thisClass.setDescription("Active");
						thisClass.setValue("ACTIVE");
						ddList.add(thisClass);
						thisClass = new DropDownSingle();	
					}
					else
					{
						mouseoverValue.append("There is Currently a Specification in Active Status, if you want to move this one Back to Active Status, you must Move the one Currently in Active Status, back to Pending Status.");
					}
				}
				catch(Exception e)
				{}
			   thisClass = new DropDownSingle();
			   thisClass.setDescription("Inactive");
			   thisClass.setValue("INACTIVE");
			   ddList.add(thisClass);
			}
			 mouseoverValue.append("  Only Specifications in Pending Status can be Deleted.");
	
			returnInfo.add(DropDownSingle.buildDropDown(ddList, "recordStatus", this.recordStatus, "None", "N", this.getReadOnlyStatus()));	
			returnInfo.add(mouseoverValue.toString());
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return returnInfo;
	}
	/**
	 * @return Returns the heightError.
	 */
	public String getHeightError() {
		return heightError;
	}
	/**
	 * @param heightError The heightError to set.
	 */
	public void setHeightError(String heightError) {
		this.heightError = heightError;
	}
	/**
	 * @return Returns the lengthError.
	 */
	public String getLengthError() {
		return lengthError;
	}
	/**
	 * @param lengthError The lengthError to set.
	 */
	public void setLengthError(String lengthError) {
		this.lengthError = lengthError;
	}
	/**
	 * @return Returns the readOnly.
	 */
	public String getReadOnly() {
		return readOnly;
	}
	/**
	 * @param readOnly The readOnly to set.
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 * @return Returns the widthError.
	 */
	public String getWidthError() {
		return widthError;
	}
	/**
	 * @param widthError The widthError to set.
	 */
	public void setWidthError(String widthError) {
		this.widthError = widthError;
	}
	/**
	 * @return Returns the revisionDateError.
	 */
	public String getRevisionDateError() {
		return revisionDateError;
	}
	/**
	 * @param revisionDateError The revisionDateError to set.
	 */
	public void setRevisionDateError(String revisionDateError) {
		this.revisionDateError = revisionDateError;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownMethod(String fieldName, String inValue) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(this.ddMethod, fieldName, inValue, "Choose One", "B", this.readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 *    Will Generate the Vectors for ALL Drop Down Lists Needed multiple times
	 */
	public void buildDropDownVectors() {
//		String dd = new String();
		try
		{
		   Vector sentValues = new Vector();
		   this.ddMethod = ServiceSpecificationMethod.dropDownMethod(sentValues);
		   sentValues.addElement("TEST");
		   this.ddAnalyticalTest = ServiceSpecification.dropDownCPGSpecsTestProcess(sentValues);
		   sentValues = new Vector();
		   sentValues.addElement("PROCESS");
		   this.ddProcessParameter = ServiceSpecification.dropDownCPGSpecsTestProcess(sentValues);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return;
	}
	/**
	 * @return Returns the ddAnalyticalTest.
	 */
	public Vector getDdAnalyticalTest() {
		return ddAnalyticalTest;
	}
	/**
	 * @param ddAnalyticalTest The ddAnalyticalTest to set.
	 */
	public void setDdAnalyticalTest(Vector ddAnalyticalTest) {
		this.ddAnalyticalTest = ddAnalyticalTest;
	}
	/**
	 * @return Returns the ddMethod.
	 */
	public Vector getDdMethod() {
		return ddMethod;
	}
	/**
	 * @param ddMethod The ddMethod to set.
	 */
	public void setDdMethod(Vector ddMethod) {
		this.ddMethod = ddMethod;
	}
	/**
	 * @return Returns the ddProcessParameter.
	 */
	public Vector getDdProcessParameter() {
		return ddProcessParameter;
	}
	/**
	 * @param ddProcessParameter The ddProcessParameter to set.
	 */
	public void setDdProcessParameter(Vector ddProcessParameter) {
		this.ddProcessParameter = ddProcessParameter;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownProcessParameter(String fieldName, String inValue) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(this.ddProcessParameter, fieldName, inValue, "Choose One", "N", this.readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownAnalyticalTest(String fieldName, String inValue) {
		String dd = new String();
		try
		{
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(this.ddAnalyticalTest, fieldName, inValue, "Choose One", "B", this.readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 * Will Return to the Screen, a String (Coded Drop Down List)
	 * Will retrieve a PreBuilt Vector from the fields
	 */
	public String buildDropDownTestValueUOM(String fieldName, String inValue) {
		String dd = new String();
		try
		{
			Vector ddsList = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setValue("%CitAc W/W");
			dds.setDescription("%CitAc W/W");
			ddsList.addElement(dds);
			dds = new DropDownSingle();
			dds.setValue("Brix");
			dds.setDescription("Brix");
			ddsList.addElement(dds);
			
		  // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownSingle.buildDropDown(ddsList, fieldName, inValue, "Choose One", "N", this.readOnly);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/*
	 * Test and Validate fields for All Analytical Tests, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 *   Within this Process will ALSO have to run Populate SpecialCharges
	 * 
	 */
	public void populateAnalyticalTests(HttpServletRequest request) {
		try
		{	
			Vector listTest = new Vector();
//			StringBuffer foundProblem = new StringBuffer();
			// Retrieve Payment information relating to the A Lot Number
			int countRecords = 0;
			countRecords = new Integer(this.getCountAnalyticalTests()).intValue();
			if (countRecords > 0)
			{
			   listTest = populateData(request, "test", countRecords);
			}
			this.setListAnalyticalTests(listTest);
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught: "  + e);
		}
		return;
	}
	/*
	 * Test and Validate fields for All Analytical Tests AND Process Parameters, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 *   Within this Process will ALSO have to run Populate SpecialCharges
	 * 
	 */
	 private Vector populateData(HttpServletRequest request, String populateType, int recordCount) {
	 	Vector listTest = new Vector();
	 	StringBuffer foundProblem = new StringBuffer();
		try
		{	
			for (int x = 0; x < (recordCount+ 1); x++)	
			{
			   UpdSpecificationTestProcess ustp = new UpdSpecificationTestProcess();
			   // Retrieve Data and Populate View Class
			   ustp.setItemNumber(this.getItemNumber());
			   ustp.setRevisionDate(this.getRevisionDate());
			   ustp.setUpdateUser(this.getUpdateUser());
			   ustp.setValueID(request.getParameter(populateType + "valueID" + x));
			   if (ustp.getValueID() == null)
			   	 ustp.setValueID("");
//			   System.out.println(ustp.getValueID());
			   ustp.setIdSequence(request.getParameter(populateType + "idSequence" + x));
			   if (ustp.getIdSequence() == null || ustp.getIdSequence().trim().equals(""))
			   	 ustp.setIdSequence("0");
			   ustp.setTarget(request.getParameter(populateType + "target" + x));
			   if (ustp.getTarget() == null || ustp.getTarget().trim().equals(""))
			   	 ustp.setTarget("0");
               ustp.setMinimum(request.getParameter(populateType + "minimum" + x));
               if (ustp.getMinimum() == null || ustp.getMinimum().trim().equals(""))
               	 ustp.setMinimum("0");
			   ustp.setMaximum(request.getParameter(populateType + "maximum" + x));
			   if (ustp.getMaximum() == null || ustp.getMaximum().trim().equals(""))
			   	 ustp.setMaximum("0");
			   ustp.setMethod(request.getParameter(populateType + "method" + x));
			   if (ustp.getMethod() == null || ustp.getMethod().trim().equals(""))
			   	 ustp.setMethod("0");
			   if (populateType.trim().equals("test"))
			   {
			   	 ustp.setTestValue(request.getParameter(populateType + "testValue" + x));
			   	 if (ustp.getTestValue() == null || ustp.getTestValue().trim().equals(""))
			   	 	ustp.setTestValue("0");
			   	 ustp.setTestValueUOM(request.getParameter(populateType + "testValueUOM" + x));
			   	 if (ustp.getTestValueUOM() == null)
			   	 	ustp.setTestValueUOM("");
			   }
			   //------------------------------------------------------
			   //--Validate:  ID Sequence Number
			   try
			   {
			   	   ustp.setIdSequenceError(validateBigDecimal(ustp.getIdSequence()));
			   	   if (!ustp.getIdSequenceError().trim().equals(""))
			   	      foundProblem.append(ustp.getIdSequenceError() + "<br>");
			   }
			   catch(Exception e)
			   {
			   	   ustp.setIdSequenceError("Problem Found with ID Sequence. " + e);
		   	       foundProblem.append("Problem Found with ID Sequence<br>");
			   }
//			 ------------------------------------------------------
			   //--Validate:  Target
			   try
			   {
			   	   ustp.setTargetError(validateBigDecimal(ustp.getTarget()));
			   	   if (!ustp.getTargetError().trim().equals(""))
			   	      foundProblem.append(ustp.getTargetError() + "<br>");
			   }
			   catch(Exception e)
			   {
			   	   ustp.setTargetError("Problem Found with Target. " + e);
		   	       foundProblem.append("Problem Found with Target<br>");
			   }
//				 ------------------------------------------------------
			   //--Validate:  Minimum
			   try
			   {
			   	   ustp.setMinimumError(validateBigDecimal(ustp.getMinimum()));
			   	   if (!ustp.getMinimumError().trim().equals(""))
			   	      foundProblem.append(ustp.getMinimumError() + "<br>");
			   }
			   catch(Exception e)
			   {
			   	   ustp.setMinimumError("Problem Found with Minimum. " + e);
		   	       foundProblem.append("Problem Found with Minimum<br>");
			   }
//				 ------------------------------------------------------
			   //--Validate:  Maximum
			   try
			   {
			   	   ustp.setMaximumError(validateBigDecimal(ustp.getMaximum()));
			   	   if (!ustp.getMaximumError().trim().equals(""))
			   	      foundProblem.append(ustp.getMaximumError() + "<br>");
			   }
			   catch(Exception e)
			   {
			   	   ustp.setMaximumError("Problem Found with Maximum. " + e);
		   	       foundProblem.append("Problem Found with Maximum<br>");
			   }
			   if (populateType.trim().equals("test"))
			   {
//				 ------------------------------------------------------
			      //--Validate:  Test Value
			      try
			      {
			   	     ustp.setTestValueError(validateBigDecimal(ustp.getTestValue()));
			   	     if (!ustp.getTestValueError().trim().equals(""))
			   	        foundProblem.append(ustp.getTestValueError() + "<br>");
			      }
			      catch(Exception e)
			      {
			   	    ustp.setTestValueError("Problem Found with Test Value. " + e);
		   	        foundProblem.append("Problem Found with Test Value<br>");
			      }
			   }
			   if (!ustp.getValueID().trim().equals("") ||
				   !foundProblem.toString().trim().equals(""))
				 listTest.addElement(ustp);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught: "  + e);
		}
		this.displayMessage = this.displayMessage + foundProblem.toString();
		return listTest;
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
	 * @return Returns the saveButton.
	 */
	public String getSaveButton() {
		return saveButton;
	}
	/**
	 * @param saveButton The saveButton to set.
	 */
	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
	}
	/**
	 * @return Returns the itemNumberOriginal.
	 */
	public String getItemNumberOriginal() {
		return itemNumberOriginal;
	}
	/**
	 * @param itemNumberOriginal The itemNumberOriginal to set.
	 */
	public void setItemNumberOriginal(String itemNumberOriginal) {
		this.itemNumberOriginal = itemNumberOriginal;
	}
	/**
	 * @return Returns the revisionDateOriginal.
	 */
	public String getRevisionDateOriginal() {
		return revisionDateOriginal;
	}
	/**
	 * @param revisionDateOriginal The revisionDateOriginal to set.
	 */
	public void setRevisionDateOriginal(String revisionDateOriginal) {
		this.revisionDateOriginal = revisionDateOriginal;
	}
	/*
	 * Test and Validate fields for All Process Parameters, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 *   Within this Process will ALSO have to run Populate SpecialCharges
	 * 
	 */
	public void populateProcessParameters(HttpServletRequest request) {
		try
		{	
			Vector listTest = new Vector();
			int countRecords = 0;
			countRecords = new Integer(this.getCountProcessParameters()).intValue();
			if (countRecords > 0)
			{
			   listTest = populateData(request, "process", countRecords);
			}
			this.setListProcessParameters(listTest);
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught: "  + e);
		}
		return;
	}
	public String getRecordStatusOriginal() {
		return recordStatusOriginal;
	}
	public void setRecordStatusOriginal(String recordStatusOriginal) {
		this.recordStatusOriginal = recordStatusOriginal;
	}
	/**
	* Determine Security for CPG Specifications
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       Set the information in readOnlyStatus Field -- Only Certain People can update the Status of a spec 
	*
	* Creation date: (1/26/2009 -- TWalton)
	*/
	public void determineSecurity(HttpServletRequest request,
								  HttpServletResponse response) {
		try
		{
			 try
			 {
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("8"))
			          this.readOnlyStatus = "N";
			    }
			 }
			 catch(Exception e)
			 {}
			 if (!this.readOnlyStatus.equals("N"))
			 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				           if (groupsR[xr].equals("111"))
				              this.readOnlyStatus = "N";
				     }
				   }
				   catch(Exception e)
				   {}
			 }  
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in UpdSpecification.determineSecurity(request, response)" + e);
		}
	    return;
	}
	public String getReadOnlyStatus() {
		return readOnlyStatus;
	}
	public void setReadOnlyStatus(String readOnlyStatus) {
		this.readOnlyStatus = readOnlyStatus;
	}
	public String getItemNumberCopy() {
		return itemNumberCopy;
	}
	public void setItemNumberCopy(String itemNumberCopy) {
		this.itemNumberCopy = itemNumberCopy;
	}
	public String getRevisionDateCopy() {
		return revisionDateCopy;
	}
	public void setRevisionDateCopy(String revisionDateCopy) {
		this.revisionDateCopy = revisionDateCopy;
	}
}
