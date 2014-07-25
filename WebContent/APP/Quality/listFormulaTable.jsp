<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.businessobjects.QaFormula" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listFormulaTable.jsp  ------------------------------------------//
//  Directly included in the listFormula.jsp
// 
//    Author :  Teri Walton  6/29/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqFormula inqFormulaTable = new InqFormula();
 Vector getData = new Vector();
 String thisEnv = "PRD";
 try
 {
	inqFormulaTable = (InqFormula) request.getAttribute("inqViewBean");
//	BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
    if (inqFormulaTable == null)
    {
       DtlFormula dtlFormulaTable = (DtlFormula) request.getAttribute("dtlViewBean");
       getData = dtlFormulaTable.getDtlBean().getRevReasonFormula();
       thisEnv = dtlFormulaTable.getEnvironment();
    }
    else
    {
	   getData = inqFormulaTable.getListReport();
	   thisEnv = inqFormulaTable.getEnvironment();
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
       columnHeadingTo = "/web/CtlQuality?requestType=listFormula&" +
                            "&environment=" + thisEnv + 
                            inqFormulaTable.buildParameterResend();
   String[] sortImage = new String[6];
   String[] sortStyle = new String[6];
   String[] sortOrder = new String[6];
   sortOrder[0] = "formulaNumber";
   sortOrder[1] = "formulaDescription";
   sortOrder[2] = "revisionDate";
   sortOrder[3] = "status";
   sortOrder[4] = "itemNumber";
   sortOrder[5] = "formulaType";
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
   String orderBy = inqFormulaTable.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "formulaNumber";
   for (int x = 0; x < 6; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (inqFormulaTable.getOrderStyle().trim().equals(""))
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
     <a class="a0412" title="Formula Type" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
<%
  }    
%>     
      <b>Type</b>
     </a>      
    </td>
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>    
     <%= sortImage[0] %>
     <a class="a0412" title="Formula Number" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
<%
  }    
%>     
      <b>Formula Number</b>
     </a>      
    </td>
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[1] %>
     <a class="a0412" title="Description of the Formula" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
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
     <a class="a0412" title="Item Assigned to the Formula" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
<%
  }    
%>      
      <b>Item Number</b>
     </a>      
    </td>          
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[2] %>
     <a class="a0412" title="Sort by Revision Date and Time" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
<%
  }    
%>      
      <b>Code and<br>Revision<br>Date and Time</b>
     </a>      
    </td>      
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>     
     <%= sortImage[3] %>
     <a class="a0412" title="Status of the Formula" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
<%
  }    
%>      
      <b>Formula<br>Status</b>
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
      QaFormula thisrow = (QaFormula) getData.elementAt(x);
      String formulaURL = "/web/CtlQuality?requestType=dtlFormula&formulaNumber=" + thisrow.getFormulaNumber() +
                       "&revisionDate=" + thisrow.getRevisionDate() +
                       "&revisionTime=" + thisrow.getRevisionTime() +
                       "&environment=" + thisEnv;
       String formulaMouseover = "Click here to see the details of this Formula";
%>     
   <tr class="tr00">
    <td class="td04121524">&#160;<%= thisrow.getTypeDescription() %>&#160;</td>
    <td class="td04121524" style="text-align:center">&#160;<%=HTMLHelpersLinks.basicLink(thisrow.getFormulaName(), formulaURL, formulaMouseover,"" , "") %>&#160;</td>
    <td class="td04121524">&#160;<%= thisrow.getFormulaDescription() %>&#160;</td>
    <td class="td04121524">&#160;<%= thisrow.getLineTankItem().trim() %>&#160;<%= thisrow.getLineTankItemDescription() %></td>
    <td class="td04121524" style="text-align:center"><abbr title="Code assigned to this Formula - Last Date and Time this Formula was Revised">
     <%= thisrow.getFormulaNumber() %><br>
     <%= GeneralQuality.formatDateForScreen(thisrow.getRevisionDate()) %>&#160;&#160;<%= GeneralQuality.formatTimeForScreen(thisrow.getRevisionTime()) %>
    </abbr></td>   
    <td class="td04121524" style="text-align:center">&#160;<%= thisrow.getStatusDescription() %></td> 
    
<%
  if (displayView.trim().equals(""))
  {
  // --- Build Vector for the MORE Button
  Vector sendParms = new Vector();
  sendParms.add(inqFormulaTable.getRequestType());
  sendParms.add(inqFormulaTable.getSecurityLevel());
  sendParms.add(inqFormulaTable.getEnvironment());
  sendParms.add(thisrow.getFormulaNumber());
  sendParms.add(thisrow.getRevisionDate());
  sendParms.add(thisrow.getRevisionTime());
%>    
   <td class="td04121524" style="text-align:right">
     <%= InqFormula.buildMoreButton(sendParms) %>
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