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
    com.treetop.app.quality.InqMethod,
    com.treetop.app.quality.DtlMethod,
    java.util.Vector
" %>
<%
//---------------  listMethod.jsp  ------------------------------------------//
// -- List page for the Method Search
//    Author :  Teri Walton  6/29/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
  
 String listTitle = "List Methods"; 
 String valueDisplay = "Method"; 
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists
 InqMethod inqMethod = new InqMethod();
 String addUrl = "CtlQuality?requestType=addMethod&environment=" + inqMethod.getEnvironment();
 String addDisplay = "Add a NEW Method";
 try {
    inqMethod = (InqMethod) request.getAttribute("inqViewBean");
    if (inqMethod.getRequestType().trim().equals("listProcedure"))
    {
       listTitle = "List Procedures";
       valueDisplay = "Procedure";
       addUrl = "CtlQuality?requestType=addProcedure&environment=" + inqMethod.getEnvironment();
       addDisplay = "Add a NEW Procedure";
    }
    if (inqMethod.getRequestType().trim().equals("listPolicy"))
    {
       listTitle = "List Policies";
       valueDisplay = "Policy";
       addUrl = "CtlQuality?requestType=addPolicy&environment=" + inqMethod.getEnvironment();
       addDisplay = "Add a NEW Policy";
    }
    
 } catch(Exception e) { }  
 

 Vector getData = new Vector();
 String thisEnv = "PRD";
 try {
    if (inqMethod == null) {
       DtlMethod dtlFormulaTable = (DtlMethod) request.getAttribute("dtlViewBean");
       getData = dtlFormulaTable.getDtlBean().getRevReasonFormula();
       thisEnv = dtlFormulaTable.getEnvironment();
    } else {
       getData = inqMethod.getListReport();
       thisEnv = inqMethod.getEnvironment();
    }
 } catch(Exception e) { } 
 
 boolean allowAdd = false;
    if (inqMethod.getSecurityLevel().trim().equals("2")) {
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
	           <%=inqMethod.buildSelectionCriteria() %>
	       </div>
	       <%  if (allowAdd) { %>
	       <div style="float:right">
               <a class="ui-add" href="<%=addUrl %>">
                   <%=addDisplay %>
               </a>
           </div>
             <% } %>
           <div style="float:right">
	           <a href="CtlQuality?requestType=inqMethod<%=inqMethod.buildParameterResend() %>&environment=<%=inqMethod.getEnvironment() %>" class="ui-select-again">
	               Select Again
	           </a>
           </div>
	   </div>
    
        <br>

<%  if (getData == null || getData.isEmpty()) { %>

        <div class="ui-comment">
	        Your seach return no results.  Click [Select Again] and try searching on something else.
	    </div>
	    
	    <script>
	        $(document).ready(function() {
	            $(".selection-criteria h3").click();
	        });
	    </script>
<%  } else { %>

       <jsp:include page="listMethodTable.jsp"></jsp:include>

<%  } %>

	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>