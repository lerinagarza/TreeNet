<%@ page language="java" %>
<%@ page import = "com.treetop.app.finance.BldFinance" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/Finance/rptStateFees.jsp -----------------------//
// Author   :  Teri Walton       3/20/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Run the State Fees Report";  
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
 String dd1name = "rptDate";
 String dd2name = "rptTime";
 String script = "";
 String dd = "";
 try
 {
  Vector dualDD = bf.buildDropDownDateTime();
   dd = (String) dualDD.elementAt(0);
   script = (String) dualDD.elementAt(1);
 }
 catch(Exception e)
 {}
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",inqTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlFinance?requestType=rptStateFees\">Reset Page");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
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
 <body onload="change<%= dd1name %>(document.forms['rptStateFees'].<%= dd1name %>)">
<%
   }
%> 
<jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
//    if (dd == null || dd.trim().equals(""))
//    {
//
// <table class="table01" cellspacing="0" style="width:100%">
//   <tr>
//     <td> 
//       The Information Must be Built before a Report Can be Pulled
//     </td>
//    </tr>
//   </table>    

//   }
//   else
//   {
%>   
  <form  name = "rptStateFees" action="/web/CtlFinance?requestType=bldReportStateFees" method="post">
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
        <td class="td04180105" colspan = "2">
         <b> Build the State Fees Report:</b>
        </td>
        <td class="td0418" style="width:3%">&nbsp;</td>
       </tr>   
       <tr class="tr00">
        <td class="td0418" rowspan="7">&nbsp;</td>
        <td class="td04160102" style="width:30%">Choose a Report:</td>
        <td class="td04160102"><%= bf.buildDropDownChosenReport(bf.getRequestType()) %></td>
        <td class="td0418" rowspan="7">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">Choose a Month:</td>
        <td class="td04160102"><%= bf.buildDropDownMonth() %></td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">Choose a Report Type:</td>
        <td class="td04160102"><%= bf.buildDropDownReportType() %></td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">Choose a Report Run Type:</td>
        <td class="td04160102"><%= bf.buildDropDownRunType() %></td>
       </tr>    
<%
   if (dd != null && !dd.trim().equals(""))
   {
%>
       <tr class="tr00">
        <td class="td04160102">Choose a Report Date: <br>&nbsp;&nbsp;Only needed if you choose 'Posted':</td>
        <td class="td03160102"><%= dd %>&nbsp;&nbsp;
<%
   if (!bf.getRptDateError().trim().equals(""))
      out.println(bf.getRptDateError());
%>        
        </td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">Choose a Report Time<br>&nbsp;&nbsp; Tied to date field:</td>
        <td class="td03160102"><select name="<%= dd2name %>"></td>
       </tr>               
<%
   }
%> 
        <tr class="tr01">
        <td class="td04160102" style="text-align:center" colspan="2">
          <%= HTMLHelpers.buttonSubmit("rptStateFees", "Submit the Selected Report") %>
        </td>
       </tr>      
      </table>  
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table> 
  </form>  
<%
//   }
%>  
  <br>  
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>