<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import="com.treetop.utilities.html.HtmlSelect.DescriptionType"%>
<%@page import="com.treetop.utilities.html.DropDownDual"%>
<%@page import="com.treetop.utilities.UtilityDateTime, java.util.Vector"%>
<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp"></jsp:include>
<%  String environment = (String) request.getParameter("environment");
    if (environment == null || environment.equals("")) { 
        environment="PRD";
    }
    
    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    if (roles != null && Arrays.asList(roles).contains("1")) {
        internal = true;
    }
%>
<%-- tpl:put name="headarea" --%>
<%@ page import="com.treetop.controller.operations.InqOperations" %>
<%  InqOperations io = null;
    io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    }
    String warehouse = "";
    if (!io.getWarehouse().equals("")) {
        warehouse = " - " + io.getWarehouse();
    }
%>

	<title>Operations Reporting<%=warehouse %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>


<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>

<div id="page-title">
    <h1>Operations Reporting</h1>
</div>

<%  if (io.getSubmit().trim().equals("")) { %>



<fieldset>
	<legend>Choose a Manufacturing Warehouse</legend>
	<form action="${pageContext.request.contextPath}/CtlOperations" method="post">
	<input type="hidden" name="requesType" value="">
	<div class="table-wrapper">
	   <table>
	       <tr>
	           <td><label for="warehouse">Warehouse: </label></td>
	           <%//TODO for now, default in 209-Selah Plant 
	               String defaultWarehouse = "";
	               if (!io.getWarehouse().trim().equals("")) {
	                   defaultWarehouse = io.getWarehouse();
	               }
	           %>
	           
	           <td colspan="2">
	               <div><%=io.buildDropDownWarehouse(defaultWarehouse)%></div>
	               <%  if (!io.getWarehouseError().equals("")) { %>
	               <div class="error"><%=io.getWarehouseError() %></div>
	               <%  } %>
	           </td>
	       </tr>
	       <tr>
	           <td><label for="fiscalYear">Fiscal Year: </label></td>
	           <td colspan="2"><%=io.buildDropDownFiscalYear(io.getFiscalYear()) %></td>
	       </tr>

	           <%Vector dates = UtilityDateTime.buildMonthWeekDualDropDown(); %>
	           <td>
	               <label for="fromMonth">Month:</label>
	           </td>
	           <td>
	               <%=DropDownDual.buildMaster(dates, 
	               "fiscalPeriodStart",    //name
	               "fiscalPeriodStart",    //id
	               "",             //cssClass
	               io.getFiscalPeriodStart(),             //selected value
	               "",             //default value
	               false,          //read only
	               DescriptionType.VALUE_DESCRIPTION ) %>
	           </td>
	           <td>
	           <%  String checked = "";
	               if (io.getRequestType().equals("monthly")) {
	                   checked = "checked";
	               } %>
                    <label><input id="monthly" type="checkbox" <%=checked %> > Monthly Summary</label>
    
               </td>
	       </tr>
           <tr id="week" style="display:none;">
               <td>
                   <label for="fromWeek">Week:</label>
               </td>
               <td>
                   <%=DropDownDual.buildSlave(dates, 
                   "fiscalPeriodStart",   //master id
                   "fiscalWeekStart",    //name
                   "fiscalWeekStart",    //id
                   "",            //cssClass
                   io.getFiscalWeekStart(),            //selected value
                   "",            //default value
                   false,         //read only
                   DescriptionType.VALUE_ONLY ) %>
               </td>
               <td>
               </td>
           </tr>
           
           <%  if (!io.getDateError().equals("")) { %>
           <tr>
            <td></td>
            <td>
                <div class="error"><%=io.getDateError() %></div>
            </td>
            <td></td>
           </tr>      
           <%  } %>
           
           <tr>
                <td colspan="3" class=center>
                    <input type=hidden name=environment value=<%=io.getEnvironment() %>>
                    <input id=submit name=submit type=submit class="processingDialog" value="GO">
                    <script>$("#submit").button();</script>
                </td>
           </tr>
	   </table>
	   
	</div>	
	</form>
</fieldset>

<script>

function changeState(el) {
    if ($(el).is(':checked')) {
        $('#week').hide();
        $('select[name=fiscalWeekStart]').val('');
        $('input[name=requestType]').val('monthly');
    } else {
        $('#week').show();
        $('input[name=requestType]').val('weekly');
    }
}

changeState($('#monthly'));

$('#monthly').change(function (){
    changeState(this);
});
/*
$('select[name=warehouse]').change(function (){
    if ($(this).val() == 'EXEC') {
        $('#monthly').prop('disabled',true);
        changeState($('#monthly').prop('checked', true));
    } else {
        $('#monthly').prop('disabled',false);
    }
});
*/
</script>

<%  } else { %>

<style>
    .opsTips {
        display:none;
        background-color:#ffffff;
        padding:.5em;
        -moz-box-shadow: 3px 3px 9px rgba(0, 0, 0, 0.5);
        -webkit-box-shadow: 3px 3px 9px rgba(0, 0, 0, 0.5);
        box-shadow: 3px 3px 9px rgba(0, 0, 0, 0.5);
        border-radius:6px;
        text-align:left;
        max-width:280px;
    }
    #ops th[data-tip]:hover {
        cursor:help;
    }
    .row-fluid [class*=span] {
        margin-bottom:1em;
    }
    #ops .row-fluid {
        page-break-inside: avoid;
    }
</style>


<% if (io.getWarehouse().equals("EXEC")) { %>
    <h2>Executive Summary</h2>
<% } else { %>
    <h2>Warehouse <%=io.getBean().getWarehouse().getWarehouse() %> - <%=io.getBean().getWarehouse().getWarehouseDescription() %></h2>
<% } %>


<div class=clearfix>
    <div style="float:left">
        <%  String yearPeriod = "Fiscal " + io.getFiscalYear() + " - ";
            if (io.getRequestType().equals("") || io.getRequestType().equals("weekly")) {
                yearPeriod +=  "Week " + io.getFiscalWeekStart();
            } else if (io.getRequestType().equals("monthly")) {
                yearPeriod +=  "Period " + io.getFiscalPeriodStart();
            } %>
        <h3><%=yearPeriod %></h3>
        <span class="comment"><%=io.getStartDateFormatted()%> - <%=io.getEndDateFormatted() %></span>
    </div>    
	<div style="float:right;">
	<form action="${pageContext.request.contextPath}/CtlOperations" method="post">
    	<%=io.buildResend() %>
    	<a href="#" class="ui-select-again" onclick="$(this).parent().submit(); return false;">Select Again</a>
	</form>
	    
	</div>
</div>

<br>

<div id="ops">
    <%  String whse = io.getWarehouse().trim(); %>
    
    <%  if (whse.equals("209")) { %>
        <jsp:include page="layouts/selah.jsp" />
    <%  } else if (whse.equals("230")) { %>
        <jsp:include page="layouts/wenatchee.jsp" />
    <%  } else if (whse.equals("240")) { %>
        <jsp:include page="layouts/ross.jsp" />
    <%  } else if (whse.equals("251")) { %>
        <jsp:include page="layouts/oxnard.jsp" />         
    <%  } else if (whse.equals("280")) { %>
        <jsp:include page="layouts/medford.jsp" />
    <%  } else if (whse.equals("290")) { %>
        <jsp:include page="layouts/woodburn.jsp" />
    <%  } else if (whse.equals("469")) { %>
        <jsp:include page="layouts/prosser.jsp" />
    <%  } else if (whse.equals("490")) { %>
        <jsp:include page="layouts/freshSlice.jsp" />
    <%  } else if (whse.equals("342")
                    || whse.equals("343")
                    || whse.equals("345")
                    || whse.equals("346")
                    || whse.equals("350")
                    || whse.equals("380")
                    || whse.equals("384")
                    ) { %>
        <jsp:include page="layouts/coPack.jsp" />
    <%  } else if (whse.equals("EXEC")) { %>
        <jsp:include page="layouts/execSummary.jsp" />
    <%  } else { %>
        <div class=ui-comment>
            This manufacturing warehouse has not been setup for this application.<br>
            Please choose another warehouse.
        </div>
    <%  } %>
    
</div>
<script>
	$("input[type=button]").button();
	$("#ops .detailed-tip").parent("th").attr("data-tip","tip");
	$("#ops th[title]").attr("data-tip","tip")
	$("#ops th[title], #ops td[title], #ops .detailed-tip")
	   .tooltip({predelay:600, delay:800, tipClass:"opsTips", effect:'slide'})    
	   .dynamic();
</script>
<%  } %>
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>
