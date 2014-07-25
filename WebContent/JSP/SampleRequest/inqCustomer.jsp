<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%
//---------------- inqCustomer.jsp ---------------------------------------//
//
//  Author :  Teri Walton  6/02/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/16/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Search Sample Request Customers");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleCustomer?requestType=add\">Add a New Sample Customer");

//**************************************************************************//
// Get Request Session Variables for Display on this Screen
//**************************************************************************
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");

//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String custNumber      = request.getParameter("custNumber");
       if (custNumber == null)
         custNumber = "";
   String fromCustNumber  = request.getParameter("fromCustNumber");
       if (fromCustNumber == null)
         fromCustNumber = "";
   String toCustNumber    = request.getParameter("toCustNumber");
       if (toCustNumber == null)
         toCustNumber = "";
   String name            = request.getParameter("name");
       if (name == null)
         name = "";
   String city            = request.getParameter("city");
       if (city == null)
         city = "";
   String state           = request.getParameter("state");
       if (state == null)
         state = "";
   String country         = request.getParameter("country");
       if (country == null)
         country = "";
   String contactName     = request.getParameter("contactName");
       if (contactName == null)
         contactName = "";
%>
<html>
<head>
   <title>Sample Request Customer Inquiry</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
   
      <form action="/web/CtlSampleCustomer?requestType=list" method="post">
   <table class="table00" cellspacing="0">
      <tr class="tr02">
         <td style="width:5%">&nbsp;</td>
         <td class="td0418" style="width:23%"><b>&nbsp;</b></td>
         <td class="td0518" style="width:12%"><b>- OR -</b></td>
         <td class="td0418" style="width:15%"><b>&nbsp;</b></td>
         <td class="td0418" style="width:40%"><b>Make Selections and then press</b></td>
         <td class="td0418" style="width:5%"><input type="Submit" value="Go"></td>
      </tr>
   </table>
   <table class="table00" cellspacing="0">
       <tr class="tr01">
         <td class="td05140102" colspan="2"><b>Choose:</b></td>
         <td class="td04101205" rowspan="7">&nbsp;</td>
         <td class="td05140102" style="width:25%"><b>Choose:</b></td>
         <td class="td05140102" style="width:20%"><b>FROM</b></td>
         <td class="td05140102"><b>TO</b></td>
      </tr>
      <tr>
         <td class="td04120102" style="width:25%"><b>Choose a Customer Number:</b></td>
         <td class="td04120102" style="width:10%">
          <input size="6" type="text" maxlength="6" name="custNumber" value="<%= custNumber %>">
         </td>
         <td class="td04120102" style="width:20%"><b><acronym title="Search By Range of Customer Numbers">Customer Numbers:</acronym></b></td>
         <td class="td04120102"><input size="6" type="text" maxlength="6" name="fromCustNumber" value="<%= fromCustNumber %>"></td>
         <td class="td04120102"><input size="6" type="text" maxlength="6" name="toCustNumber" value="<%= toCustNumber %>"></td>
      </tr>
      <tr>
         <td colspan="2">&nbsp;</td>
         <td class="td04120102"><b><acronym title="Search by Customer Name">Customer Name:</acronym></b></td>
         <td class="td04120102" colspan="2"><input size="30" type="text" maxlength="30" name="name" value="<%= name %>"></td>
      </tr>
      <tr>
         <td colspan="2">&nbsp;</td>
         <td class="td04120102"><b><acronym title="Search By City">City:</acronym></b></td>
         <td class="td04120102" colspan="2"><input size="16" type="text" maxlength="16" name="city" value="<%= city %>"></td>
      </tr>
      <tr>
         <td colspan="2">&nbsp;</td>
         <td class="td04120102"><b><acronym title="Search By State">State:</acronym></b></td>
         <td class="td04120102" colspan="2"><%= generalInfo[0] %>
         </td>
      </tr>
      <tr>
         <td colspan="2">&nbsp;</td>
         <td class="td04120102"><b><acronym title="Search By Country">Country:</acronym></b></td>
         <td class="td04120102" colspan="2"><input size="16" type="text" maxlength="16" name="country" value="<%= country %>"></td>
      </tr>
      <tr>
         <td colspan="2">&nbsp;</td>
         <td class="td04120102"><b><acronym title="Search By Customer Contact">Customer Contact:</acronym></b></td>
         <td class="td04120102" colspan="2"><input size="30" type="text" maxlength="30" name="contactName" value="<%= contactName %>"></td>
      </tr>
   </table>
   </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>
