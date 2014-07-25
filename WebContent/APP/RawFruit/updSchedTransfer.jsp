<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------  updSchedTransfer.jsp  ------------------------------------------//
// -- Decide if this is an UPDATE or an Add to the Schedule
// --  Will schedule the "TRANSFER" loads
//  -- Add will allow 5 loads to be scheduled for transfer at one time
//        will use ONE load on the Update
//    Author :  Teri Walton  7/7/11
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
 String updTitle = "Add / Schedule Transfer Loads";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdScheduledFruit updSchedFruit = new UpdScheduledFruit();
 Vector listLoads = new Vector();
 try
 {
	updSchedFruit = (UpdScheduledFruit) request.getAttribute("updViewBean");
	if (updSchedFruit.getListLoads() != null)
	   listLoads = updSchedFruit.getListLoads();
	if (updSchedFruit.getRequestType().trim().equals("updSchedTransfer"))
	   updTitle = "Update Scheduled Transfer Load " + updSchedFruit.getLoadNumber();
 }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Part of the Page
    Vector headerInfo = new Vector();
    headerInfo.addElement(updSchedFruit.getEnvironment());
    headerInfo.addElement(updTitle); // Element 0 is always the Title of the Page
    
   StringBuffer setExtraOptions = new StringBuffer();
//    if (updSchedFruit.getRequestType().trim().equals("updSchedFruit") ||
//     	updSchedFruit.getRequestType().trim().equals("addSchedFruitLoad"))
 //   {
  //     setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqSchedFruit&environment=" + updSchedFruit.getEnvironment().trim() + "\">Select Again");
//	   String parameters = updSchedFruit.getInqSched().buildParameterResend();  
//	   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=listSchedFruit&environment=" + updSchedFruit.getEnvironment().trim() + parameters + "\">Select From List");
 //   }
  //  else
   // {
	//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqAvailFruit&environment=" + updSchedFruit.getEnvironment().trim() + "\">Select Again");
	 //  String parameters = updSchedFruit.getInqAvail().buildParameterResend();  
	  // setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=listAvailFruit" + parameters + "\">Select From List");//
	//}
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqAvailFruit&environment=" + inqAvailFruit.getEnvironment().trim() + "\">Select Again");
   // comment the if statement for now, will need to add this to security
//   if (inqFormula.getSecurityLevel().trim().equals("2"))
//   {
//      setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=addFormula&environment=" + inqFormula.getEnvironment() + "\">Add a NEW Formula");
//   }
   headerInfo.addElement(setExtraOptions.toString());
					                              
//*****************************************************************************
  String formName = "schedTransferLoads";
  int imageCount  = 2;
  
%>
<html>
  <head>
    <title><%= updTitle %></title>
    <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
    <%= JavascriptInfo.getExpandingSectionHead("", 2, "Y", 2) %>
    <%= JavascriptInfo.getClickButtonOnlyOnce() %>
    <%= JavascriptInfo.getChangeSubmitButton() %>  
    <%= JavascriptInfo.getNumericCheck() %>
    <%= JavascriptInfo.getRequiredField() %> 
    <%= JavascriptInfo.getCalendarHead() %>
  </head>
  <body>
<%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
<%
  try{
%>  
  <form  name = "<%= formName %>" action="/web/CtlRawFruit?" method="post">
   <%= HTMLHelpersInput.inputBoxHidden("requestType", updSchedFruit.getRequestType().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("environment", updSchedFruit.getEnvironment().trim()) %> 
   <%= HTMLHelpersInput.inputBoxHidden("loadNumber", updSchedFruit.getLoadNumber().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("createDate", updSchedFruit.getCreateDate().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("createTime", updSchedFruit.getCreateTime().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("createUser", updSchedFruit.getCreateUser().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqRegion", updSchedFruit.getInqSched().getInqRegion().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqWhseNo", updSchedFruit.getInqSched().getInqWhseNo().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqLocAddNo", updSchedFruit.getInqSched().getInqLocAddNo().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqFacility", updSchedFruit.getInqSched().getInqFacility().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqRecLoc", updSchedFruit.getInqSched().getInqRecLoc().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqDock", updSchedFruit.getInqSched().getInqDock()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqHaulingCompany", updSchedFruit.getInqSched().getInqHaulingCompany().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqDeliveryDateFrom", updSchedFruit.getInqSched().getInqDeliveryDateFrom().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqDeliveryDateTo", updSchedFruit.getInqSched().getInqDeliveryDateTo().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqCrop", updSchedFruit.getInqSched().getInqCrop().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqVariety", updSchedFruit.getInqSched().getInqVariety().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqGrade", updSchedFruit.getInqSched().getInqGrade().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqOrganic", updSchedFruit.getInqSched().getInqOrganic().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqStickerFree", updSchedFruit.getInqSched().getInqStickerFree().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqShowPrice", updSchedFruit.getInqSched().getInqShowPrice().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqShowComments", updSchedFruit.getInqSched().getInqShowComments().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("originalRequestType", updSchedFruit.getInqSched().getOriginalRequestType().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqTransfer", updSchedFruit.getInqSched().getInqTransfer().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqShipFacility", updSchedFruit.getInqSched().getInqShipFacility()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqShipLoc", updSchedFruit.getInqSched().getInqShipLoc().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqShipDock", updSchedFruit.getInqSched().getInqShipDock()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqOrderBySupplier", updSchedFruit.getInqSched().getInqOrderBySupplier()) %>
   <%= HTMLHelpersInput.inputBoxHidden("inqCashFruit", updSchedFruit.getInqSched().getInqCashFruit()) %>
  
<%
 if (updSchedFruit.getRequestType().trim().equals("updSchedTransfer"))
 { // Update a specfic LOAD -- One load at a time
    request.setAttribute("imageCount", (imageCount + ""));
    request.setAttribute("loadNumber", updSchedFruit.getLoadNumber().trim());
    request.setAttribute("loadInformation", (UpdScheduledFruit) listLoads.elementAt(0));
%>
   <%= HTMLHelpersInput.inputBoxHidden("loadNumber", updSchedFruit.getLoadNumber().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("createDate", updSchedFruit.getCreateDate().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("createTime", updSchedFruit.getCreateTime().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("createUser", updSchedFruit.getCreateUser().trim()) %>
   <%= HTMLHelpersInput.inputBoxHidden("countLoads", "1") %>
  <table class="table00">
  <tr class = "tr02">
    <td class="td0424" ><acronym title="Schedule this Transfer load"><b>&nbsp;Load:&nbsp;&nbsp; <%= updSchedFruit.getLoadNumber() %>&nbsp;</b></acronym>
<%
   if (updSchedFruit.getRequestType().trim().equals("updSchedTransfer"))
     out.println("&nbsp;&nbsp;&nbsp;TRANSFER");
%>    
    </td> 
    <td class="td0424" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes") %>&nbsp;&nbsp;</td> 
  </tr>
 </table>   
   <jsp:include page="updSchedTransferDetail.jsp"></jsp:include> 
   
<%
   // this is where the Comment Section will display ONLY on the PER LOAD section
  int expandCount = 1;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updSchedFruit.getListComments());    
  imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();   
  imageCount++;
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>     
<%
 }else{
 //  Add Blank loads (without a load number) to be determined as we go
 //    Allow schedule of multiple loads at a time
  if (listLoads.size() > 0)
  {
    for (int x = 0; x < listLoads.size(); x++)
    {
       request.setAttribute("imageCount", (imageCount + ""));
       request.setAttribute("loadNumber", (x + ""));
       request.setAttribute("loadInformation", (UpdScheduledFruit) listLoads.elementAt(x));
%>
 <table class="table00">
  <tr class = "tr02">
    <td class="td0424" ><acronym title="Schedule this Transfer load"><b>&nbsp;Load:&nbsp;&nbsp; <%= (x + 1) %>&nbsp;</b></acronym></td> 
    <td class="td0424" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes") %>&nbsp;&nbsp;</td> 
  </tr>
 </table> 
 <jsp:include page="updSchedTransferDetail.jsp"></jsp:include> 
<%
       imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
    }
  } // end of if there are actual details
%>  
   <%= HTMLHelpersInput.inputBoxHidden("countLoads", (listLoads.size() + "")) %>
<%
   }
%>
  </form>
<%
if (updSchedFruit.getRequestType().trim().equals("updSchedTransfer"))
 {
%>
 <%= JavascriptInfo.getCalendarFoot(formName, "funcDate0", "scheduledDeliveryDate0") %>
<%
 }
 else
 {
   if (listLoads.size() > 0)
   {
      for (int x = 0; x < listLoads.size(); x++)
      {
%>  
   <%= JavascriptInfo.getCalendarFoot(formName, ("funcDate" + x), ("scheduledDeliveryDate" + x)) %>
<%
      }
   }
 }
  
  }catch(Exception e){
   // System.out.println(JSPExceptionMessages.PageExceptSystem(errorPage, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.PageExceptMsg(errorPage));
  }
%>
<%= HTMLHelpers.pageFooterTable("") %>
   </body>
</html>