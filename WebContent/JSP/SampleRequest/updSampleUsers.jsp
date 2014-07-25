<%@ page language = "java" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.treetop.data.*" %>
<%
//---------------- updSampleUsers.jsp ---------------------------------------//
//
//  Author :  Teri Walton  7/08/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/18/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Add/Update Sample User");
   String parameterList = "&returnToPage=CtlSampleUsers?requesttype=inquiry";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "");

//*************** List of parameters sent from the servlet *****************//

  String typeDropDown = "";

  try
  {
     typeDropDown = (String) request.getAttribute("typedropdown");
  }
  catch (Exception e)
  {
     System.out.println("Exception Problem in the JSP = " + e);
  }

  String eMailShippedDropDown = "";

  try
  {
     eMailShippedDropDown = (String) request.getAttribute("mailshipped");
  }
  catch (Exception e)
  {
     System.out.println("Exception Problem in the JSP = " + e);
  }

//**************************************************************************//

   String requestType = request.getParameter("requesttype");
   if (requestType == null)
      requestType = "insert";

   String sendRequestType = "";
   if (requestType.equals("insert"))
      sendRequestType = "insertfinish";
   if (requestType.equals("update"))
      sendRequestType = "updatefinish";

   String sendParms = "";

//**************************************************************************//

   String userType = request.getParameter("usertype");
   if  (userType == null)
        userType = "";

   String userProfile = request.getParameter("userprofile");
   if  (userProfile == null)
        userProfile = "";

   String userInitials = request.getParameter("userinitials");
   if  (userInitials == null)
        userInitials = "";

   String userName = request.getParameter("username");
   if  (userName == null)
        userName = "";

   String notifyShipped = request.getParameter("notifyshipped");
   if  (notifyShipped == null)
        notifyShipped = "";

   String notifyFollowUp = request.getParameter("notifyfollowup");
   if  (notifyFollowUp == null)
        notifyFollowUp = "";

   String notifyFeedback = request.getParameter("notifyfeedback");
   if  (notifyFeedback == null)
        notifyFeedback = "";

   String updateDate = request.getParameter("updatedate");
   if  (updateDate == null)
        updateDate = "";

   String updateTime = request.getParameter("updatetime");
   if  (updateTime == null)
        updateTime = "";

   String updateUser = request.getParameter("updateuser");
   if  (updateUser == null)
        updateUser = "";

   String updateUserName = request.getParameter("updateusername");
   if  (updateUserName == null)
        updateUserName = "";

   if (requestType.equals("update"))
   sendParms = sendParms + "&usertype=" + userType + "&userprofile=" + userProfile;
   else
   sendParms = sendParms + "&usertype=*new&userprofile=*new";

//**************************************************************************//
%>
<html>
   <head>
      <title>Add/Update Sample User</title>
   </head>
   <body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form action="/web/CtlSampleUsers?requesttype=<%= sendRequestType %><%= sendParms %>" method="post">
  <table class="table01" cellspacing="0">
<%
   if (requestType.equals("update"))
   {
%>
      <tr>
          <td class="td04140105" style="width:10%" rowspan="2">&nbsp;</td>
          <td class="td04140102"><b>User Type:</b></td>
          <td class="td04140102"><%= userType %></td>
          <td class="td04140105" style="width:10%" rowspan="2">&nbsp;</td>
          <td class="td04140102"><b>Last Revised Date/Time:</b></td>
          <td class="td04140102"><%= updateDate %>&nbsp;&nbsp;&nbsp;<%= updateTime %></td>
          <td class="td04140105" style="width:10%" rowspan="2">&nbsp;</td>
      </tr>
      <tr>
          <td class="td04140105"><b>Profile Name:</b></td>
          <td class="td04140105"><%= userProfile %></td>
          <td class="td04140105"><b>Last Revised By:</b></td>
          <td class="td04140105"><%= updateUserName %></td>
      </tr>
<%
   }
   else
   {
%>
      <tr>
          <td>&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140105" style="width:10%" rowspan="2">&nbsp;</td>
         <td class="td04140102"><b>User Type:</b></td>
         <td class="td04140102"><%= typeDropDown %> </td>
         <td class="td04140105" style="width:10%" rowspan="2">&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140105"><b>Profile Name:</b></td>
         <td class="td04140105">
            <input size="10" type="text" maxlength="10" name="nameprofile" value="<%= userProfile %>"></td>
      </tr>
<%
   }
%>
  </table>
<%
//--- Detail Input ---------------------------------------------------------->
%>
  <table class="table00" cellspacing="0">
      <tr class="tr00">
         <td class="td04140105" style="width:10%" rowspan="3">&nbsp;</td>
         <td class="td04140102"><b>Name Description:</b></td>
         <td class="td04140102">
            <input size="30" type="text" maxlength="30" name="namedesc" value="<%= userName %>"></td>
         <td class="td04140105" style="width:10%" rowspan="3">&nbsp;</td>
      </tr>
      <tr class="tr00">
         <td class="td04140102"><b>Name Initials:</b></td>
         <td class="td04140102">
            <input size="3" type="text" maxlength="3" name="nameinitials" value="<%= userInitials %>"></td>
      </tr>
      <tr class="tr00">
         <td class="td04140105"><b>Shipped Notification:</b></td>
         <td class="td04140105"><%= eMailShippedDropDown %> </td>
      </tr>
      <tr class="tr01">
         <td style="text-align:center" colspan="3">
         <input type="Submit" value="Save Changes"></td>
       </tr>
  </table>
         <input type="hidden" name="notifyfollowup" value="<%= notifyFollowUp %>">
         <input type="hidden" name="notifyfeedback" value="<%= notifyFeedback %>">
         <input type="hidden" name="updatedate" value="<%= updateDate %>">
         <input type="hidden" name="updatetime" value="<%= updateTime %>">
         <input type="hidden" name="updateuser" value="<%= updateUser %>">
  
 </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>

   </body>

</html>