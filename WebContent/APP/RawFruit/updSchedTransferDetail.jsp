<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updSchedTransferDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  7/7/11
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//

//**************************************************************************//
 Vector listLoadDetails = new Vector();
 UpdScheduledFruit mainSched = new UpdScheduledFruit();
 Vector  listWhseLocation = new Vector();
 UpdScheduledFruit updSched  = new UpdScheduledFruit();
 String readOnlyDetail = "";
 int cntImageDetail = 0;
 int loadNumber = 0;
 try
 {
    // to be used for Drop Down lists
    mainSched = (UpdScheduledFruit) request.getAttribute("updViewBean");
    updSched = (UpdScheduledFruit) request.getAttribute("loadInformation");
    listLoadDetails = updSched.getListScheduledFruitDetail();
    cntImageDetail = new Integer((String) request.getAttribute("imageCount")).intValue();   
    if (mainSched.getRequestType().trim().equals("schedTransfer"))
       loadNumber = new Integer((String) request.getAttribute("loadNumber")).intValue();
    
  }catch(Exception e){
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
   for (int x = 0; x < new Integer(mainSched.getCountLinesPerLoad()).intValue(); x++)
   { 
      // Set the Sending Warehouse Information
      out.println((String) ((Vector) ((Vector) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(4)).elementAt(x)).elementAt(2));
      out.println((String) ((Vector) ((Vector) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(4)).elementAt(x)).elementAt(3));
      // Set the Crop Variety Information
      out.println((String) ((Vector) ((Vector) mainSched.getDddCropVarietyScreen().elementAt(loadNumber)).elementAt(x)).elementAt(2));
      out.println((String) ((Vector) ((Vector) mainSched.getDddCropVarietyScreen().elementAt(loadNumber)).elementAt(x)).elementAt(3));
   }
%>
   <%= (String) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(2) %>
   <%= (String) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(3) %>
 </head>
 <body onload="changerecLoc0(document.forms['schedTransferLoads'].recLoc0)">
 <table class="table00">
  <tr class = "tr00">
   <td colspan = "2">
    <table class="table00">
     <tr class = "tr00">   
      <td class="td04140102" style="width:1%">&nbsp;</td>
      <td class="td04140102"><b>Delivery Date and Time:&nbsp;</b></td>
      <td class="td04140102"><%= HTMLHelpersInput.inputBoxDate(("scheduledDeliveryDate" + loadNumber), updSched.getSchedDeliveryDate().getDateFormatMMddyyyySlash(), ("funcDate" + loadNumber), "N", readOnlyDetail) %>&nbsp;</td>
      <td class="td03140102"><%= HTMLHelpersInput.inputBoxTime2Sections(("ScheduledDeliveryTime" + loadNumber), "Delivery Time", updSched.getScheduledDeliveryTime()) %>&nbsp;<%= updSched.getScheduledDeliveryTimeError().trim() %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>M3 Distribution Order Number:&nbsp;</b></td>
      <td class="td03140102" colspan = "2"><%= HTMLHelpersInput.inputBoxNumber(("distributionOrder" + loadNumber), updSched.getDistributionOrder(), "M3 Distribution Order Number", 15,15,"N", readOnlyDetail) %>&nbsp;
        <%= updSched.getDistributionOrderError().trim() %>
      </td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Hauling Company:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= DropDownSingle.buildDropDown(mainSched.getDdHauler(), ("haulingCompany" + loadNumber), updSched.getHaulingCompany(), "Choose One", "E", readOnlyDetail) %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Load Type:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= DropDownSingle.buildDropDown(mainSched.getDdLoadType(), ("truckType" + loadNumber), updSched.getTruckType(), "Choose One", "N", readOnlyDetail) %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Location:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= (String) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(0) %></td>
     </tr>   
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Dock:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= (String) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(1) %></td>
     </tr>   
<%
   if (updSched.getRequestType().trim().equals("updSchedTransfer"))
   { // ONE OFF -- update of Transfered Load
%>     
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
<%
   }
%>     
    </table>  
   </td>
 </tr>
 <tr>  
   <td colspan = "2">  
    <table class="table00">
     <tr class = "tr02">
      <td class="td04140102" style="text-align:center; width:20%"><acronym title="Warehouse - M3"><b>Sending Location</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Dock Sent From"><b>Sending Dock</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="M3 Item Number"><b>Item Number</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Type of Fruit"><b>Crop</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Variety of the Fruit"><b>Variety</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Grade - the grade of the fruit // how good the fruit quality is"><b>Grade</b></acronym></td>
      <td class="td04140102" style="text-align:center; width:8%"><acronym title="Organic / Conventional / Baby Food"><b>Fruit Type</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Sticker Free Fruit"><b>Sticker<br>Free</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Orchard Run Fruit"><b>O/R</b></acronym></td>
      <td class="td04140102" style="text-align:center"><acronym title="Number of Bins to Schedule for this load"><b>Schedule<br>Bins</b></acronym></td>      
     </tr>
<%
   //**********************************************************************************************
   // will use this loop for ERROR messages -- ONLY if something was entered into the Schedule Bins
   // for loop on the details
   
   int count = 0;
   if (mainSched.getRequestType().trim().equals("updSchedTransfer") && listLoadDetails.size() > 0)
   {
      for (count = 0; count < listLoadDetails.size(); count++)
      {
       UpdScheduledFruitDetail thisRow = (UpdScheduledFruitDetail) listLoadDetails.elementAt(count);
%>
   <tr class = "tr00">  
    <td class="td04140102" style="text-align:center"><%= thisRow.getFromRecLocDescription().trim() %>&nbsp;(<%= thisRow.getFromRecLoc() %>)</td>
     <%= HTMLHelpersInput.inputBoxHidden(("fromRecLoc" + loadNumber + count), thisRow.getFromRecLoc()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("fromRecLocDescription" + loadNumber + count), thisRow.getFromRecLocDescription()) %>  
    <td class="td04140102" style="text-align:center"><%= thisRow.getFromDockDescription().trim() %>&nbsp;(<%= thisRow.getFromDock().trim() %>)</td>
     <%= HTMLHelpersInput.inputBoxHidden(("fromDock" + loadNumber + count), thisRow.getFromDock()) %> 
     <%= HTMLHelpersInput.inputBoxHidden(("fromDockDescription" + loadNumber + count), thisRow.getFromDockDescription()) %> 
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("itemNumber" + loadNumber + count), thisRow.getItemNumber(), "M3 Item Number", 15,15,"N", readOnlyDetail) %>
       &nbsp;<%= thisRow.getItemNumberError() %>
    </td>   
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getCropDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("crop" + loadNumber + count), thisRow.getCrop()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("cropDescription" + loadNumber + count), thisRow.getCropDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getVarietyDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("variety" + loadNumber + count), thisRow.getVariety()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("varietyDescription" + loadNumber + count), thisRow.getVarietyDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getGradeDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("grade" + loadNumber + count), thisRow.getGrade()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("gradeDescription" + loadNumber + count), thisRow.getGradeDescription()) %>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getOrganicDescription() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("organic" + loadNumber + count), thisRow.getOrganic()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("organicDescription" + loadNumber + count), thisRow.getOrganicDescription()) %>
     <td class="td04140102" style="text-align:center">
<%
   if (!thisRow.getStickerFree().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
     cntImageDetail++;
   }
%>         
      <%= HTMLHelpersInput.inputBoxHidden(("stickerFree" + loadNumber + count), thisRow.getStickerFree()) %>
     &nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("orchardRun" + loadNumber + count), thisRow.getOrchardRun(), readOnlyDetail) %>&nbsp;</td>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + loadNumber + count), thisRow.getBinQuantity(), "Bin Quantity", 5,5,"N", readOnlyDetail) %>
       &nbsp;<%= thisRow.getBinQuantityError() %></td>  
   </tr>
<%
     } //end of for
   } // end of if
   if (!updSched.getDisplayMessage().trim().equals(""))
   {
%>
    <tr class = "tr00">   
     <td class="td03160102" style="text-align:center" colspan = "10"> 
       <%= updSched.getDisplayMessage().trim() %><br>
       Please Try Again!!
     </td>
    </tr>  
<%
   }
//**********************************************************************************************************************************
//  Enter in the 5 Blank Lines per load,  for theTransfer sending Information   
   for (int x = 0; x < new Integer(mainSched.getCountLinesPerLoad()).intValue(); x++)
   {
%> 
   <tr class = "tr00">   
    <td class="td03140102" style="text-align:center"><%= (String) ((Vector) ((Vector) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(4)).elementAt(x)).elementAt(0) %></td>
    <td class="td04140102" style="text-align:center"><%= (String) ((Vector) ((Vector) ((Vector) mainSched.getDddReceivingLocationScreen().elementAt(loadNumber)).elementAt(4)).elementAt(x)).elementAt(1) %></td>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("itemNumber" + loadNumber + count), "", "M3 Item Number", 15,15,"N", readOnlyDetail) %></td>  
    <td class="td03140102" style="text-align:center"><%= (String) ((Vector) ((Vector) mainSched.getDddCropVarietyScreen().elementAt(loadNumber)).elementAt(x)).elementAt(0) %></td>
    <td class="td04140102" style="text-align:center"><%= (String) ((Vector) ((Vector) mainSched.getDddCropVarietyScreen().elementAt(loadNumber)).elementAt(x)).elementAt(1) %></td>
    <td class="td04140102" style="text-align:center"><%= DropDownSingle.buildDropDown(mainSched.getDdGrade(), ("grade" + loadNumber + count), "", "", "N", readOnlyDetail) %></td>
    <td class="td04140102" style="text-align:center"><%= DropDownSingle.buildDropDown(mainSched.getDdOrganic(), ("organic" + loadNumber + count), "", "", "N", readOnlyDetail) %></td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("stickerFree" + loadNumber + count), "", readOnlyDetail) %>&nbsp;</td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("orchardRun" + loadNumber + count), "", readOnlyDetail) %>&nbsp;</td>
    <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + loadNumber + count), "", "Bin Quantity", 5,5,"N", readOnlyDetail) %></td>  
   </tr>
<%
          count++;
   }// end of for 
//  End of the Blank Lines Per Load
//************************************************************************************************************************************
//   request.setAttribute("countDetail", (count + ""));
   request.setAttribute("imageCount", (cntImageDetail + ""));
%> 
    <%= HTMLHelpersInput.inputBoxHidden("countDetail", (count + "")) %>
    </table>
   </td>
  </tr>
<%
   if (mainSched.getRequestType().trim().equals("schedTransfer"))
   {
%>  
  <tr class = "tr00"> 
   <td class="td04140102" style="width:25%"><b>Comment:&nbsp;</b></td>  
   <td class="td04140102">  
      <%= HTMLHelpersInput.inputBoxTextarea(("comments" + loadNumber), "", 2, 75, 500, "N") %>
   </td> 
  </tr>  
<%
   }
%>  
 </table>

 </body>
</html>