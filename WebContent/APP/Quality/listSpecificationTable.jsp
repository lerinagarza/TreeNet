<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.businessobjects.QaSpecification" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listSpecificationTable.jsp  ------------------------------------------//
//  Directly included in the listSpecification.jsp
// 
//    Author :  Teri Walton  10/25/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqSpecification inqSpecificationTable = new InqSpecification();
 Vector getData = new Vector();
 String thisEnv = "PRD";
 try
 {
	inqSpecificationTable = (InqSpecification) request.getAttribute("inqViewBean");
//	BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
    if (inqSpecificationTable == null)
    {
       DtlSpecification dtlSpecificationTable = (DtlSpecification) request.getAttribute("dtlViewBean");
       getData = dtlSpecificationTable.getDtlBean().getRevReasonSpecification();
       thisEnv = dtlSpecificationTable.getEnvironment();
    }
    else
    {
	   getData = inqSpecificationTable.getListReport();
	   thisEnv = inqSpecificationTable.getEnvironment();
	}
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayView = (String) request.getAttribute("displayView");
 if (displayView == null)
   displayView = "";

   String columnHeadingTo = "";
   if (displayView.trim().equals(""))
       columnHeadingTo = "/web/CtlQuality?requestType=listSpec&" +
       						"&environment=" + thisEnv + 
                            inqSpecificationTable.buildParameterResend();
   String[] sortImage = new String[6];
   String[] sortStyle = new String[6];
   String[] sortOrder = new String[6];
   sortOrder[0] = "specificationName";
   sortOrder[1] = "specificationDescription";
   sortOrder[2] = "revisionDate";
   sortOrder[3] = "status";
   sortOrder[4] = "itemNumber";
   sortOrder[5] = "specificationType";
  //************
  //Set Defaults
   for (int x = 0; x < 6; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
  if (displayView.trim().equals(""))
  {
   String orderBy = inqSpecificationTable.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "SpecificationNumber";
   for (int x = 0; x < 6; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (inqSpecificationTable.getOrderStyle().trim().equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowUpDark.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowDownDark.gif\">";
     }
   }   
   }
   int imageCount = 3;
   int expandCount = 0;
%>

<html>
  <head>
     <%= JavascriptInfo.getMoreButton() %>
     <%= JavascriptInfo.getExpandingSectionHead("", 0, "Y", 50) %>   
  </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
  //----------------------------------------------------------------------------
  // HEADING SECTION
%>  
   <tr class="tr02">
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>    
     <%= sortImage[5] %>
     <a class="a0412" title="Specification Type" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
<%
  }    
%>     
      <b>Spec Type</b>
     </a>      
    </td>
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>    
     <%= sortImage[0] %>
     <a class="a0412" title="Specification Number" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
<%
  }    
%>     
      <b>Specification Number</b>
     </a>      
    </td>
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[1] %>
     <a class="a0412" title="Description of the Specification" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
<%
  }    
%>      
      <b>Description</b>
     </a>      
    </td>      
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[4] %>
     <a class="a0412" title="Item Number Tied to this Specification" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
<%
  }    
%>      
      <b>Item</b>
     </a>      
    </td>   
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[2] %>
     <a class="a0412" title="Date and Time the Specification was Revised" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
<%
  }    
%>      
      <b>Revision<br>Date and Time</b>
     </a>      
    </td>      
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>     
     <%= sortImage[3] %>
     <a class="a0412" title="Status of the Specification" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
<%
  }    
%>      
      <b>Specification<br>Status</b>
     </a>      
    </td>   
<%
  if (displayView.trim().equals(""))
  {
%>       
    <td class="td04120605" style="width:6%">&#160;</td> 
<%
  }else{    
    if (displayView.trim().equals("detail"))
    {
%> 
    <td class="td04120605" style="text-align:center"><b>Revision Reason</b></td>   
<%
    }
  }
%>
   </tr> 
 <%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------
  // DATA SECTION
  int dataCount = 0;
  if (getData.size() > 0)
  { // IF there are LOAD Records
    for (int x = 0; x < getData.size(); x++)
    {
      dataCount++;
      QaSpecification thisrow = (QaSpecification) getData.elementAt(x);
      String SpecificationURL = "/web/CtlQuality?requestType=dtlSpec&specNumber=" + thisrow.getSpecificationNumber() +
                       "&revisionDate=" + thisrow.getRevisionDate() +
                       "&revisionTime=" + thisrow.getRevisionTime() +
                       "&environment=" + thisEnv;
       String SpecificationMouseover = "Click here to see the details of this Specification";
       
       
%>     
   <tr class="tr00">
    <td class="td04121524">&#160;<%= thisrow.getTypeDescription() %></td>
    <td class="td04121524" style="text-align:center">&#160;<%=HTMLHelpersLinks.basicLink(thisrow.getSpecificationName(), SpecificationURL, SpecificationMouseover,"" , "") %>&#160;</td>
    <td class="td04121524">&#160;<%= thisrow.getSpecificationDescription() %></td>
    <td class="td04121524">&#160;<%= thisrow.getItemNumber() %>&#160;&#160;<%= thisrow.getItemDescription() %></td>
    <td class="td04121524" style="text-align:center"><abbr title="Last Date and Time this Specification was Revised">&#160;
    <%= GeneralQuality.formatDateForScreen(thisrow.getRevisionDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(thisrow.getRevisionTime()) %>
    </abbr></td>   
    <td class="td04121524" style="text-align:center">&#160;<%= thisrow.getStatusDescription() %></td> 
    
<%
  if (displayView.trim().equals(""))
  {
  // --- Build Vector for the MORE Button
  Vector sendParms = new Vector();
  sendParms.add(inqSpecificationTable.getRequestType());
  sendParms.add(inqSpecificationTable.getSecurityLevel());
  sendParms.add(inqSpecificationTable.getEnvironment());
  sendParms.add(thisrow.getSpecificationNumber());
  sendParms.add(thisrow.getRevisionDate());
  sendParms.add(thisrow.getRevisionTime());
%>    
   <td class="td04121524" style="text-align:right">
     <%= InqSpecification.buildMoreButton(sendParms) %>
   </td>
<%

  }else{
%>  
   <td class="td04121524">
    <%= thisrow.getRevisionReasonText().trim() %>&#160;
   </td>
<%   
  }
%>
   </tr>   
<%
     } // end of the for loop
   } // end of the if no load records chosen
%>     
  </table>
 </body>
</html>