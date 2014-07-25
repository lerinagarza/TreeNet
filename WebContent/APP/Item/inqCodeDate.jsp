<%@ page language = "java" %>
<%@ page import = "com.treetop.app.item.InqCodeDate" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- inqCodeDate.jsp ------------------------------//
//  Author :  Teri Walton  12/29/03
//
//  CHANGES:
//
//    Date        Name      Comments
//  ---------   --------   -------------
//  10/15/08    TWalton     Change look and feel of page (new Stylesheet, Standards)
//   3/03/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------//
  String inqTitle = "Code Date Generator";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqCodeDate icd = new InqCodeDate();
 try
 {
	icd = (InqCodeDate) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
 }  
 String dd1name = "inqFacility";
 String dd2name = "inqWarehouse";
 String script = "";
 String dd = "";
 try
 {
  Vector dualDD = icd.buildDropDownFacilityWarehouse();
   dd = (String) dualDD.elementAt(0);
   script = (String) dualDD.elementAt(1);
 }
 catch(Exception e)
 {}
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",inqTitle);
//*****************************************************************************
%>
<html>
   <head>
      <title>Code Date Inquiry</title>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getCalendarHead() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
   <%= script %>
   <%= JavascriptInfo.getDualDropDown(dd1name, dd2name) %>  
   </head>
   <body onload="change<%= dd1name %>(document.forms['codeDateInfo'].<%= dd1name %>)">
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form name="codeDateInfo" action="/web/CtlCodeDate?requestType=detail" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!icd.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= icd.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
       <tr class="tr02">
        <td class="td0418" style="width:5%">&nbsp;</td>
        <td class="td0418">
         <b>Choose:&nbsp;&nbsp;&nbsp;<font style="color:#990000"><i>All Fields are Required!</i></font>
        </td>
        <td class="td0418" style="text-align:center">
          <%= HTMLHelpers.buttonGo("") %>
        </td>
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td rowspan="8">&nbsp;</td>       
        <td class="td04160102"><b>Date:</b></td>
        <td class="td03160102">
		  <%= HTMLHelpersInput.inputBoxDate("inqDateSelected",
	                        icd.getInqDateSelected(),
	                        "getDateInfo", "N", "N") %>
        </td>
        <td rowspan="8">&nbsp;</td>      
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Facility:</b></td>
        <td class="td03160102">
         <%= dd %>
        </td>
       </tr> 
       <tr class="tr00">
        <td class="td04160102"><b>Production Warehouse:</b></td>
        <td class="td03160102">
         <select name="<%= dd2name %>">
		</td>
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Choose a Valid Item Number:</b></td>
        <td class="td03160102">
		 <%= HTMLHelpersInput.inputBoxText("inqItem", icd.getInqItem(), "Item Number", 15, 15, "N", "N") %>
		    <b>&nbsp;&nbsp;<%= icd.getInqItemError() %></b>
        </td>
       </tr>
   </table>
   </form>   
<%= JavascriptInfo.getCalendarFoot("codeDateInfo", "getDateInfo", "inqDateSelected") %>

<jsp:include page="../../Include/footer.jsp"></jsp:include>

   </body>
</html>