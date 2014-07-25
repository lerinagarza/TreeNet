<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updSchedFruitLoadDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  10/11/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//

//**************************************************************************//
 Vector listLoadDetails = new Vector();
 UpdScheduledFruit updSched = new UpdScheduledFruit();
 UpdScheduledFruitDetail loadInfo = new UpdScheduledFruitDetail();
 String readOnlyDetail = "";
 int cntImageDetail = 0;
 try
 {
    updSched = (UpdScheduledFruit) request.getAttribute("updViewBean");
    listLoadDetails = updSched.getListScheduledFruitDetail();
    if (!updSched.getRequestType().trim().equals("addSchedFruitLoad"))
       loadInfo = (UpdScheduledFruitDetail) listLoadDetails.elementAt(0);
    cntImageDetail = new Integer((String) request.getAttribute("imageCount")).intValue();   
    //readOnlyDetail = updMain.getReadOnly();
//	readOnlyDetail = "Y";
  }catch(Exception e){
   // System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
   // request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
   System.out.println("Exception, " + e);
 }    
//--------------------------------------------------------
// Build data for Drop Down Dual options (10 rows)
%>
<html>
 <head>
    <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
<%
   for (int x = 0; x < updSched.getDddWhseLocationScreen().size(); x++)
   {
      out.println((String) ((Vector) updSched.getDddWhseLocationScreen().elementAt(x)).elementAt(2));
      out.println((String) ((Vector) updSched.getDddWhseLocationScreen().elementAt(x)).elementAt(3));
   }
   for (int x = 0; x < updSched.getDddCropVarietyScreen().size(); x++)
   {
      out.println((String) ((Vector) updSched.getDddCropVarietyScreen().elementAt(x)).elementAt(2));
      out.println((String) ((Vector) updSched.getDddCropVarietyScreen().elementAt(x)).elementAt(3));
   }
%>  
   <%= (String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(0)).elementAt(2) %>
   <%= (String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(0)).elementAt(3) %>
 </head>
 <body onload="changerecLoc(document.forms['schedLoads'].recLoc)">
 <table class="table00">
  <tr class = "tr02">
    <td class="td0424" ><acronym title="Schedule this load"><b>&nbsp;Load:&nbsp;&nbsp; <%= updSched.getLoadNumber() %>&nbsp;</b></acronym></td> 
    <td class="td0324" ><b>&nbsp;&nbsp;&nbsp; <%= updSched.getLoadReceivedFlag() %>&nbsp;</b></td> 
    <td class="td0424" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes") %>&nbsp;&nbsp;</td> 
  </tr>
  <tr class = "tr00">
   <td colspan = "3">
    <table class="table00">
     <tr class = "tr00">   
      <td class="td04140102" style="width:1%">&nbsp;</td>
      <td class="td04140102"><b>Delivery Date and Time:&nbsp;</b></td>
      <td class="td04140102"><%= HTMLHelpersInput.inputBoxDate("scheduledDeliveryDate", updSched.getScheduledDeliveryDate(), "funcDate", "N", readOnlyDetail) %>&nbsp;</td>
      <td class="td03140102"><%= HTMLHelpersInput.inputBoxTime2Sections("ScheduledDeliveryTime", "Delivery Time", updSched.getScheduledDeliveryTime()) %>&nbsp;<%= updSched.getScheduledDeliveryTimeError().trim() %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Hauling Company:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= DropDownSingle.buildDropDown(updSched.getDdHauler(), ("haulingCompany"), updSched.getHaulingCompany(), "Choose One", "E", readOnlyDetail) %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Load Type:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= DropDownSingle.buildDropDown(updSched.getDdLoadType(), ("truckType"), updSched.getTruckType(), "Choose One", "N", readOnlyDetail) %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Location:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= (String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(0)).elementAt(0) %></td>
     </tr>   
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Dock:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= (String) ((Vector) updSched.getDddReceivingLocationScreen().elementAt(0)).elementAt(1) %></td>
     </tr>   
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Date the person ENTERED the load information into the scheduling system"><b>Date Load was Entered:</b></acronym>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= updSched.getCreateDateDisplay() %>&nbsp;&nbsp;&nbsp;
      									   <%= updSched.getCreateTimeDisplay() %>&nbsp;&nbsp;&nbsp;
      									   <%= updSched.getCreateUserDisplay() %>
      </td>
     </tr>  
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Date the person LAST UPDATED the load information into the scheduling system"><b>Date Load was Last Updated:</b></acronym>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= updSched.getUpdateDate() %>&nbsp;&nbsp;&nbsp;
      									   <%= updSched.getUpdateTime() %>&nbsp;&nbsp;&nbsp;
      									   <%= updSched.getUpdateUser() %>
      </td>
     </tr>  
    </table>  
   </td>
 </tr>
 <tr>  
   <td colspan = "3">  
    <table class="table00">
     <tr class = "tr02">
<%
   if (0 == 1)
   {
%>     
      <td class="td04140102" style="text-align:center"><acronym title="Enter Pick up Date and Time"><b>PickUp Date/Time</b></acronym></td>
<%
   }
%>      
      <td class="td04140102" style="text-align:center; width:25%"><acronym title="Warehouse - M3 Supplier"><b>Whse</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Location of Warehouse"><b>Loc</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Type of Fruit"><b>Crop</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Variety of the Fruit"><b>Variety</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Grade - the grade of the fruit // how good the fruit quality is"><b>Grade</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Organic / Conventional / Baby Food"><b>Fruit Type</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Sticker Free Fruit"><b>Sticker<br>Free</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Orchard Run Fruit"><b>O/R</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Cash Fruit as opposed to Pool (Member) Fruit"><b>Cash<br>Fruit</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Cash Price - Entered at Price per Ton"><b>Cash<br>Price</b></acronym></td> 
      <td class="td04140102" style="text-align:center"><acronym title="Number of Bins to Schedule for this load"><b>Schedule<br>Bins</b></acronym></td>      
     </tr>
<%
   // for loop on the details
   int cnt = 0;
   if (listLoadDetails.size() > 0)
   {
      for (cnt = 0; cnt < listLoadDetails.size(); cnt++)
      {
       UpdScheduledFruitDetail thisRow = (UpdScheduledFruitDetail) listLoadDetails.elementAt(cnt);
%>
     <%// HTMLHelpersInput.inputBoxHidden(("scheduledPickupDate" + cnt), thisRow.getScheduledPickUpDate()) %>
     <%// HTMLHelpersInput.inputBoxHidden(("scheduledPickupTime" + cnt), thisRow.getScheduledPickUpTime()) %>  
   <tr class = "tr00">  
<%
   if (0 == 1)
   {
//    <td class="td04140102" style="text-align:center"> HTMLHelpersInput.inputBoxDate(("scheduledPickUpDate" + cnt), thisRow.getScheduledPickUpDate(), ("funcDate" + cnt), "N", readOnlyDetail) &nbsp;&nbsp;
//         HTMLHelpersInput.inputBoxTime2Sections(("scheduledPickUpTime" + cnt), "Pick Up Time", thisRow.getScheduledPickUpTime()) &nbsp; updSched.getScheduledDeliveryTimeError().trim() </td>
   }
%>        
    <td class="td04140102" style="text-align:center"><%= thisRow.getWhseName() %>&nbsp;(<%= thisRow.getWhseNo() %>)</td>
     <%= HTMLHelpersInput.inputBoxHidden(("whseNo" + cnt), thisRow.getWhseNo()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("whseName" + cnt), thisRow.getWhseName()) %>  
    <td class="td04140102" style="text-align:center"><%= thisRow.getWhseAddressName() %>&nbsp;(<%= thisRow.getLocAddNo().trim() %>)</td>
     <%= HTMLHelpersInput.inputBoxHidden(("locAddNo" + cnt), thisRow.getLocAddNo()) %> 
     <%= HTMLHelpersInput.inputBoxHidden(("whseAddressName" + cnt), thisRow.getWhseAddressName()) %> 
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getCropDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("crop" + cnt), thisRow.getCrop()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("cropDescription" + cnt), thisRow.getCropDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getVarietyDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("variety" + cnt), thisRow.getVariety()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("varietyDescription" + cnt), thisRow.getVarietyDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getGradeDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("grade" + cnt), thisRow.getGrade()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("gradeDescription" + cnt), thisRow.getGradeDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getOrganicDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("organic" + cnt), thisRow.getOrganic()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("organicDescription" + cnt), thisRow.getOrganicDescription()) %>
     <td class="td04140102" style="text-align:center">
<%
   if (!thisRow.getStickerFree().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
     cntImageDetail++;
   }
%>         
      <%= HTMLHelpersInput.inputBoxHidden(("stickerFree" + cnt), thisRow.getStickerFree()) %>
     &nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("orchardRun" + cnt), thisRow.getOrchardRun(), readOnlyDetail) %>&nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("memberCash" + cnt), thisRow.getMemberCash(), readOnlyDetail) %>&nbsp;</td>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("cashPrice" + cnt), thisRow.getCashPrice(), "Cash Price", 5,5,"N", readOnlyDetail) %>
       &nbsp;<%= thisRow.getCashPriceError() %></td>  
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + cnt), thisRow.getBinQuantity(), "Bin Quantity", 5,5,"N", readOnlyDetail) %>
       &nbsp;<%= thisRow.getBinQuantityError() %></td>  
   </tr>
<%
     } //end of for
   } // end of if
   for (int x = 0; x < 5; x++)
   {
%> 
   <tr class = "tr00">   
<%
   if (0 == 1)
   {
//    <td class="td04140102" style="text-align:center"> HTMLHelpersInput.inputBoxDate(("scheduledPickUpDate" + cnt), "", ("funcDate" + cnt), "N", readOnlyDetail) &nbsp;&nbsp;
//         HTMLHelpersInput.inputBoxTime2Sections(("scheduledPickUpTime" + cnt), "Pick Up Time", "") &nbsp; updSched.getScheduledDeliveryTimeError().trim() 
//    </td>
   }
%>    
    <td class="td03140102" style="text-align:center"><%= (String) ((Vector) updSched.getDddWhseLocationScreen().elementAt(x)).elementAt(0) %></td>
    <td class="td04140102" style="text-align:center"><%= (String) ((Vector) updSched.getDddWhseLocationScreen().elementAt(x)).elementAt(1) %></td>
    <td class="td03140102" style="text-align:center"><%= (String) ((Vector) updSched.getDddCropVarietyScreen().elementAt(x)).elementAt(0) %></td>
    <td class="td04140102" style="text-align:center"><%= (String) ((Vector) updSched.getDddCropVarietyScreen().elementAt(x)).elementAt(1) %></td>
    <td class="td04140102" style="text-align:center"><%= DropDownSingle.buildDropDown(updSched.getDdGrade(), ("grade" + cnt), "", "", "N", readOnlyDetail) %></td>
    <td class="td04140102" style="text-align:center"><%= DropDownSingle.buildDropDown(updSched.getDdOrganic(), ("organic" + cnt), "", "", "N", readOnlyDetail) %></td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("stickerFree" + cnt), "", readOnlyDetail) %>&nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("orchardRun" + cnt), "", readOnlyDetail) %>&nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("memberCash" + cnt), "", readOnlyDetail) %>&nbsp;</td>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("cashPrice" + cnt), "", "Cash Price", 5,5,"N", readOnlyDetail) %></td>  
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + cnt), "", "Bin Quantity", 5,5,"N", readOnlyDetail) %></td>  
   </tr>

<%
          cnt++;
   }// end of for 
   request.setAttribute("countDetail", (cnt + ""));
   request.setAttribute("imageCount", (cntImageDetail + ""));
%> 
    <%= HTMLHelpersInput.inputBoxHidden("countDetail", (cnt + "")) %>
    </table>
   </td>
  </tr>
 </table>

 </body>
</html>