<%@ page language="java" %>
<%@ page import = "com.treetop.app.dataset.UpdDataSet" %>
<%@ page import = "com.treetop.businessobjects.DataSet" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- APP/DataSet/updDataSet.jsp -----------------------//
// Author   :  Teri Walton      4/21/08
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
  String inqTitle = "Update DataSet Information";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdDataSet uds = new UpdDataSet();
 try
 {
	uds = (UpdDataSet) request.getAttribute("updViewBean");
    if (uds.getRequestType().equals("updCompanyCost"))
       inqTitle = "Update COMPANY COST Information in a DataSet";
    if (uds.getRequestType().equals("updAverageCost"))
       inqTitle = "Update AVERAGE COST Information in a DataSet";     
 }
 catch(Exception e)
 {
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   request.setAttribute("title",inqTitle);
//*****************************************************************************
%>
<html>
 <head>
   <title><%= inqTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>
    <%= JavascriptInfo.getExpandingSectionHead("Y", 1, "Y", 1) %>   
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <form action="/web/CtlDataSet?requestType=<%= uds.getRequestType() %>" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!uds.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102" colspan = "6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= uds.getDisplayMessage().trim() %></b></td>
       </tr>    
<%
   }
%>    
      <tr class="tr02">
        <td class="td04160102" colspan="2"><b>Update a Dataset -- Choose the criteria needed for updating the information.</b></td>
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Choose a Dataset:</b></td>
        <td class="td03160102">
         <b>
          <%= UpdDataSet.buildDropDownFile(uds.getDataSetName(), "N") %>&nbsp;&nbsp;<%= uds.getDataSetNameError() %>
         </b>
        </td>
       </tr>
<%
   if (uds.getRequestType().equals("updAverageCost"))
   {
%>       
       <tr class="tr00">
        <td class="td04160102"><b>Sales Year:</b></td>
        <td class="td04160102"><b>
        <%= UpdDataSet.buildDropDownSalesYear(uds.getSalesYear(), "N") %>
        </b></td>
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Budget Year:</b></td>
        <td class="td04160102"><b>
        <%= UpdDataSet.buildDropDownBudgetYear(uds.getBudgetYear(), "N") %>
        </b></td>
       </tr>  
<%
    }
   if (uds.getRequestType().equals("updCompanyCost"))
   {
%>       
       <tr class="tr00">
        <td class="td04160102"><b>Year to Update within the Dataset:</b></td>
        <td class="td04160102"><b>
        <%= UpdDataSet.buildDropDownYear(uds.getDataSetYear(), "N") %>
        </b></td>
       </tr>
       <tr class="tr00">
        <td class="td04160102"><b>Choose year to pull the cost FROM (Use Fiscal Year):</b></td>
        <td class="td04160102"><b>
        <%= UpdDataSet.buildDropDownCostingYear(uds.getCostYear(), "N") %>
        </b></td>
       </tr>  
       <tr class="tr00">
        <td class="td04160102"><b>Record Type:</b></td>
        <td class="td04160102"><b>
        <%= UpdDataSet.buildDropDownRecordType(uds.getRecordType(), "N") %>
        </b></td>
       </tr>    
<%
    }
%>                    
       <tr class="tr00">
        <td class="td04160102"><b>Comment:</b></td>
        <td class="td04160102"><b><%= HTMLHelpersInput.inputBoxText("comment", uds.getComment(), "Comment for Update", 50, 50, "", "") %></b></td>
       </tr>            
       <tr class="tr01">
        <td class="td04160102" style="text-align:center" colspan="2">
          <%= HTMLHelpers.buttonSubmit("updDataSet", "Go - Update DataSet") %>
        </td>
       </tr>      
      </table>  
    </td>  
    <td style="width:2%">&nbsp;</td> 
   </tr>
  </table> 
  </form>  
  <br>    
<%
//***************************************************************************************
//  Audit Log Section
Vector listAudit = new Vector();
//   Go Get the Audit Information
try
{ 
    listAudit = UpdDataSet.buildAuditLog(uds.getDataSetName().trim());
}
catch(Exception e)
{
}
%>  
  <table style="width:100%">  
   <tr class="tr02">
    <td class="td03201404" style="width:100%">
     <%= JavascriptInfo.getExpandingSection("C", "Update Audit Log", 0, 1, 3, 1, 0) %>
      <table class="table00" cellspacing="0" style="width:100%">
       <tr class="tr02">
        <td class="td04140102"><b>DataSet Description</b></td>
        <td class="td04140102"><b>Last Updated On</b></td>
        <td class="td04140102"><b>Last Updated By</b></td>
        <td class="td04140102"><b>Update Status</b></td>
        <td class="td04140102"><b>Comment</b></td>
       </tr>    
<%
   if (listAudit.size() > 0)
   {
      for (int x = 0; x < listAudit.size(); x++)
      {
         try
         {
            DataSet ds = (DataSet) listAudit.elementAt(x);
%>      
       <tr class="tr00">
        <td class="td04140102"><%= ds.getDescription().trim() %> - <%= ds.getFileName() %></td>
        <td class="td04140102"><%= ds.getUpdateDate() %> - <%= ds.getUpdateTime() %></td>
        <td class="td04140102"><%= UpdDataSet.getLongUserName(ds.getUpdateUser()) %></td>
        <td class="td04140102"><%= ds.getCompletionStatus() %>&nbsp;</td>
        <td class="td04140102"><%= ds.getComment() %>&nbsp;</td>
       </tr>   
<% 
         }
         catch(Exception e)
         {}
     } // For Loop
   } // if Statement
%>      
      </table>  
     </span>    
    </td>
   </tr>     
  </table>  
  <br>
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>