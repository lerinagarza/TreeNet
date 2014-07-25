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
<%@ page import = "
java.util.Vector,
com.treetop.app.transaction.InqTransaction,
com.treetop.utilities.html.DropDownSingle,
com.treetop.utilities.html.HTMLHelpers,
com.treetop.utilities.html.HTMLHelpersInput"; %>
<%
//---------------- APP/transaction/inqTransactionError.jsp -----------------------//
// Author   :  Teri Walton      9/21/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Search Through Transaction Errors";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields drop down lists
 InqTransaction it = new InqTransaction();
 try
 {
    it = (InqTransaction) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",inqTitle);
//*****************************************************************************
%>

	<title><%= inqTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
<script type="text/javascript">
$(document).ready(function() {
    var dates = $( "#fromDate, #toDate" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        showOn: "both",
        buttonImage: "/web/Include/images/cal.gif",
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
});
</script>
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
		
		
		<h1>Transaction Errors</h1>
		<div class="ui-comment">Only 30 days of error information will be kept.</div>
		
		
		
		
<%  if (!it.getDisplayMessage().trim().equals("")) { %>      
      <div class="ui-error"><%= it.getDisplayMessage().trim() %></div>
<%  } %> 
		
		
<form  name="inqTransactionError" action="/web/CtlTransaction?requestType=listTransactionError" method="post">
  
  <fieldset>
    <legend>Select Transactions</legend>

    <div class="table-wrapper">
     <table>

      <tr>
          <td>
              <label title="Environment of Transaction">Environment:</label>
          </td>
          <td colspan="3">
<%
       Vector buildList = new Vector();
        DropDownSingle dds = new DropDownSingle();
            dds.setDescription("PRD");
            dds.setValue("PRD");
            buildList.addElement(dds);
            dds = new DropDownSingle();
        dds.setDescription("TST");
        dds.setValue("TST");
        buildList.addElement(dds);
%>          
        <%= DropDownSingle.buildDropDown(buildList, "inqEnvironment", "TST", "None", "N", "N") %>
          </td>
      </tr>   
      
            
      <tr>
          <td><label title="User who entered the Transaction">User:</label></td>
          <td colspan="3">
          <%= InqTransaction.buildDropDownUser("", it.getInqUser()) %>
          </td>
      </tr> 
      
         
      <tr>
   
          <td><label title="Warehouse used in the Transaction">Warehouse:</label></td>
          <td colspan="3">
              <%= InqTransaction.buildDropDownWarehouse("", it.getInqWhse()) %>
          </td>
      </tr>
              
      <tr>
          <td><label title="Item used in the Transaction">Item:</label></td>
          <td colspan="3">
           <%= InqTransaction.buildDropDownItem("", it.getInqItem()) %>
          </td>
      </tr>    
      
      <tr>
          <td><label title="Lot used in the Transaction">Lot:</label></td>
          <td colspan="3">
           <%= HTMLHelpersInput.inputBoxText("inqLot", it.getInqLot(), "Lot Number", 10,10,"N", "N") %>
          </td>
      </tr>
      
                             
      <tr>
       <td><label>Transaction Date:</label></td>
       <td class="right">
           <input type="text" 
           id="fromDate"
           name="inqFromDate" 
           value="<%=it.getInqFromDate() %>" size="10">
       </td>
       <td class="center"><label>TO</label></td>
       <td>
           <input type="text"  
           id="toDate"
           name="inqToDate" 
           value="<%=it.getInqToDate() %>" size="10">
       </td>
      </tr>
      <tr>
        <td colspan="4" class="center">
            <input type="submit" name="" value="GO - Retrieve List">
        </td>
      </tr>     
      </table>
     </div>
    </fieldset>
  </form>  
  <br>  

	<script>
	   $("form[name=inqTransactionError] input[type=submit]").button();
	</script>
	
	
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>