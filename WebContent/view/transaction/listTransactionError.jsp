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
<%@ page import = "
java.util.Vector,
com.treetop.app.transaction.InqTransaction,
com.treetop.businessobjectapplications.BeanTransaction,
com.treetop.businessobjects.TransactionError"; %>
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
           sortImage[x] = "<img src='Include/images/ui-sort-up_4a3126_16x16.gif'>";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src='Include/images/ui-sort-down_4a3126_16x16.gif'>";
     }
   }   
   int imageCount = 4;
   int expandCount = 0;
%>

	<title>List Transaction Errors</title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
	<h1>Transaction Errors</h1>
	<div class="clearfix">
	   <div style="float:left">
	       <%=itTable.buildSelectionCriteria() %>
	   </div>
	   <div style="float:right">
	       <a href="CtlTransaction?requestType=inqTransactionError" class="ui-select-again">Select Again</a>
	   </div>
	</div>
	<br>
<%	if(getData.isEmpty()) { %>

	<div class="ui-comment">
		There are no transaction errors based on your search.
	</div>
    <script>
        $(document).ready(function() {
            $(".selection-criteria h3").click();
        });
    </script>
<%	} else { %>


<table class="styled full-width">
   <tr>
    <th>
     <%= sortImage[0] %>
     <a title="Date and Time of when the Transaction was Processed" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
      Transaction<br>Date and Time
     </a>      
    </th>
    <th>Transaction<br>type</th>   
    <th>
     <%= sortImage[1] %>
     <a title="User Name: Mouseover to see the Profile" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
      <br>User
     </a>      
    </th>      
    <th>Manufacturing<br>Order</th>      
    <th>
     <%= sortImage[3] %>
     <a title="Lot Number: Mouseover to see Item Information" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      Lot<br>Number
     </a>      
    </th>   
    <th>
     <%= sortImage[4] %>
     <a title="Error Message: Mouseover to see more Details" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      Error<br>Message
     </a>      
    </th>  
   </tr> 
 <%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------

    for (int x = 0; x < getData.size(); x++)
    {
      TransactionError thisrow = (TransactionError) getData.elementAt(x);
%>  
   <tr>
    <td style="text-align:center">
     <%= thisrow.getTransactionDate().getDateFormatMMddyyyySlash() %>&nbsp;&nbsp;
     <%= thisrow.getTransactionDate().getTimeFormathhmmssColon() %>
    </td>
    <td style="text-align:center">
     <%= thisrow.getTransactionType().trim() %>&nbsp;
    </td>   
    <td style="text-align:center">
     <acronym title="User Profile of :<%= thisrow.getUserProfile() %>">&nbsp;<%= thisrow.getUserLongName() %></acronym>
    </td> 
    <td>
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
    <td>
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
    <td>
      <acronym title="Error Occured <%= thisrow.getErrorBeforeAfter() %> trying to Load into M3(Movex)">&nbsp;<%= thisrow.getErrorText() %></acronym>
    </td>  
   </tr>   
<%	} // end of the for loop %>
     
  </table>

<%	} // end if getData.isEmpty()%>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>