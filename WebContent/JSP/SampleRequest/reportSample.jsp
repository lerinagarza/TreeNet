<%@ page language = "java" %>

<%
//---------------- reportSample.jsp ---------------------------------------//
//
//  Author :  Teri Walton  11/24/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//------------------------------------------------------------------------------//
   HttpSession sess = request.getSession(true);
   String pageHTML = (String) sess.getAttribute("pageHTML");
   if (pageHTML == null)
      pageHTML = "This is a problem";
   String headInfo = (String) sess.getAttribute("headInfo");
   if (headInfo == null)
      headInfo = "";
%>
<html>
<head>
<%= headInfo %>
</head>
<body>
<%= pageHTML %>
</body>
</html>