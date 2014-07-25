<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updFormulaDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  7/16/10
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector detailInfo = new Vector();
 UpdFormula updMain = new UpdFormula();
 String detailScreenType = "";
 String readOnlyDetail = "";
 
 try
 {
	updMain = (UpdFormula) request.getAttribute("updViewBean");
	detailScreenType = (String) request.getAttribute("screenType");
	if (detailScreenType == null)
	   detailScreenType = "";
	if (detailScreenType.trim().equals("preBlend"))
	   detailInfo = updMain.getListPreBlendDetail();  
	if (detailScreenType.trim().equals("production"))
	   detailInfo = updMain.getListProductionDetail(); 
	    
    readOnlyDetail = (String) request.getAttribute("readOnlyFormula");	
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>    
 </head>
 <body>
  <table class="table00" cellspacing="0" cellpadding="1">
   <tr class = "tr02">
<%
   if (readOnlyDetail.trim().equals("N"))
   {
%>   
    <td class="td04140102" style="text-align:center"><abbr title="Click here to REMOVE(Delete) the line"><b>Del</b></abbr></td>
<%
   }
%>    
    <td class="td04140102" style="text-align:center"><b>Seq</b></td>
    <td class="td04140102" style="text-align:center"><abbr title="Not Required, but can enter an item - if entered, must be a valid M3 item and will default the Description from the M3 item master"><b>Item</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Required if an Item Number is NOT entered, if an item number is entered this value will default from the Item Master"><b>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity is based on the Batch Size"><b>Quantity</b></abbr></td>    
    <td class="td04140102" style="text-align:center; width:10%"><abbr title="Required IF there is a Quantity, but will default from the Item Master if an Item is entered"><b>UOM</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Not Required, but can enter an Supplier - if entered, must be a valid M3 item and will default the Supplier Name from the M3 Supplier Master"><b>Supplier</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Not Required, but if a supplier number is entered this value will default from the Supplier Master"><b>Name</b></abbr></td>
   </tr>
<%
 // Display all the Records that are currently in the File
  int lineCount = 0;
  if (detailInfo.size() > 0)
  {
    for (int x = 0; x < detailInfo.size(); x++)
    {
       lineCount++;
       UpdFormulaDetail updLine = new UpdFormulaDetail();
       try
       {
          updLine = (UpdFormulaDetail) detailInfo.elementAt(x);
       }
       catch(Exception e)
       {}
%>
     <tr>
<%
   if (readOnlyDetail.trim().equals("N"))
   {
%>       
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox((detailScreenType + "removeLine" + lineCount), updLine.getRemoveLine(),"N") %>&#160;</td>
<%
   }
%>      
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "detailSequence" + lineCount), (lineCount + "0"), "Sequence Number of this Line", 3, 3, "N", readOnlyDetail) %>&#160;<%= updLine.getDetailSequenceError().trim() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber1" + lineCount), updLine.getItemNumber1().trim(), "Item Number", 10, 15, "N", readOnlyDetail) %>&#160;<%= updLine.getItemNumber1Error() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription1" + lineCount), updLine.getItemDescription1().trim(), "Item Description", 20, 30, "N", readOnlyDetail) %>&#160;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity1" + lineCount), updLine.getQuantity1().trim(), "Quantity Needed", 8, 11, "N", readOnlyDetail) %>&#160;<%= updLine.getQuantity1Error() %></td>
      <td class="td03140102" style="text-align:center"><%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure1" + lineCount), updLine.getUnitOfMeasure1().trim(), "Choose One", "B", readOnlyDetail) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "supplierNumber" + lineCount), updLine.getSupplierNumber().trim(), "Supplier Number", 10, 10, "N", readOnlyDetail) %>&#160;<%= updLine.getSupplierNumberError() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "supplierName" + lineCount), updLine.getSupplierName().trim(), "Supplier Name", 20, 36, "N", readOnlyDetail) %>&#160;</td>
     </tr>  
<%
      }
  }
  // This will set 5 blanks to start with, and then keep an additional 2 for all time
if (readOnlyDetail.equals("N"))  
{
  int blankCount = 5;
  if (lineCount >= 5)
    blankCount = lineCount + 2;
   for (int x = lineCount; x < blankCount; x++)
   {
      lineCount++;
%>   
     <tr>
      <td class="td03140102">&#160;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "detailSequence" + lineCount), (lineCount + "0"), "Sequence Number of this Line", 3, 3, "N", readOnlyDetail) %>&#160;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber1" + lineCount), "", "Item Number", 10, 15, "N", readOnlyDetail) %>&#160;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription1" + lineCount), "", "Item Description", 20, 30, "N", readOnlyDetail) %>&#160;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity1" + lineCount), "", "Quantity Needed", 8, 11, "N", readOnlyDetail) %>&#160;</td>
      <td class="td03140102" style="text-align:center"><%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure1" + lineCount), "", "Choose One", "B", "N") %></td> 
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "supplierNumber" + lineCount), "", "Supplier Number", 10, 10, "N", readOnlyDetail) %>&#160;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "supplierName" + lineCount), "", "Supplier Name", 20, 36, "N", readOnlyDetail) %>&#160;</td>
     </tr>
<%
   } // end of the For Loop
}      
    if (detailScreenType.trim().equals("preBlend"))
	   out.println(HTMLHelpersInput.inputBoxHidden(("countPreBlendDetail"), (lineCount + "")));
	if (detailScreenType.trim().equals("production"))
	   out.println(HTMLHelpersInput.inputBoxHidden(("countProductionDetail"), (lineCount + "")));
%>
     </table>   
   </body>
</html>