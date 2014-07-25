<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import = "
java.util.LinkedHashMap, 
java.util.Iterator,
java.util.Vector,
java.math.BigDecimal, 
com.treetop.businessobjects.ManufacturingOrderDetail,
com.treetop.businessobjects.KeyValue, 
com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 
    
    LinkedHashMap lhm = io.getBean().getProcessingLabor(); 

 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3 style="padding-left:.25em;">Labor</h3>
	</div>

	<div class="ui-widget-content ui-corner-bottom">
	
    <% 
        if (lhm.isEmpty()) {
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
			    <col>
			</colgroup>
			
			<tr>
			 <th rowspan="2"></th>
			 <th colspan="2" style="width:16%">Labor Hours</th>
			 
			 <%  String label = "LB/MnHr";
			     if (io.getWarehouse().equals("490")) {
			         label = "CS/MnHr";
			     } %>
			 
			 <th colspan="2" style="width:16%"><%=label %></th>
			 <th rowspan="2" style="width:14%">Earned</th>
             <th rowspan="2" style="width:14%">Actual</th>
             <th rowspan="2" style="width:14%">Variance</th>
             <th rowspan="2" style="width:14%">Variance with Benefits</th>
			</tr>
			
			<tr>
			    <th>Std</th>
			    <th>Act</th>
			    <th>Std</th>
			    <th>Act</th>
			</tr>
        
<%
           	
            //Summary fields    
            BigDecimal hoursActualTotal = BigDecimal.ZERO;
            BigDecimal hoursStandardTotal = BigDecimal.ZERO;
            BigDecimal earnedTotal = BigDecimal.ZERO;
            BigDecimal actualTotal = BigDecimal.ZERO;
            BigDecimal varianceTotal = BigDecimal.ZERO;
            BigDecimal varianceWithBenefitsTotal = BigDecimal.ZERO;
   
            Iterator keys = lhm.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                
                ManufacturingOrderDetail m = (ManufacturingOrderDetail) lhm.get(key);                
                
                //data fields
                BigDecimal hoursActual = m.getLaborHoursActual();
                BigDecimal hoursStandard = m.getLaborHoursStandard();
                BigDecimal earned = m.getLaborEarned();
	            BigDecimal actual = m.getLaborActual();
	            BigDecimal variance = m.getLaborVariance();
	            BigDecimal varianceWithBenefits = m.getLaborVarianceWithBenefits();
               
               
                BigDecimal lbActual = m.getProduction();
                BigDecimal lbPerMnHrStd = m.getUnitsPerManHourStandard();
                BigDecimal lbPerMnHrAct = m.getUnitsPerManHourActual();
                
                
               
                boolean displayRow = false;

                if (actual.compareTo(BigDecimal.ZERO) != 0
	                || earned.compareTo(BigDecimal.ZERO) != 0
	                || variance.compareTo(BigDecimal.ZERO) != 0
	                || varianceWithBenefits.compareTo(BigDecimal.ZERO) != 0
                    ) {
                    displayRow = true;
                }


                if (displayRow) {
                
                
                
                //calculate summary fields
                hoursActualTotal = hoursActualTotal.add(hoursActual);
                hoursStandardTotal = hoursStandardTotal.add(hoursStandard);
                earnedTotal = earnedTotal.add(earned);
                actualTotal = actualTotal.add(actual);
                varianceTotal = varianceTotal.add(variance);
                varianceWithBenefitsTotal = varianceWithBenefitsTotal.add(varianceWithBenefits);
     
        %>

			<tr>
			    <td><%=key %></td>
			    <td class="right">
                    <%=HTMLHelpersMasking.maskBigDecimal(hoursStandard) %>
                </td>
			    <td class="right">
			        <%=HTMLHelpersMasking.maskBigDecimal(hoursActual) %>
			    </td>
			    <td class="right">
                    <%=HTMLHelpersMasking.maskBigDecimal(lbPerMnHrStd,1,false) %>
                </td>
			    <td class="right">
                    <%=HTMLHelpersMasking.maskBigDecimal(lbPerMnHrAct,1,false) %>
                </td>
			    <td>
			        <%=HTMLHelpersMasking.maskAccountingFormat(earned) %>
			    </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(actual) %>
                </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(variance) %>
                </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(varianceWithBenefits) %>
                </td>
			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>

			<tr class="sub-total">
				<td>Total</td>
				<td class="right">
                    <%=HTMLHelpersMasking.maskBigDecimal(hoursStandardTotal) %>
                </td>
				<td class="right">
                    <%=HTMLHelpersMasking.maskBigDecimal(hoursActualTotal) %>
                </td>
                <td></td>
                <td></td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(earnedTotal) %>
                </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(actualTotal) %>
                </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(varianceTotal) %>
                </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(varianceWithBenefitsTotal) %>
                </td>
			</tr>

		</table>
		
	<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
	   if (plantTotal == null) {
	       plantTotal = BigDecimal.ZERO;
	   }
	   plantTotal = plantTotal.add(varianceWithBenefitsTotal);
	   request.setAttribute("plantTotal", plantTotal);
	    %>
		
	<% } //end if empty %>
        <%  KeyValue commentKeys = io.getCommentKeys();
            commentKeys.setKey1("processingLabor");
            request.setAttribute("keys",commentKeys); %>


         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","processingLabor"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

	</div>
</div>
