<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import = "java.math.BigDecimal,
com.treetop.businessobjects.KeyValue, 
com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 
    
    BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
       if (plantTotal == null) {
           plantTotal = BigDecimal.ZERO;
       }
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Summary</h3>
	</div>
	<div class="ui-widget-content ui-corner-bottom">

        <table>
            <tr>
                <td>
                    <h4>Plant Total:</h4>
                </td>
                <td class="bold" id="plantTotal">
                    <%=HTMLHelpersMasking.maskAccountingFormat(plantTotal) %>
                </td>
            </tr>
        </table>
    
    <%--Calls service to write total amount shown on screen to a workfile --%>
    <%  io.storeTotalAmount(plantTotal); %>		

		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("summary");
            request.setAttribute("keys",keys); %>

         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","summary"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

	</div>
</div>