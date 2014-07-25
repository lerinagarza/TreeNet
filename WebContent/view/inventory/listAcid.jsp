<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.inventory.*" %>
<%@ page import = "java.util.Vector" %>
<% String listTitle = "Apple Concentrate Average Acid"; %> 
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
	<title><%=listTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
	<%
	InqInventory ii = (InqInventory) request.getAttribute("inqViewBean");
	if (ii == null) {
		ii = new InqInventory();
	}
		
	boolean showDetails = !ii.getInqShowDetails().equals("");
	%>
	
		<div id="page-title">
			<a href="CtlInventoryNew?requestType=listAcid&inqShowDetails=<%=showDetails ? "" : "Yes" %>" 
				class="ui-show-details" style="float:right"><%=showDetails ? "Hide" : "Show" %> Details</a>
			<h1><%=listTitle %></h1>
		</div>
		<div class="table-wrapper">	
	
	<% 
	Vector checkLines = (Vector) ii.getBeanInventory().getByItemVectorOfInventory();
	if (checkLines == null || checkLines.size() == 0) {
	%>
	
			<div class="ui-comment center" style="padding:0.7em;display:inline-block;">
				<span style="padding:0 0 0 0.4em;float:left;">
					<strong>Oops...</strong>
				</span>
				<span style="font-style:italic">
					No valid information returned from the request.
				</span>
			</div>
			<script type="text/javascript">
				$(".selection-criteria > h3").removeClass("expand").addClass("expandOpen");
			</script>
		
	<% } else {%>
	
				<table class="styled center">
					<% if (showDetails) { // show table	%>   
    				<jsp:include page="listAcidDetailTable.jsp" /> 
					<% } else { %>   
    				<jsp:include page="listAcidSummaryTable.jsp" />       
					<% } %> 
				</table>
		
	<% } %> 

		</div>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>