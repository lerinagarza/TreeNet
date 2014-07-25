<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*, java.io.*, javax.sql.*"%>
<%@ page import="com.treetop.*" %>
<%@ page import="com.ibm.as400.access.*" %>

<%
//------------------ selMethod.jsp---------------------------------------------//
//   Author :  Teri Walton  8/30/02                                                 
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  10/13/08   TWalton	   Point to NEW AS400
//  2/23/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------------//

//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************

   String parmUpdate = request.getParameter("update");
   if  (parmUpdate == null || parmUpdate.equals(""))
      parmUpdate = " ";
%>

<html>
   <head>
      <title>Change Method</title>

<script language="JavaScript">
<!-- Begin
function closeRemote() {
timer = setTimeout('window.close();', 10);
}
// End -->
</script>
</head>

<body bgcolor="#ffffcc">
<h4 style="color:#006400">Enter New Method</h4>
<%   if  (parmUpdate.equals("yes"))
        {
%>

<form action="updMethodTTSpecs.jsp" method="get" target="main">
<p>
M-<input type="text" size="8" name="method">
</p>
<p>
<input type="Submit" value="Go" onClick="closeRemote()">
</p>
<p>
<b>Note:</b>  If you enter a new Method, you will retrieve the newest possible revised date.
</p>
</form>
<%        }
       else
          {
%>

<form action="dtlMethodTTSpecs.jsp" method="get" target="main">
<p>
M-<input type="text" size="8" name="method">
</p>
<p>
<input type="Submit" value="Go" onClick="closeRemote()">

</p>
<p>
<b>Note:</b>  If you enter the Method, you will retrieve the Current Active Formula.
</p>
</form>
<%        }
%>
</body>
</html>
