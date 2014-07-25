<%@ page import = "com.treetop.app.coa.BuildCOA" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%

//---------------- APP/COA/buildCOADistributionOrder.jsp -----------------------//
//   Use with buildCOA
// Author   :  Teri Walton       7/28/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
 DistributionOrderLineItem dolm = new DistributionOrderLineItem();
 String df1 = "0";
 try
 {
	BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	if (bldCOA.getRequestType().equals(""))
	   bldCOA.setRequestType("build");
	BeanCOA bc = (BeanCOA) bldCOA.getListReport().elementAt(0);
	dolm = (DistributionOrderLineItem) bc.getListDOLineItems().elementAt(0);
	df1 = (String) request.getAttribute("dateFormat");
    if (df1 == null || df1.trim().equals(""))
      df1 = "0";
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
  if (dolm.getFacility() != null &&
      !dolm.getFacility().equals(""))
  {
%>
  <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
   <tr class="tr00">
    <td class="td04140902" rowspan="4" style="width:3%">&nbsp;</td>
    <td class="td04140402"><b>Distribution Order Number:</b></td>
    <td class="td04140402">
     <%= dolm.getOrderNumber().trim() %>
     <%= HTMLHelpersInput.inputBoxHidden("inqDistributionOrder", dolm.getOrderNumber()) %>&nbsp;
    </td>
    <td class="td04140402"><b>Responsible:</b></td>
    <td class="td04140402">
      <%= RetrieveData.getLongName(dolm.getOrderResponsible().trim(), "M3") %>&nbsp;
    </td>
    <td class="td04141002" rowspan="4" style="width:3%">&nbsp;</td>
   </tr>
<%
  // Row 2
%>
   <tr class="tr00">
    <td class="td04140102"><b>From Facility:</b></td>
    <td class="td04140102">
     <%= dolm.getFromWarehouse().getFacility().trim() %>&nbsp;&nbsp;<%= dolm.getFromWarehouse().getFacilityDescription() %>
    </td>
    <td class="td04140102"><b>To Facility:</b></td>
    <td class="td04140102">
     <%= dolm.getToWarehouse().getFacility().trim() %>&nbsp;&nbsp;<%= dolm.getToWarehouse().getFacilityDescription() %>
    </td>
   </tr>
<%
  // Row 3
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
     <%= BuildCOA.convertDate(dolm.getActualShipDate(), new Integer(df1)) %> &nbsp;     
    </td>
    <td class="td04140102"><b>Receiving Date:</b></td>
    <td class="td04140102">
     <%= BuildCOA.convertDate(dolm.getReceiveDate(), new Integer(df1)) %> &nbsp;   
    </td>
   </tr>
  </table>
<%
  }else{
%>  
  <table class="table01" cellspacing="0">
   <tr>
    <td class="td044CL001">
     Distribution Order Information Not Found, please choose Select and Option and choose another Distribution or Sales Order
    </td>
   </tr>
  </table>
<%
   }
%>  
 </body>
</html>
