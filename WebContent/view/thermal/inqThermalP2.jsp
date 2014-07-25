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
	<title>P2 Accumulative Process Calculation</title>
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
	<h1 style="float:left;">P2 Accumulative Process Calculation</h1>
	
	<a style="margin-left: 5%;" style="float:right;" href="<%=request.getContextPath()%>/CtlThermal/inqP1" class="ui-button" > 
	   P1 Discrete Process Calc
	</a>
	
	<a style="margin-left: 2%;" style="float:right;" href="<%=request.getContextPath()%>/CtlThermal/inqP3" class="ui-button" > 
	   P3 Variation of R With Temp
	</a>
	
</div>

<br/>

<h3 class="error"><%=inqThermal.getErrorMessage() %></h3>

<br/>


 
<form name="bldThermalP2" action="/web/CtlThermal/inqP2" method="post">
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
			<h4 style="margin-top:1em">Enter one or more temperature readings</h4>
		</td>
	</tr>

	<tr>
		<td>
			<label for="temperature1"> Reading 1</label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getTemperature1() %>" id="temperature1" name="temperature1">
		</td>
		<td class="error">
			<%=inqThermal.getTemperature1ErrorMessage() %>
		</td>
	</tr>
	
	
	<tr>
		<td>
			<label for="temperature2">Reading 2<label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getTemperature2() %>" id="temperature2" name="temperature2">
		</td>
		<td class="error">
			<%=inqThermal.getTemperature2ErrorMessage() %>
		</td>
	</tr>

	<tr>
		<td>
			<label for="temperature3">Reading 3<label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getTemperature3() %>" id="temperature3" name="temperature3">
		</td>
		<td class="error">
			<%=inqThermal.getTemperature3ErrorMessage() %>
		</td>
	</tr>

		<tr>
		<td>
			<label for="temperature4">Reading 4<label>
		</td>
		<td>
			<input type="number" value="<%=inqThermal.getTemperature4() %>" id="temperature4" name="temperature4">
		</td>
		<td class="error">
			<%=inqThermal.getTemperature4ErrorMessage() %>
		</td>
	</tr>

	<tr>
		<td colspan="2" class="center"> 
			<input type="submit" name="submit" value="Calculate" class="ui-button">
		</td>
		<td></td>
	</tr>
	
 <% if(!inqThermal.getSubmit().trim().equals("") && inqThermal.getErrorMessage().trim().equals("")) { %>
	<tr class="center">
		<td>
			<h4 style="margin-top:1em">Accumulated process P.O. value </h4>
		</td>
		<td>		
			<h4 style="margin-top:1em">Accumulated log reduction value</h4>
		</td>
		<td></td>
	</tr>
	
	<tr class="center">
		<td>
			<%=inqThermal.getPoValue() %> 
		</td>
		<td>		
			<%=inqThermal.getLogReduction() %> 
		</td>
		<td></td>
	</tr>

<% } %>	

	
</table>	
</fieldset>
</form>
 
 



<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>