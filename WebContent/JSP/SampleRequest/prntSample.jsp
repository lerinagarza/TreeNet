<%@ page language = "java" %>
<%@ page import = "javax.servlet.http.*" %>
<%
//---------------- prntSample.jsp ---------------------------------------//
//
//  Author :  Teri Walton  8/08/03                                      
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//  9/26/05    TWalton     Changed to not include the Shipped Date,
//                         AND put the Delivery Date at the beginning of the comments.
//------------------------------------------------------------------------------//

   HttpSession sess = request.getSession(true);
   String pageHTML = (String) sess.getAttribute("pageHTML");
   if (pageHTML == null)
      pageHTML = "This is a problem";

%>
<html>
<head>
<style type="text/css">
td {font-family: 'Calibri' sans-serif;
    font-size:10pt}
</style>
<title>Printable Sample Request</title>
</head>
<body>
<br>
<%= pageHTML %>
</body>
</html>