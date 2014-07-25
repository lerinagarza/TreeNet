<%@ page language="java" %>
<%@ page import = "com.treetop.app.rawfruit.BldRawFruit" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/RawFruit/bldLotAttributeFile.jsp -----------------------//
// Author   :  Teri Walton      6/10/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Populate the Raw Fruit Attribute File";  
 // Bring in the Build View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 BldRawFruit brf = new BldRawFruit();
 try
 {
	brf = (BldRawFruit) request.getAttribute("bldViewBean");
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
  <form name = "bldWorkFile" action="/web/CtlRawFruit?requestType=bldLotAttribute" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!brf.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= brf.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
      <tr class="tr02">
        <td class="td04160102"><b>Run Process to Populate the Work File that Holds Main Attribute Values for Raw Fruit Items.</b></td>
       </tr>
       <tr class="tr01">
        <td class="td0416" style="text-align:center">
          <%= HTMLHelpers.buttonSubmit("goButton", "Go - Process Records") %>
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