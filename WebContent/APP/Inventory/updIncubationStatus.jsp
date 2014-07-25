<%@ page language="java" %>
<%@ page import = "com.treetop.app.inventory.UpdInventory" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Inventory.updIncubationStatus.jsp -----------------------//
// Author   :  Teri Walton       6/10/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Update Incubation Status";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdInventory ui = new UpdInventory();
 try
 {
	ui = (UpdInventory) request.getAttribute("updViewBean");
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
  <form  name = "inqInventory" action="/web/CtlInventoryNew?requestType=updateIncubationStatus" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!ui.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= ui.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
       <tr class="tr02">
        <td class="td0418" style="width:5%">&nbsp;</td>
        <td class="td0418" colspan = "2">
         <b>Press Go to Process (Change Incubation to Released) :</b>
        </td>
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td rowspan="2">&nbsp;</td>       
 		 <td class="td04160102" style="text-align:center" colspan="2">
         <%= HTMLHelpers.buttonSubmit("goButton", "Go - Update Incubation Status") %>
        </td>
        <td rowspan="2">&nbsp;</td>      
       </tr>
       <tr class="tr01">
 		<td class="td04160102" colspan = "2">
         <b>Rules of what lots will be updated:</b><br>
         &nbsp;&nbsp;If the Item Type = 100 (Packaged Goods), 110 (Custom Pack), or 120 (Dried and Frozen)
         &nbsp;&nbsp;If the status on the Lot Master = 1 (Incubation)<br>
         &nbsp;&nbsp;If there is an Item Warehouse Record for either Warehouse (209, 469, 240, 490 or 230) that has an Aging Days Greater than 0 (only cares about the aging, not the inventory<br>
         &nbsp;&nbsp;If the Reclassification Date of the lot is less than Today's Date<br>
         &nbsp;&nbsp;If the lot has not be reclassified before (records in the Transaction File)<br>
         
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