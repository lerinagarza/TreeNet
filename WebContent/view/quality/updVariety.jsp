<%@ page import="
	com.treetop.app.quality.UpdFormula,
	com.treetop.app.quality.UpdSpecification,
	com.treetop.app.quality.UpdVariety,
	com.treetop.app.quality.GeneralQuality,
	com.treetop.utilities.html.HTMLHelpersInput,
    java.util.Vector" 
%>
<%
//---------------- updVariety.jsp -------------------------------------------//
//
//    Author :  Teri Walton  3/18/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//   9/11/13	TWalton		Move to view - change to new standards
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector updVariety = new Vector();
 String readOnlyVar = "";
 String screenType = "";
 String countFieldName = "countVarietiesIncluded";
 Vector dropDown = new Vector();
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
     if (updVariety != null &&
         updVariety.size() >= 5)
        blankCount = updVariety.size() + 2;
      
     dropDown = UpdVariety.buildDropDownDualCropVarietyVectors(screenType, blankCount);
    readOnlyVar = (String) request.getAttribute("readOnlySpec");	
    if (readOnlyVar == null)
      readOnlyVar = "N";
    
  }
 catch(Exception e)
 {
   // System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 }   
 for (int x = 0; x < dropDown.size(); x++)
 {
    out.println((String) ((Vector) dropDown.elementAt(x)).elementAt(2));
    out.println((String) ((Vector) dropDown.elementAt(x)).elementAt(3));
 } 
%>
 <div class="row-fluid">
  <table class="styled full-width row-highlight">
<%//  HEADER ROW %>  
   <tr>
    <th style="text-align:center">Remove</td>
    <th>Crop</td>
    <th>Variety</td>
<%
   if (screenType.trim().equals("I"))
   {
%>
    <th>Target Percent</td>    
    <th>Minimum Percent</td>
    <th>Maximum Percent</td>
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
    <td class="center">&#160;
<% 
    if (!dtlLine.getVariety().trim().equals(""))
      out.println(HTMLHelpersInput.inputCheckBox((screenType + "removeEntry" + lineCnt), "", readOnlyVar));    
%>
    &#160;</td>  
    <td class="center">&#160;
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
    <td class="center">&#160;
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
    <td class="center">&#160;
    <%= HTMLHelpersInput.inputBoxText(screenType + "percentage" + lineCnt, dtlLine.getPercentage(), "Target Percent", 10,10, "", readOnlyVar) %>
    &#160;<%= dtlLine.getPercentageError().trim() %>
    </td>   
    <td class="center">&#160;
    <%= HTMLHelpersInput.inputBoxText((screenType + "minimumPercent" + lineCnt), dtlLine.getMinimumPercent(), "Minimum Percent", 10,10, "", readOnlyVar) %>
    &#160;<%= dtlLine.getMinimumPercentError().trim() %>
    </td>   
    <td class="center">&#160;
    <%= HTMLHelpersInput.inputBoxText((screenType + "maximumPercent" + lineCnt), dtlLine.getMaximumPercent(), "Maximum Percent", 10,10, "", readOnlyVar) %>
    &#160;<%= dtlLine.getMaximumPercentError().trim() %>
    </td> 
<%
   }
%>  
   </tr>	   
  
<%
        }catch(Exception e)
        {}
     }//end of For Loop
   } // end of If Statement  

 //************************************************************************
 //***  BLANK SECTION  -- only if in Pending
 //************************************************************************
   if (readOnlyVar.equals("N"))  
   {
   	  for (int x = updVariety.size(); x < blankCount; x++)
   	  {
%>
   <tr> 
    <td class="center">&#160;&#160;</td>  
    <td class="center">&#160;<%= (String) ((Vector) dropDown.elementAt(x)).elementAt(0) %>&#160;</td>  
    <td class="center">&#160;<%= (String) ((Vector) dropDown.elementAt(x)).elementAt(1) %>&#160;</td>      
<%
   		if (screenType.trim().equals("I"))
   		{
%>
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "percentage" + x), "", "Target Percent", 10,10, "", "") %></td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "minimumPercent" + x), "", "Minimum Percent", 10,10, "", "") %></td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "maximumPercent" + x), "", "Maximum Percent", 10,10, "", "") %></td> 
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
 </div>

