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
	<title>Build Information for the Costing Reports</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@page import="com.treetop.app.finance.BldFinance" %>
<%
    BldFinance bf =(BldFinance) request.getAttribute("bldViewBean");
    if (bf == null) {
        bf = new BldFinance();
    }
 %>	
	<h1>Build Information for the Costing Reports</h1>
	
	<fieldset>
	   <legend>Choose which year to build</legend>
	   <%  if (!bf.getDisplayMessage().trim().equals("")) { %>
	   <div class="ui-error">
	       <%= bf.getDisplayMessage().trim() %>
	   </div>
	   <%  } %>
	   
	   <div class="comment">
	       This screen executes a process that rebuilds Costing Work Files used to present component and Produced/Consumed Reports for Finance using current Lawson M3 data.
	   </div>
	   
	   <div class="ui-comment">
           This process may take in excess of 45 minutes to complete.
       </div>
	   
	   <br>
       <br>
	   
	   <div class="center">
		   <form name="bldCostFile" action="/web/CtlFinance?requestType=bldCostingReportFiles" method="post">
		   
		      <select id="years">
		          <option value="bldNextYearButton">Next Year</option>
		          <option value="bldCurrentYearButton">Current Year</option>
		          <option value="bldBothButton" selected>Current and Next Year</option>
		      </select>
		      <input type="submit" value="Build Information" class="processingDialog">
		   
	 		   <input name="bldNextYearButton" type="hidden" value="">
			   <input name="bldCurrentYearButton" type="hidden" value="bldBothButton">
			   <input name="bldBothButton" type="hidden" value="">
		   </form>
	   </div>
	</fieldset>
	<script>
	   //$("form[name=bldCostFile] input[type=submit]").button();
	   
	   $("#years").change(function (){
	       $("input[type=hidden]").val("");
	       var value = $(this).val();
	       $("input[name=" + value + "]").val(value);
	   });
	   
	</script>
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>