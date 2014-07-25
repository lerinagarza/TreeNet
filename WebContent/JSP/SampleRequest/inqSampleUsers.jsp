<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- inqSampleUsers.jsp ---------------------------------------//
//
//  Author :  David Eisenheim 6/19/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  6/17/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//------------------------------------------------------------------------------//
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Sample Request Users");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "<option value=\"CtlSampleUsers?requesttype=insert\">Add a New User");

//*************** List of parameters sent from the servlet *****************//
   String userBoxes  = "";
   try {
     userBoxes  = (String) request.getAttribute("userboxes");
   }
   catch (Exception e)
   {
   out.println("Problem with building user check boxes.  " + e);
   }
//**************************************************************************//
%>
<html>
 <head>
  <title>Sample Users Inquiry</title>
 </head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
// Beginning of the Form -- 
%>
<form action="/web/CtlSampleUsers?requesttype=list" method="post">
   <table class="table00" cellspacing="0">
       <tr class="tr02">
         <td style="width:5%">&nbsp;</td>
         <td class="td0518" style="width:15%"><b>Search On</b></td>
         <td class="td0518" style="text-align:right"><b>Make Selections and then press</b></td>
         <td style="width:5%"><input type="Submit" value="Go"></td>
      </tr>
   </table>

   <%= userBoxes %>

   <table class="table00" cellspacing="0">
      <tr class="tr01">
         <td style="width:5%">&nbsp;</td>
         <td class="td0516" colspan="2"><br><b>Choose:</b></td>
         <td style="width:5%">&nbsp;</td>
      </tr>
      <tr>
         <td rowspan="3">&nbsp;</td>
         <td class="td04140102" style="width:25%"><b><acronym title="Search by Name Description.">Name:</acronym></b></td>
         <td class="td04140102"><input size="30" type="text" maxlength="30" name="username">&nbsp;</td>
         <td rowspan="3">&nbsp;</td>
      </tr>
      <tr>
         <td class="td04140102"><b><acronym title="Search by Name Initials.">Initials:</acronym></b></td>
         <td class="td04140102"><input size="3" type="text" maxlength="3" name="userinitials">&nbsp;</td>
      </tr>
       <tr>
         <td class="td04140102"><b><acronym title="Search by Profile.">User:</acronym></b></td>
         <td class="td04140102"><input size="10" type="text" maxlength="10" name="userprofile">&nbsp;</td>
      </tr>

   </table>
         </form>
<jsp:include page="../../Include/footer.jsp"></jsp:include>
</body>
</html>
