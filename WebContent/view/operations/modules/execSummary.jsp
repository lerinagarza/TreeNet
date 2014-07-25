<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import = "
com.treetop.controller.operations.InqOperations,
com.treetop.businessobjects.KeyValue,
com.treetop.services.ServiceOperationsReporting,
java.util.LinkedHashMap,
java.util.SortedMap,
java.util.Iterator,
java.util.ArrayList,
java.math.BigDecimal" %>

<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    Integer[] weeks = io.getWeekInPeriod(Integer.parseInt(io.getFiscalPeriodEnd())); 
    
    LinkedHashMap lhm = io.getBean().getPlantSummaries();
    ArrayList weekTotals = new ArrayList();
    %>

<%  if (lhm.isEmpty()) { %>
    <div class=ui-comment>
        No information available for this reporting period.
    </div>
<%  } else { %>


<div class="ui-widget">
    <!-- 
    <div class="ui-widget-header ui-corner-top">
        <h3>Comments</h3>
    </div>
     -->
    <div class="ui-widget-content ui-corner-bottom">

<table class="styled full-width">
        <col>
    <%  for (int i=0; i<weeks.length; i++) { %>
        <col>
    <%  } %>
        <col>
        <col>
    
    <tr>
        <th></th>
    <%  for (int i=0; i<weeks.length; i++) {
        Integer week = weeks[i]; %>
        <th>Week <%=week %></th>
    <%  } %>
        <th>MTD</th>
        <th><%=ServiceOperationsReporting.FORECAST_LABEL %></th>
    </tr>
    

<%  
    BigDecimal mtdTotal = BigDecimal.ZERO;
    BigDecimal fcstTotal = BigDecimal.ZERO;
    
    Iterator i = lhm.keySet().iterator();
    while (i.hasNext()) { 
        String key = (String) i.next();
    

%>
    <tr>
        <td><%=key %></td>
        <%  
            SortedMap values = (SortedMap) lhm.get(key);
            
            BigDecimal mtd = (BigDecimal) values.get("MTD");
            if (mtd == null) {
                mtd = BigDecimal.ZERO;
            }
            mtdTotal = mtdTotal.add(mtd);
            
            BigDecimal fcst = (BigDecimal) values.get("FCST");
            if (fcst == null) {
                fcst = BigDecimal.ZERO;
            }
            fcstTotal = fcstTotal.add(fcst);
            
            for (int j=0; j<weeks.length; j++) {
                Integer week = weeks[j]; 
                String weekKey = String.valueOf(week);
                
                BigDecimal amount = (BigDecimal) values.get(weekKey);
                if (amount == null) {
                    amount = BigDecimal.ZERO;
                }
                
 
                try {
                    BigDecimal weekTotal = (BigDecimal) weekTotals.get(j);
                    weekTotals.set(j, weekTotal.add(amount));
                } catch (Exception e) {
                    weekTotals.add(amount);
                }
                
                
        
        %>
        <td><%=HTMLHelpersMasking.maskAccountingFormat(amount) %></td>
        <%  } %>
        
        <td class="bold"><%=HTMLHelpersMasking.maskAccountingFormat(mtd) %></td>
        <td class="bold"><%=HTMLHelpersMasking.maskAccountingFormat(fcst) %></td>
    </tr>
<%  } %>    


    <tr class="sub-total">
    
        <td>Total</td>
        
    <%  for (int j=0; j<weeks.length; j++) {
        Integer week = weeks[j]; %>
        <td><%=HTMLHelpersMasking.maskAccountingFormat((BigDecimal) weekTotals.get(j)) %></td>
    <%  } %>
        <td><%=HTMLHelpersMasking.maskAccountingFormat(mtdTotal) %></td>
        <td><%=HTMLHelpersMasking.maskAccountingFormat(fcstTotal) %></td>
        
    </tr>

</table>


        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("execSummary");
            request.setAttribute("keys",keys); %>
        <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
    </div>
</div>


<%  } //end if isEmpty() %>