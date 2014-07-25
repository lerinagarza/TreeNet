<%@ page language="java" %>
<%@ page import = "com.treetop.app.finance.BldFinance" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Finance/bldCostingReportFiles.jsp -----------------------//
// Author   :  Teri Walton      6/4/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Build Information for the Costing Reports";  
 // Bring in the Build View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 BldFinance bf = new BldFinance();
 try
 {
	bf = (BldFinance) request.getAttribute("bldViewBean");
 }
 catch(Exception e)
 {
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",inqTitle);
//*****************************************************************************
%>
<html>
 <head>
   <title><%= inqTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <form name = "bldCostFile" action="/web/CtlFinance?requestType=bldCostingReportFiles" method="post">
 <table class="table01" cellspacing="0" style="width:100%">
  <tr>
   <td style="width:2%">&nbsp;</td>
   <td>
    <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!bf.getDisplayMessage().trim().equals(""))
   {
%>      
     <tr class="tr00">
      <td class="td03200102" colspan = "4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= bf.getDisplayMessage().trim() %></b></td>
     </tr>    
<%
   }
%>    
     <tr class="tr02">
      <td class="td04160102" style="width:3%">&nbsp;</td>
      <td class="td04160102" colspan="2"><b>Build/Rebuild the Information for the Costing Reports.</b></td>
      <td class="td04160102" style="width:3%">&nbsp;</td>
     </tr>
     <tr class="tr01">
      <td class="td04160102">&nbsp;</td>
      <td class="td04160102" colspan="2">This jsp executes a process that rebuilds Costing Work Files used to present component and Produced/Consumed Reports for Finance using current Lawson M3 data.&nbsp;</td>
	  <td class="td04160102">&nbsp;</td>
     </tr>
     <tr class="tr00">
      <td class="td04160102">&nbsp;</td>
      <td class="td04160102"><b>Rebuild the Costing Work File For Next Year - Company 100</td>
      <td class="td04160102">&nbsp;<%= HTMLHelpers.buttonSubmit("bldNextYearButton", "Build Information - Next Year") %></td>
      <td class="td04160102">&nbsp;</td>
     </tr>      
     <tr class="tr00">
      <td class="td04160102">&nbsp;</td>
      <td class="td04160102"><b>Rebuild the Costing Work File For Current Year - Company 100</td>
      <td class="td04160102">&nbsp;<%= HTMLHelpers.buttonSubmit("bldCurrentYearButton", "Build Information - Current Year") %></td>
      <td class="td04160102">&nbsp;</td>
     </tr>  
     <tr class="tr00">
      <td class="td04160102">&nbsp;</td>
      <td class="td04160102"><b>Rebuild the Costing Work File For Both Current Year and Next Year - Company 100</td>
      <td class="td04160102">&nbsp;<%= HTMLHelpers.buttonSubmit("bldBothButton", "Build Information - Current and Next Year") %></td>
      <td class="td04160102">&nbsp;</td>
     </tr>         
    </table>  
   </td>  
   <td style="width:2%">&nbsp;</td> 
  </tr>
 </table> 
 </form>  
 <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>