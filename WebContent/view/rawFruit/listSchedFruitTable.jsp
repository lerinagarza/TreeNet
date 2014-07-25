<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="
    com.treetop.app.rawfruit.InqScheduledFruit,
    com.treetop.app.rawfruit.UpdAvailableFruit,
    com.treetop.businessobjects.ScheduledLoadDetail,
    com.treetop.businessobjects.DateTime,
    com.treetop.businessobjects.KeyValue,
    com.treetop.utilities.UtilityDateTime,
    java.util.Vector" %>
<% 
//---------------  listSchedRawFruitTable.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  8/10/10
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //      drop down lists
 InqScheduledFruit iTable = new InqScheduledFruit();
 Vector getData = new Vector(); // Vector of Scheduled Load Detail Records

 try {
    try {
       iTable = (InqScheduledFruit) request.getAttribute("inqViewBean");
    } catch(Exception e){}
    if (iTable == null || iTable.getRequestType().trim().equals("")) {
      iTable = new InqScheduledFruit();
      iTable.setRequestType((String) request.getAttribute("requestType"));
      if (iTable.getRequestType().trim().equals("updAvailFruit"))       {
         try {
            UpdAvailableFruit uaf = (UpdAvailableFruit) request.getAttribute("updViewBean");
            getData = uaf.getBeanAvailFruit().getScheduledLoadDetail();
            iTable.setEnvironment(uaf.getEnvironment());
            iTable.setInqShowComments("Y");
         }catch(Exception e) {
           System.out.println("Problem when building information for listSchedRawFruitTable.jsp");
         }
      }
     
    } else {
       if (iTable.getRequestType().trim().equals("listSchedFruit")) {
         try {
            getData = iTable.getBeanAvailFruit().getScheduledLoadDetail();
         }catch(Exception e) {
           System.out.println("Problem when building information for listSchedRawFruitTable.jsp");
         }
       }
    }
//  BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
//  getData = beanData.getListLoads();
} catch(Exception e)  {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
}    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
String displayViewStandard = request.getParameter("displayView");
if (displayViewStandard == null) {
    displayViewStandard = "";
}
%>

<div>

<style scoped>

    .load-table, .load-table tr, .load-table td {
        border:none;
    }
    .load-table h4 {
        font-size:.8em;
        padding:0;
    }
    .load-table h4 span {
        margin: 0 .25em 0 0 !important;
    }
    .list-fruit td {
        border-bottom:1px solid #ddd;
        font-size:.8em;
        text-align:center;
    }
    
    .load-scheduled, .load-scheduled td {
        background-color:#fff;
        border-color:#999;
    }
    .load-scheduled th {
        background-color:#f0f0f0;   /*light-gray*/
    }
    
    .load-deleted td, .load-cancelled td {
        background-color:#ddd;      /*gray*/
    }
    .load-deleted th {
        background-color:#fff;
    }
    
    .load-received, .load-received td {
        background-color:#DEEDF7;   /*very-light-blue*/
        border-color:#999;
    }
    .load-received th {
        background-color:#fff;
    }
    
    .load-overdue, .load-overdue td {
        background-color:#13316e;   /*navy*/
        color:#f0f0f0;
    }
    .load-overdue th {
        background-color: #2779AA;  /*med blue*/
        color: #fff;
    }
    
    #status-legend {
        font-size:.8em;
        margin:1em;
        
    }
    #status-legend span {
        padding:.5em;
        border:1px solid #000;
    }
    
</style>


<table class="styled full-width load-table">



<%
  //----------------------------------------------------------------------------
  // HEADING SECTION
  int columnSpan  = 1; // to deal with the Status and Load #  -- added to if you want to see Status
  int columnSpan1 = 8; // deal with ALL the columns in the "inside" table
  int columnSpan2 = 1;
  int columnSpan3 = 7;
%>
<%--Table Header --%>
<tr>
<%
   if (!iTable.getInqShowPrice().trim().equals("")) {
     columnSpan1++;
     columnSpan2++;
     columnSpan3++;
   }
   
   if (!iTable.getInqTransfer().trim().equals("N")) {
     columnSpan1++;
     columnSpan2++;
     columnSpan3++;
   }
%>   


<% if (iTable.getRequestType().equals("listSchedFruit")) {
     columnSpan++; %> 
    <td>Status</td> 
<% } %>  
    <th class="td0412">Load #</th>
    <th>Delivery<br>Date</th>
    <th colspan="3">Receiving<br>Location / Dock</th>
    <th colspan="2">Hauling<br>Company</th>  
    <th colspan="<%= columnSpan2 %>">Time<br>Load Type</th>    
    <th></th>
</tr>
<%--/Table Header --%>


<%
   // For EACH Load -- the BORDER goes on the TOP -- needs to be a BLACK border
  String loadNumber = "";
  DateTime rightNow = UtilityDateTime.getSystemDate();
  String saveWhseLoc = "";
  if (!getData.isEmpty()) {// make sure there is Loads to be displayed
  
     // Default Information
     String rowClass   = ""; 
 
    for (int x = 0; x < getData.size(); x++) {
       ScheduledLoadDetail thisRow = (ScheduledLoadDetail) getData.elementAt(x); 
       String allowUpdate = "Y";
       
       if (!loadNumber.trim().equals(thisRow.getScheduleLoadNo())) { 
          // if the comments are to be displayed they need to be done here
          if (!loadNumber.trim().equals("") && 
              !iTable.getInqShowComments().trim().equals("")) {
             try{
                Vector listComments = iTable.retrieveCommentsForLoad(loadNumber);
                if (listComments != null && !listComments.isEmpty()) {

    request.setAttribute("screenType", "listPage");
    request.setAttribute("longFieldType", "comment");
    request.setAttribute("listKeyValues", listComments);
%>  

<tr class="<%=rowClass %>">
    <td colspan="2"></td>
    <td colspan="<%= columnSpan1-1 %>">
        <div class="ui-widget ui-widget-content ui-corner-all">    
			<%  KeyValue keys = new KeyValue();
			     keys.setEnvironment(iTable.getEnvironment());
			     keys.setEntryType("ScheduledLoadComment");
			     keys.setKey1(loadNumber);
			     keys.setKey2("");
			     keys.setKey3("");
			     keys.setKey4("");
			     keys.setKey5("");
			     keys.setVisibleOnLoad(true);
			     keys.setViewOnly(true);
			     
			     request.setAttribute("keys",keys); %>
			<jsp:include page="../utilities/commentSection.jsp"></jsp:include>
        </div>
    </td>
</tr>



<%
                }   //end if comment
             } catch(Exception e) {}
             
          } // end of the Show Comments 
          
          // RESET EVERYTHING
          saveWhseLoc = "";
          loadNumber = thisRow.getScheduleLoadNo().trim();
          
          //default state
          rowClass = "load-scheduled";

          if (!thisRow.getLoadReceivedFlag().trim().equals("")) {
              // implies that it is C-Cancelled or D-Deleted
              rowClass = "load-cancelled";//"tr02"; // GREY
              allowUpdate = "N";
              if (thisRow.getLoadReceivedFlag().trim().equals("R")) {
                rowClass = "load-received"; //"tr06"; // Medium Blue
                allowUpdate = "R";
              }
          } else {
          
            int sched = new Integer(thisRow.getScheduledDeliveryDate()).intValue();
            int now = new Integer(rightNow.getDateFormatyyyyMMdd()).intValue();
            boolean overdue = sched < now;
          
            if (overdue) {
              
              rowClass = "load-overdue"; //"tr05"; // Navy Background
         
            }
            
          }
%>  



<tr class="<%= rowClass %>" style="border-top:3px solid #000;">
<%--Schedule load information (date, receiving location, etc.) --%>

<% if (iTable.getRequestType().equals("listSchedFruit")) { %> 
    <td class="center"><%= thisRow.getLoadReceivedFlag().trim() %></td> 
<% } %>



    <td class="bold center"><%= thisRow.getScheduleLoadNo().trim() %></td>



    <td class="center">
        <%= InqScheduledFruit.formatDate(thisRow.getScheduledDeliveryDate().trim()) %>
        <% if (!thisRow.getDistributionOrder().trim().equals("")) { %>
            <br>DO: <%=thisRow.getDistributionOrder().trim() %>
        <% } %>   
    </td>
   


    <td class="center" colspan="3"><%= thisRow.getReceivingLocationDesc().trim() %>(<%= thisRow.getReceivingLocationNo().trim() %>)
		<% if (!thisRow.getReceivingDockNo().trim().equals("")) { %>  
		   <%= thisRow.getReceivingDockNo() %>-<%= thisRow.getReceivingDockDesc() %> 
		<% } %>
    </td>
    
    
    
    <td class="center" colspan="2">
        <%= thisRow.getHaulingCompanyName() %>(<%= thisRow.getHaulingCompany() %>)
    </td>



    <td class="center">
		<% if (!thisRow.getScheduledDeliveryTime().trim().equals("0")) { %>   
		   <%= InqScheduledFruit.formatTime((thisRow.getScheduledDeliveryTime().trim() + "00")).substring(0,5) %>
		   --
		<% } %>      
	    <%= thisRow.getLoadType() %>
    </td>
    
    
    
    <td class="center" colspan="<%= columnSpan2 %>">
		<% if (iTable.getRequestType().trim().equals("listSchedFruit")) { // display the more button
		      String sendParms = iTable.buildParameterResend() + "&originalRequestType=listSchedFruit";
		      %>
		      <%=InqScheduledFruit.buildMoreButton(
			      iTable.getRequestType(), 
			      iTable.getEnvironment(), 
			      thisRow.getScheduleLoadNo(), 
			      thisRow.getWhseNumber(), 
			      thisRow.getWhseAddressNumber(), 
			      sendParms, 
			      allowUpdate, 
			      thisRow.getTransferFlag().trim()) %>
		<% } %>   
    </td>
   
   

</tr>
  
  
  
<%
     } // end of the IF NEW LOAD NUMBER
   //------------------------------------------------------------------------------
   // Test to see if it is a NEW Whse/Location
     if ((!saveWhseLoc.trim().equals(thisRow.getWhseNumber().trim() + thisRow.getWhseAddressNumber().trim())) ||
         (!thisRow.getTransferFlag().trim().equals("") && (!saveWhseLoc.trim().equals(thisRow.getShippingLocationNo().trim() + thisRow.getShippingDockNo().trim()))))
     {
        saveWhseLoc = thisRow.getWhseNumber().trim() + thisRow.getWhseAddressNumber().trim();
%>  




<tr class="<%= rowClass %>">
  
<% // if this load is a Transfer Type Load %>  
   <td colspan="<%= columnSpan %>"></td>
<% if (!thisRow.getTransferFlag().trim().equals(""))  { 
        // Will show as an M3 Warehouse -- instead of a "M3 Supplier"
      saveWhseLoc = thisRow.getShippingLocationNo().trim() + thisRow.getShippingDockNo().trim();
%> 
<%--Transfer load type --%>
    <td>
        TRANSFER:
    </td>
    
    
    <td colspan = "<%= columnSpan3 %>">
        <acronym title="Shipping Location (M3 From Whse Number): <%= thisRow.getShippingLocationNo().trim() %>">
            <%= thisRow.getShippingLocationDesc().trim() %>(<%= thisRow.getShippingLocationNo().trim() %>)
        </acronym>
		<% if (!thisRow.getShippingDockNo().trim().equals("")) { %>    
		    /
		    <acronym title="Dock Information Number: <%= thisRow.getShippingDockNo().trim() %>"><%= thisRow.getShippingDockNo() %>-<%= thisRow.getShippingDockDesc().trim() %></acronym>
		<% } %>    
    </td>

   
<% } else { %>  
<%--Not a transfer load type --%>


 
    <td class="bold" colspan="<%= (columnSpan3 + 1) %>">
		<% if (thisRow.getWhseNumber().trim().equals("ORCHARDRUN")) { %>
		     ORCHARD RUN
		<%   } else { %>   
	    <acronym title="Whse Number: <%= thisRow.getWhseNumber().trim() %>"><%= thisRow.getWhseDescription().trim() %></acronym>
	    /
	    <acronym title="Whse Location Number: <%= thisRow.getWhseAddressNumber().trim() %>"><%= thisRow.getWarehouse().getWarehouseDescription().trim() %></acronym>
        <% } %>    
    </td>
    
<% } %> <%--End If transfer load type --%>
   
</tr>



<tr class="<%= rowClass %>" style="font-size:.8em;">
    <td colspan="<%= columnSpan %>"></td>
    <th>Crop</th>
    <th>Variety</th>
    <th>Grade</th>
    <th>Fruit Type</th>
    
<% if (!iTable.getInqTransfer().trim().equals("N")) { %>  
    <th>TT Item</th>
<% } %> 

    <th>Sticker Free</th>
    <th>O/R</th>
    <th>Cash Fruit</th>
    
<% if (!iTable.getInqShowPrice().trim().equals("")) { %>
    <th style="text-align:right">Price</th>  
<% } %>
   
   <th style="text-align:right">Bins</th>  
</tr>





<% } // end of the NEW whse/location values


   // show ALL detail Lines
%>  


<tr class="list-fruit <%= rowClass %>">

    <td colspan="<%= columnSpan %>" style="border:none;"></td>
    
    <td>
        <acronym title = "Crop Code: <%= thisRow.getCropCode().trim() %>">
            <%= thisRow.getCropCodeDesc().trim() %>
        </acronym>
    </td>
    
    <td>
        <acronym title = "Variety Code: <%= thisRow.getVarietyCode().trim() %>">
            <%= thisRow.getVarietyDesc().trim() %>
        </acronym>
    </td>
    
    <td>
        <acronym title = "Grade Code: <%= thisRow.getGradeCode().trim() %>">
        <%= thisRow.getGradeDesc().trim() %>  
        </acronym>
    </td>
        
    <td>
        <acronym title = "Fruit Type Code: <%= thisRow.getOrganicCode().trim() %>">
            <%= thisRow.getOrganicDesc().trim() %>
        </acronym>
    </td>
    
<% if (!iTable.getInqTransfer().trim().equals("N"))  { %>
    <td>
        <acronym title = "M3 - Tree Top Item Number">
            <%= thisRow.getTreeTopItem().trim() %>
        </acronym>
    </td>  
<% } %>  

       
    <td>
        <acronym title = "Is this Fruit Sticker Free?">
		<% if (!thisRow.getStickerFreeFlag().trim().equals("")) { %>
		    <img src="https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif"/>"
		<% } %>       
        </acronym>
    </td>  
      
          
    <td>
        <acronym title = "Is the Fruit Orchard Run?">
		<% if (!thisRow.getOrchardRunDtlFlag().trim().equals("")) { %>
		    <img src="https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif"/> 
		<% } %>
        </acronym>
    </td>    
      
      
    <td>
        <acronym title = "This is Cash Fruit - as Opposed to Pool Fruit">
	    <% if (!thisRow.getMemberOrCash().trim().equals("")) { %>
	       <img src="https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif"/> 
	    <% } %>
	    </acronym>
    </td>      
    
    
<% if (!iTable.getInqShowPrice().trim().equals("")) { %>    
    <td" style="text-align:right">
        <acronym title = "Price agreed for this line">
            <%= thisRow.getCashPrice() %>
        </acronym>
    </td>
<% } %> 



    <td style="text-align:right">
	    <acronym title = "Bins Scheduled for Load">
	        <%= thisRow.getBinCount() %>
	    </acronym>
    </td>
    
    
</tr> 



<% }// end of the for loop %>


<%  if (!loadNumber.trim().equals("") && !iTable.getInqShowComments().trim().equals("")) {
        try {
            Vector listComments = iTable.retrieveCommentsForLoad(loadNumber);
                if (listComments != null && listComments.size() > 0) {

%>  

<tr>
	<td colspan="2"></td>
	
	<td colspan="<%= columnSpan1-1 %>"> 
         <%  KeyValue keys = new KeyValue();
             keys.setEnvironment(iTable.getEnvironment());
             keys.setEntryType("ScheduledLoadComment");
             keys.setKey1(loadNumber);
             keys.setKey2("");
             keys.setKey3("");
             keys.setKey4("");
             keys.setKey5("");
             keys.setVisibleOnLoad(true);
             keys.setViewOnly(true);
             
             request.setAttribute("keys",keys); %>
        <jsp:include page="../utilities/commentSection.jsp"></jsp:include>
	</td>
</tr>
<%          }

        } catch (Exception e) {}

    }   //end if show comments
%>

   
<% } // end of if there are any records for the list %>
  </table>

<div id="status-legend">
    <strong>Load Status:</strong> 
    <span class="load-scheduled">Scheduled</span>
    <span class="load-received">Received</span>
    <span class="load-overdue">Overdue</span>
<%  String sendFrom = (String) request.getAttribute("sendFrom");
    if (sendFrom != null && sendFrom.equals("updAvailFruitByWhse.jsp")) {
    //Do not show deleted when coming from the updAvailFruitByWhse.jsp
 %>    
<%  } else { %>
    <span class="load-deleted">Deleted</span>
<%  } %>
</div>

</div>