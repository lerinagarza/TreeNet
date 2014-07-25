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
	<title></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import = "com.treetop.controller.budget.InqBudget, 
com.treetop.utilities.html.DropDownDual,
com.treetop.utilities.html.DropDownSingle,
com.treetop.businessobjects.AccountString,
com.lawson.api.BUS100MIAddBudgetLines,
com.lawson.api.BUS100MIDelBudgetLines,
java.util.Vector,
com.treetop.utilities.html.HtmlSelect.DescriptionType
" %>

<%
    InqBudget ib = (InqBudget) request.getAttribute("inqBudget");
    if (ib == null) {
        ib = new InqBudget();
     }
 %>	
	
    <div class="clearfix">  
        <div style="float:left">
           <h1>Maintain Financial Budgets</h1>
           <div class="comment">
	           Only unlocked budgets are available<br>
	           New budgets may be added using BUS100
           </div>
        </div>
        <div style="float:right">
          <a href="CtlBudget?requestType=inqDepartment&environment=<%=ib.getEnvironment() %>" class="ui-button">
              View Department Definitions
          </a>
        </div>
    </div>
	
    <% if (!ib.getSubmit().equals("") && ib.getErrorMessage().equals("")) { %>
    <br>
      <div class="ui-comment">
      <%    String message = "";
            if (ib.getRequestType().equals("updBudget")) {
                message = "Upload Processed Successfully!"; 
            }
            if (ib.getRequestType().equals("updForecastMiss")) {
                message = "Forecast Adjusted Successfully!"; 
            } %>
            
        <h3><%=message %></h3>
        <table style="text-align:left">
            <tr>
                <th>Budget Number:</th>
                <td><%=ib.getBudgetNumber() %></td>
            </tr>
            <tr>
                <th>Budget Version:</th>
                <td><%=ib.getBudgetVersion() %></td>
            </tr>
            <tr>
                <th>Department:</th>
                <td><%=ib.getDepartment() %></td>
            </tr>
            <%  if (ib.getRequestType().equals("updBudget")) {  %>
            <tr>
                <th>Upload File:</th>
                <td><%=ib.getFilePath() %></td>
            </tr>
            <%  } %>
        </table>
      </div>
      <br>
    <% } %>
    
    
    <%  if (!ib.getErrorMessage().equals("")) { %>
        <%  String errorMessage = "";
            if (ib.getRequestType().equals("updBudget")) {
                errorMessage = "Error uploading budget";
            }
            if (ib.getRequestType().equals("updForecastMiss")) {
                errorMessage = "Error adjusting forecast";
            }
             %>
        <br>
        <div class="ui-error">
            <h3 style="color:#fff"><%=errorMessage %></h3>
            <%=ib.getErrorMessage() %>
        </div>
    
        <%  if (!ib.getBean().getInvalidAccountStrings().isEmpty()) { %>
            <h3 class="error">Invalid Account Strings</h3>
            <div class="comment error">No records were uploaded</div>
            <table class="styled">
                <tr>
                    <th>Dim 1</th>
                    <th>Dim 2</th>
                    <th>Dim 3</th>
                    <th>Dim 4</th>
                    <th>Dim 5</th>
                    <th>Dim 6</th>
                    <th>Dim 7</th>
                    <th>Error Message</th>
                </tr>
                <%  Vector invalidAccountStrings = ib.getBean().getInvalidAccountStrings();
                    for (int i=0; i<invalidAccountStrings.size(); i++) {
                        AccountString string = (AccountString) invalidAccountStrings.elementAt(i);
                 %>
                <tr>
                    <td><%=string.getDimension1().getAccountID() %></td>
                    <td><%=string.getDimension2().getAccountID() %></td>
                    <td><%=string.getDimension3().getAccountID() %></td>
                    <td><%=string.getDimension4().getAccountID() %></td>
                    <td><%=string.getDimension5().getAccountID() %></td>
                    <td><%=string.getDimension6().getAccountID() %></td>
                    <td><%=string.getDimension7().getAccountID() %></td>
                    <td><%=string.getError() %></td>
                </tr>
                 <% } %>
            </table>
            
            
        <%  } %>
        
        
        
        <%  if (!ib.getBean().getDelBudgetLines().isEmpty()) {
                boolean errors = false; 
                Vector dels = ib.getBean().getDelBudgetLines();
                for (int i=0; i<dels.size(); i++) {
                    BUS100MIDelBudgetLines del = (BUS100MIDelBudgetLines) dels.elementAt(i);
                    if (del.getResponse().startsWith("NOK")) {
                        errors = true;
                    }
                }
                if (errors) {       
         %>
        
            <h3 class="error">Budget Line Deletes</h3>
            <table class="styled">
                <tr>
                    <th>Dim 1</th>
                    <th>Dim 2</th>
                    <th>Dim 3</th>
                    <th>Dim 4</th>
                    <th>Dim 5</th>
                    <th>Dim 6</th>
                    <th>Dim 7</th>
                    <th>Error Message</th>
                </tr>
                <%  for (int i=0; i<dels.size(); i++) {
                        BUS100MIDelBudgetLines del = (BUS100MIDelBudgetLines) dels.elementAt(i);
                        if (del.getResponse().startsWith("NOK")) {
                 %>
                 <tr>
                    <td><%=del.getDimension1() %></td>
                    <td><%=del.getDimension2() %></td>
                    <td><%=del.getDimension3() %></td>
                    <td><%=del.getDimension4() %></td>
                    <td><%=del.getDimension5() %></td>
                    <td><%=del.getDimension6() %></td>
                    <td><%=del.getDimension7() %></td>
                    <td><%=del.getResponse() %></td>
                </tr>
                 <%         }
                        }
                    } %>
            </table>
        <%  } %>
        
         <%  if (!ib.getBean().getAddBudgetLines().isEmpty()) {
                boolean errors = false;
                Vector adds = ib.getBean().getAddBudgetLines();
                for (int i=0; i<adds.size(); i++) {
                    BUS100MIAddBudgetLines del = (BUS100MIAddBudgetLines) adds.elementAt(i);
                    if (del.getResponse().startsWith("NOK")) {
                        errors = true;
                    }
                } 
                if (errors) {
          %>
            <h3 class="error">Budget Line Adds</h3>
            <table class="styled">
                <tr>
                    <th>Dim 1</th>
                    <th>Dim 2</th>
                    <th>Dim 3</th>
                    <th>Dim 4</th>
                    <th>Dim 5</th>
                    <th>Dim 6</th>
                    <th>Dim 7</th>
                    <th>Error Message</th>
                </tr>
                <%  for (int i=0; i<adds.size(); i++) {
                        BUS100MIAddBudgetLines add = (BUS100MIAddBudgetLines) adds.elementAt(i);
                        if (add.getResponse().startsWith("NOK")) {
                 %>
                 <tr>
                    <td><%=add.getDimension1() %></td>
                    <td><%=add.getDimension2() %></td>
                    <td><%=add.getDimension3() %></td>
                    <td><%=add.getDimension4() %></td>
                    <td><%=add.getDimension5() %></td>
                    <td><%=add.getDimension6() %></td>
                    <td><%=add.getDimension7() %></td>
                    <td><%=add.getResponse() %></td>
                </tr>
                 <%         }
                        }
                    } %>
            </table>
        <%  } %>
    
    <%  } %>
    
    
	
	<h3 class="expandOpen ui-widget-header">Upload a Budget File</h3>
    <div class="collapse ui-widget-content">
    
	
	<form action="CtlBudget?requestType=updBudget&environment=<%=ib.getEnvironment() %>" method="post" enctype="multipart/form-data">
	<input type="hidden" name="environment" value="<%=ib.getEnvironment() %>">
    	
	<div class="row-fluid">
	   <div class="span3"></div>
	   <div class="span3">
    	<label for="budgetNumber">Budget Number</label>
    	<% Vector options = ib.buildDropDownBudget(); %>
    	<%=DropDownDual.buildMaster(options, 
                   "budgetNumber",    //name
                   "budgetNumber",    //id
                   "",             //cssClass
                   ib.getBudgetNumber(),             //selected value
                   "",             //default value
                   false,          //read only
                   DescriptionType.VALUE_ONLY) %>
        </div>
        <div class="span6">
        <label for="budgetNumber">Budget Version</label>         
       <%=DropDownDual.buildSlave(options, 
                   "budgetNumber",    //master id
                   "budgetVersion",    //name
                   "budgetVersion",    //id
                   "",             //cssClass
                   ib.getBudgetVersion(),             //selected value
                   "",             //default value
                   false,          //read only
                   DescriptionType.VALUE_DESCRIPTION) %>
         </div>
       </div>
       
       <div class="row-fluid">
         <div class="span3"></div> 
         <div class="span9">
           <label for="department">Department</label>
           <% Vector departments = ib.buildDropDownDepartments(); %>
           <%=DropDownSingle.buildDropDown(departments,
           "department",
           "department",
           ib.getDepartment(),
           "",
           false,
           "",
           DescriptionType.VALUE_ONLY) %>

         </div>
       </div>
       
       <div class="row-fluid">
         <div class="span3"></div> 
         <div class="span9">
           <input type="file" name="uploadFile">
         </div>
       </div>
       
       <div class="row-fluid">
         <div class="span3"></div>
         <div class="span9">
           <input type="submit" name="submit" value="Upload" class="ui-button processingDialog" >
         </div>
       </div>
	   
	   
	</form>
	
	</div>
	
	<br>
	
	<h3 class="expandOpen ui-widget-header">Adjust Budget for Forecast Miss</h3>
    <div class="collapse ui-widget-content">
    <form action="CtlBudget?requestType=updForecastMiss" method="post" id="forecast-miss">
    <input type="hidden" name="environment" value="<%=ib.getEnvironment() %>">
    <div class="row-fluid">
       <div class="span3"></div>
       <div class="span3">
        <label for="budgetNumber">Budget Number</label>
        <% options = ib.buildDropDownBudget(); %>
        <%=DropDownDual.buildMaster(options, 
                   "budgetNumber",    //name
                   "budgetNumber_miss",    //id
                   "",             //cssClass
                   ib.getBudgetNumber(),             //selected value
                   "",             //default value
                   false,          //read only
                   DescriptionType.VALUE_ONLY) %>
        </div>
        <div class="span6">
        <label for="budgetNumber">Budget Version</label>         
       <%=DropDownDual.buildSlave(options, 
                   "budgetNumber_miss",    //master id
                   "budgetVersion",    //name
                   "budgetVersion_miss",    //id
                   "",             //cssClass
                   ib.getBudgetVersion(),             //selected value
                   "",             //default value
                   false,          //read only
                   DescriptionType.VALUE_DESCRIPTION) %>
         </div>
       </div>
       
       <div class="row-fluid">
         <div class="span3"></div> 
         <div class="span9">
           <label for="department">Department</label>
           <% departments = ib.buildDropDownDepartments(); %>
           <%=DropDownSingle.buildDropDown(departments,
           "department",
           "department",
           ib.getDepartment(),
           "",
           false,
           "",
           DescriptionType.VALUE_ONLY) %>

         </div>
       </div>
       
       <div class="row-fluid">
         <div class="span3"></div> 
         <div class="span9">
           <label for="forecastMiss">Periods to Adjust</label>
           <select name="forecastMiss" id="forecastMiss">
             <option></option>
             <option value="DEC">December Forecast (3 mo act & 9 mo bal -> 4 mo act & 8 mo bal)</option>
             <%-- Option for April Forecast removed 2014-06-02 JH --%>
             <!-- <option value="APR">April Forecast (8 mo act & 4 mo bal -> 9 mo act & 3 mo bal)</option> -->
           </select>
         </div>
       </div>
       
       <div class="row-fluid">
         <div class="span3"></div>
         <div class="span9">
      
           <button id="preview" class="ui-state-default ui-button">Preview Forecast Adjustment</button>
         
           <input type="submit" id="submit-adjust-forecast"
                name="submit" value="Adjust Forecast" 
                class="processingDialog" style="display:none" >
           
           <a href="#" id="execute-update">Adjust Forecast (Update budgets)</a>
           
         </div>
       </div>
       
       <iframe id="download-target" src="" style="display:none"></iframe>
       <div id="download-dialog" style="display:none; text-align:center;" title="Processing...">
	       <h3>Forecast adjustments are being calculated.</h3>
	       <p>Your download will begin shortly.</p>
	       <img src="/web/Include/images/ajax-loader-bar.gif">
       </div>
       
       <div id="execute-dialog" style="display:none; text-align:center;" title="Adjust Forecast">
           <h3>You are about to adjust the forecast</h3>
           <p>Are you sure?  Changes cannot be undone.</p>
       </div>
       
       <div id="input-errors-dialog" style="display:none; text-align:center;" title="Adjust Forecast">
           <h3>Hold up!  You are missing some things...</h3>
           <p id="input-errors"></p>
       </div>
       
    </form>
	</div>
	
	<br>
	
	<h3 class="expandOpen ui-widget-header">Budget Upload Templates</h3>
    <div class="collapse ui-widget-content">
        <div class="center">
            <a href="CtlBudget?requestType=getUploadTemplate" class="ui-button">Download Template File</a>
        </div>
    </div>
	
	
	<script>
	
	   $('#execute-update').click(function (e){
	       e.preventDefault();
	       
	       $('#execute-dialog').dialog({
              modal: true,
              buttons: {
                Ok: function() {
                  $( this ).dialog( 'close' );
                  $('#submit-adjust-forecast').click();
                },
                Cancel: function() {
                  $( this ).dialog( "close" );
                }
              }
            });
	       
	       
	   });
	   $('#preview').click(function(e){
 
           e.preventDefault();
           var error = '';
           if ($('#forecast-miss select[name=budgetNumber]').val() == '') {
                error += 'Budget number cannot be empty<br>';
           }
           if ($('#forecast-miss select[name=budgetVersion]').val() == '') {
                error += 'Budget version cannot be empty<br>';
           }
           if ($('#forecast-miss select[name=department]').val() == '') {
                error += 'Budget department cannot be empty<br>';
           }
           if ($('#forecast-miss select[name=forecastMiss]').val() == '') {
                error += 'Periods to adjust cannot be empty<br>';
           }
           
           if (error == '') {
	           var parms = $('form#forecast-miss').serialize();
	           
	           var target = document.getElementById('download-target');
	           $("#download-dialog").dialog({
	              modal: true,
	              buttons: {
	                Ok: function() {
	                  $( this ).dialog( "close" );
	                }
	              }
	            });
	           target.src='CtlBudget?requestType=previewForecastMiss&' + parms;
           } else {
           
                $('#input-errors').html(error);
                $("#input-errors-dialog").dialog({
                  modal: true,
                  buttons: {
                    Ok: function() {
                      $( this ).dialog( "close" );
                    }
                  }
                });
           
           }
           
           

	   });
	</script>
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>