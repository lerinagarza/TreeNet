<%@ page import = "com.treetop.app.coa.InqCOA" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/COA/listSalesOrderComments.jsp -----------------------//
//Prototype:  Charlena Paschen  06/04/03 (jsp)
//Author   :  Teri Walton       11/05/03 (thrown from servlet)
//Changes  :
//Date       Name          Comments
//----       ----          --------
//9/5/07     TWalton 		 Rewrite with Movex //Split out JSP 
//------------------------------------------------------------//
//********************************************************************
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
// Vector   familyTree = new Vector();
// int      familySize = 0;
// String   rType = (String) request.getAttribute("reqType");
// if (rType == null)
//    rType = "";
// String   gtinOriginal = (String) request.getAttribute("gtin");
// if (gtinOriginal == null)
//    gtinOriginal = "";
    
%>
<html>
  <head>
  </head>
  <body>
<% 
//**************************************************************************************
   // Update Additional Information
//**************************************************************************************
%>
  <table class="table00" cellspacing="0" border = "1" style="width:100%">
   <tr class="tr02">
    <td class="td0412" style="width:5%" style="text-align:center">
     <b>Load Slip</b>
    </td>
    <td class="td0412" style="width:5%" style="text-align:center">
     <b>BOL</b>
    </td>
    <td class="td0412" style="width:5%" style="text-align:center">
     <b>Invoice</b>
    </td>
    <td class="td0412" style="width:5%" style="text-align:center">
     <b>Special</b>
    </td>
    <td class="td0412" style="text-align:center">
     <b>Comments</b>
    </td>
   </tr>
<%
   try
   {
      for (int x = 0; x < 2; x++)
      {
//         SalesOrderComment thisLine = (SalesOrderComment) soComments.elementAt(x);
%>
   <tr>
    <td class="td0412">
     &nbsp;
    </td>
    <td class="td0412">
     &nbsp;
    </td>
    <td class="td0412">
     &nbsp;
    </td>
    <td class="td0412">
     &nbsp;
    </td>
    <td class="td0412">
     &nbsp;
    </td>
   </tr>
<%
      } // End of the 'for' Comment section
   }
   catch (Exception e)
   {
      out.println("There is a problem with the Comment section.");
   }
%>
  </table>   
 </body>
</html>