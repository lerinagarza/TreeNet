<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlSpecificationItem.jsp -------------------------------------------//
// Use this JSP as the Driver to determine when to add in the Packaging Information to the Screen
//
//    Author :  Teri Walton  12/7/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 QaSpecificationPackaging dtlItemInfo = new QaSpecificationPackaging();
 DtlSpecification dtlSpec = new DtlSpecification();
 String saveLength = "0";
 String saveWidth  = "0";
 String saveHeight = "0";
 try
 {
	dtlSpec = (DtlSpecification) request.getAttribute("dtlViewBean");
	dtlItemInfo = (QaSpecificationPackaging) dtlSpec.getDtlBean().getSpecPackaging();
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
  <table class="table00" cellspacing="0" style="width:100%">
   <tr class="tr02">
    <td class="td05160102" colspan="3"><b>Item:<%= dtlItemInfo.getItemNumber() %>&nbsp;<%= dtlItemInfo.getItemDescription() %> Specific Information</b></td>
   </tr>	  
   <tr>
    <td class="td04140102" style="width:2%">&nbsp;</td>
    <td class="td04140102" style="width:49%">
     <table class="table00" cellspacing="0" style="width:100%">
      <tr class="tr02">
       <td class="td04160102" colspan="3"><b>&#160;UNIT</b></td>
      </tr>
<%
   if (!dtlItemInfo.getUnitUPCNumber().trim().equals(""))
   {
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Case UPC Code - information found in M3 Alias tied to the item">Case UPC Code:</acronym></td>
       <td class="td04140102" colspan= "2"><%= HTMLHelpersMasking.maskUPCCode(dtlItemInfo.getUnitUPCNumber().trim()) %></td>
      </tr>
<%
   }
   if (!dtlItemInfo.getContainerUPCNumber().trim().equals(""))
   {
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Label UPC Code - information found in M3 Alias tied to the item">Label UPC Code:</acronym></td>
       <td class="td04140102" colspan= "2"><%= HTMLHelpersMasking.maskUPCCode(dtlItemInfo.getContainerUPCNumber().trim()) %></td>
      </tr>
<%
    }
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Gross Weight - information found in M3 Item Master">Gross Weight:</acronym></td>
       <td class="td04140102" style="text-align:right;width:7%"><%= HTMLHelpersMasking.mask2Decimal(dtlItemInfo.getUnitWeight()) %></td>
       <td class="td04140102">&#160;</td>
      </tr>
<%
   if ("donotdisplayyet".equals("0"))
   {
%>      
      <tr class="tr00">
       <td class="td04140102"><acronym title="Net Weight - information found in M3 Item Master">Net Weight:</acronym></td>
       <td class="td03140102">get data from m3 / do not show if Zero</td>
      </tr>
<%
   }
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Length - information found New Item Process on TreeNet">Length:</acronym></td>
       <td class="td04140102" style="text-align:right;width:7%"><%= HTMLHelpersMasking.mask2Decimal(dtlItemInfo.getUnitLength()) %></td>
        <td class="td04140102">&#160;</td>
      </tr>
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Width - information found New Item Process on TreeNet">Width:</acronym></td>
       <td class="td04140102" style="text-align:right;width:7%"><%= HTMLHelpersMasking.mask2Decimal(dtlItemInfo.getUnitWidth()) %></td>
        <td class="td04140102">&#160;</td>
      </tr>
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Height - information found New Item Process on TreeNet">Height:</acronym></td>
       <td class="td04140102" style="text-align:right;width:7%"><%= HTMLHelpersMasking.mask2Decimal(dtlItemInfo.getUnitHeight()) %></td>
        <td class="td04140102">&#160;</td>
      </tr>
<%
   if (!dtlItemInfo.getUnitCube().trim().equals("") &&
       !dtlItemInfo.getUnitCube().trim().equals("0"))
   {    
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%"><acronym title="Cube - information found in M3 Item Master - in the Volume Field">Case Cube:</acronym></td>
       <td class="td04140102" style="text-align:right;width:7%"><%= dtlItemInfo.getUnitCube() %></td>
        <td class="td04140102">&#160;</td>
      </tr>
<%
    }
%>      
     </table>  
    </td> 
    <td class="td04140102">
     <table class="table00" cellspacing="0" style="width:100%; valign:top">
      <tr class="tr02">
       <td class="td04160102" colspan="2"><b>&#160;PALLET</b></td>
      </tr>
<%
   // Figure out which fields will be displayed.  M3 or Not
   //   If the value is entered into the qa files, then use then else use M3
   String displayUnitPerPallet = HTMLHelpersMasking.maskNumber(dtlItemInfo.getUnitsPerPallet().trim(), 0);
   if (displayUnitPerPallet.trim().equals("0"))
      displayUnitPerPallet = HTMLHelpersMasking.maskNumber(dtlItemInfo.getM3UnitsPerPallet().trim(), 0);
   String displayUnitPerLayer = HTMLHelpersMasking.maskNumber(dtlItemInfo.getUnitsPerLayer().trim(), 0);
   if (displayUnitPerLayer.trim().equals("0"))
      displayUnitPerLayer = HTMLHelpersMasking.maskNumber(dtlItemInfo.getM3UnitsPerLayer().trim(), 0);
   String displayLayersPerPallet = HTMLHelpersMasking.maskNumber(dtlItemInfo.getLayersPerPallet().trim(), 0);
   if (displayLayersPerPallet.trim().equals("0"))
      displayLayersPerPallet = HTMLHelpersMasking.maskNumber(dtlItemInfo.getM3LayersPerPallet().trim(), 0);    
   // Calculate   
   if (!dtlItemInfo.getPalletGTINNumber().trim().equals(""))
   {
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%">&#160;&#160;&#160;<acronym title="Pallet GTIN Code - information found in M3 Alias tied to the item">Pallet GTIN Code:</acronym></td>
       <td class="td04140102" style="text-align:left"><%= HTMLHelpersMasking.maskGTINNumber(dtlItemInfo.getPalletGTINNumber().trim()) %></td>
      </tr>
<%
   }
   if (!displayLayersPerPallet.trim().equals("0"))
   {
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%">&#160;&#160;&#160;<acronym title="Layers per Pallet - calculated from the Units per Layer and the Units per Pallet">Layers per Pallet:</acronym></td>
       <td class="td04140102" style="text-align:left"><%= displayLayersPerPallet.trim() %></td>
      </tr>
<%
   }
   if (!displayUnitPerLayer.trim().equals("0"))
   {
%>      
      <tr class="tr00">
       <td class="td04140102" style="width:20%">&#160;&#160;&#160;<acronym title="Units per Layer - information found in M3 Alternate Unit of Measure tied to the item">Units per Layer:</acronym></td>
       <td class="td04140102" style="text-align:left"><%= displayUnitPerLayer.trim()%></td>
      </tr>
 <%
   }
   if (!displayUnitPerPallet.trim().equals("0"))
   {
%>       
      <tr class="tr00">
       <td class="td04140102" style="width:20%">&#160;&#160;&#160;<acronym title="Cases per Pallet - information found in M3 Alternate Unit of Measure tied to the item">Units per Pallet:</acronym></td>
       <td class="td04140102" style="text-align:left"><%= displayUnitPerPallet.trim()%></td>
      </tr>
<%
   }
   if (!dtlItemInfo.getPalletHeight().trim().equals("") &&
       !dtlItemInfo.getPalletHeight().trim().equals("0"))
   {
%>        
      <tr class="tr00">
       <td class="td04140102" style="width:20%">&#160;&#160;&#160;<acronym title="Pallet Height - Calculated from the Unit Height and added XX for the pallet">Pallet Height:</acronym></td>
       <td class="td04140102" style="text-align:left"><%= dtlItemInfo.getPalletHeight().trim() %></td>
      </tr>
<%
   }
   if (!dtlItemInfo.getPalletStacking().trim().equals("") &&
       !dtlItemInfo.getPalletStacking().trim().equals("0"))
   {
%>           
      <tr class="tr00">
       <td class="td04140102" style="width:20%">&#160;&#160;&#160;<acronym title="Pallet Stacking - information found M3 in the Item Master - Fragility field - This reflects how many pallets can be stored stacked on top of one another">Pallet Stacking:</acronym></td>
       <td class="td04140102" style="text-align:left"><%= dtlItemInfo.getPalletStacking().trim() %></td>
      </tr>
<%
   }
%>      
     </table>  
    </td> 
   </tr>	  
  </table>   
 </body>
</html>