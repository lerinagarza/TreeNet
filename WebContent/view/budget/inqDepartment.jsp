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
	<title></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import = "com.treetop.controller.budget.InqBudget, 
com.treetop.businessobjects.AccountString,
com.treetop.utilities.html.DropDownSingle,
java.util.Vector,
com.treetop.utilities.html.HtmlSelect.DescriptionType
" %>

<%
    InqBudget ib = (InqBudget) request.getAttribute("inqBudget");
    if (ib == null) {
        ib = new InqBudget();
     }
 %> 

    <div class="clearfix">	
		<div style="float:left">
		   <h1>Department Definitions</h1>
		</div>
		<div style="float:right">
		  <a href="CtlBudget?environment=<%=ib.getEnvironment() %>" class="ui-select-again">
		      Return to Budget Maintenance
		  </a>
		</div>
	</div>
	
	
	
	<fieldset>
	<legend>Choose a Department to view its definition:</legend>
	<form action="CtlBudget?requestType=inqDepartment" method="post" id="inqDepartment">
	   <input type="hidden" name="environment" value="<%=ib.getEnvironment() %>">
	   <div class="row-fluid">
	       <div class="span3"></div>
	       <div class="span9">
	       <label for="department">Department</label>
		   <% Vector departments = ib.buildDropDownDepartments(); %>
	           <%=DropDownSingle.buildDropDown(departments,
	           "department",
	           "department",
	           ib.getDepartment(),
	           "",
	           false,
	           "",
	           DescriptionType.VALUE_ONLY) %>
           </div>
    	</div>
    	<div class="row-fluid">
           <div class="span3"></div>
           <div class="span9">
	          <input type="submit" value="List Definition" name="submit" class="ui-button">
    	   </div>
	   </div>
	
	</form>
	</fieldset>
	
	
	
	<% if (!ib.getErrorMessage().equals("")) { %>
	<br>
	<div class="ui-error"><%=ib.getErrorMessage() %></div>
	<% } %>
	
	<br>
	
	<% Vector accts = ib.getBean().getDepartmentDefinitions(); %>
	<% if (!accts.isEmpty()) { %>
	
	<div class="ui-comment">
	   * indicates that any value will be included.
	</div>
	
	<h3>Budget Department: <%=ib.getDepartment() %></h3>
	<div class="comment">If you require changes to this defintion, please contact the HelpDesk @ ext 1425</div>
	
	<table class="styled full-width">
        <tr>
            <th>Acct Grp</th>
            <th>Dim 1<br>(Account)</th>
            <th>Dim 2<br>(Facility)</th>
            <th>Dim 3<br>(Cost Center)</th>
            <th>Dim 4<br>(Prod Group)</th>
            <th>Dim 5<br><br></th>
            <th>Dim 6<br>(Ref No)</th>
            <th>Dim 7<br>(Setup)</th>
            <th>Responsible</th>
            <th>Description</th>
            <th>*Summary Level</th>
        </tr>
        
        <%  for(int i=0; i<accts.size(); i++) {
            AccountString acct = (AccountString) accts.elementAt(i);
         %>
         <tr>
            <td><%=acct.getDimension1().getAccountGroup() %></td>
            <td><%=acct.getDimension1().getAccountID() %></td>
            <td><%=acct.getDimension2().getAccountID() %></td>
            <td><%=acct.getDimension3().getAccountID() %></td>
            <td><%=acct.getDimension4().getAccountID() %></td>
            <td><%=acct.getDimension5().getAccountID() %></td>
            <td><%=acct.getDimension6().getAccountID() %></td>
            <td><%=acct.getDimension7().getAccountID() %></td>
            <td><%=acct.getBudgetResponsible() %></td>
            <td><%=acct.getDescription() %></td>
            <td><%=acct.getComment() %></td>
         </tr>
         <% } //end loop %>
      </table>
      
    <div class="comment">
    <b>*Summary Level:</b>  Defines to what level of detail accounts are summarized to.<br>
    E.g.  Dim 1, 2, 3  will be summarized down to Account, Facility, and Cost Center.  
    All lower level dimensions will be summed.</div>
      
	<% } //end if empty %>
	

	<br>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>