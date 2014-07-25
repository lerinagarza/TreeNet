<%@ page import = "com.treetop.app.item.*" %> 
<%@ page import = "com.treetop.businessobjectapplications.BeanItem" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.Vector" %>
<%
 //Give you the ability to reserve a Resource number.
//				And Tie UPC, EPC and GTIN to a Resource
//------------ updItem.jsp---------------------//
//  Author :  Teri Walton  07/11/05
//  Changes:  
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  3/21/08   TWalton     Newly Created for Movex Was updNewItem in the Old System
//  1/7/11    TWalton     Put on new header, fix scan and include issues
//                        Add Populate UPC's to M3 functionality
//  6/18/12   TWalton	  Moved to the view folder,  changed the stylings.
//-----------------------------------------------------------------------//
  String updateTitle  = "Update Item Information";
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdItem ui = new UpdItem();
 BeanItem bitem = new BeanItem();
 try
 {
	ui = (UpdItem) request.getAttribute("updViewBean");
	bitem = (BeanItem) request.getAttribute("beanItem");
//	System.out.println(bitem.getItemClass().getResponsible());
    if (bitem != null)
	  request.setAttribute("projectOwner", bitem.getItemClass().getResponsible());
 }
 catch(Exception e)
 {
    System.out.println("should never get here, updItem.jsp: " + e);
 }   
   
%><%-- tpl:insert page="/view/template/treeNetTemplate.jtpl" --%><%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	<title><%= updateTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
  <title><%= updateTitle %></title>
 <%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
<div class="row">
	<div class="span4">
	<%
  	// Pause Button
  	if (ui.getPause().trim().equals("") &&
    	!ui.getItem().trim().equals("") &&
      	ui.getItemError().trim().equals("") &&
      	ui.getAreYouOwner().equals("Y") &&
      	!ui.areEmailsComplete.equals("Y") )  
  	{
  		String stopHere = "StopHere"; //for debug
  		%>
		<form action="/web/CtlItem?requestType=pause" method="post" >
		<input type="hidden" name="environment" value = "<%= ui.getEnvironment() %>">
		<input type="hidden" name="item" value = "<%= ui.getItem() %>">
		<input type="Submit" name="pauseButton" value="Pause Emails">
		</form> 
		
		<%
  	}

  	// Unpause Button
  
  	if (!ui.getPause().trim().equals("") &&
    	!ui.getItem().trim().equals("") &&
    	ui.getItemError().trim().equals("") &&
    	ui.getAreYouOwner().equals("Y") &&
    	!ui.areEmailsComplete.equals("Y") )
    {
		String stopHere = "StopHere"; //for debug
		%>
		<form action="/web/CtlItem?requestType=unpause" method="post" >
		<input type="hidden" name="environment" value = "<%= ui.getEnvironment() %>">
		<input type="hidden" name="item" value = "<%= ui.getItem() %>">
		<output>Start Email Date </output>
		<input type="text" class="datepicker" name="newStartDate" value = "<%= ui.getEstTargetDate() %>">
		<input type="Submit" name="unpauseButton" value="Unpause Emails">
		</form>
		 
		<%
  	}
  	%>
  	
  	</div>
  	<div class="span8">
  	
  	<%
	// Complete Button
  	if (!ui.getItem().trim().equals("") &&
    	ui.getItemError().trim().equals("") &&
    	ui.getAreYouOwner().equals("Y") &&
    	!ui.areEmailsComplete.equals("Y") ) 
	{
		String stopHere = "StopHere"; //for debug
		%>
		<form action="/web/CtlItem?requestType=complete" method="post" >
		<input type="hidden" name="environment" value = "<%= ui.getEnvironment() %>">
		<input type="hidden" name="item" value = "<%= ui.getItem() %>">
		<output> Complete All Responsibilities </output>
		<input type="Submit" name="completeButton" value="Complete All">
		</form> 
		<%
  	}
	%>
	</div>
</div>

 <form action="/web/CtlItem?requestType=update" method="post" >		
 <input type="hidden" name="environment" value = "<%= ui.getEnvironment() %>">
 <input type="hidden" name="pause" value="<%= ui.getPause() %>">
<%
   if (!ui.getDisplayMessage().trim().equals("")){
%>
  <div class="error"><%= ui.getDisplayMessage() %></div>
<%
   }
   
//*********************************************************************
//  Enter Data -- BEGIN
//*********************************************************************   
  if (ui.getItem().equals("") ||
     !ui.getItemError().trim().equals(""))
  {    
%>  
  <fieldset>
   <legend>Choose an Item</legend>
  <div class="center comment">All Items must be entered into M3(Movex) first. Only the Item Responsible person can 'Start' a process.</div> 
<br/>  
   <table>
    <tr>
      <td>
        <label for="item" title="mouseover">Item Number:</label>
      </td>
       <td>
        <input type="text" id="item" name="item" value="<%= ui.getItem() %>" maxlength="15" />
       </td>
      <td class="error">
        <%= ui.getItemError() %>
      </td>
    </tr>
    <tr>
      <td>
        <label for="estTargetDate" title="mouseover">Start Date for the Tickler File (Responsibilities),<br/> *this only works the first time into the item:</label>
      </td>
       <td>
        <input type="text" class="datepicker" name="estTargetDate" value = "<%= ui.getEstTargetDate() %>">
       </td>
      <td class="error">&#160;</td>
    </tr>
   </table>
 <br/>   
   <div class="center"><input type="Submit" name="submit" value="Update Item"></div>
     </fieldset>
<%  
  }else{
 //*********************************************************************
 //  Update the Date -- BEGIN
 //*********************************************************************
   String groupMain = "";
   try{     
     TicklerFunctionDetail thisrow = (TicklerFunctionDetail) bitem.getFunctions().elementAt(0);
  	 groupMain = thisrow.getGroup();
	}catch(Exception e){}
%>   

	<h1 class="expandOpen ui-widget-header">New Item Update</h1>


  <table class="styled">
    <tr>
    <td>Item Number / Name:</td>
    <td><b><%= bitem.getItemClass().getItemNumber() %></b>&#160;&#160;&#160;<%= bitem.getItemClass().getItemDescription() %>
      <input type="hidden" name="item" value = "<%= bitem.getItemClass().getItemNumber() %>">
    </td>
      <td class ="right"><button type="button" onclick="window.location='CtlItem?environment=<%= ui.getEnvironment() %>'">Request a different Item</button></td>
   </tr>
   <tr>
    <td>Item Number/Status:</td>
    <td colspan="2"><b><%= bitem.getItemClass().getStatus() %></b>&#160;&#160;<%= bitem.getItemClass().getStatusDescription().trim() %></td>
   </tr>
   <tr>
    <td>Item Description:</td>
    <td colspan="2"><b><%= bitem.getItemClass().getItemLongDescription() %></b>&#160;</td>
   </tr>
   <tr>
    <td>Item Responsible:</td>
    <td colspan="2"><b><%= UpdItem.determineLongName("PRD", bitem.getItemClass().getResponsible()) %></b></td>
   </tr>
<%
   if (!bitem.getItemClass().getItemType().trim().equals("100") &&
       !bitem.getItemClass().getItemType().trim().equals("120") &&
       !bitem.getItemClass().getItemType().trim().equals("130"))
   {
%>   
   <tr>
    <td>Purchase Price:</td>
    <td colspan="2"><b><%= bitem.getItemClass().getPurchasePrice() %> per <%= bitem.getItemClass().getBasicUnitOfMeasure() %></b></td>
   </tr>
<%
    }
%>   
   <tr>
    <td>Item Tickler Group:</td>
    <td><b><%= groupMain %></b></td>
     <td class="right" style="width:15%">
      <input type="Submit" name="saveButton" value="Save Changes" >   
    </td>
   </tr>
   </table>
   
  <br />
<%
   //------------------------------------------
   //  Additional Basic Information to be Saved
   //-------------------------------------------
%>

	<h3 class="expandOpen ui-widget-header">Basic Item Information</h3>
	<div class="collapse ui-widget-content no-bg">
		<jsp:include page="updItemAdditional.jsp"></jsp:include>
	</div>

<%
   //------------------------------------------
   //  Images.. this section only displays a FOLDER on the IFS that this is going to
   //-------------------------------------------
%>

   <h3 class="expand ui-widget-header">Images</h3>
  	<div class="collapse ui-widget-content no-bg">
  	  <jsp:include page="/view/utilities/displayIFSNewV2.jsp"></jsp:include>
  	  <%--<jsp:include page="/APP/Utilities/displayIFSNew.jsp"></jsp:include> --%>
  	  
  	  
  	  <%-- don't need a submit button for this section 
  	  	<div style="text-align:right">
  	  	<input type="Submit" value="Save Changes" name="saveButton">
 	  </div>
  	   --%>
  	   
  	</div>

<%
   //------------------------------------------
   //  Additional Images and Documents directly tied to this item
   //-------------------------------------------
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("listKeyValues", bitem.getItemUrls());

%>
  <h3 class="expand ui-widget-header">Additional Images and Documents</h3>
  	<div class="collapse ui-widget-content no-bg">
  		<%-- <jsp:include page="/APP/Utilities/updKeyValuesV2.jsp"></jsp:include> --%>
  		<jsp:include page="/view/utilities/updKeyValuesV3.jsp"></jsp:include>
  		<div style="text-align:right"><input type="Submit" value="Save Changes" name="saveButton"></div>
  	</div>
<%
   //------------------------------------------
   //  Comments directly tied to this item
   //-------------------------------------------
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", bitem.getComments());  
%>  
  <h3 class="expand ui-widget-header">Comments</h3>
  	<div class="collapse ui-widget-content no-bg">
  		<jsp:include page="/view/utilities/updKeyValuesV3.jsp"></jsp:include>
  		<div style="text-align:right"><input type="Submit" value="Save Changes" name="saveButton"></div>
  	</div>   
<%
   //------------------------------------------
   //  Responsibilities / Tickler Section
   //-------------------------------------------
   if (bitem.getFunctions().size() > 0)
   {
      request.setAttribute("listResponsibilities", bitem.getFunctions());
%>
    <h3 class="expandOpen ui-widget-header">Responsibilities</h3>
  	<div class="collapse ui-widget-content no-bg">
  	 <jsp:include page="updItemFunctionDetail.jsp"></jsp:include>
     <div style="text-align:right"><input type="Submit" value="Save Changes" name="saveButton"></div>
  	</div>

<%
  }
   //------------------------------------------
   //  GTIN FAMILY TREE
   //-------------------------------------------
 if (!ui.getPalletGTIN().trim().equals("") && !bitem.getItemClass().getItemType().equals("110"))
 {
  //------GTIN Family Tree------
     request.setAttribute("reqType", ui.getRequestType());

%>  	
	<h3 class="expandOpen ui-widget-header">GTIN Family Tree</h3>
  	<div class="collapse ui-widget-content no-bg">
  		 <%-- <jsp:include page="/APP/GTIN/dtlGTINFamilyTree.jsp"></jsp:include> --%>
  		 <jsp:include page="/view/gtin/dtlGTINFamilyTree.jsp"></jsp:include>
  	</div>
<%
  } // end if 

  }
%>     
</form>
 

 <%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>