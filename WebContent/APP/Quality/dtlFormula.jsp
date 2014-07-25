<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.QaFormula" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.BigDecimal" %>
<%
//----------------------- dtlFormula.jsp ------------------------------//
//  Author :  Teri Walton  08/17/10
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String dtlTitle = "Detail of";  
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 DtlFormula dtlFormula = new DtlFormula();
 QaFormula dtlHeader = new QaFormula();
 try
 {
	dtlFormula = (DtlFormula) request.getAttribute("dtlViewBean");
	dtlHeader = dtlFormula.getDtlBean().getFormula();
	dtlTitle = dtlTitle + "&#160;" + dtlHeader.getTypeDescription() + "&#160; Formula &#160; " + dtlHeader.getFormulaName() + "&#160;";
 }
 catch(Exception e){}  
  int imageCount  = 1;
  int expandCount = 0;
  request.setAttribute("appType", "formula");
%>
<html>
 <head>
  <title><%= dtlTitle %></title>
  <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y",  10) %>     
  <%= JavascriptInfo.getMoreButton() %>
 </head>
 <body>
  <jsp:include page="dtlHeader.jsp"></jsp:include>
 <table class="table00" cellspacing="0" style="width:100%">
  <tr>
   <td class="td05180102" colspan = "5">&#160;<b><%= dtlHeader.getTypeDescription() %>&#160;Formula</b></td>
  </tr>	  
<%
   if (!dtlFormula.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "5">&#160;&#160;&#160;&#160;&#160;<b><%= dtlFormula.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
   String custCodeAbbr = "";
   if (!dtlHeader.getCustomerNumber().trim().equals("") &&
       !dtlHeader.getCustomerNumber().trim().equals("0"))
       custCodeAbbr = "Customer Data From " + dtlHeader.getCustomerCode().trim() + " system.";
%>  
  <tr>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;</td>      
   <td class="td04140102" style="width:2%" rowspan="2">&#160;</td>
   <td class="td04140102">
<%
   if (!custCodeAbbr.trim().equals(""))
     out.println("<abbr title=\"Customer Number\">Customer Number:</abbr>");
%> 
    &#160;  
   </td>
   <td class="td04140102">
<%
   if (!custCodeAbbr.trim().equals(""))
     out.println("<abbr title=\"" + custCodeAbbr + "\">&#160;" + dtlHeader.getCustomerNumber().trim() + "</abbr>");
%>    
    &#160;
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
   <td class="td04140102">&#160;
<%
   String noTargetBrix = "";
   try{
     if (!dtlHeader.getTargetBrix().equals("0.0"))
     {
       noTargetBrix = "Y";
       out.println("<abbr title=\"Need to Target this brix for the Formulation\">Target Brix:&#160;" + dtlHeader.getTargetBrix() + " </abbr>");
     }
   }catch(Exception e){}
%>      
   </td>
   <td class="td04140102" style="width:30%">&#160;</td>
   <td class="td04140102">
<%
   if (!custNameAbbr.trim().equals(""))
     out.println("<abbr title=\"Customer Name\">Customer Name:</abbr>");
%>   
    &#160;
   </td>
   <td class="td04140102">
<%
   if (!custNameAbbr.trim().equals(""))
     out.println("<abbr title=\"" + custNameAbbr + "\">&#160;" + dtlHeader.getCustomerName().trim() + "</abbr>");
%>      
    &#160;   
   </td>         
  </tr>	 
  
  <tr>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;</td>      
   <td class="td04140102" style="width:2%" rowspan="2">&#160;</td>
   <td class="td04140102">
<%
   if (!dtlHeader.getCustomerOrSupplierItemNumber().trim().equals(""))
     out.println("<abbr title=\"Customer or Supplier Item Number\">Customer or Supplier Item  Number:</abbr>");
%> 
    &#160;  
   </td>
   <td class="td04140102">
<%
   if (!dtlHeader.getCustomerOrSupplierItemNumber().trim().equals(""))
     out.println(dtlHeader.getCustomerOrSupplierItemNumber().trim());
%>    
    &#160;
   </td>      
  </tr>  
  
  
                 
 </table>
<%
 //***********EXPANDING SECTION************************************************************
 //   Pre Blend Batch Information
 if (dtlFormula.getDtlBean().getFormulaPreBlend() != null &&
     !dtlFormula.getDtlBean().getFormulaPreBlend().isEmpty())
 {
   imageCount++;
   expandCount++;
   request.setAttribute("screenType", "preBlend");
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td0412">
    <%= JavascriptInfo.getExpandingSection("O", "Pre-blend Formulation", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlFormulaDetail.jsp"></jsp:include>   
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table> 
<%
 } // end of the Pre Blend Batch Information Section
 //***********EXPANDING SECTION************************************************************
 //   Production Batch Information
 if (dtlFormula.getDtlBean().getFormulaProduction() != null &&
     !dtlFormula.getDtlBean().getFormulaProduction().isEmpty())
 { 
    imageCount++;
  	expandCount++;
  	request.setAttribute("screenType", "production");
%>   
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td0412">
    <%= JavascriptInfo.getExpandingSection("O", "Production Formula", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlFormulaDetail.jsp"></jsp:include>   
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table>  
<%
  } // end of the Production Batch Information Section
 //***********EXPANDING SECTION************************************************************
 //  Formula Details -- Comment Section
 if (dtlFormula.getListDetails() != null &&
     !dtlFormula.getListDetails().isEmpty())
 {   
  	imageCount++;
  	expandCount++;
  	request.setAttribute("screenType", "detail");
  	request.setAttribute("longFieldType", "comment1");
  	request.setAttribute("listKeyValues", dtlFormula.getListDetails());
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Formula Details", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>  
<%
   } // end of the Formula Detail Section
 //***********EXPANDING SECTION************************************************************
 //  Both Include and Exclude Varieties
 if ((dtlFormula.getDtlBean().getVarietiesIncluded() != null &&
     !dtlFormula.getDtlBean().getVarietiesIncluded().isEmpty()) ||
    (dtlFormula.getDtlBean().getVarietiesExcluded() != null &&
     !dtlFormula.getDtlBean().getVarietiesExcluded().isEmpty()))
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
//***********EXPANDING SECTION************************************************************
 //  Raw Fruit Attribute Details
 if (dtlFormula.getDtlBean().getFormulaRawFruitTests() != null &&
     !dtlFormula.getDtlBean().getFormulaRawFruitTests().isEmpty())
 {  
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "");
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405" colspan = "2">
     <%= JavascriptInfo.getExpandingSection("O", "Raw Fruit Details", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="dtlTestParameters.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>
   <tr>
    <td class="td04140102" style="width:20%"><abbr title="Origin of the Fruit for this Formula">Fruit Origin:</abbr></td>
    <td class="td04140102">&#160;<%= dtlHeader.getFruitOrigin().trim() %>&#160;</td>      
   </tr>
   <tr>

  </table>     
<%
  } // end of the Raw Fruit Attribute Detail information
 //***********EXPANDING SECTION************************************************************
 //  Raw Fruit Additional InformationFormula Details -- Comment Section
 if (dtlFormula.getListRFAdditionalInfo() != null &&
     !dtlFormula.getListRFAdditionalInfo().isEmpty())
 {   
  	imageCount++;
  	expandCount++;
  	request.setAttribute("screenType", "detail");
  	request.setAttribute("longFieldType", "comment6");
  	request.setAttribute("listKeyValues", dtlFormula.getListRFAdditionalInfo());
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td03141405" colspan="2">
    <%= JavascriptInfo.getExpandingSection("O", "Additional Information (Raw Fruit)", 0, expandCount, imageCount, 1, 0) %>
    <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>  
  </table>
<%
   } // end of the Formula Detail Section   
 //***********EXPANDING SECTION************************************************************
 //  Cinnamon Pre-Blend for Sauce
 if (dtlFormula.getDtlBean().getFormulaPreBlendSauce() != null &&
     !dtlFormula.getDtlBean().getFormulaPreBlendSauce().isEmpty())
 {  
  imageCount++;
  expandCount++;
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td04121405" colspan="4">
       <%= JavascriptInfo.getExpandingSection("O", "Cinnamon Pre-Blend for Applesauce", 0, expandCount, imageCount, 1, 0) %>
         <jsp:include page="dtlFormulaDetailSaucePreBlend.jsp"></jsp:include>  
       <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table>    
<%
  } // End of the Cinnamon Pre-blend for Sauce Information
//***********EXPANDING SECTION************************************************************
//  Calculation Details -- Comment Section
 if (dtlFormula.getListCalculations() != null &&
     !dtlFormula.getListCalculations().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment2");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", dtlFormula.getListCalculations());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Calculations", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
 } // End of the Calculations section
//***********EXPANDING SECTION************************************************************
//  Blending Instructions -- Comment Section
 if (dtlFormula.getListBlendingInstructions() != null &&
     !dtlFormula.getListBlendingInstructions().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment3");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", dtlFormula.getListBlendingInstructions());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Blending Instructions", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
  } // end of the Blending Instructions Information
//***********EXPANDING SECTION************************************************************
//  Key Label Statements -- Comment Section
 if (dtlFormula.getListKeyLabelStatements() != null &&
     !dtlFormula.getListKeyLabelStatements().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment4");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", dtlFormula.getListKeyLabelStatements());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Key Label Statements", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
  } // end of the Key Label Statements Information
//***********EXPANDING SECTION************************************************************
//  Ingredient Statement -- Comment Section
 if (dtlFormula.getListIngredientStatement() != null &&
     !dtlFormula.getListIngredientStatement().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment5");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", dtlFormula.getListIngredientStatement());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Ingredient Statement", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
  } // end of the Ingredient Statement Information
//***********EXPANDING SECTION************************************************************
//  Product Structure Information
 if (dtlFormula.getDtlBean().getProductStructure() != null &&
     !dtlFormula.getDtlBean().getProductStructure().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("listMaterials", dtlFormula.getDtlBean().getProductStructure());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Product Structure", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Item/listProductStructureMaterialsTable.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
  } // end of the Product Structure
//***********EXPANDING SECTION************************************************************
//  Comments
 if (dtlFormula.getListComments() != null &&
     !dtlFormula.getListComments().isEmpty())
 {   
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "N");
  request.setAttribute("listKeyValues", dtlFormula.getListComments());    
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("O", "Comments", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>
<%
  } // end of the List of Comments
   imageCount++;
  expandCount++;
  // Create Expandable Section for Revision Reasons for this Specification
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection("C", "Revision Reasons", 0, expandCount, imageCount, 1, 0) %>
         <jsp:include page="listFormulaTable.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table>  
  <%= HTMLHelpers.pageFooterTable(dtlFormula.getRequestType()) %>

   </body>
</html>