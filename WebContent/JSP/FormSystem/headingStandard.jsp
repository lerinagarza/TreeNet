<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------listHeadingStandard.jsp--------------------------//
//   THIS Jsp to be included in the forms.jsp.
//    This will display the top part of the Report.
//       It will have:
//				 the Name 
//				 the Report/Form Number
//               selection criteria
//
//    Author :  Teri Walton      Date: 7/30/2004
//    CHANGES:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//-------------------------------------------------------------------//
//******************************************************************************
//   Receiving in Request Session Variables
//******************************************************************************

   java.util.Vector definitionInfoHS  = null;
   int              definitionSizeHS = 0;
   
   String requestTypeHS = (String) request.getAttribute("requestType");
   if (requestTypeHS == null)
     requestTypeHS = "inq";   
   
   String[] parameterValuesHS  = (String[]) request.getAttribute("parameterValues");
try
{
    if (requestTypeHS.equals("list"))
    	definitionInfoHS = (java.util.Vector) request.getAttribute("listDefinition");
    else
		definitionInfoHS = (java.util.Vector) request.getAttribute("definitionInfo");
		
	definitionSizeHS = definitionInfoHS.size();
	
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
   int    imageCountHS   = 0;
   int    sectionCountHS = 0;
try
{
   imageCountHS   = ((Integer) request.getAttribute("imageCount")).intValue();
   sectionCountHS = ((Integer) request.getAttribute("sectionCount")).intValue();
}
catch(Exception e)
{
}
%>
<html>
  <head>
  </head>
  <body>

<%
try
{
if (definitionSizeHS > 0)
{
   com.treetop.data.FormDefinition thisFormHS = (com.treetop.data.FormDefinition) definitionInfoHS.elementAt(0);
%>
  <table class="table01001" cellspacing="0">
	<tr>
      <td style="width:2%">
      </td>
      <td class="td048CL001">
            <%= JavascriptInfo.getExpandingSection("C", "Selection Information", 0, sectionCountHS, imageCountHS, 0, 0) %>
<%
   imageCountHS   = imageCountHS + 1;
   sectionCountHS = sectionCountHS + 1;   
%>            
          <table class="table01001" cellspacing="0">        
            <tr>
              <td class="td048CL001" style="width="3%">
                &nbsp;
              </td>  
              <td class="td048CL001">
                Form#:<%= thisFormHS.getFormNumber() %><br>
                <%= parameterValuesHS[0] %>
              </td>  
            </tr>
          </table>
        </span>    
      </td>
	  <td class="td041CC001">
	    <acronym title="<%= thisFormHS.getHelpTextDef() %>">
          <b>
		    <%= thisFormHS.getFormNumber() %> - <%= thisFormHS.getFormTitle().trim() %> 
          </b>
        </acronym>
	  </td>
      <td style="width:2%">
      </td>	  
    </tr>
  </table> 
<% 
}  
   request.setAttribute("imageCount", new Integer(imageCountHS));
   request.setAttribute("sectionCount", new Integer(sectionCountHS));  
 
}
catch(Exception e)
{
	out.println("Problem found within listHeadingStandard.jsp: " +e);  
}
%>	  
  </body>
</html>