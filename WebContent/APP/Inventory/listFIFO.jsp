<%@ page language="java" %>
<%@ page import = "com.treetop.app.inventory.InqInventory" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Inventory/listFIFO.jsp -----------------------//
// Author   :  Teri Walton       5/13/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String errorPage = "Inventory/listFIFO.jsp";
  String listTitle = "List Inventory based on First in First Out Shipping Criteria";  
 // Bring in the Build View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 int lineItems = 0;
 try
 {
	InqInventory inqInv = (InqInventory) request.getAttribute("inqViewBean");
	lineItems = inqInv.getBeanInventory().getListSOLineItems().size();
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",listTitle);
   
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlInventoryNew?requestType=inqFIFO\">Enter Another Sales Order");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************
  int imageCount = 2;
  int expandCount = 0; 
%>
<html>
 <head>
   <title><%= listTitle %></title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", (2 + lineItems), "Y", (2 + lineItems)) %>  
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
<% 
  imageCount++;
  expandCount++;
%>
   <table style="width:100%">  
    <tr class="tr02">
     <td class="td03201404" style="width:100%">
      <%= JavascriptInfo.getExpandingSection("O", "Basic Customer Order Information", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="listFIFOSalesOrder.jsp"></jsp:include>
      </span>    
     </td>
    </tr>     
   </table>  
   </form>
<% 
  imageCount++;
  expandCount++;
  request.setAttribute("imageCount", new Integer(imageCount));
  request.setAttribute("expandCount", new Integer(expandCount));
%>
   <table style="width:100%">  
    <tr class="tr02">
     <td class="td03201404" style="width:100%">
      <%= JavascriptInfo.getExpandingSection("O", "List By Line Item of FIFO Inventory", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="listFIFOSalesOrderTable.jsp"></jsp:include>
      </span>    
     </td>
    </tr>     
   </table>        

    <jsp:include page="../../Include/footer.jsp"></jsp:include>
  </body>
</html>
