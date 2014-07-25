<%@ page language="java" %>
<%@ page import = "com.treetop.app.transaction.InqTransaction" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/transaction/inqTransactionError.jsp -----------------------//
// Author   :  Teri Walton      9/21/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Search Through Transaction Errors";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields drop down lists
 InqTransaction it = new InqTransaction();
 try
 {
	it = (InqTransaction) request.getAttribute("inqViewBean");
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
  <form  name = "inqTransactionError" action="/web/CtlTransaction?requestType=listTransactionError" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
    <td>
     <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!it.getDisplayMessage().trim().equals(""))
   {
%>      
      <tr class="tr00">
       <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= it.getDisplayMessage().trim() %></b></td>
      </tr>    
<%
   }
%>   
      <tr class="tr00">
       <td class="td0518" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Only 30 days of error information will be kept.</b></td>
      </tr>          
      <tr class="tr02">
       <td class="td0418" style="width:3%">&nbsp;</td>
       <td class="td0418" style="width:35%"><b> Selection Criteria:</b></td>
       <td class="td0418" style="text-align:right" colspan="3">
         <%= HTMLHelpers.buttonSubmit("getList", "Go - Retrieve List") %>
       </td>
       <td class="td0418" style="width:3%">&nbsp;</td>
      </tr>      
      <tr class="tr00">
       <td>&nbsp;</td>    
       <td class="td04160102"><b><acronym title="Environment of Transaction">Environment:</acronym></b></td>
       <td class="td04160102" colspan="3">
<%
       Vector buildList = new Vector();
		DropDownSingle dds = new DropDownSingle();
			dds.setDescription("PRD");
			dds.setValue("PRD");
			buildList.addElement(dds);
			dds = new DropDownSingle();
		dds.setDescription("TST");
		dds.setValue("TST");
		buildList.addElement(dds);
%>			
        <%= DropDownSingle.buildDropDown(buildList, "inqEnvironment", "TST", "None", "N", "N") %>
        </td>
        <td>&nbsp;</td>    
      </tr>         
      <tr class="tr00">
       <td>&nbsp;</td>    
       <td class="td04160102"><b><acronym title="User who entered the Transaction">User:</acronym></b></td>
       <td class="td04160102" colspan="3">
        <%= InqTransaction.buildDropDownUser("", it.getInqUser()) %>
       </td>
       <td>&nbsp;</td>    
      </tr>    
      <tr class="tr00">
       <td>&nbsp;</td>    
       <td class="td04160102"><b><acronym title="Warehouse used in the Transaction">Warehouse:</acronym></b></td>
       <td class="td04160102" colspan="3">
        <%= InqTransaction.buildDropDownWarehouse("", it.getInqWhse()) %>
       </td>
       <td>&nbsp;</td>    
      </tr>        
      <tr class="tr00">
       <td>&nbsp;</td>    
       <td class="td04160102"><b><acronym title="Item used in the Transaction">Item:</acronym></b></td>
       <td class="td04160102" colspan="3">
        <%= InqTransaction.buildDropDownItem("", it.getInqItem()) %>
       </td>
       <td>&nbsp;</td>    
      </tr>    
      <tr class="tr00">
       <td>&nbsp;</td>    
       <td class="td04160102"><b><acronym title="Lot used in the Transaction">Lot:</acronym></b></td>
       <td class="td04160102" colspan="3">
        <%= HTMLHelpersInput.inputBoxText("inqLot", it.getInqLot(), "Lot Number", 10,10,"N", "N") %>
       </td>
       <td>&nbsp;</td>    
      </tr>                       
      <tr class="tr01">
       <td>&nbsp;</td>       
       <td class="td04160102"><b>&nbsp;</b></td>
       <td class="td04180102"><b>FROM</b></td>
       <td class="td04160102" style="width:3%">&nbsp;</td>
       <td class="td04180102"><b>TO</b></td>
       <td>&nbsp;</td>      
      </tr>     
      <tr>
       <td rowspan="10">&nbsp;</td>       
       <td class="td04160102"><b>Transaction Date:</b></td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxDate("inqFromDate", it.getInqFromDate(), "getInqFromDate", "N", "N") %></td>
       <td class="td04160102">&nbsp;</td>
       <td class="td04160102"><%= HTMLHelpersInput.inputBoxDate("inqToDate", it.getInqToDate(), "getInqToDate", "N", "N") %></td>
       <td rowspan="10">&nbsp;</td>      
      </tr>
     </table>
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table> 
  </form>  
  <br>  
  <%= JavascriptInfo.getCalendarFoot("inqTransactionError", "getInqFromDate", "inqFromDate") %>
  <%= JavascriptInfo.getCalendarFoot("inqTransactionError", "getInqToDate", "inqToDate") %>
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>