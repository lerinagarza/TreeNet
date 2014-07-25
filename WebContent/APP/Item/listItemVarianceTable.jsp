<%@ page import = "com.treetop.businessobjects.ItemVariance" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "java.util.*" %>
<%


//------------------ listItemVarianceTable.jsp ------------------------------//
//
// Author :  Teri Walton  6/13/02
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  9/15/08    TWalton	   Broke apart the JSP - ease of maintenance
//-----------------------------------------------------------------------//
 // Bring in the Build View Bean.
 // Selection Criteria
 try
 {
 Vector listVariances = new Vector();
 String showUpdate = "";
 try
 {
	listVariances = (Vector) request.getAttribute("listVariances");
	showUpdate = (String) request.getAttribute("showUpdate");
	if (showUpdate == null)
	  showUpdate = "";
 }
 catch(Exception e)
 {}  
%>
<html>
 <head>
   <%= JavascriptInfo.getMoreButton() %>
  </head>
  <body>
<%
   if (listVariances == null ||
       listVariances.size() == 0)
   {    
%>
   <table class="table01">
    <tr>
     <td class="td0418">No Variances</td>
    </tr>
   </table>
<%
   }
   else
   {    
%>
   <table class="table00">
<%
     for (int x = 0; x < listVariances.size(); x++)
     {
        ItemVariance iv = (ItemVariance) listVariances.elementAt(x);
%>   
    <tr>
     <td class="td0414" style="text-align:right"><b>Item :  </b></td>
     <td class="td0414">
      <b><%= HTMLHelpersLinks.routerItem(iv.getItemNumber(), "a0414", "", "") %></b>&nbsp;&nbsp;-&nbsp;&nbsp;<%= iv.getItemDescription() %>
     </td>
    </tr>
    <tr>
     <td class="td0414" style="text-align:right"><b>Issue Date :  </b></td>
     <td class="td0414">
<%
   String dateIssued = iv.getDateIssued();
   try
   {
      if (!dateIssued.trim().equals("0"))
      {
         DateTime dtIssued = UtilityDateTime.getDateFromyyyyMMdd(iv.getDateIssued());
         dateIssued = dtIssued.getDateFormatMMddyyyySlash();
      }
   }
   catch(Exception e)
   {}
%>      
     <%= dateIssued %></td>
<%
   if (showUpdate.equals("showUpdate") &&
   	   !iv.getRecordStatus().trim().equals("ACTIVE"))
   {
%>
	 <td class="td0320"><b><%= iv.getRecordStatus() %></b></td>
<%   
   }
%>     
    </tr>
    <tr>
     <td class="td0414" style="text-align:right"><b>Expire Date :  </b></td>
     <td class="td0414">
<%
   String dateExpired = iv.getDateExpired();
   try
   {
      if (!dateExpired.trim().equals("0"))
      {
         DateTime dtExpired = UtilityDateTime.getDateFromyyyyMMdd(iv.getDateExpired());
         dateExpired = dtExpired.getDateFormatMMddyyyySlash();
      }
   }
   catch(Exception e)
   {}
%>           
     <%= dateExpired %></td>
<%
   if (showUpdate.equals("showUpdate"))
   {
   		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[4];
		String[] urlNames = new String[4];
		String[] newPage  = new String[4];
		for (int z = 0; z < 4; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z]  = "";
		}
		urlLinks[0] = "/web/CtlItem?requestType=addVariance&item="
						+ iv.getItemNumber().trim();
		urlNames[0] = "Add New Variance";
		newPage[0] = "N";
		if (iv.getRecordStatus().trim().equals("PENDING"))
		{
			// Update
			urlLinks[1] = "/web/CtlItem?requestType=updVariance&item="
						+ iv.getItemNumber().trim() + "&dateIssued="
						+ iv.getDateIssued() + "&dateExpired="
						+ iv.getDateExpired() + "&updDate="
						+ iv.getUpdDate() + "&updTime=" 
						+ iv.getUpdTime() + "&updUser="
						+ iv.getUpdUser();
			urlNames[1] = "Update This Variance";
			newPage[1] = "N";
			// Delete
			urlLinks[2] = "/web/CtlItem?requestType=deleteVariance&item="
						+ iv.getItemNumber().trim() + "&dateIssued="
						+ iv.getDateIssued() + "&dateExpired="
						+ iv.getDateExpired() + "&updDate="
						+ iv.getUpdDate() + "&updTime=" 
						+ iv.getUpdTime() + "&updUser="
						+ iv.getUpdUser() + "&inqItem="
						+ iv.getItemNumber().trim() + "&showPending=YES";
			urlNames[2] = "Delete This Variance";
			newPage[2] = "N";
		}
		else
		{
			// Change back to Pending	
			urlLinks[1] = "/web/CtlItem?requestType=pendVariance&item="
						+ iv.getItemNumber().trim() + "&dateIssued="
						+ iv.getDateIssued() + "&dateExpired="
						+ iv.getDateExpired() + "&updDate="
						+ iv.getUpdDate() + "&updTime=" 
						+ iv.getUpdTime() + "&updUser="
						+ iv.getUpdUser() + "&inqItem="
						+ iv.getItemNumber().trim() + "&showPending=YES";
			urlNames[1] = "Change Back to Pending";
			newPage[1] = "N";	
		}
%>
	 <td class="td0414" style="text-align:right"><%= HTMLHelpers.buttonMore(urlLinks, urlNames, newPage) %></td>
<%   
   }
%>          
    </tr>
    <tr>
     <td class="td0414" style="text-align:right" valign="top"><b>Variance :  </b></td>
     <td colspan="2">
      <%= HTMLHelpersInput.inputBoxTextarea("", iv.getVarianceText(), 10, 75, 5000, "Y") %>
     </td>
    </tr>
    <tr><td colspan="3"><hr></td></tr>
<%
     } // end of the FOR Statement
%>      
   </table>
<%
   }
%>
   </body>
</html>
<%
   } catch(Exception e)
   {}
%>