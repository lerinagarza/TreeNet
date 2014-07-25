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
		<h3 style="padding-left:.25em;">Blending</h3>
	</div>

	<div class="ui-widget-content ui-corner-bottom">
	
    <% LinkedHashMap orders = io.getBean().getBlendingMOs();
     
        if (orders.isEmpty()) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
	
		<table class="styled full-width">
		  <colgroup>
		      <col>
		      <!--
		      <col>
		      <col>
		      -->
		      <col span="3">
		  </colgroup>
			<tr>
                <th></th>
                <!-- 
                <th></th>
                <th></th>
                -->
                <th colspan="3">Material Variance</th>
            </tr>
			<tr>
			    <th></th>
			    <!-- 
			    <th>Production</th>
			    <th>Recovery</th>
			     -->
			    <th>Usage</th>
			    <th>Substitution</th>
			    <th>Total</th>
			</tr>
			
        
<%
			        	Iterator i = orders.entrySet().iterator();
			            
			            //Summary fields        

			            BigDecimal usageCostVarianceTotal = BigDecimal.ZERO;
			            BigDecimal substitutionCostVarianceTotal = BigDecimal.ZERO;
			            BigDecimal totalCostVarianceTotal = BigDecimal.ZERO;


			            while (i.hasNext()) {
			                Map.Entry me = (Map.Entry)i.next();
			                String key = (String) me.getKey();
			                ManufacturingOrderDetail m = (ManufacturingOrderDetail) me.getValue();
			                
			                
			                //data fields
			                BigDecimal production = m.getProduction();
			                BigDecimal recovery = m.getRecoveryActual();
			                
			                BigDecimal usageCostVariance = m.getCostVarianceUsage();
			                BigDecimal substitutionCostVariance = m.getCostVarianceSubstitution();
			                BigDecimal totalCostVariance = m.getCostVarianceTotal();

			                       
			                
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
			                usageCostVarianceTotal = usageCostVarianceTotal.add(usageCostVariance);
			                substitutionCostVarianceTotal = substitutionCostVarianceTotal.add(substitutionCostVariance);
			                totalCostVarianceTotal = totalCostVarianceTotal.add(totalCostVariance);
			        %>

			<tr>
				<td><%=key %></td>
				<!-- 
				<td class="right"><%=HTMLHelpersMasking.maskBigDecimal(production) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskPercent(recovery) %></td>
				 -->
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(usageCostVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(substitutionCostVariance) %></td>
				<td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(totalCostVariance) %></td>

			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>
			<tr class="sub-total">
				<td>Total</td>
				
				<!-- 
				<td colspan="2"></td>
                 -->
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(usageCostVarianceTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(substitutionCostVarianceTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(totalCostVarianceTotal) %></td>

			</tr>

 
<%  BigDecimal laborVarWithBenefitsTotal = BigDecimal.ZERO;
    i = orders.entrySet().iterator();
    while (i.hasNext()) {
       Map.Entry me = (Map.Entry)i.next();
       String key = (String) me.getKey();
       
       if (key.contains("Labor")) {
           ManufacturingOrderDetail mo = (ManufacturingOrderDetail) me.getValue();
    
           BigDecimal acutal = mo.getLaborActual();
           BigDecimal earned = mo.getLaborEarned();
           BigDecimal variance = mo.getLaborVariance();
           BigDecimal varianceWithBenifits = mo.getLaborVarianceWithBenefits();
    
           boolean displayRow = false;
           if (acutal.compareTo(BigDecimal.ZERO) != 0
                || earned.compareTo(BigDecimal.ZERO) != 0
                || variance.compareTo(BigDecimal.ZERO) != 0
                || varianceWithBenifits.compareTo(BigDecimal.ZERO) != 0
                ) {
                displayRow = true;
           }
           
           if (displayRow) {
           
       
           BigDecimal laborVarWithBenefits = BigDecimal.ZERO;
           if (mo != null) {
               laborVarWithBenefits = mo.getLaborVarianceWithBenefits();
               laborVarWithBenefitsTotal = laborVarWithBenefitsTotal.add(laborVarWithBenefits);
           }
%>
           <tr>
                <td><%=key %></td>
                <td colspan="2" class="clearfix">

                    <div style="float:left">
                        <span style="margin-right:1em"><b>Earnings</b>&nbsp;<%=HTMLHelpersMasking.maskCurrency(mo.getLaborEarned()) %></span>
                        <span style="margin-right:1em"><b>Actual</b>&nbsp;<%=HTMLHelpersMasking.maskCurrency(mo.getLaborActual()) %></span>
                        <span style="margin-right:1em"><b>Variance</b>&nbsp;<%=HTMLHelpersMasking.maskCurrency(mo.getLaborVariance()) %></span>
                    </div>
                     
                    <div style="float:right">
                        <span><b>Var w/ Ben:</b></span>
                    </div>
                    
                    
                </td>
                <td class="sub-total" style="vertical-align:bottom"><%=HTMLHelpersMasking.maskAccountingFormat(mo.getLaborVarianceWithBenefits()) %></td>
            </tr>
<%
            } // end if(displayRow)
       }   // end if key contains "Labor"
    }  // end loop
            
%>
		          
            <tr>
                <td class="right bold" colspan="3">Blending Total:</td>
                <td class="grand-total"><%=HTMLHelpersMasking.maskAccountingFormat(totalCostVarianceTotal.add(laborVarWithBenefitsTotal)) %></td>
            </tr>
		</table>
		
	<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
	   if (plantTotal == null) {
	       plantTotal = BigDecimal.ZERO;
	   }
	   plantTotal = plantTotal.add(totalCostVarianceTotal.add(laborVarWithBenefitsTotal));
	   request.setAttribute("plantTotal", plantTotal);
	    %>
		
	<% } //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("juiceBlending");
            request.setAttribute("keys",keys); %>

         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","juiceBlending"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

	</div>
</div>
