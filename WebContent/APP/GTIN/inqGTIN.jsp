<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%

//------- inqGTIN.jsp -----------------------//
//  Inquiry JSP
//   Author :  Charlena Paschen  06/02/04
//   Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  8/23/05   TWalton     Rename from inqUCCNet, and use View Beans
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//------------------------------------------------------------//
  String errorPage = "/GTIN/inqGTINNew.jsp";
  String inquiryTitle = "Global Trade Item Number Inquiry";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqGTIN ig = new InqGTIN();
 try
 {
	ig = (InqGTIN) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",inquiryTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlGTIN?requestType=add\">Add New GTIN");
					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());         
//*****************************************************************************
%>
<html>
 <head>
        <title><%= inquiryTitle %></title>
<%
  // These JavascriptInfo methods return strings of javascript code 
  //    to be used on this page.
%>        
    <%= JavascriptInfo.getClickButtonOnlyOnce() %>
    <%= JavascriptInfo.getChangeSubmitButton() %>    
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include> 
<%
  try
  {
%>  
<table class="table00" cellspacing="0" style="width:100%">
 <tr>
  <td style="width:3%">&nbsp;</td>
  <td>
   <table class="table01" cellspacing="0" style="width:100%">
   <form name = "inqNumbers" action="/web/CtlGTIN" method="post">
    <%= HTMLHelpersInput.inputBoxHidden("requestType", ig.getRequestType()) %>
    <tr class="tr02">
     <td style="width:3%">&nbsp;</td>
     <td class="td0516" colspan="2"><b> Search On: </b>
     </td>
     <td class="td0516" style="text-align:right" colspan="3"><b>and then press</b>
       <%= HTMLHelpers.buttonGo("") %>
     </td>  
     <td style="width:3%">&nbsp;</td> 
    </tr>
    <tr class="tr00">
     <td class="td04140102" rowspan="10">&nbsp;</td>
     <td class="td04140102" style = "width:1%">&nbsp;</td>
     <td class="td04140102">
      Choose View:
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= InqGTIN.buildDropDownView("") %>
     </td>
     <td class="td04140102" style = "width:1%">&nbsp;</td>
     <td class="td04140102" rowspan="10">&nbsp;</td> 
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">
      <acronym title="Input any part of a resource.
The search will search the WHOLE resource and bring back any match, anywhere in the field.">Item Number:</acronym>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= HTMLHelpersInput.inputBoxText("inqResource", ig.getInqResource(), "Item Number", 11, 10, "N", "") %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">
      <acronym title="Input any part of a GTIN Number.
The search will search the WHOLE GTIN Number and bring back any match, anywhere in the field. "> Global Trade Item Number (GTIN):</acronym>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= HTMLHelpersInput.inputBoxText("inqGTIN", ig.getInqGTIN(), "GTIN Number", 15, 15, "N", "") %><br>
      &nbsp;&nbsp;&nbsp;&nbsp;<acronym title="By clicking this the list will display ALL the GTIN's in the chosen family tree.">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqShowTree", ig.getInqShowTree(), "N") %>&nbsp;
      See Family Tree GTIN's</acronym>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">
      <acronym title="Input any part of a GTIN Description.
The search will search any part of the description and bring back any match, anywhere in the field. "> GTIN Name:</acronym>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= HTMLHelpersInput.inputBoxText("inqDescriptionLong", ig.getInqDescriptionLong(), "GTIN Name", 25, 25, "N", "") %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">UPC Code:</td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= HTMLHelpersInput.inputBoxText("inqUPCCode", ig.getInqUPCCode(), "UPC Code", 15, 15, "N", "") %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr>
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">Load:</td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= ig.getDropDownPublish() %>
     </td>
     <td class="td04120102">&nbsp;</td>
    </tr>      
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">How to Publish:</td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= ig.getDropDownPublishType() %>
     </td>
     <td class="td04120102">&nbsp;</td>
    </tr>      
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">Product Type:</td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= ig.getDropDownTIUD() %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr>   
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102">
      Brand Name:
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= ig.getDropDownBrandName() %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr>                   
   <%= HTMLHelpersInput.endForm() %>
  </table>
  </td>
  <td style="width:3%">&nbsp;</td>
 </tr> 
</table>  
<%
  }
  catch(Exception e)
  {
    System.out.println(JSPExceptionMessages.PageExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.PageExceptMsg(errorPage));
  }
%>
  <%@ include file="../../Include/footer.jsp" %>
   </body>
</html>