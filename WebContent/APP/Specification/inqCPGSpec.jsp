<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%
//------- inqCPGSpec.jsp -----------------------//
//  Inquiry JSP
//   Author :  Teri Walton  10/20/08
//   Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  10/20/08   TWalton    Replaced, inqTTSpecs, using new Style and Object Design
//------------------------------------------------------------//
  String errorPage = "/Specification/inqCPGSpecs.jsp";
  String inquiryTitle = "Request a List of CPG Specifications";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqSpecification is = new InqSpecification();
 try
 {
	is = (InqSpecification) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",inquiryTitle);
//   StringBuffer setExtraOptions = new StringBuffer();
//   setExtraOptions.append("<option value=\"/web/CtlGTIN?requestType=add\">Add New GTIN");
					                              
 // request.setAttribute("extraOptions", setExtraOptions.toString());         
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
   <form name = "inqCPGSpecs" action="/web/CtlSpecificationNEW?requestType=listCPGSpec" method="post">
    <tr class="tr02">
     <td class="td0516" colspan="2"><b> Search On: </b>
     </td>
     <td class="td0516" style="text-align:right" colspan="3"><b>and then press</b>
       <%= HTMLHelpers.buttonGo("") %>
     </td>  
    </tr>
    <tr class="tr00">
     <td class="td04140102" style = "width:1%">&nbsp;</td>
     <td class="td04140102"><b>
      <acronym title="Select a Item Type(MMITTY) - Found in Program: CRS040 - Tied to the Item Number: MMS001 -- Will only display Item Types assigned to Current Specifications">Item Type:</acronym>
      </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
       <%= is.buildDropDownItemType() %>
     </td>
     <td class="td04140102" style = "width:1%">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102"><b>
      <acronym title="Select any part of an Item Number(MMITNO), this will search the WHOLE item number and return any match, anywhere in the field - Found in Program: MMS001">Item Number:</acronym>
      </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= HTMLHelpersInput.inputBoxText("inqItemNumber", is.getInqItemNumber(), "Item Number", 11, 10, "N", "") %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102"><b>
      <acronym title="Select a Product Group(MMITCL), before M3 (Movex) this was Product Line - Found in Program: CRS035 - Tied to the Item Number: MMS001 -- Will only display Product Groups assigned to Current Specifications."> Product Group:</acronym>
      </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
        <%= is.buildDropDownProductGroup() %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102"><b>
      <acronym title="Select a Product Size(MMCFI1-User Defined Field 1) - Found in Program: CRS035 - Tied to the item Number: MMS001 -- Will only display Product Size assigned to Current Specifications.">Product Size:</acronym>
      </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
       <%= is.buildDropDownProductSize() %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr> 
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102"> <b>
<%=   HTMLHelpersLinks.basicLink("Formula Number:", "/web/JSP/TTSpecs/inqFormulaTTSpecs.jsp", "Click to Search for a Formula OR Select a Formula Number - Will only display Formula assigned to Current Specifications.", "", "") %>     
       </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= is.buildDropDownFormula() %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr>
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102"><b>
      <acronym title="Select any part of an Formula Name, this will search the WHOLE Formula Name and return any match, anywhere in the field.">Formula Name:</acronym>
      </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
       <%= HTMLHelpersInput.inputBoxText("inqFormulaName", is.getInqFormulaName(), "Formula Name", 21, 20, "N", "") %>
     </td>
     <td class="td04120102">&nbsp;</td>
    </tr>      
    <tr class="tr00">
     <td class="td04140102">&nbsp;</td>
     <td class="td04140102"><b>
      <acronym title="Status of the Specification, one Specification can have more than one Status.">Specification Status:</acronym>
      </b>
     </td>
     <td class="td04140102" colspan="2">&nbsp;
      <%= is.buildDropDownStatus() %>
     </td>
     <td class="td04140102">&nbsp;</td>
    </tr>                   
   </form>
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
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>