<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "java.net.URLEncoder" %>
<%
//---------------- specificationRouter.jsp --------------------------------------------//
//  Author :  Teri Walton 3/31/05                                          
//
//   Date       Name       Comments
// --------   ---------   -------------
//
//-------------------------------------------------------------------------------//
  //****  for the headings and such to work ***//
   request.setAttribute("title","Specification Information Available");
  
//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************

   String specification = request.getParameter("specification");
   
   if (specification == null)
      specification = "";
   else
      specification = URLDecoder.decode(specification);
      
   String specificationDate = request.getParameter("specificationDate");
   if (specificationDate == null)
      specificationDate = "";
      
   String  specificationDescription = "&nbsp;";
   String  currentSpec = "N";
   try
   {
       Specification info = new Specification();
       if (specificationDate.equals(""))
       	  info = new Specification(specification);
       else
   	      info = new Specification(specification, 
   	   							   specificationDate); 
   	   							   
   	   	if (info.getGeneralDescription() != null)
   	   	   specificationDescription = info.getGeneralDescription();
   	   	if (info.getCurrentVersion())
   	   	  currentSpec = "Y";
   }      
   catch(Exception e)
   {
   }

//******************************************************************************
%>

<html>
   <body>
   <jsp:include page="../include/heading.jsp"></jsp:include>
  <table class="table00001">
    <tr>
      <td class="td042CL001"><b>Specification Requested: </b></td>
      <td class="td072CL001"><b><%= specification %></b>&nbsp;&nbsp;<%= specificationDescription %></td>
<%
   if (!specificationDate.equals(""))
   {
%>      
      <td class="td042CL001"><b>Revision Date:</b></td>
      <td class="td072CL001"><b><%= specificationDate %></b>&nbsp;</td>      
<%
   }
%>      
    </tr>
  </table>
  <table class="table01001">
    <tr class="tr02001">
      <td class="td042CL001" colspan="2"><b>List of Links...</b></td>
    </tr>
    <tr>
      <td class="td076CL001" colspan="2">
         <a class="a04001" href="/web/CtlSpecification">
			Search for Specifications
		 </a> Click on this to go to the search for all Specifications.
      </td>
    </tr>    
    <tr>
      <td class="td076CL001" colspan="2">
         <a class="a04001" href="/web/CtlSpecification?requestType=detail&specification=<%= URLEncoder.encode(specification.trim()) %>">
			Detail Information (Current)
		 </a> Click on this to view all details for the current version of specification <%= specification %>.
      </td>
    </tr>    
<%
   if (!specificationDate.equals(""))
   {
     if (currentSpec.equals("N"))
     {
%>
    <tr>
      <td class="td076CL001" colspan="2">
         <a class="a04001" href="/web/CtlSpecification?requestType=detail&specification=<%= URLEncoder.encode(specification.trim()) %>&specificationDate=<%= specificationDate %>">
			Detail Information (<%= specificationDate %>)
		 </a> Click on this to view all details for this revision of specification <%= specification %>.
      </td>
    </tr>
<%
      }

   }
%>    

    <tr>
      <td class="td076CL001" colspan="2">&nbsp;&nbsp;</td>
    </tr>
  </table>

    <%@ include file="../include/footer.jsp" %>

   </body>
</html>