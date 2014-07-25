<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.UpdAvailableFruit" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- updAvailFruitByWhse.jsp ------------------------------//
//  Author :  Teri Walton  08/06/10
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Update Available Fruit By Warehouse Location";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 String readOnlyMain = "Y";
 UpdAvailableFruit updAvail = new UpdAvailableFruit();
 Warehouse whseObject = new Warehouse();
 String updateDateTime = "";
 try
 {
	updAvail = (UpdAvailableFruit) request.getAttribute("updViewBean");
	whseObject = updAvail.getBeanAvailFruit().getAvailFruitByWhse().getWarehouse();
	
 }
 catch(Exception e)
 {
   System.out.println("should never get here, updAvailFruitByWhse.jsp: " + e);
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   Vector headerInfo = new Vector();
    headerInfo.addElement(updAvail.getEnvironment());
    headerInfo.addElement(updTitle); // Element 1 is always the Title of the Page
    
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=updAvailFruit&environment=" + updAvail.getEnvironment() + "\">Select Again");
   if (updAvail.getListLocation().size() > 1)
   {
      for (int x = 0; x < updAvail.getListLocation().size(); x++)
      {
          DropDownTriple ddt = (DropDownTriple) updAvail.getListLocation().elementAt(x);
          String locationNumber = ddt.getList3Value().trim();
          int xLastIndex = locationNumber.lastIndexOf("-*-");
		  if (xLastIndex > 0)
			  locationNumber = locationNumber.substring((xLastIndex + 3));
          if (!locationNumber.trim().equals(updAvail.getLocAddNo()))
          {
             setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=updAvailFruit&environment=" + updAvail.getEnvironment());
             setExtraOptions.append("&whseNo=" + updAvail.getWhseNo());
             setExtraOptions.append("&locAddNo=" + locationNumber + "\">");
             setExtraOptions.append("Go To Location: " + ddt.getList3Description());
          }
      }
   }   

   headerInfo.addElement(setExtraOptions.toString());
//*****************************************************************************
 String formName = "updAvail";
 request.setAttribute("formName", formName);
%>
<html>
 <head>
  <title><%= updTitle %></title>
  <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>   
  <%= JavascriptInfo.getNumericCheck() %>
  <%= JavascriptInfo.getRequiredField() %>
  <%= JavascriptInfo.getClickButtonOnlyOnce() %>
  <%= JavascriptInfo.getChangeSubmitButton() %>   
  <%= JavascriptInfo.getMoreButton() %>
 </head>
 <body>
  <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
  <form  name = "<%= formName %>" action="/web/CtlRawFruit?requestType=updAvailFruit" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", updAvail.getEnvironment()) %> 
  <%= HTMLHelpersInput.inputBoxHidden("whseNo", updAvail.getWhseNo()) %>  
  <%= HTMLHelpersInput.inputBoxHidden("locAddNo", updAvail.getLocAddNo()) %>
 <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!updAvail.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= updAvail.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
  <tr>
   <td class="td0414" style="width:5%">&nbsp;</td>
   <td class="td0424"><b><%= whseObject.getWarehouse() %>&nbsp;&nbsp;<%= updAvail.getWhseName() %>
      <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Location:&nbsp;<%= updAvail.getLocAddNo() %> &nbsp;&nbsp;<%= whseObject.getWarehouseDescription() %></b> </td>
   <td class="td0416" style="text-align:right">&nbsp;<b>Inventory Last Updated:&nbsp;<%= updAvail.getUpdateDate() %>&nbsp;&nbsp;&nbsp;<%= updAvail.getUpdateTime() %>&nbsp;&nbsp;<%= updAvail.getUpdateUser() %></b></td>
   <td class="td0414" style="width:5%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td0414" colspan="3" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes") %></td>
   <td class="td0414" style="width=5%">&nbsp;</td>
  </tr>
 </table>
 <%
  int imageCount  = 2;
  int expandCount = 1;
  request.setAttribute("imageCount", (imageCount + ""));  
  // Create Expandable Section for Available Fruit Detail information from Warehouse
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection("O", "Available Fruit Details", 0, expandCount, imageCount, 1, 0) %>
       <jsp:include page="updAvailFruitByWhseDetail.jsp"></jsp:include>  
       <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table> 
<%
  imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updAvail.getListComments());  
  request.setAttribute("imageCount", (imageCount + ""));  
  String imageopen = "C";
  if (updAvail.getListComments().size() > 0)
     imageopen = "O";
%>  
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr class="tr02">
    <td class="td03141405">
     <%= JavascriptInfo.getExpandingSection(imageopen, "Comments", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table>    
 </form>  
<%
  imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
  imageCount++;
  expandCount++;
  request.setAttribute("imageCount", (imageCount + ""));  
  // Create Expandable Section for Scheduled Loads for this specific Warehouse
%>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr class="tr02">
   <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection("O", "Scheduled Load Details", 0, expandCount, imageCount, 1, 0) %>
       <jsp:include page="listSchedFruitTable.jsp"></jsp:include>  
       <%= HTMLHelpers.endSpan() %>
   </td>
  </tr>
 </table>   
 <%= HTMLHelpers.pageFooterTable(updAvail.getRequestType()) %>

   </body>
</html>