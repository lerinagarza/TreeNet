<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%


//---------------- displayIFSOnly.jsp -----------------------//
//   Author :  Teri Walton  1/4/2006
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//------------------------------------------------------------//
  String errorPage = "/Utilities/displayIFSOnly.jsp";
  String updateTitle = "Display Folder Path(IFS)";

 String linkURL = request.getParameter("linkURL");
 if (linkURL == null)
    linkURL = "";
 else
 {
     String findIFSValue = request.getParameter("findIFS");
     if (findIFSValue == null)
       findIFSValue = "";
    try
    {   
        String imageList[] = HTMLHelpersLinks.getDirectoryFromPath(findIFSValue);
        request.setAttribute("imageList", imageList);
        request.setAttribute("displayImagePath", (linkURL + "/"));
        String newFolderList[] = HTMLHelpersLinks.getFoldersFromPath(findIFSValue);
        request.setAttribute("folderList", newFolderList);
     }
     catch(Exception e)
     {}
   }
%>
<html>
 <head>
   <title><%= updateTitle %></title>
 </head>
 <body>
 <jsp:include page="../../JSP/include/heading.jsp"></jsp:include>
<table class="table01001" cellspacing="0" cellpadding="3">
   <tr>
    <tr class="tr02001">
     <td class="td051CL001" style="border-top:1px solid #006400" colspan = "4">
  <jsp:include page="displayIFS.jsp"></jsp:include>
       </td>
    </tr>
</table>    
<jsp:include page="../../JSP/include/footer.jsp"></jsp:include>
   </body>
</html>