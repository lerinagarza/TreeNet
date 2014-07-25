<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import = "java.math.BigDecimal,
com.treetop.businessobjects.KeyValue, 
com.treetop.controller.operations.InqOperations,
com.treetop.businessobjects.ManufacturingFinance" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    ManufacturingFinance labor = (ManufacturingFinance) io.getBean().getMaintenance().get("Labor");
    if (labor == null) {
        labor = new ManufacturingFinance();
    }
    
    ManufacturingFinance benefits = (ManufacturingFinance) io.getBean().getMaintenance().get("Benefits");
    if (benefits == null) {
        benefits = new ManufacturingFinance();
    }
    
    ManufacturingFinance parts = (ManufacturingFinance) io.getBean().getMaintenance().get("Parts");
    if (parts == null) {
        parts = new ManufacturingFinance();
    }
    
    boolean weekly = true;
    if (io.getRequestType().equals("monthly")) {
        weekly = false;
    }
    
    
    BigDecimal laborEarn = BigDecimal.ZERO;
    BigDecimal laborEarnBenefits = BigDecimal.ZERO;
    BigDecimal laborPlan = BigDecimal.ZERO;
    BigDecimal laborPlanBenefits = BigDecimal.ZERO;
    BigDecimal laborActual = BigDecimal.ZERO;
    BigDecimal laborActualBenefits = BigDecimal.ZERO;
    
    BigDecimal partsEarn = BigDecimal.ZERO;
    BigDecimal partsPlan = BigDecimal.ZERO;
    BigDecimal partsActual = BigDecimal.ZERO;
    
    if (weekly) {
        laborEarn = labor.getWtdEarnings();
        laborEarnBenefits = benefits.getWtdEarnings();
        
        laborPlan = labor.getWtdPlan();
        laborPlanBenefits = benefits.getWtdPlan();
        
        laborActual = labor.getWtdActual();
        laborActualBenefits = benefits.getWtdActual();
        
        partsEarn = parts.getWtdEarnings();
        partsPlan = parts.getWtdPlan();
        partsActual = parts.getWtdActual();
        
    } else {
    
        laborEarn = labor.getMtdEarnings();
        laborEarnBenefits = benefits.getMtdEarnings();
        
        laborPlan = labor.getMtdPlan();
        laborPlanBenefits = benefits.getMtdPlan();
        
        laborActual = labor.getMtdActual();
        laborActualBenefits = benefits.getMtdActual();
        
        partsEarn = parts.getMtdEarnings();
        partsPlan = parts.getMtdPlan();
        partsActual = parts.getMtdActual();
    
    }
    

 %>
 
<div class="ui-widget">
    <div class="ui-widget-header ui-corner-top clearfix">
        <h3>Maintenance</h3>
    </div>
    <div class="ui-widget-content ui-corner-bottom">
    <%  
        if (io.getBean().getMaintenance().isEmpty()) {
    %>
        <div class=ui-comment>
            No information available for this reporting period.
        </div>
    <%  } else { %>
    
        <div class="row-fluid">
            <div class="span12">
                <table class="styled full-width">
                    <colgroup>
                        <col>
                        <col span="3">
                        <col>
                        <col>
                    </colgroup>
                    <tr>
                        <th>Earnings on Production</th>
                        <th class="hidden-phone" style="width:15%">Labor</th>
                        <th class="hidden-phone" style="width:15%" title="Benefits Rate: <%=io.getBenefitRate() %>" >Benefits</th>
                        <th style="width:15%">Labor + Benefits</th>
                        <th style="width:15%">Parts</th>
                        <th style="width:15%">Total</th>
                    </tr>
                    <tr>
                        <td>Earnings</td>
                        <td class="right hidden-phone">
                          <%=HTMLHelpersMasking.maskAccountingFormat(laborEarn) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborEarnBenefits) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborEarn.add(laborEarnBenefits)) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(partsEarn) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                laborEarn
                                    .add(laborEarnBenefits)
                                    .add(partsEarn)) %>
                        </td>
                    </tr>
                    <tr>
                        <td>Actual</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborActual) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborActualBenefits) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                 laborActual
                                    .add(laborActualBenefits)) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(partsActual) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                laborActual
                                    .add(laborActualBenefits)
                                    .add(partsActual)) %>
                        </td>
                    </tr>
                    <%  if (weekly) { %>
                    <tr class="sub-total">
                        <td>WTD Variance</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(labor.getWtdEarningsVar()) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(benefits.getWtdEarningsVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getWtdEarningsVar()
                                    .add(benefits.getWtdEarningsVar())) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(parts.getWtdEarningsVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getWtdEarningsVar()
                                    .add(benefits.getWtdEarningsVar())
                                    .add(parts.getWtdEarningsVar())) %>
                        </td>
                    </tr>
                    <%  } %>
                    <tr class="sub-total">
                        <td>MTD Variance</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(labor.getMtdEarningsVar()) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(benefits.getMtdEarningsVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getMtdEarningsVar()
                                    .add(benefits.getMtdEarningsVar())) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(parts.getMtdEarningsVar()) %>
                        </td>
                        <td class="right">
	                        <%=HTMLHelpersMasking.maskAccountingFormat(
	                            labor.getMtdEarningsVar()
	                                .add(benefits.getMtdEarningsVar())
	                                .add(parts.getMtdEarningsVar())) %>
                        </td>
                    </tr>
                    <tr class="sub-total">
                        <td>YTD Variance</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(labor.getYtdEarningsVar()) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(benefits.getYtdEarningsVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getYtdEarningsVar()
                                    .add(benefits.getYtdEarningsVar())) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(parts.getYtdEarningsVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getYtdEarningsVar()
                                    .add(benefits.getYtdEarningsVar())
                                    .add(parts.getYtdEarningsVar())) %>
                        </td>
                    </tr>


                    <tr>
                        <th>Planned Spend (<%=io.getForecastLabel() %>)</th>
                        <th class="hidden-phone">Labor</th>
                        <th class="hidden-phone" title="Benefits Rate: <%=io.getBenefitRate() %>">Benefits</th>
                        <th>Labor + Benefits</th>
                        <th>Parts</th>
                        <th>Total</th>
                    </tr>
                    <tr>
                        <td>Planned Spend</td>
                        <td class="right hidden-phone">
                          <%=HTMLHelpersMasking.maskAccountingFormat(laborPlan) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborPlanBenefits) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborPlan.add(laborPlanBenefits)) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(partsPlan) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborPlan.add(laborPlanBenefits).add(partsPlan)) %>
                        </td>
                    </tr>
                    <tr>
                        <td>Actual</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborActual) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(laborActualBenefits) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                 laborActual
                                    .add(laborActualBenefits)) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(partsActual) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                laborActual
                                    .add(laborActualBenefits)
                                    .add(partsActual)) %>
                        </td>
                    </tr>
                    <%  if (weekly) { %>
                    <tr class="sub-total">
                        <td>WTD Variance</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(labor.getWtdPlanVar()) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(benefits.getWtdPlanVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getWtdPlanVar()
                                    .add(benefits.getWtdPlanVar())) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(parts.getWtdPlanVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getWtdPlanVar()
                                    .add(benefits.getWtdPlanVar())
                                    .add(parts.getWtdPlanVar())) %>
                        </td>
                    </tr>
                    <%  } %>
                    <tr class="sub-total">
                        <td>MTD Variance</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(labor.getMtdPlanVar()) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(benefits.getMtdPlanVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getMtdPlanVar()
                                    .add(benefits.getMtdPlanVar())) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(parts.getMtdPlanVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getMtdPlanVar()
                                    .add(benefits.getMtdPlanVar())
                                    .add(parts.getMtdPlanVar())) %>
                        </td>
                    </tr>
                    <tr class="sub-total">
                        <td>YTD Variance</td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(labor.getYtdPlanVar()) %>
                        </td>
                        <td class="right hidden-phone">
                            <%=HTMLHelpersMasking.maskAccountingFormat(benefits.getYtdPlanVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getYtdPlanVar()
                                    .add(benefits.getYtdPlanVar())) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(parts.getYtdPlanVar()) %>
                        </td>
                        <td class="right">
                            <%=HTMLHelpersMasking.maskAccountingFormat(
                                labor.getYtdPlanVar()
                                    .add(benefits.getYtdPlanVar())
                                    .add(parts.getYtdPlanVar())) %>
                        </td>
                    </tr>

                </table>
            </div>

        </div>
        
        
        
      <% BigDecimal plantTotal = (BigDecimal) request.getAttribute("plantTotal");
       if (plantTotal == null) {
           plantTotal = BigDecimal.ZERO;
       }
       
       if (weekly) {
	       plantTotal = plantTotal.add(labor.getWtdEarningsVar()
	            .add(benefits.getWtdEarningsVar())
	            .add(parts.getWtdEarningsVar()));
       } else {
	         plantTotal = plantTotal.add(labor.getMtdEarningsVar()
	            .add(benefits.getMtdEarningsVar())
	            .add(parts.getMtdEarningsVar()));
       }  
            
       request.setAttribute("plantTotal", plantTotal);
        %>
       <%  }  //end if empty %>
        <%  KeyValue keys = io.getCommentKeys();
            keys.setKey1("maintenance");
            request.setAttribute("keys",keys); %>
        
         <%  if (io.getRequestType().equals("monthly")) { %>
            <% request.setAttribute("weeklyCommentType","maintenance"); %>
             <jsp:include page="weeklyComments.jsp"></jsp:include>
        <% } else { %>
                <jsp:include page="../../utilities/commentSection.jsp"></jsp:include>
         <% } %>

    </div>
</div>