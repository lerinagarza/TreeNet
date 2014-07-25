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
        <h3 style="padding-left:.25em;">Ingredient Blending</h3>
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
              <col>
              <col>
              <col>
          </colgroup>
            <tr>
                <th style="max-width:150px;"></th>
                <th style="width:22.5%">Plan</th>
                <th style="width:22.5%">Actual</th>
                <th style="width:22.5%">% of Plan</th>
            </tr>
            
        
<%  Iterator i = orders.entrySet().iterator();
    
    //Summary fields        
    
    BigDecimal plannedTotal = BigDecimal.ZERO;
    BigDecimal actualTotal = BigDecimal.ZERO;
    BigDecimal percentTotal = BigDecimal.ZERO;
    

    while (i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        String key = (String) me.getKey();
        ManufacturingOrderDetail m = (ManufacturingOrderDetail) me.getValue();
        
        
        //data fields
        BigDecimal planned = m.getProductionPlanned();
        BigDecimal actual = m.getProduction();
        
        BigDecimal percent = BigDecimal.ZERO;
        if (planned.compareTo(BigDecimal.ZERO) != 0) {
            percent = actual.divide(planned,4,BigDecimal.ROUND_HALF_UP);
        }

        
       
        //calculate summaries
        plannedTotal = plannedTotal.add(planned);
        actualTotal = actualTotal.add(actual);               
        
        boolean displayRow = true;

        
        if (key.contains("Labor")) {
            displayRow = false;
        }
        
        if (displayRow) {
        
        
        
        //calculate summary fields


        
    %>

            <tr>
                <td>
                <%  if (m.getOrderNumbers().isEmpty()) { %>
                    <%=key %>
                <%  } else { %>
                        <div class=detailed-tip><%=key %></div>
                        <div class="opsTips left">
                           <h4>Order Numbers:</h4>
                           <p><%=InqOperations.buildOrderNumberList(m) %>
                           </p>
                        </div>
                <%  } %>
                </td>
                
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(planned) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(actual) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskPercent(percent) %></td>
            </tr>
    <%  } //end if(displayRow) %>           
<%  } //end loop %>

<%
    if (plannedTotal.compareTo(BigDecimal.ZERO) != 0) {
        percentTotal = actualTotal.divide(plannedTotal,4,BigDecimal.ROUND_HALF_UP);
    }
 %>
 
 
 
 
            <tr class="sub-total">
                <td>Total</td>
                
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(plannedTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(actualTotal) %></td>
                <td class="right"><%=HTMLHelpersMasking.maskPercent(percentTotal) %></td>
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
 
        
        </table>
        
    <% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
       if (plantTotal == null) {
           plantTotal = laborVarWithBenefitsTotal;
       }
       plantTotal = plantTotal.add(laborVarWithBenefitsTotal);
       request.setAttribute("plantTotal", plantTotal);
        %>
        
    <% } //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("ingredientBlending");
            request.setAttribute("keys",keys); %>
            
            
         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","ingredientBlending"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>


    </div>
</div>
