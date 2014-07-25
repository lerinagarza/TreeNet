<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "com.treetop.businessobjects.KeyValue, com.treetop.controller.operations.InqOperations, java.util.Vector" %>
<%  InqOperations io = (InqOperations) request.getAttribute("inqOperations");
    if (io == null) {
        io = new InqOperations();
    } 
    String weeklyCommentType = (String) request.getAttribute("weeklyCommentType");
    Vector weeklyComments = (Vector) io.getBean().getWeeklyComments().get(weeklyCommentType);

    request.setAttribute("listComments", weeklyComments);
    %>
   <jsp:include page="../../utilities/commentData.jsp"></jsp:include>