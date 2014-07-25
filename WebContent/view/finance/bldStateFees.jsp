<%@ page import = "com.treetop.app.finance.BldFinance" %>
<% String pageTitle = "Build State Fees"; %>
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
	var dates = $( "#bldFromDate, #bldToDate" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		changeYear: true,
		showOn: "both",
		buttonImage: "/web/Include/images/cal.gif",
		buttonImageOnly: true,
		onSelect: function( selectedDate ) {
			var option = this.id == "bldFromDate" ? "minDate" : "maxDate",
			instance = $( this ).data( "datepicker" ),
			date = $.datepicker.parseDate(
				instance.settings.dateFormat ||
				$.datepicker._defaults.dateFormat,
				selectedDate, instance.settings );
			dates.not( this ).datepicker( "option", option, date );
		}
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
BldFinance bf = (BldFinance) request.getAttribute("bldViewBean");
if (bf == null)
{
	bf = new BldFinance();
}

String  message     = bf.getDisplayMessage();
boolean msgDisplay  = message.length() > 0;	
%>
		<div id="page-title">
			<h1><%= pageTitle %></h1>
			<span class="comment">State fees processing - compute monthly payments</span>
			<h4>This build now creates a listing on printer PRGA1</h4>
		</div>
		<form name="bldStateFees" action="/web/CtlFinance?requestType=addStateFees" method="post">
			<input type="hidden" name="environment" id="environment" value="PRD" />
			<fieldset>
				<legend>Start / End Dates</legend> 
				<div class="table-wrapper">
					<table style="width:80%">
						<% if (msgDisplay) { %>
						<tr>
							<td colspan="2">
								<div class="ui-comment">
									<div class="left" style="margin:-1px 0 0 18px"><strong>Notice</strong></div>
									<p style="margin-left:19px;font-style:italic;"><%= message %></p>
								</div>
							</td>
						</tr>
						<% } %>
						<tr>
							<td class="center">
								<label for="bldFromDate">Start Date:</label>
								<input type="text" id="bldFromDate" name="bldFromDate" value="" />
							</td>			
							<td class="center">
								<label for="bldToDate">End Date:</label>
								<input type="text" id="bldToDate" name="bldToDate" value="" />
							</td>
						</tr>
					</table>
				</div>
				<div class="center">
					<input name="submit" id="submit" type="submit" value="Compute Monthly Payments" />
				</div>
			</fieldset>
		</form>	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>