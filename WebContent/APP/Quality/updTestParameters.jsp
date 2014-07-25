<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updTestParameters.jsp -------------------------------------------//
//
//    Author :  Teri Walton  2/15/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector updTestParameter = new Vector();
 Vector ddTests  = new Vector();
 Vector ddMethods = new Vector();
 String testRequestType = "";
 String readOnlyTest = "";
 String screenType = "";
 int lineCount = 0;
 int blankCount = 5;
 try
 {
    testRequestType = (String) request.getAttribute("requestType");
    screenType = (String) request.getAttribute("screenType");
    //------------------------------------------------------------------------------//
    //  FORMULA -- will be Hardcoded Attributes (Currently 4 Attributes)
    //------------------------------------------------------------------------------//
     if (testRequestType.trim().equals("updFormula") ||
         testRequestType.trim().equals("copyNewFormula") ||
         testRequestType.trim().equals("reviseFormula"))
     {
       // Fill in all the variable fields for this included screen  
          UpdFormula updMain = (UpdFormula) request.getAttribute("updViewBean");
          updTestParameter = updMain.getListRawFruitAttributes();
          lineCount = 4;
     }
     if (testRequestType.trim().equals("updSpec") ||
         testRequestType.trim().equals("copyNewSpec") ||
         testRequestType.trim().equals("reviseSpec"))
     {
     	UpdSpecification updMain = (UpdSpecification) request.getAttribute("updViewBean");
		if (screenType.trim().equals("TEST"))
		{
			updTestParameter = updMain.getListAnalyticalTests();
	   		ddTests = updMain.getDdAnalyticalTest();
	   	}
		if (screenType.trim().equals("MICRO"))
		{
		   updTestParameter = updMain.getListMicroTests();
		   ddTests = updMain.getDdMicroTest();
		}
		if (screenType.trim().equals("PROC"))
		{
	   	   updTestParameter = updMain.getListProcessParameters();
	   	   ddTests = updMain.getDdProcessParameter();
	   	}  
	   	if (screenType.trim().equals("ADD"))
		{
	   	   updTestParameter = updMain.getListAdditivesAndPreservatives();
	   	   ddTests = updMain.getDdAdditivesPreservatives();
	   	}    
	   	ddMethods =  updMain.getDdMethod();
	    lineCount = updTestParameter.size();	   
     } //  end of the if SPECIFICATION Section
     if (lineCount >= 6)
        blankCount = lineCount + 2;	
	 readOnlyTest = (String) request.getAttribute("readOnlySpec");	
	 if (readOnlyTest == null)
	    readOnlyTest = "N";
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

%>

  <table class="table00" cellspacing="0" style="width:100%">
   <tr class="tr02">
<%
   if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula"))
   {
%>   
    <td class="td04140102" style="text-align:center; width:10%">Order</td>
<%
   }
%>    
    <td class="td04140102" style="text-align:center; width:15%">
<%
   if (testRequestType.trim().equals("updFormula") ||
       testRequestType.trim().equals("copyNewFormula") ||
       testRequestType.trim().equals("reviseFormula"))
   {
      out.println("Attribute");
   }else{
     if (screenType.trim().equals("TEST"))
       out.println("Analytical Testing");
     if (screenType.trim().equals("MICRO"))
       out.println("Micro Testing");
     if (screenType.trim().equals("PROC"))
       out.println("Process Parameters");  
    if (screenType.trim().equals("ADD"))
       out.println("Additive or Preservative");           
   }
%>    
    </td>
<%
   if (testRequestType.trim().equals("updFormula") ||
       testRequestType.trim().equals("copyNewFormula") ||
       testRequestType.trim().equals("reviseFormula"))
   {    
%>   
    <td class="td04140102" style="text-align:center; width:12%">Unit of<br>Measure</td>
<%
   }
%>    
    <td class="td04140102" style="text-align:center; width:10%">Target</td>
    <td class="td04140102" style="text-align:center; width:10%">Minimum</td>
    <td class="td04140102" style="text-align:center; width:10%">Maximum</td>
<%
    if (!testRequestType.trim().equals("updFormula") &&
        !testRequestType.trim().equals("copyNewFormula") &&
        !testRequestType.trim().equals("reviseFormula"))
    {
%>    
    <td class="td04140102" style="text-align:center; width:15%">Method</td>
<%
    }
%>    
    <td class="td04140102" style="text-align:center; width:7%">Default</br>onto COA</td>
   </tr>
<%
  if (lineCount > 0)
  {
    for (int lineCnt = 0; lineCnt < lineCount; lineCnt++)
    {
       try{
          UpdTestParameters dtlLine = (UpdTestParameters) updTestParameter.elementAt(lineCnt);
   if (screenType.trim().equals("TEST") && dtlLine.getAttributeID().trim().equals("FLVR"))
   { // Include the "alpha fields"
%>  
  <tr>
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "attributeIDSequence" + lineCnt), ((lineCnt + 1) + "0"), "Sequence Number", 5,5, "", readOnlyTest) %>&#160;<%= dtlLine.getAttributeIDSequenceError().trim() %></td>
    <td class="td04140102">&#160;&#160;&#160;&#160;&#160;Flavor Score 
      <%= HTMLHelpersInput.inputBoxHidden((screenType + "attributeID" + lineCnt), dtlLine.getAttributeID()) %>
    </td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "targetAlpha" + lineCnt), dtlLine.getTarget(), "Target", 15,15, "", readOnlyTest) %>&#160;</td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "minimumAlpha" + lineCnt), dtlLine.getMinimum(), "Minimum", 15,15, "", readOnlyTest) %>&#160;</td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "maximumAlpha" + lineCnt), dtlLine.getMaximum(), "Maximum", 15,15, "", readOnlyTest) %>&#160;</td>   
    <td class="td03140102" style="text-align:center">&#160;<%= DropDownSingle.buildDropDown(ddMethods, (screenType + "method" + lineCnt), dtlLine.getMethod(), "Choose One", "N", readOnlyTest) %></td> 
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputCheckBox((screenType + "defaultOnCOA" + lineCnt), dtlLine.getDefaultOnCOA().trim(), readOnlyTest) %></td>
   </tr>	   
<%
   }else{
%>
   <tr> 
<%
   if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula"))
   {
%>     
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "attributeIDSequence" + lineCnt), ((lineCnt + 1) + "0"), "Sequence Number", 5,5, "", readOnlyTest) %>&#160;<%= dtlLine.getAttributeIDSequenceError().trim() %></td>   
    <td class="td04140102" style="text-align:center">&#160;<%= DropDownSingle.buildDropDown(ddTests, (screenType + "attributeID" + lineCnt), dtlLine.getAttributeID(), "Choose One", "E", readOnlyTest) %><br>
      <%= dtlLine.getUnitOfMeasure() %>
    </td>  
<%
   }else{
%>  
      <td class="td04140102">&#160;&#160;&#160;&#160;&#160;<%= dtlLine.getAttributeID().trim() %>
        <%= HTMLHelpersInput.inputBoxHidden((screenType + "attributeIDSequence" + lineCnt), dtlLine.getAttributeIDSequence()) %>
        <%= HTMLHelpersInput.inputBoxHidden((screenType + "attributeID" + lineCnt), dtlLine.getAttributeID()) %>
      </td>   
      <td class="td04140102" style="text-align:center">&#160;<%= dtlLine.getUnitOfMeasure() %></td>  
<%
      
   }
%>
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "target" + lineCnt), dtlLine.getTarget(), "Target", 15,15, "", readOnlyTest) %>&#160;<%= dtlLine.getTargetError().trim() %></td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "minimum" + lineCnt), dtlLine.getMinimum(), "Minimum", 15,15, "", readOnlyTest) %>&#160;<%= dtlLine.getMinimumError().trim() %></td>   
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "maximum" + lineCnt), dtlLine.getMaximum(), "Maximum", 15,15, "", readOnlyTest) %>&#160;<%= dtlLine.getMaximumError().trim() %></td>   
<%
   if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula"))
   {
%>     
    <td class="td03140102" style="text-align:center">&#160;<%= DropDownSingle.buildDropDown(ddMethods, (screenType + "method" + lineCnt), dtlLine.getMethod(), "Choose One", "N", readOnlyTest) %></td> 
<%
   }
%>
    <td class="td03140102" style="text-align:center">&#160;<%= HTMLHelpersInput.inputCheckBox((screenType + "defaultOnCOA" + lineCnt), dtlLine.getDefaultOnCOA().trim(), readOnlyTest) %></td>
   </tr>	   
<%
     }
        }catch(Exception e)
        {}
     }//end of For Loop
   } // end of If Statement  
   if (screenType.trim().equals("TEST"))
     out.println(HTMLHelpersInput.inputBoxHidden("countAnalyticalTests", (lineCount + "")));
   if (screenType.trim().equals("MICRO"))
     out.println(HTMLHelpersInput.inputBoxHidden("countMicroTests", (lineCount + "")));
   if (screenType.trim().equals("PROC"))
     out.println(HTMLHelpersInput.inputBoxHidden("countProcessParameters", (lineCount + "")));     
   if (screenType.trim().equals("ADD"))
     out.println(HTMLHelpersInput.inputBoxHidden("countAdditivesAndPreservatives", (lineCount + "")));     
%>
   <%= HTMLHelpersInput.inputBoxHidden("countRawFruitAttributes", (lineCount + "")) %>
  </table>   
