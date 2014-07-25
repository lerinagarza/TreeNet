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
	<title>Statements</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<style>
    textarea {
        width:90%;
        max-width:40em;
        height:20em;
        line-height:2em;
        padding:.5em 1em;
    }
    input[name=title] {
        width:90%; 
        max-width:40em;
        padding:.5em 1em;
    }
</style>
<%@ page import = "
java.util.Vector,
com.treetop.businessobjects.KeyValue,
com.treetop.app.quality.UpdStatement 
"; %>
<%
UpdStatement us = (UpdStatement) request.getAttribute("updViewBean");
if (us == null) {
    us = new UpdStatement();
}
KeyValue statement = us.getStatement();

 %>
 
<h1>Statements</h1>

<form action="<%=request.getContextPath() %>/CtlQualityStatement/<%=us.getRequestType() %>" method="post">
<input type="hidden" name="id" value="<%=us.getId() %>">
<fieldset>
<legend>Update Statement</legend>

<div class="row-fluid">
    <div class="span2">
        <label for="title">Title</label>
    </div>
    <div class="span10">
        <input type="text" id="title" name="title" 
        value="<%=statement.getDescription() %>">
    </div>
</div>

<div class="row-fluid">
    <div class="span2">
        <label for="statementText">Statement</label>
    </div>
    <div class="span10">
        <textarea name="statementText" id="statementText" 
        maxlength="500"
        ><%=statement.getValue() %></textarea>
        <div class="comment">
            Statements are limited to 500 characters in length.  You have used <span id="len"></span>.
        </div>
    </div>
</div>

<div class="row-fluid" style="margin-top:2em;">
    <div class="span12 center">
        <%  String label = "";
            if (us.getRequestType().equals("add")) {
                label = "Add New Statement";
            }
            if (us.getRequestType().equals("update")) {
                label = "Save";
            } %>
        <input type="submit" name="submit" value="<%=label %>" class="ui-button">
        <a href="<%=request.getContextPath() %>/CtlQualityStatement"
        style="margin-left:2em;">Cancel</a>
    </div>
</div>

</fieldset>
</form>

<script>

function updateCounter(len) {
    $('#len').html(len);
}
updateCounter($('#statementText').val().length);
$('#statementText').keyup(function(){
    updateCounter($(this).val().length);
});
</script>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>