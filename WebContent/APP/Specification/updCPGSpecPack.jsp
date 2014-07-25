<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%
//---------------- updCPGSpec.jsp -------------------------------------------//
//
//    Author :  Teri Walton  6/04/02                     
//      moved to Production 12/19/02
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//   1/6/09    TWalton    Changed to point to NEW Stylesheet,  Broke up the page
//                                  Changed to the Standard
//    2/25/04    TWalton    Changed comments and images for 5.0 server.
//   06/02/03    cpaschen   Update with include header/footer 
//   05/20/03    cpaschen   update presentation style     
//   01/10/03    TWalton    Change the Way Test Brix is Done 
//   12/19/02    TWalton    Changed to use new improved files   
//--------------------------------------------------------------------------//
//**************************************************************************//
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdSpecification usPack = new UpdSpecification();
 try
 {
	usPack = (UpdSpecification) request.getAttribute("updViewBean");
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
 </head>
 <body>
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr>
    <td rowspan="15" style="width:2%">&nbsp;</td>
    <td class="td04140102"><acronym title="Date Coding Information">Date Coding Information</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("codingInformation", usPack.getCodingInformation(), 3, 80, 512, usPack.getReadOnly()) %></td>
    <td rowspan="15" style="width:2%">&nbsp;</td>
   </tr>
   <tr>
    <td class="td04140102"><acronym title="Case Code Print">Case Code Print</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("caseCodePrint", usPack.getCaseCodePrint(), 3, 80, 512, usPack.getReadOnly()) %></td>
   </tr>	 
   <tr>
    <td class="td04140102"><acronym title="Case Print Line 1">Case Print Line 1</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("casePrintLine1", usPack.getCasePrintLine1(), 3, 80, 512, usPack.getReadOnly()) %></td>
   </tr>	 
   <tr>
    <td class="td04140102"><acronym title="Case Print Line 2">Case Print Line 2</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("casePrintLine2", usPack.getCasePrintLine2(), 3, 80, 512, usPack.getReadOnly()) %></td>
   </tr>	
      <tr>
    <td class="td04140102"><acronym title="Case Print Line General">Case Print Line General</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("casePrintGeneral", usPack.getCasePrintGeneral(), 3, 80, 512, usPack.getReadOnly()) %></td>
   </tr>	 
   <tr>
    <td class="td04140102"><acronym title="Storage Conditions">Storage Conditions</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("storageConditions", usPack.getStorageConditions(), 3, 80, 512, usPack.getReadOnly()) %></td>
   </tr>	 
   <tr>
    <td class="td04140102"><acronym title="Special Requirements">Special Requirements</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("specialRequirements", usPack.getSpecialRequirements(), 5, 80, 4096, usPack.getReadOnly()) %></td>
   </tr>	 
   <tr>
    <td class="td04160102"><acronym title="Length of the Case">Length</acronym></td>
    <td class="td04160102" colspan="3">&nbsp;<%= HTMLHelpersInput.inputBoxNumber("length", usPack.getLength(), "Length of Case", 7, 7, "N", usPack.getReadOnly()) %></td>
   </tr>    
   <tr>
    <td class="td04140102"><acronym title="Width of the Case">Width</acronym></td>
    <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputBoxNumber("width", usPack.getWidth(), "Width of Case", 7, 7, "N", usPack.getReadOnly()) %></td>
    <td class="td04140102" style="width:2%">&nbsp;</td>
    <td class="td04140102" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></td>
   </tr>	     
   <tr>
    <td class="td04140102"><acronym title="Height of the Case">Height</acronym></td>
    <td class="td04140102" colspan="3">&nbsp;<%= HTMLHelpersInput.inputBoxNumber("height", usPack.getHeight(), "Height of Case", 7, 7, "N", usPack.getReadOnly()) %></td>
   </tr>      
   <tr>
    <td class="td04140102"><acronym title="Slip Sheet Information">Slip Sheet Information</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxText("slipSheetInformation", usPack.getSlipSheetInformation(), "Slip Sheet Information", 75, 75, "N", usPack.getReadOnly()) %></td>
   </tr>	  
   <tr>
    <td class="td04140102"><acronym title="Stretch Wrap Information">Stretch Wrap Information</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.dropDownYesNo("stretchWrap", usPack.getStretchWrap(), "", usPack.getReadOnly()) %><%= HTMLHelpersInput.inputBoxText("stretchWrapDescription", usPack.getStretchWrapDescription(), "Stretch Wrap Information", 50, 50, "N", usPack.getReadOnly()) %></td>
<%
   if (usPack.getReadOnly().trim().equals("Y"))
   {
%>   
  <%= HTMLHelpersInput.inputBoxHidden("stretchWrap", usPack.getStretchWrap()) %>
  <%= HTMLHelpersInput.inputBoxHidden("shrinkWrap", usPack.getShrinkWrap()) %>
<%
   }
%>    
   </tr>	
   <tr>
    <td class="td04140102"><acronym title="Shrink Wrap Information">Shrink Wrap Information</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.dropDownYesNo("shrinkWrap", usPack.getShrinkWrap(), "", usPack.getReadOnly()) %><%= HTMLHelpersInput.inputBoxText("shrinkWrapDescription", usPack.getShrinkWrapDescription(), "Shrink Wrap Information", 50, 50, "N", usPack.getReadOnly()) %></td>
   </tr>   
   <tr>
    <td class="td04140102"><acronym title="Additional Comments Related to Packing">Additional Comments</acronym></td>
    <td class="td04140102" colspan = "3">&nbsp;<%= HTMLHelpersInput.inputBoxTextarea("packComments", usPack.getPackComments(), 2, 80, 512, usPack.getReadOnly()) %></td>
   </tr>	 
  </table>
 </body>
</html>