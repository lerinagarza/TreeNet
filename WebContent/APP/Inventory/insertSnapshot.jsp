<%@ page language="java" %>
<%@ page import = "com.treetop.app.inventory.UpdInventory" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Inventory.insertSnapshot.jsp -----------------------//
// Author   :  Teri Walton       7/24/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Take Snapshot of Inventory";  
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
  <form  name = "inqInventory" action="/web/CtlInventoryNew?requestType=insertSnapshot" method="post">
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
         <b>Press Go to Process (Take a Current Snapshot of the Inventory) :</b>
        </td>
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td rowspan="3">&nbsp;</td>       
 		 <td class="td04160102" style="text-align:center" colspan="2">
         <%= HTMLHelpers.buttonSubmit("goButton", "Go - Create Snapshot") %>
        </td>
        <td rowspan="3">&nbsp;</td>      
       </tr>
       <tr class="tr01">
 		<td class="td04160102" colspan = "2">
         <b>Currently this will take a snapshot of All Snapple assigned inventories.</b><br>
         <%= HTMLHelpersInput.inputBoxHidden("customPackCode", "SN") %>
        </td>
       </tr>      
       <tr class="tr01">
 		<td class="td04160102" colspan = "2">&nbsp;
<%
   try
   {
   // get list of dates to display
      Vector listVector = UpdInventory.snapshotDates("SN");
      if (listVector.size() > 0)
      {
        // Add the Table Section
%>
        <table class="table01" cellspacing="0" style="width:100%">
         <tr class="tr02">
          <td style="width:5%">&nbsp;</td>
          <td class="td04140102">Date and Time of Previous Snapshots</td>
          <td style="width:5%">&nbsp;</td>
         </tr>
<%
         for (int x = 0; x < listVector.size(); x++)
         {
            DateTime dt = (DateTime) ((Vector) listVector.elementAt(x)).elementAt(1);
%>         
         <tr class="tr00">
          <td>&nbsp;</td>
          <td class="td04140102">
            <%= dt.getDateFormatMonthNameddyyyy() %>&nbsp;&nbsp;<%= dt.getTimeFormathhmmssColon() %>
          </td>
          <td>&nbsp;</td>
         </tr>         
<%
         }
%>
         </table>
<%
      }
   }
   catch(Exception e)
   {}
%> 		
 		
         <b>ADD Listing of all dates and times of snapshot of inventory.</b>
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