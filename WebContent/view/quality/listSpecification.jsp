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
	<title>List Specifications</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import="com.treetop.app.quality.InqSpecification, 
    com.treetop.app.quality.DtlSpecification,
    com.treetop.app.quality.GeneralQuality,
    com.treetop.businessobjects.QaSpecification,
    java.util.Vector
" %>
<%  
 Vector getData = new Vector();
 String thisEnv = "PRD";
    InqSpecification inqSpecification = (InqSpecification) request.getAttribute("inqViewBean");
    if (inqSpecification == null) {
       inqSpecification = new InqSpecification();

       DtlSpecification dtlSpecificationTable = (DtlSpecification) request.getAttribute("dtlViewBean");
       getData = dtlSpecificationTable.getDtlBean().getRevReasonSpecification();
       thisEnv = dtlSpecificationTable.getEnvironment();
       
    } else {
    
       getData = inqSpecification.getListReport();
       thisEnv = inqSpecification.getEnvironment();
       
    }
    
    boolean allowAdd = false;
    if (inqSpecification.getSecurityLevel().trim().equals("2")) {
       allowAdd = true;
    }
  
    
 %>	
	<h1>List Specifications</h1>
	
	<div class="clearfix">
	    <div style="float:left">
	        <%=inqSpecification.buildSelectionCriteria() %>
	    </div>
<%  if (allowAdd) { %>
        <div style="float:right" class="no-print">
            <a class="ui-add" href="/web/CtlQuality?requestType=addSpec&environment=<%=inqSpecification.getEnvironment() %>">
                Add a NEW Specification
            </a>
        </div>
<% } %>	    
	        
	    <div style="float:right;" class="no-print">
	        <a href="CtlQuality?requestType=inqSpec&environment=<%=inqSpecification.getEnvironment() %><%=inqSpecification.buildParameterResend() %>" class="ui-select-again">
	           Select Again
	        </a>
	    </div>
    </div>

<%  if (getData.isEmpty()) { %>

    <div class="ui-comment">
        Your seach return no results.  Click [Select Again] and try searching on something else.
    </div>
    
    <script>
        $(document).ready(function() {
            $(".selection-criteria h3").click();
        });
    </script>
    
<%  } else { %>
    
    <jsp:include page="listSpecificationTable.jsp"></jsp:include>

<%  } %>
	
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>