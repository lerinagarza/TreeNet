<%@ page language="java" %>
<%@ page import = "com.treetop.app.transaction.UpdTransaction" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Transaction/updHoldFlag.jsp -----------------------//
// Author   :  Teri Walton      5/21/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Update Hold Flag for Reports";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdTransaction ut = new UpdTransaction();
 try
 {
	ut = (UpdTransaction) request.getAttribute("updViewBean");
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
  <form name = "updWorkFile" action="/web/CtlTransaction?requestType=updHoldFlag" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!ut.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= ut.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
      <tr class="tr02">
        <td class="td04160102"><b>Run Process to update hold records correctly, will only update ones that have not yet been processed.</b></td>
       </tr>
       <tr class="tr01">
        <td class="td0416" style="text-align:center">
          <%= HTMLHelpers.buttonSubmit("goButton", "Go - Update Flag Information") %>
        </td>
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