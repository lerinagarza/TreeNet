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
	<title>Mass Update Lot Attributes</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>

<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<%@ page import = "
java.util.Vector,
com.treetop.app.inventory.InqInventory,
com.treetop.utilities.html.HTMLHelpersInput,
com.treetop.utilities.html.HTMLHelpers,
com.treetop.utilities.html.DropDownDual,
com.treetop.utilities.html.HtmlSelect.DescriptionType
" %>
<%
InqInventory ii = (InqInventory) request.getAttribute("inqViewBean");
if (ii == null) {
    ii = new InqInventory();
}
Vector dualDD = InqInventory.getListDualDropDownFacilityWarehouse(ii.getFacilityFilter());
 %>
<form  name = "inqInventory" action="/web/CtlInventoryNew?requestType=listLotAttribute" method="post">
<input type="hidden" name="environment" value="<%=ii.getEnvironment() %>" />

<h1>Mass Update Lot Attributes</h1>

      
<% if (!ii.getDisplayMessage().trim().equals("")) { %>      
<div class="ui-comment">
    <%= ii.getDisplayMessage().trim() %>
</div>
<% } %> 

<fieldset>
    <legend>Selection Criteria</legend>

   
    <table class="styled full-width">
    
        <tr>    
            <td>
                <label>Lot Status:</label>
            </td>
            <td>
                <%= InqInventory.buildDropDownStatusList(ii.inqStatus) %>
            </td>
        </tr>
        
        <tr>
            <td>
                <label>Show Descriptions<br>(Status, Facility, Warehouse):</label>
            </td>
            <td>
            <%= HTMLHelpersInput.inputCheckBox("inqShowDescriptions", ii.getInqShowDescriptions(), "N") %>
            </td>
        </tr>
        <tr>
            <td>
                <label>Facility:</label>
            </td>
            <td>
                <%=DropDownDual.buildMaster(dualDD, "inqFacility", "inqFacility", "", ii.getInqFacility(), "*all", false, DescriptionType.VALUE_DESCRIPTION) %>
            </td>
        </tr>
        <tr>
            <td>
                <label>Warehouse:</label>
            </td>
            <td>
                <%=DropDownDual.buildSlave(dualDD, "inqFacility", "inqWhse", "inqWhse", "", ii.getInqWhse(), "*all", false, DescriptionType.VALUE_DESCRIPTION) %>
            </td>
        </tr> 
        <tr>
            <td><label>Item Type:</label></td>
            <td>
            <%= InqInventory.buildDropDownItemType(ii) %> 
            </td>
        </tr>
        <tr>
            <td><label>Item Number:</label></td>
            <td>
            <%= HTMLHelpersInput.inputBoxText("inqItem", ii.getInqItem(), "Item Number", 15, 15, "N", "N") %>
            &nbsp;&nbsp;<span class="error"><%= ii.getInqItemError() %></span>
            </td>
        </tr>   
        <tr>
            <td><label>Lot Ref 1:</label></td>
            <td>
            <%= HTMLHelpersInput.inputBoxText("inqLotRef1", ii.getInqLotRef1(), "Lot Reference 1", 12, 12, "N", "N") %>
            </td>
        </tr>
        <tr>
            <td><label>Lot Ref 2:</label></td>
            <td>
            <%= HTMLHelpersInput.inputBoxText("inqLotRef2", ii.getInqLotRef2(), "Lot Reference 2", 12, 12, "N", "N") %>
            </td>
        </tr>
        <tr>
            <td><label>Owner Code:</label></td>
            <td> 
            <%= InqInventory.buildDropDownOwner(ii.getInqOwnerCode().trim(), "N") %> 
            </td>
        </tr>
        <tr>
            <td><label>Tagging Type</label></td>
            <td> 
            <%= InqInventory.buildDropDownTaggingType(ii.getInqTaggingType().trim(), "N") %> 
            </td>
        </tr>
<%
  // Taken off of inquiry for now -- until requested again
  //  6/26/13 - TWalton
  if (0 == 1)
  { 
%>        
        <tr>
            <td>
             <label title="Will bring back anything with these characters in it">Hold Comments:</label>
            </td>
            <td>
            <%= HTMLHelpersInput.inputBoxText("inqRemark", ii.getInqRemark(), "Remark", 60, 60, "N", "N") %>
            </td>
        </tr>
<%
  }
%>        
        <tr>
            <td colspan = "2">
                <table class="table00" cellspacing="0" style="width:100%">
                    <tr>
                        <td class="td0520" style="text-align:center" colspan = "5">
                           <b>Choose Lot Numbers</b>
                        </td>
                    </tr>  
                    <tr>
                        <td style="text-align:center" colspan = "2">
                           <b>Specific Lots</b>
                        </td>
                        <td style="text-align:center" rowspan="6" style="width:4%">
                           <b>*OR*</b>
                        </td>
                        <td style="text-align:center" colspan = "2">
                           <b>Range of Lots</b>
                        </td>
                    </tr>                        
                    <tr>
                        <td>
                           <label>Lot 1:</label>
                        </td>
                        <td>
                            <%= HTMLHelpersInput.inputBoxText("inqLot1", ii.getInqLot1(), "Lot Number 1", 15, 15, "N", "N") %>
                            &nbsp;&nbsp;<span class="error"><%= ii.getInqLot1Error() %></span>
                        </td>
                        <td><label>From:</label></td>
                        <td>
                           <%= HTMLHelpersInput.inputBoxText("inqLotFrom", ii.getInqLotFrom(), "From Lot Number", 15, 15, "N", "N") %>
                        </td>
                    </tr>          
                    <tr>
                        <td><label>Lot 2:</label></td>
                        <td>
                            <%= HTMLHelpersInput.inputBoxText("inqLot2", ii.getInqLot2(), "Lot Number 2", 15, 15, "N", "N") %>
                            &nbsp;&nbsp;<span class="error"><%= ii.getInqLot2Error() %></span>
                        </td>
                        <td>
                           <label>To:</label>
                        </td>
                        <td>
                           <%= HTMLHelpersInput.inputBoxText("inqLotTo", ii.getInqLotTo(), "To Lot Number", 15, 15, "N", "N") %>
                        </td>
                    </tr>                    
                    <tr>
                        <td>
                           <label>Lot 3:</label>
                        </td>
                        <td>
                         <%= HTMLHelpersInput.inputBoxText("inqLot3", ii.getInqLot3(), "Lot Number 3", 15, 15, "N", "N") %>
                        &nbsp;&nbsp;<span class="error"><%= ii.getInqLot3Error() %></span>
                        </td>
                    </tr>                    
                    <tr>
                        <td><label>Lot 4:</label></td>
                        <td>
                        <%= HTMLHelpersInput.inputBoxText("inqLot4", ii.getInqLot4(), "Lot Number 4", 15, 15, "N", "N") %>
                        &nbsp;&nbsp;<span class="error"><%= ii.getInqLot4Error() %></span>
                        </td>
                    </tr>     
                    <tr>
                        <td><label>Lot 5:</label></td>
                        <td>
                        <%= HTMLHelpersInput.inputBoxText("inqLot5", ii.getInqLot5(), "Lot Number 5", 15, 15, "N", "N") %>
                        &nbsp;&nbsp;<span class="error"><%= ii.getInqLot5Error() %></span>
                        </td>
                    </tr>                        
                </table>
            </td>
        </tr>
        <tr>
            <td style="text-align:center" colspan="5">
                <input type="submit" name="getList" value="Go - Retrieve Lots" class="ui-button">
            </td>
        </tr>      
    </table>  
</fieldset>
</form>  

<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>