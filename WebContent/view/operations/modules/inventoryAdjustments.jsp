<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "
com.treetop.businessobjects.KeyValue,
com.treetop.businessobjects.AccountData,
com.treetop.businessobjects.ManufacturingFinance,  
com.treetop.controller.operations.InqOperations,
java.math.BigDecimal,
java.util.LinkedHashMap,
java.util.Iterator,
java.util.Map" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    LinkedHashMap lhm = io.getBean().getInventoryAdjustments();
    boolean isEmpty = lhm.isEmpty();
    
    boolean weekly = true;
    if (io.getRequestType().equals("monthly")) {
        weekly = false;
    }
    
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Inventory Adjustments</h3>
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
            <td><%=HTMLHelpersMasking.maskAccountingFormat(subTotal, 0, true) %></td>
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
            <td><%=a.getDim4() %></td>
            <td><%=a.getDim4Description() %></td>
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
            <td><%=HTMLHelpersMasking.maskAccountingFormat(subTotal, 0, true) %></td>
        </tr>
        <%  } %>
        
        <tr class="grand-total">
            <td colspan="2">Inventory Adjustments Total</td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(grandTotal, 0, true) %></td>
        </tr>
        
<%  // add up all returned values for a total inventory adjustments earning
    BigDecimal earnings = BigDecimal.ZERO;
    Iterator itr = io.getBean().getInventoryAdjustmentsEarnings().values().iterator();
    while (itr.hasNext()) {
    
        ManufacturingFinance mf = (ManufacturingFinance) itr.next();
        if (weekly) {
            earnings = earnings.add(mf.getWtdEarnings());
        } else {
            earnings = earnings.add(mf.getMtdEarnings());
        }
    
    }
    
    //add earnings to match fav (unfav) formatting for over all total
    //  Actual  -1,700  -- unfavorable
    //  Earnings   200  -- earnings (offsets unfavorability)
    //  Variance-1,500  -- variance
    BigDecimal variance = earnings.add(grandTotal);
 %>        
<%  // only show the earnings if it is not zero
    if (earnings.compareTo(BigDecimal.ZERO) != 0) { %>        
        <tr>
            <td colspan="2">Other Supplies Earnings</td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(earnings, 0, true) %></td>
        </tr>
        
        <tr class="grand-total">
            <td colspan="2">Variance</td>
            <td><%=HTMLHelpersMasking.maskAccountingFormat(variance, 0, true) %></td>
        </tr>
<%  } %>        
		</table>
		
		
<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
    if (plantTotal == null) {
        plantTotal = BigDecimal.ZERO;
    }
    plantTotal = plantTotal.add(variance);
    request.setAttribute("plantTotal", plantTotal);
 %>
		
		
	<% }   // end if empty %>
		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("inventoryAdjustments");
            request.setAttribute("keys",keys); %>
            
         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","inventoryAdjustments"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>
            
            
	</div>
</div>

