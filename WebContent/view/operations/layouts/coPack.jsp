<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>



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
