<html>
<%

   String inLocation = request.getParameter("location");
   if (inLocation == null ||
       inLocation.trim().equals(""))
      inLocation = "/web/CtlGTIN";
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

<body bgcolor="white"><font color="black">
<%
   if (requestType.trim().equals("updateAging"))
   {
%>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>   
<%
   }
%>
<form action="<%= inLocation %>" method="get" target="main">
<h4>Enter new Global Trade Item Number</h4>
<p>
<input type="text" size="15" maxsize="15" name="gtinNumber"> 
<input type="hidden" name="requestType" value="<%= requestType %>">
<input type="Submit" value="Go" onClick="closeRemote()">

</p>
</form>
</font>
<%
   if (requestType.trim().equals("updateAging"))
   {
%>   
 <%@ include file="../../Include/footer.jsp" %>    
<%
   }
%>
</body>
</html>
