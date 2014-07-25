<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.app.quality.GeneralQuality" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<% 
//---------------  dtlHeader.jsp  -------------------------------------------------//
//  Directly included in all of the QA Detail Pages -- standard heading for all
// 
//    Author :  Teri Walton  8/2/11
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------------------------------//
//********************************************************************

    String headerStatus			 = "";
//  Define all the variable fields needed on the screen
    String headerDocumentNo      = "";
    String headerTitle		     = "";
    String headerApprovedBy      = "";
    String headerScope		     = "";
    String headerScopeDesc		 = "";
    String headerOrigination     = "";
    String headerOriginationDesc = "";
    String headerIssuedDate	     = "";
    String headerIssuedTime		 = "";
    String headerSupersedesDate  = "";
    String headerSupersedesTime  = "";

//  Figure out which request Type it is, so you will know how to retrieve the view bean
    String headerRequestType = "";
    String environment = "PRD";
    try{
       headerRequestType = (String) request.getAttribute("requestType");
     //------------------------------------------------------------------------------//
     //  METHOD
     //------------------------------------------------------------------------------//
       if (headerRequestType.trim().equals("dtlMethod"))
       {
        // Fill in all the variable fields for this included screen  
          DtlMethod dtlMethod = (DtlMethod) request.getAttribute("dtlViewBean");
          QaMethod qm = dtlMethod.getDtlBean().getMethod();
          environment = dtlMethod.getEnvironment().trim(); 
		  headerDocumentNo      = qm.getMethodName().trim();
		  headerTitle		    = qm.getMethodDescription().trim();
		  headerApprovedBy      = GeneralQuality.findLongNameFromProfile(environment, qm.getApprovedByUser().trim());
		  headerScope		    = qm.getScopeCode().trim();
		  headerScopeDesc		= qm.getScopeDescription().trim();
		  headerOrigination     = qm.getOriginationUser().trim();      
		  headerOriginationDesc = GeneralQuality.findLongNameFromProfile(environment, qm.getOriginationUser().trim());
		  headerIssuedDate		= GeneralQuality.formatDateForScreen(qm.getRevisionDate().trim());
		  headerIssuedTime		= GeneralQuality.formatTimeForScreen(qm.getRevisionTime().trim());
		   // 11/25/13 - TWalton
		  //  Adjusted to use New Supersedes date information
		  if (!dtlMethod.getSupersedesDate().getDateFormatyyyyMMdd().trim().equals("0"))
		  {
	          headerSupersedesDate = dtlMethod.getSupersedesDate().getDateFormatMMddyyyySlash().trim();
		      headerSupersedesTime = dtlMethod.getSupersedesDate().getTimeFormathhmmColon().trim(); 
		  }
		//	  headerSupersedesDate  = GeneralQuality.formatDateForScreen(qm.getSupercededDate().trim()); 
		//    headerSupersedesTime  = GeneralQuality.formatTimeForScreen(qm.getSupercededTime().trim());      
		  if (!qm.getStatusCode().equals("AC")) 
		     headerStatus = qm.getStatusDescription();
       }
     //------------------------------------------------------------------------------//
     //  FORMULA
     //------------------------------------------------------------------------------//
       if (headerRequestType.trim().equals("dtlFormula"))
       {
        // Fill in all the variable fields for this included screen  
          DtlFormula dtlFormula = (DtlFormula) request.getAttribute("dtlViewBean");
          QaFormula  qf 		= dtlFormula.getDtlBean().getFormula();
          environment 			= dtlFormula.getEnvironment().trim(); 
		  headerDocumentNo      = qf.getFormulaName().trim();
		  headerTitle		    = qf.getFormulaDescription().trim();
		  headerApprovedBy      = GeneralQuality.findLongNameFromProfile(environment, qf.getApprovedByUser().trim());
		  headerScope		    = qf.getScopeCode().trim();
		  headerScopeDesc		= qf.getScopeDescription().trim();
		  headerOrigination     = qf.getOriginationUser().trim();      
		  headerOriginationDesc = GeneralQuality.findLongNameFromProfile(environment, qf.getOriginationUser().trim());
		  headerIssuedDate		= GeneralQuality.formatDateForScreen(qf.getRevisionDate().trim());
		  headerIssuedTime		= GeneralQuality.formatTimeForScreen(qf.getRevisionTime().trim());
          // 11/25/13 - TWalton
		  //  Adjusted to use New Supersedes date information
		  if (!dtlFormula.getSupersedesDate().getDateFormatyyyyMMdd().trim().equals("0"))
		  {
	          headerSupersedesDate = dtlFormula.getSupersedesDate().getDateFormatMMddyyyySlash().trim();
		      headerSupersedesTime = dtlFormula.getSupersedesDate().getTimeFormathhmmColon().trim(); 
		  }
		//  headerSupersedesDate  = GeneralQuality.formatDateForScreen(qf.getSupercededDate().trim()); 
		//  headerSupersedesTime  = GeneralQuality.formatTimeForScreen(qf.getSupercededTime().trim());      
		  if (!qf.getStatusCode().equals("AC")) 
		     headerStatus = qf.getStatusDescription();
       }
     //------------------------------------------------------------------------------//
     //  SPECIFICATION
     //------------------------------------------------------------------------------//
       if (headerRequestType.trim().equals("dtlSpec"))
       {
        // Fill in all the variable fields for this included screen  
          DtlSpecification dtlSpec = (DtlSpecification) request.getAttribute("dtlViewBean");
          QaSpecification  qs 	   = dtlSpec.getDtlBean().getSpecification();
          environment 			   = dtlSpec.getEnvironment().trim(); 
		  headerDocumentNo         = qs.getSpecificationName().trim();
		  headerTitle		       = qs.getSpecificationDescription().trim();
		  headerApprovedBy         = GeneralQuality.findLongNameFromProfile(environment, qs.getApprovedByUser().trim());
		  headerScope		       = qs.getScopeCode().trim();
		  headerScopeDesc		   = qs.getScopeDescription().trim();
		  headerOrigination        = qs.getOriginationUser().trim();      
		  headerOriginationDesc    = GeneralQuality.findLongNameFromProfile(environment, qs.getOriginationUser().trim());
		  headerIssuedDate		   = GeneralQuality.formatDateForScreen(qs.getRevisionDate().trim());
		  headerIssuedTime		   = GeneralQuality.formatTimeForScreen(qs.getRevisionTime().trim());
		  // 10/30/13 - TWalton
		  //  Adjusted to use New Supersedes date information
		  if (!dtlSpec.getSupersedesDate().getDateFormatyyyyMMdd().trim().equals("0"))
		  {
	          headerSupersedesDate = dtlSpec.getSupersedesDate().getDateFormatMMddyyyySlash().trim();
		      headerSupersedesTime = dtlSpec.getSupersedesDate().getTimeFormathhmmColon().trim(); 
		  }
		  //headerSupersedesDate     = GeneralQuality.formatDateForScreen(qs.getSupercededDate().trim()); 
		  //headerSupersedesTime     = GeneralQuality.formatTimeForScreen(qs.getSupercededTime().trim());      
		  if (!qs.getStatusCode().equals("AC")) 
		     headerStatus = qs.getStatusDescription();
       }
    }catch(Exception e){
      System.out.println("Quality.dtlHeader.jsp problem occurred when retrieving information from the dtlViewBean for requestType: " +
          headerRequestType.trim());
    }
%>
<html>
 <body>
  <table class="table00" cellspacing="0" style="width:100%">
   <tr>
    <td class="td04100605" rowspan="4" style="width=125px;">
        <img src="/web/Include/images/TT_logo2C-2013.png" style="height:40px; margin:0 20px;">
    </td>
    <td class="td04140605" rowspan="3" style="width=125px;">Title</td> 
    <td class="td04200605" rowspan="3"><b><%= headerStatus %>&#160;<%= headerDocumentNo.trim() %><br>
                                       &#160;<%= headerTitle.trim() %></b></td> 
    <td class="td04140605" style="width=125px;">Scope</td> 
    <td class="td04140605"><abbr title="Code: <%= headerScope %>">&#160;<%= headerScopeDesc.trim() %></abbr></td> 
   </tr> 
   <tr>
    <td class="td04140605">Origination</td> 
    <td class="td04140605"><abbr title="Code: <%= headerOrigination %>">&#160;<%= headerOriginationDesc.trim() %></abbr></td> 
   </tr> 
   <tr>
    <td class="td04140605">Issued</td> 
    <td class="td04140605"><abbr title="Specific Date (Revision Date) this version was made active,&#13 Date: <%= headerIssuedDate.trim() %>  Time: <%= headerIssuedTime.trim() %>">&#160;<%= headerIssuedDate.trim() %></abbr></td> 
   </tr> 
   <tr>
    <td class="td04140605">By</td> 
    <td class="td04140605">&#160;<%= headerApprovedBy.trim() %></td> 
    <td class="td04140605">Supersedes</td> 
    <td class="td04140605"><abbr title="Last Active Date Prior to this version,\n Date: <%= headerSupersedesDate.trim() %>  Time: <%= headerSupersedesTime.trim() %>">&#160;<%= headerSupersedesDate.trim() %></abbr></td> 
   </tr> 
  </table>
 </body>
</html>