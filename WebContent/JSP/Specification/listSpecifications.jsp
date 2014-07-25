<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %> 
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.servlets.*" %>
<%@ page import = "com.treetop.utilities.html.*" %> 
<%@ page import = "java.util.Vector" %>
<%
//---------------- listSpecifications.jsp -----------------------//
//    Change name from listIngSpecs.jsp
//   Author :  Charlena Paschen  03/21/02
//   Changes:
//   Date       Name       Comments
// --------   ---------   --------------------------------------//
//  3/21/05   TWalton      Changed Name, changed from JSP to Servlet Driven
//  3/09/04   TWalton      Changed comments and images for 5.0 server.
//--------------------------------------------------------------//
//********************************************************************
  String goldTitle = "List of Selected Specifications";
   String[] parameterValues = (String[]) request.getAttribute("parameterValues");

   request.setAttribute("title", goldTitle );
   String setExtraOptions = "<option value=\"/web/CtlSpecification\">Choose Again" +
                            "<option value=\"/web/CtlSpecification?" + parameterValues[1] + 
                             parameterValues[2] + "\">Return To Selection Screen";
   request.setAttribute("extraOptions", setExtraOptions);      
   
   String   requestTypeA     = (String) request.getAttribute("requestType");
   if (requestTypeA == null)
     requestTypeA = "list";
   
 String showinventory = request.getParameter("showinventory");
 if (showinventory == null ||
     showinventory.equals("N"))
   showinventory = "N";
 else
   showinventory = "Y";  
 if (request.getParameter("showrevisions") != null &&
     request.getParameter("showrevisions").equals("Show Summary"))
    showinventory = "Y";
   
 String showcustomer = request.getParameter("showcustomer");
 if (showcustomer == null ||
     showcustomer.equals("N"))
   showcustomer = "N";
 else
   showcustomer = "Y";  
 String showvariety = request.getParameter("showvariety");
 if (showvariety == null ||
     showvariety.equals("N"))
   showvariety = "N";                 
 else
   showvariety = "Y";
 String orderInfo = (String) request.getAttribute("orderInformation");
 if (orderInfo == null)
    orderInfo = "";	  
   int listSize = 0;
   try
   {
      Vector reportList1     = (Vector) request.getAttribute("reportList");
      listSize = reportList1.size();
   }
   catch(Exception e)
   {
   } 
%>
<html>
  <head>
    <title><%= goldTitle %></title>
    <%= JavascriptInfo.getClickButtonOnlyOnce() %>
    <%= JavascriptInfo.getChangeSubmitButton() %>   
    <%= JavascriptInfo.getExpandingSectionHead("Y", listSize, "Y", 1) %>
  </head>
  <body>
    
<jsp:include page="../include/heading.jsp"></jsp:include>    
  <table class="table01001" cellspacing="0">
    <form action="/web/CtlSpecification?requestType=list<%= parameterValues[1] %><%= orderInfo %>" method="post">   
    <tr>
      <td style="width:2%">
      </td>
      <td class="td048CL001">
        <%= JavascriptInfo.getExpandingSection("C", "Report Selection Criteria", 8,  1, 3, 1, 0) %>
          <table class="table01001" cellspacing="0">        
            <tr>
              <td class="td048CL001">
                <%= parameterValues[0] %>  
              </td>
            </tr>
          </table>
        </span>    
      </td>
      <td class="td047CR001">
      <%             
     	//Show Inventory&nbsp;&nbsp;<%= HTMLHelpersInput.inputCheckBox("showinventory", showinventory, "N"
      %> 
	  <td>  
      <td class="td047CR001">   
        Show Variety Information&nbsp;&nbsp;<%= HTMLHelpersInput.inputCheckBox("showvariety", showvariety, "N") %> 
     	<br>
        Show Customer Information&nbsp;&nbsp;<%= HTMLHelpersInput.inputCheckBox("showcustomer", showcustomer, "N") %> 
	  <td>   	                    
      <td class="td047CR001">  
Show Revisions&nbsp;&nbsp;<%= CtlSpecification.buildShowRevisionsDropDown(request.getParameter("showrevisions")) %>
        
	  <td>              
      <td style="width:5%">
        <%= HTMLHelpers.buttonGo("listAgain") %>
      </td>      
    </tr>
    </form>
  </table>
   

<jsp:include page="listSpecificationsTable.jsp"></jsp:include>

<jsp:include page="../include/footer.jsp"></jsp:include>
   </body>
</html>