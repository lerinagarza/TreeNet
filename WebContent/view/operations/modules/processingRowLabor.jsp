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
    LinkedHashMap orders = io.getBean().getProcessingMOs(); 

    BigDecimal laborVarWithBenefitsTotal = BigDecimal.ZERO;

    Iterator i = orders.entrySet().iterator();
    while (i.hasNext()) {
       Map.Entry me = (Map.Entry)i.next();
       String key = (String) me.getKey();
       
       //if this isn't a labor row, skip it
       if (!key.contains("Labor")) {
            continue;
       }
       
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
                <td colspan="2"><%=key %></td>
                <td colspan="8" class="clearfix">

                    <div style="float:left">
                        <span style="margin-right:1em"><b>Earnings</b>&nbsp;<%=HTMLHelpersMasking.maskCurrency(mo.getLaborEarned(), 0, true) %></span>
                        <span style="margin-right:1em"><b>Actual</b>&nbsp;<%=HTMLHelpersMasking.maskCurrency(mo.getLaborActual(), 0, true) %></span>
                        <span style="margin-right:1em"><b>Variance</b>&nbsp;<%=HTMLHelpersMasking.maskCurrency(mo.getLaborVariance(), 0, true) %></span>
                    </div>
                     
                    <div style="float:right">
                        <span title="Benefits Rate: <%=io.getBenefitRate() %>"><b>Var w/ Ben:</b></span>
                    </div>
                    
                    
                </td>
                <td class="sub-total" style="vertical-align:bottom"><%=HTMLHelpersMasking.maskAccountingFormat(mo.getLaborVarianceWithBenefits()) %></td>
            </tr>
<%
            } // end if(displayRow)

    }  // end loop
       
       request.setAttribute("processingLaborVarWithBenefitsTotal",laborVarWithBenefitsTotal);
%>


