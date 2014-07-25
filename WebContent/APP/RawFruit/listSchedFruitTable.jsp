<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.*" %>
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
 //		 drop down lists
 InqScheduledFruit iTable = new InqScheduledFruit();
 Vector getData = new Vector(); // Vector of Scheduled Load Detail Records
 int imageCount  = 2;
 int expandCount = 0;
 try
 {
	try
    {
	   iTable = (InqScheduledFruit) request.getAttribute("inqViewBean");
	}catch(Exception e)
	{}
	if (iTable == null || iTable.getRequestType().trim().equals(""))
	{
	  iTable = new InqScheduledFruit();
	  iTable.setRequestType((String) request.getAttribute("requestType"));
	  if (iTable.getRequestType().trim().equals("updAvailFruit"))
	  {
	     try
	     {
	        UpdAvailableFruit uaf = (UpdAvailableFruit) request.getAttribute("updViewBean");
	        getData = uaf.getBeanAvailFruit().getScheduledLoadDetail();
	        iTable.setEnvironment(uaf.getEnvironment());
	        iTable.setInqShowComments("Y");
	     }catch(Exception e)
	     {
	       System.out.println("Problem when building information for listSchedRawFruitTable.jsp");
	     }
	  }
	 
	}
	else
	{
	   if (iTable.getRequestType().trim().equals("listSchedFruit"))
	   {
	     try
	     {
	        getData = iTable.getBeanAvailFruit().getScheduledLoadDetail();
	     }catch(Exception e)
	     {
	       System.out.println("Problem when building information for listSchedRawFruitTable.jsp");
	     }
	   }
	}
//	BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
//	getData = beanData.getListLoads();
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewStandard = request.getParameter("displayView");
 if (displayViewStandard == null)
   displayViewStandard = "";
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
  int columnSpan  = 1; // to deal with the Status and Load #  -- added to if you want to see Status
  int columnSpan1 = 8; // deal with ALL the columns in the "inside" table
  int columnSpan2 = 1;
  int columnSpan3 = 7;
%> 
  <tr class="tr02">
<%
   if (!iTable.getInqShowPrice().trim().equals(""))
   {
     columnSpan1++;
     columnSpan2++;
     columnSpan3++;
   }
   if (!iTable.getInqTransfer().trim().equals("N"))
   {
     columnSpan1++;
     columnSpan2++;
     columnSpan3++;
   }
   if (iTable.getRequestType().equals("listSchedFruit"))
   {
     columnSpan++;
%> 
      <td class="td0412" style="text-align:center; width:5%"><b>Status</b></td> 
<%
   }
%>  
   <td class="td0412" style="text-align:center; width:5%"><b>Load #</b></td>
   <td class="td04120102" style="text-align:center"><b>Delivery<br>Date</b></td>
   <td class="td04120102" style="text-align:center" colspan="3"><b>Receiving<br>Location / Dock</b></td>
   <td class="td04120102" style="text-align:center" colspan="2"><b>Hauling<br>Company</b></td>  
   <td class="td04120102" style="text-align:center" colspan="<%= columnSpan2 %>"><b>Time<br>Load Type</b></td>    
   <td class="td04120102" style="text-align:center; width:5%">&nbsp;</td>
   <td class="td04120102" style="width:1%">&nbsp;</td> 
  </tr>
<%
   // For EACH Load -- the BORDER goes on the TOP -- needs to be a BLACK border
  String loadNumber = "";
  DateTime rightNow = UtilityDateTime.getSystemDate();
  String saveWhseLoc = "";
  if (getData.size() > 0)// make sure there is Loads to be displayed
  {
     // Default Information
     String rowClass   = "tr00"; // White Background
     String dataClass  = "td04120424c"; // font size 8px, color Black, thick border TOP (Navy) light grey border bottom
     String dataClass1 = "td0412";
     String dataClass2 = "td0412010202"; // font size 8px, color black, light grey border bottom - background color grey
     String dataClass3 = "td04120102"; // font size 8px, color black, light grey border bottom 
     String dataClass4 = "td04140424d"; // font size 9px, color black, thick border TOP (Navy) 
     String dataClass5 = "td04120405"; // font size 8px, color black, border TOP (Navy), border botom navy 
     String dataClass6 = "td05160405"; // font size 10px, color Navy, border TOP (Navy), border botom navy 
     
    for (int x = 0; x < getData.size(); x++)
    {
       ScheduledLoadDetail thisRow = (ScheduledLoadDetail) getData.elementAt(x); 
       String allowUpdate = "Y";
       
       if (!loadNumber.trim().equals(thisRow.getScheduleLoadNo()))  
       { 
          // if the comments are to be displayed they need to be done here
          if (!loadNumber.trim().equals("") && 
              !iTable.getInqShowComments().trim().equals(""))
          {
             try{
                Vector listComments = iTable.retrieveCommentsForLoad(loadNumber);
                if (listComments != null && 
                    listComments.size() > 0)
                {
                   imageCount++;
                   expandCount++;
    request.setAttribute("screenType", "listPage");
    request.setAttribute("longFieldType", "comment");
    request.setAttribute("listKeyValues", listComments);
%>  
  <tr class="<%= rowClass %>">
  <td colspan = "2" style="text-align:center">&nbsp;</td>
   <td colspan = "<%= columnSpan1 %>" style="text-align:center"> 
    <table class="table00" cellspacing="0" cellpadding="2">
     <tr class="tr02">
      <td>
       <%= JavascriptInfo.getExpandingSection("O", "Comments", 10, expandCount, imageCount, 1, 7) %>
       <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>  
    </table>
   </td>
   </tr>
<%
                }
             }catch(Exception e)
             {}
          } // end of the Show Comments 
          // RESET EVERYTHING
          saveWhseLoc = "";
          loadNumber = thisRow.getScheduleLoadNo().trim();
          
          rowClass   = "tr00"; // White Background
          dataClass  = "td04120424c"; // font size 8px, color Black, thick border TOP (Navy) light grey border bottom
          dataClass1 = "td0412";
          dataClass2 = "td0412010202"; // font size 8px, color black, light grey border bottom - background color grey
          dataClass3 = "td04120102"; // font size 8px, color black, light grey border bottom 
          dataClass4 = "td04140424d"; // font size 9px, color black, thick border TOP (Navy) 
          dataClass5 = "td04120405"; // font size 8px, color black, border TOP (Navy), border botom navy 
          dataClass6 = "td05160405"; // font size 10px, color Navy, border TOP (Navy), border botom navy 
          if (!thisRow.getLoadReceivedFlag().trim().equals(""))
          {
              // implies that it is C-Cancelled or D-Deleted
              rowClass = "tr02"; // GREY
              allowUpdate = "N";
              if (thisRow.getLoadReceivedFlag().trim().equals("R"))
              {
                rowClass = "tr06"; // Medium Blue
                allowUpdate = "R";
              }
          }
          else
          {
            if (new Integer(thisRow.getScheduledDeliveryDate()).intValue() < new Integer(rightNow.getDateFormatyyyyMMdd()).intValue() ||
                !thisRow.getNoAvailFruitFlag().trim().equals(""))
            {
              rowClass = "tr05"; // Navy Background
              dataClass = "td00120324c";
              dataClass1 = "td0012";
              dataClass2 = "td00120102";
              dataClass3 = "td00120102";
              dataClass4 = "td00140424d";
              dataClass5 = "td00120102";
              dataClass6 = "td00160102";
              
              if (!thisRow.getNoAvailFruitFlag().trim().equals(""))
                 rowClass = "tr03"; // Red
            }
          }
%>  
  <tr class="<%= rowClass %>">
<%
   if (iTable.getRequestType().equals("listSchedFruit"))
   {
%> 
   <td class="<%= dataClass4 %>" style="text-align:center"><b><%= thisRow.getLoadReceivedFlag().trim() %>&nbsp;</b></td> 
<%
   }
%>
   <td class="<%= dataClass4 %>" style="text-align:center"><b><%= thisRow.getScheduleLoadNo().trim() %>&nbsp;</b></td>
   <td class="<%= dataClass %>" style="text-align:center"><%= InqScheduledFruit.formatDate(thisRow.getScheduledDeliveryDate().trim()) %>
<%
   if (!thisRow.getDistributionOrder().trim().equals(""))
      out.println("<br><b>DO: " + thisRow.getDistributionOrder().trim() + "</b>");
%>   
   </td>
   <td class="<%= dataClass %>" style="text-align:center" colspan="3"><%= thisRow.getReceivingLocationDesc().trim() %>&nbsp;(<%= thisRow.getReceivingLocationNo().trim() %>)&nbsp;
<%
   if (!thisRow.getReceivingDockNo().trim().equals(""))
   {
%>  
   <b><%= thisRow.getReceivingDockNo() %>-<%= thisRow.getReceivingDockDesc() %></b> 
<%
   }
%>
   </td>
   <td class="<%= dataClass %>" style="text-align:center" colspan="2"><%= thisRow.getHaulingCompanyName() %>&nbsp;(<%= thisRow.getHaulingCompany() %>)</td>
   <td class="<%= dataClass %>" style="text-align:center">
<%
   if (!thisRow.getScheduledDeliveryTime().trim().equals("0"))
   {
%>   
   <b><%= InqScheduledFruit.formatTime((thisRow.getScheduledDeliveryTime().trim() + "00")).substring(0,5) %></b>
   &nbsp;--&nbsp;
<%
   }
%>      
   <%= thisRow.getLoadType() %>&nbsp;</td>
   <td class="<%= dataClass %>" style="text-align:right" colspan="<%= columnSpan2 %>">
<% 
    if (iTable.getRequestType().trim().equals("listSchedFruit"))
	{ // display the more button
	  if (thisRow.getLoadReceivedFlag().trim().equals("C"))
	    allowUpdate = thisRow.getLoadReceivedFlag().trim();  
	  String sendParms = iTable.buildParameterResend() + "&originalRequestType=listSchedFruit";
	  out.println(InqScheduledFruit.buildMoreButton(iTable.getRequestType(), iTable.getEnvironment(), thisRow.getScheduleLoadNo(), thisRow.getWhseNumber(), thisRow.getWhseAddressNumber(), sendParms, allowUpdate, thisRow.getTransferFlag().trim()));
	}
%>   
   &nbsp;&nbsp;</td>
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
<%
   // if this load is a Transfer Type Load
%>  
   <td class="<%= dataClass1 %>"colspan="<%= columnSpan %>">&nbsp;</td>
<%  
   if (!thisRow.getTransferFlag().trim().equals(""))  
   { // Will show as an M3 Warehouse -- instead of a "M3 Supplier"
      saveWhseLoc = thisRow.getShippingLocationNo().trim() + thisRow.getShippingDockNo().trim();
%> 
   <td class="<%= dataClass2 %>" style="background-color=yellow; color=black"><b>TRANSFER:</b></td>
   <td class="<%= dataClass6 %>" colspan = "<%= columnSpan3 %>"><b>
    <acronym title="Shipping Location (M3 From Whse Number): <%= thisRow.getShippingLocationNo().trim() %>"><%= thisRow.getShippingLocationDesc().trim() %>&nbsp;(<%= thisRow.getShippingLocationNo().trim() %>)</acronym>
<%
  if (!thisRow.getShippingDockNo().trim().equals(""))
  {
%>    
    &nbsp;/&nbsp;
    <acronym title="Dock Information Number: <%= thisRow.getShippingDockNo().trim() %>"><%= thisRow.getShippingDockNo() %>-<%= thisRow.getShippingDockDesc().trim() %>&nbsp;</acronym>
<%
   }
%>    
   </b></td>
   
<%
    }
    else
    {
%>   
   <td class="<%= dataClass6 %>" colspan = "<%= (columnSpan3 + 1) %>"><b>
<%
  if (thisRow.getWhseNumber().trim().equals("ORCHARDRUN"))
  {
     out.println("ORCHARD RUN");
   }else{
%>   
    <acronym title="Whse Number: <%= thisRow.getWhseNumber().trim() %>"><%= thisRow.getWhseDescription().trim() %></acronym>
    &nbsp;/&nbsp;
    <acronym title="Whse Location Number: <%= thisRow.getWhseAddressNumber().trim() %>"><%= thisRow.getWarehouse().getWarehouseDescription().trim() %>&nbsp;</acronym>
<%
    }
%>    
   </b></td>
<%
   }
%>   
  </tr>
  <tr class="<%= rowClass %>">
   <td class="<%= dataClass1 %>" colspan="<%= columnSpan %>">&nbsp;</td>
   <td class="<%= dataClass2 %>" style="text-align:center"><b>Crop</b></td>
   <td class="<%= dataClass2 %>" style="text-align:center"><b>Variety</b></td>
   <td class="<%= dataClass2 %>" style="text-align:center"><b>Grade</b></td>
   <td class="<%= dataClass2 %>" style="text-align:center"><b>Fruit Type</b></td>
<%
   if (!iTable.getInqTransfer().trim().equals("N"))
   {
%>  
   <td class="<%= dataClass2 %>" style="text-align:center"><b>TT Item</b></td>
<%
   }
%> 
   <td class="<%= dataClass2 %>" style="text-align:center"><b>Sticker Free</b></td>
   <td class="<%= dataClass2 %>" style="text-align:center"><b>O/R</b></td>
   <td class="<%= dataClass2 %>" style="text-align:center"><b>Cash Fruit</b></td>
<%
   if (!iTable.getInqShowPrice().trim().equals(""))
   { 
%>   
   <td class="<%= dataClass2 %>" style="text-align:right"><b>Price</b></td>  
<%
    }
%>   
   <td class="<%= dataClass2 %>" style="text-align:right"><b>Bins</b></td>  
  </tr>
<%
      } // end of the NEW whse/location values


   // show ALL detail Lines
%>  
  <tr class="<%= rowClass %>">
   <td class="<%= dataClass1 %>" colspan="<%= columnSpan %>">&nbsp;</td>
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "Crop Code: <%= thisRow.getCropCode().trim() %>">
       <%= thisRow.getCropCodeDesc().trim() %>&nbsp;</acronym></td>
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "Variety Code: <%= thisRow.getVarietyCode().trim() %>">
       <%= thisRow.getVarietyDesc().trim() %>&nbsp;</acronym></td>
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "Grade Code: <%= thisRow.getGradeCode().trim() %>">
       <%= thisRow.getGradeDesc().trim() %>&nbsp;</acronym></td>
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "Fruit Type Code: <%= thisRow.getOrganicCode().trim() %>">
       <%= thisRow.getOrganicDesc().trim() %>&nbsp;</acronym></td>
<%
   if (!iTable.getInqTransfer().trim().equals("N"))
   {
%>         
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "M3 - Tree Top Item Number">
       <%= thisRow.getTreeTopItem().trim() %>&nbsp;</acronym></td>  
<%
   }
%>         
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "Is this Fruit Sticker Free?">&nbsp;
<%
   if (!thisRow.getStickerFreeFlag().trim().equals(""))
   {
     imageCount++;
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
   }
%>       
      </acronym></td>      
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "Is the Fruit Orchard Run?">&nbsp;
<%
   if (!thisRow.getOrchardRunDtlFlag().trim().equals(""))
   {
     imageCount++;
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
   }
%>       
      </acronym></td>    
   <td class="<%= dataClass3 %>" style="text-align:center"><acronym title = "This is Cash Fruit - as Opposed to Pool Fruit">&nbsp;
<%
   if (!thisRow.getMemberOrCash().trim().equals(""))
   {
     imageCount++;
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
   }
%>       
      </acronym></td>      
<%
   if (!iTable.getInqShowPrice().trim().equals(""))
   {
%>    
   <td class="<%= dataClass3 %>" style="text-align:right"><acronym title = "Price agreed for this line"><%= thisRow.getCashPrice() %>&nbsp;</acronym></td>
<%
    }
%>      
   <td class="<%= dataClass3 %>" style="text-align:right"><acronym title = "Bins Scheduled for Load"><%= thisRow.getBinCount() %>&nbsp;</acronym></td>
  </tr> 
<%
     
    }// end of the for loop
      if (!loadNumber.trim().equals("") && 
          !iTable.getInqShowComments().trim().equals(""))
          {
             try{
                Vector listComments = iTable.retrieveCommentsForLoad(loadNumber);
                if (listComments != null && 
                    listComments.size() > 0)
                {
                   imageCount++;
                   expandCount++;
    request.setAttribute("screenType", "listPage");
    request.setAttribute("longFieldType", "comment");
    request.setAttribute("listKeyValues", listComments);
%>  
  <tr>
  <td colspan = "2" style="text-align:center">&nbsp;</td>
   <td colspan = "<%= columnSpan1 %>" style="text-align:center"> 
    <table class="table00" cellspacing="0" cellpadding="2">
     <tr class="tr02">
      <td class="td03">
       <%= JavascriptInfo.getExpandingSection("O", "Comments", 10, expandCount, imageCount, 1, 7) %>
       <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>  
    </table>
   </td>
   </tr>
<%
                }
             }catch(Exception e)
             {}
          }
    
    
  }// end of if there are any records for the list
%>
  </table>
 </body>
</html>