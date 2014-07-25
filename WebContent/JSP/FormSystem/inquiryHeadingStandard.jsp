<%
//----------------------inquiryHeadingStandard.jsp--------------------------//
//   THIS Jsp to be included in the forms.jsp.
//    This will display the drop down list of forms/reports.
//
//    Author :  Teri Walton      Date: 7/28/2004
//    CHANGES:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//-------------------------------------------------------------------//

//******************************************************************************
//   Receiving in Request Session Variables
//******************************************************************************
// 
   java.util.Vector inquiryDefinitionHeading = null;
   int              definitionCountHeading   = 0;
try
{
	inquiryDefinitionHeading = (java.util.Vector) request.getAttribute("inquiryDefinition");
	definitionCountHeading   = inquiryDefinitionHeading.size();
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
   int[]  formInfoIHS       = null;
   java.util.Vector definitionInfoIHS = null;
try
{
	formInfoIHS       = (int[])  request.getAttribute("formInfo");
	definitionInfoIHS = (java.util.Vector) request.getAttribute("definitionInfo");
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}

%>
<html>
  <head>
  </head>
  <body>
<%
try
{
	String[] userGroups  = com.treetop.SessionVariables.getSessionttiUserGroups(request,response);
	if (userGroups != null && 
		userGroups.length == 1 &&
		userGroups[0].trim().equals(""))
	   userGroups = null;
 
	com.treetop.data.FormDefinition thisForm = new com.treetop.data.FormDefinition();
 	if (definitionCountHeading > 0)
 	{
       thisForm = (com.treetop.data.FormDefinition) inquiryDefinitionHeading.elementAt(0);
 	}
%>
  <table class="table01001" cellspacing="0">
    <form action="/web/CtlForms"
                 onSubmit="return submitForm(this.submitbutton)"
                 method="post">
      <input type="hidden" name="requestType" value="inq">
      <tr class="tr01001">
        <td style="width:10%" rowspan="4">
          &nbsp;
        </td>
        <td class="td041CL002" colspan="4">
	      <font color="#003366">
	        <b>
<%
   if (definitionCountHeading == 0)
      out.println("Select a Report:");
   else
      out.println("Select Another Report:");
%>	        
	        </b>
	      </font>
	    </td>
        <td style="width:10%" rowspan="4">
          &nbsp;
        </td>
      </tr>
      <tr class="tr00001">
      <td class="td041CR002L" style="width:3%">
        &nbsp;
      </td>
      <td class="td044CL002" style="width:20%">
        <b>Report Name:</b>
      </td>
      <td class="td044CL002">
<%      
       Integer sendIn = new Integer(0);
       if (definitionCountHeading > 0)
       {
	        sendIn = thisForm.getFormNumber();
       } 
       String dropDown = com.treetop.data.FormHeader.
                         buildDropDownTitleForNumber(sendIn,
	                                               "formNumber",
	                                               "Report",
													com.treetop.SessionVariables.getSessionttiUserRoles(request,response),
													userGroups,
													com.treetop.SessionVariables.getSessionttiProfile(request,response));
									
%>	    
        <%= dropDown %>                                           
      </td>
      <td class="td041CR002R" style="width:3%">
        &nbsp;
      </td>
    </tr>
    <tr class="tr00001">
      <td class="td041CR002"
          style="border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CL002">
        &nbsp;
      </td>
      <td class="td041CL002">
<%      
   // Decide what the submit button should say
   String submitValue = "Select Another Report";
   if (definitionCountHeading == 0)
      submitValue = "Select A Report";
%>
        <input type="Submit" name="submitbutton" value="<%= submitValue %>">
      </td>
      <td class="td041CR002"
          style="border-right:1px solid #CCCC99">
             &nbsp;
       </td>  
     </tr>
   </form>
 </table>   
<%   
}
catch(Exception e)
{
	out.println("Problem found within inquiryHeadingStandard.jsp: " +e);  
}
%>	  
  </body>
</html>