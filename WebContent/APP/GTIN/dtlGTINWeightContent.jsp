<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------  dtlGTINWeightContent.jsp  ------------------------------------------//
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
//  String errorPage = "/GTIN/dtlGTINWeightContent.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 GTINDetail thisGtinWeightContent = new GTINDetail();
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
        thisGtinWeightContent = dg.getDetailClass().getGtinDetail();
	}
 } 
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 } 
String mouseoverHelpWC = "Click here to see help documents."; 
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Net_Content" 
	     target="_blank" title="<%= mouseoverHelpWC %>">Net Content:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinWeightContent.getNetContent() %></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Net_Content_Units" 
	     target="_blank" title="<%= mouseoverHelpWC %>">Net Content Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinWeightContent.getNetContentUnitOfMeasure() %></td>  
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Net Weight" 
	     target="_blank" title="<%= mouseoverHelpWC %>">Net Weight:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinWeightContent.getNetWeight() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Gross Weight" 
	     target="_blank" title="<%= mouseoverHelpWC %>">Gross Weight:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinWeightContent.getGrossWeight() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Weight_Units" 
	     target="_blank" title="<%= mouseoverHelpWC %>">Weight Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinWeightContent.getWeightUnitOfMeasure() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>        
 </table>
 </body>
</html>