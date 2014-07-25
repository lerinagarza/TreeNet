<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------updateStandard.jsp--------------------------//
//   THIS Jsp to be included in the forms.jsp.
//    This will display the standard Update Screen.
//
//    Author :  Teri Walton      Date: 7/30/2004
//    CHANGES:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//-------------------------------------------------------------------//

//******************************************************************************
//   Receiving in Request Session Variables
//******************************************************************************
// 
   java.util.Vector detailDefinition = null;
try
{
	detailDefinition = (java.util.Vector) request.getAttribute("definitionInfo");
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
	java.util.Vector thisTransaction = new java.util.Vector();
try
{
   thisTransaction = (java.util.Vector) request.getAttribute("thisTransaction");
}
catch(Exception e)
{
}	
   int    imageCountDS   = 0;
   int    sectionCountDS = 0;
try
{
   imageCountDS   = ((Integer) request.getAttribute("imageCount")).intValue();
   sectionCountDS = ((Integer) request.getAttribute("sectionCount")).intValue();
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
if (thisTransaction.size() > 0)
{

	com.treetop.data.FormData thisCell      = new com.treetop.data.FormData();
	com.treetop.data.FormData thisHeadingDS = (com.treetop.data.FormData) thisTransaction.elementAt(0);
   String sectionNameDS = "Transaction Selected:&nbsp;&nbsp;" + thisHeadingDS.getTranNumber();	
%>
  <table class="table01001" cellspacing="0">
	<tr class="tr02001">
      <td>
        <%= JavascriptInfo.getExpandingSection("O", sectionNameDS, 0, sectionCountDS, imageCountDS, 0, 0) %>
<% 
   imageCountDS++;
   request.setAttribute("imageCount", new Integer(imageCountDS));
   sectionCountDS++;
   request.setAttribute("sectionCount", new Integer(sectionCountDS));  
%>           
        <table class="table00001" cellspacing="0">
   	      <tr class="tr04001">
	        <td>
            </td>
            <td class="td011CL001">
              <b>Name</b>
            </td>
            <td class="td011CL001">
              <b>Value</b>
            </td>
            <td class="td011CL001">
              <b>Acceptable Ranges</b>
            </td> 
 	        <td>
            </td>
	        <td>
            </td>
          </tr>
<%
   for (int x = 0; x < thisTransaction.size(); x++)
   {
	  thisCell = (com.treetop.data.FormData) thisTransaction.elementAt(x);
	  
	if (thisCell.getFormula() != null &&
	    thisCell.getFormula().trim().length() <= 0)
	{
          
	   String dateFieldValue       = "";
	   String timeFieldValue       = "";
	   String numberFieldValue     = "";
	   String textFieldValue       = "";
	   
	   // new row for each record
	   String helpTextDS = thisCell.getHelpTextDef().trim();
	   if (helpTextDS == null ||
		   helpTextDS.length() == 0)
	      helpTextDS = "There is not any help available this field.";
%>   
          <tr>
	        <td class="td041CR002L">&nbsp;
            </td>
	        <td class="td041CL002">
              <acronym title="<%= helpTextDS %>">
	            <b><%= thisCell.getHeadingLong().trim() %></b>
	          </acronym>
            </td>
<%
	   String cellValue  = "&nbsp;";
	   String rangeValue = "&nbsp;";
	   //*******************//
       // DATE 
       //*******************//
       if (thisCell.getDataCode().trim().equals("DT"))
	   {
           if (thisCell.getDataDefaulted().trim().equals("N"))
              cellValue = com.treetop.servlets.CtlForms.buildStandardDate(thisCell.getDataDate(),
              															  thisCell.getDataType());

            rangeValue = com.treetop.servlets.CtlForms.buildStandardDateRange(thisCell);
	    }
	   //*******************//
       // TIME 
       //*******************//
	   if (thisCell.getDataCode().trim().equals("TM"))
	   {
           if (thisCell.getDataDefaulted().trim().equals("N"))
              cellValue = com.treetop.servlets.CtlForms.buildStandardTime(thisCell.getDataTime(),
              															  thisCell.getDataType());
   
           rangeValue = com.treetop.servlets.CtlForms.buildStandardTimeRange(thisCell);
	   } 
	   //*******************//
       // TEXT 
       //*******************//
	   if (thisCell.getDataCode().trim().equals("TX"))
	   {
		  if (thisCell.getDataDefaulted().trim().equals("N"))
		  {
		     String dataText     = thisCell.getDataText();
		     if (dataText == null)
		        dataText = "";
		     cellValue  = com.treetop.servlets.CtlForms.buildStandardText(thisCell);
		  }
		  rangeValue   = com.treetop.servlets.CtlForms.buildStandardTextRange(thisCell);
	   } 
       //*******************//
       // NUMBER 
       //*******************//
	   if (thisCell.getDataCode().trim().equals("NU"))
	   {
		   if (thisCell.getDataDefaulted().trim().equals("N"))
		      cellValue = com.treetop.servlets.CtlForms.buildStandardNumber(thisCell); 
           
           rangeValue = com.treetop.servlets.CtlForms.buildStandardNumberRange(thisCell);
	   } 
%>
            <td class="td041CL002">
	          <%= cellValue %>
	        </td>
	        <td class="td041CL002">
	          <%= rangeValue %>
	          &nbsp;
	        </td>
            <td class="td041CL002">
	          <%= thisCell.getHeadingShort().trim() %>
	          &nbsp;
	        </td>
	        <td class="td041CR002R">
	          &nbsp;
            </td>
	      </tr>	  
<%	      
	   }
   }// end of the for statement

%>  
          <tr>
	        <td class="td041CR002L">
	          &nbsp;
            </td>
	        <td class="td041CL002" style="border-bottom:1px solid #006400">
	          <b>Effective Date:</b>
	        </td>  
 	        <td class="td041CL002" style="border-bottom:1px solid #006400">
	          <%= com.treetop.servlets.CtlForms.buildStandardDate(thisCell.getTranEffDate(),
		     													  "") %>
	        </td>
	        <td class="td041CL002" style="border-bottom:1px solid #006400">
	          &nbsp;
	        </td>
	        <td class="td041CL002" style="border-bottom:1px solid #006400">
	          &nbsp;
	        </td>
	        <td class="td041CR002R">
	          &nbsp;
            </td>
	      </tr>
          <tr>
	        <td class="td041CR002L">
	          &nbsp;
            </td>
	        <td class="td041CL002">
	          <b>Date Last Updated:</b>
	        </td>
 	        <td class="td041CL002">
	          <%= com.treetop.servlets.CtlForms.buildStandardDate(thisCell.getUpdateDate(),
		     													  "") %>
	        </td>
	        <td class="td041CL002">
	          &nbsp;
	        </td>
	        <td class="td041CL002">&nbsp;
	        </td>
	        <td class="td041CR002R">&nbsp;
            </td>
	      </tr>
	      <tr>
	        <td class="td041CR002L">
	          &nbsp;
            </td>
	        <td class="td041CL002">
	          <b>Time Last Updated:</b>
	        </td>
 	        <td class="td041CL002">
 	          <%= com.treetop.servlets.CtlForms.buildStandardTime(thisCell.getUpdateTime(),
		     													  "") %>
	        </td>
	        <td class="td041CL002">
	          &nbsp;
	        </td>
	        <td class="td041CL002">
	          &nbsp;
	        </td>	     
	        <td class="td041CR002R">
	          &nbsp;
            </td>
	      </tr>
<%	   
  // User Last Updated
    String updateUser   = com.treetop.servlets.CtlForms.buildStandardText(thisCell.getUpdateUser(),
		     															  "");
    try
    {
	   com.treetop.data.UserFile thisUser = new com.treetop.data.UserFile(thisCell.getUpdateUser());
	   thisUser = new com.treetop.data.UserFile(new Integer(thisUser.getUserNumber()));
	   updateUser        = com.treetop.servlets.CtlForms.buildStandardText(thisUser.getUserNameLong(),
		     															   "");
    }
    catch(Exception e)
    {
    }
%>       
          <tr>
	        <td class="td041CR002L">
	          &nbsp;
            </td>
	        <td class="td041CL002">
	          <b>Last Updated By:</b>
	        </td>
 	        <td class="td041CL002">
	          <%= updateUser %>
	        </td>
	        <td class="td041CL002">
	          &nbsp;
	        </td>
	        <td class="td041CL002">
	          &nbsp;
	        </td>     
	        <td class="td041CR002R">
	          &nbsp;
            </td>
	      </tr>
	    </table> 
	    </span>
	  </td>
	</tr>
  </table>	           
<%   
}
}
catch(Exception e)
{
	out.println("Problem found within updateStandard.jsp: " +e);  
}
%>	  
  </body>
</html>