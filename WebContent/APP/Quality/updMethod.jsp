<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.quality.UpdMethod" %>
<%@ page import = "com.treetop.app.quality.GeneralQuality" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//  Will use the same screen for update/add/   copy will be done as an update 
//----------------------- updMethod.jsp ------------------------------//
//  Author :  Teri Walton  11/18/10
//
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------

//-----------------------------------------------------------------------//
  String updTitle = "Update Method";  
  String valueDisplay = "Method";
  // Read only "Y" will apply for ANYTHING that is not ind Pending Status
  // in Active or InActive the only thing that is allowed to change is the "Status" 
  String readOnlyMethod = "N"; 
  String screenType = "update";
 UpdMethod updMethod = new UpdMethod();
 try
 {
	updMethod = (UpdMethod) request.getAttribute("updViewBean");
	updTitle = updTitle + "&#160;" + updMethod.getMethodName() + "(" + updMethod.getMethodNumber() + ")";
	if (!updMethod.getStatus().trim().equals("PD"))
	{
	   readOnlyMethod = "Y";
	   screenType = "detail";
	}
    if (updMethod.getRequestType().trim().equals("updProcedure") ||
        updMethod.getRequestType().trim().equals("addProcedure") ||
        updMethod.getRequestType().trim().equals("copyNewProcedure") ||
        updMethod.getRequestType().trim().equals("reviseProcedure"))  
    
    {
       updTitle = "Update Procedure";
       valueDisplay = "Procedure";
    }
    if (updMethod.getRequestType().trim().equals("updPolicy") ||
        updMethod.getRequestType().trim().equals("addPolicy") ||
        updMethod.getRequestType().trim().equals("copyNewPolicy") ||
        updMethod.getRequestType().trim().equals("revisePolicy"))  
    
    {
       updTitle = "Update Policy";
       valueDisplay = "Policy";
    }
	request.setAttribute("readOnlyMethod", readOnlyMethod);
 }
 catch(Exception e)
 {
 } 

//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   Vector headerInfo = new Vector();
    headerInfo.addElement(updMethod.getEnvironment());
    headerInfo.addElement(updTitle); // Element 0 is always the Title of the Page
    
    StringBuffer setExtraOptions = new StringBuffer();
    setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=inqMethod\">ReturnToSelection");
    setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=add" + valueDisplay);
    setExtraOptions.append("&environment=" + updMethod.getEnvironment());
    setExtraOptions.append("\">Add a New " + valueDisplay);

   headerInfo.addElement(setExtraOptions.toString());
//*****************************************************************************
 String formName = "updMethod";
 request.setAttribute("formName", formName);
%>
 <jsp:include page="../../Include/heading_nobanner.jsp"></jsp:include>
  <title><%= updTitle %></title>
 </head>
 <body>
  <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
  <form  name = "<%= formName %>" action="/web/CtlQuality?requestType=upd<%= valueDisplay %>" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", updMethod.getEnvironment()) %> 
  <%= HTMLHelpersInput.inputBoxHidden("methodNumber", updMethod.getMethodNumber()) %>  
  <%= HTMLHelpersInput.inputBoxHidden("originalStatus", updMethod.getOriginalStatus()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionDate", updMethod.getRevisionDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("revisionTime", updMethod.getRevisionTime()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationDate", updMethod.getCreationDate()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationTime", updMethod.getCreationTime()) %>
  <%= HTMLHelpersInput.inputBoxHidden("creationUser", updMethod.getCreationUser()) %>
  
 <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!updMethod.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "5">&#160;&#160;&#160;&#160;&#160;<b><%= updMethod.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
  <tr>
   <td class="td04160102" style="width:20%">&#160;&#160;<abbr title="<%= valueDisplay %> Number: Program Code= <%= updMethod.getMethodNumber() %>"><%= valueDisplay %> Number</abbr></td>
   <td class="td04160102">&#160;<%= HTMLHelpersInput.inputBoxText("methodName", updMethod.getMethodName(), (valueDisplay + " Number"), 20, 20, "Y", readOnlyMethod) %>&#160;</td>
   <td class="td04140102" style="width:1%">&#160;</td>
   <td class="td04140102" colspan="2" style="text-align:right; width:30%"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %>&#160;&#160;</td>
  </tr>
  <tr>
   <td class="td04140102">&#160;&#160;<abbr title="Description of the <%= valueDisplay %> / Name"><%= valueDisplay %> Name:</abbr></td>
   <td class="td04140102">&#160;<%= HTMLHelpersInput.inputBoxText("methodDescription", updMethod.getMethodDescription(), (valueDisplay + " Description-Name"), 35, 50, "Y", readOnlyMethod) %></td>  
   <td class="td04160102">&#160;</td>
   <td class="td04140102" style="width:20%">&#160;&#160;<abbr title="Date and Time <%= valueDisplay %> Revision was Created">Revision Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updMethod.getRevisionDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updMethod.getRevisionTime()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Status - Specific to this revision -- you can only update information in Pending Status">Status of this Revision:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListStatus(), "status", updMethod.getStatus(), "None", "N", "N") %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="User that Originally Created the <%= valueDisplay %>">Creation User:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.findLongNameFromProfile(updMethod.getEnvironment(), updMethod.getCreationUser()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Scope - Specific to this Method">Scope:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListScope(), "scopeCode", updMethod.getScopeCode(), "Choose One", "N", readOnlyMethod) %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="Date and Time <%= valueDisplay %> was Originally Created">Creation Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updMethod.getCreationDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updMethod.getCreationTime()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Origination - who Originated this specific <%= valueDisplay %> or this revision of the <%= valueDisplay %>">Origination:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListOrigination(), "originationUser", updMethod.getOriginationUser(), "Choose One", "N", readOnlyMethod) %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="Who last UPDATED/REVIEWED this <%= valueDisplay %>">User Who Last Reviewed:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.findLongNameFromProfile(updMethod.getEnvironment(), updMethod.getUpdateUser()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Approved By - Who will be tagged as Approving this specific revision of the <%= valueDisplay %>">By:</abbr></td>
   <td class="td04160102">&#160;<%= DropDownSingle.buildDropDown(GeneralQuality.getListApprovedByUser(), "approvedByUser", updMethod.getApprovedByUser(), "Choose One", "N", readOnlyMethod) %></td>
   <td class="td04140102">&#160;</td>
   <td class="td04140102">&#160;&#160;<abbr title="Date and Time this <%= valueDisplay %> was last UPDATED/REVIEWED">Last Review Date and Time:</abbr></td>
   <td class="td04140102">&#160;<%= GeneralQuality.formatDateForScreen(updMethod.getUpdateDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(updMethod.getUpdateTime()) %></td>
  </tr>
  <tr>
   <td class="td04160102">&#160;&#160;<abbr title="Reason for this specific Revision">Reason for Current Revision:</abbr></td>
   <td class="td03160102" colspan="4">&#160;<%= HTMLHelpersInput.inputBoxText("revisionReason", updMethod.getRevisionReason(), "Revision Reason", 50, 50, "N", readOnlyMethod) %>
      <%= updMethod.getRevisionReasonError() %>
   </td>
  </tr>
  <tr>
   <td class="td05180102" style="text-align:center" colspan="5">&#160;<br><b>Keep in mind that you only fill out the sections that are applicable to this specific <%= valueDisplay %>, if not filled in they will NOT display on the detail page.</b></td>
  </tr>  
 </table>
 <%
  int imageCount  = 2;
  int expandCount = 1;
  // Create Expandable Section for Method Detail information
  request.setAttribute("appType", "Method"); 
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
   if (readOnlyMethod.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListTheory());    
%>  
  <h3 class="expandOpen">Theory & Principle</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
   if (readOnlyMethod.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListTheoryUrl());    
%>  
  <h3 class="expandOpen">Theory & Principle Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
</div> 
<%
  }
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
     request.setAttribute("screenType", "detail");
  else
     request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListEquipment());    
%>  
  <h3 class="expandOpen">Equipment</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListEquipmentUrl());    
%>  
  <h3 class="expandOpen">Equipment Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
</div>
<%
  }
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment4");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListReagents());    
%>  
  <h3 class="expandOpen">Reagents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url4");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListReagentsUrl());    
%>  
  <h3 class="expandOpen">Reagents Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment5");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListCaution());    
%>  
  <h3 class="expandOpen">Caution</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url5");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListCautionUrl());    
%>  
  <h3 class="expandOpen">Caution Images and Documents</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }
   if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment6");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListFrequency());    
%>  
  <h3 class="expandOpen">Frequency</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url6");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListFrequencyUrl());    
%>  
  <h3 class="expandOpen">Frequency Images and Documents</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }     
  if (valueDisplay.trim().equals("Procedure") ||
      valueDisplay.trim().equals("Policy"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment4");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListPolicy());    
%>  
  <h3 class="expandOpen">Policy</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url4");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListPolicyUrl());    
%>  
  <h3 class="expandOpen">Policy Images and Documents</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }
    if (valueDisplay.trim().equals("Procedure"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment5");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListPurpose());    
%>  
  <h3 class="expandOpen">Purpose</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url5");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListPurposeUrl());    
%>  
  <h3 class="expandOpen">Purpose Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }
    if (valueDisplay.trim().equals("Procedure"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment6");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListScope());    
%>  
  <h3 class="expandOpen">Scope</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url6");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListScopeUrl());    
%>  
  <h3 class="expandOpen">Scope Images and Documents</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }
  if (valueDisplay.trim().equals("Procedure"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment7");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListResponsibility());    
%>  
  <h3 class="expandOpen">Responsibility</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div> 
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url7");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListResponsibilityUrl());    
%>  
  <h3 class="expandOpen">Responsibility Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
     <%= HTMLHelpers.endSpan() %>
    </td>
   </tr>  
  </table> 
<%
  }
  if (valueDisplay.trim().equals("Procedure"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListDefinitions());    
%>  
  <h3 class="expandOpen">Definitions</h3>
  	<div class="collapse">
      <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div> 
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListDefinitionsUrl());    
%>  
  <h3 class="expandOpen">Definitions Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  }  
  if (valueDisplay.trim().equals("Method") ||
      valueDisplay.trim().equals("Procedure"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment2");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListProcedure());    
%>  
  <h3 class="expandOpen">Procedure</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url2");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListProcedureUrl());    
%>  
  <h3 class="expandOpen">Procedure Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
 
<%
  } 
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment7");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListExamples());    
%>  
  <h3 class="expandOpen">Examples</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url7");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListExamplesUrl());    
%>  
  <h3 class="expandOpen">Examples Images and Documents</h3>
  	<div class="collapse">
     <div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  } 
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else 
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment8");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListCalculations());    
%>  
  <h3 class="expandOpen">Calculations</h3>
  	<div class="collapse">
     	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url8");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListCalculationsUrl());    
%>  
  <h3 class="expandOpen">Calculations Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  } 
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment9");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListInterpretation());    
%>  
  <h3 class="expandOpen">Interpretation of Results</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url9");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListInterpretationUrl());    
%>  
  <h3 class="expandOpen">Interpretation of Results Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  } 
  if (valueDisplay.trim().equals("Policy"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListAuthorization());    
%>  
  <h3 class="expandOpen">Authorization</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url1");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListAuthorizationUrl());    
%>  
  <h3 class="expandOpen">Authorization Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  } 
  if (valueDisplay.trim().equals("Method") ||
      valueDisplay.trim().equals("Policy"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment10");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListActions());    
%>  
  <h3 class="expandOpen">Actions</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url10");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListActionsUrl());    
%>  
  <h3 class="expandOpen">Actions Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  } 
  if (valueDisplay.trim().equals("Method"))
  {
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment11");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListReporting());    
%>  
  <h3 class="expandOpen">Reporting</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url11");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListReportingUrl());    
%>  
  <h3 class="expandOpen">Reporting Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
    	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%
  } 
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment3");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListAdditional());    
%>  
  <h3 class="expandOpen">Comments</h3>
  	<div class="collapse">
		<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     <jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
	</div>
<%  
  imageCount++;
  expandCount++;
  if (readOnlyMethod.trim().equals("Y"))
    request.setAttribute("screenType", "detail");
  else
    request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url3");
  request.setAttribute("showSequence", "Y");
  request.setAttribute("listKeyValues", updMethod.getListAdditionalUrl());    
%>  
  <h3 class="expandOpen">Comments Images and Documents</h3>
  	<div class="collapse">
    	<div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
     	<jsp:include page="../Utilities/updKeyValuesV2.jsp"></jsp:include>
   	</div>
 </form>  
 <%= HTMLHelpers.pageFooterTable(updMethod.getRequestType()) %>

   </body>
</html>