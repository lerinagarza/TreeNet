<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlTestParameters.jsp -----------------------------------//
//
//    Author :  Teri Walton  2/23/11
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//   11/6/13   TWalton       Revamp for Spec's to allow Alpha fields 
//--------------------------------------------------------------------------//
 Vector dtlList = new Vector();
 String screenType = "";
 String testRequestType = "";
 String environ = "";
 String showCOA = "";
 int columnCount = 5;
 try
 {
    testRequestType = (String) request.getAttribute("requestType");
    screenType = (String) request.getAttribute("screenType");
    //------------------------------------------------------------------------------//
    //  FORMULA
    //------------------------------------------------------------------------------//
     if (testRequestType.trim().equals("dtlFormula"))
     {
         DtlFormula dtlMain = (DtlFormula) request.getAttribute("dtlViewBean");
		 dtlList = dtlMain.getDtlBean().getFormulaRawFruitTests();         
     }
    //-----------------------------------------------------------------------------//
    //  SPECIFICATION
	//-----------------------------------------------------------------------------//
	if (testRequestType.trim().equals("dtlSpec"))
	{
	    DtlSpecification dtlMain = (DtlSpecification) request.getAttribute("dtlViewBean");
	    environ = dtlMain.getEnvironment().trim();
	    if (screenType.trim().equals("TEST"))
	    {
	       dtlList = dtlMain.getDtlBean().getSpecAnalyticalTests();
	    }
	    if (screenType.trim().equals("PROC"))
	    {
	       dtlList = dtlMain.getDtlBean().getSpecProcessParameters();
	    }
	    if (screenType.trim().equals("MICRO"))
	    {
	       dtlList = dtlMain.getDtlBean().getSpecMicroTests();
	    }
	    if (screenType.trim().equals("ADD"))
	    {
	       dtlList = dtlMain.getDtlBean().getSpecAdditiveAndPreserve();
	    }
	    // Test to see if there is any COA information to print
	    if (!dtlList.isEmpty())
	    {
	       for (int x = 0; x < dtlList.size(); x++)
	       {
	    	  if (!((QaTestParameters) dtlList.elementAt(x)).getDefaultOnCOA().trim().equals(""))
	    	  {
	    	     showCOA = "Y";
	    	     x = dtlList.size();
	    	     columnCount++;
	    	  }  
	       }	
	    }
    }
  }
  catch(Exception e)
  {
    //System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 	//request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
  }     

%>
<html>
 <head>
 </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" border="1" >
   <tr class="tr02">
   
     <td class="td04120102" style="text-align:center; width:15%">
<%
   if (testRequestType.trim().equals("dtlFormula"))
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
       out.println("Additives and Preservatives");       
   }
%>    
    </td>
    <td class="td04120102" style="text-align:center; width:12%">Unit of Measure</td>
    <td class="td04120102" style="text-align:center">Target</td>
    <td class="td04120102" style="text-align:center">Minimum</td>
    <td class="td04120102" style="text-align:center">Maximum</td>
<%
   if (!testRequestType.trim().equals("dtlFormula"))
   {
     columnCount++;
%>    
    <td class="td04120102" style="text-align:center; width:15%">Method</td>
<%
   }
   if (!showCOA.trim().equals(""))
   {
%>   
    <td class="td04120102" style="text-align:center; width:5%">On<br>COA</td>   
<%
   }
%>    
   </tr>
<%
  if (dtlList.size() > 0)
  {
    for (int lineCnt = 0; lineCnt < dtlList.size(); lineCnt++)
    {
       try{
          QaTestParameters dtlLine = (QaTestParameters) dtlList.elementAt(lineCnt);
          if (dtlLine.getAttributeIdentity().trim().equals("Water Addition"))
			 dtlLine.setUnitOfMeasure("GALLONS");
		  if (dtlLine.getAttributeIdentity().trim().equals("Decay"))
			 dtlLine.setUnitOfMeasure("PERCENT");
//  11/6/13 TWalton - until Formula Needs to change -- addition of Alpha values, will separate Formula and Spec Screens
//     This entire Formula Section could be removed once the Alpha Values are added to Formula   			 
		  if (testRequestType.trim().equals("dtlFormula")){
		    if (!dtlLine.getTargetValue().trim().equals("0.000000") ||
                !dtlLine.getMinimumStandard().trim().equals("0.000000") ||
                !dtlLine.getMaximumStandard().trim().equals("0.000000")){
              if (dtlLine.getDecimalPlaces().trim().equals(""))
	            dtlLine.setDecimalPlaces("0");
%>           
   <tr> 
    <td class="td04120102">&#160;<%= dtlLine.getAttributeIdentity() %>&#160;</td>
    <td class="td04120102" style="text-align:center">&#160;<%= dtlLine.getUnitOfMeasure() %></td> 
    <td class="td04120102" style="text-align:center">&#160;<%= HTMLHelpersMasking.maskBigDecimal(dtlLine.getTargetValue(),new Integer(dtlLine.getDecimalPlaces()).intValue()) %></td>   
    <td class="td04120102" style="text-align:center">&#160;<%= HTMLHelpersMasking.maskBigDecimal(dtlLine.getMinimumStandard(),new Integer(dtlLine.getDecimalPlaces()).intValue()) %></td>   
    <td class="td04120102" style="text-align:center">&#160;<%= HTMLHelpersMasking.maskBigDecimal(dtlLine.getMaximumStandard(),new Integer(dtlLine.getDecimalPlaces()).intValue()) %></td> 
<%
   if (!showCOA.trim().equals(""))
   {
%>        
    <td class="td04120102">&#160;
<%   
      if (!dtlLine.getDefaultOnCOA().trim().equals(""))
        out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
%>      
    </td>
<%
   }
%>    
    </tr>             
<%
 	  	    } // end of the if Zero - should this specific line display for Formulas
		  }else{
		     // Would show for the Spec Screen
		     
		    // will need to evaluate if a row should be shown. 
		    String targetValue = "";
		    String minimumValue = "";
		    String maximumValue = "";
		    	// if Value is Numeric
		    if (dtlLine.getAttributeType().trim().equals("2"))
		    { 
		      // If this attribute is numeric must test for Zero, and mask the value for the screen
		       try{// Separate and test each Value
		          if (!dtlLine.getTargetValue().trim().equals(""))
		             targetValue = HTMLHelpersMasking.maskBigDecimal(dtlLine.getTargetValue(),new Integer(dtlLine.getDecimalPlaces()).intValue());
				  if (!dtlLine.getMinimumStandard().trim().equals(""))
				     minimumValue = HTMLHelpersMasking.maskBigDecimal(dtlLine.getMinimumStandard(),new Integer(dtlLine.getDecimalPlaces()).intValue());	
		          if (!dtlLine.getMaximumStandard().trim().equals(""))
				     maximumValue = HTMLHelpersMasking.maskBigDecimal(dtlLine.getMaximumStandard(),new Integer(dtlLine.getDecimalPlaces()).intValue());
		       } catch (Exception e){}
		    }else{
		      // If this attribute is Alpha, can just push out to the screen
		        targetValue = dtlLine.getTargetValue().trim();
		        minimumValue = dtlLine.getMinimumStandard().trim();
		        maximumValue = dtlLine.getMaximumStandard().trim();
		    }	
%>
   <tr>
    <td class="td04120102">&#160;<%= dtlLine.getAttributeDescription() %></td>  
    <td class="td04120102" style="text-align:center">&#160;<%= dtlLine.getUnitOfMeasure().trim() %></td> 
    <td class="td04120102" style="text-align:center">&#160;<%= targetValue %></td>   
    <td class="td04120102" style="text-align:center">&#160;<%= minimumValue %></td>   
    <td class="td04120102" style="text-align:center">&#160;<%= maximumValue %></td> 
    <td class="td04120102">&#160;
<%
      if (dtlLine.getMethodNumber().trim().equals("0"))
         out.println("No Method Chosen");
      else
         out.println(DtlSpecification.methodLink(environ,dtlLine.getMethodNumber(), dtlLine.getMethodName(), dtlLine.getMethodRevDate(), dtlLine.getMethodRevTime()) + "--" + dtlLine.getMethodDescription());
%>    
    </td> 		  
<%
  // Column for Checkbox of defaulting on COA or Not
   if (!showCOA.trim().equals(""))
   {
%>    

    <td class="td04120102">&#160;
<%   
      if (!dtlLine.getDefaultOnCOA().trim().equals(""))
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
%>      
    </td>
<%
   }
%>    	  
   </tr>
<%		  
          } // end of Else if DtlFormula
        }catch(Exception e)
        {}
     }//end of For Loop
   } // end of If Statement  
%>
   <tr> 
    <td class="td04121405" colspan="<%= columnCount %>">&#160;</td>  
   </tr> 
  </table>   
 </body>
</html>