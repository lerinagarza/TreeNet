<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%

//   ITEM & CLASSIFICATION
//---------------  updGTINBlue.jsp  ------------------------------------------//
//  Item & Classification
//   To Be included in the updGTIN Page
//
//   Author :  Teri Walton  9/8/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
//  String errorPage = "/GTIN/updGTINBlue.jsp";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdGTIN updGTINBlue = new UpdGTIN();
 try
 {
	updGTINBlue = (UpdGTIN) request.getAttribute("updViewBean");
 }
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }  
 String[] canUpdateBlue = (String[]) request.getAttribute("canUpdate");
 String roBlue = "Y";  
 if ((canUpdateBlue[0].equals("Y") ||
     canUpdateBlue[3].equals("Y")) && updGTINBlue.getPublishToUCCNet().equals(""))
     roBlue = "N"; 
String mouseoverHelpBlue = "Click here to see help documents.";     
     
%>
<html>
  <head>
  </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
	<tr>
	 <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Classification_Category_Code" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Alternate Item Classification Code:</a>
	 </td>
	 <td class="td04140102">&nbsp;
	   <%= UpdGTIN.buildDropDown("classificationCategoryCode", updGTINBlue.getClassificationCategoryCode().trim(), roBlue) %>
	 </td>
	 <td class="td04140102" style="width:2%">&nbsp;</td>
	</tr> 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Additional_Classification_Category_Code" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Global Item Classification Code:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%=HTMLHelpersInput.inputBoxText("additionalClassCategoryCode",
							updGTINBlue.getAdditionalClassCategoryCode(),
							"Global Item Classification Code", 15, 15, "", roBlue)%>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>      
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_With_Green_Dot" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Is Packaging Marked With Green Dot:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("hasGreenDot", "F", "Y") %>  
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>      
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Has_Batch_Number" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Trade Item Has Batch Number:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("hasBatchNumber", updGTINBlue.getHasBatchNumber(), roBlue) %> 
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr> 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_With_Ingredients" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Is Packaging Marked With Ingredients:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("hasIngredients", updGTINBlue.getHasIngredients(), roBlue) %> 
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_With_Expiration_Date" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Is Packaging Marked With Expiration Date:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("hasExpireDate", updGTINBlue.getHasExpireDate(), roBlue) %> 
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Net_Content_Declaration_Indicated" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Is Net Content Declaration Indicated:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("isNetContentDeclarationIndicated", updGTINBlue.getIsNetContentDeclarationIndicated(), roBlue) %> 
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>     
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_Marked_As_Recyclable" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Is Trade Item Marked As Recyclable:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("isItemRecyclable", "F", "Y") %> 
   </td>	
   <td class="td04140102">&nbsp;</td>
  </tr> 
<%
  if (1 == 0)
  { // Hide No longer use field
%>    
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Barcode_Symbology_Is_Derivable" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Barcode Symbology Is Derivable:</a>
   </td>		
   <td class="td04140102">&nbsp;
     <%= HTMLHelpersInput.inputBoxText("barcodeSymbology", updGTINBlue.getBarcodeSymbology(), "Barcode Symbology", 1, 1, "", roBlue) %>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>   
<%
  }
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Country_Of_Origin_1" 
	     target="_blank" title="<%= mouseoverHelpBlue %>">Trade Item Country Of Origin (1):</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildDropDown("countryOfOrigin", updGTINBlue.getCountryOfOrigin().trim(), roBlue)%>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>   
 </table>
 </body>
</html>