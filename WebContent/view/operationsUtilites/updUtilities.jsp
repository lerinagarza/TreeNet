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

<%@page import = "com.treetop.controller.operationsUtilities.InqOperationsUtilities, java.util.Vector" %>    

<%  Vector types = InqOperationsUtilities.getUtilitiesTypes(); 
    Vector units = InqOperationsUtilities.getUtilitiesUnits();
    Vector days = InqOperationsUtilities.getDaysOfWeek();
     %>
    <style>
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
    </style>

    <h1>Record Utilities Usage</h1>

    <div class="table-wrapper">    
        <form name="inquiry">
	        <input type="hidden" name="environment" value="<%=environment %>">
	        <label for="warehouse">Warehouse:
	            <%=InqOperationsUtilities.buildDropDownWarehouse("")%>
	       </label>
	      
	       <br>
	      
	       <label for="week">Week of:
	           <input id="week" type="text" name="weekBeginningDate" class="datepicker">
	       </label>
	       <br>
	       <div style="display:none">
	           Fiscal&nbsp;Week:&nbsp;<span id="fiscalWeek"></span>
	       </div>
	       <br>
       </form>
       
       <div id="entry" style="display:none;">
           <form name="update" method="post" action="#">
           <table class="styled">
               <tr>
                   <th colspan="2">
                       Date
                   </th>
                   <% for (int i=0; i<types.size(); i++) { %>
                   <th>
                       <span data-type="<%=types.elementAt(i) %>"><%=types.elementAt(i) %> (<%=units.elementAt(i) %>)</span>
                   </th>
                   <%   } %>
               </tr>
               <tr>
                   <td colspan="2">Rate ($/Unit)</td>
                   <% for (int i=0; i<types.size(); i++) { %>
                   <td class="right">
                       <input type="number" step=".000001" style="width:7em;"
                        data-rate data-type="<%=types.elementAt(i) %>">
                   </td>
                   <% } %>
               </tr>
               <tr>
                   <th colspan="<%=types.size() + 1 %>" style="font-size:.25em">&nbsp;</th>
               </tr>
               
               
               <%  for(int j=0; j<days.size(); j++) { %>  
               <tr>
                   <td data-day-name="<%=days.elementAt(j) %>"><%=days.elementAt(j) %></td>
                   <td class="center"></td>
                   <% for (int i=0; i<types.size(); i++) { %>
                   <td class="right">
                       <input type="number" pattern="[0-9]*" style="width:7em;""
                        data-day="<%=days.elementAt(j) %>" data-type="<%=types.elementAt(i) %>">
                   </td>
                   <%  } %>   
               </tr>  
               <%  } %>
               
                 
               <tr class="sub-total">
                   <td colspan="2">Total Units</td>
            <%  for (int i=0; i<types.size(); i++) { %>
                   <td>
                       <input class="bold" type="number" pattern="[0-9]*" style="width:7em;" readonly
                        data-total data-type="<%=types.elementAt(i) %>">
                   </td>
            <%   } %>
               </tr>
               <tr class="sub-total">
                   <td colspan="2">Total Dollars</td>
            <%  for (int i=0; i<types.size(); i++) { %>
                   <td>
                       <input class="bold" type="number" pattern="[0-9]*" style="width:7em;" readonly
                        data-total-dollars data-type="<%=types.elementAt(i) %>">
                   </td>
            <%   } %>
               </tr>
           </table>
           </form>
          <br>

          <input type="submit" id="save" class="ui-add" value="Save">
          <br>
          <span id="success" 
              style="display:none; 
              color:#26BC4F; 
              font-size:14pt; 
              font-weight:bold">
              Success!
          </span>
          <div id="error" class="ui-error" style="display:none; max-width:350px; width:80%; margin:0 auto;"></div>
          <span id="saving" class="comment"
              style="display:none;">
              Saving...
          </span>

        </div>
    
        
    </div>
    
    
    
    <script>
       
       //global data object
       var data = {};
       var unsavedData = false;
       function getData() {
           
           $.ajax({
               type: 'POST',
               url: '/web/CtlOperationsUtilities?requestType=dtlUtilities',
               data: $('form[name=inquiry]').serialize(),
               dataType: 'json'
           })
           .done(function (rtnData) {
               data = rtnData;
           })
           .fail(function (jqXHR, textStatus, errorThrown) {
               var trimmedError = jqXHR.responseText.substring(11, jqXHR.responseText.length);
               
               $("#error .ui-error-text").html(trimmedError);
               $("#error").fadeIn();
           })  
           .always(function () {
               showEntryForm();
           });
       
       }
       
       function displayColumn(type) {
           $('[data-type="' + type + '"]').parent().show();
       }
       function hideAllColumns() {
           $('[data-type]').parent().hide();
       }
       
       function updateFormData() {
       
           hideAllColumns();
           
           $("#fiscalWeek").html(data.fiscalWeek).parent().show();
           
           for (var type in data.rate) {
            $("input[data-rate][data-type='" + type + "']").val(data.rate[type]);
            displayColumn(type);
           }

           for (var day in data.usage) {
               for (var type in data.usage[day]) {
                    var usage = data.usage[day];
                    $("input[data-day='" + day + "'][data-type='" + type + "']").val(Math.round(usage[type]));
               }
           }
           
           $(data.calendar).each(function (i, day){
               $("[data-day-name='" + day.day + "']")
                   .html(day.dayShort)
                   .next()
                   .html(day.dateShort);
           });

           for (var type in data.rate) {
               calculateTotal(type);
               calculateDollars(type);
           }
       }
       
       function showEntryForm() {

           $("#entry").fadeOut('fast', updateFormData);
           $("#entry").find("input[type=submit]").removeClass("ui-state-highlight");
           $("#entry").slideDown();
           
       }
       
       function calculateTotal(type) {
           var total = Number(0);
           var elems = $("input[data-day][data-type='" + type + "']");
           $("input[data-day][data-type='" + type + "']").each(function(){
             total += Number($(this).val());
           });
           
           $("input[data-total][data-type='" + type + "']").val(total);
       }
       
       function calculateDollars(type) {
           var units =  $("input[data-total][data-type='" + type + "']").val(),
               rate = $("input[data-rate][data-type='" + type + "']").val(),
               dollars = units * rate;
               
           $("input[data-total-dollars][data-type='" + type + "']").val(dollars.toFixed(2));
       }
       
       
       function updateUsage() {
           unsavedData = true;
           $("#save").addClass("ui-state-highlight");
           var day = $(this).data("day"),
               type = $(this).data("type"),
               amount = $(this).val();
           
           if (isNaN(amount)) {
               $(this).val("");
           } else {
               data.usage[day][type] = amount;
               calculateTotal(type);
               calculateDollars(type);
           }
           
           
       }

       function updateRate() {
           unsavedData = true;
           $("#save").addClass("ui-state-highlight");
           var type = $(this).data("type"),
               amount = $(this).val();
           if (isNaN(amount)) {
               $(this).val("");
           } else {
               data.rate[type] = amount;
               calculateDollars(type);
           }
           
           
       }

       function saveHandler() {
       
           if (unsavedData) {
               $("#error").hide();
               $("#saving").fadeIn('fast',saveData);
           }
       
       }

       function saveData(){
           $.ajax({
               type: 'POST',
               url: '/web/CtlOperationsUtilities?requestType=updUtilities',
               data: $('form[name=inquiry]').serialize() + '&json=' + JSON.stringify(data),
               dataType: 'json'
           })
           .done(function (rtnData) {
               unsavedData = false;
               
               $("#save").removeClass("ui-state-highlight");
               $("#saving").hide();
               $("#success").fadeIn().delay(2000).fadeOut();
               
           })
           .fail(function (jqXHR, textStatus, errorThrown) {

               $("#saving").hide();
               
               var trimmedError = jqXHR.responseText.substring(11, jqXHR.responseText.length);
               
               $("#error .ui-error-text").html(trimmedError);
               $("#error").fadeIn();
           });

       }
       

       $(document).ready(function(){
           $("#week")
               // force week to start on Monday
                .datepicker( "option", "firstDay", 1 )
               // allow only Mondays to be selected
                .datepicker("option", "beforeShowDay", function(date){ return [date.getDay() == 1,""]}); 

           $("#week, #warehouse").change( function () {
               if ($("#week").val() !== "" && $("#warehouse").val() !== "" ) {
                   
                   if (!unsavedData || confirm("You have unsaved data.\nClick [OK] to continue without saving.")) {
                       $("#error").hide();
                       getData();
                       
                   }
                   
                   
               }
            });

           $("input[data-day][data-type]").change(updateUsage);
           $("input[data-rate]").change(updateRate);
           
           $("#save").click(saveHandler);
           
       });


       </script>
    
        <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>