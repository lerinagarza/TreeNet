<%@ page import="
    java.util.Vector,
    com.treetop.app.quality.BuildProductDataSheet,
    com.treetop.app.quality.BuildProductDataSheetAttributes,
    com.treetop.utilities.html.HTMLHelpersInput,
    com.treetop.utilities.html.HTMLHelpersMasking,
    com.treetop.businessobjects.QaTestParameters
" %>
<%
//---------------- selectproductDatasheetAttributes.jsp -------------------------------------------//
//
//    Author :  Teri Walton  12/19/13
//			To be used included in the selectProductDataSheet.jsp
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//

 BuildProductDataSheet bldPDSAttr = new BuildProductDataSheet();
 Vector listAttributes = new Vector(); 
 String screenType = "";
 String screenTitle = "";
 try
 {
	bldPDSAttr = (BuildProductDataSheet) request.getAttribute("bldViewBean");
	screenType = (String) request.getAttribute("screenType"); 
	
	if (screenType.trim().equals("TEST"))	{
	   listAttributes = bldPDSAttr.getDtlBean().getSpecAnalyticalTests();
	   out.println(HTMLHelpersInput.inputBoxHidden("countAnalyticalTests", (listAttributes.size() + "")));
	   screenTitle = "Analytical Testing";
	}
	if (screenType.trim().equals("MICRO"))	{
	   listAttributes = bldPDSAttr.getDtlBean().getSpecMicroTests();
	   out.println(HTMLHelpersInput.inputBoxHidden("countMicroTests", (listAttributes.size() + "")));
	   screenTitle = "Micro Testing";
	}
	if (screenType.trim().equals("PROC"))	{
	   listAttributes = bldPDSAttr.getDtlBean().getSpecProcessParameters();
	   out.println(HTMLHelpersInput.inputBoxHidden("countProcessParameters", (listAttributes.size() + "")));
	   screenTitle = "Process Parameters";
	}     
   	if (screenType.trim().equals("ADD"))   	{
	   listAttributes = bldPDSAttr.getDtlBean().getSpecAdditiveAndPreserve();
	   out.println(HTMLHelpersInput.inputBoxHidden("countAdditivesAndPreservatives", (listAttributes.size() + "")));
	   screenTitle = "Additive or Preservative";
	}        

 }catch(Exception e)
 {
 
 }
%>

<%  if (!listAttributes.isEmpty()) { %>

<div style="margin:1em 0 2em 0">
<h3><%=screenTitle %></h3>
<table class="styled full-width">
<%//  HEADER ROW %>  
    <tr>
        <th></th>  
        <th></th>   
        <th>Unit of Measure</th> 
        <th>Target</th>
        <th>Minimum</th>
        <th>Maximum</th>
    </tr>    
<%
 // Display the Rows that already have Data in Them
  if (listAttributes.size() > 0)
  {
    for (int lineCnt = 0; lineCnt < listAttributes.size(); lineCnt++)
    {
       try{
            QaTestParameters dtlLine = (QaTestParameters) listAttributes.elementAt(lineCnt);
            
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
    <td class="center" style="width:5%"><%= HTMLHelpersInput.inputCheckBox((screenType + "selectAttribute" + lineCnt), "", "N") %></td>           
    <td><b><%= dtlLine.getAttributeIdentity().trim() %><%= HTMLHelpersInput.inputBoxHidden((screenType + "attributeID" + lineCnt), dtlLine.getAttributeIdentity()) %></b></td> 
    <td><%= dtlLine.getUnitOfMeasure() %></td>
    <td class="center"><%= targetValue %></td>   
    <td class="center"><%= minimumValue %></td>   
    <td class="center"><%= maximumValue %></td>    
   </tr>
<%         
        }catch(Exception e){}
    } // End of the for loop - list of Attribute
  } // End if there are any in the list of attributes    
%>
    </table>
 </div>
 
<%  } // end if isEmpty %>
