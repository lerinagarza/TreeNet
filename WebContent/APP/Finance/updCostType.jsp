<%@ page language="java" %>
<%@ page import = "com.treetop.app.finance.UpdFinance" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Finance/updCostType.jsp -----------------------//
// Author   :  Teri Walton       3/12/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Update Cost Types";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdFinance updF = new UpdFinance();
 try
 {
	updF = (UpdFinance) request.getAttribute("updViewBean");
 }
 catch(Exception e)
 {
 }  
 String dd1name = "fromCostType";
 String dd2name = "fromCostDate";
 String script = "";
 String dd = "";
 try
 {
  Vector dualDD = updF.buildDropDownDates();
   dd = (String) dualDD.elementAt(0);
   script = (String) dualDD.elementAt(1);
 }
 catch(Exception e)
 {}
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
   <%= script %>
   <%= JavascriptInfo.getDualDropDown(dd1name, dd2name) %>
   <%= JavascriptInfo.getCalendarHead() %>      
 </head>
<% 
   if (updF.getShouldOverride().trim().equals("TEST"))
   {
%>   
 <body>
<%
   }else{
%>
 <body onload="change<%= dd1name %>(document.forms['updCostType'].<%= dd1name %>)">
<%
   }
%> 
<jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form  name = "updCostType" action="/web/CtlFinance?requestType=updateCostType" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!updF.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= updF.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
   if (updF.getShouldOverride().trim().equals("TEST"))
   {
%>    
       <tr class="tr02">
        <td class="td0418" style="width:3%">&nbsp;</td>
        <td class="td0418" colspan = "4">
         <b> Build the Company Cost:</b>
        </td>
        <td class="td0418" style="width:3%">&nbsp;</td>
       </tr>   
       <tr class="tr01">
        <td class="td0418">&nbsp;</td>      
        <td class="td03180105" colspan = "4">
         <b> ** STOP ** Cost Type <%= updF.getToCostType() %> for the date of <%= updF.getToCostDate() %> already has records.</b>
         <%= HTMLHelpersInput.inputBoxHidden("fromCostType", updF.getFromCostType()) %>
         <%= HTMLHelpersInput.inputBoxHidden("fromCostDate", updF.getFromCostDate()) %>
         <%= HTMLHelpersInput.inputBoxHidden("toCostType", updF.getToCostType()) %>
         <%= HTMLHelpersInput.inputBoxHidden("toCostDate", updF.getToCostDate()) %>
         <%= HTMLHelpersInput.inputBoxHidden("fromFiscalYear", updF.getFromFiscalYear()) %>
         <%= HTMLHelpersInput.inputBoxHidden("itemType", updF.getItemType()) %>
         <%= HTMLHelpersInput.inputBoxHidden("itemNumber", updF.getItemNumber()) %>
              <br>
        </td>
        <td class="td0418">&nbsp;</td> 
       </tr>
       <tr class="tr01">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102" style="text-align:center" colspan="2">
          <%= HTMLHelpers.buttonSubmit("retryCompanyCost", "Go Back And Try Again") %>
        </td>
        <td class="td04160102" style="text-align:center" colspan="2">
          <%= HTMLHelpers.buttonSubmit("shouldOverride", "Delete and Replace the Records") %>
        </td>
        <td class="td0418">&nbsp;</td>
       </tr>         
<%
   }
   else
   {
%>        
      <%= HTMLHelpersInput.inputBoxHidden("page1", "Y" )%>
       <tr class="tr02">
        <td class="td0418" style="width:3%">&nbsp;</td>
        <td class="td0418" colspan = "4">
         <b> Build the Company Cost:</b>
        </td>
        <td class="td0418" style="width:3%">&nbsp;</td>
       </tr>   
       <tr class="tr01">
        <td class="td0418">&nbsp;</td>      
        <td class="td04140105" colspan = "4">
         <b>Use the Forecast which was uploaded from an Excel Spreadsheet (into file DBPRD/CSPAFCST)
              to calculate and determine the information for Company Cost </b> <br>
              This will calculate each Item Type Separately.
              <br>
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
        <td class="td04160102"><acronym title = "The Drop Down list will change (add a new year) as the CSPAFCST file is updated - Sorted Descending">Fiscal Year of Forecasted Quantities</acronym></td>
        <td class="td04167324"><%= updF.buildDropDownFromFiscalYear() %></td>
        <td class="td04160102" colspan="2">&nbsp;</td>
        <td class="td0418">&nbsp;</td>
       </tr>             
       <tr class="tr00">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102">Cost Type For Start Cost</td>
        <td class="td04167324"><%= dd %></td>
        <td class="td04160102">Cost Type</td>
        <td class="td04160102"><%= updF.buildDropDownToCostType() %></td>
        <td class="td0418">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102">Costing Date</td>
        <td class="td04167324"><select name="<%= dd2name %>"></td>
        <td class="td04160102">Costing Date</td>
        <td class="td04160102">
          <%= HTMLHelpersInput.inputBoxDate("toCostDate", updF.getToCostDate(), "cal1", "N", "N") %>
        </td>        
        <td class="td0418">&nbsp;</td>
       </tr>      
       <tr class="tr00">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102"><br>ItemType</td>
        <td class="td04160102"><br><%= updF.buildDropDownItemType(updF.getItemType()) %></td>
        <td class="td05180102"><br><b>** OR ** </b>Item Number </td>
        <td class="td04160102"><br><%= HTMLHelpersInput.inputBoxText("itemNumber",updF.getItemNumber(), "Item Number", 15, 15, "N", "N") %></td>
        <td class="td0418">&nbsp;</td>
       </tr>                  
       <tr class="tr01">
        <td class="td0418">&nbsp;</td>
        <td class="td04160102" style="text-align:center" colspan="4">
          <%= HTMLHelpers.buttonSubmit("updateCompanyCost", "Generate Company Cost") %>
        </td>
        <td class="td0418">&nbsp;</td>
       </tr>      
       <%= JavascriptInfo.getCalendarFoot("updCostType", "cal1", "toCostDate") %> 
<%
   }
%>           
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