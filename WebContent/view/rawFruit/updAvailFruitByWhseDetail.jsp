<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "
    com.treetop.app.rawfruit.UpdAvailableFruit,
    com.treetop.app.rawfruit.UpdAvailableFruitDetail,
    com.treetop.businessobjects.Warehouse,
    com.treetop.businessobjects.KeyValue,
    com.treetop.businessobjects.AvailFruitByWhse,
    com.treetop.utilities.html.DropDownSingle,
    com.treetop.utilities.html.HTMLHelpersInput,
    com.treetop.utilities.html.HTMLHelpersMasking,
    java.util.Vector,
    java.math.BigDecimal
    
    " %>
<%
//---------------- updAvailFruitByWhseDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  8/9/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------

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
 // request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//--------------------------------------------------------
// Build data for Drop Down Dual options (10 rows)
%>


    
    <%
   for (int x = 0; x < updMain.getDualDropDown10Blank().size(); x++)
   {
      out.println((String) ((Vector) updMain.getDualDropDown10Blank().elementAt(x)).elementAt(2));
      out.println((String) ((Vector) updMain.getDualDropDown10Blank().elementAt(x)).elementAt(3));
   }
%>  
<%-- Form tag located in updAvailFruitByWhse.jsp --%>

	<table class="styled full-width">
	   <style scoped>
	       tr.duplicate {
	           background-color:#cd0a0a;
	           color:#fff;
	       }
	   </style>
	   <colgroup>
	       <col span="5">
	       <col>
	       <col>
	       <col>
	   </colgroup>
		<tr>
			<th colspan="5"></th>
			<th colspan="3">
			    Bins
			</th>
		</tr>
        <tr>
            <th title="Type of Fruit">
                Crop
            </th>
            <th title="Variety of the Fruit">
                Variety
            </th>
            <th title="Grade - the grade of the fruit // how good the fruit quality is">
                Grade
            </th>
            <th title="Organic / Conventional / Baby Food">
                Fruit Type
            </th>
            <th title="Sticker Free Fruit">
                Sticker Free
            </th>
            <th style="width:90px;" title="Only Bins will be Entered, whole numbers, Pounds and Tons will be calculated if needed">
                Available
            </th>
            <th style="width:90px;" title="Total up all the Scheduled Loads, Pounds and Tons will be calculated if needed">
                Scheduled
            </th>
            <th style="width:90px;" title="Difference between the Available Fruit and the Scheduled Loads, Pounds and Tons will be calculated if needed">
                Balance
            </th>
        </tr>
		
		
<%  BigDecimal needReschedule = new BigDecimal("0");
    try {
      needReschedule = new BigDecimal(afInfo.getAllScheduledQtyTotal()).subtract(new BigDecimal(afInfo.getAvailScheduledQtyTotal()));
      if (needReschedule.compareTo(new BigDecimal("0")) > 0) {
%>
	<tr>
		<td class="center" colspan="5">
		    This Inventory Is On The Schedule To Be Received But No Longer Available from the Warehouse:
	    </td>
		<td class="right"><b><%= needReschedule %></b></td>
		<td></td>
	</tr>
<%      }
  } catch(Exception e) {}
%>
  
  
  
  <%
  
  // that totals and math need to be done?
  BigDecimal totalAvailBins = BigDecimal.ZERO;
  BigDecimal totalSchedBins = BigDecimal.ZERO;
  BigDecimal totalBalanceBins = BigDecimal.ZERO;
  int lineCount = 0;
  String saveValue = "";

    for (int i = 0; !detailInfo.isEmpty() && i<detailInfo.size(); i++) {
       boolean testDuplicate = false;
       lineCount++;
       UpdAvailableFruitDetail updLine = new UpdAvailableFruitDetail();
       String rowClass = "";

       try {
          updLine = (UpdAvailableFruitDetail) detailInfo.elementAt(i);
          if (saveValue.trim().equals(updLine.getCrop().trim() + updLine.getVariety().trim() + updLine.getGrade().trim() + updLine.getOrganic().trim() + updLine.getStickerFree()))
            testDuplicate = true;
          saveValue = updLine.getCrop().trim() + updLine.getVariety().trim() + updLine.getGrade().trim() + updLine.getOrganic().trim() + updLine.getStickerFree();
          if (!updLine.getDuplicateKeyFlag().trim().equals("")) {
             rowClass = "duplicate";
          }
       } catch(Exception e) {}
%>       
        <tr class="<%= rowClass %>">
<%  if (readOnlyDetail.trim().equals("Y")) { %>    
<%--Fruit varieties detail - update --%>
			<td>
			 <%= updLine.getCrop().trim() %>
			</td>
			<td>
			 <%= updLine.getVariety().trim() %>
			</td>
			<td>
			 <%= updLine.getGrade().trim() %>
			</td>
			<td>
			 <%= updLine.getOrganic().trim() %>
			</td>
			<td class="center">
				<%  if (!updLine.getStickerFree().trim().equals("")) { 
				          cntImageDetail++;%>
				    <span class="ui-icon ui-icon-check" style="margin-left:40%;"></span>
				<%  } %>         
			</td>      
            <td class="right">
                <%= updLine.getBinQuantity().trim() %>
            </td>

<%  } else { %>
<%--Fruit varieties detail - read-only --%>
			<td>
			    <%= updLine.getCropDescription() %>
			    <%= HTMLHelpersInput.inputBoxHidden(("crop" + lineCount), updLine.getCrop()) %>
			</td>
			    
			<td>
				<%= updLine.getVarietyDescription() %>
				<%= HTMLHelpersInput.inputBoxHidden(("variety" + lineCount), updLine.getVariety()) %>
			</td>
			    
			<td>
				<%= updLine.getGradeDescription() %>
				<%= HTMLHelpersInput.inputBoxHidden(("grade" + lineCount), updLine.getGrade()) %>
			</td>

			<td>
				<%= updLine.getOrganicDescription() %>
				<%= HTMLHelpersInput.inputBoxHidden(("organic" + lineCount), updLine.getOrganic()) %>
			</td>

			<td class="center">
                <%  if (!updLine.getStickerFree().trim().equals("")) { 
                          cntImageDetail++;%>
                    <span class="ui-icon ui-icon-check" style="margin-left:40%;"></span>                  
                <%  } %>
                <%= HTMLHelpersInput.inputBoxHidden(("stickerFree" + lineCount), updLine.getStickerFree()) %>      
            </td>
                 
            <td class="right">
            <%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + lineCount), updLine.getBinQuantity().trim(),
             "Number of Bins", 6, 8, "N", readOnlyDetail) %>
            <%= updLine.getBinQuantityError().trim() %>
            </td>
<%  } //end if detail %>



<%--Schedule and Balance --%>

            <td class="right">
				<%  if (testDuplicate) { %>
				      0
				<%  } else { %>
				      <%=HTMLHelpersMasking.maskBigDecimal(updLine.getBinQuantitySched().trim(),0) %>
				<%  } %>
            </td>
            <td class="right">
                <%  if (testDuplicate) { %>
                      <%=HTMLHelpersMasking.maskBigDecimal(updLine.getBinQuantity().trim(),0) %>
                <%  } else { %>
                      <%=HTMLHelpersMasking.maskBigDecimal(updLine.getBinQuantityBalance().trim(),0) %>
                <%  } %> 
            </td>
        </tr>     

<%
try {
    totalAvailBins = totalAvailBins.add(new BigDecimal(updLine.getBinQuantity()));
} catch(Exception e) {}

try {
	if (!testDuplicate) {
	   totalSchedBins = totalSchedBins.add(new BigDecimal(updLine.getBinQuantitySched()));
	}
} catch(Exception e) {}

try {
	if (testDuplicate) {
	   totalBalanceBins = totalBalanceBins.add(new BigDecimal(updLine.getBinQuantity()));
	} else {
	   totalBalanceBins = totalBalanceBins.add(new BigDecimal(updLine.getBinQuantityBalance()));
	}
} catch(Exception e) {}
%>    

<% } // end of the for loop %> 




<%  
if (!readOnlyDetail.trim().equals("Y")) {
	// This will set 10 blanks to start with, and then keep an additional 5 for all time
	int blankCount = 10;
	if (lineCount > 0) {
	   blankCount = lineCount + 5;
	}
	int countBlanks = 0;
	for (int x = lineCount; x < blankCount; x++) {
	   lineCount++;
%>   
		<tr>
			<td>
			    <%= (String) ((Vector) updMain.getDualDropDown10Blank().elementAt(countBlanks)).elementAt(0) %>
			</td>
			<td>
			    <%= (String) ((Vector) updMain.getDualDropDown10Blank().elementAt(countBlanks)).elementAt(1) %>
			</td>
			<td>
			    <%= DropDownSingle.buildDropDown(updMain.getDdGrade(), ("grade" + lineCount), "", "", "N", readOnlyDetail) %>
			</td>
			<td>
			    <%= DropDownSingle.buildDropDown(updMain.getDdOrganic(), ("organic" + lineCount), "", "", "N", readOnlyDetail) %>
			</td>
			<td class="center">
			    <%= HTMLHelpersInput.inputCheckBox(("stickerFree" + lineCount), "", readOnlyDetail) %>
			</td>
			<td class="right">
			    <%= HTMLHelpersInput.inputBoxNumber(("binQuantity" + lineCount), "0", "Number of Bins", 6, 8, "N", readOnlyDetail) %>
			</td>
			<td></td>
			<td></td>
		</tr>
<%
     countBlanks++;
      }  // end for loop
  } // end of the if the person is allowed to update
   request.setAttribute("imageCount", (cntImageDetail + ""));
%>
   <%= HTMLHelpersInput.inputBoxHidden("countDetail", (lineCount + "")) %>
		<tr class="sub-total">
			<td colspan="5">TOTALS:</td>
			<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(totalAvailBins.toString(),0) %></td>
			<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(totalSchedBins.toString(),0) %></td>
			<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(totalBalanceBins.toString(),0) %></td>
		</tr>  
	</table>   

	
<%-- End form tag located in updAvailFruitByWhse.jsp --%>