<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------  displayIFSNew.jsp  -----------------------------//
//
//   Author :  Teri Walton  10/20/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//   9/25/06    twalton    Changed to look at NEW X Drive
//   4/07/08    twalton	   Changed to the NEW look (blue and grey)
//------------------------------------------------------------------//
//********************************************************************
%>

<table class="table00" cellspacing="0" cellpadding="2">
<%
  String displayImagePath = (String) request.getAttribute("displayImagePath");
  if (displayImagePath == null)
    displayImagePath = "https://image.treetop.com/webapp/";
%>
  <tr>
   <td class="td0414" style="text-align:center">
     <%= HTMLHelpersLinks.basicLink("Click Here to go directly to " + displayImagePath, displayImagePath, "This will take you to the folder structure", "a04002", "") %>
     <br>&nbsp;&nbsp;&nbsp;Please Note that if this folder is not created you will receive a <b>"The page cannot be displayed"</b> display. 
   </td>
  </tr> 
 </table>
