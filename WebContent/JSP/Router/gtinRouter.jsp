<%@ page language = "java" %>
<%@ page import = "com.treetop.services.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanGTIN" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- gtinRouter.jsp --------------------------------------------//
//  Author :  Teri Walton 1/3/06                                          
//
//   Date       Name       Comments
// --------   ---------   -------------
//
//-------------------------------------------------------------------------------//
  //****  for the headings and such to work ***//
   request.setAttribute("title","Gtin Information Available");
  
//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************
   String requestType  = request.getParameter("requestType");
   if (requestType == null)
     requestType = "";

   String gtinNumber = request.getParameter("gtin");
   if (gtinNumber == null)
      gtinNumber = "";
 
   String gtinDescription = "&nbsp;";
 
   BeanGTIN gd = new BeanGTIN();
   if (!gtinNumber.equals(""))
   {
     try
     {
       gd = ServiceGTIN.buildGtinDetail(gtinNumber);
       if (gd.getGtinDetail().getGtinDescription() != null &&
       	   !gd.getGtinDetail().getGtinDescription().equals(""))
       {
         gtinDescription = gd.getGtinDetail().getGtinDescription();
       }	   
     }
     catch(Exception e)
     {
       // There is NO gtin Detail
     }
   }
//******************************************************************************
%>
<html>
<head>
  <title>GTIN <%= HTMLHelpersMasking.maskGTINNumber(gtinNumber) %></title>
</head>
   <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <table class="table00">
    <tr>
      <td class="td0514"><b>Gtin Requested: </b></td>
      <td class="td0514"><b><%= HTMLHelpersMasking.maskGTINNumber(gtinNumber) %></b></td>
      <td class="td0514"><b><%= gtinDescription %></b></td>
    </tr>
  </table>
 <table class="table00">
<%   
//------------------------------------------------------------------------------->
//--   Main Information...                                                            -->
//------------------------------------------------------------------------------->
%>
  <tr class="tr02">
   <td class="td0316" colspan="2"><b>Information...</b></td>
  </tr>
<%
  if (gtinNumber.equals("") ||
      gtinDescription.equals(""))
  {
%>  
  <tr>
   <td class="td0414" colspan="2">
    There are NO details for Gtin Number:  <%= gtinNumber %>
   </td>
  </tr>    
<%
  }else{
%>
  <tr>
   <td class="td0414" colspan="2">
    <a class="a0414" href="/web/CtlGTIN?requestType=detail&gtinNumber=<%= gtinNumber %>">
	 Detail Gtin Information
	</a> 
	Click on this to view all information for this specific gtin number.
   </td>
  </tr>
  <tr>
   <td class="td0414" colspan="2">
    <a class="a0414" href="/web/CtlGTIN?requestType=list&inqShowTree=Y&inqGTIN=<%= gtinNumber %>">
	 Detailed Family Tree
	</a> 
	List ALL Elements of the family tree for this gtin.  Will list the GTIN information also.
   </td>
  </tr>  
  <tr>
   <td class="td0414" colspan="2">
    <a class="a0414" href="/web/CtlGTIN?requestType=update&gtinNumber=<%= gtinNumber %>">
	 Maintain Gtin Information
	</a> 
	Click on this to update the GTIN Information Values.
   </td>
  </tr>
<%
   }
%>   
  <tr>
   <td class="td0414" colspan="2">&nbsp;&nbsp;</td>
  </tr>
<%
//------------------------------------------------------------------------------->
//--   Searches ...                                                            -->
//------------------------------------------------------------------------------->
%>
  <tr class="tr02">
   <td class="td0316" colspan="2">
    <b>Searches...</b>
   </td>
  </tr>  
  <tr>
   <td class="td0414" colspan="2">
    <a class="a0414" href="/web/CtlGTIN?requestType=inquiry&inqGTIN=<%= gtinNumber %>">
	 Find A Gtin Number
	</a> 
	Click on this to go to the search for all Global Trade Item Numbers.
   </td>
  </tr>  
  <tr>
   <td class="td0414" colspan="2">&nbsp;&nbsp;</td>
  </tr>
 </table>
    <%@ include file="../../Include/footer.jsp" %>
 </body>
</html>