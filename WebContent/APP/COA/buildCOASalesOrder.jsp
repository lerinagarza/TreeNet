<%@ page import = "com.treetop.app.coa.BuildCOA" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%


//---------------- APP/COA/buildCOASalesOrder.jsp -----------------------//
// Prototype:  Charlena Paschen  06/04/03 (jsp)
// Author   :  Teri Walton       11/05/03 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//  9/5/07     TWalton 		 Rewrite with Movex  - was listCOA.jsp
//------------------------------------------------------------//
 SalesOrderLineItem solm = new SalesOrderLineItem();
 try
 {
	BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	if (bldCOA.getRequestType().equals(""))
	   bldCOA.setRequestType("build");
	BeanCOA bc = (BeanCOA) bldCOA.getListReport().elementAt(0);
	solm = (SalesOrderLineItem) bc.getListSOLineItems().elementAt(0);
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
  if (solm.getCustomerNumber() != null &&
      !solm.getCustomerNumber().equals(""))
  {
%>
  <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
   <tr class="tr00">
    <td class="td04140902" rowspan="4" style="width:3%">&nbsp;</td>
    <td class="td04140402"><b>Customer Order Number:</b></td>
    <td class="td04140402">
<%
   if (solm.getOrderNumber().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm.getOrderNumber().trim() %>
     <%= HTMLHelpersInput.inputBoxHidden("inqSalesOrder", solm.getOrderNumber()) %>
    </td>
    <td class="td04140402"><b>Order Ship Date:</b></td>
    <td class="td04140402">
<%
   String df1 = (String) request.getAttribute("dateFormat");
   if (df1 == null || df1.trim().equals(""))
      df1 = "0";
%>    
      <%= BuildCOA.convertDate(solm.getActualShipDate(), new Integer(df1)) %>
    </td>
    <td class="td04141002" rowspan="4" style="width:3%">&nbsp;</td>
   </tr>
<%
  // Row 2
%>
   <tr class="tr00">
    <td class="td04140102"><b>Customer Number:</b></td>
    <td class="td04140102">
<%
   if (solm.getCustomerNumber().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm.getCustomerNumber().trim() %>
    </td>
    <td class="td04140102"><b>Order Entered By:</b></td>
    <td class="td04140102">
<%
   if (solm.getOrderResponsible().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= RetrieveData.getLongName(solm.getOrderResponsible().trim(), "M3") %>
    </td>
   </tr>
<%
  // Row 3
%>      
   <tr class="tr00">
    <td class="td04140102"><b>Customer PO:</b></td>
    <td class="td04140102">
<%
   if (solm.getCustomerPONumber().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm.getCustomerPONumber().trim() %>
    </td>
    <td class="td04140102"><b>Last Modified by:</b></td>
    <td class="td04140102">
<%
   if (solm.getLastModifiedBy().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= RetrieveData.getLongName(solm.getLastModifiedBy().trim(), "M3") %>   
    </td>
   </tr>
<%
 // Row 4
%>      
   <tr class="tr00">
    <td class="td04140102"><b>Customer Name:</b></td>
    <td class="td04140102">
<%
   if (solm.getCustomerName().trim().equals(""))
      out.println("&nbsp;");
%>     
     <%= solm.getCustomerName().trim() %>      
    </td>
    <td class="td04140102"><b>Ship To Address:</b></td>
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
