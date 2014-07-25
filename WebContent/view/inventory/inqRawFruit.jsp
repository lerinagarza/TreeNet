<%@ page import = "java.util.Vector" %>
<%@ page import = "com.treetop.app.inventory.InqInventory" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.html.HtmlSelect.DescriptionType" %>
<%@ page import = "com.treetop.utilities.html.DropDownDual %>
<% String pageTitle = "Find Raw Fruit Inquiry"; %>
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
	<title><%= pageTitle %></title>
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
		InqInventory ii = (InqInventory) request.getAttribute("inqViewBean");
 		if (ii == null) {
 			ii = new InqInventory();
 		}
 		
 		Vector options = ii.getFacilityWarehouseDDValues();
 		%>
 		<h1><%= pageTitle %></h1>
		<span class="comment"></span>	
		<form action="CtlInventoryNew?requestType=listRawFruit" method="post">		
 			<input type="hidden" name="environment" value="PRD">
  			<fieldset>
  				<legend>Selection</legend>
				<div class="table-wrapper"> 
   					<table>
    					<tr >
      						<td>
        						<label for="inqFacility" title="Facility">Facility:</label>
      						</td>
       						<td>
        						<%=DropDownDual.buildMaster(options, 
									"inqFacility", "inqFacility", "", ii.getInqFacility(), "Choose a Facility", 
									false, DescriptionType.VALUE_DESCRIPTION) %>
       						</td>
     						<td class="error">
      						</td>
      						</tr>
      					<tr >
      						<td>
        						<label for="inqWhse" title="Warehouse">Warehouse:</label>
      						</td>
       						<td>
        						<%=DropDownDual.buildSlave(options, "inqFacility",
									"inqWhse", "inqWhse", "", ii.getInqWhse(), "Choose a Warehouse", 
									false, DescriptionType.VALUE_DESCRIPTION) %>
       						</td>
     						<td class="error">
      						</td>
    					</tr>
    					<tr >
      						<td>
        						<label for="inqLocation" title="Location Starts With">Location Starts With:</label>
      						</td>
       						<td>
        						<input type="text" id="inqLocation" name="inqLocation" value="<%=ii.getInqLocation()%>" />
       						</td>
     						<td class="error">
      						</td>
    					</tr>
    					<tr >
    						<td>
        						<label for="inqRunType" title="Run Type">Run Type:</label>
      						</td>
       						<td>
        						<%=InqInventory.buildDropDownRunType(ii.getInqRunType(), "") %>
       						</td>
     						<td class="error">
      						</td>
      					</tr>
      					<tr >
      						<td>
        						<label for="inqOrganicConventional" title="Organic/Conventional">Organic/Conventional:</label>
      						</td>
       						<td>
        						<%=InqInventory.buildDropDownConvOrganic(ii.getInqOrganicConventional(),"") %>
       						</td>
     						<td class="error">
      						</td>
      					</tr>
      					<tr >
      						<td>
        						<label for="inqCOO" title="Country of Origin">Country of Origin:</label>
      						</td>
       						<td>
        						<%=InqInventory.buildDropDownCOO(ii.getInqCOO(), "") %>
       						</td>
     						<td class="error">
      						</td>
    					</tr>
    					<tr >
    						<td>
        						<label for="inqTGRADE" title="TGrade">TGrade:</label>
      						</td>
       						<td>
        					<%=InqInventory.buildDropDownTGRADE(ii.getInqTGRADE(), "") %>
       						</td>
     						<td class="error">
      						</td>
      					</tr>
      					<tr >
      						<td>
        						<label for="inqVariety" title="Variety">Variety:</label>
      						</td>
       						<td>
        						<%=InqInventory.buildDropDownVariety(ii.getInqVariety(),"") %>
       						</td>
     						<td class="error">
      						</td>
      					</tr> 
      					<tr >
      						<td>
        						<label for="inqRFItemGroup" title="RFItemGroup">Crop/Item Group:</label>
      						</td>
       						<td>
        						<%=InqInventory.buildDropDownRFItemGroup() %>
       						</td>
     						<td class="error">
      						</td>
      					</tr> 
      					<tr>
      						<td>
        						<label for="inqShowDetails" title="Details">Show detailed information (Lots):</label>
      						</td>
       						<td>
        						<input type="checkbox" name ="inqShowDetails" id="inqShowDetails" value="Details" 
        							<% if(!ii.getInqShowDetails().equals("")) { %>checked="checked"<% } %> />
       						</td>
     						<td class="error">
      						</td>
      					</tr>     					
   					</table>
 				</div>   
   				<div class="center">
   					<input type="Submit" name="submit" value="Retrieve Inventory">
   				</div>
    		</fieldset>
    	</form>
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>