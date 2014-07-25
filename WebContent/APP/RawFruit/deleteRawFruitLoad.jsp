<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.InqRawFruit" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------- deleteRawFruitLoad.jsp ------------------------------//
//  Author :  Teri Walton  2/24/09
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Delete a Raw Fruit Load (Scale Ticket)";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqRawFruit irf = new InqRawFruit();
 try
 {
	irf = (InqRawFruit) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",updTitle);
//*****************************************************************************
%>
<html>
   <head>
      <title><%= updTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
   </head>
   <body>
    <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <form name="updRawFruitLoad" action="/web/CtlRawFruit?requestType=deleteScaleTicket" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:5%">&nbsp;</td>
    <td>
     <table class="table00" cellspacing="0" style="width:100%">
      <tr class="tr02">
       <td class="td0418" style="width:5%">&nbsp;</td>
       <td class="td0418"><%= HTMLHelpers.buttonSubmit("deleteLoad", "Delete Load") %></td>
       <td class="td0418" style="text-align:right"><%= HTMLHelpers.buttonSubmit("getList", "Cancel - Return to List") %></td>
       <td class="td0418" style="width:5%">&nbsp;</td>
      </tr> 
<%
   // List all the HIDDEN fields -- all the Inquiry fields, to be able to go back to the List Page
%>       
       <%= HTMLHelpersInput.inputBoxHidden("inqReceivingDateFrom", irf.getInqReceivingDateFrom()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqReceivingDateTo", irf.getInqReceivingDateTo()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqScaleTicketFrom", irf.getInqScaleTicketFrom()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqScaleTicketTo", irf.getInqScaleTicketTo()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqWarehouseTicketFrom", irf.getInqWarehouseTicketFrom()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqWarehouseTicketTo", irf.getInqWarehouseTicketTo()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqCarrierFrom", irf.getInqCarrierFrom()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqCarrierTo", irf.getInqCarrierTo()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqCarrierBOLFrom", irf.getInqCarrierBOLFrom()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqCarrierBOLTo", irf.getInqCarrierBOLTo()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqFacility", irf.getInqFacility()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqLocation", irf.getInqWarehouse()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqFromLocation", irf.getInqFromLocation()) %>
       <%= HTMLHelpersInput.inputBoxHidden("inqBinBulk", irf.getInqBinBulk()) %>
       
      <tr class="tr00">
       <td class="td0418" rowspan="3">&nbsp;</td>
       <td class="td04180102"><b>Scale Ticket: </b></td>
       <td class="td04180102"><b><%= irf.getScaleTicket() %>
<%
   if (!irf.getScaleTicketCorrectionSequence().trim().equals("0"))
     out.println("-" + irf.getScaleTicketCorrectionSequence().trim());
%>   
       </b></td>
       <%= HTMLHelpersInput.inputBoxHidden("scaleTicket", irf.getScaleTicket()) %>
       <%= HTMLHelpersInput.inputBoxHidden("scaleTicketCorrectionSequence", irf.getScaleTicketCorrectionSequence()) %>       
       <td class="td0418" rowspan="3">&nbsp;</td>
      </tr>   
      <tr class="tr00">
       <td class="td04180102"><b>Receiving Date: </b></td>
       <td class="td04180102"><b><%= irf.getReceivingDate() %></b></td>
      </tr>    
     </table>
    </td>
    <td style="width:5%">&nbsp;</td>
   </tr>
  </table>
 </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>