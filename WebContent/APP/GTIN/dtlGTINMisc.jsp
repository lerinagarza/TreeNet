<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------  dtlGTINMisc.jsp  ------------------------------------------//
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
//  String errorPage = "/GTIN/dtlGTINMisc.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 GTINDetail thisGtinMisc = new GTINDetail();
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
        thisGtinMisc = dg.getDetailClass().getGtinDetail();
	}
 } 
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }   
String mouseoverHelpMisc = "Click here to see help documents."; 
  
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
<%
  if (0 == 1)
  { // Hide no longer using these fields
%> 
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Delivery_Method_Indicator" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Delivery Method Indicator:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getDeliveryMethodIndicator() %></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Barcode_Symbology_Is_Derivable" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Barcode Symbology Is Derivable:</a>
   </td>		
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getIsBarcodeSymbologyDerivable() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr> 
<%
  }
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Effective_Date" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Effective Date:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getEffectiveDate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Catalogue_Item_State" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Catalogue Item State:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getCatalogItemState() %></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Country_Of_Origin_1" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Trade Item Country Of Origin (1):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getCountryOfOrigin() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_Information_Private" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Is Trade Item Information Private:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getIsInformationPrivate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Target_Market_Country_Code" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Target Market:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getTargetMarketCountryCode() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>      
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Publication_Date" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Trade Item Publication Date:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getPublicationDate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Publish_To_UCCNet" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Load To UCCNet:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getPublishToUCCNet() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <acronym title = "How this item should be published to UCCNet.">How Loaded UCCNet:</acronym>
   </td>
   <td class="td04140102">&nbsp;
<%
   if (thisGtinMisc.getStatus().trim().equals("M"))
     out.println("Modify");
   if (thisGtinMisc.getStatus().trim().equals("A"))
     out.println("Add");
%>   
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>        
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Start_Availability_Date" 
	     target="_blank" title="<%= mouseoverHelpMisc %>">Trade Item Start Availability Date:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinMisc.getStartAvailabilityDate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
 </table>
 </body>
</html>