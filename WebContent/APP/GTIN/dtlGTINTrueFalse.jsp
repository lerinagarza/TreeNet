<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------  dtlGTINTrueFalse.jsp  ------------------------------------------//
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
//  String errorPage = "/GTIN/dtlGTINTrueFalse.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 GTINDetail thisGtinTrueFalse = new GTINDetail();
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
        thisGtinTrueFalse = dg.getDetailClass().getGtinDetail();
	}
 } 
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }   
String mouseoverHelpTF = "Click here to see help documents."; 
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" style="width:30%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Consumer_Unit" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item A Consumer Unit:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsConsumerUnit() %></td>  
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" style="width:25%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_An_Orderable_Unit" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item An Orderable Unit:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsOrderableUnit() %></td>		
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" style="width:25%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Base_Unit" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item A Base Unit:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsBaseUnit() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Dispatch_Unit" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item A Dispatch Unit:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsDispatchUnit() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_An_Invoice_Unit" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item An Invoice Unit:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsInvoiceUnit() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>      
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Variable_Unit" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item A Variable Unit:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsVariableUnit() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_As_Recyclable" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Packaging Marked As Recyclable:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsPackagingMarkedRecyclable() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_As_Returnable" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Packaging Marked As Returnable:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsPackagingMarkedReturnable() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_With_Expiration_Date" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Packaging Marked With Expiration Date:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsPackagingMarkedWithExpirationDate() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_With_Green_Dot" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Packaging Marked With Green Dot:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsPackagingMarkedWithGreenDot() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_With_Ingredients" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Packaging Marked With Ingredients:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsPackagingMarkedWithIngredients() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Net_Content_Declaration_Indicated" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Net Content Declaration Indicated:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsNetContentDeclarationIndicated() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Has_Batch_Number" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Trade Item Has Batch Number:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getHasBatchNumber() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Non_Sold_Trade_Item_Returnable" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Non Sold Trade Item Returnable:</a>
</td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsNonSoldReturnable() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_Marked_As_Recyclable" 
	     target="_blank" title="<%= mouseoverHelpTF %>">Is Trade Item Marked As Recyclable:</a>
   </td>
   <td class="td04140102">&nbsp;<%= thisGtinTrueFalse.getIsItemMarkedRecyclable() %></td>	
   <td class="td04140102">&nbsp;</td>
  </tr> 
 </table>
 </body>
</html>