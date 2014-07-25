<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>

<%
//---------------- lotRouter.jsp --------------------------------------------//
//  Author :  Teri Walton 1/7/05                                          
//
//   Date       Name       Comments
// --------   ---------   -------------
//
//-------------------------------------------------------------------------------//
  //****  for the headings and such to work ***//
   request.setAttribute("title","Lot Information Available");
  
//******************************************************************************
//   Get, Check & Edit Parameter Fields (What to do if null)
//******************************************************************************
   String requestType  = request.getParameter("requestType");
   if (requestType == null)
     requestType = "";

   String lotNumber = request.getParameter("lot");
   if (lotNumber == null)
      lotNumber = "";
      
   String resourceNumber = request.getParameter("resource");
   if (resourceNumber == null)
      resourceNumber = "";
      
   String lotType = request.getParameter("lotType");
   if (lotType == null)
      lotType = "";
   String lotDescription = "";
   
    
//   if (lotType.equals("") &&
//       !resourceNumber.equals(""))
///  {
//      lotType = Resource.findTypeByResource(resourceNumber);
//   }    
  
//   String       lotDescription = "&nbsp;";
//   try
//   {
 //  	   InventoryLot lotInfo   = new InventoryLot(lotNumber, 
  // 	   								lotType); 
   //	   	if (lotInfo.getResource() != null)
   	//   	{
   	 //  	   lotDescription = lotInfo.getResource();
   	  // 	   try
   	   //	   {
   	   	//   		Resource resourceInfo = new Resource(lotInfo.getResource());
   	   	 //  		if (resourceInfo.getResourceDescription() != null)
   	   	  // 		{
   	   	   //		   lotDescription = lotDescription + "&nbsp;&nbsp;&nbsp;" +
   	   	   //		   				    resourceInfo.getResourceDescription();
   	   	   //		}
//   	   	   }
 //  	   	   catch(Exception e)
  // 	   	   {
   //	   	   }
   	//   	}
//   }      
//   catch(Exception e)
//   {
 //  }

//******************************************************************************
%>

<html>
   <body>
   <jsp:include page="../include/heading.jsp"></jsp:include>
  <table class="table00001">
    <tr>
      <td class="td042CL001"><b>Lot Requested: </b></td>
      <td class="td072CL001"><b><%= lotNumber %></b></td>
      <td class="td072CL001"><b>Resource: <%= lotDescription %></b></td>
    </tr>
  </table>
  <table class="table01001">
<%   
//------------------------------------------------------------------------------->
//--   Main Information...                                                            -->
//------------------------------------------------------------------------------->
%>
      <tr class="tr02001">
         <td class="td042CL001" colspan="2"><b>Information...</b></td>
      </tr>
    <tr>
      <td class="td076CL001" colspan="2">&nbsp;&nbsp;</td>
    </tr>
<%
//------------------------------------------------------------------------------->
//--   Searches ...                                                            -->
//------------------------------------------------------------------------------->
%>
      <tr class="tr02001">
         <td class="td042CL001" colspan="2">
            <b>Searches...</b>
         </td>
     </tr>
     <tr>
      <td class="td076CL001" colspan="2">&nbsp;&nbsp;</td>
    </tr>
      
  </table>

    <%@ include file="../include/footer.jsp" %>

   </body>
</html>