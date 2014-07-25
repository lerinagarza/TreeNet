<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import = "
java.util.LinkedHashMap, 
java.util.Iterator,
java.util.Vector, 
java.util.Map, 
java.math.BigDecimal, 
com.treetop.businessobjects.ManufacturingOrderDetail,
com.treetop.businessobjects.KeyValue, 
com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3 style="padding-left:.25em;">Frozen Cherries</h3>
	</div>

	<div class="ui-widget-content ui-corner-bottom">
	
    <% LinkedHashMap orders = io.getBean().getFrozenCherryMOs();
     
        if (orders.isEmpty()) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
	
		<table class="styled full-width">
		  <colgroup>
		      <col>
		      <col>
		      <col>
		      <col>
		  </colgroup>
			<tr>
			    <th></th>
			    <th style="width:22.5%">Plan</th>
			    <th style="width:22.5%">Actual</th>
			    <th style="width:22.5%">% of Plan</th>
			</tr>
			
        
<%	Iterator i = orders.entrySet().iterator();
    
    //Summary fields        
    
    BigDecimal plannedTotal = BigDecimal.ZERO;
    BigDecimal actualTotal = BigDecimal.ZERO;
    BigDecimal percentTotal = BigDecimal.ZERO;

    while (i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        String key = (String) me.getKey();
        ManufacturingOrderDetail m = (ManufacturingOrderDetail) me.getValue();
        
        
        //data fields
        BigDecimal planned = m.getProductionPlanned();
        BigDecimal actual = m.getProduction();
        BigDecimal percent = m.getProductionPercentagePlanned();
       
        plannedTotal = plannedTotal.add(planned);
        actualTotal = actualTotal.add(actual);               
        
        boolean displayRow = false;

        
        // TESTING
        displayRow = true;
        
        if (displayRow) {
        
        
        
        //calculate summary fields


        
    %>

			<tr>
				<td><%=key %></td>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(planned) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(actual) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskPercent(BigDecimal.ZERO) %></td>
			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>

<%
    if (plannedTotal.compareTo(BigDecimal.ZERO) != 0) {
        percentTotal = actualTotal.divide(plannedTotal,4,BigDecimal.ROUND_HALF_UP);
    }
 %>
			<tr class="sub-total">
				<td>Total</td>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(plannedTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(actualTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskPercent(BigDecimal.ZERO) %></td>
			</tr>
		
		</table>
		
	<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
	   if (plantTotal == null) {
	       plantTotal = BigDecimal.ZERO;
	   }
	   plantTotal = plantTotal.add(BigDecimal.ZERO);
	   request.setAttribute("plantTotal", plantTotal);
	    %>
		
	<% } //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("frozenCherries");
            request.setAttribute("keys",keys); %>
            
       <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","frozenCherries"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>


	</div>
</div>
