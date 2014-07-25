<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------  dtlGTINRelationship.jsp  ------------------------------------------//
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
//  String errorPage = "/GTIN/dtlGTINRelationship.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 GTINDetail thisGtinRelationship = new GTINDetail();
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
        thisGtinRelationship = dg.getDetailClass().getGtinDetail();
	}
 } 
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 } 
String mouseoverHelpRel = "Click here to see help documents."; 
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Unit_Descriptor" 
	     target="_blank" title="<%= mouseoverHelpRel %>">Product Type:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinRelationship.getTradeItemUnitDescriptor() %></td> 
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Total_Quantity_Of_Next_Lower_Level_Trade_Item" 
	     target="_blank" title="<%= mouseoverHelpRel %>">Total Quantity Of Next Lower Level Trade Item:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinRelationship.getQtyOfNextLowerLevelTradeItem() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Quantity_Of_Children" 
	     target="_blank" title="<%= mouseoverHelpRel %>">Quantity Of Children:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinRelationship.getQtyChildren() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Quantity_Of_Layers_Contained_In_A_Trade_Item" 
	     target="_blank" title="<%= mouseoverHelpRel %>">Quantity Of Layers Contained In A Trade Item (HI):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinRelationship.getQtyCompleteLayers() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>        
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Quantity_Of_Trade_Items_Contained_In_A_Complete_Layer" 
	     target="_blank" title="<%= mouseoverHelpRel %>">Quantity Of Trade Items Contained In A Complete Layer (TI):</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinRelationship.getQtyItemsPerCompleteLayer() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>       
 </table>
 </body>
</html>