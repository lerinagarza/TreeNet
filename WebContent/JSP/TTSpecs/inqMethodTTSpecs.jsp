<%@ page language = "java" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.treetop.data.*" %>
<%@ page import = "java.util.*, java.sql.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//------------------ inqMethodTTSpecs.jsp ------------------------------//
//
// Author :  Charlena Paschen  10/30/03
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/13/08   TWalton	   Point to NEW AS400
//  2/23/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------//
//**************************************************************************//
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title","Method Inquiry");
   String parameterList = "&returnToPage=/web/TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "");

//*****************************************************************************
//********************************************************************
// Execute security servlet.
//********************************************************************
   // Allow Session Variable Access
   HttpSession sess = request.getSession(true);
		
   // Set the Status
   SessionVariables.setSessionttiSecStatus(request,response,"");
		
   // Set the URL address used by the security servlet.
   String urlAddress = "/web/JSP/TTSpecs/inqMethodTTSpecs.jsp";
   SessionVariables.setSessionttiTheURL(request, response, urlAddress);

   // Call the security Servlet
   getServletConfig().getServletContext().
   getRequestDispatcher("/TTISecurity" ).
   include(request, response);

   // Decision of whether or not to use the Inq, List or Detail
   if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
   {
      String x = "Authority was not granted for this page " +
                 "(Method Inquiry). " +
                 "Please contact the Information Services Help Desk" ;
      response.sendRedirect("/web/TreeNetInq?msg=" + x);
      return;
   }
		
   //remove the Status and the Url
   sess.removeAttribute("ttiTheURL");
   sess.removeAttribute("ttiSecStatus");

%>

<html>
 <head>
      <%= JavascriptInfo.getNumericCheck() %>

        <title>Method Inquiry</title>

</head>
<body>
<jsp:include page="../include/heading.jsp"></jsp:include>  
   
<table class="table01001" cellspacing="0">
<form action="/web/JSP/TTSpecs/listMethodTTSpecs.jsp" method="get">

    <tr class="tr01001">
       <td style="width:20%">
       </td>
       <td class="td041CL002" colspan="5"><b>Search On:</b>
       </td>
       <td>
       </td>
    </tr>
    <tr class="tr00001">
      <td class="td001SC001" style="width:5%">&nbsp;
       </td>
      <td class="td041CR002L" style="width:3%">&nbsp;
       </td>
      <td class="td044CL002">
            Method ID:
       </td>
      <td class="td044CL002">
           M-<input type="text" size="5" onBlur="checknumeric(this, 'Method ID')" name="method">
       </td>
      <td class="td044CL002">&nbsp;
       </td>
      <td class="td041CR002R" style="width:3%">&nbsp;
       </td>
      <td class="td01SC001" style="width:20%">&nbsp;
       </td>
    </tr>

     <tr class="tr00001">
      <td class="td001SC001" style="width:5%">&nbsp;
      </td>
      <td class="td041CR002L" style="width:3%">&nbsp;
       </td>
      <td class="td044CL002" style="width:15%">
            Method Category:
        </td>
          <td class="td044CL002">
            <input type="text" size="5" onBlur="checknumeric(this, 'Method Category')" name="category">
       </td>
       <td class="td044CL002">&nbsp;
       </td>
     <td class="td041CR002R" style="width:3%">&nbsp;
       </td>
     <td class="td01SC001" style="width:20%">&nbsp;
       </td>
    </tr>
    <tr class="tr00001">
    <td class="td001SC001" style="width:5%">&nbsp;
      </td>
      <td class="td041CR002L" style="width:3%">&nbsp;
       </td>
      <td class="td044CL002" style="width:15%">
            Method Title:
      </td>
      <td class="td044CL002">
        <input size="50" type="text" maxlength="50" name="title">
       </td>
      <td class="td044CL002">&nbsp;
       </td>
     <td class="td041CR002R" style="width:3%">&nbsp;
       </td>
     <td class="td01SC001" style="width:20%">&nbsp;
       </td>
    </tr>
    <tr class="tr00001">
    <td class="td001SC001" style="width:5%">&nbsp;
      </td>
      <td class="td041CR002L" style="width:3%">&nbsp;
       </td>
      <td class="td044CL002" style="width:15%">
           Revision Date:
      </td>
          <td class="td044CL002">
            <input type="text" size="10" onBlur="checknumeric(this, 'Revision Date')" name="revdate">YYYYMMDD
       </td>
    <td class="td044CL002">&nbsp;
       </td>
     <td class="td041CR002R" style="width:3%">&nbsp;
       </td>
     <td class="td01SC001" style="width:20%">&nbsp;
       </td>
    </tr>
    <tr class="tr00001">
    <td class="td001SC001" style="width:5%">&nbsp;
      </td>
      <td class="td041CR002L" style="width:3%">&nbsp;
       </td>
      <td class="td044CL002" style="width:15%">
           Record Status
      </td>
       <td class="td044CL002">
        <select name="recstat"><option value="all">*all
            <option value="ACTIVE">ACTIVE
            <option value="INACTIVE"> INACTIVE
            <option value="PENDING"> PENDING
       </select>
       </td>
      <td class="td044CL002">&nbsp;
       </td>
     <td class="td041CR002R" style="width:3%">&nbsp;
       </td>
     <td class="td01SC001" style="width:20%">&nbsp;
       </td>
    </tr>
          <tr class="tr00001">
    <td class="td001SC001"> &nbsp;
      </td>
      <td class="td041CR002L">&nbsp;
       </td>
      <td class="td044CC002" colspan="2">
         <input type="submit" value="GO">
       </td>
      <td class="td044CL002">&nbsp;
       </td>
     <td class="td041CR002R" style="width:3%">&nbsp;
       </td>
     <td class="td01SC001" style="width:20%">&nbsp;
       </td>
    </tr>
</form>
</table>
<jsp:include page="../include/footer.jsp"></jsp:include>

</body>
</html>
