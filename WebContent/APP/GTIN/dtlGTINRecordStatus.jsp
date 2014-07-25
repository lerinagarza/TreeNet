<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------  dtlGTINRecordStatus.jsp  ------------------------------------------//
//   To Be included in the dtlGTIN Page
//
//   Author :  Teri Walton  8/30/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
//  String errorPage = "/GTIN/dtlGTINRecordStatus.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 GTINDetail thisGtinRecordStatus = new GTINDetail();
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
	   thisGtinRecordStatus = dg.getDetailClass().getGtinDetail();
	}
 } 
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }    
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%"><b>Last Change Date:</b></td>
   <td class="td04140102">&nbsp;<%= thisGtinRecordStatus.getLastChangeDate() %></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Last Update Date:</b></td>
   <td class="td04140102">&nbsp;<%= thisGtinRecordStatus.getLastUpdateDate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Last Update Time:</b></td>
   <td class="td04140102">&nbsp;<%= thisGtinRecordStatus.getLastUpdateTime() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Last Update User:</b></td>
   <td class="td04140102">&nbsp;<%= thisGtinRecordStatus.getLastUpdateUser() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Last Update Workstation:</b></td>
   <td class="td04140102">&nbsp;<%= thisGtinRecordStatus.getLastUpdateWorkstation() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>      
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Record Status:</b></td>
   <td class="td04140102">&nbsp;<%= thisGtinRecordStatus.getStatus() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>   
 </table>
 </body>
</html>