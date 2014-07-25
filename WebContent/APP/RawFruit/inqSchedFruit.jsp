<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.InqScheduledFruit" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- inqSchedFruit.jsp ------------------------------//
//  Author :  Teri Walton  09/22/10
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Search for Scheduled Loads of Fruit";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqScheduledFruit inqSched = new InqScheduledFruit();
 try
 {
	inqSched = (InqScheduledFruit) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
   System.out.println("should never get here, updSchedFruit.jsp: " + e);
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   Vector headerInfo = new Vector();
    headerInfo.addElement(inqSched.getEnvironment());
    headerInfo.addElement(updTitle); // Element 1 is always the Title of the Page
    
   StringBuffer setExtraOptions = new StringBuffer();
//   if (ir.getAllowUpdate().equals("Y"))
//      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a Load");
//   setExtraOptions.append("<option value=\"/web/CtlQuality?requestType=inqFormula\">Select Again");
//   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket&");
//   setExtraOptions.append(ir.buildParameterResend());
//   setExtraOptions.append("\">Return To Selection Screen");
//   if (!is.getAllowUpdate().equals(""))   
//     setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=addCPGSpec\">Add New Specification");
      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=addSchedFruitLoad&environment=" + inqSched.getEnvironment() + "&originalRequestType=inqSchedFruit" +  "\">Add a NEW Scheduled Load");      
      setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=schedTransfer&environment=" + inqSched.getEnvironment() + "&originalRequestType=inqSchedFruit" +  "\">Add Transfer Loads");     
   headerInfo.addElement(setExtraOptions.toString());
//*****************************************************************************
 String formName = "inqSched";
 request.setAttribute("formName", formName);
%>
<html>
 <head>
  <title><%= updTitle %></title>
  <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getClickButtonOnlyOnce() %>
  <%= JavascriptInfo.getChangeSubmitButton() %>   
  <%= JavascriptInfo.getCalendarHead() %>
<%
   try
   {
    // if (inqSched.getDualDropDownWarehouse().size() == 4)
    // {
    //   out.println((String) inqSched.getDualDropDownWarehouse().elementAt(2));
    //   out.println((String) inqSched.getDualDropDownWarehouse().elementAt(3));
    // }
     out.println((String) inqSched.getDualDropDownCropVariety().elementAt(2));
     out.println((String) inqSched.getDualDropDownCropVariety().elementAt(3));
     out.println((String) inqSched.getDualDropDownReceivingLocation().elementAt(2));
     out.println((String) inqSched.getDualDropDownReceivingLocation().elementAt(3));
     out.println((String) inqSched.getDualDropDownShippingLocation().elementAt(2));
     out.println((String) inqSched.getDualDropDownShippingLocation().elementAt(3));
   }catch(Exception e)
   {}
%>
  <%= inqSched.getTripleDropDownRegion().elementAt(0) %>
  <%= inqSched.getTripleDropDownRegion().elementAt(1) %>
 </head>
 <body>
  <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
  <form  name = "<%= formName %>" action="/web/CtlRawFruit?requestType=listSchedFruit" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", inqSched.getEnvironment()) %> 
 <table class="table00" cellspacing="0" style="width:100%">
  <tr>
   <td style="width:2%">&nbsp;</td>
   <td>
    <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!inqSched.getDisplayMessage().trim().equals(""))
   {
%>      
     <tr class="tr00">
      <td class="td03200102">&nbsp;</td>
      <td class="td03200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= inqSched.getDisplayMessage().trim() %></b></td>
      <td class="td03200102">&nbsp;</td>
     </tr>    
<%
   }
%>    
      <tr class="tr02">
      <td class="td0420" colspan = "4"><b> Choose:</b></td>
      <td class="td0420" style="text-align:right" colspan = "3"><b>and then press</b>
       <%= HTMLHelpers.buttonSubmitRight("saveButton", "List Scheduled Loads of Fruit") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>  
     </tr>
     <tr class="tr00">
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102" style="width:20%"><acronym title="Scheduled Load Number"><b>Scheduled Load Number:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputBoxNumber("inqScheduledLoadNumber", inqSched.getInqScheduledLoadNumber(), "Scheduled Load Number", 10, 10, "N", "N") %></td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102" style="width:20%"><acronym title="Choose a Status"><b>Load Status:</b></acronym></td>
      <td class="td04140102" style="width:27%">&nbsp;<%= inqSched.getSingleDropDownStatus() %></td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
     </tr>  
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Date product is scheduled to be received"><b>From Receiving Date:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputBoxDate("inqDeliveryDateFrom", inqSched.getInqDeliveryDateFrom(), "funcDateFrom", "N", "N") %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Date product is scheduled to be received"><b>To Receiving Date:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputBoxDate("inqDeliveryDateTo", inqSched.getInqDeliveryDateTo(), "funcDateTo", "N", "N") %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>  
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Region"><b>Region (North/South):</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getTripleDropDownRegion().elementAt(2) %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Facility - does not directly tie to the Whse for this inquiry"><b>Facility:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getSingleDropDownFacility() %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>        
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Warehouse from the Chosen Region -- M3 Supplier"><b>Warehouse:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getTripleDropDownRegion().elementAt(3) %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Receiving Location -- Where the Loads will be Delivered -- is an M3 Warehouse"><b>Receiving Location:</b></acronym></td>
      <td class="td03140102">&nbsp;<%= (String) inqSched.getDualDropDownReceivingLocation().elementAt(0) %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>       
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Location - Associated Directly to the M3 Supplier (Warehouse)"><b>Location:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getTripleDropDownRegion().elementAt(4) %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Dock associated to the Receiving Location -- The only Receiving Location that has a dock should be 901"><b>Dock:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= (String) inqSched.getDualDropDownReceivingLocation().elementAt(1) %><%= HTMLHelpersInput.inputCheckBox("inqBulkOnly", inqSched.getInqBulkOnly(), "") %>Bulk Only?</td>
      <td class="td04140102">&nbsp;</td>
     </tr>          
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Crop -- by choosing this it will change the Variety Drop Down List"><b>Crop:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= (String) inqSched.getDualDropDownCropVariety().elementAt(0) %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Hauling Company -- Which Company will we have Haul the Fruit"><b>Hauling Company:</b></acronym></td>
      <td class="td03140102">&nbsp;<%= inqSched.getSingleDropDownHaulingCompany() %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>  
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Variety -- Variety is directly reliant upon the Crop."><b>Variety:</b></acronym></td>
      <td class="td03140102">&nbsp;<%= (String) inqSched.getDualDropDownCropVariety().elementAt(1) %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
     </tr> 
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Fruit Grade"><b>Grade:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getSingleDropDownGrade() %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Field Representative"><b>Field Representative:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getSingleDropDownFieldRepresentative() %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>   
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Fruit Type"><b>Fruit Type:</b></acronym></td>
      <td class="td03140102">&nbsp;<%= inqSched.getSingleDropDownOrganic() %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Click if you want the PRICE to show on the list page"><b>Show Price:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqShowPrice", inqSched.getInqShowPrice(), "") %></td>
      <td class="td04140102">&nbsp;</td>
     </tr> 
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Click if you want to only see Loads with Sticker Free Fruit on them"><b>Sticker Free:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqStickerFree", inqSched.getInqStickerFree(), "") %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Click if you want the Comments for the Scheduled to show on the list page, keep in mind this may slow down the process."><b>Show Comments:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqShowComments", "Y", "") %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>    
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Click if you want to only see Cash Loads"><b>Cash Fruit:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqCashFruit", inqSched.getInqCashFruit(), "") %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Click if you want the List page to be ordered alphabetically by Supplier Name."><b>Order by Supplier:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqOrderBySupplier", inqSched.getInqOrderBySupplier(), "") %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>    
     <tr class="tr02">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102" colspan="5">For Transfers</td>
      <td class="td04140102">&nbsp;</td>
     </tr>         
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="View Transfer Load Information"><b>Filter for Transfer Load:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getSingleDropDownTransfer() %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Facility - does not directly tie to the Whse for this inquiry"><b>Shipping Facility:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqSched.getSingleDropDownShippingFacility() %></td>     
      <td class="td04140102">&nbsp;</td>
     </tr>         
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Shipping Location -- Where the Loads will be Shipped From -- is an M3 Warehouse"><b>Shipping Location:</b></acronym></td>
      <td class="td03140102">&nbsp;<%= (String) inqSched.getDualDropDownShippingLocation().elementAt(0) %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>         
     <tr class="tr00">
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Dock associated to the Shipping Location -- The only Shipping Location that has a dock should be 901"><b>Dock:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= (String) inqSched.getDualDropDownShippingLocation().elementAt(1) %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>              
    </table>
   </td>
   <td style="width:2%">&nbsp;</td>
  </tr>
 </table>
 </form>
 <%= JavascriptInfo.getCalendarFoot(formName, "funcDateFrom", "inqDeliveryDateFrom") %>
 <%= JavascriptInfo.getCalendarFoot(formName, "funcDateTo", "inqDeliveryDateTo") %>
 <%= HTMLHelpers.pageFooterTable(inqSched.getRequestType()) %>

   </body>
</html>