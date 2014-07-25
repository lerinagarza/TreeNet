<%@ page language="java" %>
<%@ page import = "com.treetop.app.inventory.InqInventory" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Inventory/inqFIFO.jsp -----------------------//
// Author   :  Teri Walton       5/12/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String errorPage = "Inventory/inqFIFO.jsp";
  String inqTitle = "FIFO List";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqInventory inqInv = new InqInventory();
 try
 {
	inqInv = (InqInventory) request.getAttribute("inqViewBean");
	if (inqInv.getRequestType().equals(""))
	   inqInv.setRequestType("inqFIFO");
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
 </head>
 <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form  name = "inqInv" action="/web/CtlInventoryNew?requestType=listFIFO" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!inqInv.getDisplayErrors().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102">&nbsp;</td>
        <td class="td03200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= inqInv.getDisplayErrors().trim() %></b></td>
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
         <%= HTMLHelpers.buttonGo("Get FIFO List") %>
        </td>  
       </tr>
       <tr class="tr00">
        <td class="td04160102" style="width:2%">&nbsp;</td>
        <td class="td04160102">
         <b>Customer Order Number:</b>
        </td>
        <td class="td03160102">&nbsp;
         <%= HTMLHelpersInput.inputBoxText("inqCustomerOrder", inqInv.getInqCustomerOrder(), "Customer Order Number", 10, 10, "N", "N") %>&nbsp;&nbsp;
         <b><%= inqInv.getInqCustomerOrderError() %></b>
        </td>
        <td class="td04160102" style="width:2%">&nbsp;</td>
       </tr>  
       <tr class="tr00">
        <td class="td05160102" style="width:2%">&nbsp;</td>
        <td class="td05160102" colspan = "2">
         <b>Information based on the following criteria: <br></b>
            &nbsp;&nbsp;The Balance ID must be in Released Status = '2'<br>
            &nbsp;&nbsp;The Balance ID must be Allocatable<br>
            &nbsp;&nbsp;The Last Sales Date must be Greater than the 'Ship Date' on the Order<br>
            &nbsp;&nbsp;Will Return 6 choices for each Sales order Line Item
        </td>
        <td class="td04160102" style="width:2%">&nbsp;</td>
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