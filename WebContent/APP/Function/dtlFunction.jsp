<%@ page language = "java" 
import = " 
    com.treetop.utilities.html.JSPExceptionMessages,
    com.treetop.app.function.DtlFunction,
    com.treetop.businessobjects.TicklerFunctionDetail,
    com.treetop.data.UserFile,
    java.util.Vector
    " 
%>
<%
//---------------  dtlFunction.jsp  ------------------------------------------//
//   Includes Sections (Tables)
//      listFunctionsTable
//
//   Author :  Teri Walton  12/8/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//------------------------------------------------------//
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "Function/dtlFunction.jsp";
  String listTitle = "List Functions";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists
 DtlFunction dtlF = new DtlFunction();
 Vector functionDetails = new Vector();
 int  functionsDetailCount   = 0;
 try
 {
    dtlF = (DtlFunction) request.getAttribute("dtlFunctionViewBean");
    
    functionDetails = dtlF.getDtlInformation().getDependantFunctions();
    functionsDetailCount  = functionDetails.size();
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
    request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",listTitle);
//*****************************************************************************
%>

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

<h1>New Item Process</h1>
<h2><%=dtlF.getDtlInformation().getIdKeyValue() %> <%=dtlF.getDtlInformation().getIdKeyDescription() %></h2>
<h3>Function Dependencies of: <%=dtlF.getDtlInformation().getDescription() %> (<%=dtlF.getDtlInformation().getPhaseName() %>)</h3>


<table class="styled">
<% if (functionsDetailCount == 0) { %>   
	<tr>
    	<td>There are NO Functions to Display.</td>   
	</tr> 
<% } else { %>  
	<tr>
		<th>Sequence</th>
		<th>Function</th>
		<th>Responsible Person</th>
		<th>Due Date</th>    
		<th>Completed</th>
	</tr>
<%
  ///  **-------------- LIST of Functions with Status --------------------**
   try {  // All old References - Updateable
   
      for (int cntFD = 0; cntFD < functionsDetailCount; cntFD++) {
         TicklerFunctionDetail thisrow = (TicklerFunctionDetail) functionDetails.elementAt(cntFD);
%>
	<tr>  
		<td><%= thisrow.getPhaseName() %></td>
		<td><%= thisrow.getDescription() %></td>
		<td>
<%
    String displayPerson = thisrow.getRespPerson();
    try {
       UserFile thisUser = new UserFile(displayPerson);
       if (thisUser.getUserNameLong() != null &&
           !thisUser.getUserNameLong().equals(""))
           displayPerson = thisUser.getUserNameLong();
    } catch(Exception e) {}
%>    
		  <%= displayPerson %>
		</td>
		
		<td><%= thisrow.getTargetDate() %></td>
		<td><%= thisrow.getStatus() %></td>
	</tr>
<%     
      } // For Loop
   } catch(Exception e) {}
   
  }// end if count == 0 
%>        
 </table>  
<br>
<br>
<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>