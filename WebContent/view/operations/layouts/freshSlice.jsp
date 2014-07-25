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

<div class="row-fluid">
    <div class="span12">
        <jsp:include page="../modules/processing.jsp"></jsp:include>
    </div>
</div>

<div class="row-fluid">
    <div class="span12">
        <jsp:include page="../modules/processingLaborWithHours.jsp"></jsp:include>
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