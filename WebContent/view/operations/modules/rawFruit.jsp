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
		<h3 style="padding-left:.25em;">Raw Fruit</h3>
	</div>

	<div class="ui-widget-content ui-corner-bottom">
	
    <% LinkedHashMap lines = io.getBean().getRawFruitMOs(); 
        if (lines.isEmpty()) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
	
		<table class="styled full-width">
			
			<colgroup>
			 <col>
			 <col span="3">
			 <col>
			 <col span="3">
			</colgroup>
			
			
			<tr>
			    <th>
                    Fruit
                </th>
			    <th>
                    <span class="hidden-phone"><%=io.getForecastLabel() %></span>
                    <span class="hidden-tablet hidden-desktop"><%=io.getForecastLabel() %></span>
                </th>
                <th>
                    Plan
                </th>
                <th>
                    <span class="hidden-phone">Standard</span>
                    <span class="hidden-tablet hidden-desktop">Std</span>
                </th>
                <th>
                    <span class="hidden-phone">Actual</span>
                    <span class="hidden-tablet hidden-desktop">Act</span>
                </th>
                <th>
                    % to Fcst
                </th>
                <th>
                    % to Plan
                </th>
                <th>
                    % to Std
                </th>                
			</tr>
        
<%	Iterator i = lines.entrySet().iterator();
    
    //Summary fields    
    BigDecimal fcstTotal = BigDecimal.ZERO;
    BigDecimal planTotal = BigDecimal.ZERO;
    BigDecimal stdTotal = BigDecimal.ZERO;
    BigDecimal actTotal = BigDecimal.ZERO;
    
        
    while (i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        String key = (String) me.getKey();
        ManufacturingOrderDetail line = (ManufacturingOrderDetail) me.getValue();

        String[] keyParts = key.split(":");
        String displayKey = "";
        if (keyParts.length > 1) {
            displayKey = keyParts[1];
        } else {
            displayKey = keyParts[0];
        }
        
        BigDecimal fcst     = line.getUsageRawFruitForecast();
        BigDecimal plan     = line.getUsageRawFruitPlanned();
        BigDecimal std      = line.getUsageRawFruitStandard();
        BigDecimal act      = line.getUsageRawFruitActual();
        
        boolean displayRow = false;
        if (fcst.compareTo(BigDecimal.ZERO) != 0) {
            displayRow = true;
        }
        if (plan.compareTo(BigDecimal.ZERO) != 0) {
            displayRow = true;
        }
        if (std.compareTo(BigDecimal.ZERO) != 0) {
            displayRow = true;
        }
        if (act.compareTo(BigDecimal.ZERO) != 0) {
            displayRow = true;
        }
        
        if (displayRow) {
        
        
        //calculate summary fields
        fcstTotal = fcstTotal.add(fcst);
        planTotal = planTotal.add(plan);
        stdTotal  = stdTotal.add(std);
        actTotal  = actTotal.add(act);
        
        //calculate % to fields
        BigDecimal pctToFcst = BigDecimal.ZERO;
        if (fcst.compareTo(BigDecimal.ZERO) != 0) {
            pctToFcst = act.divide(fcst,4,BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal pctToPlan = BigDecimal.ZERO;
        if (plan.compareTo(BigDecimal.ZERO) != 0) {
            pctToPlan = act.divide(plan,4,BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal pctToStd = BigDecimal.ZERO;
        if (std.compareTo(BigDecimal.ZERO) != 0) {
            pctToStd = act.divide(std,4,BigDecimal.ROUND_HALF_UP);
        }
   
        
    %>
			<tr>
				<td><%=displayKey %></td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(fcst) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(plan) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(std) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(act) %></td>
				
                <td class="right"><%=HTMLHelpersMasking.maskPercent(pctToFcst) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskPercent(pctToPlan) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskPercent(pctToStd) %></td>
				
			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>


<%      //Calculate total percentages
        BigDecimal pctToFcstTotal = BigDecimal.ZERO;
        if (fcstTotal.compareTo(BigDecimal.ZERO) != 0) {
            pctToFcstTotal = actTotal.divide(fcstTotal,4,BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal pctToPlanTotal = BigDecimal.ZERO;
        if (planTotal.compareTo(BigDecimal.ZERO) != 0) {
            pctToPlanTotal = actTotal.divide(planTotal,4,BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal pctToStdTotal = BigDecimal.ZERO;
        if (stdTotal.compareTo(BigDecimal.ZERO) != 0) {
            pctToStdTotal = actTotal.divide(stdTotal,4,BigDecimal.ROUND_HALF_UP);
        }
 %>

			<tr class="sub-total">
				<td>Total</td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(fcstTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(planTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(stdTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(actTotal) %></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskPercent(pctToFcstTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskPercent(pctToPlanTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskPercent(pctToStdTotal) %></td>
                
			</tr>
		
		</table>

		
	<% } //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("rawFruit");
            request.setAttribute("keys",keys); %>
            
         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","rawFruit"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>
        
        
        
	</div>
</div>