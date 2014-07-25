<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updFormulaDetailSaucePreBlend.jsp ---------------------------//
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
 String readOnlySauce = "";
 String detailScreenType = "sauce";
 try
 {
	updMain = (UpdFormula) request.getAttribute("updViewBean");
    detailInfo = updMain.getListPreBlendSauceDetail();   
	
	readOnlySauce = (String) request.getAttribute("readOnlyFormula");	
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
   if (readOnlySauce.trim().equals("N"))
   {
%>   
    <td class="td04140102" style="text-align:center"><abbr title="Click here to REMOVE(Delete) the line"><b>Del</b></abbr></td>
<%
   }
%>    
    <td class="td04140102" style="text-align:center"><abbr title="If the Sauce Brix is this Level, use the information Provided to use the correct ingredients"><b>Starting Sauce Brix</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Item Number (M3)  -- Description IS required if an Item Number is NOT entered, if an item number is entered this value will default from the Item Master"><b>Sweetener Used<br>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity of Sweetener Used for the Specific Batch Size and Brix"><b>Quantity</b></abbr><br><abbr title="Required IF there is a Quantity, but will default from the Item Master if an Item is entered"><b>UOM</b></abbr></td>    
    <td class="td04140102" style="text-align:center"><abbr title="Item Number (M3)  -- Description IS required if an Item Number is NOT entered, if an item number is entered this value will default from the Item Master"><b>Ingredient Used<br>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity of Sweetener Used for the Specific Batch Size and Brix"><b>Quantity</b></abbr><br><abbr title="Required IF there is a Quantity, but will default from the Item Master if an Item is entered"><b>UOM</b></abbr></td>  
    <td class="td04140102" style="text-align:center"><abbr title="Item Number (M3)  -- Description IS required if an Item Number is NOT entered, if an item number is entered this value will default from the Item Master"><b>Ingredient Used<br>Description</b></abbr></td>
    <td class="td04140102" style="text-align:center"><abbr title="Quantity of Sweetener Used for the Specific Batch Size and Brix"><b>Quantity</b></abbr><br><abbr title="Required IF there is a Quantity, but will default from the Item Master if an Item is entered"><b>UOM</b></abbr></td>        
    <td class="td04140102" style="text-align:center"><abbr title="How many gallons of Sauce does this produce?"><b>Produced Sauce Gallons</b></abbr></td>
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
   if (readOnlySauce.trim().equals("N"))
   {
%>       
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox((detailScreenType + "removeLine" + lineCount), updLine.getRemoveLine(),"N") %>&#160;</td>
<%
   }
%>      
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "sauceTargetBrix" + lineCount), updLine.getSauceTargetBrix(), "Starting Sauce Brix of this Line", 4, 4, "N", readOnlySauce) %>&#160;<%= updLine.getSauceTargetBrixError().trim() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber1" + lineCount), updLine.getItemNumber1().trim(), "Item Number (Sweetener)", 10, 15, "N", readOnlySauce) %>&#160;<%= updLine.getItemNumber1Error().trim() %><br>
        <%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription1" + lineCount), updLine.getItemDescription1().trim(), "Item Description (Sweetener)", 20, 30, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity1" + lineCount), updLine.getQuantity1().trim(), "Quantity Needed", 8, 11, "N", readOnlySauce) %>&#160;<%= updLine.getQuantity1Error().trim() %><br>
        <%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure1" + lineCount), updLine.getUnitOfMeasure1().trim(), "Choose One", "B", readOnlySauce) %>&#160;<%= updLine.getUnitOfMeasure1Error().trim() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber2" + lineCount), updLine.getItemNumber2().trim(), "Item Number (Ingredient)", 10, 15, "N", readOnlySauce) %>&#160;<%= updLine.getItemNumber2Error().trim() %><br>
        <%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription2" + lineCount), updLine.getItemDescription2().trim(), "Item Description (Ingredient)", 20, 30, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity2" + lineCount), updLine.getQuantity2().trim(), "Quantity Needed", 8, 11, "N", readOnlySauce) %>&#160;<%= updLine.getQuantity2Error().trim() %><br>
        <%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure2" + lineCount), updLine.getUnitOfMeasure2().trim(), "Choose One", "B", readOnlySauce) %>&#160;<%= updLine.getUnitOfMeasure2Error().trim() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber3" + lineCount), updLine.getItemNumber3().trim(), "Item Number (Ingredient)", 10, 15, "N", readOnlySauce) %>&#160;<%= updLine.getItemNumber3Error().trim() %><br>
        <%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription3" + lineCount), updLine.getItemDescription3().trim(), "Item Description (Ingredient)", 20, 30, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity3" + lineCount), updLine.getQuantity3().trim(), "Quantity Needed", 8, 11, "N", readOnlySauce) %>&#160;<%= updLine.getQuantity3Error().trim() %><br>
        <%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure3" + lineCount), updLine.getUnitOfMeasure3().trim(), "Choose One", "B", readOnlySauce) %>&#160;<%= updLine.getUnitOfMeasure3Error().trim() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "sauceBatchQty" + lineCount), updLine.getSauceBatchQty().trim(), "Batch Quantity", 8, 11, "N", readOnlySauce) %>&#160;<%= updLine.getSauceBatchQtyError().trim() %></td>
     </tr>  
<%
      }
  }
  // This will set 5 blanks to start with, and then keep an additional 2 for all time
if (readOnlySauce.equals("N"))  
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
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "sauceTargetBrix" + lineCount), "0.0", "Starting Sauce Brix of this Line", 4, 4, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber1" + lineCount), "", "Item Number (Sweetener)", 10, 15, "N", readOnlySauce) %><br>
        <%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription1" + lineCount), "", "Item Description (Sweetener)", 20, 30, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity1" + lineCount), "0", "Quantity Needed", 8, 11, "N", readOnlySauce) %><br>
        <%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure1" + lineCount), "", "Choose One", "B", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber2" + lineCount), "", "Item Number (Ingredient)", 10, 15, "N", readOnlySauce) %><br>
        <%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription2" + lineCount), "", "Item Description (Ingredient)", 20, 30, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity2" + lineCount), "0", "Quantity Needed", 8, 11, "N", readOnlySauce) %><br>
        <%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure2" + lineCount), "", "Choose One", "B", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemNumber3" + lineCount), "", "Item Number (Ingredient)", 10, 15, "N", readOnlySauce) %><br>
        <%= HTMLHelpersInput.inputBoxText((detailScreenType + "itemDescription3" + lineCount), "", "Item Description (Ingredient)", 20, 30, "N", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "quantity3" + lineCount), "0", "Quantity Needed", 8, 11, "N", readOnlySauce) %><br>
        <%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), (detailScreenType + "unitOfMeasure3" + lineCount), "", "Choose One", "B", readOnlySauce) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((detailScreenType + "sauceBatchQty" + lineCount), "0", "Batch Quantity", 8, 11, "N", readOnlySauce) %></td>
     </tr>
<%
   } // end of the For Loop
}      
%>
   <%= HTMLHelpersInput.inputBoxHidden("countPreBlendSauceDetail", (lineCount + "")) %>
     </table>   
   </body>
</html>