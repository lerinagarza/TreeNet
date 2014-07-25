<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlVariety.jsp -------------------------------------------//
//
//    Author :  Teri Walton  2/23/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
 Vector dtlInclude = new Vector();
 Vector dtlExclude = new Vector();
 int largeCount = 0;
 try
 {
    String varietyRequestType = (String) request.getAttribute("requestType");
    //------------------------------------------------------------------------------//
    //  FORMULA
    //------------------------------------------------------------------------------//
     if (varietyRequestType.trim().equals("dtlFormula"))
     {
         DtlFormula dtlMain = (DtlFormula) request.getAttribute("dtlViewBean");
		 dtlInclude = dtlMain.getDtlBean().getVarietiesIncluded();
		 dtlExclude = dtlMain.getDtlBean().getVarietiesExcluded();	         
     }
    //------------------------------------------------------------------------------//
    //  SPECIFICATION
    //------------------------------------------------------------------------------//
     if (varietyRequestType.trim().equals("dtlSpec"))
     {
         DtlSpecification dtlMain = (DtlSpecification) request.getAttribute("dtlViewBean");
		 dtlInclude = dtlMain.getDtlBean().getVarietiesIncluded();
		 dtlExclude = dtlMain.getDtlBean().getVarietiesExcluded();	         
     }
     
     largeCount = dtlInclude.size();
     if (largeCount < dtlExclude.size())
       largeCount = dtlExclude.size();
 }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

%>
<html>
 <head>
 </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" border="1">
   <tr class="tr02">
    <td class="td04160102" style="text-align:center"><b>Varieties Included</b></td>
<%
if (!dtlExclude.isEmpty())
   {
%>    
    <td class="td04160102" style="text-align:center"><b>Varieties Excluded</b></td>
<%
   }
%>    
   </tr>
   <tr>
    <td>
     <table class="table00" cellspacing="0" style="width:100%" border="1">
      <tr class="tr02">
       <td class="td04140102" style="text-align:center">Crop</td>
       <td class="td04140102" style="text-align:center">Variety</td>
       <td class="td04140102" style="text-align:center">Target Percent</td>    
       <td class="td04140102" style="text-align:center">Minimum Percent</td>
       <td class="td04140102" style="text-align:center">Maximum Percent</td>
      </tr>
<%
   int countRows = 0;
   if (!dtlInclude.isEmpty())
   {
      for (int x = 0; x < dtlInclude.size(); x++)
      {
		 countRows++;
		 QaFruitVariety rowData = (QaFruitVariety) dtlInclude.elementAt(x);
%>
      <tr class="tr00">
       <td class="td04140102">&#160;<%= rowData.getAttributeCropModelDescription() %></td>
       <td class="td04140102">&#160;<%= rowData.getFruitVarietyDescription() %></td>
       <td class="td04140102" style="text-align:right"><%= GeneralQuality.formatPercent(rowData.getIncludePercentage()) %></td>    
       <td class="td04140102" style="text-align:right"><%= GeneralQuality.formatPercent(rowData.getIncludeMinimumPercent()) %></td>
       <td class="td04140102" style="text-align:right"><%= GeneralQuality.formatPercent(rowData.getIncludeMaximumPercent()) %></td>
      </tr>
<%
      }
   }
   if (countRows < largeCount)
   {
      for (int allrows = countRows; allrows < largeCount; allrows++)
      {
%>      
      <tr class="tr00">
       <td class="td04140102" colspan="5">&#160;</td>
      </tr>
<%
      }
   }
%>      
     </table>
    </td>
<%
  countRows = 0;
   if (!dtlExclude.isEmpty())
   {
%>        
    <td>
     <table class="table00" cellspacing="0" style="width:100%" border="1">

      <tr class="tr02">
       <td class="td04140102" style="text-align:center">Crop</td>
       <td class="td04140102" style="text-align:center">Variety</td>
      </tr>
<%
      for (int x = 0; x < dtlExclude.size(); x++)
      {
		 countRows++;
		 QaFruitVariety rowData = (QaFruitVariety) dtlExclude.elementAt(x);
%>
      <tr class="tr00">
       <td class="td04140102">&#160;<%= rowData.getAttributeCropModelDescription() %></td>
       <td class="td04140102">&#160;<%= rowData.getFruitVarietyDescription() %></td>
      </tr>
<%
      }
      if (countRows < largeCount)
      {
         for (int allrows = countRows; allrows < largeCount; allrows++)
         {
%>      
      <tr class="tr00">
       <td class="td04140102" colspan="5">&#160;</td>
      </tr>
<%
         }
      }
%>      
     </table>
    </td>
<%
   }
%>    
   </tr> 
  </table>   
 </body>
</html>