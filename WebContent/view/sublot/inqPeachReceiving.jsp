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
<%@ page import = "com.treetop.controller.sublot.InqSubLot,
com.treetop.controller.sublot.DtlSubLot,
com.treetop.businessobjects.PeachTicket,
com.treetop.utilities.UtilityDateTime" %>
<%  String context = request.getContextPath();    
    InqSubLot i = (InqSubLot) request.getAttribute("inqPeach");
    if (i == null) {
        i = new InqSubLot();
    }
    
    boolean hasRows = i.getBeanPeach().getTicketList().size() > 0;
   
    
%>
	<title>Peach Receiving</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<h1>Raw Fruit Grower Receiving Information</h1>


    <form id="updPeach"  action="<%=context %>/CtlSubLot/inqPeach" method="post">

<div class="row-fluid">
    <div class="span12">
    <fieldset>
        <legend>Load Details</legend>

        
        <br>
        
        <div class="row-fluid">
            <div class="span12 grower">
                <label for="growerName">Grower Name:</label>
                <input type="text" autocapitalize="off" autocorrect="off" autocomplete="off" 
                    name="growerName" id="growerName" value="<%=i.getGrowerName() %>">
            </div>
        </div>
        
        <br>
        <br>
        
        
        <script>
			var growerList;
			$.getJSON('<%=context %>/CtlSubLot/listGrowers', function (data) {
			    growerList = data;
			    $('input#growerName').typeahead({
			
			        name: 'growers',
			        local: growerList,
			        limit: 10
			    });
			})

        </script>
        <div class="row-fluid">
           <div class="span6">
               
               <table>
                   
                   <tr>
                       <td>
                           <label for="supplier">Supplier:</label>
                       </td>
                       <td>
                           <%=InqSubLot.buildDropDownPeachSuppliers(i.getEnvironment(), i.getSupplierNumber()) %>
                       </td>
                   </tr>
                   
                   <tr>
                       <td>
                            <label for="loadNumber">Load #:</label>
                       </td>
                       <td>
                            <input type="number" min="0" step="1" name="loadNumber" id="loadNumber" 
                            autocapitalize="off" autocorrect="off" autocomplete="off"
                            value="<%=i.getLoadNumber() %>">
                       </td>
                   </tr>
                   
                   
               </table>

           </div>
           
           
           <div class="span6">
           
               <table>
                   <tr>
                       <td>
                            <label for="lotNumber">Lot Number:</label>
                       </td>
                       <td>
                           <input type="text" name="lotNumber" id="lotNumber" 
                           autocapitalize="off" autocorrect="off" autocomplete="off"
                           value="<%=i.getLotNumber() %>">
                       </td>
                   </tr>
                   <tr>
                       <td>
                            <label for="supplier">Item Number:</label>
                       </td>
                       <td>
                            <%=InqSubLot.buildDropDownPeachItems(i.getEnvironment(), i.getItemNumber()) %>
                       </td>
                   </tr>
                  
                   
               </table>
           
           </div>
           
        </div>
        
        <br>
        
        <div class="row-fluid">
            
            <div class="span6">
            <table>
                <tr>
	                <td><label for="fromDate">From:</label></td>
	                <%String from = UtilityDateTime.getDateFromyyyyMMdd(i.getFromDate()).getDateFormatMMddyyyySlash(); %>
	                <td><input type="text" id="fromDate" name="fromDate" value="<%=from %>" /></td>
	            </tr>
	            
	            <tr>
	                <td><label for="toDate">To:</label></td>
	                <%String to = UtilityDateTime.getDateFromyyyyMMdd(i.getToDate()).getDateFormatMMddyyyySlash(); %>
	                <td><input type="text" id="toDate" name="toDate" value="<%=to %>" /></td>
                </tr>
            </table>
            </div>
        </div>
        
        <div class="row-fluid">
            <div class="span12 center">
                <input type="submit" class="ui-button" name="submitButton" value="List Receiving Tickets">
            </div>
        </div>
        
    </fieldset>
    
    </div>
</div>
    
<script>
  var dates = $( "#fromDate, #toDate" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      changeYear: true,
      showOn: "both",
      buttonImage: "<%=request.getContextPath() %>/Include/images/cal.gif",
      buttonImageOnly: true,
      onSelect: function( selectedDate ) {
          var option = this.id == "fromDate" ? "minDate" : "maxDate",
              instance = $( this ).data( "datepicker" ),
              date = $.datepicker.parseDate(
                  instance.settings.dateFormat ||
                  $.datepicker._defaults.dateFormat,
                  selectedDate, instance.settings );
          dates.not( this ).datepicker( "option", option, date );
      }
  }); 
</script>


<%  if (i.isAllowAdd()) { %>
<div class="row-fluid">
    <div class="span12 right">
        <a href="<%=context %>/CtlSubLot/updPeach" class="ui-button" target="_blank">Add a New Ticket</a>
    </div>
</div>
<%  } %>



<%  if (hasRows) { %>

<div class="row-fluid">
	<div class="span12">
	   <table class="styled full-width">
	       
	           <col>
	           <col span="2">
	           <col span="2">
	           <col>
	           <col>
	           <col>
	           <col>
	       
	       <thead>
	           <tr>
	               <th>Lot</th>
	               <th colspan="2">Item</th>
	               <th colspan="2">Supplier</th>
	               <th>Load</th>
	               <th>Receipt Date</th>
	               <th>DEC</th>
	               <th></th>
	           </tr>
	       </thead>
	       <tbody>
	       <%  for (int x=0; x<i.getBeanPeach().getTicketList().size(); x++) {
	           PeachTicket t = (PeachTicket) i.getBeanPeach().getTicketList().elementAt(x);
	        %>
	           <tr>
	               <td><%=t.getLotNumber() %></td>
	               <td><%=t.getItemNumber() %></td>
	               <td><%=t.getItemDescription() %></td>
	               <td><%=t.getSupplierName() %></td>
	               <td><%=t.getSupplierNumber() %></td>
	               <td><%=t.getLoadNumber() %></td>
	               <% String date = UtilityDateTime.getDateFromyyyyMMdd(t.getCreateDate()).getDateFormatMMddyyyySlash(); %>
	               <td class="center"><%=date %></td>
	               <td><%=t.getCreateUser() %></td>
	               <td>
	                   <a href="<%=context %>/CtlSubLot/dtlPeach?lotNumber=<%=t.getLotNumber() %>&itemNumber=<%=t.getItemNumber() %>"
	                   target="_blank">Print Form</a>
	                   
	                   
	                   <%  if (i.isAllowUpdate()) { %>
	                   <a href="<%=context %>/CtlSubLot/updPeach?lotNumber=<%=t.getLotNumber() %>&itemNumber=<%=t.getItemNumber() %>"
                       target="_blank">Update</a>
                       <%   } %>
                       
                       
	               </td>
	           </tr>
	           <%  } %>
	       </tbody>
	   </table>
	</div>
</div>

<%  } else { %>
    
    <%  if (!i.getSubmitButton().equals("")) { %>
    <div class="row-fluid">
	    <div class="span12">
	       <div class="ui-comment">
	           No tickets found
	       </div>
	    </div>
    </div>
    <%  } %>
    
<%  } %>

<br>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>