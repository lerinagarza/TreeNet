
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
    com.treetop.businessobjects.RawFruitAgreement,
    com.treetop.businessobjects.RawFruitAgreementLine,
    com.treetop.businessobjects.Contact,
    com.treetop.businessobjects.PhoneNumber,
    java.util.List"
        %>
<%@ page import="com.treetop.utilities.html.HTMLHelpersMasking" %>

<title>Raw Fruit Agreement</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>

<% InqRawFruitAgreement rawFruit = (InqRawFruitAgreement)request.getAttribute("DarthVader");
   RawFruitAgreement rfa = rawFruit.getAgreement();

%>
<h1><%=rawFruit.getWriteUpNumber()%></h1>

<div class="row-fluid ui-widget-content ui-corner-bottom" id="details">
    <div class="span12">
      <!-- new comment -->
      <h1>Contract Details</h1>
      <div class="well">
      <div>
        <div class="clearfix">
        <div style="float:left;">
          <div>Write up #: <%=rfa.getWriteUpNumber() %></div>
        </div>
        <div style="float:right">
          <a href="/web/CtlRawFruitAgreements/editContract/<%=rawFruit.getId()%>/?environment=<%= rawFruit.getEnvironment()%>">Edit</a>
        </div>
      </div>
      </div>

      <div>Supplier #: <%=rfa.getSupplierNumber() %> - <%=rfa.getSupplierName()%></div>

      <div class="clearfix">
      <div style="float:left; margin-right:2em;">
        Entry Date: <%=rfa.getEntryDate() %>
      </div>
        <div style="float:left">
          Revised Date: <%=rfa.getRevisedDate() %>
        </div>
      </div>
      
      <div>Crop Year: <%=rfa.getCropYear() %></div>
      <div>Rep: <%=rfa.getFieldRep()%></div>
      </div>
      
    </div>
  </div>
  
    <div style="height:30px;">&nbsp;</div>
  
  <div class="row-fluid ui-widget-content ui-corner-bottom" id="cropDetails">
    <div class="span12">
      <h1>Crop Information</h1>
      <div class="well">
       <%List crop = rfa.getLines();
       for(int i = 0; i < crop.size(); i++){
       RawFruitAgreementLine line = (RawFruitAgreementLine) crop.get(i);
       	%>
       	<div class="clearfix">
        <div style="float:left;">
          <h3><%=line.getType() + " " + line.getCrop()%></h3>
        </div>
        <div style="float:right">
          <a href="/web/CtlRawFruitAgreements/editCropInfo/<%= rawFruit.getId()%>/<%=line.getSequence()%>/?environment=<%= rawFruit.getEnvironment()%> ">Edit</a>
        </div>
      </div>
        
        
        
        <div class="row-fluid">
          <div class="span6">

        <div><%= line.getRun() + ", " + line.getCategory() %></div>
        <div><%= line.getVariety() + ", " + line.getVarietyMisc() %></div>
        <div><%= line.getBins() + " Bins, " + line.getBinType()%></div>  
            
          </div>
          <div class="span6">
          
          <table>
            <tr>
              <td>Juice</td>
              <td class="right"><%= HTMLHelpersMasking.maskBigDecimal(line.getJuicePrice()) %></td>
            </tr>
            <tr>
              <td>J/P</td>
              <td class="right"><%= HTMLHelpersMasking.maskBigDecimal(line.getJpPrice()) %></td>
            </tr>
            <tr>
              <td>Peeler</td>
              <td class="right"><%= HTMLHelpersMasking.maskBigDecimal(line.getPeelerPrice()) %></td>
            </tr>
            <tr>
              <td>Premium</td>
              <td class="right"><%= HTMLHelpersMasking.maskBigDecimal(line.getPremiumPrice()) %></td>
            </tr>
            <tr>
              <td>Fresh Slice</td>
              <td class="right"><%= HTMLHelpersMasking.maskBigDecimal(line.getFreshSlicePrice()) %></td>
            </tr>
          </table>  
          </div>
        </div>
        
      </div>
       		
       <%} %> 
      
      <button>Add</button>

   </div>
  
    <div style="height:30px;">&nbsp;</div>
  
  <div class="row-fluid ui-widget-content ui-corner-bottom" id="locationDetails">
    <div class="span12">
      <h1>Location</h1>
      <div class="well">
  
        <div class="clearfix">
        <div style="float:left;">
          
        </div>
        <div style="float:right">
            <a href="/web/CtlRawFruitAgreements/editContract/<%=rawFruit.getId()%>/?environment=<%= rawFruit.getEnvironment()%>">Edit</a>
        </div>
      </div>
        
        
        
        <div class="row-fluid">
          <div class="span6">
            <h4>Carrier</h4>
            <div>Carrier: <%= rfa.getCarrierName() %></div>
            <div>Comments: <%= rfa.getCarrierComments() %></div>
            
          </div>
          <div class="span6">
            <h4>Location</h4>
            <div>Location: <%= rfa.getSupplierLocation() %></div>
            <div>Location #: <%= rfa.getSupplierNumber() %></div>
            <div>Latitude: <%= rfa.getOrchardLocation().getLatitude() %></div>
            <div>Longitude: <%= rfa.getOrchardLocation().getLongitude() %></div>
            
          </div><!---->
        </div>
        
        <div class="row-fluid">
          
          <div>Orchard Location: <%= rfa.getOrchardLocation().getAddressId() +  " " + rfa.getOrchardLocation().getStreet1() +
          " " + rfa.getOrchardLocation().getCity() + " " + rfa.getOrchardLocation().getState() 
          + " " + rfa.getOrchardLocation().getZip() %></div>
          
          <div>Driving Directions: <%= rfa.getOrchardLocation().getDrivingDirections() %></div>
          
        </div>

      </div>
      
    </div>
  </div>
  

  <div style="height:30px;">&nbsp;</div>
    
   <div class="row-fluid ui-widget-content ui-corner-bottom" id="contactDetails">
    <div class="span12">
      <h1>Contacts</h1>
      <div class="well">
  
        <div class="clearfix">
        <div style="float:left;">
          
        </div>
        <div style="float:right">
            <a href="/web/CtlRawFruitAgreements/editContract/<%=rawFruit.getId()%>/?environment=<%= rawFruit.getEnvironment()%>">Edit</a>
        </div>
      </div>
        
        
        
        <div class="row-fluid">
        <% List contact = rfa.getContacts();
        for(int i = 0; i < contact.size(); i++){
        Contact line = (Contact) contact.get(i);%> 
          <div class="span4">
            <div>Name: <%= line.getName() %></div>
          </div>
          <% List phone = line.getPhoneNumbers();
          for(int j =0; j < phone.size(); j++){
          PhoneNumber numbers = (PhoneNumber) phone.get(j); %>
          <div class="span4">
            <div>Type: <%= numbers.getType() %></div>
          </div>
          <div class="span4">
            <div>Number: <%= numbers.getPhoneNumber() %></div>
          </div>
          <% } %>
        </div>
        <% } %>
      </div>
      
      
      <a href="">Add </a>
      
    </div>
  </div>

          
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>