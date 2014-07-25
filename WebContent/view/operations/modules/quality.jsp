<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "com.treetop.businessobjects.KeyValue, com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Quality</h3>
	</div>
	<div class="ui-widget-content ui-corner-bottom">

        <%  if (io.getRequestType().equals("monthly")) { %>
        
	        <% request.setAttribute("weeklyCommentType","quality"); %>
	         <jsp:include page="weeklyComments.jsp"></jsp:include>
	        
        <% } else { %>
		
			<%  KeyValue keys = io.getCommentKeys();
	            keys.setKey1("quality");
	             String headerText = keys.getHeaderText();
	            keys.setHeaderText(null);
	            request.setAttribute("keys",keys); %>
	
			    <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
	
			<%
	            //turn on headers for modules after this one
	            keys.setHeaderText(headerText);
	            request.setAttribute("keys",keys);
	         %>
         
         <% } %>
	</div>
</div>