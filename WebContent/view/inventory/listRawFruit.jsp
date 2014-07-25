<%@ page import = "java.util.Vector" %>
<%@ page import = "com.treetop.app.inventory.InqInventory" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<% String pageTitle = "List Raw Fruit Inventory"; %>
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
	<title><%=pageTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
<style type="text/css">
@media print {
	.table-wrapper table {
		font-size: 0.8em;
	}
}
</style>
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
 	 %>
	<div id="page-title">
		<a href="CtlInventoryNew?requestType=inqRawFruit<%=ii.buildParameterResend() %>" class="ui-select-again">Select Again</a>
		<h1><%=pageTitle %></h1>	
	</div>	
	<%=ii.buildSelectionCriteria() %>	

	
	<%
	Vector checkLines = (Vector) ii.getBeanInventory().getListOfInventory();
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
	
		<% if (ii.getInqShowDetails().equals("")) { %>
			<jsp:include page="listRawFruitSummaryTable.jsp" />
		<% } else {%>
			<jsp:include page="listRawFruitDetailTable.jsp" />
		<% } %>
		
		</table>
	
	<% } %>

	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>