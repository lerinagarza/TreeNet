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
	<title>P3 Variation of R With Temperature</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>

<%@ page import = "
com.treetop.controller.thermal.InqThermal,
java.lang.Integer,
com.treetop.utilities.html.HTMLHelpersMasking,
java.util.Vector,
java.math.BigDecimal
" %>

<%
	 InqThermal inqThermal = (InqThermal) request.getAttribute("inqThermal");
%>

<div class="clearfix">
	<h1 style="float:left;">P3 Variation of R With Temperature</h1>
	
	<a style="margin-left: 5%;" style="float:right;" href="<%=request.getContextPath()%>/CtlThermal/inqP1" class="ui-button" > 
	   P1 Discrete Process Calc
	</a>
	
	<a style="margin-left: 2%;" style="float:right;" href="<%=request.getContextPath()%>/CtlThermal/inqP2" class="ui-button" > 
	   P2 Accumulative Process Calc
	</a>
	
</div>
<br/>

<h3 class="error"><%=inqThermal.getErrorMessage() %></h3>

<br/>

<form name="bldThermalP3" action="/web/CtlThermal/inqP3" method="post">
<fieldset>
<legend>Inputs</legend>

<table> 

	<tr>
		<td>
			<label for="deeValue">D-Value of Organism</label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getDeeValue() %>" id="deeValue" name="deeValue">
		</td>
		<td class="error">
			<%=inqThermal.getDeeValueErrorMessage() %>
		</td>
	</tr>
	
	<tr>
		<td>
			<label for="zeeValue">Z-Value of Organism</label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getZeeValue() %>" id="zeeValue" name="zeeValue">
		</td>
		<td class="error">
			<%=inqThermal.getZeeValueErrorMessage() %>
		</td>
	</tr>
		
	<tr>
		<td>
			<label for="secondsValue">Holding time in seconds</label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getSecondsValue() %>" id="secondsValue" name="secondsValue">
		</td>
		<td class="error">
			<%=inqThermal.getSecondsValueErrorMessage() %>
		</td>
	</tr>
	
	<tr>
		<td colspan="3">
			<h4 style="margin-top:1em">Range of holding temperatures in &deg;C</h4>
		</td>
	</tr>
	
	<tr>
		<td>
			<label for="minTempValue">From </label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getMinTempValue() %>" id="minTempValue" name="minTempValue">
		</td>
		<td class="error">
			<%=inqThermal.getMinTempValueErrorMessage() %>
		</td>
	</tr>
	
	<tr>
		<td>
			<label for="maxTempValue">To</label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getMaxTempValue() %>" id="maxTempValue" name="maxTempValue">
		</td>
		<td class="error">
			<%=inqThermal.getMaxTempValueErrorMessage() %>
		</td>
	</tr>
	
	<tr>
		<td colspan="2" class="center"> 
			<input type="submit" name="submit" value="Calculate" class="ui-button">
		</td>
	</tr>
	
</table>	
</fieldset>
</form>


	
 <% if(!inqThermal.getSubmit().trim().equals("") && inqThermal.getErrorMessage().trim().equals("")) { %>
 <h3 style="margin-top:2em">Temperature log reduction of process </h3>
 <table class="styled">
 	<tr>
 		<th>Temperature</th>
 		<th>Log Reduction Value</th>
 	</tr>
 <% 
 	int min = Integer.parseInt(inqThermal.getMinTempValue());
 	int max = Integer.parseInt(inqThermal.getMaxTempValue());
 	Vector logValues = inqThermal.getLogReductionValues();
 	
 	for(int i=0; i < logValues.size(); i++) {
 		BigDecimal bd = (BigDecimal) logValues.elementAt(i);
 	
 %>
 	<tr>
 		<td class="center"> <%= min + i %> </td>
 		<td class="center"> <%= HTMLHelpersMasking.maskBigDecimal(bd)%> </td>
 	</tr>
 <%} %>
 
 </table>
 <%} %>



<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>