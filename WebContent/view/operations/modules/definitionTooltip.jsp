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
    <%  boolean isEmpty = false;
        if (isEmpty) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
        <div class=table-wrapper>
			<table>
				<tr>
					<th class=left>
					   Consumer Complaints Index
				   </th>
					<td class=right>_____</td>
				</tr>
				<tr>
	                <th class=left>
	                   Holds for Non Conformance %
                   </th>
	                <td class=right>_____</td>
	            </tr>
			</table>
		</div>
	<% }   // end if empty %>
		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("quality");
            request.setAttribute("keys",keys); %>
		<jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
	</div>
</div>