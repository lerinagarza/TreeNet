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
    com.treetop.controller.rawfruitagreements.InqRawFruitAgreement,
    com.treetop.businessobjects.RawFruitAgreement"
 %>
<title>Raw Fruit Agreement Crop Details</title>
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

<% InqRawFruitAgreement rawFruit = (InqRawFruitAgreement)request.getAttribute("DarthVader");
   RawFruitAgreement rfa = rawFruit.getAgreement();
%>
  
  </div>
    <div class="row-fluid" id="editLocation">
    <div class="span12">
      <h1>Edit Location</h1>
      <div class="well">
        <div class="row-fluid">
          <div class="span6">
            <label for="">Carrier</label>
            <input type="text">
            <label for="">Carrier Comments</label>
            <input type="text">
          </div>
          <div class="span6">
            <label for="">Location Lookup</label>
            <select name="" id="">
              <option value=""></option>
              <option value="">335 - LOCO507 - 2280 Eastside Dr. - Hood River, OR</option>
              <option value=""></option>
            </select>
            <label for="">Location #</label>
            <input type="tel">
            <label for="">Latitude</label>
            <input type="tel">
            <label for="">Longitude</label>
            <input type="tel">
          </div>
        </div>

        <div class="row-fluid">
          <div class="span12">
            <label for="">Orchard Location</label>
            <input type="text">
            <label for="">Driving Directions</label>
            <input type="text">
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