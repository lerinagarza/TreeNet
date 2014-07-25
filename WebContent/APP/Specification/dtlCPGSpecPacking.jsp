<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import="com.treetop.businessobjectapplications.BeanSpecification" %>
<%
//---------------- dtlCPGSpecPacking.jsp -------------------------------------------//
//
//    Author :  Teri Walton  10/28/08
//   CHANGES: // Split off of the Detail Page
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 SpecificationNEW thisSpecPack = new SpecificationNEW();
 ItemWarehouse itemPack = new ItemWarehouse();
 Item iPack = new Item();
 try
 {
	DtlSpecification ds = (DtlSpecification) request.getAttribute("dtlViewBean");
	thisSpecPack = ds.getBeanSpec().getSpecClass();
	itemPack = ds.getBeanSpec().getSpecClass().getItemWhse();
	iPack = ds.getBeanSpec().getItemClass();
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
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
	 <td class="td04140102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>SPECIAL PACKAGING REQUIREMENTS</b></td>
     <td class="td04120102" style="width:2%">&nbsp;</td>
	 <td class="td04140102" colspan = "3" style="text-align:center"><b>CASE AND PALLET INFORMATION</b></td>
   </tr>
   <tr>
	 <td class="td04120102" style="width:15%">Item Number:</td>
	 <td class="td04120102" style="width:45%">&nbsp;<%= HTMLHelpersLinks.routerItem(thisSpecPack.getItemWhse().getItemNumber().trim(), "a0412", "", "") %></td>
     <td class="td04120102" style="width:2%">&nbsp;</td>
	 <td class="td04120102">Weight:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= itemPack.getGrossWeight() %></td>
	 <td class="td04120102">&nbsp;LB</td>
	</tr>
	<tr>
	 <td class="td04120102">Case UPC Number:</td>
	 <td class="td04120102">&nbsp;
<%
     if (iPack.getNewItemCaseUPC().length() == 12)
     {
        out.println(iPack.getNewItemCaseUPC().substring(0, 1) + "&nbsp;");
        out.println(iPack.getNewItemCaseUPC().substring(1, 6) + "&nbsp;");
        out.println(iPack.getNewItemCaseUPC().substring(6, 11) + "&nbsp;");
        out.println(iPack.getNewItemCaseUPC().substring(11));
     }
     else
        out.println(iPack.getNewItemCaseUPC());
%>	 
	 </td>
	 <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Length:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= thisSpecPack.getLength() %></td>
	 <td class="td04120102">&nbsp;Inches</td>
	</tr>
	<tr>
	 <td class="td04120102">Label UPC Number:</td>
	 <td class="td04120102">&nbsp;
<%
     if (iPack.getNewItemLabelUPC().length() == 12)
     {
        out.println(iPack.getNewItemLabelUPC().substring(0, 1) + "&nbsp;");
        out.println(iPack.getNewItemLabelUPC().substring(1, 6) + "&nbsp;");
        out.println(iPack.getNewItemLabelUPC().substring(6, 11) + "&nbsp;");
        out.println(iPack.getNewItemLabelUPC().substring(11));
     }
     else
        out.println(iPack.getNewItemCaseUPC());
%>	 	 
	 </td>
	 <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Width:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= thisSpecPack.getWidth() %></td>
	 <td class="td04120102">&nbsp;Inches</td>
	</tr>
	<tr>
	 <td class="td04120102">Date Coding Information:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getCodingInfo() %></td>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Height:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= thisSpecPack.getHeight() %></td>
	 <td class="td04120102">&nbsp;Inches</td>	 
    </tr>
   	<tr>
	 <td class="td04120102">Case Code Print:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getCaseCodePrint() %></td>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Cube:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= DtlSpecification.calculateCaseCube(thisSpecPack.getLength(), thisSpecPack.getWidth(), thisSpecPack.getHeight()) %></td>
	 <td class="td04120102">&nbsp;Cubic Feet</td>	 
    </tr>
   	<tr>
	 <td class="td04120102">Case Print Line 1:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getCasePrintLine1() %></td>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Layers per Pallet:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;
<%
   String calculatedLayers = DtlSpecification.calculateLayersPerPallet(itemPack.getCasesPerPallet(), itemPack.getCasesPerLayer());
%>	 
	 <%=  calculatedLayers %></td>
	 <td class="td04120102">&nbsp;Layers</td>	 
    </tr>
 	<tr>
	 <td class="td04120102">Case Print Line 2:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getCasePrintLine2() %></td>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Cases Per Layer:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskNumber(itemPack.getCasesPerLayer(), 0) %></td>
	 <td class="td04120102">&nbsp;Cases</td>	 
   </tr>  
   	<tr>
	 <td class="td04120102">Case Print General:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getCasePrintGeneral() %></td>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Cases per Pallet:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskNumber(itemPack.getCasesPerPallet(), 0) %></td>
	 <td class="td04120102">&nbsp;Cases</td>	 
   </tr>
   	<tr>
	 <td class="td04120102">Shelf Life:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getItemWhse().getDaysShelfLife() %>&nbsp; Days</td>
     <td class="td04120102">&nbsp;</td>
 	 <td class="td04120102">Pallet Height:</td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= DtlSpecification.calculatePalletHeight(calculatedLayers, thisSpecPack.getHeight()) %></td>	 
	 <td class="td04120102">&nbsp;Inches</td>
   </tr>
   	<tr>
	 <td class="td04120102">Storage Conditions:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecPack.getStorageConditions() %></td>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102"><acronym title="This is found in the Fragility Field of M3">Pallet Stacking:</acronym></td>
	 <td class="td04120102" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskNumber(itemPack.getPalletStacking(), 0) %></td>	 
	 <td class="td04120102">&nbsp;Pallets</td>
     
   </tr>
   	<tr>
	 <td class="td04120102" rowspan="3">Special Requirements:</td>
	 <td class="td04120102" rowspan="3">&nbsp;<%= thisSpecPack.getSpecialRequirements() %></td>
     <td class="td04120102">&nbsp;</td>
     <td class="td04120102">Slip Sheet Information:</td>
	 <td class="td04120102" colspan="2">&nbsp;<%= thisSpecPack.getSlipSheetInfo() %></td>	 
     
   </tr>   
    <tr>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Stretch Wrap:</td>
	 <td class="td04120102" colspan="2">&nbsp;<%= thisSpecPack.getStretchWrap() %>&nbsp;-&nbsp;<%= thisSpecPack.getStretchWrapInfo() %></td>	 
   </tr>
    <tr>
     <td class="td04120102">&nbsp;</td>
	 <td class="td04120102">Shrink Wrap:</td>
	 <td class="td04120102" colspan="2">&nbsp;<%= thisSpecPack.getShrinkWrap() %>&nbsp;-&nbsp;<%= thisSpecPack.getShrinkWrapInfo() %></td>	 
   </tr>
  </table>
 </body>
</html>