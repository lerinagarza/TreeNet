<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlSpecificationCoding.jsp -----------------------------------//
//
//    Author :  Teri Walton  12/6/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
 DtlSpecification specCodingInfo = new DtlSpecification();
 QaSpecificationPackaging specCoding = new QaSpecificationPackaging();
 try
 {
    specCodingInfo = (DtlSpecification) request.getAttribute("dtlViewBean");
    specCoding = specCodingInfo.getDtlBean().getSpecPackaging();
 }
 catch(Exception e)
 {
    //System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 	//request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }     
 
%>
<html>
 <head>
 </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%">
 
<%    
     if (!specCodingInfo.getListNutritionPanel().isEmpty())
     {
%>     
    <tr class="tr00">
     <td class="td04140102" style="width:3%">&#160;</td>
     <td class="td04140102" colspan="4">
<%
        KeyValue listLine = (KeyValue) specCodingInfo.getListNutritionPanel().elementAt(0);
        out.println(HTMLHelpersLinks.basicLink("Nutrition Panel", listLine.getValue(), "", "a0518", ""));
%>        
     </td>
    </tr>
<%        
     }
   if (!specCoding.getContainerCodeLocation().trim().equals("") ||
       !specCoding.getContainerCodeFontSize().trim().equals("") ||
       !specCodingInfo.getListContainerPrintByLine().isEmpty() ||
       !specCodingInfo.getListContainerPrintAdditional().isEmpty())
   {
%>  
   <tr class="tr02">
    <td class="td05160102" colspan="5">&#160;&#160;<b>Container Coding</b></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="2" style="width:30%">Font Size:&#160;&#160;&#160;<%= specCoding.getContainerCodeFontSize() %></td>
    <td class="td04140102" colspan="2">Location:&#160;&#160;&#160;
      <abbr title="Location Code Value: <%= specCoding.getContainerCodeLocation() %>" %>&#160;
      <%= specCoding.getContainerCodeLocationDescription() %></abbr>
    </td>
   </tr>
<%
     if (!specCodingInfo.getListContainerPrintByLine().isEmpty())
     {
       for (int contX = 0; contX < specCodingInfo.getListContainerPrintByLine().size(); contX++)
       {
          KeyValue listLine = (KeyValue) specCodingInfo.getListContainerPrintByLine().elementAt(contX);
%>
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" style="width:20%">Container Print Line <%= (contX + 1) %>:</td>
    <td class="td04140102" colspan = "3">&#160;<%= listLine.getValue().trim() %></td>
   </tr> 
<%
       } 
     }
     if (specCodingInfo.getListContainerPrintAdditional() != null &&
        !specCodingInfo.getListContainerPrintAdditional().isEmpty())
     {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment6");
  	   request.setAttribute("listKeyValues", specCodingInfo.getListContainerPrintAdditional());
%>  
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" colspan="4">
         <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    </td>
   </tr>
<%
    }
  } 
  if (!specCoding.getCartonCodeLocation().trim().equals("") ||
      !specCoding.getCartonCodeFontSize().trim().equals("") ||
      !specCodingInfo.getListCartonPrintByLine().isEmpty() ||
      !specCodingInfo.getListCartonPrintAdditional().isEmpty())
   {
%>  
   <tr class="tr02">
    <td class="td05160102" colspan="5">&#160;&#160;<b>Carton Coding</b></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="2" style="width:30%">Font Size:&#160;&#160;&#160;<%= specCoding.getCartonCodeFontSize() %></td>
    <td class="td04140102" colspan="2">Location:&#160;&#160;&#160;
      <abbr title="Location Code Value: <%= specCoding.getCartonCodeLocation() %>" %>&#160;
      <%= specCoding.getCartonCodeLocationDescription() %></abbr>
    </td>
   </tr>
<%
     if (!specCodingInfo.getListCartonPrintByLine().isEmpty())
     {
       for (int contX = 0; contX < specCodingInfo.getListCartonPrintByLine().size(); contX++)
       {
          KeyValue listLine = (KeyValue) specCodingInfo.getListCartonPrintByLine().elementAt(contX);
%>
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" style="width:20%">Carton Print Line <%= (contX + 1) %>:</td>
    <td class="td04140102" colspan = "3">&#160;<%= listLine.getValue().trim() %></td>
   </tr> 
<%
       } 
     }
     if (specCodingInfo.getListCartonPrintAdditional() != null &&
        !specCodingInfo.getListCartonPrintAdditional().isEmpty())
     {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment6");
  	   request.setAttribute("listKeyValues", specCodingInfo.getListCartonPrintAdditional());
%>  
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" colspan="4">
         <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    </td>
   </tr>
<%
    }
  }
  if (!specCoding.getUnitCodeFontSize().trim().equals("") ||
      !specCoding.getUnitShowBarCode().trim().equals("") ||
      !specCodingInfo.getListCasePrintByLine().isEmpty() ||
      !specCodingInfo.getListCasePrintAdditional().isEmpty())
   {
%>  
   <tr class="tr02">
    <td class="td05160102" colspan="5">&#160;&#160;<b>Case Coding</b></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="2" style="width:30%">Font Size:&#160;&#160;&#160;<%= specCoding.getUnitCodeFontSize() %></td>
    <td class="td04140102" colspan="2">&#160;
<%
   if (!specCoding.getUnitShowBarCode().trim().equals(""))
   {
     out.println("Display Bar Code on the Case ");
     if (!specCodingInfo.getShowCaseGTIN().trim().equals(""))
       out.println("<b>: " + HTMLHelpersMasking.maskGTINNumber(specCodingInfo.getShowCaseGTIN()) + "</b>");
   }
%>    
    </td>
   </tr>
<%
     if (!specCodingInfo.getListCasePrintByLine().isEmpty())
     {
       for (int caseX = 0; caseX < specCodingInfo.getListCasePrintByLine().size(); caseX++)
       {
          KeyValue listLine = (KeyValue) specCodingInfo.getListCasePrintByLine().elementAt(caseX);
%>
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" style="width:20%">Print Line <%= (caseX + 1) %>:</td>
    <td class="td04140102" colspan = "3">&#160;<%= listLine.getValue().trim() %></td>
   </tr> 
<%
       } 
     }
   } 
   // Should you be allowed to choose a date to display best by and the julian date?
   if ((!specCoding.getContainerCodeLocation().trim().equals("") || // Container Coding
       !specCoding.getContainerCodeFontSize().trim().equals("") ||
       !specCodingInfo.getListContainerPrintByLine().isEmpty() ||
       !specCodingInfo.getListContainerPrintAdditional().isEmpty() ||
       !specCoding.getCartonCodeLocation().trim().equals("") || // Carton Coding
       !specCoding.getCartonCodeFontSize().trim().equals("") ||
       !specCodingInfo.getListCartonPrintByLine().isEmpty() ||
       !specCodingInfo.getListCartonPrintAdditional().isEmpty() ||
       !specCoding.getUnitCodeFontSize().trim().equals("") || // Case Coding
       !specCoding.getUnitShowBarCode().trim().equals("") ||
       !specCodingInfo.getListCasePrintByLine().isEmpty() ||
       !specCodingInfo.getListCasePrintAdditional().isEmpty() || 
       !specCoding.getKosherSymbolRequired().trim().equals("") ||
       !specCodingInfo.getListLabelPrintByLine().isEmpty() ||
       !specCodingInfo.getListLabelPrintAdditional().isEmpty()) &&
      (!specCodingInfo.getBestByValue().trim().equals("") ||
       !specCodingInfo.getJulianDate().trim().equals(""))  )
      {   
%>
  <form name="changeDate" action="/web/CtlQuality?requestType=dtlSpec" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("specNumber", specCodingInfo.getSpecNumber()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionDate", specCodingInfo.getRevisionDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionTime", specCodingInfo.getRevisionTime()) %>  
  <%= HTMLHelpersInput.inputBoxHidden("environment", specCodingInfo.getEnvironment()) %>
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" colspan = "2">&#160;
     <%= HTMLHelpersInput.inputBoxDate("chosenDate", specCodingInfo.getChosenDate(), "getChosenDate", "N", "N") %>
     &#160;&#160;
     <%= HTMLHelpers.buttonSubmit("", "Choose New Date") %>
    </td>
    <td class="td04140102" colspan = "2">&#160;
<%
   if (!specCodingInfo.getBestByValue().trim().equals(""))
      out.println("Best By Code: <b>" + specCodingInfo.getBestByValue() + "</b>");
   if (!specCodingInfo.getJulianDate().trim().equals(""))
      out.println("Julian Date: <b>" + specCodingInfo.getJulianDate() + "</b>");
%>     
    </td>
   </tr>     
  </form>     
  <%= JavascriptInfo.getCalendarFoot("changeDate", "getChosenDate", "chosenDate") %>  
<%    
   }
     if (specCodingInfo.getListCasePrintAdditional() != null &&
        !specCodingInfo.getListCasePrintAdditional().isEmpty())
     {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment8");
  	   request.setAttribute("listKeyValues", specCodingInfo.getListCasePrintAdditional());
%>  
   <tr class="tr00">
    <td class="td04140102">&#160;</td>
    <td class="td04140102" colspan="4">
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    </td>
   </tr>
<%
    } 
   if (!specCoding.getKosherSymbolRequired().trim().equals("") ||
       !specCodingInfo.getListLabelPrintByLine().isEmpty() ||
       !specCodingInfo.getListLabelPrintAdditional().isEmpty())
   {
%>  
   <tr class="tr02">
    <td class="td05160102" colspan="5">&#160;&#160;<b>Identification Label Coding</b></td>
   </tr>
<%
    if (!specCoding.getKosherSymbolRequired().trim().equals(""))
    {
%>   
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="4">&#160;Kosher Symbol is Required&#160;&#160;
<%
   if (!specCoding.getKosherSymbol().trim().equals(""))
   {
%>    
    <img src="<%= specCoding.getKosherSymbol() %>" style="height:20px" />
<%
   }
%>   
    </td>
   </tr>
<%
     }
     if (!specCodingInfo.getListLabelPrintByLine().isEmpty())
     {
       for (int lblX = 0; lblX < specCodingInfo.getListLabelPrintByLine().size(); lblX++)
       {
          KeyValue listLine = (KeyValue) specCodingInfo.getListLabelPrintByLine().elementAt(lblX);
%>
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" style="width:25%">Label Print Line <%= (lblX + 1) %>:</td>
    <td class="td04140102" colspan = "3">&#160;<%= listLine.getValue().trim() %></td>
   </tr> 
<%
       } 
     }
     if (specCodingInfo.getListLabelPrintAdditional() != null &&
        !specCodingInfo.getListLabelPrintAdditional().isEmpty())
     {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment12");
  	   request.setAttribute("listKeyValues", specCodingInfo.getListLabelPrintAdditional());
%>  
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="4">
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    </td>
   </tr>
<%
    }
  }
//----------------------------------------------------
//  Link to the Example Label
  if (specCodingInfo.getListExampleLabel() != null &&
      !specCodingInfo.getListExampleLabel().isEmpty())
  {    
      KeyValue listLine = (KeyValue) specCodingInfo.getListExampleLabel().elementAt(0);
%>
  <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="4">
<%= HTMLHelpersLinks.basicLink("Example Label", listLine.getValue(), "", "a0518", "") %>    
    </td>
   </tr>
<%    
  }
  if (!specCoding.getPalletLabelType().trim().equals("") ||
      !specCoding.getPalletLabelLocation().trim().equals("") || 
      !specCodingInfo.getListPalletPrintByLine().isEmpty() ||
      !specCodingInfo.getListPalletPrintAdditional().isEmpty())
   {
%>  
   <tr class="tr02">
    <td class="td05160102" colspan="5">&#160;&#160;<b>Pallet Placard Coding</b></td>
   </tr>
<%
    if (!specCoding.getPalletLabelType().trim().equals("") ||
        !specCoding.getPalletLabelLocation().trim().equals(""))
    {
%>   
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="2" style="width:35%">Placard Type:&#160;&#160;&#160;
       <abbr title="Label Type Code Value: <%= specCoding.getPalletLabelType() %>" %>
       <%= specCoding.getPalletLabelTypeDescription() %></abbr></td>
    <td class="td04140102" colspan="2">Location:&#160;&#160;&#160;
      <abbr title="Location Code Value: <%= specCoding.getPalletLabelLocation() %>" %>
      <%= specCoding.getPalletLabelLocationDescription() %></abbr>
    </td>
   </tr>
<%
     }
     if (!specCodingInfo.getListPalletPrintByLine().isEmpty())
     {
       for (int pltX = 0; pltX < specCodingInfo.getListPalletPrintByLine().size(); pltX++)
       {
          KeyValue listLine = (KeyValue) specCodingInfo.getListPalletPrintByLine().elementAt(pltX);
%>
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" style="width:20%">Pallet Label Print Line <%= (pltX + 1) %>:</td>
    <td class="td04140102" colspan = "3">&#160;<%= listLine.getValue().trim() %></td>
   </tr> 
<%
       } 
     }
     if (specCodingInfo.getListPalletPrintAdditional() != null &&
        !specCodingInfo.getListPalletPrintAdditional().isEmpty())
     {   
  	   request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment10");
  	   request.setAttribute("listKeyValues", specCodingInfo.getListPalletPrintAdditional());
%>  
   <tr class="tr00">
    <td class="td04140102" style="width:3%">&#160;</td>
    <td class="td04140102" colspan="4">
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
    </td>
   </tr>
<%
    }
  }  
%>  
  </table>   
 </body>
</html>