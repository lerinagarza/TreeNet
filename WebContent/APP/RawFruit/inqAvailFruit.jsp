<%@ page language = "java" %>
<%@ page import = "com.treetop.app.rawfruit.InqAvailableFruit" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.Vector" %>
<%
//----------------------- inqAvailFruit.jsp ------------------------------//
//  Author :  Teri Walton  09/07/10
//  CHANGES:
//    Date        Name      Comments
//  ---------   --------   -------------
//-----------------------------------------------------------------------//
  String updTitle = "Search for Available Fruit";  
 // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqAvailableFruit inqAvail = new InqAvailableFruit();
 String listRequestType = "listAvailFruitAll";
 try
 {
	inqAvail = (InqAvailableFruit) request.getAttribute("inqViewBean");
	if (inqAvail.getRequestType().equals("inqAvailFruit"))
	{
	   updTitle = updTitle + " specifically to Schedule Loads";
	   listRequestType = "listAvailFruit";
	}
 }
 catch(Exception e)
 {
   System.out.println("should never get here, updAvailFruit.jsp: " + e);
 }  
//**************************************************************************//
  // Allows the Title to display in the Top Area of the Page
   Vector headerInfo = new Vector();
    headerInfo.addElement(inqAvail.getEnvironment());
    headerInfo.addElement(updTitle); // Element 1 is always the Title of the Page
    
//*****************************************************************************
 String formName = "inqAvail";
 request.setAttribute("formName", formName);
%>
<html>
 <head>
  <title><%= updTitle %></title>
  <%= HTMLHelpers.pageHeaderHeadSection("", "") %>
  <%= JavascriptInfo.getClickButtonOnlyOnce() %>
  <%= JavascriptInfo.getChangeSubmitButton() %>   
<%
//   if (inqAvail.getDualDropDownWarehouse().size() == 4)
 //  {
  //    out.println((String) inqAvail.getDualDropDownWarehouse().elementAt(2));
   //   out.println((String) inqAvail.getDualDropDownWarehouse().elementAt(3));
 //  }
   out.println((String) inqAvail.getDualDropDownCropVariety().elementAt(2)); 
   out.println((String) inqAvail.getDualDropDownCropVariety().elementAt(3)); 
%>
  <%= inqAvail.getTripleDropDownRegion().elementAt(0) %>
  <%= inqAvail.getTripleDropDownRegion().elementAt(1) %>
 </head>
 <body>
  <%= HTMLHelpers.pageHeaderTable(request, response, headerInfo) %>
  <form  name = "<%= formName %>" action="/web/CtlRawFruit?requestType=<%= listRequestType %>" method="post">
  <%= HTMLHelpersInput.inputBoxHidden("environment", inqAvail.getEnvironment()) %> 
 <table class="table00" cellspacing="0" style="width:100%">
  <tr>
   <td style="width:2%">&nbsp;</td>
   <td>
    <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!inqAvail.getDisplayMessage().trim().equals(""))
   {
%>      
     <tr class="tr00">
      <td class="td03200102">&nbsp;</td>
      <td class="td03200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= inqAvail.getDisplayMessage().trim() %></b></td>
      <td class="td03200102">&nbsp;</td>
     </tr>    
<%
   }
%>       
     <tr class="tr02">
      <td class="td0420" colspan = "4"><b> Choose:</b></td>
      <td class="td0420" style="text-align:right" colspan = "3"><b>and then press</b>
       <%= HTMLHelpers.buttonSubmitRight("saveButton", "List Available Fruit") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>  
     </tr> 
     <tr class="tr00">
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102" style="width:15%"><acronym title="Choose a Region"><b>Region (North/South):</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqAvail.getTripleDropDownRegion().elementAt(2) %></td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102" style="width:15%"><acronym title="Choose a Crop"><b>Crop:</b></acronym></td>
      <td class="td04140102">&nbsp;
       <%= (String) inqAvail.getDualDropDownCropVariety().elementAt(0) %>
      </td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
     </tr>        
     <tr class="tr00">
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Warehouse from the Chosen Region -- M3 Supplier"><b>Warehouse:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqAvail.getTripleDropDownRegion().elementAt(3) %></td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
     <td class="td04140102"><acronym title="Choose a Variety Tied to a Crop."><b>Variety:</b></acronym></td>
      <td class="td03140102">&nbsp;
       <%= (String) inqAvail.getDualDropDownCropVariety().elementAt(1) %>
      </td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
     </tr>       
     <tr class="tr00">
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Location - Associated Directly to the M3 Supplier (Warehouse)"><b>Location:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= inqAvail.getTripleDropDownRegion().elementAt(4) %></td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Grade"><b>Grade:</b></acronym></td>
      <td class="td04140102">&nbsp;
       <%= inqAvail.getSingleDropDownGrade() %>
      </td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
     </tr>       
     
     <tr class="tr00">
      <td class="td04140102" style="width:2%">&nbsp;</td>
       <td class="td04140102"><acronym title="Choose a Field Representative to Filter On"><b>Field Representative:</b></acronym></td>
      <td class="td04140102">&nbsp;
       <%= inqAvail.getSingleFieldRepresentative() %>
      </td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose a Fruit Type - Organic / Conventional / Baby Food"><b>Fruit Type:</b></acronym></td>
      <td class="td03140102">&nbsp;
       <%= inqAvail.getSingleDropDownOrganic() %>
      </td>
      <td class="td04140102" style="width:2%">&nbsp;</td>
     </tr> 
     <tr class="tr00">
      <td class="td04140102" style="width:2%">&nbsp;</td>
      <td class="td04140102"><acronym title="Will EITHER show Fruit Available to Purchase OR, Regular Available Fruit"><b>Fruit Available to Purchase:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.dropDownYesNo("inqFruitAvailToPurchase", inqAvail.getInqFruitAvailToPurchase(), "", "N") %></td>
      <td class="td04140102">&nbsp;</td>
      <td class="td04140102"><acronym title="Choose ONLY Sticker Free Fruit"><b>Sticker Free Only:</b></acronym></td>
      <td class="td04140102">&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqStickerFree", "", "N") %></td>
      <td class="td04140102">&nbsp;</td>
     </tr>   
    </table>
   </td>
   <td style="width:2%">&nbsp;</td>
  </tr>
 </table>
 </form>
 <%= HTMLHelpers.pageFooterTable(inqAvail.getRequestType()) %>

   </body>
</html>