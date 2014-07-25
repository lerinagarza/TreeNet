<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.coa.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanCOA" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>

<%


//---------------- APP/COA/updateCOA.jsp -----------------------//
// Prototype:  Charlena Paschen  06/04/03 (jsp)
// Author   :  Teri Walton       11/05/03 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//  9/5/07     TWalton 		 Rewrite with Movex  
// 11/2/09     TWalton       Added Information for Distribution Order
//------------------------------------------------------------//
  String errorPage = "COA/inqCOA.jsp";
  String listTitle = "Select COA Pallet - Resource Idents";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
  String coaType = "CO";
  String comments = "";
  UpdCOA updCOA = null;
  String df3 = "0";
  try
  {
     updCOA = (UpdCOA) request.getAttribute("updViewBean");
     if (updCOA.getEnvironment().equals("TST"))
	   listTitle = listTitle + " TST Environment";
     if (updCOA.getRequestType().equals(""))
	   updCOA.setRequestType("goToUpdate");
	 if (!updCOA.getCoaType().trim().equals(""))
	    coaType = updCOA.getCoaType();
	 request.setAttribute("listSequence", updCOA.getListAttributeSequence());
	 request.setAttribute("listAttributes", updCOA.getListAttributeID());
	 request.setAttribute("listLots", updCOA.getListLots());
	 request.setAttribute("coaType", coaType);
	 com.treetop.businessobjectapplications.COADetailAttributes aa = (com.treetop.businessobjectapplications.COADetailAttributes)updCOA.getReturnBean().getListCOADetailAttributes().elementAt(0);
	 comments = aa.getItemComment();
	 df3 = aa.getDateFormat();	
	 if (df3 == null || df3.trim().equals(""))
	    df3 = "0";
  } catch(Exception e) {
    System.out.println("does it catch an exception?" + e);
  } 

//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",listTitle);
//*****************************************************************************
  int imageCount = 2;
  int expandCount = 0; 
%>
<html>
 <head>
   <title><%= listTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
   <%= JavascriptInfo.getCheckTextareaLength() %>
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
   String formName = "updateCOAs";
   request.setAttribute("formName", formName);
%>
<form  name = "<%= formName %>" action="/web/CtlCOANew?requestType=update" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("coaType", coaType) %>
  <%= HTMLHelpersInput.inputBoxHidden("environment", updCOA.getEnvironment()) %>
<table style="width:100%">
 <tr>
  <td>
<%
  if (coaType.equals("LOT"))
  { // Lot COA Type
     Lot lotObject = new Lot();  
     try
     {
    	lotObject = (Lot) ((AttributeValue) updCOA.getReturnBean().getListAttrValues().elementAt(0)).getLotObject();
     }catch(Exception e)
     {}
%>   
  <table class="table00" cellspacing="0">
   <%= HTMLHelpersInput.inputBoxHidden("inqLot", lotObject.getLotNumber() ) %>
   <tr class="tr02">
    <td style="width:1%">&nbsp;</td>
    <td class="td0414">
     <b>
      Lot Number:&nbsp;<%= lotObject.getLotNumber().trim() %>&nbsp;&nbsp; -- &nbsp;&nbsp; 
      Item Number: <%= lotObject.getItemNumber() %> - <%= lotObject.getItemDescription().trim() %>&nbsp;
     </b>
    </td>
    <td style="width:1%">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td>&nbsp;</td>
    <td>
     <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
      <tr class="tr00">
       <td class="td04140402" style="width:20%"><b>Attribute Model:</b></td>
       <td class="td04140402" style="width:27%">
        <%= lotObject.getAttributeModel().trim() %><br><%= lotObject.getAttributeModelDescription() %>      
       </td>
<%
 // Comment out the Comment Section
//       <td class="td04140402" style="width:20%" rowspan="3"><b>Lot Comments: </b></td>
//      <td class="td04140402" rowspan="3">
//        <%= HTMLHelpersInput.inputBoxTextarea("lineComment", comments, 5, 40, 135, "N")  
//       </td>
%>       
      </tr>
<%
  // Row 2
%>
      <tr class="tr00">
       <td class="td04140102"><b>Basic Unit of Measure:</b></td>
       <td class="td04140102"><%= lotObject.getBasicUnitOfMeasure() %>&nbsp;</td>
      </tr>   
<%
  // Row 3
%>
      <tr class="tr00">
       <td class="td04140102"><b>Manufacture Date: </b></td>
       <td class="td04140102">
        <%= com.treetop.app.coa.BuildCOA.convertDate(lotObject.getManufactureDate(), new Integer(df3)) %>    
       </td>
      </tr>  
<%
  // Row 4
%>
      <tr class="tr00">
       <td class="td04140102"><b>Expiration Date: </b></td>
       <td class="td04140102">
        <%= com.treetop.app.coa.BuildCOA.convertDate(lotObject.getExpirationDate(), new Integer(df3)) %>    
       </td>
      </tr>               
     </table>
    </td>
   </tr>
  </table>  
<%
  } // end if COAtype = LOT
//-----------------------------------------------------------------------------------------------------
  if (coaType.equals("DO"))
  { // DISTRIBUTION ORDER
     DistributionOrderLineItem dolm = new DistributionOrderLineItem();  
     try
     {
    	dolm = (DistributionOrderLineItem) updCOA.getReturnBean().getListDOLineItems().elementAt(0);
     }catch(Exception e)
     {}
%>   
  <table class="table00" cellspacing="0">
   <%= HTMLHelpersInput.inputBoxHidden("inqDistributionOrder", dolm.getOrderNumber() ) %>
   <%= HTMLHelpersInput.inputBoxHidden("lineNumber", dolm.getLineNumber() ) %>
   <%= HTMLHelpersInput.inputBoxHidden("lineSuffix", dolm.getSuffix() ) %>
   <tr class="tr02">
    <td style="width:5%">&nbsp;</td>
    <td class="td0414">
     <b>
      Distribution Order Number:&nbsp;<%= dolm.getOrderNumber().trim() %>&nbsp;&nbsp; -- &nbsp;&nbsp; 
      Item Number: <%= dolm.getItemClass().getItemNumber() %> - <%= dolm.getItemClass().getItemDescription() %>&nbsp;&nbsp; -- &nbsp;&nbsp;
      Attribute Model: <%= dolm.getItemClass().getAttributeModel() %>
     </b>
    </td>
    <td style="width:5%">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td>&nbsp;</td>
    <td>
     <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
      <tr class="tr00">
       <td class="td04140902" rowspan="3" style="width:3%">&nbsp;</td>
       <td class="td04140402" style="width:20%"><b>From Facility:</b></td>
       <td class="td04140402" style="width:27%">
        <%= dolm.getFromWarehouse().getFacility().trim() %>&nbsp;&nbsp;<%= dolm.getFromWarehouse().getFacilityDescription() %>   
       </td>
       <td class="td04140402" style="width:20%"><b>To Facility:</b></td>
       <td class="td04140402">
        <%= dolm.getToWarehouse().getFacility().trim() %>&nbsp;&nbsp;<%= dolm.getToWarehouse().getFacilityDescription() %>
       </td>
      </tr>
<%
  // Row 2
%>      
   <tr class="tr00">
    <td class="td04140102"><b>From Warehouse:</b></td>
    <td class="td04140102">
     <%= dolm.getFromWarehouse().getWarehouse().trim() %>&nbsp;&nbsp;<%= dolm.getFromWarehouse().getWarehouseDescription() %>
    </td>
    <td class="td04140102"><b>To Warehouse:</b></td>
    <td class="td04140102">
     <%= dolm.getToWarehouse().getWarehouse().trim() %>   &nbsp;&nbsp;<%= dolm.getToWarehouse().getWarehouseDescription() %>
    </td>
   </tr>
<%
 // Row 4
%>      
   <tr class="tr00">
    <td class="td04140102"><b>Ship Date:</b></td>
    <td class="td04140102">
     <%= BuildCOA.convertDate(dolm.getShipDate(), new Integer(df3)) %> &nbsp;     
    </td>
    <td class="td04140102"><b>Receiving Date:</b></td>
    <td class="td04140102">
     <%= BuildCOA.convertDate(dolm.getReceiveDate(), new Integer(df3)) %> &nbsp;   
    </td>
   </tr>
  </table>  
<%
   }
//--------------------------------------------------------------------------------------- 
   if (coaType.equals("CO"))
   {
   // SALES ORDER
     SalesOrderLineItem solm = new SalesOrderLineItem();  
     try
     {
    	solm = (SalesOrderLineItem) updCOA.getReturnBean().getListSOLineItems().elementAt(0);
     }catch(Exception e)
     {}
%>
  <table class="table00" cellspacing="0">
   <%= HTMLHelpersInput.inputBoxHidden("inqSalesOrder", solm.getOrderNumber() ) %>
   <%= HTMLHelpersInput.inputBoxHidden("lineNumber", solm.getLineNumber() ) %>
   <%= HTMLHelpersInput.inputBoxHidden("lineSuffix", solm.getSuffix() ) %>
   <tr class="tr02">
    <td style="width:5%">&nbsp;</td>
    <td class="td0414">
     <b>
      Customer Order Number:&nbsp;<%= solm.getOrderNumber().trim() %>&nbsp;&nbsp; -- &nbsp;&nbsp; 
      Item Number: <%= solm.getItemClass().getItemNumber() %> - <%= solm.getItemClass().getItemDescription() %>&nbsp;&nbsp; -- &nbsp;&nbsp;
      Attribute Model: <%= solm.getItemClass().getAttributeModel() %>
     </b>
    </td>
    <td style="width:5%">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td>&nbsp;</td>
    <td>
     <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
      <tr class="tr00">
       <td class="td04140902" rowspan="3" style="width:3%">&nbsp;</td>
       <td class="td04140402" style="width:20%">
        <b>Customer Name:</b>
       </td>
       <td class="td04140402" style="width:27%">
        <%= solm.getCustomerName().trim() %>      
       </td>
       <td class="td04140402" style="width:20%" rowspan="3">
        <b>Item Comments: </b>
       </td>
       <td class="td04140402" rowspan="3">
        <%= HTMLHelpersInput.inputBoxTextarea("lineComment", comments, 5, 40, 135, "N") %> 
       </td>
      </tr>
<%
  // Row 2
%>
      <tr class="tr00">
       <td class="td04140102">
        <b>Ship To Address: </b>
       </td>
       <td class="td04140102">
<%
   if (!solm.getAddress1().trim().equals(""))
   {
      out.println(solm.getAddress1().trim());
      if (!solm.getAddress2().trim().equals("") ||
          !solm.getAddress3().trim().equals("") ||
          !solm.getAddress4().trim().equals(""))
         out.println("<br>");
   }
   if (!solm.getAddress2().trim().equals(""))
   {
      out.println(solm.getAddress2().trim());
      if (!solm.getAddress3().trim().equals("") ||
          !solm.getAddress4().trim().equals(""))
         out.println("<br>");
   }   
   if (!solm.getAddress3().trim().equals(""))
   {
      out.println(solm.getAddress3().trim());
      if (!solm.getAddress4().trim().equals(""))
         out.println("<br>");
   }        
   if (!solm.getAddress4().trim().equals(""))
      out.println(solm.getAddress4().trim());
%>     
     &nbsp;
       </td>
      </tr>   
<%
  // Row 3
%>
      <tr class="tr00">
       <td class="td04140102">
        <b>Order Ship Date: </b>
       </td>
       <td class="td04140102">
     <%= com.treetop.app.coa.BuildCOA.convertDate(solm.getActualShipDate(), new Integer(df3)) %>    
       </td>
      </tr>      
     </table>
    </td>
   </tr>
  </table>  
<%
   }
%> 
  <table class="table00" cellspacing="0" style="width:100%">
   <tr class="tr02">
    <td class="td0416" style="text-align:center">
     <%= HTMLHelpers.buttonSubmit("saveChanges", "Update COA") %>
    </td>
   </tr>
  </table> 
 <%@ include file="updateCOASequence.jsp" %>  
  <table class="table00" cellspacing="0" style="width:100%">
   <tr class="tr02">
    <td class="td0416" style="text-align:center">
     <%= HTMLHelpers.buttonSubmit("saveChanges", "Update COA") %>
    </td>
   </tr>
  </table>     
  </td>
 </tr>
</table>   
   <%= HTMLHelpersInput.endForm() %>
     <jsp:include page="../../Include/footer.jsp"></jsp:include>
  </body>
</html>
