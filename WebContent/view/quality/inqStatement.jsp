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
	<title>Statements</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<style>
pre.statement {
    margin:0 auto 1em;
    font-family:sans-serif;
    line-height:2em;
    text-indent:2em;
    max-width:40em;
}
</style>
<%@ page import = "
java.util.Vector,
com.treetop.businessobjects.KeyValue,
com.treetop.app.quality.GeneralQuality
"; %>
<%

Vector statements = (Vector) request.getAttribute("listStatements");
if (statements == null) {
    statements = new Vector();
}

 %>

<div class="clearFix">
       <div style="float:left">
            <h1>Statements</h1>
       </div>
       <div style="float:right">
           <a href="<%=request.getContextPath() %>/CtlQualityStatement/add" class="ui-button">Add New Statement</a>
       </div>
</div>

<%  if (!statements.isEmpty()) { %>
<br>
<br>
<div class="row-fluid">
    <div class="span12">
		<table class="styled full-width">
		    
		    <col>
		    <col>
		    <col>
		    <col>
		    <col>
		    <col>
		    
		    <tr>
		        <th>Title</th>
		        <th>Statement</th>
		        <th>Last Modified</th>
		        <th>User</th>
		        <th colspan="2"></th>
		    </tr>
		<%  for (int i=0; i<statements.size(); i++) { 
		        KeyValue statement = (KeyValue) statements.elementAt(i);
		        String updateUrl = request.getContextPath() + 
		            "/CtlQualityStatement/update/" + statement.getUniqueKey();
		            
		        String deleteUrl = request.getContextPath() + 
		            "/CtlQualityStatement/delete/" + statement.getUniqueKey();
		%>
		    <tr>
		        <td><%=statement.getDescription() %></td>
		        <td>
		              <pre class="statement"><%=statement.getValue() %></pre>
		        </td>
		        <td>
		             <%=statement.getLastUpdateDate() %> 
		             <%=GeneralQuality.formatTimeForScreen(statement.getLastUpdateTime()) %>
	             </td>
		        <td><%=statement.getLastUpdateUser() %></td>
		        <td>
			        <a href="<%=updateUrl %>">Update</a>
			        <a href="<%=deleteUrl %>">Delete</a>
		        </td>
		    </tr>
		<%  } %>
		</table>
    </div>
</div>
<%  } %>
<br>
<br>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>