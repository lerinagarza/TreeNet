<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Quality/inqFormula.jsp -----------------------//
// Author   :  Teri Walton       5/25/10 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
//**************************************************************************//
  // Allows the Title to display in the Top Part of the Page
  Vector headerInfo = new Vector();
  headerInfo.addElement("PRD");
  headerInfo.addElement("Reset the Lists for the Quality Application");
//*****************************************************************************
%>
<html>
 <head>
   <title>Reset the Lists for the Quality Application</title>
 </head>
 <body>
<%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
<hr>
  <table class="table01" cellspacing="0" style="width:100%">
   <tr class="tr01">
    <td style="width:5%">&#160;</td>
    <td class="td0420"><b>The Lists for the Quality Application have Been Reset</b></td>
    <td style="width:5%">&#160;</td>
   </tr>
   <tr class="tr00">
    <td>&#160;</td>
    <td class="td00414">&#160;&#160;&#160;&#160;&#160;</b></td>
    <td>&#160;</td>
   </tr>    
  </table>  
<hr>  
  <br>  
  <%= HTMLHelpers.pageFooterTable("") %>
   </body>
</html>