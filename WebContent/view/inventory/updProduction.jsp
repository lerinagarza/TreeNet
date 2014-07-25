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
<%@ page import = "com.treetop.app.inventory.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Inventory/updProduction.jsp -----------------------//
// Author   :  Teri Walton       9/10/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
 // Bring in the Build View Bean.
 // Selection Criteria
// Vector listLots = new Vector();
 String returnMessage = "";
 UpdProduction updProd = new UpdProduction();
 Vector listLots = new Vector();
 ManufacturingOrder mo = new ManufacturingOrder();
 try
 {
    updProd = (UpdProduction) request.getAttribute("updViewBean");
    listLots = updProd.getListLots();
    mo = updProd.getUpdBean().getMoHeader();
 }
 catch(Exception e)
 {
    System.out.println("There is a error Loading the updProduction.jsp");
    request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg("There is a error Loading the updProduction.jsp"));
 }  
 String listTitle = "Short Cut Production Screen";
 String moReadOnly    = "N";
 String lotsReadOnly  = "N";
 String processedInfo = "Choose an Environment to work in.";
 String processedInfoDisplay = "comment";
 // Figure out what should be displayed on the screen
 String displayLots = "N";
  if (!updProd.getEnvironment().equals(""))
  {
     processedInfo = "Choose a Manufacturing Order and an Item:  Both Fields are Required.";
     listTitle = listTitle + " For Environment " + updProd.getEnvironment();
  }
  if ((!updProd.getGetMOButton().equals("") &&
      updProd.getGetMOError().equals("")) ||
      !updProd.getProcessLotsButton().equals(""))
  {
      displayLots = "Y";
      moReadOnly  = "Y";
      processedInfo = "Add Lot Number Information and Press Process Lots, this will push the information into a batch to be moved into Movex (M3)";
  }
  
  if (!updProd.getProcessLotsButton().equals("") &&
      updProd.getProcessLotsError().equals(""))
  {
     lotsReadOnly = "Y";
     processedInfo = "These Lots have been Processed (moved into a batch to be entered into M3(Movex), <br> You should be able to see the production on the Movex M3 System. <br>  ";
     processedInfoDisplay = "ui-comment";

  }
  
  if (!updProd.getProcessLotsButton().equals("") &&
      !updProd.getProcessLotsError().equals(""))
  {
     lotsReadOnly = "Y";
     processedInfo = "Transactions did not process successfully.  Review errors";
     processedInfoDisplay = "ui-error";

  }
  
  
  String moReadOnlyAttr = "";
  if (moReadOnly.equals("Y")) {
    moReadOnlyAttr = "readonly";
  }
  String lotsReadOnlyAttr = "";
  if (lotsReadOnly.equals("Y")) {
    lotsReadOnlyAttr = "readonly";
  }

%> 

    <title><%=listTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
    
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
		
		<div class="clearfix">
    		<h1 style="float:left">Shortcut Production</h1>
    		<%    if (moReadOnly.equals("Y")) { %>
	       	<a href="CtlInventoryNew?requestType=updProduction<%=updProd.buildParameterResend() %>" 
	       	   class="ui-select-again">Select Again</a>
	       	<%  } %>
		</div>
		
		
		<div class="<%=processedInfoDisplay %>"><%= processedInfo %></div>
		
        <%  if (!updProd.getGetMOError().equals("")) { %>  
        <div class="ui-error"><%= updProd.getGetMOError() %></div>
        <%  } %> 
        
        
        
        
        
        
  <form  name = "updProduction" action="/web/CtlInventoryNew?requestType=updProduction" method="post">  
  <%= HTMLHelpersInput.inputBoxHidden("defaultLocation", updProd.getDefaultLocation().trim()) %>
  <%= HTMLHelpersInput.inputBoxHidden("defaultWarehouse", updProd.getDefaultWarehouse().trim()) %>
  
  
  <%  //if the environment is set, pass it along  
      if (!updProd.getEnvironment().equals("")) { %>
  <%= HTMLHelpersInput.inputBoxHidden("environment", updProd.getEnvironment()) %>
  <%    } %>
  
  
    <%  if (!updProd.getLotPrefix().equals("")) { %>
      <%= HTMLHelpersInput.inputBoxHidden("lotPrefix", updProd.getLotPrefix()) %>
    <%  } %>

 <table>  

<%
 if (updProd.getEnvironment().trim().equals(""))
 {
    String displayDropDown = "";
    try
        {
            Vector buildList = new Vector();
            DropDownSingle dds = new DropDownSingle();
            dds.setDescription("PRD");
            dds.setValue("PRD");
            buildList.addElement(dds);
            dds = new DropDownSingle();
            dds.setDescription("TST");
            dds.setValue("TST");
            buildList.addElement(dds);
            
            displayDropDown = DropDownSingle.buildDropDown(buildList, "environment", "", "None", "N", "N");
        }
        catch(Exception e)
        {
            // Catch any problems, and do not display them unless testing
        }
%>  
  <tr>
   <td colspan="2" style="text-align:right; width:30%">
    <b>Environment</b>
   </td>
   <td><%= displayDropDown %></td>
   <td>&nbsp;</td>
  </tr>
  <tr>
   <td style="text-align:right">  
    <%= HTMLHelpers.buttonSubmit("", "GO to Next Screen") %>
   </td>
  </tr>   
  
<%  } else { %>  

  <tr>
   <td><label>Manufacturing Order Number</label></td>
   <td><%= HTMLHelpersInput.inputBoxNumber("manufacturingOrder", updProd.getManufacturingOrder(), "", 8, 7, "Y", moReadOnly) %>
        <span class="error"><%= updProd.getManufacturingOrderError() %></span>
    </td>
<%
   if (displayLots.equals("Y") &&
       mo != null &&
       mo.getLine() != null &&
       !updProd.getDefaultWarehouse().trim().equals("360"))
   {
%>  
   <td><label>Production Line</label></td>
   <td><%= HTMLHelpersMasking.addZerosToFrontOfField(mo.getLine(), 3) %></td>
<%
   }else{
%> 
   <td colspan="2">&nbsp;</td>
<%
   }
%>   
  </tr>
  
  
  <tr>
   <td><label>Item Number
<%
   if (displayLots.equals("Y"))
      out.println(" and Description");
%>
    </label>
   </td>
<%
  String classValue = "td03160102";
  if (displayLots.equals("Y"))
     classValue = "td04160102";
%>   
   <td class="<%= classValue %>" ><%= HTMLHelpersInput.inputBoxNumber("itemNumber", updProd.getItemNumber(), "", 11, 10, "Y", moReadOnly) %>&nbsp;&nbsp;
<%
    if (mo != null &&
        mo.getItem() != null &&
        mo.getItem().getItemDescription() != null)
        out.println(mo.getItem().getItemDescription());
 %>   
    <span class="error"><%= updProd.getItemNumberError() %>&nbsp;</span>
   </td>
 <%
   if (displayLots.equals("Y") &&
       mo != null &&
       mo.getLine() != null &&
       !updProd.getDefaultWarehouse().trim().equals("360"))
   {
%>  
   <td><b>Shift:</b></td>
   <td><%= HTMLHelpersMasking.addZerosToFrontOfField(mo.getShift(), 3) %></td>
<%
   }else{
%> 
   <td colspan="2">&nbsp;</td>
<%
   }
%>     
  </tr>
  
<%  if (!moReadOnly.equals("Y")) {
    //only display on the initial screen, not when showing the table of lots to produce
 %>
    <tr>
        <td colspan="4" class="comment">
        <br>
            Lot Series and Manufacture date are optional fields.  Values will be used as default values.
        </td>
    </tr>
 
  <tr>
    <td>
        <label>Lot Series</label>
    </td>
    <td>
        <input type="text" name="lotSeriesBeginning" value="<%= updProd.getLotSeriesBeginning() %>" <%=moReadOnlyAttr %>>
		 - 
		<input type="text" name="lotSeriesEnding" value="<%= updProd.getLotSeriesEnding() %>" <%=moReadOnlyAttr %>>
		<div class="error"><%= updProd.getLotSeriesBeginningError() %></div>
		<div class="error"><%= updProd.getLotSeriesEndingError() %></div>
		<div>
		  <% String checked = "checked";
			  if (updProd.getLotPrefix().equals("")) {
			      checked = "";
			  } 
		  %>
		  <label><input type="checkbox" name="lotPrefix" value="F" <%=checked %>>Use "F" lot prefix</label>
		</div>
    </td>
    <td colspan="4"></td>
  </tr>

  <tr>
    <td>
        <label>Manufacture Date</label>
    </td>
    <td>
        <input type="text" class="datepicker"
			name="manufactureDate"
			value="<%= updProd.getManufactureDate() %>"
			size="10"
			>
		<div class="error"><%= updProd.getManufactureDateError() %></div>
    </td>
    <td colspan="4"></td>
  </tr>
<%  } else { 
    //don't let the values be input, just send them along
%>
  <%= HTMLHelpersInput.inputBoxHidden("lotSeriesBeginning", updProd.getLotSeriesBeginning()) %>
  <%= HTMLHelpersInput.inputBoxHidden("lotSeriesEnding", updProd.getLotSeriesEnding()) %>
  <%= HTMLHelpersInput.inputBoxHidden("manufactureDate", updProd.getManufactureDate()) %>
<%  } %>  
  
<%
  if (displayLots.equals("N"))
  {
%>  
  <tr>
   <td colspan="4" style="text-align:center">  
   <br>
    <%= HTMLHelpers.buttonSubmit("getMOButton", "Get the MO Information") %>
   </td>
  </tr> 
<%
  }
  else
  {
%> 
<%--  No longer used...
  <tr>
   <td><label>Bill Of Lading Number:</label></td>
   <td colspan="3"><%= HTMLHelpersInput.inputBoxNumber("billOfLading", updProd.getBillOfLading(), "", 11, 10, "Y", lotsReadOnly) %>
	   <span class="error"><%= updProd.getBillOfLadingError() %></span>
   </td>
  </tr>
   --%>  
<%
  }
%>
  </table>
  
  

  
  
  
  
  
  
  
  
  
  
<%
//******************************************************************
// Section will only display on the LOT Page
  if (displayLots.equals("Y"))
  {
%>
  <table class="styled full-width">  
<%
   if (!updProd.getProcessLotsError().equals(""))
   {
   
%>  
  <tr>
   <td colspan="4" style="text-align:center">  
    <span class="error"><%= updProd.getProcessLotsError() %></span>
   </td>
  </tr> 
<%
   }
   if (updProd.getDefaultWarehouse().trim().equals("360"))
   {  
%>
  <tr>
   <th colspan="4">Move Pallets in the M3(Movex System) from Clement Pappas to OHL</th>  
  </tr>
  <tr> 
   <td style="text-align:center">
    <label title="Chep Pallets (Item Number 1005001001) to be Moved from Clement Pappas (01005) to OHL (00005)">Chep Pallets</label>
   </td>  
   <td style="text-align:center">
     <%= HTMLHelpersInput.inputBoxNumber("chepPallets", updProd.getChepPallets(), "", 5,5,"N",lotsReadOnly) %>
     <span class="error"><%= updProd.getChepPalletsError() %></span>
   </td>  
   <td style="text-align:center">
    <label title="Peco Pallets (Item Number 1000001055) to be Moved from Clement Pappas (01006) to OHL (00006)">
     Peco Pallets
    </label>
   </td>  
   <td style="text-align:center">
     <%= HTMLHelpersInput.inputBoxNumber("pecoPallets", updProd.getPecoPallets(), "", 5,5,"N",lotsReadOnly) %>
     <span class="error"><%= updProd.getPecoPalletsError() %></span>
   </td>  
  <tr>
<%
    }
%> 
  <tr>
   <th>Manufacture Date<br>(Format mm/dd/yyyy)</th>
   <th>Lot</th>
<%
   if (!updProd.getDefaultWarehouse().trim().equals("360"))
   {
%>   
   <th>Warehouse</th>
<%
   }
%>   
   <th>Location</th>
   <th>Quantity</th>  
  </tr>
<%
    if (listLots.size() > 0)
    {  
      for (int x = 0; x < listLots.size(); x++)
      {
        try
        {
          UpdProductionLot upl = (UpdProductionLot) listLots.elementAt(x);
%>  
  <tr>
   <td style="text-align:center">
    <acronym title="Date the lot number was Manufactured">
    <%-- <%= HTMLHelpersInput.inputBoxDateTypeOrChoose(("manufactureDate" + x), upl.getManufactureDate(), ("getManufactureDate" + x), "N",lotsReadOnly) %>--%>
	<input type="text" class="datepicker" 
		name="manufactureDate<%=x %>" 
		value="<%=upl.getManufactureDate() %>"
		size="10" 
		<%=lotsReadOnlyAttr %>>
     <span class="error"><%= upl.getManufactureDateError() %></span>
    </acronym>
   </td>  
   
   
   <td style="text-align:center">
    <acronym title="Enter Lot Number that has NOT been added to M3 (Movex)">
     <%= HTMLHelpersInput.inputBoxText(("lot" + x), upl.getLot(), "", 10,10,"N",lotsReadOnly) %>
     <span class="error"><%= upl.getLotError() %></span>
    </acronym>
   </td>
<%
   if (!updProd.getDefaultWarehouse().trim().equals("360"))
   {
%>     
   <td style="text-align:center">
    <acronym title="Must enter a valid M3 warehouse -- default information will come from the Manufacturing Order">
     <%= HTMLHelpersInput.inputBoxNumber(("warehouse" + x), upl.getWarehouse(), "", 3, 3,"N", lotsReadOnly) %> 
     <span class="error"><%= upl.getWarehouseError() %></span>
    </acronym>
   </td>
<%
   }
   else
   {
%>  
  <%= HTMLHelpersInput.inputBoxHidden(("warehouse" + x), upl.getWarehouse()) %>
<%
   }
%>   
   <td style="text-align:center">
    <acronym title="Must enter a valid M3 Location -- default information will come from the Manufacturing Order / OR the Item Warehouse Record">
     <%= HTMLHelpersInput.inputBoxText(("location" + x), upl.getLocation(), "", 7, 7,"N", lotsReadOnly) %> 
     <span class="error"><%= upl.getLocationError() %></span>
    </acronym>
   </td>
   <td style="text-align:center">
    <acronym title="Must enter a valid Number -- default information will come from Alternate Unit of Measure for Cases per Pallet">
     <%= HTMLHelpersInput.inputBoxNumber(("quantity" + x), upl.getQuantity(), "", 5, 5,"N", lotsReadOnly) %> 
     <span class="error"><%= upl.getQuantityError() %></span>
    </acronym>
   </td>   
  </tr> 
<% 
       }
       catch(Exception e)
       {
          System.out.println("Problem Processing Vector of Lots:" + e);
       }   
    }

    if (lotsReadOnly.equals("N"))
    {
%>  
  <tr>
   <td class="td0516" colspan="4" style="text-align:center">  
    <%= HTMLHelpers.buttonSubmit("processLotsButton", "Create Production for These Lots") %>
    
   </td>
   <td>
    <span id="quantity-total"></span>
   </td>
  </tr> 
<%
     } 
  }
%>
 </table> 
<%
  } // Display the Lots
// End of the Lot Section  
// ***********************************************************************  
 }// end of the IF Environment  
%>  
</form>

        <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>