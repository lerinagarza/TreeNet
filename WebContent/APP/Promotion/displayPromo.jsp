<%@ page language="java" %>
<%@ page import = "com.treetop.app.promotion.InqPromotion" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- APP/Promotion/displayPromotion.jsp -----------------------//
// Author   :  Teri Walton       12/03/07 
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //    This Screen will Display Only!
 //     Information to Display
 InqPromotion inqPromo = new InqPromotion();
 try
 {
	inqPromo = (InqPromotion) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem("displayPromotion.jsp", e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg("displayPromotion.jsp"));
 }  
   String inqTitle = "Display the Next Promotion Number";
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",inqTitle);
//*****************************************************************************
%>
<html>
 <head>
   <title><%= inqTitle %></title>
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <table class="table01" cellspacing="0" style="width:100%">
   <tr class="tr01">
    <td>&nbsp;</td>  
   </tr>
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
       <tr class="tr02">
        <td class="td0420" colspan = "4">&nbsp;</td>  
       </tr>
       <tr class="tr00">
        <td class="td04160102" style="width:2%">&nbsp;</td>
        <td class="td04160102" style="width:30%">
         <b>Company:</b>
        </td>
        <td class="td04160102">&nbsp;
         <%= inqPromo.getInqCompany() %>
        </td>
        <td class="td04160102" style="width:2%">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">&nbsp;</td>
        <td class="td04160102">
         <b>Division:</b>
        </td>
        <td class="td04160102">&nbsp;
         <%= inqPromo.getInqDivision() %>
        </td>
        <td class="td04160102">&nbsp;</td>
       </tr>         
       <tr class="tr00">
        <td class="td04180102">&nbsp;</td>
        <td class="td04180102">
         <b>Promotion Number:</b>
        </td>
        <td class="td04180102">&nbsp;
         <b><%= inqPromo.getInqPromotion() %></b>
        </td>
        <td class="td04160102">&nbsp;</td>
       </tr>  
<%
  if (!inqPromo.getInqWarningMessage().trim().equals(""))
  { 
%>       
       <tr class="tr00">
        <td class="td04160102">&nbsp;</td>
        <td class="td03160102">
         <b>Warning Message:</b>
        </td>
        <td class="td03160102">&nbsp;
         <b><%= inqPromo.getInqWarningMessage() %></b>
        </td>
        <td class="td04160102">&nbsp;</td>
       </tr>   
<%
   }
%>    
      <tr class="tr02">
       <td class="td0420" colspan = "4">&nbsp;</td>  
      </tr>        
     </table>  
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table>  
  <br>  
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>