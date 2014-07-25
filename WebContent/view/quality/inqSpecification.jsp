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
	<title>Search for a Specification</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import="com.treetop.app.quality.InqSpecification, com.treetop.app.quality.GeneralQuality, com.treetop.utilities.html.DropDownSingle, com.treetop.utilities.html.HTMLHelpersInput" %>
<%  
	InqSpecification inqSpecification = (InqSpecification) request.getAttribute("inqViewBean");
	if (inqSpecification == null) {
	    inqSpecification = new InqSpecification();
	}
	if (inqSpecification.getRequestType().equals("")) {
	    inqSpecification.setRequestType("inqSpec");
	}
	
	boolean allowAdd = false;
	if (inqSpecification.getSecurityLevel().trim().equals("2")) {
	   allowAdd = true;
	}
	
 %>

   <div class="clearfix">
       <h1 style="float:left">
           Search for a Specification
       </h1>
<%  if (allowAdd) { %>
           <div style="float:right">
               <a class="ui-add" href="/web/CtlQuality?requestType=addSpec&environment=<%=inqSpecification.getEnvironment() %>" target = "blank">
                   Add a NEW Specification
               </a>
           </div>
<% } %>
    </div>
	
<%  if (!inqSpecification.getDisplayMessage().trim().equals("")) { %>
    <div class="ui-error">
        <%= inqSpecification.getDisplayMessage().trim() %>
    </div>
<%  } %>
	<form name="inqSpecification" action="/web/CtlQuality?requestType=listSpec" method="post">
	<input type="hidden" name="environment" value="<%=inqSpecification.getEnvironment() %>">
	<fieldset>
	   <legend>Choose:</legend>
	   <div class="table-wrapper">
		   <table>
		       <tr>
		           <td><label for="inqSpecNumber">Specification Number:</label></td>
		           <td><input type="text" id="inqSpecNumber" name="inqSpecNumber" size="10" 
		              value="<%=inqSpecification.getInqSpecNumber() %>">
		              <div class="error"><%= inqSpecification.getInqSpecNumberError() %></div>
		           </td>
		           <td><label for="inqSpecDescription">Specification Description:</label></td>
	               <td><input type="text" id="inqSpecDescription" name="inqSpecDescription" size="30" 
	                   value="<%=inqSpecification.getInqSpecDescription() %>"></td>
		       </tr>
		       <tr>
                   <td><label for="inqFormula">Formula Number:</label></td>
                   <td><input type="text" id="inqFormula" name="inqFormula" size="10" 
                        value="<%=inqSpecification.getInqFormula() %>">
                        <div class="error"><%= inqSpecification.getInqFormulaError() %></div>
                   </td>
                   <td><label for="inqFormulaName">Formula Description (Name):</label></td>
                   <td><input type="text" id="inqFormulaName" name="inqFormulaName" size="30" 
                        value="<%=inqSpecification.getInqFormulaName() %>"></td>
               </tr>
               
               <tr>
                   <td><label for="inqWarehouse">Production Warehouse:</label></td>
                   <td>
                       <%=inqSpecification.buildDropDownSpecItemProductionWarehouse(inqSpecification.getInqWarehouse()) %>
                   </td>
                   <td colspan="2"></td>
               </tr>
               
               <tr>
                   <td><label for="inqItemNumber">Item Number:</label></td>
                   <td><input type="text" id="inqItemNumber" name="inqItemNumber" size="10" 
                        value="<%=inqSpecification.getInqItemNumber() %>">
                        <div class="error"><%=inqSpecification.getInqItemNumberError() %></div>
                   </td>
                   <td><label>Item Type:</label></td>
                   <td>
                    <%= inqSpecification.buildDropDownItemType("N") %>
                   </td>
               </tr>
               <tr>
                   <td><label for="">Product Size:</label></td>
                   <td>
                        <%= inqSpecification.buildDropDownProductSize("N") %>
                   </td>
                   <td><label for="">Product Group:</label></td>
                   <td>
                        <%= inqSpecification.buildDropDownProductGroup("N") %>
                   </td>
               </tr>
               <tr>
                   <td><label for="">Type:</label></td>
                   <td>
                        <%= DropDownSingle.buildDropDown(GeneralQuality.getListType(), "inqSpecType", 
                        inqSpecification.getInqSpecType(), "", "N", "N") %>
                   </td>
                   <td colspan="2"></td>
               </tr>
<%  if (allowAdd) { %>               
               <tr>
                   <td><label for="">Status:</label></td>
                   <td>
                        <%= DropDownSingle.buildDropDown(GeneralQuality.getListStatus(), "inqStatus", 
                        inqSpecification.getInqStatus(), "", "N", "N") %>
                   </td>
                   <td colspan="2"></td>
               </tr>
<%
   }
   else
     out.println(HTMLHelpersInput.inputBoxHidden("inqStatus", "AC"));
%>       
               <tr>
                   <td><label for="">Scope:</label></td>
                   <td>
                        <%= DropDownSingle.buildDropDown(GeneralQuality.getListScope(), "inqScope", 
                        inqSpecification.getInqScope(), "", "N", "N") %>
                   </td>
                   <td colspan="2"></td>
               </tr>
               <tr>
                   <td><label for="">Origination:</label></td>
                   <td>
                        <%= DropDownSingle.buildDropDown(GeneralQuality.getListOrigination(), "inqOrigination", 
                        inqSpecification.getInqOrigination(), "", "N", "N") %>
                   </td>
                   <td colspan="2"></td>
               </tr>
               <tr>
                   <td><label for="">Approved By:</label></td>
                   <td>
                        <%= DropDownSingle.buildDropDown(GeneralQuality.getListApprovedByUser(), "inqApprovedBy", 
                        inqSpecification.getInqApprovedBy(), "", "N", "N") %>
                   </td>
                   <td colspan="2"></td>
               </tr>
               <tr>
                   <td colspan="4" class="center">
                    <input type="submit" name="saveButton" value="List Specifications">
                   </td>
               </tr>
		   </table>
	   </div>
	</fieldset>
	</form>
	
	<script>
	   $("input[name=saveButton]").button();
	</script>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>