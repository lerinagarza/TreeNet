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
 String showSequence = (String) request.getAttribute("showSequence");
 if (showSequence == null)
   showSequence = "";
 
 Vector keyValues = new Vector();
 try
 {
   	keyValues = (Vector) request.getAttribute("listKeyValues");
   	countKV   = keyValues.size();
 }
 catch(Exception e)
 {
 } 
%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getCheckTextareaLength() %>
 </head>
 <body>
  <table class="table01001"  cellspacing="0" >
   <tr class="tr04001">
    <td class="td074CL001" style="width:2%">&nbsp;</td>
<%    
  if (screenType.equals("update"))
  {
%>    
    <td class="td014CC001">
     <b>Delete</b>
    </td>
<% 
   }
   if (showSequence.equals("Y"))
   {
%>    
    <td class="td014CC001">
     <b>Sequence</b>
    </td>
<%
  }
%>    
    <td class="td014CC001">
     <b>
<%
   if (longFieldType.equals("url"))
      out.println("URL(Path) for Image/Document");
   else
      out.println("Comments");
%>     
     </b>
    </td>
<%
   if (longFieldType.equals("url"))
   {
%>   
    <td class="td014CC001">
     <b>Description</b>
    </td>
<%
   }
%>    
    <td class="td044CC001">
     <b>Date and Time<br>Last Updated</b>
    </td>
    <td class="td044CC001">
     <b>User</b>
    </td>
    <td class="td074CL001" style="width:2%">&nbsp;</td>
   </tr>
<%   
  ///  **-------------- NEW RECORD --------------------**
  
  int rowspan = countKV + 1;
  if (screenType.equals("update"))
  {
  if (screenType.equals("update"))
     rowspan++;
%>   
   <tr class="tr00001">  
    <td class="td074CL001" style="width:2%" rowspan="<%= rowspan %>">&nbsp;</td>
<%
  if (screenType.equals("update"))
  {
%>    
    <td class="td074CL002" >NEW</td>
<%
   }
   if (showSequence.equals("Y"))
   {
%>    
    <td class="td074CL002">
     <%= HTMLHelpersInput.inputBoxNumber((longFieldType + "Sequence"), "", "Added Sequence Number", 4, 3, "N", "N")%>                            	                
    </td>
<%
  }
%>    
    <td class="td044CL002">
<%
   if (longFieldType.equals("url"))
   {
%>       
     <%= HTMLHelpersInput.inputBoxUrl((longFieldType + "LongInformation"), "", "", 25, 500, "N", "N") %>
<%
   }else{
  String rOnlyKV = "Y";
  if (screenType.equals("update"))
     rOnlyKV = "N";
%>   
     <%= HTMLHelpersInput.inputBoxTextarea((longFieldType + "LongInformation"), "", 4, 75, 500, rOnlyKV) %>
<% 
   }
%>
    </td>
<%
   if (longFieldType.equals("url"))
   {
%>      
   <td class="td044CL002">
     <%= HTMLHelpersInput.inputBoxText((longFieldType + "Description"), "", "", 25, 200, "N", "N") %>
    </td>
<% 
   }
%>    
    <td class="td074CL002" >&nbsp;</td>
    <td class="td074CL002" >&nbsp;</td>
    <td class="td074CL001" style="width:2%" rowspan="<%= rowspan %>">&nbsp;</td>
   </tr> 
   <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "KeyValuesCount"), new Integer(countKV).toString()) %>
<%
  }
  ///  **-------------- Previously Entered --------------------**
  if (countKV > 0)
  {
    if (screenType.equals("update") &&
        longFieldType.equals("url"))
    {  
      String columnSpan = "5";
      if (longFieldType.equals("url"))
         columnSpan = "6";
%>
        <tr class="tr00001">  
          <td class="td041CL002" colspan="<%= columnSpan %>">
            IF YOU WANT TO CHANGE THE IMAGE USE THE BROWSE BUTTON TO REPLACE IT.<br>
            IF YOU DO NOT USE THE BROWSE BUTTON - NOTHING WILL CHANGE
          </td>
        </tr>
<%
   }
   try
   {  // All old References - Updateable
      for (int cntKV = 0; cntKV < countKV; cntKV++)
      {
         KeyValue thisrow = (KeyValue) keyValues.elementAt(cntKV);
%>
   <tr class="tr00001">  
<%
  if (screenType.equals("update"))
  {
%>  
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "UniqueNumber" + cntKV), thisrow.getUniqueKey()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "Status" + cntKV), thisrow.getStatus()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "DeleteDate" + cntKV), thisrow.getDeleteDate()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "DeleteTime" + cntKV), thisrow.getDeleteTime()) %>
    <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "DeleteUser" + cntKV), thisrow.getDeleteUser()) %>
    <td class="td074CL002">
     <%= HTMLHelpersInput.inputCheckBox((longFieldType + "Delete" + cntKV), "", "N")%>                            	                
    </td>
<%
  }
  else
  {
%> 
    <td class="td074CL001" style="width:2%">&nbsp;</td>
<%
  }
   if (showSequence.equals("Y"))
   {  
%>   
    <td class="td044CL002">
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
    <td class="td044CL002">
<%
   if (longFieldType.equals("url"))
   {
%>      
     <%= HTMLHelpersLinks.imageCamera(thisrow.getValue().trim()) %>&nbsp;<%= thisrow.getValue().trim() %>
     <%= HTMLHelpersInput.inputBoxHidden((longFieldType + "LongInformationOld" + cntKV), thisrow.getValue().trim()) %>
<%
    if (screenType.equals("update"))
    {
%>     
      <br>
     <%= HTMLHelpersInput.inputBoxUrl((longFieldType + "LongInformation" + cntKV), "", "", 25, 500, "N", "N") %>
<%
    }
  }else{
  if (screenType.equals("update"))
  {
%>   
     <%= HTMLHelpersInput.inputBoxTextarea((longFieldType + "LongInformation" + cntKV), thisrow.getValue().trim(), 4, 75, 500, "N") %>
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
   if (longFieldType.equals("url"))
   {
%>       
   <td class="td044CL002">
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
%>    
    <td class="td074CL002" ><%= thisrow.getLastUpdateDate() %>&nbsp;&nbsp;<%= HTMLHelpersMasking.maskTime(thisrow.getLastUpdateTime()) %></td>
    <td class="td074CL002" ><%= thisrow.getLastUpdateUser() %></td>
<%
   if (!screenType.equals("update"))
   {
%>    
    <td class="td074CL001" style="width:2%">&nbsp;</td>
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
</body>
</html>
<% // Try catch so this can be used More than Once on A Page.
   }
   catch(Exception e)
   {}
%>