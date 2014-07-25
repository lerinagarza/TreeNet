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
<%@page import ="
    com.treetop.controller.rawfruitagreements.InqRawFruitAgreements,
    com.treetop.businessobjects.RawFruitAgreement"
 %>
<title>Raw Fruit Agreement Contact Details</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<style> input,select {
	display: block;}
</style>

<% InqRawFruitAgreements rawFruit = (InqRawFruitAgreements)request.getAttribute("DarthVader");
   RawFruitAgreement rfa = rawFruit.getAgreement();
%>
  
<!-- <form action="/web/CtlRawFruitAgreement/_________" method="post">  -->
    <div class="row-fluid" id="editContact">
    <div class="span12">
      <h1>Contact Details</h1>
      <div class="well">
        <div class="row-fluid">
          
          <div class="span4">
            <label for="">Name</label>
            <input type="text">
          </div>
          <div class="span4">
            <label for="">Type</label>
            <select name="" id="">
              <option value=""></option>
              <option value="">Cell</option>
              <option value="">Home</option>
              <option value="">Work</option>
            </select>
          </div>
          <div class="span4">
            <label for="">Number</label>
            <input type="tel">
          </div>
          
        </div>
        
        
       <button>Save</button>
        </div>
    </div>
  </div>

		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>