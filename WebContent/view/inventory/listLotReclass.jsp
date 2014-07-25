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
	<title>Lots Chosen for Reclassification</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import="
java.util.Vector,
com.treetop.app.inventory.InqInventory,
com.treetop.app.inventory.UpdInventory,
com.treetop.businessobjectapplications.BeanInventory,
com.treetop.businessobjects.Inventory,
com.treetop.utilities.html.HTMLHelpers,
com.treetop.utilities.html.HTMLHelpersInput
" %>
<%


InqInventory inqInv = (InqInventory) request.getAttribute("inqViewBean");
if (inqInv == null) {
    inqInv = new InqInventory();
}
BeanInventory bean = inqInv.getBeanInventory();
if (bean == null) {
    bean = new BeanInventory();
}
Vector listLots = bean.getByItemVectorOfInventory();



 %>


<h1>Chosen Lot Reclassification</h1>

<div>
    <%=inqInv.buildSelectionCriteria() %>
</div>

<%  if (!bean.getReturnMessage().equals("")) { %>
    <div class="ui-comment">
        <%=bean.getReturnMessage() %>
    </div>
<%  } %>
<form name="listLots" action="/web/CtlInventoryNew?requestType=updLotReclass" method="post">
	<input type="hidden" name="environment" value="<%=inqInv.getEnvironment() %>" />
	   
	<div class="clearfix" style="margin:1em 0;">
	    <div style="float:left">
	        <input type="submit" name="getList" value="Re-Select the Lots / Do Not Process" class="ui-select-again"
	         onclick="$(this).parents('form').attr('action','/web/CtlInventoryNew?requestType=inqLotReclass&<%= inqInv.buildParameterResend() %>');" />
	    </div>
	    <div style="float:right">
	        <input type="submit" name="goButton" value="Process Reclassify Transactions" class="ui-button">
	    </div>
	</div>
    


     
<table style="width:100%">  

   <tr>

	   <td>
	       <label title="Expiration Date -- Date which this product will EXPIRE">Expiration Date:</label>
	   </td>
	   <td>
	       <input type="text" class="datepicker" name="expirationDate" />
	   </td>
	   
	   <td style="width:2%">&nbsp;</td>
	   
	   <td>
	       <label title="Transaction Reason in M3 is used as the Hold Type to Group Lots that are on Hold">Transaction Reason(Hold Type):</label>
	   </td>
	   <td>
	       <%= UpdInventory.buildDropDownTransactionReason("", "") %>
	   </td>

   </tr> 
   
   
    
  <tr>
	  <td>
	      <label title="Follow Up Date -- Date which this product should be Followed up">Follow Up Date:</label>
	  </td>
	  <td>
	      <input type="text" class="datepicker" name="followUpDate" />
	  </td>
	  
	  <td style="width:2%" >&nbsp;</td>
	  
	  <td>
	  <label title="Lot Reference 2 in M3 is used as the Hold Number when product goes on hold, this can be used to group lots for reporting purposes.">Lot Ref 2(Hold Number):</label>
	  </td>
	  <td>
	   <%= HTMLHelpersInput.inputBoxText("lotRef2", "", "Lot Reference 2", 12, 12, "N", "N") %>
	  </td>
	  
  </tr> 
  
  
      
   <tr>
    <td><label title="Sales Date -- Date which this product should be Sold By">Sales Date:</label></td>
    <td>
        <input type="text" class="datepicker" name="salesDate" />
    </td>
    <td style="width:2%" >&nbsp;</td>
    <td><label title="Lot Comments are stored as a text box attribute on the lot">Lot Comments:</label></td>
    <td><%= HTMLHelpersInput.inputBoxText("remark", "", "Remark", 25, 60, "N", "N") %></td>
    <td style="width:2%" rowspan="4">&nbsp;</td>
   </tr> 
   
   
       
   <tr>
    <td><label title="Tagging Type - will be found in the Lot Master - Will be used as the Disposition Code (what to do with the product)">TaggingType(Disposition):</label></td>
    <td><%= UpdInventory.buildDropDownTaggingType("", "") %></td>
    <td style="width:2%" >&nbsp;</td>
    <td><label title="Owner Code - will be found in the Lot Master - Will be used as the Hold Reason">Owner Code (Hold Reason):</label></td>
    <td><%= UpdInventory.buildDropDownDisposition("", "") %></td>
    <td style="width:2%" rowspan="4">&nbsp;</td>
   </tr>    
      
  </table>
  
  
  
  
  
  <h3>Choose which Lots to Process</h3>
  
  <div class="comment">
    Reclassify(Process) Chosen (Checked) Lots from Status <%= InqInventory.returnStatusDescription(inqInv.getInqStatusFrom()) %>
     to Status <%= InqInventory.returnStatusDescription(inqInv.getInqStatusTo()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqStatusFrom", inqInv.getInqStatusFrom()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqStatusTo", inqInv.getInqStatusTo()) %>
     <%= HTMLHelpersInput.inputBoxHidden("inqStatus", inqInv.getInqStatus()) %>
  </div>
  
  <%    if (listLots.isEmpty()) { %>
  
  <div class="ui-comment">
    No lots found that match the selection criteria
  </div>
  
  <%    } else { %>
  
  <table class="styled full-width">  
    <th class="left">
        <input type="checkbox" id="checkAll" checked="checked" />&nbsp;Process
    </th>
    <th>Current Status</th>
    <th>Facility</th>
    <th>Warehouse</th>
    <th>Item</th>
    <th>Item Description</th>
    <th>Lot</th>   
    <th>Lot Ref 1</th>   
    <th>Quantity</th>
   </tr>
<%
   if (listLots != null &&
       listLots.size() > 0)
   {
     String showDesc = "N";
     if (!inqInv.getInqShowDescriptions().equals(""))
        showDesc = "Y";
     for (int x = 0; x < listLots.size(); x++)
     {
       Inventory i = (Inventory) listLots.elementAt(x);
%>   
   <tr>
    <td><%= HTMLHelpersInput.inputCheckBox((x + i.getLotNumber().trim()), i.getCheckedValue(), "N") %></td>
    <td>&nbsp;
<%
     if(showDesc.equals("Y"))
       out.println(InqInventory.returnStatusDescription(i.getStatus()));
     else
       out.println(i.getStatus());   
%>    
    </td>
    <td><%= i.getFacility().trim() %>&nbsp;&nbsp;
<%
     if (showDesc.equals("Y"))
       out.println(i.getFacilityDescription().trim());
%>    
    </td>
    <td><%= i.getWarehouse().trim() %>&nbsp;&nbsp;
<%
    if (showDesc.equals("Y"))    
      out.println(i.getWarehouseDescription().trim());
%>    
    </td>      
    <td><%= i.getItemNumber().trim() %></td>    
    <td><%= i.getItemDescription().trim() %></td> 
    <td><%= i.getLotNumber().trim() %></td>    
    <td><%= i.getLotRef1().trim() %>&nbsp;</td>  
    <td class="right"><%= i.getQuantityOnHand().trim() %></td>      
   </tr>
<%
     }
   }
%> 
    
   </table>
   <%   } %>
   </form>       
<script type="text/javascript">
$(document).ready(function(){
    $('#checkAll').click(function(){
        if ($(this).prop('checked')) {
            $('input[type=checkbox]').prop('checked',true);
        } else {
            $('input[type=checkbox]').prop('checked',false);
        }
    });
     
 });
 </script>  

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>