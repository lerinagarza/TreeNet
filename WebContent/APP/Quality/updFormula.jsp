<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.quality.UpdFormula" %>
<%@ page import = "com.treetop.app.quality.GeneralQuality" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//  Will use the same screen for update/add/   copy will be done as an update 
//----------------------- updFormula.jsp ------------------------------//
//  Author :  Teri Walton  04/28/10
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Update Formula";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 String readOnlyFormula = "N";
 UpdFormula updFormula = new UpdFormula();
 try
 {
	updFormula = (UpdFormula) request.getAttribute("updViewBean");
	updTitle = updTitle + "&#160;" + updFormula.getFormulaName() + "&#160;" + updFormula.getFormulaNumber();
	if (!updFormula.getStatus().trim().equals("PD"))
	   readOnlyFormula = "Y";
	request.setAttribute("readOnlyFormula", readOnlyFormula);
 }
 catch(Exception e)
 {
 } 

//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   Vector headerInfo = new Vector();
    headerInfo.addElement(updFormula.getEnvironment());
    headerInfo.addElement(updTitle); // Element 0 is always the Title of the Page
    
//   StringBuffer setExtraOptions = new StringBuffer();
//   if (ir.getAllowUpdate().equals("Y"))
//      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a Load");
//   setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=inqFormula\">Select Again");
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket&");
//   setExtraOptions.append(ir.buildParameterResend());
//   setExtraOptions.append("\">Return To Selection Screen");
//   if (!is.getAllowUpdate().equals(""))   
//     setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=addCPGSpec\">Add New Specification");

//   headerInfo.addElement(setExtraOptions.toString());
//*****************************************************************************
 String formName = "updFormula";
 request.setAttribute("formName", formName);
%>
<jsp:include page="../../Include/heading_nobanner.jsp"></jsp:include>
  <title><%= updTitle %></title>
 </head>
 <body>
  <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
  <form  name = "<%= formName %>" action="/web/CtlQuality?requestType=updFormula" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", updFormula.getEnvironment()) %> 
  <%= HTMLHelpersInput.inputBoxHidden("formulaNumber", updFormula.getFormulaNumber()) %>  
  <%= HTMLHelpersInput.inputBoxHidden("originalStatus", updFormula.getOriginalStatus()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionDate", updFormula.getRevisionDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionTime", updFormula.getRevisionTime()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationDate", updFormula.getCreationDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationTime", updFormula.getCreationTime()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationUser", updFormula.getCreationUser()) %>
  <%= HTMLHelpersInput.inputBoxHidden("referenceFormulaNumber", updFormula.getReferenceFormulaNumber()) %>
  <%= HTMLHelpersInput.inputBoxHidden("referenceFormulaRevisionDate", updFormula.getReferenceFormulaRevisionDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("referenceFormulaRevisionTime", updFormula.getReferenceFormulaRevisionTime()) %>
  
 <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!updFormula.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "5">&#160;&#160;&#160;&#160;&#160;<b><%= updFormula.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
   if (updFormula.getFormulaName().trim().equals("") &&
       updFormula.getFormulaNameError().trim().equals(""))
       updFormula.setFormulaNameError("Must be Entered");
   if (updFormula.getFormulaDescription().trim().equals("") &&
       updFormula.getFormulaDescriptionError().trim().equals(""))
       updFormula.setFormulaDescriptionError("Must be Entered");  
%> 
  <tr>
   <td class="td04160102" style="width:20%">&#160;&#160;<abbr title="Formula Type:  Group the Formula's Together">Formula Type</abbr></td>
   <td class="td03160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListType(), "formulaType", updFormula.getFormulaType(), "None", "N", readOnlyFormula, "50") %>&#160;<%= updFormula.getFormulaTypeError().trim() %></td>
   <td class="td04140102" style="width:1%">&#160;</td>
   <td class="td04140102" colspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %>&#160;&#160;</td>
  </tr>     
  <tr>
   <td class="td04160102" style="width:20%">&#160;&#160;<abbr title="Formula Number: Program Code= <%= updFormula.getFormulaNumber() %>">Formula Number</abbr></td>
   <td class="td03160102">&#160;<%= HTMLHelpersInput.inputBoxText("formulaName", updFormula.getFormulaName(), "Formula Number", 20, 20, "Y", readOnlyFormula) %>&#160;<%= updFormula.getFormulaNameError().trim() %></td>
   <td class="td04140102" style="width:1%">&#160;</td>
   <td class="td04140102" colspan="2">&#160;</td>
  </tr> 
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="Description of the Formula / Name">Formula Name:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxText("formulaDescription", updFormula.getFormulaDescription(), "Formula Description-Name", 35, 50, "Y", readOnlyFormula) %>&#160;<%= updFormula.getFormulaDescriptionError().trim() %></td>  
   <td class="td04160102">&#160;</td>
   <td class="td04140102" style="width:20%">&#160;&#160;<abbr title="Date and Time Revision was Created">Revision Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updFormula.getRevisionDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updFormula.getRevisionTime()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Status - Specific to this revision -- you can only update information in Pending Status">Status of this Revision:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListStatus(), "status", updFormula.getStatus(), "None", "N", "N", "50") %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="User that Originally Created the Formula">Creation User:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.findLongNameFromProfile(updFormula.getEnvironment(), updFormula.getCreationUser()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Scope - Specific to this Method">Scope:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListScope(), "scopeCode", updFormula.getScopeCode(), "Choose One", "N", readOnlyFormula, "50") %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="Date and Time Formula was Originally Created">Creation Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updFormula.getCreationDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updFormula.getCreationTime()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Origination - who Originated this specific Formula or this revision of the Formula">Origination:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListOrigination(), "originationUser", updFormula.getOriginationUser(), "Choose One", "N", readOnlyFormula, "50") %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="Who last UPDATED/REVIEWED this Formula">User Who Last Reviewed:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.findLongNameFromProfile(updFormula.getEnvironment(), updFormula.getUpdateUser()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Approved By - Who will be tagged as Approving this specific revision of the Formula">By:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListApprovedByUser(), "approvedByUser", updFormula.getApprovedByUser(), "Choose One", "N", readOnlyFormula, "50") %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="Date and Time this Formula was last UPDATED/REVIEWED">Last Review Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updFormula.getUpdateDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updFormula.getUpdateTime()) %></td>
  </tr>
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="Customer Code -- Default to M3 Customer - Required if you have a Customer Number">Customer Type:</abbr></td>
   <td class="td03140102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListCustomerCode(), "customerCode", updFormula.getCustomerCode(), "Choose One", "N", readOnlyFormula, "50") %>&#160;<%= updFormula.getCustomerCodeError() %></td>     
   <td class="td04140102">&#160;</td>
<%
   if (updFormula.getReferenceFormulaNumber().trim().equals("0"))
   {
%> 
   <td class="td04140102" colspan="2">&#160;</td>
<%
   }else{
%>  
   <td class="td0416010201" colspan="2" style="text-align:center"><b>COPIED FROM</b></td>
<%
   }
%>    
  </tr> 
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="Customer Number -- Not Required --">Customer Number:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxText("customerNumber", updFormula.getCustomerNumber(), "Customer Number", 10, 10, "N", readOnlyFormula) %>&#160;<%= updFormula.getCustomerNumberError() %></td>      
   <td class="td04140102">&#160;</td>
<%
   if (updFormula.getReferenceFormulaNumber().trim().equals("0"))
   {
%> 
   <td class="td04140102" colspan="2">&#160;</td>
<%
   }else{
   String formulaURL = "/web/CtlQuality?requestType=dtlFormula&formulaNumber=" + updFormula.getReferenceFormulaNumber() +
                       "&revisionDate=" + updFormula.getReferenceFormulaRevisionDate() +
                       "&revisionTime=" + updFormula.getReferenceFormulaRevisionTime();
   String formulaMouseover = "Click here to see the details of the 'Copied From' Formula";
%>     
   <td class="td04140102">&#160;&#160;<abbr title="Formula Number that this Formula was Created From">Formula Number:</abbr></td>
   <td class="td04140102">&#160;<%= HTMLHelpersLinks.basicLink(updFormula.getReferenceFormulaNumber(), formulaURL, formulaMouseover,"" , "") %>&#160;</td>
<%
   }
%>      
  </tr>	
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="Name of the Customer">Customer Name:</abbr></td>
   <td class="td04140102">&#160;<%= HTMLHelpersInput.inputBoxText("customerName", updFormula.getCustomerName(), "Customer Name", 25, 50, "N", readOnlyFormula) %>&#160;</td>    
   <td class="td04140102">&#160;</td>
<%
   if (updFormula.getReferenceFormulaNumber().trim().equals("0"))
   {
%> 
   <td class="td04140102" colspan="2">&#160;</td>
<%
   }else{
%>        
   <td class="td04140102"><abbr title="Date and Time of Revision Copied From">Revision Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updFormula.getReferenceFormulaRevisionDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updFormula.getReferenceFormulaRevisionTime()) %></td>
<%
   }
   if (updFormula.getRevisionReason().trim().equals("") &&
       updFormula.getRevisionReasonError().trim().equals(""))
      updFormula.setRevisionReasonError("Must Enter a Reason");
%>        
  </tr>	
  
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="M3 Item Number - not required, but if entered must be a valid item">Customer or Supplier Item Number:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxText("customerOrSupplierItemNumber", updFormula.getCustomerOrSupplierItemNumber(), "Customer Or Supplier Item Number", 50, 50, "N", readOnlyFormula) %></td>
   <td class="td04140102" colspan="3">&#160;</td>
  </tr> 
  
  
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="M3 Item Number - not required, but if entered must be a valid item">Line Tank Item Number:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxText("lineTankItem", updFormula.getLineTankItem(), "M3 Item Number", 15, 15, "N", readOnlyFormula) %>&#160;<%= updFormula.getLineTankItemError().trim() %></td>
   <td class="td04140102" colspan="3">&#160;<%= updFormula.getUpdBean().getFormula().getLineTankItemDescription() %></td>
  </tr>	   
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="Target Brix, if applicable">Target Brix:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxNumber("targetBrix", updFormula.getTargetBrix(), "Target Brix", 6, 5, "N", readOnlyFormula) %>&#160;</td>
   <td class="td04140102" colspan="3">&#160;</td>
  </tr>	 
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Reason for this specific Revision">Reason for Current Revision:</abbr></td>
   <td class="td03160102" colspan="4">&#160;<%= HTMLHelpersInput.inputBoxText("revisionReason", updFormula.getRevisionReason(), "Revision Reason", 50, 50, "N", readOnlyFormula) %>&#160;<%= updFormula.getRevisionReasonError() %></td>
  </tr>
 </table> 
<%
  int imageCount  = 2;
  int expandCount = 1;
  // Create Expandable Section for FormulaDetail information
  request.setAttribute("screenType", "preBlend");
%>  
  <h3 class="expandOpen">Pre Blend Formulation</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="updFormulaDetail.jsp"></jsp:include>  
 <table class="table00" cellspacing="0" cellpadding="0">
  <tr>
   <td class="td04140102"><abbr title="Pre Blend: Batch Size and Unit of Measure -- if a Pre Blend Batch Size is entered a UOM MUST be entered-- Pre Blend Batch Size is Not Required --">Pre Blend Batch Size:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxNumber("batchSizePreBlend", updFormula.getBatchSizePreBlend().trim(), "Pre Blend Batch Size", 8, 11, "N", readOnlyFormula) %>&#160;<%= updFormula.getBatchSizePreBlendError() %></td>      
   <td class="td04140102"><abbr title="Pre Blend Batch Size Unit of Measure -- if a Pre Blend Batch Size is entered a UOM MUST be entered">Pre Blend Batch Size UOM:</abbr></td>
   <td class="td03140102" style="width:30%">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), "batchPreBlendUOM", updFormula.getBatchPreBlendUOM().trim().trim(), "Choose One", "B", readOnlyFormula) %>&#160;<%= updFormula.getBatchPreBlendUOMError() %></td>   
   <td class="td03140102" style="width:15%">&#160;</td>   
   </tr>	 
 </table>
 </div>  
<%
  imageCount++;
  expandCount++;
  // Create Expandable Section for FormulaDetail information
  request.setAttribute("screenType", "production");
%>  

 <h3 class="expandOpen">Production Formula</h3>
  	<div class="collapse">
       <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
        <jsp:include page="updFormulaDetail.jsp"></jsp:include>  
   <table class="table00" cellspacing="0" cellpadding="0">
  <tr>
  <td class="td04140102"><abbr title="Production: Batch Size and Unit of Measure -- if a Batch Size is entered a UOM MUST be entered-- Pre Blend Batch Size is Not Required --">Batch Size:</abbr></td>
   <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxNumber("batchSize", updFormula.getBatchSize().trim(), "Batch Size", 8, 11, "N", readOnlyFormula) %>&#160;<%= updFormula.getBatchSizeError() %></td>      
   <td class="td04140102"><abbr title="Production Batch Size Unit of Measure -- if a Batch Size is entered a UOM MUST be entered">Batch Size UOM:</abbr></td>
   <td class="td03140102" style="width:30%">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListUOM(), "batchUOM", updFormula.getBatchUOM().trim().trim(), "Choose One", "B", readOnlyFormula) %>&#160;<%= updFormula.getBatchUOMError() %></td>   
   <td class="td03140102" style="width:15%">&#160;</td> 
  </tr>	 
 </table>
 	</div>    
<%
  request.setAttribute("appType", "formula"); 
  imageCount++;
  expandCount++;
   if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListDetails());    
%>  
  <h3 class="expandOpen">Formula Details</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "I");
%>  
  <h3 class="expandOpen">Varieties Included in the Formula</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="updVariety.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "X");
%>
  <h3 class="expandOpen">Varieties Excluded in the Formula</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="updVariety.jsp"></jsp:include>
	</div>    
<%
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "");
%>
  <h3 class="expandOpen">Raw Fruit Details</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="updTestParameters.jsp"></jsp:include>
  <table class="table00" cellspacing="0" cellpadding="2">
   <tr>
    <td class="td04140102" style="width:20%"><abbr title="Origin of the Fruit for this Formula">Fruit Origin:</abbr></td>
    <td class="td03140102">&#160;<%= HTMLHelpersInput.inputBoxText("fruitOrigin", updFormula.getFruitOrigin().trim(), "Fruit Origin", 5, 5, "N", readOnlyFormula) %>&#160;</td>      
   </tr>
   </table>
   </div>
<%
  imageCount++;
  expandCount++;
   if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment6");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListRFAdditionalInfo());    
%>    
  <h3 class="expandOpen">Raw Fruit Additional Information</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>   
<%
  imageCount++;
  expandCount++;
  // Create Expandable Section for FormulaDetail information
  //  To be used for Preblend data for Applesauce
%>  
  <h3 class="expandOpen">Cinnamon Pre-Blend for Applesauce</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="updFormulaDetailSaucePreBlend.jsp"></jsp:include>  
	</div>   
<%
  imageCount++;
  expandCount++;
   if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment2");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListCalculations());    
%>  
  <h3 class="expandOpen">Calculations</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment3");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListBlendingInstructions());    
%>  
  <h3 class="expandOpen">Blending Instructions</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
   if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment4");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListKeyLabelStatements());    
%>  
  <h3 class="expandOpen">Key Label Statements</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>	
<%
  imageCount++;
  expandCount++;
   if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment5");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListIngredientStatement());    
%>  
  <h3 class="expandOpen">Ingredient Statement</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  if (!updFormula.getUpdBean().getProductStructure().isEmpty())
  {
    imageCount++;
    expandCount++;
    request.setAttribute("listMaterials", updFormula.getUpdBean().getProductStructure());    
%>  
  <h3 class="expandOpen">Product Structure</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Item/listProductStructureMaterialsTable.jsp"></jsp:include>
    </div>  
<%
  } 
  imageCount++;
  expandCount++;
   if (readOnlyFormula.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updFormula.getListComments());    
%>  
  <h3 class="expandOpen">Comments</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
 </form>  
 <%= HTMLHelpers.pageFooterTable(updFormula.getRequestType()) %>

   </body>
</html>