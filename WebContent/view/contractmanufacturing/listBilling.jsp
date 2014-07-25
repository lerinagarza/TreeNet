<%@ page import = "com.treetop.controller.contractmanufacturing.*" %>
<%@ page import = "com.treetop.businessobjects.Lot" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- listBilling.jsp ------------------------------//
//  Author :  Teri Walton 2012-4-3
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String inqTitle = "Review Lots to Process";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqBilling ib = new InqBilling();
 Lot  totalLine = new Lot();
 
 try {
	ib = (InqBilling) request.getAttribute("viewBean");
	if (!ib.getBeanInfo().getListLots().isEmpty())
	{ 
	   totalLine = (Lot) ib.getBeanInfo().getListLots().elementAt((ib.getBeanInfo().getListLots().size()-1));
    } 
 }
 catch(Exception e) {
   System.out.println("should never get here, inqBilling.jsp: " + e);
 }  
%><%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
 <form action="CtlBilling?requestType=updBilling" method="post">
 <input type="hidden" name="environment" value = "<%= ib.getEnvironment() %>">
 <input type="hidden" name="billingType" value = "<%= ib.getBillingType() %>">
 <input type="hidden" name="manufacturingOrderNumber" value = "<%= ib.getManufacturingOrderNumber() %>">
   <table class="styled">
<%
   if (!ib.getDisplayMessage().trim().equals("")){
%>
   <tr>
    <td class="error" colspan="3"><%= ib.getDisplayMessage() %></td>
   </tr>  
<%
   }
%>
   <tr>
    <td>Manufacturing Order:&#160; </td>
    <td><b><%= ib.getManufacturingOrderNumber() %>  
    </b></td>
    <td class = "center"><button type="button" onclick="window.location='CtlBilling?environment=<%= ib.getEnvironment() %>'">Request a new MO</button></td>
   </tr>   
   <tr>
    <td>Manufactured for:&#160;</td>
    <td><b>
<%
   if (ib.getBillingType().trim().equals("OSA"))
      out.println(" Ocean Spray Alliance ");
%>   
    &#160;</b></td>
    <td></td>
   </tr>   
   <tr>
    <td>Facility:&#160;</td>
    <td><b><%= ib.getBeanInfo().getMoHeader().getWarehouse().getFacility() %>&#160;&#160;
           <%= ib.getBeanInfo().getMoHeader().getWarehouse().getFacilityDescription() %>  
    </b></td>
    <td></td>
   </tr>      
   <tr>
    <td>Tree Top Item:&#160;</td>
    <td><b><%= ib.getBeanInfo().getMoHeader().getItem().getItemNumber() %>&#160;&#160;
           <%= ib.getBeanInfo().getMoHeader().getItem().getItemDescription() %> 
    </b></td>
   </tr>       
   <tr>
    <td>Ocean Spray Item:&#160;</td>
    <td><b><%= ib.getBeanInfo().getMoHeader().getItem().getM3ItemAliasPopular().trim() %>     
    </b></td>
    <td></td>
   </tr>    
   <tr>
    <td>Manufactured Quantity:&#160;</td>
    <td class="right"><b><%= HTMLHelpersMasking.maskNumber(totalLine.getOriginalQuantity(), 0) %>  
    </b></td>
    <td>
<%
   if (!totalLine.getQuantity().trim().equals("0.000000")){
%>        
    Date used to process transactions: &#160;<input type="text" class="datepicker" name="transactionDate" value = "<%= ib.getBeanInfo().getMoHeader().getActualStartDate().getDateFormatMMddyySlash()%>">
<%
   }
%>   
      &#160; 
    </td>
   </tr>      
   <tr>
    <td>Quantity to be Invoiced:&#160;</td>
    <td class="right"><b><%= HTMLHelpersMasking.maskNumber(totalLine.getQuantity(), 0) %> 
    </b></td>
    <td class="center">
<%
   if (!totalLine.getQuantity().trim().equals("0.000000")){
%>    
    <input type="Submit" name="submit" value="Process Billing">
<%
  }
%>
      &#160;
    </td>
  </tr>
 </table>
</form>
 <jsp:include page="listBillingTable.jsp"></jsp:include>

 <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>