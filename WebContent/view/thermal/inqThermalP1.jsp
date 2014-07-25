<%@page import="java.math.BigDecimal"%>
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
	<title>P1 Discrete Process Calculation</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>

<%@ page import = "com.treetop.controller.thermal.InqThermal" %>
<%
	 InqThermal inqThermal = (InqThermal) request.getAttribute("inqThermal");
%>

<div class="clearfix">
	<h1 style="float:left;">P1 Discrete Process Calculation</h1>
	
	<a style="margin-left: 5%;" style="float:right;" href="<%=request.getContextPath()%>/CtlThermal/inqP2" class="ui-button" > 
	   P2 Accumulative Process Calc
	</a>
	
	<a style="margin-left: 2%;" style="float:right;" href="<%=request.getContextPath()%>/CtlThermal/inqP3" class="ui-button" > 
	   P3 Variation of R With Temp
	</a>
	
</div>

<br/>

<h3>Enter four known values with "0" for the unknown</h3>
<h3 class="error"><%=inqThermal.getErrorMessage() %></h3>

<br/>

<form name="bldThermalP1" action="/web/CtlThermal" method="post">
<fieldset>
<legend>Inputs</legend>

<table> 


<% if(inqThermal.isDeeValueHighlight()) {
%>
	<tr class="highlight">
<% } else  {%>
	<tr>
<%} %>
		<td>
			<label for="deeValue">D-Value of Organism</label>
		</td>
		<td>
			<input type="number" step=".01" value="<%=inqThermal.getDeeValue() %>" id="deeValue" name="deeValue">
		</td>
		<td class="error">
			<%=inqThermal.getDeeValueErrorMessage() %>
		</td>
	</tr>
	
	
<% if(inqThermal.isZeeValueHighlight()) {
%>
	<tr class="highlight">
<% } else  {%>
	<tr>
<%} %>
		<td>
			<label for="zeeValue">Z-Value of Organism</label>
		</td>
		<td>
			<input type="number" step=".01" value="<%=inqThermal.getZeeValue() %>" id="zeeValue" name="zeeValue">
		</td>
		<td class="error">
			<%=inqThermal.getZeeValueErrorMessage() %>
		</td>
	</tr>
	
	
<% if(inqThermal.isSecondsValueHighlight()) {
%>
	<tr class="highlight">
<% } else  {%>
	<tr>
<%} %>
		<td>
			<label for="secondsValue">Holding time in seconds</label>
		</td>
		<td>
			<input type="number" step=".01" value="<%=inqThermal.getSecondsValue() %>" id="secondsValue" name="secondsValue">
		</td>
		<td class="error">
			<%=inqThermal.getSecondsValueErrorMessage() %>
		</td>
	</tr>
	
	
<% if(inqThermal.isTemperatureValueHighlight()) {
%>
	<tr class="highlight">
<% } else  {%>
	<tr>
<%} %>
		<td>
			<label for="tempValue">Holding temperature </label>
		</td>
		<td>
			<% if(inqThermal.getP1UnitType().equals("C")) { %>
				<input type="number" step=".01" value="<%=inqThermal.getTempValue() %>" id="tempValue" name="tempValue">
			<%} else { %>
				<input type="number" step=".01" value="<%=inqThermal.getfTempValue() %>" id="tempValue" name="tempValue">
			<%} %>	
		</td>
		<td class="error">
			<%=inqThermal.getTempValueErrorMessage() %>
		</td>
		<td>
			<% if(inqThermal.getP1UnitType().equals("F")) { %>
				<input type="radio" name="p1UnitType" value="C">&deg;C&nbsp;
				<input type="radio" name="p1UnitType" value="F" checked>&deg;F
			<%} else { %>
				<input type="radio" name="p1UnitType" value="C" checked>&deg;C&nbsp;
				<input type="radio" name="p1UnitType" value="F">&deg;F
			<%} %>
		</td>
	</tr>
	
	
<% if(inqThermal.isReductionValueHighlight()) {
%>
	<tr class="highlight">
<% } else  {%>
	<tr>
<%} %>
		<td>
			<label for="reductionValue">Log Reduction of Process</label>
		</td>
		<td>
			<input type="number" step=".01" value="<%=inqThermal.getReductionValue() %>" id="reductionValue" name="reductionValue">
		</td>
		<td class="error">
			<%=inqThermal.getReductionValueErrorMessage() %>
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


<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>