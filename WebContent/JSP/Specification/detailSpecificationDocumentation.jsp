<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
   // Use this JSP in conjunction with detailSpecification.jsp
   //
//-------------------------- detailSpecificationDocumentation.jsp---------------------//
//  Author :  Teri Walton  03/28/05 
       // Separate out the analytical codes
//                                   
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//------------------------------------------------------------//
    
 Vector tInfo  = new Vector();
 int    tCount = 0;
 try
 {
   	tInfo  = (Vector) request.getAttribute("specificationText");
   	tCount = tInfo.size();
 }
 catch(Exception e)
 {
 }   
 
  int imgCnt = 3;
  int expCnt = 1;
  try
  {
   expCnt  = ((Integer) request.getAttribute("expandCount")).intValue();
   imgCnt  = ((Integer) request.getAttribute("imageCount")).intValue();
  }
  catch(Exception e)
  {}  
%>
<html>
  <head>
    <title></title>
  </head>
  <body>
<table class="table00001"  cellspacing="0" >
<tr>
<td style="width:1%">&nbsp;
</td>
<td>
<table class="table00001"  cellspacing="0" >
<%   
  String saveSubjectCode = "";
   try
   {  
      for (int x = 0; x < tCount; x++)
      {
         SpecificationDocumentation thisrowt = (SpecificationDocumentation) tInfo.elementAt(x);
         if (!saveSubjectCode.equals(thisrowt.getSubjectCode().trim()))
         { // Display Subject Line
            saveSubjectCode = thisrowt.getSubjectCode().trim();
  if (x != 0){ %></div></pre></td></tr></table></span></td></tr><%  } %><tr class="tr02001">
  <td class="td01CC001">
<%= JavascriptInfo.getExpandingSection("O", thisrowt.getSubjectDescription().trim(), 8, expCnt, imgCnt, 1, 4) %>  
<table class="table00001"  cellspacing="0" ><tr><td><pre><div align="center"><%        
       expCnt++;
       imgCnt++;
     }
if (thisrowt.getTextHighlighted())   
        out.println("<font style=\"background-color:#CCCC99\">" + thisrowt.getTextSpecification() + "</font>");  
      else
        out.println(thisrowt.getTextSpecification());    
      } // end of the for loop
   }
   catch(Exception e)
   {
   }%></div></pre></td></tr>
    </table>
   </span>
     </td>
     </tr>
</table>  
</td>
<td style="width:1%">&nbsp;
</td>
</tr>
</table>
<%
  request.setAttribute("imageCount", new Integer(imgCnt));
  request.setAttribute("expandCount", new Integer(expCnt)); 
%>  
</body>
</html>