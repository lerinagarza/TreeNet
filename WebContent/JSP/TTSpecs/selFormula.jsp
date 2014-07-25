<%@ page language = "java" %>
<%@ page import = "java.util.*, java.sql.*, java.math.*, java.io.*, javax.sql.*"%>
<%@ page import="com.treetop.*" %>
<%@ page import="com.ibm.as400.access.*" %>

<%
//------------------ selFormula.jsp---------------------------------------------//
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
<%!
//******************************************************************************
//   Declare Variables
//******************************************************************************
//   Connection conn = null;
   String userProfile = null;
   String userPassword = null;
%>
<%//******************************************************************************
//   Get input fields
//******************************************************************************
//   String sys = ("10.6.100.3");
//   String userProfile = ("DAUSER");
//   String password = ("WEB230502");

//   String rname = request.getParameter("resource");
//   String cname = request.getParameter("formula");

//   String driver = "com.ibm.as400.access.AS400JDBCDriver";

//   Driver drv = (Driver) Class.forName(driver).newInstance();

//   Properties prop = new Properties();
//   prop.put("user", userProfile);
//   prop.put("password", password);

//   conn = DriverManager.getConnection("jdbc:as400:" + sys, prop);

%>
<html>
   <head>
      <title>Change Formula</title>

<script language="JavaScript">
<!-- Begin
function closeRemote() {
timer = setTimeout('window.close();', 10);
}
// End -->
</script>
</head>

<body bgcolor="#ffffcc">
<h4 style="font-color=#006400">Enter New Formula</h4>
<%   if  (parmUpdate.equals("yes"))
        {
%>
<p>
<form action="updFormulaTTSpecs.jsp" method="get" target="main">
<input type="text" size="14" name="formula">
<br>
<input type="Submit" value="Go" onClick="closeRemote()">
</form>
<br>
<b>Note:</b>  If you enter a new Formula, you will retrieve the newest possible revised date.
</p>
<%        }
       else
          {
%>
<p>
<form action="dtlFormulaTTSpecs.jsp" method="get" target="main">
<input type="text" size="14" name="formula">
<br>
<input type="Submit" value="Go" onClick="closeRemote()">
</form>
<br>
<b>Note:</b>  If you enter the Method, you will retrieve the Current Active Formula.
</p>
<%        }
%>
</body>
</html>
