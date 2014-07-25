<%@ page import = "com.treetop.app.finance.BldFinance" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.html.HtmlSelect.DescriptionType" %>
<%@ page import = "com.treetop.utilities.html.DropDownDual %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "java.util.Vector" %>
<% String pageTitle = "Run State Fees Report"; %>
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

String  requestType = bf.getRequestType();
Vector  options     = bf.getDateTimeDDValues();
String  message     = bf.getDisplayMessage();
boolean msgDisplay  = message.length() > 0;	
%>

		<div id="page-title">
			<h1><%= pageTitle %></h1>
		</div>
		<form name="rptStateFees" action="/web/CtlFinance?requestType=bldReportStateFees" method="post">
			<input type="hidden" name="environment" id="environment" value="PRD" />
			<fieldset>
				<legend>Selection Criteria</legend> 
				<div class="table-wrapper" style="margin-right:20px">
					<table>
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
							<td>
								<label>Report:</label>
							</td>
							<td style="width:300px">
								<div style="width:95%">
									<%= bf.buildDropDownChosenReport(requestType) %>
								</div>
							</td>
						</tr>
						<tr>			
							<td>
								<label>Month:</label>
							</td>
							<td style="width:300px">
								<div style="width:95%">
									<%= bf.buildDropDownMonth() %>
							</td>
						</tr>
						<tr>			
							<td>
								<label>Report Type:</label>
							</td>
							<td style="width:300px">
								<div style="width:95%">
									<%= bf.buildDropDownReportType() %>
							</td>
						</tr>
						<tr>			
							<td>
								<label>Report Run Type:</label>
							</td>
							<td style="width:300px">
								<div style="width:95%">
									<%= bf.buildDropDownRunType() %>
							</td>
						</tr>
						<tr id="report-date">
							<td>
								<label for="rptDate">Report Date:</label>
							</td>
							<td>
								<%=DropDownDual.buildMaster(options, 
									"rptDate", "rptDate", "", "", "Choose Date", 
									false, DescriptionType.DESCRIPTION_ONLY) %>
							</td>
						</tr>
						<tr id="report-time">
							<td>
								<label for="rptTime">Report Time:</label>
							</td>
							<td>
								<%=DropDownDual.buildSlave(options, "rptDate",
									"rptTime", "rptTime", "", "", "Choose Time", 
									false, DescriptionType.DESCRIPTION_ONLY) %>
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						var dropdown = $('select[name="rptRunType"]');
						dropdown.change(function() {
    						if(dropdown.find('option:selected').val() == "P")
    						{
        						$('#report-date').show().find('select').prop('disabled', false);
        						$('#report-time').show().find('select').prop('disabled', false);
    						} else {
        						$('#report-date').find('select').prop('disabled', true).end().hide();
        						$('#report-time').find('select').prop('disabled', true).end().hide();
    						}
						});
					</script>
				</div>
				<div class="center">
					<input name="submit" id="submit" type="submit" value="Submit Selected Report" />
				</div>
			</fieldset>
		</form>	
			
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>