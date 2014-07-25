<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import="com.treetop.utilities.html.HTMLHelpers"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "
com.treetop.controller.blending.InqBlending,
com.treetop.businessobjectapplications.BeanBlending,
com.treetop.businessobjects.ManufacturingOrder,
com.treetop.utilities.UtilityDateTime,
java.math.BigDecimal,
java.util.Vector" %>
<%
InqBlending ib = (InqBlending) request.getAttribute("inqViewBean");
if (ib == null) {
    ib = new InqBlending();
}
BeanBlending bean = ib.getBean();
if (bean == null) {
    bean = new BeanBlending();
}
Vector listProduction = bean.getListProduction();

String fromDate = UtilityDateTime.getDateFromyyyyMMdd(ib.getFromDate()).getDateFormatMMddyyyySlash();
String toDate = UtilityDateTime.getDateFromyyyyMMdd(ib.getToDate()).getDateFormatMMddyyyySlash();
%>
<div class="row-fluid" style="margin:2em 0;">
    <div class="span12">
       <h3>Blending for <%=fromDate %> through <%=toDate %></h3>
    </div>
</div>

<div class="row-fluid" style="margin:2em 0;">
<div class="span12">
<table class="styled full-width row-highlight">

<thead>
    <tr>
        <th>Week</th>
        <th>Facility</th>
        <th>Item</th>
        <th>Volume (CS)</th>
        <th>Batches</th>       
    </tr>
</thead>

<tbody>
<%  
    BigDecimal productionTotal = BigDecimal.ZERO;
    BigDecimal productionSubTotalWeek = BigDecimal.ZERO;
    BigDecimal productionSubTotalFacility = BigDecimal.ZERO;
    
    BigDecimal batchTotal = BigDecimal.ZERO;
    BigDecimal batchSubTotalWeek = BigDecimal.ZERO;
    BigDecimal batchSubTotalFacility = BigDecimal.ZERO;
    
    String lastWeek = "";
    String lastWeekFacility = "";
    String lastFacility = "";
    
    String subTotalFacilityLabel = "";
    String subTotalWeekLabel = "";
    
    boolean printSubTotalFacility = false;
    boolean printSubTotalWeek = false;
    
    for (int i=0; i<listProduction.size(); i++) { 
        ManufacturingOrder m = (ManufacturingOrder) listProduction.elementAt(i);
        String week = m.getActualStartDate().getM3FiscalWeek();
        String facility = m.getWarehouse().getFacility();
        String itemNumber = m.getItem().getItemNumber();
        String itemDescription = m.getItem().getItemDescription();
        
        BigDecimal orderQuantity = m.getOrderQuantity();
        BigDecimal batchQuantity = m.getNumberOfBatches();
        
        
        subTotalFacilityLabel = "Facility " + lastFacility + " sub-total";
        subTotalWeekLabel = "Week " + week + " sub-total";
        
        if (i > 0 && !lastWeek.equals(week)) {
            printSubTotalWeek = true;
            lastFacility = "";
        }
        
         if (i > 0 && !lastWeekFacility.equals(week + facility)) {
            printSubTotalFacility = true;
        }

%>    
<%  if (printSubTotalFacility) {
        printSubTotalFacility = false;
        
 %>
    <tr class="sub-total">
        <td colspan="3"><%=subTotalFacilityLabel %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(productionSubTotalFacility) %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(batchSubTotalFacility,1) %></td>
    </tr>
<%      productionSubTotalFacility = BigDecimal.ZERO;
        batchSubTotalFacility = BigDecimal.ZERO;
    } %>
<%  if (printSubTotalWeek) {
        printSubTotalWeek = false;
        lastFacility = "";
 %>
    <tr class="sub-total light-bg">
        <td colspan="3"><%=subTotalWeekLabel %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(productionSubTotalWeek) %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(batchSubTotalWeek,1) %></td>
    </tr>
<%      productionSubTotalWeek = BigDecimal.ZERO;
        batchSubTotalWeek = BigDecimal.ZERO;
    } %>

<%


 %>

    <tr>
        <td class="center"><%=week %></td>
        <td class="center"><%=facility %></td>
        <td><%=itemNumber %> <%=itemDescription %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(orderQuantity) %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(batchQuantity,1) %></td>
    </tr>
    
    
<%  
        productionSubTotalFacility = productionSubTotalFacility.add(orderQuantity);
        productionSubTotalWeek = productionSubTotalWeek.add(orderQuantity);
        productionTotal = productionTotal.add(orderQuantity);
        
        batchSubTotalFacility = batchSubTotalFacility.add(batchQuantity);
        batchSubTotalWeek = batchSubTotalWeek.add(batchQuantity);
        batchTotal = batchTotal.add(batchQuantity);

        lastFacility = facility;
        lastWeekFacility = week + facility;
        lastWeek = week;

    }
 %>  

    <tr class="sub-total">
        <td colspan="3">Facility <%=lastFacility %> Sub Total</td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(productionSubTotalFacility) %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(batchSubTotalFacility,1) %></td>
    </tr>
    <tr class="sub-total light-bg">
        <td colspan="3">Week <%=lastWeek %> Sub Total</td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(productionSubTotalWeek) %></td>
        <td class="right"><%=HTMLHelpersMasking.maskBigDecimal(batchSubTotalWeek,1) %></td>
    </tr>
    <tr class="grand-total">
        <td colspan="3">Grand Total</td>
        <td><%=HTMLHelpersMasking.maskBigDecimal(productionTotal) %></td>
        <td><%=HTMLHelpersMasking.maskBigDecimal(batchTotal,1) %></td>
    </tr>  

</tbody>

</table>
</div>
</div>