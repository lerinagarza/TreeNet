<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.app.quality.*" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.businessobjects.QaMethod" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listMethodTable.jsp  ------------------------------------------//
//  Directly included in the listMethod.jsp
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
 InqMethod inqMethodTable = new InqMethod();
 Vector getData = new Vector();
 String thisEnv = "PRD";
 String valDisplay = "Method";
 try
 {
	inqMethodTable = (InqMethod) request.getAttribute("inqViewBean");
//	BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
    if (inqMethodTable == null)
    {
       DtlMethod dtlMethodTable = (DtlMethod) request.getAttribute("dtlViewBean");
       getData = dtlMethodTable.getDtlBean().getRevReasonMethod();
       thisEnv = dtlMethodTable.getEnvironment();
    }
    else
    {
	   getData = inqMethodTable.getListReport();
	   thisEnv = inqMethodTable.getEnvironment();
	}
	if (inqMethodTable.getRequestType().trim().equals("listProcedure"))
	   valDisplay = "Procedure";
	if (inqMethodTable.getRequestType().trim().equals("listPolicy"))
	   valDisplay = "Policy";   
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
       columnHeadingTo = "/web/CtlQuality?requestType=list" + valDisplay + "&" +
       						"&environment=" + thisEnv + 
                            inqMethodTable.buildParameterResend();
   String[] sortImage = new String[13];
   String[] sortStyle = new String[13];
   String[] sortOrder = new String[13];
   sortOrder[0] = "methodNumber";
   sortOrder[1] = "methodDescription";
   sortOrder[2] = "revisionDate";
   sortOrder[3] = "status";
//   sortOrder[4] = "loadFruitNetWeight";
//   sortOrder[5] = "loadFullBins";
//   sortOrder[6] = "facility";
//   sortOrder[7] = "carrierBOL";
//   sortOrder[8] = "carrier";
//   sortOrder[9] = "fromLocation";
//   sortOrder[10] = "whseTicket";  
//   sortOrder[11] = "warehouse";
  //************
  //Set Defaults
   for (int x = 0; x < 4; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
  if (displayView.trim().equals(""))
  {
   String orderBy = inqMethodTable.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "MethodNumber";
   for (int x = 0; x < 4; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (inqMethodTable.getOrderStyle().trim().equals(""))
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
     <%= sortImage[0] %>
     <a class="a0412" title="<%= valDisplay %> Number" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
<%
  }    
%>     
      <b>Document<br>Number</b>
     </a>      
    </td>
    
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[1] %>
     <a class="a0412" title="Description of the <%= valDisplay %>" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
<%
  }    
%>      
      <b>Title</b>
     </a>      
    </td>      
    
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>      
     <%= sortImage[2] %>
     <a class="a0412" title="Date and Time the <%= valDisplay %> was Revised" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
<%
  }    
%>      
      <b>Revision Date<br>and User</b>
     </a>      
     
    </td>  
<%
  if (displayView.trim().equals("detail"))
  {
%>    
    <td class="td04120605" style="text-align:center"><b>Revision Reason</b></td>      
<%
  }
%>  
    <td class="td04120605" style="text-align:center">
<%
  if (displayView.trim().equals(""))
  {
%>     
     <%= sortImage[3] %>
     <a class="a0412" title="Status of the <%= valDisplay %>" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
<%
  }    
%>      
      <b>Status</b>
     </a>      
    </td>  
<%
   if (displayView.trim().equals(""))
  {
%>       
    <td class="td04120605" style="width:6%">&#160;</td> 
<%
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
      QaMethod thisrow = (QaMethod) getData.elementAt(x);
      String methodURL = "/web/CtlQuality?requestType=dtl" + valDisplay + "&methodNumber=" + thisrow.getMethodNumber() +
                       "&revisionDate=" + thisrow.getRevisionDate() +
                       "&revisionTime=" + thisrow.getRevisionTime() +
                       "&environment=" + thisEnv;
       String methodMouseover = "Click here to see the details Number: " + thisrow.getMethodNumber();
       String methodName = thisrow.getMethodName();
       if (methodName.trim().equals(""))
          methodName = "Please add info to Method: " + thisrow.getMethodNumber();
%>     
   <tr class="tr00">
    <td class="td04121524" style="text-align:center">&#160;<%=HTMLHelpersLinks.basicLink(methodName, methodURL, methodMouseover,"" , "") %></td>
    <td class="td04121524">&#160;<%= thisrow.getMethodDescription() %></td>
    <td class="td04121524" style="text-align:center">
     <abbr title="Last Date and User to revise:  Revision Time: <%= GeneralQuality.formatTimeForScreen(thisrow.getRevisionTime()) %>">
      <%= GeneralQuality.formatDateForScreen(thisrow.getRevisionDate()) %>&#160;<br>
      <%// GeneralQuality.findLongNameFromProfile(inqMethodTable.getEnvironment(), thisrow.getApprovedByUser()) %>
     </abbr>
    </td>  
<%
  if (displayView.trim().equals("detail"))
  {
%>    
    <td class="td04121524">
      <%= thisrow.getRevisionReasonText().trim() %>&#160;
    </td> 
<%
   }
%>     
    <td class="td04121524" style="text-align:center">&#160;<%= thisrow.getStatusDescription() %></td> 
    
<%
  if (displayView.trim().equals(""))
  {
  // --- Build Vector for the MORE Button
  Vector sendParms = new Vector();
  sendParms.add(inqMethodTable.getRequestType());
  sendParms.add(inqMethodTable.getSecurityLevel());
  sendParms.add(inqMethodTable.getEnvironment());
  sendParms.add(thisrow.getMethodNumber());
  sendParms.add(thisrow.getRevisionDate());
  sendParms.add(thisrow.getRevisionTime());
%>    
   <td class="td04121524" style="text-align:right">
     <%= InqMethod.buildMoreButton(sendParms) %>
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