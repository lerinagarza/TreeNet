<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updVariety.jsp -------------------------------------------//
//
//    Author :  Teri Walton  3/18/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
try
{
 Vector updVariety = new Vector();
 String readOnlyVar = "";
 String screenType = "";
 String countFieldName = "countVarietiesIncluded";
 Vector dropDown = new Vector();
 int lineCount = 0;
 int blankCount = 5;
 try
 {
    String varietyRequestType = (String) request.getAttribute("requestType");
    screenType = (String) request.getAttribute("screenType");
    //------------------------------------------------------------------------------//
    //  FORMULA
    //------------------------------------------------------------------------------//
     if (varietyRequestType.trim().equals("updFormula") ||
         varietyRequestType.trim().equals("copyNewFormula") ||
         varietyRequestType.trim().equals("reviseFormula"))
     {
       // Fill in all the variable fields for this included screen  
          UpdFormula updMain = (UpdFormula) request.getAttribute("updViewBean");
		  if (screenType.trim().equals("I"))
	         updVariety = updMain.getListVarietiesIncluded();
	      else // which means X
	      {
	         updVariety = updMain.getListVarietiesExcluded();
	         countFieldName = "countVarietiesExcluded";
	      }
	         
     }
	//------------------------------------------------------------------------------//
    //  SPECIFICATION
    //------------------------------------------------------------------------------//
     if (varietyRequestType.trim().equals("updSpec") ||
         varietyRequestType.trim().equals("copyNewSpec") ||
         varietyRequestType.trim().equals("reviseSpec"))
     {
       // Fill in all the variable fields for this included screen  
          UpdSpecification updMain = (UpdSpecification) request.getAttribute("updViewBean");
		  if (screenType.trim().equals("I"))
	         updVariety = updMain.getListVarietiesIncluded();
	      else // which means X
	      {
	         updVariety = updMain.getListVarietiesExcluded();
	         countFieldName = "countVarietiesExcluded";
	      }
	         
     }     
     lineCount = updVariety.size();
     if (lineCount >= 5)
        blankCount = lineCount + 2;
      
     dropDown = UpdVariety.buildDropDownDualCropVarietyVectors(screenType, blankCount);
    readOnlyVar = (String) request.getAttribute("readOnlySpec");	
    if (readOnlyVar == null)
      readOnlyVar = "N";
    
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

%>
   <%
   for (int x = 0; x < dropDown.size(); x++)
   {
      out.println((String) ((Vector) dropDown.elementAt(x)).elementAt(2));
      out.println((String) ((Vector) dropDown.elementAt(x)).elementAt(3));
   }
%>  

  <table class="table00" cellspacing="0" style="width:100%">
   <tr class="tr02">
    <td class="td04140102" style="text-align:center">Remove</td>
    <td class="td04140102" style="text-align:center">Crop</td>
    <td class="td04140102" style="text-align:center">Variety</td>
<%
   if (screenType.trim().equals("I"))
   {
%>
    <td class="td04140102" style="text-align:center">Target Percent</td>    
    <td class="td04140102" style="text-align:center">Minimum Percent</td>
    <td class="td04140102" style="text-align:center">Maximum Percent</td>
<%
   }
%>    
   </tr>
<%
  
  if (updVariety.size() > 0)
  {
    for (int lineCnt = 0; lineCnt < updVariety.size(); lineCnt++)
    {
       try{
          UpdVariety dtlLine = (UpdVariety) updVariety.elementAt(lineCnt);
%>
   <tr> 
    <td class="td03140102" style="text-align:center">&#160;
<% 
    if (!dtlLine.getVariety().trim().equals(""))
      out.println(HTMLHelpersInput.inputCheckBox((screenType + "removeEntry" + lineCnt), "", readOnlyVar));    
%>
    &#160;</td>  
    <td class="td04140102" style="text-align:center">&#160;
<%
    if (!dtlLine.getCropModel().trim().equals(""))
    {
       out.println(dtlLine.getCropModelDescription());
       out.println(HTMLHelpersInput.inputBoxHidden((screenType + "cropModel" + lineCnt), dtlLine.getCropModel()));
       out.println(HTMLHelpersInput.inputBoxHidden((screenType + "cropModelDescription" + lineCnt), dtlLine.getCropModelDescription()));
    }
    else
      out.println((String) ((Vector) dropDown.elementAt(lineCnt)).elementAt(0));
%>    
    &#160;</td>  
    <td class="td04140102" style="text-align:center">&#160;
<%
    if (!dtlLine.getVariety().trim().equals(""))
    {
       out.println(dtlLine.getVarietyDescription());
       out.println(HTMLHelpersInput.inputBoxHidden((screenType + "variety" + lineCnt), dtlLine.getVariety()));
       out.println(HTMLHelpersInput.inputBoxHidden((screenType + "varietyDescription" + lineCnt), dtlLine.getVarietyDescription()));
    }
    else
      out.println((String) ((Vector) dropDown.elementAt(lineCnt)).elementAt(1));
%>    
    &#160;</td>      
<%
   if (screenType.trim().equals("I"))
   {
%>
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "percentage" + lineCnt), dtlLine.getPercentage(), "Target Percent", 10,10, "", readOnlyVar) %>&#160;<%= dtlLine.getPercentageError().trim() %></td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "minimumPercent" + lineCnt), dtlLine.getMinimumPercent(), "Minimum Percent", 10,10, "", readOnlyVar) %>&#160;<%= dtlLine.getMinimumPercentError().trim() %></td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "maximumPercent" + lineCnt), dtlLine.getMaximumPercent(), "Maximum Percent", 10,10, "", readOnlyVar) %>&#160;<%= dtlLine.getMaximumPercentError().trim() %></td> 
<%
   }
%>  
   </tr>	   
<%
        }catch(Exception e)
        {}
     }//end of For Loop
   } // end of If Statement  
   
   
   if (readOnlyVar.equals("N"))  
   {
   for (int x = updVariety.size(); x < blankCount; x++)
   {
%>
   <tr> 
    <td class="td03140102" style="text-align:center">&#160;&#160;</td>  
    <td class="td03140102" style="text-align:center">&#160;<%= (String) ((Vector) dropDown.elementAt(x)).elementAt(0) %>&#160;</td>  
    <td class="td03140102" style="text-align:center">&#160;<%= (String) ((Vector) dropDown.elementAt(x)).elementAt(1) %>&#160;</td>      
<%
   if (screenType.trim().equals("I"))
   {
%>
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "percentage" + x), "", "Target Percent", 10,10, "", "") %></td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "minimumPercent" + x), "", "Minimum Percent", 10,10, "", "") %></td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "maximumPercent" + x), "", "Maximum Percent", 10,10, "", "") %></td> 
<%
   }
%>  
   </tr>	   
<%
     }//end of For Loop
   } // end of If Statement  
%>
   <%= HTMLHelpersInput.inputBoxHidden(countFieldName, (blankCount + "")) %>
  </table>   
<%
  }catch(Exception e)
  {}
%>  