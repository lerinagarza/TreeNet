<%@ page language="java" %>
<%@ page import = "com.treetop.app.finance.BldFinance" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Finance/bldStateFees.jsp -----------------------//
// Author   :  Teri Walton       3/20/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Build the State Fees";  
 // Bring in the Update View Bean.
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
   <%= JavascriptInfo.getCalendarHead() %>      
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form  name = "bldStateFees" action="/web/CtlFinance?requestType=addStateFees" method="post">
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
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= bf.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>        
       <tr class="tr02">
        <td class="td0418" style="width:3%">&nbsp;</td>
        <td class="td0418" colspan = "4">
         <b> Build the State Fees:</b>
        </td>
        <td class="td0418" style="width:3%">&nbsp;</td>
       </tr>   
       <tr class="tr01">
        <td class="td0418">&nbsp;</td>      
        <td class="td04140105" colspan = "4">
         <b> State Fees Processing - Compute Monthly Payments</b>
        </td>
        <td class="td0418">&nbsp;</td> 
       </tr>
       <tr class="tr02">
        <td class="td0418">&nbsp;</td>
        <td class="td05187324" style="text-align:center" colspan = "2"><b>FROM</b></td>
        <td class="td05180102" style="text-align:center" colspan = "2"><b>TO</b></td>
        <td class="td0418">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102">From Date</td>
        <td class="td03167324">
          <%= HTMLHelpersInput.inputBoxDate("bldFromDate", bf.getBldFromDate(), "cal1", "N", "N") %>
<%
   if (!bf.getBldFromDateError().trim().equals(""))
      out.println("<b>&nbsp;" + bf.getBldFromDateError() + "</b>");
%>      
        </td>
        <td class="td04160102">To Date</td>
        <td class="td03160102">
          <%= HTMLHelpersInput.inputBoxDate("bldToDate", bf.getBldToDate(), "cal2", "N", "N") %>
<%
   if (!bf.getBldToDateError().trim().equals(""))
      out.println("<b>&nbsp;" + bf.getBldToDateError() + "</b>");
%>      
        </td>        
        <td class="td0418">&nbsp;</td>
       </tr>               
       <tr class="tr01">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102" style="text-align:center" colspan="4">
          <%= HTMLHelpers.buttonSubmit("addStateFees", "Compute Monthly Payments") %>
        </td>
        <td class="td0418">&nbsp;</td>
       </tr>      
       <%= JavascriptInfo.getCalendarFoot("bldStateFees", "cal1", "bldFromDate") %> 
       <%= JavascriptInfo.getCalendarFoot("bldStateFees", "cal2", "bldToDate") %> 
      </table>  
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table> 
  </form>  
  <br>  
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>