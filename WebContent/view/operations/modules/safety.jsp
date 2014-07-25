<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "
com.treetop.businessobjects.KeyValue, 
com.treetop.businessobjects.Safety,
com.treetop.controller.operations.InqOperations,
com.treetop.utilities.html.HTMLHelpersMasking,
java.math.BigDecimal
" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 
    Safety s = io.getBean().getSafetyMetrics();
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Safety</h3>
	</div>
	<div class="ui-widget-content ui-corner-bottom">
	
		<%  KeyValue keys = io.getCommentKeys();
		    keys.setKey1("safety");
		    String headerText = keys.getHeaderText();
		    keys.setHeaderText(null);
		    request.setAttribute("keys",keys); %>

        <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","safety"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>
         
             
		<%
			 //turn on headers for modules after this one
			 keys.setHeaderText(headerText);
			 request.setAttribute("keys",keys);
		%>
	</div>
</div>