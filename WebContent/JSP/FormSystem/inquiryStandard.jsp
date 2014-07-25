<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------inquiryStandard.jsp--------------------------//
//   THIS Jsp to be included in the forms.jsp.
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
   String           requestTypeIS     = null;
   java.util.Vector inquiryDefinition = null;
   int              definitionCount   = 0;
try
{
	requestTypeIS     = (String) request.getAttribute("requestType");
	inquiryDefinition = (java.util.Vector) request.getAttribute("inquiryDefinition");
	definitionCount   = inquiryDefinition.size();
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
   int imageCountIS   = 0;
   int sectionCountIS = 0;
try
{   
   imageCountIS   = ((Integer) request.getAttribute("imageCount")).intValue();
   sectionCountIS = ((Integer) request.getAttribute("sectionCount")).intValue();
}
catch(Exception e)
{
}
   
  //Build In the Footer Calendar Information (IF Needed)
  String calendarFooter = "";
%>
<html>
  <head>
  </head>
  <body>
<%
try
{

  com.treetop.data.FormDefinition thisForm = new com.treetop.data.FormDefinition();
  if (definitionCount > 0)
  {
    thisForm = (com.treetop.data.FormDefinition) inquiryDefinition.elementAt(0);
    String sectionName = "Report Selected:&nbsp;&nbsp;" + thisForm.getFormTitle();
%>		
    <table class="table01001" cellspacing="0">
      <tr class="tr02001">
        <td>
            <%= JavascriptInfo.getExpandingSection("O", sectionName, 0, sectionCountIS, imageCountIS, 0, 0) %>
<%
   imageCountIS++;
   request.setAttribute("imageCount", new Integer(imageCountIS));
   sectionCountIS++;
   request.setAttribute("sectionCount", new Integer(sectionCountIS));   
%>            
          <table class="table00001" cellspacing="0">
            <form name="<%= requestTypeIS %>" action="/web/CtlForms" method="post" target="_blank">
<%                  
    //* Always send the request type
    //  && a Form Number
%>    
            <input type="hidden" name="requestType" value="list">   
            <input type="hidden" name="formNumber" value="<%= thisForm.getFormNumber() %>">   
            <tr>	
              <td class="td041CR002L" style="width:3%">
                &nbsp;
              </td>
              <td class="td054CL001">* required fields</td>
              <td class="td041CL001">
                <b>Enter a Starting Value</b>
              </td>
              <td></td>
              <td class="td041CL001">
                <b>Enter an Ending Value</b>
              </td>
              <td class="td041CL001">
                <b>Acceptable Ranges</b>
              </td>
              <td class="td041CR002R" style="width:3%">
                &nbsp;
              </td>
            </tr>
<%
   // Run through the definition.   
    for (int x = 0; x < definitionCount; x++)
    {
	  thisForm = (com.treetop.data.FormDefinition) inquiryDefinition.elementAt(x);
	  if (thisForm.getFormula() != null &&
	      thisForm.getFormula().trim().length() <= 0)
	      // IF a form has a formula, that does not display on the Inquiry page.
	  {
        String helpText = thisForm.getHelpTextDef().trim();
	  	if (helpText == null ||
		    helpText.length() == 0)
	       helpText = "There is not any help available this field.";
	      
%>
   	        <tr>
   	          <td class="td041CR002L">
                &nbsp;
              </td>
  	          <td class="td041CL002">
<%  	          
	       //** title of what was to be the heading **//
%>	           
				<acronym title="<%= helpText %>">
	              <b><%= thisForm.getHeadingLong().trim() %>:</b>
                </acronym>
	          </td>
<%	 
        if (thisForm.getDataCode().trim().equals("DT"))
	    {
        //***************************************//
        // DATE 
        //***************************************//
		   // Add in a value field (parameter value)        
           String[] fromDate = com.treetop.servlets.CtlForms.buildStandardDate("",
	       	                                         thisForm,
	   			      		                         "from",
	   							  		             requestTypeIS);
   	    
	       if (!thisForm.getSearchType().trim().toLowerCase().equals("single") &&
	           !thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {
	           String[] toDate   = com.treetop.servlets.CtlForms.buildStandardDate("",
		                                                  thisForm,
	    	                                              "to",
	        	                                          requestTypeIS);
           
	           calendarFooter = calendarFooter + " " +
    	                        fromDate[1] + " " +
        	                    toDate[1] + " ";
                          
              String rangeDate = com.treetop.servlets.CtlForms.buildStandardDateRange(thisForm);
%>       
              <td class="td041CL002">
                <%= fromDate[0] %>
	          </td>
              <td class="td041CL002">
	            <b>TO</b>
  	          </td>   
 	          <td class="td041CL002">
 	            <%= toDate[0] %>
	          </td>
	          <td class="td041CL002">
	            <%= rangeDate %>
  	            &nbsp;
  	          </td>	  
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("single"))
	       {              
%>  
              <td class="td041CL002" colspan="2">
                <%= fromDate[0] %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will only look for exact matches, unless blank.
  	          </td>	  
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {          
%>
              <td class="td041CL002" colspan="2">
                <%= fromDate[0] %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will look for match anywhere in the field.
  	          </td>	  <%  
           }	          
        } 
	    if (thisForm.getDataCode().trim().equals("TM"))
	    {
	   //**************************************************//
       // TIME 
       //**************************************************//
		   // Add in a value field (parameter value)
             String fromTime  = com.treetop.servlets.CtlForms.buildStandardTime("",
	                                            thisForm,
  							                    "from");
	       if (!thisForm.getSearchType().trim().toLowerCase().equals("single") &&
	           !thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {
	             String toTime    = com.treetop.servlets.CtlForms.buildStandardTime("",
		                                            thisForm,
	    	                                        "to");
           
            	 String rangeTime = com.treetop.servlets.CtlForms.buildStandardTimeRange(thisForm);
%>           
              <td class="td041CL002">
	            <%= fromTime %>
  	          </td>
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
	            <%= toTime %>
	          </td>
	          <td class="td041CL002">
	            <%= rangeTime %>
	            &nbsp;
	          </td>
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("single"))
	       {              
%>  
              <td class="td041CL002" colspan="2">
                <%= fromTime %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will only look for exact matches, unless blank.
  	          </td>	  
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {          
%>
              <td class="td041CL002" colspan="2">
                <%= fromTime %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will look for match anywhere in the field.
  	          </td>	  
<%  
           }	          
          
        } 
	   if (thisForm.getDataCode().trim().equals("TX"))
	   {
	   //*******************//
       // TEXT 
       //*******************//
		   // Add in a value field (parameter value)
           String fromText  = com.treetop.servlets.CtlForms.buildStandardText("",
	                                            thisForm,
  							                    "from",
  							                    request,
  							                    response);
  							                    
	       if (!thisForm.getSearchType().trim().toLowerCase().equals("single") &&
	           !thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {
	           String toText    = com.treetop.servlets.CtlForms.buildStandardText("",
		                                            thisForm,
	    	                                        "to",
	        	                                    request,
	            	                                response);
           
	           String rangeText = com.treetop.servlets.CtlForms.buildStandardTextRange(thisForm);
%>           
              <td class="td041CL002">
	            <%= fromText %>
	          </td>
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
	            <%= toText %>
	          </td>
	          <td class="td041CL002">
	            <%= rangeText %>
	            &nbsp;
	            </td>
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("single"))
	       {              
%>  
              <td class="td041CL002" colspan="2">
                <%= fromText %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will only look for exact matches, unless blank.
  	          </td>	  
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {          
%>
              <td class="td041CL002" colspan="2">
                <%= fromText %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will look for match anywhere in the field.
  	          </td>	  
<%  
           }	          
        } 
	   if (thisForm.getDataCode().trim().equals("NU"))
	   {
       //*******************//
       // NUMBER 
       //*******************//
		   // Add in a value field (parameter value)
           String fromNumber  = com.treetop.servlets.CtlForms.buildStandardNumber("",
	                                               thisForm,
	   							                   "from");
	   							                   
	       if (!thisForm.getSearchType().trim().toLowerCase().equals("single") &&
	           !thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {
	           String toNumber    = com.treetop.servlets.CtlForms.buildStandardNumber("",
		                                               thisForm,
	    	                                           "to");
           
           	String rangeNumber = com.treetop.servlets.CtlForms.buildStandardNumberRange(thisForm);
           	
%>           
              <td class="td041CL002">
	            <%= fromNumber %>
	          </td>
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
	            <%= toNumber %>
	          </td>
	          <td class="td041CL002">
	            <%= rangeNumber %>
	            &nbsp;
	          </td>
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("single"))
	       {              
%>  
              <td class="td041CL002" colspan="2">
                <%= fromNumber %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will only look for exact matches, unless blank.
  	          </td>	  
<%
           }
           if (thisForm.getSearchType().trim().toLowerCase().equals("contains"))
	       {          
%>
              <td class="td041CL002" colspan="2">
                <%= fromNumber %>
	          </td>
	          <td class="td041CL002" colspan="2">
  	            &nbsp;&nbsp;Search will look for match anywhere in the field.
  	          </td>	  
<%  
           }	          
         
	    } 
	   //*******************//
	   // Blank Cell
%>	   
              <td class="td041CR002R">
               &nbsp;
              </td>
           </tr>
<%           
      } // End of the is there a formula. (Formula's do not display) 
	    
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
   String[] testField = thisForm.getShowOnInquiry();
   
   if (testField[0] != null &&
       !testField[0].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction Number
%>      
            <tr>
	          <td class="td041CR002L">
                &nbsp;
              </td>
        	  <td class="td041CL002">
	            <b>Transaction Number:</b>
	          </td>   
	          <td class="td041CL002">
                <%= HTMLHelpersInput.inputBoxNumber("fromTransactionNumber",
	                                        "",
	                                        " From Transaction Number ",
	                                        6, 
	                                        6,
	                                        "N", "N") %>
  	          </td>
<% 
     if (testField[0].toLowerCase().equals("r"))
     {
%>	                                   
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
                <%= HTMLHelpersInput.inputBoxNumber("toTransactionNumber",
	                                        "",
	                                        " To Transaction Number ",
	                                        6, 
	                                        6,
	                                        "N", "N") %> 	          
	          </td>
<%
     }
     else
     {  
%>	 
              <td class="td041CL002">
	            <b>&nbsp;</b>
	          </td>
              <td class="td041CL002">
<%
        if (testField[0].toLowerCase().equals("s"))
           out.println("&nbsp;&nbsp;Search will only look for exact matches, unless blank.");
        else
           out.println("&nbsp;&nbsp;Search will look for match anywhere in the field.");        
%>	            
	          </td>
<%
     }
%>         
	          <td class="td041CL002">
	            &nbsp;
	          </td>
	          <td class="td041CR002R">
                &nbsp;
              </td>
	        </tr>  
<%
   } // end of Transaction Number  
   
   if (testField[1] != null &&
       !testField[1].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction Date (Effective Date)
             
    	String dateValue   = "";
    	String requireDate = "";
    	if (thisForm.getRequireDateRange() != null &&
	    	thisForm.getRequireDateRange().equals("Y"))
	    {
		    String dateArray[]        = com.treetop.SystemDate.getSystemDate();
 	    	java.sql.Date currentDate = java.sql.Date.valueOf(dateArray[7]);
	  	    String[] newDateArray     = com.treetop.GetDate.getDates(currentDate);
   			dateValue                 = newDateArray[5];
   			requireDate = "<font color=\"#990000\"> *</font>";
	    }
    
   //** From and To Effective Dates (On each page) **//
        String[] fromDate = com.treetop.servlets.CtlForms.buildStandardDate(dateValue,
	                                   thisForm,
	   								   "fromEffectiveDate",
	   								   requestTypeIS);
        calendarFooter = calendarFooter + " " +
                    fromDate[1] + " "; 
%>
            <tr>
	          <td class="td041CR002L">
                &nbsp;
              </td>
        	  <td class="td041CL002">
	            <b>Effective Date:</b>
	          </td>
	          <td class="td041CL002">
	            <%= fromDate[0] %><%= requireDate %>
  	          </td>
<% 
     if (testField[1].toLowerCase().equals("r"))
     {
        String[] toDate   = com.treetop.servlets.CtlForms.buildStandardDate(dateValue,
	                                   thisForm,
	                                   "toEffectiveDate",
	                                   requestTypeIS);  
        calendarFooter = calendarFooter + " " +
                    toDate[1] + " "; 
%>	                                   
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
                <%= toDate[0] %><%= requireDate %>          
	          </td>
<%
     }
     else
     {  
%>	 
              <td class="td041CL002">
	            <b>&nbsp;</b>
	          </td>
 
              <td class="td041CL002">
<%
        if (testField[1].toLowerCase().equals("s"))
           out.println("&nbsp;&nbsp;Search will only look for exact matches, unless blank.");
        else
           out.println("&nbsp;&nbsp;Search will look for match anywhere in the field.");        
%>	            
	          </td>
<%
     }
%>           	          
	          <td class="td041CL002">
	            &nbsp;
	          </td>
	          <td class="td041CR002R">
                &nbsp;
              </td>
	        </tr>   
<% 
   } // end of Transaction (Effective) Date
   
   if (testField[2] != null &&
       !testField[2].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  User
%>      
            <tr>
	          <td class="td041CR002L">
                &nbsp;
              </td>
        	  <td class="td041CL002">
	            <b>Transaction User:</b>
	          </td>   
	          <td class="td041CL002">
                <%= HTMLHelpersInput.inputBoxText("fromTransactionUser",
	                                        "",
	                                        " Transaction User ",
	                                        6, 
	                                        6,
	                                        "N",
	                                        "N") %>
  	          </td>
<% 
     if (testField[2].toLowerCase().equals("r"))
     {
%>	                                   
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
                <%= HTMLHelpersInput.inputBoxText("toTransactionUser",
	                                        "",
	                                        " To Transaction User ",
	                                        6, 
	                                        6,
	                                        "N", "N") %> 	          
	          </td>
<%
     }
     else
     {  
%>	 
              <td class="td041CL002">
	            <b>&nbsp;</b>
	          </td>
              <td class="td041CL002">
<%
        if (testField[2].toLowerCase().equals("s"))
           out.println("&nbsp;&nbsp;Search will only look for exact matches, unless blank.");
        else
           out.println("&nbsp;&nbsp;Search will look for match anywhere in the field.");        
%>	            
	          </td>
<%
     }
%>         
	          <td class="td041CL002">
	            &nbsp;
	          </td>
	          <td class="td041CR002R">
                &nbsp;
              </td>
	        </tr>  
<%
   } // end of Transaction User
   
   if (testField[3] != null &&
       !testField[3].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Last Updated Date
             
    	String dateValue   = "";
    
   //** From and To Update Dates (On each page) **//
        String[] fromDate = com.treetop.servlets.CtlForms.buildStandardDate(dateValue,
	                                   thisForm,
	   								   "fromUpdateDate",
	   								   requestTypeIS);
        calendarFooter = calendarFooter + " " +
                    fromDate[1] + " "; 
%>
            <tr>
	          <td class="td041CR002L">
                &nbsp;
              </td>
        	  <td class="td041CL002">
	            <b>Last Update Date:</b>
	          </td>
	          <td class="td041CL002">
	            <%= fromDate[0] %>
  	          </td>
<% 
     if (testField[3].toLowerCase().equals("r"))
     {
        String[] toDate   = com.treetop.servlets.CtlForms.buildStandardDate(dateValue,
	                                   thisForm,
	                                   "toUpdateDate",
	                                   requestTypeIS);  
        calendarFooter = calendarFooter + " " +
                    toDate[1] + " "; 
%>	                                   
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
                <%= toDate[0] %>
	          </td>
<%
     }
     else
     {  
%>	 
              <td class="td041CL002">
	            <b>&nbsp;</b>
	          </td>
              <td class="td041CL002">
<%
        if (testField[3].toLowerCase().equals("s"))
           out.println("&nbsp;&nbsp;Search will only look for exact matches, unless blank.");
        else
           out.println("&nbsp;&nbsp;Search will look for match anywhere in the field.");        
%>	            
	          </td>
<%
     }
%>           	          
	          <td class="td041CL002">
	            &nbsp;
	          </td>
	          <td class="td041CR002R">
                &nbsp;
              </td>
	        </tr>   
<% 
   } // end of Last Update Date   
   
   if (testField[4] != null &&
       !testField[4].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Last Update Time

%>      
            <tr>
	          <td class="td041CR002L">
                &nbsp;
              </td>
        	  <td class="td041CL002">
	            <b>Last Update Time:</b>
	          </td>   
	          <td class="td041CL002">
                <%= HTMLHelpersInput.inputBoxTime3Sections("fromUpdateTime",
	                                                " Last Update Time ",
	                                                "00:00:00") %>
  	          </td>
<% 
     if (testField[4].toLowerCase().equals("r"))
     {
%>	                                   
              <td class="td041CL002">
	            <b>TO</b>
	          </td>
 	          <td class="td041CL002">
                <%= HTMLHelpersInput.inputBoxTime3Sections("toUpdateTime",
	                                                " To Last Update Time ",
	                                                "00:00:00") %>
	          </td>
<%
     }
     else
     {  
%>
              <td class="td041CL002">
	            <b>&nbsp;</b>
	          </td>
              <td class="td041CL002">
<%
        if (testField[4].toLowerCase().equals("s"))
           out.println("&nbsp;&nbsp;Search will only look for exact matches, unless blank.");
        else
           out.println("&nbsp;&nbsp;Search will look for match anywhere in the field.");        
%>	            
	          </td>
<%
     }
%>         
	          <td class="td041CL002">
	            &nbsp;
	          </td>
	          <td class="td041CR002R">
                &nbsp;
              </td>
	        </tr>  
<%
   } // end of Transaction User
   
   
  }
  catch(Exception e)
  {
  }   
   //*** Add the submit Button on.
%>
	        <tr>
              <td class="td041CR002" 
                   style="border-left:1px solid #CCCC99">
                &nbsp;
              </td>
	          <td class="td041CC002" colspan="5">
	            <input type="submit" value="Go">
	          </td>
              <td class="td041CR002"
                   style="border-right:1px solid #CCCC99">
                &nbsp;
              </td>
	        </tr>
          </form>
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
	out.println("Problem found within inquiryStandard.jsp: " +e);  
}
%>	  
<%= calendarFooter %>  
  </body>
</html>