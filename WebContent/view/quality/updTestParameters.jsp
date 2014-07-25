<%@ page import="
	com.treetop.app.quality.UpdFormula,
	com.treetop.app.quality.UpdSpecification,
	com.treetop.app.quality.UpdTestParameters,
	com.treetop.utilities.html.HTMLHelpersInput,
	com.treetop.utilities.html.DropDownSingle,
    java.util.Vector
" %>
<%
//---------------- updTestParameters.jsp -------------------------------------------//
//
//    Author :  Teri Walton  2/15/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//   9/10/13	TWalton		Move to view - change to new standards
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
     if (lineCount > 3)
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
 <div class="row-fluid">
  <table class="styled full-width row-highlight">
<%//  HEADER ROW %>  
    <tr>
<%
   if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula"))
   {
%>   
    <th class="center">Order</th>
<%
   }
%>        
    <th>
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
    </th>    
<%
   if (testRequestType.trim().equals("updFormula") ||
       testRequestType.trim().equals("copyNewFormula") ||
       testRequestType.trim().equals("reviseFormula"))
   {    
%>   
    <th>Unit of<br>Measure</th>
<%
   }
%>    
    <th>Target</th>
    <th>Minimum</th>
    <th>Maximum</th>
<%
    if (!testRequestType.trim().equals("updFormula") &&
        !testRequestType.trim().equals("copyNewFormula") &&
        !testRequestType.trim().equals("reviseFormula"))
    {
%>    
    <th>Method</th>
<%
    }
%>    
    <th>Default<br>onto COA</th>
   </tr>    
<%
 // Display the Rows that already have Data in Them
  if (lineCount > 0)
  {
    for (int lineCnt = 0; lineCnt < lineCount; lineCnt++)
    {
       try{
          UpdTestParameters dtlLine = (UpdTestParameters) updTestParameter.elementAt(lineCnt);
          
   //************************************************************************
   // Alpha Attributes
   //  10/31/13 TWalton -- no longer using this specific to Alpha stuff
   //             All values will be entered as alpha, that way if they 
   //             are numeric and blank, the blank will show as well 
   //************************************************************************       
//   if (screenType.trim().equals("TEST") && dtlLine.getAttributeID().trim().equals("FLVR"))
//   { 
   // Include the "alpha fields"
   			// This is what Alpha Fields Look like -- review if alpha fields (just flav) 
  
//  <tr>
 //   <td class="center">&#160; HTMLHelpersInput.inputBoxNumber((screenType + "attributeIDSequence" + lineCnt), ((lineCnt + 1) + "0"), "Sequence Number", 3,5, "", readOnlyTest) &#160; dtlLine.getAttributeIDSequenceError().trim() </td>
  //  <td>&#160;&#160;&#160;&#160;&#160;Flavor Score -- <b>This section will need to be REDONE</b> 
   //    HTMLHelpersInput.inputBoxHidden((screenType + "attributeID" + lineCnt), dtlLine.getAttributeID()) 
//    </td>   
 //   <td class="center">&#160; HTMLHelpersInput.inputBoxText((screenType + "targetAlpha" + lineCnt), dtlLine.getTargetAlpha(), "Target", 10,15, "", readOnlyTest) &#160;</td>   
  //  <td class="center">&#160; HTMLHelpersInput.inputBoxText((screenType + "minimumAlpha" + lineCnt), dtlLine.getMinimumAlpha(), "Minimum", 10,15, "", readOnlyTest) &#160;</td>   
//    <td class="center">&#160; HTMLHelpersInput.inputBoxText((screenType + "maximumAlpha" + lineCnt), dtlLine.getMaximumAlpha(), "Maximum", 10,15, "", readOnlyTest) &#160;</td>   
//    <td class="center">&#160; DropDownSingle.buildDropDown(ddMethods, (screenType + "method" + lineCnt), dtlLine.getMethod(), "Choose One", "N", readOnlyTest) </td> 
//    <td class="center">&#160; HTMLHelpersInput.inputCheckBox((screenType + "defaultOnCOA" + lineCnt), dtlLine.getDefaultOnCOA().trim(), readOnlyTest) </td>
//   </tr>	   
//   } else {
   //************************************************************************
   // Numeric Attributes 
   //************************************************************************       
%>
   <tr>
   
    
<%
   if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula")) {
%>     
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "attributeIDSequence" + lineCnt), ((lineCnt + 1) + "0"), "Sequence Number", 3,3, "", readOnlyTest) %>&#160;<%= dtlLine.getAttributeIDSequenceError().trim() %></td>   
    <td class="center">&#160;<%= DropDownSingle.buildDropDown(ddTests, (screenType + "attributeID" + lineCnt), dtlLine.getAttributeID(), "Choose One", "E", readOnlyTest) %><br>
      <%= dtlLine.getUnitOfMeasure() %>
    </td>
    
      
<% } else { %>  
      <td>&#160;&#160;&#160;&#160;&#160;<%= dtlLine.getAttributeID().trim() %>
        <%= HTMLHelpersInput.inputBoxHidden((screenType + "attributeIDSequence" + lineCnt), dtlLine.getAttributeIDSequence()) %>
        <%= HTMLHelpersInput.inputBoxHidden((screenType + "attributeID" + lineCnt), dtlLine.getAttributeID()) %>
      </td>   
      <td class="center">&#160;<%= dtlLine.getUnitOfMeasure() %></td>
        
<% } %>
    
<%  if (dtlLine.getAttributeFieldType().equals("numeric")) { %>    
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "target" + lineCnt), dtlLine.getTarget(), "Target", 10,15, "", readOnlyTest) %>&#160;<%= dtlLine.getTargetError().trim() %></td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "minimum" + lineCnt), dtlLine.getMinimum(), "Minimum", 10,15, "", readOnlyTest) %>&#160;<%= dtlLine.getMinimumError().trim() %></td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "maximum" + lineCnt), dtlLine.getMaximum(), "Maximum", 10,15, "", readOnlyTest) %>&#160;<%= dtlLine.getMaximumError().trim() %></td>
<%  } else { %>
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "target" + lineCnt), dtlLine.getTarget(), "Target", 10,15, "", readOnlyTest) %>&#160;<%= dtlLine.getTargetError().trim() %></td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "minimum" + lineCnt), dtlLine.getMinimum(), "Minimum", 10,15, "", readOnlyTest) %>&#160;<%= dtlLine.getMinimumError().trim() %></td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "maximum" + lineCnt), dtlLine.getMaximum(), "Maximum", 10,15, "", readOnlyTest) %>&#160;<%= dtlLine.getMaximumError().trim() %></td>
<%  } %>


      
<% if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula")) {  %>     
    <td class="center">&#160;<%= DropDownSingle.buildDropDown(ddMethods, (screenType + "method" + lineCnt), dtlLine.getMethod(), "Choose One", "N", readOnlyTest) %></td> 
    
<% } %>

    <td class="center">&#160;<%= HTMLHelpersInput.inputCheckBox((screenType + "defaultOnCOA" + lineCnt), dtlLine.getDefaultOnCOA().trim(), readOnlyTest) %></td>
    
    
   </tr>	   
<%
 
        } catch(Exception e) {}
        
     }//end of For Loop
   } // end of If Statement
   
 //************************************************************************
 //***  BLANK SECTION  -- only if Status is PENDING
 //************************************************************************
   if (readOnlyTest.equals("N"))  
   {
   	  for (int x = lineCount; x < blankCount; x++)
   	  {
%>
   <tr> 
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxNumber((screenType + "attributeIDSequence" + x), ((x + 1) + "0"), "Sequence Number", 3,5, "", readOnlyTest) %>&#160;</td>   
    <td class="center">&#160;<%= DropDownSingle.buildDropDown(ddTests, (screenType + "attributeID" + x), "", "Choose One", "E", readOnlyTest) %></td>

    <%--Attribute field type is not known, assume alpha to capture all inputs --%>
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "target" + x), "", "Target", 10,15, "", readOnlyTest) %>&#160;</td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "minimum" + x), "", "Minimum", 10,15, "", readOnlyTest) %>&#160;</td>   
    <td class="center">&#160;<%= HTMLHelpersInput.inputBoxText((screenType + "maximum" + x), "", "Maximum", 10,15, "", readOnlyTest) %>&#160;</td>

<%
   if (!testRequestType.trim().equals("updFormula") &&
       !testRequestType.trim().equals("copyNewFormula") &&
       !testRequestType.trim().equals("reviseFormula"))
   {
%>     
    <td class="center">&#160;<%= DropDownSingle.buildDropDown(ddMethods, (screenType + "method" + x), "", "Choose One", "N", readOnlyTest) %></td> 
    
<%
   }
%>
    <td class="center">&#160;<%= HTMLHelpersInput.inputCheckBox((screenType + "defaultOnCOA" + x), "", readOnlyTest) %></td>
   </tr>	   
<%
      }//end of For Loop
   } // end of If Statement  

   if (screenType.trim().equals("TEST"))
     out.println(HTMLHelpersInput.inputBoxHidden("countAnalyticalTests", (blankCount + "")));
   if (screenType.trim().equals("MICRO"))
     out.println(HTMLHelpersInput.inputBoxHidden("countMicroTests", (blankCount + "")));
   if (screenType.trim().equals("PROC"))
     out.println(HTMLHelpersInput.inputBoxHidden("countProcessParameters", (blankCount + "")));     
   if (screenType.trim().equals("ADD"))
     out.println(HTMLHelpersInput.inputBoxHidden("countAdditivesAndPreservatives", (blankCount + "")));     
%>
    </table>
 </div>

