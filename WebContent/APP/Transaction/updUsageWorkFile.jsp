<%@ page language="java" %>
<%@ page import = "com.treetop.app.transaction.UpdTransaction" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Transaction/updUsageWorkFile.jsp -----------------------//
// Author   :  Teri Walton      9/25/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Update Usage Work File Information";  
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
 String dd1name = "fiscalYear";
 String dd2name = "fiscalWeek";
 String script = "";
 String dd = "";
 try
 {
  Vector dualDD = UpdTransaction.buildDropDownYearPeriod(ut.getFiscalYear(), ut.getFiscalWeek());
   dd = (String) dualDD.elementAt(0);
   script = (String) dualDD.elementAt(1);
 }
 catch(Exception e)
 {
   System.out.println(e);
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
   <%= script %>
   <%= JavascriptInfo.getDualDropDown(dd1name, dd2name) %>
 </head>
<%
   if (dd == null || dd.trim().equals(""))
   {
%>
 <body>
<%
   }
   else
   {
%>
 <body onload="change<%= dd1name %>(document.forms['updWorkFile'].<%= dd1name %>)">
<%
   }
%> 
<jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form name = "updWorkFile" action="/web/CtlTransaction?requestType=updUsageWorkFile" method="post">
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
        <td class="td04160102" colspan="2"><b>Update Usage Work File (Information for LBI Report) -- Choose the criteria needed for updating the information.</b></td>
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Fiscal Year:</b></td>
        <td class="td03160102">
		 <%= dd %>
        </td>
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Fiscal Week:</b></td>
        <td class="td04160102">
         <select name="<%= dd2name %>">
        </td>
       </tr>
       <tr class="tr01">
        <td class="td0416">&nbsp;</td>
        <td class="td0416">
          <%= HTMLHelpers.buttonSubmit("goButton", "Go - Update Usage Information") %>
        </td>
       </tr>      
<%

   if (ut.getSecurityType().trim().equals("IS"))
   {
%>   
       <tr class="tr01">
        <td class="td04160105" colspan="2">&nbsp;</td>
       </tr>     
       <tr class="tr00">
        <td class="td04160102"><b>Press this button to Run the Normal Daily Process:</b><br>
&nbsp;&nbsp;&nbsp; Normally this process runs of the Timer Job at .... every Day.<br>
&nbsp;&nbsp;&nbsp;&nbsp; This proces goes to the BAPAHIST File to see which fiscal weeks were updated in the past 5 Days <br>
&nbsp;&nbsp;&nbsp;&nbsp; THEN: Deletes and Repopulates everything for each of those weeks which were found.<br>
        </td>
        <td class="td04160102">
          <%= HTMLHelpers.buttonSubmit("goDailyButton", "Go - Run Normal Daily Process") %>
        </td>
       </tr>
       <tr class="tr01">
        <td class="td04160105" colspan="2">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td class="td04160102"><b>Press this button to Rebuild the Entire work File:</b><br>
&nbsp;&nbsp;&nbsp; Deletes ALL Records from the Work File (WKPAUSAGE) rebuilds everything from the BAPAHIST and MITTRA Files.<br>
&nbsp;&nbsp;&nbsp;&nbsp; This process runs a separate SQL statement for the load of each YEAR/PERIOD.<br>
        </td>
        <td class="td04160102">
          <%= HTMLHelpers.buttonSubmit("goAllButton", "Go - Rebuild entire file") %>
        </td>
       </tr>  
       <tr class="tr01">
        <td class="td04160105" colspan="2">&nbsp;</td>
       </tr>   
<%
    }
%>                    
      </table>  
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table> 
  </form>  
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>