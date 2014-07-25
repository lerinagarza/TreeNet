<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updRawFruitBin.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/06/08
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector binInfo = new Vector();
 UpdRawFruitLoad urflBin = new UpdRawFruitLoad();
 try
 {
	urflBin = (UpdRawFruitLoad) request.getAttribute("updViewBean");
	binInfo = urflBin.getListBins();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
 String readOnlyBin = "";
%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>    
 </head>
 <body>
  <table class="table00" cellspacing="0" cellpadding="1">
  <tr>
    <td class="td04160102" style="width:2%">&nbsp;</td>
    <td class="td04160102" colspan="3">&nbsp;</td>
    <td class="td04160102" colspan="4">&nbsp;</td>       
   </tr>
   <tr class = "tr02">
    <td class="td04160102">&nbsp;</td>
    <td class="td04160102"><b>Bin Type / Description</b></td>
    <td class="td04160102" style="text-align:right"><b>Tare Weight per Bin</b></td>
    <td class="td04160102">&nbsp;&nbsp;</td>
    <td class="td04160102" style="text-align:center"><b>Number of Bins per Type</b></td>
    <td class="td04160102" style="text-align:right"><b>Tare Weight</b></td>    
    <td class="td04160102" style="width:2%">&nbsp;</td>       
   </tr>
<%
 // Display all the Records that are currently in the File
  int lineCount = 0;
  BigDecimal totalBins = new BigDecimal(0);
  BigDecimal totalWeight = new BigDecimal(0);
  if (binInfo.size() > 0)
  {
    for (int x = 0; x < binInfo.size(); x++)
    {
       lineCount++;
       UpdRawFruitBins updLine = new UpdRawFruitBins();
       try
       {
          updLine = (UpdRawFruitBins) binInfo.elementAt(x);
         
       }
       catch(Exception e)
       {}
       RawFruitBin fileData = new RawFruitBin();
       try
       {
          fileData = updLine.getBinInfo();
          totalBins = totalBins.add(new BigDecimal(fileData.getBinQuantity()));
          totalWeight = totalWeight.add(new BigDecimal(fileData.getBinTotalWeight()));
       }
       catch(Exception e)
       {}
%>   
     <tr>
      <td class="td04140102">&nbsp;</td>
      <td class="td03140102"><%= urflBin.buildDropDownBinType((x + "binType"), updLine.getBinType(), readOnlyBin) %>&nbsp;</td>
      <td class="td04140102" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(fileData.getBinWeight(), 1) %></td>
      <td class="td04160102">&nbsp;</td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "numberOfBins"), updLine.getNumberOfBins(), "", 5, 5,"N", readOnlyBin) %>&nbsp;<%= updLine.getNumberOfBinsError() %></td>
      <td class="td04140102" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(fileData.getBinTotalWeight(),1) %></td>
      <td class="td04140102">&nbsp;</td> 
     </tr>
<%
      }
  }
%>
   
<%
  int blankCount = 5;
  if (lineCount >= 5)
    blankCount = lineCount + 2;
    
   for (int x = lineCount; x < blankCount; x++)
   {
      lineCount++;
%>   
     <tr>
      <td class="td04140102">&nbsp;</td>
      <td class="td03140102"><%= urflBin.buildDropDownBinType((x + "binType"), "", readOnlyBin) %>&nbsp;</td>
      <td class="td04140102" style="text-align:right">&nbsp;</td>
      <td class="td04160102">&nbsp;</td>
      <td class="td03140102"><%= HTMLHelpersInput.inputBoxNumber((x + "numberOfBins"), "", "", 5, 5,"N", readOnlyBin) %>&nbsp;</td>
      <td class="td04140102" style="text-align:right">&nbsp;</td>
      <td class="td04140102">&nbsp;</td> 
     </tr>
<%
      }
%>
   <%= HTMLHelpersInput.inputBoxHidden("countBins", (lineCount + "")) %>
   <tr class = "tr02">
    <td class="td04160102">&nbsp;</td>
    <td class="td04160102" colspan = "3"><b>TOTAL:</b></td>
    <td class="td04160102"><b><%= HTMLHelpersMasking.maskNumber(totalBins.toString(), 0) %></b></td>
    <td class="td04160102" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalWeight.toString(), 0) %></b></td>    
    <td class="td04160102">&nbsp;</td>       
   </tr> 
     </table>   
   </body>
</html>