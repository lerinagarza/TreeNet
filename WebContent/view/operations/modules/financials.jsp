<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "com.treetop.businessobjects.KeyValue, com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    
    boolean isEmpty = false;
    
    String[] accts = {"Lab and Quality Control","Indirect","Warehouse and Shiping","Other"};
    
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Misc Financials</h3>
	</div>
	<div class="ui-widget-content ui-corner-bottom">
    <%  
        if (isEmpty) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
		<table class="styled full-width">
            <tr>
                <th></th>
                <th>Labor</th>
                <th>Supplies</th>
                <th>Total</th>
            </tr>
            <tr>
                <td>Earnings</td>
                <td class="right"></td>
                <td class="right"></td>
                <td class="right"></td>
            </tr>
            <tr>
                <td>Actual</td>
                <td class="right"></td>
                <td class="right"></td>
                <td class="right"></td>
            </tr>
            <tr class="sub-total">
                <td>WTD Variance</td>
                <td class="right"></td>
                <td class="right"></td>
                <td class="right"></td>
            </tr>
            <tr class="bold">
                <td class="right">MTD Variance</td>
                <td class="right"></td>
                <td class="right"></td>
                <td class="right"></td>
            </tr>
            <tr class="bold">
                <td class="right">YTD Variance</td>
                <td class="right"></td>
                <td class="right"></td>
                <td class="right"></td>
            </tr>
		</table>
	<% }   // end if empty %>
		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("financials");
            request.setAttribute("keys",keys); %>
		
         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","financials"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

	</div>
</div>