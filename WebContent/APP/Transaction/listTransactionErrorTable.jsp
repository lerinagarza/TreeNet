<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.app.transaction.*" %>
<%@ page import = "com.treetop.businessobjects.TransactionError" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanTransaction" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listScaleTicketTable.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  9/22/09
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 InqTransaction itTable = new InqTransaction();
 Vector getData = new Vector();
 try
 {
	itTable = (InqTransaction) request.getAttribute("inqViewBean");
	BeanTransaction beanData = (BeanTransaction) itTable.getBean();
	getData = beanData.getListTransactionErrors();
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
   String columnHeadingTo = "/web/CtlTransaction?requestType=listTransactionError" +
                            itTable.buildParameterResend();
   String[] sortImage = new String[6];
   String[] sortStyle = new String[6];
   String[] sortOrder = new String[6];
   sortOrder[0] = "transactionDate";
   sortOrder[1] = "user";
   sortOrder[2] = "";
   sortOrder[3] = "lot";
   sortOrder[4] = "error";
//   sortOrder[5] = "loadFullBins";
//   sortOrder[6] = "facility";
//   sortOrder[7] = "carrierBOL";
//   sortOrder[8] = "carrier";
//   sortOrder[9] = "fromLocation";
//   sortOrder[10] = "whseTicket";  
//   sortOrder[11] = "warehouse";
  //************
  //Set Defaults
   for (int x = 0; x < 5; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
   String orderBy = itTable.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "transactionDate";
   for (int x = 0; x < 5; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (itTable.getOrderStyle().trim().equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowUpDark.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowDownDark.gif\">";
     }
   }   
   int imageCount = 4;
   int expandCount = 0;
%>

<html>
  <head>
  </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
  //----------------------------------------------------------------------------
  // HEADING SECTION
%>  
   <tr class="tr02">
    <td class="td04100605" style="text-align:center">
     <%= sortImage[0] %>
     <a class="a0412" title="Date and Time of when the Transaction was Processed" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
      <b>Transaction<br>Date and Time</b>
     </a>      
    </td>
    <td class="td04120605" style="text-align:center">
      <b>Transaction<br>type</b>
    </td>   
    <td class="td04100605" style="text-align:center">
     <%= sortImage[1] %>
     <a class="a0412" title="User Name: Mouseover to see the Profile" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
      <b><br>User</b>
     </a>      
    </td>      
    <td class="td04120605" style="text-align:center">
      <b>Manufacturing<br>Order</b>
    </td>      
    <td class="td04100605" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0412" title="Lot Number: Mouseover to see Item Information" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      <b>Lot<br>Number</b>
     </a>      
    </td>   
    <td class="td04100605" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0410" title="Error Message: Mouseover to see more Details" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      <b>Error<br>Message</b>
     </a>      
    </td>  
   </tr> 
 <%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------
  // DATA SECTION
  if (getData.size() > 0)
  { // IF there are LOAD Records
    for (int x = 0; x < getData.size(); x++)
    {
      TransactionError thisrow = (TransactionError) getData.elementAt(x);
%>  
   <tr class="tr00">
    <td class="td04121524" style="text-align:center">
     <%= thisrow.getTransactionDate().getDateFormatMMddyyyySlash() %>&nbsp;&nbsp;
     <%= thisrow.getTransactionDate().getTimeFormathhmmssColon() %>
    </td>
    <td class="td04121524" style="text-align:center">
     <%= thisrow.getTransactionType().trim() %>&nbsp;
    </td>   
    <td class="td04101524" style="text-align:center">
     <acronym title="User Profile of :<%= thisrow.getUserProfile() %>">&nbsp;<%= thisrow.getUserLongName() %></acronym>
    </td> 
    <td class="td04101524">
<% 
   StringBuffer moMouseover = new StringBuffer();
   moMouseover.append("Manufacturing Order Information for &#10;&#13 ");
   if (!thisrow.getWarehouseInfo().getWarehouse().trim().equals(""))
   {
     moMouseover.append("Warehouse: " + thisrow.getWarehouseInfo().getWarehouse() + "-");
     moMouseover.append(thisrow.getWarehouseInfo().getWarehouseDescription());
   }
   if (!thisrow.getItemNumber().trim().equals(""))
   {
     moMouseover.append(" &#10;&#13 Item: " + thisrow.getItemNumber());
     moMouseover.append("-" + thisrow.getItemDescription());
   }
%>    
     <acronym title="<%= moMouseover.toString() %>">&nbsp;<%= thisrow.getOrderNumber() %></acronym>
    </td>   
    <td class="td04101524">
<%

   StringBuffer lotMouseover = new StringBuffer();
   lotMouseover.append("Lot Number Information for &#10;&#13 ");
   if (!thisrow.getItemNumber().trim().equals(""))
   {
     lotMouseover.append("Item: " + thisrow.getItemNumber() + "-");
     lotMouseover.append(thisrow.getItemDescription()); 
   }
%>    
     <acronym title="<%= lotMouseover.toString() %>">&nbsp;<%= thisrow.getLotNumber() %></acronym>
    </td> 
    <td class="td04101524">
      <acronym title="Error Occured <%= thisrow.getErrorBeforeAfter() %> trying to Load into M3(Movex)">&nbsp;<%= thisrow.getErrorText() %></acronym>
    </td>  
   </tr>   
   
<%
     } // end of the for loop
   } // end of the if no load records chosen
%>     
  </table>
 </body>
</html>