<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp"></jsp:include>
<%  String environment = (String) request.getParameter("environment");
    if (environment == null || environment.equals("")) { 
        environment="PRD";
    }
    
    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    if (roles != null && Arrays.asList(roles).contains("1")) {
        internal = true;
    }
%>
<%-- tpl:put name="headarea" --%>
<%@ page import = "
	com.treetop.app.quality.BuildProductDataSheet,
	com.treetop.app.quality.GeneralQuality,
	com.treetop.businessobjects.QaSpecificationPackaging,
	com.treetop.businessobjects.QaSpecification,
	com.treetop.businessobjects.QaFruitVariety,
	com.treetop.businessobjects.KeyValue,
	com.treetop.utilities.html.HTMLHelpersInput,
    java.util.Vector
   " %>
   
<%   
//  Screen will be used to choose (checkboxes) which pieces of the Spec will be 
//   displayed on the Product DataSheet 
//----------------------- selectProductDataSheet.jsp ------------------------------//
//  Author :  Teri Walton  12/6/13
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Options for Product Data Sheet";  
 // Bring in the Detail View Bean.
 BuildProductDataSheet bldPDS = new BuildProductDataSheet();
 QaSpecificationPackaging dtlSpec = new QaSpecificationPackaging();
 try
 {
	bldPDS = (BuildProductDataSheet) request.getAttribute("bldViewBean");
	updTitle = updTitle + "&#160;for&#160; Add in the Spec Number?";
	dtlSpec = bldPDS.getDtlBean().getSpecPackaging();

	
	request.setAttribute("appType", "spec");
	     
 }catch(Exception e){
    System.out.println("Actually show error - within the selectProductDataSheet.jsp");
 } 
%>    
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>



 <%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>


<h1>Build Product Data Sheet</h1>

<form  name = "bldPDS" action="/web/CtlQuality?requestType=buildProductDataSheet" method="post">
    <input type="hidden" name="environment" value="<%=bldPDS.getEnvironment()%>">
    <input type="hidden" name="specNumber" value="<%=bldPDS.getSpecNumber()%>">
    <input type="hidden" name="revisionDate" value="<%=dtlSpec.getRevisionDate()%>">
    <input type="hidden" name="revisionTime" value="<%=dtlSpec.getRevisionTime()%>">
   
<% if (!bldPDS.getDisplayMessage().trim().equals("")) { %>      
    <div><%= bldPDS.getDisplayMessage().trim() %></div>    
<% } %>   

    <div class="center comment">Choose sections to display on the Product Data Sheet by checking the boxes on the left.</div> 

     
     <input type="submit" name="previewButton" value="PREVIEW">
     
     
<div style="float:right;">
<table>
    <tr>
        <td><input type="checkbox" name="showRevisionDate"></td>
        <td>Revision Date</td>
        <td><%=GeneralQuality.formatDateForScreen(dtlSpec.getRevisionDate()) %></td>
    </tr>
    <tr>
        <td><input type="checkbox" name="showSpecNumber"></td>
        <td>Specification Number</td>
        <td><%=dtlSpec.getSpecificationName() %></td>
    </tr>
    
</table>

</div>
<div class="clearfix"></div>     
     
<h2>Specification Details</h2>
     
 <table class="styled" style="width:100%">
   <tr>
    <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("specType", bldPDS.getSpecType(), "N") %></td> 
    <td>Specification Type:</td>
    <td><b><%=dtlSpec.getTypeDescription() %></b></td>
   </tr>
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("specTitle", bldPDS.getSpecTitle(), "N") %></td> 
    <td>Specification Title:</td>
    <td><b><%= dtlSpec.getSpecificationName() %>&#160;&#160;<%= dtlSpec.getSpecificationDescription() %></b></td>
   </tr>
<%
  // Will only want to show each line IF applicable
   if (!dtlSpec.getKosherSymbol().trim().equals(""))
   {
%>
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("kosherSymbol", bldPDS.getKosherSymbol(), "N") %></td> 
    <td>Kosher Information:</td>
    <td><b><%= dtlSpec.getKosherStatusDescription().trim() %>&#160;&#160;<img src="<%= dtlSpec.getKosherSymbol().trim() %>" style="height:20px"/></b></td>
   </tr>
<%
   }
   if (!dtlSpec.getCountryOfOrigin().trim().equals(""))
   {
%>     
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("countryOfOrigin", bldPDS.getCountryOfOrigin(), "N") %></td> 
    <td>Country of Origin:</td>
    <td><b><%= dtlSpec.getCountryOfOrigin().trim() %></b></td>
   </tr>
<%  
   }
   if (!dtlSpec.getInlineSockRequired().trim().equals(""))
   {
%>     
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("inlineSock", bldPDS.getInlineSock(), "N") %></td> 
    <td>Inline Filter Requirement:</td>
    <td><b><%= dtlSpec.getInlineSockDescription().trim() %></b></td>
   </tr>  
<%
   }
   if (!dtlSpec.getCipType().trim().equals(""))
   {
%>   
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("cipType", bldPDS.getCipType(), "N") %></td> 
    <td>CIP Type Information:</td>
    <td><b><%= dtlSpec.getCipTypeDescription().trim() %></b></td>
   </tr>
<%
   }
   if (!dtlSpec.getCutSizeDescription().trim().equals(""))
   {
%>   
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("cutSize", bldPDS.getCutSize(), "N") %></td> 
    <td>Cut Size Information:</td>
    <td><b><%= dtlSpec.getCutSizeDescription().trim() %>
<% 
    if (!dtlSpec.getCutSizeDescription2().trim().equals(""))
       out.println(" and " + dtlSpec.getCutSizeDescription2());
%>     
    </b></td>
   </tr>
<%
   }
   if (!dtlSpec.getScreenSizeDescription().trim().equals(""))
   {
%>   
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("screenSize", bldPDS.getScreenSize(), "N") %></td> 
    <td>Screen Size Information:</td>
    <td><b><%= dtlSpec.getScreenSizeDescription().trim() %></b></td>
   </tr>
<%
   }  
   if (!dtlSpec.getForeignMaterialsDetectionDescription().trim().equals(""))
   {
%>   
   <tr>
    <td class="center"><%= HTMLHelpersInput.inputCheckBox("foreignMaterialDetection", bldPDS.getForeignMaterialDetection(), "N") %></td> 
    <td>Foreign Material Detection:</td>
    <td><b><%= dtlSpec.getForeignMaterialsDetectionDescription().trim() %></b></td>
   </tr>
<%
   }    
%>  


<%-- Test Brix / Reconstitution Ratio --%>
<%  if ((!dtlSpec.getTestBrix().trim().equals("") &&
        !dtlSpec.getTestBrix().trim().equals("0.0")) ||
        !dtlSpec.getReconstitutionRatio().trim().equals("")) { %>

<%  if (!dtlSpec.getTestBrix().trim().equals("") &&
          !dtlSpec.getTestBrix().trim().equals("0.0")) { %>
   <tr>
    <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("testBrix", bldPDS.getTestBrix(), "N") %></td> 
    <td>Test Brix:</td>
    <td><b><%= dtlSpec.getTestBrix() %></b></td>
   </tr>
<% } %>


<%  if (!dtlSpec.getReconstitutionRatio().trim().equals("")) { %>
   <tr>
    <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("reconstitutionRatio", bldPDS.getReconstitutionRatio(), "N") %></td> 
    <td>Reconstitution Ratio:</td>
    <td><b><%= dtlSpec.getReconstitutionRatio() %></b></td>
   </tr>
<%  } %>  

<%  } // end test brix or reconstitution %>



<%  if (!dtlSpec.getRevisionReasonText().trim().equals("")) { %>
   <tr>
    <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("revisionReason", bldPDS.getRevisionReason(), "N") %></td> 
    <td>Reason for Current Revision:</td>
    <td><b><%= dtlSpec.getRevisionReasonText() %></b></td>
   </tr>
<%  } %>  

 
  </table>
 
 
 
<%  if (bldPDS.getListProductDescription() != null &&
       !bldPDS.getListProductDescription().isEmpty()) {   
  	   request.setAttribute("listKeyValues", bldPDS.getListProductDescription());
  	   request.setAttribute("linesFieldName", "linesProductDescription");
%>  
<div style="margin:1em 0 2em 0">
    <h3>Product Description</h3>  

     <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

</div>
<%  } %>



<%  if (bldPDS.getListIngredientStatement() != null &&
       !bldPDS.getListIngredientStatement().isEmpty()) {  
  	   request.setAttribute("listKeyValues", bldPDS.getListIngredientStatement());
  	   request.setAttribute("linesFieldName", "linesIngredientStatement");
%>  
<div style="margin:1em 0 2em 0">
    <h3>Ingredient Statement</h3>  
    
	<jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
	 
</div>
<% } %>




<%  if (bldPDS.getListFinishedPalletAdditional() != null &&
       !bldPDS.getListFinishedPalletAdditional().isEmpty()) {  
       request.setAttribute("listKeyValues", bldPDS.getListFinishedPalletAdditional());
       request.setAttribute("linesFieldName", "linesFinishedPalletAdditional");
%>  
<div style="margin:1em 0 2em 0">
    <h3>Finished Pallet Additional Comments</h3>  
    
     <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
     
</div>
<% } %>



<%-- Analytical Testing --%>
<%  request.setAttribute("screenType", "TEST"); %> 
 	<div>
 	  <jsp:include page="selectProductDataSheetAttributes.jsp"></jsp:include>
	</div>
<%
    if (bldPDS.getListAnalyticalTestComments() != null &&
       !bldPDS.getListAnalyticalTestComments().isEmpty()) {   
  	   request.setAttribute("commentTitle", "Comments");
  	   request.setAttribute("listKeyValues", bldPDS.getListAnalyticalTestComments());
  	   request.setAttribute("linesFieldName", "linesAnalyticalTestComments");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
<% } %>




<%-- Micro Testing --%>
<%   request.setAttribute("screenType", "MICRO"); %> 
 	<div><jsp:include page="selectProductDataSheetAttributes.jsp"></jsp:include></div>
<%
    if (bldPDS.getListMicroTestComments() != null &&
       !bldPDS.getListMicroTestComments().isEmpty()) {   

  	   request.setAttribute("commentTitle", "Comments");
  	   request.setAttribute("listKeyValues", bldPDS.getListMicroTestComments());
  	   request.setAttribute("linesFieldName", "linesMicroTestComments");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
<% } %>




<%-- Process Parameters --%>
<%   request.setAttribute("screenType", "PROC"); %> 
 	<div>
 	  <jsp:include page="selectProductDataSheetAttributes.jsp"></jsp:include>
 	</div>
<%
    if (bldPDS.getListProcessParameterComments() != null &&
       !bldPDS.getListProcessParameterComments().isEmpty()) {   

  	   request.setAttribute("commentTitle", "Comments");
  	   request.setAttribute("listKeyValues", bldPDS.getListProcessParameterComments());
  	   request.setAttribute("linesFieldName", "linesProcessParameterComments");
%>  

    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>



<%-- Additive or Preservative --%>
<%   request.setAttribute("screenType", "ADD");
%> 
 	<div>
 	  <jsp:include page="selectProductDataSheetAttributes.jsp"></jsp:include>
 	</div>
<%
    if (bldPDS.getListAdditivesAndPreservativeComments() != null &&
       !bldPDS.getListAdditivesAndPreservativeComments().isEmpty()) {   
  	   request.setAttribute("commentTitle", "Comments");
  	   request.setAttribute("listKeyValues", bldPDS.getListAdditivesAndPreservativeComments());
  	   request.setAttribute("linesFieldName", "linesAdditivesAndPreservativeComments");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
 <% } %>
 
 
 
 
 
<%-- Fruit Varieties  --%>
<% StringBuffer includedRF = new StringBuffer();
   StringBuffer excludedRF = new StringBuffer();
   
   if (!bldPDS.getDtlBean().getVarietiesIncluded().isEmpty() &&
       bldPDS.getDtlBean().getVarietiesIncluded().size() > 0) {
      includedRF.append("Approved Fruit Varieties " + ((QaFruitVariety) bldPDS.getDtlBean().getVarietiesIncluded().elementAt(0)).getFruitVarietyDescription());
	  if (bldPDS.getDtlBean().getVarietiesIncluded().size() > 1) {
	  	   for (int i = 1; i < bldPDS.getDtlBean().getVarietiesIncluded().size(); i++)  {
    	      includedRF.append(", " + ((QaFruitVariety) bldPDS.getDtlBean().getVarietiesIncluded().elementAt(i)).getFruitVarietyDescription());
   		   }
   	  }	
   }
   
   if (!bldPDS.getDtlBean().getVarietiesExcluded().isEmpty() &&
       bldPDS.getDtlBean().getVarietiesExcluded().size() > 0)  {
      excludedRF.append("Excluded Fruit Varieties " + ((QaFruitVariety) bldPDS.getDtlBean().getVarietiesExcluded().elementAt(0)).getFruitVarietyDescription());
	  if (bldPDS.getDtlBean().getVarietiesExcluded().size() > 1)  {
	  	   for (int i = 1; i < bldPDS.getDtlBean().getVarietiesExcluded().size(); i++) {
    	      excludedRF.append(", " + ((QaFruitVariety) bldPDS.getDtlBean().getVarietiesExcluded().elementAt(i)).getFruitVarietyDescription());
   		   }
   	  }	
   }
%>
   
<% if (!includedRF.toString().trim().equals("") ||
       !excludedRF.toString().trim().equals("")) {  %>

<div style="margin:1em 0 2em 0">

	<h3>Fruit Varieties</h3>
	 
	<table class="styled" style="width:100%">
	 
	<% if (!includedRF.toString().trim().equals("")) {  %>
	  <tr>
	   <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("varietiesIncluded", bldPDS.getVarietiesIncluded(), "N") %></td>
	   <td><b><%= includedRF %></b></td>
	  </tr>
	<% } %>
	
	<%   if (!excludedRF.toString().trim().equals(""))  {  %>
	  <tr>
	   <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("varietiesExcluded", bldPDS.getVarietiesExcluded(), "N") %></td>
	   <td><b><%= excludedRF %></b></td>
	  </tr>
	<% } %>
	  
	</table> 

<% } %>


<%   if (bldPDS.getListFruitVarietiesAdditional() != null &&
       !bldPDS.getListFruitVarietiesAdditional().isEmpty()) {   

  	   request.setAttribute("commentTitle", "Additional Fruit Variety Information");
  	   request.setAttribute("listKeyValues", bldPDS.getListFruitVarietiesAdditional());
  	   request.setAttribute("linesFieldName", "linesFruitVarietiesAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
 
 </div>
<% } %>
 
 
 

<%-- Intended Use  --%>
<% if (bldPDS.getListIntendedUse() != null &&
       !bldPDS.getListIntendedUse().isEmpty()) {   
  	   request.setAttribute("listKeyValues", bldPDS.getListIntendedUse());
       request.setAttribute("linesFieldName", "linesIntendedUse");
%>  
<div style="margin:1em 0 2em 0">

    <h3>Intended Use</h3>
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
    
</div>
<% } %>



<%--  Foreign Matter --%>

<% if (bldPDS.getListForeignMatter() != null &&
   !bldPDS.getListForeignMatter().isEmpty()) {   
  	   request.setAttribute("listKeyValues", bldPDS.getListForeignMatter());
       request.setAttribute("linesFieldName", "linesForeignMatter");
%>  
<div style="margin:1em 0 2em 0">

    <h3>Foreign Matter</h3>
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

</div>
<% } %>



<%--  Shelf Life --%>
<% if ((dtlSpec.getShelfLifeNotValid().trim().equals("") &&
       !dtlSpec.getShelfLife().trim().equals("")) 
       || 
       (bldPDS.getListShelfLifeRequirements() != null &&
       !bldPDS.getListShelfLifeRequirements().isEmpty())) {   %>
<div style="margin:1em 0 2em 0">
    <h3>Shelf Life</h3>


<% if (dtlSpec.getShelfLifeNotValid().trim().equals("") &&
       !dtlSpec.getShelfLife().trim().equals("")) {   %>

	<table class="styled" style="width:100%">
		<tr>
			<td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("shelfLife", bldPDS.getShelfLife(), "N") %></td>
			<td style="width:20%">Shelf Life:</td>
			<td><b><%= dtlSpec.getShelfLife().trim() %></b></td>
		</tr>
	</table> 


<% } %>

<% if (bldPDS.getListShelfLifeRequirements() != null &&
       !bldPDS.getListShelfLifeRequirements().isEmpty()) {   

  	   request.setAttribute("commentTitle", "Shelf Life Requirements");
  	   request.setAttribute("listKeyValues", bldPDS.getListShelfLifeRequirements());
       request.setAttribute("linesFieldName", "linesShelfLifeRequirements");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
<% } %>
</div>

<%  } %>



<%--  M3 Storage Recommendation --%>
<% if (!dtlSpec.getM3StorageRecommendation().trim().equals("") ||
       !dtlSpec.getStorageRecommendation().trim().equals("")) {    %>
<div style="margin:1em 0 2em 0">
    <h3>Storage Recommendation</h3>
 <table class="styled" style="width:100%">
  <tr>
   <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("storageRecommendation", bldPDS.getStorageRecommendation(), "N") %></td>
   <td style="width:20%">Storage Recommendation:</td>
   <td><b>

<% if (!dtlSpec.getM3StorageRecommendation().trim().equals(""))
      out.println(dtlSpec.getM3StorageRecommendation().trim());
    else
      out.println(dtlSpec.getStorageRecommendationDescription().trim());
%>      
   </b></td>
  </tr>
 </table> 

</div>
<% } %>



<%--  Storage Requirements --%>
<% if (bldPDS.getListStorageRequirements() != null &&
       !bldPDS.getListStorageRequirements().isEmpty())  {   
       request.setAttribute("screenType", "detail");
  	   request.setAttribute("longFieldType", "comment14");
  	   request.setAttribute("commentTitle", "Storage Requirements");
  	   request.setAttribute("listKeyValues", bldPDS.getListStorageRequirements());
       request.setAttribute("linesFieldName", "linesStorageRequirements");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
<% } %>



<%-- Container Information --%>
<%  if (!dtlSpec.getContainerCodeLocation().trim().equals("") 
    || !dtlSpec.getContainerCodeFontSize().trim().equals("")
    || !bldPDS.getListContainerPrintByLine().isEmpty()
    || !bldPDS.getListContainerPrintAdditional().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>Container Code Requirements</h3>
  
<% if (!dtlSpec.getContainerCodeLocation().trim().equals("") 
    || !dtlSpec.getContainerCodeFontSize().trim().equals("") ) { %>  
  
  <table class="styled" style="width:100%">
  
  <%    if (!dtlSpec.getContainerCodeLocation().trim().equals("") ) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("containerCodeLocation", bldPDS.getContainerCodeLocation(), "N") %>
      </td>
      <td>
        Location:
      </td>
      <td>
        <b><%=dtlSpec.getContainerCodeLocationDescription() %></b>
      </td>
    </tr>
  <%  } %>
  
  <%    if (!dtlSpec.getContainerCodeFontSize().trim().equals("") ) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("containerCodeFontSize", bldPDS.getContainerCodeFontSize(), "N") %>
      </td>
      <td style="width:10%; min-width:130px">
        Font Size:
      </td>
      <td>
        <b><%=dtlSpec.getContainerCodeFontSize() %></b>
      </td>
    </tr>
  <%  } %>
    
  </table> 
  
<%  } %>  


<%--  Container Print By Line --%>
<% if (bldPDS.getListContainerPrintByLine() != null &&
       !bldPDS.getListContainerPrintByLine().isEmpty())  {   
       request.setAttribute("commentTitle", "Container Print Lines");
       request.setAttribute("listKeyValues", bldPDS.getListContainerPrintByLine());
       request.setAttribute("linesFieldName", "linesContainerPrintByLine");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
<% } %>

<%--  Additional Container Print Instructions --%>
<% if (bldPDS.getListContainerPrintAdditional() != null &&
       !bldPDS.getListContainerPrintAdditional().isEmpty())  {   
       request.setAttribute("commentTitle", "Additional Container Print Instructions");
       request.setAttribute("listKeyValues", bldPDS.getListContainerPrintAdditional());
       request.setAttribute("linesFieldName", "linesContainerPrintAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>
<% } %>

</div>

<%  }// end container information %>






<%-- Carton Information --%>
<%  if (!dtlSpec.getCartonCodeLocation().trim().equals("") 
    || !dtlSpec.getCartonCodeFontSize().trim().equals("")
    || !bldPDS.getListCartonPrintByLine().isEmpty()
    || !bldPDS.getListCartonPrintAdditional().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>Carton Code Requirements</h3>
  
<% if (!dtlSpec.getCartonCodeLocation().trim().equals("") 
    || !dtlSpec.getCartonCodeFontSize().trim().equals("") ) { %>  
  
  <table class="styled" style="width:100%">
  
  <%    if (!dtlSpec.getCartonCodeLocation().trim().equals("") ) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("cartonCodeLocation", bldPDS.getCartonCodeLocation(), "N") %>
      </td>
      <td>
        Location:
      </td>
      <td>
        <b><%=dtlSpec.getCartonCodeLocationDescription() %></b>
      </td>
    </tr>
  <%  } %>
  
  <%    if (!dtlSpec.getCartonCodeFontSize().trim().equals("") ) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("cartonCodeFontSize", bldPDS.getCartonCodeFontSize(), "N") %>
      </td>
      <td style="width:10%; min-width:130px">
        Font Size:
      </td>
      <td>
        <b><%=dtlSpec.getCartonCodeFontSize() %></b>
      </td>
    </tr>
  <%  } %>
    
  </table> 
  
<%  } %>  


<%--  Carton Print By Line --%>
<% if (bldPDS.getListCartonPrintByLine() != null &&
       !bldPDS.getListCartonPrintByLine().isEmpty())  {   
       request.setAttribute("commentTitle", "Carton Print Lines");
       request.setAttribute("listKeyValues", bldPDS.getListCartonPrintByLine());
       request.setAttribute("linesFieldName", "linesCartonPrintByLine");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

<%--  Additional Carton Print Instructions --%>
<% if (bldPDS.getListCartonPrintAdditional() != null &&
       !bldPDS.getListCartonPrintAdditional().isEmpty())  {   
       request.setAttribute("commentTitle", "Additional Carton Print Instructions");
       request.setAttribute("listKeyValues", bldPDS.getListCartonPrintAdditional());
       request.setAttribute("linesFieldName", "linesCartonPrintAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

</div>

<%  }// end carton information %>







<%-- Case Information --%>
<%  if (!dtlSpec.getUnitShowBarCode().trim().equals("") 
    || !dtlSpec.getUnitCodeFontSize().trim().equals("")
    || !bldPDS.getListCasePrintByLine().isEmpty()
    || !bldPDS.getListCasePrintAdditional().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>Case Code Requirements</h3>
  
<% if (!dtlSpec.getUnitShowBarCode().trim().equals("") 
    || !dtlSpec.getUnitCodeFontSize().trim().equals("") ) { %>  
  
  <table class="styled" style="width:100%">
  <%  if (!dtlSpec.getUnitShowBarCode().equals("")) { 
          String display = "No";
          if (dtlSpec.getUnitShowBarCode().equals("Y")) {
              display = "Yes";
          }
    %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("caseShowBarCode", bldPDS.getCaseShowBarCode(), "N") %>
      </td>
      <td>
        Display Barcode on Case:
      </td>
      <td>
        <b><%=display %></b>
      </td>
    </tr>
   <%  } %>
   
   <%  if (!dtlSpec.getUnitCodeFontSize().equals("")) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("caseCodeFontSize", bldPDS.getCaseCodeFontSize(), "N") %>
      </td>
      <td style="width:10%; min-width:130px">
        Font Size:
      </td>
      <td>
        <b><%=dtlSpec.getUnitCodeFontSize() %></b>
      </td>
    </tr>
    <%  } %>
  </table> 
  
<%  } %>  


<%--  Case Print By Line --%>
<% if (bldPDS.getListCasePrintByLine() != null &&
       !bldPDS.getListCasePrintByLine().isEmpty())  {   
       request.setAttribute("commentTitle", "Case Print Lines");
       request.setAttribute("listKeyValues", bldPDS.getListCasePrintByLine());
       request.setAttribute("linesFieldName", "linesCasePrintByLine");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

<%--  Additional Case Print Instructions --%>
<% if (bldPDS.getListCasePrintAdditional() != null &&
       !bldPDS.getListCasePrintAdditional().isEmpty())  {   
       request.setAttribute("commentTitle", "Additional Case Print Instructions");
       request.setAttribute("listKeyValues", bldPDS.getListCasePrintAdditional());
       request.setAttribute("linesFieldName", "linesCasePrintAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

</div>

<%  } // end case information %>







<%--  Label Info --%>
<% if (!dtlSpec.getKosherSymbolRequired().equals("") &&
       !bldPDS.getListLabelPrintByLine().isEmpty() &&
       !bldPDS.getListLabelPrintAdditional().isEmpty())  {   
%>
<div style="margin:1em 0 2em 0">
  <h3>Identification Label Requirements - Industrial Item Printed ID Label</h3>

  <%  if (!dtlSpec.getKosherSymbolRequired().trim().equals("")) { %>
  <table class="styled" style="width:100%">
    <tr>
      <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox("kosherSymbolRequired", bldPDS.getKosherSymbolRequired(), "N") %></td>
      <td style="margin:0;padding:0">
        Check if the Kosher Symbol is required to display on the label
      </td>
    </tr>
  </table> 
  <%  } %>
    
<%--  Label Print By Line --%>
<% if (bldPDS.getListLabelPrintByLine() != null &&
       !bldPDS.getListLabelPrintByLine().isEmpty())  {   
       request.setAttribute("commentTitle", "Label Print Lines");
       request.setAttribute("listKeyValues", bldPDS.getListLabelPrintByLine());
       request.setAttribute("linesFieldName", "linesLabelPrintByLine");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

<%--  Additional Label Print Instructions --%>
<% if (bldPDS.getListLabelPrintAdditional() != null &&
       !bldPDS.getListLabelPrintAdditional().isEmpty())  {   
       request.setAttribute("commentTitle", "Additional Label Print Instructions");
       request.setAttribute("listKeyValues", bldPDS.getListLabelPrintAdditional());
       request.setAttribute("linesFieldName", "linesLabelPrintAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>


</div>

<%  }  // end label info %>







<%-- Pallet Information --%>
<%  if (!dtlSpec.getPalletLabelType().trim().equals("") 
    || !dtlSpec.getPalletLabelLocation().trim().equals("")
    || !bldPDS.getListPalletPrintByLine().isEmpty()
    || !bldPDS.getListPalletPrintAdditional().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>Pallet Placard Requirements</h3>
  
<% if (!dtlSpec.getPalletLabelType().trim().equals("") 
    || !dtlSpec.getPalletLabelLocation().trim().equals("") ) { %>  
  
  <table class="styled" style="width:100%">
  <%  if (!dtlSpec.getPalletLabelType().equals("")) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("palletLabelType", bldPDS.getPalletLabelType(), "N") %>
      </td>
      <td>
        Placard Type:
      </td>
      <td>
        <b><%=dtlSpec.getPalletLabelTypeDescription() %></b>
      </td>
    </tr>
   <%  } %>
   
   <%  if (!dtlSpec.getPalletLabelLocation().equals("")) { %>
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("palletLabelLocation", bldPDS.getPalletLabelLocation(), "N") %>
      </td>
      <td style="width:10%; min-width:130px">
        Location:
      </td>
      <td>
        <b><%=dtlSpec.getPalletLabelLocationDescription() %></b>
      </td>
    </tr>
    <%  } %>
  </table> 
  
<%  } %>  


<%--  Pallet Print By Line --%>
<% if (bldPDS.getListPalletPrintByLine() != null &&
       !bldPDS.getListPalletPrintByLine().isEmpty())  {   
       request.setAttribute("commentTitle", "Pallet Print Lines");
       request.setAttribute("listKeyValues", bldPDS.getListPalletPrintByLine());
       request.setAttribute("linesFieldName", "linesPalletPrintByLine");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

<%--  Additional Pallet Print Instructions --%>
<% if (bldPDS.getListPalletPrintAdditional() != null &&
       !bldPDS.getListPalletPrintAdditional().isEmpty())  {   
       request.setAttribute("commentTitle", "Additional Pallet Print Instructions");
       request.setAttribute("listKeyValues", bldPDS.getListPalletPrintAdditional());
       request.setAttribute("linesFieldName", "linesPalletPrintAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

</div>

<%  }// end pallet information %>




<%-- Additional Coding Requirements --%>
<%  if (!bldPDS.getListShippingRequirements().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>Additional Coding Requirements</h3>

<% if (bldPDS.getListShippingRequirements() != null &&
       !bldPDS.getListShippingRequirements().isEmpty())  {   
       request.setAttribute("listKeyValues", bldPDS.getListCodingRequirementsAdditional());
       request.setAttribute("linesFieldName", "linesCodingRequirementsAdditional");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

</div>

<%  }// end Coding Requirements Additional information %>





<%-- Shipping Requirements --%>
<%  if (!bldPDS.getListShippingRequirements().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>Shipping Requirements</h3>

<% if (bldPDS.getListShippingRequirements() != null &&
       !bldPDS.getListShippingRequirements().isEmpty())  {   
       request.setAttribute("listKeyValues", bldPDS.getListShippingRequirements());
       request.setAttribute("linesFieldName", "linesShippingRequirements");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>


<% } %>

</div>

<%  }// end shipping information %>




<%-- COA Requirements --%>
<%  if (!bldPDS.getListCOARequirements().isEmpty()) { %>

<div style="margin:1em 0 2em 0">
  <h3>COA Requirements</h3>

<% if (bldPDS.getListCOARequirements() != null &&
       !bldPDS.getListCOARequirements().isEmpty())  {   
       request.setAttribute("listKeyValues", bldPDS.getListCOARequirements());
       request.setAttribute("linesFieldName", "linesCOARequirements");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

</div>

<%  }// end COA information %>



<%-- Pallet --%>
<%  if (!dtlSpec.getPalletRequirement().equals("")) { %>

<div style="margin:1em 0 2em 0">
  <h3>Pallet</h3>
  
  <table class="styled" style="width:100%">
    <tr>
      <td class="center" style="width:5%">
        <%= HTMLHelpersInput.inputCheckBox("palletRequirement", bldPDS.getPalletRequirement(), "N") %>
      </td>
      <td>
        Pallet Requirement: <b><%=dtlSpec.getPalletRequirementDescription() %> </b>
      </td>
    </tr>
  </table> 
  
</div>

<%  } // end pallet%>


<%-- Statements --%>

<div style="margin:1em 0 2em 0">
  <h3>Statements</h3>
  
  <% if (bldPDS.getListCOARequirements() != null &&
       !bldPDS.getListCOARequirements().isEmpty())  {   
       request.setAttribute("listKeyValues", bldPDS.getListStatements());
       request.setAttribute("linesFieldName", "linesStatements");
%>  
    <jsp:include page="selectProductDataSheetComments.jsp"></jsp:include>

<% } %>

  </table> 
  
</div>


</form>
 
<%--
 <script>
 $('input[type=checkbox]').attr('checked',true);
 </script>
 --%>

 <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>