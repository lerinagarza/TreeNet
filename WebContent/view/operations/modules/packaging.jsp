<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import="
java.util.LinkedHashMap, 
java.util.Iterator, 
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
        <h3>Packaging</h3>
    </div>

    <div class="ui-widget-content ui-corner-bottom">
    
    <% LinkedHashMap orders = io.getBean().getPackagingMOs(); 
    
        Iterator it = orders.keySet().iterator();
  
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
                <col>
                <col>
                <col>
                <col>
                <col span="4">
            </colgroup>
            <tr>
                <th></th>
                <th colspan="3">
                <%  if (io.getWarehouse().equals("209")) { %>
                    Cases
                <%  } else if (io.getWarehouse().equals("280")) { %>
                    Pounds
                <%  } else { %>
                    Units
                <%  } %>
                </th>
                
                <th rowspan="2" style="vertical-align:bottom;">
                    <div class=detailed-tip>Eff %</div>
                    <div class="opsTips">
                       <h4>Efficiency %:</h4>
                       <p>Calculation: (Actual cases / Planned Cases)</p>
                    </div>
                </th>
                
                <th rowspan="2" style="vertical-align:bottom;">
                    <div class=detailed-tip>Labor Hours</div>
                    <div class="opsTips">
                       <h4>Labor Hours:</h4>
                       <p>Actual labor hours reported in Timekeeper</p>
                    </div>
                </th>
                
                <th rowspan="2" style="vertical-align:bottom;">
                    <div class=detailed-tip>CS/MnHr<br>% of Std</div>
                    <div class="opsTips">
                       <h4>CS/MnHr % fo Standard:</h4>
                       <p>Calculation: (Actual cases per man hour / Standard cases per man hour)</p>
                    </div>
                </th>
                
                
                <th colspan="4">Labor $</td>
                
                <th title="Material Variance" rowspan="2" style="vertical-align:bottom;">
                    Material<br>Variance
                </th>
                
                <th title="Total Cost Variance" rowspan="2" style="vertical-align:bottom;">
                    Total<br>Variance
                </th>

            </tr>
            <tr>
                <th>
                    <div class=detailed-tip>Line</div>
                    <div class="opsTips">
                       <h4>Production Line:</h4>
                       <p>Work center code on the operation reported against the MO</p>
                    </div>
                </th>
                <th>
                    <div class=detailed-tip><%=io.getForecastLabel() %></div>
                    <div class="opsTips">
                       <h4><%=io.getForecastLabel() %> Cases:</h4>
                       <p>Monthly forecasted production distributed to weeks (4-4-5)</p>
                    </div>
                </th>
                <th>
                    <div class=detailed-tip>Plan</div>
                    <div class="opsTips">
                       <h4>Planned Cases:</h4>
                       <p>Weekly planned MOs created from FGS</p>
                    </div>
                </th>
                <th>
                    <div class=detailed-tip>Actual</div>
                    <div class="opsTips">
                       <h4>Actual Cases:</h4>
                       <p>Manufacture quantity reported against the MO</p>
                    </div>
                </th>
                
                

                <th>Earnings</th>
                <th>Actual</th>
                <th>
                    <div class=detailed-tip>Variance</div>
                    <div class="opsTips">
                       <h4>Variance:</h4>
                       <p>Calculation: (Actual $ &minus; Earned $)</p>
                       <p>( &plus; Fav)  ( &minus; Unfav)</p>
                    </div>
                </th>

                <th title="Labor variance with benefits (<%=io.getBenefitRate() %>)">Var w/ Ben.</th>
                
            </tr>

    <%
    	//total fields
            BigDecimal forecastTotal = BigDecimal.ZERO;
            BigDecimal planTotal = BigDecimal.ZERO;
            BigDecimal actualTotal = BigDecimal.ZERO;
            BigDecimal percentTotal = BigDecimal.ZERO;
            
            BigDecimal earnedLaborTotal = BigDecimal.ZERO;
            BigDecimal actualLaborTotal = BigDecimal.ZERO;
            BigDecimal varianceLaborTotal = BigDecimal.ZERO;
            BigDecimal varianceLaborWithBenefitsTotal = BigDecimal.ZERO;
            
            BigDecimal actualLaborHoursTotal = BigDecimal.ZERO;
            
            BigDecimal costVarianceTotal = BigDecimal.ZERO;
            
            Iterator i = orders.entrySet().iterator();
            
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
    	        String key = (String) me.getKey();
    	        ManufacturingOrderDetail order = (ManufacturingOrderDetail) me.getValue();
    	        
    	        String[] keyParts = key.split(":");
    	        if (keyParts.length > 1) {
    	           key = keyParts[1];
    	        } else {
    	           key = keyParts[0];
    	        }
    	        
    	        //check values to hide empty rows
                boolean displayRow = false;
                if (order.getProductionForecast().compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (order.getProductionPlanned().compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (order.getProduction().compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (order.getLaborHoursActual().compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (order.getLaborEarned().compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
                if (order.getCostVarianceTotal().compareTo(BigDecimal.ZERO) != 0) {
                    displayRow = true;
                }
    	        
    	        
    	        if (displayRow) {
    	        
    	            // TODO for now, only show forecast/plan/actual cases
    		        
    		        //fields to display in the table
    		        BigDecimal forecast    = order.getProductionForecast();
    	            BigDecimal plan        = order.getProductionPlanned();
    		        BigDecimal actual      = order.getProduction();
    		        BigDecimal percent     = order.getProductionPercentagePlanned();

    	
    		        BigDecimal laborHours  = order.getLaborHoursActual();
    		        BigDecimal laborHoursStd  = order.getLaborHoursStandard();
    		        
    		        BigDecimal csPerMnHrAct   = BigDecimal.ZERO;
                    if (laborHours.compareTo(BigDecimal.ZERO) != 0) {
                        csPerMnHrAct = actual.divide(laborHours,2,BigDecimal.ROUND_HALF_UP);
                    }
                    BigDecimal csPerMnHrStd   = BigDecimal.ZERO;
                    if (laborHoursStd.compareTo(BigDecimal.ZERO) != 0) {
                        csPerMnHrStd = actual.divide(laborHoursStd,2,BigDecimal.ROUND_HALF_UP);
                    }
                    
                    BigDecimal csPerMnHrPctToStd = BigDecimal.ZERO;
                    if (csPerMnHrStd.compareTo(BigDecimal.ZERO) != 0) {
                       csPerMnHrPctToStd = csPerMnHrAct.divide(csPerMnHrStd,2,BigDecimal.ROUND_HALF_UP);
                    }

    	            BigDecimal laborEarned = order.getLaborEarned();
    	            BigDecimal laborActual = order.getLaborActual();
    	            BigDecimal laborVar    = order.getLaborVariance();
    	            BigDecimal laborVarWithBenefits    = order.getLaborVarianceWithBenefits();
    	            
    	            BigDecimal costVariance    = order.getCostVarianceTotal();
    	           
    	                
    	            //calculate summary fields           
    	            forecastTotal = forecastTotal.add(forecast);
    	            planTotal = planTotal.add(plan);
    	            actualTotal = actualTotal.add(actual);
    	            
    	            actualLaborHoursTotal = actualLaborHoursTotal.add(laborHours);
    	            
    	            earnedLaborTotal = earnedLaborTotal.add(laborEarned);
    	            actualLaborTotal = actualLaborTotal.add(laborActual);
    	            varianceLaborTotal = varianceLaborTotal.add(laborVar);
    	            varianceLaborWithBenefitsTotal = varianceLaborWithBenefitsTotal.add(laborVarWithBenefits);
    	            
    	            costVarianceTotal = costVarianceTotal.add(costVariance);
    %>

            <tr>
                <td>
                <%  if (order.getOrderNumbers().isEmpty()) { %>
                    <%=key %>
                <%  } else { %>
	                    <div class=detailed-tip><%=key %></div>
	                    <div class="opsTips left">
	                       <h4>Order Numbers:</h4>
	                       <p><%=InqOperations.buildOrderNumberList(order) %>
	                       </p>
	                    </div>
                <%  } %>
                </td>
                
                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(forecast) %>
                </td>
                
                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(plan) %>
                </td>
                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(actual) %>
                </td>
                <td class=center>
                <%  if (plan.compareTo(BigDecimal.ZERO) != 0) {
                    percent = actual
                        .divide(plan, 4, BigDecimal.ROUND_HALF_UP);
                    } %>
                <%=HTMLHelpersMasking.maskPercent(percent) %>
                </td>


                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(laborHours) %>
                </td>

                <td class=right>
                    <%=HTMLHelpersMasking.maskPercent(csPerMnHrPctToStd) %>
                </td>

                <td class=right>
                    <%=HTMLHelpersMasking.maskAccountingFormat(laborEarned) %>
                </td>
                <td class=right>
                    <%=HTMLHelpersMasking.maskAccountingFormat(laborActual) %>
                </td>
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(laborVar) %>
                </td>

       
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(laborVarWithBenefits) %>
                </td>
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(costVariance) %>
                </td>
                
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(laborVarWithBenefits.add(costVariance)) %>
                </td>
                
                
            </tr>
           <%   }  //end if(displayRow) %>
    <%    } //end loop %>

            <tr class="sub-total">
                <td>Total</td>
                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(forecastTotal) %>
                </td>
                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(planTotal) %>
                </td>
                <td class=right>

                    <%=HTMLHelpersMasking.maskBigDecimal(actualTotal) %>

                </td>
                <td class=center>
                <% if (planTotal.compareTo(BigDecimal.ZERO) != 0) {
                    percentTotal = actualTotal
			            .divide(planTotal, 4, BigDecimal.ROUND_HALF_UP);
		            } %>
		            <%=HTMLHelpersMasking.maskPercent(percentTotal) %>

                </td>

                <td class=right>
                    <%=HTMLHelpersMasking.maskBigDecimal(actualLaborHoursTotal) %>
                </td>
                
                <td class=right>
                </td>

                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(earnedLaborTotal) %>
                </td>
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(actualLaborTotal) %>
                </td>
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(varianceLaborTotal) %>
                </td>

                 
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(varianceLaborWithBenefitsTotal) %>
                </td>
                
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(costVarianceTotal) %>
                </td>
                
                <td class=right>
                   <%=HTMLHelpersMasking.maskAccountingFormat(varianceLaborWithBenefitsTotal.add(costVarianceTotal)) %>
                </td>
            </tr>
        </table>

    <% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
       if (plantTotal == null) {
           plantTotal = BigDecimal.ZERO;
       }
       plantTotal = plantTotal.add(varianceLaborWithBenefitsTotal.add(costVarianceTotal));
       request.setAttribute("plantTotal", plantTotal);
        %>

    <%  }  //end if empty %>        
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("packaging");
            request.setAttribute("keys",keys); %>


         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","packaging"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

    </div>
</div>