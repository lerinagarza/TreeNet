<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------  dtlGTINDimension.jsp  ------------------------------------------//
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
  String errorPageTable = "/GTIN/dtlGTINDimension.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 GTINDetail thisGtinDimension = new GTINDetail();
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
        thisGtinDimension = dg.getDetailClass().getGtinDetail();
	}
 } 
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }    
String mouseoverHelpDim = "Click here to see help documents."; 
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Height" 
	     target="_blank" title="<%= mouseoverHelpDim %>">Height:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinDimension.getHeight() %></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Width" 
	     target="_blank" title="<%= mouseoverHelpDim %>">Width:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinDimension.getWidth() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Depth" 
	     target="_blank" title="<%= mouseoverHelpDim %>">Depth:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinDimension.getDepth() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Linear_Units" 
	     target="_blank" title="<%= mouseoverHelpDim %>">Linear Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinDimension.getLinearUnitOfMeasure() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Volume" 
	     target="_blank" title="<%= mouseoverHelpDim %>">Volume:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinDimension.getVolume() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>        
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Volume_Units" 
	     target="_blank" title="<%= mouseoverHelpDim %>">Volume Units (UOM):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinDimension.getVolumeUnitOfMeasure() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>    
 </table>
 </body>
</html>