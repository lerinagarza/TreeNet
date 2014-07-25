<%@page import=""%>
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
		<h3 style="padding-left:.25em;">Packaging</h3>
	</div>

	<div class="ui-widget-content ui-corner-bottom">
	
    <% LinkedHashMap orders = io.getBean().getPackagingMOs();
     
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
		      <col span="3">
		      <col>
              <col>
              <col>
              <col>
		  </colgroup>
			<tr>
                <th></th>
                <th></th>
                <th colspan="3">Line Tank Material Variance</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
			<tr>
			    <th></th>
			    <th>Production</th>
			    <th>Usage</th>
			    <th>Substitution</th>
			    <th>Total</th>
			    <th>Rework</th>
			    <th>Supplies</th>
			    <th>Total</th>
			</tr>

<%
			        	Iterator i = orders.entrySet().iterator();
			            
			            //Summary fields        

                        BigDecimal productionTotal = BigDecimal.ZERO;
                        
			            BigDecimal usageCostVarianceTotal = BigDecimal.ZERO;
			            BigDecimal substitutionCostVarianceTotal = BigDecimal.ZERO;
			            BigDecimal totalCostVarianceTotal = BigDecimal.ZERO;
			            BigDecimal reworkCostVarianceTotal = BigDecimal.ZERO;
			            BigDecimal suppliesCostVarianceTotal = BigDecimal.ZERO;
			            
			            BigDecimal grandTotalCostVarianceTotal = BigDecimal.ZERO;


			            while (i.hasNext()) {
			                Map.Entry me = (Map.Entry)i.next();
			                String key = (String) me.getKey();
			                ManufacturingOrderDetail m = (ManufacturingOrderDetail) me.getValue();
			                
			                
			                //data fields
			                BigDecimal production = m.getProductionLbAtStd();
			                
			                BigDecimal usageCostVariance = m.getCostVarianceUsage();
			                BigDecimal substitutionCostVariance = m.getCostVarianceSubstitution();
			                BigDecimal totalCostVariance = m.getCostVarianceTotal();
			                
			                BigDecimal reworkCostVariance = m.getCostVarianceRework();
			                BigDecimal suppliesCostVariance = m.getCostVarianceSupplies();
			                
			                BigDecimal grandTotalCostVariance = totalCostVariance
					                .add(reworkCostVariance)
					                .add(suppliesCostVariance);

			                       
			                
			                boolean displayRow = false;
			                if (production.compareTo(BigDecimal.ZERO) != 0) {
			                    displayRow = true;
			                }
			                if (totalCostVariance.compareTo(BigDecimal.ZERO) != 0) {
			                    displayRow = true;
			                }
			                if (key.contains("Labor")) {
			                    displayRow = false;
			                }
			                
			                if (displayRow) {
			                
			                
			                
			                //calculate summary fields
			                productionTotal = productionTotal.add(production);
			                
			                usageCostVarianceTotal = usageCostVarianceTotal.add(usageCostVariance);
                            substitutionCostVarianceTotal = substitutionCostVarianceTotal.add(substitutionCostVariance);
			                totalCostVarianceTotal = totalCostVarianceTotal.add(totalCostVariance);

			                reworkCostVarianceTotal = reworkCostVarianceTotal.add(reworkCostVariance);
			                suppliesCostVarianceTotal = suppliesCostVarianceTotal.add(suppliesCostVariance);
			                
			                grandTotalCostVarianceTotal = grandTotalCostVarianceTotal.add(grandTotalCostVariance);
			                
			                
			                
			        %>

			<tr>
				<td>
				    <div class=detailed-tip><%=key %></div>
                    <div class="opsTips left">
                       <h4>Order Numbers:</h4>
                       <p><%=InqOperations.buildOrderNumberList(m) %>
                       </p>
                    </div>
				</td>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(production) %></td>
				
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(usageCostVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(substitutionCostVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(totalCostVariance) %></td>
				
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(reworkCostVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(suppliesCostVariance) %></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(grandTotalCostVariance) %></td>

			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>
			<tr class="sub-total">
				<td>Total</td>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(productionTotal) %></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(usageCostVarianceTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(substitutionCostVarianceTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(totalCostVarianceTotal) %></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(reworkCostVarianceTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(suppliesCostVarianceTotal) %></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(grandTotalCostVarianceTotal) %></td>

			</tr>
			
            <tr>
                <td class="right bold" colspan="7">Packaging Total:</td>
                <td class="grand-total"><%=HTMLHelpersMasking.maskAccountingFormat(grandTotalCostVarianceTotal) %></td>
            </tr>
		</table>
		
	<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
	   if (plantTotal == null) {
	       plantTotal = BigDecimal.ZERO;
	   }
	   plantTotal = plantTotal.add(grandTotalCostVarianceTotal);
	   request.setAttribute("plantTotal", plantTotal);
	    %>
		
	<% } //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("ingredientPackaging");
            request.setAttribute("keys",keys); %>
            
         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","ingredientPackaging"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>
            
	</div>
</div>
