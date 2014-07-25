<%@ page import = "com.treetop.app.planning.*,com.treetop.utilities.html.*, 
	com.treetop.businessobjects.*" %>
<% UpdPlanning up = (UpdPlanning) request.getAttribute("viewBean");
  String pageTitle = "Update Start and End Dates in Planned MOs";
 %>
 
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
<title><%= pageTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
<script type="text/javascript">
$(document).ready(function() {
	$('#main-form').submit(function(e) {
		$('#dialog-loading').dialog('open');
		return true;
	});
	$('#progressbar' ).progressbar({value: 55}); 
	$('#dialog-message' ).dialog({
		modal: true,
<% if(up.getSubmit().equals("")){ out.println("autoOpen: false,");} %>
		buttons: {Ok: function() {$( this ).dialog( 'close' );}} 
		}); 
	$('#dialog-loading' ).dialog({
		modal: true,
		autoOpen: false
		}); 
});
</script>
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%
  if (0 == 1)
  {
 %>
<div id="dialog-loading" title="Processing...">
<!-- 	<div id='progressbar'></div> -->
<div class="center">
	Please wait while your request is being processed
	<br />
	<img src="<%=request.getContextPath() %>/Include/images/ajax-loader-bar.gif">
</div>

</div>

<%
   }
%>
<div id="dialog-message" title="Status of your Update of Dates">
<% 
if(!up.getDisplayMessage().equals("")){
	out.println(up.getDisplayMessage());
} else if(!up.getErrorMessage().equals("")){
	out.println(up.getErrorMessage());
}else{
	out.println("Processing...");
}
 %>
</div>


<h1><%= pageTitle %></h1>
<form id="main-form" method="post" action="/web/CtlPlanning?requestType=updPlanningDate" >
<input type="hidden" name="environment" id="environment" value="<%= up.getEnvironment() %>" />
<%
   // No longer needed, using direct call to the Service instead of using the API
   //-- next time into this JSP, should delete this information
   // 11/1/12 - TWalton
%> 
		<div style="text-align:center">
		<input name="submit" id="submit" type="submit" value="Update Planning Date">
		</div>
</form>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>
