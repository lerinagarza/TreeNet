<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.app.function.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%
   // Use this JSP in conjunction with ANY update Page
   //  REMEMBER to call the CtlResponsibility Servlet to update and get detail lists
   //
//-------------------------- updItemFunctionDetail.jsp---------------------//
//  Author :  Teri Walton  11/30/05   // Separate Fields to be used with ANY Update Page
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
// 3/21/08    TWalton      Change to the New Look - Same Functionality
// 6/18/12    TWalton      New Functionality JS and also new look
//							Removed GetDate and SystemDate - replaced with UtilityDateTime	
//							Changed to the "datePicker" for the dates		 
//------------------------------------------------------------//
 int  countR   = 0;
 // IF at some point it needs to look different
 String screenType = (String) request.getAttribute("screenType");
 if (screenType == null ||
     screenType.equals(""))
   screenType = "";
 
 Vector responsibilities = new Vector();
 try
 {
   	responsibilities = (Vector) request.getAttribute("listResponsibilities");
   	countR   = responsibilities.size();
 }
 catch(Exception e)
 {
 } 
 String formNameR = (String) request.getAttribute("formName");
 if (formNameR == null)
    formNameR = "update";
 String userR = SessionVariables.getSessionttiProfile(request, response);
 String ownerR = (String) request.getAttribute("projectOwner");
 if (ownerR == null)
   ownerR = "";
 String[] authorityR = UpdFunctionDetail.getSecurity(formNameR, ownerR, request, response);
 
// authorityR[0] = "N";
// authorityR[1] = "N";
// authorityR[2] = "BGODFR";
 String mainReadOnlyR = "Y";
 if (authorityR[0].equals("Y") ||
     authorityR[1].equals("Y"))
     mainReadOnlyR = "N";
%>
  <table class="styled full-width">
<%
   if (countR == 0)
   {
%>   
   <tr>
    <td class="error center">
     <b>A Group has to be chosen.  If you have chosen a group there are NO Functions set up for the chosen group.</b>
    </td>   
   </tr> 
<%    
   }   else   {
%>  
   <tr>
    <th style="width:2%">&nbsp;</th>   
    <th>Sequence</th>
    <th>Function</th>
    <th>Target Date</th>    
    <th>Completed</th>    
    <th>Responsible Person</th>
<%
   if (authorityR[0].equals("Y") ||
       authorityR[1].equals("Y"))
   { 
%>    
    <th>Reminder</th>    
<%
   }
%>    
    <th>Dependencies</th>  
    <th style="width:2%">&nbsp;</th>   
   </tr>
   <%= HTMLHelpersInput.inputBoxHidden("functionCount", (countR + "")) %>
<%
  ///  **-------------- LIST of Responsibilities --------------------**
   try
   {  // All old References - Updateable
      for (int cntR = 0; cntR < countR; cntR++)
      {
        String saveReadOnly = mainReadOnlyR;
         TicklerFunctionDetail thisrow = (TicklerFunctionDetail) responsibilities.elementAt(cntR);
        if (!thisrow.getStatus().trim().equals(""))
        {
         if (cntR == 0)
         {
            out.println(HTMLHelpersInput.inputBoxHidden("group", thisrow.getGroup()));
            out.println(HTMLHelpersInput.inputBoxHidden("keyValue", thisrow.getIdKeyValue()));
         }
  String dependency = "ready";
  String dependentStyle = "";         
  try
  {
     for (int dep = 0; dep < thisrow.getDependantFunctions().size(); dep++)
     {
       TicklerFunctionDetail thistest = (TicklerFunctionDetail) thisrow.getDependantFunctions().elementAt(dep); 
       if (!thistest.getStatus().toLowerCase().equals("complete") ||
           thistest.getCompletionDate().toString().equals("0"))
       {
          dependency = "pending";
          dep = thisrow.getDependantFunctions().size();
         if (!thisrow.getStatus().toLowerCase().equals("complete"))
           dependentStyle = "background-color:yellow";
       }   
     }
  }
  catch(Exception e)
  {
  }
  if (!thisrow.getStatus().toLowerCase().equals("complete"))
  {
  try
  {
      String testDate = thisrow.getTargetDate();
      int thisDate = 0;
      if (!testDate.equals("") &&
          !testDate.equals("0"))
      {
         DateTime targetDte = UtilityDateTime.getDateFromMMddyyWithSlash(testDate);
         thisDate = (new Integer(targetDte.getDateFormatyyyyMMdd()).intValue());
       //  thisDate = GetDate.formatSetDate(testDate).intValue();
      }
      DateTime todayDte = UtilityDateTime.getSystemDate();   
      //String[] systemDate = SystemDate.getSystemDate();
     // int todaysDate = GetDate.formatSetDate(systemDate[5]).intValue();
      int todaysDate = (new Integer(todayDte.getDateFormatyyyyMMdd()).intValue());
      if (thisDate < todaysDate)
        dependentStyle = "background-color:red";
  }
  catch(Exception e)
  {
  }
  if (dependentStyle.equals(""))
     dependentStyle = "background-color:green";
  }
%>
    <%= HTMLHelpersInput.inputBoxHidden(("functionNumber" + cntR), thisrow.getNumber().toString()) %>
    <%= HTMLHelpersInput.inputBoxHidden(("completionDate" + cntR), thisrow.getCompletionDate()) %>
    <%= HTMLHelpersInput.inputBoxHidden(("completionTime" + cntR), thisrow.getCompletionTime().toString()) %>
    <%= HTMLHelpersInput.inputBoxHidden(("completionUser" + cntR), thisrow.getCompletionUser()) %>           
   <tr> 
    <td class="center" style="<%= dependentStyle %>">&nbsp;</td>   
<%
   if (!thisrow.getCompletionDate().toString().equals("0"))
     mainReadOnlyR = "Y";
%>     
    <td><%= thisrow.getPhaseName() %></td>
    <td>
<%
   if (thisrow.getProcessDocument().trim().equals(""))
      out.println(thisrow.getDescription());
   else
      out.println(HTMLHelpersLinks.basicLink(thisrow.getDescription(), thisrow.getProcessDocument(), "", "", ""));
%>    
    </td>
    <td class="center">
<%	// Name of Date
    String dateName = "targetDate" + cntR;
    String readOnlyInfo = "";
    if (mainReadOnlyR.trim().equals("Y")) { %>
    <input type="hidden" name="<%= dateName %>" value="<%= thisrow.getTargetDate() %>" />
    <%= thisrow.getTargetDate() %>
<%  } else {%>
	<input type="text" class="datepicker" name="<%= dateName %>" value="<%= thisrow.getTargetDate() %>" <%= readOnlyInfo %> size="12" />
<%	} %>
     <%// HTMLHelpersInput.inputBoxDateTypeOrChoose(("targetDate" + cntR), thisrow.getTargetDate(), ("dd" + cntR), "N", mainReadOnlyR) %>
    </td>
    
    
    <td class="center">
<%  
   if (thisrow.getStatus().toLowerCase().equals("complete"))
     out.println(thisrow.getCompletionDate());       
   else
   {
     String completeRO = "Y";
     if (authorityR[0].equals("Y") ||
         thisrow.getRespPerson().toUpperCase().equals(authorityR[2].toUpperCase()))
        completeRO = "N";
     out.println(HTMLHelpersInput.inputCheckBox(("complete" + cntR), "N", completeRO));
   }  
%>
    </td>
    <td class="center">
     <%= UpdFunctionDetail.buildDropDownIDResponsiblePerson(("responsiblePerson" + cntR), thisrow.getRespPerson(), mainReadOnlyR) %>
    </td>
<%
   if (authorityR[0].equals("Y") ||
       authorityR[1].equals("Y"))
   { 
      String subject = thisrow.getIdKeyValue().trim() + " Reminder";
%>    
    <td class="center">
     <%= HTMLHelpersLinks.buttonEmail(thisrow.getRespPerson(), authorityR[2], subject, thisrow.getReminderText()) %>   
    </td>    
<%
   }
%>    
    <td class="center">
<%
  // Build Link to go to Dependencies
  StringBuffer dependenciesLink = new StringBuffer();
  dependenciesLink.append("/web/CtlFunction?"); 
  dependenciesLink.append("requestType=detail");
  dependenciesLink.append("&group=");
  dependenciesLink.append(thisrow.getGroup());
  dependenciesLink.append("&functionNumber=");
  dependenciesLink.append(thisrow.getNumber());
  dependenciesLink.append("&keyValue="); 
  dependenciesLink.append(thisrow.getIdKeyValue());

%>    
     <a href="<%= dependenciesLink.toString() %>" title="Click here to see all dependecies associated to this Function, AND the status of each." target = "_blank"><%= dependency %></a>
    </td> 
    <td style="<%= dependentStyle %>">&nbsp;</td>   
   </tr> 
     <% // JavascriptInfo.getCalendarFoot(formNameR, ("dd" + cntR), ("targetDate" + cntR)) %>
<%    
     mainReadOnlyR = saveReadOnly;
       }
      } // For Loop
   }
   catch(Exception e)
   {   }
  } 
%>        
 </table>  
