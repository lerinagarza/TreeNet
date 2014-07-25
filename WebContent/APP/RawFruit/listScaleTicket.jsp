<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------  listScaleTicket.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  2/20/09
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//---------------------------------------------------------//
//********************************************************************
  String errorPage = "/Specification/listCPGSpec.jsp";
  String listTitle = "List Scale Tickets";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqRawFruit ir = new InqRawFruit();
 try
 {
	ir = (InqRawFruit) request.getAttribute("inqViewBean");
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
   if (ir.getAllowUpdate().equals("Y"))
      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a Load");
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket\">Select Again");
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket&");
   setExtraOptions.append(ir.buildParameterResend());
   setExtraOptions.append("\">Return To Selection Screen");
//   if (!is.getAllowUpdate().equals(""))   
//     setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=addCPGSpec\">Add New Specification");
					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());    
  request.setAttribute("msg", ir.getDisplayMessage());   
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
                <%= ir.buildParameterDisplay() %>
              </td> 
            </tr>
          </table>
        <%= HTMLHelpers.endSpan() %>
      </td>
      <td style="width:2%">&nbsp;</td>  
    </tr>
  </table>
   <jsp:include page="listScaleTicketTable.jsp"></jsp:include> 
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