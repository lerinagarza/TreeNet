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
    
    LinkedHashMap lhm = io.getBean().getProcessingMOs(); 

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
			</colgroup>
			<tr>
			    <th></th>
                <th style="width:18%">Earned</th>
                <th style="width:18%">Actual</th>
                <th style="width:18%">Variance</th>
                <th style="width:18%" title="Benefits Rate: <%=io.getBenefitRate() %>">
                    <span class="hidden-phone">Variance with Benefits</span>
                    <span class="hidden-tablet hidden-desktop">Var + Ben</span>
                </th>
			</tr>
        
<%
           	
            //Summary fields    
            BigDecimal earnedTotal = BigDecimal.ZERO;
            BigDecimal actualTotal = BigDecimal.ZERO;
            BigDecimal varianceTotal = BigDecimal.ZERO;
            BigDecimal varianceWithBenefitsTotal = BigDecimal.ZERO;
   
            boolean showLineVariances = !lhm.keySet().contains("Labor - Earnings");
   
            Iterator keys = lhm.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                ManufacturingOrderDetail m = (ManufacturingOrderDetail) lhm.get(key);
                
                
                //data fields
                BigDecimal earned = m.getLaborEarned();
	            BigDecimal actual = m.getLaborActual();
	            BigDecimal variance = m.getLaborVariance();
	            BigDecimal varianceWithBenefits = m.getLaborVarianceWithBenefits();
               
                boolean displayRow = false;
                if (key.contains("Labor")) {
            
	                if (actual.compareTo(BigDecimal.ZERO) != 0
		                || earned.compareTo(BigDecimal.ZERO) != 0
		                || variance.compareTo(BigDecimal.ZERO) != 0
		                || varianceWithBenefits.compareTo(BigDecimal.ZERO) != 0
	                    ) {
	                    displayRow = true;
	                }
	                
                }

                if (displayRow) {
                key = key.substring(8,key.length());
                
                
                //calculate summary fields
                earnedTotal = earnedTotal.add(earned);
                actualTotal = actualTotal.add(actual);
                varianceTotal = varianceTotal.add(variance);
                varianceWithBenefitsTotal = varianceWithBenefitsTotal.add(varianceWithBenefits);
     
        %>

			<tr>
			    <td><%=key %></td>
			    <td>
			        <%=HTMLHelpersMasking.maskAccountingFormat(earned) %>
			    </td>
                <td>
                    <%=HTMLHelpersMasking.maskAccountingFormat(actual) %>
                </td>
                <td>
                    <%  if (showLineVariances) { %>
                        <%=HTMLHelpersMasking.maskAccountingFormat(variance) %>
                    <%  } %>
                </td>
                <td>
                    <%  if (showLineVariances) { %>
                        <%=HTMLHelpersMasking.maskAccountingFormat(varianceWithBenefits) %>
                    <%  } %>
                </td>
			</tr>
    <%  } //end if(displayRow) %>			
<%	} //end loop %>

			<tr class="sub-total">
				<td>Total</td>
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
