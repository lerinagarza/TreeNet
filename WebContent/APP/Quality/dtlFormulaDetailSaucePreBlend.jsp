<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlFormulaDetailSaucePreBlend.jsp ------------------------//
//    Author :  Teri Walton  9/30/11
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector detailInfo = new Vector();
 String display3 = "";
 try
 {
	DtlFormula dtlMain = (DtlFormula) request.getAttribute("dtlViewBean");
	display3 = dtlMain.getSaucePreBlendIngredient3();
	detailInfo = dtlMain.getDtlBean().getFormulaPreBlendSauce();
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
   <tr class = "tr02">
    <td class="td04140102" style="text-align:center"><abbr title="Sauce Brix - to determine what should be used"><b>Starting Sauce Brix</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Item and Description -- if Item is entered must be a VALID M3 item, and will use the M3 Description"><b>Sweetener Used<br>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity of Sweetener Used for the Specific Batch Size and Brix"><b>Quantity</b></abbr></td>   
    <td class="td04140102" style="text-align:center">UOM</td>     
    <td class="td04140102" style="text-align:center"><abbr title="Item and Description -- if Item is entered must be a VALID M3 item, and will use the M3 Description"><b>Ingredient<br>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity of Sweetener Used for the Specific Batch Size and Brix"><b>Quantity</b></abbr></td>    
    <td class="td04140102" style="text-align:center">UOM</td>    
<%
  if (display3.trim().equals(""))
  {
%>    
    <td class="td04140102" style="text-align:center"><abbr title="Item and Description -- if Item is entered must be a VALID M3 item, and will use the M3 Description"><b>Ingredient<br>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity of Sweetener Used for the Specific Batch Size and Brix"><b>Quantity</b></abbr></td>
    <td class="td04140102" style="text-align:center">UOM</td>
<%
   }
%>            
    <td class="td04140102" style="text-align:center"><abbr title="How many gallons of Sauce should this produce?"><b>Produced Sauce Gallons</b></abbr></td>
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
    <td class="td04140102" style="text-align:right"><%= dtlLine.getSauceTargetBrix().trim() %>&#160;</td>
    <td class="td04140102"><%= dtlLine.getItemNumber1().trim() %>&#160;<%= dtlLine.getItemDescription1().trim() %></td>
    <td class="td04140102" style="text-align:right"><%= dtlLine.getItemQuantity1().trim() %>&#160;</td>
    <td class="td04140102">&#160;<%= dtlLine.getItemUnitOfMeasure1() %></td>   
    <td class="td04140102"><%= dtlLine.getItemNumber2().trim() %>&#160;<%= dtlLine.getItemDescription2().trim() %></td>
    <td class="td04140102" style="text-align:right"><%= dtlLine.getItemQuantity2().trim() %>&#160;</td>
    <td class="td04140102">&#160;<%= dtlLine.getItemUnitOfMeasure2() %></td>   
<%
  if (display3.trim().equals(""))
  {
%>        
    <td class="td04140102"><%= dtlLine.getItemNumber3().trim() %>&#160;<%= dtlLine.getItemDescription3().trim() %></td>
    <td class="td04140102" style="text-align:right"><%= dtlLine.getItemQuantity3().trim() %>&#160;</td>
    <td class="td04140102">&#160;<%= dtlLine.getItemUnitOfMeasure3() %></td>   
<%
   }
%>         
    <td class="td04140102" style="text-align:right"><%= dtlLine.getSauceBatchQuantity().trim() %>&#160;</td>        
   </tr>  
<%
      }
  }
%>  
     </table>   
   </body>
</html>