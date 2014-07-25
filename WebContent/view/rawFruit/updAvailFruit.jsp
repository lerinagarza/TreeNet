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
<%@page import ="
    com.treetop.app.rawfruit.InqAvailableFruit,
    com.treetop.utilities.html.DropDownTriple,
    com.treetop.utilities.html.HtmlSelect.DescriptionType "
 %>
<%    
    InqAvailableFruit inqAvail = (InqAvailableFruit) request.getAttribute("inqViewBean");
    if (inqAvail == null) 
        inqAvail = new InqAvailableFruit();
    String requestType = "updAvailFruit";
   if (inqAvail.getRequestType().trim().equals("updAvailFruitToPurchase")) {
      requestType = "updAvailFruitToPurchase";
%>	
<title>Fruit Available to Purchase</title>
<% } else { %>
<title>Update Available Fruit</title>
<% } %>	
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<% 
   if (requestType.equals("updAvailFruitToPurchase")) {
%>	
<h1>Fruit Available to Purchase</h1>
<% } else { %>
<h1>Update Available Fruit</h1>
<% } %>

<%  if (!inqAvail.getDisplayMessage().trim().equals("")) { %>
<div class="ui-error"><%=inqAvail.getDisplayMessage() %></div>
<%  } %> 


<form action="/web/CtlRawFruit?requestType=<%= requestType %>" method="post">
    <input type="hidden" name="environment" value="<%=inqAvail.getEnvironment() %>">
	<fieldset>
	   <legend>Choose a Warehouse and Location</legend>
	       
           <table style="margin:0 auto; width:400px;">
               <tr>
                      <td style="width:75px;"><label>Region</label></td>
                      <td>
                          <%=DropDownTriple.buildList1(inqAvail.getListReport(), 
                             "region", "region", "class", "", "Select a Region", false, DescriptionType.DESCRIPTION_ONLY, true) %>
                      </td>
                  </tr>
                  <tr>
                      <td><label>Warehouse</label></td>
                      <td>
                          <%=DropDownTriple.buildList2(inqAvail.getListReport(), 
                             "whseNo2", "whseNo2", "class", "", "Select a Warehouse", false, DescriptionType.DESCRIPTION_VALUE) %>
                      </td>
                  </tr>
                  <tr>
                      <td><label>Location</label></td>
                      <td>
                          <%=DropDownTriple.buildList3(inqAvail.getListReport(), 
                             "region","whseNo2","locAddNo", "locAddNo", "class", "", null, false, DescriptionType.DESCRIPTION_VALUE) %>
                      </td>
                  </tr>
                  <tr>
                      <td colspan="2" class="center">
                          <input type="submit" name="goButton" value="Go to Update" class="ui-button">
                      </td>
                  </tr>
                  
           </table>

	   
	</fieldset>
</form>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>