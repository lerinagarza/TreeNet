<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import="com.treetop.businessobjectapplications.BeanSpecification" %>
<%
//---------------- dtlCPGSpecOther.jsp -------------------------------------------//
//
//    Author :  Teri Walton  10/31/08
//   CHANGES: // Create a new section with additional information included
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 SpecificationNEW thisSpecOther = new SpecificationNEW();
 try
 {
	DtlSpecification ds = (DtlSpecification) request.getAttribute("dtlViewBean");
	thisSpecOther = ds.getBeanSpec().getSpecClass();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
%>
<html>
 <head>
 </head>
 <body>
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr>
	 <td class="td04120102" style="width:15%">Lab Book ID:</td>
	 <td class="td04120102" style="width:20%">&nbsp;<%= thisSpecOther.getLabBookID() %></td>
     <td class="td04120102" style="width:2%">&nbsp;</td>
	 <td class="td04120102" style="width:15%">Spec Additional Comments:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecOther.getHeadComment() %></td>
	</tr>
	<tr>
	 <td class="td04120102">Last Update User:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecOther.getLastUpdateUser() %></td>
     <td class="td04120102" colspan="3">&nbsp;</td>
    </tr>
   	<tr>
	 <td class="td04120102">Last Update Date:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecOther.getLastUpdateDate() %></td>
     <td class="td04120102" colspan="3">&nbsp;</td>
    </tr>
   	<tr>
	 <td class="td04120102">Last Update Time:</td>
	 <td class="td04120102">&nbsp;<%= thisSpecOther.getLastUpdateTime() %></td>
     <td class="td04120102" colspan = "3">&nbsp;</td>
    </tr>
  </table>
 </body>
</html>