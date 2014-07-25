<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//-----  Author, Date, Changes  -----previewCOA.jsp---------------
//Author :  Teri Walton  08/8/03
//Changes:

//  Date       Name          Comments
//  ----       ----          --------
// 8/18/06    TWalton        Changed from Session to Request
//-------------------------------------------------->
   String pageHTML = (String) request.getAttribute("pageHTML");
   String soNumber = request.getParameter("soNumber");
   if (pageHTML == null || pageHTML.trim().equals(""))
   {
      pageHTML = "COA has not been Built -- Please Review with the plant the product was made in.";
   }
%>
<html>
<head>
<title>Certificate of Analysis</title>
</head>
<body>
  <table>
   <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
  </table>
<%= pageHTML %>
</body>
</html>