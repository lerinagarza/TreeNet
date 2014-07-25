<%@ page language="java" %>
<%@ page import = "com.treetop.app.item.InqItem" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Item/inqItemVariance.jsp -----------------------//
// Author   :  Teri Walton       9/11/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Choose Item to See Variances";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqItem ii = new InqItem();
 try
 {
	ii = (InqItem) request.getAttribute("inqViewBean");
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
  <form  name = "inqInventory" action="/web/CtlItem?requestType=listVariance" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!ii.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= ii.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
       <tr class="tr02">
        <td class="td0418" style="width:5%">&nbsp;</td>
        <td class="td0418" colspan = "2">
         <b> Choose Item to View Variances</b>
        </td>
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td rowspan="8">&nbsp;</td>       
        <td class="td04160102"><b>Choose a Valid Item Number:</b></td>
        <td class="td03160102">
		 <%= HTMLHelpersInput.inputBoxText("inqItem", ii.getInqItem(), "Item Number", 15, 15, "N", "N") %>
		    &nbsp;&nbsp;<b><%= ii.getInqItemError() %></b>
        </td>
        <td rowspan="8">&nbsp;</td>      
       </tr>
<%
   if (ii.getAllowUpdate().equals("Y"))
   {
%>       
       <tr class="tr00">
        <td class="td04160102"><b>Show Pending Variances:</b></td>
        <td class="td04160102">
		 <%= HTMLHelpersInput.inputCheckBox("showPending", ii.getShowPending(), "N") %>
        </td>
       </tr>
<%
   }
%>       
       <tr class="tr01">
        <td class="td04160102" style="text-align:center" colspan="5">
         <%= HTMLHelpers.buttonSubmit("getList", "Go - Retrieve Variances") %>
        </td>
       </tr>      
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