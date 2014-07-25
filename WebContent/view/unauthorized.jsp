<%@ page language="java" %>
<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp"></jsp:include>
<%  String environment = (String) request.getParameter("environment");
    if (environment == null || environment.equals("")) { 
        environment="PRD";
    }
    
    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    if (roles != null && Arrays.asList(roles).contains("1")) {
        internal = true;
    }
%>
<%-- tpl:put name="headarea" --%>
<title>Unauthorized Access</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
<!-- Insert page specific JavaScript here -->
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<!-- Insert body content here -->

<%
	String message = request.getParameter("msg");
	if (message == null) {
		message = "";
	}
	String url = request.getParameter("url");
	if (url == null) {
		url = "";
	}
 %>

<h1>Unauthorized Access</h1>

<br />

<fieldset>
	<div class="center">
	<br />
	<h2><%=message %></h2>
	<br />
	<p><%=url %></p>
	<br />
	<hr style="width:75%; margin-left:12.5%"/>
	<br />
	<p>If you would like to request access, please email the Help Desk or call them at ext. 1425</p>
	<br />
	<input type="button" value="Go Back" onclick="javascript:history.go(-1)">
	<input type="button" value="Send Email" onclick="sendEmail()">
	</div>
</fieldset>

<script type="text/javascript">

function sendEmail() {
	var address = "helpdesk@treetop.com";
	var subject = "Tree Net Authorization";
	var body = escape("Requesting Tree Net Authorization to   " + 
		"URL: <%=url %>");
		
	window.location.href = "mailto:" + address +
		 "&subject=" + subject +
		 "&body=" + body;
}

</script>



<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>