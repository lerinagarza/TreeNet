<%@ page import = "com.treetop.app.planning.*,com.treetop.utilities.html.*, 
	com.treetop.businessobjects.*" %>
<% UpdPlanning up = (UpdPlanning) request.getAttribute("viewBean");
  String pageTitle = "Mass Delete Planned MOs";
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
<%
   // No longer needed, using direct call to the Service instead of using the API
   //-- next time into this JSP, should delete this information
   // 11/1/12 - TWalton
   if (0 == 0)
   {
%> 
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
//	var dates = $( "#fromDate, #toDate" ).datepicker({
//		defaultDate: "+1w",
//		changeMonth: true,
//		changeYear: true,
//		showOn: "both",
//		buttonImage: "http://image.treetop.com/webapp/cal.gif",
//		buttonImageOnly: true,
//		onSelect: function( selectedDate ) {
//			var option = this.id == "fromDate" ? "minDate" : "maxDate",
//				instance = $( this ).data( "datepicker" ),
//				date = $.datepicker.parseDate(
//					instance.settings.dateFormat ||
//					$.datepicker._defaults.dateFormat,
//					selectedDate, instance.settings );
//			dates.not( this ).datepicker( "option", option, date );
//		}
//	});

});
</script>
<%
   }
%>
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>


<div id="dialog-loading" title="Processing...">
<!-- 	<div id='progressbar'></div> -->
<div class="center">
	Please wait while your request is being processed
	<br />
	<img src="<%=request.getContextPath() %>/Include/images/ajax-loader-bar.gif">
</div>

</div>


<div id="dialog-message" title="Status of your Delete">
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
<form id="main-form" method="post" action="/web/CtlPlanning?requestType=updPlannedMO" >
<input type="hidden" name="environment" id="environment" value="<%= up.getEnvironment() %>" />


<%
   // No longer needed, using direct call to the Service instead of using the API
   //-- next time into this JSP, should delete this information
   // 11/1/12 - TWalton
   if (0 == 1)
   {
%> 
<fieldset>
		<legend>Selection</legend> 
	
		<table style="margin:0 auto;">
			<tr>
				<td><label for="facility">Facility:</label></td>
				<td><%= DropDownSingle.buildDropDown(UpdPlanning.dropDownFacility(),"facility","facility",up.getFacility(),"",false,"", HtmlSelect.DescriptionType.VALUE_DESCRIPTION) %></td>
			</tr>
			
			<tr>
				<td><label for="fromDate">From:</label></td>
				<td><input type="text" id="fromDate" name="fromDate" value="<%= up.getFromDate() %>" /></td>
			</tr>
			
			<tr>
				<td><label for="toDate">To:</label></td>
				<td><input type="text" id="toDate" name="toDate" value="<%= up.getToDate() %>" /></td>
			</tr>
			
			<tr>
				<td><label for="itemType">Item Type:</label></td>
				<td><%= DropDownSingle.buildDropDown(UpdPlanning.dropDownItemType(),"itemType","itemType",up.getItemType(),"",false,"", HtmlSelect.DescriptionType.VALUE_DESCRIPTION) %></td>
			</tr>
			
			<tr>
				<td><label for="orderStatus">Order Status:</label></td>
				<td><%= DropDownSingle.buildDropDown(UpdPlanning.dropDownOrderStatuses(),"orderStatus","orderStatus",up.getOrderStatus(),"",false,"", HtmlSelect.DescriptionType.VALUE_DESCRIPTION) %></td>
			</tr>
			
		</table>
</fieldset>
<%
   }
%>			
		<div style="text-align:center">
		<input name="submit" id="submit" type="submit" value="Delete Planned MOs">
		</div>
	

</form>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>
