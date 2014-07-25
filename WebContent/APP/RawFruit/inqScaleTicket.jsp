<%@ page language="java" %>
<%@ page import = "com.treetop.app.rawfruit.InqRawFruit" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%

//---------------- APP/RawFruit/inqScaleTicket.jsp -----------------------//
// Author   :  Teri Walton      2/18/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Find Raw Fruit Receiving Scale Tickets";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqRawFruit ir = new InqRawFruit();
 try
 {
	ir = (InqRawFruit) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",inqTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a Load");
   request.setAttribute("extraOptions", setExtraOptions.toString());     
//*****************************************************************************
String dd1name = "inqFacility";
String dd2name = "inqWarehouse";
String script = "";
String dd = "";
 try
 {
  Vector dualDD = InqRawFruit.buildDropDownFacilityWarehouse(ir.getInqFacility(), ir.getInqWarehouse());
   dd = (String) dualDD.elementAt(0);
   script = (String) dualDD.elementAt(1);
 }
 catch(Exception e)
 {} 
%>
<html>
 <head>
   <title><%= inqTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
   <%= JavascriptInfo.getCalendarHead() %>  
   <%= script %>
   <%= JavascriptInfo.getDualDropDown(dd1name, dd2name) %>  
 </head>
<%
   if (dd == null || dd.trim().equals(""))
   {
%>
 <body>
<%
   }
   else
   {
%>
 <body onload="change<%= dd1name %>(document.forms['inqScaleTicket'].<%= dd1name %>)">
<%
   }
%> 
 <jsp:include page="../../Include/heading.jsp"></jsp:include> 
  <form  name = "inqScaleTicket" action="/web/CtlRawFruit?requestType=listScaleTicket" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
    <td>
     <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!ir.getDisplayMessage().trim().equals(""))
   {
%>      
      <tr class="tr00">
       <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= ir.getDisplayMessage().trim() %></b></td>
      </tr>    
<%
   }
%>         
      <tr class="tr02">
       <td class="td0418" style="width:3%">&nbsp;</td>
       <td class="td0418" style="width:35%"><b> Selection Criteria:</b></td>
       <td class="td0418" style="text-align:right" colspan="3">
         <%= HTMLHelpers.buttonSubmit("getList", "Go - Retrieve List") %>
       </td>
       <td class="td0418" style="width:3%">&nbsp;</td>
      </tr>       
      <tr class="tr01">
       <td>&nbsp;</td>       
       <td class="td04160102"><b>&nbsp;</b></td>
       <td class="td04180102"><b>FROM</b></td>
       <td class="td04160102" style="width:3%">&nbsp;</td>
       <td class="td04180102"><b>TO</b></td>
       <td>&nbsp;</td>      
      </tr>     
      <tr>
       <td rowspan="13">&nbsp;</td>       
       <td class="td04160102"><b>Receiving Date:</b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxDateTypeOrChoose("inqReceivingDateFrom", ir.getInqReceivingDateFrom(), "getInqReceivingDateFrom", "N", "N") %></td>
       <td class="td04160102">&nbsp;</td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxDateTypeOrChoose("inqReceivingDateTo", ir.getInqReceivingDateTo(), "getInqReceivingDateTo", "N", "N") %></td>
       <td rowspan="13">&nbsp;</td>      
      </tr>
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Scale Ticket Number is given to each LOAD coming across the scale">Scale Ticket:</acronym></b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqScaleTicketFrom", ir.getInqScaleTicketFrom(), "Scale Ticket From", 10, 10, "N", "N") %></td>
       <td class="td04160102">&nbsp;</td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqScaleTicketTo", ir.getInqScaleTicketTo(), "Scale Ticket To", 10, 10, "N", "N") %></td>
      </tr>
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Warehouse Document Number - Number that came in from the Warehouse on the Load">Warehouse Document Number:</acronym></b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqWarehouseTicketFrom", ir.getInqWarehouseTicketFrom(), "Warehouse Document Number - FROM", 15, 25, "N", "N") %></td>
       <td class="td04160102">&nbsp;</td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqWarehouseTicketTo", ir.getInqWarehouseTicketTo(), "Warehouse Document Number - TO", 15, 25, "N", "N") %></td>
      </tr>    
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Carrier Number -- which in Movex (M3) is a Supplier">Carrier - Supplier Number:</acronym></b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqCarrierFrom", ir.getInqCarrierFrom(), "Carrier/Supplier Number - FROM", 10, 10, "N", "N") %></td>
       <td class="td04160102">&nbsp;</td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqCarrierTo", ir.getInqCarrierTo(), "Carrier/Supplier Number - TO", 10, 10, "N", "N") %></td>
      </tr>   
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Carrier BOL(Bill if Lading)">Carrier BOL:</acronym></b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqCarrierBOLFrom", ir.getInqCarrierBOLFrom(), "Carrier BOL Number - FROM", 10, 10, "N", "N") %></td>
       <td class="td04160102">&nbsp;</td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqCarrierBOLTo", ir.getInqCarrierBOLTo(), "Carrier BOL Number - TO", 10, 10, "N", "N") %></td>
      </tr>  
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Facility the LOAD was received at">Receiving Facility:</acronym></b></td>
       <td class="td04160102" colspan="3"><%= dd %></td>
      </tr>
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Warehouse the LOAD was received at">Receiving Warehouse:</acronym></b></td>
       <td class="td04160102" colspan="3"><select name="<%= dd2name %>"></td>
      </tr>
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="From Location - for freight">From Location:</acronym></b></td>
       <td class="td04160102" colspan="3"><%= InqRawFruit.buildDropDownFromLocation(ir.getInqFromLocation(), "N") %></td>
      </tr>  
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Lot Number">Lot Number:</acronym></b></td>
       <td class="td04160102" colspan="3"><%= HTMLHelpersInput.inputBoxText("inqLot", ir.getInqLot(), "Lot Number", 10, 10, "N", "N") %></td>
      </tr>     
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Supplier found on the Lot Number">Supplier Number:</acronym></b></td>
       <td class="td04160102" colspan="3"><%= HTMLHelpersInput.inputBoxText("inqSupplier", ir.getInqSupplier(), "Supplier Number", 10, 10, "N", "N") %></td>
      </tr>           
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Bin - Bulk OR Both">Kind of Load:</acronym></b></td>
       <td class="td04160102"><%= InqRawFruit.buildDropDownTypeOfLoad(ir.getInqBinBulk(), "N") %></td>
       <td class="td04160102" colspan="2">&nbsp;</td>
      </tr>          
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Load Number Assigned to the Scheduled Load">Scheduled Load Number:</acronym></b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxNumber("inqScheduledLoadNumber", ir.getInqScheduledLoadNumber(), "Scheduled Load Number", 10, 10, "N", "N") %></td>
       <td class="td04160102" colspan="2">&nbsp;</td>
      </tr>      
      <tr class="tr00">
       <td class="td04160102"><b><acronym title="Orchard Run Write Up Number">Write-Up #:</acronym></b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxText("inqWriteUpNumber", ir.getInqWriteUpNumber(), "Write Up Number", 8, 8, "N", "N") %></td>
       <td class="td04160102" colspan="2">&nbsp;</td>
      </tr>                
     </table>
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table> 
  </form>  
  <br>  
  <%= JavascriptInfo.getCalendarFoot("inqScaleTicket", "getInqReceivingDateTo", "inqReceivingDateTo") %>
  <%= JavascriptInfo.getCalendarFoot("inqScaleTicket", "getInqReceivingDateFrom", "inqReceivingDateFrom") %>
   <jsp:include page="../../Include/footer.jsp"></jsp:include> 
   </body>
</html>