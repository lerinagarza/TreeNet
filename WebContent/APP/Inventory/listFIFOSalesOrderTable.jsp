<%@ page import = "com.treetop.app.inventory.InqInventory" %>
<%@ page import = "com.treetop.businessobjects.SalesOrderLineItem" %>
<%@ page import = "com.treetop.businessobjects.Inventory" %>
<%@ page import = "com.treetop.utilities.html.HTMLHelpersMasking" %>
<%@ page import = "java.util.*" %>
<%

//---------------- APP/Inventory/listFIFOSalesOrderTable.jsp -----------------------//
//Author   :  Teri Walton       5/13/08
//Changes  :
//Date       Name          Comments
//----       ----          --------
//------------------------------------------------------------//
//********************************************************************
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
   int isImageCount = 3;
   int isExpandCount = 1;
   try
   {
   	  isImageCount = ((Integer) request.getAttribute("imageCount")).intValue();
   	  isExpandCount = ((Integer) request.getAttribute("expandCount")).intValue();
   }
   catch(Exception e)
   {}	
      
 Vector invItems3 = new Vector();
 Vector soItems3  = new Vector();
 try
 {
    InqInventory ii3 = (InqInventory) request.getAttribute("inqViewBean");
    invItems3 = ii3.getBeanInventory().getByItemVectorOfInventory();
    soItems3  = ii3.getBeanInventory().getListSOLineItems();
 }
 catch(Exception e)
 {
 }  
%>
<html>
  <head>
  </head>
  <body>
   <table cellspacing="0" style="width:100%">
<%
   try
   {
      if (soItems3.size() > 0 && invItems3.size() > 0)
      {
%>
     <tr class="tr02">
       <td class="td0414"><b>Line</b></td>
       <td class="td0414"><b>Item</b></td>
       <td class="td0414"><b>Name</b></td>
       <td class="td0414" style="text-align:center"><b>Qty</b></td> 
       <td class="td0414"><b>U/M</b></td>
       <td class="td0414"><b>Code Date</b></td>
       <td class="td0414"><b>Location</b></td>       
     </tr>
<%
         // Read through the Customer Order Line Items
         for (int x3 = 0; x3 < soItems3.size(); x3++)
         {
             SalesOrderLineItem thisItem = (SalesOrderLineItem) soItems3.elementAt(x3);
%>   
    <tr class="tr01">
     <td class="td04141405">
       <b><%= thisItem.getLineNumber() %></b>
     </td>    
     <td class="td04141405">
       <b><%= thisItem.getItemClass().getItemNumber() %></b>
     </td>
     <td class="td04141405">
        <b><%= thisItem.getItemClass().getItemDescription() %></b>&nbsp;
     </td>
     <td class="td04141405" style = "text-align:right">
        <b><%= HTMLHelpersMasking.maskBigDecimal(thisItem.getOrderQuantity(), 0) %></b>&nbsp;
     </td>
     <td class="td04141405">
        <b><%= thisItem.getItemClass().getBasicUnitOfMeasure() %></b>&nbsp;
     </td>
     <td class="td04141405" colspan = "2">
        &nbsp;
     </td>
    </tr>
<%
        Vector listCodeDate = (Vector) invItems3.elementAt(x3);
        if (listCodeDate.size() > 0)
        {
          for (int y3 = 0; y3 < listCodeDate.size(); y3++)
          {
             Inventory thisRow3 = (Inventory) listCodeDate.elementAt(y3);
%>    
    <tr class="tr00">
     <td colspan="5">&nbsp;</td>
     <td class="td0414"><%= thisRow3.getLotRef1().trim() %>&nbsp;</td>
     <td class="td0414"><%= thisRow3.getLocation().trim() %>&nbsp;</td>
    </tr>
<% 
          }
        } 
      } // end of 'for' lineItemCount
    } // End if the IF there is information
    else
    {
%>    
     <tr class="tr00">
      <td class="td0312" style="width:100%"> <b>Information not found for this Sales Order</b></td>
     </tr>        
<%
    }
   }
   catch(Exception e)
   {
      out.println("Problem within the Line Item Section" + e);
   }
%>
    </table>
 </body>
</html>