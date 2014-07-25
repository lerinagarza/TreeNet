<%@ page language = "java" %>
<%@ page import="com.treetop.app.item.InqCodeDate" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//------  dtlCodeDate.jsp  --------------------//
//  Author :  Teri Walton  12/29/03
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//  10/16/08    TWalton		Changed Stylesheet and Data Access to NEW Box.
//   3/03/04    TWalton     Changed comments and images for 5.0 server.
//------------------------------------------------------//
 String errorPage = "Item/dtlCodeDate.jsp";
 String dtlTitle = "Code Date Information";  
 // Bring in the Build View Bean.
 // Selection Criteria
 String returnMessage = "";
 InqCodeDate inqCodeDate = new InqCodeDate();
 try
 {
	inqCodeDate = (InqCodeDate) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
   request.setAttribute("title",dtlTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlCodeDate?requestType=inq\">Choose Again");
   request.setAttribute("extraOptions", setExtraOptions.toString());      
//**************************************************************************//
%>
<html>
 <head>
  <title><%= dtlTitle %></title>
  <%= JavascriptInfo.getExpandingSectionHead("Y", 1, "Y", 1) %>   
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <table class="table00" cellspacing="0">
   <tr class="tr00">
    <td style="width:5%" rowspan = "10">&nbsp;</td>
    <td style="width:2%">&nbsp;</td>
    <td colspan = "2">&nbsp;</td>
    <td style="width:2%">&nbsp;</td>
    <td style="width:5%" rowspan = "10">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">Item Number:</td>
    <td class="td04140102"><b>
     <%= HTMLHelpersLinks.routerItem(inqCodeDate.getInqItem(), "a0414", "", "") %>
     &nbsp;&nbsp;&nbsp;&nbsp;<%= inqCodeDate.getItemWhse().getItemDescription().trim() %>
     </b></td>
    <td class="td04140102">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">Facility:</td>
    <td class="td04140102"><b>
     <%= inqCodeDate.getInqFacility() %>
     &nbsp;&nbsp;&nbsp;&nbsp;<%= inqCodeDate.getItemWhse().getFacilityName().trim() %>
     </b></td>
    <td class="td04140102">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">Warehouse:</td>
    <td class="td04140102"><b>
     <%= inqCodeDate.getInqWarehouse() %>
     &nbsp;&nbsp;&nbsp;&nbsp;<%= inqCodeDate.getItemWhse().getWarehouseName().trim() %>
     </b></td>
    <td class="td04140102">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">Date Chosen:</td>
    <td class="td04140102"><b>
     <%= inqCodeDate.getInqDateSelected() %>
     </b></td>
    <td class="td04140102">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">Shelf Life:</td>
    <td class="td04140102"><b>
     <%= inqCodeDate.getItemWhse().getDaysShelfLife() %>&nbsp;&nbsp;Days
     </b></td>
    <td class="td04140102">&nbsp;</td>
   </tr>
   <tr class="tr00">
    <td colspan="4">
     <table class="table0006" cellspacing="0" >
      <tr class="tr00">
       <td style="width:5%" rowspan = "2">&nbsp;</td>
       <td class="td04280102" style="text-align:center">&nbsp;
        <b><%= inqCodeDate.getBestByValue() %></b>
       </td>
       <td style="width:5%" rowspan = "2">&nbsp;</td>
      </tr>
      <tr class="tr00">
       <td class="td04280102" style="text-align:center">&nbsp;</td>
      </tr>     
     </table>    
    </td>
   </tr>
   <tr>
    <td colspan="4">
     <table class="table00" cellspacing="0" cellpadding="2">
      <tr class="tr02">
       <td class="td03141405">
        <%= JavascriptInfo.getExpandingSection("C", "GTIN Family Tree", 0, 1, 3, 1, 0) %>
        <%@ include file="../../APP/GTIN/dtlGTINFamilyTree.jsp" %>   
        </span>    
       </td>
      </tr> 
     </table>
    </td>
   </tr>    
  </table>
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
 </body>
</html>