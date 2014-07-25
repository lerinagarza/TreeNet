<%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.treetop.SessionVariables, java.util.Arrays" %>
<!doctype html>
<jsp:include page="/view/template/head.jsp"></jsp:include>
<%  String environment = (String) request.getParameter("environment");
    if (environment == null || environment.equals("")) { 
        environment="PRD";
    }
    
    String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    boolean internal = false;
    if (roles != null && Arrays.asList(roles).contains("1")) {
        internal = true;
    }
%>
<%-- tpl:put name="headarea" --%>
<%@ page import = "
	com.treetop.app.quality.UpdSpecification,
	com.treetop.app.quality.GeneralQuality,
	com.treetop.businessobjects.QaSpecificationPackaging,
	com.treetop.businessobjects.QaSpecification,
	com.treetop.utilities.html.HTMLHelpersInput,
	com.treetop.utilities.html.HTMLHelpersLinks,
	com.treetop.utilities.html.HTMLHelpersMasking,
	com.treetop.utilities.html.DropDownSingle,
    java.util.Vector
   " %>
   
<%   
//  Will use the same screen for update/add/copy will be done as an update 
//----------------------- updSpecification.jsp ------------------------------//
//  Author :  Teri Walton  02/10/11
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//  09/10/13    TWalton	   Change Style - to new Sytle
//							   Spec Release II Changes (Fields, added Text Boxes)								 
//-----------------------------------------------------------------------//
  String updTitle = "Update Specification";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 String readOnlySpec = "N";
 UpdSpecification updSpec = new UpdSpecification();
 QaSpecificationPackaging dtlSpec = new QaSpecificationPackaging();
 String supersedesDate = "";
 try
 {
	updSpec = (UpdSpecification) request.getAttribute("updViewBean");
	updTitle = updTitle + "&nbsp;" + updSpec.getSpecNumber();
	if (!updSpec.getStatus().trim().equals("PD"))
	   readOnlySpec = "Y";
	request.setAttribute("readOnlySpec", readOnlySpec);
	dtlSpec = updSpec.getUpdBean().getSpecPackaging();
	
	if (!updSpec.getSupersedesDate().getDateFormatyyyyMMdd().trim().equals("0"))
	    supersedesDate = updSpec.getSupersedesDate().getDateFormatMMddyyyySlash().trim();

    if (((String) request.getAttribute("requestType")).equals("reviseSpec"))
       updSpec.setRevisionReason("");
	   
 }catch(Exception e){
 
 } 
%>    
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>


 <%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>

  <form  name = "updSpec" action="/web/CtlQuality?requestType=updSpec" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", updSpec.getEnvironment()) %> 
  <%= HTMLHelpersInput.inputBoxHidden("specNumber", updSpec.getSpecNumber()) %>  
  <%= HTMLHelpersInput.inputBoxHidden("originalStatus", updSpec.getOriginalStatus()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionDate", updSpec.getRevisionDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionTime", updSpec.getRevisionTime()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationDate", updSpec.getCreationDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationTime", updSpec.getCreationTime()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationUser", updSpec.getCreationUser()) %>
  <%= HTMLHelpersInput.inputBoxHidden("referenceSpecNumber", updSpec.getReferenceSpecNumber()) %>
  <%= HTMLHelpersInput.inputBoxHidden("referenceSpecRevisionDate", updSpec.getReferenceSpecRevisionDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("referenceSpecRevisionTime", updSpec.getReferenceSpecRevisionTime()) %>
  
 <table class="styled" style="width:100%">
<%
   if (!updSpec.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr>
        <td colspan = "5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= updSpec.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
  <tr>
   <td style="width:20%">&nbsp;&nbsp;<abbr title="Specification Type">Specification Type</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(GeneralQuality.getListType(), "specType", updSpec.getSpecType(), "None", "N", readOnlySpec, "50") %>&nbsp;<%= updSpec.getSpecTypeError().trim() %>&nbsp;</td>
   <td style="width:1%">&nbsp;</td>
   <td style="text-align:right; width:30%">
    <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   &nbsp;&nbsp;</td>
  </tr>
  <tr>
   <td style="width:20%">&nbsp;&nbsp;<abbr title="Specification Number: Program Code= <%= updSpec.getSpecNumber() %>">Specification Number</abbr></td>
   <td style="width:25%">&nbsp;<%= HTMLHelpersInput.inputBoxText("specName", updSpec.getSpecName(), "Specification Number", 20, 20, "Y", readOnlySpec) %>&nbsp;</td>
   <td>&nbsp;</td>
   <td style="width:20%">&nbsp;&nbsp;<abbr title="Date of Last Active Revision">Supersedes Date:</abbr></td>
   <td>&nbsp;<%= supersedesDate %></td>
  </tr>
  <tr>
   <td>&nbsp;&nbsp;<abbr title="Description of the Specification / Name">Specification Name:</abbr></td>
   <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("specDescription", updSpec.getSpecDescription(), "Specification Description-Name", 35, 50, "Y", readOnlySpec) %></td>  
   <td>&nbsp;</td>
   <td style="width:20%">&nbsp;&nbsp;<abbr title="Date and Time Revision was Created">Revision Date and Time:</abbr></td>
   <td>&nbsp;<%= GeneralQuality.formatDateForScreen(updSpec.getRevisionDate()) %>&nbsp;&nbsp;<%= GeneralQuality.formatTimeForScreen(updSpec.getRevisionTime()) %></td>
  </tr> 
  <tr>
   <td>&nbsp;&nbsp;<abbr title="Status - Specific to this revision -- you can only update information in Pending Status">Status of this Revision:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(GeneralQuality.getListStatus(), "status", updSpec.getStatus(), "None", "N", "N", "50") %></td>
   <td>&nbsp;</td>
   <td>&nbsp;&nbsp;<abbr title="User that Originally Created the Specification">Creation User:</abbr></td>
   <td>&nbsp;<%= GeneralQuality.findLongNameFromProfile(updSpec.getEnvironment(), updSpec.getCreationUser()) %></td>
  </tr>  
  <tr>
   <td>&nbsp;&nbsp;<abbr title="Scope - Specific to this Specification">Scope:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(GeneralQuality.getListScope(), "scopeCode", updSpec.getScopeCode(), "Choose One", "N", readOnlySpec, "50") %></td>
   <td>&nbsp;</td>
   <td>&nbsp;&nbsp;<abbr title="Date and Time Formula was Originally Created">Creation Date and Time:</abbr></td>
   <td>&nbsp;<%= GeneralQuality.formatDateForScreen(updSpec.getCreationDate()) %>&nbsp;&nbsp;<%= GeneralQuality.formatTimeForScreen(updSpec.getCreationTime()) %></td>
  </tr>
  <tr>
   <td>&nbsp;&nbsp;<abbr title="Origination - who Originated this specific Specification or this revision of the Specification">Origination:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(GeneralQuality.getListOrigination(), "originationUser", updSpec.getOriginationUser(), "Choose One", "N", readOnlySpec, "50") %></td>
   <td>&nbsp;</td>
   <td>&nbsp;&nbsp;<abbr title="Who last UPDATED/REVIEWED this Formula">User Who Last Reviewed:</abbr></td>
   <td>&nbsp;<%= GeneralQuality.findLongNameFromProfile(updSpec.getEnvironment(), updSpec.getUpdateUser()) %></td>
  </tr>
  <tr>
   <td>&nbsp;&nbsp;<abbr title="Approved By - Who will be tagged as Approving this specific revision of the Specification">By:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(GeneralQuality.getListApprovedByUser(), "approvedByUser", updSpec.getApprovedByUser(), "Choose One", "N", readOnlySpec, "50") %></td>
   <td>&nbsp;</td>
   <td>&nbsp;&nbsp;<abbr title="Date and Time this Specification was last UPDATED/REVIEWED">Last Review Date and Time:</abbr></td>
   <td>&nbsp;<%= GeneralQuality.formatDateForScreen(updSpec.getUpdateDate()) %>&nbsp;&nbsp;<%= GeneralQuality.formatTimeForScreen(updSpec.getUpdateTime()) %></td>
  </tr> 
  <tr>
   <td><abbr title="Customer Code -- Default to M3 Customer - Required if you have a Customer Number">Customer Type:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(GeneralQuality.getListCustomerCode(), "customerCode", updSpec.getCustomerCode(), "Choose One", "N", readOnlySpec, "50") %>&nbsp;<%= updSpec.getCustomerCodeError() %></td>     
   <td>&nbsp;</td>
<%
   if (updSpec.getReferenceSpecNumber().trim().equals("0"))
   {
%> 
   <td colspan="2">&nbsp;</td>
<%
   }else{
%>  
   <td colspan="2" style="text-align:center"><b>COPIED FROM</b></td>
<%
   }
%>    
  </tr> 
  <tr>
   <td><abbr title="Customer Number -- Not Required --">Customer Number:</abbr></td>
   <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("customerNumber", updSpec.getCustomerNumber(), "Customer Number", 10, 10, "N", readOnlySpec) %>&nbsp;<%= updSpec.getCustomerNumberError() %></td>      
   <td>&nbsp;</td>
<%
   if (updSpec.getReferenceSpecNumber().trim().equals("0"))
   {
%> 
   <td colspan="2">&nbsp;</td>
<%
   }else{
   String specURL = "/web/CtlQuality?requestType=dtlSpec&specNumber=" + updSpec.getReferenceSpecNumber() +
                       "&revisionDate=" + updSpec.getReferenceSpecRevisionDate() +
                       "&revisionTime=" + updSpec.getReferenceSpecRevisionTime();
   String specMouseover = "Click here to see the details of the 'Copied From' Specification";
%>     
   <td><abbr title="Specification Number that this Specification was Created From">Specification Number:</abbr></td>
   <td>&nbsp;<%= HTMLHelpersLinks.basicLink(updSpec.getReferenceSpecNumber(), specURL, specMouseover,"" , "") %>&nbsp;</td>
<%
   }
%>      
  </tr>	   
  <tr>
   <td><abbr title="Name of the Customer">Customer Name:</abbr></td>
   <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("customerName", updSpec.getCustomerName(), "Customer Name", 25, 50, "N", readOnlySpec) %>&nbsp;</td>    
   <td>&nbsp;</td>
<%
   if (updSpec.getReferenceSpecNumber().trim().equals("0"))
   {
%> 
   <td colspan="2">&nbsp;</td>
<%
   }else{
%>        
   <td><abbr title="Date and Time of Revision Copied From">Revision Date and Time:</abbr></td>
   <td>&nbsp;<%= GeneralQuality.formatDateForScreen(updSpec.getReferenceSpecRevisionDate()) %>&nbsp;&nbsp;<%= GeneralQuality.formatTimeForScreen(updSpec.getReferenceSpecRevisionTime()) %></td>
<%
   }
%>        
  </tr>	    
  <tr>
   <td><abbr title="M3 Item Number - tied to this specification">Item Number:</abbr></td>
   <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("itemNumber", updSpec.getItemNumber(), "Item Number", 12,12, "", readOnlySpec) %>&nbsp;<font style="color:black"><%= dtlSpec.getItemDescription() %></font><%= updSpec.getItemNumberError() %></td>   
   <td>&nbsp;</td>
   <td>Kosher Status:</td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdKosherStatus(), "kosherStatusCode", updSpec.getKosherStatusCode(), "Choose One", "N", readOnlySpec) %>&nbsp;</td>   
  </tr>	    
  <tr>
   <td><abbr title="Formula - tied to this specification">Formula Number:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdFormula(), "formulaNumber", updSpec.getFormulaNumber(), "Choose One", "N", readOnlySpec, "50") %>&nbsp;<%= updSpec.getFormulaNumberError() %></td>   
   <td>&nbsp;</td>
   <td>Kosher Symbol:</td>
   <td>&nbsp;
<%
   if (!updSpec.getUpdBean().getSpecPackaging().getKosherSymbol().trim().equals(""))
   {
%>    
    <img src="<%=updSpec.getUpdBean().getSpecPackaging().getKosherSymbol() %>" style="height:20px" />
<%
   }
%>   
   </td>   
  </tr>	
  <tr>
   <td>Cut Size of Product:</td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdCutSize(), "cutSize", updSpec.getCutSize(), "Choose One", "N", readOnlySpec, "50") %>&nbsp;</td>   
   <td>&nbsp;</td>
   <td>Country of Origin:</td>
   <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("countryOfOrigin", updSpec.getCountryOfOrigin().trim(), "Country of Origin", 40, 75, "Y", readOnlySpec) %>&nbsp;</td>   
  </tr>	 
  <tr>
   <td>Second Cut Size of Product:</td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdCutSize(), "cutSize2", updSpec.getCutSize2(), "Choose One", "N", readOnlySpec, "50") %>&nbsp;</td>   
   <td>&nbsp;</td>
   <td>Inline Filter Required:</td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdInlineSock(), "inlineSockRequired", updSpec.getInlineSockRequired(), "Choose One", "N", readOnlySpec) %>&nbsp;</td>   
  </tr>	 
  <tr>  
   <td>Screen Size:</td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdScreenSize(), "screenSize", updSpec.getScreenSize(), "Choose One", "N", readOnlySpec, "50") %>&nbsp;</td>   
   <td>&nbsp;</td>
   <td><abbr title="CIP - Clean in Place">CIP Type:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdCIPType(), "cipType", updSpec.getCipType(), "Choose One", "N", readOnlySpec) %>&nbsp;</td>   
  </tr>	
  <tr>  
   <td>&nbsp;</td>
   <td>&nbsp;</td>   
   <td>&nbsp;</td>
   <td><abbr title="Foreign Material Detection">Foreign Material Detection:</abbr></td>
   <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdForeignMatDetection(), "foreignMaterialDetection", updSpec.getForeignMaterialDetection(), "Choose One", "N", readOnlySpec) %>&nbsp;</td>   
  </tr>	 
  <tr>
   <td><abbr title="Reason this revision was created">Reason for Current Revision:</abbr></td>
   <td colspan="3">&nbsp;<%= HTMLHelpersInput.inputBoxText("revisionReason", updSpec.getRevisionReason().trim(), "Reason for Revision", 75, 200, "Y", readOnlySpec) %></td>  
   <td><div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>&nbsp;&nbsp;</td> 
  </tr>
 </table>
 <table class="styled" style="width:100%">	   
  <tr>
   <td><abbr title="A Slipsheet is Required for each Pallet">
   <%= HTMLHelpersInput.inputCheckBox("slipSheetRequired", updSpec.getSlipSheetRequired(), readOnlySpec) %>&nbsp; Check Box if the Slip Sheet is Required?
   </abbr></td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetBottom", updSpec.getSlipSheetBottom(), readOnlySpec) %>&nbsp; Check if you want Slipsheet on Bottom</td>
  </tr>	    
  <tr>
   <td>&nbsp;<%= updSpec.getSlipSheetRequiredError() %></td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer1", updSpec.getSlipSheetLayer1(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 1</td>
  </tr>	 
  <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer2", updSpec.getSlipSheetLayer2(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 2</td>
  </tr>	   
  <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer3", updSpec.getSlipSheetLayer3(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 3</td>
  </tr>	    
  <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer4", updSpec.getSlipSheetLayer4(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 4</td>
  </tr>	 
  <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer5", updSpec.getSlipSheetLayer5(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 5</td>
  </tr>	
    <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer6", updSpec.getSlipSheetLayer6(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 6</td>
  </tr>	 
  <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetLayer7", updSpec.getSlipSheetLayer7(), readOnlySpec) %>&nbsp; Check if you want Slipsheet <b>AFTER</b> Layer 7</td>
  </tr>	
  <tr>
   <td>&nbsp;</td>
   <td colspan="3"><%= HTMLHelpersInput.inputCheckBox("slipSheetTop", updSpec.getSlipSheetTop(), readOnlySpec) %>&nbsp; Check if you want Slipsheet on Top of the Pallet</td>
  </tr>	  
  <tr>
   <td><abbr title="Stretch Wrap is Required">
   <%= HTMLHelpersInput.inputCheckBox("stretchWrapRequired", updSpec.getStretchWrapRequired(), readOnlySpec) %>&nbsp; Check Box if the Stretch Wrap is Required?
   </abbr></td>
   <td>&nbsp;</td>
   <td colspan = "2">&nbsp;<b>If Required must fill out the Type, Width and Gauge</b></td>
  </tr>	  
  <tr>
   <td>&nbsp;</td>
   <td>&nbsp;Type of Stretch Wrap:</td>
   <td colspan="2"><%= DropDownSingle.buildDropDown(updSpec.getDdStretchWrapType(), "stretchWrapType", updSpec.getStretchWrapType(), "Choose One", "N", readOnlySpec) %>&nbsp;<%= updSpec.getStretchWrapTypeError().trim() %></td>
  </tr>	    
  <tr>
   <td>&nbsp;</td>
   <td>&nbsp;Width of Stretch Wrap:</td>
   <td style="width:15%"><%= HTMLHelpersInput.inputBoxNumber("stretchWrapWidth", updSpec.getStretchWrapWidth(), "Stretch Wrap Width", 8, 8, "N", readOnlySpec) %>&nbsp;<%= updSpec.getStretchWrapWidthError() %></td>
   <td><%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), "stretchWrapWidthUOM", updSpec.getStretchWrapWidthUOM(), "Choose One", "N", readOnlySpec) %>&nbsp;<%= updSpec.getStretchWrapWidthUOMError() %></td>
  </tr>	      
  <tr>
   <td>&nbsp;</td>
   <td>&nbsp;Gauge of Stretch Wrap:</td>
   <td><%= HTMLHelpersInput.inputBoxNumber("stretchWrapGauge", updSpec.getStretchWrapGauge(), "Stretch Wrap Gauge", 8, 8, "N", readOnlySpec) %>&nbsp;<%= updSpec.getStretchWrapGaugeError() %></td>
   <td><%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), "stretchWrapGaugeUOM", updSpec.getStretchWrapGaugeUOM(), "Choose One", "N", readOnlySpec) %>&nbsp;<%= updSpec.getStretchWrapGaugeUOMError() %></td>
  </tr>	      
  <tr>
   <td><abbr title="Shrink Wrap is Required">
   <%= HTMLHelpersInput.inputCheckBox("shrinkWrapRequired", updSpec.getShrinkWrapRequired(), readOnlySpec) %>&nbsp; Check Box if the Shrink Wrap is Required?
   </abbr></td>
   <td>&nbsp;</td>
   <td colspan = "2">&nbsp;<b>If Required must fill out the Type, Width and Thickness</b></td>
  </tr>	  
  <tr>
   <td>&nbsp;</td>
   <td>&nbsp;Type of Shrink Wrap:</td>
   <td colspan="2"><%= DropDownSingle.buildDropDown(updSpec.getDdShrinkWrapType(), "shrinkWrapType", updSpec.getShrinkWrapType(), "Choose One", "N", readOnlySpec) %>&nbsp;<%= updSpec.getShrinkWrapTypeError().trim() %></td>
  </tr>	    
  <tr>
   <td>&nbsp;</td>
   <td>&nbsp;Width of Shrink Wrap:</td>
   <td><%= HTMLHelpersInput.inputBoxNumber("shrinkWrapWidth", updSpec.getShrinkWrapWidth(), "Shrink Wrap Width", 8, 8, "N", readOnlySpec) %>&nbsp;<%= updSpec.getShrinkWrapWidthError() %></td>
   <td><%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), "shrinkWrapWidthUOM", updSpec.getShrinkWrapWidthUOM(), "Choose One", "N", readOnlySpec) %>&nbsp;<%= updSpec.getShrinkWrapWidthUOMError() %></td>
  </tr>	      
  <tr>
   <td>&nbsp;</td>
   <td>&nbsp;Thickness of Shrink Wrap:</td>
   <td><%= HTMLHelpersInput.inputBoxNumber("shrinkWrapThickness", updSpec.getShrinkWrapThickness(), "Shrink Wrap Thickness", 8, 8, "N", readOnlySpec) %>&nbsp;<%= updSpec.getShrinkWrapThicknessError() %></td>
   <td><%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), "shrinkWrapThicknessUOM", updSpec.getShrinkWrapThicknessUOM(), "Choose One", "N", readOnlySpec) %>&nbsp;<%= updSpec.getShrinkWrapThicknessUOMError() %></td>
  </tr>	      
  <tr>
   <td rowspan="2">Pallet Information, only fill in if DIFFERENT than what is in M3:</td>
   <td rowspan="2">&nbsp;</td>
   <td>&nbsp;Units per Pallet<br>in M3:&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(updSpec.getUpdBean().getSpecPackaging().getM3UnitsPerPallet(),0) %></td>
   <td><%= HTMLHelpersInput.inputBoxNumber("unitsPerPallet", updSpec.getUnitsPerPallet(), "Units Per Pallet", 5, 5, "N", readOnlySpec) %>&nbsp;<%= updSpec.getUnitsPerPalletError() %></td>
  </tr>    
  <tr>
   <td>&nbsp;Units per Layer<br>in M3:&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(updSpec.getUpdBean().getSpecPackaging().getM3UnitsPerLayer(), 0) %></td>
   <td><%= HTMLHelpersInput.inputBoxNumber("unitsPerLayer", updSpec.getUnitsPerLayer(), "Units Per Layer", 5, 5, "N", readOnlySpec) %>&nbsp;<%= updSpec.getUnitsPerPalletError() %></td>
  </tr>               
 </table>
<%
   //  Product Description Comment / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment21");
%>
   <h3 class="expandOpen ui-widget-header">Product Description</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
   <br>
<%
   //  Ingredient Statement Comment / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment22");
%>
   <h3 class="expandOpen ui-widget-header">Ingredient Statement</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
<%
   //  Finished Pallet Additional Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment15");
%>
   <h3 class="expandOpen ui-widget-header">Finished Pallet Additional Comments</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
<%
  request.setAttribute("appType", "spec"); ;
  request.setAttribute("screenType", "TEST");
%> 
  <h3 class="expandOpen ui-widget-header">Analytical Testing</h3> 
 	<div class="collapse">
	<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
	  <table class = "styled">
	   <tr>
        <td style="width:15%">&nbsp;&nbsp;<abbr title="Brix that the Analytical Tests should be tested at">Test Brix:</abbr></td>
        <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("testBrix", updSpec.getTestBrix(), "Test Brix", 4, 4, "", readOnlySpec) %>&nbsp;<%= updSpec.getTestBrixError() %></td>
        <td style="width:15%">&nbsp;&nbsp;<abbr title="">Reconstitution Ratio:</abbr></td>
        <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("reconstitutionRatio", updSpec.getReconstitutionRatio(), "Reconstitution Ratio", 20, 20, "", readOnlySpec) %></td>  
       </tr>
	  </table>
     <jsp:include page="updTestParameters.jsp"></jsp:include>

   <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
<%
   //  Analytical Testing Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment1");
%>
    <h3 class="expandOpen ui-widget-header">Analytical Testing Comments</h3>
    <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
   </div>
<%
  request.setAttribute("screenType", "MICRO");
%> 
  <h3 class="expandOpen ui-widget-header">Micro and Trace Element Testing</h3> 
 	<div class="collapse">
	<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
      <jsp:include page="updTestParameters.jsp"></jsp:include>
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
<%
   //  Micro Testing Additional Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment3");
%>
    <h3 class="expandOpen ui-widget-header">Micro Testing Additional Comments</h3>
    <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
   </div>
<%
  request.setAttribute("screenType", "PROC");
%> 
  <h3 class="expandOpen ui-widget-header">Process Paramters</h3> 
 	<div class="collapse">
	<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
      <jsp:include page="updTestParameters.jsp"></jsp:include>
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
<%
   //  Process Parameters Additional Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment2");
%>
    <h3 class="expandOpen ui-widget-header">Process Parameters Additional Comments</h3>
    <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
   </div>
<%
  request.setAttribute("screenType", "ADD");
  
%> 
  <h3 class="expandOpen ui-widget-header">Additives and Preservatives</h3> 
 	<div class="collapse">
	<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
      <jsp:include page="updTestParameters.jsp"></jsp:include>
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
<%
   //  Additives and Preservatives Additional Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment4");
%>
    <h3 class="expandOpen ui-widget-header">Additives and Preservatives Additional Comments</h3>
    <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
   </div>
<%
  request.setAttribute("screenType", "I");
%> 
  <h3 class="expandOpen ui-widget-header">Varieties Included in the Specification</h3> 
 	<div class="collapse">
	<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
      <jsp:include page="updVariety.jsp"></jsp:include>
    </div>
<%
  request.setAttribute("screenType", "X");
%> 
  <h3 class="expandOpen ui-widget-header">Varieties Excluded in the Specification</h3> 
 	<div class="collapse">
	<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
      <jsp:include page="updVariety.jsp"></jsp:include>
     </div>
<%
   //  Fruit Variety Additional Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment16");
%>
   <h3 class="expandOpen ui-widget-header">Fruit Variety Additional Comments</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
<%
   //  Intended Use Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment23");
%>
   <h3 class="expandOpen ui-widget-header">Intended Use</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
<%
   //  Foreign Matter Comments / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment24");
%>
   <h3 class="expandOpen ui-widget-header">Foreign Matter</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>
<%
 // ****************************************
 // NUTRITIONAL INFORMATION
%>  
   <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Nutritional Information</h3> 
 	<div class="collapse">   
    <table class="styled" style="width:100%">
      <tr>
        <td>&nbsp;&nbsp;<abbr title="Attach the Nutrition Panel">Attach Nutrition Panel:</abbr></td>
        <td>&nbsp;
<%
   if (readOnlySpec.trim().equals("Y"))
   {
   	  out.println("<b>" + updSpec.getNutritionPanelURL() + "</b>");
   	
   }else{
   		if (updSpec.getNutritionPanelURL().trim().equals(""))
   		{
%>        
        <%= HTMLHelpersInput.inputBoxUrlBrowse("nutritionPanelURL", updSpec.getNutritionPanelURL(), "URL for the Nutrition Panel", 60, 500, "N", readOnlySpec) %>
<%  
    	}else{
%>   
        <%= HTMLHelpersInput.inputBoxHidden("nutritionPanelURL", updSpec.getNutritionPanelURL()) %>
        <%= HTMLHelpersInput.inputCheckBox("nutritionPanelURLRemove", "", readOnlySpec) %>&nbsp;Check if want to remove the attached Nutrition Panel&nbsp;
        <b><%= updSpec.getNutritionPanelURL() %></b>     
<%
    	}
    }
%>
        </td>  
       </tr> 
    </table>   
    </div>
<%
 // ****************************************
 // CODING - CONTAINER
%>  
 <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Container Code Requirements</h3> 
 	<div class="collapse">   
      <table class="styled">
       <tr>
        <td style="width:20%"><abbr title="Location the code should display on the container">&nbsp;Location:</abbr></td>
        <td style="width:25%">&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdContainerCodeLocation(), "containerCodeLocation", updSpec.getContainerCodeLocation(), "Choose One", "N", readOnlySpec) %></td>     
        <td style="width:20%"><abbr title="Font Size for the code that will display on the container">&nbsp;Font Size:</abbr></td>
        <td >&nbsp;<%= HTMLHelpersInput.inputBoxText("containerCodeFontSize", updSpec.getContainerCodeFontSize(), "Container Font Size", 10, 10, "N", readOnlySpec) %>&nbsp;</td>       
       </tr>
      </table>
<%
   //  Container Print Lines / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment5");
%>
   <h3 class="expandOpen ui-widget-header">Container Print Lines</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>     
<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>     
<%
   //  Additional Container Print Instructions / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment6");
%>
   <h3 class="expandOpen ui-widget-header">Additional Container Print Instructions</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>           
 </div>    
<%
 // ****************************************
 // CODING - CARTON
%>  
 <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Carton Code Requirements</h3> 
 	<div class="collapse">   
      <table class="styled">
       <tr>
        <td style="width:20%"><abbr title="Location the code should display on the container">Location:</abbr></td>
        <td style="width:25%">&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdCartonCodeLocation(), "cartonCodeLocation", updSpec.getCartonCodeLocation(), "Choose One", "N", readOnlySpec) %></td>     
        <td style="width:20%"><abbr title="Font Size for the code that will display on the container">&nbsp;Font Size:</abbr></td>
        <td >&nbsp;<%= HTMLHelpersInput.inputBoxText("cartonCodeFontSize", updSpec.getCartonCodeFontSize(), "Carton Font Size", 10, 10, "N", readOnlySpec) %>&nbsp;</td>       
       </tr>
      </table>
<%
   //  Carton Print Lines / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment19");
%>
   <h3 class="expandOpen ui-widget-header">Carton Print Lines</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>     
<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>     
<%
   //  Additional Carton Print Instructions / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment20");
%>
   <h3 class="expandOpen ui-widget-header">Additional Carton Print Instructions</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>           
 </div>    
<%
 // ****************************************
 // CODING - CASE
%>  
 <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Case Code Requirements</h3> 
 	<div class="collapse">   
      <table class="styled">
       <tr>
        <td style="width:45%" colspan = "2"><abbr title="Flag to allow Display of Bar Code on the Case -- thie bar code is the case GTIN"><%= HTMLHelpersInput.inputCheckBox("caseShowBarCode", updSpec.getCaseShowBarCode(), readOnlySpec) %>&nbsp;
          Check if you want the Bar Code to Display on the Case</abbr></td>
        <td style="width:20%"><abbr title="Font Size for the code that will display on the case">Font Size:</abbr></td>
        <td>&nbsp;<%= HTMLHelpersInput.inputBoxText("caseCodeFontSize", updSpec.getCaseCodeFontSize(), "Case Font Size", 10, 10, "N", readOnlySpec) %>&nbsp;</td>       
       </tr>
      </table>
<%
   //  Case Print Lines / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment7");
%>
   <h3 class="expandOpen ui-widget-header">Case Print Lines</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>     
<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>     
<%
   //  Additional Case Print Instructions / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment8");
%>
   <h3 class="expandOpen ui-widget-header">Additional Case Print Instructions</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>           
 </div>   
<%
 // ****************************************
 // CODING - LABEL - Pre-Printed ID Label
%>  
 <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Identification Label Requirements - Industrial Item Printed ID Label</h3> 
 	<div class="collapse">   
     <table class="styled">
       <tr>
        <td><abbr title="Flag if the Kosher Symbol is required on the label"><%= HTMLHelpersInput.inputCheckBox("kosherSymbolRequired", updSpec.getKosherSymbolRequired(), readOnlySpec) %>&nbsp;
          Check if the Kosher Symbol is required to display on the label</abbr></td>  
       </tr>
      </table>
<%
   //  Label Print Lines / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment11");
%>
   <h3 class="expandOpen ui-widget-header">Label Print Lines</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>     
<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>     
<%
   //  Additional Label Print Instructions / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment12");
%>
   <h3 class="expandOpen ui-widget-header">Additional Label Print Instructions</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>  

 	</div>
<%
 // ****************************************
 // Example Label
%>  
   <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Example Label</h3> 
 	<div class="collapse">   
    <table class="styled" style="width:100%">
      <tr>
        <td>&nbsp;&nbsp;<abbr title="Attach an Example of the Label">Attach Example Label:</abbr></td>
        <td>&nbsp;
<%
   if (readOnlySpec.trim().equals("Y"))
   {
   	  out.println("<b>" + updSpec.getExampleLabelURL() + "</b>");
   	
   }else{
   		if (updSpec.getExampleLabelURL().trim().equals(""))
   		{
%>        
        <%= HTMLHelpersInput.inputBoxUrlBrowse("exampleLabelURL", updSpec.getExampleLabelURL(), "URL for the Example Label", 60, 500, "N", readOnlySpec) %>
<%  
    	}else{
%>   
        <%= HTMLHelpersInput.inputBoxHidden("exampleLabelURL", updSpec.getExampleLabelURL()) %>
        <%= HTMLHelpersInput.inputCheckBox("exampleLabelURLRemove", "", readOnlySpec) %>&nbsp;Check if want to remove the attached Example Label&nbsp;
        <b><%= updSpec.getExampleLabelURL() %></b>     
<%
    	}
    }
%>
        </td>  
       </tr> 
    </table>   
    </div>
<%
 // ****************************************
 // CODING - PALLET
%>  
 <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Pallet Placard Requirements</h3> 
 	<div class="collapse">   
      <table class="styled">
       <tr>
        <td style = "width:15%"><abbr title="Type of Coding Attached to the Pallet">Placard Type:</abbr></td>
        <td style = "width:30%">&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdPalletLabelType(), "palletLabelType", updSpec.getPalletLabelType(), "Choose One", "N", readOnlySpec) %></td>     
        <td style = "width:15%"><abbr title="Location the Type should be placed on the pallet">Location:</abbr></td>
        <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdPalletLabelLocation(), "palletLabelLocation", updSpec.getPalletLabelLocation(), "Choose One", "N", readOnlySpec) %></td>       
       </tr>
      </table>
<%
   //  Case Print Lines / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment9");
%>
   <h3 class="expandOpen ui-widget-header">Pallet Print Lines</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>     
<div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>     
<%
   //  Additional Case Print Instructions / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment10");
%>
   <h3 class="expandOpen ui-widget-header">Additional Pallet Print Instructions</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>           
 </div>       
<%
   //  Additional Coding Requirements / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment25");
%>
   <h3 class="expandOpen ui-widget-header">Additional Coding Requirements</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>    
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>   
<%
   //  Shipping Requirements / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment17");
%>
   <h3 class="expandOpen ui-widget-header">Shipping Requirements</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>    
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div> 
<%
   //  COA Requirements / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment18");
%>
   <h3 class="expandOpen ui-widget-header">COA Requirements</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>    
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>  
<%
 //**************************************
 //  Shelf Life and Storage Recommendations
%>  
 <table class="styled">
  <tr>
   <td>&nbsp;
     <%= HTMLHelpersInput.inputCheckBox("shelfLifeNotValid", updSpec.getShelfLifeNotValid(), readOnlySpec) %> 
     &nbsp;Check if you do NOT want to see the Shelf Life on the Specification.  &nbsp;&nbsp;
     The M3 Shelf Life is <%= updSpec.getUpdBean().getSpecPackaging().getShelfLife() %>.
   </td>
  </tr>  
 </table>  
  
<%
   //  Shelf Life and Storage Recommendations / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment13");
%>
   <h3 class="expandOpen ui-widget-header">Shelf Life and Storage Recommendations</h3>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>    
<%
 // ****************************************
 // Storage Requirements
%>  
 <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Storage Requirements</h3> 
 	<div class="collapse">   
      <table class="styled">
       <tr>
<%
   if (updSpec.getUpdBean().getSpecPackaging().getM3StorageRecommendation().trim().equals("")){
%>
        <td style = "width:25%">&nbsp; Storage Recomendations:</td>
        <td><%= DropDownSingle.buildDropDown(updSpec.getDdStorageCondition(), "storageRecommendation", updSpec.getStorageRecommendation(), "Choose One", "N", readOnlySpec) %></td>
<%
  }else{
%>
        <td style = "width:10%">&nbsp; M3 says:</td>
        <td>&nbsp;<b><%= updSpec.getUpdBean().getSpecPackaging().getM3StorageRecommendation().trim() %></b></td>      
<%   
   }
%>       
       </tr>
      </table>
<%
   //  Storage Requirements / Text Box Section
   request.setAttribute("textCommentName", "SpecRevisionComment14");
%>
   <jsp:include page="updSpecificationTextBox.jsp"></jsp:include>      
   </div> 

<%
 // ****************************************
 // Pallet Information
%>  
   <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
   <h3 class="expandOpen ui-widget-header">Pallet</h3> 
 	<div class="collapse">   
    <table class="styled" style="width:100%">
     <tr>
      <td style="width:25%">&nbsp;&nbsp;<abbr title="Pallet Requirement">Pallet Requirement:</abbr></td>
      <td>&nbsp;<%= DropDownSingle.buildDropDown(updSpec.getDdPalletRequirement(), "palletRequirement", updSpec.getPalletRequirement(), "Choose One", "N", readOnlySpec) %>&nbsp;</td>
     </tr>
     <tr>
        <td>&nbsp;&nbsp;<abbr title="Attach the Pallet Pattern">Attach Pallet Pattern:</abbr></td>
        <td>&nbsp;
<%
   if (readOnlySpec.trim().equals("Y"))
   {
   	  out.println("<b>" + updSpec.getPalletPatternURL() + "</b>");
   	
   }else{
   		if (updSpec.getPalletPatternURL().trim().equals(""))
   		{
%>        
        <%= HTMLHelpersInput.inputBoxUrlBrowse("palletPatternURL", updSpec.getPalletPatternURL(), "URL for the Pallet Pattern", 60, 500, "N", readOnlySpec) %>
<%  
    	}else{
%>   
        <%= HTMLHelpersInput.inputBoxHidden("palletPatternURL", updSpec.getPalletPatternURL()) %>
        <%= HTMLHelpersInput.inputCheckBox("palletPatternURLRemove", "", readOnlySpec) %>&nbsp;Check if want to remove the attached Pallet Pattern&nbsp;
        <b><%= updSpec.getPalletPatternURL() %></b>     
<%
    	}
    }
%>
        </td>  
       </tr> 
    </table>   
    </div>
<%
// ******************************************
//  Product Structures
  if (!updSpec.getUpdBean().getProductStructure().isEmpty())
  {
    request.setAttribute("listMaterials", updSpec.getUpdBean().getProductStructure());    
%> 
  <div class="right"><input type="Submit" name="submit" value="Save Changes on Specification"></div>
  <h3 class="expandOpen ui-widget-header">Product Structure</h3> 
  <div class="collapse">   
   <jsp:include page="/view/item/listProductStructureMaterialsTable.jsp"></jsp:include>
  </div>  
<%
  }   
// ******************************************
//  Additional Images and Documents
 if (readOnlySpec.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("listKeyValues", updSpec.getListSpecUrls());
%>
  <h3 class="expandOpen ui-widget-header">Additional Images and Documents</h3> 
  <div class="collapse">   
   <jsp:include page="/view/utilities/updKeyValuesV3.jsp"></jsp:include>
  </div> 
  
</form>
 

 <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>