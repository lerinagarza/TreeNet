<%@ page language = "java" %>
<%@ page import = "com.treetop.app.item.InqItem" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanItem" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>


<%
//------------------ listItemVariance.jsp ------------------------------//
//
// Author :  Teri Walton  6/13/02
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  9/12/08    TWalton	   Redo Process - using Servlet and new Stylesheet
//  2/25/04    TWalton     Changed comments and images for 5.0 server.
// 12/19/02    TWalton     Moved Live to use new improved files       
//-----------------------------------------------------------------------//
 String errorPage = "Item/listItemVariance.jsp";
 String listTitle = "Variances for Chosen Item";  
 // Bring in the Build View Bean.
 // Selection Criteria
 String returnMessage = "";
 InqItem inqItem = new InqItem();
 BeanItem bi = new BeanItem();
 try
 {
	inqItem = (InqItem) request.getAttribute("inqViewBean");
	bi = (BeanItem) inqItem.getListReport().elementAt(0);
	if (inqItem.getAllowUpdate().equals("Y") &&
	    !inqItem.getShowPending().equals(""))
	    request.setAttribute("showUpdate", "showUpdate");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
   request.setAttribute("title",listTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlItem?requestType=inqVariance\">Choose Again");
   if (inqItem.getAllowUpdate().equals("Y") &&
	    !inqItem.getShowPending().equals(""))
     setExtraOptions.append("<option value=\"/web/CtlItem?requestType=addVariance&item=" + inqItem.getInqItem().trim() + "\">Add New Variance");
   request.setAttribute("extraOptions", setExtraOptions.toString());      
//*****************************************************************************
%>
<html>
 <head>
  <title><%= listTitle %></title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 3, "Y", 3) %>
  </head>
  <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
   // Go out and set the Vector to be used in the TABLE
   request.setAttribute("listVariances", bi.getItemVariance());
%>
 <br> 
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td>
    <%= JavascriptInfo.getExpandingSection("O", "Current Variances", 0, 1, 3, 1, 0) %>
     <jsp:include page="listItemVarianceTable.jsp"></jsp:include>
    </span>    
   </td>
  </tr>
 </table>
<%
   // Go out and set the Vector to be used in the TABLE
   request.setAttribute("listVariances", bi.getItemVarianceFuture());
%> 
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td>
    <%= JavascriptInfo.getExpandingSection("C", "Future Variances", 0, 2, 4, 1, 0) %>
    <jsp:include page="listItemVarianceTable.jsp"></jsp:include>
    </span>    
   </td>
  </tr>
 </table> 
<%
   // Go out and set the Vector to be used in the TABLE
   request.setAttribute("listVariances", bi.getItemVariancePast());
%> 
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td>
    <%= JavascriptInfo.getExpandingSection("C", "Past Variances", 0, 3, 5, 1, 0) %>
     <jsp:include page="listItemVarianceTable.jsp"></jsp:include>
    </span>    
   </td>
  </tr>
 </table> 
 <jsp:include page="../../Include/footer.jsp"></jsp:include>

   </body>
</html>