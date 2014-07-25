<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- dtlSchedFruit.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/16/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 String dtlTitle = "Detail of Scheduled Load";  
 Vector listLoadDetails = new Vector();
 DtlScheduledFruit dtlSched = new DtlScheduledFruit();
 ScheduledLoadDetail loadInfo = new ScheduledLoadDetail();
 String readOnlyDetail = "";
 try
 {
    dtlSched = (DtlScheduledFruit) request.getAttribute("dtlViewBean");
    loadInfo = (ScheduledLoadDetail) dtlSched.getBeanAvailFruit().getScheduledLoadDetail().elementAt(0);
    if (loadInfo.getTransferFlag().trim().equals("Y"))
       dtlTitle = "Detail of Transfer Load";
    dtlTitle = dtlTitle + "&nbsp;" + loadInfo.getScheduleLoadNo();
 }
 catch(Exception e)
 {
   // System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
   // request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
   System.out.println("Exception, " + e);
 }    
//**************************************************************************//
  // Allows the Title to display in the Top Part of the Page
    Vector headerInfo = new Vector();
    headerInfo.addElement(dtlSched.getEnvironment());
    headerInfo.addElement(dtlTitle); // Element 0 is always the Title of the Page
    
//   StringBuffer setExtraOptions = new StringBuffer();
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqAvailFruit&environment=" + updSchedFruit.getEnvironment().trim() + "\">Select Again");
//   String parameters = updSchedFruit.getInqAvail().buildParameterResend();  
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=listAvailFruit" + parameters + "\">Select From List");
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqAvailFruit&environment=" + inqAvailFruit.getEnvironment().trim() + "\">Select Again");
   // comment the if statement for now, will need to add this to security
//   if (inqFormula.getSecurityLevel().trim().equals("2"))
//   {
//      setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=addFormula&environment=" + inqFormula.getEnvironment() + "\">Add a NEW Formula");
//   }
//   headerInfo.addElement(setExtraOptions.toString());
%>
<html>
 <head>
  <title><%= dtlTitle %></title>
   <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
   <%= JavascriptInfo.getExpandingSectionHead("", 2, "Y", 2) %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>     
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getMoreButton() %>
 </head>
 <body>
 <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
 <table class="table00">
<%   
   if (dtlSched.getRequestType().trim().equals("deleteSchedLoad") ||
       dtlSched.getRequestType().trim().equals("cancelSchedLoad") ||
       dtlSched.getRequestType().trim().equals("receiveSchedLoad") ||
       dtlSched.getRequestType().trim().equals("unreceiveSchedLoad"))
   {
%>
 <form  name = "fromDetail" action="/web/CtlRawFruit?requestType=<%= dtlSched.getRequestType().trim() %>" method="post">
     <%= HTMLHelpersInput.inputBoxHidden("environment", dtlSched.getEnvironment()) %> 
     <%= HTMLHelpersInput.inputBoxHidden("loadNumber", dtlSched.getLoadNumber()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqRegion", dtlSched.getInqSched().getInqRegion().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqWhseNo", dtlSched.getInqSched().getInqWhseNo().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqLocAddNo", dtlSched.getInqSched().getInqLocAddNo().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqFacility", dtlSched.getInqSched().getInqFacility().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqRecLoc", dtlSched.getInqSched().getInqRecLoc().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqDock", dtlSched.getInqSched().getInqDock().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqHaulingCompany", dtlSched.getInqSched().getInqHaulingCompany().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqDeliveryDateFrom", dtlSched.getInqSched().getInqDeliveryDateFrom().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqDeliveryDateTo", dtlSched.getInqSched().getInqDeliveryDateTo().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqStatus", dtlSched.getInqSched().getInqStatus().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqCrop", dtlSched.getInqSched().getInqCrop().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqVariety", dtlSched.getInqSched().getInqVariety().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqGrade", dtlSched.getInqSched().getInqGrade().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqOrganic", dtlSched.getInqSched().getInqOrganic().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqShowPrice", dtlSched.getInqSched().getInqShowPrice().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqStickerFree", dtlSched.getInqSched().getInqStickerFree().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqShowComments", dtlSched.getInqSched().getInqShowComments().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("originalRequestType", dtlSched.getOriginalRequestType()) %>   
     <%= HTMLHelpersInput.inputBoxHidden("inqScheduledLoadNumber", dtlSched.getInqSched().getInqScheduledLoadNumber().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqTransfer", dtlSched.getInqSched().getInqTransfer().trim()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqOrderBySupplier", dtlSched.getInqSched().getInqOrderBySupplier()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqCashFruit", dtlSched.getInqSched().getInqCashFruit()) %>
  <tr class = "tr02">
   <td class="td0424" ><acronym title="Schedule this load"><b>&nbsp;Load:&nbsp;&nbsp; <%= loadInfo.getScheduleLoadNo() %>&nbsp;</b></acronym>
<%
   if (loadInfo.getTransferFlag().trim().equals("Y"))
      out.println("&nbsp;&nbsp;TRANSFER");
%>   
   </td> 
   <td class="td0324" ><b>&nbsp;&nbsp;&nbsp; <%= InqScheduledFruit.getLongLoadReceivedFlag(loadInfo.getLoadReceivedFlag()) %>&nbsp;</b></td> 
   <td class="td0414" style="text-align:right">
<%
     if (dtlSched.getRequestType().trim().equals("deleteSchedLoad"))
     {
%>     
     <%= HTMLHelpers.buttonSubmitRight("deleteLoad", "Confirm Deletion of Load") %>&nbsp;&nbsp;&nbsp;
<%    
     }
     if (dtlSched.getRequestType().trim().equals("cancelSchedLoad"))
     {
 %>     
     <%= HTMLHelpers.buttonSubmitRight("cancelLoad", "Confirm Cancellation of Load") %>&nbsp;&nbsp;&nbsp;
<%    
     } 
     if (dtlSched.getRequestType().trim().equals("receiveSchedLoad"))
     {
 %>     
     <%= HTMLHelpers.buttonSubmitRight("receiveLoad", "Load has been Received") %>&nbsp;&nbsp;&nbsp;
<%    
     } 
      if (dtlSched.getRequestType().trim().equals("unreceiveSchedLoad"))
     {
%>     
     <%= HTMLHelpers.buttonSubmitRight("unreceiveLoad", "Load has NOT been Received") %>&nbsp;&nbsp;&nbsp;
<%    
     } 
%>   
   </td>
  </tr>
<%   
     if (dtlSched.getRequestType().trim().equals("receiveSchedLoad"))
     {
%>
  <tr class = "tr00"> 
   <td class="td04140102"><b>Empty Bins Loaded Back Onto Truck:&nbsp;</b></td>  
   <td class="td04140102" colspan = "2">  
      <%= HTMLHelpersInput.inputBoxNumber("emptyBins", loadInfo.getEmptyBinCount(), "", 5, 5, "N", "N") %>
   </td> 
  </tr>  
<%
     }
     if (dtlSched.getRequestType().trim().equals("cancelSchedLoad") ||
        dtlSched.getRequestType().trim().equals("receiveSchedLoad") ||
        dtlSched.getRequestType().trim().equals("unreceiveSchedLoad"))
     {
%>
  <tr class = "tr00"> 
   <td class="td04140102"><b>Comment:&nbsp;</b></td>  
   <td class="td04140102" colspan = "2">  
      <%= HTMLHelpersInput.inputBoxTextarea("comment", "", 2, 75, 500, "N") %>
   </td> 
  </tr>  
<%
     }
%>
  <%= HTMLHelpersInput.endForm() %>
<%
   }else{
%> 
  <tr class = "tr02">
   <td class="td0424" ><acronym title="Schedule this load"><b>&nbsp;Load:&nbsp;&nbsp; <%= loadInfo.getScheduleLoadNo() %>&nbsp;</b></acronym>
<%
   if (loadInfo.getTransferFlag().trim().equals("Y"))
      out.println("&nbsp;&nbsp;TRANSFER");
%>    
   </td> 
   <td class="td0324" ><b>&nbsp;&nbsp;&nbsp; <%= InqScheduledFruit.getLongLoadReceivedFlag(loadInfo.getLoadReceivedFlag()) %>&nbsp;</b></td> 
   <td class="td0414" style="text-align:right">&nbsp;
<%
    String readOnly = "Y";
     if (!loadInfo.getLoadReceivedFlag().trim().equals(""))
        readOnly = "N";    
     String sendOriginal = "&originalRequestType=dtlSchedFruit";
     out.println(InqScheduledFruit.buildMoreButton(dtlSched.getRequestType(), dtlSched.getEnvironment(), loadInfo.getScheduleLoadNo(), "", "", sendOriginal, readOnly, loadInfo.getTransferFlag().trim())); 
%>   
   </td>
  </tr> 
<%
   }
%>
  <tr class = "tr00">
   <td colspan = "3">
    <table class="table00">
     <tr class = "tr00">   
      <td class="td04140102" style="width:1%">&nbsp;</td>
      <td class="td04140102"><acronym title="This is the Date and Time the Load SHOULD be delivered">
         <b>Scheduled Delivery Date and Time:&nbsp;</b></acronym></td>
      <td class="td04140102"><%= InqScheduledFruit.formatDate(loadInfo.getScheduledDeliveryDate()) %>&nbsp;&nbsp;<%= InqScheduledFruit.formatTime(loadInfo.getScheduledDeliveryTime() + "00") %></td>
      <td class="td03140102">&nbsp;</td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102" style="width:1%">&nbsp;</td>
      <td class="td04140102"><acronym title="This is the Date and Time the Load WAS Marked as Received">
         <b>Actual Delivery Date and Time:&nbsp;</b></acronym></td>
      <td class="td04140102">
<%
   if (!loadInfo.getActualDeliveryDate().trim().equals("0") &&
       !loadInfo.getActualDeliveryDate().trim().equals(""))
   {
      out.println(InqScheduledFruit.formatDate(loadInfo.getActualDeliveryDate()) + "&nbsp;&nbsp;");
      if (!loadInfo.getActualDeliveryTime().trim().equals("0") &&
          !loadInfo.getActualDeliveryTime().trim().equals(""))
        out.println(InqScheduledFruit.formatTime(loadInfo.getActualDeliveryTime() + "00"));
   }
%>      
      &nbsp;</td>
      <td class="td03140102">&nbsp;</td>
     </tr>
<%
   if (loadInfo.getTransferFlag().trim().equals("Y"))
   {
%>     
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>M3 Distribution Order Number:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= loadInfo.getDistributionOrder().trim() %>&nbsp;</td>
     </tr>     
<%
   }
%>     
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Hauling Company:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= loadInfo.getHaulingCompany() %>&nbsp;-&nbsp;<%= loadInfo.getHaulingCompanyName() %></td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Load Type:&nbsp;</b></td>
      <td class="td04140102" colspan = "2"><%= loadInfo.getLoadType() %>&nbsp;</td>
     </tr>
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Location:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= loadInfo.getReceivingLocationNo() %>&nbsp;-&nbsp;<%= loadInfo.getReceivingLocationDesc() %></td>
     </tr>
<%
  if (!loadInfo.getReceivingDockNo().trim().equals(""))
  {
%>     
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><b>Receiving Dock:</b>&nbsp;</td>
      <td class="td04140102" colspan = "2"><%= loadInfo.getReceivingDockNo() %>&nbsp;-&nbsp;<%= loadInfo.getReceivingDockDesc() %></td>
     </tr>        
<%
   }
%>     
     <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="WHO and When this load was created"><b>Date this Load was Created (Originally):&nbsp;</b></acronym></td>
      <td class="td04140102"><%= InqScheduledFruit.formatDate(loadInfo.getCreateDate()) %>&nbsp;&nbsp;<%= InqScheduledFruit.formatTime(loadInfo.getCreateTime() + "00") %>&nbsp;&nbsp;<%= InqAvailableFruit.longUser(dtlSched.getEnvironment(), loadInfo.getCreateUser()) %></td>
     </tr>    
      <tr class = "tr00">   
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="WHO and When this load was last updated"><b>Date this Load was Last Updated:&nbsp;</b></acronym></td>
      <td class="td04140102"><%= InqScheduledFruit.formatDate(loadInfo.getChangeDate()) %>&nbsp;&nbsp;<%= InqScheduledFruit.formatTime(loadInfo.getChangeTime() + "00") %>&nbsp;&nbsp;<%= InqAvailableFruit.longUser(dtlSched.getEnvironment(), loadInfo.getChangeUser()) %></td>
     </tr>    
    </table>  
   </td>
  </tr>
<%
   // Detail Table 
   // Image Count... need to set the count for the page to display correctly
   int imageCount = 2;
   request.setAttribute("imageCount", (imageCount + ""));
%>  
  <tr class = "tr00">
   <td colspan = "3">
    <jsp:include page="dtlSchedFruitDetail.jsp"></jsp:include> 
   </td>
  </tr> 
 </table>  
<%
 // Comment Section
  if (dtlSched.getListComments() != null &&
      dtlSched.getListComments().size() > 0)
  {  
    try{
    imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
    }catch(Exception e)
    {}
    int expandCount = 1;
    request.setAttribute("screenType", "detail");
    request.setAttribute("longFieldType", "comment");
    request.setAttribute("listKeyValues", dtlSched.getListComments());
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 0) %>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>
<%
   }
%>  
<%= HTMLHelpers.pageFooterTable("") %>
   </body>
</html>