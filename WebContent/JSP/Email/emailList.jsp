<%@ page language = "java" %>
<%@ page import   ="com.treetop.*" %>
<%@ page import   ="com.treetop.data.*" %>
<%@ page import   = "java.util.*" %>
<%@ page import   ="java.sql.*" %>
<%@ page import   ="java.math.*" %>
<%
//---------------- emailList.jsp -----------------------//
//   Author :  Charlena Paschen  10/13/04
//   Changes:
//   Date       Name       Comments
//
//------------------------------------------------------------//
//********************************************************************
// Execute security servlet.
//********************************************************************
   // Allow Session Variable Access
//   HttpSession sess = request.getSession(true);
   // Set the Status
//   SessionVariables.setSessionttiSecStatus(request,response,"");
   // Set the URL address used by the security servlet.
//   String urlAddress = "/JSP/Email/emailList.jsp";
//   SessionVariables.setSessionttiTheURL(request, response, urlAddress);
   // Call the security Servlet
//   getServletConfig().getServletContext().
 //  getRequestDispatcher("/servlet/com.treetop.servlets.TTISecurity" ).
  // include(request, response);
   // Decision of whether or not to use the Inq, List or Detail
//   if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
//  {
//      String x = "Authority was not granted for this page " +
//                 "(List Customer Information Ingredient Specs). " +
//                 "Please contact the Information Services Help Desk" ;
//      response.sendRedirect("/web/TreeNetInq?msg=" + x);
//      return;
//   }
		
   //remove the Status and the Url
//   sess.removeAttribute("ttiTheURL");
//   sess.removeAttribute("ttiSecStatus");

//**************************************************************************//
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title","List Emails for COA's");
   String parameterList = "&returnToPage=/web/TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", ""); 
%>
<html>
 <head>
   <title>Email List</title>
  </head>
    <body>
      <jsp:include page="../../Include/heading.jsp"></jsp:include>

<%
//********************************************
//  Get Vector of emails.
//********************************************
	String type = request.getParameter("type");
	String order = request.getParameter("order");
	String lot = request.getParameter("lot");
	String heading = "Sales Order:";
	try
	{
	    String sendKey = "";
	    if (order != null && !order.trim().equals(""))
	    { // ORDER sent in
	       sendKey = order;
	    }
	    else
	    {
	      sendKey = lot;
	      heading = "Lot:";
	    }
		Vector list = EmailKey.findEmailsByKeys(type.trim(), sendKey.trim());
												
%>
<table class="table00" cellspacing="0" cellpadding="1">
 <tr><td>&nbsp;</td></tr>
 <tr class="tr01">	
  <td class="td0418" style="text-align:right" colspan="3"><b><%= heading %>&nbsp;</b> 
  <td class="td0418"><b><%= sendKey %></b> </td>   	
 </tr>
 <tr><td>&nbsp;</td></tr>
 <tr class="tr02">
  <td class="td04160102" style="width:8%; text-align:center"><b>Date</b></td>
  <td class="td04160102" style="width:8%; text-align:center"><b>Time</b></td>
  <td class="td04160102" style="width:5%; text-align:center"><b>System</b></td>
  <td class="td04160102" style="width:29%"><b>To Address</b></td>
  <td class="td04160102" style="width:29%"><b>From Address</b></td>
  <td class="td04160102"><b>Email Subject</b></td>	
 </tr> 
<%
		// list all emails sent for this COA.
		for (int x = 0; x < list.size(); x++)
		{
			EmailKey email = (EmailKey) list.elementAt(x);
   
%>
	<tr>     
    	<td class="td04140102" style="text-align:center"><%= email.getAuditDate() %> 
    	</td>
    	<td class="td04140102" style="text-align:center"><%= email.getAuditTime() %>
    	</td>
    	<td class="td04140102"><%= email.getAuditSystem().trim() %>
    	</td>    	
    	<td class="td04140102"><a href="mailto:<%= email.getAuditTo().trim() %>"><%= email.getAuditTo().trim() %></a>
    	</td>
    	<td class="td04140102"><a href="mailto:<%= email.getAuditFrom().trim() %>"><%= email.getAuditFrom().trim() %></a>
    	</td>
    	<td class="td04140102"><%= email.getAuditSubject().trim() %>
    	</td>    	
	</tr>
	
<% } // end for 

	} catch (Exception e) {
		out.println("jsp-Exception : " + e);
	}
%>
<tr><td>&nbsp;</td></tr>
</table>
      <%@ include file="../../Include/footer.jsp" %>
</body>

</html>