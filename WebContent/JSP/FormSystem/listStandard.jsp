<%@ page import = "java.math.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//----------------------listStandard.jsp--------------------------//
//   THIS Jsp to be included in the forms.jsp.
//    This will display the standard Report.
//       It will have:
//				 Edit Buttons and everything that the 
//               Standard Report Should Have
//
//    Author :  Teri Walton      Date: 7/30/2004
//    CHANGES:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//-------------------------------------------------------------------//

//******************************************************************************
//   Receiving in Request Session Variables
//******************************************************************************
// 
   String[] parameterValuesLS  = (String[]) request.getAttribute("parameterValues");

   java.util.Vector formHeadings    = null;
   int              formHeadingSize = 0;
try
{
	formHeadings    = (java.util.Vector) request.getAttribute("formHeadings");
	formHeadingSize = formHeadings.size();
}
catch(Exception e)
{
// Problem was found within the request Session Variables.
}
   java.util.Vector listDefinition     = null;
   int              listDefinitionSize = 0;
   com.treetop.data.FormDefinition headerInfo = new com.treetop.data.FormDefinition();
try
{   
	listDefinition     = (java.util.Vector) request.getAttribute("listDefinition");
	listDefinitionSize = listDefinition.size();
	headerInfo         = (com.treetop.data.FormDefinition) listDefinition.elementAt(0);
}
catch(Exception e)
{
}	
   java.util.Vector dataList     = null;
   int              dataListSize = 0;
try
{   
	dataList     = (java.util.Vector) request.getAttribute("dataList");
	dataListSize = dataList.size();
}
catch(Exception e)
{
}	
   int    imageCountLS   = 0;
   int    sectionCountLS = 0;
try
{
   imageCountLS   = ((Integer) request.getAttribute("imageCount")).intValue();
   sectionCountLS = ((Integer) request.getAttribute("sectionCount")).intValue();
}
catch(Exception e)
{
}
 //*******************************************************************************
 //  Data to search on which is unique to each transaction
 //     1) Transaction Number
 //     2) Transaction Date (Effective)
 //     3) Transaction User
 //     4) Last Update Date
 //     5) Last Update User
 //
   String[] testFieldList = new String[5];
 try
 {
   testFieldList = headerInfo.getShowOnList();
 }
 catch(Exception e)
 {
 }
%>
<html>
  <head>
    <%= JavascriptInfo.getEditButtonOneLine() %>
  </head>
  <body>
<%
try
{
  if (listDefinitionSize > 0)
  {
	   String hrefUrl    = "/web/CtlForms?" +    
                           "requestType=list" +
                           "&formNumber=" + headerInfo.getFormNumber() +
                           parameterValuesLS[1];  
  	   String viewReport = "View Report: " + "<a class=\"a05005\" " +
  	   					   "title=\"Click here to maintain " + 
  	   					   headerInfo.getFormTitle().trim() +
  	   					   ".\" " + 	
  	    				   "href=\"" + hrefUrl +
  	   					   "\" target=\"_blank\">" + headerInfo.getFormTitle() + 
  	   					   "</a>"; 
%> 
  <table class="table01001" cellspacing="0">
    <tr class="tr02001">
      <td>
        <%= JavascriptInfo.getExpandingSection("O", viewReport, 0, sectionCountLS, imageCountLS, 2, 0) %>
        <table class="table01001" cellspacing="0" border="1">
<% 
   imageCountLS++;
   sectionCountLS++;
  //-----------------------------------------------------------
  //REPORT HEADING SECTION
  //-----------------------------------------------------------
   if (formHeadingSize > 0)
   {
	  int saveLineNumber = 0;
			
	  for(int x = 0; x < formHeadingSize; x++)
	  {
	 	 com.treetop.data.FormHeadings thisHeading = (com.treetop.data.FormHeadings) formHeadings.elementAt(x);
		 if(saveLineNumber != thisHeading.getFormLineHdgNumber().intValue())
		 {
			if(saveLineNumber == 0)
			{
%>			
    <tr class="tr04001">
<%
 			}
			else
			{
%>
      <td>&nbsp;</td>
    </tr>
	<tr class="tr04001">
<%					       
			}
			saveLineNumber = thisHeading.getFormLineHdgNumber().intValue();
		}
		int columnSpan = thisHeading.getColumnNumberTo().intValue() -
		                 thisHeading.getColumnNumberFrom().intValue() + 1;
%>
      <td class="td014CC001" colspan="<%= columnSpan %>">
        <b>
  		  <%= thisHeading.getColumnHeading().trim() %>
		</b>
	  </td>
<%				       
	} // END of the FOR Record
	//  put in an if statement if the effective date will display
%>
      <td class="td014CC001">
	  </td>   
<%
  // an Edit Button
%>	         
      <td class="td014CC001">
	  </td>    
    </tr>
<%
  } // END of the Report Heading Section
%>
    <tr class="tr02001">
<%    
   if (testFieldList[0] != null &&
       !testFieldList[0].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction Number
%>      
      <td class="td044CC001">
        <b>
		  Transaction Number
		</b>
	  </td>
<%   
   } 
  //********************************************************
  //  Column Heading section of the form
  //** Build Column Heading Section of Report
   String[] rangeHeadings = new String[listDefinitionSize];
   String rangeHeading = "";   
   int howManyRanges = 0;
  String orderBy = (String) request.getAttribute("orderBy");
  if (orderBy == null)
    orderBy = "";
  String orderStyle = (String) request.getAttribute("orderStyle");   
  if (orderStyle == null)
    orderStyle = "";
  Vector otherForms = new Vector();
  for (int y = 0; y < listDefinitionSize; y++)
  {  
	 com.treetop.data.FormDefinition thisForm = (com.treetop.data.FormDefinition) listDefinition.elementAt(y);
	 String newOrderStyle = "";
	 String arrowLocation = "";
   if (!thisForm.getListOrderView().toString().trim().equals("0"))
   {
	 if (thisForm.getColumnNumber().toString().trim().equals(orderBy))
	 { // This one (Column) is the one we Order By
	    arrowLocation = "<img src=\"https://image.treetop.com/webapp/";
		if (!orderStyle.equals("Desc"))
		{
		   arrowLocation = arrowLocation + "UpArrowGreenTrans.gif\">&nbsp;";
		   newOrderStyle = "Desc";
		}   
	    else
	    {
		   arrowLocation = arrowLocation + "DownArrowGreenTrans.gif\">&nbsp;";
		   newOrderStyle = "";
		}
		   
		imageCountLS++;	           	   
	  }
     //** Column Headings **//
     // new row for each record
     String helpTextLS = thisForm.getHelpTextDef().trim();
     if (helpTextLS == null ||
         helpTextLS.length() == 0)
	   helpTextLS = "There is not any help available this field.";
	   
	 String noSort = (String) request.getAttribute("noSort");  
	 if (noSort == null)
	    noSort = "";
	 if (thisForm.getFormula().trim().equals("") &&
	 	 !noSort.equals("Y"))
	 { // Cannot order by a calculated (Formula) Column
       // heading is a link which will be used to order by if wanted.	
       String linkSendTo = "/web/CtlForms?" +    
                           "requestType=list" +
                           "&formNumber=" + thisForm.getFormNumber() +
                           "&orderBy=" + thisForm.getColumnNumber() +
                           "&orderStyle=" + newOrderStyle +
                           parameterValuesLS[1];
%>
      <td class="td044CC001">
		<acronym title="<%= helpTextLS %>">
		  <b>
			<%= arrowLocation %>
			<a class="a04002" href="<%= linkSendTo %>">
			  <%= thisForm.getHeadingLong().trim() %>
            </a>
		  </b>
		</acronym>
	  </td>
<%	  
	 }
	 else
	 {  //heading is not a link 
%>	 
	  <td class="td044CC001">
        <acronym title="<%= helpTextLS %>">
		  <b>
		    <%= thisForm.getHeadingLong().trim()%>
	      </b>
		</acronym>
	  </td>
<%	  
      }
	  //** Set up the Ranges to Display if needed/wanted
	  
	  //** DATE **//			      
	  if (thisForm.getDataCode().trim().equals("DT"))
	  {
	      String dateRange = com.treetop.servlets.CtlForms.buildStandardDateRange(thisForm);
	      rangeHeadings[y] = dateRange;
	      if (dateRange.trim().length() != 0)
	        rangeHeading = ":Acceptable Ranges";
	  }
	  //** TIME **//
	  if (thisForm.getDataCode().trim().equals("TM"))
	  {
	      String timeRange = com.treetop.servlets.CtlForms.buildStandardTimeRange(thisForm);
	      rangeHeadings[y] = timeRange;
	                              
	      if (timeRange.trim().length() != 0)
	        rangeHeading = ":Acceptable Ranges";
	   } 
	   //** NUMBER **//
	   if (thisForm.getDataCode().trim().equals("NU"))
	   {
	      String numberRange = com.treetop.servlets.CtlForms.buildStandardNumberRange(thisForm);
	      rangeHeadings[y]   = numberRange;
	                              
	      if (numberRange.trim().length() != 0)
	        rangeHeading = ":Acceptable Ranges";
	      if (thisForm.getDataType().trim().equals("transaction"))
	      {
	         String addIt = "Y";
	         for (int extra = 0; extra < otherForms.size(); extra++)
	         {
	         	if (otherForms.elementAt(extra).toString().equals(thisForm.getJoinFormNumber().toString()))
	         	   addIt = "N";
	         }
	         if (addIt.equals("Y"))
	         	otherForms.addElement(thisForm.getJoinFormNumber());
	      }   
	   }
	   //** TEXT
	   if (thisForm.getDataCode().trim().equals("TX"))
	   {
	      String textRange = com.treetop.servlets.CtlForms.buildStandardTextRange(thisForm);
	      rangeHeadings[y] = textRange;
	                              
	      if (textRange.trim().length() != 0)
	        rangeHeading = ":Acceptable Ranges";
	   }
//**** Stick Unit of Measure Stuff in here.
	   String unitOfMeasure = thisForm.getUnitOfMeasure();
	   if (unitOfMeasure == null)
		 unitOfMeasure = ""; 
		 
	   rangeHeadings[y] = rangeHeadings[y] +
			                  "&nbsp;" + unitOfMeasure;
}			                  
    } // END of the FOR LOOP
	
   if (testFieldList[1] != null &&
       !testFieldList[1].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction (Effective) Date
%>      
      <td class="td044CC001">
        <b>
		  Effective Date
		</b>
	  </td>
<%
   }   
   if (testFieldList[2] != null &&
       !testFieldList[2].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction User
%>      
      <td class="td044CC001">
        <b>
		  Transaction User
		</b>
	  </td>
<%
   }   
   if (testFieldList[3] != null &&
       !testFieldList[3].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Last Update Date
%>      
      <td class="td044CC001">
        <b>
		  Last Update Date
		</b>
	  </td>
<%
   }   
   if (testFieldList[4] != null &&
       !testFieldList[4].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Last Update Time
%>      
      <td class="td044CC001">
        <b>
		  Last Update Time
		</b>
	  </td>
<%
   }
%>
      <td class="td044CC001">
		&nbsp;
	  </td>	  
    </tr>
<%		             
    if (!rangeHeading.equals(""))
	{
%>
    <tr class="tr01001">
<%	
	  for (int y = 0; y < listDefinitionSize; y++)
	  {
%>	  
	  <td class="td044CC001">
	    <b>&nbsp;
<%
         if (rangeHeadings[y] != null)
            out.println(rangeHeadings[y]);
%>	    
           &nbsp;
         </b>
       </td>  
<%
      } // end of the for loop
     // Change Colspan if NO effective date.
%>       
	   <td class="td044CL001" colspan="2">
		 <b>
		   <%= rangeHeading %>
		 </b>
	   </td>
	 </tr>
<%	
    } // End of the Range Display
    
  // Build Links for the More Button, to go on EVERY line
  //    Maintain the other Forms.  
	String[] extraUrlLinks = new String[otherForms.size()];
	String[] extraUrlNames = new String[otherForms.size()];
	
	for (int linkNumber = 0; linkNumber < otherForms.size(); linkNumber++)
	{
		extraUrlLinks[linkNumber] = "";
		extraUrlNames[linkNumber] = "";
		try
		{
			FormHeader retrieveName = new FormHeader((Integer) otherForms.elementAt(linkNumber));
			extraUrlLinks[linkNumber] = "/web/CtlForms?requestType=list" +
						  				"&formNumber=" + ((Integer) otherForms.elementAt(linkNumber)).toString();
		    extraUrlNames[linkNumber] = "Maintain " + retrieveName.getFormTitle().trim();
		}
		catch(Exception e)
		{
		}
	}
	
  //-----------------------------------------------------------
  // DETAIL DATA SECTION
  //-----------------------------------------------------------				     
//*********************************
// Detail Data Section of the Form
//  Will need to use both the dataClass and the formClass/Column Class
//  Will Also set up Averages and Totals if applicable by row/column number.
    //Build Array's for totals and averages.
    java.math.BigDecimal[] totals      = new java.math.BigDecimal[listDefinitionSize];
    java.math.BigDecimal[] countTotals = new java.math.BigDecimal[listDefinitionSize];
    for (int x = 0; x < listDefinitionSize; x++)
    {
	    totals[x]      = new java.math.BigDecimal("0");
	    countTotals[x] = new java.math.BigDecimal("0");
    }
    String displayAverages = "";
    String displayTotals   = "";
  	try
	{
		com.treetop.data.FormData listByCell = new com.treetop.data.FormData();
		int transactionCount = 0;
		String saveTransactionNumber = "";
		//** Determine number of seperate transactions/rows.
		for (int x = 0; x < dataListSize; x++)
		{
			listByCell = (com.treetop.data.FormData) dataList.elementAt(x);
			if (!saveTransactionNumber.equals(listByCell.getTranNumber().toString()))
			{
				saveTransactionNumber = listByCell.getTranNumber().toString();
				transactionCount++;
			} 
		}
		// For Loop for how many rows.
		int dataCount = 0;
		for (int x = 0; x < transactionCount; x++)
		{
		   com.treetop.data.FormDefinition thisForm = new com.treetop.data.FormDefinition();
%>		
    <tr class="tr00001">
<%
			int totalCount = 0;
			// For Loop for how many columns.
			for (int y = 0; y < listDefinitionSize; y++)
			{
			  thisForm = (com.treetop.data.FormDefinition) listDefinition.elementAt(y);
   if (!thisForm.getListOrderView().toString().trim().equals("0"))
   {  	 
			  if (thisForm.getShowTotals().trim().equals("Y"))
		  	      displayTotals = thisForm.getShowTotals().trim();
		  	  if (thisForm.getShowAverage().trim().equals("Y"))
		  	      displayAverages = thisForm.getShowAverage().trim();
			// Need to code something into here to deal with Totals/Averages, so forth.
			  if (dataCount < dataListSize)
		 	  {
			  	listByCell = (com.treetop.data.FormData) dataList.elementAt(dataCount);
			  	dataCount++;
			   	String backgroundChange = " class=\"td044CR001\" ";
				if (listByCell.getDataCode().equals("TX"))
				   backgroundChange = " class=\"td044CL001\" ";
							  	
			  	if (!listByCell.getStatusCode().trim().equals("X"))
			  	{
			 	   String outOfRange = com.treetop.servlets.CtlForms.compareToRanges(listByCell);
				   if (outOfRange.trim().equals("YES") &&
					   (listByCell.getDataDefaulted().equals("N") ||
						listByCell.getDataType().trim().equals("Duration")))
				   {
					   backgroundChange = " class=\"td014CR001\" style=\"background-color:#990000\" ";
				      if (listByCell.getDataCode().equals("TX"))
                         backgroundChange = " class=\"td014CL001\" style=\"background-color:#990000\" ";
				   }
			  	}
		
			  	if (y == 0)
			  	   saveTransactionNumber = listByCell.getTranNumber().toString();
				String displayCellValue = "&nbsp;";
				
		 	   if (saveTransactionNumber.equals(listByCell.getTranNumber().toString()) &&
			 	   !listByCell.getStatusCode().trim().equals("X"))
		  	  {
				  //** Date **
				  if (listByCell.getDataCode().equals("DT"))
				  {
				     if(listByCell.getDataDefaulted().trim().equals("N"))
				     displayCellValue = com.treetop.servlets.CtlForms.buildStandardDate(listByCell.getDataDate(),
				     																	listByCell.getDataType());
				  }   
				  //** Time **
				  if (listByCell.getDataCode().equals("TM"))
				  {
					 if (listByCell.getDataType().trim().toLowerCase().equals("duration"))
					   displayCellValue = com.treetop.servlets.CtlForms.buildCustomDurationTime(listByCell);
					 else
					 {
					   if (listByCell.getDataDefaulted().trim().equals("N"))
					        displayCellValue = com.treetop.servlets.CtlForms.buildStandardTime(listByCell.getDataTime(),
					        																   listByCell.getDataType());
					 }       
					 
				 	 if (!displayCellValue.equals("&nbsp;"))
				 	 {
				 	  	int seconds = com.treetop.TimeUtilities.convertSQLTimeToSeconds(listByCell.getDataTime());
				 	  	java.math.BigDecimal BDseconds = new java.math.BigDecimal(seconds);
					  	totals[y] = totals[y].add(BDseconds);
					  	countTotals[y] = countTotals[y].add(new java.math.BigDecimal("1"));
				 	 }
				  }
				  //**Text
				  if (listByCell.getDataCode().equals("TX"))
				  {
					  if (listByCell.getDataDefaulted().trim().equals("N"))
					  {
					    displayCellValue = com.treetop.servlets.CtlForms.buildStandardText(listByCell);
					  }
				  }	
				  //**Number
				  if (listByCell.getDataCode().equals("NU"))
				  {
					 if (listByCell.getDataDefaulted().trim().equals("N"))
					 {
					     displayCellValue = com.treetop.servlets.CtlForms.buildStandardNumber(listByCell);
			         }
				 	 if (!displayCellValue.equals("&nbsp;"))
				 	 {
					     totals[y] = totals[y].add(listByCell.getDataNumeric());
					     countTotals[y] = countTotals[y].add(new java.math.BigDecimal("1"));
				 	 }				 	              
				  }
			  }
   if (testFieldList[0] != null &&
       !testFieldList[0].toLowerCase().equals("n") &&
       y == 0)
   {    
    Integer transNumber = listByCell.getTranNumber();
    BigDecimal sendTrans = new BigDecimal("0");
     //----------------------------------------------------------------------
     //  Transaction Number
%>      
      <td class="td044CC001">
		  <%= com.treetop.servlets.CtlForms.buildStandardNumber(new BigDecimal(listByCell.getTranNumber().toString()), 
						   									    0,
						   									    "transaction",
						   									    listByCell.getFormNumber()) %>
	  </td>
<%
   }    	  	
%>
      <td <%= backgroundChange %>>
        <%= displayCellValue %>
          &nbsp;
      </td>
<%			   
			}
		 	else
		 	{
%>		 	  
			 	  <td class="td044CC001">
			 	      &nbsp;</td>
<%			 	      
		 	}
		 	}
      } // end for viewDefinition 
      
 //*******************************************************************************

   if (testFieldList[1] != null &&
       !testFieldList[1].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction (Effective) Date
%>      
      <td class="td044CC001">
		  <%= com.treetop.servlets.CtlForms.buildStandardDate(listByCell.getTranEffDate(),
		  													  "") %>
	  </td>
<%
   }   
   if (testFieldList[2] != null &&
       !testFieldList[2].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Transaction User
%>      
      <td class="td044CC001">
		  <%= listByCell.getUpdateUser() %>
	  </td>
<%
   }   
   if (testFieldList[3] != null &&
       !testFieldList[3].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Last Update Date
%>      
      <td class="td044CC001">
		  <%= com.treetop.servlets.CtlForms.buildStandardDate(listByCell.getUpdateDate(),
		  													  "") %>
	  </td>
<%
   }   
   if (testFieldList[4] != null &&
       !testFieldList[4].toLowerCase().equals("n"))
   {    
     //----------------------------------------------------------------------
     //  Last Update Time
%>      
      <td class="td044CC001">
		  <%= listByCell.getUpdateTime() %>
	  </td>
<%
   }

 		  	   totalCount++;
	    // BUILD Edit/More Button Section(Column)  
	    String[] urlLinks = new String[(3 + otherForms.size())];
	    String[] urlNames = new String[(3 + otherForms.size())];
	    String[] newPage  = new String[(3 + otherForms.size())];
	    for (int z = 0; z < (3 + otherForms.size()); z++)
	    {
		   urlLinks[z] = "";
		   urlNames[z] = "";
	       newPage[z]  = ""; 	    
	    }
	    String throwAnotherPage = "N";
	    if (request.getAttribute("throwUpdatePage") != null &&
	        !((String) request.getAttribute("throwUpdatePage")).equals(""))
	       throwAnotherPage = (String) request.getAttribute("throwUpdatePage");
        if (listByCell.getSecureUpdate() != null && 
	        listByCell.getSecureUpdate().trim().equals("Y"))
        {
           if (throwAnotherPage.equals("Y"))
           {
		 	   urlLinks[1] = hrefUrl;
		 	   urlNames[1] = "Maintain " + headerInfo.getFormTitle();
		       newPage[1]  = throwAnotherPage; 
           }
	       else
	       {
		 	   urlLinks[0] = "/web/CtlForms?requestType=list" +
		                  "&transactionNumber=" + saveTransactionNumber +
						  "&formNumber=" + thisForm.getFormNumber() +	                  
	        	          "&throwAnotherPage=" + throwAnotherPage +
                	      parameterValuesLS[1];
		 	   urlNames[0] = "Change this Line";
		       newPage[0]  = "N"; 
		       urlLinks[1] = "JavaScript:deleteTrans('/web/CtlForms?requestType=delete" +
		                  "&transactionNumber=" + saveTransactionNumber +
 						  "&formNumber=" + thisForm.getFormNumber() +	                  
            	          parameterValuesLS[1] + "')";
		       urlNames[1] = "Delete this Line";
		       newPage[1]  = "N"; 
		   }
	    }     
	    urlLinks[2] = "/web/CtlForms?requestType=detail" +
	                  "&transactionNumber=" + saveTransactionNumber +
                      "&formNumber=" + thisForm.getFormNumber();
	    urlNames[2] = "Details for this Line";
	    newPage[2]  = "Y";
	    int otherFormCount = 0; 	 
	    for (int z = 3; z < (3 + otherForms.size()); z++)
	    {
		   urlLinks[z] = extraUrlLinks[otherFormCount];
		   urlNames[z] = extraUrlNames[otherFormCount];
	       newPage[z]  = "Y"; 	    
	    }
		    	 
%>
     
      <td class="td044CR001">
        <%= HTMLHelpers.buttonMoreOneLine(urlLinks, urlNames, newPage) %>
      </td>
      
    </tr>
<%    
    } // end of for Rows   
    
    }
    catch(Exception e)
    {
    }   	
    
    
    
    
    
//------------------------------------------------
// Average/Total Section
//  Will need to use both the dataClass and the formClass/Column Class				
   // For Loop for how many columns.
      if (displayAverages.equals("Y"))
      {
%>
    <tr>
<%		    
  		for (int y = 0; y < listDefinitionSize; y++)
		{
		   String     displayCellTotal = "&nbsp;";
	 	   com.treetop.data.FormDefinition thisForm = (com.treetop.data.FormDefinition) listDefinition.elementAt(y);
	 	   
		   java.math.BigDecimal zero             = new java.math.BigDecimal("0");
		   int        testZeroCount    = countTotals[y].compareTo(zero);
	 	   int        testZero         = totals[y].compareTo(zero);
		   
	 	   if (thisForm.getShowAverage().trim().equals("Y") &&
			   testZeroCount > 0)
	   	   {
		   	  java.math.BigDecimal average = new java.math.BigDecimal("0");
		 	  if (testZeroCount > 0 || 
			 	  testZero > 0)
			 	 average = totals[y].divide(countTotals[y],1);
		 	  else
		 	     average = totals[y]; 
		 	  if (thisForm.getDataCode().equals("NU"))
		 	  {
              	 displayCellTotal = com.treetop.servlets.CtlForms.buildStandardNumber(average,
	                                               thisForm.getDecimalPositions().intValue(),
	                                               "",
	                                               new Integer("0"));
	 	       }

	 	   	   if (thisForm.getDataCode().equals("TM"))
	 	       {
				    int timeAverage  = average.intValue();
				    displayCellTotal = com.treetop.TimeUtilities.convertSecondsToTime(timeAverage);

	 	       }
	 	   }
%>
			<td  class="td044CR001">
			  <b>
			    <%= displayCellTotal %>&nbsp;
			  </b>
			</td>    
<%
		}
  //  Change the Colspan depending on whether effective date shows.
%>		
            <td  class="td044CL001" colspan = "2">
              <b>
		        :Averages
		      </b>
		    </td>
		  </tr>
<%		  
	 }
	
	if (displayTotals.equals("Y"))
	{
%>
          <tr>	
<%
  		for (int y = 0; y < listDefinitionSize; y++)
		{
		   String     displayCellTotal = "&nbsp;";
		   
	       com.treetop.data.FormDefinition thisForm = (com.treetop.data.FormDefinition) listDefinition.elementAt(y);       
	 	   if (thisForm.getShowTotals().trim().equals("Y"))
	 	   {
              if (thisForm.getDataCode().equals("NU"))
		 	  {		 	   
                 displayCellTotal = com.treetop.servlets.CtlForms.buildStandardNumber(totals[y], 
					   			     thisForm.getDecimalPositions().intValue(),
					   			     "",
	                                 new Integer("0"));
		 	  }
	 	   	  if (thisForm.getDataCode().equals("TM"))
	 	      {
				 int timeTotal = totals[y].intValue();
				 displayCellTotal = com.treetop.TimeUtilities.convertSecondsToTime(timeTotal);
	 	      }			               
	 	   }    
%>	 	       
		    <td  class="td044CR001">
		      <b>
		        <%= displayCellTotal %>&nbsp;
		      </b>
		    </td>
<%		  	                
		}
  //  Change the Colspan depending on whether effective date shows.		
%>		
            <td  class="td044CL001" colspan = "2">
              <b>
		        :Totals
		      </b>
		    </td>
		  </tr>
<%		       
	}
%>	      
        </table>
     </span>
        
      </td>
    </tr>
  </table>
<%   
   } // Definition has values
   request.setAttribute("imageCount", new Integer(imageCountLS));
   request.setAttribute("sectionCount", new Integer(sectionCountLS));  
   
}
catch(Exception e)
{
	out.println("Problem found within listStandard.jsp: " +e);  
}
%>	  
  </body>
</html>