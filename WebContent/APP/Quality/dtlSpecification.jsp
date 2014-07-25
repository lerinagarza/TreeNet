<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.BigDecimal" %>
<%
//----------------------- dtlSpecification.jsp ------------------------------//
//  Author :  Teri Walton  02/23/11
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String dtlTitle = "Detail of Specification";  
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 DtlSpecification         dtlSpec   = new DtlSpecification();
 QaSpecificationPackaging dtlHeader = new QaSpecificationPackaging();
 try
 {
	dtlSpec = (DtlSpecification) request.getAttribute("dtlViewBean");
	dtlTitle = dtlTitle + "&#160;" + dtlSpec.getSpecNumber() + "&#160;&#160;" + dtlSpec.getSpecNumber();
	dtlHeader = dtlSpec.getDtlBean().getSpecPackaging();
 }
 catch(Exception e)
 {}
 int imageCount = 1;
 int expandCount = 0;
 request.setAttribute("appType", "spec");  
%>
<html>
 <head>
  <title><%= dtlTitle %></title>
  <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>     
  <%= JavascriptInfo.getMoreButton() %>
  <%= JavascriptInfo.getCalendarHead() %>  
 </head>
 <body>
 <jsp:include page="dtlHeader.jsp"></jsp:include>
 <table class="table00" cellspacing="0" style="width:100%">
  <tr>
   <td class="td05180102" colspan = "5">&#160;<b><%= dtlHeader.getTypeDescription() %>&#160;Specification</b></td>
  </tr>	  
<%
   if (!dtlSpec.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= dtlSpec.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
  
//  <tr>

 // MAY add security for this one
  // --- Build Vector for the MORE Button
//  Vector sendParms = new Vector();
//  sendParms.add(dtlSpec.getRequestType());
//  sendParms.add(dtlSpec.getSecurityLevel());
//  sendParms.add(dtlSpec.getEnvironment());
 // sendParms.add(dtlHeader.getSpecificationNumber());
//  sendParms.add(dtlHeader.getRevisionDate());
//  sendParms.add(dtlHeader.getRevisionTime());
    
//   <td class="td04140102" style="text-align:right" colspan="5">&nbsp;<%= InqQASpecification.buildMoreButton(sendParms) </td>
//  </tr>
//---------------------------------------------------
// -- KOSHER and Country of Origin Line
// if ONLY Kosher needs to display
    if (!dtlHeader.getKosherStatus().trim().equals("") &&
         dtlHeader.getCountryOfOrigin().trim().equals("")){ 
%>
  <tr>
   <td class="td04140102" style="width:20%">Kosher Status: </td>
   <td class="td04140102" colspan = "4">&#160;
    <abbr title="Kosher Status Code Value: <%= dtlHeader.getKosherStatus().trim() %>">
     <b><%= dtlHeader.getKosherStatusDescription().trim() %>&#160;&#160;<img src="<%= dtlHeader.getKosherSymbol().trim() %>" style="height:20px"/></b>
    </abbr> 
   </td>
  </tr>
<%
  }
// if ONLY Country of Origin Needs to display  
  if (dtlHeader.getKosherStatus().trim().equals("") &&
      !dtlHeader.getCountryOfOrigin().trim().equals("")){
%>
  <tr>
   <td class="td04140102" colspan = "3">&#160;
   <td class="td04140102" style="width:20%">Country of Origin: </td>
   <td class="td04140102">&#160;
    <abbr title="Country of Origin %>">
     <b><%= dtlHeader.getCountryOfOrigin().trim() %>&#160;</b>
    </abbr> 
   </td>
  </tr>           
<%  
  }
  // if BOTH Country of Origin ans Kosher status
  if (!dtlHeader.getKosherStatus().trim().equals("") &&
      !dtlHeader.getCountryOfOrigin().trim().equals("")){
%>
  <tr>
   <td class="td04140102" style="width:20%">Kosher Status: </td>
   <td class="td04140102">&#160;
    <abbr title="Kosher Status Code Value: <%= dtlHeader.getKosherStatus().trim() %>">
     <b><%= dtlHeader.getKosherStatusDescription().trim() %>&#160;&#160;<img src="<%= dtlHeader.getKosherSymbol().trim() %>" style="height:20px"/></b>
    </abbr> 
   </td>
   <td class="td04140102">&#160;
   <td class="td04140102" style="width:20%">Country of Origin: </td>
   <td class="td04140102">&#160;
    <abbr title="Country of Origin %>">
     <b><%= dtlHeader.getCountryOfOrigin().trim() %>&#160;</b>
    </abbr> 
   </td>
  </tr>           
<%  
  }
//---------------------------------------------------- 
  if (!dtlHeader.getInlineSockRequired().trim().equals(""))
  { // Only Display if a Inline Sock Required is Chosen
%>  
  <tr>
   <td class="td04140102" style="width:20%">Inline Filter Required: </td>
   <td class="td04140102" colspan = "4">&#160;
    <abbr title="Inline Sock Required Code Value: <%= dtlHeader.getInlineSockRequired().trim() %>">
     <b><%= dtlHeader.getInlineSockDescription().trim() %></b>
    </abbr> 
   </td>
  </tr>
<%
  }
  if (!dtlHeader.getCipType().trim().equals(""))
  { // Only Display if a CIP Type is Chosen
%>  
  <tr>
   <td class="td04140102" style="width:20%">CIP Type: </td>
   <td class="td04140102" colspan = "4">&#160;
    <abbr title="CIP Type Code Value: <%= dtlHeader.getCipType().trim() %>">
     <b><%= dtlHeader.getCipTypeDescription().trim() %></b>
    </abbr> 
   </td>
  </tr>
<%
  } 
  if (!dtlHeader.getCutSizeDescription().trim().equals(""))
  { // Only Display if a Cut Size has been is Chosen
%>  
  <tr>
   <td class="td04140102" style="width:20%">Cut Size: </td>
   <td class="td04140102" colspan = "4">&#160;
    <abbr title="Cut Size Code Value: <%= dtlHeader.getCutSizeCode().trim() %>">
     <b><%= dtlHeader.getCutSizeDescription().trim() %>
<% 
    if (!dtlHeader.getCutSizeDescription2().trim().equals(""))
       out.println(" and " + dtlHeader.getCutSizeDescription2());
%>     
     </b>
    </abbr> 
   </td>
  </tr>
<%
  } 
  if (!dtlHeader.getScreenSizeDescription().trim().equals(""))
  { // Only Display if a Screen Size has been is Chosen
%>  
  <tr>
   <td class="td04140102" style="width:20%">Screen Size: </td>
   <td class="td04140102" colspan = "4">&#160;
    <abbr title="Screen Size Code Value: <%= dtlHeader.getScreenSizeCode().trim() %>">
     <b><%= dtlHeader.getScreenSizeDescription().trim() %></b>
    </abbr> 
   </td>
  </tr>
<%
  } 
    if (!dtlHeader.getForeignMaterialsDetectionDescription().trim().equals(""))
  { // Only Display if a Foreign Material Detection has been is Chosen
%>  
  <tr>
   <td class="td04140102" style="width:20%">Foreign Material Detection: </td>
   <td class="td04140102" colspan = "4">&#160;
    <abbr title="Foreign Material Detection Code Value: <%= dtlHeader.getForeignMaterialsDetectionCode().trim() %>">
     <b><%= dtlHeader.getForeignMaterialsDetectionDescription().trim() %></b>
    </abbr> 
   </td>
  </tr>
<%
  } 
  if (!dtlHeader.getCustomerNumber().trim().equals(""))
  {
    String custCodeAbbr = "";
    if (!dtlHeader.getCustomerNumber().trim().equals("") &&
        !dtlHeader.getCustomerNumber().trim().equals("0"))
        custCodeAbbr = "Customer Data From " + dtlHeader.getCustomerCode().trim() + " system.";
%>  
  <tr>
   <td class="td04140102" style="width:20%">&#160;</td>
   <td class="td04140102">&#160;</td>      
   <td class="td04140102" style="width:2%" rowspan="2">&#160;</td>
   <td class="td04140102">
<%
      if (!custCodeAbbr.trim().equals(""))
        out.println("<abbr title=\"Customer Number\">Customer Number:</abbr>");
%> 
    &#160;  
   </td>
   <td class="td04140102">&#160;
<%
   if (!custCodeAbbr.trim().equals(""))
     out.println("<abbr title=\"" + custCodeAbbr + "\">&#160;" + dtlHeader.getCustomerNumber().trim() + "</abbr>");
%>    
   </td>      
  </tr>	 
<%
   String custNameAbbr = "";
   if (!custCodeAbbr.trim().equals("") &&
       !dtlHeader.getCustomerNumber().trim().equals("") &&
       !dtlHeader.getCustomerNumber().trim().equals("0"))
       custNameAbbr = "Customer " + dtlHeader.getCustomerNumber() + " Data From " + dtlHeader.getCustomerCode().trim() + " system.";
%>    
  <tr>
     <td class="td04140102">
<%
   if (!custNameAbbr.trim().equals(""))
     out.println("<abbr title=\"Customer Name\">Customer Name:</abbr>");
%>   
    &#160;
   </td>
   <td class="td04140102">&#160;
<%
   if (!custNameAbbr.trim().equals(""))
     out.println("<abbr title=\"" + custNameAbbr + "\">&#160;" + dtlHeader.getCustomerName().trim() + "</abbr>");
%>      
   </td>     
   </tr>
<%
   } %>
   
   
<%  if (!dtlHeader.getItemNumber().equals("")) { %>
  <tr>
   <td class="td04140102">Item Number:&#160;</td>
   <td class="td04140102">&#160;
     <%=dtlHeader.getItemNumber() %> <%=dtlHeader.getItemDescription() %>
   </td>     
   </tr>
<%  } %>
   
   
<%   if (!dtlHeader.getFormulaName().equals("")) {
       String formulaInfoAbbr = "Formula " + dtlHeader.getFormulaNumber() + " ";
       String formulaLink = "/web/CtlQuality?requestType=dtlFormula&formulaNumber=" + dtlHeader.getFormulaNumber();
%>   
  <tr>
   <td class="td04140102">Formula Number / Revision:&#160;</td>
   <td class="td04140102">&#160;
     <%= HTMLHelpersLinks.basicLink(dtlHeader.getFormulaName(), formulaLink, formulaInfoAbbr, "", "") %> 
   </td>     
   </tr>
<%
   }
%>   

 </table>
<%
//------------------------------------------------------
// **** Product Description - Comment Section
 if (dtlSpec.getListProductDescription() != null &&
    !dtlSpec.getListProductDescription().isEmpty())
 {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment21");
  	   request.setAttribute("listKeyValues", dtlSpec.getListProductDescription());
%>  
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("C", "Product Description", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
 </table> 
<%
 }
 //------------------------------------------------------
// **** Ingredient Statement - Comment Section
 if (dtlSpec.getListIngredientStatement() != null &&
    !dtlSpec.getListIngredientStatement().isEmpty())
 {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment22");
  	   request.setAttribute("listKeyValues", dtlSpec.getListIngredientStatement());
%>  
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("C", "Ingredient Statement", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
 </table> 
<%
 }
//------------------------------------------------------
  //****** ANALYTICAL TESTS
  if (!dtlSpec.getDtlBean().getSpecAnalyticalTests().isEmpty()){
    request.setAttribute("screenType", "TEST");
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Analytical Testing", 0, expandCount, imageCount, 1, 0) %>
      <table class="table00" cellspacing="0" style="width:100%">
<%
 //---------------------------------------------------------------------
 //****  Row for Test Brix - Reconstitution Ratio
 
 //  IF ONLY Test Brix
   if ((!dtlHeader.getTestBrix().trim().equals("") &&
       !dtlHeader.getTestBrix().trim().equals("0.0")) &&
       dtlHeader.getReconstitutionRatio().trim().equals(""))
   {
%>      
       <tr>
        <td class="td04140102" style="width:20%">&#160;&#160;<abbr title="Brix that the Analytical Tests should be tested at">Test Brix:</abbr></td>
        <td class="td04140102" style="width:20%" >&#160;<%= dtlHeader.getTestBrix() %>&#160;</td>  
        <td class="td04140102" colspan="3">&#160;</td> 
       </tr>
<%
   }
    //  IF ONLY Reconstitution Ratio
   if ((dtlHeader.getTestBrix().trim().equals("") ||
        dtlHeader.getTestBrix().trim().equals("0.0")) &&
       !dtlHeader.getReconstitutionRatio().trim().equals(""))
   {
%>      
       <tr>
        <td class="td04140102" colspan="3">&#160;</td> 
        <td class="td04140102" style="width:20%">&#160;&#160;<abbr title="Ratio the test should be reconstituted To">Reconstitution Ratio:</abbr></td>
        <td class="td04140102" style="width:20%" >&#160;<%= dtlHeader.getReconstitutionRatio() %>&#160;</td>  
       </tr>
<%
   }
   //  IF BOTH have Values
   if ((!dtlHeader.getTestBrix().trim().equals("") &&
        !dtlHeader.getTestBrix().trim().equals("0.0")) &&
       !dtlHeader.getReconstitutionRatio().trim().equals(""))
   {
%>      
       <tr>
        <td class="td04140102" style="width:20%">&#160;&#160;<abbr title="Brix that the Analytical Tests should be tested at">Test Brix:</abbr></td>
        <td class="td04140102" style="width:20%" >&#160;<%= dtlHeader.getTestBrix() %>&#160;</td>  
        <td class="td04140102">&#160;</td> 
        <td class="td04140102" style="width:20%">&#160;&#160;<abbr title="Ratio the test should be reconstituted To">Reconstitution Ratio:</abbr></td>
        <td class="td04140102" style="width:20%" >&#160;<%= dtlHeader.getReconstitutionRatio() %>&#160;</td>  
       </tr>
<%   
   }
  //--------------------------------------------------------------------- 
%>       
       <tr>
        <td colspan = "5">
         <jsp:include page="dtlTestParameters.jsp"></jsp:include>
        </td>
       </tr> 
      </table>   
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
<%
    //  Additional Information Relating to the Analytical Tests
    if (dtlSpec.getListAnalyticalTestComments() != null &&
       !dtlSpec.getListAnalyticalTestComments().isEmpty())
    {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment1");
  	   request.setAttribute("listKeyValues", dtlSpec.getListAnalyticalTestComments());
%>  
  <tr>
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("O", "Additional Analytical Test Instructions", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
<%
    }
%>   
  </table> 
<%
  }
  //****** MICRO TESTING
  if (!dtlSpec.getDtlBean().getSpecMicroTests().isEmpty()){
    imageCount++;
    expandCount++;
    request.setAttribute("screenType", "MICRO");
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Micro and Trace Element Testing", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlTestParameters.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
<%
    //  Additional Information Relating to the Micro Tests
    if (dtlSpec.getListMicroTestComments() != null &&
       !dtlSpec.getListMicroTestComments().isEmpty())
    {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment3");
  	   request.setAttribute("listKeyValues", dtlSpec.getListMicroTestComments());
%>  
  <tr>
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("O", "Additional Micro Test Instructions", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
<%
    }
%>     
  </table> 
<%
  }
  //****** PROCESS PARAMETERS
  if (!dtlSpec.getDtlBean().getSpecProcessParameters().isEmpty()){
    imageCount++;
    expandCount++;
    request.setAttribute("screenType", "PROC");
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Process Parameters", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlTestParameters.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
<%
    //  Additional Information Relating to the Process Parameters
    if (dtlSpec.getListProcessParameterComments() != null &&
       !dtlSpec.getListProcessParameterComments().isEmpty())
    {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment2");
  	   request.setAttribute("listKeyValues", dtlSpec.getListProcessParameterComments());
%>  
  <tr>
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("O", "Additional Process Parameter Instructions", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
<%
    }
%>      
  </table> 
<%
  }

  //****** ADDITIVES AND PRESERVATIVES
  if (!dtlSpec.getDtlBean().getSpecAdditiveAndPreserve().isEmpty()){
    imageCount++;
    expandCount++;
    request.setAttribute("screenType", "ADD");
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Additives and Preservatives", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlTestParameters.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
  </table>   
<%
    }
    //  Additional Information Relating to the Additives and Preservatives
    if (dtlSpec.getListAdditivesAndPreservativeComments() != null &&
       !dtlSpec.getListAdditivesAndPreservativeComments().isEmpty())
    {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment4");
  	   request.setAttribute("listKeyValues", dtlSpec.getListAdditivesAndPreservativeComments());
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr>
    <td class="td03141405" colspan="2">
     <%= JavascriptInfo.getExpandingSection("O", "Additional Additives and Preservative Instructions", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr> 
  </table> 
<%
    }
    //***********EXPANDING SECTION************************************************************
 //  Both Include and Exclude Varieties
 if ((dtlSpec.getDtlBean().getVarietiesIncluded() != null &&
     !dtlSpec.getDtlBean().getVarietiesIncluded().isEmpty()) ||
     (dtlSpec.getDtlBean().getVarietiesExcluded() != null &&
     !dtlSpec.getDtlBean().getVarietiesExcluded().isEmpty()))
 {  
  imageCount++;
  expandCount++;
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Fruit Variety Information", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlVariety.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr> 
  </table> 
<%
 } // end of the Fruit Variety Information
    //  Additional Information Relating to the Fruit Varieties
 if (dtlSpec.getListFruitVarietiesAdditional() != null &&
    !dtlSpec.getListFruitVarietiesAdditional().isEmpty())
 {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment16");
  	   request.setAttribute("listKeyValues", dtlSpec.getListFruitVarietiesAdditional());
%>  
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("O", "Fruit Variety Additional Comments", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
  </table> 
<%
 }
//------------------------------------------------------
// **** Intended Use - Comment Section
 if (dtlSpec.getListIntendedUse() != null &&
    !dtlSpec.getListIntendedUse().isEmpty())
 {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment23");
  	   request.setAttribute("listKeyValues", dtlSpec.getListIntendedUse());
%>  
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("C", "Intended Use", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
 </table> 
<%
 }   
//------------------------------------------------------
// **** Foreign Matter - Comment Section
 if (dtlSpec.getListForeignMatter() != null &&
    !dtlSpec.getListForeignMatter().isEmpty())
 {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment24");
  	   request.setAttribute("listKeyValues", dtlSpec.getListForeignMatter());
%>  
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("C", "Foreign Matter", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
 </table> 
<%
 }      
  //****** CODING REQUIREMENTS
    imageCount++;
    expandCount++;
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Coding Requirements", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlSpecificationCoding.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
  </table> 
<%
//---------------------------------------------------------  
//****** Shelf Life
if (dtlHeader.getShelfLifeNotValid().trim().equals("") ||
    (dtlSpec.getListShelfLifeRequirements() != null &&
    !dtlSpec.getListShelfLifeRequirements().isEmpty()))
{    
%>  
  <table class="table00" cellspacing="0" cellpadding="2" >
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Shelf Life and Storage Recommendations", 0, expandCount, imageCount, 1, 0) %>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (dtlHeader.getShelfLifeNotValid().trim().equals(""))
   {
%>      
      
       <tr class="tr00">
        <td class="td04140102" colspan="2">&#160;Shelf Life:&#160;<%= dtlHeader.getShelfLife() %>&#160;Days</td>
       </tr>
<%
    }
    if (dtlSpec.getListShelfLifeRequirements() != null &&
       !dtlSpec.getListShelfLifeRequirements().isEmpty())
    {   
       request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment13");
  	   request.setAttribute("listKeyValues", dtlSpec.getListShelfLifeRequirements());
%>  
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102">
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    </td>
   </tr>
<%
    }
%>            
      </table>     
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
  </table>   
<%
} // Should the entire Shelf Life section be skipped?
//-----------------------------------------------------------  
//****** Storage Information
if (!dtlHeader.getStorageRecommendation().trim().equals("") ||
    !dtlHeader.getM3StorageRecommendation().trim().equals("") ||
    (dtlSpec.getListStorageRequirements() != null &&
    !dtlSpec.getListStorageRequirements().isEmpty()))
{  
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Storage Information", 0, expandCount, imageCount, 1, 0) %>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!dtlHeader.getM3StorageRecommendation().trim().equals("")){
%>
       <tr class="tr00">
        <td class="td04140102">&#160;<%= "Required Storage: " + dtlHeader.getM3StorageRecommendation().trim() %></td>
       </tr>
<%
    }else{
       if (!dtlHeader.getStorageRecommendation().trim().equals(""))
       {
%>
       <tr class="tr00">
        <td class="td04140102">&#160;<%= "Required Storage: " + dtlHeader.getStorageRecommendationDescription().trim() %></td>
       </tr>
<%
       }
    }   
    if (dtlSpec.getListStorageRequirements() != null &&
       !dtlSpec.getListStorageRequirements().isEmpty())
    {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment14");
  	   request.setAttribute("listKeyValues", dtlSpec.getListStorageRequirements());
%>  
  <tr>
   <td class="td03141405" colspan="2">
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
   </td>
  </tr>  
<%
    }
%>            
      </table>     
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
  </table> 
<%  
} // Should the entire Storage Information section be skipped?
//-----------------------------------------------------------  
  //****** Additional Images and Documents
   if (dtlSpec.getListSpecUrls() != null &&
      !dtlSpec.getListSpecUrls().isEmpty())
    {   
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Additional Images and Documents", 0, expandCount, imageCount, 1, 0) %>
      <table class="table00" cellspacing="0" style="width:100%">
<%
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "url");
  	   request.setAttribute("listKeyValues", dtlSpec.getListSpecUrls());
%>  
  <tr>
   <td class="td03141405" colspan="2">
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
   </td>
  </tr>  
      </table>     
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
  </table>
<%
   }
%>    
  
  <table class="table00" cellspacing="0" style="width:100%">
<%
   if ((!dtlHeader.getPalletRequirementDescription().trim().equals("") ||
       !dtlSpec.getListPalletPattern().isEmpty()) ||
       (dtlSpec.getListFinishedPalletAdditional() != null &&
       !dtlSpec.getListFinishedPalletAdditional().isEmpty()) ||
       (dtlSpec.getListCodingRequirementsAdditional() != null &&
       !dtlSpec.getListCodingRequirementsAdditional().isEmpty()) ||
       (dtlSpec.getListShippingRequirements() != null &&
       !dtlSpec.getListShippingRequirements().isEmpty()) ||
       !dtlHeader.getSlipSheetRequired().trim().equals("") ||
       !dtlHeader.getStretchWrapRequired().trim().equals("") ||
       !dtlHeader.getShrinkWrapRequired().trim().equals(""))
       {
%>  
   <tr class="tr02">
    <td class="td05180102" colspan="4">&#160;<b>Pallet</b>&#160;</td>
   </tr>
<%
   if (!dtlHeader.getPalletRequirementDescription().trim().equals("") ||
       !dtlSpec.getListPalletPattern().isEmpty())
   {
%>  
   <tr class="tr00">
    <td class="td04140102" colspan="2">&#160;<%= dtlHeader.getPalletRequirementDescription() %></td>
    <td class="td04140102" colspan="2">&#160;
<%    
     if (!dtlSpec.getListPalletPattern().isEmpty())
     {
        KeyValue listLine = (KeyValue) dtlSpec.getListPalletPattern().elementAt(0);
        out.println(HTMLHelpersLinks.basicLink("Pallet Pattern", listLine.getValue(), "", "a0518", ""));
     }
%>
    </td>
   </tr>
<%
    }
    if (dtlSpec.getListFinishedPalletAdditional() != null &&
       !dtlSpec.getListFinishedPalletAdditional().isEmpty())
    {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment15");
  	   request.setAttribute("listKeyValues", dtlSpec.getListFinishedPalletAdditional());
%>  
  <tr>
   <td class="td03141405" colspan="4">
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
   </td>
  </tr>  
<%
    }
 //------------------------------------------------------
// **** Additional Coding Requirements - Comment Section
 if (dtlSpec.getListCodingRequirementsAdditional() != null &&
    !dtlSpec.getListCodingRequirementsAdditional().isEmpty())
 {   
       imageCount++;
  	   expandCount++;
  	   request.setAttribute("imageCount", (imageCount + ""));
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment25");
  	   request.setAttribute("listKeyValues", dtlSpec.getListCodingRequirementsAdditional());
%>  
  <tr class="tr02">
   <td class="td03141405" colspan="4">
    <%= JavascriptInfo.getExpandingSection("C", "Additional Coding Requirements", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
<%
 }         
    if (dtlSpec.getListShippingRequirements() != null &&
       !dtlSpec.getListShippingRequirements().isEmpty())
    {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment17");
  	   request.setAttribute("listKeyValues", dtlSpec.getListShippingRequirements());
%>  
   <tr class="tr02">
    <td class="td05180102" colspan="4">&#160;<b>Shipping Requirements</b>&#160;</td>
   </tr>
  <tr>
   <td class="td03141405" colspan="4">
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
   </td>
  </tr>  
<%
    }
   if (!dtlHeader.getSlipSheetRequired().trim().equals(""))
   {
%>  
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" style="width:20%">&#160;Slipsheet is required</td>
    <td class="td04140102" style="width:20%">Location:</td>
    <td class="td04140102"><abbr title="Where on the Pallet should the Slip Sheet be Located?" %>&#160;
      Slipsheets will be added to the pallet:&#160;
<%
   StringBuffer sb = new StringBuffer();
   if (!dtlHeader.getSlipSheetBottom().trim().equals(""))
     sb.append("bottom");
   if ((!dtlHeader.getSlipSheetTop().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer1().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer2().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer3().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer4().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer5().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer6().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer7().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer8().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer9().trim().equals("") ||
       !dtlHeader.getSlipSheetLayer10().trim().equals("")))
    {   
       if (!dtlHeader.getSlipSheetLayer1().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 1");
       }  
       if (!dtlHeader.getSlipSheetLayer2().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 2");
       }           
       if (!dtlHeader.getSlipSheetLayer3().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 3");
       }  
       if (!dtlHeader.getSlipSheetLayer4().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 4");
       }  
       if (!dtlHeader.getSlipSheetLayer5().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 5");
       }   
       if (!dtlHeader.getSlipSheetLayer6().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 6");
       }  
        if (!dtlHeader.getSlipSheetLayer7().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 7");
       }  
       if (!dtlHeader.getSlipSheetLayer8().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 8");
       }  
       if (!dtlHeader.getSlipSheetLayer9().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 9");
       }   
       if (!dtlHeader.getSlipSheetLayer10().trim().equals(""))
       {
          if (!sb.toString().trim().equals(""))
            sb.append(", ");
          sb.append("after Layer 10");
       }  
       if (!dtlHeader.getSlipSheetTop().trim().equals(""))
       {
          sb.append(" and On Top of the Pallet");
       }  
    }
%>    
      <%= sb.toString().trim() %>
     </abbr>
    </td>
   </tr>
<%
   }
   if (!dtlHeader.getStretchWrapRequired().trim().equals(""))
   {
%>  
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" style="width:20%">&#160;Stretch Wrap is required</td>
    <td class="td04140102" style="width:20%">Type:</td>
    <td class="td04140102"><abbr title="Type Code Value: <%= dtlHeader.getStretchWrapType() %>" %>&#160;
      <%= dtlHeader.getStretchWrapTypeDescription() %></abbr>
    </td>
   </tr>
   <tr class="tr00"> 
    <td class="td04140102">&#160;</td>
    <td class="td04140102">&#160;</td>
    <td class="td04140102" style="width:20%">Width:</td>
    <td class="td04140102"><%= dtlHeader.getStretchWrapWidth() %>&#160;<%= dtlHeader.getStretchWrapWidthUOMDescription().trim() %></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102">&#160;</td>
    <td class="td04140102" style="width:20%">Gauge:</td>
    <td class="td04140102"><%= dtlHeader.getStretchWrapGauge() %>&#160;<%= dtlHeader.getStretchWrapGaugeUOMDescription().trim() %></td>
   </tr>
<%
   }
   if (!dtlHeader.getShrinkWrapRequired().trim().equals(""))
   {
%>  
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" style="width:20%">Shrink Wrap is required</td>
    <td class="td04140102" style="width:20%">Type:</td>
    <td class="td04140102"><abbr title="Type Code Value: <%= dtlHeader.getShrinkWrapType() %>" %>
      <%= dtlHeader.getShrinkWrapTypeDescription() %></abbr>&#160;
    </td>
   </tr>
   <tr class="tr00">
    <td class="td04140102" colspan="2">&#160;</td>
    <td class="td04140102" style="width:20%">Width:</td>
    <td class="td04140102"><%= dtlHeader.getShrinkWrapWidth() %>&#160;<%= dtlHeader.getShrinkWrapWidthUOMDescription().trim() %></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102" colspan = "2">&#160;</td>
    <td class="td04140102" style="width:20%">Thickness:</td>
    <td class="td04140102"><%= dtlHeader.getShrinkWrapThickness() %>&#160;<%= dtlHeader.getShrinkWrapThicknessUOMDescription().trim() %></td>
   </tr>
<%
   }
 } // end if you should display the "pallet" section
//---------------------------------------------------------------------   
   if (dtlSpec.getListCOARequirements() != null &&
      !dtlSpec.getListCOARequirements().isEmpty())
    {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment18");
  	   request.setAttribute("listKeyValues", dtlSpec.getListCOARequirements());
%>  
   <tr class="tr02">
    <td class="td05180102" colspan="4">&#160;<b>COA Requirements</b>&#160;</td>
   </tr>
  <tr>
   <td class="td03141405" colspan="4">
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
   </td>
  </tr>  
<%
    }
%>
  </table> 
    
<%
  //****** Item / Case / Pallet Information
    imageCount++;
    expandCount++;
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td0412">
     <%= JavascriptInfo.getExpandingSection("O", "Case and Pallet Information", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlSpecificationItem.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
  </table>    
<%
//***********EXPANDING SECTION************************************************************
//  Product Structure Information
 if (dtlSpec.getDtlBean().getProductStructure() != null &&
     !dtlSpec.getDtlBean().getProductStructure().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("listMaterials", dtlSpec.getDtlBean().getProductStructure());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Packaging Components", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Item/listProductStructureMaterialsTable.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
  } // end of the Product Structure  
  imageCount++;
  expandCount++;
  // Create Expandable Section for Revision Reasons for this Specification
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection("C", "Revision Reasons", 0, expandCount, imageCount, 1, 0) %>
         <jsp:include page="listSpecificationTable.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table>  
 <%= HTMLHelpers.pageFooterTable(dtlSpec.getRequestType()) %>

   </body>
</html>