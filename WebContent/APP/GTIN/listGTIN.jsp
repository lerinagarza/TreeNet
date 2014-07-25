<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%



//---------------  listGTIN.jsp  ------------------------------------------//
//   Includes Sections (Tables)
//      listGTINTable
//
//   Author :  Teri Walton  8/30/05   
//   Changes:  // Converted from 
//                listUCCnet.jsp  Author :  Charlena Paschen 06/03/04
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look
//------------------------------------------------------//
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "/GTIN/listGTIN.jsp";
  String listTitle = "List Global Trade Item Numbers";  
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
   request.setAttribute("title",listTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlGTIN\">Select Again");
   setExtraOptions.append("<option value=\"/web/CtlGTIN?requestType=inquiry&");
   setExtraOptions.append(ig.buildParameterResend());
   setExtraOptions.append("\">Return To Selection Screen");
   setExtraOptions.append("<option value=\"/web/CtlGTIN?requestType=add\">Add New GTIN");
					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************
%>
<html>
  <head>
    <title><%= listTitle %></title>
    <%= JavascriptInfo.getExpandingSectionHead("", 0, "Y", 1) %>
    <%= JavascriptInfo.getConfirmAlertBox("deleteTrans", "Click OK to Delete this GTIN OR Cancel to return")%>    
    <%= JavascriptInfo.getClickButtonOnlyOnce() %>
    <%= JavascriptInfo.getChangeSubmitButton() %>   
  </head>
  <body>
     <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
  try
  {
%>  
  <table class="table00" cellspacing="0">
    <tr>
      <td style="width:2%">&nbsp;</td>
      <td class="td0410">
        <%= JavascriptInfo.getExpandingSection("C", "Selection Criteria", 8, 1, 3, 1, 0) %>
          <table class="table01" cellspacing="0">        
            <tr>
              <td class="td0410">
                <%= ig.buildParameterDisplay() %>
              </td> 
            </tr>
          </table>
        <%= HTMLHelpers.endSpan() %>
      </td>
<%
 String urlTo = "/web/CtlGTIN?requestType=list" + ig.buildParameterResend();
 String displayView = request.getParameter("displayView");
 if (displayView == null)
   displayView = "";
%>        
      <form action="<%= urlTo %>" method="post"> 
        <td class="td0410" style="text-align:right; width:30%">
          <%= InqGTIN.buildDropDownView(displayView) %>
        </td>	
        <td style="width:5%">
         <%= HTMLHelpers.buttonGo("") %>
        </td>
      <%= HTMLHelpersInput.endForm() %>
                   
      <td style="width:2%">&nbsp;</td>  
    </tr>
  </table>
<%
   if (displayView.equals(""))
   { // Standard View
%>  
    <jsp:include page="listGTINTable.jsp"></jsp:include>
<%
    }
   if (displayView.toLowerCase().equals("description"))
   { // Description View
%>  
    <jsp:include page="listGTINTableDescription.jsp"></jsp:include>
<%
    }  
   if (displayView.toLowerCase().equals("dimension"))
   { // Description View
%>  
    <jsp:include page="listGTINTableDimension.jsp"></jsp:include>
<%
    }    
   if (displayView.toLowerCase().equals("weightandcontent"))
   { // Description View
%>  
    <jsp:include page="listGTINTableWeightContent.jsp"></jsp:include>
<%
    }   
   if (displayView.toLowerCase().equals("relationship"))
   { // Description View
%>  
    <jsp:include page="listGTINTableRelationship.jsp"></jsp:include>
<%
    }   
   if (displayView.toLowerCase().equals("trueandfalse"))
   { // Description View
%>  
    <jsp:include page="listGTINTableTrueFalse.jsp"></jsp:include>
<%
    }        
   if (displayView.toLowerCase().equals("miscellaneous"))
   { // Description View
%>  
    <jsp:include page="listGTINTableMisc.jsp"></jsp:include>
<%
    }           
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