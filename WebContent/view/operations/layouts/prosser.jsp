<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div class="row-fluid">
    <div class="span6">
        <jsp:include page="../modules/safety.jsp"></jsp:include>
    </div>
    <div class="span6">
        <jsp:include page="../modules/quality.jsp"></jsp:include>
    </div>
</div>

<div class="row-fluid">
    <div class="span12">
        <jsp:include page="../modules/rawFruit.jsp"></jsp:include>
    </div>
</div>

<div class="row-fluid" id="processing">
	<div class="span12">
		<jsp:include page="../modules/processing.jsp"></jsp:include>
	</div>
</div>

<div class="row-fluid">
	<div class="span12">
		<jsp:include page="../modules/packaging.jsp"></jsp:include>
	</div>
</div>


    <%-- Only display the Cherries module if there is data --%>
    <%@page import="
    com.treetop.controller.operations.InqOperations,
    com.treetop.businessobjects.ManufacturingOrderDetail
    " %>

    <%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
        if (io == null) {
            io = new InqOperations();
        }
        boolean displayCherry = io.getBean().getFrozenCherryMOs().isEmpty(); 
        String spanWidth = "6";
        if (!displayCherry) {
            spanWidth = "12";
        }
     %>

<div class="row-fluid">
    <div class="span<%=spanWidth %>">
        <jsp:include page="../modules/ingredientBlending.jsp"></jsp:include>
    </div>
    


    <%  if (displayCherry) { %>
    
    <div class="span6">
        <jsp:include page="../modules/frozenCherry.jsp"></jsp:include>
    </div>
    
    <%  } %>
        
    </div>
</div>

<div class="row-fluid">
    <div class="span12">
        <jsp:include page="../modules/maintenance.jsp"></jsp:include>
    </div>
</div>

<div class="row-fluid">
    <div class="span6">
        <div class="row-fluid">
            <div class="span12">
                <jsp:include page="../modules/utilities.jsp"></jsp:include>
            </div>
        </div>
    </div>
    
    <div class="span6">
        <div class="row-fluid">
            <div class="span12">
                <jsp:include page="../modules/labAndQuality.jsp"></jsp:include>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <jsp:include page="../modules/warehousing.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="span6">
        <jsp:include page="../modules/journalEntries.jsp"></jsp:include>
    </div>
    <div class="span6">
        <jsp:include page="../modules/inventoryAdjustments.jsp"></jsp:include>
    </div>
</div>


<div class="row-fluid">
    <div class="span12">
        <jsp:include page="../modules/summary.jsp"></jsp:include>
    </div>
</div>
