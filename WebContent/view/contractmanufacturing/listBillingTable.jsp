<%@ page import = "com.treetop.controller.contractmanufacturing.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>

<%@ page import = "java.util.Vector" %>
<% 
//---------------  listBillingTable.jsp  ------------------------------------------//
//  Directly included in the listBilling.jsp
// 
//    Author :  Teri Walton  4/4/12
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 Vector getData = new Vector();
 try 
 {
	InqBilling inqBillingTable = (InqBilling) request.getAttribute("viewBean");
	getData = inqBillingTable.getBeanInfo().getListLots();
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
%>
  <table class="styled" cellspacing="0" style="width:100%" align="center">
<%
 // Define the Columns -- Groupings of Columns
%>  
  <colgroup span="2"></colgroup>
  <colgroup span="2"></colgroup>
  <colgroup span="5"></colgroup>
<%
  //----------------------------------------------------------------------------
  // HEADING SECTION
%>  
   <tr>
    <th colspan = "2">&nbsp;</th>
    <th colspan = "2">Manufacture</th>
    <th colspan = "5">Current Inventory</th>
   </tr> 

   <tr>
    <th class="left">TT Lot</th>
    <th class="left">OS Lot</th>
    <th>Date</th>
    <th class="right">Quantity</th>
    <th>Whse</th>
    <th>Location</th>
    <th>Status</th>
    <th class="right">Quantity</th>
    <th>UOM</th>
   </tr> 
<%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------
  if (!getData.isEmpty())
  { 
    String classInfo = "";
    for (int x = 0; x < getData.size(); x++)
    {
      Lot thisrow = (Lot) getData.elementAt(x);
      DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(thisrow.getManufactureDate());
      if (x == (getData.size() - 1))
         classInfo = "grand-total";
%>     
   <tr class="<%= classInfo %>">
    <td><%= thisrow.getLotNumber().trim() %>&#160;</td>
    <td><%= thisrow.getOSLot().trim() %>&#160;</td>
    <td class="center"><%= dt.getDateFormatMMddyyyySlash() %>&#160;</td>
    <td class="right"><%= HTMLHelpersMasking.maskNumber(thisrow.getOriginalQuantity(), 0) %>&#160;</td>
<%
   if (!thisrow.getTempOrder().trim().equals("") &&
       thisrow.getQuantity().trim().equals("0.000000"))
   {
   //  show adjusted temporary order adjusted to
%>  
   <td colspan = "5">Temporary Order Number: <%= thisrow.getTempOrder()%> -- No Inventory Left to Bill</td>
<%
   }else{
%>
    <td class="center"><%= thisrow.getWarehouse()%>&#160;<%= thisrow.getWarehouseName() %></td>
    <td class="center"><%= thisrow.getLocation() %>&#160;</td>
    <td class="center"><%= thisrow.getStatus() %>&#160;</td>
    <td class="right"><%= HTMLHelpersMasking.maskNumber(thisrow.getQuantity(), 0) %>&#160;</td>
    <td class="center"><%= thisrow.getBasicUnitOfMeasure() %>&#160;</td>
<%  
   }
%>    
   </tr>   
<%
     } // end of the for loop
   } // end of the if no load records chosen
%>     
  </table>
