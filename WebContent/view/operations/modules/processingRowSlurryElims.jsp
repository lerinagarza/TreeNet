<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="
com.treetop.utilities.html.HTMLHelpersMasking,
java.math.BigDecimal, 
com.treetop.businessobjects.ManufacturingOrderDetail,
com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 

    ManufacturingOrderDetail m = (ManufacturingOrderDetail) io.getBean().getProcessingMOs().get("ProcSlurryElims");
    
    if (m != null) {
    
       String label = "Slurry and Elims";
       if (io.getWarehouse().equals("490")) {
           label = "Elims";
       }
       
%>       
    <tr>
        <td colspan="2"><%=label %></td>
        <td colspan="9" class="clearfix">
        <%  if (m.getYieldSlurryStandard().compareTo(BigDecimal.ZERO) != 0) { %>
            <span style="margin-right:1em"><b>Slurry Std</b>&nbsp;<%=HTMLHelpersMasking.maskPercent(m.getYieldSlurryStandard().multiply(BigDecimal.valueOf(-1))) %></span>
        <%  } %>
        <%  if (m.getYieldSlurryActual().compareTo(BigDecimal.ZERO) != 0) { %>
            <span style="margin-right:1em"><b>Slurry Act</b>&nbsp;<%=HTMLHelpersMasking.maskPercent(m.getYieldSlurryActual().multiply(BigDecimal.valueOf(-1))) %></span>
        <%  } %>
        <%  if (m.getYieldElimsStandard().compareTo(BigDecimal.ZERO) != 0) { %>
            <span style="margin-right:1em"><b>Elims Std</b>&nbsp;<%=HTMLHelpersMasking.maskPercent(m.getYieldElimsStandard().multiply(BigDecimal.valueOf(-1))) %></span>
        <%  } %>
        <%  if (m.getYieldElimsActual().compareTo(BigDecimal.ZERO) != 0) { %>
            <span style="margin-right:1em"><b>Elims Act</b>&nbsp;<%=HTMLHelpersMasking.maskPercent(m.getYieldElimsActual().multiply(BigDecimal.valueOf(-1))) %></span>
        <%  } %>
        </td>
    </tr>
    
<%  } //end if object is not null %>