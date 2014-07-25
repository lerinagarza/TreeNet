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
    com.treetop.app.quality.InqMethod,
    com.treetop.app.quality.GeneralQuality,
    com.treetop.businessobjectapplications.BeanQuality,
    com.treetop.utilities.html.DropDownSingle,
    com.treetop.utilities.html.HTMLHelpersInput,
    java.util.Vector" %>
<%
//---------------- APP/Quality/inqMethod.jsp -----------------------//
// Author   :  Teri Walton       10/25/10 (thrown from servlet)
//    Search for a Method, a Procedure OR a Policy
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String errorPage = "Quality/inqMethod.jsp";
  String inqTitle = "Search for a Method";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists

 InqMethod inqMethod = new InqMethod();
 String addUrl = "CtlQuality?requestType=addMethod&environment=" + inqMethod.getEnvironment();
  String addDisplay = "Add a NEW Method";
 try
 {
    inqMethod = (InqMethod) request.getAttribute("inqViewBean");
    if (inqMethod.getRequestType().equals("")) {
       inqMethod.setRequestType("inqMethod");
    }
    if (inqMethod.getRequestType().trim().equals("inqProcedure") ||
        inqMethod.getRequestType().trim().equals("listProcedure")) {
       inqTitle = "Search for a Procedure";
       addUrl = "CtlQuality?requestType=addProcedure&environment=" + inqMethod.getEnvironment();
       addDisplay = "Add a NEW Procedure";
    }
    if (inqMethod.getRequestType().trim().equals("inqPolicy") ||
        inqMethod.getRequestType().trim().equals("listPolicy")) {
       inqTitle = "Search for a Policy";   
       addUrl = "CtlQuality?requestType=addPolicy&environment=" + inqMethod.getEnvironment();
       addDisplay = "Add a NEW Policy";
    }
 }
 catch(Exception e) { }  
 
    boolean allowAdd = false;
	if (inqMethod.getSecurityLevel().trim().equals("2")) {
	   allowAdd = true;
	}

%>

	<title><%=inqTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
		
	<div class="clearfix">
	   <h1 style="float:left">
	       <%=inqTitle %>
	   </h1>
<%  if (allowAdd) { %>
           <div style="float:right">
               <a class="ui-add" href="<%=addUrl %>" target = "blank">
                   <%=addDisplay %>
               </a>
           </div>
<% } %>
	</div>
	
	
<%  if (!inqMethod.getDisplayMessage().trim().equals("")) { %>
    <div class="ui-error">
        <%=inqMethod.getDisplayMessage().trim() %>
    </div>
<%  } %>
<%
  //*********************************************************************
   String formName = "inqMethod";
   String newRequestType = "listMethod";
   String saveButtonDisplay = "List Methods";
   String valueDisplay = "Method";
   if (inqMethod.getRequestType().trim().equals("inqProcedure") ||
       inqMethod.getRequestType().trim().equals("listProcecure"))
   {
      formName = "inqProcedure";
      newRequestType = "listProcedure";
      saveButtonDisplay = "List Procedures";
      valueDisplay = "Procedure";
   }    
   if (inqMethod.getRequestType().trim().equals("inqPolicy") ||
       inqMethod.getRequestType().trim().equals("listPolicy"))
   {
      formName = "inqPolicy";
      newRequestType = "listPolicy";
      saveButtonDisplay = "List Policies";
      valueDisplay = "Policy";
   }    
   request.setAttribute("formName", formName);
%>

<form  name = "<%= formName %>" action="/web/CtlQuality?requestType=<%= newRequestType %>" method="post">
	
<fieldset>
<legend>Choose:</legend>
    <input type="hidden" name="environment" value="<%=inqMethod.getEnvironment() %>">
    <div class="table-wrapper">
	
  <%= HTMLHelpersInput.inputBoxHidden("environment", inqMethod.getEnvironment()) %>
		<table>
		
		    <tr>
		        <td>
		         <label title="Will be tied to the Name in the File"><%= valueDisplay %>&nbsp;Number:</label>
		        </td>
		        <td>
		         <%= HTMLHelpersInput.inputBoxText("inqMethodName", inqMethod.getInqMethodName(), (valueDisplay + " Name"), 20, 20, "N", "N") %>
		        </td>
		        <td>
		            <label title="This will use what is entered and search the ENTIRE description for that value.">
		                <%= valueDisplay %>&#160;Description:
		            </label>
		        </td>
		        <td>
		            <%= HTMLHelpersInput.inputBoxText("inqMethodDescription", inqMethod.getInqMethodDescription(), (valueDisplay + " Description"), 30, 30, "N", "N") %>&#160;
		        </td>
		    </tr> 
       
       
<% if (inqMethod.getSecurityLevel().trim().equals("2")) { %>   
		    <tr>
		
		        <td>
		            <label title="Choose a Status.">Status:</label>
		        </td>
		        <td>
		            <%= DropDownSingle.buildDropDown(GeneralQuality.getListStatus(), "inqStatus", inqMethod.getInqStatus(), "", "N", "N") %>
		        </td>
		        <td colspan="2"></td>
		    </tr>
<% } else {
     out.println(HTMLHelpersInput.inputBoxHidden("inqStatus", "AC"));
   } %>    
      
		    <tr>
		        <td>
		            <label title="Select by the Scope, how this method relates to areas within Tree Top, Inc.">Scope:</label>
		        </td>
		        <td>
		            <%= DropDownSingle.buildDropDown(GeneralQuality.getListScope(), "inqScope", inqMethod.getInqScope(), "", "N", "N") %>
		        </td>
		    </tr>
		    
		        
		    <tr>
		        <td>
		            <label title="Select by Origination, who was the person who originated this method.">Origination:</label>
		        </td>
		        <td>
		            <%= DropDownSingle.buildDropDown(GeneralQuality.getListOrigination(), "inqOrigination", inqMethod.getInqOrigination(), "", "N", "N") %>
		        </td>
		        <td colspan="2"></td>
		    </tr>
		    
		        
		    <tr>
		        <td>
		            <label title="Select by person who approved this Method.  This Method was reviewed and approved by:">Approved By:</label>
		        </td>
		        <td>
		            <%= DropDownSingle.buildDropDown(GeneralQuality.getListApprovedByUser(), "inqApprovedBy", inqMethod.getInqApprovedBy(), "", "N", "N") %>
		        </td>
		    </tr>    
		    
		    <tr>
	            <td colspan="4" class="center">
	                <input type="submit" name="saveButton" value="<%=saveButtonDisplay %>" class="ui-button">
	            </td>
	        </tr>  
		      
		</table>
	</div>
</fieldset>
</form>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>