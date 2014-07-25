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
	<title>Request Track and Trace</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@page import="com.treetop.app.transaction.InqTransaction, java.util.Vector" %>

<%	InqTransaction it = (InqTransaction) request.getAttribute("inqViewBean");
	if (it == null) {
		it = new InqTransaction();
	}
 %>	

	<h1>Track and Trace</h1>
	<br />

	<fieldset>
		<legend>Select Report to Run</legend>

			<form action="/web/CtlTrackAndTrace" method="post" id="trackAndTraceForm"> 

				<table id="inputParameters">
					<thead>
						<tr>
							<td>
								<label for="requestType">Report:</label>
							</td>
							<td>
								<select id="requestType" name="requestType">
								<%  String selected = ""; %>
								
									<% if (it.getRequestType().equals("inqTrackAndTrace") || it.getRequestType().equals("")) {
	                                     selected = "selected"; 
	                                   } %>
									<option value="inqTrackAndTrace" <%=selected %>></option>
									
									<% selected = "";
                                       if (it.getRequestType().equals("inqSingleIngredientForward")) {
                                        selected = "selected"; 
                                       } %>
                                    <option value="inqSingleIngredientForward" <%=selected %>>Individual Item Lot Trace</option>
                                    
                                    
									<% selected = "";
                                       if (it.getRequestType().equals("inqFruitToShipping")) {
                                        selected = "selected"; 
                                       } %>
                                    <option value="inqFruitToShipping" <%=selected %>>Recall Trace &ndash; Fruit to Shipping-Fresh Slice</option>
                                    
									
									<% selected = "";
                                       if (it.getRequestType().equals("inqProductionDayBack")) {
	                                    selected = "selected"; 
	                                   } %>
									<option value="inqProductionDayBack" <%=selected %>>Recall Trace &ndash; Production to Ingredients</option>
									
									
									<% selected = "";
                                       if (it.getRequestType().equals("inqProductionDayForward")) {
	                                    selected = "selected"; 
	                                   } %>
									<option value="inqProductionDayForward" <%=selected %>>Recall Trace &ndash; Production to Shipping</option>
									
									<% selected = "";
                                       if (it.getRequestType().equals("inqProductionDayForwardOS")) {
                                        selected = "selected"; 
                                       } %>
                                    <option value="inqProductionDayForwardOS" <%=selected %>>Recall Trace &ndash; Production to Shipping - OS</option>
                                    
								
									
									
									
								</select>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3">
								<%	if(!it.getDisplayErrors().trim().equals("")) { %>
								<div class="ui-error"><%=it.getDisplayErrors() %></div>
								<%	} %>
								&nbsp;
							</td>
						</tr>
					</thead>
					
					<tbody>

						<tr class="inqFruitToShipping inqProductionDayBack inqProductionDayForward">
							<td>
								<label for="inqFacility">Facility:</label>
							</td>
							<td>
								<%= InqTransaction.buildDropDownFacilityTrackAndTrace(it.getInqEnvironment(), it.getInqFacility()) %>
							</td>
							<td></td>
						</tr>
						
						<tr class="inqFruitToShipping inqProductionDayBack inqProductionDayForward">
							<td>
								<label for="inqDate">Start Date:</label>
							</td>
							<td>
								<input type="text" name="inqDate" id="inqDate" class="datepicker" 
									value="<%=it.getInqDate() %>">
							</td>
							<td>
								<% if (!it.getInqDateError().trim().equals("")) {  %>
								<div class="ui-error">
									<%=it.getInqDateError() %>
								</div>
								<%	}%>
							</td>
						</tr>
						
						<tr class="inqSingleIngredientForward inqFruitToShipping inqProductionDayBack inqProductionDayForward">
							<td>
								<label for=inqItem>Item Number:</label>
							</td>
							<td>
								<input type="number" id="inqItem" name="inqItem" value="<%=it.getInqItem()%>" />
							</td>
							<td>
								<% if (!it.getInqItemError().trim().equals("")) {  %>
								<div class="ui-error">
									<%=it.getInqItemError() %>
								</div>
								<%	}%>
							</td>
						</tr>
						
						<tr class="inqFruitToShipping inqProductionDayBack inqProductionDayForward">
							<td colspan="2" class="center">
								OR
							</td>
							<td></td>
						</tr>
						
						<tr class="inqFruitToShipping inqProductionDayBack inqProductionDayForward">
							<td>
								<label for="inqOrder">Manufacturing<br />Order Number:</label>
							</td>
							<td>
								<input type="number" id="inqOrder" name="inqOrder" value="<%=it.getInqOrder() %>" />
							</td>
							<td>
								<% if (!it.getInqOrderError().trim().equals("")) {  %>
								<div class="ui-error">
									<%=it.getInqOrderError() %>
								</div>
								<%	}%>
							</td>
						</tr>
						
						<tr class="inqSingleIngredientForward">
							<td>
								<label for="inqLot">Lot Number:</label>
							</td>
							<td>
								<input type="text" id="inqLot" name="inqLot" value="<%=it.getInqLot() %>" />
							</td>
							<td>
								<% if (!it.getInqLotError().trim().equals("")) {  %>
								<div class="ui-error">
									<%=it.getInqLotError() %>
								</div>
								<%	}%>
							</td>
						</tr>
						
						<tr class="inqProductionDayForwardOS">
                            <td>
                                <label for="inqLot">Manufacture Label:</label>
                                <br>
                                <span class="comment">Ex. P53000000700031013</span>
                            </td>
                            <td>
                                <input type="text" id="inqManufactureLabel" name="inqManufactureLabel" value="<%=it.getInqManufactureLabel() %>" />
                            </td>
                            <td>
                                <% if (!it.getInqManufactureLabelError().trim().equals("")) {  %>
                                <div class="ui-error">
                                    <%=it.getInqManufactureLabelError() %>
                                </div>
                                <%  }%>
                            </td>
                        </tr>
						
					</tbody>
					
					<tfoot>
						<tr>
							<td colspan="2" class="center">
								<input type="submit" class="ui-button" name="goButton" value="GO" />
							</td>
							<td></td>
						</tr>
					</tfoot>
		
				</table>
			</form>
	
	</fieldset>
	
	<div id="download-dialog" style="display:none; text-align:center;" title="Processing...">
           <h3>The Track and Trace report is being built.</h3>
           <p>Your download will begin shortly.</p>
           <img src="/web/Include/images/ajax-loader-bar.gif">
       </div>
	
	<script type="text/javascript">
	
	
	
		function controlInputs(reportType) {
			$("#inputParameters tbody tr").each(function(){
				if ($(this).hasClass(reportType)) {
					$(this).show();
				} else {
					$(this).hide();
				}
			});
			
			//after the report select is changed, show the footer (with sumbit button)
			if (reportType == "" || reportType == "inqTrackAndTrace") {
				$("#inputParameters tfoot tr").hide();
			} else {
				$("#inputParameters tfoot tr").show();
			}
		
		}
		
		(function () {
		   $('#trackAndTraceForm').submit(function (){
	           
	           //hide all error messages
	           $('.ui-error').hide();
	           
	           //show dialog
	           $('#download-dialog').dialog({
	              modal: true,
	              buttons: {
	                Ok: function() {
	                  $( this ).dialog( 'close' );
	                  $('input[name=goButton]').val('GO').prop('disabled', false);
	                }
	              }
	            });
	            
	            return true;
            });

            //initialize layout
            var r = '<%=it.getRequestType() %>';
            if (r == '') {
                r = $('#requestType').val();
            }
            controlInputs(r);

			//only show the rows with classes match the report select value
			$("select[name=requestType]").change(function(){
				controlInputs($(this).val());		
			});
			
		})();
	</script>

	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>