<%@ page language="java" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "java.util.*" %>

<html>
<%
   String requestType = request.getParameter("requestType");
   if (requestType == null)
      requestType = "";
%>
<head>

<script language="JavaScript1.2">
<!-- Begin
function closeRemote() {
timer = setTimeout('window.close();', 10);
}
// End -->
</script>
</head>


<body bgcolor="#ffffcc"><font color="006400">
<h4>Enter New Specification</h4>
<br>
<form action="/web/CtlSpecification" method="get" target="main">
<input type="text" size="6" name="specification"> 
<input type="hidden" name="requestType" value="<%= requestType %>">
<br>
<input type="Submit" value="Go" onClick="closeRemote()">
</form>

</body>
</html>
