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
<%@page import = "
java.util.Vector,
com.treetop.businessobjects.KeyValue, 
com.treetop.SessionVariables,
com.treetop.controller.sublot.UpdSubLotHeader,
com.treetop.controller.sublot.UpdSubLotDetail,
com.treetop.controller.sublot.InqSubLot,
com.treetop.businessobjects.TicketDetail" %>
	<title>Peach Receiving</title>
<%	
    UpdSubLotHeader h = (UpdSubLotHeader) request.getAttribute("updPeach");
    if (h == null) {
        h = new UpdSubLotHeader();
    }
    Vector tags = h.getBeanPeach().getTicket().getTagDetail();
    String context = request.getContextPath();

 %>

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


<div class="row-fluid">
    <div class="span12 right">
        <a href="<%=context %>/CtlSubLot/inqPeach" class="ui-select-again">List Tickets</a>
    </div>
</div>


<form id="updPeach"  action="<%=context %>/CtlSubLot/updPeach" method="post">

<div class="row-fluid">
    <div class="span12">
    <fieldset>
	    <legend>Load Details</legend>

	    
	    
	    <br>
	    
	    <div class="row-fluid">
	       <div class="span6">
	           
	           <table>
	               
	               <tr>
	                   <td>
	                       <label for="supplier">Supplier:</label>
	                   </td>
	                   <td>
	                       <%=InqSubLot.buildDropDownPeachSuppliers(h.getEnvironment(), h.getSupplierNumber()) %>
	                   </td>
	               </tr>
	               
	               <tr>
                       <td>
                            <label for="loadNumber">Load #:</label>
                       </td>
                       <td>
                            <input type="number" min="0" step="1" name="loadNumber" id="loadNumber" 
                            autocapitalize="off" autocorrect="off" autocomplete="off"
                            value="<%=h.getLoadNumber() %>">
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
	                       value="<%=h.getLotNumber() %>">
	                   </td>
	               </tr>
	               <tr>
                       <td>
                            <label for="supplier">Item Number:</label>
                       </td>
                       <td>
	                        <%=InqSubLot.buildDropDownPeachItems(h.getEnvironment(), h.getItemNumber()) %>
                       </td>
                   </tr>
                   
                   <tr>
                       <td>
                            <label for="receivingUser">User:</label>
                       </td>
                       <td>
                            <input type="text" name="receivingUser" id="receivingUser" 
                            autocapitalize="off" autocorrect="off" autocomplete="off"
                            value="<%=h.getReceivingUser() %>">
                       </td>
                   </tr>
                   
	           </table>
	       
	       </div>
	       
	    </div>
	    
	</fieldset>
    
    </div>
</div>

<br>


<div class="row-fluid">
    <div class="span12">
        <div class="ui-comment">
            Use the <button class="ui-button">&#8626;</button> button to copy the Grower Name from the row above.
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="span12">
    
    <table class="styled full-width">
        <thead>
		    <tr>
		        <th style="width:3em">Tag #</th>
		        <th>Grower Name</th>
		        <th width="65px;"></th>
		    </tr>
	    </thead>
	    <tbody>
		    <%  for (int i=0; i<h.getUnitCount(); i++) {
                String growerName = ""; 
		         if (i < tags.size()) {
		             TicketDetail td = (TicketDetail) tags.elementAt(i);
		             growerName = td.getGrowerName();
		         }
		    %>
		    <tr>
		    
		        <td class="center" style="vertical-align:middle;"><%=i+1 %></td>
		        
		        
		        <td class="grower" style="vertical-align:middle; z-index:1;">
		           <input type="text" class="typeahead" style="width:100%;"
		           autocapitalize="off" autocorrect="off" autocomplete="off"
		           name="grower<%=i %>" tabindex="<%=i+5 %>" value="<%=growerName %>">
		        </td>    
		        
		        
		        <td class="center" style="vertical-align:middle;">
		           <% if (i > 0) { %>
		           <button class="ui-button copy">&#8626;</button>
		           <%  } %>
		           <button class="ui-button delete" style="display:none;">X</button>
		           
		        </td>
		        
		        
		    </tr>
		    <%  } %>
	    </tbody>
    </table>
    
    </div>
</div>

<div class="row-fluid" 
style="position:fixed; top:100%; margin-top:-80px; height:80px; left:0; padding-top:20px; background-color:rgb(214,214,214); background-color:rgba(214,214,214,0.8); z-index: 999;">
    <div class="span12 center">
        <input type="submit" style="font-size:1.5em;" name="submitButton" class="ui-button" value="Submit">
    </div>
</div>



<script>
var growerList;
$.getJSON('<%=context %>/CtlSubLot/listGrowers', function (data) {
    growerList = data;
    $('input.typeahead').typeahead({

		name: 'growers',
		local: growerList,
		limit: 10
	});
})

</script>


<script>
    $('button.copy').bind('click', function (e){
        e.preventDefault();
        var thisRow = $(this).parents('tr');
        var prevRow = $(thisRow).prev();
        var input = $(prevRow).find('input.typeahead');
        var value = $(input).val();
        
        if (value.length > 0) {
           if ($(thisRow).find('input.typeahead').val().length == 0) {
                $(thisRow).find('input.typeahead').val( value );
                $(this).hide();
                window.scroll(0, $(thisRow).offset().top - 100);
                $(this).next().show();               
           }

        }
        

    });
    
    $('button.delete').bind('click', function (e){
        e.preventDefault();
        var thisRow = $(this).parents('tr');
        $(thisRow).find('input').val('');
                
        $(this).hide();
        $(this).prev().show(); 

    });
    
    $('input.typeahead').bind('blur', function(e){
    
        if ($(this).val().length > 0) {
            
            $(this).parents('tr').find('button.copy').hide();
            $(this).parents('tr').find('button.delete').show();
        
        } else {
        
            $(this).parents('tr').find('button.copy').show();
            $(this).parents('tr').find('button.delete').hide();
        
        }
        
    });
       
    
    $('form#updPeach').submit(function (e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode == 13) {
            return false;
        } else {
            return true;
        }
    
    });
    
    //Prevent submitting the form when the enter key is pressed
    document.onkeypress = function (e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode == 13) {
            e.preventDefault();
        }
    }
    

</script>


<div class="row-fluid" style="margin-top:2em;">            
    <div class="span12 ui-widget-content ui-corner-all" style="padding: 0 0 5px 5px;">
    <h3>Comments:</h3>
        <div class="center">
        <textarea name="comment" maxlength="500" style="height:5em; width:100%; max-width:40em;"><%=h.getComment() %></textarea>
        </div>
    </div>
</div>


<div style="margin-bottom:80px;">&nbsp;</div>

</form>

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>