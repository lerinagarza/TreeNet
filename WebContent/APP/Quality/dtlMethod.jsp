<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.QaMethod" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- dtlMethod.jsp ------------------------------//
//  Author :  Teri Walton  10/26/10
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String dtlTitle = "Detail of Method";  
  String valueDisplay = "Method";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 DtlMethod dtlMethod = new DtlMethod();
 QaMethod dtlHeader = new QaMethod();
 try
 {
	dtlMethod = (DtlMethod) request.getAttribute("dtlViewBean");
	if (dtlMethod.getRequestType().trim().equals("dtlProcedure"))
	{
	  dtlTitle = "Detail of Procedure";
	  valueDisplay = "Procedure";
	}
	if (dtlMethod.getRequestType().trim().equals("dtlPolicy"))
	{
	  dtlTitle = "Detail of Policy";
	  valueDisplay = "Policy";
	}
	dtlTitle = dtlTitle + "&#160;" + dtlMethod.getMethodNumber();
	dtlHeader = dtlMethod.getDtlBean().getMethod();
 }
 catch(Exception e)
 {
 }  
%>
<html>
 <head>
  <title><%= dtlTitle %></title>
  <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>     
  <%= JavascriptInfo.getMoreButton() %>
 </head>
 <body>
  <jsp:include page="dtlHeader.jsp"></jsp:include>
<%
  //---------------------------------------------------------
  //  Special Header Section (not using the HeaderTable information
  //
//-------------------------------------------------------------------------------------------------------
// Theory - comment
//     Method Only
//-------------------------------------------------------------------------------------------------------
  int imageCount  = 1;
  int expandCount = 0;
  request.setAttribute("appType", "method");  
 if (dtlMethod.getListTheory().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", dtlMethod.getListTheory());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("O", "Theory & Principle", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListTheoryUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url");
     request.setAttribute("listKeyValues", dtlMethod.getListTheoryUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Theory & Principle Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Equipment - comment1
//       Method Only
//-------------------------------------------------------------------------------------------------------
 if (dtlMethod.getListEquipment().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("listKeyValues", dtlMethod.getListEquipment());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Equipment", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListEquipmentUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url1");
     request.setAttribute("listKeyValues", dtlMethod.getListEquipmentUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Equipment Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Reagents - comment4
//       Method Only
//-------------------------------------------------------------------------------------------------------
 if (dtlMethod.getListReagents().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment4");
  request.setAttribute("listKeyValues", dtlMethod.getListReagents());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Reagents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListReagentsUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url4");
     request.setAttribute("listKeyValues", dtlMethod.getListReagentsUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Reagents Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Caution - comment5
//       Method Only
//-------------------------------------------------------------------------------------------------------
 if (dtlMethod.getListCaution().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment5");
  request.setAttribute("listKeyValues", dtlMethod.getListCaution());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Caution", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListCautionUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url5");
     request.setAttribute("listKeyValues", dtlMethod.getListCautionUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Caution Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Frequency - comment6
//       Method Only
//-------------------------------------------------------------------------------------------------------
 if (dtlMethod.getListFrequency().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment6");
  request.setAttribute("listKeyValues", dtlMethod.getListFrequency());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Frequency", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListFrequencyUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url6");
     request.setAttribute("listKeyValues", dtlMethod.getListFrequencyUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Frequency Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Policy - comment4
//       Procedure, and Policy
//-------------------------------------------------------------------------------------------------------
 if (dtlMethod.getListPolicy().size() > 0 &&
     (valueDisplay.trim().equals("Procedure") ||
      valueDisplay.trim().equals("Policy")))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment4");
  request.setAttribute("listKeyValues", dtlMethod.getListPolicy());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Policy", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListPolicyUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url4");
     request.setAttribute("listKeyValues", dtlMethod.getListPolicyUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Policy Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Purpose - comment5
//       Procedure Only
//------------------------------------------------------------------------------------------------------- 
 if (dtlMethod.getListPurpose().size() > 0 &&
     valueDisplay.trim().equals("Procedure"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment5");
  request.setAttribute("listKeyValues", dtlMethod.getListPurpose());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Purpose", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListPurposeUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url5");
     request.setAttribute("listKeyValues", dtlMethod.getListPurposeUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Purpose Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Scope - comment6
//       Procedure Only
//-------------------------------------------------------------------------------------------------------  
 if (dtlMethod.getListScope().size() > 0 &&
     valueDisplay.trim().equals("Procedure"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment6");
  request.setAttribute("listKeyValues", dtlMethod.getListScope());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Scope", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListScopeUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url6");
     request.setAttribute("listKeyValues", dtlMethod.getListScopeUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Scope Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Responsibility - comment7
//       Procedure Only
//-------------------------------------------------------------------------------------------------------  
 if (dtlMethod.getListResponsibility().size() > 0 &&
     valueDisplay.trim().equals("Procedure"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment7");
  request.setAttribute("listKeyValues", dtlMethod.getListResponsibility());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Responsibility", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListResponsibilityUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url7");
     request.setAttribute("listKeyValues", dtlMethod.getListResponsibilityUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Responsibility Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Definitions - comment1
//       Procedure Only
//-------------------------------------------------------------------------------------------------------  
 if (dtlMethod.getListDefinitions().size() > 0 &&
     valueDisplay.trim().equals("Procedure"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("listKeyValues", dtlMethod.getListDefinitions());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Definitions", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListDefinitionsUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url1");
     request.setAttribute("listKeyValues", dtlMethod.getListDefinitionsUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Definitions Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Procedure - comment2
//       Method and Procedure 
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListProcedure().size() > 0 &&
     (valueDisplay.trim().equals("Method") ||
      valueDisplay.trim().equals("Procedure")))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment2");
  request.setAttribute("listKeyValues", dtlMethod.getListProcedure());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Procedure", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListProcedureUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url2");
     request.setAttribute("listKeyValues", dtlMethod.getListProcedureUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Procedure Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Examples - comment7
//       Method Only
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListExamples().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment7");
  request.setAttribute("listKeyValues", dtlMethod.getListExamples());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Examples", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListExamplesUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url7");
     request.setAttribute("listKeyValues", dtlMethod.getListExamplesUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Examples Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Calculations - comment8
//       Method Only
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListCalculations().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment8");
  request.setAttribute("listKeyValues", dtlMethod.getListCalculations());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Calculations", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListCalculationsUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url8");
     request.setAttribute("listKeyValues", dtlMethod.getListCalculationsUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Calculations Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 }
//-------------------------------------------------------------------------------------------------------
// Interpretation of Results - comment9
//       Method Only 
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListInterpretation().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment9");
  request.setAttribute("listKeyValues", dtlMethod.getListInterpretation());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Interpretation of Results", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListInterpretationUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url9");
     request.setAttribute("listKeyValues", dtlMethod.getListInterpretationUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Interpretation Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 } 
//-------------------------------------------------------------------------------------------------------
// Authorization - comment1
//       Policy Only
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListAuthorization().size() > 0 &&
     valueDisplay.trim().equals("Policy"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("listKeyValues", dtlMethod.getListAuthorization());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Authorization", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListAuthorizationUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url1");
     request.setAttribute("listKeyValues", dtlMethod.getListAuthorizationUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Authorization Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 } 
//-------------------------------------------------------------------------------------------------------
// Actions - comment10
//       Method and Policy
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListActions().size() > 0 &&
     (valueDisplay.trim().equals("Method") ||
      valueDisplay.trim().equals("Policy")))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment10");
  request.setAttribute("listKeyValues", dtlMethod.getListActions());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Actions", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListActionsUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url10");
     request.setAttribute("listKeyValues", dtlMethod.getListActionsUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Actions Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 } 
//-------------------------------------------------------------------------------------------------------
// Reporting - comment11
//       Method Only 
//-------------------------------------------------------------------------------------------------------   
 if (dtlMethod.getListReporting().size() > 0 &&
     valueDisplay.trim().equals("Method"))
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment11");
  request.setAttribute("listKeyValues", dtlMethod.getListReporting());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Reporting", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListReportingUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url11");
     request.setAttribute("listKeyValues", dtlMethod.getListReportingUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Reporting Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 } 
//-------------------------------------------------------------------------------------------------------
// Comments - comment3
//-------------------------------------------------------------------------------------------------------   
 
 if (dtlMethod.getListAdditional().size() > 0)
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment3");
  request.setAttribute("listKeyValues", dtlMethod.getListAdditional());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Comments", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   if (dtlMethod.getListAdditionalUrl().size() > 0)
   { // Images and Documents tied to this specific SECTION of the Screen
     imageCount++;
     expandCount++;
     request.setAttribute("screenType", "detail");
     request.setAttribute("longFieldType", "url3");
     request.setAttribute("listKeyValues", dtlMethod.getListAdditionalUrl());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td05141405">
     <%= JavascriptInfo.getExpandingSection("C", "Additional Images and Documents", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } 
 } 
 if (dtlMethod.getListComments().size() > 0)
 {
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", dtlMethod.getListComments());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 6) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
 }
  imageCount++;
  expandCount++;
  // Create Expandable Section for Revision Reasons for this Specification
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection("C", "Revision Reasons", 0, expandCount, imageCount, 1, 6) %>
         <jsp:include page="listMethodTable.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table>     
 <%= HTMLHelpers.pageFooterTable(dtlMethod.getRequestType()) %>

   </body>
</html>