<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, com.treetop.businessobjects.RawFruitAgreementLine" %>
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
    com.treetop.controller.rawfruitagreements.InqRawFruitAgreements"
 %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.treetop.controller.rawfruitagreements.UpdCropInfo" %>
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

<% InqRawFruitAgreements rawFruit = (InqRawFruitAgreements)request.getAttribute("DarthVader");
   RawFruitAgreementLine line = (RawFruitAgreementLine) request.getAttribute("agreementLine");
    UpdCropInfo updCropInfo = new UpdCropInfo(request);
    updCropInfo.buildDropDowns();
%>
  
<div class="row-fluid" id="editCrop1">
    <form action="/web/CtlRawFruitAgreements/editCropInfo/<%= rawFruit.getWriteUpNumber()%>/1" method="post">
      <div class="span12">
      <h1>Crop Details</h1>
      <div class="well">
        <div class="row-fluid">
          <div class="span4">
            <label >Crop</label>
              <select name="crop">
                  <option value=""></option>
                  <option value="2013">2013</option>
                  <option value="2014">2014</option>
              </select>
            <label >Type</label>
            <input type="text" name="type" value="<%=line.getType()%>">

            <label >Run</label>
            <select name="run" >
              <option value=""></option>
              <option value="">Orchard Run</option>
            </select>

            <label >Category</label>
            <select name="category" >
              <option value=""></option>
              <option value="">Juice</option>
              <option value="">Peeler</option>
            </select>

            <label >Variety</label>
            <select name="variety" >
              <option value=""></option>
              <option value="">Fuji</option>
              <option value="">Jazz</option>
              <option value="">Golden Delicious</option>
            </select>

            <label >Variety Misc</label>
            <input type="text" name="varietyMisc" value="<%=line.getVarietyMisc()%>">
          </div>
          
          <div class="span4">
            <label ># of Bins</label>
            <input type="tel" name="bins" value="<%=line.getBins()%>">
            <label >Bin Type</label>
            <select name="binType" >
              <option value=""></option>
              <option value="">Wood</option>
              <option value="">Plastic</option>
            </select>
            <label >Payment Type</label>
            <select name="paymentType" >
              <option value=""></option>
              <option value="">Cash</option>
              <option value="">Credit</option>
            </select>
          </div>
          
          <div class="span4">
          	<h4>Price</h4>
            <label >JUICE$$</label>
            <input type="tel" name="juicePrice" class="right" value="<%=line.getJuicePrice()%>">
            <label >J-PS$$</label>
            <input type="tel" name="jpPrice" class="right" value="<%=line.getJpPrice()%>">
            <label >PEELER$$</label>
            <input type="tel" name="peelerPrice" class="right" value="<%=line.getPeelerPrice()%>">
            <label >PREM$$</label>
            <input type="tel" name="premiumPrice" class="right" value="<%=line.getPremiumPrice()%>">
            <label >FS$$</label>
            <input type="tel" name="freshSlicePrice" class="right" value="<%=line.getFreshSlicePrice()%>">
            <label >CH/PC$$</label>
            <input type="tel" name="price" class="right" value="<%=line.getPrice()%>">
          </div>
  
        </div>

        

      </div>

    </div>
        <input type="submit" value="Save" />
    </form>
</div>




<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>