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
<%@ page import=" 
    com.treetop.app.quality.GeneralQuality,
    com.treetop.app.quality.InqFormula,
    com.treetop.app.quality.DtlFormula,
    java.util.Vector
" %>
<%
//---------------  listFormula.jsp  ------------------------------------------//
// -- List page for the Formula Search
//    Author :  Teri Walton  6/29/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "/Quality/listFormula.jsp";
  String listTitle = "List Formulas";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists
 InqFormula inqFormula = new InqFormula();
 try {
    inqFormula = (InqFormula) request.getAttribute("inqViewBean");
 } catch(Exception e) { }  
 

 Vector getData = new Vector();
 String thisEnv = "PRD";
 try {
    if (inqFormula == null) {
       DtlFormula dtlFormulaTable = (DtlFormula) request.getAttribute("dtlViewBean");
       getData = dtlFormulaTable.getDtlBean().getRevReasonFormula();
       thisEnv = dtlFormulaTable.getEnvironment();
    } else {
       getData = inqFormula.getListReport();
       thisEnv = inqFormula.getEnvironment();
    }
 } catch(Exception e) { } 
 
 boolean allowAdd = false;
 if (inqFormula.getSecurityLevel().trim().equals("2")) {
    allowAdd = true;
 }


%>

	<title><%=listTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
	
	   <h1><%=listTitle %></h1>
	   
	   <div class="clearfix">
	       <div style="float:left">
	           <%=inqFormula.buildSelectionCriteria() %>
	       </div>
<%  if (allowAdd) { %> 
	       <div style="float:right" class="no-print">
               <a class="ui-add" href="/web/CtlQuality?requestType=addFormula&environment=<%=inqFormula.getEnvironment() %>">
                   Add a NEW Formula
               </a>
           </div>
<%  } %>
           <div style="float:right" class="no-print">
	           <a href="CtlQuality?requestType=inqFormula<%=inqFormula.buildParameterResend() %>" class="ui-select-again">
	               Select Again
	           </a>
           </div>
	   </div>
    
        <br>

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

       <jsp:include page="listFormulaTable.jsp"></jsp:include>
       
<%  } %>

	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>