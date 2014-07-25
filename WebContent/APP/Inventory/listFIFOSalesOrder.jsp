<%@ page import = "com.treetop.app.inventory.InqInventory" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%

//---------------- APP/Inventory/listFIFOSalesOrder.jsp -----------------------//
// Author   :  Teri Walton       8/13/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
 SalesOrderLineItem solm2 = new SalesOrderLineItem();
 try
 {
    InqInventory ii2 = (InqInventory) request.getAttribute("inqViewBean");
	solm2 = (SalesOrderLineItem) ii2.getBeanInventory().getListSOLineItems().elementAt(0);
 }
 catch(Exception e)
 {
 }  
%>
<html>
 <head>
 </head>
 <body>
<%
  if (solm2.getCustomerNumber() != null &&
      !solm2.getCustomerNumber().equals(""))
  {
%>
  <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
   <tr class="tr00">
    <td class="td04140902" rowspan="2" style="width:3%">&nbsp;</td>
    <td class="td04140402"><b>Customer Order Number:</b></td>
    <td class="td04140402">
<%
   if (solm2.getOrderNumber().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm2.getOrderNumber().trim() %>
    </td>
    <td class="td04140102"><b>Customer Number:</b></td>
    <td class="td04140102">
<%
   if (solm2.getCustomerNumber().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm2.getCustomerNumber().trim() %>
    </td>
    <td class="td04141002" rowspan="2" style="width:3%">&nbsp;</td>
   </tr>
<%
  // Row 2
%>
   <tr class="tr00">
    <td class="td04140102" colspan="2">&nbsp;</td>
    <td class="td04140102"><b>Customer Name:</b></td>
    <td class="td04140102">
<%
   if (solm2.getCustomerName().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm2.getCustomerName().trim() %>      
    </td>
   </tr>
  </table>
<%
  }else{
%>  
  <table class="table01" cellspacing="0">
   <tr>
    <td class="td044CL001">
     Sales Order Information Not Found, please choose Select and Option and choose another Sales order
    </td>
   </tr>
  </table>
<%
   }
%>  
 </body>
</html>
