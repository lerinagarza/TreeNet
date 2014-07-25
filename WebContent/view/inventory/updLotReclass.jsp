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
	<title>Lot Chosen for Reclassification</title>
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
java.util.Vector,
com.treetop.app.inventory.InqInventory,
com.treetop.app.inventory.UpdInventory,
com.treetop.businessobjects.Inventory,
com.treetop.businessobjectapplications.BeanInventory" %>
<%

UpdInventory updInv = (UpdInventory) request.getAttribute("updViewBean");
if (updInv == null) {
    updInv = new UpdInventory();
}
BeanInventory bean = updInv.getBeanInventory();
if (bean == null) {
    bean = new BeanInventory();
}
Vector listLots = updInv.getBeanInventory().getByItemVectorOfInventory();

String selectAgainUrl = request.getContextPath() + 
    "/CtlInventoryNew?requestType=inqLotReclass&environment=" + 
    updInv.getEnvironment();

 %>
<div class="clearfix">
    <div style="float:left">
        <h1>Lots chosen for Reclassification</h1>
    </div>
    
    <div style="float:right;">
        <a href="<%=selectAgainUrl %>" class="ui-select-again">
            Select Again
        </a>
    </div>
</div>

<div class="ui-comment">
    If the Reclassification Message begins with OK, the Reclassification went through.<br>
    If it says NOK, that means that it did not go through because of a problem.<br>
    Try any NOK reclassification within the M3 screens.
</div>

<h3>
    Reclassify(Process) Chosen (Checked) 
    Lots from Status <%= InqInventory.returnStatusDescription(updInv.getInqStatusFrom()) %> 
    to Status <%= InqInventory.returnStatusDescription(updInv.getInqStatusTo()) %>
</h3>


<table class="styled full-width">

<col>
<col>
<col>
<col>

<tr>
    <th>Warehouse</th>
    <th>Item</th>
    <th>Lot</th>
    <th>Reclassification Message</th>   
   </tr>
<%
   if (listLots != null &&
       listLots.size() > 0)
   {
       String atLeastOne = "";
       for (int x = 0; x < listLots.size(); x++)
       {
          Inventory i = (Inventory) listLots.elementAt(x);
          if (i.getCheckedValue() != null &&
             !i.getCheckedValue().equals(""))
          {
             atLeastOne = "Y";
%>   
   <tr>
    <td><%= i.getWarehouse().trim() %></td>
    <td><%= i.getItemNumber().trim() %></td>
    <td><%= i.getLotNumber().trim() %></td>    
   
   
   <%  String message = i.getComment().trim();
        if (message.equals("")) {
            message = "Try again after 10 min if this does not go through.";
        }
        String css = "";
        if (message.contains("NOK")) {
            css = "ui-error";
        }
        
         %> 
    <td class="<%=css %>">
        <%=message %>
    </td>  
   </tr>
<%
          }
       } 
%>   


<% if (atLeastOne.equals("")) { %>    

   <tr>
    <td colspan="4" class="center" style="padding:1em;">No Lots were Chosen</td>
   </tr>

<%  } %>


<% } %>



</table>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>