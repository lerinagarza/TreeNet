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
<%@ page import="
    com.treetop.app.quality.InqFormula,
    com.treetop.app.quality.GeneralQuality,
    com.treetop.businessobjectapplications.BeanQuality,
    com.treetop.utilities.html.DropDownSingle,
    com.treetop.utilities.html.HTMLHelpersInput,
    java.util.Vector" %>
<%
//---------------- APP/Quality/inqFormula.jsp -----------------------//
// Author   :  Teri Walton       5/25/10 (thrown from servlet)
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String errorPage = "Quality/inqFormula.jsp";
  String inqTitle = "Search for a Formula";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists
 InqFormula inqFormula = new InqFormula();
 
 try {
    inqFormula = (InqFormula) request.getAttribute("inqViewBean");
    if (inqFormula.getRequestType().equals(""))
       inqFormula.setRequestType("inqFormula");
 } catch(Exception e) { }  
 
 boolean allowAdd = false;
 if (inqFormula.getSecurityLevel().trim().equals("2")) {
    allowAdd = true;
 }
 

   
%>
	<title><%=inqTitle %></title>
<%-- /tpl:put --%>
<jsp:include page="/view/template/scripts.jsp"></jsp:include>
<%-- tpl:put name="js" --%>
	
<%-- /tpl:put --%>
</head>
<body>
<jsp:include page="/view/template/stylesheets.jsp"></jsp:include>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<%-- tpl:put name="bodyarea" --%>
		
	<div class="clearfix">
	   <h1 style="float:left">
	       Search for a Formula
	   </h1>
<%  if (allowAdd) { %> 
           <div style="float:right">
               <a class="ui-add" href="/web/CtlQuality?requestType=addFormula&environment=<%=inqFormula.getEnvironment() %>" target = "blank">
                   Add a NEW Formula
               </a>
           </div>
<%  } %>
	</div>
	
	
<%  if (!inqFormula.getDisplayMessage().trim().equals("")) { %>
    <div class="ui-error">
        <%=inqFormula.getDisplayMessage().trim() %>
    </div>
<%  } %>

<form action="/web/CtlQuality?requestType=listFormula" method="post">
	
	<fieldset>
	   <legend>Choose:</legend>
	   <input type="hidden" name="environment" value="<%=inqFormula.getEnvironment() %>">
    <div class="table-wrapper">
	<table>
	   <tr>
           <td>
               <label title="Must be a valid Formula" for="inqFormulaName">
                   Formula Number
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxNumber("inqFormulaName", inqFormula.getInqFormulaName(), "Formula Number", 10, 10, "N", "N") %>
               <div class="error"><%= inqFormula.getInqFormulaNumberError() %></div>
           </td>
           <td>
               <label title="This will use what is entered and search the ENTIRE description for that value." for="inqFormulaDescription">
                   Formula Description
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxText("inqFormulaDescription", inqFormula.getInqFormulaDescription(), "Formula Description", 30, 30, "N", "N") %>
           </td>
       </tr>
       
       <tr>
           <td>
               <label title="Must be a VALID M3(Movex, Lawson) Item Number." for="inqItemNumber">
                   Item Number
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxNumber("inqItemNumber", inqFormula.getInqItemNumber(), "Item Number", 12, 12, "N", "N") %>
               <div class="error"><%= inqFormula.getInqItemNumberError() %></div>
           </td>
           <td>
               <label title="This will use what is entered and search the ENTIRE description for that value." for="inqItemDescription">
                   Item Description
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxText("inqItemDescription", inqFormula.getInqItemDescription(), "Item Description", 30, 30, "N", "N") %>
           </td>
       </tr>
       
       <tr>
           <td>
               <label title="Must be a VALID M3(Movex, Lawson) or Sample System Customer Number." for="inqCustomerNumber">
                   Customer Number
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxText("inqCustomerNumber", inqFormula.getInqCustomerNumber(), "Customer Number", 10, 10, "N", "N") %>
               <div class="error"><%= inqFormula.getInqCustomerNumberError() %></div>
           </td>
           <td>
               <label title="This will use what is entered and search the ENTIRE name for that value." for="inqCustomerName">
                   Customer Name
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxText("inqCustomerName", inqFormula.getInqCustomerName(), "Customer Name", 30, 30, "N", "N") %>
           </td>
       </tr>
       
       <tr>
           <td>
               <label title="Must be a VALID M3(Movex, Lawson) Supplier Number." for="inqSupplierNumber">
                   Supplier Number
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxText("inqSupplierNumber", inqFormula.getInqSupplierNumber(), "Supplier Number", 10, 10, "N", "N") %>&#160;&#160;
               <div class="error"><%= inqFormula.getInqSupplierNumberError() %></div>
           </td>
           <td>
               <label title="This will use what is entered and search the ENTIRE name for that value." for="inqSupplierName">
                   Supplier Name
               </label>
           </td>
           <td>
               <%= HTMLHelpersInput.inputBoxText("inqSupplierName", inqFormula.getInqSupplierName(), "Supplier Name", 30, 30, "N", "N") %>
           </td>
       </tr>
       
       <tr>
           <td>
               <label title="Choose a Formula Type." for="inqFormulaType">
                   Type
               </label>
           </td>
           <td>
               <%= DropDownSingle.buildDropDown(GeneralQuality.getListType(), "inqFormulaType", inqFormula.getInqFormulaType(), "", "N", "N") %>
           </td>
           <td colspan="2"></td>
       </tr>
<%  if (inqFormula.getSecurityLevel().trim().equals("2")) { %>       
       <tr>
           <td>
               <label title="Choose a Status" for="inqStatus">
                   Status
               </label>
           </td>
           <td>
               <%= DropDownSingle.buildDropDown(GeneralQuality.getListStatus(), "inqStatus", inqFormula.getInqStatus(), "", "N", "N") %>
           </td>
           <td colspan="2"></td>
       </tr>
<%  } else { %>  
        <tr>
            <td colspan="4">
                <%=HTMLHelpersInput.inputBoxHidden("inqStatus", "AC") %>
            </td>
        </tr>
<%  } %>     
       <tr>
           <td>
               <label title="Choose a Scope" for="inqScope">
                   Scope
               </label>
           </td>
           <td>
               <%= DropDownSingle.buildDropDown(GeneralQuality.getListScope(), "inqScope", inqFormula.getInqScope(), "", "N", "N") %>
           </td>
           <td colspan="2"></td>
       </tr>
       
       <tr>
           <td>
               <label title="Choose an Origination" for="inqOrigination">
                   Origination
               </label>
           </td>
           <td>
               <%= DropDownSingle.buildDropDown(GeneralQuality.getListOrigination(), "inqOrigination", inqFormula.getInqOrigination(), "", "N", "N") %>
           </td>
           <td colspan="2"></td>
       </tr>    
       
       <tr>
           <td>
               <label title="Choose who this formual was approved by" for="inqApprovedBy">
                   Approved By
               </label>
           </td>
           <td>
               <%= DropDownSingle.buildDropDown(GeneralQuality.getListApprovedByUser(), "inqApprovedBy", inqFormula.getInqApprovedBy(), "", "N", "N") %>
           </td>
           <td colspan="2"></td>
       </tr>
       
       <tr>
            <td colspan="4" class="center">
                <input type="submit" name="saveButton" value="List Formulas" class="ui-button">
            </td>
        </tr>
	</table>
	</div>
	</fieldset>
</form>
	
		<%-- /tpl:put --%>
<jsp:include page="/view/template/footer.jsp"></jsp:include>
</body>
</html><%-- /tpl:insert --%>