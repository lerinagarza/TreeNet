<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.UpdRawFruitLoad" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------- updRawFruit.jsp ------------------------------//
//  Author :  Teri Walton  11/5/08
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Choose Raw Fruit Load";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdRawFruitLoad urfr = new UpdRawFruitLoad();
 try
 {
	urfr = (UpdRawFruitLoad) request.getAttribute("updViewBean");
 }
 catch(Exception e)
 {
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",updTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket\">Search for a Load (to update)");
   request.setAttribute("extraOptions", setExtraOptions.toString());    
//*****************************************************************************
%>
<html>
   <head>
      <title><%= updTitle %></title>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
   </head>
   <body>
    <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form name="updRawFruitLoad" action="/web/CtlRawFruit?requestType=update" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("scheduledLoadNumber", urfr.getScheduledLoadNumber()) %>
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!urfr.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= urfr.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
       <tr class="tr02">
        <td class="td0418" style="width:5%">&nbsp;</td>
        <td class="td0418">
         <b>Choose for Update:&nbsp;&nbsp;&nbsp;<font style="color:#990000"><i>All Fields are Required!</i></font>
        </td>
        <td class="td0418" style="text-align:center">
          <%= HTMLHelpers.buttonGo("") %>
          <input type="submit" name="quickEntry" value="Quick Entry">
        </td>
        
        <td class="td0418" style="width:5%">&nbsp;</td>
       </tr>   
       
       
       
       <tr class="tr00">
        <td rowspan="8">&nbsp;</td>       
        <td class="td04160102">
            <b><acronym title="Scale Ticket Number: is based on ONE load across the scale">
                Scale Ticket Number:
            </acronym></b>
        </td>
        <td class="td03160102">
		  <%= HTMLHelpersInput.inputBoxText("scaleTicket", urfr.getScaleTicket(), "Scale Ticket", 9, 9, "Y", "N") %>
        </td>

        <td rowspan="8">&nbsp;</td>      
       </tr>
       
       
       
   </table>
   </form>   
<jsp:include page="../../Include/footer.jsp"></jsp:include>

   </body>
</html>