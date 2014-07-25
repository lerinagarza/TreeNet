<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------  dtlIFS.jsp  ------------------------------------------//
//   To Be included in the dtlIFS Page
//
//   Author :  Teri Walton  10/20/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//   9/25/06    twalton    Changed to look at NEW X Drive
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
//  String errorPage = "";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 String[] imageList = null;
 try
 {
	imageList = (String[]) request.getAttribute("imageList");
 }
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }   
  String[] fList = null;
 try
 {
	fList = (String[]) request.getAttribute("folderList");
 }
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }     
%>
<html>
  <head>
  </head>
 <body>
<table class="table00001" cellspacing="0" cellpadding="2">
<%
  String displayImagePath = (String) request.getAttribute("displayImagePath");
  if (displayImagePath == null)
    displayImagePath = "https://image.treetop.com/webapp/";
  if (fList != null &&
      fList.length > 0)
  {
    String inPath = (String) request.getAttribute("initialPath");
    if (inPath == null)
      inPath = "";
%>
  <tr class = "tr04001">
   <td class="td011CL001" style="width:5%">&nbsp;</td>
   <td class="td011CL001" style="width:5%">&nbsp;</td>
   <td class="td011CL001" style="width:5%">Link</td>
   <td class="td011CL001">Folder Name</td>
   <td class="td011CL001" style="width:5%">Link</td>
   <td class="td011CL001">Folder Name</td>
   <td class="td011CL001" style="width:5%">&nbsp;</td>
   <td class="td011CL001" style="width:5%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td041CC001" colspan = "8">Image Path <%= HTMLHelpersLinks.basicLink(displayImagePath, displayImagePath, "Click here to go to this folder", "", "") %></td>
  </tr> 
<%
     for (int fdrx = 0; fdrx < fList.length; fdrx++)
     {    
%>  
  <tr>
   <td class="td041CL001">&nbsp;</td>
   <td class="td041CL002">&nbsp;</td>
   <td class="td041CL002">
      <%// HTMLHelpersLinks.imageFolder(displayImagePath + fList[fdrx], (inPath + "\\" + fList[fdrx])) %>
     <%= HTMLHelpersLinks.imageFolder(displayImagePath + fList[fdrx], (inPath + fList[fdrx] + "/")) %>
   </td>
   <td class="td071CL002"><%= fList[fdrx] %></td>
<%
  fdrx++;
  if (fdrx < fList.length)
  {
%>   
   <td class="td041CL002">
     <%// HTMLHelpersLinks.imageFolder(displayImagePath + fList[fdrx], (inPath + "\\" + fList[fdrx])) %>
     <%= HTMLHelpersLinks.imageFolder(displayImagePath + fList[fdrx], (inPath + fList[fdrx] + "/")) %>

   </td>  
   <td class="td071CL002">
     <%= fList[fdrx] %>
   </td>
<%
 }else{
%>   
   <td class="td041CL002" colspan = "2">&nbsp;</td>
<%
  }
%>   
   <td class="td041CL002">&nbsp;</td>
   <td class="td041CL001">&nbsp;</td>
  </tr>
<%
      }

try
   {
      int countIFSImages = ((Integer) request.getAttribute("imageCount")).intValue();
      request.setAttribute("imageCount", new Integer((fList.length) + countIFSImages));
   }
   catch(Exception e)
   {}      
   }
%>  
  <tr class = "tr04001">
   <td class="td011CL001" style="width:5%">&nbsp;</td>
   <td class="td011CL001" style="width:5%">&nbsp;</td>
   <td class="td011CL001" style="width:5%">Link</td>
   <td class="td011CL001">Image Name</td>
   <td class="td011CL001" style="width:5%">Link</td>
   <td class="td011CL001">Image Name</td>
   <td class="td011CL001" style="width:5%">&nbsp;</td>
   <td class="td011CL001" style="width:5%">&nbsp;</td>
  </tr>
<%
  if (imageList == null ||
      imageList.length <= 0)
  {
%>
  <tr>
   <td class="td041CC001" colspan = "8">
     <%= HTMLHelpersLinks.basicLink("Click Here to go directly to " + displayImagePath, displayImagePath, "This will take you to the folder structure", "a04002", "") %>
     <br>&nbsp;&nbsp;&nbsp;Please Note that if this folder is not created you will receive a <b>"The page cannot be displayed"</b> display. 
     
   </td>
<%// This code has changed, because of a pathing issue on the X Drive.. IF we can figure out HOW to look directly at
  // the X Drive this code SHOULD be changed back
  //  There are No Images in this Path displayImagePath 
%>   
  </tr> 
<%
  }else{
%>
  <tr>
   <td class="td041CC001" colspan = "8">Image Path <%= displayImagePath %></td>
  </tr> 
<%
     for (int imgx = 0; imgx < imageList.length; imgx++)
     {    
%>  
  <tr>
   <td class="td041CL001">&nbsp;</td>
   <td class="td041CL002">&nbsp;</td>
   <td class="td041CL002">
      <%= HTMLHelpersLinks.imageCamera(displayImagePath + imageList[imgx]) %>
   </td>
   <td class="td071CL002"><%= imageList[imgx] %></td>
<%
  imgx++;
  if (imgx < imageList.length)
  {
%>   
   <td class="td041CL002">
     <%= HTMLHelpersLinks.imageCamera(displayImagePath + imageList[imgx]) %>
   </td>  
   <td class="td071CL002">
     <%= imageList[imgx] %>
   </td>
<%
 }else{
%>   
   <td class="td041CL002" colspan = "2">&nbsp;</td>
<%
  }
%>   
   <td class="td041CL002">&nbsp;</td>
   <td class="td041CL001">&nbsp;</td>
  </tr>
<%
      }
   }
try
   {
      int countIFSImages = ((Integer) request.getAttribute("imageCount")).intValue();
      request.setAttribute("imageCount", new Integer((imageList.length) + countIFSImages));
   }
   catch(Exception e)
   {}      
%>  
 </table>
 </body>
</html>