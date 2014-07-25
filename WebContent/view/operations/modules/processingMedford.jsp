<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking,
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
		<h3 style="padding-left:.25em;">Processing</h3>
	</div>

	<div class="ui-widget-content ui-corner-bottom">
	
    <% LinkedHashMap orders = io.getBean().getProcessingMOs(); 
        if (orders.isEmpty()) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
	
		<table class="styled full-width">
			<colgroup>
			    <col span="2">				
				<col>
				<col>
				<!-- <col> -->
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col span="4">
				<col>
			</colgroup>
			<tr>
			    <th colspan="2" rowspan="2" style="vertical-align:bottom;"></th>
			    
				<th rowspan="2" style="vertical-align:bottom;">
                    <div class="definition-term">Usage</div>
                    <div class="definition-tooltip"
                        data-definition-environment="<%=io.getEnvironment() %>" 
                        data-definition-key1="Usage" 
                        data-definition-key2="<%=io.getWarehouse() %>">
                    </div>
                </th>
                
                <th rowspan="2" style="vertical-align:bottom;">
                    <span class="hidden-phone">Washdown</span>
                    <span class="hidden-tablet hidden-desktop">Wash<br>down</span>
                </th>
                
                
                <!-- 
                <th rowspan="2" style="vertical-align:bottom;">Blend in</th>
                 -->
                 
                 
				<th rowspan="2" style="vertical-align:bottom;">
				    <div class="hidden-desktop">Prod</div>
				    <div class="definition-term hidden-tablet hidden-phone">Production</div>
                    <div class="definition-tooltip"
                        data-definition-environment="<%=io.getEnvironment() %>" 
                        data-definition-key1="Production" 
                        data-definition-key2="<%=io.getWarehouse() %>">
                    </div>

				</th>
				<th colspan="2" style="vertical-align:bottom;">
                    Yield
                </th>
                
				<th colspan="3">Material Variance</th>
				<th rowspan="2" style="vertical-align:bottom;">
				    <span class="hidden-phone">Supplies<br>Variance</span>
                    <span class="hidden-tablet hidden-desktop">Supl<br>Var</span>
				</th>
				<th rowspan="2" style="vertical-align:bottom;">Total<br>Variance</th>
			</tr>
			
			
			<tr>
			
			    <th>Std</th>
			    <th>Act</th>
                <th>
                    <div class=detailed-tip>Usage</div>
                    <div class="opsTips">
                       <h4>Usage Cost Variance:</h4>
                       <p>(Actual&nbsp;Usage&nbsp;&minus;&nbsp;Standard&nbsp;Usage) &times;&nbsp;Standard&nbsp;Cost</p>
                    </div>
                </th>
                <th>
                    <div class=detailed-tip>
                        <span class="hidden-phone">Substitution</span>
                        <span class="hidden-tablet hidden-desktop">Subs</span>
                    </div>
                    <div class="opsTips">
                       <h4>Substitution Cost Variance:</h4>
                       <p>(Actual&nbsp;Price&nbsp;&minus;&nbsp;Standard&nbsp;Price) &times;&nbsp;Actual&nbsp;Usage</p>
                    </div>
                </th>
                <th>Total</th>
			</tr>
        
<%
        	Iterator i = orders.entrySet().iterator();
                                                        	
                                                            
            //Summary fields    
            
            BigDecimal tonsActTotal                 = BigDecimal.ZERO;

            BigDecimal prodActTotal                 = BigDecimal.ZERO;
            BigDecimal washdownTotal                = BigDecimal.ZERO;
            BigDecimal blendInTotal                 = BigDecimal.ZERO;
            
            BigDecimal supplyVarTotal               = BigDecimal.ZERO;
            BigDecimal manufMixVarTotal             = BigDecimal.ZERO;
            BigDecimal varTotal                     = BigDecimal.ZERO;
            
            BigDecimal costVarianceUsageTotal        = BigDecimal.ZERO;
            BigDecimal costVarianceSubstitutionTotal = BigDecimal.ZERO;
            BigDecimal costVarianceTotalTotal        = BigDecimal.ZERO;
            
            BigDecimal supplyVarianceTotal           = BigDecimal.ZERO;
            
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
                String key = (String) me.getKey();
                
                // if this is the labor row, don't display it with the processing lines
                // it will be added manually at the end
                if (key.contains("Labor") || key.contains("Slurry and Elims")) {
                    continue;
                }
                
                ManufacturingOrderDetail order = (ManufacturingOrderDetail) me.getValue();
                
                String[] keyParts = key.split(":");
                if (keyParts.length == 1) {
                    keyParts = new String[] {keyParts[0], ""};
                }
                
                //data fields
                BigDecimal hfnc         = BigDecimal.ZERO;
                
                BigDecimal usageRawFruitActual      = order.getUsageActualYield();
                BigDecimal washdown                 = order.getUsageActualWashdown();
                BigDecimal blendIn                  = order.getUsageActualBlendIn();

                
                BigDecimal prodFcst     = order.getProductionForecast();
                BigDecimal prodPlan     = order.getProductionPlanned();
                BigDecimal prodAct      = BigDecimal.ZERO;
                
                BigDecimal costVarianceUsage        = order.getCostVarianceUsage();
                BigDecimal costVarianceSubstitution = order.getCostVarianceSubstitution();
                BigDecimal costVarianceTotal        = order.getCostVarianceTotal();
                
                BigDecimal supplyVariance           = order.getCostVarianceSupplies();

                BigDecimal varianceTotal            = costVarianceTotal.add(supplyVariance);
                
                BigDecimal prodYield                = BigDecimal.ZERO;
                
                String yieldActFormatted    = "";
                String yieldStdFormatted    = "";

                if (order.getYieldType() == ManufacturingOrderDetail.YieldType.YIELD) {
                    prodAct = order.getProductionFsAtStd();
                    yieldActFormatted = HTMLHelpersMasking.maskBigDecimal(order.getYieldActual());
                    yieldStdFormatted = HTMLHelpersMasking.maskBigDecimal(order.getYieldStandard());
                } else if (order.getYieldType() == ManufacturingOrderDetail.YieldType.RECOVERY) {
                    if (io.getWarehouse().equals("469")) {
                        prodAct = order.getProductionFsAtStd();
                    } else {
                        prodAct = order.getProductionLbAtStd();
                    }
                    yieldActFormatted = HTMLHelpersMasking.maskPercent(order.getRecoveryActual());
                    yieldStdFormatted = HTMLHelpersMasking.maskPercent(order.getRecoveryStandard());
                } else if (order.getYieldType() == ManufacturingOrderDetail.YieldType.LBPERTON) {
                    prodAct = order.getProductionLbAtStd();
                    yieldActFormatted = HTMLHelpersMasking.maskBigDecimal(order.getRecoveryActual());
                    yieldStdFormatted = HTMLHelpersMasking.maskBigDecimal(order.getRecoveryStandard()); 
                }
                        
                
                
                boolean displayRow = false;
                if (hfnc.compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (usageRawFruitActual.compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (prodFcst.compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (prodPlan.compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (prodAct.compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                
                
                
                if (displayRow) {
                
                
                
                //calculate summary fields
               
                tonsActTotal = tonsActTotal.add(usageRawFruitActual);
                washdownTotal = washdownTotal.add(washdown);
                blendInTotal = blendInTotal.add(blendIn);
                
                prodActTotal = prodActTotal.add(prodAct);
                
                costVarianceUsageTotal = costVarianceUsageTotal.add(costVarianceUsage);
                costVarianceSubstitutionTotal = costVarianceSubstitutionTotal.add(costVarianceSubstitution); 
                costVarianceTotalTotal = costVarianceTotalTotal.add(costVarianceTotal);
                
                supplyVarianceTotal = supplyVarianceTotal.add(supplyVariance);
                
                varTotal = varTotal.add(costVarianceTotal).add(supplyVariance);
        %>

			<tr>

                <%  String keySpan = "";
                    if (keyParts[1].equals("")) {
                        keySpan = "colspan='2'"; 
                    } %>
                <td <%=keySpan %>>
                    <div class=detailed-tip><%=keyParts[0] %></div>
                    <div class="opsTips left">
                       <h4>Order Numbers:</h4>
                       <p><%=InqOperations.buildOrderNumberList(order) %>
                       </p>
                    </div>
                </td>
                
                <%  if (!keyParts[1].equals("")) { %>
                <td style="line-height:0.9em; padding-top:.45em">
                    <%=InqOperations.formatRowLabel(keyParts[1]) %>
                </td>
                <%  } %>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(usageRawFruitActual) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(washdown) %></td>
				
				<!-- 
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(blendIn) %></td>
				 -->
				 
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(prodAct) %></td>

                <%  String yieldDefKey = key;
                    //only selah using key based yield defs right now...
                    if (!io.getWarehouse().equals("209")) {
                        key = "";
                    } %>

				<%-- Standard Yield --%>
                <td class="right">
                    <span class="definition-term"><%=yieldStdFormatted %></span>
                    <div class="definition-tooltip"
                        data-definition-environment="<%=io.getEnvironment() %>" 
                        data-definition-key1="Yield" 
                        data-definition-key2="<%=io.getWarehouse() %>" 
                        data-definition-key3="<%=key %>">
                    </div>
                </td>
				
				<%-- Actual Yield --%>
				<td class="right">
				    <span class="definition-term"><%=yieldActFormatted %></span>
				    <div class="definition-tooltip"
				        data-definition-environment="<%=io.getEnvironment() %>" 
                        data-definition-key1="Yield" 
                        data-definition-key2="<%=io.getWarehouse() %>" 
                        data-definition-key3="<%=key %>">
                    </div>
			    </td>
			    
			    
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(costVarianceUsage) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(costVarianceSubstitution) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(costVarianceTotal) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(supplyVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(varianceTotal) %></td>
			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>
			<tr class="sub-total">
				<td colspan="2">Total</td>
				
				<td class="right">
				<%  if (!io.getWarehouse().equals("469")) {   //Prosser has mixed usage units %>
				<%=HTMLHelpersMasking.maskBigDecimal(tonsActTotal) %>
				<%  } %>
				</td>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(washdownTotal) %></td>
				
				<!-- 
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(blendInTotal) %></td>
				 -->
				 
				 
				<%  if (io.getWarehouse().equals("209")) { 
				    //Selah has mixed production UOM, do not display a total production value
				%>
                <td class="right"><!-- Production --></td>
                <%  } else { %>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(prodActTotal) %></td>
                <%  } %>
                
                
                <td class="right"><!-- Yield Standard --></td>
                <td class="right"><!-- Yield Actual --></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(costVarianceUsageTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(costVarianceSubstitutionTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(costVarianceTotalTotal) %></td>
                
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(supplyVarianceTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(varTotal) %></td>
			</tr>

			<tr>
			  <td colspan="11" class="bold right">Processing Total:</td>
			  <td class="grand-total"><%=HTMLHelpersMasking.maskAccountingFormat(varTotal) %></td>
			</tr>
 
		</table>
		
	<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
	   if (plantTotal == null) {
	       plantTotal = BigDecimal.ZERO;
	   }
	   plantTotal = plantTotal.add(varTotal);
	   request.setAttribute("plantTotal", plantTotal);
	    %>
		
	<% } //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("processing");
            request.setAttribute("keys",keys); %>

         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","processing"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

	</div>
</div>

