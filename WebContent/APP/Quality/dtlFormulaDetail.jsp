<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlFormulaDetail.jsp -------------------------------------------//
//    Author :  Teri Walton  8/18/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector detailInfo = new Vector();
 String batchSize    = "";
 String batchSizeUOM = "";
 try
 {
	DtlFormula dtlMain = (DtlFormula) request.getAttribute("dtlViewBean");
	String detailScreenType = (String) request.getAttribute("screenType");
	QaFormula dtlHeader = dtlMain.getDtlBean().getFormula();
	if (detailScreenType.trim().equals("preBlend"))
	{
	   detailInfo   = dtlMain.getDtlBean().getFormulaPreBlend();
	   batchSize    = dtlHeader.getBatchQuantityPreBlend();
	   batchSizeUOM = dtlHeader.getBatchUOMPreBlend();
	}
	else // production
	{
	   detailInfo = dtlMain.getDtlBean().getFormulaProduction();
	   batchSize    = dtlHeader.getBatchQuantity();
	   batchSizeUOM = dtlHeader.getBatchUnitOfMeasure();
	}
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
  <table class="table00" cellspacing="0" cellpadding="1">
   <tr>
    <td>&#160;</td>
    <td class="td04140102" colspan="6">Batch Size:&#160;&#160;&#160;<%= batchSize %>&#160;&#160;<%= batchSizeUOM %></td>
   </tr>  
   <tr class = "tr02">
    <td class="td04140102" style="text-align:center"><abbr title="A valid M3 item from the M3 item master"><b>Item</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="If an Item is chosen this is the M3 Description - if not then this is the Keyed Description"><b>Description</b></abbr></td>
    <td class="td04140102" style="text-align:right"><abbr title="Quantity is based on the Batch Size"><b>Quantity&#160;</b></abbr></td>    
    <td class="td04140102" style="width:10%;text-align:center"><b>&#160;&#160;UOM</b></td>
    <td class="td04140102" style="text-align:center"><abbr title="A valid M3 Supplier from the M3 Supplier Master"><b>Supplier</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="If a Supplier is chosen this is the M3 Description - if not then this is the Keyed Description"><b>Name</b></abbr></td>   
   </tr>
<%
 // Display all the Records from the Vector
  if (!detailInfo.isEmpty())
  {
    for (int x = 0; x < detailInfo.size(); x++)
    {
       QaFormulaDetail dtlLine = new QaFormulaDetail();
       try
       {
          dtlLine = (QaFormulaDetail) detailInfo.elementAt(x);
       }
       catch(Exception e)
       {}
%>
     <tr>
      <td class="td04140102"><%= dtlLine.getItemNumber1().trim() %>&#160;</td>
      <td class="td04140102"><%= dtlLine.getItemDescription1().trim() %>&#160;</td>
      <td class="td04140102" style="text-align:right"><%= dtlLine.getItemQuantity1().trim() %></td>
      <td class="td04140102" style="text-align:center">&#160;<%= dtlLine.getItemUnitOfMeasure1() %>&#160;</td> 
      <td class="td04140102" style="text-align:center"><%= dtlLine.getSupplierNumber().trim() %>&#160;</td>
      <td class="td04140102" style="text-align:center"><%= dtlLine.getSupplierName().trim() %>&#160;</td>
     </tr>  
<%
      }
  }
%>  
     </table>   
   </body>
</html>