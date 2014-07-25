<%@ page language = "java" %>
<%@ page import="com.treetop.*" %>
<%@ page import="com.treetop.data.*" %>
<%@ page import = "java.util.*" %>

<%
//--------------------------- preview10PerSheet.jsp ----------------------------------//
// Author :  Teri Walton  12/17/03                                      
//                                                                             
//    Date       Name       Comments                                           
//  --------   ----------  ------------------------------------                
//   3/09/04    TWalton     Changed comments and images for 5.0 server.
//-----------------------------------------------------------------------------------//

   HttpSession sess = request.getSession(true);
   Vector labelInfo  = new Vector();
   int    labelCount = 0;
try
{
   labelInfo  = (Vector) sess.getAttribute("labelInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Vectors) = " + e);
}
try
{
   labelCount = labelInfo.size();
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When determining the Size of the Vector) = " + e);
}
%>
<html>
  <head>
     <title>Preview Label Page</title>
     <style>
        td {font-family: 'Calibri' sans-serif;}
     </style>
  </head>
<body>
  <table cellpadding="12">

<%
 String pageBreak    = "";
 String rightSide    = "N";
 int    rowCount     = 0;
 Labels whichLabel = (Labels) labelInfo.elementAt(0);
 int startNumber  = whichLabel.getLabelNumber().intValue();
 while (startNumber > 1)
 {
   startNumber = startNumber - 1;
   if (startNumber == 1)
     rightSide = "Y";
   else
   {
     startNumber = startNumber - 1;
%>
    <tr>
      <td>
        <table cellpadding="1">
          <tr>
            <td style="height:160px">
               &nbsp;
            </td>
          </tr>
        </table>
      </td>
    </tr>
<%
   }
 }
   for (int x = 0; x < labelCount; x++)
   {
      if (rowCount == 5)
        pageBreak   = " style=\"page-break-before:always\" ";
     rowCount++;

      Labels thisLabel = (Labels) labelInfo.elementAt(x);
%>
    <tr <%= pageBreak %>>
      <td>
<%
   if (rightSide.equals("N"))
   {
%>
        <table cellpadding="1">
          <tr>
            <td style="width:360px;
                 height:160px">
               <%= thisLabel.getLabelInformation().trim() %>
            </td>
          </tr>
        </table>
<%
   }
   else
   {
%>
        <table cellpadding="1">
          <tr>
            <td style="width:360px">
               &nbsp;
            </td>
          </tr>
        </table>
<%
   }
%>
      </td>
      <td>
        <table cellpadding="1">
          <tr>
            <td style="width:5px">
               &nbsp;
            </td>
          </tr>
        </table>
      </td>
      <td>
<%
      String displayThis = "&nbsp;";
     if (rightSide.equals("N"))
        x++;
      if (labelCount != x)
      {
         if (rightSide.equals("N"))
            thisLabel = (Labels) labelInfo.elementAt(x);
         else
            rightSide = "N";
         displayThis = thisLabel.getLabelInformation().trim();
%>
        <table cellpadding="1">
          <tr>
            <td style="width:360px;
                 height:160px">
              <%= displayThis %>
            </td>
          </tr>
        </table>
<%
       }
%>
      </td>
    </tr>
<%
   pageBreak = "";
   }
%>
  </table>

</body>
</html>