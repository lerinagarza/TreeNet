<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.SpecificationTestProcess" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanSpecification" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listCPGSpecTestProcessTable.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  10/28/08
//                  Split off of the rest of the Detail Page
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
try
{
//********************************************************************
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 DtlSpecification dsTable = new DtlSpecification();
 Vector getData = new Vector();
 String listType = "";
 try
 {
	dsTable = (DtlSpecification) request.getAttribute("dtlViewBean");
	listType = (String) request.getAttribute("listType");
	if (listType.equals("TEST"))
	{
	   getData = dsTable.getBeanSpec().getListTests(); 
	}
	if (listType.equals("PROCESS"))
	{
	   getData = dsTable.getBeanSpec().getListProcesses();
	}
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
%>
<html>
  <head>
  </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
  // HEADING SECTION - for COLUMNS
%>  
   <tr class="tr02">
    <td class="td04120302" style="text-align:center"><b>
<%
   if (listType.equals("TEST"))
      out.println("Test");
   else
      out.println("Process Parameter");
%>    
     </b>      
    </td>
<%
   if (listType.equals("TEST"))
   {
%>    
    <td class="td04120302" style="text-align:center"><b>Test Parameter</b></td>
<%
   }
%>    
    <td class="td04120302" style="text-align:center"><b>Unit of Measure</b></td>      
    <td class="td04120302" style="text-align:center"><b>Target</b></td>   
    <td class="td04120302" style="text-align:center"><b>Minimum Standard</b></td>   
    <td class="td04120302" style="text-align:center"><b>Maximum Standard</b></td>   
    <td class="td04120102" style="text-align:center"><b>Method</b></td>   
   </tr> 
<%
  // DATA SECTION
  if (getData != null &&
      getData.size() > 0)
  {
    for (int x = 0; x < getData.size(); x++)
    {
      SpecificationTestProcess thisrow = (SpecificationTestProcess) getData.elementAt(x);
%>  
   <tr class="tr00">
    <td class="td04120302"><acronym title="Code: <%= thisrow.getTestProcess() %>">&nbsp;<%= thisrow.getTestProcessDescription() %></acronym></td>
<%
   int decimalPlaces = 1;
   if (listType.equals("TEST"))
   {
      decimalPlaces = 2;
%>    
    <td class="td04120302">&nbsp;
<%
    if (!thisrow.getTestValue().equals("0.000"))
       out.println(thisrow.getTestValue());
%>    
    </td> 
<%
    }
%>    
    <td class="td04120302" style="text-align:center">&nbsp;<%= thisrow.getTestProcessUOM() %></td> 
    <td class="td04120302" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskNumber(thisrow.getTarget(), decimalPlaces) %></td>   
    <td class="td04120302" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskNumber(thisrow.getMinValue(), decimalPlaces) %></td> 
    <td class="td04120302" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskNumber(thisrow.getMaxValue(), decimalPlaces) %></td>  
    <td class="td04120302">&nbsp;
<%
    if (thisrow.getMethod().trim().equals("0.000"))
       out.println("No Method Chosen");
    else
       out.println("M-" + HTMLHelpersLinks.routerMethod(thisrow.getMethod(), "", "a0412", "", "") + "&nbsp;" + thisrow.getMethodName());
%>    
    </td>  
   </tr>   
<%  
     } // end of the for loop
   } // end of the if no records
%>     
  </table>
 </body>
<%
  }
  catch(Exception e)
  {}
%> 
</html>