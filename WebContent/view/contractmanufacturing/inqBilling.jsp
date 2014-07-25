<%@ page import = "com.treetop.controller.contractmanufacturing.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------- inqBilling.jsp ------------------------------//
//  Author :  John Hagler 2011-12-19
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//  4/2/12      TWalton	    made Adjustments for new Shell and functionality
//-----------------------------------------------------------------------//
  String inqTitle = "Create Custom Pack Billing";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqBilling ib = new InqBilling();

 try {
	ib = (InqBilling) request.getAttribute("viewBean");
 }
 catch(Exception e) {
   System.out.println("should never get here, inqBilling.jsp: " + e);
 }  
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
	<title><%= inqTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	 <title><%= inqTitle %></title>
	<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<h1>Create Custom Pack Billing</h1>		
<br/>	
		
		
  <form action="CtlBilling?requestType=listBilling" method="post">
  <input type="hidden" name="environment" value = "<%= ib.getEnvironment() %>">
  <input type="hidden" name="billingType" value = "<%= ib.getBillingType() %>">
  <fieldset>
   <legend>Choose a Manufacturing Order number to process</legend>

  <div class="center comment">Currently only allow MO's that are tied to Item Type 105 (Ocean Spray production at Selah)</div> 
<br/>  

<%
   if (ib.getListMOs().size() == 0)
   {
%>   
	<h3 class="center">Currently no Manufacturing Orders to Process</h3>
<%       
   }else{
%>         
   <table>
    <tr>
      <td>
        <label for="manufacturingOrderNumber" title="mouseover">Manufacturing Order:</label>
      </td>
       <td>

        <%=DropDownSingle.buildDropDown(ib.getListMOs(), "manufacturingOrderNumber", ib.getManufacturingOrderNumber(), "None", "B", "", "") %>
      </td>
      <td class="error">
        <%= ib.getManufacturingOrderNumberError() %>
      </td>
    </tr>

   </table>
   
<br/>   
   <div class="center"><input type="Submit" name="submit" value="GO"></div>
<%
    }
%>        
     </fieldset>
  </form>
 <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>