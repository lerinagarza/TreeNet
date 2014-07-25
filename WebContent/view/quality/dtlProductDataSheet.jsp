<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->

    <!--[if !HTML5]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <![endif]-->
    <meta charset=utf-8>
    <meta name="viewport" content="width=device-width">
    <link href="/web/Include/css/style-compiled.css" rel="stylesheet">
<%@page import="
    com.treetop.app.quality.BuildProductDataSheet,
    com.treetop.app.quality.BuildProductDataSheetAttributes,
    com.treetop.app.quality.GeneralQuality,
    com.treetop.businessobjects.QaSpecificationPackaging,
    com.treetop.businessobjects.QaSpecification,
    com.treetop.businessobjects.QaTestParameters,
    com.treetop.businessobjects.QaFruitVariety,
    com.treetop.businessobjects.KeyValue,
    com.treetop.utilities.html.HTMLHelpersInput,
    java.util.Vector,
    java.util.Arrays
   "%>
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
  String updTitle = "Product Data Sheet";  
 // Bring in the Detail View Bean.
 BuildProductDataSheet bldPDS = new BuildProductDataSheet();
 QaSpecificationPackaging dtlSpec = new QaSpecificationPackaging();
 try {
    bldPDS = (BuildProductDataSheet) request.getAttribute("bldViewBean");
    dtlSpec = bldPDS.getDtlBean().getSpecPackaging();
    
    updTitle = updTitle + ": " + dtlSpec.getSpecificationName();

    
    request.setAttribute("appType", "spec");
         
 } catch(Exception e) {
    System.out.println("Actually show error - within the selectProductDataSheet.jsp");
 } 
%>   
<style>

* {
    font-family: Calibri, sans-serif;
}

h2 {
    margin-top:1em;
}
</style>

	<title><%=updTitle %></title>


</head>
<body>

<div class="no-print" style="padding:1em 2em; background-color:#dedede">
        <button onclick="javascript:history.go(-1)">Go back and edit</button>
        <button onclick="javascript:window.print()">Looks great!  Print it.</button>
</div>


<div class="container" style="max-width:8.5in; width:80%; min-width:400px; margin:1em auto;">

<div style="margin-bottom:2em;">
    <img src="/web/Include/images/TT_Logo_2C_300dpi.png" alt="Tree Top, Inc." style="width:169px;">
</div>

<div>

    
    <h1 style="float:left;">
	    Product Data Sheet
	</h1>
	
	<div style="float:right;">
	   <table>
	   <%  if (!bldPDS.getShowRevisionDate().equals("")) { %>
	       <tr>
	           <td>Revision Date</td>
	           <td><%=GeneralQuality.formatDateForScreen(dtlSpec.getRevisionDate()) %></td>
	       </tr>
	   <%  } %>
	   <%  if (!bldPDS.getShowSpecNumber().equals("")) { %>
	       <tr>
               <td>Specification Number</td>
               <td><%=dtlSpec.getSpecificationName() %></td>
           </tr>
       <%  } %>
	   </table>
	</div>

</div>

<div class="clearfix"></div>


<%  if (bldPDS.showSection("DEF")) { %>
<div style="page-break-inside:avoid">
	<h2>Product Definition</h2>
	
	<table cellpadding="0" cellspacing="0">
	<%  if (!bldPDS.getSpecType().equals("")) { %>
	<tr>
	    <td>Specification Type</td>
	    <td><%=dtlSpec.getTypeDescription() %></td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getSpecTitle().equals("")) { %>
	<tr>
	    <td>Specification Title</td>
	    <td><%= dtlSpec.getSpecificationName() %> <%=dtlSpec.getSpecificationDescription() %></td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getKosherSymbol().equals("")) { %>
	<tr>
	    <td>Kosher Information</td>
	    <td>
	        <%= dtlSpec.getKosherStatusDescription() %>
	        <img src="<%= dtlSpec.getKosherSymbol() %>" style="height:16px; margin-top:-4px; padding:left:1em"/>
	    </td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getCountryOfOrigin().equals("")) { %>
	<tr>
	    <td>Country of Origin</td>
	    <td><%= dtlSpec.getCountryOfOrigin() %></td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getInlineSock().equals("")) { %>
	<tr>
	    <td>Inline Filter Requirement</td>
	    <td><%= dtlSpec.getInlineSockDescription() %></td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getCipType().equals("")) { %>
	<tr>
	    <td>CIP Type Information</td>
	    <td><%= dtlSpec.getCipTypeDescription() %></td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getCutSize().equals("")) { %>
	<tr>
	    <td>Cut Size Information</td>
	    <td>
	        <%= dtlSpec.getCutSizeDescription() %>
	        <%  if (!dtlSpec.getCutSizeDescription2().equals("")) { %>
	        and <%= dtlSpec.getCutSizeDescription2() %>
	        <%  } %>
	    </td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getScreenSize().equals("")) { %>
	<tr>
	    <td>Screen Size Information</td>
	    <td><%= dtlSpec.getScreenSizeDescription() %></td>
	</tr>
	<%  } %>
	
	<%  if (!bldPDS.getForeignMaterialDetection().equals("")) { %>
	<tr>
	    <td>Foreign Material Detection</td>
	    <td><%= dtlSpec.getForeignMaterialsDetectionDescription() %></td>
	</tr>
	<%  } %>
	
    <%  if (!bldPDS.getTestBrix().equals("")) { %>
    <tr>
        <td>Test Brix</td>
        <td><%= dtlSpec.getTestBrix() %></td>
    </tr>
    <%  } %>
    
    
    <%  if (!bldPDS.getReconstitutionRatio().equals("")) { %>
    <tr>
        <td>Reconstitution Ratio</td>
        <td><%= dtlSpec.getReconstitutionRatio() %></td>
    </tr>
    <%  } %>
	
</table>
</div>	
<%  }  //end product definition %>	
	

<%  if (bldPDS.showSection("PRODDESC")) { %>
<div style="page-break-inside:avoid">
	<h3 style="margin-top:1em;">Product Description</h3>
    <%  request.setAttribute("specAttributeComments", bldPDS.getListProductDescription()); %>
    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesProductDescription()); %>
    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>


<%  if (bldPDS.showSection("INGSTMT")) { %>
<div style="page-break-inside:avoid">
	<h3 style="margin-top:1em;">Ingredient Statement</h3>
	<%  request.setAttribute("specAttributeComments", bldPDS.getListIngredientStatement()); %>
    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesIngredientStatement()); %>
    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>


<%  if (bldPDS.showSection("FINPALLET")) { %>
<div style="page-break-inside:avoid">
	<h3 style="margin-top:1em;">Finished Pallet Additional Comments</h3>
	<%  request.setAttribute("specAttributeComments", bldPDS.getListFinishedPalletAdditional()); %>
    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesFinishedPalletAdditional()); %>
    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>






<%  if (bldPDS.showSection("TEST")) { %>
<div style="page-break-inside:avoid">
	<h2>Analytical Testing</h2>
	
	<%  request.setAttribute("listSpecAttribute",bldPDS.getDtlBean().getSpecAnalyticalTests());
	    request.setAttribute("listSpecAttributeSel",bldPDS.getListAnalyticalTests());%>
	<jsp:include page="dtlProductDataSheetAttribute.jsp"></jsp:include>
	
	<%  request.setAttribute("specAttributeComments", bldPDS.getListAnalyticalTestComments()); %>
	<%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesAnalyticalTestComments()); %>
	<jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("MICRO")) { %>
<div style="page-break-inside:avoid">
	<h2>Micro Testing</h2>
	
	<%  request.setAttribute("listSpecAttribute",bldPDS.getDtlBean().getSpecMicroTests());
	    request.setAttribute("listSpecAttributeSel",bldPDS.getListMicroTests());%>
	<jsp:include page="dtlProductDataSheetAttribute.jsp"></jsp:include>
	
	<%  request.setAttribute("specAttributeComments", bldPDS.getListMicroTestComments()); %>
	<%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesMicroTestComments()); %>
	<jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("PROC")) { %>
<div style="page-break-inside:avoid">
	<h2>Process Parameters</h2>
	<%  request.setAttribute("listSpecAttribute",bldPDS.getDtlBean().getSpecProcessParameters());
	    request.setAttribute("listSpecAttributeSel",bldPDS.getListProcessParameters());%>
	<jsp:include page="dtlProductDataSheetAttribute.jsp"></jsp:include>
	
	<%  request.setAttribute("specAttributeComments", bldPDS.getListProcessParameterComments()); %>
	<%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesProcessParameterComments()); %>
	<jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("ADD")) { %>
<div style="page-break-inside:avoid">
	<h2>Additive or Preservative</h2>
	
	<%  request.setAttribute("listSpecAttribute",bldPDS.getDtlBean().getSpecAdditiveAndPreserve());
	    request.setAttribute("listSpecAttributeSel",bldPDS.getListAdditivesAndPreservatives());%>
	<jsp:include page="dtlProductDataSheetAttribute.jsp"></jsp:include>
	
	<%  request.setAttribute("specAttributeComments", bldPDS.getListAdditivesAndPreservativeComments()); %>
    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesAdditivesAndPreservativeComments()); %>
    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>


<%  if (bldPDS.showSection("VAR")) { %>
<div style="page-break-inside:avoid">
	<h2>Fruit Varieties</h2>
	
	<%  if (!bldPDS.getVarietiesIncluded().equals("")) { %>
	    <b>Approved</b>    
	    <div style="margin-left:2em;">
	    <%  Vector includedVarities = bldPDS.getDtlBean().getVarietiesIncluded();
	        StringBuffer includedString = new StringBuffer();
	        for (int i=0; i<includedVarities.size(); i++) {
	            QaFruitVariety variety = (QaFruitVariety) includedVarities.elementAt(i); 
	            if (i > 0) { 
	                includedString.append(", ");
	            }
	            includedString.append(variety.getFruitVarietyDescription());
	     %>
	     <%=includedString %>
	     <% } %>
	     </div>
	<%  } %>
	
	<%  if (!bldPDS.getVarietiesExcluded().equals("")) { %>
	    <b>Excluded</b> 
	    <div style="margin-left:2em;">
	    <%  Vector excludedVarities = bldPDS.getDtlBean().getVarietiesExcluded();
	        StringBuffer excludedString = new StringBuffer();
	        for (int i=0; i<excludedVarities.size(); i++) {
	            QaFruitVariety variety = (QaFruitVariety) excludedVarities.elementAt(i); 
	            if (i > 0) { 
	                excludedString.append(", ");
	            }
	            excludedString.append(variety.getFruitVarietyDescription());
	     %>
	     <%=excludedString %>
	     <% } %>   
	     </div>
	<%  } %>
	   

	<%  request.setAttribute("specAttributeComments", bldPDS.getListFruitVarietiesAdditional()); %>
	<%  request.setAttribute("specAttributeCommentsTitle", "Additional Comments"); %>
	<%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesFruitVarietiesAdditional()); %>
	<jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("USE")) { %>
<div style="page-break-inside:avoid">
	<h2>Intended Use</h2>
	<%  request.setAttribute("specAttributeComments", bldPDS.getListIntendedUse()); %>
	<%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesIntendedUse()); %>
	<jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>



<%  if (bldPDS.showSection("FOREIGN")) { %>
<div style="page-break-inside:avoid">
    <h2>Foreign Matter</h2>
    <%  request.setAttribute("specAttributeComments", bldPDS.getListForeignMatter()); %>
    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesForeignMatter()); %>
    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>



<%  if (bldPDS.showSection("SHELF")) { %>
<div style="page-break-inside:avoid">
    <h2>Shelf Life Requirements</h2>
    
    <%  if (!bldPDS.getShelfLife().equals("")) { %>
        <div>Shelf Life: <b><%=dtlSpec.getShelfLife() %></b></div>
    <%  } %>
    
	    <%  request.setAttribute("specAttributeComments", bldPDS.getListShelfLifeRequirements()); %>
	    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesShelfLifeRequirements()); %>
	    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>



<%  if (bldPDS.showSection("STORAGE")) { %>
<div style="page-break-inside:avoid">
	<h2>Storage Recommendation</h2>
	<%  if (!bldPDS.getStorageRecommendation().equals("")) { %>
	    <div>
	        <%  if (!dtlSpec.getM3StorageRecommendation().equals("")) { %>
	            <%=dtlSpec.getM3StorageRecommendation() %>
	        <%  } else { %>
	            <%=dtlSpec.getStorageRecommendationDescription() %>
	        <%  } %>
	    </div>
	<%  } %>

	    <%  request.setAttribute("specAttributeComments", bldPDS.getListStorageRequirements()); %>
	    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesStorageRequirements()); %>
	    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
</div>
<%  } %>



<%  if (bldPDS.showSection("CONTAINER")) { %>
<div style="page-break-inside:avoid">
    <h2>Container Code Requirements</h2>
<% if (!bldPDS.getContainerCodeLocation().trim().equals("") 
    || !bldPDS.getContainerCodeFontSize().trim().equals("") ) { %>  
        <div>
            Location: <b><%=dtlSpec.getContainerCodeLocationDescription() %></b><br>
            Font Size: <b><%=dtlSpec.getContainerCodeFontSize() %></b>
        </div>
    <%  } %>
    
    

        <%  request.setAttribute("specAttributeComments", bldPDS.getListContainerPrintByLine()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Print Lines"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesContainerPrintByLine()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

        <%  request.setAttribute("specAttributeComments", bldPDS.getListContainerPrintAdditional()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Additional Print Instructions"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesContainerPrintAdditional()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>



<%  if (bldPDS.showSection("CASE")) { %>
 <div style="page-break-inside:avoid">   
    <h2>Case Code Requirements</h2>
<% if (!bldPDS.getCaseShowBarCode().trim().equals("") 
    || !bldPDS.getCaseCodeFontSize().trim().equals("") ) { %>  
        <div>
            Show Bar Code on Case: <b><%=dtlSpec.getUnitShowBarCode().equals("Y") ? "Yes" : "No" %></b><br>
            Font Size: <b><%=dtlSpec.getUnitCodeFontSize() %></b>
        </div>
    <%  } %>
    
    

        <%  request.setAttribute("specAttributeComments", bldPDS.getListCasePrintByLine()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Print Lines"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesCasePrintByLine()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

        <%  request.setAttribute("specAttributeComments", bldPDS.getListCasePrintAdditional()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Additional Print Instructions"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesCasePrintAdditional()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>



<%  if (bldPDS.showSection("LABEL")) { %>
<div style="page-break-inside:avoid">    
    <h2>Identification Label Requirements - Industrial Item Printed ID Label</h2>
    <% if (!bldPDS.getKosherSymbolRequired().trim().equals("")) { %>  
        <div>
            Kosher Symbol required on label: <b><%=dtlSpec.getKosherSymbolRequired().equals("Y") ? "Yes" : "No" %></b><br>
        </div>
    <%  } %>
    
    

        <%  request.setAttribute("specAttributeComments", bldPDS.getListLabelPrintByLine()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Print Lines"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesLabelPrintByLine()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

        <%  request.setAttribute("specAttributeComments", bldPDS.getListLabelPrintAdditional()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Additional Print Instructions"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesLabelPrintAdditional()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("PALLET")) { %>
<div style="page-break-inside:avoid"> 
    <h2>Pallet Placard Requirements</h2>
<% if (!bldPDS.getPalletLabelType().trim().equals("") 
    || !bldPDS.getPalletLabelLocation().trim().equals("") ) { %>  
        <div>
            Label Type: <b><%=dtlSpec.getPalletLabelTypeDescription() %></b><br>
            Location: <b><%=dtlSpec.getPalletLabelLocationDescription() %></b>
        </div>
    <%  } %>
    
    

        <%  request.setAttribute("specAttributeComments", bldPDS.getListPalletPrintByLine()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Print Lines"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesPalletPrintByLine()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

        <%  request.setAttribute("specAttributeComments", bldPDS.getListPalletPrintAdditional()); %>
        <%  request.setAttribute("specAttributeCommentsTitle", "Additional Print Instructions"); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesPalletPrintAdditional()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("ADDCODING")) { %>
<div style="page-break-inside:avoid">
    <h2>Additional Coding Requirements</h2>
    

        <%  request.setAttribute("specAttributeComments", bldPDS.getListCodingRequirementsAdditional()); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesCodingRequirementsAdditional()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("SHIPPING")) { %>
<div style="page-break-inside:avoid">
    <h2>Shipping Requirements</h2>
    
        <%  request.setAttribute("specAttributeComments", bldPDS.getListShippingRequirements()); %>
        <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesShippingRequirements()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>


<%  if (bldPDS.showSection("COA")) { %>
<div style="page-break-inside:avoid">
    <h2>COA Requirements</h2>

    <%  request.setAttribute("specAttributeComments", bldPDS.getListCOARequirements()); %>
    <%  request.setAttribute("specAttributeCommentsLines", bldPDS.getLinesCOARequirements()); %>
    <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>

</div>
<%  } %>

<%  if (bldPDS.showSection("PALLETREQ")) { %>
<div style="page-break-inside:avoid">
    <h2>Pallet</h2>

    <div>Pallet Requirement: <b><%=dtlSpec.getPalletRequirementDescription() %></b></div>    

</div>
<%  } %>


<%  if (bldPDS.showSection("STATEMENTS")) { %>
<div style="page-break-inside:avoid">
    <h2>Statements</h2>

    <%  for (int i=0; i<bldPDS.getListStatements().size(); i++) { 
        KeyValue kv = (KeyValue) bldPDS.getListStatements().elementAt(i);
        boolean show = false;
        if (Arrays.asList(bldPDS.getLinesStatements()).contains(kv.getUniqueKey())) {
            show = true;
            Vector oneStatment = new Vector();
            oneStatment.addElement(kv);

%>    

        <%  request.setAttribute("specAttributeComments", oneStatment); %>
        <%  request.setAttribute("specAttributeCommentsTitle", kv.getDescription()); %>
        <jsp:include page="dtlProductDataSheetComment.jsp"></jsp:include>
        
    <%  } //end if contains %>
<%  } //end loop %>

</div>
<%  } // end statements %>




</div>

</body>
</html>
