<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "
com.treetop.businessobjects.KeyValue,
com.treetop.businessobjects.AccountData,  
com.treetop.controller.operations.InqOperations,
java.math.BigDecimal,
java.util.LinkedHashMap,
java.util.Iterator,
java.util.Map" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    LinkedHashMap lhm = io.getBean().getPlantJournals();
    boolean isEmpty = lhm.isEmpty();
    
    
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Misc Journal Entries</h3>
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
    <%  boolean first = true;    
        String lastCostCenter = "";
        String lastCostCenterDescr = "";
        String thisCostCenter = "";
        int count = 0;
        
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal grandTotal = BigDecimal.ZERO;
        
        Iterator i = lhm.entrySet().iterator();
        while (i.hasNext()) {

	        count++;
	        Map.Entry me = (Map.Entry)i.next();
	        AccountData a = (AccountData) me.getValue();
	        thisCostCenter = a.getDim3();
	            

            
     %>    
     
     <%  if (!first && count > 1 && !lastCostCenter.equals(thisCostCenter)) {
            count = 0; %>
        <tr class="sub-total">
            <td colspan="2"><%=lastCostCenter %> Total:</td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(subTotal) %></td>
        </tr>
     <%  subTotal = BigDecimal.ZERO;
            
         } %>
     
        <%  if (!lastCostCenter.equals(thisCostCenter)) {
            count = 0;
            subTotal = BigDecimal.ZERO; %>
        <tr>
            <%  if (first) { %>
            <th colspan="2" class="left">
                <%=a.getDim3() + " " + a.getDim3Description() %>
            </th>
            <th>
                Fav&nbsp;(Unfav)
            </th>
            <%  } else { %>
            <th colspan="3" class="left">
                <%=a.getDim3() + " " + a.getDim3Description() %>
            </th>
            <%  } %>
        </tr>
        <%  } %>
        
        <tr>
            <td><%=a.getDim1() %></td>
            <td><%=a.getDim1Description() %></td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(a.getAmount1()) %></td>
        </tr>
        
        
        <%  subTotal = subTotal.add(a.getAmount1());
            grandTotal = grandTotal.add(a.getAmount1());
            first = false;
            lastCostCenter = thisCostCenter;
            lastCostCenterDescr = a.getDim3Description();
             %>
        <% } %>
        
        <%  if (count > 1) { %>     
        <tr class="sub-total">
            <td colspan="2"><%=thisCostCenter %> Total:</td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(subTotal) %></td>
        </tr>
        <%  } %>
        
        <tr class="grand-total">
            <td colspan="2">Misc Journal Entries Total:</td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(grandTotal) %></td>
        </tr>
		</table>
		
		
<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
    if (plantTotal == null) {
        plantTotal = BigDecimal.ZERO;
    }
    plantTotal = plantTotal.add(grandTotal);
    request.setAttribute("plantTotal", plantTotal);
 %>
		
		
	<% }   // end if empty %>
		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("journalEntries");
            request.setAttribute("keys",keys); %>

         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","journalEntries"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>


	</div>
</div>

