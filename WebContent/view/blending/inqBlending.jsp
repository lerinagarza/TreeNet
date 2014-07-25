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
	<title>Blending</title>
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
com.treetop.controller.blending.InqBlending,
com.treetop.utilities.UtilityDateTime,
com.treetop.utilities.html.DropDownTriple,
java.util.Vector" %>

<%
InqBlending ib = (InqBlending) request.getAttribute("inqViewBean");
if (ib == null) {
    ib = new InqBlending();
}
 %>

<h1>Blending</h1>

<form action="<%=request.getContextPath() %>/CtlBlending/productionSchedule" method="post">
<fieldset>

    <legend>Choose a Week</legend>
    
    <div class="row-fluid">
        <div class="span2 hidden-phone"></div>
        <div class="span2">
            <label for="year">Year:</label>
            <br>
            <%=ib.dropDownYear() %>
        </div>
        <div class="span2">
            <label for="period">Period:</label>
            <br>
            <%=ib.dropDownPeriod() %>
        </div>
        <div class="span2">
            <label for="week">Week:</label>
            <br>
            <%=ib.dropDownWeek() %>
        </div>
        <div class="span4">
            <label for="week">Number of Weeks:</label>
            <br>
            <select name="weeks" id="weeks">
            <%  int thisWeek = Integer.parseInt(ib.getWeeks());
                for (int week=1; week<=3; week++) { %>
                <%  String selected = "";
                    if (thisWeek == week) {
                        selected = "selected"; 
                    }
                 %>
                <option <%=selected %>><%=week %></option>
            <%  } %>
            </select>
        </div>
    </div>
    
    <div class="row-fluid" style="margin-top:2em;">
        <div class="span12 center">
            <input type="submit" class="ui-button" name="submit" value="Pull Production Schedule">
        </div>
    </div>
    
</fieldset>
</form>

<%  if (!ib.getErrorMessage().equals("")) { %>
    <div class="ui-error">
        <%=ib.getErrorMessage() %>
    </div>
<%  } %>

<%  if (!ib.getSubmit().equals("") && ib.getErrorMessage().equals("")) { %>
<jsp:include page="inqBlendingPlannedProductionTable.jsp"></jsp:include>
<%  }  //end if submit and no errors %>
<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>