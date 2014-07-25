<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.app.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>

<%
try
{
   // Use this JSP in conjunction with ANY update Page
   //  REMEMBER to call the CtlKeyValues Servlet to update and get detail lists
   //
//-------------------------- updKeyValues.jsp---------------------//
//  Author :  Teri Walton  11/17/05   // Separate Fields to be used with ANY Update Page
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
// 04/07/08   TWalton	  Change the new Look (Blue and Grey)
//------------------------------------------------------------//
 int          countKV   = 0;
 String screenType = (String) request.getAttribute("screenType");
 if (screenType == null ||
     screenType.equals(""))
   screenType = "";
 String longFieldType = (String) request.getAttribute("longFieldType");
 if (longFieldType == null ||
     longFieldType.equals(""))
    longFieldType = "url";
 String screenOption = "comment";
 if (longFieldType.length() >= 3)
 {
    if (longFieldType.substring(0,3).equals("url"))
      screenOption = "url";
 }
 String showSequence = (String) request.getAttribute("showSequence");
 if (showSequence == null)
   showSequence = "";
 String appType = (String) request.getAttribute("appType");
 if (appType == null)
   appType = ""; 
 String headerAlign = "text-align:center";
 if (appType.equals("formula") ||
     appType.equals("method") ||
     appType.equals("spec"))
    headerAlign = "";
 Vector keyValues = new Vector();
 try
 {
   	keyValues = (Vector) request.getAttribute("listKeyValues");
   	countKV   = keyValues.size();
 }
 catch(Exception e)
 {} 
%>

  <table class="styled full-width">
<%
   if (!screenType.equals("listPage") &&
       !screenType.equals("detailNoHeading"))
   {
%>  
   <tr>
<%    
  if (screenType.equals("update"))
  {
%>    
    <th style="<%= headerAlign %>">Delete</th>
<% 
   }
   if (showSequence.equals("Y"))
   {
%>    
    <th style="<%= headerAlign %>">Sequence</th>
<%
  }
%>    
    <th style="<%= headerAlign %>">
<%
   if (screenOption.equals("url"))
      out.println("URL(Path) for Image/Document");
   else
   {
     if (appType.trim().equals("formula") ||
         appType.trim().equals("method") ||
         appType.trim().equals("spec"))
     {
      out.println("&nbsp;");
      //formatTD = "td04160105";
     }
     else
      out.println("Comments");
   }
%> 
    </th>
<%
   if (screenOption.equals("url"))
   {
%>   
    <th style="<%= headerAlign %>">Description</th>
<%
   }
   if (screenType.equals("update") ||
       (!appType.equals("formula") &&
        !appType.equals("method") &&
        !appType.equals("spec")))
   {
%>    
    <th style="<%= headerAlign %>">
     Date and Time<br />Last Updated
    </th>
    
    <th style="<%= headerAlign %>">User</th>
<%
   }
%>    
   </tr>
<%   
   } // end of if this is a listPage screen 
  ///  **-------------- NEW RECORD --------------------**
  
  int rowspan = countKV + 1;
  if (screenType.equals("update"))
  {
    if (screenType.equals("update"))
       rowspan++;
%>   
   <tr>  
<%
  if (screenType.equals("update"))
  {
%>    
    <td>NEW</td>
<%
   }
   if (showSequence.equals("Y"))
   {
%>    
    <td>
     <%= HTMLHelpersInput.inputBoxNumber((longFieldType + "Sequence"), "", "Added Sequence Number", 4, 3, "N", "N")%>                            	                
    </td>
<%
  }
%>    
    <td>
<%
   if (screenOption.equals("url"))
   {
%>       
     
     
     <%= HTMLHelpersInput.inputBoxUrlBrowse((longFieldType + "LongInformation"), "", "", 25, 500, "N", "N") %>
<%
   }else{
  String rOnlyKV = "Y";
  if (screenType.equals("update"))
     rOnlyKV = "N";
%>   
     <%-- <%= HTMLHelpersInput.inputBoxTextarea((longFieldType + "LongInformation"), "", 4, 75, 500, rOnlyKV) %> --%>

	<textarea type="textarea" rows="4" cols="75" name="<%=longFieldType + "LongInformation"%>" maxlength="500"></textarea>

<% 
   }
%>
    </td>
<%
   if (screenOption.equals("url"))
   {
%>      
   <td>
     <%= HTMLHelpersInput.inputBoxText((longFieldType + "Description"), "", "", 25, 200, "N", "N") %>
    </td>
<% 
   }
   if (screenType.equals("update") ||
       (!appType.equals("formula") &&
        !appType.equals("method") &&
        !appType.equals("spec")))
   {
%>    
    <td>&nbsp;</td>
    <td>&nbsp;</td>
<%
   }
%>    
   </tr> 
   <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "KeyValuesCount"), new Integer(countKV).toString()) %>
<%
  }
  ///  **-------------- Previously Entered --------------------**
  if (countKV > 0)
  {
    if (screenType.equals("update") &&
        screenOption.equals("url"))
    {  
      String columnSpan = "5";
      if (appType.equals("formula") ||
          appType.equals("method") ||
          appType.equals("spec"))
        columnSpan = "3";
      if (screenOption.equals("url"))
         columnSpan = "6";
%>
        <%-- this comment is no longer valid 
        <tr>  
          <td class="center" colspan="<%= columnSpan %>">
          
          <div class="ui-comment">
            IF YOU WANT TO CHANGE THE IMAGE USE THE BROWSE BUTTON TO REPLACE IT.<br />
            IF YOU DO NOT USE THE BROWSE BUTTON - NOTHING WILL CHANGE
            </div>
            
          </td>
        </tr>
        --%>
<%
   }
   try
   {  // All old References - Updateable
      for (int cntKV = 0; cntKV < countKV; cntKV++)
      {
         KeyValue thisrow = (KeyValue) keyValues.elementAt(cntKV);
%>
   <tr>  
<%
  if (screenType.equals("update"))
  {
%>  
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "UniqueNumber" + cntKV), thisrow.getUniqueKey()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "Status" + cntKV), thisrow.getStatus()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "DeleteDate" + cntKV), thisrow.getDeleteDate()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "DeleteTime" + cntKV), thisrow.getDeleteTime()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "DeleteUser" + cntKV), thisrow.getDeleteUser()) %>
    <td>
     <%= HTMLHelpersInput.inputCheckBox((longFieldType + "Delete" + cntKV), "", "N")%>                            	                
    </td>
<%
  }
  else
  {
%> 
<%
  }
   if (showSequence.equals("Y"))
   {  
%>   
    <td>
<%
  if (screenType.equals("update"))
    out.println(HTMLHelpersInput.inputBoxText((longFieldType + "Sequence" + cntKV), thisrow.getSequence().trim(), "Sequence Number", 4, 3, "N", "N"));
  else
  {
    out.println(HTMLHelpersInput.inputBoxHidden((longFieldType + "Sequence" + cntKV), thisrow.getSequence().trim()));
    out.println(thisrow.getSequence().trim());
  }  
%>    
    </td>
<%
   }
%>    
    <td>
<%
   if (screenOption.equals("url"))
   {
%>      
     <%= HTMLHelpersLinks.imageCamera(thisrow.getValue().trim(), "Y") %>&nbsp;
     <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "LongInformationOld" + cntKV), thisrow.getValue().trim()) %>
<%
    if (screenType.equals("update"))
    {
%>     
      <br />
     <%= HTMLHelpersInput.inputBoxUrlBrowse((longFieldType + "LongInformation" + cntKV), "", "", 25, 500, "N", "N") %>
<%
    }
  }else{
  if (screenType.equals("update"))
  {
%>   
     <%-- <%= HTMLHelpersInput.inputBoxTextarea((longFieldType + "LongInformation" + cntKV), thisrow.getValue().trim(), 4, 75, 500, "N") %> --%>
     <textarea type="textarea" rows="4" cols="75" name="<%=longFieldType + "LongInformation" + cntKV%>" maxlength="500"><%=thisrow.getValue().trim() %></textarea>
<%
   }else{
%>
     <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "LongInformation" + cntKV), thisrow.getValue().trim()) %>
     <%= thisrow.getValue().trim() %>    
<%  
    } 
  }
%>  
    </td>
<%
   if (screenOption.equals("url"))
   {
%>       
   <td>
<%
  if (screenType.equals("update"))
    out.println(HTMLHelpersInput.inputBoxText((longFieldType + "Description" + cntKV), thisrow.getDescription(), "", 25, 200, "N", "N"));
  else
  {
    out.println(HTMLHelpersInput.inputBoxHidden((longFieldType + "Description" + cntKV), thisrow.getDescription()));
    out.println(thisrow.getDescription().trim());
  }  
%>     
    </td>
<%
   }
   if (screenType.equals("update") ||
      (!appType.equals("formula") &&
       !appType.equals("method") &&
       !appType.equals("spec")) &&
       !screenType.equals("listPage"))
   {
%>    
    <td><%= thisrow.getLastUpdateDate() %>&nbsp;&nbsp;<%= HTMLHelpersMasking.maskTime(thisrow.getLastUpdateTime()) %></td>
    <td><%= thisrow.getLastUpdateUser() %></td>
<%
   }
   if (!screenType.equals("update"))
   {
%>    
<%
   }
%>    
   </tr> 
<%     
      }
   }
   catch(Exception e)
   {
   }
   }
   try
   {
      int countChosenImages = ((Integer) request.getAttribute("imageCount")).intValue();
      request.setAttribute("imageCount", new Integer((countChosenImages + countKV)));
   }
   catch(Exception e)
   {}   
   
%>        
</table>  

<% // Try catch so this can be used More than Once on A Page.
   }
   catch(Exception e)
   {}
%>