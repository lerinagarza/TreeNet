<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.*" %>
<%
//---------------- listAvailFruitTable.jsp -------------------------------------------//
//
//    Author :  Teri Walton  9/8/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector detailInfo = new Vector();
 InqAvailableFruit inqAvailFruit = new InqAvailableFruit();
 String readOnlyDetail = "";
 try
 {
	inqAvailFruit = (InqAvailableFruit) request.getAttribute("inqViewBean");
	detailInfo = inqAvailFruit.getListReport();
    //readOnlyDetail = updMain.getReadOnly();
//	readOnlyDetail = "Y";
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//--------------------------------------------------------
// Build Sort Options 
 String columnHeadingTo = "/web/CtlRawFruit?requestType=" +
 							inqAvailFruit.getRequestType() + "&" +
                            inqAvailFruit.buildParameterResend();
   String[] sortImage = new String[8];
   String[] sortStyle = new String[8];
   String[] sortOrder = new String[8];
   sortOrder[0] = "whseDescription";
   sortOrder[1] = "whseAddressDescription";
   sortOrder[2] = "crop";
   sortOrder[3] = "variety";
   sortOrder[4] = "grade";
   sortOrder[5] = "fruitType";
   sortOrder[6] = "stickerFree";
  //************
  //Set Defaults
   for (int x = 0; x < 7; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
   String orderBy = inqAvailFruit.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "crop";
   for (int x = 0; x < 7; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (inqAvailFruit.getOrderStyle().trim().equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowUpDark.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowDownDark.gif\">";
     }
   }   
%>
<html>
 <head>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
 </head>
 <body>
  <table class="table00" cellspacing="0" cellpadding="1">
   <tr class = "tr02">
<%
  if (inqAvailFruit.getRequestType().trim().equals("listAvailFruit"))
  {
%>   
    <td class="td04140102" style="text-align:center; width:10%"><acronym title="Click to move row to the next Scheduling Screen"><b>Choose to Schedule</b></acronym></td>
<%
  }
%>    
    <td class="td04140102" style="text-align:center; width:13%">
     <%= sortImage[0] %>
     <a class="a0414" title="Warehouse Name(Description) and Number -- M3 Supplier" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
      <b>Warehouse</b>
     </a>      
    </td>
    <td class="td04140102" style="text-align:center; width:13%">
     <%= sortImage[1] %>
     <a class="a0414" title="Warehouse Address - Location and Number" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
      <b>Location</b>
     </a>      
    </td>
    <td class="td04140102" style="text-align:center; width:10%">
     <%= sortImage[2] %>
     <a class="a0414" title="Type of Fruit" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      <b>Crop</b>
     </a>      
    </td>
    <td class="td04140102" style="text-align:center; width:10%">
     <%= sortImage[3] %>
     <a class="a0414" title="Variety of the Fruit" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      <b>Variety</b>
     </a>      
    </td>
    <td class="td04140102" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0414" title="Grade - the grade of the fruit // how good the fruit quality is" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      <b>Grade</b>
     </a>      
    </td>
    <td class="td04140102" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0414" title="Organic / Conventional / Baby Food" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      <b>Fruit Type</b>
     </a>      
    </td>
    <td class="td04140102" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0414" title="The fruit is Free of all Stickers" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      <b>Sticker Free</b>
     </a>      
    </td>
    <td class="td04140324" style="text-align:center; width:15%"><acronym title="Only Bins will be Entered, whole numbers, Pounds and Tons will be calculated if needed"><b>Available Inventory<br>Bins</b></acronym></td>
<%
   if (!inqAvailFruit.getInqFruitAvailToPurchase().trim().equals("Y"))
   {
%>    
    <td class="td04140324" style="text-align:center; width:15%"><acronym title="Total up all the Scheduled Loads, Pounds and Tons will be calculated if needed"><b>Scheduled Inventory<br>Bins</b></acronym></td>
    <td class="td04140102" style="text-align:center; width:15%"><acronym title="Difference between the Available Fruit and the Scheduled Loads, Pounds and Tons will be calculated if needed"><b>Balance to Schedule<br>Bins</b></acronym></td>
<%
    }
%>    
   </tr>
<%
  // that totals and math need to be done?
  BigDecimal totalAvailBins = new BigDecimal("0");
  BigDecimal totalSchedBins = new BigDecimal("0");
  BigDecimal totalBalanceBins = new BigDecimal("0");
  int lineCount = 0;
  String saveValue = "";
   if (detailInfo.size() > 0)
  {
    for (int x = 0; x < detailInfo.size(); x++)
    {
      AvailFruitByWhseDetail thisRow = new AvailFruitByWhseDetail();
      try
      {
         thisRow = (AvailFruitByWhseDetail) detailInfo.elementAt(x);
      }
      catch(Exception e)
      {}
      String showSchedBins = thisRow.getScheduledQty();
      if (showSchedBins.trim().equals(""))
        showSchedBins = "0";
      String showBalanceBins = "0";
      try{
         showBalanceBins = ((new BigDecimal(thisRow.getBinCount())).subtract(new BigDecimal(showSchedBins))).toString();  
      }catch(Exception e)
      {} 
       if (new BigDecimal(showBalanceBins.trim()).compareTo(new BigDecimal("0")) > 0 ||
           inqAvailFruit.getRequestType().trim().equals("listAvailFruitAll"))
       {
          lineCount++;
          String rowClass = "";
          String dataClass1 = "td04140102";
          String dataClass2 = "td04140324";
          boolean testDuplicate = false;
          if (saveValue.trim().equals(thisRow.getWhseNumber().trim() + thisRow.getWhseAddressNumber().trim() + thisRow.getCropCode().trim() + thisRow.getVarietyCode().trim() + thisRow.getGradeCode().trim() + thisRow.getOrganicCode().trim() + thisRow.getStickerFree().trim()))
            testDuplicate = true;
          saveValue = thisRow.getWhseNumber().trim() + thisRow.getWhseAddressNumber().trim() + thisRow.getCropCode().trim() + thisRow.getVarietyCode().trim() + thisRow.getGradeCode().trim() + thisRow.getOrganicCode().trim() + thisRow.getStickerFree().trim();
          if (!thisRow.getDuplicated().trim().equals(""))
          {
             rowClass = "class=\"tr03\"";
             dataClass1 = "td00140102";
             dataClass2 = "td00140324";
          }
          String showAvailBins = thisRow.getBinCount().trim();
          if (testDuplicate)
            showBalanceBins = showAvailBins.trim();
          // Add for the Totals
          try{
             totalAvailBins = totalAvailBins.add(new BigDecimal(showAvailBins));
          }catch(Exception e)
          {}
          try{
              totalSchedBins = totalSchedBins.add(new BigDecimal(showSchedBins));
          }catch(Exception e)
          {}
          try{
            totalBalanceBins = totalBalanceBins.add(new BigDecimal(showBalanceBins));
          }catch(Exception e)
          {}     
          showAvailBins = HTMLHelpersMasking.maskBigDecimal(showAvailBins, 0);
          showSchedBins = HTMLHelpersMasking.maskBigDecimal(showSchedBins, 0);
          showBalanceBins = HTMLHelpersMasking.maskBigDecimal(showBalanceBins, 0);
%>       
   <tr <%= rowClass %>>     
<%
  if (inqAvailFruit.getRequestType().trim().equals("listAvailFruit"))
  {
%>   
    <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= HTMLHelpersInput.inputCheckBox(("chooseRow" + lineCount), "", "N") %>&nbsp;</td>
<%
   }
%> 
    <td class="<%= dataClass1 %>" style="text-align:center"><%= thisRow.getWhseDescription() %>&nbsp;(<%= thisRow.getWhseNumber() %>)</td>
     <%= HTMLHelpersInput.inputBoxHidden(("whseNo" + lineCount), thisRow.getWhseNumber()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("whseName" + lineCount), thisRow.getWhseDescription()) %>
    <td class="<%= dataClass1 %>" style="text-align:center"><%= thisRow.getWarehouse().getWarehouseDescription() %>&nbsp;(<%= thisRow.getWhseAddressNumber() %>)</td>
     <%= HTMLHelpersInput.inputBoxHidden(("locAddNo" + lineCount), thisRow.getWhseAddressNumber()) %> 
     <%= HTMLHelpersInput.inputBoxHidden(("whseAddressName" + lineCount), thisRow.getWarehouse().getWarehouseDescription()) %> 
    <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= thisRow.getCropCodeDesc() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("crop" + lineCount), thisRow.getCropCode()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("cropDescription" + lineCount), thisRow.getCropCodeDesc()) %>
    <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= thisRow.getVarietyDesc() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("variety" + lineCount), thisRow.getVarietyCode()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("varietyDescription" + lineCount), thisRow.getVarietyDesc()) %>
    <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= thisRow.getGradeDesc() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("grade" + lineCount), thisRow.getGradeCode()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("gradeDescription" + lineCount), thisRow.getGradeDesc()) %>
    <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= thisRow.getOrganicDesc() %>&nbsp;</td>
     <%= HTMLHelpersInput.inputBoxHidden(("organic" + lineCount), thisRow.getOrganicCode()) %>
     <%= HTMLHelpersInput.inputBoxHidden(("organicDescription" + lineCount), thisRow.getOrganicDesc()) %>
    <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;
<%
   if (!thisRow.getStickerFree().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
   }
%>       
    </td>
     <%= HTMLHelpersInput.inputBoxHidden(("stickerFree" + lineCount), thisRow.getStickerFree()) %>
    <td class="<%= dataClass2 %>" style="text-align:right"><%= showAvailBins %></td>      
     <%= HTMLHelpersInput.inputBoxHidden(("availableBins" + lineCount), showAvailBins) %> 
<%
   if (!inqAvailFruit.getInqFruitAvailToPurchase().trim().equals("Y"))
   {
%>         
    <td class="<%= dataClass2 %>" style="text-align:right"><%= showSchedBins %></td>      
     <%= HTMLHelpersInput.inputBoxHidden(("scheduledBins" + lineCount), showSchedBins) %> 
    <td class="<%= dataClass1 %>" style="text-align:right"><%= showBalanceBins %></td>  
     <%= HTMLHelpersInput.inputBoxHidden(("balanceBins" + lineCount), showBalanceBins) %> 
<%
   }
%>     
   </tr>
<%      
       } // show row with a Balance
    } // end of the for loop
  }
%>
   <%= HTMLHelpersInput.inputBoxHidden("countDetail", (lineCount + "")) %>
     <tr class = "tr02">
<%   
    if (inqAvailFruit.getRequestType().trim().equals("listAvailFruit"))    
    {
%>    
    <td class="td04160102">&nbsp;</td>
<%
   }
%>    
      <td class="td04160102" colspan = "7"><b>&nbsp;TOTALS:</b>&nbsp;</td>
      <td class="td04160324" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalAvailBins.toString(),0) %></b>&nbsp;</td>
<%
   if (!inqAvailFruit.getInqFruitAvailToPurchase().trim().equals("Y"))
   {
%>          
      <td class="td04160324" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalSchedBins.toString(),0) %></b>&nbsp;</td>
      <td class="td04160102" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalBalanceBins.toString(),0) %></b>&nbsp;</td>
<%
   }
%>      
     </tr>  
     </table>   
   </body>
</html>