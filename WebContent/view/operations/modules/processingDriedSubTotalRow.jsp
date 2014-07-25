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
<%InqOperations io = (InqOperations) request.getAttribute("inqOperations");
  if (io == null) {
      io = new InqOperations();
  } 
          String key = (String) request.getAttribute("processingRowSubtotal");
          ManufacturingOrderDetail order = (ManufacturingOrderDetail) io.getBean().getProcessingMOsSummary().get(key);
          
          String[] keyParts = key.split(":");
          if (keyParts.length == 1) {
              keyParts = new String[] {keyParts[0], ""};
          }
          
          //data fields
          BigDecimal hfnc         = BigDecimal.ZERO;
          
          BigDecimal usageRawFruitActual      = order.getUsageActualYield();

          
          BigDecimal prodFcst     = order.getProductionForecast();
          BigDecimal prodPlan     = order.getProductionPlanned();
          BigDecimal prodAct      = BigDecimal.ZERO;
          
          BigDecimal costVarianceUsage        = order.getCostVarianceUsage();
          BigDecimal costVarianceSubstitution = order.getCostVarianceSubstitution();
          BigDecimal costVarianceTotal        = order.getCostVarianceTotal();
          
          BigDecimal slurryVariance           = order.getCostVarianceSlurry();
          BigDecimal supplyVariance           = order.getCostVarianceSupplies();

          BigDecimal varianceTotal            = costVarianceTotal.add(supplyVariance).add(slurryVariance);
          
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

        %>

			<tr class="sub-total">

                <%  String keySpan = "";
                    if (keyParts[1].equals("")) {
                        keySpan = "colspan='2'"; 
                    } %>
                <td <%=keySpan %>>
                    <%=keyParts[0] %>
                </td>
                
                <%  if (!keyParts[1].equals("")) { %>
                <td style="line-height:0.9em; padding-top:.45em">
                    <%=InqOperations.formatRowLabel(keyParts[1]) %>
                </td>
                <%  } %>
				
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(usageRawFruitActual) %></td>
				
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
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(slurryVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(supplyVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(varianceTotal) %></td>
			</tr>
    <%  } //end if(displayRow) %>			
		

