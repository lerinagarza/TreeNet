<%@ page language="java" %>
<%@ page import = "com.treetop.app.coa.InqCOA" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/COA/inqCOA.jsp -----------------------//
// Prototype:  Charlena Paschen  06/04/03 (jsp)
// Author   :  Teri Walton       11/05/03 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//  9/5/07     TWalton 		 Rewrite with Movex  
//------------------------------------------------------------//
  String errorPage = "COA/inqCOA.jsp";
  String inqTitle = "Building a COA";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqCOA inqCOA = new InqCOA();
 try
 {
	inqCOA = (InqCOA) request.getAttribute("inqViewBean");
	if (inqCOA.getEnvironment().equals("TST"))
	   inqTitle = inqTitle + " TST Environment";
	if (inqCOA.getRequestType().equals(""))
	   inqCOA.setRequestType("inquiry");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",inqTitle);
   
//*****************************************************************************
%>
<html>
 <head>
   <title><%= inqTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
   <%= JavascriptInfo.getExpandingSectionHead("Y", 3, "Y", 3) %>     
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
  //*********************************************************************
   String formName = "inqCOAs";
   request.setAttribute("formName", formName);
%>
  <form  name = "<%= formName %>" action="/web/CtlCOANew" method="get">
  <%= HTMLHelpersInput.inputBoxHidden("environment", inqCOA.getEnvironment()) %>
  <%= HTMLHelpersInput.inputBoxHidden("requestType", "build") %>
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!inqCOA.getDisplayErrors().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102">&nbsp;</td>
        <td class="td03200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= inqCOA.getDisplayErrors().trim() %></b></td>
        <td class="td03200102">&nbsp;</td>
       </tr>    
<%
   }
%>       
       <tr class="tr02">
        <td class="td0420" colspan = "2">
         <b> Choose:</b>
        </td>
        <td class="td0420" colspan = "2"><b>and then press</b>
         <%= HTMLHelpers.buttonGo("Build COA") %>
        </td>  
       </tr>
       <tr class="tr00">
        <td class="td04160102" style="width:2%">&nbsp;</td>
        <td class="td04160102">
         <b>Customer Order Number:</b>
        </td>
        <td class="td03160102">&nbsp;
         <%= HTMLHelpersInput.inputBoxText("inqSalesOrder", inqCOA.getInqSalesOrder(), "Sales Order Number", 10, 10, "N", "N") %>&nbsp;&nbsp;
         <b><%= inqCOA.getInqSalesOrderError() %></b>
        </td>
        <td class="td04160102" style="width:2%">&nbsp;</td>
       </tr>   
<%
   // Until the DO is ready this needs to stay commented out
 //  if (0 == 1)
 //  {
%>        
       <tr class="tr00">
        <td class="td05200102">&nbsp;</td>
        <td class="td05200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>OR</b></td>
        <td class="td05200102">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">&nbsp;</td>
        <td class="td04160102">
         <b>Distribution Order Number:</b>
        </td>
        <td class="td03160102">&nbsp;
         <%= HTMLHelpersInput.inputBoxText("inqDistributionOrder", inqCOA.getInqDistributionOrder(), "Distribution Order Number", 10, 10, "N", "N") %>&nbsp;&nbsp;
         <b><%= inqCOA.getInqDistributionOrderError() %></b>
        </td>
        <td class="td04160102">&nbsp;</td>
       </tr> 
<%
 // }
  // Until the Lot number is ready this needs to stay commented out
//  if (inqCOA.getEnvironment().trim().equals("TST"))
//  {
%>              
       <tr class="tr00">
        <td class="td05200102">&nbsp;</td>
        <td class="td05200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>OR</b></td>
        <td class="td05200102">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">&nbsp;</td>
        <td class="td04160102">
         <b>Lot Number:</b>
        </td>
        <td class="td03160102">&nbsp;
         <%= HTMLHelpersInput.inputBoxText("inqLot", inqCOA.getInqLot(), "Lot Number", 12, 12, "N", "N") %>&nbsp;&nbsp;
         <b><%= inqCOA.getInqLotError() %></b>	
        </td>
        <td class="td04160102">&nbsp;</td>
       </tr>     
<%
 //  }
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