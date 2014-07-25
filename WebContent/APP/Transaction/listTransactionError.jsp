<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.transaction.*" %>
<%

//---------------  listTransactionError.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  9/22/09
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "/Transaction/listTransactionError.jsp";
  String listTitle = "List Transaction Errors";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqTransaction it = new InqTransaction();
 try
 {
	it = (InqTransaction) request.getAttribute("inqViewBean");
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
   setExtraOptions.append("<option value=\"/web/CtlTransaction?requestType=inqTransactionError\">Select Again");
   setExtraOptions.append("<option value=\"/web/CtlTransaction?requestType=inqTransactionError&");
   setExtraOptions.append(it.buildParameterResend());
   setExtraOptions.append("\">Return To Selection Screen");
					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());    
  request.setAttribute("msg", it.getDisplayMessage());   
//*****************************************************************************
%>
<html>
  <head>
    <title><%= listTitle %></title>
    <%= JavascriptInfo.getExpandingSectionHead("", 0, "Y", 1) %>
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
                <%= it.buildParameterDisplay() %>
              </td> 
            </tr>
          </table>
        <%= HTMLHelpers.endSpan() %>
      </td>
      <td style="width:2%">&nbsp;</td>  
    </tr>
  </table>
    <jsp:include page="listTransactionErrorTable.jsp"></jsp:include>
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