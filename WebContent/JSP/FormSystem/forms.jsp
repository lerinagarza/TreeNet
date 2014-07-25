<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.servlets.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
   String titleF           = "";
   FormHeader headingInfo  = new FormHeader();
   Integer formNumber      = new Integer("0");
try
{
	headingInfo = (FormHeader) request.getAttribute("formHeader");
	formNumber  = headingInfo.getFormNumber();
	titleF      = formNumber + " - " + headingInfo.getFormTitle();
		
}
catch(Exception e)
{
}

   String requestType = (String) request.getAttribute("requestType");
   if (requestType == null)
     requestType = "inq";  
      
   String extraOptionsF   = "";
   if (requestType.equals("list"))
   {
      extraOptionsF = "<option value=\"/web/CtlForms\">Choose Another Report" +
                      "<option value=\"/web/CtlForms?requestType=inq" +
                                      "&formNumber=" + formNumber + "\">Go Back to Selection";     
   }  


//----------------------forms.jsp--------------------------//
//    Author :  Teri Walton      Date: 11/04/2003
//    CHANGES:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  3/09/04   TWalton      Changed comments and images for 5.0 server.
//---------------------------------------------------------//
      
 //********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", titleF);
   request.setAttribute("extraOptions", extraOptionsF); 
      
  
//--------------------------------------------------
//  Specific to the Forms System
//--------------------------------------------------     
   int sectionCount = 1;
   request.setAttribute("sectionCount", new Integer(sectionCount));
   int imageCount   = 3;     
   request.setAttribute("imageCount", new Integer(imageCount));
  
   
   
%>
<html>
  <head>
    <%= JavascriptInfo.getCalendarHead() %>
    <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 1) %>
    <%= JavascriptInfo.getChangeSubmitButton() %>   
    <%= JavascriptInfo.getNumericCheck() %>
    <%= JavascriptInfo.getConfirmAlertBox("deleteTrans", "Click OK to Delete this transaction OR Cancel to return")%>
    <%= JavascriptInfo.getRequiredField() %>
    <%= JavascriptInfo.getCheckTextareaLength() %>
    <title><%= titleF %></title>
  </head>
  <body>
    <jsp:include page="../include/heading.jsp"></jsp:include>
<%

   if (requestType.equals("inq"))
   {    
%>    
  <table class="table01001" cellspacing="0">
    <tr>
      <td style="width:2%">&nbsp;</td>
      <td>
      <jsp:include page="../FormSystem/inquiryHeadingStandard.jsp"></jsp:include>
      </td>
      <td style="width:2%">&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>
      <jsp:include page="inquiryStandard.jsp"></jsp:include>
      </td>
      <td>&nbsp;</td>
    </tr>   
  </table>
<%
  }
   if (requestType.equals("list"))
   {  
%>    
  <table class="table01001" cellspacing="0">
    <tr>
      <td style="width:2%">&nbsp;</td>
      <td>
      <jsp:include page="../FormSystem/headingStandard.jsp"></jsp:include>
      </td>
      <td style="width:2%">&nbsp;</td>
    </tr>
    
<%
   if (request.getParameter("throwAnotherPage") == null ||
       request.getParameter("throwAnotherPage").equals("") ||
       request.getParameter("throwAnotherPage").equals("N") )
   {    
%>
    <tr>
      <td>&nbsp;</td>
      <td>
      <jsp:include page="../FormSystem/listStandard.jsp"></jsp:include>
      </td>
      <td>&nbsp;</td>
    </tr>
<%
   }
%>    
    <tr>
      <td>&nbsp;</td>
      <td>
      <jsp:include page="../FormSystem/updateStandard.jsp"></jsp:include>
      </td>
      <td>&nbsp;</td>
    </tr>  
   </table> 
<%
  }
   if (requestType.equals("detail"))
   {  
%>   
  <table class="table01001" cellspacing="0">
    <tr>
      <td style="width:2%">&nbsp;</td>
      <td>
      <jsp:include page="../FormSystem/headingStandard.jsp"></jsp:include>
      </td>
      <td style="width:2%">&nbsp;</td>
    </tr>     
    <tr>
      <td>&nbsp;</td>
      <td>
      <jsp:include page="../FormSystem/detailStandard.jsp"></jsp:include>
      </td>
      <td>&nbsp;</td>
    </tr>                   
  </table>
<%
  }   
%>
<jsp:include page="../include/footer.jsp"></jsp:include>
   </body>
</html>