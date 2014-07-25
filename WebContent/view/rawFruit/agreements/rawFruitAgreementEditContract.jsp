<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, com.treetop.businessobjects.RawFruitAgreement" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp" />
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
    com.treetop.utilities.UtilityDateTime"
 %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.treetop.businessobjects.DateTime" %>
<%@ page import="com.treetop.utilities.html.DropDownSingle" %>
<%@ page import="com.treetop.utilities.html.HtmlSelect" %>
<%@ page import="com.treetop.controller.rawfruitagreements.UpdContract" %>
<title>Raw Fruit Agreement Contract Details</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp" />
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp" />
<jsp:include page="/view/template/header.jsp" />
<%-- tpl:put name="bodyarea" --%>
<style> input,select {
	display: block;}
</style>

<% InqRawFruitAgreements rawFruit = (InqRawFruitAgreements)request.getAttribute("DarthVader");
   RawFruitAgreement rfa = rawFruit.getAgreement();
   DateTime entryDate = UtilityDateTime.getDateFromyyyyMMdd(rfa.getEntryDate());
   DateTime revDate = UtilityDateTime.getDateFromyyyyMMdd(rfa.getRevisedDate());

   UpdContract updContract = new UpdContract(request);
    updContract.buildDropDowns();

%>
  
  <div class="row-fluid" id="editDetails">
    <div class="span12">
      <h1>Contract Details <%=rawFruit.getWriteUpNumber() %></h1>
      <form action="/web/CtlRawFruitAgreements/editContract/<%=rawFruit.getWriteUpNumber() %>" method="post">
	      <div class="well">
	        <label for="">Supplier #</label>
	        <input type="text" name="supplierNumber" value="<%=rfa.getSupplierNumber()%>">
	        <label for="">Entry Date</label>
	        <input type="text" class="datepicker" name="entryDate" value="<%=entryDate.getDateFormatMMddyyyySlash()%>">
	        <label for="">Revision Date</label>
	        <input type="text" class="datepicker" name="revisionDate" value="<%=revDate.getDateFormatMMddyyyySlash()%>">
	        <label for="">Crop Year</label>
   	        <%=DropDownSingle.buildDropDown(updContract.getDropDownCropYear(), "cropYear","",rfa.getCropYear(),"", false, "", HtmlSelect.DescriptionType.DESCRIPTION_ONLY)%>

	       <button>Save</button>
	        </div>
        </form>
    </div>
  </div>


		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp" />
</body>
</html><%-- /tpl:insert --%>