 <%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%
//---------------- listSampleUsers.jsp ---------------------------------------//
//
//  Author :  David Eisenheim  6/19/03                                      
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
   request.setAttribute("title", "Sample Users List");
   String parameterList = "&returnToPage=CtlSampleUsers?requesttype=inquiry";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleUsers?requesttype=insert\">Add a New User<option value=\"CtlSampleUsers?requesttype=inquiry\">Select Again");

    String sortOrderImagea = "https://image.treetop.com/webapp/null.gif";
    String sortOrderImageb = "https://image.treetop.com/webapp/null.gif";
    String sortOrderImagec = "https://image.treetop.com/webapp/null.gif";
    String sortOrderImaged = "https://image.treetop.com/webapp/null.gif";

    String sortOrderCol1 = "A";
    String sortOrderCol2 = "B";
    String sortOrderCol3 = "C";
    String sortOrderCol4 = "D";
    String sortOrder = "";

   String resendPage = "CtlSampleUsers?requesttype=list";
//   String selectPage = "CtlSampleUsers?requesttype=inquiry";
   String updatePage = "CtlSampleUsers?requesttype=update";
   String detailPage = "CtlSampleUsers?requesttype=detail";
   String deletePage = "CtlSampleUsers?requesttype=delete";

  //**************************************************************************
  // Determine sort order per column
  //**************************************************************************

   String orderBy = request.getParameter("orderby");

   if (orderBy == null)
      orderBy = "";

   String sortBy = "C";

   if (!orderBy.trim().equals(""))
      sortBy = orderBy;

    if (sortBy.equals("A"))
         {
          sortOrderImagea = "https://image.treetop.com/webapp/UpArrowYellow.gif";
         }
     else
         {
          sortOrderImagea = "https://image.treetop.com/webapp/null.gif";
         }

    if (sortBy.equals("B"))
         {
          sortOrderImageb = "https://image.treetop.com/webapp/UpArrowYellow.gif";
         }
     else
         {
          sortOrderImageb = "https://image.treetop.com/webapp/null.gif";
         }
    if (sortBy.equals("C"))
         {
          sortOrderImagec = "https://image.treetop.com/webapp/UpArrowYellow.gif";
         }
     else
         {
          sortOrderImagec = "https://image.treetop.com/webapp/null.gif";
         }

    if (sortBy.equals("D"))
         {
          sortOrderImaged = "https://image.treetop.com/webapp/UpArrowYellow.gif";
         }
     else
         {
          sortOrderImaged = "https://image.treetop.com/webapp/null.gif";
         }

  //**************************************************************************
  // Receiving in parameters and attributes
  //**************************************************************************

   String box1 = request.getParameter("box1");  	
   String box2 = request.getParameter("box2");  	
   String box3 = request.getParameter("box3");  	
   String userName     = request.getParameter("username");
   String userInitials = request.getParameter("userinitials");
   String userProfile  = request.getParameter("userprofile");
   String resendParms  = "&box1=" + box1 + "&box2=" + box2 + "&box3=" + box3 +
                         "&username=" + userName +
                         "&userinitials=" + userInitials +
                         "&userprofile=" + userProfile;

   Vector listUsers = new Vector();
   int userCount = 0;
   String parmsQueried = "";
  try
  {
     listUsers = (Vector) request.getAttribute("listofusers");
  }
  catch (Exception e)
  {
     System.out.println("listUsers Exception Problem in the JSP = " + e);
  }
  try
  {
     userCount = listUsers.size();
  }
  catch (Exception e)
  {
     System.out.println("userCount Exception Problem in the JSP = " + e);
  }
  try
  {
     parmsQueried = (String) request.getAttribute("parmsqueried");
  }
  catch (Exception e)
  {
     System.out.println("parmsQueried Exception Problem in the JSP = " + e);
  }
  //**************************************************************************
// --- Start HTML output ----------------------------------------------------->
%>
<html>
<head>
<style>
<!--
.spanstyle{
position:absolute;
z-index:100;
background-color:F3FAFF;
width:150px;
right:20;
}
-->
</style>
<%
//--- Display the menu options ---------------------------------------------->
%>
  <script language="JavaScript1.2">
   var head="display:'none'"
   function viewmenu(header)
   {
      var head=header.style
      if (head.display=="none")
      {
         head.display=""
      }
      else
      {
         head.display="none"
      }
   }
  </script>
<%
//--------------------------------------------------------------------------->
%>
<title>List Sample Request Users</title>
</head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
<table class="table01" cellspacing="0">
   <tr>
       <td style="width:10%">&nbsp;</td>
       <td class="td0410">Parms Queried:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <%= parmsQueried %>
       </td>
   </tr>
</table>
<table class="table00" cellspacing="0" cellpadding="1">
<%
 //--------------------- Headings ----------------------------
%>
   <tr class="tr02">
     <td class="td04140102">
       <img src="<%= sortOrderImagea %>">
       <a class="a0414" href="<%= resendPage %>&orderby=<%= sortOrderCol1 %><%= resendParms %> ">Name Initials</a>
     </td>
     <td class="td04140102">
       <img src="<%= sortOrderImageb %>">
       <a class="a0414" href="<%= resendPage %>&orderby=<%= sortOrderCol2 %><%= resendParms %> ">User Type</a>
     </td>
     <td class="td04140102">
       <img src="<%= sortOrderImagec %>">
       <a class="a0414" href="<%= resendPage %>&orderby=<%= sortOrderCol3 %><%= resendParms %> ">Profile Name</a>
     </td>
     <td class="td04140102">
       <img src="<%= sortOrderImaged %>">
       <a class="a0414" href="<%= resendPage %>&orderby=<%= sortOrderCol4 %><%= resendParms %> ">Name Description</a>
     </td>
     <td style="width:5%">&nbsp;</td>
   </tr>
<%
//---  Loop to show selected list of users ---------------------------------->
for (int x = 0; x < userCount; x++)
   {
      SampleRequestUsers userRow = (SampleRequestUsers) listUsers.elementAt(x);

      String parmUserType    = userRow.getUserType();
      String parmUserProfile = userRow.getUserProfile();
      String detailParms = "&usertype=" + parmUserType.trim() +
                           "&userprofile=" + parmUserProfile.trim();
%>
     <tr class="tr00">
       <td class="td04120102" style="text-align:center"><%= userRow.getUserInitials() %>&nbsp;</td>
       <td class="td04120102"><%= userRow.getUserType() %>&nbsp;</td>
       <td class="td04120102"><%= userRow.getUserProfile() %>&nbsp;</td>
       <td class="td04120102"><a class="a04001" href="<%= detailPage %><%= detailParms %> "><%= userRow.getUserName() %></a></td>
       <td class="td04120102">
          <div>
             &nbsp; &nbsp;<input style="font-family:arial; font-size:8pt" type="button" value="Edit"
             onClick="viewmenu(document.all[this.sourceIndex+1]);">
          </div>
          <span class="spanstyle" style="display:none" style=&{head}; >
             &nbsp;<a href="<%= updatePage %><%= detailParms %>" target="_blank">Change User Info</a><br>
             &nbsp;<a href="<%= deletePage %><%= detailParms %>" target="_blank">Delete User Info</a><br>
          </span>
       </td>
     </tr>
<%
   }
%>
</table>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
 
</body>
</html>