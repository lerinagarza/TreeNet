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
   String[] parameterValuesUS  = (String[]) request.getAttribute("parameterValues");

   java.util.Vector updateDefinition     = new java.util.Vector();
   int              updateDefinitionSize = 0;
   com.treetop.data.FormDefinition updateHeadingInfo = new com.treetop.data.FormDefinition();
try
{
	updateDefinition     = (java.util.Vector) request.getAttribute("updateDefinition");
	updateDefinitionSize = updateDefinition.size();
	updateHeadingInfo    = (com.treetop.data.FormDefinition) updateDefinition.elementAt(0);
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
   java.util.Vector thisTransaction = new java.util.Vector();
   int              transactionSize = 0; // Should be 1 if there is a transaction number
   com.treetop.data.FormData transHeadingInfo = new com.treetop.data.FormData();
try
{
	thisTransaction = (java.util.Vector) request.getAttribute("thisTransaction");
	transactionSize = thisTransaction.size();
	transHeadingInfo = (com.treetop.data.FormData) thisTransaction.elementAt(0);
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
   int    imageCountUS   = 0;
   int    sectionCountUS = 0;
try
{
   imageCountUS   = ((Integer) request.getAttribute("imageCount")).intValue();
   sectionCountUS = ((Integer) request.getAttribute("sectionCount")).intValue();
}
catch(Exception e)
{
}
	String requestTypeUS   = (String) request.getAttribute("requestType");
	String buildDateJavascript = "";
	
%>
<html>
  <head>
  </head>
  <body>
<%
try
{
	
if (updateDefinitionSize > 0)
{
	com.treetop.data.FormDefinition updateForm = (com.treetop.data.FormDefinition) updateDefinition.elementAt(0);
%>
  <table class="table01001" cellspacing="0">
    <tr class="tr02001">
      <td>
        <%= com.treetop.utilities.html.JavascriptInfo.getExpandingSection("O", "Update Report", 0, sectionCountUS, imageCountUS, 0, 0) %>
<%
   imageCountUS++;
   request.setAttribute("imageCount", new Integer(imageCountUS));
   sectionCountUS++;
   request.setAttribute("sectionCount", new Integer(sectionCountUS));  
%>        
        <table class="table00001" cellspacing=\"0\">
          <form name="<%= requestTypeUS %>" 
               onSubmit="return submitForm(this.submitbutton)"
               action="/web/CtlForms?requestType=finishUpdate<%= parameterValuesUS[1]%>" 
               method="post">
          <tr class="tr01001">
            <td class="td041CR001">
            </td>
            <td class="td041CL001" colspan="3">
              &nbsp;&nbsp;&nbsp;
              <%= updateForm.getReferenceGuideHdr() %>
            </td>
            <td></td>
            <td class="td041CR001">
            </td>
          </tr>
          <tr class="tr02001">
            <td class="td041CR002L" style="width:3%">
<%           
    //*********************************************************
    //   IF it is an update, it also has a transaction number
    //**** will have to add this in....
    //*********************************************************
%>    
            </td>
            <td class="td041CL001">
<%
      // If applicable will add the Transaction Number
       if (transactionSize > 0)
	   {
	      com.treetop.data.FormData fieldValues = (com.treetop.data.FormData) thisTransaction.elementAt(0);
		  out.println("<b>Line#: " + fieldValues.getTranNumber() + "</b>");
%>		   
           <input type="hidden" name="transactionNumber" value="<%= fieldValues.getTranNumber() %>">
<%      
	   }
%>	   
            </td>
            <td class="td041CL001">
              <b>Enter Data</b>
            </td>
            <td class="td041CL001">
              <b>Acceptable Ranges</b>
            </td>
            <td></td>
            <td class="td041CR002R" style="width:3%">
            </td>
          </tr>
<%     
   String transactionDateValue = "";
   int thisTrans = 0;
   for (int x = 0; x < updateDefinitionSize; x++)
   {
	          updateForm         = (com.treetop.data.FormDefinition) updateDefinition.elementAt(x);
	if (!updateForm.getListOrderEntry().toString().equals("0"))
	{
	if (updateForm.getFormula() != null &&
	    updateForm.getFormula().trim().length() <= 0)
	   {
          
	   String dateFieldValue       = "";
	   String timeFieldValue       = "";
	   String numberFieldValue     = "";
	   String textFieldValue       = "";
	   
	   if (transactionSize > 0)
	   {
	      com.treetop.data.FormData fieldValues = (com.treetop.data.FormData) thisTransaction.elementAt(thisTrans);
	      thisTrans++;

	      if (fieldValues.getDataDefaulted().trim().equals("N"))
	      {
		     dateFieldValue       = fieldValues.getDataDate() + "";
		     timeFieldValue       = fieldValues.getDataTime() + "";
		     numberFieldValue     = fieldValues.getDataNumeric() + "";
		     numberFieldValue     = com.treetop.servlets.CtlForms.buildStandardNumber(fieldValues.getDataNumeric(),
			                                         updateForm.getDecimalPositions().intValue(),
			                                         "",
			                                         new Integer(0));
	      }
		  if (fieldValues.getDataText() != null)
		     textFieldValue    = fieldValues.getDataText().trim();	 
		  if (transactionDateValue.equals(""))
		  {
			 String[] effectiveDate = com.treetop.GetDate.getDates(fieldValues.getTranEffDate());
		     transactionDateValue = effectiveDate[5];
		  }
		  if (!dateFieldValue.equals(""))
		  {
		     String[] transDateValue = com.treetop.GetDate.getDates(fieldValues.getDataDate());
                dateFieldValue = transDateValue[5];
		  }		  
	   }	
	   
	   // new row for each record
	   String helpTextUS = updateForm.getHelpTextDef().trim();
	   if (helpTextUS == null ||
		   helpTextUS.length() == 0)
	      helpTextUS = "There is not any help available this field.";
%>
          <tr>
	        <td class="td041CR002L">&nbsp;
            </td>
	        <td class="td041CL002">
              <acronym title="<%= helpTextUS %>">
	            <b><%= updateForm.getHeadingLong().trim() %></b>
	          </acronym>
            </td>
<%
	    String requiredEntry = "";
		String inputAreaUS   = "";
		String standardRange = "";

	//*** Is this field Required???
	    if (updateForm.getRequiredEntry().trim().equals("Y"))
		    requiredEntry = "<font color=\"#990000\"> *</font>";
		    
	   //*******************//
       // DATE 
       //*******************//
       if (updateForm.getDataCode().trim().equals("DT"))
	   {
	       String[] testDate = com.treetop.servlets.CtlForms.buildStandardDate(dateFieldValue,
	                                           updateForm,
	   							               "input",
	   							               requestTypeUS);
	   		inputAreaUS = testDate[0];
	   		buildDateJavascript = buildDateJavascript + testDate[1];

           standardRange = com.treetop.servlets.CtlForms.buildStandardDateRange(updateForm);
           
	    }
	   //*******************//
       // TIME 
       //*******************//
	   if (updateForm.getDataCode().trim().equals("TM"))
	   {
           inputAreaUS = com.treetop.servlets.CtlForms.buildStandardTime(timeFieldValue,
	                                     updateForm,
  						                 "input");
   
           standardRange = com.treetop.servlets.CtlForms.buildStandardTimeRange(updateForm);
           
	   } 
	   //*******************//
       // TEXT 
       //*******************//
	   if (updateForm.getDataCode().trim().equals("TX"))
	   {
		   // Add in a value field (parameter value)
           inputAreaUS  = com.treetop.servlets.CtlForms.buildStandardText(textFieldValue,
	                                            updateForm,
  							                    "input",
  							                    request,
  							                    response);
  	      if (updateForm.getInputMaxLength().intValue() <= 30 ||
		      updateForm.getInputSize().intValue() < 30)
             standardRange = com.treetop.servlets.CtlForms.buildStandardTextRange(updateForm);
             
	   } 
       //*******************//
       // NUMBER 
       //*******************//
	   if (updateForm.getDataCode().trim().equals("NU"))
	   {
		   // Add in a value field (parameter value)
           inputAreaUS  = com.treetop.servlets.CtlForms.buildStandardNumber(numberFieldValue,
	                                               updateForm,
	   							                   "input");
           
           standardRange = com.treetop.servlets.CtlForms.buildStandardNumberRange(updateForm);
	   } 
	   if (updateForm.getDataCode().trim().equals("TX") &&
	      (updateForm.getInputMaxLength().intValue() > 30 ||
		   updateForm.getInputSize().intValue() >= 30))
  	   {
%>
             <td class="td041CL002" colspan="2">
	           <%= inputAreaUS %><%= requiredEntry %>
	           &nbsp;
	         </td>
<%	         
  	      }
  	      else
  	      { 
%>         
            <td class="td041CL002">
	          <%= inputAreaUS %><%= requiredEntry %>
	        </td>
            <td class="td041CL002">
	          <%= standardRange %>
	          &nbsp;
	        </td>
<%	        
  	      }	   
%>	
	       <td class="td041CL002">
	         <%= updateForm.getHeadingShort().trim() %>
	         &nbsp;
	       </td>
	       <td class="td041CR002R">&nbsp;
           </td>
         </tr>	  
<%       
	   }
	   }
   }// end of the for statement
   
 //*******************************************************************************
 //  Data to search on which is unique to each transaction
 //     1) Transaction Number
 //     2) Transaction Date (Effective)
 //     3) Transaction User
 //     4) Last Update Date
 //     5) Last Update User
 //
 try
 {
   String[] testField = updateHeadingInfo.getShowOnUpdate();
   
   if ((testField[0] != null &&
       !testField[0].toLowerCase().equals("n")) &&
        transactionSize > 0)
   {    
     //----------------------------------------------------------------------
     //  Transaction Number
%>     
          <tr>
	       <td class="td041CR002L">&nbsp;
           </td>
	       <td class="td041CL002">
	         <b>Transaction Number:</b>
	       </td>   
 	       <td class="td041CL002">
 	         <%= transHeadingInfo.getTranNumber() %>
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>	     
	       <td class="td041CR002R">&nbsp;
           </td>
	     </tr>  
<%
   }
   if (testField[1] != null &&
       !testField[1].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction (Effective) Date
   
   String[] dataDate   = com.treetop.servlets.CtlForms.buildStandardDate(transactionDateValue,
	   									updateForm,
		                               "inputEffectiveDate",
		                               requestTypeUS);
		buildDateJavascript = buildDateJavascript + dataDate[1];     
%>      
          <tr>
	       <td class="td041CR002L">&nbsp;
           </td>
	       <td class="td041CL002">
	         <b>Effective Date:</b>
	       </td>   
 	       <td class="td041CL002">
 	         <%= dataDate[0] %>
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>	     
	       <td class="td041CR002R">&nbsp;
           </td>
	     </tr>  

<%
   }   
   if ((testField[2] != null &&
       !testField[2].toLowerCase().equals("n")) &&
       transactionSize > 0)
   {    
     //----------------------------------------------------------------------
     //  Transaction User
%>  
          <tr>
	       <td class="td041CR002L">&nbsp;
           </td>
	       <td class="td041CL002">
	         <b>Transaction User:</b>
	       </td>   
 	       <td class="td041CL002">
			  <%= transHeadingInfo.getUpdateUser() %>
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>	     
	       <td class="td041CR002R">&nbsp;
           </td>
	     </tr>  
 
<%
   }   
   if ((testField[3] != null &&
       !testField[3].toLowerCase().equals("n")) &&
       transactionSize > 0)
   {    
     //----------------------------------------------------------------------
     //  Last Update Date
%>      
          <tr>
	       <td class="td041CR002L">&nbsp;
           </td>
	       <td class="td041CL002">
	         <b>Last Update Date:</b>
	       </td>   
 	       <td class="td041CL002">
		     <%= com.treetop.servlets.CtlForms.buildStandardDate(transHeadingInfo.getUpdateDate(),
		     													 transHeadingInfo.getDataType()) %>
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>	     
	       <td class="td041CR002R">&nbsp;
           </td>
	     </tr>  
<%
   }   
   if ((testField[4] != null &&
       !testField[4].toLowerCase().equals("n")) &&
       transactionSize > 0)
   {    
     //----------------------------------------------------------------------
     //  Last Update Time
%>    
          <tr>
	       <td class="td041CR002L">&nbsp;
           </td>
	       <td class="td041CL002">
	         <b>Last Update Time:</b>
	       </td>   
 	       <td class="td041CL002">
		     <%= transHeadingInfo.getUpdateTime() %>
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>
	       <td class="td041CL002">
	         &nbsp;
	       </td>	     
	       <td class="td041CR002R">&nbsp;
           </td>
	     </tr>  

<%
   }
  }
  catch(Exception e)
  {
  } 
  
   //*** Add the submit Button on.
%>
	     <tr>
         <td class="td041CR002"
             style="border-left:1px solid #CCCC99">&nbsp;
         </td>
	     <td class="td041CC002" colspan="4">
	       <input type="submit" name="submitbutton" value="Go">
	     </td>
         <td class="td041CR002"
             style="border-right:1px solid #CCCC99">&nbsp;
         </td>
	   </tr>
	   </form>
     </table>
   </td>
   <td class="td041CR001" style="width:5%">
     &nbsp;
   </td>
  </tr>
 </table>
 <%= buildDateJavascript %>       
<%  
   } // No Records 
}
catch(Exception e)
{
	out.println("Problem found within updateStandard.jsp: " +e);  
}
%>	  
  </body>
</html>