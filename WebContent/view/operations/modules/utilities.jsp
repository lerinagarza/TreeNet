<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = 
"com.treetop.businessobjects.KeyValue, 
com.treetop.controller.operations.InqOperations,
com.treetop.businessobjects.ManufacturingFinance,
java.util.LinkedHashMap,
java.util.Iterator, 
java.util.Map,
java.math.BigDecimal" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    LinkedHashMap lhm = io.getBean().getUtilities(); 
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Utilities</h3>
	</div>
	<div class="ui-widget-content ui-corner-bottom">
		
		
	<% if (lhm.isEmpty()) { %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>

		<table class="styled full-width">
		
			<colgroup>
				<col title="Account" />
				<col title="Earned" />
				<col title="Spend" />
				<col title="Variance" />
			</colgroup>
			<tr>
				<th></th>
				<th>Earned</th>
				<th>Spend</th>
				<th>Variance</th>
			</tr>
    <%  
    
        BigDecimal actualTotal = BigDecimal.ZERO;
        BigDecimal earningsTotal = BigDecimal.ZERO;
        BigDecimal varianceTotal = BigDecimal.ZERO;
    
        Iterator i = lhm.entrySet().iterator();
        while (i.hasNext()) {
		Map.Entry me = (Map.Entry)i.next();
		String key = (String) me.getKey();
		ManufacturingFinance mf = (ManufacturingFinance) me.getValue();
		
		BigDecimal actual = mf.getWtdActual();
		BigDecimal earnings = mf.getWtdEarnings();
		BigDecimal variance = mf.getWtdEarningsVar();
		
		actualTotal = actualTotal.add(actual);
		earningsTotal = earningsTotal.add(earnings);
		varianceTotal = varianceTotal.add(variance);
		
		boolean displayRow = false;
		if (actual.compareTo(BigDecimal.ZERO) != 0) {
		  displayRow = true;
		}
        if (earnings.compareTo(BigDecimal.ZERO) != 0) {
          displayRow = true;
        }


		
    %>
			<tr>
				<td><%=key %></td>
				<td><%=HTMLHelpersMasking.maskAccountingFormat(earnings) %></td>
				<td><%=HTMLHelpersMasking.maskAccountingFormat(actual) %></td>
				<td><%=HTMLHelpersMasking.maskAccountingFormat(variance) %></td>
			</tr>
			<%	} %>
			
			<tr class="sub-total">
				<td>Total</td>
                <td><%=HTMLHelpersMasking.maskAccountingFormat(earningsTotal) %></td>
                <td><%=HTMLHelpersMasking.maskAccountingFormat(actualTotal) %></td>
                <td><%=HTMLHelpersMasking.maskAccountingFormat(varianceTotal) %></td>
			</tr>
			
		</table>
		
    <% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
       if (plantTotal == null) {
           plantTotal = BigDecimal.ZERO;
       }
       plantTotal = plantTotal.add(varianceTotal);
       request.setAttribute("plantTotal", plantTotal);
     %>
		
    <%  } %>
    
    
    
    
		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("utilities");
            request.setAttribute("keys",keys); %>

         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","utilities"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

	</div>
</div>