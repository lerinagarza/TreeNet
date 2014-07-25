<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "com.treetop.businessobjects.KeyValue,
java.math.BigDecimal, 
com.treetop.controller.operations.InqOperations,
com.treetop.businessobjects.ManufacturingFinance" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    ManufacturingFinance labor = (ManufacturingFinance) io.getBean().getQuality().get("Labor");
    if (labor == null) {
        labor = new ManufacturingFinance();
    }
    ManufacturingFinance benefits = (ManufacturingFinance) io.getBean().getQuality().get("Benefits");
    if (benefits == null) {
        benefits = new ManufacturingFinance();
    }
    ManufacturingFinance supplies = (ManufacturingFinance) io.getBean().getQuality().get("Supplies"); 
    if (supplies == null) {
        supplies = new ManufacturingFinance();
    }
    
    boolean weekly = true;
    if (io.getRequestType().equals("monthly")) {
        weekly = false;
    }
    BigDecimal earnVarTotal = BigDecimal.ZERO;
    BigDecimal laborEarn = BigDecimal.ZERO;
    BigDecimal laborEarnBenefits = BigDecimal.ZERO;
    
    BigDecimal laborActual = BigDecimal.ZERO;
    BigDecimal laborActualBenefits = BigDecimal.ZERO;
    
    BigDecimal suppliesEarn = BigDecimal.ZERO;
    BigDecimal suppliesActual = BigDecimal.ZERO;    

    if (weekly) {
        laborEarn = labor.getWtdEarnings();
        laborEarnBenefits = benefits.getWtdEarnings();
        
        laborActual = labor.getWtdActual();
        laborActualBenefits = benefits.getWtdActual();
        
        suppliesEarn = supplies.getWtdEarnings();
        suppliesActual = supplies.getWtdActual();
    } else {
        laborEarn = labor.getMtdEarnings();
        laborEarnBenefits = benefits.getMtdEarnings();
        
        laborActual = labor.getMtdActual();
        laborActualBenefits = benefits.getMtdActual();
        
        suppliesEarn = supplies.getMtdEarnings();
        suppliesActual = supplies.getMtdActual();
    
    }
    
    
 %>
<div class="ui-widget">
	<div class="ui-widget-header ui-corner-top">
		<h3>Lab and Quality</h3>
	</div>
	
    <div class="ui-widget-content ui-corner-bottom">
    
    <%  if (io.getBean().getQuality().isEmpty()) { %>
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
			    <th>Labor</th>
			    <th title="Benefits Rate: <%=io.getBenefitRate() %>">Benefits</th>
			    <th>Labor + Ben.</th>
			    <th>Supplies</th>
			    <th>Total</th>
			</tr>
			<tr>
			    <%   BigDecimal earnTotal = laborEarn
												    .add(laborEarnBenefits)
												    .add(suppliesEarn); %>
			    <td>Earnings</td>
			    <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(laborEarn) %></td>
			    <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(laborEarnBenefits) %></td>
			    <td class="right">
                    <%=HTMLHelpersMasking.maskAccountingFormat(laborEarn.add(laborEarnBenefits)) %>
			    </td>
			    <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(suppliesEarn) %></td>
			    <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(earnTotal) %></td>
			</tr>
			<tr>
			   <%   BigDecimal actualTotal = laborActual
												   .add(laborActualBenefits)
												   .add(suppliesActual); %>
               <td>Actual</td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(laborActual) %></td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(laborActualBenefits) %></td>
               <td class="right">
                    <%=HTMLHelpersMasking.maskAccountingFormat(laborActual.add(laborActualBenefits)) %>
               </td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(suppliesActual) %></td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(actualTotal) %></td>
           </tr>
           <tr class="sub-total">
               <%  earnVarTotal = earnTotal.subtract(actualTotal); %>
               <td>Variance</td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(laborEarn.subtract(laborActual)) %></td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(laborEarnBenefits.subtract(laborActualBenefits)) %></td>
               <td class="right">
                    <%=HTMLHelpersMasking.maskAccountingFormat(
                        laborEarn.subtract(laborActual)
                        .add(laborEarnBenefits.subtract(laborActualBenefits))) %>
               </td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(suppliesEarn.subtract(suppliesActual)) %></td>
               <td class="right"><%=HTMLHelpersMasking.maskAccountingFormat(earnVarTotal) %></td>
           </tr>

		</table>
        <% } %> 
		
	<% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
       if (plantTotal == null) {
           plantTotal = BigDecimal.ZERO;
       }
       plantTotal = plantTotal.add(earnVarTotal);
       request.setAttribute("plantTotal", plantTotal);
        %>

		<%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("labAndQuality");
            request.setAttribute("keys",keys); %>

         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","labAndQuality"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>


	</div>

</div>
