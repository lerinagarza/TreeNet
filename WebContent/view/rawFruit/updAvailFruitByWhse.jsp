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
<%@ page import = "
    com.treetop.app.rawfruit.UpdAvailableFruit,
    com.treetop.businessobjects.Warehouse,
    com.treetop.businessobjects.KeyValue,
    com.treetop.utilities.html.DropDownTriple,
    java.util.Vector
    " %>
<%
//----------------------- updAvailFruitByWhse.jsp ------------------------------//
//  Author :  Teri Walton  08/06/10
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//

 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists
 String readOnlyMain = "Y";
 UpdAvailableFruit updAvail = new UpdAvailableFruit();
 Warehouse whseObject = new Warehouse();
 String updateDateTime = "";
 try  {
    updAvail = (UpdAvailableFruit) request.getAttribute("updViewBean");
    whseObject = updAvail.getBeanAvailFruit().getAvailFruitByWhse().getWarehouse();
 } catch(Exception e) {
   System.out.println("should never get here, updAvailFruitByWhse.jsp: " + e);
 }  
//**************************************************************************//

    
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=updAvailFruit&environment=" + updAvail.getEnvironment() + "\">Select Again");
   if (updAvail.getListLocation().size() > 1)
   {
      for (int x = 0; x < updAvail.getListLocation().size(); x++)
      {
          DropDownTriple ddt = (DropDownTriple) updAvail.getListLocation().elementAt(x);
          String locationNumber = ddt.getList3Value().trim();
          int xLastIndex = locationNumber.lastIndexOf("-*-");
          if (xLastIndex > 0)
              locationNumber = locationNumber.substring((xLastIndex + 3));
          if (!locationNumber.trim().equals(updAvail.getLocAddNo()))
          {
             setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=updAvailFruit&environment=" + updAvail.getEnvironment());
             setExtraOptions.append("&whseNo=" + updAvail.getWhseNo());
             setExtraOptions.append("&locAddNo=" + locationNumber + "\">");
             setExtraOptions.append("Go To Location: " + ddt.getList3Description());
          }
      }
   }   

//*****************************************************************************
 String formName = "updAvail";
 request.setAttribute("formName", formName);
%>

	
	<title>Update Available Fruit</title>
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
    <h1 style="float:left">Update Available Fruit</h1>
    
<%  //if the user is internal (not an external grower/warehouse) then display the select again button
    if (internal) { %>
    <div style="float:right">
<%
String selectAgainUrl = "/web/CtlRawFruit?requestType=updAvailFruit"
             + "&environment=" + updAvail.getEnvironment();
 %>    
        <a href="<%=selectAgainUrl %>" class="ui-select-again">Select Again</a>
    </div>
<%  } %>



</div>	
<% if (!updAvail.getDisplayMessage().trim().equals("")) { %>      
<div class="ui-error"><%= updAvail.getDisplayMessage().trim() %></div>
<%  } %>

<div class="clearfix">
    <div style="float:left;">
        <h2>
            <%= whseObject.getWarehouse() %>&nbsp;&nbsp;<%= updAvail.getWhseName() %>
        </h2>
        <h3>

<%
if (updAvail.getListLocation().size() == 1) { %>
        <%= updAvail.getLocAddNo() %>&nbsp;&nbsp;<%= whseObject.getWarehouseDescription() %>

<% } else if (updAvail.getListLocation().size() > 1) { %>
            <select id="changeLocation">
<%   for (int i=0; i < updAvail.getListLocation().size(); i++) {
       DropDownTriple ddt = (DropDownTriple) updAvail.getListLocation().elementAt(i);
       String locationNumber = ddt.getList3Value().trim();
       
       int j = locationNumber.lastIndexOf("-*-");
       if (j > 0) {
           locationNumber = locationNumber.substring((j + 3));
       }
       
       String url = "/web/CtlRawFruit?requestType=updAvailFruit"
                   + "&environment=" + updAvail.getEnvironment()
                   + "&region=" + ddt.getList1Value().trim()
                   + "&whseNo2=" + ddt.getList2Value().trim()
                   + "&locAddNo=" + locationNumber;
       String selected = "";            
       if (locationNumber.trim().equals(updAvail.getLocAddNo())) {
           selected = "selected";
       }
       
      
       %>
          <option value="<%=url %>" <%=selected %>><%=ddt.getList3Value() %> <%=ddt.getList3Description() %></option>
<% }    //end loop %>
            </select>
            
            <script>
                $("#changeLocation").change(function () {
                    var url = $(this).val();
                    window.location.href=url;
                });
            </script>
<% }    //end if empty %>

        </h3>
        
        
    </div>
    <div style="float:right;">
        Inventory last updated: <b><%= updAvail.getUpdateDate() %>&nbsp;&nbsp;&nbsp;<%= updAvail.getUpdateTime() %>&nbsp;&nbsp;<%= updAvail.getUpdateUser() %></b>
    </div>
</div>

<br>

<form action="/web/CtlRawFruit?requestType=updAvailFruit" method="post">
    <input type="hidden" name="environment" value="<%=updAvail.getEnvironment() %>">
    <input type="hidden" name="whseNo" value="<%=updAvail.getWhseNo() %>">
    <input type="hidden" name="locAddNo" value="<%=updAvail.getLocAddNo() %>">
    
	<div class="ui-widget">
	    <div class="ui-widget-header ui-corner-top clearfix">
	        <h3 style="padding-left:.25em; float:left;">Available Fruit Details</h3>
	        <input type="submit" class="ui-button" name="saveButton" value="Save Changes" style="float:right">
	    </div>
	
	    <div class="ui-widget-content ui-corner-bottom">
	    
			<jsp:include page="updAvailFruitByWhseDetail.jsp"></jsp:include>
	
	    </div>
	</div>
    
</form>

<br>

<div class="ui-widget">

    <div class="ui-widget-content ui-corner-all">

         <%  KeyValue keys = new KeyValue();
             keys.setEnvironment(updAvail.getEnvironment());
             keys.setEntryType("AvailableFruitComment");
             keys.setKey1(updAvail.getWhseNo());
             keys.setKey2(updAvail.getLocAddNo());
             keys.setKey3("");
             keys.setKey4("");
             keys.setKey5("");
             keys.setVisibleOnLoad(true);
             keys.setViewOnly(false);
             keys.setManagedByTT(true);
             
             request.setAttribute("keys",keys); %>
        <jsp:include page="../utilities/commentSection.jsp"></jsp:include>
    </div>
</div>

<br>
<div class="page-break"></div>

<div class="ui-widget">
    <div class="ui-widget-header ui-corner-top">
        <h3 style="padding-left:.25em;">Scheduled Load Details</h3>
    </div>

    <div class="ui-widget-content ui-corner-bottom">
        <% request.setAttribute("sendFrom","updAvailFruitByWhse.jsp"); %>
       <jsp:include page="listSchedFruitTable.jsp"></jsp:include> 
    </div>
</div>
<br>
<br>

		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>