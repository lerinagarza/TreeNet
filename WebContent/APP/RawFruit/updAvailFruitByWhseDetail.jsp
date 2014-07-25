<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.math.*" %>
<%
//---------------- updAvailFruitByWhseDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  8/9/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 Vector detailInfo = new Vector();
 UpdAvailableFruit updMain = new UpdAvailableFruit();
 AvailFruitByWhse afInfo = new AvailFruitByWhse();
 String readOnlyDetail = "";
 int cntImageDetail = 0;
 try
 {
	updMain = (UpdAvailableFruit) request.getAttribute("updViewBean");
    afInfo = updMain.beanAvailFruit.getAvailFruitByWhse();
	detailInfo = updMain.getListAvailFruitDetail();
	readOnlyDetail = updMain.getReadOnly();
	cntImageDetail = new Integer((String) request.getAttribute("imageCount")).intValue();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//--------------------------------------------------------
// Build data for Drop Down Dual options (10 rows)
%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
<%
   for (int x = 0; x < updMain.getDualDropDown10Blank().size(); x++)
   {
      out.println((String) ((Vector) updMain.getDualDropDown10Blank().elementAt(x)).elementAt(2));
      out.println((String) ((Vector) updMain.getDualDropDown10Blank().elementAt(x)).elementAt(3));
   }
%>  
 </head>
 <body>
  <table class="table00" cellspacing="0" cellpadding="1">
   <tr class = "tr02">
    <td class="td04140102" style="text-align:center; width:13%"><acronym title="Type of Fruit"><b>Crop</b></acronym></td>
    <td class="td04140102" style="text-align:center; width:13%"><acronym title="Variety of the Fruit"><b>Variety</b></acronym></td>
    <td class="td04140102" style="text-align:center; width:13%"><acronym title="Grade - the grade of the fruit // how good the fruit quality is"><b>Grade</b></acronym></td>
    <td class="td04140102" style="text-align:center"><acronym title="Organic / Conventional / Baby Food"><b>Fruit Type</b></acronym></td>
    <td class="td04140102" style="text-align:center"><acronym title="Sticker Free Fruit"><b>Sticker Free</b></acronym></td>
    <td class="td04140324" style="text-align:center; width:15%"><acronym title="Only Bins will be Entered, whole numbers, Pounds and Tons will be calculated if needed"><b>Available Inventory<br>Bins</b></acronym></td>
    <td class="td04140324" style="text-align:center; width:15%"><acronym title="Total up all the Scheduled Loads, Pounds and Tons will be calculated if needed"><b>Scheduled Inventory<br>Bins</b></acronym></td>
    <td class="td04140102" style="text-align:center; width:15%"><acronym title="Difference between teh Available Fruit and the Scheduled Loads, Pounds and Tons will be calculated if needed"><b>Balance of Inventory<br>Bins</b></acronym></td>
   </tr>
<%
  BigDecimal needReschedule = new BigDecimal("0");
  try{
      needReschedule = new BigDecimal(afInfo.getAllScheduledQtyTotal()).subtract(new BigDecimal(afInfo.getAvailScheduledQtyTotal()));
      if (needReschedule.compareTo(new BigDecimal("0")) > 0)
      {
%>
  <tr class = "tr03">
    <td class="td00160324" style="text-align:center" colspan = "5"><b>This Inventory Is On The Schedule To Be Received But No Longer Avalilable from the Warehouse:</b></td>
    <td class="td00160324" style="text-align:right"><b><%= needReschedule %></b></td>
    <td class="td00160102" style="text-align:center">&nbsp;</td>
   </tr>
<%
      }
  }
  catch(Exception e)
  {}
  
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
       boolean testDuplicate = false;
       lineCount++;
       UpdAvailableFruitDetail updLine = new UpdAvailableFruitDetail();
       String rowClass = "";
       String dataClass1 = "td04140102";
       String dataClass2 = "td04140324";
       String dataClass3 = "td03140324";
       try
       {
          updLine = (UpdAvailableFruitDetail) detailInfo.elementAt(x);
          if (saveValue.trim().equals(updLine.getCrop().trim() + updLine.getVariety().trim() + updLine.getGrade().trim() + updLine.getOrganic().trim() + updLine.getStickerFree()))
            testDuplicate = true;
          saveValue = updLine.getCrop().trim() + updLine.getVariety().trim() + updLine.getGrade().trim() + updLine.getOrganic().trim() + updLine.getStickerFree();
          if (!updLine.getDuplicateKeyFlag().trim().equals(""))
          {
             rowClass = "class=\"tr03\"";
             dataClass1 = "td00140102";
             dataClass2 = "td00140324";
             dataClass3 = "td00140324";
          }
       }
       catch(Exception e)
       {}
%>       
    <tr <%= rowClass %>>
<%
    if (readOnlyDetail.trim().equals("Y")){
%>    
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getCrop().trim() %>&nbsp;</td>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getVariety().trim() %>&nbsp;</td>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getGrade().trim() %>&nbsp;</td>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getOrganic().trim() %>&nbsp;</td>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;
<%
      if (!updLine.getStickerFree().trim().equals(""))
      {
        out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmarkred.gif\"/>"); 
        cntImageDetail++;
      }  
%>         
      &nbsp;</td>      
      <td class="<%= dataClass2 %>" style="text-align:right"><%= updLine.getBinQuantity().trim() %></td>
         
<%
       }else{
%>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getCropDescription() %>&nbsp;</td>
      <%= HTMLHelpersInput.inputBoxHidden(("crop" + lineCount), updLine.getCrop()) %>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getVarietyDescription() %>&nbsp;</td>
      <%= HTMLHelpersInput.inputBoxHidden(("variety" + lineCount), updLine.getVariety()) %>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getGradeDescription() %>&nbsp;</td>
      <%= HTMLHelpersInput.inputBoxHidden(("grade" + lineCount), updLine.getGrade()) %>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;<%= updLine.getOrganicDescription() %>&nbsp;</td>
      <%= HTMLHelpersInput.inputBoxHidden(("organic" + lineCount), updLine.getOrganic()) %>
      <td class="<%= dataClass1 %>" style="text-align:center">&nbsp;
<%
      if (!updLine.getStickerFree().trim().equals(""))
      {
        out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
        cntImageDetail++;
      }  
%>          
      &nbsp;</td>
      <%= HTMLHelpersInput.inputBoxHidden(("stickerFree" + lineCount), updLine.getStickerFree()) %>     
      <td class="<%= dataClass3 %>" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + lineCount), updLine.getBinQuantity().trim(), "Number of Bins", 6, 8, "N", readOnlyDetail) %>&nbsp;<%= updLine.getBinQuantityError().trim() %></td>
<%
       }
%>
      <td class="<%= dataClass2 %>" style="text-align:right">
<%
   if (testDuplicate)
      out.println("0");
   else
      out.println(HTMLHelpersMasking.maskBigDecimal(updLine.getBinQuantitySched().trim(),0));
%>      
      &nbsp;</td>
      <td class="<%= dataClass1 %>" style="text-align:right">
<%
   if (testDuplicate)
      out.println(HTMLHelpersMasking.maskBigDecimal(updLine.getBinQuantity().trim(),0));
   else
      out.println(HTMLHelpersMasking.maskBigDecimal(updLine.getBinQuantityBalance().trim(),0));
%>            
      &nbsp;</td>
     </tr>     

<%
       try{
          totalAvailBins = totalAvailBins.add(new BigDecimal(updLine.getBinQuantity()));
       }catch(Exception e)
       {}
       try{
         if (!testDuplicate)
           totalSchedBins = totalSchedBins.add(new BigDecimal(updLine.getBinQuantitySched()));
       }catch(Exception e)
       {}
       try{
         if (testDuplicate)
            totalBalanceBins = totalBalanceBins.add(new BigDecimal(updLine.getBinQuantity()));
         else
            totalBalanceBins = totalBalanceBins.add(new BigDecimal(updLine.getBinQuantityBalance()));
       }catch(Exception e)
       {}   
    } // end of the for loop
  }
  if (!readOnlyDetail.trim().equals("Y"))
  {
  // This will set 10 blanks to start with, and then keep an additional 5 for all time
  int blankCount = 10;
  if (lineCount > 0)
     blankCount = lineCount + 5;
   int countBlanks = 0;
   for (int x = lineCount; x < blankCount; x++)
   {
      lineCount++;
%>   
     <tr>
      <td class="td03140102" style="text-align:center"><%= (String) ((Vector) updMain.getDualDropDown10Blank().elementAt(countBlanks)).elementAt(0) %></td>
      <td class="td03140102" style="text-align:center"><%= (String) ((Vector) updMain.getDualDropDown10Blank().elementAt(countBlanks)).elementAt(1) %></td>
      <td class="td03140102" style="text-align:center"><%= DropDownSingle.buildDropDown(updMain.getDdGrade(), ("grade" + lineCount), "", "", "N", readOnlyDetail) %></td>
      <td class="td03140102" style="text-align:center"><%= DropDownSingle.buildDropDown(updMain.getDdOrganic(), ("organic" + lineCount), "", "", "N", readOnlyDetail) %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputCheckBox(("stickerFree" + lineCount), "", readOnlyDetail) %>&nbsp;</td>
      <td class="td03140324" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + lineCount), "0", "Number of Bins", 6, 8, "N", readOnlyDetail) %>&nbsp;</td>
      <td class="td03140324" style="text-align:center">&nbsp;&nbsp;</td>
      <td class="td03140102" style="text-align:center">&nbsp;&nbsp;</td>
     </tr>
<%
     countBlanks++;
      }
  } // end of the if the person is allowed to update
   request.setAttribute("imageCount", (cntImageDetail + ""));
%>
   <%= HTMLHelpersInput.inputBoxHidden("countDetail", (lineCount + "")) %>
     <tr class = "tr02">
      <td class="td04160102" colspan = "5"><b>&nbsp;TOTALS:</b>&nbsp;</td>
      <td class="td04160324" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalAvailBins.toString(),0) %></b>&nbsp;</td>
      <td class="td04160324" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalSchedBins.toString(),0) %></b>&nbsp;</td>
      <td class="td04160102" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalBalanceBins.toString(),0) %></b>&nbsp;</td>
     </tr>  
     </table>   
   </body>
</html>