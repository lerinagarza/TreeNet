<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updSchedFruitDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  9/8/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector listLoadDetails = new Vector();
 UpdScheduledFruit updSched = new UpdScheduledFruit();
 UpdScheduledFruitDetail loadInfo = new UpdScheduledFruitDetail();
 String readOnlyDetail = "";
 int loadNumber = 0;
 try
 {
    updSched = (UpdScheduledFruit) request.getAttribute("updViewBean");
    listLoadDetails = (Vector) request.getAttribute("listLoadDetails");
    loadNumber = new Integer((String) request.getAttribute("loadNumber")).intValue();
    loadInfo = (UpdScheduledFruitDetail) listLoadDetails.elementAt(0);
    //readOnlyDetail = updMain.getReadOnly();
//	readOnlyDetail = "Y";
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//--------------------------------------------------------
// Build data for Drop Down Dual options (10 rows)
%>
<html>
 <head>
<% 
   for (int x = 0; x < updSched.getDddReceivingLocationScreen().size(); x++)
   {
      out.println((String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(2));
      out.println((String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(3)); 
   }
%>
 </head>
 <body>
 <table class="table00">
<%
   if (loadNumber == 0 ||
       loadNumber == 3 ||
       loadNumber == 6 ||
       loadNumber == 9)
   {
%> 
  <tr class = "tr01">
    <td class="td0014" colspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes") %>&nbsp;&nbsp;</td> 
  </tr>
<%
   }
%>  
  <tr class = "tr05">
    <td class="td0024" colspan="2" style="text-align:center"><acronym title="Schedule this load"><b>&nbsp;Load&nbsp;&nbsp; <%= (loadNumber + 1) %>&nbsp;</b></acronym></td> 
  </tr>
  <tr class = "tr00">
   <td style="width:25%" valign="top">
    <table class="table00">
     <tr class = "tr00">   
      <td class="td04140102" style="width:1%">&nbsp;</td>
      <td class="td04140102"><b>Delivery Date and Time:&nbsp;</b></td>
      <td class="td04140102"><%= HTMLHelpersInput.inputBoxDate(("scheduledDeliveryDate" + loadNumber), updSched.getScheduledDeliveryDate(), ("funcDate" + loadNumber), "N", readOnlyDetail) %>&nbsp;</td>
      <td class="td03140102"><%= HTMLHelpersInput.inputBoxTime2Sections(("scheduledDeliveryTime" + loadNumber), "Delivery Time", updSched.getScheduledDeliveryTime()) %>&nbsp;<%= updSched.getScheduledDeliveryTimeError() %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Hauling Company:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= DropDownSingle.buildDropDown(updSched.getDdHauler(), ("haulingCompany" + loadNumber), updSched.getHaulingCompany(), "Choose One", "E", readOnlyDetail) %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Load Type:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= DropDownSingle.buildDropDown(updSched.getDdLoadType(), ("truckType" + loadNumber), updSched.getTruckType(), "Choose One", "N", readOnlyDetail) %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Location:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= (String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(0) %></td>
     </tr>   
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Dock:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= (String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(1) %></td>
     </tr>    
    </table>  
   </td>
   <td>  
    <table class="table00" style="valign:top">
<%
   // for loop on the details
   if (listLoadDetails.size() > 0)
   {
      String whse = "";
      String loc = "";
      Vector loadRecords = new Vector();
      for (int cnt = 0; cnt < listLoadDetails.size(); cnt++)
      {
        if (cnt == 0)
        {
   // will only show headings on the first one
%>    
     <tr class = "tr02">
      <td class="td04140102" style="text-align:center"><acronym title="Type of Fruit"><b>Crop</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Variety of the Fruit"><b>Variety</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Grade - the grade of the fruit // how good the fruit quality is"><b>Grade</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Organic / Conventional / Baby Food"><b>Fruit Type</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Sticker Free Fruit"><b>Sticker Free</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Orchard Run Fruit"><b>O/R</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Is this a Cash Load, as opposed to a Pool Load"><b>Cash Fruit</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Cash Price - Entered at Price per Ton"><b>Cash<br>Price</b></acronym></td> 
      <td class="td04140102" style="text-align:center"><acronym title="Balance of Bins to Schedule -- Difference between the Available Fruit and the Scheduled Loads, Pounds and Tons will be calculated if needed"><b>Balance</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Number of Bins to Schedule for this load"><b>Schedule<br>Bins</b></acronym></td>      
     </tr>
<%
       }
       UpdScheduledFruitDetail thisRow = (UpdScheduledFruitDetail) listLoadDetails.elementAt(cnt);
       if (!thisRow.getWhseNo().trim().equals(whse) ||
           !thisRow.getLocAddNo().trim().equals(loc))
       {
         whse = thisRow.getWhseNo().trim();
         loc = thisRow.getLocAddNo().trim();
         loadRecords.addElement("" + cnt);
%>    
     <tr class = "tr01">
      <td class="td04140102" colspan="4"><acronym title="Warehouse - M3 Supplier"><b>Warehouse:&nbsp;</b></acronym><%= thisRow.getWhseNo() %>&nbsp;&nbsp;<%= thisRow.getWhseName().trim() %></td>
      <td class="td04140102" colspan="6"><acronym title="Location of Warehouse"><b>Location:&nbsp;</b></acronym><%= thisRow.getLocAddNo() %>&nbsp;&nbsp;<%= thisRow.getWhseAddressName().trim() %></td>
     </tr>
<%
          if (0 == 1){ // Will not show the Pickup Date and Time information
%>     
     <tr class = "tr01">   
      <td class="td04140102" colspan = "4"><b>PickUp Date and Time:&nbsp;</b></td>
      <td class="td04140102" colspan = "5"><%= HTMLHelpersInput.inputBoxDate(("scheduledPickUpDate" + loadNumber + cnt), thisRow.getScheduledPickUpDate(), ("funcDate" + loadNumber + cnt), "N", readOnlyDetail) %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= HTMLHelpersInput.inputBoxTime2Sections(("scheduledPickUpTime" + loadNumber + cnt), "Scheduled Pick Up Time", thisRow.getScheduledPickUpTime()) %>&nbsp;<%= thisRow.getScheduledPickUpTimeError() %></td>
     </tr>
<%   
          }
       }
       // Will need to add options for pickup DATE and time
%>
     <%= HTMLHelpersInput.inputBoxHidden(("scheduledPickupDate" + loadNumber + cnt), thisRow.getScheduledPickUpDate()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("scheduledPickupTime" + loadNumber + cnt), thisRow.getScheduledPickUpTime()) %>  
   <tr class = "tr00">   
     <%= HTMLHelpersInput.inputBoxHidden(("whseNo" + loadNumber + cnt), thisRow.getWhseNo()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("whseName" + loadNumber + cnt), thisRow.getWhseName()) %>  
     <%= HTMLHelpersInput.inputBoxHidden(("locAddNo" + loadNumber + cnt), thisRow.getLocAddNo()) %> 
     <%= HTMLHelpersInput.inputBoxHidden(("whseAddressName" + loadNumber + cnt), thisRow.getWhseAddressName()) %> 
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getCropDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("crop" + loadNumber + cnt), thisRow.getCrop()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("cropDescription" + loadNumber + cnt), thisRow.getCropDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getVarietyDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("variety" + loadNumber + cnt), thisRow.getVariety()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("varietyDescription" + loadNumber + cnt), thisRow.getVarietyDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getGradeDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("grade" + loadNumber + cnt), thisRow.getGrade()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("gradeDescription" + loadNumber + cnt), thisRow.getGradeDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getOrganicDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("organic" + loadNumber + cnt), thisRow.getOrganic()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("organicDescription" + loadNumber + cnt), thisRow.getOrganicDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;
<%
   if (!thisRow.getStickerFree().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
   }
%>    
        <%= HTMLHelpersInput.inputBoxHidden(("stickerFree" + loadNumber + cnt), thisRow.getStickerFree()) %>
    </td> 
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("orchardRun" + loadNumber + cnt), thisRow.getOrchardRun(), readOnlyDetail) %>&nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("memberCash" + loadNumber + cnt), thisRow.getMemberCash(), readOnlyDetail) %>&nbsp;</td>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("cashPrice" + loadNumber + cnt), thisRow.getCashPrice(), "Cash Price", 5,5,"N", readOnlyDetail) %>
       &nbsp;<%= thisRow.getCashPriceError() %></td>  
    <td class="td04140102" style="text-align:right"><%= thisRow.getBalanceBins() %></td>  
     <%= HTMLHelpersInput.inputBoxHidden(("balanceBins" + loadNumber + cnt), thisRow.getBalanceBins()) %> 
     <%= HTMLHelpersInput.inputBoxHidden(("availableBins" + loadNumber + cnt), thisRow.getAvailableBins()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("scheduledBins" + loadNumber + cnt), thisRow.getScheduledBins()) %>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + loadNumber + cnt), thisRow.getBinQuantity(), "Bin Quantity", 5,5,"N", readOnlyDetail) %>
       &nbsp;<%= thisRow.getBinQuantityError() %></td>  
   </tr>
<%
     }
     request.setAttribute("countValues", loadRecords);
   }
%>     
    </table>
   </td>
  </tr>
  <tr class = "tr00"> 
   <td class="td04140102"><b>Comment:&nbsp;</b></td>  
   <td class="td04140102">  
      <%= HTMLHelpersInput.inputBoxTextarea(("comments" + loadNumber), "", 2, 75, 500, "N") %>
   </td> 
  </tr>
 </table>

 </body>
</html>