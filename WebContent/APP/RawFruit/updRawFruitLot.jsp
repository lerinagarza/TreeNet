<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updRawFruitLot.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/05/08
//
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
  String errorPage = "/RawFruit/updRawFruitLot.jsp";
  String updTitle = " Update a Raw Fruit Lot";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdRawFruitLot ul = new UpdRawFruitLot();
 RawFruitLot rfl = new RawFruitLot();
 String poNumber = "0";
 
 try
 {
	ul = (UpdRawFruitLot) request.getAttribute("updViewBean");
	rfl = ul.getUpdBean().getRfLot();
  }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",updTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a NEW Load");
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket\">Search for a Load (to update)");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************  
 String readOnlyLot = "N";
%>
<html>
 <head>
   <title><%= updTitle %></title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 5, "Y", 5) %>   
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <form name="updateLot" action="/web/CtlRawFruit?requestType=updateLot" method="post">
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102"><acronym title="Scale Ticket Number - Load Number">Scale Ticket Number:</acronym></td>
   <%= HTMLHelpersInput.inputBoxHidden("scaleTicket", ul.getScaleTicket()) %>
   <%= HTMLHelpersInput.inputBoxHidden("scaleTicketCorrectionSequence", ul.getScaleTicketCorrectionSequence()) %>
   <%= HTMLHelpersInput.inputBoxHidden("receivingDate", ul.getReceivingDate()) %>
   <td class="td04140102" colspan="3"><b><%= ul.getScaleTicket() %>
 <%
   if (!ul.getScaleTicketCorrectionSequence().trim().equals("0"))
     out.println("-" + ul.getScaleTicketCorrectionSequence().trim());
%>     
   &nbsp;</b></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" colspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("backToLoad", "Save / Return to Load") %></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Scale Ticket Sequence Number - Ties with Scale Number to the PO Number">Scale Ticket Sequence Number:</acronym></td>
   <%= HTMLHelpersInput.inputBoxHidden("scaleSequence", ul.getScaleSequence()) %>
   <td class="td04140102" colspan="3"><b><%= ul.getScaleSequence() %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="PO Number - When Available">PO Number:</acronym></td>
   <%= HTMLHelpersInput.inputBoxHidden("poNumber", ul.getPoNumber()) %>   
   <%= HTMLHelpersInput.inputBoxHidden("poLineNumber", ul.getPoLineNumber()) %>
   <td class="td04140102"><b><%= ul.getPoNumber() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td> 
  </tr> 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Lot Number(M3) = Raw Fruit Ticket Number   ** 8-digit number at top of the receiving ticket">Lot Number:</acronym></td>
   <td class="td03140102" colspan="3">
  <%= HTMLHelpersInput.inputBoxHidden("originalLotNumber", ul.getOriginalLotNumber()) %>
  <%= HTMLHelpersInput.inputBoxHidden("lotSequenceNumber", ul.getLotSequenceNumber()) %>
  <%= HTMLHelpersInput.inputBoxText("lotNumber", ul.getLotNumber(), "Lot Number", 15,15,"Y", "N") %>&nbsp;<%= ul.getLotNumberError() %>
   </td>  
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102">Receiving Date:</td>
   <td class="td04140102"><%= ul.getReceivingDate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Location   ** Yard Location -- if you need to view Valid Locations hit 'F4' in Movex for the Table">Location:</acronym></td>
   <td class="td03140102" colspan="3"><%= HTMLHelpersInput.inputBoxText("location", ul.getLocation(), "Location", 10,10,"Y", readOnlyLot) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Facility">Facility:</acronym></td>
   <td class="td04140102">
<%
 if (ul.getDisplayMessage().trim().equals("") ||
     ul.getDisplayMessage().trim().equals("Lot Number MUST Be Chosen<br>"))
 {
   if (rfl.getLotInformation().getFacility().equals(""))
   {
%>  
     <%= ul.getRfPO().getWarehouseFacility().getFacility() %>&nbsp;-&nbsp;<%= ul.getRfPO().getWarehouseFacility().getFacilityDescription() %>
     <%= HTMLHelpersInput.inputBoxHidden("facility", ul.getRfPO().getWarehouseFacility().getFacility()) %>
<%
   }
   else
   {
%> 
   <%= rfl.getLotInformation().getFacility() %>&nbsp;-&nbsp;<%= rfl.getLotInformation().getFacilityName() %>
   <%= HTMLHelpersInput.inputBoxHidden("facility", rfl.getLotInformation().getFacilityName()) %>
<%
   }
 }
 else
 {
%> 
   <%= ul.getFacility() %>&nbsp;
   <%= HTMLHelpersInput.inputBoxHidden("facility", ul.getFacility()) %>
<%
 }
%>   
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Harvest Year   ** Year the Fruit was Harvested">Harvest Year:</acronym></td>
   <td class="td04140102" colspan="3"><%= UpdRawFruitLot.buildDropDownHarvestYear(ul.getHarvestYear()) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Warehouse">Warehouse:</acronym></td>
   <td class="td04140102">
<%
 if (ul.getDisplayMessage().trim().equals("") ||
     ul.getDisplayMessage().trim().equals("Lot Number MUST Be Chosen<br>"))
 {

   if (rfl.getLotInformation().getWarehouse().equals(""))
   {
%>  
     <%= ul.getRfPO().getWarehouseFacility().getWarehouse() %>&nbsp;-&nbsp;<%= ul.getRfPO().getWarehouseFacility().getWarehouseDescription() %>
     <%= HTMLHelpersInput.inputBoxHidden("warehouse", ul.getRfPO().getWarehouseFacility().getWarehouse()) %>
<%
   }
   else
   {
%> 
   <%= rfl.getLotInformation().getWarehouse() %>&nbsp;-&nbsp;<%= rfl.getLotInformation().getWarehouseName() %>
   <%= HTMLHelpersInput.inputBoxHidden("warehouse", rfl.getLotInformation().getWarehouse()) %>
<%
   }
 }
 else
 {
%> 
   <%= ul.getWarehouse() %>&nbsp;
   <%= HTMLHelpersInput.inputBoxHidden("warehouse", ul.getWarehouse()) %>
<%
 }
%>   
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
 <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Supplier Load Number - Doc Number - Delivery Note    ** Number on Ticket that was sent with the load by the Supplier  (Defaulted from the LOAD, Warehouse Document Number)">Supplier Delivery Note:</acronym></td>
   <td class="td04140102" colspan="3">
 <%
   String supplierDeliveryNote = ul.getSupplierDeliveryNote().trim();
//   if (rfl.getSupplierLoadNumber().equals(""))
//	 supplierDeliveryNote = ul.getRfPO().getWhseTicket();
%>   
   <%= HTMLHelpersInput.inputBoxText("supplierDeliveryNote", supplierDeliveryNote, "Supplier Delivery Note", 12, 12,"Y", readOnlyLot) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Supplier - Packer - Who we received the Fruit From   ** Supplier number in M3  CRS620 for Number and Mileage">Supplier:</acronym></td>
   <td class="td04140102">
<%
 if (ul.getDisplayMessage().trim().equals("") ||
     ul.getDisplayMessage().trim().equals("Lot Number MUST Be Chosen<br>"))
 {
   if (rfl.getLotSupplier().getSupplierNumber().equals(""))
   {
%>  
     <%= ul.getRfPO().getPoSupplier().getSupplierNumber() %>&nbsp;-&nbsp;<%= ul.getRfPO().getPoSupplier().getSupplierName() %>
     <%= HTMLHelpersInput.inputBoxHidden("supplierNumber", ul.getRfPO().getPoSupplier().getSupplierNumber()) %>
<%
   }
   else
   {
%> 
   <%= rfl.getLotSupplier().getSupplierNumber() %>&nbsp;-&nbsp;<%= rfl.getLotSupplier().getSupplierName() %>
   <%= HTMLHelpersInput.inputBoxHidden("supplierNumber", rfl.getLotSupplier().getSupplierNumber()) %>
<%
   }
 }
 else
 {
%> 
   <%= ul.getSupplierNumber() %>&nbsp;
   <%= HTMLHelpersInput.inputBoxHidden("supplierNumber", ul.getSupplierNumber()) %>
<%
 }
%>   
   </td>   
   <td class="td04140102">&nbsp;</td>
  </tr>		
<%
  // 9/16/13 TWalton - show the Write Up Number
 %>  
   <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Orchard Run Write-Up Number">Write-Up #:</acronym></td>
   <td class="td04140102" colspan="6">
     <%= HTMLHelpersInput.inputBoxText("writeUpNumber", ul.getWriteUpNumber().trim(), "Orchard Run Write Up Number", 8, 8,"N", readOnlyLot) %>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>				
<%
   String tdType = "td03140102";
   if (ul.getItemNumberError().trim().equals(""))
     tdType = "td04140102"; 
%>
 <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Item Number   ** Raw Fruit Item Number Starts with '75'">Item Number:</acronym></td>
   <td class="<%= tdType %>" colspan="4"><%= UpdRawFruitLot.buildDropDownItem(ul.getItemNumber(), readOnlyLot) %>&nbsp;</td>
   <td class="td04140102" colspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  
<% 
   if (!rfl.getLotInformation().getItemDescription().equals(""))
   {
%>   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Crop - Built from M3 Attribute Values Listed on the Attribute Model Assigned to the Item">M3 - Crop:</acronym></td>
   <td class="td04140102" colspan="3"><%= UpdRawFruitLot.buildDropDownLotCrop(rfl.getLotInformation().getAttributeModel(), ul.getCrop(), readOnlyLot) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Conventional or Organic -- information retrieved from the Item MASTER in M3">Conventional OR Organic:</acronym></td>
   <td class="td04140102">
<%
    if (rfl.getLotInformation().getOrganicConventional().equals("OR"))
       out.println("Organic");
    else
       out.println("Conventional");
%>
   </td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	  
<%
   if (!ul.getCrop().equals("CHERRY") &&
       !ul.getCrop().equals("STRAWBERRY") &&
       !ul.getCrop().equals("GRAPE") &&
       !ul.getCrop().trim().equals(""))
   { // Not applicable for Cherries, Strawberries or Grapes
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Machine Run or Orchard Run - Built from M3 Attribute Values Listed on the Attribute Model Assigned to the Item"">M3 - Machine Run or Orchard Run:</acronym></td>
   <td class="td03140102" colspan="6"><%= UpdRawFruitLot.buildDropDownRun(rfl.getLotInformation().getAttributeModel(), ul.getRunType(), readOnlyLot) %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	    
<%
   }
   if (!ul.getCrop().trim().equals(""))
   {
%>    
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Variety - Built from M3 Attribute Values Listed on the Attribute Model Assigned to the Item">M3 - Variety:</acronym></td>
   <td class="td03140102" colspan="6"><%= UpdRawFruitLot.buildDropDownLotVariety(rfl.getLotInformation().getAttributeModel(), ul.getVariety(), readOnlyLot) %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	 
<%
   }
   if (!ul.getCrop().equals("CHERRY") &&
       !ul.getCrop().equals("STRAWBERRY") &&
       !ul.getCrop().equals("GRAPE") &&
       !ul.getCrop().trim().equals(""))
   { // Not applicable for Cherries or Grapes
%>   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Country of Origin (COO) - Built from M3 Attribute Values Listed on the Attribute Model Assigned to the Item">M3 - Country Of Origin:</acronym></td>
   <td class="td03140102" colspan="6"><%= UpdRawFruitLot.buildDropDownLotCountryOfOrigin(rfl.getLotInformation().getAttributeModel(), ul.getCountryOfOrigin(), readOnlyLot) %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  
<%
    }
    if (ul.getCrop().equals("CHERRY") ||
        ul.getCrop().equals("STRAWBERRY") )
    { 
       String highlightCull = "N";
       try{
         if (ul.getCullsPercentError().trim().equals("") &&
             ((new BigDecimal(ul.getCullsPercent())).compareTo(new BigDecimal("5"))) > 0)
             highlightCull = "Y";
       
       }catch(Exception e)
       {
          System.out.println("updRawFruitLot.jsp:  Working out if Cull Percent should be Highlighted > 5%::" + e);
       }
           
      //--if (totalLot.compareTo(addedPayment) != 0)
     //-- {
     // --   highlightWeight = "td0114050203";
     //  --  mouseoverWeight = "The total amount of Payment Weight, " + addedPayment + "  does not equal the Lot Weight " + totalLot;
     // --} 
%>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Culls - Enter the Number of Pounds and a Percentage will be Calculated">Pounds of Culls:</acronym></td>
   <td class="td03140102" colspan="4"><%= HTMLHelpersInput.inputBoxNumber("cullsPounds", ul.getCullsPounds(), "Pounds of Culls", 6,6,"N", readOnlyLot) %>&nbsp;<%= ul.getCullsPoundsError() %></td>
   <td class="td04140102"><acronym title="Calculation Based upon Pounds of Culls">Percentage of Culls:</acronym></td>
<%
   if (highlightCull.equals("N"))
   {
%>   
   <td class="td04140102" style="text-align:right"><%= HTMLHelpersInput.inputBoxNumber("cullsPercent", ul.getCullsPercent(), "Percent of Culls", 6,6,"N", readOnlyLot) %><b>%</b>&nbsp;<%= ul.getCullsPercentError() %></td>
<%
   }else{
%>  
    <td class="td0114050203" style="text-align:right"><%= HTMLHelpersInput.inputBoxNumber("cullsPercent", ul.getCullsPercent(), "Percent of Culls", 6,6,"N", readOnlyLot) %><b>%</b><br>
    Cull amount should not be paid for</td> 
<%
   }
%>
   <td class="td04140102">&nbsp;</td>
  </tr>
<%
     }
     if (ul.getCrop().equals("GRAPE"))
     {
%>  	
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Brix of Received Fruit">Brix:</acronym></td>
   <td class="td03140102" colspan="4"><%= UpdRawFruitLot.buildDropDownGrapeBrix(ul.getOrganic(), ul.getBrix(), readOnlyLot) %></td>
   <td class="td04140102" colspan="3">&nbsp;</td>
  </tr>   
<% 
     }

   if (ul.getCrop().equals("GRAPE"))
   {
%>  

 <tr>
   <td class="td04140102" colspan = "9">
    <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td class="td04121405">
       <jsp:include page="updRawFruitLotGrape.jsp"></jsp:include> 
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>
    </table>   
   </td>   
  </tr>	 
<%  
   }else{
%>  
 <tr>
   <td class="td04140102" colspan = "9">
    <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td class="td04121405">
      <jsp:include page="updRawFruitLotMain.jsp"></jsp:include> 
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>
    </table>   
   </td>   
  </tr>	 
<%    
  }
  }
  int imageCount = 3;
  int expandCount = 1;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", ul.getUpdBean().getComments());  
%>  
 <tr>
   <td class="td04140102" colspan = "9">
    <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
      <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include> 
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>
    </table>   
   </td>   
  </tr>	     
</table>
 </form>

 <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>