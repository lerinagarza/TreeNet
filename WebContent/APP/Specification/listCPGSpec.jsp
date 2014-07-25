<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%

//---------------  listCPGSpec.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  6/03/02
//      moved to Production  7/10/02
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//	  10/22/08    TWalton	   Changed to Point to New Box, with New Stylesheet
//    10/12/06    TWalton      Changed to look at New Item File AND Resource Master File
//    2/25/04     TWalton      Changed comments and images for 5.0 server.
//    02/05/04    TWalton      Add Shelf Life
//    05/20/03    cpaschen     Update with new presentation style
//    12/19/02    TWalton      Moved Live to use new improved files
//     9/9/02     TWalton      Changed to Reflect Current Standards
//     8/15/02    TWalton      Changed SQL statements to better create list
//---------------------------------------------------------//

//********************************************************************
  String errorPage = "/Specification/listCPGSpec.jsp";
  String listTitle = "List CPG Type Specifications";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqSpecification is = new InqSpecification();
 try
 {
	is = (InqSpecification) request.getAttribute("inqViewBean");
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
   setExtraOptions.append("<option value=\"/web/CtlSpecificationNEW\">Select Again");
   setExtraOptions.append("<option value=\"/web/CtlSpecificationNEW?requestType=inqCPGSpec&");
   setExtraOptions.append(is.buildParameterResend());
   setExtraOptions.append("\">Return To Selection Screen");
   if (!is.getAllowUpdate().equals(""))   
     setExtraOptions.append("<option value=\"/web/CtlSpecificationNEW?requestType=addCPGSpec\">Add New Specification");
					                              
  request.setAttribute("extraOptions", setExtraOptions.toString());       
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
                <%= is.buildParameterDisplay() %>
              </td> 
            </tr>
          </table>
        </span>    
      </td>
      <td style="width:2%">&nbsp;</td>  
    </tr>
  </table>
    <%@ include file="listCPGSpecTable.jsp" %>
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