/*
 * Created on May 26, 2010
 */
package com.treetop.services; 

import java.sql.*;
import java.util.*;
import java.math.*;

import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.controller.operations.InqOperations;
import com.treetop.app.item.InqItem;
import com.treetop.app.quality.*;
import com.treetop.services.ServiceOperationsReporting.ReportingType;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.*;
import com.ibm.as400.access.*;

/**
 * @author deisen.
 *
 * Services class to obtain and return data 
 * to business objects for quality.
 * 
 * 11/17/11 Twalton - change to reflect a more streamline Specification 
 * 
 */
public class ServiceQuality extends BaseService {	
	
	private static class BuildSQL {
	/**
	 *  Use this method to get the correct Supersedes Date 
	 *     to be used for the Specification Application
	 *  10/22/13 - TWalton Added BuildSQL section
	 */
	 private static String findSpecificationSupersedes(CommonRequestBean inBean)
	    throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {
				String library = GeneralUtility.getTTLibrary(inBean.getEnvironment()) + ".";
		
				sqlString.append("SELECT SHSPNO, SHRDTE, SHRTME ");
				
				sqlString.append("FROM " + library + "QAPGSPHD ");
	
				sqlString.append("WHERE SHCONO = " + inBean.getCompanyNumber().trim() + " ");
				sqlString.append("  AND SHSPNO = '" + inBean.getIdLevel1().trim() + "' ");
				sqlString.append("  AND SHRDTE < " + inBean.getIdLevel2().trim() + " ");
				
				sqlString.append("ORDER BY SHRDTE DESC ");
				
				//	*********************************************************************************
		} catch (Exception e) {
			throwError.append(" Error building findSpecificationSupersedes. " + e);
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceQuality.");
			throwError.append("BuildSQL.findSpecificationSupersedes(CommonRequestBean)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 *  Use this method to get the correct Supersedes Date 
	 *     to be used for the Formula Application
	 *  11/25/13 - TWalton Added BuildSQL section
	 */
	 private static String findFormulaSupersedes(CommonRequestBean inBean)
	    throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {
				String library = GeneralUtility.getTTLibrary(inBean.getEnvironment()) + ".";
		
				sqlString.append("SELECT FHFONO, FHRDTE, FHRTME ");
				
				sqlString.append("FROM " + library + "QAPIFOHD ");
	
				sqlString.append("WHERE FHCONO = " + inBean.getCompanyNumber().trim() + " ");
				sqlString.append("  AND FHFONO = '" + inBean.getIdLevel1().trim() + "' ");
				sqlString.append("  AND FHRDTE < " + inBean.getIdLevel2().trim() + " ");
				
				sqlString.append("ORDER BY FHRDTE DESC ");
				
				//	*********************************************************************************
		} catch (Exception e) {
			throwError.append(" Error building findFormulaSupersedes. " + e);
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceQuality.");
			throwError.append("BuildSQL.findFormulaSupersedes(CommonRequestBean)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 *  Use this method to get the correct Supersedes Date 
	 *     to be used for the Method Application - within the Specifications
	 *  11/25/13 - TWalton Added BuildSQL section
	 */
	 private static String findMethodSupersedes(CommonRequestBean inBean)
	    throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {
				String library = GeneralUtility.getTTLibrary(inBean.getEnvironment()) + ".";
		
				sqlString.append("SELECT MHMENO, MHRDTE, MHRTME ");
				
				sqlString.append("FROM " + library + "QAPKMEHD ");
	
				sqlString.append("WHERE MHCONO = " + inBean.getCompanyNumber().trim() + " ");
				sqlString.append("  AND MHMENO = '" + inBean.getIdLevel1().trim() + "' ");
				sqlString.append("  AND MHRDTE < " + inBean.getIdLevel2().trim() + " ");
				
				sqlString.append("ORDER BY MHRDTE DESC ");
				
				//	*********************************************************************************
		} catch (Exception e) {
			throwError.append(" Error building findMethodSupersedes. " + e);
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceQuality.");
			throwError.append("BuildSQL.findMethodSupersedes(CommonRequestBean)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}
	
   }// End of the Build SQL Section
//*********************************************************************************************	
	
	public ServiceQuality() {
		super();
	}
	
/**
 * @author deisen.
 * Main testing. 
 */
	
public static void main(String[] args) 
{
	try {				
		
		//Start here
		String startDebug = "start debug here. ";

		// Testing gather of Supersedes Date
		if (1 == 0)
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("TST");
			crb.setIdLevel1("78917");
			crb.setIdLevel2("20120918");
			
			DateTime dt = findSpecificationSupersedes(crb);
			String breakPoint = "stopHere";
			
		}
		
		// testing delete specification
		
		if (1 == 0) 
		{
			Vector<UpdSpecification> beanSpecification = new Vector<UpdSpecification>();
			
			UpdSpecification specificationHeader = new UpdSpecification();	
			specificationHeader.setEnvironment("TST");
			specificationHeader.setCompany("300 ");
			specificationHeader.setDivision(" 275"); 			
			specificationHeader.setSpecNumber(" 73410 ");
			specificationHeader.setRevisionDate("20080416");
			specificationHeader.setRevisionTime("120000");
			
//			specificationHeader.setListSpecItems(beanDetail);			
						
			beanSpecification.addElement(specificationHeader);
			BeanQuality result  = new BeanQuality();	
			result = deleteSpecification(beanSpecification);
			String breakPoint = "stopHere";
		}
		// testing find specification
		
		if (1 == 0)
		{
			Vector<DtlSpecification> beanSpecification = new Vector<DtlSpecification>();
			
			DtlSpecification specification = new DtlSpecification();
			specification.setEnvironment("TST");
			specification.setCompany("100 ");
			specification.setDivision(" 100"); 
			specification.setSpecNumber("80076");			
			specification.setRevisionDate("20130204");
			specification.setRevisionTime("102755");
		
			beanSpecification.addElement(specification);
			BeanQuality result  = new BeanQuality();	
			result = findSpecification(beanSpecification);
			String breakPoint = "stopHere";
			
			String request = new String("find");
			String throwError = new String();
			result = returnSpecification(request, throwError, beanSpecification);
			breakPoint = "stopHere";
		}
		// testing insert/update specification
		
		if (1 == 0)
		{
			Vector<UpdSpecification>            beanSpecification = new Vector<UpdSpecification>();
			Vector<UpdTestParameters> specTestList      = new Vector<UpdTestParameters>();
			Vector<UpdTestParameters> specProcessList   = new Vector<UpdTestParameters>();
			Vector<UpdVariety>     specVarIncList    = new Vector<UpdVariety>();
			Vector<UpdVariety>     specVarExcList    = new Vector<UpdVariety>();
			
			UpdSpecification specificationHeader = new UpdSpecification();	
			specificationHeader.setEnvironment("TST");
			specificationHeader.setCompany("400 ");
			specificationHeader.setDivision(" 276"); 
			specificationHeader.setStatus("PD ");			
			specificationHeader.setSpecNumber(" 73410 ");
			specificationHeader.setRevisionDate("20080416");
			specificationHeader.setRevisionTime("120000");
			specificationHeader.setFormulaNumber(" 0 ");
			specificationHeader.setUpdateUser("UpdatE");
			specificationHeader.setCountryOfOrigin("MEXICO USA CANADA DRY");
			specificationHeader.setReconstitutionRatio("25% Concentrate");
			specificationHeader.setShelfLifeNotValid("Y");
			
			
			UpdTestParameters specificationTest1 = new UpdTestParameters();
			specificationTest1.setEnvironment(specificationHeader.getEnvironment());
			specificationTest1.setCompany(specificationHeader.getCompany());
			specificationTest1.setDivision(specificationHeader.getDivision());	
			specificationTest1.setRecordID(specificationHeader.getSpecNumber());
			specificationTest1.setRevisionDate(specificationHeader.getRevisionDate());
			specificationTest1.setRevisionTime(specificationHeader.getRevisionTime());
			specificationTest1.setAttributeID("BRIX");
			specificationTest1.setAttributeIDSequence("1");
			specificationTest1.setUnitOfMeasure("Degrees");
			specificationTest1.setTarget("11.70");
//			specificationTest1.setMinimumPercent("10.15");
//			specificationTest1.setMaximumPercent("13.25");
			specificationTest1.setTestValue("12.00");
			specificationTest1.setTestValueUOM("Brix");
			specificationTest1.setMethod("0");
			specTestList.addElement(specificationTest1);
			
			UpdTestParameters specificationProcess1 = new UpdTestParameters();
			specificationProcess1.setEnvironment(specificationHeader.getEnvironment());	
			specificationProcess1.setCompany(specificationHeader.getCompany());
			specificationProcess1.setDivision(specificationHeader.getDivision());	
			specificationProcess1.setRecordID(specificationHeader.getSpecNumber());
			specificationProcess1.setRevisionDate(specificationHeader.getRevisionDate());
			specificationProcess1.setRevisionTime(specificationHeader.getRevisionTime());
			specificationProcess1.setAttributeID("PASTEURIZATION");
			specificationProcess1.setAttributeIDSequence("1");
			specificationProcess1.setUnitOfMeasure("Seconds");
			specificationProcess1.setTarget("45.0");
//			specificationProcess1.setMinimumPercent("30.0");
//			specificationProcess1.setMaximumPercent("60.0");
			specificationProcess1.setMethod("0");
			specProcessList.addElement(specificationProcess1);
			
			UpdVariety specificationVariety1 = new UpdVariety();
			specificationVariety1.setEnvironment(specificationHeader.getEnvironment());	
			specificationVariety1.setCompany(specificationHeader.getCompany());
			specificationVariety1.setDivision(specificationHeader.getDivision());	
			specificationVariety1.setRecordID(specificationHeader.getSpecNumber());
			specificationVariety1.setRevisionDate(specificationHeader.getRevisionDate());
			specificationVariety1.setRevisionTime(specificationHeader.getRevisionTime());
			specificationVariety1.setCropModel("QA APPLE");
			specificationVariety1.setAttributeID("VAR");
			specificationVariety1.setVariety("GOLDEN");		
			specificationVariety1.setMinimumPercent("0.6000");
			specificationVariety1.setMaximumPercent("1.0000");
			specificationVariety1.setUpdateUser("UserI");	
			specVarIncList.addElement(specificationVariety1);
			
			UpdVariety specificationVariety2 = new UpdVariety();
			specificationVariety2.setEnvironment(specificationHeader.getEnvironment());
			specificationVariety2.setCompany(specificationHeader.getCompany());
			specificationVariety2.setDivision(specificationHeader.getDivision());
			specificationVariety2.setRecordID(specificationHeader.getSpecNumber());
			specificationVariety2.setRevisionDate(specificationHeader.getRevisionDate());
			specificationVariety2.setRevisionTime(specificationHeader.getRevisionTime());
			specificationVariety2.setCropModel("QA APPLE");
			specificationVariety2.setAttributeID("VAR");
			specificationVariety2.setVariety("REDS");
			specificationVariety2.setMinimumPercent("0.2500");
			specificationVariety2.setMaximumPercent("0.3500");
			specificationVariety2.setUpdateUser("UserX");	
			specVarExcList.addElement(specificationVariety2);
			
//			specificationHeader.setListSpecItems(specItemList);
			specificationHeader.setListAnalyticalTests(specTestList);
			specificationHeader.setListProcessParameters(specProcessList);
			specificationHeader.setListVarietiesIncluded(specVarIncList);
			specificationHeader.setListVarietiesExcluded(specVarExcList);
			beanSpecification.addElement(specificationHeader);	
			
			BeanQuality result  = new BeanQuality();	
			result = updateSpecification(beanSpecification);
			String breakPoint = "stopHere";
		}
		// testing list specification
		
		if (1 == 0)
		{
			Vector<InqSpecification> beanSpecification = new Vector<InqSpecification>();
			
			InqSpecification specification = new InqSpecification();
			specification.setEnvironment("TST");
			specification.setCompany("300 ");
			specification.setDivision(" 275");
			specification.setInqTemplate("QaSpecificationDetail");
//			specification.setInqItemNumber(" 101490 ");
//			specification.setInqProductGroup(" PG1016 ");
			specification.setInqSpecNumber("  73410 ");
//			specification.setInqRecordStatus(" IN");
//			specification.setInqSpecDescription("flake");
//			specification.setInqFormulaName("drum");
			specification.setOrderBy(" status ");
			specification.setOrderStyle("desc    ");
			
			beanSpecification.addElement(specification);
			BeanQuality result = new BeanQuality();	
			result = listSpecification(beanSpecification);
			String breakPoint = "stopHere";
		}
		// testing update specification
		
		if (1 == 0)
		{
			Vector<UpdSpecification> beanSpecification = new Vector<UpdSpecification>();		
			
			UpdSpecification specificationHeader = new UpdSpecification();	
			specificationHeader.setEnvironment("TST");
			specificationHeader.setCompany(" 300");
			specificationHeader.setDivision("275 ");
			specificationHeader.setStatus("DL");
			specificationHeader.setOriginalStatus("AC");
			specificationHeader.setSpecNumber(" 73410 ");
			specificationHeader.setRevisionDate("20080416");
			specificationHeader.setRevisionTime("120000");
			
			beanSpecification.addElement(specificationHeader);
			BeanQuality result = new BeanQuality();
			result = updateSpecificationStatus(beanSpecification);
			String breakPoint = "stopHere";
		}
		// testing verify specification
		
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("300");
			commonRequest.setDivisionNumber("275");
			commonRequest.setIdLevel1(" 73410");
			commonRequest.setDate("20080416 ");
			commonRequest.setTime(" 120000");			
			String result = new String();	
			result = verifySpecification(commonRequest);
			String breakPoint = "stopHere";
		}
		// testing specification identification number
		
		if (1 == 0)
		{
			BeanQuality result  = new BeanQuality();	
			result = nextIDNumberSpecification("TST");
			String breakPoint = "stopHere";
		}		
		// testing delete formula
		
		if (1 == 0) 
		{
			Vector<UpdFormula> beanFormula = new Vector<UpdFormula>();
			
			UpdFormula formulaHeader = new UpdFormula();	
			formulaHeader.setEnvironment("TST");
			formulaHeader.setCompany(" 100 ");
			formulaHeader.setDivision(" 100 ");
			formulaHeader.setFormulaNumber(" 665544 ");
			formulaHeader.setRevisionDate("20100731");
			formulaHeader.setRevisionTime("120000");
			
			UpdFormulaDetail formulaDetail = new UpdFormulaDetail();
			formulaDetail.setEnvironment(formulaHeader.getEnvironment());
			formulaDetail.setCompany(formulaHeader.getCompany());
			formulaDetail.setDivision(formulaHeader.getDivision());	
			formulaDetail.setFormulaNumber(formulaHeader.getFormulaNumber());
			formulaDetail.setRevisionDate(formulaHeader.getRevisionDate());
			formulaDetail.setRevisionTime(formulaHeader.getRevisionTime());				
			Vector<UpdFormulaDetail> beanDetail = new Vector<UpdFormulaDetail>();
			beanDetail.addElement(formulaDetail);
		//	formulaHeader.setListFormulaDetail(beanDetail);			
						
			beanFormula.addElement(formulaHeader);
			BeanQuality result = new BeanQuality();	
			result = deleteFormula(beanFormula);
			String breakPoint = "stopHere";
		}
		// testing find formula
		
		if (1 == 0)
		{
			Vector<DtlFormula> beanFormula = new Vector<DtlFormula>();
			
			DtlFormula formula = new DtlFormula();
			formula.setEnvironment("TST");
			formula.setCompany(" 200");
			formula.setDivision("100 ");	
			formula.setFormulaNumber(" 1530 ");
			formula.setRevisionDate(" 20100915");	
			formula.setRevisionTime(" 123045 ");
		
			beanFormula.addElement(formula);
			BeanQuality result = new BeanQuality();	
			result = findFormula(beanFormula);
			String breakPoint = "stopHere";
			
			String request = new String("find");
			String throwError = new String();
			result = returnFormula(request, throwError, beanFormula);
			breakPoint = "stopHere";
		}
		// testing insert/update formula
		
		if (1 == 0)
		{
			Vector<UpdFormula>       beanFormula       = new Vector<UpdFormula>();
			Vector<UpdFormulaDetail> formulaDetailList = new Vector<UpdFormulaDetail>();
			
			UpdFormula formulaHeader = new UpdFormula();	
			formulaHeader.setEnvironment("TST");
			formulaHeader.setCompany("200");
			formulaHeader.setDivision("100");	
			formulaHeader.setOriginalStatus("AC ");			
			formulaHeader.setFormulaNumber("  1530 ");
			formulaHeader.setRevisionDate("20100915");
			formulaHeader.setRevisionTime("123045");
			formulaHeader.setReferenceFormulaNumber(" 0 ");
			formulaHeader.setReferenceFormulaRevisionDate("0");
			formulaHeader.setReferenceFormulaRevisionTime("0");
			formulaHeader.setFormulaDescription("pear flakes  ");
			formulaHeader.setBatchSize("10500");
			formulaHeader.setBatchUOM("GAL");
			formulaHeader.setTargetBrix("16.5");
			formulaHeader.setUpdateUser("UPDATE");
			formulaHeader.setUpdateDate("19550810");
			formulaHeader.setUpdateTime("121150");
			formulaHeader.setCreationDate("19540823");
			formulaHeader.setCreationTime("081055");
			//formulaHeader.setSupercededDate("19550810");
			//formulaHeader.setSupercededTime("082354");
			
			UpdFormulaDetail formulaDetail1 = new UpdFormulaDetail();
			formulaDetail1.setEnvironment(formulaHeader.getEnvironment());
			formulaDetail1.setCompany(formulaHeader.getCompany());
			formulaDetail1.setDivision(formulaHeader.getDivision());	
			formulaDetail1.setStatus(formulaHeader.getStatus());
			formulaDetail1.setFormulaNumber(formulaHeader.getFormulaNumber());
			formulaDetail1.setRevisionDate(formulaHeader.getRevisionDate());
			formulaDetail1.setRevisionTime(formulaHeader.getRevisionTime());
			formulaDetail1.setDetailSequence("11");
			formulaDetail1.setItemNumber1(" ");
			formulaDetail1.setItemDescription1("this is the description");
			formulaDetail1.setQuantity1("2080");
			formulaDetail1.setQuantity2("1040");
			formulaDetail1.setUnitOfMeasure2("LB");
			formulaDetail1.setSupplierNumber("12345");
			formulaDetail1.setReferenceSpec("85429");		
			formulaDetailList.addElement(formulaDetail1);				
			
			UpdFormulaDetail formulaDetail2 = new UpdFormulaDetail();
			formulaDetail2.setEnvironment(formulaHeader.getEnvironment());
			formulaDetail2.setCompany(formulaHeader.getCompany());
			formulaDetail2.setDivision(formulaHeader.getDivision());	
			formulaDetail2.setStatus(formulaHeader.getStatus());
			formulaDetail2.setFormulaNumber(formulaHeader.getFormulaNumber());
			formulaDetail2.setRevisionDate(formulaHeader.getRevisionDate());
			formulaDetail2.setRevisionTime(formulaHeader.getRevisionTime());
			formulaDetail2.setDetailSequence("12");
			formulaDetail2.setItemNumber1(" 101493");
			formulaDetail2.setItemDescription1("another description");
			formulaDetail2.setQuantity1("1500");
			formulaDetail2.setQuantity2("750");
			formulaDetail2.setUnitOfMeasure2("FS");		
			formulaDetail2.setSupplierNumber("82354");
			formulaDetail2.setReferenceSpec("1278");		
			formulaDetailList.addElement(formulaDetail2);	
			
	//		formulaHeader.setListFormulaDetail(formulaDetailList);
			beanFormula.addElement(formulaHeader);	
			BeanQuality result  = new BeanQuality();	
			result = updateFormula(beanFormula);
			String breakPoint = "stopHere";
		}
		// testing list formula
		
		if (1 == 0)
		{
			InqFormula formula = new InqFormula();
			formula.setEnvironment("TST");
			formula.setCompany(" 100");
			formula.setDivision("100 ");
			formula.setInqTemplate("QaFormulaDetail");
		//	formula.setInqFormulaNumber("  1530 ");
		//	formula.setInqStatus("AC");
		//	formula.setInqFormulaDescription("flake");
		//	formula.setInqItemNumber("101493");
		//	formula.setInqItemDescription("FLAVOR");
		//	formula.setOrderBy(" formulaNumber ");
			formula.setOrderStyle(" desc   ");
			
			Vector<InqFormula> beanFormula = new Vector<InqFormula>();
			beanFormula.addElement(formula);
			BeanQuality result = new BeanQuality();
			result = listFormula(beanFormula);
			String breakPoint = "stopHere";
		}
		// testing update formula 
		
		if (1 == 0)
		{
			Vector<UpdFormula> beanFormula = new Vector<UpdFormula>();		
			
			UpdFormula formulaHeader = new UpdFormula();	
			formulaHeader.setEnvironment("TST");
			formulaHeader.setStatus("DL");
			formulaHeader.setOriginalStatus("PD");
			formulaHeader.setFormulaNumber("  9500 ");
			formulaHeader.setRevisionDate("20100910");
			formulaHeader.setRevisionTime("123457");
			
			beanFormula.addElement(formulaHeader);
			BeanQuality result = new BeanQuality();
			result = updateFormulaStatus(beanFormula);
			String breakPoint = "stopHere";
		}
		// testing verify formula
		
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");
			commonRequest.setIdLevel1(" 1530");
			commonRequest.setDate("20100915 ");
			commonRequest.setTime(" 123045");
			String result = new String();		
			result = verifyFormula(commonRequest);
			String breakPoint = "stopHere";
		}
		// testing formula identification number
		
		if (1 == 0)
		{
			BeanQuality result  = new BeanQuality();	
			result = nextIDNumberFormula("TST");
			String breakPoint = "stopHere";
		}		
		// testing delete method
		
		if (1 == 0) 
		{
			Vector<UpdMethod> beanMethod = new Vector<UpdMethod>();
			
			UpdMethod methodHeader = new UpdMethod();
			methodHeader.setEnvironment("TST");
			methodHeader.setCompany(" 525 ");
			methodHeader.setDivision(" 888 ");
			methodHeader.setMethodNumber("  85700 ");
			methodHeader.setRevisionDate("20100815");
			methodHeader.setRevisionTime("122745");
			
			beanMethod.addElement(methodHeader);
			BeanQuality result = new BeanQuality();
			result = deleteMethod(beanMethod);
			String breakPoint = "stopHere";
		}
		// testing find method
		
		if (1 == 0)
		{
			Vector<DtlMethod> beanMethod = new Vector<DtlMethod>();
			
			DtlMethod method = new DtlMethod();			
			method.setEnvironment("TST");
			method.setCompany(" 525 ");
			method.setDivision(" 889 ");
			method.setMethodNumber("  85700 ");
		//	method.setRevisionDate("20100815");
		//  method.setRevisionTime("122746");
			
			beanMethod.addElement(method);
			BeanQuality result = new BeanQuality();
			result = findMethod(beanMethod);
			String breakPoint = "stopHere";
			
			String request = new String("find");
			String throwError = new String();
			result = returnMethod(request, throwError, beanMethod);
			breakPoint = "stopHere";
		}
		// testing insert/update method
		
		if (1 == 0)
		{
			Vector<UpdMethod> beanMethod = new Vector<UpdMethod>();
			
			UpdMethod methodHeader = new UpdMethod();	
			methodHeader.setEnvironment("TST");
			methodHeader.setCompany(" 525 ");
			methodHeader.setDivision(" 889 ");
			methodHeader.setMethodNumber("  85700 ");
			methodHeader.setRevisionDate("20100815");
			methodHeader.setRevisionTime("122745");
			methodHeader.setStatus("AC ");
			methodHeader.setRecordType("PRO");
			methodHeader.setOriginationUser("DEISEN");
			methodHeader.setGroupCode("CON");
			methodHeader.setScopeCode("SP2");
			methodHeader.setMethodName(" whats in a name  ");
			methodHeader.setMethodDescription("New Method for Friday");
			methodHeader.setRevisionReason("reason for the revision");
			methodHeader.setUpdateDate("19220519");
			methodHeader.setUpdateTime("080900");
			methodHeader.setUpdateUser("InserU");
			methodHeader.setCreationDate("19540823");
			methodHeader.setCreationTime("120500");
			methodHeader.setCreationUser("InserC");
//			methodHeader.setSupercededDate("19550810");
//			methodHeader.setSupercededTime("082354");
			
			beanMethod.addElement(methodHeader);	
			BeanQuality result  = new BeanQuality();	
			result = updateMethod(beanMethod);
			String breakPoint = "stopHere";
		}
		// testing list method
		
		if (1 == 0)
		{
			InqMethod method = new InqMethod();
			method.setEnvironment("TST");
			method.setCompany(" 525 ");
			method.setDivision(" 889 ");
			method.setInqTemplate("QaMethodHeader");
		//	method.setInqMethodNumber("  5600 ");
		//	method.setInqMethodStatus(" IN");
		//	method.setInqMethodDescription("flake");
		//	method.setInqBodyTextLine("testing");
			method.setOrderBy(" status ");
			method.setOrderStyle(" desc   ");
			
			Vector<InqMethod> beanMethod = new Vector<InqMethod>(); 
			beanMethod.addElement(method);
			BeanQuality result = new BeanQuality();	
			result = listMethod(beanMethod);
			String breakPoint = "stopHere";
		}
		// testing update method
		
		if (1 == 0)
		{
			Vector<UpdMethod> beanMethod = new Vector<UpdMethod>();		
			
			UpdMethod methodHeader = new UpdMethod();	
			methodHeader.setEnvironment("TST");
			methodHeader.setCompany(" 525 ");
			methodHeader.setDivision(" 888 ");
			methodHeader.setStatus("PD");
			methodHeader.setOriginalStatus("AC");
			methodHeader.setMethodNumber("  85700 ");			
			methodHeader.setRevisionDate("20100815");
			methodHeader.setRevisionTime("122745");
			
			beanMethod.addElement(methodHeader);
			BeanQuality result = new BeanQuality();
			result = updateMethodStatus(beanMethod);
			String breakPoint = "stopHere";
		}
		// testing verify method
		
		if (1 == 0) 
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("525");
			commonRequest.setDivisionNumber("888");
			commonRequest.setIdLevel1(" 85700");
			commonRequest.setDate("20100815 ");
			commonRequest.setTime(" 122744");			
			String result = new String();	
			result = verifyMethod(commonRequest);
			String breakPoint = "stopHere";
		}
		// testing method identification number
		
		if (1 == 0)
		{
			BeanQuality result  = new BeanQuality();	
			result = nextIDNumberMethod("TST");
			String breakPoint = "stopHere";
		}		
		// testing drop down boxes (change method as needed)
		
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");			
			Vector result  = new Vector();	
			result = dropDownStatus(commonRequest);
			String breakPoint = "stopHere";
		}
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");			
			Vector result  = new Vector();	
			result = dropDownScope(commonRequest);
			String breakPoint = "stopHere";
		}
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");
		//	commonRequest.setIdLevel1("Grouping");		
			Vector result  = new Vector();	
			result = dropDownGrouping(commonRequest);
			String breakPoint = "stopHere";
		}
		// miscellaneous
		
		if (1 == 0)
		{
			Vector result  = new Vector();	
			result = GeneralUtility.dropDownUnitOfMeasure("TST", "100");
			String breakPoint = "stopHere";
		}
		
		if (1 == 0)
		{
			Vector result  = new Vector();
			result = ServiceAttribute.dropDownCrop("TST");
			String breakPoint = "stopHere";
		}
		
		if (1 == 0)
		{
			Vector result  = new Vector();
			result = ServiceAttribute.dropDownCropVariety("TST", "Cherry");
			String breakPoint = "stopHere";
		}		
		
		if (1 == 0)
		{
			Vector result  = new Vector();
			result = ServiceAttribute.dropDownFruitVariety("TST");
			String breakPoint = "stopHere";
		}
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");
			commonRequest.setIdLevel1(" 139 ");
			commonRequest.setIdLevel2(" m3 ");			
			String result  = new String();	
			result = ServiceCustomer.verifyCustomerByNumber(commonRequest);
			String breakPoint = "stopHere";
		}
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");
			commonRequest.setIdLevel1(" 100588166 ");				
			String result  = new String();	
			result = ServiceItem.verifyItem(commonRequest);
			String breakPoint = "stopHere";
		}
		if (1 == 0)
		{
			CommonRequestBean commonRequest = new CommonRequestBean();
			commonRequest.setEnvironment("TST");
			commonRequest.setCompanyNumber("100");
			commonRequest.setDivisionNumber("100");
			commonRequest.setIdLevel1(" 10000 ");				
			String result  = new String();	
			result = ServiceSupplier.verifySupplier(commonRequest);
			String breakPoint = "stopHere";
		}
		
	} catch (Exception e) {
		System.out.println(e);	
	}
}

/**
 * @author deisen.
 * return the "find" result or provide an error response.
 */	

private static BeanQuality returnSpecification(String inRequestType, String inThrowError,
								               Vector inValues)
throws Exception 
{
	StringBuffer               throwError        = new StringBuffer();
	Vector<DtlSpecification> beanSpecification = new Vector<DtlSpecification>();
	BeanQuality                returnValue       = new BeanQuality();
			
	try {	
		
		DtlSpecification findRequest = (DtlSpecification) inValues.elementAt(0);
		
		QaSpecification specification = new QaSpecification();		
		specification.setSpecificationNumber(findRequest.getSpecNumber());
		specification.setCompanyNumber(findRequest.getCompany());
		specification.setDivisionNumber(findRequest.getDivision());
		specification.setRevisionDate(findRequest.getRevisionDate());
		specification.setRevisionTime(findRequest.getRevisionTime());
		returnValue.setSpecification(specification);
				
		if (inRequestType.trim().equals("find")) 
		{		
			beanSpecification.addElement(findRequest);
			returnValue = findSpecification(beanSpecification);
		}	
		
		if (inRequestType.trim().equals("error")) 
		{		
			returnValue.setEnvironment(findRequest.getEnvironment());	
			returnValue.setStatusMessage(inThrowError);		
		}				

	} catch (Exception e) {
			throwError.append(" Error processing a find or error response for request type " +
						      inRequestType + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("returnSpecification(String, String, Vector)");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Load class fields from result set.
 * 11/17/11 TWalton - streamline the specification,  delete not used code
 *       if you need to review the old code, see the project TreeNet61 -- the code is in that
 */
			
private static Vector loadFields(String requestType,
						         ResultSet rs)
throws Exception
{
	StringBuffer throwError     = new StringBuffer();	
	Vector       returnValue    = new Vector();
	
	Hashtable categoryCodes = buildSqlCategoryCodes();
	
//  *************************************************************************************
//	SPECIFICATION (Header)
//  *************************************************************************************			
	try { 
		
		if (requestType.trim().equals("QaSpecificationHeader")) 
		{
			QaSpecification specification = new QaSpecification();
		  
			specification.setCompanyNumber(rs.getString("SHCONO").trim());
			specification.setDivisionNumber(rs.getString("SHDIVI").trim());
			specification.setSpecificationNumber(rs.getString("SHSPNO").trim());
			specification.setRevisionDate(rs.getString("SHRDTE").trim());
			specification.setRevisionTime(rs.getString("SHRTME").trim());		
			specification.setSpecificationName(rs.getString("SHNAME").trim());
			specification.setStatusCode(rs.getString("SHSTAT").trim());
			specification.setStatusDescription(rs.getString("STTEXT").trim());
			specification.setTypeCode(rs.getString("SHTYPE").trim());
			specification.setTypeDescription(rs.getString("TPTEXT").trim());
			specification.setGroupingCode(rs.getString("SHGRUP").trim());
			specification.setGroupingDescription(rs.getString("GPTEXT").trim());
			specification.setScopeCode(rs.getString("SHSCOP").trim());
			specification.setScopeDescription(rs.getString("SCTEXT").trim());
			specification.setSpecificationDescription(rs.getString("SHDESC").trim());
			specification.setOriginationUser(rs.getString("SHOUSR").trim());
			specification.setApprovedByUser(rs.getString("SHAUSR").trim());
			specification.setItemNumber(rs.getString("SHITNO").trim());
			specification.setItemDescription(rs.getString("IDESC").trim());
			specification.setProductionStatus(rs.getString("SHPDST").trim());
			specification.setRevisionReasonText(rs.getString("SHRTXT").trim());
			specification.setReferenceSpecNumber(rs.getString("SHRSNO").trim());
			specification.setReferenceSpecRevDate(rs.getString("SHRSDT").trim());
			specification.setReferenceSpecRevTime(rs.getString("SHRSTM").trim());
			specification.setFormulaNumber(rs.getString("SHFONO").trim());
			specification.setFormulaName(rs.getString("FNAME").trim());
		    specification.setFormulaDescription(rs.getString("FDESC").trim());
			specification.setFormulaRevisionDate(rs.getString("FDATE").trim());
			specification.setFormulaRevisionTime(rs.getString("FTIME").trim());
			specification.setCustomerNumber(rs.getString("SHCUNO").trim());
			specification.setCustomerName(rs.getString("SHCUNM").trim());
			specification.setCustomerCode(rs.getString("SHCUCD").trim());
			specification.setUpdatedDate(rs.getString("SHUDTE").trim());
			specification.setUpdatedTime(rs.getString("SHUTME").trim());
			specification.setUpdatedUser(rs.getString("SHUUSR").trim());
			specification.setCreatedDate(rs.getString("SHCDTE").trim());
			specification.setCreatedTime(rs.getString("SHCTME").trim());
			specification.setCreatedUser(rs.getString("SHCUSR").trim());
//			try{
//				if (rs.getString("SuperDate") != null)
//					specification.setSupercededDate(rs.getString("SuperDate").trim());
				//	specification.setSupercededTime(rs.getString("SHSTME").trim());
//			}catch(Exception e){}
			
			if ((!rs.getString("SHCUNO").trim().equals(""))	&&
				 (rs.getString("SHCUCD").trim().equals(categoryCodes.get(11))))
			{
				specification.setCustomerName(rs.getString("M3CUNM").trim());
			}
			if ((!rs.getString("SHCUNO").trim().equals(""))	&&
				 (rs.getString("SHCUCD").trim().equals(categoryCodes.get(12))))
			{
				specification.setCustomerName(rs.getString("SACUNM").trim());
			}
			specification.setCountryOfOrigin(rs.getString("SHCORG").trim());
			specification.setReconstitutionRatio(rs.getString("SHRCON").trim());
			specification.setShelfLifeNotValid(rs.getString("SHSLCB").trim());
			
			Vector<QaSpecification> specificationHeader = new Vector<QaSpecification>();
			specificationHeader.addElement(specification);
			returnValue = specificationHeader;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaSpecificationHeader from the result set. " + e) ;
	}
	
//  *************************************************************************************
//	SPECIFICATION (Special Packaging Requirements)
//  *************************************************************************************		
	try {
		
		if (requestType.trim().equals("QaSpecificationPackaging")) 
		{
			QaSpecificationPackaging packaging = new QaSpecificationPackaging(); // extends QaSpecification
			// Header Section
			packaging.setCompanyNumber(rs.getString("SHCONO").trim());
			packaging.setDivisionNumber(rs.getString("SHDIVI").trim());
			packaging.setSpecificationNumber(rs.getString("SHSPNO").trim());
			packaging.setRevisionDate(rs.getString("SHRDTE").trim());
			packaging.setRevisionTime(rs.getString("SHRTME").trim());		
			packaging.setSpecificationName(rs.getString("SHNAME").trim());
			packaging.setStatusCode(rs.getString("SHSTAT").trim());
			packaging.setStatusDescription(rs.getString("STTEXT").trim());
			packaging.setTypeCode(rs.getString("SHTYPE").trim());
			packaging.setTypeDescription(rs.getString("TPTEXT").trim());
			packaging.setGroupingCode(rs.getString("SHGRUP").trim());
			packaging.setGroupingDescription(rs.getString("GPTEXT").trim());
			packaging.setScopeCode(rs.getString("SHSCOP").trim());
			packaging.setScopeDescription(rs.getString("SCTEXT").trim());
			packaging.setSpecificationDescription(rs.getString("SHDESC").trim());
			packaging.setOriginationUser(rs.getString("SHOUSR").trim());
			packaging.setApprovedByUser(rs.getString("SHAUSR").trim());
			packaging.setItemNumber(rs.getString("SHITNO").trim());
			packaging.setItemDescription(rs.getString("IDESC").trim());
			packaging.setProductionStatus(rs.getString("SHPDST").trim());
			packaging.setRevisionReasonText(rs.getString("SHRTXT").trim());
			packaging.setReferenceSpecNumber(rs.getString("SHRSNO").trim());
			packaging.setReferenceSpecRevDate(rs.getString("SHRSDT").trim());
			packaging.setReferenceSpecRevTime(rs.getString("SHRSTM").trim());
			packaging.setFormulaNumber(rs.getString("SHFONO").trim());
			packaging.setFormulaName(rs.getString("FNAME").trim());
		    packaging.setFormulaDescription(rs.getString("FDESC").trim());
			packaging.setFormulaRevisionDate(rs.getString("FDATE").trim());
			packaging.setFormulaRevisionTime(rs.getString("FTIME").trim());
			packaging.setCustomerNumber(rs.getString("SHCUNO").trim());
			packaging.setCustomerName(rs.getString("SHCUNM").trim());
			packaging.setCustomerCode(rs.getString("SHCUCD").trim());
			packaging.setUpdatedDate(rs.getString("SHUDTE").trim());
			packaging.setUpdatedTime(rs.getString("SHUTME").trim());
			packaging.setUpdatedUser(rs.getString("SHUUSR").trim());
			packaging.setCreatedDate(rs.getString("SHCDTE").trim());
			packaging.setCreatedTime(rs.getString("SHCTME").trim());
			packaging.setCreatedUser(rs.getString("SHCUSR").trim());
		//	packaging.setSupercededDate(rs.getString("SHSDTE").trim());
		//	packaging.setSupercededTime(rs.getString("SHSTME").trim());
			packaging.setTestBrix(rs.getString("SHTBRX").trim());
			
			if ((!rs.getString("SHCUNO").trim().equals(""))	&&
				 (rs.getString("SHCUCD").trim().equals(categoryCodes.get(11))))
			{
				packaging.setCustomerName(rs.getString("M3CUNM").trim());
			}
			if ((!rs.getString("SHCUNO").trim().equals(""))	&&
				 (rs.getString("SHCUCD").trim().equals(categoryCodes.get(12))))
			{
				packaging.setCustomerName(rs.getString("SACUNM").trim());
			}
			packaging.setKosherStatus(rs.getString("SHKSTS").trim());
			packaging.setKosherStatusDescription(rs.getString("KSTEXT").trim());
			packaging.setKosherSymbol(rs.getString("KITEXT").trim());
			packaging.setKosherSymbolRequired(rs.getString("SHKSYM").trim());
			packaging.setInlineSockRequired(rs.getString("SHINSK").trim());
			packaging.setInlineSockDescription(rs.getString("ISTEXT").trim());
			packaging.setCipType(rs.getString("SHCIPT").trim());
			packaging.setCipTypeDescription(rs.getString("CTTEXT").trim());
			packaging.setCutSizeCode(rs.getString("SHCUTZ").trim());
			packaging.setCutSizeDescription(rs.getString("CTSZTEXT").trim());
			packaging.setCutSizeCode2(rs.getString("SHCUT2").trim());
			packaging.setCutSizeDescription2(rs.getString("CTS2TEXT").trim());
			packaging.setScreenSizeCode(rs.getString("SHSRNZ").trim());
			packaging.setScreenSizeDescription(rs.getString("SCSZTEXT").trim());
			packaging.setForeignMaterialsDetectionCode(rs.getString("SHFMAT").trim());
			packaging.setForeignMaterialsDetectionDescription(rs.getString("FMDTEXT").trim());
			packaging.setStorageRecommendation(rs.getString("SHSREC").trim());
			packaging.setStorageRecommendationDescription(rs.getString("CNTEXT").trim());	
			packaging.setM3StorageRecommendation(rs.getString("SRTX40").trim());
			
			try {
				Vector<String> inValues = new Vector<String>();
				inValues.addElement(packaging.getItemNumber());
				Vector listShelfLife = ServiceItem.findShelfLife(inValues);
				if (listShelfLife.size() == 0)
					packaging.setShelfLife("0");
				if (listShelfLife.size() == 1)
					packaging.setShelfLife(((ItemWarehouse) listShelfLife.elementAt(0)).getDaysShelfLife());
				if (listShelfLife.size() > 1)
					packaging.setShelfLife("MULTIPLE");
			} catch(Exception e){}
			// Container, Label
			packaging.setContainerTamperSeal(rs.getString("SHCTSL").trim());
			packaging.setContainerCodeLocation(rs.getString("SHCCDL").trim());
			packaging.setContainerCodeLocationDescription(rs.getString("CLTEXT").trim());
			packaging.setContainerCodeFontSize(rs.getString("SHCDFS").trim());
			packaging.setContainerUPCNumber(rs.getString("LBLUPC").trim());	
			// Carton
			packaging.setCartonCodeLocation(rs.getString("SHCCTL").trim());
			packaging.setCartonCodeLocationDescription(rs.getString("CTNTEXT").trim());
			packaging.setCartonCodeFontSize(rs.getString("SHCTFS").trim());
			// Unit / Case
		    packaging.setUnitWeight(rs.getString("IGRWE").trim());				
			packaging.setUnitLength(rs.getString("ILENGTH").trim());
			packaging.setUnitWidth(rs.getString("IWIDTH").trim());
			packaging.setUnitHeight(rs.getString("IHEIGHT").trim());
			packaging.setUnitCube(rs.getString("IVOL3").trim());
			packaging.setUnitUPCNumber(rs.getString("UNITUPC").trim());
			packaging.setUnitCodeFontSize(rs.getString("SHCSFS").trim());
			packaging.setUnitShowBarCode(rs.getString("SHCSBC").trim());
			// Pallet Information
			packaging.setM3UnitsPerPallet(rs.getString("M3UNITPL"));//******* Alternate UOM - PL
			packaging.setM3UnitsPerLayer(rs.getString("M3UNITTIE"));//******* Alternate UOM - TIE
			BigDecimal layersPer = new BigDecimal(0);
			if (!packaging.getM3UnitsPerPallet().trim().equals("") && !packaging.getM3UnitsPerPallet().trim().equals("0") &&
			    !packaging.getM3UnitsPerLayer().trim().equals("") && !packaging.getM3UnitsPerLayer().trim().equals("0")) 
			{
				BigDecimal perPallet = new BigDecimal(packaging.getM3UnitsPerPallet().trim());
				BigDecimal perLayer  = new BigDecimal(packaging.getM3UnitsPerLayer().trim());
				if (perPallet.compareTo(new BigDecimal("0")) != 0 &&
					perLayer.compareTo(new BigDecimal("0")) != 0)
			      layersPer = perPallet.divide(perLayer);
			}				
			packaging.setM3LayersPerPallet(layersPer.toString().trim());
			packaging.setUnitsPerPallet(rs.getString("SHUNTP"));//******* If want override of M3
			packaging.setUnitsPerLayer(rs.getString("SHUNTL"));//******* if want override of M3
			layersPer = new BigDecimal(0);
			if (!packaging.getUnitsPerPallet().trim().equals("") && !packaging.getUnitsPerPallet().trim().equals("0") &&
			    !packaging.getUnitsPerLayer().trim().equals("") && !packaging.getUnitsPerLayer().trim().equals("0")) 
			{
				BigDecimal perPallet = new BigDecimal(packaging.getUnitsPerPallet().trim());
				BigDecimal perLayer  = new BigDecimal(packaging.getUnitsPerLayer().trim());
				if (perPallet.compareTo(new BigDecimal("0")) != 0 &&
					perLayer.compareTo(new BigDecimal("0")) != 0)
			      layersPer = perPallet.divide(perLayer);
			}				
			packaging.setLayersPerPallet(layersPer.toString().trim());
			packaging.setPalletStacking(rs.getString("IFRAG").trim());
			packaging.setPalletHeight(""); // unit height * layersPerPallet + palletframeheight
			packaging.setPalletGTINNumber(rs.getString("PALLETGTIN").trim());
			packaging.setPalletLabelType(rs.getString("SHPLBT").trim());
			packaging.setPalletLabelTypeDescription(rs.getString("LTTEXT").trim());				
			packaging.setPalletLabelLocation(rs.getString("SHPLBL").trim());
			packaging.setPalletLabelLocationDescription(rs.getString("PLTEXT").trim());

				// Will calculate the Pallet Height on the screen
			BigDecimal palletHeight = new BigDecimal(0);
			BigDecimal palletFrame  = new BigDecimal(5.75);				// Standard pallet frame height
			packaging.setPalletHeight("0"); // put in as Default
			if (packaging.getUnitHeight().equals(""))
			{
				BigDecimal unitHeight   = new BigDecimal(packaging.getUnitHeight().trim());
			    palletHeight = (unitHeight.multiply(layersPer)).add(palletFrame);					
			    packaging.setPalletHeight(palletHeight.toString().trim());
			}
			packaging.setStretchWrapRequired(rs.getString("SHWREQ").trim());
			packaging.setStretchWrapType(rs.getString("SHWTYP").trim());
			packaging.setStretchWrapTypeDescription(rs.getString("TTTEXT").trim());
			packaging.setStretchWrapWidth(rs.getString("SHWWDH").trim());
			packaging.setStretchWrapWidthUOM(rs.getString("SHWUOM").trim());
			packaging.setStretchWrapWidthUOMDescription(rs.getString("UOM1TXT").trim());
			packaging.setStretchWrapGauge(rs.getString("SHWGAU").trim());
			packaging.setStretchWrapGaugeUOM(rs.getString("SHGUOM").trim());
			packaging.setStretchWrapGaugeUOMDescription(rs.getString("UOM2TXT").trim());
			
			packaging.setShrinkWrapRequired(rs.getString("SHKREQ").trim());
			packaging.setShrinkWrapType(rs.getString("SHKTYP").trim());
			packaging.setShrinkWrapTypeDescription(rs.getString("KTTEXT").trim());
			packaging.setShrinkWrapWidth(rs.getString("SHKWDH").trim());
			packaging.setShrinkWrapWidthUOM(rs.getString("SHKUOM").trim());
			packaging.setShrinkWrapWidthUOMDescription(rs.getString("UOM3TXT").trim());
			packaging.setShrinkWrapThickness(rs.getString("SHKTHK").trim());
			packaging.setShrinkWrapThicknessUOM(rs.getString("SHTUOM").trim());
			packaging.setShrinkWrapThicknessUOMDescription(rs.getString("UOM4TXT").trim());
			
			packaging.setSlipSheetRequired(rs.getString("SHSREQ").trim());
			packaging.setSlipSheetBottom(rs.getString("SHSLOC").trim());
			packaging.setSlipSheetLayer1(rs.getString("SHSLY1").trim());
			packaging.setSlipSheetLayer2(rs.getString("SHSLY2").trim());
			packaging.setSlipSheetLayer3(rs.getString("SHSLY3").trim());
			packaging.setSlipSheetLayer4(rs.getString("SHSLY4").trim());
			packaging.setSlipSheetLayer5(rs.getString("SHSLY5").trim());
			packaging.setSlipSheetLayer6(rs.getString("SHSLY6").trim());
			packaging.setSlipSheetLayer7(rs.getString("SHSLY7").trim());
			packaging.setSlipSheetLayer8(rs.getString("SHSLY8").trim());
			packaging.setSlipSheetLayer9(rs.getString("SHSLY9").trim());
			packaging.setSlipSheetLayer10(rs.getString("SHSLY10").trim());
			packaging.setSlipSheetTop(rs.getString("SHSTOP").trim());
			packaging.setPalletRequirement(rs.getString("SHPREQ").trim());
			packaging.setPalletRequirementDescription(rs.getString("PRTEXT").trim());
			
			packaging.setCountryOfOrigin(rs.getString("SHCORG").trim());
			packaging.setReconstitutionRatio(rs.getString("SHRCON").trim());
			packaging.setShelfLifeNotValid(rs.getString("SHSLCB").trim());
			
			Vector<QaSpecificationPackaging> specificationPackaging = new Vector<QaSpecificationPackaging>();
			specificationPackaging.addElement(packaging);
			returnValue = specificationPackaging;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaSpecificationPackaging from the result set. " + e) ;
	}
//  *************************************************************************************
//	SPECIFICATION (Revision Reason)
//  *************************************************************************************		
	try {		
	
		if (requestType.trim().equals("QaSpecificationRevisionReason")) 
		{
			QaSpecification specification = new QaSpecification();	  
			
			specification.setCompanyNumber(rs.getString("SHCONO").trim());
			specification.setDivisionNumber(rs.getString("SHDIVI").trim());
			specification.setSpecificationNumber(rs.getString("SHSPNO").trim());
			specification.setSpecificationName(rs.getString("SHNAME").trim());
			specification.setSpecificationDescription(rs.getString("SHDESC").trim());
			specification.setItemNumber(rs.getString("SHITNO").trim());
			specification.setTypeCode(rs.getString("SHTYPE").trim());
			specification.setTypeDescription(rs.getString("TPTEXT").trim());
			specification.setRevisionDate(rs.getString("SHRDTE").trim());
			specification.setRevisionTime(rs.getString("SHRTME").trim());
			specification.setStatusCode(rs.getString("SHSTAT").trim());
			specification.setStatusDescription(rs.getString("STTEXT").trim());
			specification.setRevisionReasonText(rs.getString("SHRTXT").trim());
			
			
			Vector<QaSpecification> specificationReason = new Vector<QaSpecification>();
			specificationReason.addElement(specification);
			returnValue = specificationReason;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaSpecificationRevisionReason from the result set. " + e) ;
	}		
//  *************************************************************************************
//	Analytical Tests & Process Parameters -- used for both Specifications and Formula's
//  *************************************************************************************		
	try {
		
		if (requestType.trim().equals("QaTestParameters") ||
			requestType.trim().equals("QaTestParametersFormula"))
		{
			QaTestParameters tests = new QaTestParameters();
			
			tests.setCompanyNumber(rs.getString("TPCONO").trim());
			tests.setDivisionNumber(rs.getString("TPDIVI").trim());
			tests.setApplicationType(rs.getString("TPATYP").trim());
			tests.setIdNumber(rs.getString("TPSPNO").trim());
			tests.setRevisionDate(rs.getString("TPRDTE").trim());
			tests.setRevisionTime(rs.getString("TPRTME").trim());
			
			tests.setIdentificationCode(rs.getString("TPCODE").trim());		
			tests.setAttributeType(rs.getString("ATATVC").trim());
			tests.setAttributeGroup(rs.getString("TPATGR").trim());
			tests.setAttributeIdentity(rs.getString("TPATTR").trim());
			tests.setAttributeDescription(rs.getString("ATTEXT").trim()); // not main file
			tests.setDecimalPlaces(rs.getString("ATDECI").trim()); // not main file
			tests.setSequenceNumber(rs.getString("TPSEQ#").trim());	
			tests.setUnitOfMeasure(rs.getString("CTTEXT").trim());
			//if (tests.getAttributeIdentity().trim().equals("FLVR"))
			//{
			   // 10/31/13 TWalton -- 
			   // Show only Alpha... because then can save BLANK, vs. a number
//wth allow blanks
//wth		if (requestType.trim().equals("QaTestParametersFormula")) // only needed until move Formula to Alpha
//wth		{
//wth			tests.setTargetValue(rs.getString("TPTARG").trim());
//wth			tests.setMinimumStandard(rs.getString("TPMNST").trim());
//wth			tests.setMaximumStandard(rs.getString("TPMXST").trim());
//wth		}else{
				tests.setTargetValue(rs.getString("TPTRGA").trim());
				tests.setMinimumStandard(rs.getString("TPMINA").trim());
				tests.setMaximumStandard(rs.getString("TPMAXA").trim());	
//wth		}
			
			//}else{
				
				//tests.setTargetValue(rs.getString("TPTARG").trim());
				//tests.setMinimumStandard(rs.getString("TPMNST").trim());
				//tests.setMaximumStandard(rs.getString("TPMXST").trim());
			tests.setTestedAtValue(rs.getString("TPTVAL").trim());
			tests.setTestedAtUnitOfMeasure(rs.getString("TPTUOM").trim());
			//}
			tests.setDefaultOnCOA(rs.getString("TPPCOA").trim());
			tests.setMethodNumber(rs.getString("TPMENO").trim());
			
			tests.setMethodName(rs.getString("MHNAME") == null ? "" : rs.getString("MHNAME").trim());
			
			// going to use the ACTIVE version of the Method..
			//testProcess.setMethodRevDate(rs.getString("TPMEDT").trim());
			//testProcess.setMethodRevTime(rs.getString("TPMETM").trim());
			try {
				tests.setMethodRevDate(rs.getString("MERDTE").trim());
				tests.setMethodRevTime(rs.getString("MERTME").trim());
				tests.setMethodDescription(rs.getString("MEDESC").trim());
			} catch(Exception e){}
			
			tests.setUpdatedDate(rs.getString("TPUDTE").trim());
			tests.setUpdatedTime(rs.getString("TPUTME").trim());
			tests.setUpdatedUser(rs.getString("TPUUSR").trim());
			tests.setCreatedDate(rs.getString("TPCDTE").trim());
			tests.setCreatedTime(rs.getString("TPCTME").trim());
			tests.setCreatedUser(rs.getString("TPCUSR").trim());
			
			Vector<QaTestParameters> sendTest = new Vector<QaTestParameters>();
			sendTest.addElement(tests);
			returnValue = sendTest;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaTestProcess from the result set. " + e) ;
	}		
	
//  *************************************************************************************
//	FRUIT VARIETY INFORMATION
//  *************************************************************************************		
	try {
		
		if ((requestType.trim().equals("QaVarietyIncluded")) ||
			(requestType.trim().equals("QaVarietyExcluded")))
		{
			QaFruitVariety fruitVariety = new QaFruitVariety();
			
			fruitVariety.setCompanyNumber(rs.getString("FVCONO").trim());
			fruitVariety.setDivisionNumber(rs.getString("FVDIVI").trim());
			fruitVariety.setApplicationType(rs.getString("FVATYP").trim());
			fruitVariety.setIdNumber(rs.getString("FVSPNO").trim());
			fruitVariety.setRevisionDate(rs.getString("FVRDTE").trim());
			fruitVariety.setRevisionTime(rs.getString("FVRTME").trim());			
			fruitVariety.setAttributeCropModel(rs.getString("FVATMO").trim());
			if (rs.getString("ADTEXT") != null)
			   fruitVariety.setAttributeCropModelDescription(rs.getString("ADTEXT").trim());
			fruitVariety.setAttributeIdentity(rs.getString("FVATID").trim());			
			fruitVariety.setFruitVarietyValue(rs.getString("FVVALU").trim());
			fruitVariety.setFruitVarietyDescription(rs.getString("PFTEXT").trim());
			fruitVariety.setIncludeExclude(rs.getString("FVIECD").trim());	
			fruitVariety.setIncludePercentage(rs.getString("FVIPCA").trim());
			fruitVariety.setIncludeMinimumPercent(rs.getString("FVMIIA").trim());
			fruitVariety.setIncludeMaximumPercent(rs.getString("FVMXIA").trim());			
			fruitVariety.setUpdatedDate(rs.getString("FVUDTE").trim());
			fruitVariety.setUpdatedTime(rs.getString("FVUTME").trim());
			fruitVariety.setUpdatedUser(rs.getString("FVUUSR").trim());
			fruitVariety.setCreatedDate(rs.getString("FVCDTE").trim());
			fruitVariety.setCreatedTime(rs.getString("FVCTME").trim());
			fruitVariety.setCreatedUser(rs.getString("FVCUSR").trim());
			
			Vector<QaFruitVariety> returnFruitVariety = new Vector<QaFruitVariety>();
			returnFruitVariety.addElement(fruitVariety);
			returnValue = returnFruitVariety;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaFruitVariety from the result set. " + e) ;
	}		
//  *************************************************************************************
//	FORMULA (Header and Detail)
//  *************************************************************************************			
	try { 
		
		if (requestType.trim().equals("QaFormulaHeader")) 
		{
			QaFormula formula = new QaFormula();
		  
			formula.setCompanyNumber(rs.getString("FHCONO").trim());
			formula.setDivisionNumber(rs.getString("FHDIVI").trim());
			formula.setStatusCode(rs.getString("FHSTAT").trim());
			formula.setStatusDescription(rs.getString("STTEXT").trim());
			formula.setTypeCode(rs.getString("FHTYPE").trim());
			formula.setTypeDescription(rs.getString("TPTEXT").trim());
			formula.setOriginationUser(rs.getString("FHOUSR").trim());
			formula.setGroupingCode(rs.getString("FHGRUP").trim());
			formula.setGroupingDescription(rs.getString("GPTEXT").trim());
			formula.setScopeCode(rs.getString("FHSCOP").trim());
			formula.setScopeDescription(rs.getString("SOTEXT").trim()); 
			formula.setFormulaNumber(rs.getString("FHFONO").trim());
			formula.setRevisionDate(rs.getString("FHRDTE").trim());
			formula.setRevisionTime(rs.getString("FHRTME").trim());
			formula.setFormulaName(rs.getString("FHNAME").trim());
			formula.setFormulaDescription(rs.getString("FHDESC").trim());
			formula.setLineTankItem(rs.getString("FHLTNO").trim());
			formula.setBatchQuantity(rs.getString("FHQTTY").trim());
			formula.setBatchUnitOfMeasure(rs.getString("FHUNMS").trim());
			formula.setTargetBrix(rs.getString("FHBRIX").trim());
			formula.setCustomerNumber(rs.getString("FHCUNO").trim());
			formula.setCustomerName(rs.getString("FHCUNM").trim());
			formula.setCustomerCode(rs.getString("FHCUCD").trim());
			formula.setCustomerOrSupplierItemNumber(rs.getString("FHCUIT").trim());
			formula.setReferenceFormulaNumber(rs.getString("FHRFNO").trim());
			formula.setReferenceFormulaRevDate(rs.getString("FHRFDT").trim());
			formula.setReferenceFormulaRevTime(rs.getString("FHRFTM").trim());
			
			formula.setProductionStatus(rs.getString("FHPDST").trim());	
			formula.setRevisionReasonText(rs.getString("FHRTXT").trim());			
			formula.setUpdatedDate(rs.getString("FHUDTE").trim());
			formula.setUpdatedTime(rs.getString("FHUTME").trim());
			formula.setUpdatedUser(rs.getString("FHUUSR").trim());
			formula.setCreatedDate(rs.getString("FHCDTE").trim());
			formula.setCreatedTime(rs.getString("FHCTME").trim());
			formula.setCreatedUser(rs.getString("FHCUSR").trim());
//			try{
//				if (rs.getString("SuperDate") != null)
//					formula.setSupercededDate(rs.getString("SuperDate").trim());
//			}catch(Exception e){}
//			formula.setSupercededDate(rs.getString("FHSDTE").trim());
//			formula.setSupercededTime(rs.getString("FHSTME").trim());
			
			formula.setApprovedByUser(rs.getString("FHAUSR").trim());
			formula.setFruitOrigin(rs.getString("FHORIG").trim());
			formula.setBatchQuantityPreBlend(rs.getString("FHPQTY").trim());
			formula.setBatchUOMPreBlend(rs.getString("FHPUOM").trim());
			
			if ((!rs.getString("FHCUNO").trim().equals(""))	&&
				 (rs.getString("FHCUCD").trim().equals(categoryCodes.get(11))))
			{
				formula.setCustomerName(rs.getString("M3CUNM").trim());
			}
			if ((!rs.getString("FHCUNO").trim().equals(""))	&&
				 (rs.getString("FHCUCD").trim().equals(categoryCodes.get(12))))
			{
				formula.setCustomerName(rs.getString("SACUNM").trim());
			}
			if (rs.getString("ITEMDESC") != null && !rs.getString("ITEMDESC").trim().equals(""))
		      formula.setLineTankItemDescription(rs.getString("ITEMDESC").trim());
			
			Vector<QaFormula> formulaHeader = new Vector<QaFormula>();
			formulaHeader.addElement(formula);
			returnValue = formulaHeader;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaFormulaHeader from the result set. " + e) ;
	}
	
	try {
		
		if (requestType.trim().equals("QaFormulaPreBlend") ||
			requestType.trim().equals("QaFormulaProduction") ||
			requestType.trim().equals("QaFormulaPreBlendSauce")) 
		{
			QaFormulaDetail formula = new QaFormulaDetail();
		  
			formula.setCompanyNumber(rs.getString("FHCONO").trim());
			formula.setDivisionNumber(rs.getString("FHDIVI").trim());
			formula.setStatusCode(rs.getString("FHSTAT").trim());
			formula.setTypeCode(rs.getString("FHTYPE").trim());
			formula.setFormulaNumber(rs.getString("FHFONO").trim());
			formula.setRevisionDate(rs.getString("FHRDTE").trim());
			formula.setRevisionTime(rs.getString("FHRTME").trim());
			if (rs.getString("ITEMDESC") != null && !rs.getString("ITEMDESC").trim().equals(""))
			   formula.setLineTankItemDescription(rs.getString("ITEMDESC").trim());
			
			formula.setIdentificationCode(rs.getString("FDCODE").trim());
			formula.setSequenceNumber(rs.getString("FDSEQ#").trim());
			
			formula.setItemNumber1(rs.getString("FDITNO").trim());
			formula.setItemDescription1(rs.getString("FDITDS").trim());
			formula.setItemQuantity1(rs.getString("FDQTY1").trim());
			formula.setItemUnitOfMeasure1(rs.getString("FDUOM1").trim());
			
			if (!rs.getString("D1ITDS").trim().equals(""))				
			{
				formula.setItemDescription1(rs.getString("D1ITDS").trim());
//				// 11/2/10 TWalton added note if the UOM is Blank, then use the Item UOM
				if (formula.getItemUnitOfMeasure1().trim().equals("") &&
					!rs.getString("D1UNMS").trim().equals(""))				
				{
					formula.setItemUnitOfMeasure1(rs.getString("D1UNMS").trim());
				}
			}
			
			formula.setSupplierNumber(rs.getString("FDSUNO").trim());
			formula.setSupplierName(rs.getString("FDSUNM").trim());
			
			if (!rs.getString("FDSUNO").trim().equals("") &&
				!rs.getString("CIDSUNM").trim().equals(""))
			{
				formula.setSupplierName(rs.getString("CIDSUNM").trim());
			}
			formula.setReferenceSpecNumber(rs.getString("FDRSNO").trim());			
			
			if (requestType.trim().equals("QaFormulaPreBlendSauce"))
			{
				formula.setSauceTargetBrix(rs.getString("FDBRIX").trim());
				formula.setSauceBatchQuantity(rs.getString("FDQTTY").trim());
				formula.setSauceBatchUOM(rs.getString("FDBUOM").trim());
				formula.setItemNumber2(rs.getString("FDITNO2").trim());
				formula.setItemDescription2(rs.getString("FDITDS2").trim());
				formula.setItemQuantity2(rs.getString("FDQTY2").trim());
				formula.setItemUnitOfMeasure2(rs.getString("FDUOM2").trim());
				formula.setItemNumber3(rs.getString("FDITNO3").trim());
				formula.setItemDescription3(rs.getString("FDITDS3").trim());
				formula.setItemQuantity3(rs.getString("FDQTY3").trim());
				formula.setItemUnitOfMeasure3(rs.getString("FDUOM3").trim());
				if (!rs.getString("D2ITDS").trim().equals(""))				
				{
					formula.setItemDescription2(rs.getString("D2ITDS").trim());
//					// 11/2/10 TWalton added note if the UOM is Blank, then use the Item UOM
					if (formula.getItemUnitOfMeasure2().trim().equals("") &&
						!rs.getString("D2UNMS").trim().equals(""))				
					{
						formula.setItemUnitOfMeasure2(rs.getString("D2UNMS").trim());
					}
				}
				if (!rs.getString("D3ITDS").trim().equals(""))				
				{
					formula.setItemDescription3(rs.getString("D3ITDS").trim());
//					// 11/2/10 TWalton added note if the UOM is Blank, then use the Item UOM
					if (formula.getItemUnitOfMeasure3().trim().equals("") &&
						!rs.getString("D3UNMS").trim().equals(""))				
					{
						formula.setItemUnitOfMeasure3(rs.getString("D3UNMS").trim());
					}
				}
			}
//			if (!rs.getString("FDSUNO").trim().equals(""))				
//			{
//				formula.setSupplierName(rs.getString("DTSUNM").trim());
//			}
//						  
			Vector<QaFormulaDetail> formulaDetail = new Vector<QaFormulaDetail>();
			formulaDetail.addElement(formula);
			returnValue = formulaDetail;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaFormulaDetail/Header from the result set. " + e) ;
	}
//  *************************************************************************************
//	FORMULA (Revision Reason)
//  *************************************************************************************		
	try {		
	
		if (requestType.trim().equals("QaFormulaRevisionReason")) 
		{
			QaFormula formula = new QaFormula();		  
			
			formula.setCompanyNumber(rs.getString("FHCONO").trim());
			formula.setDivisionNumber(rs.getString("FHDIVI").trim());
			formula.setFormulaNumber(rs.getString("FHFONO").trim());
			formula.setRevisionDate(rs.getString("FHRDTE").trim());
			formula.setRevisionTime(rs.getString("FHRTME").trim());
			formula.setStatusCode(rs.getString("FHSTAT").trim());
			formula.setStatusDescription(rs.getString("STTEXT").trim());
			formula.setRevisionReasonText(rs.getString("FHRTXT").trim());
			formula.setFormulaDescription(rs.getString("FHDESC").trim());
			
			Vector<QaFormula> formulaReason = new Vector<QaFormula>();
			formulaReason.addElement(formula);
			returnValue = formulaReason;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaFormulaRevisionReason from the result set. " + e) ;
	}		
//  *************************************************************************************
//	METHOD (Header)
//  *************************************************************************************	
	try { 
		
		if (requestType.trim().equals("QaMethodHeader")) 
		{
			QaMethod method = new QaMethod();
		  
			method.setCompanyNumber(rs.getString("MHCONO").trim());
			method.setDivisionNumber(rs.getString("MHDIVI").trim());
			method.setStatusCode(rs.getString("MHSTAT").trim());
			method.setStatusDescription(rs.getString("STTEXT").trim());
			method.setTypeCode(rs.getString("MHTYPE").trim());
			method.setTypeDescription(rs.getString("TPTEXT").trim());
			method.setOriginationUser(rs.getString("MHOUSR").trim());
			method.setGroupingCode(rs.getString("MHGRUP").trim());
			method.setGroupingDescription(rs.getString("GPTEXT").trim());
			method.setScopeCode(rs.getString("MHSCOP").trim());
			method.setScopeDescription(rs.getString("SPTEXT").trim());
			method.setMethodNumber(rs.getString("MHMENO").trim());
			method.setRevisionDate(rs.getString("MHRDTE").trim());
			method.setRevisionTime(rs.getString("MHRTME").trim());
			method.setMethodName(rs.getString("MHNAME").trim());
			method.setMethodDescription(rs.getString("MHDESC").trim());
			method.setRevisionReasonText(rs.getString("MHRTXT").trim());
			method.setUpdatedDate(rs.getString("MHUDTE").trim());
			method.setUpdatedTime(rs.getString("MHUTME").trim());
			method.setUpdatedUser(rs.getString("MHUUSR").trim());
			method.setCreatedDate(rs.getString("MHCDTE").trim());
			method.setCreatedTime(rs.getString("MHCTME").trim());
			method.setCreatedUser(rs.getString("MHCUSR").trim());
//			try{
//				if (rs.getString("SuperDate") != null)
//					method.setSupercededDate(rs.getString("SuperDate").trim());
//			}catch(Exception e){}
//			method.setSupercededDate(rs.getString("MHSDTE").trim());
//			method.setSupercededTime(rs.getString("MHSTME").trim());
			method.setApprovedByUser(rs.getString("MHAUSR").trim());
		  
			Vector<QaMethod> methodHeader = new Vector<QaMethod>();
			methodHeader.addElement(method);
			returnValue = methodHeader;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaMethodHeader from the result set. " + e) ;
	}
//  *************************************************************************************
//	METHOD (Revision Reason)
//  *************************************************************************************		
	try {		
	
		if (requestType.trim().equals("QaMethodRevisionReason")) 
		{
			QaMethod method = new QaMethod();		  
			
			method.setCompanyNumber(rs.getString("MHCONO").trim());
			method.setDivisionNumber(rs.getString("MHDIVI").trim());
			method.setMethodNumber(rs.getString("MHMENO").trim());
			method.setRevisionDate(rs.getString("MHRDTE").trim());
			method.setRevisionTime(rs.getString("MHRTME").trim());
			method.setStatusCode(rs.getString("MHSTAT").trim());
			method.setStatusDescription(rs.getString("STTEXT").trim());
			method.setRevisionReasonText(rs.getString("MHRTXT").trim());
			method.setMethodDescription(rs.getString("MHDESC").trim());
			method.setApprovedByUser(rs.getString("MHAUSR").trim());
			
			Vector<QaMethod> methodReason = new Vector<QaMethod>();
			methodReason.addElement(method);
			returnValue = methodReason;
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading QaMethodRevisionReason from the result set. " + e) ;
	}		
	
//  *************************************************************************************					
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceQuality.");
		throwError.append("loadFields(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Create a method per request.
 */

public static BeanQuality insertMethod(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = insertMethod(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("insertMethod(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Create a method per request. (Header)
 */

private static BeanQuality insertMethod(Vector inValues, 
						    	        Connection conn)
throws Exception
{
	StringBuffer 		throwError  = new StringBuffer();
	String       		sql         = new String();
	PreparedStatement   insertIt    = null;
	BeanQuality  		returnValue = new BeanQuality();
	
	try {
		
		try {			
			
			sql = buildSqlStatement("insertMethodHeader", inValues);
			// 10/11/11 TWalton - Change to a Prepared Statement
			UpdMethod methodHeader = (UpdMethod) inValues.elementAt(0);
			//insertIt = conn.createStatement();
			insertIt = conn.prepareStatement(sql);
			insertIt.setInt(1, new Integer(methodHeader.getCompany().trim()).intValue());
			insertIt.setString(2, methodHeader.getDivision().trim());
			insertIt.setString(3, methodHeader.getStatus().trim());
			insertIt.setString(4, methodHeader.getRecordType().trim());
			insertIt.setString(5, methodHeader.getOriginationUser().trim());
			insertIt.setString(6, methodHeader.getGroupCode().trim());
			insertIt.setString(7, methodHeader.getScopeCode().trim());
			insertIt.setInt(8, new Integer(methodHeader.getMethodNumber().trim()).intValue());
			insertIt.setInt(9, new Integer(methodHeader.getRevisionDate().trim()).intValue());
			insertIt.setInt(10, new Integer(methodHeader.getRevisionTime().trim()).intValue());
			insertIt.setString(11, methodHeader.getMethodName().trim());
			insertIt.setString(12, methodHeader.getMethodDescription().trim());
			insertIt.setString(13, methodHeader.getRevisionReason().trim());
			insertIt.setInt(14, new Integer(methodHeader.getUpdateDate().trim()).intValue());
			insertIt.setInt(15, new Integer(methodHeader.getUpdateTime().trim()).intValue());
			insertIt.setString(16, methodHeader.getUpdateUser().trim());
			insertIt.setInt(17, new Integer(methodHeader.getCreationDate().trim()).intValue());
			insertIt.setInt(18, new Integer(methodHeader.getCreationTime().trim()).intValue());
			insertIt.setString(19, methodHeader.getCreationUser().trim());
			insertIt.setInt(20, 0);
			insertIt.setInt(21, 0);
			insertIt.setString(22, methodHeader.getApprovedByUser().trim());
			
			insertIt.executeUpdate();
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (insertIt != null)
	   {
		   try {
			  insertIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("insertMethod(");
		throwError.append("Vector, conn). ");		
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());		
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a header and detail method per request.
 */

public static BeanQuality findMethod(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findMethod(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findMethod(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return a header and detail method per request.
 */

private static BeanQuality findMethod(Vector inValues, 
								      Connection conn)
throws Exception
{
	StringBuffer           throwError        = new StringBuffer();
	ResultSet              rs                = null;
	Statement              listThem          = null;
	QaMethod               method            = new QaMethod();
	DtlMethod            methodRequest     = new DtlMethod();
	Vector<DtlMethod>    methodRequestList = new Vector<DtlMethod>();
	BeanQuality            returnValue       = new BeanQuality();
	
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlStatement("findMethod", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {				
				 
				 DtlMethod inRequest = (DtlMethod) inValues.elementAt(0);
				 returnValue.setEnvironment(inRequest.getEnvironment());
			
				 while (rs.next())
				 {			 		
					 method = returnValue.getMethod();
			 	
					 if (method.getMethodNumber().toString().trim().equals(""))
					 {
						 Vector   oneMethod    = loadFields("QaMethodHeader", rs);	
						 QaMethod methodHeader = (QaMethod) oneMethod.elementAt(0);
						 returnValue.setMethod(methodHeader);
						 
						 methodRequest.setEnvironment(inRequest.getEnvironment());
						 methodRequest.setCompany(methodHeader.getCompanyNumber());
						 methodRequest.setDivision(methodHeader.getDivisionNumber());		
						 methodRequest.setMethodNumber(methodHeader.getMethodNumber());						 
						 methodRequest.setRevisionDate(methodHeader.getRevisionDate());
						 methodRequest.setRevisionTime(methodHeader.getRevisionTime());
						 methodRequestList.addElement(methodRequest);
					 }			 	
					 
				 }
				 
				 if ((!methodRequest.getMethodNumber().trim().equals("")) &&
					 (!methodRequest.getRevisionDate().trim().equals("")) &&
					 (!methodRequest.getRevisionTime().trim().equals("")))
				 {						 		
					 Vector methodRevisionReason = new Vector();
					 methodRevisionReason = findMethodRevisionReason(methodRequestList);
					 returnValue.setRevReasonMethod(methodRevisionReason);
				 }
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findMethod(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a list of methods per request.
 */

public static BeanQuality listMethod(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {		
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listMethod(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listMethod(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a list of methods per request.
 */

private static BeanQuality listMethod(Vector inValues, 
								      Connection conn)
throws Exception
{
	StringBuffer     throwError  = new StringBuffer();
	ResultSet        rs          = null;
	Statement        listThem    = null;
	String			 template    = "";		
	Vector<QaMethod> methodList  = new Vector<QaMethod>();
	BeanQuality      returnValue = new BeanQuality();
	
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlStatement("listMethod", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 InqMethod inRequest = (InqMethod) inValues.elementAt(0);
				 returnValue.setEnvironment(inRequest.getEnvironment().trim());
				 template = inRequest.getInqTemplate().trim();
				 
				 if (template.trim().equals("QaMethodHeader"))
				 {				 
					 QaMethod lastMethod = new QaMethod();
				 
					 while (rs.next())
					 {					
						 Vector oneMethod    = loadFields("QaMethodHeader", rs);
						 QaMethod thisMethod = new QaMethod();
						 thisMethod = (QaMethod) oneMethod.lastElement();
					 
						 if ((!thisMethod.getMethodNumber().trim().equals(lastMethod.getMethodNumber().trim())) ||
						     (!thisMethod.getRevisionDate().trim().equals(lastMethod.getRevisionDate().trim())) ||
						     (!thisMethod.getRevisionTime().trim().equals(lastMethod.getRevisionTime().trim())))	
						 {	
							 QaMethod method  = (QaMethod) oneMethod.elementAt(0);
							 methodList.addElement(method);
							 returnValue.setListMethod(methodList);
					 
							 lastMethod = thisMethod;		// Retain last method processed
						 }
					 }
				 }				 
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listMethod(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Delete a method per request.
 */

public static BeanQuality deleteMethod(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = deleteMethod(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("deleteMethod(");
		throwError.append("Vector). ");		
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Delete a method per request. (Header and Detail)
 */

private static BeanQuality deleteMethod(Vector inValues, 
							 	        Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	Statement    updateIt    = null;
	BeanQuality  returnValue = new BeanQuality();
	
	try {
		
		try {	
			// 11/22/10 TWalton -- not using the detail file/section
//			sql = buildSqlStatement("deleteMethodDetail", inValues);
//			updateIt.executeUpdate(sql);
			
			sql = buildSqlStatement("deleteMethodHeader", inValues);
			updateIt = conn.createStatement();
			updateIt.executeUpdate(sql);	
		   
			UpdMethod deleteRequest = (UpdMethod) inValues.elementAt(0);
			QaMethod method = new QaMethod();		 			
			method.setMethodNumber(deleteRequest.getMethodNumber());
			method.setCompanyNumber(deleteRequest.getCompany());
			method.setDivisionNumber(deleteRequest.getDivision());
 			method.setRevisionDate(deleteRequest.getRevisionDate().trim());
 			method.setRevisionTime(deleteRequest.getRevisionTime().trim());
 			returnValue.setEnvironment(deleteRequest.getEnvironment());
 			returnValue.setMethod(method);
		    
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("deleteMethod(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());			
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a list of specifications per request.
 */

public static BeanQuality listSpecification(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {		
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listSpecification(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecification(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return a list of specifications per request.
 */

private static BeanQuality listSpecification(Vector inValues, 
							       	         Connection conn)
throws Exception
{
	StringBuffer            throwError        = new StringBuffer();
	ResultSet               rs                = null;
	Statement               listThem          = null;
	String					template          = "";	
	Vector<QaSpecification> specificationList = new Vector<QaSpecification>();
	BeanQuality             returnValue       = new BeanQuality();
	
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlStatement("listSpecification", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 InqSpecification inRequest = (InqSpecification) inValues.elementAt(0);
				 returnValue.setEnvironment(inRequest.getEnvironment().trim());
				 template = inRequest.getInqTemplate().trim();
				 
				 if (template.trim().equals("QaSpecificationHeader"))
				 {				 
					 QaSpecification lastSpecification = new QaSpecification();
					 
					 while (rs.next())
					 {			 		
						 Vector oneSpecification           = loadFields(template, rs);
						 QaSpecification thisSpecification = new QaSpecification();
						 thisSpecification = (QaSpecification) oneSpecification.lastElement();
					 
						 if ((!thisSpecification.getSpecificationNumber().trim().equals(lastSpecification.getSpecificationNumber().trim())) ||
							 (!thisSpecification.getRevisionDate().trim().equals(lastSpecification.getRevisionDate().trim())) ||
							 (!thisSpecification.getRevisionTime().trim().equals(lastSpecification.getRevisionTime().trim())))	
						 {						 
							 QaSpecification specification  = (QaSpecification) oneSpecification.elementAt(0);
							 specificationList.addElement(specification);
							 returnValue.setListSpecification(specificationList);
						
							 lastSpecification = thisSpecification;		// Retain last specification processed
						 }
					 }				 
				 }
	// Not currently using Detail			 
//				 if (template.trim().equals("QaSpecificationDetail"))
//				 {				 
//					 while (rs.next())
//					 {			 		
//						 Vector oneSpecification              = loadFields(template, rs);						 				
//						 QaSpecificationPackaging specification  = (QaSpecificationPackaging) oneSpecification.elementAt(0);
//						 specificationList.addElement(specification);
//						 returnValue.setListSpecification(specificationList);					
//					 }				 
//				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecification(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Create a specification per request.
 */

public static BeanQuality insertSpecification(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
		
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = insertSpecification(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("insertSpecification(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Create a specification per request. (Header, Detail, Packaging)
 * 10/12/11 TWalton - change to use a prepared Statement (Deleted OLD Code) -- can see the code in the project TreeNet61 if needed
 * 11/17/11 TWalton - change to use one Spec File, instead of 3 (Delete Not needed Code) -- can see the code in the project TreeNet61 if needed
 */

private static BeanQuality insertSpecification(Vector inValues, 
							 	               Connection conn)
throws Exception
{
	StringBuffer 		throwError  = new StringBuffer();
	String       		sql         = new String();
	PreparedStatement   insertIt    = null;
	BeanQuality  		returnValue = new BeanQuality();
	
	try {
		
 			UpdSpecification specHeader = (UpdSpecification) inValues.elementAt(0);
 			
 			sql = buildSqlStatement("insertSpecificationHeader", inValues);
 			
 			insertIt = conn.prepareStatement(sql);
			insertIt.setInt(1, new Integer(specHeader.getCompany().trim()).intValue());
			insertIt.setString(2, specHeader.getDivision().trim());
			insertIt.setInt(3, new Integer(specHeader.getSpecNumber().trim()).intValue());
			insertIt.setInt(4, new Integer(specHeader.getRevisionDate().trim()).intValue());
			insertIt.setInt(5, new Integer(specHeader.getRevisionTime().trim()).intValue());
			insertIt.setString(6, specHeader.getSpecName().trim());
			insertIt.setString(7, specHeader.getStatus().trim());
			insertIt.setString(8, specHeader.getSpecType().trim());
			insertIt.setString(9, specHeader.getGroupCode().trim());
			insertIt.setString(10, specHeader.getScopeCode().trim());
			insertIt.setString(11, specHeader.getSpecDescription().trim());
			insertIt.setString(12, specHeader.getOriginationUser().trim());
			insertIt.setString(13, specHeader.getApprovedByUser().trim());
			insertIt.setString(14, specHeader.getItemNumber().trim());
			insertIt.setString(15, specHeader.getProductionStatus().trim());
			insertIt.setString(16, specHeader.getRevisionReason().trim());
			insertIt.setInt(17, new Integer(specHeader.getReferenceSpecNumber().trim()).intValue());
			insertIt.setInt(18, new Integer(specHeader.getReferenceSpecRevisionDate().trim()).intValue());
			insertIt.setInt(19, new Integer(specHeader.getReferenceSpecRevisionTime().trim()).intValue());
			insertIt.setInt(20, new Integer(specHeader.getFormulaNumber().trim()).intValue());
			insertIt.setInt(21, 0); // Formula Revision Date -- Currently going to pull the ACTIVE one
			insertIt.setInt(22, 0); // Formula Revision Time -- Currently going to pull the ACTIVE one
			insertIt.setString(23, specHeader.getCustomerNumber().trim());
			insertIt.setString(24, specHeader.getCustomerName().trim());
			insertIt.setString(25, specHeader.getCustomerCode().trim());
			insertIt.setString(26, specHeader.getKosherStatusCode().trim());
			insertIt.setString(27, specHeader.getKosherSymbolRequired().trim());
			insertIt.setString(28, specHeader.getCutSize().trim());
			insertIt.setString(29, specHeader.getScreenSize().trim());
			insertIt.setString(30, specHeader.getInlineSockRequired().trim());
			insertIt.setString(31, specHeader.getCipType().trim());
			insertIt.setString(32, specHeader.getStorageRecommendation().trim());
			insertIt.setString(33, specHeader.getContainerTamperSeal().trim());
			insertIt.setString(34, specHeader.getContainerCodeLocation().trim());
			insertIt.setString(35, specHeader.getContainerCodeFontSize().trim());
			insertIt.setString(36, specHeader.getCaseCodeFontSize().trim());
			insertIt.setString(37, specHeader.getCaseShowBarCode().trim());
			insertIt.setString(38, specHeader.getPalletLabelType().trim());
			insertIt.setString(39, specHeader.getPalletLabelLocation().trim());
			insertIt.setString(40, specHeader.getStretchWrapRequired().trim());
			insertIt.setString(41, specHeader.getStretchWrapType().trim());
			insertIt.setBigDecimal(42, new BigDecimal(specHeader.getStretchWrapWidth().trim()));
			insertIt.setString(43, specHeader.getStretchWrapWidthUOM().trim());
			insertIt.setBigDecimal(44, new BigDecimal(specHeader.getStretchWrapGauge().trim()));
			insertIt.setString(45, specHeader.getStretchWrapGaugeUOM().trim());
			insertIt.setString(46, specHeader.getShrinkWrapRequired().trim());
			insertIt.setString(47, specHeader.getShrinkWrapType().trim());
			insertIt.setBigDecimal(48, new BigDecimal(specHeader.getShrinkWrapWidth().trim()));
			insertIt.setString(49, specHeader.getShrinkWrapWidthUOM().trim());
			insertIt.setBigDecimal(50, new BigDecimal(specHeader.getShrinkWrapThickness().trim()));
			insertIt.setString(51, specHeader.getShrinkWrapThicknessUOM().trim());
			insertIt.setString(52, specHeader.getSlipSheetRequired().trim());
			insertIt.setString(53, specHeader.getSlipSheetBottom().trim());
			insertIt.setString(54, specHeader.getSlipSheetTop().trim());
			insertIt.setString(55, specHeader.getSlipSheetLayer1().trim());
			insertIt.setString(56, specHeader.getSlipSheetLayer2().trim());
			insertIt.setString(57, specHeader.getSlipSheetLayer3().trim());
			insertIt.setString(58, specHeader.getSlipSheetLayer4().trim());
			insertIt.setString(59, specHeader.getSlipSheetLayer5().trim());
			insertIt.setString(60, specHeader.getSlipSheetLayer6().trim());
			insertIt.setString(61, specHeader.getPalletRequirement().trim());
			insertIt.setInt(62, new Integer(specHeader.getUpdateDate().trim()).intValue());
			insertIt.setInt(63, new Integer(specHeader.getUpdateTime().trim()).intValue());
			insertIt.setString(64, specHeader.getUpdateUser().trim());
			insertIt.setInt(65, new Integer(specHeader.getCreationDate().trim()).intValue());
			insertIt.setInt(66, new Integer(specHeader.getCreationTime().trim()).intValue());
			insertIt.setString(67, specHeader.getCreationUser().trim());
			insertIt.setInt(68, 0);
			insertIt.setInt(69, 0);
			insertIt.setBigDecimal(70, new BigDecimal(specHeader.getTestBrix().trim()));
			insertIt.setBigDecimal(71, new BigDecimal(specHeader.getUnitsPerPallet().trim()));
			insertIt.setBigDecimal(72, new BigDecimal(specHeader.getUnitsPerLayer().trim()));
			insertIt.setString(73, specHeader.getForeignMaterialDetection().trim());
			insertIt.setString(74, specHeader.getCutSize2().trim());
			insertIt.setString(75, specHeader.getCartonCodeLocation().trim());
			insertIt.setString(76, specHeader.getCartonCodeFontSize().trim());
			insertIt.setString(77, specHeader.getCountryOfOrigin().trim());
			insertIt.setString(78, specHeader.getReconstitutionRatio().trim());
			insertIt.setString(79, specHeader.getShelfLifeNotValid().trim());
			insertIt.setString(80, specHeader.getSlipSheetLayer7().trim());
			insertIt.setString(81, specHeader.getSlipSheetLayer8().trim());
			insertIt.setString(82, specHeader.getSlipSheetLayer9().trim());
			insertIt.setString(83, specHeader.getSlipSheetLayer10().trim());
			
			insertIt.executeUpdate();
			insertIt.close();
 			
 			//-------------------------------------------------------------------------------------------------
			// Code to be used for Multiple Sections -- Test Parameters
			//  For the Specification, will be used for Analytical Tests, Micro Tests and Process Parameters
			//-------------------------------------------------------------------------------------------------
 			for (int y = 0; y < 4; y++)
 			{
 				try{
 				Vector listDetails = new Vector();
 				if (y == 0)
 				   listDetails = specHeader.getListAnalyticalTests();
 				if (y == 1)
 				   listDetails = specHeader.getListMicroTests();
 				if (y == 2)
 				   listDetails = specHeader.getListProcessParameters();
 				if (y == 3)
  				   listDetails = specHeader.getListAdditivesAndPreservatives();
 				if (listDetails.isEmpty() == false)
 				{ 	
 					for (int tstCount = 0; listDetails.size() > tstCount; tstCount++)
 					{			   
 						UpdTestParameters   oneTestDetail = (UpdTestParameters) listDetails.elementAt(tstCount);
 						if (!oneTestDetail.getAttributeID().trim().equals(""))
 						{
 							// 10/31/13 TWalton - No Longer using Alpha,  going to put all
 							//            the target, min max values into the Alpha fields
 							//            then we can figure out when something is BLANK
 							
// 						  if (!oneTestDetail.getAttributeID().trim().equals("FLVR") ||
// 							  (oneTestDetail.getAttributeID().trim().equals("FLVR") &&
// 									  (!oneTestDetail.getTargetAlpha().trim().equals("") ||
// 								       !oneTestDetail.getMinimumAlpha().trim().equals("") ||
// 								       !oneTestDetail.getMaximumAlpha().trim().equals(""))))
// 								       {
 							Vector<UpdTestParameters> testDetail    = new Vector<UpdTestParameters>(); 				
 							testDetail.addElement(oneTestDetail);
 							sql = buildSqlStatement("insertTestParameter", testDetail);
// 						 10/11/11 TWalton - Change to a Prepared Statement
 //							insertIt.executeUpdate(sql);
 							insertIt = conn.prepareStatement(sql);
 							insertIt.setInt(1, new Integer(oneTestDetail.getCompany().trim()).intValue());
 							insertIt.setString(2, oneTestDetail.getDivision().trim());
 							insertIt.setInt(3, new Integer(oneTestDetail.getRecordID().trim()).intValue());
 							insertIt.setInt(4, new Integer(oneTestDetail.getRevisionDate().trim()).intValue());
 							insertIt.setInt(5, new Integer(oneTestDetail.getRevisionTime().trim()).intValue());
 							insertIt.setString(6, oneTestDetail.getRecordType().trim());
 							insertIt.setString(7, ""); // Attribute Group - Not currently used
 							insertIt.setString(8, oneTestDetail.getAttributeID().trim());
 							insertIt.setInt(9, new Integer(oneTestDetail.getAttributeIDSequence().trim()));
 							//insertIt.setString(10, oneTestDetail.getUnitOfMeasure().trim());
 							insertIt.setString(10, ""); // will be pulling information from the attributes
 							//  Change to input only to the alpha Fields
 							//insertIt.setBigDecimal(11, new BigDecimal(oneTestDetail.getTarget().trim()));
 							//insertIt.setBigDecimal(12, new BigDecimal(oneTestDetail.getMinimum().trim()));
 							//insertIt.setBigDecimal(13, new BigDecimal(oneTestDetail.getMaximum().trim()));
 							insertIt.setBigDecimal(11, new BigDecimal("0"));
 							insertIt.setBigDecimal(12, new BigDecimal("0"));
 							insertIt.setBigDecimal(13, new BigDecimal("0"));
 							insertIt.setBigDecimal(14, new BigDecimal(oneTestDetail.getTestValue().trim()));
 							insertIt.setString(15, oneTestDetail.getTestValueUOM().trim());
 							insertIt.setString(16, oneTestDetail.getDefaultOnCOA().trim());  
 							insertIt.setInt(17, new Integer(oneTestDetail.getMethod().trim()));
 							insertIt.setInt(18, 0); // Method Revision Date -- going to always use the ACTIVE One
 							insertIt.setInt(19, 0); // Method Revision Time -- going to always use the ACTIVE One
 							insertIt.setInt(20, 0); // Updated Date - not Currently Used
 							insertIt.setInt(21, 0); // Updated Time - not Currently Used
 							insertIt.setString(22, ""); // Updated User - not Currently Used
 							insertIt.setInt(23, 0); // Created Date - not Currently Used
 							insertIt.setInt(24, 0); // Created Time - not Currently Used
 							insertIt.setString(25, ""); // Created User - not Currently Used
 							insertIt.setString(26, oneTestDetail.getApplicationType().trim());
 							insertIt.setString(27, oneTestDetail.getTarget().trim());
 							insertIt.setString(28, oneTestDetail.getMinimum().trim());
 							insertIt.setString(29, oneTestDetail.getMaximum().trim());
 							//insertIt.setString(27, oneTestDetail.getTargetAlpha().trim());
 							//insertIt.setString(28, oneTestDetail.getMinimumAlpha().trim());
 							//insertIt.setString(29, oneTestDetail.getMaximumAlpha().trim());
 						
 							insertIt.executeUpdate();
 							insertIt.close();
 //					       }
 						}
 					}
 				}
 				}catch(Exception e)
 				{
 					System.out.println("Error Occurred when trying to intert to the test File:");
 					if (y == 0)
 	 				   System.out.println("Error found when trying to insert Analytical Tests/");
 	 				if (y == 1)
 	 					System.out.println("Error found when trying to insert Micro Tests/");
 	 				if (y == 2)
 	 					System.out.println("Error found when trying to insert Process Perameters/");
 	 				if (y == 3)
 	 					System.out.println("Error found when trying to insert Additives and Preservatives/");
 					System.out.println(" For Spec " + specHeader.getSpecNumber());
 					System.out.println(" Revision Date/Time " + specHeader.getRevisionDate() + "/" + specHeader.getRevisionTime());
 				}
 			} // end of the for how many sections on the Screen
 			//------------------------------------------------------------------------
 			//  Varieties
 			//-----------------------------------------------------------------------
 			String insertType = "insertVarietyIncluded";
 			String includeExclude = "I";
 			for (int y = 0; y < 2; y++)
 			{
 				Vector listDetails = new Vector();
 				if (y == 0)
 				   listDetails = specHeader.getListVarietiesIncluded();
 				if (y == 1)
 				{
 				   listDetails = specHeader.getListVarietiesExcluded();
 				   insertType = "insertVarietyExcluded";
 				   includeExclude = "X";
 				}
 				if (listDetails.isEmpty() == false)
 				{ 	
 					for (int varCount = 0; listDetails.size() > varCount; varCount++)
 					{			   
 						UpdVariety         oneVarietyDetail = (UpdVariety) listDetails.elementAt(varCount);
 						Vector<UpdVariety> varietyDetail    = new Vector<UpdVariety>(); 				
 						varietyDetail.addElement(oneVarietyDetail);
 						sql = buildSqlStatement(insertType, varietyDetail);
 						// 10/12/11 TWalton - Change to a Prepared Statement
// 						insertIt.executeUpdate(sql);
 						insertIt = conn.prepareStatement(sql);
 						insertIt.setInt(1, new Integer(oneVarietyDetail.getCompany().trim()).intValue());
 						insertIt.setString(2, oneVarietyDetail.getDivision().trim());
 						insertIt.setInt(3, new Integer(oneVarietyDetail.getRecordID().trim()).intValue());
 						insertIt.setInt(4, new Integer(oneVarietyDetail.getRevisionDate().trim()).intValue());
 						insertIt.setInt(5, new Integer(oneVarietyDetail.getRevisionTime().trim()).intValue());
 						insertIt.setString(6, oneVarietyDetail.getCropModel().trim());
 						insertIt.setString(7, oneVarietyDetail.getAttributeID().trim());
 						insertIt.setString(8, oneVarietyDetail.getVariety().trim());
 						insertIt.setString(9, includeExclude.trim());
 						insertIt.setString(10, oneVarietyDetail.getPercentage().trim());
 						insertIt.setString(11, oneVarietyDetail.getMinimumPercent().trim());
 						insertIt.setString(12, oneVarietyDetail.getMaximumPercent().trim());
 						insertIt.setInt(13, 0); //Update Date -- not currently used
 						insertIt.setInt(14, 0); //Update Time -- not currently used
 						insertIt.setString(15, ""); //Update User -- not currently used
 						insertIt.setInt(16, 0); //Create Date -- not currently used
 						insertIt.setInt(17, 0); //Create Time  -- not currently used
 						insertIt.setString(18, ""); //Create User -- not currently used
 						insertIt.setString(19, oneVarietyDetail.getApplicationType().trim());
 						insertIt.setString(20, oneVarietyDetail.getPercentage().trim());
 						insertIt.setString(21, oneVarietyDetail.getMinimumPercent().trim());
 						insertIt.setString(22, oneVarietyDetail.getMaximumPercent().trim());
 						
 						insertIt.executeUpdate();
 						insertIt.close();
 						
 					}
 				}
 			}
 			
 			DtlSpecification specification = new DtlSpecification();
 			specification.setEnvironment(specHeader.getEnvironment().trim());
 			specification.setCompany(specHeader.getCompany().trim());
 			specification.setDivision(specHeader.getDivision().trim());
 			specification.setSpecNumber(specHeader.getSpecNumber().trim());
 			specification.setRevisionDate(specHeader.getRevisionDate().trim());
 			specification.setRevisionTime(specHeader.getRevisionTime().trim());
 			
 			Vector<DtlSpecification> findSpecification = new Vector<DtlSpecification>();
 			findSpecification.addElement(specification);

 			returnValue = returnSpecification("find", throwError.toString(), findSpecification);	
 		    		   
	
	} catch (Exception e)
	{
		throwError.append("Error occured retrieving or executing a sql statement. " + e);
		throwError.append(e);
	}
	
	finally {
			  
	   if (insertIt != null)
	   {
		   try {
			  insertIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("insertSpecification(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update a method per request. (Header and Detail)
 */

private static BeanQuality updateMethod(Vector inValues, 
							 	        Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	Statement    updateIt    = null;
	BeanQuality  returnValue = new BeanQuality();
	
	try {
 		
 		try {
			  
 			returnValue = deleteMethod(inValues, conn);
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (delete). " + e);
 		 }	
 		 
		try {
			  
			returnValue = insertMethod(inValues, conn);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateMethod(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all revision reasons for one formula number.
 */

public static Vector findFormulaRevisionReason(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findFormulaRevisionReason(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaRevisionReason(");
		throwError.append("Vector). ");		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return all revision reasons for one formula number.
 */

private static Vector findFormulaRevisionReason(Vector inValues, 
								                Connection conn)
throws Exception
{
	StringBuffer      throwError        = new StringBuffer();
	ResultSet         rs                = null;
	Statement         listThem          = null;		
	Vector<QaFormula> returnValue   = new Vector<QaFormula>();
			
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("findFormulaRevisionReason", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				
				 while (rs.next())
				 {			 		
					 Vector    oneRevisionReason = loadFields("QaFormulaRevisionReason", rs);
					 QaFormula revisionReason    = (QaFormula) oneRevisionReason.elementAt(0);
					 returnValue.addElement(revisionReason);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaRevisionReason(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update a method per request.
 */

public static BeanQuality updateMethod(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = updateMethod(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateMethod(");
		throwError.append("Vector). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Delete a specification per request.
 */

public static BeanQuality deleteSpecification(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = deleteSpecification(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("deleteSpecification(");
		throwError.append("Vector). ");		
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Delete a specification per request. (Header, Detail, Packaging)
 */

private static BeanQuality deleteSpecification(Vector inValues, 
							 	               Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	Statement    updateIt    = null;
	BeanQuality  returnValue = new BeanQuality();
			
	try {
				
		try {
			updateIt = conn.createStatement();
			
			sql = buildSqlStatement("deleteSpecificationFruitVariety", inValues);
			updateIt.executeUpdate(sql);			   
			
			sql = buildSqlStatement("deleteSpecificationTestParameters", inValues);			
			updateIt.executeUpdate(sql);			   
			
			sql = buildSqlStatement("deleteSpecificationHeader", inValues);
			updateIt.executeUpdate(sql);
		   
			UpdSpecification deleteRequest = (UpdSpecification) inValues.elementAt(0);
			QaSpecification specification = new QaSpecification();	
			specification.setCompanyNumber(deleteRequest.getCompany());
			specification.setDivisionNumber(deleteRequest.getDivision());
			specification.setSpecificationNumber(deleteRequest.getSpecNumber());
 			specification.setRevisionDate(deleteRequest.getRevisionDate().trim());
 			specification.setRevisionTime(deleteRequest.getRevisionTime().trim());
 			returnValue.setEnvironment(deleteRequest.getEnvironment());
 			returnValue.setSpecification(specification);
		    
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("deleteSpecification(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());			
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update a specification per request.
 */

public static BeanQuality updateSpecification(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = updateSpecification(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateSpecification(");
		throwError.append("Vector). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Update a specification per request. (Header and Detail)
 */

private static BeanQuality updateSpecification(Vector inValues, 
							 	               Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	Statement    updateIt    = null;
	BeanQuality  returnValue = new BeanQuality();
	
	try {
 		
 		try {
			  
 			returnValue = deleteSpecification(inValues, conn);
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (delete). " + e);
 		 }	
 		 
		try {
			  
			returnValue = insertSpecification(inValues, conn);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateSpecification(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a list of formulas per request.
 */

public static BeanQuality listFormula(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {		
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listFormula(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listFormula(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return a list of formulas per request.
 */

private static BeanQuality listFormula(Vector inValues, 
								       Connection conn)
throws Exception
{
	StringBuffer      throwError  = new StringBuffer();
	ResultSet         rs          = null;
	Statement         listThem    = null;
	String			  template    = "";	
	Vector<QaFormula> formulaList = new Vector<QaFormula>();
	BeanQuality       returnValue = new BeanQuality();
	
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlStatement("listFormula", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {				 
				
				 InqFormula inRequest = (InqFormula) inValues.elementAt(0);
				 returnValue.setEnvironment(inRequest.getEnvironment().trim());
				 template = inRequest.getInqTemplate().trim();
				 
				 if (template.trim().equals("QaFormulaHeader"))
				 {				 
					 QaFormula lastFormula = new QaFormula();
				 
					 while (rs.next())
					 {			 		
						 Vector oneFormula     = loadFields(template, rs);
						 QaFormula thisFormula = new QaFormula();
						 thisFormula = (QaFormula) oneFormula.lastElement();
					 
						 if ((!thisFormula.getFormulaNumber().trim().equals(lastFormula.getFormulaNumber().trim())) ||
							 (!thisFormula.getRevisionDate().trim().equals(lastFormula.getRevisionDate().trim())) ||
						     (!thisFormula.getRevisionTime().trim().equals(lastFormula.getRevisionTime().trim())))	
						 {						 
							 QaFormula formula  = (QaFormula) oneFormula.elementAt(0);
							 formulaList.addElement(formula);
							 returnValue.setListFormula(formulaList);
					 
							 lastFormula = thisFormula;		// Retain last formula processed
						 }
					 }
				 }
				 
				 if (template.trim().equals("QaFormulaDetail"))
				 {				 
					 while (rs.next())
					 {			 		
						 Vector oneFormula        = loadFields(template, rs);						 				
						 QaFormulaDetail formula  = (QaFormulaDetail) oneFormula.elementAt(0);
						 formulaList.addElement(formula);
						 returnValue.setListFormula(formulaList);					
					 }				
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listFormula(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Build an SQL order by statement for specifications.
 */
	
private static String buildSqlOrderBy(String requestType, String requestSort, String requestStyle)						
throws Exception 
{
	StringBuffer sqlString    = new StringBuffer();
	StringBuffer throwError   = new StringBuffer();
	String       nextOrderBy  = new String();
	String       defaultOrder = new String(); 

	try {
		
//  *************************************************************************************
//	SPECIFICATION
//  *************************************************************************************		
		
		if (requestType.trim().equals("sortSpecification")) 
		{		
			if (requestSort.trim().trim().equals("specificationType")) 
			{
				sqlString.append(" ORDER BY SHTYPE");
				nextOrderBy = ", SHSPNO, SHRDTE DESC, SHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("specificationName")) 
			{
				sqlString.append(" ORDER BY SHNAME");
				nextOrderBy = ", SHRDTE DESC, SHRTME DESC";			
			}	
			if (requestSort.trim().trim().equals("specificationDescription")) 
			{
				sqlString.append(" ORDER BY SHDESC");
				nextOrderBy = ", SHSPNO, SHRDTE DESC, SHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("itemNumber")) 
			{
				sqlString.append(" ORDER BY SHITNO");
				nextOrderBy = ", SHSPNO, SHRDTE DESC, SHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("revisionDate")) 
			{
				sqlString.append(" ORDER BY SHRDTE");
				nextOrderBy = ", SHRTME";	
				if (!requestStyle.trim().trim().equals(""))
					nextOrderBy = nextOrderBy + " " + requestStyle.trim().toUpperCase();
				nextOrderBy = nextOrderBy + ", SHSPNO";	
			}
			if (requestSort.trim().trim().equals("status")) 
			{
				sqlString.append(" ORDER BY SHSTAT");
				nextOrderBy = ", SHSPNO, SHRDTE DESC, SHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("formulaNumber")) 
			{
				sqlString.append(" ORDER BY SHFONO");
				nextOrderBy = ", SHSPNO, SHRDTE DESC, SHRTME DESC";			
			}
			
			defaultOrder = (" ORDER BY SHSPNO, SHRDTE DESC, SHRTME DESC");
		}		
//  *************************************************************************************
//	FORMULA
//  *************************************************************************************
		if (requestType.trim().equals("sortFormula")) 
		{		
			if (requestSort.trim().trim().equals("formulaType")) 
			{
				sqlString.append(" ORDER BY FHTYPE");
				nextOrderBy = ", FHFONO, FHRDTE DESC, FHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("formulaNumber")) 
			{
				sqlString.append(" ORDER BY FHNAME");
				nextOrderBy = ", FHRDTE DESC, FHRTME DESC";			
			}	
			if (requestSort.trim().trim().equals("formulaDescription")) 
			{
				sqlString.append(" ORDER BY FHDESC");
				nextOrderBy = ", FHFONO, FHRDTE DESC, FHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("itemNumber")) 
			{
				sqlString.append(" ORDER BY FHLTNO");
				nextOrderBy = ", FHFONO, FHRDTE DESC, FHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("revisionDate")) 
			{
				sqlString.append(" ORDER BY FHRDTE");
				nextOrderBy = ", FHFONO, FHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("status")) 
			{
				sqlString.append(" ORDER BY FHSTAT");
				nextOrderBy = ", FHFONO, FHRDTE DESC, FHRTME DESC";			
			}
			
			defaultOrder = (" ORDER BY FHFONO, FHRDTE DESC, FHRTME DESC");
		}		
//	*************************************************************************************
//	List METHOD
//	*************************************************************************************
		if (requestType.trim().equals("sortMethod")) 
		{			
			if (requestSort.trim().trim().equals("methodNumber")) 
			{
				sqlString.append(" ORDER BY MHMENO");
				nextOrderBy = ", MHRDTE DESC, MHRTME DESC";			
			}	
			if (requestSort.trim().trim().equals("methodName")) 
			{
				sqlString.append(" ORDER BY MHNAME");
				nextOrderBy = ", MHMENO, MHRDTE DESC, MHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("methodDescription")) 
			{
				sqlString.append(" ORDER BY MHDESC");
				nextOrderBy = ", MHMENO, MHRDTE DESC, MHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("revisionDate")) 
			{
				sqlString.append(" ORDER BY MHRDTE");
				nextOrderBy = ", MHMENO, MHRTME DESC";			
			}
			if (requestSort.trim().trim().equals("status")) 
			{
				sqlString.append(" ORDER BY MHSTAT");
				nextOrderBy = ", MHMENO, MHRDTE DESC, MHRTME DESC";			
			}
			
			defaultOrder = (" ORDER BY MHMENO, MHRDTE DESC, MHRTME DESC");
		}						
//	*************************************************************************************
		if (!sqlString.toString().trim().equals("")) 
		{
			if (!requestStyle.trim().trim().equals(""))
			{
				sqlString.append(" ");
				sqlString.append(requestStyle.trim().toUpperCase());
			}
			sqlString.append(nextOrderBy);	
		}
		
		else {
			if (!defaultOrder.trim().equals(""))
			{
			sqlString.append(defaultOrder);		
			}
		}			
//	*************************************************************************************		
	} catch (Exception e) {
			throwError.append(" Error building sql order by clause" +
						      " for request type " + requestType + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("buildSqlOrderBy(String, String, String)");
		throw new Exception(throwError.toString());
	}
		
	return sqlString.toString();
}

/**
 * @author deisen.
 * Retrieving the next available method identification number.
 */
	
public static BeanQuality nextIDNumberMethod(String environment)							
throws Exception 
{
	AS400	     as400       = null;
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
		
	try {
		
		QaMethod Method = new QaMethod();
		Method.setMethodNumber("0");
		returnValue.setMethod(Method);
		// 1/17/12 TWalton Change to use ServiceConnection
		//as400 = ConnectionStack.getAS400Object();
		as400 = ServiceConnection.getAS400();
		ProgramCall pgm   = new ProgramCall(as400);
		AS400Text library = new AS400Text(3); 
		
		ProgramParameter[] parmList = new ProgramParameter[2];
		parmList[0] = new ProgramParameter(100);
		parmList[1] = new ProgramParameter(library.toBytes(environment.trim())); 
				pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/QACIDMETH.PGM", parmList);
	
		if (pgm.run() != true)
			return returnValue;
		
		else {
			AS400PackedDecimal number = new AS400PackedDecimal(10, 0);
			byte[] data = parmList[0].getOutputData();
			double dd   = number.toDouble(data, 0);
			int    id   = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			Method.setMethodNumber(Integer.toString(id));
			returnValue.setMethod(Method);
			return returnValue;			
		}
		
	} catch (Exception e) {
			throwError.append(" Error retrieving next available method identification number. " + e);			
	}
	
	finally {
		if (as400 !=null)
		{
		//	ConnectionStack.returnAS400Object(as400);
			ServiceConnection.returnAS400(as400);
		}
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("nextIDNumberMethod(String)");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * return the "find" result or provide an error response.
 */	

private static BeanQuality returnMethod(String inRequestType, String inThrowError,
							            Vector inValues)
throws Exception 
{
	StringBuffer        throwError  = new StringBuffer();
	Vector<DtlMethod> beanMethod  = new Vector<DtlMethod>();
	BeanQuality         returnValue = new BeanQuality();
			
	try {
		
		DtlMethod findRequest = (DtlMethod) inValues.elementAt(0);
		
		QaMethod method = new QaMethod();	
		method.setCompanyNumber(findRequest.getCompany());
		method.setDivisionNumber(findRequest.getDivision());
		method.setMethodNumber(findRequest.getMethodNumber());
		method.setRevisionDate(findRequest.getRevisionDate());
		method.setRevisionTime(findRequest.getRevisionTime());
		returnValue.setMethod(method);
					
		if (inRequestType.trim().equals("find")) 
		{
			beanMethod.addElement(findRequest);
			returnValue = findMethod(beanMethod);
		}	
		
		if (inRequestType.trim().equals("error")) 
		{		
			returnValue.setEnvironment(findRequest.getEnvironment());	
			returnValue.setStatusMessage(inThrowError);		
		}				

	} catch (Exception e) {
			throwError.append(" Error processing a find or error response for request type " +
						      inRequestType + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("returnMethod(String, String, Vector)");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Delete a formula per request.
 */

public static BeanQuality deleteFormula(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = deleteFormula(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("deleteFormula(");
		throwError.append("Vector). ");		
		
		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Delete a formula per request. (Header and Detail)
 */

private static BeanQuality deleteFormula(Vector inValues, 
							 	         Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	Statement    updateIt    = null;	
	BeanQuality  returnValue = new BeanQuality();
	
	try {
		
		try {	
				
			sql = buildSqlStatement("deleteFormulaFruitVariety", inValues);
			updateIt = conn.createStatement();
			updateIt.executeUpdate(sql);	
			
			sql = buildSqlStatement("deleteFormulaTestParameters", inValues);
			updateIt = conn.createStatement();
			updateIt.executeUpdate(sql);	
			
			sql = buildSqlStatement("deleteFormulaDetail", inValues);
			updateIt = conn.createStatement();
			updateIt.executeUpdate(sql);			   
		  
			sql = buildSqlStatement("deleteFormulaHeader", inValues);		   
			updateIt.executeUpdate(sql);		   
		   
			UpdFormula deleteRequest = (UpdFormula) inValues.elementAt(0);
			QaFormula formula = new QaFormula();
			formula.setCompanyNumber(deleteRequest.getCompany());
			formula.setDivisionNumber(deleteRequest.getDivision());	
			formula.setFormulaNumber(deleteRequest.getFormulaNumber());
 			formula.setRevisionDate(deleteRequest.getRevisionDate().trim());
 			formula.setRevisionTime(deleteRequest.getRevisionTime().trim());
 			returnValue.setEnvironment(deleteRequest.getEnvironment());
 			returnValue.setFormula(formula);
		    
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("deleteFormula(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());			
	}

	return returnValue;
}

/**
 * @author deisen.
 * return the "find" result or provide an error response.
 */	

private static BeanQuality returnFormula(String inRequestType, String inThrowError,
								         Vector inValues)
throws Exception 
{
	StringBuffer         throwError  = new StringBuffer();
	Vector<DtlFormula> beanFormula = new Vector<DtlFormula>();
	BeanQuality          returnValue = new BeanQuality();
			
	try {
		
		DtlFormula findRequest = (DtlFormula) inValues.elementAt(0);		
		
		QaFormula formula = new QaFormula();		
		formula.setCompanyNumber(findRequest.getCompany());
		formula.setDivisionNumber(findRequest.getDivision());
		formula.setFormulaNumber(findRequest.getFormulaNumber());
		formula.setRevisionDate(findRequest.getRevisionDate());
		formula.setRevisionTime(findRequest.getRevisionTime());
		returnValue.setFormula(formula);
				
		if (inRequestType.trim().equals("find")) 
		{				
			beanFormula.addElement(findRequest);
			returnValue = findFormula(beanFormula);
		}	
		
		if (inRequestType.trim().equals("error")) 
		{		
			returnValue.setEnvironment(findRequest.getEnvironment());	
			returnValue.setStatusMessage(inThrowError);			
		}				

	} catch (Exception e) {
			throwError.append(" Error processing a find or error response for request type " +
						      inRequestType + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("returnFormula(String, String, Vector)");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Return a drop down single box for type codes.
 */

public static Vector dropDownType(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownType");
		dropDownBox = dropDownGenericSingle(requestBean);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownType(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Create a formula per request.
 */

public static BeanQuality insertFormula(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = insertFormula(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("insertFormula(");
		throwError.append("Vector). ");	
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Create a formula per request. (Header and Detail)
 */

private static BeanQuality insertFormula(Vector inValues, 
							 	         Connection conn)
throws Exception
{
	StringBuffer         throwError  = new StringBuffer();
	String               sql         = new String();
	PreparedStatement    insertIt    = null;
	BeanQuality          returnValue = new BeanQuality();
	Vector<DtlFormula> findFormula = new Vector<DtlFormula>();
	
	try {
		
 		try { 		 			
 			
 			UpdFormula oneFormula       = (UpdFormula) inValues.elementAt(0);
			
			DtlFormula formula = new DtlFormula();
 			formula.setEnvironment(oneFormula.getEnvironment().trim());
 			formula.setCompany(oneFormula.getCompany().trim());
 			formula.setDivision(oneFormula.getDivision().trim());
 			formula.setFormulaNumber(oneFormula.getFormulaNumber().trim());
 			formula.setRevisionDate(oneFormula.getRevisionDate().trim());
 			formula.setRevisionTime(oneFormula.getRevisionTime().trim()); 			
 			findFormula.addElement(formula);	
 			
 			sql = buildSqlStatement("insertFormulaHeader", inValues);
 			//insertIt = conn.createStatement();
 			//insertIt.executeUpdate(sql); 
 			// 10/11/11 TWalton Change to a prepared Statement
 			insertIt = conn.prepareStatement(sql);
			insertIt.setInt(1, new Integer(oneFormula.getCompany().trim()).intValue());
			insertIt.setString(2, oneFormula.getDivision().trim());
			insertIt.setString(3, oneFormula.getOriginalStatus().trim());
			insertIt.setString(4, oneFormula.getFormulaType().trim());
			insertIt.setString(5, oneFormula.getOriginationUser().trim());
			insertIt.setString(6, oneFormula.getGroupCode().trim());
			insertIt.setInt(7, new Integer(oneFormula.getFormulaNumber().trim()).intValue());
			insertIt.setInt(8, new Integer(oneFormula.getRevisionDate().trim()).intValue());
			insertIt.setInt(9, new Integer(oneFormula.getRevisionTime().trim()).intValue());
			insertIt.setString(10, oneFormula.getLineTankItem().trim());
			insertIt.setBigDecimal(11, new BigDecimal(oneFormula.getBatchSize().trim()));
			insertIt.setString(12, oneFormula.getBatchUOM().trim());
			insertIt.setBigDecimal(13, new BigDecimal(oneFormula.getTargetBrix().trim()));
			insertIt.setString(14, oneFormula.getCustomerNumber().trim());
			insertIt.setString(15, oneFormula.getCustomerName().trim());
			insertIt.setString(16, oneFormula.getCustomerCode().trim());
			insertIt.setInt(17, new Integer(oneFormula.getReferenceFormulaNumber().trim()).intValue());
			insertIt.setInt(18, new Integer(oneFormula.getReferenceFormulaRevisionDate().trim()).intValue());
			insertIt.setInt(19, new Integer(oneFormula.getReferenceFormulaRevisionTime().trim()).intValue());
			insertIt.setString(20, oneFormula.getFormulaDescription().trim());
			insertIt.setString(21, oneFormula.getProductionStatus().trim());
			insertIt.setString(22, oneFormula.getRevisionReason().trim());
			insertIt.setInt(23, new Integer(oneFormula.getUpdateDate().trim()).intValue());
			insertIt.setInt(24, new Integer(oneFormula.getUpdateTime().trim()).intValue());
			insertIt.setString(25, oneFormula.getUpdateUser().trim());
			insertIt.setInt(26, new Integer(oneFormula.getCreationDate().trim()).intValue());
			insertIt.setInt(27, new Integer(oneFormula.getCreationTime().trim()).intValue());
			insertIt.setString(28, oneFormula.getCreationUser().trim());
			insertIt.setInt(29, 0);
			insertIt.setInt(30, 0);
			insertIt.setString(31, oneFormula.getApprovedByUser().trim());
			insertIt.setString(32, oneFormula.getFruitOrigin().trim());
			insertIt.setBigDecimal(33, new BigDecimal(oneFormula.getBatchSizePreBlend().trim()));
			insertIt.setString(34, oneFormula.getBatchPreBlendUOM().trim());
			insertIt.setString(35, oneFormula.getFormulaName().trim());
			insertIt.setString(36, oneFormula.getScopeCode().trim());
			insertIt.setString(37, oneFormula.getCustomerOrSupplierItemNumber().trim());
			
			insertIt.executeUpdate();
 			
 			//---Detail Sections
 			for (int y = 0; y < 3; y++)
 			{
 				Vector listDetails = new Vector();
 				if (y == 0)
 				   listDetails = oneFormula.getListPreBlendDetail();
 				if (y == 1)
 				   listDetails = oneFormula.getListProductionDetail();
 				if (y == 2)
 				   listDetails = oneFormula.getListPreBlendSauceDetail();
 				if (listDetails.isEmpty() == false)
 				{ 	
 					for (int x = 0; listDetails.size() > x; x++)
 					{			   
 						UpdFormulaDetail         oneFormulaDetail = (UpdFormulaDetail) listDetails.elementAt(x);
 						Vector<UpdFormulaDetail> formulaDetail    = new Vector<UpdFormulaDetail>(); 				
 						formulaDetail.addElement(oneFormulaDetail);
 						sql = buildSqlStatement("insertFormulaDetail", formulaDetail);
 						//	 10/11/11 TWalton - Change to a Prepared Statement
 						//insertIt.executeUpdate(sql);
 						String detailItemNumber1 = new String();
 						String detailItemDescription1 = new String();
 						String detailItemNumber2 = new String();
 						String detailItemDescription2 = new String();
 						String detailItemNumber3 = new String();
 						String detailItemDescription3 = new String();
 						String detailSupplierNumber = new String();
 						String detailSupplierName = new String();
 						
 						if (!oneFormulaDetail.getItemNumber1().trim().equals(""))
 						{
 							detailItemNumber1 = oneFormulaDetail.getItemNumber1().trim();
 							detailItemDescription1 = " ";
 						}
 						else {
 							detailItemNumber1 = " ";
 							detailItemDescription1 = oneFormulaDetail.getItemDescription1().trim();
 						}
 						if (!oneFormulaDetail.getItemNumber2().trim().equals(""))		
 						{
 							detailItemNumber2 = oneFormulaDetail.getItemNumber2().trim();
 							detailItemDescription2 = " ";
 						}
 						else {
 							detailItemNumber2 = " ";
 							detailItemDescription2 = oneFormulaDetail.getItemDescription2().trim();
 						}
 						if (!oneFormulaDetail.getItemNumber3().trim().equals(""))
 						{
 							detailItemNumber3 = oneFormulaDetail.getItemNumber3().trim();
 							detailItemDescription3 = " ";
 						}
 						else {
 							detailItemNumber3 = " ";
 							detailItemDescription3 = oneFormulaDetail.getItemDescription3().trim();
 						}
 						if (!oneFormulaDetail.getSupplierNumber().trim().equals(""))
 						{
 							detailSupplierNumber = oneFormulaDetail.getSupplierNumber().trim();
 							detailSupplierName = " ";
 						}
 						else {
 							detailSupplierNumber = " ";
 							detailSupplierName = oneFormulaDetail.getSupplierName().trim();
 						}
 
 						insertIt = conn.prepareStatement(sql);
 						insertIt.setInt(1, new Integer(oneFormulaDetail.getCompany().trim()).intValue());
 						insertIt.setString(2, oneFormulaDetail.getDivision().trim());
 						insertIt.setInt(3, new Integer(oneFormulaDetail.getFormulaNumber().trim()).intValue());
 						insertIt.setString(4, oneFormulaDetail.getRecordType().trim());
 						insertIt.setBigDecimal(5, new BigDecimal(oneFormulaDetail.getSauceTargetBrix().trim()));
 						insertIt.setBigDecimal(6, new BigDecimal(oneFormulaDetail.getSauceBatchQty().trim()));
 						insertIt.setString(7, oneFormulaDetail.getSauceBatchQtyUOM().trim());
 						insertIt.setInt(8, new Integer(oneFormulaDetail.getRevisionDate().trim()).intValue());
 						insertIt.setInt(9, new Integer(oneFormulaDetail.getRevisionTime().trim()).intValue());
 						insertIt.setInt(10, new Integer(oneFormulaDetail.getDetailSequence().trim()).intValue());
 						insertIt.setString(11, detailItemNumber1.trim());
 						insertIt.setString(12, detailItemDescription1.trim());
 						insertIt.setBigDecimal(13, new BigDecimal(oneFormulaDetail.getQuantity1().trim()));
 						insertIt.setString(14, oneFormulaDetail.getUnitOfMeasure1().trim());
 						insertIt.setString(15, detailItemNumber2.trim());
 						insertIt.setString(16, detailItemDescription2.trim());
 						insertIt.setBigDecimal(17, new BigDecimal(oneFormulaDetail.getQuantity2().trim()));
 						insertIt.setString(18, oneFormulaDetail.getUnitOfMeasure2().trim());
 						insertIt.setString(19, detailItemNumber3.trim());
 						insertIt.setString(20, detailItemDescription3.trim());
 						insertIt.setBigDecimal(21, new BigDecimal(oneFormulaDetail.getQuantity3().trim()));
 						insertIt.setString(22, oneFormulaDetail.getUnitOfMeasure3().trim());
 						insertIt.setString(23, detailSupplierNumber.trim());
 						insertIt.setString(24, detailSupplierName.trim());
 						insertIt.setString(25, oneFormulaDetail.getReferenceSpec().trim());
 						
 						insertIt.executeUpdate();
 						
 					}
 				}
 			}
 			//---Variety Sections
 			String insertType = "insertVarietyIncluded";
 			String includeExclude = "I";
 			for (int y = 0; y < 2; y++)
 			{
 				Vector listDetails = new Vector();
 				if (y == 0)
 				   listDetails = oneFormula.getListVarietiesIncluded();
 				if (y == 1)
 				{
 				   listDetails = oneFormula.getListVarietiesExcluded();
 				   insertType = "insertVarietyExcluded";
 				   includeExclude = "X";
 				}
 				if (listDetails.isEmpty() == false)
 				{ 	
 					for (int x = 0; listDetails.size() > x; x++)
 					{			   
 						UpdVariety         oneVarietyDetail = (UpdVariety) listDetails.elementAt(x);
 						Vector<UpdVariety> varietyDetail    = new Vector<UpdVariety>(); 				
 						varietyDetail.addElement(oneVarietyDetail);
 						sql = buildSqlStatement(insertType, varietyDetail);
 						// 10/12/11 TWalton - Change to a Prepared Statement
// 						insertIt.executeUpdate(sql);
 						insertIt = conn.prepareStatement(sql);
 						insertIt.setInt(1, new Integer(oneVarietyDetail.getCompany().trim()).intValue());
 						insertIt.setString(2, oneVarietyDetail.getDivision().trim());
 						insertIt.setInt(3, new Integer(oneVarietyDetail.getRecordID().trim()).intValue());
 						insertIt.setInt(4, new Integer(oneVarietyDetail.getRevisionDate().trim()).intValue());
 						insertIt.setInt(5, new Integer(oneVarietyDetail.getRevisionTime().trim()).intValue());
 						insertIt.setString(6, oneVarietyDetail.getCropModel().trim());
 						insertIt.setString(7, oneVarietyDetail.getAttributeID().trim());
 						insertIt.setString(8, oneVarietyDetail.getVariety().trim());
 						insertIt.setString(9, includeExclude.trim());
// wth allow blanks		insertIt.setBigDecimal(10, new BigDecimal(oneVarietyDetail.getPercentage().trim()));
// wth allow blanks		insertIt.setBigDecimal(11, new BigDecimal(oneVarietyDetail.getMinimumPercent().trim()));
// wth allow blanks		insertIt.setBigDecimal(12, new BigDecimal(oneVarietyDetail.getMaximumPercent().trim()));
 						insertIt.setString(10, "0"); //wth
 						insertIt.setString(11, "0"); //wth
 						insertIt.setString(12, "0"); //wth
 						insertIt.setInt(13, 0); //Update Date -- not currently used
 						insertIt.setInt(14, 0); //Update Time -- not currently used
 						insertIt.setString(15, ""); //Update User -- not currently used
 						insertIt.setInt(16, 0); //Create Date -- not currently used
 						insertIt.setInt(17, 0); //Create Time  -- not currently used
 						insertIt.setString(18, ""); //Create User -- not currently used
 						insertIt.setString(19, oneVarietyDetail.getApplicationType().trim());
 						insertIt.setString(20, oneVarietyDetail.getPercentage().trim() ); //wth
 						insertIt.setString(21, oneVarietyDetail.getMinimumPercent().trim() ); //wth
 						insertIt.setString(22, oneVarietyDetail.getMaximumPercent().trim() ); //wth
 						
 						insertIt.executeUpdate();
 						
 					}
 				}
 			}
 			//---Test Attribute Section
 			for (int y = 0; y < 1; y++)
 			{
 				Vector listDetails = new Vector();
 				if (y == 0)
 				   listDetails = oneFormula.getListRawFruitAttributes();
 				if (listDetails.isEmpty() == false)
 				{ 	
 					for (int x = 0; listDetails.size() > x; x++)
 					{			   
 						UpdTestParameters   oneTestDetail = (UpdTestParameters) listDetails.elementAt(x);
 						Vector<UpdTestParameters> testDetail    = new Vector<UpdTestParameters>(); 				
 						testDetail.addElement(oneTestDetail);
 						sql = buildSqlStatement("insertTestParameter", testDetail);
// 						 10/11/11 TWalton - Change to a Prepared Statement
 //						insertIt.executeUpdate(sql);
 						insertIt = conn.prepareStatement(sql);
 						insertIt.setInt(1, new Integer(oneTestDetail.getCompany().trim()).intValue());
 						insertIt.setString(2, oneTestDetail.getDivision().trim());
 						insertIt.setInt(3, new Integer(oneTestDetail.getRecordID().trim()).intValue());
 						insertIt.setInt(4, new Integer(oneTestDetail.getRevisionDate().trim()).intValue());
 						insertIt.setInt(5, new Integer(oneTestDetail.getRevisionTime().trim()).intValue());
 						insertIt.setString(6, oneTestDetail.getRecordType().trim());
 						insertIt.setString(7, ""); // Attribute Group - Not currently used
 						insertIt.setString(8, oneTestDetail.getAttributeID().trim());
 						insertIt.setInt(9, new Integer(oneTestDetail.getAttributeIDSequence().trim()));
 						insertIt.setString(10, oneTestDetail.getUnitOfMeasure().trim());
//wth allow blanks      insertIt.setBigDecimal(11, new BigDecimal(oneTestDetail.getTarget().trim()));
//wth allow blanks      insertIt.setBigDecimal(12, new BigDecimal(oneTestDetail.getMinimum().trim()));
//wth allow blanks 		insertIt.setBigDecimal(13, new BigDecimal(oneTestDetail.getMaximum().trim()));
 						insertIt.setString(11, "0"); //wth
 						insertIt.setString(12, "0"); //wth
 						insertIt.setString(13, "0"); //wth
 						insertIt.setBigDecimal(14, new BigDecimal(oneTestDetail.getTestValue().trim()));
 						insertIt.setString(15, oneTestDetail.getTestValueUOM().trim());
 						insertIt.setString(16, oneTestDetail.getDefaultOnCOA().trim()); 
 						insertIt.setInt(17, new Integer(oneTestDetail.getMethod().trim()));
 						insertIt.setInt(18, 0); // Method Revision Date -- going to always use the ACTIVE One
 						insertIt.setInt(19, 0); // Method Revision Time -- going to always use the ACTIVE One
 						insertIt.setInt(20, 0); // Updated Date - not Currently Used
 						insertIt.setInt(21, 0); // Updated Time - not Currently Used
 						insertIt.setString(22, ""); // Updated User - not Currently Used
 						insertIt.setInt(23, 0); // Created Date - not Currently Used
 						insertIt.setInt(24, 0); // Created Time - not Currently Used
 						insertIt.setString(25, ""); // Created User - not Currently Used
 						insertIt.setString(26, oneTestDetail.getApplicationType().trim());
 						insertIt.setString(27, oneTestDetail.getTarget().trim());
 						insertIt.setString(28, oneTestDetail.getMinimum().trim());
 						insertIt.setString(29, oneTestDetail.getMaximum().trim());
// 						insertIt.setString(27, oneTestDetail.getTargetAlpha().trim());
// 						insertIt.setString(28, oneTestDetail.getMinimumAlpha().trim());
// 						insertIt.setString(29, oneTestDetail.getMaximumAlpha().trim());
 						
 						insertIt.executeUpdate();
 						
 					}
 				}
 			}
 		 returnValue = returnFormula("find", throwError.toString(), findFormula);	
 		    		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
 		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (insertIt != null)
	   {
		   try {
			  insertIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("insertFormula(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update a formula per request.
 */

public static BeanQuality updateFormula(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = updateFormula(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateFormula(");
		throwError.append("Vector). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * validate either the formula number or formula number, revision date and time.
 */

public static String verifyFormula(CommonRequestBean requestBean)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	String       returnValue = new String();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = verifyFormula(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("verifyFormula(");
		throwError.append("CommonRequestBean). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Update a formula per request. (Header and Detail)
 */

private static BeanQuality updateFormula(Vector inValues, 
							 	         Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	
	try {
 		
 		try {
 			
 			returnValue = deleteFormula(inValues, conn);
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (delete). " + e);
 		 }	
 		 
		try {
			  
			returnValue = insertFormula(inValues, conn);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateFormula(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Load class fields from result set for a drop down box. (single)
 */

public static DropDownSingle loadFieldsDropDown(String dropDownType, 
						          		  		ResultSet rs)
throws Exception
{
	StringBuffer   throwError  = new StringBuffer();
	DropDownSingle returnValue = new DropDownSingle();
	
	try { 
		
		if  (dropDownType.trim().equals("dropDownSingle")) 			
		{
			returnValue.setValue(rs.getString("DCCACD").trim());
			returnValue.setDescription(rs.getString("DCTEXT").trim());
		}
		if  (dropDownType.trim().equals("dropDownMethod")) 			
		{
			returnValue.setValue(rs.getString("MHMENO").trim());
			returnValue.setDescription(rs.getString("MHNAME") + " " + rs.getString("MHDESC").trim());
		}
		if  (dropDownType.trim().equals("dropDownFormula")) 			
		{
			returnValue.setValue(rs.getString("FHFONO").trim());
			returnValue.setDescription(rs.getString("FHNAME") + " " + rs.getString("FHDESC").trim());
		}
		
	} catch(Exception e)
	{
		throwError.append(" Problem loading the drop down single class");
		throwError.append(" from the result set. " + e) ;
	}

//  *************************************************************************************				
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceQuality.");
		throwError.append("loadFieldsDropDownSingle(String, rs: ");
		throwError.append(dropDownType + "). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a header and detail formula per request.
 */

public static BeanQuality findFormula(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findFormula(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormula(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return a header and detail formula per request.
 */

private static BeanQuality findFormula(Vector inValues, 
							       	   Connection conn)
throws Exception
{
	StringBuffer            throwError         = new StringBuffer();
	ResultSet               rs                 = null;
	Statement               listThem           = null;
	QaFormula               formula            = new QaFormula();
	DtlFormula            	formulaRequest     = new DtlFormula();
	Vector<DtlFormula>    	formulaRequestList = new Vector<DtlFormula>();
	Vector<QaFormulaDetail> formulaList        = new Vector<QaFormulaDetail>();
	BeanQuality             returnValue        = new BeanQuality();
			
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlStatement("findFormula", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 DtlFormula inRequest = (DtlFormula) inValues.elementAt(0);
				 returnValue.setEnvironment(inRequest.getEnvironment());
			
				 while (rs.next())
				 {			 			
					 formula = returnValue.getFormula();
					 // Will build ONLY the Formula Header information 
					 if (formula.getFormulaNumber().toString().trim().equals(""))
					 {
						 Vector    oneFormula    = loadFields("QaFormulaHeader", rs);	
						 QaFormula formulaHeader = (QaFormula) oneFormula.elementAt(0);
						 returnValue.setFormula(formulaHeader);
						 
						 formulaRequest.setEnvironment(inRequest.getEnvironment());
						 formulaRequest.setCompany(formulaHeader.getCompanyNumber());
						 formulaRequest.setDivision(formulaHeader.getDivisionNumber());		
						 formulaRequest.setFormulaNumber(formulaHeader.getFormulaNumber());						 
						 formulaRequest.setRevisionDate(formulaHeader.getRevisionDate());
						 formulaRequest.setRevisionTime(formulaHeader.getRevisionTime());
						 formulaRequestList.addElement(formulaRequest);
					 }
				 }
					 
				 if ((!formulaRequest.getFormulaNumber().trim().equals("")) &&
					 (!formulaRequest.getRevisionDate().trim().equals("")) &&
					 (!formulaRequest.getRevisionTime().trim().equals("")))
				 {						 
					 Vector revisionReason = new Vector();
					 revisionReason = findFormulaRevisionReason(formulaRequestList);
					 returnValue.setRevReasonFormula(revisionReason);
					 
					//Formula DETAIL Information
				 	 Vector formulaPreBlend = new Vector();
					 formulaPreBlend = findFormulaPreBlend(formulaRequestList);
					 returnValue.setFormulaPreBlend(formulaPreBlend);
					
					 Vector formulaProduction = new Vector();
					 formulaProduction = findFormulaProduction(formulaRequestList);
					 returnValue.setFormulaProduction(formulaProduction);
				 
					 Vector formulaPreBlendSauce = new Vector();
					 formulaPreBlendSauce = findFormulaPreBlendSauce(formulaRequestList);
					 returnValue.setFormulaPreBlendSauce(formulaPreBlendSauce);
					 
					 Vector formulaTests = new Vector();
					 formulaTests = listFormulaTests(formulaRequestList);
					 returnValue.setFormulaRawFruitTests(formulaTests);
					 
					 Vector varietiesIncluded = new Vector();
					 varietiesIncluded = listVarietiesIncluded("FORMULA", formulaRequestList);
					 returnValue.setVarietiesIncluded(varietiesIncluded);
				 
					 Vector varietiesExcluded = new Vector();
					 varietiesExcluded = listVarietiesExcluded("FORMULA", formulaRequestList);
					 returnValue.setVarietiesExcluded(varietiesExcluded);
				 }			
				 // Retrieve the Product Structure Information
				 if (!returnValue.getFormula().getLineTankItem().trim().equals(""))
				 {
					 try{
						 InqItem ii = new InqItem();
						 ii.setInqItem(returnValue.getFormula().getLineTankItem().trim());
						 ii.setEnvironment(returnValue.getEnvironment().trim());
						 BeanItem bi = ServiceItem.listProductStructureMaterials(ii);
						 returnValue.setProductStructure(bi.getProductStructure());
					 }catch(Exception e){}
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormula(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());
		
		throw new Exception(throwError.toString());		
	}

	return returnValue;
}

/**
 * @author deisen.
 * Build an SQL statement for specifications.
 * 10/12/11 TWalton for Insert change to Prepared Statements (Delete the not needed code) - if need to see look in the Project TreeNet61
 * 11/17/11 Spec Detail not needed (Delete the not needed code) - if need to see look in the Project TreeNet61
 */
	
private static String buildSqlStatement(String requestType,
									    Vector requestClass)
throws Exception 
{
	StringBuffer sqlString       = new StringBuffer();	
	StringBuffer throwError      = new StringBuffer();
	String       condition       = new String();
	String       where           = new String(" WHERE");
	String       and             = new String(" AND");
	String		 orderBy		 = new String("");
		
	try {
		
		Hashtable categoryCodes = buildSqlCategoryCodes();
		condition = where;
		
//  *************************************************************************************
//	Delete SPECIFICATION
//  *************************************************************************************		
		if (requestType.trim().equals("deleteSpecificationHeader")) 
		{		
			UpdSpecification specificationHeader = (UpdSpecification) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
			if ((!specificationHeader.getSpecNumber().trim().equals(""))   &&
			    (!specificationHeader.getRevisionDate().trim().equals("")) &&
			    (!specificationHeader.getRevisionTime().trim().equals("")))	
			{	
				sqlString.append("DELETE FROM " + libraryTT + ".QAPGSPHD");
				sqlString.append(where);
				sqlString.append(" SHCONO = " + specificationHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" SHDIVI = '" + specificationHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" SHSPNO = " + specificationHeader.getSpecNumber().trim());
				sqlString.append(and);
				sqlString.append(" SHRDTE = " + specificationHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" SHRTME = " + specificationHeader.getRevisionTime().trim());
			}
		}
			
//  *************************************************************************************
//	Find SPECIFICATION
//  *************************************************************************************		
		if (requestType.trim().equals("findSpecification")) 
		{		
			DtlSpecification specification = (DtlSpecification) requestClass.elementAt(0);
			String libraryM3 = GeneralUtility.getLibrary(specification.getEnvironment().trim());
			String libraryTT = GeneralUtility.getTTLibrary(specification.getEnvironment().trim());
			if (!specification.getSpecNumber().trim().equals(""))
			{			
				sqlString.append(" SELECT  SHCONO, SHDIVI, SHSPNO, SHRDTE, SHRTME, SHNAME, SHSTAT, SHTYPE,");
				sqlString.append(" SHGRUP, SHSCOP, SHDESC, SHOUSR, SHAUSR, SHITNO, SHPDST, SHRTXT, SHRSNO,");
				sqlString.append(" SHRSDT, SHRSTM, SHFONO, SHFODT, SHFOTM, SHCUNO, SHCUNM, SHCUCD, SHKSTS,");
				sqlString.append(" SHKSYM, SHCUTZ, SHSRNZ, SHINSK, SHCIPT, SHSREC, SHCTSL, SHCCDL, SHCDFS,");
				sqlString.append(" SHCSFS, SHCSBC, SHPLBT, SHPLBL, SHWREQ, SHWTYP, SHWWDH, SHWUOM, SHWGAU,");
				sqlString.append(" SHGUOM, SHKREQ, SHKTYP, SHKWDH, SHKUOM, SHKTHK, SHTUOM, SHSREQ, SHSLOC,");
				sqlString.append(" SHSTOP, SHSLY1, SHSLY2, SHSLY3, SHSLY4, SHSLY5, SHSLY6, SHPREQ, SHUDTE,");
				sqlString.append(" SHUTME, SHUUSR, SHCDTE, SHCTME, SHCUSR, SHSDTE, SHSTME, SHTBRX, SHUNTP,");
				sqlString.append(" SHUNTL, SHFMAT, SHCUT2, SHCCTL, SHCTFS, SHCORG, SHRCON, SHSLCB, SHSLY7,");
				sqlString.append(" SHSLY8, SHSLY9, SHSLY10, ");
				
				sqlString.append(" IFNULL(FHNAME,' ') as FNAME, IFNULL(FHDESC,' ') as FDESC,");
				sqlString.append(" IFNULL(FHRDTE,'0') as FDATE, IFNULL(FHRTME,'0') as FTIME,");
				
				sqlString.append(" IFNULL(OKCUNM,' ') as M3CUNM, IFNULL(SRANAME,' ') as SACUNM,");
				
				sqlString.append(" IFNULL(MMITDS,' ') as IDESC, IFNULL(MMFRAG,'0') as IFRAG,");
				sqlString.append(" IFNULL(MMGRWE,'0') as IGRWE, IFNULL(MMVOL3,'0') as IVOL3,");		
				
				sqlString.append(" IFNULL(MSWDTH,'0') as ILENGTH, IFNULL(MSWWTH,'0') as IWIDTH,");
				sqlString.append(" IFNULL(MSWGWG,'0') as IHEIGHT,");		
				
				sqlString.append(" IFNULL(u1.MUCOFA,'0') as M3UNITPL, IFNULL(u2.MUCOFA,'0') as M3UNITTIE,");
				
				sqlString.append(" IFNULL(a1.MPPOPN,' ') as LBLUPC, IFNULL(a2.MPPOPN,' ') as UNITUPC,");
				sqlString.append(" IFNULL(a3.MPPOPN,' ') as PALLETGTIN,");
				
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT,");
				sqlString.append(" IFNULL(sc.DCTEXT,' ') as SCTEXT, IFNULL(gp.DCTEXT,' ') as GPTEXT,");	
				sqlString.append(" IFNULL(ks.DCTEXT,' ') as KSTEXT, IFNULL(soc.DCTEXT,' ') as ISTEXT,");
				sqlString.append(" IFNULL(ct.DCTEXT,' ') as CTTEXT, IFNULL(cn.DCTEXT,' ') as CNTEXT,");	
				sqlString.append(" IFNULL(cl.DCTEXT,' ') as CLTEXT, IFNULL(lt.DCTEXT,' ') as LTTEXT,");
				sqlString.append(" IFNULL(pl.DCTEXT,' ') as PLTEXT, IFNULL(tt.DCTEXT,' ') as TTTEXT,");	
				sqlString.append(" IFNULL(kt.DCTEXT,' ') as KTTEXT, IFNULL(pr.DCTEXT,' ') as PRTEXT,");	
				sqlString.append(" IFNULL(ki.DCTEXT,' ') as KITEXT, IFNULL(ctn.DCTEXT,' ') as CTNTEXT,");
				sqlString.append(" IFNULL(ctsz.DCTEXT,' ') as CTSZTEXT, IFNULL(cts2.DCTEXT,' ') as CTS2TEXT,");
				sqlString.append(" IFNULL(scsz.DCTEXT,' ') as SCSZTEXT, IFNULL(fmd.DCTEXT,' ') as FMDTEXT, ");
				
				sqlString.append(" IFNULL(sr.CTTX40,' ') as SRTX40,    IFNULL(uom1.CTTX40,' ') as UOM1TXT,");	
				sqlString.append(" IFNULL(uom2.CTTX40,' ') as UOM2TXT, IFNULL(uom3.CTTX40,' ') as UOM3TXT,");	
				sqlString.append(" IFNULL(uom4.CTTX40,' ') as UOM4TXT ");
				//  get the Supersedes date
				// - 11/25/13 TWalton Change how Supersedes Date is retrieved
//				sqlString.append(" (SELECT IFNULL(MAX(SHRDTE), ' ') FROM " + libraryTT + ".QAPGSPHD ");
//				sqlString.append(" WHERE SHCONO = " + specification.getCompany().trim());
//				sqlString.append(" AND SHDIVI = '" + specification.getDivision().trim() + "'");
//				sqlString.append(" AND SHSPNO = " + specification.getSpecNumber().trim());
//				if (!specification.getRevisionDate().trim().equals(""))
//					sqlString.append(" AND SHRDTE <= " + specification.getRevisionDate().trim());	
//								sqlString.append(" AND SHSTAT = 'IN') as SuperDate ");
				
				sqlString.append(" FROM " + libraryTT + ".QAPGSPHD");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPIFOHD ON");
				sqlString.append(" SHCONO = FHCONO AND SHDIVI = FHDIVI AND SHFONO = FHFONO AND");
				sqlString.append(" SHFONO > 0 AND FHSTAT = 'AC' ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".OCUSMA ON");
				sqlString.append(" SHCONO = OKCONO AND SHDIVI = OKDIVI AND SHCUNO = OKCUNO AND");
				sqlString.append(" SHCUCD = '" + categoryCodes.get(11) +"'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".SRPACUST ON");
				sqlString.append(" SHCUNO = CAST(SRANUMBER AS CHAR(10)) AND SHCUCD = '" + categoryCodes.get(12) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS ON");
				sqlString.append(" SHCONO = MMCONO and SHITNO = MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".MSPWITRS ON");
				sqlString.append(" MSWRSC = SHITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON"); // Status
				sqlString.append(" SHCONO = st.DCCONO AND SHDIVI = st.DCDIVI AND");
				sqlString.append(" SHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON"); // Type
				sqlString.append(" SHCONO = tp.DCCONO AND SHDIVI = tp.DCDIVI AND");
				sqlString.append(" SHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC sc ON"); // Scope
				sqlString.append(" SHCONO = sc.DCCONO AND SHDIVI = sc.DCDIVI AND");
				sqlString.append(" SHSCOP = sc.DCCACD AND sc.DCCAID = '" + categoryCodes.get(6) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC gp ON"); // Group
				sqlString.append(" SHCONO = gp.DCCONO AND SHDIVI = gp.DCDIVI AND");
				sqlString.append(" SHGRUP = gp.DCCACD AND gp.DCCAID = '" + categoryCodes.get(4) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC ks ON"); // Kosher Status
				sqlString.append(" SHCONO = ks.DCCONO AND SHDIVI = ks.DCDIVI AND");
				sqlString.append(" SHKSTS = ks.DCCACD AND ks.DCCAID = '" + categoryCodes.get(3) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC soc ON"); // Inline Sock
				sqlString.append(" SHCONO = soc.DCCONO AND SHDIVI = soc.DCDIVI AND");
				sqlString.append(" SHINSK = soc.DCCACD AND soc.DCCAID = '" + categoryCodes.get(7) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC ct ON"); // CIP Type
				sqlString.append(" SHCONO = ct.DCCONO AND SHDIVI = ct.DCDIVI AND");
				sqlString.append(" SHCIPT = ct.DCCACD AND ct.DCCAID = '" + categoryCodes.get(8) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC cn ON"); // Storage Condition
				sqlString.append(" SHCONO = cn.DCCONO AND SHDIVI = cn.DCDIVI AND");
				sqlString.append(" SHSREC = cn.DCCACD AND cn.DCCAID = '" + categoryCodes.get(9) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC cl ON"); // Container Code Location
				sqlString.append(" SHCONO = cl.DCCONO AND SHDIVI = cl.DCDIVI AND");
				sqlString.append(" SHCCDL = cl.DCCACD AND cl.DCCAID = '" + categoryCodes.get(10) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC ctn ON"); // Carton Code Location
				sqlString.append(" SHCONO = ctn.DCCONO AND SHDIVI = ctn.DCDIVI AND");
				sqlString.append(" SHCCTL = ctn.DCCACD AND ctn.DCCAID = '" + categoryCodes.get(18) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC lt ON"); // Pallet Label Type
				sqlString.append(" SHCONO = lt.DCCONO AND SHDIVI = lt.DCDIVI AND");
				sqlString.append(" SHPLBT = lt.DCCACD AND lt.DCCAID = '" + categoryCodes.get(13) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC pl ON"); // Pallet Label Location
				sqlString.append(" SHCONO = pl.DCCONO AND SHDIVI = pl.DCDIVI AND");
				sqlString.append(" SHPLBL = pl.DCCACD AND pl.DCCAID = '" + categoryCodes.get(14) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tt ON"); // Stretch Wrap Type
				sqlString.append(" SHCONO = tt.DCCONO AND SHDIVI = tt.DCDIVI AND");
				sqlString.append(" SHWTYP = tt.DCCACD AND tt.DCCAID = '" + categoryCodes.get(15) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC kt ON"); // Shrink Wrap Type
				sqlString.append(" SHCONO = kt.DCCONO AND SHDIVI = kt.DCDIVI AND");
				sqlString.append(" SHKTYP = kt.DCCACD AND kt.DCCAID = '" + categoryCodes.get(16) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC pr ON"); // Pallet Requirement
				sqlString.append(" SHCONO = pr.DCCONO AND SHDIVI = pr.DCDIVI AND");
				sqlString.append(" SHPREQ = pr.DCCACD AND pr.DCCAID = '" + categoryCodes.get(17) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC ki ON"); // Kosher Symbol Image
				sqlString.append(" SHCONO = ki.DCCONO AND SHDIVI = ki.DCDIVI AND");
				sqlString.append(" SHKSTS = ki.DCCACD AND ki.DCCAID = 'Kosher Image'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC ctsz ON"); // Cut Size
				sqlString.append(" SHCONO = ctsz.DCCONO AND SHDIVI = ctsz.DCDIVI AND");
				sqlString.append(" SHCUTZ = ctsz.DCCACD AND ctsz.DCCAID = 'Cut Size'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC cts2 ON"); // Cut Size 2 (Second)
				sqlString.append(" SHCONO = cts2.DCCONO AND SHDIVI = cts2.DCDIVI AND");
				sqlString.append(" SHCUT2 = cts2.DCCACD AND cts2.DCCAID = 'Cut Size'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC scsz ON"); // Screen Size
				sqlString.append(" SHCONO = scsz.DCCONO AND SHDIVI = scsz.DCDIVI AND");
				sqlString.append(" SHSRNZ = scsz.DCCACD AND scsz.DCCAID = 'Screen Size'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC fmd ON"); // Foreign Material Detection
				sqlString.append(" SHCONO = fmd.DCCONO AND SHDIVI = fmd.DCDIVI AND");
				sqlString.append(" SHFMAT = fmd.DCCACD AND fmd.DCCAID = 'Foreign Detection'");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITAUN u1 ON");
				sqlString.append(" SHCONO = u1.MUCONO AND SHITNO = u1.MUITNO AND u1.MUAUTP = 1 AND u1.MUALUN = 'PL'");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITAUN u2 ON");
				sqlString.append(" SHCONO = u2.MUCONO AND SHITNO = u2.MUITNO AND u2.MUAUTP = 1 AND u2.MUALUN = 'TIE'");		
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITPOP a1 ON");
				sqlString.append(" SHCONO = a1.MPCONO AND SHITNO = a1.MPITNO AND");
				sqlString.append(" a1.MPALWT = 3 AND a1.MPALWQ = 'LBL ' AND a1.MPE0PA = ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITPOP a2 ON");
				sqlString.append(" SHCONO = a2.MPCONO AND SHITNO = a2.MPITNO AND");
				sqlString.append(" a2.MPALWT = 2 AND a2.MPALWQ = 'UPC ' AND a2.MPE0PA = ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITPOP a3 ON");
				sqlString.append(" SHCONO = a3.MPCONO AND SHITNO = a3.MPITNO AND");
				sqlString.append(" a3.MPALWT = 2 AND a3.MPALWQ = 'GTIN' AND a3.MPE0PA = ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CSYTAB sr ON");
				sqlString.append(" sr.CTCONO = MMCONO and sr.CTDIVI = ' ' AND sr.CTSTCO = 'STCN' and MMSTCN = sr.CTSTKY ");
				sqlString.append(" AND MMSTCN <> ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CSYTAB uom1 ON"); // will be tied to the Stretch Wrap Width UOM
				sqlString.append(" uom1.CTCONO = MMCONO AND uom1.CTDIVI = ' ' AND uom1.CTSTCO = 'UNIT' ");
				sqlString.append(" AND SHWUOM = uom1.CTSTKY AND SHWUOM <> ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CSYTAB uom2 ON"); // will be tied to the Stretch Wrap Gauge UOM
				sqlString.append(" uom2.CTCONO = MMCONO AND uom2.CTDIVI = ' ' AND uom2.CTSTCO = 'UNIT' ");
				sqlString.append(" AND SHGUOM = uom2.CTSTKY AND SHGUOM <> ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CSYTAB uom3 ON"); // will be tied to the Shrink Wrap Width UOM
				sqlString.append(" uom3.CTCONO = MMCONO AND uom3.CTDIVI = ' ' AND uom3.CTSTCO = 'UNIT' ");
				sqlString.append(" AND SHKUOM = uom3.CTSTKY AND SHKUOM <> ' '");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CSYTAB uom4 ON"); // will be tied to the Shrink Wrap Thickness UOM
				sqlString.append(" uom4.CTCONO = MMCONO AND uom4.CTDIVI = ' ' AND uom4.CTSTCO = 'UNIT' ");
				sqlString.append(" AND SHTUOM = uom4.CTSTKY AND SHTUOM <> ' '");
				
				sqlString.append(where);			
				sqlString.append(" SHCONO = " + specification.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" SHDIVI = '" + specification.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" SHSPNO = " + specification.getSpecNumber().trim());
				condition = and;
								
				if ((!specification.getRevisionDate().trim().equals("")) &&
					(!specification.getRevisionTime().trim().equals("")))
				{
					sqlString.append(condition);
					sqlString.append(" SHRDTE = " + specification.getRevisionDate().trim());
					sqlString.append(condition);
					sqlString.append(" SHRTME = " + specification.getRevisionTime().trim());	
				}
				else {
					sqlString.append(condition);
					sqlString.append(" SHSTAT = 'AC'");					// Only actives with solo spec number
				}			
				
				sqlString.append(" ORDER BY SHSPNO, SHRDTE, SHRTME ");
			}
		}
		
		if (requestType.trim().equals("findSpecificationRevisionReason")) 
		{		
			DtlSpecification specification = (DtlSpecification) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(specification.getEnvironment().trim());
			if (!specification.getSpecNumber().trim().equals(""))				
			{			
				sqlString.append(" SELECT  SHCONO, SHDIVI, SHSPNO, SHRDTE, SHRTME, SHNAME, SHSTAT, ");
				sqlString.append("         SHTYPE, SHRTXT, SHDESC, SHITNO, ");
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT ");
				sqlString.append(" FROM " + libraryTT + ".QAPGSPHD");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON"); // Status
				sqlString.append(" SHCONO = st.DCCONO AND SHDIVI = st.DCDIVI AND");
				sqlString.append(" SHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON"); // Type
				sqlString.append(" SHCONO = tp.DCCONO AND SHDIVI = tp.DCDIVI AND");
				sqlString.append(" SHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");
				sqlString.append(where);
				sqlString.append(" SHCONO = " + specification.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" SHDIVI = " + specification.getDivision().trim());
				sqlString.append(and);
				sqlString.append(" SHSPNO = " + specification.getSpecNumber().trim());
				sqlString.append(" ORDER BY SHSPNO, SHRDTE DESC, SHRTME DESC");	
			}
		}
//  *********************************************************************************		
//  Insert SPECIFICATION
//  *********************************************************************************
		if (requestType.trim().equals("insertSpecificationHeader")) 
		{		
			UpdSpecification specificationHeader = (UpdSpecification) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
			
			sqlString.append("INSERT  INTO  " + libraryTT + ".QAPGSPHD ");
			sqlString.append("(SHCONO, SHDIVI, SHSPNO, SHRDTE, SHRTME,");
			sqlString.append(" SHNAME, SHSTAT, SHTYPE, SHGRUP, SHSCOP,");
			sqlString.append(" SHDESC, SHOUSR, SHAUSR, SHITNO, SHPDST,");
			sqlString.append(" SHRTXT, SHRSNO, SHRSDT, SHRSTM, SHFONO,");
			sqlString.append(" SHFODT, SHFOTM, SHCUNO, SHCUNM, SHCUCD,");
			sqlString.append(" SHKSTS, SHKSYM, SHCUTZ, SHSRNZ, SHINSK,");
			sqlString.append(" SHCIPT, SHSREC, SHCTSL, SHCCDL, SHCDFS,");
			sqlString.append(" SHCSFS, SHCSBC, SHPLBT, SHPLBL, SHWREQ,");
			sqlString.append(" SHWTYP, SHWWDH, SHWUOM, SHWGAU, SHGUOM,");
			sqlString.append(" SHKREQ, SHKTYP, SHKWDH, SHKUOM, SHKTHK,");
			sqlString.append(" SHTUOM, SHSREQ, SHSLOC, SHSTOP, SHSLY1,");
			sqlString.append(" SHSLY2, SHSLY3, SHSLY4, SHSLY5, SHSLY6,");
			sqlString.append(" SHPREQ, SHUDTE, SHUTME, SHUUSR, SHCDTE,");
			sqlString.append(" SHCTME, SHCUSR, SHSDTE, SHSTME, SHTBRX,");
			sqlString.append(" SHUNTP, SHUNTL, SHFMAT, SHCUT2, SHCCTL,");
			sqlString.append(" SHCTFS, SHCORG, SHRCON, SHSLCB, SHSLY7,");
			sqlString.append(" SHSLY8, SHSLY9, SHSLY10 )");
			
			sqlString.append(" VALUES(?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,? )");
		}
	
//  *************************************************************************************
//  List SPECIFICATION
//  *************************************************************************************
		if (requestType.trim().equals("listSpecification")) 
		{		// 11/21/11 TWalton not currently using the Detail Section	
			String whereHeader = buildSqlWhereClause("listSpecificationHeader", requestClass);
			//String whereDetail = buildSqlWhereClause("listSpecificationDetail", requestClass);
				
			InqSpecification specification = (InqSpecification) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(specification.getEnvironment().trim());
			String libraryM3 = GeneralUtility.getLibrary(specification.getEnvironment().trim());
				
			sqlString.append(" SELECT  SHCONO, SHDIVI, SHSPNO, SHRDTE, SHRTME, SHNAME, SHSTAT, SHTYPE,");
			sqlString.append(" SHGRUP, SHSCOP, SHDESC, SHOUSR, SHAUSR, SHITNO, SHPDST, SHRTXT, SHRSNO,");
			sqlString.append(" SHRSDT, SHRSTM, SHFONO, SHFODT, SHFOTM, SHCUNO, SHCUNM, SHCUCD, SHUDTE,");
			sqlString.append(" SHUTME, SHUUSR, SHCDTE, SHCTME, SHCUSR, SHSDTE, SHSTME, SHTBRX, ");
			sqlString.append(" SHCORG, SHRCON, SHSLCB, ");
			sqlString.append(" IFNULL(FHNAME,' ') as FNAME, IFNULL(FHDESC,' ') as FDESC,");
			sqlString.append(" IFNULL(FHRDTE,'0') as FDATE, IFNULL(FHRTME,'0') as FTIME,");
			
			sqlString.append(" IFNULL(OKCUNM,' ') as M3CUNM, IFNULL(SRANAME,' ') as SACUNM,");
			sqlString.append(" IFNULL(MMITDS,' ') as IDESC,");
				
			sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT,");
			sqlString.append(" IFNULL(sc.DCTEXT,' ') as SCTEXT, IFNULL(gp.DCTEXT,' ') as GPTEXT ");				
				
//			if ((!whereDetail.trim().equals("")) ||
//				(specification.getInqTemplate().trim().equals("QaSpecificationDetail")))
//			{	
//				sqlString.append(",");
//				sqlString.append(" SDSEQ#, SDITNO, SDITDS, SDCPSP, SDCPDT, SDCPTM, SDCDTE, SDCTME, SDCUSR,");
//				sqlString.append(" IFNULL(MMITDS,' ') as DTITDS,");
//				sqlString.append(" IFNULL(CAST(SDSEQ# AS CHAR(4)),'null') as DTSEQ#,");
//				sqlString.append(" IFNULL(CAST(SDSEQ# AS CHAR(4)),'null') as PKSEQ#");				
//			}
			sqlString.append(" FROM " + libraryTT + ".QAPGSPHD AS A ");
			sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPIFOHD ON");
			sqlString.append(" SHCONO = FHCONO AND SHDIVI = FHDIVI AND SHFONO = FHFONO AND");
			sqlString.append(" SHFONO > 0 AND FHSTAT = 'AC' ");
			sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".OCUSMA ON");
			sqlString.append(" SHCONO = OKCONO AND SHDIVI = OKDIVI AND SHCUNO = OKCUNO AND");
			sqlString.append(" SHCUCD = '" + categoryCodes.get(11) +"'");
			sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".SRPACUST ON");
			sqlString.append(" SHCUNO = CAST(SRANUMBER AS CHAR(10)) AND SHCUCD = '" + categoryCodes.get(12) + "'");
			sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS ON");
			sqlString.append(" SHCONO = MMCONO and SHITNO = MMITNO ");
			sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON"); // Status
			sqlString.append(" SHCONO = st.DCCONO AND SHDIVI = st.DCDIVI AND");
			sqlString.append(" SHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
			sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON"); // Type
			sqlString.append(" SHCONO = tp.DCCONO AND SHDIVI = tp.DCDIVI AND");
			sqlString.append(" SHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");
			sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC sc ON"); // Scope
			sqlString.append(" SHCONO = sc.DCCONO AND SHDIVI = sc.DCDIVI AND");
			sqlString.append(" SHSCOP = sc.DCCACD AND sc.DCCAID = '" + categoryCodes.get(6) + "'");
			sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC gp ON"); // Group
			sqlString.append(" SHCONO = gp.DCCONO AND SHDIVI = gp.DCDIVI AND");
			sqlString.append(" SHGRUP = gp.DCCACD AND gp.DCCAID = '" + categoryCodes.get(4) + "'");
			
//			if ((!whereDetail.trim().equals("")) ||
//				(specification.getInqTemplate().trim().equals("QaSpecificationDetail")))
//			{	
//				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPHSPDT ON");
//				sqlString.append(" SHCONO = SDCONO AND SHDIVI = SDDIVI AND");
//				sqlString.append(" SHSPNO = SDSPNO AND SHRDTE = SDRDTE AND SHRTME = SDRTME");
//				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS ON");
//				sqlString.append(" SDCONO = MMCONO AND SDITNO = MMITNO");
//			}
			
			if (!whereHeader.trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(whereHeader);
				condition = and;
			}
//			if (!whereDetail.trim().equals(""))
//			{
//				sqlString.append(condition);
//				sqlString.append(whereDetail);
//			}
			
			
			if (!specification.getInqWarehouse().trim().equals("")) {
				
				sqlString.append(" AND EXISTS ( ");
				sqlString.append(" SELECT 1 ");
				sqlString.append(" FROM " + libraryTT + ".QAPGSPHD AS B ");
				sqlString.append(" INNER JOIN " + libraryM3 + ".MITBAL ON ");
				sqlString.append("       B.SHCONO=MBCONO AND MBITNO=SHITNO  AND MBPUIT='1' ");
				sqlString.append(" INNER JOIN " + libraryM3 + ".MITWHL ON ");
				sqlString.append("       B.SHCONO=MWCONO AND MBWHLO=MWWHLO ");
				sqlString.append("       AND MWWHTY='10' ");
				sqlString.append(" INNER JOIN " + libraryM3 + ".MPDHED ON ");
				sqlString.append("       B.SHCONO = PHCONO AND MBFACI = PHFACI ");
				sqlString.append("       AND MBITNO = PHPRNO AND PHSTAT <> '90' ");
				sqlString.append(" WHERE B.SHCONO=A.SHCONO AND B.SHDIVI=A.SHDIVI ");
				sqlString.append(" AND A.SHITNO=B.SHITNO ");
				sqlString.append(" AND MWWHLO='" + specification.getInqWarehouse().trim() + "' ");
				sqlString.append(" ) ");
				
			}
			
			orderBy = buildSqlOrderBy("sortSpecification", 
					                   specification.getOrderBy(), specification.getOrderStyle());
			sqlString.append(orderBy);
		}
//	************************************************************************************
//	Update SPECIFICATION
//	************************************************************************************
		if (requestType.trim().equals("updateSpecificationStatusInactive")) 
		{
			UpdSpecification specificationHeader = (UpdSpecification) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
			
			if (!specificationHeader.getSpecNumber().trim().equals(""))				
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPGSPHD");			
				sqlString.append(" SET");
				sqlString.append(" SHSTAT = 'IN'");
				sqlString.append(where);
				sqlString.append(" SHCONO = " + specificationHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" SHDIVI = '" + specificationHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" SHSPNO = " + specificationHeader.getSpecNumber().trim());
				sqlString.append(and);
				sqlString.append(" SHSTAT = 'AC'");
			}
		}
		
		if (requestType.trim().equals("updateSpecificationStatus")) 
		{
			UpdSpecification specificationHeader = (UpdSpecification) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
			
			if ((!specificationHeader.getSpecNumber().trim().equals("")) &&
				(!specificationHeader.getRevisionDate().trim().equals("")) &&
				(!specificationHeader.getRevisionTime().trim().equals("")))
			{			
				sqlString.append(" UPDATE " + libraryTT + ".QAPGSPHD");			
				sqlString.append(" SET");
				sqlString.append(" SHSTAT = '" + specificationHeader.getStatus().trim() + "'");
				if (specificationHeader.originalStatus.trim().equals("PD") &&
					specificationHeader.getStatus().trim().equals("AC"))
				{ // Reset the Revision Date and Time
					sqlString.append(" , SHRDTE = " + specificationHeader.getUpdateDate());
					sqlString.append(" , SHRTME = " + specificationHeader.getUpdateTime());
				}
				sqlString.append(where);
				sqlString.append(" SHCONO = " + specificationHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" SHDIVI = '" + specificationHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" SHSPNO = " + specificationHeader.getSpecNumber().trim());
				sqlString.append(and);
				sqlString.append(" SHRDTE = " + specificationHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" SHRTME = " + specificationHeader.getRevisionTime().trim());
				}
		}
//	************************************************************************************
//	Verify SPECIFICATION
//	************************************************************************************			
		if (requestType.trim().equals("verifySpecificationNumber"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append(" SELECT SHSPNO");
			sqlString.append(" FROM " + libraryTT + ".QAPGSPHD");						
			sqlString.append(where);
			sqlString.append(" SHCONO = " + commonRequest.getCompanyNumber().trim());
			sqlString.append(and);
			sqlString.append(" SHDIVI = " + commonRequest.getDivisionNumber().trim());
			sqlString.append(and);
			sqlString.append(" SHSPNO = " + commonRequest.getIdLevel1().trim());						
		}
			
		if (requestType.trim().equals("verifySpecificationIndex"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append(" SELECT SHSPNO");
			sqlString.append(" FROM " + libraryTT + ".QAPGSPHD");				
			sqlString.append(where);
			sqlString.append(" SHCONO = " + commonRequest.getCompanyNumber().trim());
			sqlString.append(and);
			sqlString.append(" SHDIVI = " + commonRequest.getDivisionNumber().trim());
			sqlString.append(and);
			sqlString.append(" SHSPNO = " + commonRequest.getIdLevel1().trim());
			sqlString.append(and);
			sqlString.append(" SHRDTE = " + commonRequest.getDate().trim());
			sqlString.append(and);
			sqlString.append(" SHRTME = " + commonRequest.getTime().trim());
		}
//  *************************************************************************************
//	Delete FORMULA
//  *************************************************************************************		
		if (requestType.trim().equals("deleteFormulaHeader")) 
		{		
			UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
			
			if ((!formulaHeader.getFormulaNumber().trim().trim().equals("")) &&
			    (!formulaHeader.getRevisionDate().trim().trim().equals(""))  &&
			    (!formulaHeader.getRevisionTime().trim().trim().equals("")))	
			{	
				sqlString.append("DELETE FROM " + libraryTT + ".QAPIFOHD");
				//sqlString.append("DELETE FROM JHAGLE.QAPIFOHD");
				sqlString.append(where);
				sqlString.append(" FHCONO = " + formulaHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FHDIVI = '" + formulaHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FHFONO = " + formulaHeader.getFormulaNumber().trim());
				sqlString.append(and);
				sqlString.append(" FHRDTE = " + formulaHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" FHRTME = " + formulaHeader.getRevisionTime().trim());
			}
		}
			
		if (requestType.trim().equals("deleteFormulaDetail")) 
		{	
			UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
			
			if ((!formulaHeader.getFormulaNumber().trim().trim().equals("")) &&
			    (!formulaHeader.getRevisionDate().trim().trim().equals(""))  &&
			    (!formulaHeader.getRevisionTime().trim().trim().equals("")))	
			{	
				sqlString.append("DELETE FROM " + libraryTT + ".QAPJFODT");
				sqlString.append(where);
				sqlString.append(" FDCONO = " + formulaHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FDDIVI = '" + formulaHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FDFONO = " + formulaHeader.getFormulaNumber().trim());
				sqlString.append(and);
				sqlString.append(" FDRDTE = " + formulaHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" FDRTME = " + formulaHeader.getRevisionTime().trim());
			}
		}
//  *************************************************************************************
//	Find FORMULA
//  *************************************************************************************		
		if (requestType.trim().equals("findFormula")) 
		{	
			
			DtlFormula formula = (DtlFormula) requestClass.elementAt(0);
			String libraryM3 = GeneralUtility.getLibrary(formula.getEnvironment().trim());
			String libraryTT = GeneralUtility.getTTLibrary(formula.getEnvironment().trim());
			
			if (!formula.getFormulaNumber().trim().equals(""))
			{						
				sqlString.append(" SELECT  FHCONO, FHDIVI, FHSTAT, FHTYPE, FHOUSR, FHGRUP, FHFONO, FHRDTE, FHRTME,");
				sqlString.append(" FHLTNO, FHQTTY, FHUNMS, FHBRIX, FHCUNO, FHCUNM, FHCUCD, FHRFNO, FHRFDT, FHRFTM,");
				sqlString.append(" FHDESC, FHPDST, FHRTXT, FHUDTE, FHUTME, FHUUSR, FHCDTE, FHCTME, FHCUSR, FHSDTE,");
				sqlString.append(" FHSTME, FHAUSR, FHORIG, FHPQTY, FHPUOM, FHNAME, FHSCOP, FHCUIT, ");
				sqlString.append(" IFNULL(MMITDS,' ') as ITEMDESC,");
				sqlString.append(" IFNULL(OKCUNM,' ') as M3CUNM, IFNULL(SRANAME,' ') as SACUNM,");
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT,");
				sqlString.append(" IFNULL(so.DCTEXT,' ') as SOTEXT, IFNULL(gp.DCTEXT,' ') as GPTEXT ");
			//  get the Supersedes date
//				- 11/25/13 TWalton Change how Supersedes Date is retrieved
//				sqlString.append(" (SELECT IFNULL(MAX(FHRDTE), ' ') FROM " + libraryTT + ".QAPIFOHD ");
//				sqlString.append(" WHERE FHCONO = " + formula.getCompany().trim());
//				sqlString.append(" AND FHDIVI = '" + formula.getDivision().trim() + "'");
//				sqlString.append(" AND FHFONO = " + formula.getFormulaNumber().trim());
//				if (!formula.getRevisionDate().trim().equals(""))
//					sqlString.append(" AND FHRDTE <= " + formula.getRevisionDate().trim());	
//				sqlString.append(" AND FHSTAT = 'IN') as SuperDate ");
				
				
				sqlString.append(" FROM " + libraryTT + ".QAPIFOHD");
				
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".OCUSMA ON");
				sqlString.append(" FHCONO = OKCONO AND FHDIVI = OKDIVI AND FHCUNO = OKCUNO AND");
				sqlString.append(" FHCUCD = '" + categoryCodes.get(11) +"'");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS ON");
				sqlString.append(" FHCONO = MMCONO AND FHLTNO = MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".SRPACUST ON");
				sqlString.append(" FHCUNO = CAST(SRANUMBER AS CHAR(10)) AND FHCUCD = '" + categoryCodes.get(12) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON");
				sqlString.append(" FHCONO = st.DCCONO AND FHDIVI = st.DCDIVI AND");
				sqlString.append(" FHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON");
				sqlString.append(" FHCONO = tp.DCCONO AND FHDIVI = tp.DCDIVI AND");
				sqlString.append(" FHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC so ON");
				sqlString.append(" FHCONO = so.DCCONO AND FHDIVI = so.DCDIVI AND");
				sqlString.append(" FHSCOP = so.DCCACD AND so.DCCAID = '" + categoryCodes.get(6) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC gp ON");
				sqlString.append(" FHCONO = gp.DCCONO AND FHDIVI = gp.DCDIVI AND");
				sqlString.append(" FHGRUP = gp.DCCACD AND gp.DCCAID = '" + categoryCodes.get(4) + "'");
				
				sqlString.append(where);			
				sqlString.append(" FHCONO = " + formula.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FHDIVI = '" + formula.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FHFONO = " + formula.getFormulaNumber().trim());
				condition = and;
								
				if ((!formula.getRevisionDate().trim().equals("")) &&
					(!formula.getRevisionTime().trim().equals("")))
				{
					sqlString.append(condition);
					sqlString.append(" FHRDTE = " + formula.getRevisionDate().trim());
					sqlString.append(condition);
					sqlString.append(" FHRTME = " + formula.getRevisionTime().trim());	
				}
				else {
					sqlString.append(condition);
					sqlString.append(" FHSTAT = 'AC'");					// Only actives with solo spec number
				}			
				
				sqlString.append(" ORDER BY FHFONO, FHRDTE DESC, FHRTME DESC");
			}
		}
		// Detail Sections -- will return LIST
		if (requestType.trim().equals("findFormulaPreBlend") ||
			requestType.trim().equals("findFormulaProduction") ||
			requestType.trim().equals("findFormulaPreBlendSauce")) 
		{		
			DtlFormula formula = (DtlFormula) requestClass.elementAt(0);	
			String libraryM3 = GeneralUtility.getLibrary(formula.getEnvironment().trim());
			String libraryTT = GeneralUtility.getTTLibrary(formula.getEnvironment().trim());
			
			if (!formula.getFormulaNumber().trim().equals("")) 				
			{						 
				sqlString.append(" SELECT  FHCONO, FHDIVI, FHSTAT, FHTYPE,  FHOUSR,  FHGRUP, FHFONO, FHRDTE,  FHRTME,");
				sqlString.append(" FHLTNO, FHQTTY, FHUNMS, FHBRIX, FHCUNO,  FHCUNM,  FHCUCD, FHRFNO, FHRFDT,  FHRFTM,");
				sqlString.append(" FHDESC, FHPDST, FHRTXT, FHUDTE, FHUTME,  FHUUSR,  FHCDTE, FHCTME, FHCUSR,  FHSDTE,");
				sqlString.append(" FHSTME, FHAUSR, FHORIG, FHPQTY, FHPUOM,  FHNAME,  FHSCOP, ");
				sqlString.append(" FDCONO, FDDIVI, FDFONO, FDCODE, FDBRIX,  FDQTTY,  FDBUOM, FDRDTE, FDRTME,  FDSEQ#, ");
				sqlString.append(" FDITNO, FDITDS, FDQTY1, FDUOM1, FDITNO2, FDITDS2, FDQTY2, FDUOM2, FDITNO3, FDITDS3, ");
				sqlString.append(" FDQTY3, FDUOM3, FDSUNO, FDSUNM, FDRSNO, ");
				sqlString.append(" IFNULL(itnoHDR.MMITDS,' ') as ITEMDESC, ");
				sqlString.append(" IFNULL(itno1.MMITDS, ' ') as D1ITDS, IFNULL(itno1.MMUNMS, ' ') as D1UNMS, ");
				sqlString.append(" IFNULL(itno2.MMITDS, ' ') as D2ITDS, IFNULL(itno2.MMUNMS, ' ') as D2UNMS, ");
				sqlString.append(" IFNULL(itno3.MMITDS, ' ') as D3ITDS, IFNULL(itno3.MMUNMS, ' ') as D3UNMS, ");
				sqlString.append(" IFNULL(IDSUNM, ' ') as CIDSUNM ");
				
				sqlString.append(" FROM " + libraryTT + ".QAPIFOHD");
				sqlString.append(" INNER JOIN " + libraryTT + ".QAPJFODT ON ");
				sqlString.append(" FHCONO = FDCONO AND FHDIVI = FDDIVI AND FHFONO = FDFONO AND ");
				sqlString.append(" FHRDTE = FDRDTE AND FHRTME = FDRTME ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS itnoHDR ON ");
				sqlString.append(" FHCONO = itnoHDR.MMCONO AND FHLTNO = itnoHDR.MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS itno1 ON ");
				sqlString.append(" FHCONO = itno1.MMCONO AND FDITNO = itno1.MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS itno2 ON ");
				sqlString.append(" FHCONO = itno2.MMCONO AND FDITNO2 = itno2.MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS itno3 ON ");
				sqlString.append(" FHCONO = itno3.MMCONO AND FDITNO3 = itno3.MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CIDMAS ON ");
				sqlString.append(" FHCONO = IDCONO AND FDSUNO = IDSUNO ");
				
				sqlString.append(where);
				sqlString.append(" FHCONO = " + formula.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FHDIVI = '" + formula.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FHFONO = " + formula.getFormulaNumber().trim());
				sqlString.append(and);
				sqlString.append(" FHRDTE = " + formula.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" FHRTME = " + formula.getRevisionTime().trim());
				sqlString.append(and);
				sqlString.append(" FDCODE = '");
				if (requestType.trim().equals("findFormulaPreBlend"))
					sqlString.append("PREBL");
				if (requestType.trim().equals("findFormulaProduction"))
					sqlString.append("PROD");
				if(requestType.trim().equals("findFormulaPreBlendSauce"))
					sqlString.append("SAUCE");
				sqlString.append("' ");
				
				sqlString.append(" ORDER BY FDFONO, FDRDTE DESC, FDRTME DESC, FDSEQ# ");
			}				
		}
		
		if (requestType.trim().equals("findFormulaRevisionReason")) 
		{		
			DtlFormula formula = (DtlFormula) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(formula.getEnvironment().trim());
			
			if (!formula.getFormulaNumber().trim().equals("")) 				
			{						 
				sqlString.append(" SELECT  FHCONO, FHDIVI, FHFONO, FHRDTE, FHRTME, FHSTAT, FHRTXT, FHDESC,");				
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT");
				sqlString.append(" FROM " + libraryTT + ".QAPIFOHD");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON");
				sqlString.append(" FHCONO = st.DCCONO AND FHDIVI = st.DCDIVI AND");
				sqlString.append(" FHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(where);
				sqlString.append(" FHCONO = " + formula.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FHDIVI = '" + formula.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FHFONO = " + formula.getFormulaNumber().trim());				
				sqlString.append(" ORDER BY FHFONO, FHRDTE DESC, FHRTME DESC");
			}				
		}
//  *********************************************************************************		
//  Insert FORMULA
//  *********************************************************************************
		if (requestType.trim().equals("insertFormulaHeader")) 
		{		
			UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
			
			sqlString.append("INSERT  INTO  " + libraryTT + ".QAPIFOHD ");
			sqlString.append("(FHCONO, FHDIVI, FHSTAT, FHTYPE, FHOUSR,");
			sqlString.append(" FHGRUP, FHFONO, FHRDTE, FHRTME, FHLTNO,");
			sqlString.append(" FHQTTY, FHUNMS, FHBRIX, FHCUNO, FHCUNM,");
			sqlString.append(" FHCUCD, FHRFNO, FHRFDT, FHRFTM, FHDESC,");
			sqlString.append(" FHPDST, FHRTXT, FHUDTE, FHUTME, FHUUSR,");
			sqlString.append(" FHCDTE, FHCTME, FHCUSR, FHSDTE, FHSTME,");
			sqlString.append(" FHAUSR, FHORIG, FHPQTY, FHPUOM, FHNAME,");
			sqlString.append(" FHSCOP, FHCUIT) VALUES(?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?)");
		}
		
		if (requestType.trim().equals("insertFormulaDetail")) 
		{			
			UpdFormulaDetail formulaDetail = (UpdFormulaDetail) requestClass.elementAt(0);				
			String libraryTT = GeneralUtility.getTTLibrary(formulaDetail.getEnvironment().trim());		
			
			sqlString.append("INSERT  INTO  " + libraryTT + ".QAPJFODT ");
			sqlString.append("(FDCONO, FDDIVI, FDFONO, FDCODE, FDBRIX,");
			sqlString.append(" FDQTTY, FDBUOM, FDRDTE, FDRTME, FDSEQ#,");
			sqlString.append(" FDITNO, FDITDS, FDQTY1, FDUOM1, FDITNO2,");
			sqlString.append(" FDITDS2, FDQTY2, FDUOM2, FDITNO3, FDITDS3,");
			sqlString.append(" FDQTY3, FDUOM3, FDSUNO, FDSUNM, FDRSNO)");
			sqlString.append(" VALUES(?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?)");
		}
//  *************************************************************************************
//	List FORMULA
//  *************************************************************************************
		if (requestType.trim().equals("listFormula")) 
		{
			String whereHeader = buildSqlWhereClause("listFormulaHeader", requestClass);
		//	String whereDetail = buildSqlWhereClause("listFormulaDetail", requestClass);
			
			InqFormula formula = (InqFormula) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(formula.getEnvironment().trim());
			String libraryM3 = GeneralUtility.getLibrary(formula.getEnvironment().trim());		
			
				sqlString.append(" SELECT  FHCONO, FHDIVI, FHSTAT, FHTYPE, FHOUSR, FHGRUP, FHFONO, FHRDTE, FHRTME,");
				sqlString.append(" FHLTNO, FHQTTY, FHUNMS, FHBRIX, FHCUNO, FHCUNM, FHCUCD, FHRFNO, FHRFDT, FHRFTM,");
				sqlString.append(" FHDESC, FHPDST, FHRTXT, FHUDTE, FHUTME, FHUUSR, FHCDTE, FHCTME, FHCUSR, FHSDTE,");
				sqlString.append(" FHSTME, FHAUSR, FHORIG, FHPQTY, FHPUOM, FHNAME, FHSCOP, FHCUIT, ");
				sqlString.append(" IFNULL(itemHdr.MMITDS,' ') as ITEMDESC,");
				sqlString.append(" IFNULL(OKCUNM,' ') as M3CUNM, IFNULL(SRANAME,' ') as SACUNM,");
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT,");
				sqlString.append(" IFNULL(so.DCTEXT,' ') as SOTEXT, IFNULL(gp.DCTEXT,' ') as GPTEXT ");				
			
			if (!formula.getInqSupplierNumber().trim().equals("") ||
			    !formula.getInqSupplierName().trim().equals(""))
			{	
				sqlString.append(",");
				sqlString.append(" FDSEQ#, FDITNO, FDITDS, FDQTY1, FDUOM1, FDQTY2, FDUOM2, FDSUNO, FDSUNM, FDRSNO,");				
				sqlString.append(" IFNULL(itemDtl.MMITDS,' ') as DTITDS, IFNULL(itemDtl.MMUNMS,' ') as DTUNMS,");
				sqlString.append(" IFNULL(IDSUNM,' ') as DTSUNM, IFNULL(CAST(FDSEQ# as CHAR(4)),'null') as DTSEQ#");				
			}			
				
				sqlString.append(" FROM " + libraryTT + ".QAPIFOHD");
				//sqlString.append(" FROM JHAGLE.QAPIFOHD");
				
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS itemHdr ON ");
				sqlString.append(" FHCONO = itemHdr.MMCONO AND FHLTNO = itemHdr.MMITNO ");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".OCUSMA ON");
				sqlString.append(" FHCONO = OKCONO AND FHDIVI = OKDIVI AND FHCUNO = OKCUNO AND");
				sqlString.append(" FHCUCD = '" + categoryCodes.get(11) +"'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".SRPACUST ON");
				sqlString.append(" FHCUNO = CAST(SRANUMBER AS CHAR(10)) AND FHCUCD = '" + categoryCodes.get(12) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON");
				sqlString.append(" FHCONO = st.DCCONO AND FHDIVI = st.DCDIVI AND");
				sqlString.append(" FHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON");
				sqlString.append(" FHCONO = tp.DCCONO AND FHDIVI = tp.DCDIVI AND");
				sqlString.append(" FHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC so ON");
				sqlString.append(" FHCONO = so.DCCONO AND FHDIVI = so.DCDIVI AND");
				sqlString.append(" FHSCOP = so.DCCACD AND so.DCCAID = '" + categoryCodes.get(6) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT+ ".QAPMDESC gp ON");
				sqlString.append(" FHCONO = gp.DCCONO AND FHDIVI = gp.DCDIVI AND");
				sqlString.append(" FHGRUP = gp.DCCACD AND gp.DCCAID = '" + categoryCodes.get(4) + "'");
			
			if (!formula.getInqSupplierNumber().trim().equals("") ||
			    !formula.getInqSupplierName().trim().equals(""))
			{			
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPJFODT ON");			
				sqlString.append(" FHCONO = FDCONO AND FHDIVI = FDDIVI AND");
				sqlString.append(" FHFONO = FDFONO AND FHRDTE = FDRDTE AND FHRTME = FDRTME");																																		
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MITMAS itemDtl ON");
				sqlString.append(" FDCONO = itemDtl.MMCONO AND FDITNO = itemDtl.MMITNO");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CIDMAS ON");
				sqlString.append(" FDCONO = IDCONO AND FDSUNO = IDSUNO");
			}
							
			if (!whereHeader.trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(whereHeader);
				condition = and;
			}
	//		if (!whereDetail.trim().equals(""))
	//		{
	//			sqlString.append(condition);
	//			sqlString.append(whereDetail);
	//		}
			
			orderBy = buildSqlOrderBy("sortFormula", formula.getOrderBy(), formula.getOrderStyle());	      
			sqlString.append(orderBy);
		}
//	************************************************************************************
//	Update FORMULA
//	************************************************************************************
		if (requestType.trim().equals("updateFormulaStatusInactive")) 
		{
			UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
			
			if (!formulaHeader.getFormulaNumber().trim().equals(""))				
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPIFOHD");			
				sqlString.append(" SET");
				sqlString.append(" FHSTAT = 'IN'");
				sqlString.append(where);
				sqlString.append(" FHCONO = " + formulaHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FHDIVI = '" + formulaHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FHFONO = " + formulaHeader.getFormulaNumber().trim());
				sqlString.append(and);
				sqlString.append(" FHSTAT = 'AC'");
			}
		}
		
		if (requestType.trim().equals("updateFormulaStatus")) 
		{
			UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
			
			if ((!formulaHeader.getFormulaNumber().trim().equals("")) &&
				(!formulaHeader.getRevisionDate().trim().equals("")) &&
				(!formulaHeader.getRevisionTime().trim().equals("")))
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPIFOHD");			
				sqlString.append(" SET");
				sqlString.append(" FHSTAT = '" + formulaHeader.getStatus().trim() + "'");
				if (formulaHeader.originalStatus.trim().equals("PD") &&
						formulaHeader.getStatus().trim().equals("AC"))
				{ // Reset the Revision Date and Time
					sqlString.append(" , FHRDTE = " + formulaHeader.getUpdateDate());
					sqlString.append(" , FHRTME = " + formulaHeader.getUpdateTime());
				}
				sqlString.append(where);
				sqlString.append(" FHCONO = " + formulaHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FHDIVI = '" + formulaHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FHFONO = " + formulaHeader.getFormulaNumber().trim());
					sqlString.append(and);
				sqlString.append(" FHRDTE = " + formulaHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" FHRTME = " + formulaHeader.getRevisionTime().trim());
			}
		}
		if (requestType.trim().equals("updateFormulaDetailRevisionDate")) 
		{
			UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
			
			if ((!formulaHeader.getFormulaNumber().trim().equals("")) &&
				(!formulaHeader.getRevisionDate().trim().equals("")) &&
				(!formulaHeader.getRevisionTime().trim().equals("")))
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPJFODT");			
				sqlString.append(" SET");
				sqlString.append(" FDRDTE = " + formulaHeader.getUpdateDate());
				sqlString.append(" , FDRTME = " + formulaHeader.getUpdateTime());
				sqlString.append(where);
				sqlString.append(" FDCONO = " + formulaHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" FDDIVI = '" + formulaHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" FDFONO = " + formulaHeader.getFormulaNumber().trim());
				sqlString.append(and);
				sqlString.append(" FDRDTE = " + formulaHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" FDRTME = " + formulaHeader.getRevisionTime().trim());
			}
		}
//	************************************************************************************
//	Verify FORMULA
//	************************************************************************************			
		if (requestType.trim().equals("verifyFormulaNumber"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append(" SELECT FHFONO");
			sqlString.append(" FROM " + libraryTT + ".QAPIFOHD");						
			sqlString.append(where);
			sqlString.append(" FHCONO = " + commonRequest.getCompanyNumber().trim());
			sqlString.append(and);
			sqlString.append(" FHDIVI = " + commonRequest.getDivisionNumber().trim());
			sqlString.append(and);
			sqlString.append(" FHFONO = " + commonRequest.getIdLevel1().trim());								
		}
			
		if (requestType.trim().equals("verifyFormulaIndex"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append(" SELECT FHFONO");
			sqlString.append(" FROM " + libraryTT + ".QAPIFOHD");						
			sqlString.append(where);
			sqlString.append(" FHCONO = " + commonRequest.getCompanyNumber().trim());
			sqlString.append(and);
			sqlString.append(" FHDIVI = " + commonRequest.getDivisionNumber().trim());
			sqlString.append(and);
			sqlString.append(" FHFONO = " + commonRequest.getIdLevel1().trim());
			sqlString.append(and);
			sqlString.append(" FHRDTE = " + commonRequest.getDate().trim());
			sqlString.append(and);
			sqlString.append(" FHRTME = " + commonRequest.getTime().trim());
		}
//  *************************************************************************************
//	Delete METHOD 
//  *************************************************************************************		
		if (requestType.trim().equals("deleteMethodHeader")) 
		{		
			UpdMethod methodHeader = (UpdMethod) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(methodHeader.getEnvironment().trim());
			
			if ((!methodHeader.getMethodNumber().trim().equals("")) &&
			    (!methodHeader.getRevisionDate().trim().equals("")) &&
			    (!methodHeader.getRevisionTime().trim().equals("")))	
			{	
				sqlString.append("DELETE FROM " + libraryTT + ".QAPKMEHD");
				sqlString.append(where);
				sqlString.append(" MHCONO = " + methodHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" MHDIVI = '" + methodHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" MHMENO = " + methodHeader.getMethodNumber().trim());
				sqlString.append(and);
				sqlString.append(" MHRDTE = " + methodHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" MHRTME = " + methodHeader.getRevisionTime().trim());
			}
		}
//  *************************************************************************************
//	Find METHOD
//  *************************************************************************************		
		if (requestType.trim().equals("findMethod")) 
		{		
			DtlMethod method = (DtlMethod) requestClass.elementAt(0);				
			String libraryTT = GeneralUtility.getTTLibrary(method.getEnvironment().trim());
			
			if (!method.getMethodNumber().trim().equals(""))
			{			
				sqlString.append(" SELECT  MHCONO, MHDIVI, MHSTAT, MHTYPE, MHOUSR, MHGRUP, MHSCOP, MHMENO,");
				sqlString.append(" MHRDTE, MHRTME, MHNAME, MHDESC, MHRTXT, MHUDTE, MHUTME, MHUUSR, MHCDTE,");	
				sqlString.append(" MHCTME, MHCUSR, MHSDTE, MHSTME, MHAUSR, "); //08/04/11 tw - add MHAUSR	
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT,");
				sqlString.append(" IFNULL(gp.DCTEXT,' ') as GPTEXT, IFNULL(sp.DCTEXT,' ') as SPTEXT ");
			//  get the Supersedes date
			//	- 11/25/13 TWalton Change how Supersedes Date is retrieved
			//	sqlString.append(" (SELECT IFNULL(MAX(MHRDTE), ' ') FROM " + libraryTT + ".QAPKMEHD ");
			//	sqlString.append(" WHERE MHCONO = " + method.getCompany().trim());
			//	sqlString.append(" AND MHDIVI = '" + method.getDivision().trim() + "'");
			//	sqlString.append(" AND MHMENO = " + method.getMethodNumber().trim());
			//	if (!method.getRevisionDate().trim().equals(""))
			//		sqlString.append(" AND MHRDTE <= " + method.getRevisionDate().trim());	
			//	sqlString.append(" AND MHSTAT = 'IN') as SuperDate ");
								
				sqlString.append(" FROM " + libraryTT + ".QAPKMEHD");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON");
				sqlString.append(" MHCONO = st.DCCONO AND MHDIVI = st.DCDIVI AND");
				sqlString.append(" MHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON");
				sqlString.append(" MHCONO = tp.DCCONO AND MHDIVI = tp.DCDIVI AND");
				sqlString.append(" MHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");
//				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC so ON");
//				sqlString.append(" MHCONO = so.DCCONO AND MHDIVI = so.DCDIVI AND");
//				sqlString.append(" MHSRCE = so.DCCACD AND so.DCCAID = '" + categoryCodes.get(3) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC gp ON");
				sqlString.append(" MHCONO = gp.DCCONO AND MHDIVI = gp.DCDIVI AND");
				sqlString.append(" MHGRUP = gp.DCCACD AND gp.DCCAID = '" + categoryCodes.get(4) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC sp ON");
				sqlString.append(" MHCONO = sp.DCCONO AND MHDIVI = sp.DCDIVI AND");
				sqlString.append(" MHSCOP = sp.DCCACD AND sp.DCCAID = '" + categoryCodes.get(6) + "'");

				sqlString.append(where);			
				sqlString.append(" MHCONO = " + method.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" MHDIVI = '" + method.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" MHMENO = " + method.getMethodNumber().trim());
				condition = and;
				
				if ((!method.getRevisionDate().trim().equals("")) &&
					(!method.getRevisionTime().trim().equals("")))
				{
					sqlString.append(condition);
					sqlString.append(" MHRDTE = " + method.getRevisionDate().trim());
					sqlString.append(condition);
					sqlString.append(" MHRTME = " + method.getRevisionTime().trim());	
				}
				else {
					sqlString.append(condition);
					sqlString.append(" MHSTAT = 'AC'");					// Only actives with solo spec number
				}			
			
				sqlString.append(" ORDER BY MHMENO, MHRDTE DESC, MHRTME DESC");			
			}
		}
		
		if (requestType.trim().equals("findMethodRevisionReason")) 
		{		
			DtlMethod method = (DtlMethod) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(method.getEnvironment().trim());
			
			if (!method.getMethodNumber().trim().equals(""))				
			{			
				sqlString.append(" SELECT  MHCONO, MHDIVI, MHMENO, MHRDTE, MHRTME, MHSTAT, MHRTXT, MHDESC, MHAUSR, ");						
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT");
				sqlString.append(" FROM " + libraryTT + ".QAPKMEHD");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON");
				sqlString.append(" MHCONO = st.DCCONO AND MHDIVI = st.DCDIVI AND");
				sqlString.append(" MHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(where);			
				sqlString.append(" MHCONO = " + method.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" MHDIVI = '" + method.getDivision().trim() + "'");
				sqlString.append(and);			
				sqlString.append(" MHMENO = " + method.getMethodNumber().trim());				
				sqlString.append(" ORDER BY MHMENO, MHRDTE DESC, MHRTME DESC");
			}				
		}
//  *********************************************************************************		
//  Insert METHOD -- ONLY on newly created Methods
//  *********************************************************************************
		if (requestType.trim().equals("insertMethodHeader")) 
		{			
			UpdMethod methodHeader = (UpdMethod) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(methodHeader.getEnvironment().trim());
			
			sqlString.append("INSERT  INTO  " + libraryTT + ".QAPKMEHD ");
			sqlString.append("(MHCONO, MHDIVI, MHSTAT, MHTYPE, MHOUSR,");
			sqlString.append(" MHGRUP, MHSCOP, MHMENO, MHRDTE, MHRTME,");
			sqlString.append(" MHNAME, MHDESC, MHRTXT, MHUDTE, MHUTME,");
			sqlString.append(" MHUUSR, MHCDTE, MHCTME, MHCUSR, MHSDTE,");
			sqlString.append(" MHSTME, MHAUSR) VALUES(?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?)");
		}
//  *************************************************************************************
//	List METHOD
//  *************************************************************************************		
		if (requestType.trim().equals("listMethod")) 
		{	
			String whereHeader = buildSqlWhereClause("listMethodHeader", requestClass);
						
			InqMethod method = (InqMethod) requestClass.elementAt(0);				
			String libraryTT = GeneralUtility.getTTLibrary(method.getEnvironment().trim());
			
				sqlString.append(" SELECT  MHCONO, MHDIVI, MHSTAT, MHTYPE, MHOUSR, MHGRUP, MHSCOP, MHMENO,");
				sqlString.append(" MHRDTE, MHRTME, MHNAME, MHDESC, MHRTXT, MHUDTE, MHUTME, MHUUSR, MHCDTE,");	
				sqlString.append(" MHCTME, MHCUSR, MHSDTE, MHSTME, MHAUSR, "); // 08/04/11 tw - added MHAUSR	
				sqlString.append(" IFNULL(st.DCTEXT,' ') as STTEXT, IFNULL(tp.DCTEXT,' ') as TPTEXT,");
				sqlString.append(" IFNULL(gp.DCTEXT,' ') as GPTEXT, IFNULL(sp.DCTEXT,' ') as SPTEXT");
				sqlString.append(" FROM " + libraryTT + ".QAPKMEHD");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC st ON");
				sqlString.append(" MHCONO = st.DCCONO AND MHDIVI = st.DCDIVI AND");
				sqlString.append(" MHSTAT = st.DCCACD AND st.DCCAID = '" + categoryCodes.get(1) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC tp ON");
				sqlString.append(" MHCONO = tp.DCCONO AND MHDIVI = tp.DCDIVI AND");
				sqlString.append(" MHTYPE = tp.DCCACD AND tp.DCCAID = '" + categoryCodes.get(2) + "'");	
//				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC so ON");
//				sqlString.append(" MHCONO = so.DCCONO AND MHDIVI = so.DCDIVI AND");
//				sqlString.append(" MHSRCE = so.DCCACD AND so.DCCAID = '" + categoryCodes.get(3) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC gp ON");
				sqlString.append(" MHCONO = gp.DCCONO AND MHDIVI = gp.DCDIVI AND");
				sqlString.append(" MHGRUP = gp.DCCACD AND gp.DCCAID = '" + categoryCodes.get(4) + "'");
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPMDESC sp ON");
				sqlString.append(" MHCONO = sp.DCCONO AND MHDIVI = sp.DCDIVI AND");
				sqlString.append(" MHSCOP = sp.DCCACD AND sp.DCCAID = '" + categoryCodes.get(6) + "'");

			if (!whereHeader.trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(whereHeader);
				condition = and;
			}
			orderBy = buildSqlOrderBy("sortMethod", method.getOrderBy(), method.getOrderStyle());	      
			sqlString.append(orderBy);
		}
//	************************************************************************************
//	Update METHOD
//	************************************************************************************
		if (requestType.trim().equals("updateMethodStatusInactive")) 
		{
			UpdMethod methodHeader = (UpdMethod) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(methodHeader.getEnvironment().trim());
			
			if (!methodHeader.getMethodNumber().trim().equals(""))				
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPKMEHD");			
				sqlString.append(" SET");
				sqlString.append(" MHSTAT = 'IN'");
				sqlString.append(where);
				sqlString.append(" MHCONO = " + methodHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" MHDIVI = '" + methodHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" MHMENO = " + methodHeader.getMethodNumber().trim());
				sqlString.append(and);
				sqlString.append(" MHSTAT = 'AC'");
			}
		}
		
		if (requestType.trim().equals("updateMethodStatus")) 
		{
			UpdMethod methodHeader = (UpdMethod) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(methodHeader.getEnvironment().trim());
			
			if ((!methodHeader.getMethodNumber().trim().equals("")) &&
				(!methodHeader.getRevisionDate().trim().equals("")) &&
				(!methodHeader.getRevisionTime().trim().equals("")))
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPKMEHD");			
				sqlString.append(" SET");
				sqlString.append(" MHSTAT = '" + methodHeader.getStatus().trim() + "'");
				if (methodHeader.originalStatus.trim().equals("PD") &&
					methodHeader.getStatus().trim().equals("AC"))
				{ // Reset the Revision Date and Time
					sqlString.append(" , MHRDTE = " + methodHeader.getUpdateDate());
					sqlString.append(" , MHRTME = " + methodHeader.getUpdateTime());
				}
				sqlString.append(where);
				sqlString.append(" MHCONO = " + methodHeader.getCompany().trim());
				sqlString.append(and);
				sqlString.append(" MHDIVI = '" + methodHeader.getDivision().trim() + "'");
				sqlString.append(and);
				sqlString.append(" MHMENO = " + methodHeader.getMethodNumber().trim());
				sqlString.append(and);
				sqlString.append(" MHRDTE = " + methodHeader.getRevisionDate().trim());
				sqlString.append(and);
				sqlString.append(" MHRTME = " + methodHeader.getRevisionTime().trim());
			}
		}
//	************************************************************************************
//	Verify METHOD
//	************************************************************************************			
		if (requestType.trim().equals("verifyMethodNumber"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append(" SELECT MHMENO");
			sqlString.append(" FROM " + libraryTT + ".QAPKMEHD");						
			sqlString.append(where);
			sqlString.append(" MHCONO = " + commonRequest.getCompanyNumber().trim());
			sqlString.append(and);
			sqlString.append(" MHDIVI = " + commonRequest.getDivisionNumber().trim());
			sqlString.append(and);
			sqlString.append(" MHMENO = " + commonRequest.getIdLevel1().trim());						
		}
			
		if (requestType.trim().equals("verifyMethodIndex"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);			
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append(" SELECT MHMENO");
			sqlString.append(" FROM " + libraryTT + ".QAPKMEHD");						
			sqlString.append(where);
			sqlString.append(" MHCONO = " + commonRequest.getCompanyNumber().trim());
			sqlString.append(and);
			sqlString.append(" MHDIVI = " + commonRequest.getDivisionNumber().trim());
			sqlString.append(and);
			sqlString.append(" MHMENO = " + commonRequest.getIdLevel1().trim());
			sqlString.append(and);
			sqlString.append(" MHRDTE = " + commonRequest.getDate().trim());
			sqlString.append(and);
			sqlString.append(" MHRTME = " + commonRequest.getTime().trim());
		}
		
//	  *************************************************************************************
//		Delete VARIETY
//	  *************************************************************************************		
		if (requestType.trim().equals("deleteSpecificationFruitVariety") ||
			requestType.trim().equals("deleteFormulaFruitVariety")) 
		{	
			String company 		= "";
			String division 	= "";
			String recordID 	= "";
			String revisionDate = "";
			String revisionTime = "";
			String appType  	= "";
			String libraryTT	= "";
			if (requestType.trim().equals("deleteSpecificationFruitVariety"))
			{
				UpdSpecification specificationHeader = (UpdSpecification) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
				company      = specificationHeader.getCompany().trim();
				division     = specificationHeader.getDivision().trim();
				recordID     = specificationHeader.getSpecNumber().trim();
				revisionDate = specificationHeader.getRevisionDate().trim();
				revisionTime = specificationHeader.getRevisionTime().trim();
				appType		 = "SPEC";
			}
			if (requestType.trim().equals("deleteFormulaFruitVariety"))
			{
				UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
				company      = formulaHeader.getCompany().trim();
				division     = formulaHeader.getDivision().trim();
				recordID     = formulaHeader.getFormulaNumber().trim();
				revisionDate = formulaHeader.getRevisionDate().trim();
				revisionTime = formulaHeader.getRevisionTime().trim();
				appType		 = "FORMULA";
			}
			if ((!recordID.trim().equals(""))   &&
				(!revisionDate.trim().equals("")) &&
				(!revisionTime.trim().equals("")))	
			{	
				sqlString.append("DELETE FROM " + libraryTT + ".QAPRFVAR");
				sqlString.append(where);
				sqlString.append(" FVCONO = " + company.trim());
				sqlString.append(and);
				sqlString.append(" FVDIVI = '" + division.trim() + "'");
				sqlString.append(and);
				sqlString.append(" FVSPNO = " + recordID.trim());
				sqlString.append(and);
				sqlString.append(" FVRDTE = " + revisionDate.trim());
				sqlString.append(and);
				sqlString.append(" FVRTME = " + revisionTime.trim());
				sqlString.append(and);
				sqlString.append(" FVATYP = '" + appType.trim() + "' ");
			}
		}
//	  *********************************************************************************		
//	  Insert VARIETY INFORMATION
//	  *********************************************************************************
		if ((requestType.trim().equals("insertVarietyIncluded")) ||
			(requestType.trim().equals("insertVarietyExcluded")))
			{			
				UpdVariety fruitVariety = (UpdVariety) requestClass.elementAt(0);				
				String libraryTT = GeneralUtility.getTTLibrary(fruitVariety.getEnvironment().trim());
				
				sqlString.append("INSERT  INTO  " + libraryTT + ".QAPRFVAR ");
				sqlString.append("(FVCONO, FVDIVI, FVSPNO, FVRDTE, FVRTME,");
				sqlString.append(" FVATMO, FVATID, FVVALU, FVIECD, FVIPCT,");
				sqlString.append(" FVMIIP, FVMXIP, FVUDTE, FVUTME, FVUUSR,");
				sqlString.append(" FVCDTE, FVCTME, FVCUSR, FVATYP, FVIPCA,");
				sqlString.append(" FVMIIA, FVMXIA )");
				sqlString.append(" VALUES(?,?,?,?,?,");
				sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,? ,?,?)");
			}
//	  *************************************************************************************
//		List VARIETY INFORMATION
//	  *************************************************************************************	
		if (requestType.trim().equals("listSpecificationVarietiesIncluded") ||
			requestType.trim().equals("listSpecificationVarietiesExcluded") ||
			requestType.trim().equals("listFormulaVarietiesIncluded") ||
			requestType.trim().equals("listFormulaVarietiesExcluded"))
		{		
          // tw 8/16/11 - Header information no longer needed, will find these from the DtlSpecification or the DtlFormula
			String company 		= "";
			String division 	= "";
			String recordID 	= "";
			String revisionDate = "";
			String revisionTime = "";
			String appType  	= "";
			String libraryTT	= "";
			String libraryM3	= "";
			if (requestType.trim().equals("listSpecificationVarietiesIncluded") ||
				requestType.trim().equals("listSpecificationVarietiesExcluded"))
			{
				DtlSpecification specificationHeader = (DtlSpecification) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
				libraryM3 	 = GeneralUtility.getLibrary(specificationHeader.getEnvironment().trim());
				company      = specificationHeader.getCompany().trim();
				division     = specificationHeader.getDivision().trim();
				recordID     = specificationHeader.getSpecNumber().trim();
				revisionDate = specificationHeader.getRevisionDate().trim();
				revisionTime = specificationHeader.getRevisionTime().trim();
				appType		 = "SPEC";
			}
			if (requestType.trim().equals("listFormulaVarietiesIncluded") ||
				requestType.trim().equals("listFormulaVarietiesExcluded"))
			{
				DtlFormula formulaHeader = (DtlFormula) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
				libraryM3 	 = GeneralUtility.getLibrary(formulaHeader.getEnvironment().trim());
				company      = formulaHeader.getCompany().trim();
				division     = formulaHeader.getDivision().trim();
				recordID     = formulaHeader.getFormulaNumber().trim();
				revisionDate = formulaHeader.getRevisionDate().trim();
				revisionTime = formulaHeader.getRevisionTime().trim();
				appType		 = "FORMULA";
			}	
			if ((!recordID.trim().equals("")) &&
				(!revisionDate.trim().equals("")) &&
				(!revisionTime.trim().equals("")))
			{						 
				String includeOrExclude = "";
				if (requestType.trim().equals("listSpecificationVarietiesIncluded") ||
					requestType.trim().equals("listFormulaVarietiesIncluded"))
					includeOrExclude = "I";
				else
					includeOrExclude = "X";
				
				sqlString.append(" SELECT  FVCONO, FVDIVI, FVSPNO, FVRDTE, FVRTME, FVATMO, FVATID,");
				sqlString.append(" FVVALU, FVIECD, FVIPCA, FVMIIA, FVMXIA, FVUDTE, FVUTME, FVUUSR,");			
				sqlString.append(" FVCDTE, FVCTME, FVCUSR, FVATYP, ");				
				sqlString.append(" IFNULL(PFTX30,' ') as PFTEXT,");
				sqlString.append(" SUBSTRING(ADTX40,3,37) as ADTEXT ");
				
				sqlString.append(" FROM " + libraryTT + ".QAPRFVAR");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MATVAV ON");
				sqlString.append(" FVCONO = AJCONO AND AJATID = FVATID AND");
				sqlString.append(" FVATMO = AJOBV1 AND AJAALF = FVVALU");
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MPDOPT ON");
				sqlString.append(" FVCONO = PFCONO AND AJAALF = PFOPTN");	
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MAMOHE ON");
				sqlString.append(" FVCONO = ADCONO AND FVATMO = ADATMO");
					
				sqlString.append(where);
				sqlString.append(" FVCONO = " + company.trim());
				sqlString.append(and);
				sqlString.append(" FVDIVI = '" + division.trim() + "'");
				sqlString.append(and);
				sqlString.append(" FVATYP = '" + appType.trim() + "'");
				sqlString.append(and);			
				sqlString.append(" FVSPNO = " + recordID.trim());
				sqlString.append(and);
				sqlString.append(" FVRDTE = " + revisionDate.trim());
				sqlString.append(and);
				sqlString.append(" FVRTME = " + revisionTime.trim());
				sqlString.append(and);
				sqlString.append(" FVIECD = '" + includeOrExclude + "'");
				sqlString.append(" ORDER BY FVCONO, FVDIVI, FVATYP, FVSPNO, FVRDTE DESC, FVRTME DESC, FVIECD, ADTEXT");
			}				
		}
//	  *************************************************************************************
//		UPDATE Revision Date and Time for the Variety File
//	  *************************************************************************************					
		if (requestType.trim().equals("updateFormulaVarietyRevisionDate") ||
			requestType.trim().equals("updateSpecVarietyRevisionDate")) 
		{
			String company 		= "";
			String division 	= "";
			String recordID 	= "";
			String revisionDate = "";
			String revisionTime = "";
			String libraryTT	= "";
			String updateDate	= "";
			String updateTime	= "";
			if (requestType.trim().equals("updateFormulaVarietyRevisionDate")) 
			{
				UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
				libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
				company = formulaHeader.getCompany();
				division = formulaHeader.getDivision();
				recordID = formulaHeader.getFormulaNumber();
				revisionDate = formulaHeader.getRevisionDate();
				revisionTime = formulaHeader.getRevisionTime();
				updateDate = formulaHeader.getUpdateDate();
				updateTime = formulaHeader.getUpdateTime();
			}
			if (requestType.trim().equals("updateSpecVarietyRevisionDate")) 
			{
				UpdSpecification specHeader = (UpdSpecification) requestClass.elementAt(0);
				libraryTT = GeneralUtility.getTTLibrary(specHeader.getEnvironment().trim());
				company = specHeader.getCompany();
				division = specHeader.getDivision();
				recordID = specHeader.getSpecNumber();
				revisionDate = specHeader.getRevisionDate();
				revisionTime = specHeader.getRevisionTime();
				updateDate = specHeader.getUpdateDate();
				updateTime = specHeader.getUpdateTime();
			}
			if ((!recordID.equals("")) &&
				(!revisionDate.trim().equals("")) &&
				(!revisionTime.trim().equals("")))
			{	
				sqlString.append(" UPDATE " + libraryTT + ".QAPRFVAR");			
				sqlString.append(" SET");
				sqlString.append(" FVRDTE = " + updateDate);
				sqlString.append(" , FVRTME = " + updateTime);
				sqlString.append(where);
				sqlString.append(" FVCONO = " + company.trim());
				sqlString.append(and);
				sqlString.append(" FVDIVI = '" + division.trim() + "'");
				sqlString.append(and);
				sqlString.append(" FVSPNO = " + recordID.trim());
				sqlString.append(and);
				sqlString.append(" FVRDTE = " + revisionDate.trim());
				sqlString.append(and);
				sqlString.append(" FVRTME = " + revisionTime.trim());
			}
		}
		
//    *************************************************************************************
//		Delete Analytical TESTS and Process PARAMETERS  -- used for both Specifications and Formulas
//	  *************************************************************************************			
		if (requestType.trim().equals("deleteSpecificationTestParameters") ||
			requestType.trim().equals("deleteFormulaTestParameters")) 
		{	
			String company 		= "";
			String division 	= "";
			String recordID 	= "";
			String revisionDate = "";
			String revisionTime = "";
			String appType  	= "";
			String libraryTT	= "";
			if (requestType.trim().equals("deleteSpecificationTestParameters"))
			{
				UpdSpecification specificationHeader = (UpdSpecification) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
				company      = specificationHeader.getCompany().trim();
				division     = specificationHeader.getDivision().trim();
				recordID     = specificationHeader.getSpecNumber().trim();
				revisionDate = specificationHeader.getRevisionDate().trim();
				revisionTime = specificationHeader.getRevisionTime().trim();
				appType		 = "SPEC";
			}
			if (requestType.trim().equals("deleteFormulaTestParameters"))
			{
				UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
				company      = formulaHeader.getCompany().trim();
				division     = formulaHeader.getDivision().trim();
				recordID     = formulaHeader.getFormulaNumber().trim();
				revisionDate = formulaHeader.getRevisionDate().trim();
				revisionTime = formulaHeader.getRevisionTime().trim();
				appType		 = "FORMULA";
			}
			if ((!recordID.trim().equals(""))   &&
				(!revisionDate.trim().equals("")) &&
				(!revisionTime.trim().equals("")))	
			{	
				sqlString.append("DELETE FROM " + libraryTT + ".QAPQPROC");
				sqlString.append(where);
				sqlString.append(" TPCONO = " + company.trim());
				sqlString.append(and);
				sqlString.append(" TPDIVI = '" + division.trim() + "'");
				sqlString.append(and);
				sqlString.append(" TPSPNO = " + recordID.trim());
				sqlString.append(and);
				sqlString.append(" TPRDTE = " + revisionDate.trim());
				sqlString.append(and);
				sqlString.append(" TPRTME = " + revisionTime.trim());
				sqlString.append(and);
				sqlString.append(" TPATYP = '" + appType.trim() + "' ");
			}
		}	
//	  *********************************************************************************		
//	  Insert TEST PARAMETERS -- Analytical Tests and Process Parameters 
//		For both Specifications and Formulas		
//	  *********************************************************************************		
		if (requestType.trim().equals("insertTestParameter"))
		{			
			UpdTestParameters testParameter = (UpdTestParameters) requestClass.elementAt(0);				
			String libraryTT = GeneralUtility.getTTLibrary(testParameter.getEnvironment().trim());
			sqlString.append("INSERT  INTO  " + libraryTT + ".QAPQPROC ");
			sqlString.append("(TPCONO, TPDIVI, TPSPNO, TPRDTE, TPRTME,");
			sqlString.append(" TPCODE, TPATGR, TPATTR, TPSEQ#, TPUOFM,");
			sqlString.append(" TPTARG, TPMNST, TPMXST, TPTVAL, TPTUOM,");
			sqlString.append(" TPPCOA, TPMENO, TPMEDT, TPMETM, TPUDTE,");
			sqlString.append(" TPUTME, TPUUSR, TPCDTE, TPCTME, TPCUSR,");
			sqlString.append(" TPATYP, TPTRGA, TPMINA, TPMAXA )");
			sqlString.append(" VALUES(?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,");
			sqlString.append("?,?,?,?,?, ?,?,?,?)");
				
		}
//	  *************************************************************************************
//		List TEST PARAMETERS - Analytical Tests / Process Parameters for both Specifications and Formulas
//	  *************************************************************************************			
		
		if (requestType.trim().equals("listSpecificationAnalyticalTests") ||
			requestType.trim().equals("listSpecificationProcessParameters") ||
			requestType.trim().equals("listSpecificationMicroTests") ||
			requestType.trim().equals("listSpecificationAdditivePreservative") ||
			requestType.trim().equals("listFormulaTests"))
		{		
			String company 		= "";
			String division 	= "";
			String recordID 	= "";
			String revisionDate = "";
			String revisionTime = "";
			String appType  	= "";
			String idType		= "";
			String libraryTT	= "";
			String libraryM3	= "";
			if (requestType.trim().equals("listSpecificationAnalyticalTests") ||
				requestType.trim().equals("listSpecificationProcessParameters") ||
				requestType.trim().equals("listSpecificationMicroTests")  ||
				requestType.trim().equals("listSpecificationAdditivePreservative"))
			{
				DtlSpecification specificationHeader = (DtlSpecification) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(specificationHeader.getEnvironment().trim());
				libraryM3    = GeneralUtility.getLibrary(specificationHeader.getEnvironment().trim());
				company      = specificationHeader.getCompany().trim();
				division     = specificationHeader.getDivision().trim();
				recordID     = specificationHeader.getSpecNumber().trim();
				revisionDate = specificationHeader.getRevisionDate().trim();
				revisionTime = specificationHeader.getRevisionTime().trim();
				appType		 = "SPEC";
				if (requestType.trim().equals("listSpecificationProcessParameters"))
					idType = "PROC";
				if (requestType.trim().equals("listSpecificationMicroTests"))
					idType = "MICRO";
				if (requestType.trim().equals("listSpecificationAdditivePreservative"))
					idType = "ADD";
			}
			if (requestType.trim().equals("listFormulaTests"))
			{
				DtlFormula formulaHeader = (DtlFormula) requestClass.elementAt(0);
				libraryTT    = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
				libraryM3	 = GeneralUtility.getLibrary(formulaHeader.getEnvironment().trim());
				company      = formulaHeader.getCompany().trim();
				division     = formulaHeader.getDivision().trim();
				recordID     = formulaHeader.getFormulaNumber().trim();
				revisionDate = formulaHeader.getRevisionDate().trim();
				revisionTime = formulaHeader.getRevisionTime().trim();
				appType		 = "FORMULA";
			}
			if ((!recordID.trim().equals("")) &&
				(!revisionDate.trim().equals("")) &&
				(!revisionTime.trim().equals("")))
			{						 
			
				sqlString.append(" SELECT  TPCONO, TPDIVI, TPSPNO, TPRDTE, TPRTME, TPCODE,");
				sqlString.append(" TPATGR, TPATTR, TPSEQ#, TPUOFM, TPTARG, TPMNST, TPMXST,");
				sqlString.append(" TPTVAL, TPTUOM, TPPCOA, TPMENO, MHNAME, TPMEDT, TPMETM, TPUDTE,");
				sqlString.append(" TPUTME, TPUUSR, TPCDTE, TPCTME, TPCUSR, TPATYP, TPTRGA,");
				sqlString.append(" TPMINA, TPMAXA, ");
				sqlString.append(" IFNULL(AATX30,' ') as ATTEXT, IFNULL(AADCCD,'0') as ATDECI,");
				sqlString.append(" IFNULL(AAATVC,' ') as ATATVC,");
				sqlString.append(" IFNULL(CTTX15,' ') as CTTEXT,");
				sqlString.append(" IFNULL(MHRDTE, '') as MERDTE, IFNULL(MHRTME, '') as MERTME, IFNULL(MHDESC, '') as MEDESC");
				
				sqlString.append(" FROM " + libraryTT + ".QAPQPROC");
				
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".MATRMA ON");
				sqlString.append(" TPCONO = AACONO AND UPPER(TPATTR) = UPPER(AAATID) AND");

				sqlString.append(" (");
				sqlString.append(" AAATGR = TPCODE");
				sqlString.append(" OR AAATGR = 'ANLYT' ");	//ALLOW FOR ANLYT type of attributes
				sqlString.append(" )");
				
				sqlString.append(" LEFT OUTER JOIN " + libraryM3 + ".CSYTAB ON");
				sqlString.append(" CTCONO = TPCONO AND CTSTCO = 'UNIT' AND AAUNMS = CTSTKY ");
				
				sqlString.append(" LEFT OUTER JOIN " + libraryTT + ".QAPKMEHD ON");
				sqlString.append(" TPCONO = MHCONO AND TPMENO = MHMENO AND MHSTAT = 'AC'");
					
				sqlString.append(where);
				sqlString.append(" TPCONO = " + company);
				sqlString.append(and);
				sqlString.append(" TPDIVI = '" + division + "' ");
				sqlString.append(and);			
				sqlString.append(" TPSPNO = " + recordID);
				sqlString.append(and);
				sqlString.append(" TPRDTE = " + revisionDate);
				sqlString.append(and);
				sqlString.append(" TPRTME = " + revisionTime);
				sqlString.append(and);
				sqlString.append(" TPCODE = '" + idType + "'");
				sqlString.append(and);
				sqlString.append(" TPATYP = '" + appType + "'");
				
				sqlString.append(" ORDER BY TPSPNO, TPRDTE DESC, TPRTME DESC, TPSEQ#");
			}				
		}	
//	  *************************************************************************************
//		UPDATE Revision Date and Time for the Test Parameters File
//	  *************************************************************************************					
		if (requestType.trim().equals("updateFormulaTestRevisionDate") ||
			requestType.trim().equals("updateSpecTestRevisionDate")) 
		{
			String company 		= "";
			String division 	= "";
			String recordID 	= "";
			String revisionDate = "";
			String revisionTime = "";
			String libraryTT	= "";
			String updateDate	= "";
			String updateTime	= "";
			if (requestType.trim().equals("updateFormulaTestRevisionDate")) 
			{
				UpdFormula formulaHeader = (UpdFormula) requestClass.elementAt(0);
				libraryTT = GeneralUtility.getTTLibrary(formulaHeader.getEnvironment().trim());
				company = formulaHeader.getCompany();
				division = formulaHeader.getDivision();
				recordID = formulaHeader.getFormulaNumber();
				revisionDate = formulaHeader.getRevisionDate();
				revisionTime = formulaHeader.getRevisionTime();
				updateDate = formulaHeader.getUpdateDate();
				updateTime = formulaHeader.getUpdateTime();
			}
			if (requestType.trim().equals("updateSpecTestRevisionDate")) 
			{
				UpdSpecification specHeader = (UpdSpecification) requestClass.elementAt(0);
				libraryTT = GeneralUtility.getTTLibrary(specHeader.getEnvironment().trim());
				company = specHeader.getCompany();
				division = specHeader.getDivision();
				recordID = specHeader.getSpecNumber();
				revisionDate = specHeader.getRevisionDate();
				revisionTime = specHeader.getRevisionTime();
				updateDate = specHeader.getUpdateDate();
				updateTime = specHeader.getUpdateTime();
			}
			if ((!recordID.equals("")) &&
				(!revisionDate.trim().equals("")) &&
				(!revisionTime.trim().equals("")))
			{						
				sqlString.append(" UPDATE " + libraryTT + ".QAPQPROC");			
				sqlString.append(" SET");
				sqlString.append(" TPRDTE = " + updateDate);
				sqlString.append(" , TPRTME = " + updateTime);
				sqlString.append(where);
				sqlString.append(" TPCONO = " + company.trim());
				sqlString.append(and);
				sqlString.append(" TPDIVI = '" + division.trim() + "'");
				sqlString.append(and);
				sqlString.append(" TPSPNO = " + recordID.trim());
				sqlString.append(and);
				sqlString.append(" TPRDTE = " + revisionDate.trim());
				sqlString.append(and);
				sqlString.append(" TPRTME = " + revisionTime.trim());
			}
		}		
		
		
//	*********************************************************************************
	} catch (Exception e) {
			throwError.append(" Error building sql statement" +
						      " for request type " + requestType + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("buildSqlStatement(String, Vector)");
		throw new Exception(throwError.toString());
	}
		
	return sqlString.toString();
}

/**
 * @author deisen.
 * Return a drop down single box for text section heading.
 */

public static Vector dropDownTextSection(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownTextSection");
		dropDownBox = dropDownGenericSingle(requestBean);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownTextSection(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for status codes.
 */

public static Vector dropDownStatus(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownStatus");
		dropDownBox = dropDownGenericSingle(requestBean);				
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownStatus(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Build an SQL where clause statement for specifications.
 *    Used for LISTS
 */
	
private static String buildSqlWhereClause(String requestType,
									      Vector requestClass)
throws Exception 
{
	StringBuffer sqlString     = new StringBuffer();
	StringBuffer throwError    = new StringBuffer();
	String       condition     = new String();
	String       and           = new String(" AND");
		
	try {
		
//  *************************************************************************************
//	List SPECIFICATION
//  *************************************************************************************
		if (requestType.trim().equals("listSpecificationHeader")) 
		{
			InqSpecification specification = (InqSpecification) requestClass.elementAt(0);			
			sqlString.append(condition);
			sqlString.append(" SHCONO = " + specification.getCompany().trim());
			sqlString.append(and);
			sqlString.append(" SHDIVI = '" + specification.getDivision().trim() + "'");
			condition = and;
			
			if (!specification.getInqSpecNumber().trim().equals(""))
			{
				sqlString.append(condition);
				// 1/30/12 TWalton, change to reflect the Spec Name instead
				//sqlString.append(" SHSPNO = " + specification.getInqSpecNumber().trim());
				sqlString.append(" UPPER(SHNAME) LIKE UPPER('%" + specification.getInqSpecNumber().trim() + "%')");
				condition = and;
			}
			if (!specification.getInqStatus().trim().equals(""))
			{   				
				sqlString.append(condition);
				sqlString.append(" SHSTAT = '" + specification.getInqStatus().trim() + "' ");
				condition = and;
			}			
			if (!specification.getInqSpecDescription().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(SHDESC) LIKE UPPER('%" + 
						specification.getInqSpecDescription().trim() + "%')");
				condition = and;
			}
			if (!specification.getInqFormula().trim().equals(""))
			{
				sqlString.append(condition);
				// 1/30/12 TWalton - change to reflect Formula Name instead
				//sqlString.append(" SHFONO = " + specification.getInqFormula().trim());
				sqlString.append(" UPPER(FHNAME) LIKE UPPER('%" + 
						specification.getInqFormula().trim() + "%')");
				condition = and;
			}
			if (!specification.getInqFormulaName().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHDESC) LIKE UPPER('%" + 
						specification.getInqFormulaName().trim() + "%')");
				condition = and;
			}
			if (!specification.getInqSpecType().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(SHTYPE) = UPPER('" + specification.getInqSpecType().trim() + "')");
				condition = and;
			}	
			if (!specification.getInqGroup().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(SHGRUP) = UPPER('" + specification.getInqGroup().trim() + "')");
				condition = and;
			}	
			if (!specification.getInqScope().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(SHSCOP) = UPPER('" + specification.getInqScope().trim() + "')");
				condition = and;
			}		
			if (!specification.getInqOrigination().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(SHOUSR) = UPPER('" + specification.getInqOrigination().trim() + "')");
				condition = and;
			}	
			if (!specification.getInqApprovedBy().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(SHAUSR) = UPPER('" + specification.getInqApprovedBy().trim() + "')");
				condition = and;
			}		
			if (!specification.getInqItemNumber().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" SHITNO LIKE '" + specification.getInqItemNumber().trim() + "%'");
				condition = and;
			}
			if (!specification.getInqItemType().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" MMITTY = '" + specification.getInqItemType().trim() + "'");
				condition = and;
			}
			if (!specification.getInqProductSize().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" MMCFI1 = '" + specification.getInqProductSize().trim() + "'");
				condition = and;
			}
			if (!specification.getInqProductGroup().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" MMITGR = '" + specification.getInqProductGroup().trim() + "'");
				condition = and;
			}
			if (requestClass.size() > 1)
			{
				sqlString.append(condition);
				sqlString.append(" SHTDTE < " + (String) requestClass.elementAt(1)); 
			}
		}
//  *************************************************************************************
//	List FORMULA
//  *************************************************************************************
		if (requestType.trim().equals("listFormulaHeader") ||
			requestType.trim().equals("listFormulaDetail")) 
		{
			InqFormula formula = (InqFormula) requestClass.elementAt(0);
			sqlString.append(condition);
			sqlString.append(" FHCONO = " + formula.getCompany().trim());
			sqlString.append(and);
			sqlString.append(" FHDIVI = '" + formula.getDivision().trim() + "'");
			condition = and;
			
			if (!formula.getInqFormulaNumber().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" FHFONO = " + formula.getInqFormulaNumber().trim());
				condition = and;
			}
			if (!formula.getInqStatus().trim().equals(""))
			{   
				sqlString.append(condition);				
				sqlString.append(" UPPER(FHSTAT) = UPPER('" + formula.getInqStatus().trim() + "')");
				condition = and;
			}			
			if (!formula.getInqFormulaDescription().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHDESC) LIKE UPPER('%" + 
		    		             formula.getInqFormulaDescription().trim() + "%')");
				condition = and;
			}			
			if (!formula.getInqFormulaName().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHNAME) LIKE UPPER('%" + 
		    		             formula.getInqFormulaName().trim() + "%')");
				condition = and;
			}	
			if (!formula.getInqFormulaType().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHTYPE) = UPPER('" + formula.getInqFormulaType().trim() + "')");
				condition = and;
			}	
			if (!formula.getInqGroup().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHGRUP) = UPPER('" + formula.getInqGroup().trim() + "')");
				condition = and;
			}		
			if (!formula.getInqScope().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHSCOP) = UPPER('" + formula.getInqScope().trim() + "')");
				condition = and;
			}		
			if (!formula.getInqOrigination().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHOUSR) = UPPER('" + formula.getInqOrigination().trim() + "')");
				condition = and;
			}	
			if (!formula.getInqApprovedBy().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FHAUSR) = UPPER('" + formula.getInqApprovedBy().trim() + "')");
				condition = and;
			}	
			if (!formula.getInqItemNumber().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" FHLTNO = '" + formula.getInqItemNumber().trim() + "'");
				condition = and;
			}
			if (!formula.getInqItemDescription().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(itemHdr.MMITDS) LIKE UPPER('%" + 
		    		             formula.getInqItemDescription().trim() + "%')");
				condition = and;
			}
			if (!formula.getInqCustomerNumber().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" FHCUNO == '" + formula.getInqCustomerNumber().trim() + "'");
				condition = and;
			}
			if (!formula.getInqCustomerName().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(OKCUNM) LIKE UPPER('%" + 
		    		             formula.getInqCustomerName().trim() + "%')");
				condition = and;
			}			
			if (!formula.getInqSupplierNumber().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" FDSUNO == '" + formula.getInqSupplierNumber().trim() + "'");
				condition = and;
			}
			if (!formula.getInqSupplierName().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(FDSUNM) LIKE UPPER('%" + 
		    		             formula.getInqSupplierName().trim() + "%')");
				condition = and;
			}			
		}
	//	*************************************************************************************
//	List METHOD
//	*************************************************************************************
		if (requestType.trim().equals("listMethodHeader")) 
		{
			InqMethod method = (InqMethod) requestClass.elementAt(0);
			sqlString.append(condition);
			sqlString.append(" MHCONO = " + method.getCompany().trim());
			sqlString.append(and);
			sqlString.append(" MHDIVI = '" + method.getDivision().trim() + "'");
			condition = and;
			if (method.getRequestType().trim().equals("listMethod"))
			{ // Only show the Methods
				sqlString.append(condition);
				sqlString.append(" MHTYPE = 'MTH' ");
				condition = and;
			}
			if (method.getRequestType().trim().equals("listProcedure"))
			{ // Only show the Procedures
				sqlString.append(condition);
				sqlString.append(" MHTYPE = 'PRC' ");
				condition = and;	
			}
			if (method.getRequestType().trim().equals("listPolicy"))
			{ // Only show the Policies
				sqlString.append(condition);
				sqlString.append(" MHTYPE = 'POL' ");
				condition = and;	
			}
			if (!method.getInqMethodNumber().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" MHMENO = " + method.getInqMethodNumber().trim());
				condition = and;
			}
			if (!method.getInqStatus().trim().equals(""))
			{   
				sqlString.append(condition);				
				sqlString.append(" UPPER(MHSTAT) = UPPER('" + method.getInqStatus().trim() + "')");
				condition = and;
			}			
			if (!method.getInqMethodDescription().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(MHDESC) LIKE UPPER('%" + 
		    		             method.getInqMethodDescription().trim() + "%')");
				condition = and;
			}			
			if (!method.getInqMethodName().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(MHNAME) LIKE UPPER('%" + 
		    		             method.getInqMethodName().trim() + "%')");
				condition = and;
			}		
			if (!method.getInqScope().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(MHSCOP) = UPPER('" + method.getInqScope().trim() + "')");
				condition = and;
			}		
			if (!method.getInqOrigination().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(MHOUSR) = UPPER('" + method.getInqOrigination().trim() + "')");
				condition = and;
			}	
			if (!method.getInqApprovedBy().trim().equals(""))
			{
				sqlString.append(condition);
				sqlString.append(" UPPER(MHAUSR) = UPPER('" + method.getInqApprovedBy().trim() + "')");
				condition = and;
			}		
		}

				
//	*********************************************************************************
	} catch (Exception e) {
			throwError.append(" Error building sql where clause" +
						      " for request type " + requestType + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("buildSqlWhereClause(String, Vector)");
		throw new Exception(throwError.toString());
	}
		
	return sqlString.toString();
}

/**
 * @author deisen.
 * Build an table for storing static category codes.
 */
	
private static Hashtable buildSqlCategoryCodes()
throws Exception 
{	
	Hashtable<Integer, String> categoryCodes = new Hashtable<Integer, String>();	
	StringBuffer               throwError    = new StringBuffer();
		
	try {		
		
		categoryCodes.put(new Integer(1), "Status");
		categoryCodes.put(new Integer(2), "Type");
		categoryCodes.put(new Integer(3), "Kosher Status");
		categoryCodes.put(new Integer(4), "Grouping");
		categoryCodes.put(new Integer(5), "Method Section");
		categoryCodes.put(new Integer(6), "Scope");
		categoryCodes.put(new Integer(7), "Inline Sock");
		categoryCodes.put(new Integer(8), "CIP Type");
		categoryCodes.put(new Integer(9), "Storage Cond");
		categoryCodes.put(new Integer(10), "Cont Code Location");
		categoryCodes.put(new Integer(11), "M3");
		categoryCodes.put(new Integer(12), "SAM");
		categoryCodes.put(new Integer(13), "PL Label Type");
		categoryCodes.put(new Integer(14), "PL Label Location");
		categoryCodes.put(new Integer(15), "Stretch Wrap Type");
		categoryCodes.put(new Integer(16), "Shrink Wrap Type");
		categoryCodes.put(new Integer(17), "Pallet Require");
		categoryCodes.put(new Integer(18), "Cart Code Location");

	} catch (Exception e) {
			throwError.append(" Error building static category codes. " + e);	
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("buildSqlCategoryCodes()");
		throw new Exception(throwError.toString());
	}
		
	return categoryCodes;
}

/**
 * @author deisen.
 * Return a header and detail specification per request.
 */

public static BeanQuality findSpecification(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findSpecification(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecification(");
		throwError.append("Vector). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return a header and detail specification per request.
 */

private static BeanQuality findSpecification(Vector inValues, 
								             Connection conn)
throws Exception
{
	StringBuffer               throwError        = new StringBuffer();
	ResultSet                  rs                = null;
	Statement                  listThem          = null;
	QaSpecification            specification     = new QaSpecification();
	DtlSpecification           specRequest       = new DtlSpecification();
	Vector<DtlSpecification>   specRequestList   = new Vector<DtlSpecification>(); // will include only one element (used to retrieve other information)
	BeanQuality                returnValue       = new BeanQuality();
				
	try {
		
		try {
			
			String sql = new String();	
			sql = buildSqlStatement("findSpecification", inValues);
			listThem = conn.createStatement(); 
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 DtlSpecification inRequest = (DtlSpecification) inValues.elementAt(0);
				 returnValue.setEnvironment(inRequest.getEnvironment());
				 
				 if (rs.next()) // only ONE spec
				 {			 		
					 Vector          oneSpecification    = loadFields("QaSpecificationHeader", rs);	
					 QaSpecification specificationHeader = (QaSpecification) oneSpecification.elementAt(0);						 
					 returnValue.setSpecification(specificationHeader);		
						 
					 specRequest.setEnvironment(inRequest.getEnvironment());
					 specRequest.setCompany(inRequest.getCompany());
					 specRequest.setDivision(inRequest.getDivision());
					 specRequest.setSpecNumber(specificationHeader.getSpecificationNumber());						 
					 specRequest.setRevisionDate(specificationHeader.getRevisionDate());
					 specRequest.setRevisionTime(specificationHeader.getRevisionTime());
					 specRequestList.addElement(specRequest);
					 
					 Vector<QaSpecificationPackaging>  packSpecification = loadFields("QaSpecificationPackaging", rs);
					 QaSpecificationPackaging specPackaging    = (QaSpecificationPackaging) packSpecification.elementAt(0);
					 returnValue.setSpecPackaging(specPackaging);
				 }
				 
				 if ((!specRequest.getSpecNumber().trim().equals("")) &&
					 (!specRequest.getRevisionDate().trim().equals("")) &&
					 (!specRequest.getRevisionTime().trim().equals("")))
				 {						 
					 Vector specRevisionReason = new Vector();
					 specRevisionReason = findSpecificationRevisionReason(specRequestList);
					 returnValue.setRevReasonSpecification(specRevisionReason);
				 
					 Vector specAnalyticalTests = new Vector();
					 specAnalyticalTests = listSpecificationAnalyticalTest(specRequestList);
					 returnValue.setSpecAnalyticalTests(specAnalyticalTests);
				 
					 Vector specMicroTests = new Vector();
					 specMicroTests = listSpecificationMicroTest(specRequestList);
					 returnValue.setSpecMicroTests(specMicroTests);
				 	 
					 Vector specProcessParameters = new Vector();
					 specProcessParameters = listSpecificationProcessParameter(specRequestList);
					 returnValue.setSpecProcessParameters(specProcessParameters);
					 
					 Vector specAdditivePreservative = new Vector();
					 specAdditivePreservative = listSpecificationAdditivePreservative(specRequestList);
					 returnValue.setSpecAdditiveAndPreserve(specAdditivePreservative);
				 
					 Vector specVarietiesIncluded = new Vector();
					 specVarietiesIncluded = listVarietiesIncluded("SPEC", specRequestList);
					 returnValue.setVarietiesIncluded(specVarietiesIncluded);
				 
					 Vector specVarietiesExcluded = new Vector();
					 specVarietiesExcluded = listVarietiesExcluded("SPEC", specRequestList);
					 returnValue.setVarietiesExcluded(specVarietiesExcluded);
					 
					// Retrieve Additional Information
					 if (!returnValue.getSpecification().getItemNumber().trim().equals(""))
					 {
						 try{ // find the Product Structure Information
							 InqItem ii = new InqItem();
							 ii.setInqItem(returnValue.getSpecification().getItemNumber().trim());
							 ii.setEnvironment(returnValue.getEnvironment().trim());
							 BeanItem bi = ServiceItem.listProductStructureMaterials(ii);
							 returnValue.setProductStructure(bi.getProductStructure());
						 }catch(Exception e){}
						 try{ // find the Basic Item Information
							 returnValue.setItemInformation(ServiceItem.buildNewItem(specRequest.getEnvironment(), returnValue.getSpecification().getItemNumber().trim()));
						 }catch(Exception e){}
					 }
				 }
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecification(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all revision reasons for one specification number.
 */

public static Vector findSpecificationRevisionReason(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findSpecificationRevisionReason(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecificationRevisionReason(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return all revision reasons for one specification number.
 */

private static Vector findSpecificationRevisionReason(Vector inValues, 
								                      Connection conn)
throws Exception
{
	StringBuffer            throwError  = new StringBuffer();
	ResultSet               rs          = null;
	Statement               listThem    = null;		
	Vector<QaSpecification> returnValue = new Vector<QaSpecification>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("findSpecificationRevisionReason", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector          oneRevisionReason = loadFields("QaSpecificationRevisionReason", rs);
					 QaSpecification revisionReason    = (QaSpecification) oneRevisionReason.elementAt(0);
					 returnValue.addElement(revisionReason);			 		
				 }
			
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecificationRevisionReason(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all revision reasons for one method number.
 */

private static Vector findMethodRevisionReason(Vector inValues, 
								               Connection conn)
throws Exception
{
	StringBuffer     throwError        = new StringBuffer();
	ResultSet        rs                = null;
	Statement        listThem          = null;		
	Vector<QaMethod> returnValue       = new Vector<QaMethod>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("findMethodRevisionReason", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				
				 while (rs.next())
				 {			 			
					 Vector    oneRevisionReason = loadFields("QaMethodRevisionReason", rs);
					 QaMethod  revisionReason    = (QaMethod) oneRevisionReason.elementAt(0);
					 returnValue.addElement(revisionReason);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findMethodRevisionReason(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all revision reasons for one method number.
 */

public static Vector findMethodRevisionReason(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findMethodRevisionReason(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findMethodRevisionReason(");
		throwError.append("Vector). ");		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * * Return all fruit varieties excluded for one specification or formula
 *   App Type will be SPEC or FORMULA and will be used to "cast" the Vector
 */

private static Vector listVarietiesExcluded(String appType, 
											Vector inValues, 
								            Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;		
	Vector<QaFruitVariety> returnValue = new Vector<QaFruitVariety>();
	
	try {
		String requestType = "listSpecificationVarietiesExcluded";
		if (appType.trim().equals("FORMULA"))
			requestType = "listFormulaVarietiesExcluded";
		try {			
			
			String sql = new String();
			sql = buildSqlStatement(requestType, inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector         oneVarietyExcluded = loadFields("QaVarietyExcluded", rs);
					 QaFruitVariety varietyExcluded    = (QaFruitVariety) oneVarietyExcluded.elementAt(0);
					 returnValue.addElement(varietyExcluded);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findVarietyExcluded(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all analytical tests for one specification.
 */

public static Vector listSpecificationAnalyticalTest(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listSpecificationAnalyticalTest(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecificationAnalyticalTest(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return all analytical tests for one specification.
 */

private static Vector listSpecificationAnalyticalTest(Vector inValues, 
								                      Connection conn)
throws Exception
{
	StringBuffer          	 throwError  = new StringBuffer();
	ResultSet             	 rs          = null;
	Statement             	 listThem    = null;		
	Vector<QaTestParameters> returnValue = new Vector<QaTestParameters>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("listSpecificationAnalyticalTests", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {				 
				
				while (rs.next())
				{			 		
					Vector        	 oneAnalyticalTest = loadFields("QaTestParameters", rs);
					QaTestParameters analyticalTest    = (QaTestParameters) oneAnalyticalTest.elementAt(0);
					returnValue.addElement(analyticalTest);			 		
				}					
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecificationAnalyticalTest(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all process parameters for one specification.
 */

public static Vector listSpecificationProcessParameter(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listSpecificationProcessParameter(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecificationProcessParameter(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return all process parameters for one specification.
 */

private static Vector listSpecificationProcessParameter(Vector inValues, 
								                        Connection conn)
throws Exception
{
	StringBuffer          throwError  = new StringBuffer();
	ResultSet             rs          = null;
	Statement             listThem    = null;		
	Vector<QaTestParameters> returnValue = new Vector<QaTestParameters>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("listSpecificationProcessParameters", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector           oneProcessParameter = loadFields("QaTestParameters", rs);
					 QaTestParameters processParameter    = (QaTestParameters) oneProcessParameter.elementAt(0);
					 returnValue.addElement(processParameter);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecificationProcessParameter(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all fruit varieties included for one Specification or Formula.
 * 		Send in the App Type of SPEC or FORMULA - to determine what is returned
 *      This information will be used to Cast the Vector
 */

public static Vector listVarietiesIncluded(String appType, Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listVarietiesIncluded(appType, inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listVarietiesIncluded(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return all fruit varieties included for one specification or formula
 *   App Type will be SPEC or FORMULA and will be used to "cast" the Vector
 */

private static Vector listVarietiesIncluded(String appType, 
											Vector inValues, 
								            Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;		
	Vector<QaFruitVariety> returnValue = new Vector<QaFruitVariety>();
	
	try {
		String requestType = "listSpecificationVarietiesIncluded";
		if (appType.trim().equals("FORMULA"))
			requestType = "listFormulaVarietiesIncluded";
		try {			
			String sql = new String();
			sql = buildSqlStatement(requestType, inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector         oneVarietyIncluded = loadFields("QaVarietyIncluded", rs);
					 QaFruitVariety varietyIncluded    = (QaFruitVariety) oneVarietyIncluded.elementAt(0);
					 returnValue.addElement(varietyIncluded);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listVarietiesIncluded(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return all fruit varieties excluded for one Specification or Formula.
 * 		Send in the App Type of SPEC or FORMULA - to determine what is returned
 *      This information will be used to Cast the Vector
 */

public static Vector listVarietiesExcluded(String appType, Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listVarietiesExcluded(appType, inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listVarietiesExcluded(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Retrieving the next available specification identification number.
 */
	
public static BeanQuality nextIDNumberSpecification(String environment)							
throws Exception 
{
	AS400	     as400       = null;
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
		
	try {
		
		QaSpecification specification = new QaSpecification();
		specification.setSpecificationNumber("0");
		returnValue.setSpecification(specification);
		// 1/17/12 TWalton - Change to ServiceConnection
		//as400 = ConnectionStack.getAS400Object();
		as400 = ServiceConnection.getAS400();
		ProgramCall pgm   = new ProgramCall(as400);
		AS400Text library = new AS400Text(3); 
		
		ProgramParameter[] parmList = new ProgramParameter[2];
		parmList[0] = new ProgramParameter(100);
		parmList[1] = new ProgramParameter(library.toBytes(environment.trim())); 
				pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/QACIDSPEC.PGM", parmList);
	
		if (pgm.run() != true)
			return returnValue;
		
		else {
			AS400PackedDecimal number = new AS400PackedDecimal(10, 0);
			byte[] data = parmList[0].getOutputData();
			double dd   = number.toDouble(data, 0);
			int    id   = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			specification.setSpecificationNumber(Integer.toString(id));
			returnValue.setSpecification(specification);
			return returnValue;			
		}
		
	} catch (Exception e) {
			throwError.append(" Error retrieving next available specification identification number. " + e);			
	}
	
	finally {
		if (as400 !=null)
		{
		//	ConnectionStack.returnAS400Object(as400);
			ServiceConnection.returnAS400(as400);
		}
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("nextIDNumberSpecification(String)");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Retrieving the next available formula identification number.
 */
	
public static BeanQuality nextIDNumberFormula(String environment)							
throws Exception 
{
	AS400	     as400       = null;
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
		
	try {
		
		QaFormula formula = new QaFormula();
		formula.setFormulaNumber("0");
		returnValue.setFormula(formula);
		// 1/17/12 TWalton change to ServiceConnection
		//as400 = ConnectionStack.getAS400Object();
		as400 = ServiceConnection.getAS400();
		ProgramCall pgm   = new ProgramCall(as400);
		AS400Text library = new AS400Text(3); 
		
		ProgramParameter[] parmList = new ProgramParameter[2];
		parmList[0] = new ProgramParameter(100);
		parmList[1] = new ProgramParameter(library.toBytes(environment.trim())); 
				pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/QACIDFORM.PGM", parmList);
	
		if (pgm.run() != true)
			return returnValue;
		
		else {
			AS400PackedDecimal number = new AS400PackedDecimal(10, 0);
			byte[] data = parmList[0].getOutputData();
			double dd   = number.toDouble(data, 0);
			int    id   = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			formula.setFormulaNumber(Integer.toString(id));
			returnValue.setFormula(formula);
			return returnValue;			
		}
		
	} catch (Exception e) {
			throwError.append(" Error retrieving next available formula identification number. " + e);			
	}
	
	finally {
		if (as400 !=null)
		{
		  //	ConnectionStack.returnAS400Object(as400);
			ServiceConnection.returnAS400(as400);
		}
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("nextIDNumberFormula(String)");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

public static Vector dropDownGenericSingle(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		dropDownBox = dropDownGenericSingle(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownGenericSingle(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

private static Vector dropDownGenericSingle(CommonRequestBean requestBean,
                                            Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;
	Vector<DropDownSingle> dropDownBox = new Vector<DropDownSingle>();
		
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlDropDown(requestBean);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement to build a drop down list. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {			 
				
				 while (rs.next())
				 {			 		
					 DropDownSingle oneGenericCode = loadFieldsDropDown("dropDownSingle", rs);					
					 dropDownBox.addElement(oneGenericCode);					
				 }				 
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building drop down vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownGenericSingle(");
		throwError.append("CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for grouping codes.
 */

public static Vector dropDownGrouping(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownGrouping");
		dropDownBox = dropDownGenericSingle(requestBean);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownGrouping(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Update the method status.
 */

public static BeanQuality updateMethodStatus(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = updateMethodStatus(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateMethodStatus(");
		throwError.append("Vector). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Update the formula status.
 */

private static BeanQuality updateFormulaStatus(Vector inValues, 
							 	               Connection conn)
throws Exception
{
	StringBuffer         throwError  = new StringBuffer();
	String               sql         = new String();
	Statement            updateIt    = null;
	BeanQuality          returnValue = new BeanQuality();
	Vector<DtlFormula> findFormula = new Vector<DtlFormula>();
	
	try {
 		
 		try {
 			
			UpdFormula oneFormula = (UpdFormula) inValues.elementAt(0);			
			
			DtlFormula formula = new DtlFormula();
	 		formula.setEnvironment(oneFormula.getEnvironment().trim());
	 		formula.setCompany(oneFormula.getCompany().trim());
	 		formula.setDivision(oneFormula.getDivision().trim());
	 		formula.setFormulaNumber(oneFormula.getFormulaNumber().trim());
	 		formula.setRevisionDate(oneFormula.getRevisionDate().trim());
	 		formula.setRevisionTime(oneFormula.getRevisionTime().trim()); 			
 			
 			updateIt = conn.createStatement();
 			
 			if (oneFormula.getStatus().trim().equals("AC"))
			{			 			
 				sql = buildSqlStatement("updateFormulaStatusInactive", inValues); 			
 				updateIt.executeUpdate(sql);
			}
 			
 			sql = buildSqlStatement("updateFormulaStatus", inValues); 			
 			updateIt.executeUpdate(sql);  	
 			
 			if (oneFormula.originalStatus.trim().equals("PD") &&
				oneFormula.getStatus().trim().equals("AC"))
			{ 
 				try{
 					// Update the Revision Date on all the Extra Files
 					//------------Formula DETAIL
 					sql = buildSqlStatement("updateFormulaDetailRevisionDate", inValues);
 					updateIt.executeUpdate(sql);
 					//------------Varieties (Include Exclude)
 					sql = buildSqlStatement("updateFormulaVarietyRevisionDate", inValues);
 					updateIt.executeUpdate(sql);
 					//------------Test Parameters
 					sql = buildSqlStatement("updateFormulaTestRevisionDate", inValues);
 					updateIt.executeUpdate(sql);
 				}catch(Exception e){
 					System.out.println("Error Updating DATES: " + e);
 				}
 
 				// Reset the Revision Date and Time
				formula.setRevisionDate(oneFormula.getUpdateDate());
				formula.setRevisionTime(oneFormula.getUpdateTime());
			}

 			findFormula.addElement(formula);
 			returnValue = returnFormula("find", throwError.toString(), findFormula);	
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (update). " + e);
 		 }		
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateFormulaStatus(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update the specification status.
 */

private static BeanQuality updateSpecificationStatus(Vector inValues, 
							 	                     Connection conn)
throws Exception
{
	StringBuffer               throwError        = new StringBuffer();
	String                     sql               = new String();
	Statement                  updateIt          = null;
	BeanQuality                returnValue       = new BeanQuality();
	Vector<DtlSpecification> findSpecification = new Vector<DtlSpecification>();
	
	try {
 		
 		try {
 			
			UpdSpecification oneSpecification = (UpdSpecification) inValues.elementAt(0);			
			
			DtlSpecification specification = new DtlSpecification();
 			specification.setEnvironment(oneSpecification.getEnvironment().trim());
 			specification.setCompany(oneSpecification.getCompany().trim());
 			specification.setDivision(oneSpecification.getDivision().trim());
 			specification.setSpecNumber(oneSpecification.getSpecNumber().trim());
 			specification.setRevisionDate(oneSpecification.getRevisionDate().trim());
 			specification.setRevisionTime(oneSpecification.getRevisionTime().trim()); 			
 			
 			
 			updateIt = conn.createStatement();
 			
 			if (oneSpecification.getStatus().trim().equals("AC"))
			{			 			
 				sql = buildSqlStatement("updateSpecificationStatusInactive", inValues); 			
 				updateIt.executeUpdate(sql);
 				
 			}
 			sql = buildSqlStatement("updateSpecificationStatus", inValues); 			
 			updateIt.executeUpdate(sql);  	
 			
 			if (oneSpecification.originalStatus.trim().equals("PD") &&
 				oneSpecification.getStatus().trim().equals("AC"))
 				{ 
 	 				try{
 	 					// Update the Revision Date on all the Extra Files
 	 					//------------Varieties (Include Exclude)
 	 					sql = buildSqlStatement("updateSpecVarietyRevisionDate", inValues);
 	 					updateIt.executeUpdate(sql);
 	 					//------------Test Parameters
 	 					sql = buildSqlStatement("updateSpecTestRevisionDate", inValues);
 	 					updateIt.executeUpdate(sql);
 	 				}catch(Exception e){
 	 					System.out.println("Error Updating DATES: " + e);
 	 				}
 	 				// Reset the Revision Date and Time
 					specification.setRevisionDate(oneSpecification.getUpdateDate());
 					specification.setRevisionTime(oneSpecification.getUpdateTime());
 				}
 			
 			findSpecification.addElement(specification);
 		 	returnValue = returnSpecification("find", throwError.toString(), findSpecification);	
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (update). " + e);
 		 }		
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateSpecificationStatus(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update the specification status.
 */

public static BeanQuality updateSpecificationStatus(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = updateSpecificationStatus(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateSpecificationStatus(");
		throwError.append("Vector). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Update the method status.
 */

private static BeanQuality updateMethodStatus(Vector inValues, 
							 	              Connection conn)
throws Exception
{
	StringBuffer         throwError  = new StringBuffer();
	String               sql         = new String();
	Statement            updateIt    = null;
	BeanQuality          returnValue = new BeanQuality();
	Vector<DtlMethod> findMethod = new Vector<DtlMethod>();
	
	try {
 		
 		try {
 			
			UpdMethod oneMethod = (UpdMethod) inValues.elementAt(0);			
			
			DtlMethod method = new DtlMethod();
 			method.setEnvironment(oneMethod.getEnvironment().trim());
 			method.setCompany(oneMethod.getCompany().trim());
 			method.setDivision(oneMethod.getDivision().trim());
 			method.setMethodNumber(oneMethod.getMethodNumber().trim());
 			method.setRevisionDate(oneMethod.getRevisionDate().trim());
 			method.setRevisionTime(oneMethod.getRevisionTime().trim()); 			
 			findMethod.addElement(method);
 			
 			updateIt = conn.createStatement();
 			
 			if (oneMethod.getStatus().trim().equals("AC"))
			{			 			
 				sql = buildSqlStatement("updateMethodStatusInactive", inValues); 			
 				updateIt.executeUpdate(sql);
			}
 			
 			sql = buildSqlStatement("updateMethodStatus", inValues); 			
 			updateIt.executeUpdate(sql);  	
 			
 		 //	returnValue = returnMethod("find", throwError.toString(), findMethod);		// Diasabled
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (update). " + e);
 		 }		
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (updateIt != null)
	   {
		   try {
			   updateIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateMethodStatus(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Update the formula status.
 */

public static BeanQuality updateFormulaStatus(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	BeanQuality  returnValue = new BeanQuality();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = updateFormulaStatus(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("updateFormulaStatus(");
		throwError.append("Vector). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * validate either the formula number or formula number, revision date and time.
 */

private static String verifyFormula(CommonRequestBean requestBean,
						            Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	ResultSet    rs          = null;
	Statement    listThem    = null;
	String		 requestType = new String();
	String       returnValue = new String();
	
	try {
		
 		try {	
			
			Vector<CommonRequestBean> commonRequest = new Vector<CommonRequestBean>();
			commonRequest.addElement(requestBean);
			
			if ((!requestBean.getDate().trim().equals("")) &&
				(!requestBean.getTime().trim().equals("")))
			{
				requestType = "verifyFormulaIndex";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			}
			
			else {
				requestType = "verifyFormulaNumber";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			}
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 if (rs.next())
				 {
					 returnValue = "";
				 }
				 else {
					 StringBuffer message = new StringBuffer();
					 
					 if (requestType.equals("verifyFormulaIndex"))
					 {
						 message.append(" Formula Number '" + requestBean.getIdLevel1().trim() + "',");
						 message.append(" Revision Date '" + requestBean.getDate().trim() + "',");
						 message.append(" Revision Time '" + requestBean.getTime().trim() + "'");
						 message.append(" does not exist. ");
						 returnValue = message.toString();		
					 }
					 else {
						 message.append(" Formula Number '" + requestBean.getIdLevel1().trim() + "'");
						 message.append(" does not exist. ");
						 returnValue = message.toString();		
					 }
				}
				 
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building formula verify from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("verfiyFormula(");
		throwError.append("CommonRequestBean, conn). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * validate either the method number or method number, revision date and time.
 */

public static String verifySpecification(CommonRequestBean requestBean)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	String       returnValue = new String();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = verifySpecification(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("verifySpecification(");
		throwError.append("CommonRequestBean). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * validate either the method number or method number, revision date and time.
 */

private static String verifyMethod(CommonRequestBean requestBean,
						           Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	ResultSet    rs          = null;
	Statement    listThem    = null;
	String		 requestType = new String();
	String       returnValue = new String();
	
	try {
		
 		try {	
			
			Vector<CommonRequestBean> commonRequest = new Vector<CommonRequestBean>();
			commonRequest.addElement(requestBean);
			
			if ((!requestBean.getDate().trim().equals("")) &&
				(!requestBean.getTime().trim().equals("")))
			{
				requestType = "verifyMethodIndex";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			}
			
			else {
				requestType = "verifyMethodNumber";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			}
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 if (rs.next())
				 {
					 returnValue = "";
				 }
				 else {
					 StringBuffer message = new StringBuffer();
					 
					 if (requestType.equals("verifyMethodIndex"))
					 {
						 message.append(" Method Number '" + requestBean.getIdLevel1().trim() + "',");
						 message.append(" Revision Date '" + requestBean.getDate().trim() + "',");
						 message.append(" Revision Time '" + requestBean.getTime().trim() + "'");
						 message.append(" does not exist. ");
						 returnValue = message.toString();		
					 }
					 else {
						 message.append(" Method Number '" + requestBean.getIdLevel1().trim() + "'");
						 message.append(" does not exist. ");
						 returnValue = message.toString();		
					 }
				}
				 
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building method verify from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("verfiyMethod(");
		throwError.append("CommonRequestBean, conn). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * validate either the specification number or specification number, revision date and time.
 */

private static String verifySpecification(CommonRequestBean requestBean,
						                  Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	ResultSet    rs          = null;
	Statement    listThem    = null;
	String		 requestType = new String();
	String       returnValue = new String();
	
	try {
		
 		try {	
			
			Vector<CommonRequestBean> commonRequest = new Vector<CommonRequestBean>();
			commonRequest.addElement(requestBean);
			
			if ((!requestBean.getDate().trim().equals("")) &&
				(!requestBean.getTime().trim().equals("")))
			{
				requestType = "verifySpecificationIndex";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			}
			
			else {
				requestType = "verifySpecificationNumber";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			}
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 if (rs.next())
				 {
					 returnValue = "";
				 }
				 else {
					 StringBuffer message = new StringBuffer();
					 
					 if (requestType.equals("verifySpecificationIndex"))
					 {
						 message.append(" Specification Number '" + requestBean.getIdLevel1().trim() + "',");
						 message.append(" Revision Date '" + requestBean.getDate().trim() + "',");
						 message.append(" Revision Time '" + requestBean.getTime().trim() + "'");
						 message.append(" does not exist. ");
						 returnValue = message.toString();		
					 }
					 else {
						 message.append(" Specification Number '" + requestBean.getIdLevel1().trim() + "'");
						 message.append(" does not exist. ");
						 returnValue = message.toString();		
					 }
				}
				 
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building specification verify from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("verfiySpecification(");
		throwError.append("CommonRequestBean, conn). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * validate either the method number or method number, revision date and time.
 */

public static String verifyMethod(CommonRequestBean requestBean)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	String       returnValue = new String();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = verifyMethod(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("verifyMethod(");
		throwError.append("CommonRequestBean). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Build an SQL statement for drop down box requests.
 */
	
private static String buildSqlDropDown(CommonRequestBean requestBean)
throws Exception 
{
	String		 categoryId = new String("");
	StringBuffer sqlString  = new StringBuffer();	
	StringBuffer throwError = new StringBuffer();
		
	try {	
		if (requestBean.getIdLevel1().trim().equals("method") ||
			requestBean.getIdLevel1().trim().equals("formula"))
		{
			String libraryTT = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());
			if (requestBean.getIdLevel1().trim().equals("method"))
			{
				sqlString.append("SELECT MHMENO, MHDESC, MHNAME ");
				sqlString.append("FROM " + libraryTT + ".QAPKMEHD ");
				sqlString.append("WHERE MHSTAT = 'AC' ");
				sqlString.append("ORDER BY MHNAME, MHDESC, MHMENO ");
			}
			if (requestBean.getIdLevel1().trim().equals("formula"))
			{
				sqlString.append("SELECT FHFONO, FHDESC, FHNAME ");
				sqlString.append("FROM " + libraryTT + ".QAPIFOHD ");
				sqlString.append("WHERE FHSTAT = 'AC' ");
				sqlString.append("ORDER BY FHNAME, FHDESC, FHFONO ");
			}
		}
		else
		{
			Hashtable categoryCodes = buildSqlCategoryCodes();

			if (requestBean.getIdLevel1().trim().equals("dropDownStatus")) {		
				categoryId = "'" + categoryCodes.get(1) + "'";
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownType")) {		
				categoryId = "'" + categoryCodes.get(2) + "'";
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownKosherStatus")) {	
				categoryId = "'" + categoryCodes.get(3) + "'";				
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownGrouping")) {		
				categoryId = "'" + categoryCodes.get(4) + "'";
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownTextSection")) {		
				categoryId = "'" + categoryCodes.get(5) + "'";
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownScope")) {		
				categoryId = "'" + categoryCodes.get(6) + "'";
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownInlineSock")) {		
				categoryId = "'" + categoryCodes.get(7) + "'";
			}
			if (requestBean.getIdLevel1().trim().equals("dropDownCIPType")) {		
				categoryId = "'" + categoryCodes.get(8) + "'";
			}
			if (categoryId.trim().equals(""))
			{
				String genericCategory = (String) requestBean.getIdLevel1().trim();
				categoryId = "'" + genericCategory + "'";
			}
		
			if (!categoryId.trim().equals(""))
			{			
				String libraryTT = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());
			
				sqlString.append(" SELECT DCSEQ#, DCTEXT, DCCACD");
				sqlString.append(" FROM " + libraryTT + ".QAPMDESC");
				sqlString.append(" WHERE");
				sqlString.append(" DCCONO = " + requestBean.getCompanyNumber().trim());
				sqlString.append(" AND");
				sqlString.append(" DCDIVI = '" + requestBean.getDivisionNumber().trim() + "'");
				sqlString.append(" AND");
				sqlString.append(" DCCAID = " + categoryId);
				sqlString.append(" GROUP BY DCSEQ#, DCTEXT, DCCACD");
				sqlString.append(" ORDER BY DCSEQ#, DCTEXT, DCCACD");
			}
		}

	} catch (Exception e) {
			throwError.append(" Error building a drop down sql statement" +
						      " for request type " + requestBean.getIdLevel1() + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("buildSqlDropDown(CommonRequestBean)");
		throw new Exception(throwError.toString());
	}
		
	return sqlString.toString();
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

public static Vector dropDownMethod(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		dropDownBox = dropDownMethod(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownMethod(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

private static Vector dropDownMethod(CommonRequestBean requestBean,
                                     Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;
	Vector<DropDownSingle> dropDownBox = new Vector<DropDownSingle>();
		
	try {
		
		try {
			requestBean.setIdLevel1("method");
			String sql = buildSqlDropDown(requestBean);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement to build a drop down list. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {			 
				
				 while (rs.next())
				 {			 		
					 DropDownSingle oneGenericCode = loadFieldsDropDown("dropDownMethod", rs);					
					 dropDownBox.addElement(oneGenericCode);					
				 }				 
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building drop down vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownMethod(");
		throwError.append("CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

public static Vector dropDownFormula(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		dropDownBox = dropDownFormula(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownFormula(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

private static Vector dropDownFormula(CommonRequestBean requestBean,
                                      Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;
	Vector<DropDownSingle> dropDownBox = new Vector<DropDownSingle>();
		
	try {
		
		try {
			requestBean.setIdLevel1("formula");
			String sql = buildSqlDropDown(requestBean);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement to build a drop down list. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {			 
				
				 while (rs.next())
				 {			 		
					 DropDownSingle oneGenericCode = loadFieldsDropDown("dropDownFormula", rs);					
					 dropDownBox.addElement(oneGenericCode);					
				 }				 
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building drop down vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownFormula(");
		throwError.append("CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return dropDownBox;
}

/**
 * @author twalto.
 * Return all micro tests for one specification.
 */

public static Vector listSpecificationMicroTest(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listSpecificationMicroTest(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecificationMicroTest(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author twalto.
 * Return all Micro tests for one specification.
 */

private static Vector listSpecificationMicroTest(Vector inValues, 
								                 Connection conn)
throws Exception
{
	StringBuffer             throwError  = new StringBuffer();
	ResultSet                rs          = null;
	Statement                listThem    = null;		
	Vector<QaTestParameters> returnValue = new Vector<QaTestParameters>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("listSpecificationMicroTests", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {				 
				
				while (rs.next())
				{			 		
					Vector        oneMicroTest = loadFields("QaTestParameters", rs);
					QaTestParameters microTest    = (QaTestParameters) oneMicroTest.elementAt(0);
					returnValue.addElement(microTest);			 		
				}					
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecificationMicroTest(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author deisen.
 * Return a drop down single box for scope codes.
 */

public static Vector dropDownScope(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	try {
		requestBean.setIdLevel1("dropDownScope");
		dropDownBox = dropDownGenericSingle(requestBean);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownScope(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Return all process parameters for one specification.
 */

public static Vector listFormulaTests(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listFormulaTests(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listFormulaTests(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author deisen.
 * Return all process parameters for one specification.
 */

private static Vector listFormulaTests(Vector inValues, 
								       Connection conn)
throws Exception
{
	StringBuffer          throwError  = new StringBuffer();
	ResultSet             rs          = null;
	Statement             listThem    = null;		
	Vector<QaTestParameters> returnValue = new Vector<QaTestParameters>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("listFormulaTests", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector           oneTest = loadFields("QaTestParametersFormula", rs);
					 QaTestParameters tests    = (QaTestParameters) oneTest.elementAt(0);
					 returnValue.addElement(tests);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listFormulaTests(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author twalto.
 * Return all PreBlend Detail information for one formula.
 */

public static Vector findFormulaPreBlend(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findFormulaPreBlend(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaPreBlend(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author twalto.
 * Return all Detail Records for one Formula (PRE Blend)
 */

private static Vector findFormulaPreBlend(Vector inValues, 
				                          Connection conn)
throws Exception
{
	StringBuffer          throwError  = new StringBuffer();
	ResultSet             rs          = null;
	Statement             listThem    = null;		
	Vector<QaFormulaDetail> returnValue = new Vector<QaFormulaDetail>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("findFormulaPreBlend", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector oneDetail = loadFields("QaFormulaPreBlend", rs);
					 QaFormulaDetail formulaPreBlend = (QaFormulaDetail) oneDetail.elementAt(0);
					 returnValue.addElement(formulaPreBlend);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaPreBlend(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author twalto.
 * Return all PreBlend Detail information for one formula.
 */

public static Vector findFormulaPreBlendSauce(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findFormulaPreBlendSauce(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaPreBlend(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author twalto.
 * Return all Detail PreBlend records for one Formula of Sauce
 */

private static Vector findFormulaPreBlendSauce(Vector inValues, 
				                          Connection conn)
throws Exception
{
	StringBuffer          throwError  = new StringBuffer();
	ResultSet             rs          = null;
	Statement             listThem    = null;		
	Vector<QaFormulaDetail> returnValue = new Vector<QaFormulaDetail>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("findFormulaPreBlendSauce", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector oneDetail = loadFields("QaFormulaPreBlendSauce", rs);
					 QaFormulaDetail formulaPreBlend = (QaFormulaDetail) oneDetail.elementAt(0);
//					 System.out.println("item1" + formulaPreBlend.getItemDescription1());
//					 System.out.println("item2" + formulaPreBlend.getItemDescription2());
//					 System.out.println("item3" + formulaPreBlend.getItemDescription3());
					 returnValue.addElement(formulaPreBlend);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaPreBlendSauce(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author twalto.
 * Return all PreBlend Detail information for one formula.
 */

public static Vector findFormulaProduction(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findFormulaProduction(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaProduction(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author twalto.
 * Return all Detail Records for one Formula (Production Values)
 */

private static Vector findFormulaProduction(Vector inValues, 
				                          Connection conn)
throws Exception
{
	StringBuffer          throwError  = new StringBuffer();
	ResultSet             rs          = null;
	Statement             listThem    = null;		
	Vector<QaFormulaDetail> returnValue = new Vector<QaFormulaDetail>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("findFormulaProduction", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 while (rs.next())
				 {			 		
					 Vector oneDetail = loadFields("QaFormulaProduction", rs);
					 QaFormulaDetail formulaProduction = (QaFormulaDetail) oneDetail.elementAt(0);
					 returnValue.addElement(formulaProduction);			 		
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaProduction(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author twalto.
 * Return a drop down single box for Kosher Status.
 */

public static Vector dropDownKosherStatus(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownKosherStatus");
		dropDownBox = dropDownGenericSingle(requestBean);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownKosherStatus(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author twalto.
 * Return a drop down single box for Inline Sock Required 
 */

public static Vector dropDownInlineSock(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownInlineSock");
		dropDownBox = dropDownGenericSingle(requestBean);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownInlineSock(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author twalto.
 * Return a drop down single box for CIP Type
 */

public static Vector dropDownCIPType(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	
	try {
		requestBean.setIdLevel1("dropDownCIPType");
		dropDownBox = dropDownGenericSingle(requestBean);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("dropDownKosherStatus(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author twalto.
 * Return all additives and preservatives for one specification.
 */

public static Vector listSpecificationAdditivePreservative(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector       returnValue = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = listSpecificationAdditivePreservative(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("listSpecificationAdditivePreservative(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * @author twalto.
 * Return all additives and preservatives for one specification.
 */

private static Vector listSpecificationAdditivePreservative(Vector inValues, 
								                            Connection conn)
throws Exception
{
	StringBuffer          	 throwError  = new StringBuffer();
	ResultSet             	 rs          = null;
	Statement             	 listThem    = null;		
	Vector<QaTestParameters> returnValue = new Vector<QaTestParameters>();
	
	try {
		
		try {			
			
			String sql = new String();
			sql = buildSqlStatement("listSpecificationAdditivePreservative", inValues);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {				 
				
				while (rs.next())
				{			 		
					Vector        	 oneAnalyticalTest = loadFields("QaTestParameters", rs);
					QaTestParameters analyticalTest    = (QaTestParameters) oneAnalyticalTest.elementAt(0);
					returnValue.addElement(analyticalTest);			 		
				}					
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecificationAdditivePreservative(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/*
 * Call this with a Common Request Bean
 * This bean will need to include,
 *    Environment
 *    Company
 *    Division
 *    Spec # - idLevel1
 *    Spec Revision Date - idLevel2
 *    
 * Will Return a DateTime object which will be teh Supersedes Date for the
 *    Specific revision you requested
 *    
 *    Creation date: (10/22/2013 TWalton)
 */

public static DateTime findSpecificationSupersedes(CommonRequestBean crb)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	DateTime     returnValue = new DateTime();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findSpecificationSupersedes(crb, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecificationSupersedes(CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/*
 * Call this with a Common Request Bean
 * This bean will need to include,
 *    Environment
 *    Company
 *    Division
 *    Spec # - idLevel1
 *    Spec Revision Date - idLevel2
 *    
 *  Will Return a DateTime object which will be teh Supersedes Date for the
 *    Specific revision you requested
 *    
 *    Creation date: (10/22/2013 TWalton)
 *    	  Will also use the BuildSQL section for building the SQL statement
 */
private static DateTime findSpecificationSupersedes(CommonRequestBean crb, 
								                  Connection conn)
throws Exception{
	
	StringBuffer    throwError  = new StringBuffer();
	ResultSet       rs          = null;
	Statement       listThem    = null;		
	DateTime		returnValue = new DateTime();
	
	try {

		try {			
			
			String sql = new String();
			sql = BuildSQL.findSpecificationSupersedes(crb);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 // only look at the first record, take the Date/Time information from there
				 if (rs.next())
				 {
					 CommonRequestBean sendCRB = new CommonRequestBean();
					 sendCRB.setDate(rs.getString("SHRDTE").trim());
					 sendCRB.setTime(rs.getString("SHRTME").trim());
					 returnValue = UtilityDateTime.getDateFromyyyyMMddTimeFromhhmmss(sendCRB);
				 }
			
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findSpecificationSupersedes(CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/*
 * Call this with a Common Request Bean
 * This bean will need to include,
 *    Environment
 *    Company
 *    Division
 *    Formula # - idLevel1
 *    Formula Revision Date - idLevel2
 *    
 * Will Return a DateTime object which will be teh Supersedes Date for the
 *    Specific revision you requested
 *    
 *    Creation date: (11/25/2013 TWalton)
 */

public static DateTime findFormulaSupersedes(CommonRequestBean crb)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	DateTime     returnValue = new DateTime();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findFormulaSupersedes(crb, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaSupersedes(CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/*
 * Call this with a Common Request Bean
 * This bean will need to include,
 *    Environment
 *    Company
 *    Division
 *    Formula # - idLevel1
 *    Formula Revision Date - idLevel2
 *    
 *  Will Return a DateTime object which will be teh Supersedes Date for the
 *    Specific revision you requested
 *    
 *    Creation date: (11/25/2013 TWalton)
 *    	  Will also use the BuildSQL section for building the SQL statement
 */
private static DateTime findFormulaSupersedes(CommonRequestBean crb, 
								              Connection conn)
throws Exception{
	
	StringBuffer    throwError  = new StringBuffer();
	ResultSet       rs          = null;
	Statement       listThem    = null;		
	DateTime		returnValue = new DateTime();
	
	try {

		try {			
			
			String sql = new String();
			sql = BuildSQL.findFormulaSupersedes(crb);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 // only look at the first record, take the Date/Time information from there
				 if (rs.next())
				 {
					 CommonRequestBean sendCRB = new CommonRequestBean();
					 sendCRB.setDate(rs.getString("FHRDTE").trim());
					 sendCRB.setTime(rs.getString("FHRTME").trim());
					 returnValue = UtilityDateTime.getDateFromyyyyMMddTimeFromhhmmss(sendCRB);
				 }
			
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findFormulaSupersedes(CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/*
 * Call this with a Common Request Bean
 * This bean will need to include,
 *    Environment
 *    Company
 *    Division
 *    Method # - idLevel1
 *    Method Revision Date - idLevel2
 *    
 * Will Return a DateTime object which will be teh Supersedes Date for the
 *    Specific revision you requested
 *    
 *    Creation date: (11/25/2013 TWalton)
 */

public static DateTime findMethodSupersedes(CommonRequestBean crb)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	DateTime     returnValue = new DateTime();
	Connection   conn        = null;
	
	try {
		conn = ServiceConnection.getConnectionStack3();
		returnValue = findMethodSupersedes(crb, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try {
				ServiceConnection.returnConnectionStack3(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findMethodSupersedes(CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/*
 * Call this with a Common Request Bean
 * This bean will need to include,
 *    Environment
 *    Company
 *    Division
 *    Method # - idLevel1
 *    Method Revision Date - idLevel2
 *    
 *  Will Return a DateTime object which will be teh Supersedes Date for the
 *    Specific revision you requested
 *    
 *    Creation date: (11/25/2013 TWalton)
 *    	  Will also use the BuildSQL section for building the SQL statement
 */
private static DateTime findMethodSupersedes(CommonRequestBean crb, 
								                  Connection conn)
throws Exception{
	
	StringBuffer    throwError  = new StringBuffer();
	ResultSet       rs          = null;
	Statement       listThem    = null;		
	DateTime		returnValue = new DateTime();
	
	try {

		try {			
			
			String sql = new String();
			sql = BuildSQL.findMethodSupersedes(crb);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 // only look at the first record, take the Date/Time information from there
				 if (rs.next())
				 {
					 CommonRequestBean sendCRB = new CommonRequestBean();
					 sendCRB.setDate(rs.getString("MHRDTE").trim());
					 sendCRB.setTime(rs.getString("MHRTME").trim());
					 returnValue = UtilityDateTime.getDateFromyyyyMMddTimeFromhhmmss(sendCRB);
				 }
			
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceQuality.");
		throwError.append("findMethodSupersedes(CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

}




