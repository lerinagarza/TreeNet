<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.servlets.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>

<%
   // Use this JSP in conjunction with detailSpecification.jsp
   //
//-------------------------- detailSpecificationAnalytical.jsp---------------------//
//  Author :  Teri Walton  03/28/05 
       // Separate out the analytical codes
//                                   
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//------------------------------------------------------------//
    
 Vector aInfo  = new Vector();
 int    aCount = 0;
 try
 {
   	aInfo  = (Vector) request.getAttribute("analyticalInformation");
   	aCount = aInfo.size();
 }
 catch(Exception e)
 {
 }   
 int imageNumber = 1;
 try
 {
 	imageNumber = ((Integer) request.getAttribute("imageCount")).intValue();
 }
 catch(Exception e)
 {
 }
%>
<%! 
// Do calculations for Target, Min & Max.
   public String decimalPositions(BigDecimal inValue, 
                                  Integer inDecimal,
                                  Integer displayDecimals)
   {
      String outValue = "";

      try
      {
           if (displayDecimals != null &&
               displayDecimals.intValue() != 0)
             outValue = HTMLHelpers.displayNumber(inValue, displayDecimals.intValue());
           else
             outValue = HTMLHelpers.displayNumber(inValue, inDecimal.intValue());
           if (outValue == null ||
               outValue.trim().equals(""))
             outValue = inValue.toString();
      }
      catch(Exception e)
      {  
        outValue = inValue.toString();
      }
      return outValue;
   } 
%>
<html>
  <head>
    <title></title>
  </head>
  <body>
 <table class="table01001"  cellspacing="0" >
  <tr class="tr04001">
   <td class="td074CL001" style="width:5%">&nbsp;</td>
   <td class="td074CL001" style="width:5%">&nbsp;</td>
   <td class="td014CL001">
    <b>&nbsp;</b>
   </td>
   <td class="td014CL001">
    <b>Ident</b>
   </td>      
   <td class="td014CL001">
    <b>Description</b>
   </td>
   <td class="td014CC001">
    <b>Target</b>
   </td>
   <td class="td014CC001">
    <b>Unit of Measure</b>
   </td>
   <td class="td014CC001">
    <b>Minimum</b>
   </td>
   <td class="td014CC001">
    <b>Maximum</b>
   </td>  
   <td class="td014CC001">
    <b>Print on COA</b>
   </td>
   <td class="td014CC001">
    <b>Required</b>
   </td>       
   <td class="td074CL001" style="width:5%">&nbsp;</td>
   <td class="td074CL001" style="width:5%">&nbsp;</td>
  </tr>
<%
   try
   {  
      for (int x = 0; x < aCount; x++)
      {
         SpecificationAnalytical thisrowa = (SpecificationAnalytical) aInfo.elementAt(x);
%>
  <tr class="tr00001"> 
   <td></td>       
   <td class="td074CL002">&nbsp;</td> 
   <td class="td074CR002">
<%   
       out.println(HTMLHelpersLinks.imageCamera(thisrowa.getAnalyticalReference()));
     if (thisrowa.getAnalyticalReference() != null &&
         !thisrowa.getAnalyticalReference().trim().equals(""))       
         imageNumber++;   
%>         
   &nbsp;</td>    
   <td class="td074CL002">
       <%= "<b>" + thisrowa.getAnalyticalCode().trim() + "</b>" %>&nbsp; 
       <%
       //HTMLHelpersLinks.routerAnalyticalCode(thisrowa.getAnalyticalCode().trim(), "", "", ("codeType=" + thisrowa.getCodeType()))
       %>
      &nbsp;</td>             
   <td class="td074CL002">
<%
   String displayCode = "";
   if (thisrowa.getLongDescription() != null &&
       !thisrowa.getLongDescription().trim().equals(""))
   {
     if (thisrowa.getShortDescription() != null &&
         !thisrowa.getShortDescription().equals(""))   
        displayCode = "<b><acronym title=\"" + thisrowa.getLongDescription().trim() +  "\">" + 
        				thisrowa.getShortDescription().trim() + 
        				"</acronym></b>";
     else
        displayCode = "<b>" + thisrowa.getLongDescription().trim() + "</b>";     
   }
   else
   {
     if (thisrowa.getShortDescription() != null &&
         !thisrowa.getShortDescription().equals(""))   
        displayCode = "<b>" + thisrowa.getShortDescription().trim() + "</b>";
   }
%>   
             <%= displayCode  %>&nbsp;
    </td>
   <td class="td044CC002">
     <%= thisrowa.getTargetValue() %>&nbsp; 
   </td>
   <td class="td044CC002">
     &nbsp;<%= thisrowa.getUnitOfMeasure() %>
   </td>
   <td class="td044CC002">
     <%= decimalPositions(thisrowa.getAcceptableLowerLimit(), thisrowa.getDecimalPositions(), thisrowa.getDecimalsToDisplay()) %>&nbsp;   
   </td>   
   <td class="td044CC002">
     <%= decimalPositions(thisrowa.getAcceptableUpperLimit(), thisrowa.getDecimalPositions(), thisrowa.getDecimalsToDisplay()) %>&nbsp;      
     
   </td>   
   <td class="td044CC002">
<%
   if (thisrowa.getCertificateOfAnalysis())
      out.println("Yes");
   else
      out.println("No");
%>   
   </td>
   <td class="td044CC002">
<%
   String displayR = "No";
   if (thisrowa.getRequiredLevelSequence() != null &&
       thisrowa.getRequiredLevelSequence().intValue() != 0)
   {
      if (thisrowa.getRequiredLevelSequence().intValue() == 9)    
         displayR = "Mandatory";
      else
         displayR = "Yes";
   }    
   out.println(displayR);
%>   
    &nbsp;
   </td>   
   <td class="td074CL002">&nbsp;</td>       
   <td></td>            
  </tr> 
<%     
      }
   }
   catch(Exception e)
   {
   }
   request.setAttribute("imageCount", new Integer(imageNumber));
%>   
</table>  
</body>
</html>