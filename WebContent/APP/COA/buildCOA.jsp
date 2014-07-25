<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.coa.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%

//---------------- APP/COA/buildCOA.jsp -----------------------//
// Prototype:  Charlena Paschen  06/04/03 (jsp)
// Author   :  Teri Walton       11/05/03 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//  9/5/07     TWalton 		 Rewrite with Movex  - was listCOA.jsp
//------------------------------------------------------------//

  String errorPage = "COA/buildCOA.jsp";
  String listTitle = "Build a Certificate of Analysis";  
 // Bring in the Build View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
   BuildCOA buildCOA = new BuildCOA();
 try
 {
	buildCOA = (BuildCOA) request.getAttribute("buildViewBean");
	if (buildCOA.getEnvironment().equals("TST"))
	   listTitle = listTitle + " TST Environment";
	if (buildCOA.getRequestType().equals(""))
	   buildCOA.setRequestType("build");
	request.setAttribute("msg", buildCOA.getDisplayErrors());
	request.setAttribute("dateFormat", buildCOA.getBuildDateFormat());
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",listTitle);
   
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlCOANew?environment=" + buildCOA.getEnvironment() + "\">Build Another COA");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************
  int imageCount = 2;
  int expandCount = 0; 
%>
<html>
 <head>
   <title><%= listTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
   <%= JavascriptInfo.getExpandingSectionHead("Y", 5, "Y", 5) %>  
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
  //*********************************************************************
   String formName = "listCOAs";
   request.setAttribute("formName", formName);
 %>
   <table class="table01" cellspacing="0">
  <form  name = "<%= formName %>" action="/web/CtlCOANew?requestType=build" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", buildCOA.getEnvironment()) %>
   <tr class="tr02">
    <td style="width:5%">&nbsp;</td>
    <td class="td0520">
     <b>Build The COA:</b>
    </td>
    <td style="width:5%">&nbsp;</td>
   </tr>
<%
   if (buildCOA.getCoaType().trim().equals("DO"))
   {
%>   
   <tr class="tr00">
    <td>&nbsp;</td>
    <td>
     <jsp:include page="buildCOADistributionOrder.jsp"></jsp:include>
    </td>
    <td>&nbsp;</td>
   </tr>
<%
   }
   if (buildCOA.getCoaType().trim().equals("LOT"))
   {
%>
   <tr class="tr00">
    <td>&nbsp;</td>
    <td>
     <jsp:include page="buildCOALot.jsp"></jsp:include>
    </td>
    <td>&nbsp;</td>
   </tr>
<%      
   }
   if (buildCOA.getCoaType().trim().equals("CO"))
   {
%>   
   <tr class="tr00">
    <td>&nbsp;</td>
    <td>
     <jsp:include page="buildCOASalesOrder.jsp"></jsp:include>
    </td>
    <td>&nbsp;</td>
   </tr>
<%
   }
%>   
  </table>
<% 
  imageCount++;
  expandCount++;
%>
   <table style="width:100%">  
    <tr class="tr02">
     <td class="td03201404" style="width:100%">
      <%= JavascriptInfo.getExpandingSection("C", "Update Additional Information", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="buildCOAUpdate.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>     
   </table>  
   <%= HTMLHelpersInput.endForm() %>
<% 
  if (0 == 1)
  {
  imageCount++;
  expandCount++;
%>
   <table style="width:100%">  
    <tr class="tr02">
     <td class="td03201404" style="width:100%">
      <%= JavascriptInfo.getExpandingSection("C", "View Sales Order Comments", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="buildSalesOrderComments.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>     
   </table>     
<% 
   }
  imageCount++;
  expandCount++;
  request.setAttribute("imageCount", new Integer(imageCount));
  request.setAttribute("expandCount", new Integer(expandCount));
  if (buildCOA.getCoaType().equals("LOT"))
  {
%>
   <table style="width:100%">  
    <tr class="tr02">
     <td class="td03201404" style="width:100%">
      <%= JavascriptInfo.getExpandingSection("C", "View / Determine Attributes", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="buildCOALotDetail.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>     
   </table>        
<%  
  }
  else
  {
  
%>
   <table style="width:100%">  
    <tr class="tr02">
     <td class="td03201404" style="width:100%">
      <%= JavascriptInfo.getExpandingSection("C", "View Items / Determine Attributes", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="buildCOAOrderLineDetail.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>     
   </table>        
<%  
   }
   // Buttons at the Bottom of the screen
   StringBuffer sendTo = new StringBuffer();
   
   sendTo.append("&environment=" + buildCOA.getEnvironment() + 
   				 "&coaType=" + buildCOA.getCoaType());
   StringBuffer history = new StringBuffer();
   if (buildCOA.getCoaType().equals("LOT"))
   {
      sendTo.append("&inqLot=" + buildCOA.getInqLot());
      history.append("&lot=" + buildCOA.getInqLot());
   }
   if (buildCOA.getCoaType().equals("DO"))
      sendTo.append("&inqDistributionOrder=" + buildCOA.getInqDistributionOrder());
   if (buildCOA.getCoaType().equals("CO"))
   {
      sendTo.append("&inqSalesOrder=" +  buildCOA.getInqSalesOrder());
      history.append("&order=" + buildCOA.getInqSalesOrder());
   }
%>  
  <table class="table00" cellspacing="0">
   <tr>
    <td class="td04140502">
     <input type="button" value="Preview"
                 onClick="location.href='/web/CtlCOANew?requestType=preview<%= sendTo %>' ">
    
<%    
   if (!buildCOA.getBuildEmail1().trim().equals("") ||
       !buildCOA.getBuildEmail2().trim().equals("") ||
       !buildCOA.getBuildEmail3().trim().equals("") ||
       !buildCOA.getBuildEmail4().trim().equals("") ||
       !buildCOA.getBuildEmail5().trim().equals("") ||
       !buildCOA.getBuildEmail6().trim().equals("") ||
       !buildCOA.getBuildEmail7().trim().equals("") ||
       !buildCOA.getBuildEmail8().trim().equals(""))
   {
%>
           <input type="button" value="Send Email"
                 onClick="location.href='/web/CtlCOANew?requestType=email<%= sendTo %>'">
<%   
   } 
   if (!buildCOA.getBuildFax1().trim().equals("") ||
       !buildCOA.getBuildFax2().trim().equals("") ||
       !buildCOA.getBuildFax3().trim().equals("") ||
       !buildCOA.getBuildFax4().trim().equals(""))
   {
%>                 
            <input type="button" value="Send Fax"
                 onClick="location.href='/web/CtlCOANew?requestType=fax<%= sendTo %>'">   
<%
   }
   if (!buildCOA.getBuildEmail1().trim().equals("") ||
       !buildCOA.getBuildEmail2().trim().equals("") ||
       !buildCOA.getBuildEmail3().trim().equals("") ||
       !buildCOA.getBuildEmail4().trim().equals("") ||
       !buildCOA.getBuildEmail5().trim().equals("") ||
       !buildCOA.getBuildEmail6().trim().equals("") ||
       !buildCOA.getBuildEmail7().trim().equals("") ||
       !buildCOA.getBuildEmail8().trim().equals("") ||
       !buildCOA.getBuildFax1().trim().equals("") ||
       !buildCOA.getBuildFax2().trim().equals("") ||
       !buildCOA.getBuildFax3().trim().equals("") ||
       !buildCOA.getBuildFax4().trim().equals(""))
   { // Show the History if possible
   
%>
  <input type="button" value="Sending History"
                 onClick="location.href='/web/JSP/Email/emailList.jsp?type=<%= buildCOA.getCoaType() %><%= history.toString() %>'">           
<%
   }   
%>
        </td>
      </tr>
    </table>
<%
//   }
//   else
//   {
//      String msg = "This sales order(" + request.getParameter("soNumber") + "), is not valid or does not have any line items";
//   }
%>
   <jsp:include page="../../Include/footer.jsp"></jsp:include>
  </body>
</html>
