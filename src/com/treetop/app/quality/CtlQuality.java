/*
 * Created on April 28, 2010
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.quality;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.app.CtlKeyValues;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.QaSpecificationPackaging;
import com.treetop.businessobjects.QaTestParameters;
import com.treetop.services.ServiceGTIN;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceKeyValue;
import com.treetop.services.ServiceQuality;
import com.treetop.servlets.BaseServlet;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.viewbeans.ParameterMessageBean;


/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlQuality extends BaseServlet {

	/*
	 * Process incoming HTTP GET requests Goes directly to the performTask
	 * 
	 */
	public void doGet(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}

	/*
	 * Process incoming HTTP POST requests Goes directly to the performTask
	 * 
	 * @see com.treetop.servlets.BaseServlet#doPost()
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}
	/*
	 * Tests Parameters, Calls Security, and Directs Traffic between the pages
	 * 
	 * @see com.treetop.servlets.BaseServlet#performTask(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "inqSpec";
		

		
		String environment = request.getParameter("environment");
		String viewPath = "/APP/Quality/";
//		if (environment != null && environment.equals("TST")) {
			viewPath = "/view/quality/";
//		}
				
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlQuality";
//		if (requestType.equals("updCPGSpec"))
//			urlAddress = "/web/CtlSpecificationNEW?requestType=updCPGSpec";

		if (!callSecurity(request, response, urlAddress).equals("")) {
			try {
				response.sendRedirect("/web/TreeNetInq?msg="
						+ SessionVariables.getSessionttiSecStatus(request,
								response));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//------------------------------------------------------------------
		//Passed Security
		//*********************************
		// Default Screen
		String sendTo = "/APP/Quality/inqFormula.jsp";
		
		//-----------------------------------------------------------------
		//   Allow from a screen to Reset the lists used for all Quality 
		//    The lists are stored in General Quality
		//-----------------------------------------------------------------
		if (requestType.equals("resetLists"))
		{
			// Reset the lists, and then on a screen say the lists have been reset
			GeneralQuality.setListApprovedByUser(null);
			GeneralQuality.setListCropVariety(null);
			GeneralQuality.setListCustomerCode(null);
			GeneralQuality.setListOrigination(null);
			GeneralQuality.setListScope(null);
			GeneralQuality.setListStatus(null);
			GeneralQuality.setListType(null);
			GeneralQuality.setListUOM(null);
			sendTo = "/APP/Quality/resetQuality.jsp";
		}
		//*********************************
		// Set the information for the Drop Down Lists if not already There
		GeneralQuality gq = new GeneralQuality();
		//-----------------------------------------------------------------
		//   UPDATE PAGE To be dealt with BEFORE the List Pages
		//     this is because if there is a big problem it can send it back
		//     to the list or inquiry pages
		//  ADD / COPY / and REVISE all go to the Update Page
		//-----------------------------------------------------------------
		if (requestType.equals("updFormula") ||
			requestType.equals("addFormula") ||
			requestType.equals("reviseFormula") ||
			requestType.equals("copyNewFormula")) {
			UpdFormula updFormula = new UpdFormula();
			updFormula.populate(request);
//			updFormula.setFormulaName(request.getParameter("formulaName"));
//			if (updFormula.getFormulaName() == null)
//			   updFormula.setFormulaName("");
//			updFormula.setFormulaDescription(request.getParameter("formulaDescription"));
//			if (updFormula.getFormulaDescription() == null)
//			   updFormula.setFormulaDescription("");
			if (requestType.equals("updFormula"))
			{
				updFormula.validate();
				updFormula.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			
				if (!updFormula.getSaveButton().trim().equals(""))
				{
				   // Pre Blend, Production Sauce PreBlend sections, will all be populated
				   updFormula.populateDetails(request);
				
				   // VARIETY SECTION
				   Vector sendData = new Vector();
				   sendData.addElement("FORMULA");
				   sendData.addElement(updFormula);
				   try
				   {
				      Vector returnInclude = UpdVariety.populateVariety(request, sendData, "I", new Integer(updFormula.getCountVarietiesIncluded()).intValue());
				      updFormula.setDisplayMessage(updFormula.getDisplayMessage() + ((String) returnInclude.elementAt(0)).trim());
				      if (!updFormula.getDisplayMessage().trim().equals(""))
				    	  updFormula.setDisplayMessage(updFormula.getDisplayMessage().trim() + "<br>");
				      
				      updFormula.setListVarietiesIncluded((Vector) returnInclude.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   try
				   {
				      Vector returnExclude = UpdVariety.populateVariety(request, sendData, "X", new Integer(updFormula.getCountVarietiesExcluded()).intValue());
				      updFormula.setDisplayMessage(updFormula.getDisplayMessage() + ((String) returnExclude.elementAt(0)).trim());
				      if (!updFormula.getDisplayMessage().trim().equals(""))
				    	  updFormula.setDisplayMessage(updFormula.getDisplayMessage().trim() + "<br>");
				      updFormula.setListVarietiesExcluded((Vector) returnExclude.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   // Raw Fruit Attributes
				   try
				   {
				      Vector returnTests = UpdTestParameters.populateTests(request, sendData, "", new Integer(updFormula.getCountRawFruitAttributes()).intValue());
				      updFormula.setDisplayMessage(updFormula.getDisplayMessage() + ((String) returnTests.elementAt(0)).trim());
				      if (!updFormula.getDisplayMessage().trim().equals(""))
				    	  updFormula.setDisplayMessage(updFormula.getDisplayMessage().trim() + "<br>");
				      updFormula.setListRawFruitAttributes((Vector) returnTests.elementAt(1)); 
				   }catch(Exception e)
				   {}
				}
				request.setAttribute("requestType", requestType);
			}
			request.setAttribute("updViewBean", updFormula);
			sendTo = "/APP/Quality/updFormula.jsp";
		    pageUpd(request, response);
		}
		if (requestType.equals("updMethod") ||
			requestType.equals("addMethod") ||
			requestType.equals("reviseMethod") ||
			requestType.equals("copyNewMethod") ||
			requestType.equals("updProcedure") ||
			requestType.equals("addProcedure") ||
			requestType.equals("reviseProcedure") ||
			requestType.equals("copyNewProcedure") ||
			requestType.equals("updPolicy") ||
			requestType.equals("addPolicy") ||
			requestType.equals("revisePolicy") ||
			requestType.equals("copyNewPolicy")) {
			UpdMethod updMethod = new UpdMethod();
			updMethod.populate(request);
			if (requestType.equals("updMethod") ||
				requestType.equals("updProcedure") ||
				requestType.equals("updPolicy"))
			{
				
				updMethod.setMethodDescription(request.getParameter("methodDescription"));
				if (updMethod.getMethodDescription() == null)
					updMethod.setMethodDescription("");
				
				updMethod.validate();

				updMethod.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			    
				request.setAttribute("requestType", requestType);
			}
			sendTo = "/APP/Quality/updMethod.jsp";
			request.setAttribute("updViewBean", updMethod);
		    pageUpd(request, response);
		}
		if (requestType.equals("updSpec") ||
			requestType.equals("addSpec") ||
			requestType.equals("reviseSpec") ||
			requestType.equals("copyNewSpec")) {
			UpdSpecification updSpec = new UpdSpecification();
			updSpec.populate(request);
//			updSpec.setSpecName(request.getParameter("specName"));
//			if (updSpec.getSpecName() == null)
//			   updSpec.setSpecName("");
//			updSpec.setSpecDescription(request.getParameter("specDescription"));
//			if (updSpec.getSpecDescription() == null)
//			   updSpec.setSpecDescription("");
//			updSpec.setKosherSymbol(request.getParameter("kosherSymbol"));
//			if (updSpec.getKosherSymbol() == null)
//			   updSpec.setKosherSymbol("");
			if (requestType.equals("updSpec"))
			{
				updSpec.validate();
//					us.determineSecurity(request, response);
				updSpec.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
				if(!updSpec.submit.trim().equals("") &&
				    updSpec.getOriginalStatus().trim().equals("PD"))
				{ // only care if these are populated IF the save button is hit
				   // VARIETY SECTION
				   Vector sendData = new Vector();
				   sendData.addElement("SPEC");
				   sendData.addElement(updSpec);
				   try
				   { // Include Varieties
				      Vector returnInclude = UpdVariety.populateVariety(request, sendData, "I", new Integer(updSpec.getCountVarietiesIncluded()).intValue());
				      updSpec.setDisplayMessage(updSpec.getDisplayMessage() + ((String) returnInclude.elementAt(0)).trim());
				      if (!updSpec.getDisplayMessage().trim().equals(""))
				    	  updSpec.setDisplayMessage(updSpec.getDisplayMessage().trim() + "<br>");
				      
				      updSpec.setListVarietiesIncluded((Vector) returnInclude.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   try
				   { // Exclude Varieties
				      Vector returnExclude = UpdVariety.populateVariety(request, sendData, "X", new Integer(updSpec.getCountVarietiesExcluded()).intValue());
				      updSpec.setDisplayMessage(updSpec.getDisplayMessage() + ((String) returnExclude.elementAt(0)).trim());
				      if (!updSpec.getDisplayMessage().trim().equals(""))
				    	  updSpec.setDisplayMessage(updSpec.getDisplayMessage().trim() + "<br>");
				      updSpec.setListVarietiesExcluded((Vector) returnExclude.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   try
				   { //  Analytical Testing
				      Vector returnTests = UpdTestParameters.populateTests(request, sendData, "TEST", new Integer(updSpec.getCountAnalyticalTests()).intValue());
				      updSpec.setDisplayMessage(updSpec.getDisplayMessage() + ((String) returnTests.elementAt(0)).trim());
				      if (!updSpec.getDisplayMessage().trim().equals(""))
				    	  updSpec.setDisplayMessage(updSpec.getDisplayMessage().trim() + "<br>");
				      updSpec.setListAnalyticalTests((Vector) returnTests.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   try
				   { //  Microbiological Testing
				      Vector returnTests = UpdTestParameters.populateTests(request, sendData, "MICRO", new Integer(updSpec.getCountMicroTests()).intValue());
				      updSpec.setDisplayMessage(updSpec.getDisplayMessage() + ((String) returnTests.elementAt(0)).trim());
				      if (!updSpec.getDisplayMessage().trim().equals(""))
				    	  updSpec.setDisplayMessage(updSpec.getDisplayMessage().trim() + "<br>");
				      updSpec.setListMicroTests((Vector) returnTests.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   try
				   { //  Process Parameters
				      Vector returnTests = UpdTestParameters.populateTests(request, sendData, "PROC", new Integer(updSpec.getCountProcessParameters()).intValue());
				      updSpec.setDisplayMessage(updSpec.getDisplayMessage() + ((String) returnTests.elementAt(0)).trim());
				      if (!updSpec.getDisplayMessage().trim().equals(""))
				    	  updSpec.setDisplayMessage(updSpec.getDisplayMessage().trim() + "<br>");
				      updSpec.setListProcessParameters((Vector) returnTests.elementAt(1)); 
				   }catch(Exception e)
				   {}
				   try
				   { //  Additives and Preservatives
				      Vector returnTests = UpdTestParameters.populateTests(request, sendData, "ADD", new Integer(updSpec.getCountAdditivesAndPreservatives()).intValue());
				      updSpec.setDisplayMessage(updSpec.getDisplayMessage() + ((String) returnTests.elementAt(0)).trim());
				      if (!updSpec.getDisplayMessage().trim().equals(""))
				    	  updSpec.setDisplayMessage(updSpec.getDisplayMessage().trim() + "<br>");
				      updSpec.setListAdditivesAndPreservatives((Vector) returnTests.elementAt(1)); 
				   }catch(Exception e)
				   {}
				}
				   
				updSpec.buildDropDownVectors();
				request.setAttribute("requestType", requestType);
			}
		//	sendTo = "/APP/Quality/updSpecification.jsp";
			// 9/10/13 TWalton - Moved to the View Section
			sendTo = "/view/quality/updSpecification.jsp";
		    request.setAttribute("updViewBean", updSpec);
		    pageUpd(request, response);
		}
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("dtlFormula")) {
			DtlFormula formula = new DtlFormula();
			formula.populate(request);
			formula.validate();
//			ds.determineSecurity(request, response);
			request.setAttribute("dtlViewBean", formula);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Quality/dtlFormula.jsp";
			pageDtl(request, response);
		}
		if (requestType.equals("dtlMethod") ||
			requestType.equals("dtlProcedure") ||
			requestType.equals("dtlPolicy")) {
			DtlMethod method = new DtlMethod();
			method.populate(request);
			method.validate();
//			ds.determineSecurity(request, response);
			request.setAttribute("dtlViewBean", method);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Quality/dtlMethod.jsp";
			pageDtl(request, response);
		}
		if (requestType.equals("dtlSpec")) {
			DtlSpecification spec = new DtlSpecification();
			spec.populate(request);
			spec.validate();
//				ds.determineSecurity(request, response);
			request.setAttribute("dtlViewBean", spec);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Quality/dtlSpecification.jsp";
			pageDtl(request, response);
		}
		//-----------------------------------------------------------------
		//   PRODUCT DATA SHEET 
		//     Choose what is seen AND show a product data sheet with the chosen options
		//-----------------------------------------------------------------

		//TODO buildProductDataSheet
		
		if (requestType.equals("buildProductDataSheet")) {
			BuildProductDataSheet bldPDS = new BuildProductDataSheet();
			bldPDS.populate(request);
			
			bldPDS.setListAnalyticalTests(BuildProductDataSheetAttributes.populateTests(
					request, "TEST", Integer.parseInt(bldPDS.getCountAnalyticalTests())));
			bldPDS.setListMicroTests(BuildProductDataSheetAttributes.populateTests(
					request, "MICRO", Integer.parseInt(bldPDS.getCountMicroTests())));
			bldPDS.setListProcessParameters(BuildProductDataSheetAttributes.populateTests(
					request, "PROC", Integer.parseInt(bldPDS.getCountProcessParameters())));
			bldPDS.setListAdditivesAndPreservatives(BuildProductDataSheetAttributes.populateTests(
					request, "ADD", Integer.parseInt(bldPDS.getCountAdditivesAndPreservatives())));
			
			bldPDS.validate(); 
			System.out.println("bldPDS - Validate?  should validate the spec is there and active");
			request.setAttribute("bldViewBean", bldPDS);
			request.setAttribute("requestType", requestType);
			
			if (request.getParameter("previewButton") == null) {
				sendTo = "/view/quality/selectProductDataSheet.jsp";
			} else {
				sendTo = "/view/quality/dtlProductDataSheet.jsp";
			}
			
			pageBld(request, response);
		}
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//       Main Inquiry and List Pages
		//-----------------------------------------------------------------
		if (requestType.equals("inqFormula") ||
			requestType.equals("listFormula"))
		{
			InqFormula inqFormula = new InqFormula();
			inqFormula.populate(request);
//			if (is.getRequestType().equals("deleteCPGSpec"))
//				is.setRequestType(requestType);
			inqFormula.validate();
			inqFormula.determineSecurity(request, response);
			request.setAttribute("inqViewBean", inqFormula);
			request.setAttribute("requestType", requestType);
			if (requestType.equals("listFormula"))
			{
				pageList(request, response);
				sendTo = viewPath + "listFormula.jsp";
			}
			requestType = (String) request.getAttribute("requestType");
			if (requestType.equals("inqFormula"))
			{
			   pageInq(request, response);
			   sendTo = viewPath + "inqFormula.jsp";
			}
		}
		if (requestType.equals("inqMethod") ||
			requestType.equals("listMethod") ||
			requestType.equals("inqProcedure") ||
			requestType.equals("listProcedure") ||
			requestType.equals("inqPolicy") ||
			requestType.equals("listPolicy"))
		{
			InqMethod inqMethod = new InqMethod();
			inqMethod.populate(request);
//				if (is.getRequestType().equals("deleteCPGSpec"))
//					is.setRequestType(requestType);
			inqMethod.validate();
			inqMethod.determineSecurity(request, response);
			request.setAttribute("inqViewBean", inqMethod);
			request.setAttribute("requestType", requestType);
			if (requestType.equals("listMethod") ||
				requestType.equals("listProcedure") ||
				requestType.equals("listPolicy"))
			{
				pageList(request, response);
				sendTo = viewPath + "listMethod.jsp";
			}
			requestType = (String) request.getAttribute("requestType");
			if (requestType.equals("inqMethod") ||
				requestType.equals("inqProcedure") ||
				requestType.equals("inqPolicy"))
			{
			   pageInq(request, response);
			   sendTo = viewPath + "inqMethod.jsp";
			}
		}
		if (requestType.equals("inqSpec") ||
			requestType.equals("listSpec"))
		{
			InqSpecification inqSpec = new InqSpecification();
			inqSpec.populate(request);
//					if (is.getRequestType().equals("deleteCPGSpec"))
//						is.setRequestType(requestType);
			inqSpec.validate();
			inqSpec.determineSecurity(request, response);
			request.setAttribute("inqViewBean", inqSpec);
			request.setAttribute("requestType", requestType);
			if (requestType.equals("listSpec"))
			{
				pageList(request, response);
				sendTo = viewPath +"listSpecification.jsp";
			}
			requestType = (String) request.getAttribute("requestType");
			if (requestType.equals("inqSpec"))
			{
			   pageInq(request, response);
			   sendTo = viewPath +"inqSpecification.jsp";
			}
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlQuality: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
			ParameterMessageBean.printParameters(request);
			System.out.println("---------------------");
			System.out.println(theException);
			theException.printStackTrace();
		}
		return;

	}

	/*
	 * Retrieve and Build everthing needed for the Inquiry Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageInquiry(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(HttpServletRequest request,
						   HttpServletResponse response) {
	  String reqType = (String) request.getAttribute("requestType");
	//  
	 // if (reqType.equals("inqFormula"))
	 // { // get Drop Down List Information
	//	  InqFormula inqFormula = (InqFormula) request.getAttribute("inqViewBean");
	//	try{
	//	    CommonRequestBean crb = new CommonRequestBean();
	//		crb.setEnvironment(inqFormula.getEnvironment());
	//		crb.setCompanyNumber(inqFormula.getInqCompanyNumber());
	//		crb.setDivisionNumber(inqFormula.getInqDivisionNumber());
	//	    inqFormula.setDropDownStatus(ServiceQuality.dropDownStatus(crb));
	//	    request.setAttribute("inqViewBean", inqFormula);
	//	 } catch(Exception e)
	//	 {
	//		System.out.println("Problem in CtlQuality.pageInq - inqFormula:" + e);
	//	 }
	 // }
	  if (reqType.equals("inqSpec"))
	  { // get Drop Down List Information
		  InqSpecification inqSpec = (InqSpecification) request.getAttribute("inqViewBean");
		try{
		    CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment(inqSpec.getEnvironment());
			crb.setCompanyNumber(inqSpec.getCompany());
			crb.setDivisionNumber(inqSpec.getDivision());
			crb.setIdLevel1(reqType);
		 //   inqSpec.setDropDownStatus(ServiceQuality.dropDownStatus(crb));
		    inqSpec.setDropDownItemType(ServiceItem.dropDownItemType(crb));
		    inqSpec.setDropDownProductGroup(ServiceItem.dropDownProductGroup(crb));
		    inqSpec.setDropDownUser1(ServiceItem.dropDownUser1(crb));
		    request.setAttribute("inqViewBean", inqSpec);
		 } catch(Exception e)
		 {
			System.out.println("Problem in CtlQuality.pageInq - inqSpecification:" + e);
		 }
	  }
	 return;
	}

	/*
	 * Retrieve and Build everthing needed for the List Page
	 * 
	 */
	protected void pageList(HttpServletRequest request,
							HttpServletResponse response) {
	try{
	  String reqType = (String) request.getAttribute("requestType");
	  if (reqType.equals("listFormula"))
	  {
	     //-------------------------------------------------------------------------	
	     //-------- FORMULA --------------------------------------------------------
	     InqFormula inqFormula = (InqFormula) request.getAttribute("inqViewBean");
			try{
			    Vector sendParms = new Vector();
			    sendParms.addElement(inqFormula);
			    BeanQuality bq = ServiceQuality.listFormula(sendParms);
				inqFormula.setListReport(bq.getListFormula());
				
		// SECTION TO DEAL WITH GOOGLE PAGE DOWN 		
			  	// Testing Information Will Need to use Another Actual Method when ready	
//				listReport = InventoryLot.findInventoryLotInquiry(fromHeader, toHeader);

	//			String   orderInformation = il.buildPageSelectOrder("");

		//	    BigDecimal startRec = new BigDecimal("0");
			//	Integer    startRecord  = fromHeader.getStartAt();
//			    if (startRecord != null)
	//		    {
		//	    	try
		  //  		{
		    //			startRec = new BigDecimal(startRecord.intValue());    
			  //  	}
			    //	catch(Exception e)
			    	//{}
//			    }
	//		        			
		//		String switchPages = "/web/CtlLot?requestType=list" + 
			//	                     "&summaryLevel=" + il.getSummaryLevel() +
				//					  il.buildParameterResend() +
					//				  orderInformation;
						//		  
//				String[] resultsInfo = HTMLHelpers.resultSubFile(startRec,
	//															 (BigDecimal) listReport.elementAt(0),
		//														 new BigDecimal("25"),
			//												 switchPages);      			
				//request.setAttribute("resultsInfo", resultsInfo);			
//			}
	//		il.setListReport(listReport);	
				
				
		    } catch(Exception e)
		    {
		    	System.out.println("Problem found in CtlQuality.listPage when running Service to get list of Formula's: " + e);
		    }
		  request.setAttribute("inqViewBean", inqFormula);
	  }
	  if (reqType.equals("listMethod") ||
		  reqType.equals("listProcedure") ||
		  reqType.equals("listPolicy"))
	  {
	     //-------------------------------------------------------------------------	
	     //-------- METHOD - PROCEDURE - POLICY ------------------------------------
	     InqMethod inqMethod = (InqMethod) request.getAttribute("inqViewBean");
			try{
			    Vector sendParms = new Vector();
			    sendParms.addElement(inqMethod);
			    BeanQuality bq = ServiceQuality.listMethod(sendParms);
				inqMethod.setListReport(bq.getListMethod());
				
		// SECTION TO DEAL WITH GOOGLE PAGE DOWN -- copy from the Formula 		
				
		    } catch(Exception e)
		    {
		    	System.out.println("Problem found in CtlQuality.listPage when running Service to get list of Methods's: " + e);
		    }
		  request.setAttribute("inqViewBean", inqMethod);
	  }
	  if (reqType.equals("listSpec"))
	  {
	     //-------------------------------------------------------------------------	
	     //-------- SPECIFICATION --------------------------------------------------------
	     InqSpecification inqSpec = (InqSpecification) request.getAttribute("inqViewBean");
			try{
			    Vector sendParms = new Vector();
			    sendParms.addElement(inqSpec);
			    BeanQuality bq = ServiceQuality.listSpecification(sendParms);
				inqSpec.setListReport(bq.getListSpecification());
				
		// SECTION TO DEAL WITH GOOGLE PAGE DOWN -- copy from the Formula 		
				
		    } catch(Exception e)
		    {
		    	System.out.println("Problem found in CtlQuality.listPage when running Service to get list of Specification's: " + e);
		    }
		  request.setAttribute("inqViewBean", inqSpec);
	  }
	} catch(Exception e)
	{
		 System.out.println("Problem found in CtlQuality.pageList: " + e);
	}	
	}

	/*
	 * Retrieve and Build everything needed for the Detail Page
	 */
	protected void pageDtl(HttpServletRequest request,
						   HttpServletResponse response) {
		
		  String requestType = (String) request.getAttribute("requestType");
		  try
		  {
		  	if (requestType.equals("dtlFormula"))
		  		pageDtlFormula(request, response);
		  	
		  	if (requestType.equals("dtlMethod") ||
		  		requestType.equals("dtlProcedure") ||
		  		requestType.equals("dtlPolicy"))
		  		pageDtlMethod(request, response);
		  	
		  	if (requestType.equals("dtlSpec"))
		  		pageDtlSpecification(request, response);
		  	
		  } catch(Exception e){
				// Use for Debug
				System.out.println("Error Found in CtlQuality.pageDtl(): " + e);
		  }
	}

	/*
	 * Guide the Update to the correct area
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpd(HttpServletRequest request,
					   HttpServletResponse response) {
	  String requestType = (String) request.getAttribute("requestType");
	  try
	  {
		if (requestType.equals("addFormula") ||
			requestType.equals("copyNewFormula") ||
			requestType.equals("reviseFormula") ||
			requestType.equals("updFormula"))
		{
			pageUpdFormula(request, response);
		}
		if (requestType.equals("addMethod") ||
			requestType.equals("copyNewMethod") ||
			requestType.equals("reviseMethod") ||
			requestType.equals("updMethod") ||
			requestType.equals("addProcedure") ||
			requestType.equals("copyNewProcedure") ||
			requestType.equals("reviseProcedure") ||
			requestType.equals("updProcedure") ||
			requestType.equals("addPolicy") ||
			requestType.equals("copyNewPolicy") ||
			requestType.equals("revisePolicy") ||
			requestType.equals("updPolicy"))
		{
				pageUpdMethod(request, response);
		}
		if (requestType.equals("addSpec") ||
			requestType.equals("copyNewSpec") ||
			requestType.equals("reviseSpec") ||
			requestType.equals("updSpec"))
		{
			pageUpdSpecification(request, response);
		}
	  } catch(Exception e){
			// Use for Debug
//			System.out.println("Error Found in CtlQuality.pageUpd(): " + e);
	  }
   }

	/*
	 * Retrieve and Build everything needed for the FORMULA Update Page
	 *   // Update the Records and then Retrieve and Build Everything
	 *    on an update will return the NEW version
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpdFormula(HttpServletRequest request,
					   			  HttpServletResponse response) {
	  String requestType = (String) request.getAttribute("requestType");
	  try
	  {
		UpdFormula updFormula = (UpdFormula) request.getAttribute("updViewBean");
		DateTime dt = UtilityDateTime.getSystemDate();
		// ********************************************************************
		// Update the Comment/URL Sections FIRST, before anything else is done
//		// URL's and Comments -- Call the KeyValues Servlet
//		//  ONLY on and UPDATE-ADD-DELETE (Saved)
		if(!updFormula.saveButton.trim().equals("") &&
		    updFormula.getOriginalStatus().trim().equals("PD"))
		{
		  try {
			CtlKeyValues thisOne = new CtlKeyValues();
			thisOne.performTask(request, response);
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		} 		
		//  Retrieve the Values at the bottom of this method
		//*********************************************************************
		// ********************************************************************
		//  ADD a Brand New Formula -- 
		//      get the Next ID Number
		//      add the basic Formula into the file
		//*********************************************************************
		if (requestType.equals("addFormula"))
		{
			// Add a new BLANK Formula
			try
			{
				updFormula.populate(request);
				if (updFormula.getDisplayMessage().trim().equals(""))
				{
					try{
			    	  BeanQuality bqNumber = ServiceQuality.nextIDNumberFormula(updFormula.getEnvironment());
			    	  updFormula.setFormulaNumber(bqNumber.getFormula().getFormulaNumber());
					}catch(Exception e)
			       {}
					updFormula.setRevisionDate(dt.getDateFormatyyyyMMdd());
					updFormula.setRevisionTime(dt.getTimeFormathhmmss());
					updFormula.setUpdateDate(dt.getDateFormatyyyyMMdd());
					updFormula.setUpdateTime(dt.getTimeFormathhmmss());
					updFormula.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
					updFormula.setCreationDate(dt.getDateFormatyyyyMMdd());
					updFormula.setCreationTime(dt.getTimeFormathhmmss());
					updFormula.setCreationUser(SessionVariables.getSessionttiProfile(request, response));
					updFormula.setStatus("PD");
					updFormula.setOriginalStatus("PD");
					Vector sendValues = new Vector();
					sendValues.addElement(updFormula);
					BeanQuality bq = ServiceQuality.updateFormula(sendValues);
				}
			}
			catch(Exception e)
			{
				System.out.println("problem getting new number for Formula: " + e);
			}
			request.setAttribute("requestType", requestType);
			// Change to Update - for the screen

		}
		//********************************************************************
		//  COPY, CREATE REVISION -- will create a Brand New Formula Revision Combination 
		//      get the Next ID Number -- for the COPY
		//      add the basic Formula into the file
		//*********************************************************************
		if (requestType.equals("copyNewFormula") ||
			requestType.equals("reviseFormula"))
		{
			//Test the Revise Formula section...
			//Revise Formula will create a new PENDING Formula
			// Find Information from Original Formula
			try{
				DtlFormula formula = new DtlFormula();
				formula.populate(request);
				Vector sendValues = new Vector();
				sendValues.addElement(formula);
				// Just go get the Information
			    BeanQuality bq = ServiceQuality.findFormula(sendValues);
			    updFormula.loadFromBeanQuality(bq);
			    updFormula.setEnvironment(formula.getEnvironment());
			    updFormula.setCompany(formula.getCompany());
			    updFormula.setDivision(formula.getDivision());
			    updFormula.setReferenceFormulaNumber(updFormula.getFormulaNumber());
			    updFormula.setReferenceFormulaRevisionDate(updFormula.getRevisionDate());
			    updFormula.setReferenceFormulaRevisionTime(updFormula.getRevisionTime());
			    updFormula.setRevisionDate(dt.getDateFormatyyyyMMdd());
			    updFormula.setRevisionTime(dt.getTimeFormathhmmss());
			    updFormula.setUpdateDate(dt.getDateFormatyyyyMMdd());
			    updFormula.setUpdateTime(dt.getTimeFormathhmmss());
			    updFormula.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		//	    updFormula.setTargetBrix(bq.formula.getT)
			    updFormula.setStatus("PD");
			    updFormula.setOriginalStatus("PD");
			    //--- if copyNewFormula then will need to get a NEW Formula Number
			    if (requestType.equals("copyNewFormula"))
			    {
			       try{
			    	  BeanQuality bqNumber = ServiceQuality.nextIDNumberFormula(formula.getEnvironment());
			    	  updFormula.setFormulaNumber(bqNumber.getFormula().getFormulaNumber());
			    	//  System.out.println("Will need to uncomment the code and not have a default formula number");
			    	//  updFormula.setFormulaNumber(("998882"));
			    	  updFormula.setCreationDate(dt.getDateFormatyyyyMMdd());
			    	  updFormula.setCreationTime(dt.getTimeFormathhmmss());
			    	  updFormula.setCreationUser(SessionVariables.getSessionttiProfile(request, response));
			    	  
			       }catch(Exception e)
			       {}
			    }
			    //Replace Details with NEW formula Number
			    updFormula = UpdFormulaDetail.replaceRecordID(updFormula);
			    sendValues = new Vector();
			    sendValues.addElement(updFormula);
			    sendValues = UpdVariety.replaceRecordID("FORMULA", sendValues);
			    sendValues = UpdTestParameters.replaceRecordID("FORMULA", sendValues);
			    bq = ServiceQuality.updateFormula(sendValues);
			    updFormula.loadFromBeanQuality(bq);	
			   // on a copy or a revision the Comments also need to be copied
			    //  ALL of the Comment Sections displayed on the Screen
			    try {
					if (!formula.getFormulaNumber().equals("") &&
						!formula.getRevisionDate().equals("") &&
						!formula.getRevisionTime().equals(""))
					{
						KeyValue kv = new KeyValue();
						kv.setEnvironment(formula.getEnvironment().trim());
						kv.setKey1(formula.getFormulaNumber().trim());
						kv.setKey2(formula.getRevisionDate());
						kv.setKey3(formula.getRevisionTime());
						for (int x = 0; x < 7; x++)
						{
						   if (x == 0)
							  kv.setEntryType("FormulaDetailsComment");
						   if (x == 1)
							  kv.setEntryType("FormulaCalculationsComment");
						   if (x == 2)
							  kv.setEntryType("FormulaBlendingInstructionsComment");
						   if (x == 3)
							  kv.setEntryType("FormulaKeyLabelStatementsComment");    
						   if (x == 4)
							  kv.setEntryType("FormulaIngredientStatementComment");
						   if (x == 5)
							  kv.setEntryType("FormulaRevisionComment");
						   if (x == 6)
							  kv.setEntryType("FormulaRFInfoComment");
						   
							try{
								Vector listDetails = ServiceKeyValue.buildKeyValueList(kv);
								if (!listDetails.isEmpty())
								{
									for (int comment = 0; comment < listDetails.size(); comment++)
									{
										KeyValue kvAdd = (KeyValue) listDetails.elementAt(comment);
										kvAdd.setEnvironment(formula.getEnvironment().trim());
										kvAdd.setKey1(updFormula.getFormulaNumber());
										kvAdd.setKey2(updFormula.getRevisionDate());
										kvAdd.setKey3(updFormula.getRevisionTime());
									    ServiceKeyValue.addKeyValue(kvAdd);
									}
								} // only do something IF there are records
							}catch(Exception e)
							{	
							}
						}
						
					}
			    }catch(Exception e){
			    	// System.out.prinltn("Cannot find FormulaNumber");
			    }
			}catch(Exception e){
				System.out.println("CtlQuality.pageUpd, found error when adding new Formula or Revision:" + e);
				updFormula.setDisplayMessage("CtlQuality.pageUpd, found error when addingFormula:" + e);
			}
			request.setAttribute("requestType", requestType);
		}
		//********************************************************************
		//  UPDATE - a Formula Revision(Date and Time) 
		//*********************************************************************
	  	if (requestType.equals("updFormula"))
	  	{
	  		updFormula = (UpdFormula) request.getAttribute("updViewBean");
			if (updFormula.getDisplayMessage().trim().equals(""))
			{	
				BeanQuality bq = new BeanQuality();
				if (!updFormula.getSaveButton().trim().equals(""))
				{
					// Actually Update the records
					try{
						Vector sendValues = new Vector();
						sendValues.addElement(updFormula);
						updFormula.setUpdateDate(dt.getDateFormatyyyyMMdd());
						updFormula.setUpdateTime(dt.getTimeFormathhmmss());
						if (updFormula.getOriginalStatus().equals("PD"))
							bq = ServiceQuality.updateFormula(sendValues);
						
						
						if (!updFormula.getStatus().equals(updFormula.getOriginalStatus()))
						{
					    	 if (updFormula.getStatus().equals("AC") &&
					    	     updFormula.getOriginalStatus().equals("PD"))
					    	 {
						    	 //-----------------------------------------
						    	 // Start by Resetting the Date and Time for ALL of the Comment and Attachment Fields
						    	 KeyValue origKeyValue = new KeyValue();
						    	 origKeyValue.setEntryType("Formula");
						    	 origKeyValue.setKey1(updFormula.getFormulaNumber().trim());
						    	 origKeyValue.setKey2(updFormula.getRevisionDate().trim());
						    	 origKeyValue.setKey3(updFormula.getRevisionTime().trim());
						    	 origKeyValue.setKey4("");
						    	 origKeyValue.setKey5("");
						    	 
						    	 KeyValue newKeyValue = new KeyValue();
						    	 newKeyValue.setKey1(updFormula.getFormulaNumber().trim());
						    	 newKeyValue.setKey2(dt.getDateFormatyyyyMMdd());
						    	 String setTime = dt.getTimeFormathhmmss();
								 if (setTime.substring(0,1).trim().equals("0"))
									setTime = setTime.substring(1, setTime.length());
					    		 newKeyValue.setKey3(setTime);
						    	 newKeyValue.setKey4("");
						    	 newKeyValue.setKey5("");
						    	 newKeyValue.setLastUpdateDate(dt.getDateFormatyyyyMMdd());
						    	 newKeyValue.setLastUpdateTime(dt.getTimeFormathhmmss());
						    	 newKeyValue.setLastUpdateUser(updFormula.getUpdateUser());
						    	 
						    	 ServiceKeyValue.updateReplaceKeys(origKeyValue, newKeyValue);
								 // Reset the Revision Date and Time -- when a Formula becomes ACTIVE
						    	 //  that is when the Revision Date and Time needs to be SET
						    	 // This will take the Update Date Time and User and Put it into the 
						    	 //   Revision Date and Time -- ONLY when a formula becomes ACTIVE
						    		 
					     }// end of if the status changed from PD to AC
					   	 sendValues.addElement(updFormula); 
					   	bq = ServiceQuality.updateFormulaStatus(sendValues);
					} // end of the If the Status Changed	
					}catch(Exception e){
						System.out.println("CtlQuality.pageUpd, found error when updating Formula:" + e);
						updFormula.setDisplayMessage("CtlQuality.pageUpd, found error when updating Formula:" + e);
					}
				} else {
					//IF there are no Messages -- must go out and get the actual DETAIL information
					//  and Load it into the Upd Screens
					try{
						DtlFormula formula = new DtlFormula();
						formula.populate(request);
						Vector sendValues = new Vector();
						sendValues.addElement(formula);
				// 	Just go get the Information
						bq = ServiceQuality.findFormula(sendValues);
					
					}catch(Exception e){
						System.out.println("CtlQuality.pageUpd, found error when retrieving Formula:" + e);
						updFormula.setDisplayMessage("CtlQuality.pageUpd, found error when retrieving Formula:" + e);
					}
				}
				updFormula.loadFromBeanQuality(bq);		
			}	  		
	  	} // END of is updFormula
		//-----------------------------------------------------------------------------------------
	  	//-----------------------------------------------------------------------------------------
	    // Retrieve the Comments/URLs from the file
		if (!updFormula.getFormulaNumber().equals("") &&
			!updFormula.getRevisionDate().equals("") &&
			!updFormula.getRevisionTime().equals(""))
		{
			KeyValue kv = new KeyValue();
			kv.setEnvironment(updFormula.getEnvironment().trim());
			kv.setKey1(updFormula.getFormulaNumber().trim());
			kv.setKey2(updFormula.getRevisionDate());
			kv.setKey3(updFormula.getRevisionTime());
			try {  // comment
				kv.setEntryType("FormulaRevisionComment");
				updFormula.setListComments(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
				//skip it. allowed to be empty
				updFormula.setListComments(new Vector());
			}	
		// Obtain Formula Details Comments for the formula
			try { // comment1
				kv.setEntryType("FormulaDetailsComment");
				updFormula.setListDetails(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
				//skip it. allowed to be empty
				updFormula.setListDetails(new Vector());
			}	
		// Obtain Formula Calculations Comments for the formula
			try { // comment2
				kv.setEntryType("FormulaCalculationsComment");
				updFormula.setListCalculations(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
		 //skip it. allowed to be empty
				updFormula.setListCalculations(new Vector());
			}	
		// Obtain Formula Blending Instructions	
			try {
				kv.setEntryType("FormulaBlendingInstructionsComment");
				updFormula.setListBlendingInstructions(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
		 //skip it. allowed to be empty
				updFormula.setListBlendingInstructions(new Vector());
			}	
		// Obtain Blending Key Label Statements	
			try {
				kv.setEntryType("FormulaKeyLabelStatementsComment");
				updFormula.setListKeyLabelStatements(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
		 //skip it. allowed to be empty
				updFormula.setListKeyLabelStatements(new Vector());
			}
		// Obtain Ingredient Statement	
			try {
				kv.setEntryType("FormulaIngredientStatementComment");
				updFormula.setListIngredientStatement(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
		 //skip it. allowed to be empty
				updFormula.setListIngredientStatement(new Vector());
			}	
		// Obtain Additional Information related to the Raw Fruit	
			try {
				kv.setEntryType("FormulaRFInfoComment");
				updFormula.setListRFAdditionalInfo(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
		 //skip it. allowed to be empty
				updFormula.setListIngredientStatement(new Vector());
			}	
		}// end of the have formula/revision date and revision time
		
		// ***********************************************
		// Add the Supersedes Date Information 
		try{

			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment(updFormula.getEnvironment().trim());
			crb.setIdLevel1(updFormula.getFormulaNumber().trim());
			crb.setIdLevel2(updFormula.getRevisionDate().trim());
			
			updFormula.setSupersedesDate(ServiceQuality.findFormulaSupersedes(crb));
		//*************************************************	
		}catch(Exception e){
			// Use for Debug
			System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageUpdFormula(): " + e);
		}
		request.setAttribute("updViewBean", updFormula);
	  	
	  } catch(Exception e){
			// Use for Debug
			System.out.println("Error Found in CtlQuality.pageUpd(): " + e);
	  }
   }

/*
 * Retrieve and Build everything needed for the Detail Page
 */
	protected void pageDtlFormula(HttpServletRequest request,
						          HttpServletResponse response) {
		
	  String requestType = (String) request.getAttribute("requestType");
	  try
	  {
		 if (requestType.equals("dtlFormula"))
		 {
			DtlFormula dtlFormula = (DtlFormula) request.getAttribute("dtlViewBean");	
			try{
				Vector sendValues = new Vector();
				sendValues.addElement(dtlFormula);
				// 	Just go get the Information specific to the Formula Revision Date and Revision Time
				dtlFormula.setDtlBean(ServiceQuality.findFormula(sendValues));
				// Test to see if the Raw Fruit Details List needs to be removed.
				// Do not want it to display if all the target, minimum and maximum fields are 0.
				try{
					BeanQuality bq = dtlFormula.getDtlBean();
					if (dtlFormula.getRevisionDate().trim().equals(""))
					{
						dtlFormula.setRevisionDate(bq.getFormula().getRevisionDate());
						dtlFormula.setRevisionTime(bq.getFormula().getRevisionTime());
					}
					Vector listDetails = bq.getFormulaRawFruitTests();
					if (!listDetails.isEmpty())
					{
						String foundData = "";
						for (int x = 0; x < listDetails.size(); x++)
						{
							QaTestParameters qtp = (QaTestParameters) listDetails.elementAt(x);
							if (new BigDecimal(qtp.getTargetValue()).compareTo(new BigDecimal("0")) != 0 ||
								new BigDecimal(qtp.getMinimumStandard()).compareTo(new BigDecimal("0")) != 0 ||
								new BigDecimal(qtp.getMaximumStandard()).compareTo(new BigDecimal("0")) != 0 )
								foundData = "Y";
						}
						if (foundData.trim().equals(""))
						{
							listDetails = new Vector();
							bq.setFormulaRawFruitTests(listDetails);
							dtlFormula.setDtlBean(bq);
						}
					}
					if (!bq.getFormulaPreBlendSauce().isEmpty())
						dtlFormula.processSaucePreBlend(bq.getFormulaPreBlendSauce());
				
				}catch(Exception e){}
			}catch(Exception e){
				System.out.println("CtlQuality.pageDtl, found error when retrieving Formula:" + e);
				dtlFormula.setDisplayMessage("CtlQuality.pageDtl, found error when retrieving Formula:" + e);
			}
			//-------------------------------------------------------
			// Retrieves all of the Comment Style Elements
			if (!dtlFormula.getFormulaNumber().equals("") &&
				!dtlFormula.getRevisionDate().equals("") &&
				!dtlFormula.getRevisionTime().equals(""))
			{
				KeyValue kv = new KeyValue();
				kv.setEnvironment(dtlFormula.getEnvironment().trim());
				kv.setKey1(dtlFormula.getFormulaNumber().trim());
				kv.setKey2(dtlFormula.getRevisionDate());
				kv.setKey3(dtlFormula.getRevisionTime());
				try {  // comment
					kv.setEntryType("FormulaRevisionComment");
					dtlFormula.setListComments(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
					//skip it. allowed to be empty
					dtlFormula.setListComments(new Vector());
				}	
			// Obtain Formula Details Comments for the formula
				try { // comment1
					kv.setEntryType("FormulaDetailsComment");
					dtlFormula.setListDetails(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
					//skip it. allowed to be empty
					dtlFormula.setListDetails(new Vector());
				}	
			// Obtain Formula Calculations Comments for the formula
				try { // comment2
					kv.setEntryType("FormulaCalculationsComment");
					dtlFormula.setListCalculations(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
			 //skip it. allowed to be empty
					dtlFormula.setListCalculations(new Vector());
				}	
			// Obtain Formula Blending Instructions	
				try {
					kv.setEntryType("FormulaBlendingInstructionsComment");
					dtlFormula.setListBlendingInstructions(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
			 //skip it. allowed to be empty
					dtlFormula.setListBlendingInstructions(new Vector());
				}	
			// Obtain Blending Key Label Statements	
				try {
					kv.setEntryType("FormulaKeyLabelStatementsComment");
					dtlFormula.setListKeyLabelStatements(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
			 //skip it. allowed to be empty
					dtlFormula.setListKeyLabelStatements(new Vector());
				}
			// Obtain Ingredient Statement	
				try {
					kv.setEntryType("FormulaIngredientStatementComment");
					dtlFormula.setListIngredientStatement(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
			 //skip it. allowed to be empty
					dtlFormula.setListIngredientStatement(new Vector());
				}		
			// Obtain Additional Raw Fruit Information	
				try {
					kv.setEntryType("FormulaRFInfoComment");
					dtlFormula.setListRFAdditionalInfo(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e)
				{
			 //skip it. allowed to be empty
					dtlFormula.setListIngredientStatement(new Vector());
				}		
			}
			// ***********************************************
			// Add the Supersedes Date Information 
			try{

				CommonRequestBean crb = new CommonRequestBean();
				crb.setCompanyNumber("100");
				crb.setDivisionNumber("100");
				crb.setEnvironment(dtlFormula.getEnvironment().trim());
				crb.setIdLevel1(dtlFormula.getFormulaNumber().trim());
				crb.setIdLevel2(dtlFormula.getRevisionDate().trim());
				
				dtlFormula.setSupersedesDate(ServiceQuality.findFormulaSupersedes(crb));
				
			}catch(Exception e){
				// Use for Debug
				System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageDtlFormula(): " + e);
			}	
			//*************************************************
			
			request.setAttribute("displayView", "detail");
			request.setAttribute("dtlViewBean", dtlFormula);
		 }
	  } catch(Exception e){
			System.out.println("Error Found in CtlQuality.pageDtlFormula(): " + e);
	  }		  
	}

	/*
	 * Retrieve and Build everything needed for the Detail Page
	 */
	protected void pageDtlMethod(HttpServletRequest request,
						         HttpServletResponse response) {
		
	  String requestType = (String) request.getAttribute("requestType");
	  try
	  {
	  	if (requestType.equals("dtlMethod") ||
	  		requestType.equals("dtlProcedure") ||
	  		requestType.equals("dtlPolicy"))
	  	{
			DtlMethod dtlMethod = (DtlMethod) request.getAttribute("dtlViewBean");	
			try{
				Vector sendValues = new Vector();
				sendValues.addElement(dtlMethod);
				// 	Just go get the Information
				dtlMethod.setDtlBean(ServiceQuality.findMethod(sendValues));
				
				if (dtlMethod.getRevisionDate().trim().equals(""))
				{
					dtlMethod.setRevisionDate(dtlMethod.getDtlBean().getMethod().getRevisionDate());
					dtlMethod.setRevisionTime(dtlMethod.getDtlBean().getMethod().getRevisionTime());
				}
		
			}catch(Exception e){
				System.out.println("CtlQuality.pageDtl, found error when retrieving Method:" + dtlMethod.getMethodNumber() + ": " + e);
				dtlMethod.setDisplayMessage("CtlQuality.pageDtl, found error when retrieving Method:" + dtlMethod.getMethodNumber() + ": " + e);
			}
			
			//*************************************************
			// Retrieve ALL of the Comments -- sections for this methods..
			if (!dtlMethod.getMethodNumber().equals("") &&
				!dtlMethod.getRevisionDate().equals("") &&
				!dtlMethod.getRevisionTime().equals(""))
			{  	
			  	KeyValue kv = new KeyValue();
				kv.setEnvironment(dtlMethod.getEnvironment().trim());
				kv.setKey1(dtlMethod.getMethodNumber().trim());
				kv.setKey2(dtlMethod.getRevisionDate());
				kv.setKey3(dtlMethod.getRevisionTime());
			  	//--------------------------------------------------
				// Specifically used for Methods
			//--------------------------------------------------	
				if (requestType.trim().equals("dtlMethod")) 
				{
					//  Theory & Principle
					try {
						kv.setEntryType("MethodRevisionComment");
						dtlMethod.setListTheory(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListTheory(new Vector());
					}	  		
					try {
						kv.setEntryType("MethodRevisionUrl");
						dtlMethod.setListTheoryUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListTheoryUrl(new Vector());
					}	  
					//-------------------------------------------------
					//  Equipment 
					try {
						kv.setEntryType("MethodRevisionComment1");
						dtlMethod.setListEquipment(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListEquipment(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl1");
						dtlMethod.setListEquipmentUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListEquipmentUrl(new Vector());
					}	  
					//	-------------------------------------------------
					//  Procedure
					try {
						kv.setEntryType("MethodRevisionComment2");
						dtlMethod.setListProcedure(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListProcedure(new Vector());
				    }
					try {
						kv.setEntryType("MethodRevisionUrl2");
						dtlMethod.setListProcedureUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListProcedureUrl(new Vector());
					}	  
					// ---------------------------------------------------	
					//  Comments -- Additional Information
					try {
						kv.setEntryType("MethodRevisionComment3");
						dtlMethod.setListAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAdditional(new Vector());
				    }	
					try {
						kv.setEntryType("MethodRevisionUrl3");
						dtlMethod.setListAdditionalUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAdditionalUrl(new Vector());
					}	  
					//----------------------------------------------------
					// Reagents
					try {
						kv.setEntryType("MethodRevisionComment4");
						dtlMethod.setListReagents(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListReagents(new Vector());
					}	  		
					try {
						kv.setEntryType("MethodRevisionUrl4");
						dtlMethod.setListReagentsUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListReagentsUrl(new Vector());
					}	 
					//-------------------------------------------------
					//  Caution 
					try {
						kv.setEntryType("MethodRevisionComment5");
						dtlMethod.setListCaution(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListCaution(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl5");
						dtlMethod.setListCautionUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListCautionUrl(new Vector());
					}	 
					//	-------------------------------------------------
					//  Frequency
					try {
						kv.setEntryType("MethodRevisionComment6");
						dtlMethod.setListFrequency(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListFrequency(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl6");
						dtlMethod.setListFrequencyUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListFrequencyUrl(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Examples
					try {
						kv.setEntryType("MethodRevisionComment7");
						dtlMethod.setListExamples(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListExamples(new Vector());
					}	
					try {
						kv.setEntryType("MethodRevisionUrl7");
						dtlMethod.setListExamplesUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListExamplesUrl(new Vector());
					}	 
					//----------------------------------------------------
					//  Calculations
					try {
						kv.setEntryType("MethodRevisionComment8");
						dtlMethod.setListCalculations(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListCalculations(new Vector());
					}	  		
					try {
						kv.setEntryType("MethodRevisionUrl8");
						dtlMethod.setListCalculationsUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListCalculationsUrl(new Vector());
					}	 
					//-------------------------------------------------
					//  Interpretation of Results
					try {
						kv.setEntryType("MethodRevisionComment9");
						dtlMethod.setListInterpretation(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListInterpretation(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl9");
						dtlMethod.setListInterpretationUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListInterpretationUrl(new Vector());
					}	 
					//	-------------------------------------------------
					//  Actions
					try {
						kv.setEntryType("MethodRevisionComment10");
						dtlMethod.setListActions(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListActions(new Vector());
				    }
					try {
						kv.setEntryType("MethodRevisionUrl10");
						dtlMethod.setListActionsUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListActionsUrl(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Reporting
					try {
						kv.setEntryType("MethodRevisionComment11");
						dtlMethod.setListReporting(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListReporting(new Vector());
					}	 
					try {
						kv.setEntryType("MethodRevisionUrl11");
						dtlMethod.setListReportingUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListReportingUrl(new Vector());
					}	 
				}
			//--------------------------------------------------
				// Specifically used for Procedures
			//--------------------------------------------------	
				//	-------------------------------------------------
				if (requestType.trim().equals("dtlProcedure")) 
				{
					//-------------------------------------------------
					//  Definitions
					try {
						kv.setEntryType("MethodRevisionComment1");
						dtlMethod.setListDefinitions(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListDefinitions(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl1");
						dtlMethod.setListDefinitionsUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListDefinitionsUrl(new Vector());
					}	 
					//	-------------------------------------------------
					//  Procedure
					try {
						kv.setEntryType("MethodRevisionComment2");
						dtlMethod.setListProcedure(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListProcedure(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl2");
						dtlMethod.setListProcedureUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListProcedureUrl(new Vector());
					}	 
					//---------------------------------------------------
					// Comments (Additional Information)
					try {
						kv.setEntryType("MethodRevisionComment3");
						dtlMethod.setListAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAdditional(new Vector());
				    }	 
					try {
						kv.setEntryType("MethodRevisionUrl3");
						dtlMethod.setListAdditionalUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAdditionalUrl(new Vector());
					}	 
					//---------------------------------------------------
					//  Policy
					try {
						kv.setEntryType("MethodRevisionComment4");
						dtlMethod.setListPolicy(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListPolicy(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl4");
						dtlMethod.setListPolicyUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListPolicyUrl(new Vector());
					}	 
					//-------------------------------------------------
					//  Purpose 
					try {
						kv.setEntryType("MethodRevisionComment5");
						dtlMethod.setListPurpose(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListPurpose(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl5");
						dtlMethod.setListPurposeUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListPurposeUrl(new Vector());
					}	 
					//-------------------------------------------------
					//  Scope 
					try {
						kv.setEntryType("MethodRevisionComment6");
						dtlMethod.setListScope(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListScope(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl6");
						dtlMethod.setListScopeUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListScopeUrl(new Vector());
					}	 
					//-------------------------------------------------
					//  Responsibility
					try {
						kv.setEntryType("MethodRevisionComment7");
						dtlMethod.setListResponsibility(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListResponsibility(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl7");
						dtlMethod.setListResponsibilityUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListResponsibilityUrl(new Vector());
					}	 
				}
			//--------------------------------------------------
				// Specifically used for Policies
			//--------------------------------------------------				
				//-------------------------------------------------
				if (requestType.trim().equals("dtlPolicy")) 
				{
					//-------------------------------------------------
					//  Authorization
					try {
						kv.setEntryType("MethodRevisionComment1");
						dtlMethod.setListAuthorization(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAuthorization(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl1");
						dtlMethod.setListAuthorizationUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAuthorizationUrl(new Vector());
					}	 
					//---------------------------------------------------
					// Comments (Additional Information)
					try {
						kv.setEntryType("MethodRevisionComment3");
						dtlMethod.setListAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAdditional(new Vector());
				    }
					try {
						kv.setEntryType("MethodRevisionUrl3");
						dtlMethod.setListAdditionalUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListAdditionalUrl(new Vector());
					}	 
					//---------------------------------------------------
					//  Policy
					try {
						kv.setEntryType("MethodRevisionComment4");
						dtlMethod.setListPolicy(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListPolicy(new Vector());
					}	 
					try {
						kv.setEntryType("MethodRevisionUrl4");
						dtlMethod.setListPolicyUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListPolicyUrl(new Vector());
					}	 
					//-------------------------------------------------
					//  Actions
					try {
						kv.setEntryType("MethodRevisionComment10");
						dtlMethod.setListActions(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListActions(new Vector());
					}
					try {
						kv.setEntryType("MethodRevisionUrl10");
						dtlMethod.setListActionsUrl(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlMethod.setListActionsUrl(new Vector());
					}	 
				}
			}
			// ***********************************************
			// Add the Supersedes Date Information 
			try{

				CommonRequestBean crb = new CommonRequestBean();
				crb.setCompanyNumber("100");
				crb.setDivisionNumber("100");
				crb.setEnvironment(dtlMethod.getEnvironment().trim());
				crb.setIdLevel1(dtlMethod.getMethodNumber().trim());
				crb.setIdLevel2(dtlMethod.getRevisionDate().trim());
				
				dtlMethod.setSupersedesDate(ServiceQuality.findMethodSupersedes(crb));
				
			}catch(Exception e){
				// Use for Debug
				System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageDtlMethod(): " + e);
			}
			//*************************************************

			request.setAttribute("displayView", "detail");
			request.setAttribute("dtlViewBean", dtlMethod);
				
	  	}
	  } catch(Exception e){
			// Use for Debug
			System.out.println("Error Found in CtlQuality.pageDtlMethod(): " + e);
	  }
	}

	/*
	 * Retrieve and Build everything needed for the FORMULA Update Page
	 *   // Update the Records and then Retrieve and Build Everything
	 *    on an update will return the NEW version
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpdMethod(HttpServletRequest request,
					   			  HttpServletResponse response) {
	  String requestType = (String) request.getAttribute("requestType");
	  String origRequestType = requestType;
	  UpdMethod updMethod = new UpdMethod();
	  DateTime dt = UtilityDateTime.getSystemDate();
	  try
	  {
		  updMethod = (UpdMethod) request.getAttribute("updViewBean");
		// ********************************************************************
			// Update the Comment/URL Sections FIRST, before anything else is done
//			// URL's and Comments -- Call the KeyValues Servlet
//			//  ONLY on and UPDATE-ADD-DELETE (Saved)
			if(!updMethod.saveButton.trim().equals("") &&
				updMethod.getOriginalStatus().trim().equals("PD"))
			{
			  try {
				CtlKeyValues thisOne = new CtlKeyValues();
				thisOne.performTask(request, response);
			  } catch (Exception e) {
				e.printStackTrace();
			  }
			} 		
			//  Retrieve the Values at the bottom of this method
		  
		  DtlMethod method = null;
		  if (requestType.equals("addMethod") ||
			  requestType.equals("addProcedure") ||
			  requestType.equals("addPolicy"))
		  {
			 try
			 {
				updMethod.populate(request);
				updMethod.validate();
				try{
			    	  BeanQuality bqNumber = ServiceQuality.nextIDNumberMethod(updMethod.getEnvironment());
			    	  updMethod.setMethodNumber(bqNumber.getMethod().getMethodNumber());
			       }catch(Exception e)
			       {}
			    updMethod.setRevisionDate(dt.getDateFormatyyyyMMdd());
				updMethod.setRevisionTime(dt.getTimeFormathhmmss());
				updMethod.setUpdateDate(dt.getDateFormatyyyyMMdd());
				updMethod.setUpdateTime(dt.getTimeFormathhmmss());
				updMethod.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
				updMethod.setCreationDate(dt.getDateFormatyyyyMMdd());
				updMethod.setCreationTime(dt.getTimeFormathhmmss());
				updMethod.setCreationUser(SessionVariables.getSessionttiProfile(request, response));
				updMethod.setStatus("PD");
				Vector sendValues = new Vector();
			    sendValues.addElement(updMethod);
			    BeanQuality bq = ServiceQuality.updateMethod(sendValues);
			    updMethod.setUpdBean(bq);
			    updMethod.setSaveButton("");
			    if (requestType.equals("addMethod"))
			    {
				   updMethod.setRequestType("updMethod");
				   requestType = "updMethod";
			    }
			    if (requestType.equals("addProcedure"))
			    {
			    	updMethod.setRequestType("updProcedure");
			    	requestType = "updProcedure";
			    }
			    if (requestType.equals("addPolicy"))
			    {
			    	updMethod.setRequestType("updPolicy");
			    	requestType = "updPolicy";
			    }
				request.setAttribute("updViewBean", updMethod);
				method = new DtlMethod();
				method.setEnvironment(updMethod.getEnvironment());
				method.setMethodNumber(updMethod.getMethodNumber());
				method.setRevisionDate(updMethod.getRevisionDate());
				method.setRevisionTime(updMethod.getRevisionTime());
			}
			catch(Exception e)
			{	System.out.println("problem getting new number for Method: " + e);
			}
		  }
		if (requestType.equals("copyNewMethod") ||
			requestType.equals("reviseMethod") ||
			requestType.equals("copyNewProcedure") ||
			requestType.equals("reviseProcedure") ||
			requestType.equals("copyNewPolicy") ||
			requestType.equals("revisePolicy"))
		{
//			//Test the Revise Method section...
//			//Revise Method will create a new PENDING Method
//			// Find Information from Original Method
			updMethod = new UpdMethod();
			try{
				method = new DtlMethod();
				method.populate(request);
				Vector sendValues = new Vector();
				sendValues.addElement(method);
				// Just go get the Information
			    BeanQuality bq = ServiceQuality.findMethod(sendValues);
			    updMethod.loadFromBeanQuality(bq);
			    updMethod.setEnvironment(method.getEnvironment());
			    updMethod.setCompany(method.getCompany());
			    updMethod.setDivision(method.getDivision());
			    updMethod.setRequestType(requestType);
			    
			    updMethod.setRevisionDate(dt.getDateFormatyyyyMMdd());
			    updMethod.setRevisionTime(dt.getTimeFormathhmmss());
			    updMethod.setUpdateDate(dt.getDateFormatyyyyMMdd());
			    updMethod.setUpdateTime(dt.getTimeFormathhmmss());
			    updMethod.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			    updMethod.setStatus("PD");
			    updMethod.setOriginalStatus("PD");
			    //--- if copyNewMethod then will need to get a NEW Method Number
			    if (requestType.equals("copyNewMethod") ||
			    	requestType.equals("copyNewProcedure") ||
			    	requestType.equals("copyNewPolicy"))
			    {
			       try{
			    	  BeanQuality bqNumber = ServiceQuality.nextIDNumberMethod(method.getEnvironment());
			    	  updMethod.setMethodNumber(bqNumber.getMethod().getMethodNumber());
			    //	  System.out.println("Will need to uncomment the code and not have a default formula number");
			    //	  updFormula.setFormulaNumber(("998878"));
			    	  updMethod.setCreationDate(dt.getDateFormatyyyyMMdd());
			    	  updMethod.setCreationTime(dt.getTimeFormathhmmss());
			    	  updMethod.setCreationUser(SessionVariables.getSessionttiProfile(request, response));
			       }catch(Exception e)
			       {}
			    }
			    // Detail section not needed for Methods

			    sendValues = new Vector();
			    sendValues.addElement(updMethod);
			    bq = ServiceQuality.updateMethod(sendValues);
//			   // on a copy or a revision the Comments also need to be copied
//			    // get list of comments to Copy FROM...
//			  //  The Details of the Method-------
			    if (!method.getMethodNumber().equals("") &&
					!method.getRevisionDate().equals("") &&
					!method.getRevisionTime().equals(""))
			    {
			    	// get the information from the OLD version/method 
			    	KeyValue kvOld = new KeyValue();
			    	kvOld.setEnvironment(method.getEnvironment().trim());
					kvOld.setKey1(method.getMethodNumber().trim());
					kvOld.setKey2(method.getRevisionDate());
					kvOld.setKey3(method.getRevisionTime());

					for (int x = 0; x < 20; x++)
					{
					   if (x == 0)
					      kvOld.setEntryType("MethodRevisionComment");
					   else
						  kvOld.setEntryType("MethodRevisionComment" + x);
					   try{
						  Vector listDetails = ServiceKeyValue.buildKeyValueList(kvOld);
						  if (listDetails.size() > 0)
						  {
							 for (int comment = 0; comment < listDetails.size(); comment++)
							 {
								KeyValue kvAdd = (KeyValue) listDetails.elementAt(comment);
								kvAdd.setEnvironment(method.getEnvironment().trim());
								kvAdd.setKey1(updMethod.getMethodNumber());
								kvAdd.setKey2(updMethod.getRevisionDate());
								kvAdd.setKey3(updMethod.getRevisionTime());
								try{
							       ServiceKeyValue.addKeyValue(kvAdd);
								}catch(Exception e)
								{
									System.out.println("error found in the addKeyValues for a Method:MethodRevisionComment" + x + ": " + e);
								}
							 } // end of the for loop -- listing of detailed comments
						  } // only do something IF there are records
					   }catch(Exception e)
					   {
						   System.out.println("error found in getting a list of the KeyValues for a Method:MethodRevisionComment" + x + ": " + e); 
					   }
					} // end of the For loop
					
					
					
					//  copy the URLs
			    	KeyValue kvOldUrl = new KeyValue();
			    	kvOldUrl.setEnvironment(method.getEnvironment().trim());
					kvOldUrl.setKey1(method.getMethodNumber().trim());
					kvOldUrl.setKey2(method.getRevisionDate());
					kvOldUrl.setKey3(method.getRevisionTime());

					for (int x = 0; x < 20; x++)
					{
					   if (x == 0)
					      kvOldUrl.setEntryType("MethodRevisionUrl");
					   else
						  kvOldUrl.setEntryType("MethodRevisionUrl" + x);
					   try{
						  Vector listDetails = ServiceKeyValue.buildKeyValueList(kvOldUrl);
						  if (listDetails.size() > 0)
						  {
							 for (int url = 0; url < listDetails.size(); url++)
							 {
								KeyValue kvAdd = (KeyValue) listDetails.elementAt(url);
								kvAdd.setEnvironment(method.getEnvironment().trim());
								kvAdd.setKey1(updMethod.getMethodNumber());
								kvAdd.setKey2(updMethod.getRevisionDate());
								kvAdd.setKey3(updMethod.getRevisionTime());
								try{
							       ServiceKeyValue.addKeyValue(kvAdd);
								}catch(Exception e)
								{
									System.out.println("error found in the addKeyValues for a Method:MethodRevisionComment" + x + ": " + e);
								}
							 } // end of the for loop -- listing of detailed comments
						  } // only do something IF there are records
					   }catch(Exception e)
					   {
						   System.out.println("error found in getting a list of the KeyValues for a Method:MethodRevisionComment" + x + ": " + e); 
					   }
					} // end of the For loop
					
					
					
			    }
			}catch(Exception e){
				System.out.println("CtlQuality.pageUpdMethod, found error when adding new Method or Revision:" + e);
				updMethod.setDisplayMessage("CtlQuality.pageUpdMethod, found error when addingMethod:" + e);
			}
			request.setAttribute("requestType", requestType);
			request.setAttribute("updViewBean", updMethod);
		}
	  	if (requestType.equals("updMethod") ||
	  		requestType.equals("updProcedure") ||
	  		requestType.equals("updPolicy"))
	  	{
			updMethod = (UpdMethod) request.getAttribute("updViewBean");

			//updFormula.buildDropDownVectors();
			BeanQuality bq = new BeanQuality();
			if(!updMethod.saveButton.trim().equals("")){
				try{
					 Vector sendValues = new Vector();
					 
				     updMethod.setUpdateDate(dt.getDateFormatyyyyMMdd());
				     updMethod.setUpdateTime(dt.getTimeFormathhmmss());
				     updMethod.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
				     if (updMethod.getStatus().equals("PD"))
				     {
				        sendValues.addElement(updMethod); 
				        bq = ServiceQuality.updateMethod(sendValues);
				     }
				     if (!updMethod.getStatus().equals(updMethod.getOriginalStatus()))
				     {
				    	 if (updMethod.getStatus().equals("AC") &&
				    	     updMethod.getOriginalStatus().equals("PD"))
				    	 {
				    		 //-----------------------------------------
				    		 // Start by Resetting the Date and Time for ALL of the Comment and Attachment Fields
				    		 KeyValue origKeyValue = new KeyValue();
				    		 origKeyValue.setEntryType("MethodRevision");
				    		 origKeyValue.setKey1(updMethod.getMethodNumber().trim());
			//	    System.out.println("decide if I need to adjust the date");
			//	    System.out.println(updMethod.getRevisionDate());
				    		 origKeyValue.setKey2(updMethod.getRevisionDate().trim());
			//	    System.out.println("decide if I need to adjust the time");
			//	    System.out.println(updMethod.getRevisionTime());				    		 
				    		 origKeyValue.setKey3(updMethod.getRevisionTime().trim());
				    		 origKeyValue.setKey4("");
				    		 origKeyValue.setKey5("");
				    		 
				    		 KeyValue newKeyValue = new KeyValue();
				    		 newKeyValue.setKey1(updMethod.getMethodNumber().trim());
				    		 newKeyValue.setKey2(dt.getDateFormatyyyyMMdd());
				    		 String setTime = dt.getTimeFormathhmmss();
							 if (setTime.substring(0,1).trim().equals("0"))
								setTime = setTime.substring(1, setTime.length());
				    		 newKeyValue.setKey3(setTime);
				    		 newKeyValue.setKey4("");
				    		 newKeyValue.setKey5("");
				    		 newKeyValue.setLastUpdateDate(dt.getDateFormatyyyyMMdd());
				    		 newKeyValue.setLastUpdateTime(dt.getTimeFormathhmmss());
				    		 newKeyValue.setLastUpdateUser(updMethod.getUpdateUser());
				    		 
				    		 ServiceKeyValue.updateReplaceKeys(origKeyValue, newKeyValue);

				    		 // Reset the Revision Date and Time -- when a Method becomes ACTIVE
				    		 //  that is when the Revision Date and Time needs to be SET
				    		 // This will take the Update Date Time and User and Put it into the 
				    		 //   Revision Date and Time -- ONLY when a method becomes ACTIVE
				    	 }
				    	 sendValues.addElement(updMethod); 
				    	 bq = ServiceQuality.updateMethodStatus(sendValues);
				     }
				}
				catch(Exception e)
				{
					System.out.println("CtlQuality.pageUpdMethod, found error when updating Method:" + e);
					updMethod.setDisplayMessage("CtlQuality.pageUpdMethod, found error when updating Method:" + e);
				}
			} // end of if the save button was pushed
			try{
				if (method == null)
				{
				  method = new DtlMethod();
				  method.populate(request);
				  if (!updMethod.saveButton.trim().equals("") &&
					  updMethod.getDisplayMessage().trim().equals("") &&
					  updMethod.originalStatus.equals("PD") &&
					  updMethod.status.equals("AC"))
				  {
					 method.setRevisionDate(updMethod.getUpdateDate());
					 method.setRevisionTime(updMethod.getUpdateTime());
				  }
				}
				Vector sendValues = new Vector();
				sendValues.addElement(method);
				// Just go get the Information
			    bq = ServiceQuality.findMethod(sendValues);
				
			}catch(Exception e){
				System.out.println("CtlQuality.pageUpdMethod, found error when retrieving Method:" + e);
				updMethod.setDisplayMessage("CtlQuality.pageUpdMethod, found error when retrieving Method:" + e);
			}
			
			if (updMethod.getDisplayMessage().trim().equals("") &&
				!origRequestType.equals("addMethod") &&
				!origRequestType.equals("addProcedure") &&
				!origRequestType.equals("addPolicy"))
			   updMethod.loadFromBeanQuality(bq);

	  	} // END of is updMethod
	

		//*************************************************
	  	// Retrieve ALL of the Comments -- sections for this methods..
	  	if (!updMethod.getMethodNumber().equals("") &&
			!updMethod.getRevisionDate().equals("") &&
			!updMethod.getRevisionTime().equals(""))
		{  	
	  		KeyValue kv = new KeyValue();
			kv.setEnvironment(updMethod.getEnvironment().trim());
			kv.setKey1(updMethod.getMethodNumber().trim());
			kv.setKey2(updMethod.getRevisionDate());
			kv.setKey3(updMethod.getRevisionTime());
	  	//--------------------------------------------------
			// Specifically used for Methods
		//--------------------------------------------------	
			if (requestType.trim().equals("addMethod") ||
				requestType.trim().equals("updMethod") ||
				requestType.trim().equals("reviseMethod") ||
				requestType.trim().equals("copyNewMethod")) 
			{
				//  Theory & Principle
				try {
					kv.setEntryType("MethodRevisionComment");
					updMethod.setListTheory(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListTheory(new Vector());
				}	  	
				try {
					kv.setEntryType("MethodRevisionUrl");
					updMethod.setListTheoryUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListTheoryUrl(new Vector());
				}	  	
				//-------------------------------------------------
				//  Equipment 
				try {
					kv.setEntryType("MethodRevisionComment1");
					updMethod.setListEquipment(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListEquipment(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl1");
					updMethod.setListEquipmentUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListEquipmentUrl(new Vector());
				}
				//	-------------------------------------------------
				//  Procedure
				try {
					kv.setEntryType("MethodRevisionComment2");
					updMethod.setListProcedure(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListProcedure(new Vector());
			    }
				try {
					kv.setEntryType("MethodRevisionUrl2");
					updMethod.setListProcedureUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListProcedureUrl(new Vector());
			    }
				// ---------------------------------------------------	
				//  Comments -- Additional Information
				try {
					kv.setEntryType("MethodRevisionComment3");
					updMethod.setListAdditional(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAdditional(new Vector());
			    }	
				try {
					kv.setEntryType("MethodRevisionUrl3");
					updMethod.setListAdditionalUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAdditionalUrl(new Vector());
			    }	
				//----------------------------------------------------
				// Reagents
				try {
					kv.setEntryType("MethodRevisionComment4");
					updMethod.setListReagents(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListReagents(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl4");
					updMethod.setListReagentsUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListReagentsUrl(new Vector());
				}	 
				//-------------------------------------------------
				//  Caution 
				try {
					kv.setEntryType("MethodRevisionComment5");
					updMethod.setListCaution(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListCaution(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl5");
					updMethod.setListCautionUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListCautionUrl(new Vector());
				}
				//	-------------------------------------------------
				//  Frequency
				try {
					kv.setEntryType("MethodRevisionComment6");
					updMethod.setListFrequency(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListFrequency(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl6");
					updMethod.setListFrequencyUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListFrequencyUrl(new Vector());
				}
				// ---------------------------------------------------	
				//  Examples
				try {
					kv.setEntryType("MethodRevisionComment7");
					updMethod.setListExamples(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListExamples(new Vector());
				}	
				try {
					kv.setEntryType("MethodRevisionUrl7");
					updMethod.setListExamplesUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListExamplesUrl(new Vector());
				}	
				//----------------------------------------------------
				//  Calculations
				try {
					kv.setEntryType("MethodRevisionComment8");
					updMethod.setListCalculations(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListCalculations(new Vector());
				}	 
				try {
					kv.setEntryType("MethodRevisionUrl8");
					updMethod.setListCalculationsUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListCalculationsUrl(new Vector());
				}	 
				//-------------------------------------------------
				//  Interpretation of Results
				try {
					kv.setEntryType("MethodRevisionComment9");
					updMethod.setListInterpretation(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListInterpretation(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl9");
					updMethod.setListInterpretationUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListInterpretationUrl(new Vector());
				}
				//	-------------------------------------------------
				//  Actions
				try {
					kv.setEntryType("MethodRevisionComment10");
					updMethod.setListActions(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListActions(new Vector());
			    }
				try {
					kv.setEntryType("MethodRevisionUrl10");
					updMethod.setListActionsUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListActionsUrl(new Vector());
			    }
				// ---------------------------------------------------	
				//  Reporting
				try {
					kv.setEntryType("MethodRevisionComment11");
					updMethod.setListReporting(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListReporting(new Vector());
				}	 
				try {
					kv.setEntryType("MethodRevisionUrl11");
					updMethod.setListReportingUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListReportingUrl(new Vector());
				}	 
			}
		//--------------------------------------------------
			// Specifically used for Procedures
		//--------------------------------------------------	
			//	-------------------------------------------------
			if (requestType.trim().equals("addProcedure") ||
				requestType.trim().equals("updProcedure") ||
				requestType.trim().equals("reviseProcedure") ||
				requestType.trim().equals("copyNewProcedure")) 
			{
				//-------------------------------------------------
				//  Definitions
				try {
					kv.setEntryType("MethodRevisionComment1");
					updMethod.setListDefinitions(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListDefinitions(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl1");
					updMethod.setListDefinitionsUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListDefinitionsUrl(new Vector());
				}
				//	-------------------------------------------------
				//  Procedure
				try {
					kv.setEntryType("MethodRevisionComment2");
					updMethod.setListProcedure(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListProcedure(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl2");
					updMethod.setListProcedureUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListProcedureUrl(new Vector());
				}
				//---------------------------------------------------
				// Comments (Additional Information)
				try {
					kv.setEntryType("MethodRevisionComment3");
					updMethod.setListAdditional(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAdditional(new Vector());
			    }	
				try {
					kv.setEntryType("MethodRevisionUrl3");
					updMethod.setListAdditionalUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAdditionalUrl(new Vector());
			    }	 
				//---------------------------------------------------
				//  Policy
				try {
					kv.setEntryType("MethodRevisionComment4");
					updMethod.setListPolicy(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListPolicy(new Vector());
				}	  	
				try {
					kv.setEntryType("MethodRevisionUrl4");
					updMethod.setListPolicyUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListPolicyUrl(new Vector());
				}	 
				//-------------------------------------------------
				//  Purpose 
				try {
					kv.setEntryType("MethodRevisionComment5");
					updMethod.setListPurpose(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListPurpose(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl5");
					updMethod.setListPurposeUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListPurposeUrl(new Vector());
				}
				//-------------------------------------------------
				//  Scope 
				try {
					kv.setEntryType("MethodRevisionComment6");
					updMethod.setListScope(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListScope(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl6");
					updMethod.setListScopeUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListScopeUrl(new Vector());
				}
				//-------------------------------------------------
				//  Responsibility
				try {
					kv.setEntryType("MethodRevisionComment7");
					updMethod.setListResponsibility(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListResponsibility(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl7");
					updMethod.setListResponsibilityUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListResponsibilityUrl(new Vector());
				}
			}
		//--------------------------------------------------
			// Specifically used for Policies
		//--------------------------------------------------				
			//-------------------------------------------------
			if (requestType.trim().equals("addPolicy") ||
				requestType.trim().equals("updPolicy") ||
				requestType.trim().equals("revisePolicy") ||
				requestType.trim().equals("copyNewPolicy")) 
			{
				//-------------------------------------------------
				//  Authorization
				try {
					kv.setEntryType("MethodRevisionComment1");
					updMethod.setListAuthorization(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAuthorization(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl1");
					updMethod.setListAuthorizationUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAuthorizationUrl(new Vector());
				}
				//---------------------------------------------------
				// Comments (Additional Information)
				try {
					kv.setEntryType("MethodRevisionComment3");
					updMethod.setListAdditional(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAdditional(new Vector());
			    }
				try {
					kv.setEntryType("MethodRevisionUrl3");
					updMethod.setListAdditionalUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListAdditionalUrl(new Vector());
			    }
				//---------------------------------------------------
				//  Policy
				try {
					kv.setEntryType("MethodRevisionComment4");
					updMethod.setListPolicy(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListPolicy(new Vector());
				}	  	
				try {
					kv.setEntryType("MethodRevisionUrl4");
					updMethod.setListPolicyUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListPolicyUrl(new Vector());
				}	
				//-------------------------------------------------
				//  Actions
				try {
					kv.setEntryType("MethodRevisionComment10");
					updMethod.setListActions(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListActions(new Vector());
				}
				try {
					kv.setEntryType("MethodRevisionUrl10");
					updMethod.setListActionsUrl(ServiceKeyValue.buildKeyValueList(kv));
				}catch(Exception e){
					updMethod.setListActionsUrl(new Vector());
				}
			}
		//-------------------------------------------------
	    } // end of if there is a Method Number, Revision Date and Revision Time
	  	
		// ***********************************************
		// Add the Supersedes Date Information 
		try{

			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment(updMethod.getEnvironment().trim());
			crb.setIdLevel1(updMethod.getMethodNumber().trim());
			crb.setIdLevel2(updMethod.getRevisionDate().trim());
			
			updMethod.setSupersedesDate(ServiceQuality.findMethodSupersedes(crb));
		
		}catch(Exception e){
			// Use for Debug
			System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageUpdMethod(): " + e);
		}
		//*************************************************	
		
	  	request.setAttribute("requestType", requestType);
		request.setAttribute("updViewBean", updMethod); 	
		  	
		  	
	  } catch(Exception e){
			// Use for Debug
			System.out.println("Error Found in CtlQuality.pageUpdMethod(): " + e);
	  }
   }

	/*
	 * Retrieve and Build everything needed for the SPECIFICATION Update Page
	 *   // Update the Records and then Retrieve and Build Everything
	 *    on an update will return the NEW version
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 *      
	 *  10/23/13 - TWalton - adjust to get the SupersedesDate at the end right before 
	 *  		it goes to the screen -- remove other code
	 */
	protected void pageUpdSpecification(HttpServletRequest request,
						   			    HttpServletResponse response) {
	  String requestType = (String) request.getAttribute("requestType");
	  try
	  {
		UpdSpecification updSpec = (UpdSpecification) request.getAttribute("updViewBean");
		DateTime dt = UtilityDateTime.getSystemDate();
		// ********************************************************************
		// Update the Comment/URL Sections FIRST, before anything else is done
//		// URL's and Comments -- Call the KeyValues Servlet
//		//  ONLY on and UPDATE-ADD-DELETE (Saved)
		// 9/26/13 TWalton Changed the screen to update the comment sections direct from the screen
		
		//  Still need this for the URL's
		
		
		if(!updSpec.submit.trim().equals("") &&
		   updSpec.getOriginalStatus().trim().equals("PD"))
		{
		  try {
			CtlKeyValues thisOne = new CtlKeyValues();
			thisOne.performTask(request, response);
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		} 		
		//  Retrieve the Values at the bottom of this method
		//*****************************************************************
		//*****************************************************************
		//*********** ADD a new Specification
		if (requestType.equals("addSpec"))
		{
			// Add a new BLANK Specification
			try
			{
				if (updSpec.getDisplayMessage().trim().equals(""))
				{
						try{
				    	  BeanQuality bqNumber = ServiceQuality.nextIDNumberSpecification(updSpec.getEnvironment());
				    	  updSpec.setSpecNumber(bqNumber.getSpecification().getSpecificationNumber());
						}catch(Exception e)
				       {}
						updSpec.setRevisionDate(dt.getDateFormatyyyyMMdd());
						updSpec.setRevisionTime(dt.getTimeFormathhmmss());
						updSpec.setUpdateDate(dt.getDateFormatyyyyMMdd());
						updSpec.setUpdateTime(dt.getTimeFormathhmmss());
						updSpec.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
						updSpec.setCreationDate(dt.getDateFormatyyyyMMdd());
						updSpec.setCreationTime(dt.getTimeFormathhmmss());
						updSpec.setCreationUser(SessionVariables.getSessionttiProfile(request, response));
						updSpec.setStatus("PD");
						updSpec.setOriginalStatus("PD");
						Vector sendValues = new Vector();
						sendValues.addElement(updSpec);
						BeanQuality bq = ServiceQuality.updateSpecification(sendValues);
				}
			}
			catch(Exception e)
			{
				System.out.println("problem getting new number for Specification: " + e);
			}
			updSpec.buildDropDownVectors();
		
		} // end of the addSpec
		//********************************************************************
		//  COPY, CREATE REVISION -- will create a Brand New Specification Revision Combination 
		//      get the Next ID Number -- for the COPY
		//      add the basic Specification into the file
		//*********************************************************************
		if (requestType.equals("copyNewSpec") ||
			requestType.equals("reviseSpec"))
		{
			//Test the Revise Specification section...
			//Revise Specification will create a new PENDING Specification
			// Find Information from Original Specification
			try{
				DtlSpecification spec = new DtlSpecification();
				spec.populate(request);
				Vector sendValues = new Vector();
				sendValues.addElement(spec);
				// Just go get the Information
			    BeanQuality bq = ServiceQuality.findSpecification(sendValues);
			    updSpec.loadFromBeanQuality(bq);
			    updSpec.setEnvironment(spec.getEnvironment());
			    updSpec.setCompany(spec.getCompany());
			    updSpec.setDivision(spec.getDivision());
			    updSpec.setReferenceSpecNumber(updSpec.getSpecNumber());
			    updSpec.setReferenceSpecRevisionDate(updSpec.getRevisionDate());
			    updSpec.setReferenceSpecRevisionTime(updSpec.getRevisionTime());
			    updSpec.setRevisionDate(dt.getDateFormatyyyyMMdd());
			    updSpec.setRevisionTime(dt.getTimeFormathhmmss());
			    updSpec.setUpdateDate(dt.getDateFormatyyyyMMdd());
			    updSpec.setUpdateTime(dt.getTimeFormathhmmss());
			    updSpec.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			    updSpec.setStatus("PD");
			    updSpec.setOriginalStatus("PD");
				    //--- if copyNewSpec then will need to get a NEW Specification Number
			    if (requestType.equals("copyNewSpec"))
			    {
			       try{
			    	  BeanQuality bqNumber = ServiceQuality.nextIDNumberSpecification(spec.getEnvironment());
			    	  updSpec.setSpecNumber(bqNumber.getSpecification().getSpecificationNumber());
			    	  updSpec.setCreationDate(dt.getDateFormatyyyyMMdd());
			    	  updSpec.setCreationTime(dt.getTimeFormathhmmss());
			    	  updSpec.setCreationUser(SessionVariables.getSessionttiProfile(request, response));
			       }catch(Exception e){}
			    }
			    //Replace Details with NEW specification Number 
			    
			    //  This is where a Detail Section would be added if needed
			    			    
			    sendValues = new Vector();
			    sendValues.addElement(updSpec);
			    sendValues = UpdVariety.replaceRecordID("SPEC", sendValues);
			    sendValues = UpdTestParameters.replaceRecordID("SPEC", sendValues);
			    bq = ServiceQuality.updateSpecification(sendValues);
			    updSpec.loadFromBeanQuality(bq);	
			    
			 // on a copy or a revision the Comments also need to be copied
			    //  ALL of the Comment Sections displayed on the Screen
			    try {
					if (!spec.getSpecNumber().equals("") &&
						!spec.getRevisionDate().equals("") &&
						!spec.getRevisionTime().equals(""))
					{
						KeyValue kv = new KeyValue();
						kv.setEnvironment(spec.getEnvironment().trim());
						kv.setKey1(spec.getSpecNumber().trim());
						kv.setKey2(spec.getRevisionDate());
						kv.setKey3(spec.getRevisionTime());
						for (int x = 0; x < 25; x++)
						{
							if (x == 0)
							      kv.setEntryType("SpecRevisionComment");
							   else
								  kv.setEntryType("SpecRevisionComment" + x);
						   
							try{
								Vector listDetails = ServiceKeyValue.buildKeyValueList(kv);
								if (!listDetails.isEmpty())
								{
									for (int comment = 0; comment < listDetails.size(); comment++)
									{
										KeyValue kvAdd = (KeyValue) listDetails.elementAt(comment);
										kvAdd.setEnvironment(spec.getEnvironment().trim());
										kvAdd.setKey1(updSpec.getSpecNumber());
										kvAdd.setKey2(updSpec.getRevisionDate());
										kvAdd.setKey3(updSpec.getRevisionTime());
									    ServiceKeyValue.addKeyValue(kvAdd);
									}
								} // only do something IF there are records
							}catch(Exception e)
							{	
							}
						}
						for (int x = 0; x < 5; x++)
						{
							if (x == 0)
							      kv.setEntryType("SpecRevisionUrl");
							   else
								  kv.setEntryType("SpecRevisionUrl" + x);
						   
							try{
								Vector listDetails = ServiceKeyValue.buildKeyValueList(kv);
								if (!listDetails.isEmpty())
								{
									for (int comment = 0; comment < listDetails.size(); comment++)
									{
										KeyValue kvAdd = (KeyValue) listDetails.elementAt(comment);
										kvAdd.setEnvironment(spec.getEnvironment().trim());
										kvAdd.setKey1(updSpec.getSpecNumber());
										kvAdd.setKey2(updSpec.getRevisionDate());
										kvAdd.setKey3(updSpec.getRevisionTime());
									    ServiceKeyValue.addKeyValue(kvAdd);
									}
								} // only do something IF there are records
							}catch(Exception e)
							{	
							}
						}
					}
			    }catch(Exception e){
			    	// System.out.prinltn("Cannot find FormulaNumber");
			    }
			}catch(Exception e){
				System.out.println("CtlQuality.pageUpd, found error when copying a new Specification or Creating a Revision:" + e);
				updSpec.setDisplayMessage("CtlQuality.pageUpd, found error when adding Specification:" + e);
			}
			updSpec.buildDropDownVectors(); 
		} // end of copy or revise spec
		//*****************************************************************
		//*********** UPDATE a Specification Revision (Date and Time
		//*****************************************************************
	  	if (requestType.equals("updSpec"))
	  	{
			if (updSpec.getDisplayMessage().trim().equals(""))
			{	
				BeanQuality bq = new BeanQuality();
				if(!updSpec.submit.trim().equals("")){
					// Actually update the records
					try{
						Vector sendValues = new Vector();
						sendValues.addElement(updSpec);
						updSpec.setUpdateDate(dt.getDateFormatyyyyMMdd());
						updSpec.setUpdateTime(dt.getTimeFormathhmmss());
						// Update the Spec -- any changes can be made in Pending Status
						if (updSpec.getOriginalStatus().equals("PD"))
							bq = ServiceQuality.updateSpecification(sendValues);
						
						if (!updSpec.getStatus().equals(updSpec.getOriginalStatus()))
						{
							if (updSpec.getStatus().trim().equals("AC") &&
								updSpec.getOriginalStatus().trim().equals("PD"))
							{
								// Start by Resetting the Date and Time for all of the Comment and Attachment Files
								KeyValue origKeyValue = new KeyValue();
								origKeyValue.setEnvironment(updSpec.getEnvironment());
								origKeyValue.setEntryType("Spec");
								origKeyValue.setKey1(updSpec.getSpecNumber().trim());
								origKeyValue.setKey2(updSpec.getRevisionDate().trim());
								origKeyValue.setKey3(updSpec.getRevisionTime().trim());
								origKeyValue.setKey4("");
								origKeyValue.setKey5("");
								
								KeyValue newKeyValue = new KeyValue();
								newKeyValue.setEnvironment(updSpec.getEnvironment());
								newKeyValue.setKey1(updSpec.getSpecNumber().trim());
								newKeyValue.setKey2(dt.getDateFormatyyyyMMdd());
								String setTime = dt.getTimeFormathhmmss();
								if (setTime.substring(0,1).trim().equals("0"))
									setTime = setTime.substring(1, setTime.length());
								newKeyValue.setKey3(setTime);
								newKeyValue.setKey4("");
								newKeyValue.setKey5("");
								newKeyValue.setLastUpdateDate(dt.getDateFormatyyyyMMdd());
								newKeyValue.setLastUpdateTime(dt.getTimeFormathhmmss());
								newKeyValue.setLastUpdateUser(updSpec.getUpdateUser().trim());
								
								ServiceKeyValue.updateReplaceKeys(origKeyValue, newKeyValue);
								// Reset the Revision date and time -- when a Spec becomes ACTIVE
								// that is when the Revision Date and Time needs to be set
								// this will take the Update Date Time and User and put it 
								// into the Revision Date and Time -- ONLY when a Spec becomes
								// ACTIVE
							} // if change the status to ACTIVE from Pending
							sendValues.addElement(updSpec);
							bq = ServiceQuality.updateSpecificationStatus(sendValues);
						} // end if the Status is different than the original status
					}
					catch(Exception e)
					{
						System.out.println("CtlQuality.pageUpd, found error when updating the Specification:" + e);
						updSpec.setDisplayMessage("CtlQuality.pageUpd, found error when updating the Specification:" + e);
					}
				} else {// end if the save button is blank... if yes then skip to here
					// if there are no messages must go out and get the actual 
					// DETAIL information and load the information into the Upd Screens
					try{
						DtlSpecification spec = new DtlSpecification();
						spec.populate(request);
						Vector sendValues = new Vector();
						sendValues.addElement(spec);
						// Just go get the information
						bq = ServiceQuality.findSpecification(sendValues);
					}catch(Exception e){
						System.out.println("CtlQuality.pageUpdSpecification, found error when retrieving Spec:" + e);
						updSpec.setDisplayMessage("CtlQuality.pageUpdSpecification, found error when retrieving Spec:" + e);
					}
				}
				updSpec.loadFromBeanQuality(bq);
			} // end of the Display Message -- must be blank
	  	} // END of is updSpec (Specification)
	  	//*******************************************************************************

	    // Retrieve the Comments from the file -- Also retrieve the Documents and Pictures
		if (!updSpec.getSpecNumber().equals("") &&
			!updSpec.getRevisionDate().equals("") &&
			!updSpec.getRevisionTime().equals(""))
		{
		    // retrieve Specification Comments
			KeyValue kv = new KeyValue();
			kv.setEnvironment(updSpec.getEnvironment().trim());
			kv.setKey1(updSpec.getSpecNumber().trim());
			kv.setKey2(updSpec.getRevisionDate());
			kv.setKey3(updSpec.getRevisionTime());
			if (!updSpec.getStatus().trim().equals("PD"))
			{
				// Revision Comments
				try {
					kv.setEntryType("SpecRevisionComment");
					updSpec.setListComments(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
				//skip it. allowed to be empty
					updSpec.setListComments(new Vector());
				}
				// Comments about Analytical Testing
				try {
					kv.setEntryType("SpecRevisionComment1");
					updSpec.setListAnalyticalTestComments(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
				//skip it. allowed to be empty
					updSpec.setListAnalyticalTestComments(new Vector());
				}
				//	Comments about Process Parameters
				try {
					kv.setEntryType("SpecRevisionComment2");
					updSpec.setListProcessParameterComments(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListProcessParameterComments(new Vector());
				}
				// Comments about Micro Testing
				try {
					kv.setEntryType("SpecRevisionComment3");
					updSpec.setListMicroTestComments(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListMicroTestComments(new Vector());
				}
				//	Comments about Additives and Preservatives
				try {
					kv.setEntryType("SpecRevisionComment4");
					updSpec.setListAdditivesAndPreservativeComments(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListAdditivesAndPreservativeComments(new Vector());
				}
				// Comments about Container Print Instructions 
				try {
					kv.setEntryType("SpecRevisionComment5");
					updSpec.setListContainerPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListContainerPrintByLine(new Vector());
				}
				// Comments about Container Additional Instructions
				try {
					kv.setEntryType("SpecRevisionComment6");
					updSpec.setListContainerPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListContainerPrintAdditional(new Vector());
				}
				// Comments about Case Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment7");
					updSpec.setListCasePrintByLine(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListCasePrintByLine(new Vector());
				}
				// Comments about Additional Case Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment8");
					updSpec.setListCasePrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListCasePrintAdditional(new Vector());
				}
				// Comments about Pallet Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment9");
					updSpec.setListPalletPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListPalletPrintByLine(new Vector());
				}
				//	Comments about Additional Pallet Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment10");
					updSpec.setListPalletPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListPalletPrintAdditional(new Vector());
				}
				// Comments about Label Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment11");
					updSpec.setListLabelPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListLabelPrintByLine(new Vector());
				}
				// Comments about Additional Label Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment12");
					updSpec.setListLabelPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListLabelPrintAdditional(new Vector());
				}
				// Comments about Shelf Life Requirements
				try {
					kv.setEntryType("SpecRevisionComment13");
					updSpec.setListShelfLifeRequirements(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListShelfLifeRequirements(new Vector());
				}
				// Comments about Storage Requirements
				try {
					kv.setEntryType("SpecRevisionComment14");
					updSpec.setListStorageRequirements(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListStorageRequirements(new Vector());
				}
				// Comments about Finished Pallet Additional Comments
				try {
					kv.setEntryType("SpecRevisionComment15");
					updSpec.setListFinishedPalletAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListFinishedPalletAdditional(new Vector());
				}
				// Comments about Fruit Varieties Additional 
				try {
					kv.setEntryType("SpecRevisionComment16");
					updSpec.setListFruitVarietiesAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListFruitVarietiesAdditional(new Vector());
				}
				// Comments about Shipping Requirements
				try {
					kv.setEntryType("SpecRevisionComment17");
					updSpec.setListShippingRequirements(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListShippingRequirements(new Vector());
				}
				// Comments about COA Requirements 
				try {
					kv.setEntryType("SpecRevisionComment18");
					updSpec.setListCOARequirements(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListCOARequirements(new Vector());
				}
				// Comments about Carton Print Instructions
				try {
					kv.setEntryType("SpecRevisionComment19");
					updSpec.setListCartonPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListCartonPrintByLine(new Vector());
				}
				// Comments about Additional Carton Instructions
				try {
					kv.setEntryType("SpecRevisionComment20");
					updSpec.setListCartonPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListCartonPrintAdditional(new Vector());
				}
				// Comments about Product Description
				try {
					kv.setEntryType("SpecRevisionComment21");
					updSpec.setListProductDescription(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListProductDescription(new Vector());
				}
				// Comments about Ingredient Statement
				try {
					kv.setEntryType("SpecRevisionComment22");
					updSpec.setListIngredientStatement(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListIngredientStatement(new Vector());
				}
				// Comments about Intended Use
				try {
					kv.setEntryType("SpecRevisionComment23");
					updSpec.setListIntendedUse(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListIntendedUse(new Vector());
				}
				// Comments about Foreign Matter
				try {
					kv.setEntryType("SpecRevisionComment24");
					updSpec.setListForeignMatter(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
					//skip it. allowed to be empty
					updSpec.setListForeignMatter(new Vector());
				}
				// Comments about Additional Coding Requirements
				try {
					kv.setEntryType("SpecRevisionComment25");
					updSpec.setListCodingRequirementsAdditional(ServiceKeyValue.buildKeyValueList(kv));
				} catch(Exception e){
				//skip it. allowed to be empty
					updSpec.setListCodingRequirementsAdditional(new Vector());
				}	
			}
					
			// Retrieve Images and Documents
			try {
				kv.setEntryType("SpecRevisionUrl");
				updSpec.setListSpecUrls(ServiceKeyValue.buildKeyValueList(kv));
			} catch(Exception e)
			{
		//skip it. allowed to be empty
				updSpec.setListSpecUrls(new Vector());
			}
			// Nutrition Panel
			try {
				kv.setEntryType("SpecRevisionUrl1");
				Vector listNutritionPanel = ServiceKeyValue.buildKeyValueList(kv);
				if (!listNutritionPanel.isEmpty())
				{
					KeyValue newKV = (KeyValue) listNutritionPanel.elementAt(0);
					if (updSpec.getNutritionPanelURLRemove().trim().equals(""))
					{
					   updSpec.setNutritionPanelURL(newKV.getValue());
					}else{
					  try{
					       KeyValue deleteKV = new KeyValue();
					       deleteKV.setDeleteUser(updSpec.getUpdateUser());
					       deleteKV.setUniqueKey(newKV.getUniqueKey());
					       ServiceKeyValue.deleteKeyValue(deleteKV);
					       updSpec.setNutritionPanelURL("");
						}catch(Exception e){}
					}
				}
			} catch(Exception e)
			{}
			// Pallet Pattern
			try {
				kv.setEntryType("SpecRevisionUrl2");
				Vector listPalletPattern = ServiceKeyValue.buildKeyValueList(kv);
				if (!listPalletPattern.isEmpty())
				{
					KeyValue newKV = (KeyValue) listPalletPattern.elementAt(0);
					if (updSpec.getPalletPatternURLRemove().trim().equals(""))
					{
					   updSpec.setPalletPatternURL(newKV.getValue());
				    }else{
					  try{
					       KeyValue deleteKV = new KeyValue();
					       deleteKV.setDeleteUser(updSpec.getUpdateUser());
					       deleteKV.setUniqueKey(newKV.getUniqueKey());
					       ServiceKeyValue.deleteKeyValue(deleteKV);
					       updSpec.setPalletPatternURL("");
						}catch(Exception e){}
					}
				}
			} catch(Exception e)
			{}	
			// Example Label
			try {
				kv.setEntryType("SpecRevisionUrl3");
				Vector listExampleLabel = ServiceKeyValue.buildKeyValueList(kv);
				if (!listExampleLabel.isEmpty())
				{
					KeyValue newKV = (KeyValue) listExampleLabel.elementAt(0);
					if (updSpec.getExampleLabelURLRemove().trim().equals(""))
					{
					   updSpec.setExampleLabelURL(newKV.getValue());
				    }else{
					  try{
					       KeyValue deleteKV = new KeyValue();
					       deleteKV.setDeleteUser(updSpec.getUpdateUser());
					       deleteKV.setUniqueKey(newKV.getUniqueKey());
					       ServiceKeyValue.deleteKeyValue(deleteKV);
					       updSpec.setExampleLabelURL("");
						}catch(Exception e){}
					}
				}
			} catch(Exception e)
			{}	
		}// end of the have specification/revision date and revision time
		
		// ***********************************************
		// Add the Supersedes Date Information 
		try{

			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment(updSpec.getEnvironment().trim());
			crb.setIdLevel1(updSpec.getSpecNumber().trim());
			crb.setIdLevel2(updSpec.getRevisionDate().trim());
			
			updSpec.setSupersedesDate(ServiceQuality.findSpecificationSupersedes(crb));
		//*************************************************	
		}catch(Exception e){
			// Use for Debug
			System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageUpdSpecification(): " + e);
		}
		
		request.setAttribute("updViewBean", updSpec);
		  	
	  } catch(Exception e){
				// Use for Debug
			System.out.println("Error Found in CtlQuality.pageUpdSpecification(): " + e);
	  }
   }

	/*
	 * Retrieve and Build everything needed for the Detail Page
	 */
	protected void pageDtlSpecification(HttpServletRequest request,
						                HttpServletResponse response) {
			
		  String requestType = (String) request.getAttribute("requestType");
		  try
		  {
		  	if (requestType.equals("dtlSpec"))
		  	{
				DtlSpecification dtlSpec = (DtlSpecification) request.getAttribute("dtlViewBean");	
				try{
						Vector sendValues = new Vector();
						sendValues.addElement(dtlSpec);
						// 	Just go get the Information
						dtlSpec.setDtlBean(ServiceQuality.findSpecification(sendValues));
						dtlSpec.processCodeDateInformation();
						
						if (dtlSpec.getRevisionDate().trim().equals(""))
						{
							dtlSpec.setRevisionDate(dtlSpec.getDtlBean().getSpecification().getRevisionDate());
							dtlSpec.setRevisionTime(dtlSpec.getDtlBean().getSpecification().getRevisionTime());
						}
				
				}catch(Exception e){
					System.out.println("CtlQuality.pageDtl, found error when retrieving Specification:" + e);
					dtlSpec.setDisplayMessage("CtlQuality.pageDtl, found error when retrieving Specification:" + e);
				}
				
				// Obtain Specification Comments and Images for this Specific Specification
				if (!dtlSpec.getSpecNumber().equals("") &&
					!dtlSpec.getRevisionDate().equals("") &&
					!dtlSpec.getRevisionTime().equals(""))
				{
						KeyValue kv = new KeyValue();
						kv.setEnvironment(dtlSpec.getEnvironment().trim());
						kv.setKey1(dtlSpec.getSpecNumber().trim());
						kv.setKey2(dtlSpec.getRevisionDate());
						kv.setKey3(dtlSpec.getRevisionTime());
					try {
						kv.setEntryType("SpecRevisionComment");
						dtlSpec.setListComments(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListComments(new Vector());
					}	  		
					try {
						kv.setEntryType("SpecRevisionUrl");
						dtlSpec.setListSpecUrls(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListSpecUrls(new Vector());
					}	  
					//-------------------------------------------------
					//  analytical Tests
					try {
						kv.setEntryType("SpecRevisionComment1");
						dtlSpec.setListAnalyticalTestComments(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListAnalyticalTestComments(new Vector());
					}
					//  Nutrition Panel
					try {
						kv.setEntryType("SpecRevisionUrl1");
						dtlSpec.setListNutritionPanel(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListNutritionPanel(new Vector());
					}	  
					//	-------------------------------------------------
					//  Process Parameters
					try {
						kv.setEntryType("SpecRevisionComment2");
						dtlSpec.setListProcessParameterComments(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListProcessParameterComments(new Vector());
				    }
					//  Pallet Pattern
					try {
						kv.setEntryType("SpecRevisionUrl2");
						dtlSpec.setListPalletPattern(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListPalletPattern(new Vector());
					}	  
					// ---------------------------------------------------	
					//  Micro Testing
					try {
						kv.setEntryType("SpecRevisionComment3");
						dtlSpec.setListMicroTestComments(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListMicroTestComments(new Vector());
				    }	
					//  Example Label
					try {
						kv.setEntryType("SpecRevisionUrl3");
						dtlSpec.setListExampleLabel(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListExampleLabel(new Vector());
					}	  
					//----------------------------------------------------
					// Additives and Preservatives
					try {
						kv.setEntryType("SpecRevisionComment4");
						dtlSpec.setListAdditivesAndPreservativeComments(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListAdditivesAndPreservativeComments(new Vector());
					}	  		
					//-------------------------------------------------
					//  Container Print Instructions - By Line 
					try {
						kv.setEntryType("SpecRevisionComment5");
						dtlSpec.setListContainerPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListContainerPrintByLine(new Vector());
					}
					//	-------------------------------------------------
					//  Container Print Instructions - Additional
					try {
						kv.setEntryType("SpecRevisionComment6");
						dtlSpec.setListContainerPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListContainerPrintAdditional(new Vector());
					}
					// ---------------------------------------------------	
					//  Case Print Instructions - By Line
					try {
						kv.setEntryType("SpecRevisionComment7");
						dtlSpec.setListCasePrintByLine(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListCasePrintByLine(new Vector());
					}	
					//----------------------------------------------------
					//  Case Print Instructions - Additional
					try {
						kv.setEntryType("SpecRevisionComment8");
						dtlSpec.setListCasePrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListCasePrintAdditional(new Vector());
					}	  		
					//-------------------------------------------------
					//  Pallet Print Instructions - By Line
					try {
						kv.setEntryType("SpecRevisionComment9");
						dtlSpec.setListPalletPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListPalletPrintByLine(new Vector());
					}
					//	-------------------------------------------------
					//  Pallet Print Instructions - Additional
					try {
						kv.setEntryType("SpecRevisionComment10");
						dtlSpec.setListPalletPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListPalletPrintAdditional(new Vector());
				    }
					// ---------------------------------------------------	
					//  Label Print Instructions - By Line
					try {
						kv.setEntryType("SpecRevisionComment11");
						dtlSpec.setListLabelPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListLabelPrintByLine(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Label Print Instructions - Additional
					try {
						kv.setEntryType("SpecRevisionComment12");
						dtlSpec.setListLabelPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListLabelPrintAdditional(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Shelf Life Requirements
					try {
						kv.setEntryType("SpecRevisionComment13");
						dtlSpec.setListShelfLifeRequirements(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListShelfLifeRequirements(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Storage Requirements
					try {
						kv.setEntryType("SpecRevisionComment14");
						dtlSpec.setListStorageRequirements(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListStorageRequirements(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Finished Pallet Additional Comments
					try {
						kv.setEntryType("SpecRevisionComment15");
						dtlSpec.setListFinishedPalletAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListFinishedPalletAdditional(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Fruit Varieties Additional Comments
					try {
						kv.setEntryType("SpecRevisionComment16");
						dtlSpec.setListFruitVarietiesAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListFruitVarietiesAdditional(new Vector());
					}	 
					// ---------------------------------------------------	
					//  Shipping Requirements
					try {
						kv.setEntryType("SpecRevisionComment17");
						dtlSpec.setListShippingRequirements(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListShippingRequirements(new Vector());
					}	 
					// ---------------------------------------------------	
					//  COA Requirements
					try {
						kv.setEntryType("SpecRevisionComment18");
						dtlSpec.setListCOARequirements(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListCOARequirements(new Vector());
					}
					//-------------------------------------------------
					//  Carton Print Instructions - By Line 
					try {
						kv.setEntryType("SpecRevisionComment19");
						dtlSpec.setListCartonPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListCartonPrintByLine(new Vector());
					}
					//	-------------------------------------------------
					//  Carton Print Instructions - Additional
					try {
						kv.setEntryType("SpecRevisionComment20");
						dtlSpec.setListCartonPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListCartonPrintAdditional(new Vector());
					}
					//  -------------------------------------------------
					//  Product Description
					try {
						kv.setEntryType("SpecRevisionComment21");
						dtlSpec.setListProductDescription(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListProductDescription(new Vector());
					}
//					-------------------------------------------------
					//  Ingredient Statement
					try {
						kv.setEntryType("SpecRevisionComment22");
						dtlSpec.setListIngredientStatement(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListIngredientStatement(new Vector());
					}
//					-------------------------------------------------
					//  Intended Use
					try {
						kv.setEntryType("SpecRevisionComment23");
						dtlSpec.setListIntendedUse(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListIntendedUse(new Vector());
					}
//					-------------------------------------------------
					//  Foreign Matter
					try {
						kv.setEntryType("SpecRevisionComment24");
						dtlSpec.setListForeignMatter(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListForeignMatter(new Vector());
					}
//					-------------------------------------------------
					//  Coding Requirements - Additional
					try {
						kv.setEntryType("SpecRevisionComment25");
						dtlSpec.setListCodingRequirementsAdditional(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						dtlSpec.setListCodingRequirementsAdditional(new Vector());
					}
					
					// IF Item if filled in -- go get the pallet gtin to be used to get the family tree
					//   will display the family tree at the bottom, but will also run through the tree to get the CASE gtin information
					try{
						if (!dtlSpec.getDtlBean().getItemInformation().getItemClass().getNewItemPalletGTIN().trim().equals(""))
						{
							Vector listTree = ServiceGTIN.buildViews(dtlSpec.getDtlBean().getItemInformation().getItemClass().getNewItemPalletGTIN().trim());
							dtlSpec.processCaseGTIN(listTree);
						}
					}catch(Exception e){
						
					}
				}
				// ***********************************************
				// Add the Supersedes Date Information 
				try{

					CommonRequestBean crb = new CommonRequestBean();
					crb.setCompanyNumber("100");
					crb.setDivisionNumber("100");
					crb.setEnvironment(dtlSpec.getEnvironment().trim());
					crb.setIdLevel1(dtlSpec.getSpecNumber().trim());
					crb.setIdLevel2(dtlSpec.getRevisionDate().trim());
					
					dtlSpec.setSupersedesDate(ServiceQuality.findSpecificationSupersedes(crb));
				//*************************************************
					
				}catch(Exception e){
					// Use for Debug
					System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageDtlSpecification(): " + e);
				}
				request.setAttribute("displayView", "detail");
				request.setAttribute("dtlViewBean", dtlSpec);
		  	} // end of dtlSpec
		  } catch(Exception e){
					// Use for Debug
				System.out.println("Error Found in CtlQuality.pageDtlSpecification(): " + e);
		  }
	}

	/*
	 * Retrieve and Build additional functionality not saved to a file
	 * 	Specifications Product Data Sheet -- 12/9/13 TWalton 
	 */
	protected void pageBld(HttpServletRequest request,
						   HttpServletResponse response) {
		
		  String requestType = (String) request.getAttribute("requestType");
		  try
		  {
		  	if (requestType.equals("buildProductDataSheet"))
		  		pageBldSpecProductDataSheet(request, response);
		  	
		  	
		  	
		  	
		  } catch(Exception e){
				System.out.println("Error Found in CtlQuality.pageBld(): " + e);
		  }
	}

	/*
	 * Retrieve and Build everything needed for the
	 * 	  Specification Product Data Sheet
	 * 	  Only retrieve needed information / no update of information
	 * 			After selection, of what is to be displayed can limit even more
	 * 			Specific to the comment sections
	 */
	protected void pageBldSpecProductDataSheet(HttpServletRequest request,
						                   HttpServletResponse response) {
			
		String requestType = (String) request.getAttribute("requestType");
		try
		{
			BuildProductDataSheet bldPDS = (BuildProductDataSheet) request.getAttribute("bldViewBean");
			System.out.println("pageBldSpecProductDataSheet - go get the BeanQuality data for Acrive Spec");
			
			
			DtlSpecification dtlSpec = new DtlSpecification();
			dtlSpec.populate(request);

			Vector sendValues = new Vector();
			sendValues.addElement(dtlSpec);
			
			bldPDS.setDtlBean(ServiceQuality.findSpecification(sendValues));
			
			QaSpecificationPackaging specData = bldPDS.getDtlBean().getSpecPackaging();
			
			
			
			//---------------------------------------------------------------
			// Retrieve ALL the comments -- 
			//   Once the selection is done, only go get the comments requested.
			//---------------------------------------------------------------
			KeyValue kv = new KeyValue();
			kv.setEnvironment(bldPDS.getEnvironment());
			kv.setKey1(specData.getSpecificationNumber().trim());
			kv.setKey2(specData.getRevisionDate());
			kv.setKey3(specData.getRevisionTime());


			// *** Analytical Tests ***
			try {
				kv.setEntryType("SpecRevisionComment1");
				bldPDS.setListAnalyticalTestComments(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListAnalyticalTestComments(new Vector());
			}

			System.out.println("Save or delete code for retrive Nutrition Panel");
		 //// *** Nutrition Panel *** -- don't know if needed
		//try {
			//kv.setEntryType("SpecRevisionUrl1");
				//dtlSpec.setListNutritionPanel(ServiceKeyValue.buildKeyValueList(kv));
//				}catch(Exception e){
//				dtlSpec.setListNutritionPanel(new Vector());
	//		}	  

			// *** Process Parameters ***
			try {
				kv.setEntryType("SpecRevisionComment2");
				bldPDS.setListProcessParameterComments(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListProcessParameterComments(new Vector());
			}
		
			System.out.println("Save or delete code for retrive Pallet Pattern");
			//  Pallet Pattern -- don't know if needed
//				try {
//					kv.setEntryType("SpecRevisionUrl2");
//					dtlSpec.setListPalletPattern(ServiceKeyValue.buildKeyValueList(kv));
//				}catch(Exception e){
//					dtlSpec.setListPalletPattern(new Vector());
//				}
		
		

			// *** Micro Testing ***
			try {
				kv.setEntryType("SpecRevisionComment3");
				bldPDS.setListMicroTestComments(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListMicroTestComments(new Vector());
			}

		
			System.out.println("Save or delete code for retrive Example Label");
			//  Example Label
//				try {
//					kv.setEntryType("SpecRevisionUrl3");
//					dtlSpec.setListExampleLabel(ServiceKeyValue.buildKeyValueList(kv));
//				}catch(Exception e){
//					dtlSpec.setListExampleLabel(new Vector());
//				}	
		
		
		

			// *** Additives and Preservatives ***
			try {
				kv.setEntryType("SpecRevisionComment4");
				bldPDS.setListAdditivesAndPreservativeComments(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListAdditivesAndPreservativeComments(new Vector());
			}
		
		
		
			// *** Container Print By Line ***
			try {
				kv.setEntryType("SpecRevisionComment5");
				bldPDS.setListContainerPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListContainerPrintByLine(new Vector());
			}
		
		
			// *** Container Print Additional ***
			try {
				kv.setEntryType("SpecRevisionComment6");
				bldPDS.setListContainerPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListContainerPrintAdditional(new Vector());
			}

		
		
			// *** Case Print By Line ***
			try {
				kv.setEntryType("SpecRevisionComment7");
				bldPDS.setListCasePrintByLine(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListCasePrintByLine(new Vector());
			}
		
			// *** Case Print Additional ***
			try {
				kv.setEntryType("SpecRevisionComment8");
				bldPDS.setListCasePrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListCasePrintAdditional(new Vector());
			}
		
		
		
		
			// *** Case Print By Line ***
			try {
				kv.setEntryType("SpecRevisionComment9");
				bldPDS.setListPalletPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListPalletPrintByLine(new Vector());
			}
		
			// *** Case Print Additional ***
			try {
				kv.setEntryType("SpecRevisionComment10");
				bldPDS.setListPalletPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListPalletPrintAdditional(new Vector());
			}
		
		
		
		
			// *** Case Print By Line ***
			try {
				kv.setEntryType("SpecRevisionComment11");
				bldPDS.setListLabelPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListLabelPrintByLine(new Vector());
			}
		
			// *** Case Print Additional ***
			try {
				kv.setEntryType("SpecRevisionComment12");
				bldPDS.setListLabelPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListLabelPrintAdditional(new Vector());
			}
		
		
		
		
		
		
			// *** Shelf Life Requirements ***
			try {
				kv.setEntryType("SpecRevisionComment13");
				bldPDS.setListShelfLifeRequirements(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListShelfLifeRequirements(new Vector());
			}	 
		
		
			// *** Storage Requirements ***
			try {
				kv.setEntryType("SpecRevisionComment14");
				bldPDS.setListStorageRequirements(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListStorageRequirements(new Vector());
			}	 
		
		
			// *** Finished Pallet Additional Comments ***
			try {
				kv.setEntryType("SpecRevisionComment15");
				bldPDS.setListFinishedPalletAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListFinishedPalletAdditional(new Vector());
			}	 
		
		
		
			// *** Fruit Varieties Additional Comments ***
			try {
				kv.setEntryType("SpecRevisionComment16");
				bldPDS.setListFruitVarietiesAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListFruitVarietiesAdditional(new Vector());
			}	
		
			// *** Shipping Requirements ***
			try {
				kv.setEntryType("SpecRevisionComment17");
				bldPDS.setListShippingRequirements(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListShippingRequirements(new Vector());
			}	
		
		
		
			// *** COA Requirements ***
			try {
				kv.setEntryType("SpecRevisionComment18");
				bldPDS.setListCOARequirements(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListCOARequirements(new Vector());
			}	
			
		
		
			// *** Carton Print Requirements ***
			try {
				kv.setEntryType("SpecRevisionComment19");
				bldPDS.setListCartonPrintByLine(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListCartonPrintByLine(new Vector());
			}	
		
		
		
		
			// *** Carton Print Additional ***
			try {
				kv.setEntryType("SpecRevisionComment20");
				bldPDS.setListCartonPrintAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListCartonPrintAdditional(new Vector());
			}	
		
		
		
			// *** Product Description ***
			try {
				kv.setEntryType("SpecRevisionComment21");
				bldPDS.setListProductDescription(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListProductDescription(new Vector());
			}
		
		
			// *** Ingredient Statement ***
			try {
				kv.setEntryType("SpecRevisionComment22");
				bldPDS.setListIngredientStatement(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListIngredientStatement(new Vector());
			}
		
		
			// *** Intended Use ***
			try {
				kv.setEntryType("SpecRevisionComment23");
				bldPDS.setListIntendedUse(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListIntendedUse(new Vector());
			}
		
		
			// *** Foreign Matter ***
			try {
				kv.setEntryType("SpecRevisionComment24");
				bldPDS.setListForeignMatter(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListForeignMatter(new Vector());
			}
		
		
			// *** Coding Requirements Additional ***
			try {
				kv.setEntryType("SpecRevisionComment25");
				bldPDS.setListCodingRequirementsAdditional(ServiceKeyValue.buildKeyValueList(kv));
			}catch(Exception e){
				bldPDS.setListCodingRequirementsAdditional(new Vector());
			}
				
			
			if (bldPDS.getSubmit().trim().equals("") ||
					bldPDS.getLinesStatements().length > 0) {
					// *** Statements ***
					try {
						kv.setEntryType("QualityStatements");
						kv.setKey1("");
						kv.setKey2("");
						kv.setKey3("");
						kv.setOrderBy("description");
						bldPDS.setListStatements(ServiceKeyValue.buildKeyValueList(kv));
					}catch(Exception e){
						bldPDS.setListStatements(new Vector());
					}
				}
			
			
			request.setAttribute("bldViewBean", bldPDS);
			
		}catch(Exception e){
			System.out.println("Error Found Retrieving Supersedes Date in CtlQuality.pageDtlSpecification(): " + e);
		}
	}
}
