<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- updCPGSpecTestProcess.jsp -------------------------------------------//
//
//    Author :  Teri Walton  1/07/09
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
try
{
//**************************************************************************//
 Vector tableInfo = new Vector();
 String tableType = "test";
 UpdSpecification usTP = new UpdSpecification();
 try
 {
	usTP = (UpdSpecification) request.getAttribute("updViewBean");
	tableType = (String) request.getAttribute("tableType");
	if (tableType.equals("test"))
	  tableInfo = usTP.getListAnalyticalTests();
	if (tableType.equals("process"))
	  tableInfo = usTP.getListProcessParameters();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>    
 </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="1" border="1">
  <tr class = "tr02">
   <td class="td04140102" style="text-align:center"><b>Order</b></td>
   <td class="td04140102" style="text-align:center; width=20%"><b>
<%
   if (tableType.equals("test"))
     out.println("Analytical<br>Testing");
   if (tableType.equals("process"))
     out.println("Process<br>Parameter");
%>    
    </b></td>
<%
   if (0 == 1)
   {
%>    
   <td class="td04140102" style="text-align:center"><b>Unit Of<br>Measure</b></td>
<%
   }
   if (tableType.equals("test"))
   {
%>  
   <td class="td04140102" style="text-align:center"><b>Test<br>Parameter<br>Value</b></td>
   <td class="td04140102" style="text-align:center"><b>Test<br>Parameter<br>UOM</b></td>
<%
   }
%>  
   <td class="td04140102" style="text-align:center"><b>Target</b></td>
   <td class="td04140102" style="text-align:center"><b>Minimum</b></td>
   <td class="td04140102" style="text-align:center"><b>Maximum</b></td>
   <td class="td04140102" style="text-align:center; width=20%"><b>Method</b></td>       
  </tr>
<%
 // Display all the Records that are currently in the File
  int lineCount = 0;
  if (tableInfo.size() > 0)
  {
    for (int x = 0; x < tableInfo.size(); x++)
    {
       lineCount++;
       UpdSpecificationTestProcess updLine = new UpdSpecificationTestProcess();
       try
       {
          updLine = (UpdSpecificationTestProcess) tableInfo.elementAt(x);
         
       }
       catch(Exception e)
       {}
       SpecificationTestProcess fileData = new SpecificationTestProcess();
       try
       {
          fileData = updLine.getTestProcessInfo();
       }
       catch(Exception e)
       {}
%>   
     <tr>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "idSequence" + lineCount), updLine.getIdSequence(), "", 5,5,"N", "Y") %>&nbsp;<%= updLine.getIdSequenceError() %></td>
      <td class="td04140102">
<%
   if (tableType.equals("test"))
   {
      out.println(usTP.buildDropDownAnalyticalTest((tableType + "valueID" + lineCount), updLine.getValueID()));
   }
   if (tableType.equals("process"))
   {
      out.println(usTP.buildDropDownProcessParameter((tableType + "valueID" + lineCount), updLine.getValueID()));
   }
%>            
      </td>      
<%
   if (0 == 1)
   {
%>      
      <td class="td04140102"><%= fileData.getTestProcessDescription() %>&nbsp;</td>
      <%= HTMLHelpersInput.inputBoxHidden((tableType + "valueID" + lineCount), updLine.getValueID()) %>
      <td class="td04140102"><%= fileData.getTestProcessUOM() %>&nbsp;</td>
<%
   }
   if (tableType.equals("test"))
   {
%>      
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "testValue" + lineCount), updLine.getTestValue(), "", 7,7,"N", usTP.getReadOnly()) %>&nbsp;<%= updLine.getTestValueError() %></td>
      <td class="td04140102"><%= usTP.buildDropDownTestValueUOM((tableType + "testValueUOM" + lineCount), updLine.getTestValueUOM()) %></td>
<%
   }
%>      
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "target" + lineCount), updLine.getTarget(), "", 8,8,"N", usTP.getReadOnly()) %>&nbsp;<%= updLine.getTargetError() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "minimum" + lineCount), updLine.getMinimum(), "", 8,8,"N", usTP.getReadOnly()) %>&nbsp;<%= updLine.getMinimumError() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "maximum" + lineCount), updLine.getMaximum(), "", 8,8,"N", usTP.getReadOnly()) %>&nbsp;<%= updLine.getMaximumError() %></td> 
      <td class="td04140102"><%= usTP.buildDropDownMethod((tableType + "method" + lineCount), updLine.getMethod()) %></td> 
     </tr>
<%
      }
  }
  int blankCount = 7;
  if (tableType.equals("test"))
    blankCount = 10;
  if (lineCount >= blankCount)
    blankCount = lineCount + 3;
    
   for (int x = lineCount; x < blankCount; x++)
   {
      lineCount++;
%>   
     <tr>
      <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "idSequence" + lineCount), (lineCount + "0"), "", 5,5,"N", usTP.getReadOnly()) %>&nbsp;</td>
      <td class="td04140102">
<%
   if (tableType.equals("test"))
   {
      out.println(usTP.buildDropDownAnalyticalTest((tableType + "valueID" + lineCount), ""));
   }
   if (tableType.equals("process"))
   {
      out.println(usTP.buildDropDownProcessParameter((tableType + "valueID" + lineCount), ""));
   }
%>            
      </td>
<%
   if (tableType.equals("test"))
   {
%>      
      <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "testValue" + lineCount), "0.000", "", 7,7,"N", usTP.getReadOnly()) %>&nbsp;</td>
      <td class="td04140102"><%= usTP.buildDropDownTestValueUOM((tableType + "testValueUOM" + lineCount), "") %></td>
<%
   }
%>      
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "target" + lineCount), "0.0000", "", 8,8,"N", usTP.getReadOnly()) %></td>
      <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "minimum" + lineCount), "0.0000", "", 8,8,"N", usTP.getReadOnly()) %></td>
      <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((tableType + "maximum" + lineCount), "0.0000", "", 8,8,"N", usTP.getReadOnly()) %></td> 
      <td class="td04140102"><%= usTP.buildDropDownMethod((tableType + "method" + lineCount), "") %></td> 
     </tr>
<%
      }
      if (tableType.equals("test"))
       out.println(HTMLHelpersInput.inputBoxHidden("countAnalyticalTests", (lineCount + "")));
      if (tableType.equals("process"))
       out.println(HTMLHelpersInput.inputBoxHidden("countProcessParameters", (lineCount + "")));
%>
     </table>   
   </body>
</html>
<%
   }
   catch(Exception e)
   {}
%>