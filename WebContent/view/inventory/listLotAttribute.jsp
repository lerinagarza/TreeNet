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
<%@ page import = "
java.util.Vector,
java.math.BigDecimal,
com.treetop.app.inventory.InqInventory,
com.treetop.app.inventory.UpdAttribute,
com.treetop.app.inventory.UpdAttributeDetail,
com.treetop.businessobjectapplications.BeanInventory,
com.treetop.businessobjects.Attribute,
com.treetop.businessobjects.AttributeValue,
com.treetop.businessobjects.Inventory,
com.treetop.utilities.html.HTMLHelpers,
com.treetop.utilities.html.HTMLHelpersInput,
com.treetop.utilities.html.HTMLHelpersMasking,
com.treetop.utilities.html.HtmlSelect,
com.treetop.utilities.html.HtmlSelect.DescriptionType

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
Vector attributes = bean.getListAttributes();
Vector listLots = bean.getByItemVectorOfInventory();

UpdAttribute ua = (UpdAttribute) request.getAttribute("updViewBean");
if (ua == null) {
    ua = new UpdAttribute();
}


 %>

<h1>Chosen Lot Update Attributes</h1>

<div>
    <%=inqInv.buildSelectionCriteria() %>
</div>

<%  if (!bean.getReturnMessage().trim().equals("")) { %>
    <div class="ui-comment">
        <%=bean.getReturnMessage() %>
    </div>
<%  } %>

<%  if (!ua.getErrorMessage().trim().equals("")) { %>
    <div class="ui-error">
        <%=ua.getErrorMessage().trim() %>
    </div>
<%  } %>


<form name="listLots" action="/web/CtlInventoryNew?requestType=updLotAttribute" method="post">
<fieldset>
    <legend>Update Attributes</legend>
    
    
    <%=inqInv.buildParameterResendInputs() %>
    <input type="hidden" name="environment" value="<%=inqInv.getEnvironment() %>" />
   
    <table class="styled">
        
        <tr>
            <th>Attribute</th>
            <th colspan="6">Value <span style="color:#990000; font-weight:bold;">* = attribute value changed</span></th>
        </tr>
        
        <%  Vector details = ua.getAttributeDetails();
            for (int i=0; !attributes.isEmpty() && i<attributes.size(); i++) {
            AttributeValue a = (AttributeValue)attributes.elementAt(i);
            String error = "";
            //test for any errors
            for (int j=0; !details.isEmpty() && j<details.size(); j++) {
                UpdAttributeDetail detail = (UpdAttributeDetail) details.elementAt(j);
                if (detail.getAttributeID().equals(a.getAttribute())) {
                    if (!detail.getErrorMessage().trim().equals("")) {
                        error = "\"" + detail.getValueTo() + "\" is not valid";
                        break;
                    }
                }
            }
            
            
            int decimalPlaces = 0;
            try {
                decimalPlaces = Integer.parseInt(a.getDecimalPlaces());
            } catch (Exception e) {}
            String fieldType = a.getFieldType();
        %>
        <tr>
            <td style="vertical-align:top;">
                <b><%=a.getAttributeDescription() %></b>
                <input type="hidden" name="attributeDetail_r<%=i %>_attributeID" value="<%=a.getAttribute() %>" />
                <input type="hidden" name="attributeDetail_r<%=i %>_valueFrom" value="<%=a.getValue() %>" />
                <input type="hidden" name="attributeDetail_r<%=i %>_fieldType" value="<%=a.getFieldType() %>" />
                <input type="hidden" name="attributeDetail_r<%=i %>_decimalPlaces" value="<%=a.getDecimalPlaces() %>" />
            </td>
            
            <td>
            
            <%  if (fieldType.equals("numeric")) { 
                
            %>
                <input type="text" name="attributeDetail_r<%=i %>_valueTo"
                value="<%=HTMLHelpersMasking.maskBigDecimal(a.getValue(), decimalPlaces) %>"
                style="text-align:right;" />
                <%=a.getAttributeUOMDescription() %>
                
            
            
            <%  } else if (fieldType.equals("alpha")){ 
                    HtmlSelect select = new HtmlSelect();
                    select.setName("attributeDetail_r" + i + "_valueTo");
                    if (!a.getValue().trim().equals("")) {
                        select.setDefaultValue(null);
                        select.setSelectedValue(a.getValue());
                    } else {
                        select.setSelectedValue(a.getValue());
                    }
                    select.setDescriptionType(DescriptionType.VALUE_DESCRIPTION);
                    select.setOptions(a.getAttributeOptions());
            %>
            <%=select.toString() %>
            
            <%  } else if (fieldType.equals("date")){ %>
                <input type="text" name="attributeDetail_r<%=i%>_valueTo" value="<%=a.getValue() %>" />
            
            <%  } else if (fieldType.equals("text") && a.getAttribute().equals("LOT COMMENTS")){ %>
                <input type="text" name="attributeDetail_r<%=i%>_valueTo" size="60" value="<%=a.getValue() %>">
                    
                
            <%  } %>
            
            <span id="attributeDetail_r<%=i %>_valueToChanged" style="color:#990000; font-weight:bold; display:none;">*</span>
            </td>
            <td>
                <b><%=error %>&nbsp;</b>
            </td>
                <%  if (fieldType.equals("numeric")) {
                    BigDecimal min = null;
                    BigDecimal max = null;
                    try {
                        min = new BigDecimal(a.getLowValue());
                    } catch (Exception e) {}
                    try {
                        max = new BigDecimal(a.getHighValue());
                    } catch (Exception e) {}
                    if (min != null && max != null) { %>
                
                <td style="text-align:right;"><i>Range(</i></td>
                <td style="text-align:right;"><i><%=HTMLHelpersMasking.maskBigDecimal(min.toString(),decimalPlaces) %></td>
                <td style="text-align:center; padding:0 10px;"><i> to </i></td>
                <td style="text-align:left;"><i><%=HTMLHelpersMasking.maskBigDecimal(max.toString(),decimalPlaces) %></i></td>
                <td><i>)</i></td>
                    <% }
                    } else { %>
                    <td colspan="5">&nbsp;</td>
                    
                    <%} %>
                
        </tr>
        <%} %>
</table>
</fieldset>

<div class="clearfix" style="margin:2em 0 1em 0;">
    <div style="float:left">
        <input type="submit" name="getlist" value="Re-Select the Lots / Do Not Process" class="ui-button">
    </div>
    <div style="float:right">
        <input type="submit" name="submit" value="Process Update Attributes" class="ui-button">
    </div>
</div>

<h3>Choose which Lots to Process</h3>
<table class="styled full-width">  
   <tr>
	    <th>
	        <input type="checkbox" id="checkAll" checked="checked"/>
	    </th>
	    <th>
	       <label for="checkAll">Process</label>
	    </th>
	    
	    <th>Current Status</th>
	    <th>In Inventory</th>
	    <th>Facility</th>
	    <th>Warehouse</th>
	    <th>Item</th>
	    <th>Item Description</th>
	    <th>Lot</th>
	    <th>Lot Ref</th>   
	    <th>Quantity</th>
   </tr>
<%
   if (listLots != null &&
       listLots.size() > 0)
   {
     String showDesc = "N";
     if (!inqInv.getInqShowDescriptions().equals(""))
        showDesc = "Y";
     for (int x = 0; !listLots.isEmpty() && x < listLots.size(); x++)
     {
       Inventory i = (Inventory) listLots.elementAt(x);
%>   
   <tr>
    <td class="center">
        <input type="checkbox" checked="checked" name="attributeNumber_r<%=x %>_attributeNumber" value="<%=i.getAttributeNumber() %>" />
        <input type="hidden" name="attributeNumber_r<%=x %>_itemNumber" value="<%=i.getItemNumber() %>" />
        <input type="hidden" name="attributeNumber_r<%=x %>_lotNumber" value="<%=i.getLotNumber() %>" />
    </td>
    <td></td>
    
    
    <td class="center">&nbsp;
		<%
		     if(showDesc.equals("Y"))
		       out.println(InqInventory.returnStatusDescription(i.getStatus()));
		     else
		       out.println(i.getStatus());   
		%>    
    </td>
    
    
    <td class="center">
	    <%  //if the warehouse is empty, this lot is not in inventory
	    if (i.getWarehouse().trim().equals("")) { %>No<%} else { %>Yes<%}%>
    </td>
    
    
    <td><%= i.getFacility().trim() %>&nbsp;&nbsp;
<%
     if (showDesc.equals("Y"))
       out.println(i.getFacilityDescription().trim());
%>    
    </td>
    
    <td class="center">
        <%  //if the warehouse is empty, this lot is not in inventory
	    if (i.getWarehouse().trim().equals("")) { %>
	        ---
	    <%  } else {%>
	        <%= i.getWarehouse().trim() %>&nbsp;&nbsp;
	        <%  if (showDesc.equals("Y")){  out.println(i.getWarehouseDescription().trim());}%>    
	    <%  } %>
    </td>    
    
      
    <td><%= i.getItemNumber().trim() %></td>    
    <td><%= i.getItemDescription().trim() %></td> 
    <td><%= i.getLotNumber().trim() %></td>    
    <td><%= i.getLotRef1().trim() %>&nbsp;</td>  
    
    <%  //if the warehouse is empty, this lot is not in inventory, quantity is not available
    if (i.getWarehouse().trim().equals("")) { %>
    <td>
        ---
        </td>
    <%  } else {%>
    <td class="right">
        <%= i.getQuantityOnHand().trim() %>
        </td>      
    <%  } %>
       
   </tr>
<%
     }
   }
%> 
</table>

</form>


<script type="text/javascript">
        $(document).ready(function(){
            $('[name$="valueTo"]').bind('keyup change', function(){
                var name = $(this).attr('name');
                var origName = name.replace('To','From');

                var idChanged = name + 'Changed';
                
                var valueTo = $(this).val();
                var valueFrom = $('[name=' + origName + ']').val();
                
                var valueToNum = Number(valueTo.replace(',',''));
                var valueFromNum = Number(valueFrom.replace(',',''));
                
                if (valueFrom == valueTo || valueFromNum == valueToNum) {
                    $("#" + idChanged).hide();
                } else {
                    $("#" + idChanged).show();
                }
            });
            
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