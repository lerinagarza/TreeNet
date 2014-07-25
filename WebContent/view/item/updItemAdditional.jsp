<%@ page import = "com.treetop.businessobjectapplications.*" %> 
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.item.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%
//  Give you the ability to reserve a Resource number.
//				And Tie UPC, EPC and GTIN to a Resource
//------------ updItemAdditional.jsp---------------------//
//   to be incuded in updItem -- Was converted from updItemMain 
//  Author :  Teri Walton  02/17/06
//    -- Split out fields, to make easier to Maintain  
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  6/18/12   TWalton     Adjusted the Screens to use new Stylings
//------------------------------------------------------------//
// String errorPage = "/APP/Resource/updNewItem.jsp";
  // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdItem uiAdd = new UpdItem();
 Item itemAdd = new Item();
 String groupAdd = "";
 try
 {
	uiAdd = (UpdItem) request.getAttribute("updViewBean");
	BeanItem beani = (BeanItem) request.getAttribute("beanItem");
	itemAdd = beani.getItemClass();
	TicklerFunctionDetail thisrow = (TicklerFunctionDetail) beani.getFunctions().elementAt(0);
	groupAdd = thisrow.getGroup();
 }
 catch(Exception e)
 {
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }   
  String readOnly = "Y";
   if (UpdItem.getSecurity(request, response, itemAdd.getResponsible()).equals("Y"))
     readOnly = "N";
   request.setAttribute("readOnly", readOnly);
%>
  <table class="styled">
<%
   if (groupAdd.equals("100-CPGItem") 
      || groupAdd.equals("110-CPACK-FG") 
      || groupAdd.equals("120-Dried-Frozen")
      || groupAdd.equals("125-Formulated") 
      || groupAdd.equals("130-Fresh Slice") 
      || groupAdd.equals("140-Puree")
      || groupAdd.equals("150-Conc-Manufactured")
      || groupAdd.equals("210-ProcFruit-Manufactured"))
   {
%>   
   <tr>
    <td style="width:30%">Open Order Report:</td>
    <td colspan="2">
<%
   if (!itemAdd.getM3ItemAliasOpenOrders().trim().equals(""))
      out.println("This Item is Assigned to the Open Order Report, change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up on the Open Orders Report\">" + 
                  HTMLHelpersInput.inputCheckBox("flagOPN", "", readOnly) + "</aronym>");
   }
%>     
      &#160;
    </td>
   </tr>
   <tr>
    <td>Fresh Pack Report:</td>
    <td colspan = "2">
<%
   if (!itemAdd.getM3ItemAliasFreshPack().trim().equals(""))
      out.println("This Item is Assigned to the Fresh Pack Report, change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up on the Fresh Pack Report\">" + 
                  HTMLHelpersInput.inputCheckBox("flagFPK", "", readOnly) + "</aronym>");
   }
%>     
      &#160;
    </td>
   </tr>   
   <tr>
    <td>CAR Report:</td>
    <td colspan="2">
<%
   if (!itemAdd.getM3ItemAliasCAR().trim().equals(""))
      out.println("This Item is Assigned to the CAR Report, change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up on the CAR Report\">" + 
                  HTMLHelpersInput.inputCheckBox("flagCAR", "", readOnly) + "</aronym>");
   }
%>     
      &#160;&#160; Item Group: <%= itemAdd.getItemGroup() %>&#160;&#160;<%= itemAdd.getItemGroupDescription() %>
       <input type="hidden" name="itemGroup" value = "<%= itemAdd.getItemGroup() %>">
    </td>
   </tr>  
   <tr>
    <td>
      <acronym title="Choose the RPT1 Alias for this specific Item. ">RPT1 Alias/Planning Size:&#160;</acronym></td>
    <td colspan="2">
<% 
	if (!itemAdd.getM3ItemAliasReport1().trim().equals(""))
	   out.println("<b>" + itemAdd.getM3ItemAliasReport1() + " - " + itemAdd.getM3ItemAliasReport1Desc().trim() + "</b>, change in M3 Alias file(MMS025) if necessary");
    else
    {
       out.println(UpdItem.buildDropDownReport1(itemAdd.getM3ItemAliasReport1().trim(), ""));   
    }
%>      
    </td>
   </tr>    
   <tr>
    <td>100 Percent Juice:</td>
    <td colspan = "2">
<%
   if (!itemAdd.getM3ItemAlias100PercentJuice().trim().equals(""))
      out.println("This Item is Assigned a 100% Juice Alias, , change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up as 100% Juice for Reporting purposes\">" + 
                  HTMLHelpersInput.inputCheckBox("flagJCE", "", readOnly) + "</aronym>");
   }
%>     
      &#160;
    </td>
   </tr>  
<%
   if (groupAdd.equals("140-Puree"))
   {
%>   
   <tr>
    <td>Is the Product Single Strength?:</td>
    <td colspan = "2">
<%
   if (!itemAdd.getM3ItemAliasSingleStrength().trim().equals(""))
      out.println("This Item is Assigned a Single Strength Alias, , change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up as Single Strength for Reporting purposes\">" + 
                  HTMLHelpersInput.inputCheckBox("flagSingleStrength", "", readOnly) + "</aronym>");
   }
%>     
      &#160;
    </td>
   </tr>
<%
   }
%>  
   <tr>
    <td>Does this Item Contain Allergens?:</td>
    <td colspan = "2">
<%
   if (!itemAdd.getM3ItemAliasAllergen().trim().equals(""))
      out.println("This Item is Assigned an Allergen Alias, change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up as Allergen for Reporting purposes\">" + 
                  HTMLHelpersInput.inputCheckBox("flagAllergen", "", readOnly) + "</aronym>");
   }
%>     
      &#160;
    </td>
   </tr>   
   <tr>
    <td>
      <acronym title="Choose the Planner that will be Planning this specific Item. ">Production Planner:&#160;</acronym></td>
    <td colspan="2">
<% 
	if (!itemAdd.getM3ItemAliasPlanner().trim().equals(""))
	   out.println("<b>" + itemAdd.getM3ItemAliasPlannerDesc().trim() + "</b>, change in M3 Alias file(MMS025) if necessary");
    else
    {
       out.println(UpdItem.buildDropDownPlanner(itemAdd.getM3ItemAliasPlanner().trim(), ""));   
    }
%>      
    </td>
   </tr>  
   <tr>
    <td>
      <acronym title="This field is for the 'OLD' Item Number that this one replaces, if applicable. ">Replaced Item Number:&#160;</acronym></td>
    <td class="error" colspan="2">
      <%= HTMLHelpersInput.inputBoxText("itemReplacing", uiAdd.getItemReplacing(), "Item Replacing", 15, 15, "N", readOnly) %>&#160;
      <%= uiAdd.getItemReplacingError() %>
    </td>
   </tr>    
   <tr>
    <td>Length:</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxNumber("length", uiAdd.getLength(), "Length", 10, 10, "", "") %>
      &#160; <%= uiAdd.getLengthError().trim() %>
    </td>
   </tr>
   <tr>
    <td>Width:</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxNumber("width", uiAdd.getWidth(), "Width", 10, 10, "", "") %>
      &#160; <%= uiAdd.getWidthError().trim() %>
    </td>
   </tr>   
   <tr>
    <td>Height:</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxNumber("height", uiAdd.getHeight(), "Height", 10, 10, "", "") %>
      &#160; <%= uiAdd.getHeightError().trim() %>
    </td>
   </tr>      
   <tr>
    <td>Manufacturer:</td>
    <td colspan="2">
      <%= UpdItem.buildDropDownManufacturer(uiAdd.getManufacturer(), "") %>
    </td>
   </tr>
   <tr>
    <td>Club Item:</td>
    <td colspan = "2">
<%
   if (!itemAdd.getM3ItemAliasClubCustItem().trim().equals("") &&
       !itemAdd.getM3ItemAliasClubCustItem().trim().equals("Y"))
   {
      out.println("This Item is a Club Item, change in M3 Alias file(MMS025) if necessary, value in M3 is <b>");
      out.println(itemAdd.getM3ItemAliasClubCustItem().trim() + "</b>");
   } else {
      if (!itemAdd.getM3ItemAliasClubCustItem().trim().equals(""))
      { // if the box has already been checked, the user can put the Club Customer Item Number in this field
        // within the service it will Delete the Alias with the Y, and add one with the actual Club Customer Item Number
          out.println("<acronym title=\"By adding a record in this field, the CCI (Alias) will change from a Y to " + 
                      "be the Actual Club Customer Item Number entered here in the Alias File in M3 " +  
                      "so this item will show up as a Club Item for Reporting purposes\">" + 
                      HTMLHelpersInput.inputBoxText("clubItemNumber", uiAdd.getClubItemNumber(), "Club Item Number", 30, 30, "N", readOnly)  +
    				  "&#160;</aronym>");
      }else{
      // IF this is checked, it will put a Y in the Alias file, and then allow the user to input an actual Club Customer Item Number
      		out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up as a Club Item for Reporting purposes\">" + 
                  HTMLHelpersInput.inputCheckBox("flagClub", "", readOnly) + "</aronym>");
      }
   }
%>     
      &#160;
    </td>
   </tr>
   <tr>
    <td colspan="3">
      <b><div class="center">Check Digit is Automatically Calculated for all UPC and GTIN Codes</div></b>
      <%
   if (itemAdd.getM3ItemAliasCaseUPC().trim().equals(""))
   {
%>    
      </br>&#160;&#160;
       <abbr title="By checking this box, the program will add the appropriate M3 alias' for LABEL anc CASE UPC and also for Pallet GTIN to the Alias file in M3."> 
                 <%= HTMLHelpersInput.inputCheckBox("aliasCheckBox", "", readOnly) %> </abbr>
      &#160;&#160;Check this Box when ready for the UPC and GTIN codes for Label, Case, and Pallet to be put into the Alias File in M3
<%   
    }
%>             
    </td>
   </tr>
   <tr>
    <td>Label UPC Code:</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxText("labelUPCA", uiAdd.getLabelUPCA(), "Label UPC Code", 1, 1, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("labelUPCB", uiAdd.getLabelUPCB(), "Label UPC Code", 5, 5, "N", readOnly) %>&#160;		
      <%= HTMLHelpersInput.inputBoxText("labelUPCC", uiAdd.getLabelUPCC(), "Label UPC Code", 5, 5, "N", readOnly) %>&#160;			
      <%= HTMLHelpersInput.inputBoxText("labelUPCD", uiAdd.getLabelUPCD(), "Label UPC Code", 1, 1, "N", "Y") %>	
      &#160;&#160;<%= uiAdd.getErrorLabelUPC() %>&#160;Current M3 Value:&#160;<%= itemAdd.getM3ItemAliasLabelUPC() %>
    </td>
   </tr>
   <tr>
    <td>Case UPC Code:</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxText("caseUPCA", uiAdd.getCaseUPCA(), "Case UPC Code", 1, 1, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("caseUPCB", uiAdd.getCaseUPCB(), "Case UPC Code", 5, 5, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("caseUPCC", uiAdd.getCaseUPCC(), "Case UPC Code", 5, 5, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("caseUPCD", uiAdd.getCaseUPCD(), "Case UPC Code", 1, 1, "N", "Y") %>
      &#160;&#160;<%= uiAdd.getErrorCaseUPC() %>&#160;Current M3 Value:&#160;<%= itemAdd.getM3ItemAliasCaseUPC() %>
    </td>
   </tr>   
<%    
   if (!itemAdd.getM3ItemAliasClubCustItem().trim().equals(""))
   { // Only if it is a Club Item  
%>   
   <tr>
    <td>Carrier UPC Code (Dog Bone):</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxText("carrierUPCA", uiAdd.getCarrierUPCA(), "Carrier UPC Code", 1, 1, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("carrierUPCB", uiAdd.getCarrierUPCB(), "Carrier UPC Code", 5, 5, "N", readOnly) %>&#160;		
      <%= HTMLHelpersInput.inputBoxText("carrierUPCC", uiAdd.getCarrierUPCC(), "Carrier UPC Code", 5, 5, "N", readOnly) %>&#160;			
      <%= HTMLHelpersInput.inputBoxText("carrierUPCD", uiAdd.getCarrierUPCD(), "Carrier UPC Code", 1, 1, "N", "Y") %>	
      &#160;&#160;<%= uiAdd.getErrorCarrierUPC() %>&#160;
    </td>
   </tr>
   <tr>
    <td>Wrap/Carton UPC Code:</td>
    <td colspan="2">
      <%= HTMLHelpersInput.inputBoxText("wrapUPCA", uiAdd.getWrapUPCA(), "Wrap UPC Code", 1, 1, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("wrapUPCB", uiAdd.getWrapUPCB(), "Wrap UPC Code", 5, 5, "N", readOnly) %>&#160;		
      <%= HTMLHelpersInput.inputBoxText("wrapUPCC", uiAdd.getWrapUPCC(), "Wrap UPC Code", 5, 5, "N", readOnly) %>&#160;			
      <%= HTMLHelpersInput.inputBoxText("wrapUPCD", uiAdd.getWrapUPCD(), "Wrap UPC Code", 1, 1, "N", "Y") %>	
      &#160;&#160;<%= uiAdd.getErrorWrapUPC() %>&#160;
    </td>
   </tr>   
<%
    }
    if (groupAdd.equals("100-CPGItem") 
        || groupAdd.equals("130-Fresh Slice") )
    {
%>
   <tr>
    <td>Pallet GTIN:</td>
    <td>
      <%= HTMLHelpersInput.inputBoxText("palletGTINA", uiAdd.getPalletGTINA(), "Pallet GTIN", 2, 2, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("palletGTINB", uiAdd.getPalletGTINB(), "Pallet GTIN", 6, 6, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("palletGTINC", uiAdd.getPalletGTINC(), "Pallet GTIN", 5, 5, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("palletGTIND", uiAdd.getPalletGTIND(), "Pallet GTIN", 1, 1, "N", "Y") %>
      &#160;&#160;<%= uiAdd.getErrorPalletGTIN() %> 
<%  
   if (!uiAdd.getErrorPalletGTIN().equals(""))
      out.println("<br>");
   if (uiAdd.getPalletGTINDesc() != null &&
       !uiAdd.getPalletGTINDesc().trim().equals(""))
      out.println(uiAdd.getPalletGTINDesc());
%> 
     &#160;Current M3 Value:&#160;<%= itemAdd.getM3ItemAliasPalletGTIN() %>
    </td>
      <td class="right">
<%
  if (!uiAdd.getPalletGTINC().equals(""))
  {
%>     
    <input type="button" value="More" class="moreButton" />
				<ul class="tooltip">
					<li><a target="_blank" href="/web/CtlGTIN?requestType=detail&gtinNumber=<%= uiAdd.getPalletGTIN() %>">Details of <%= uiAdd.getPalletGTIN() %></a></li>
					<li><a target="_blank" href="/web/CtlGTIN?requestType=update&gtinNumber=<%= uiAdd.getPalletGTIN() %>">Update <%= uiAdd.getPalletGTIN() %></a></li>
					<li><a target="_blank" href="/web/CtlGTIN?requestType=updateTies&parentGTIN=<%= uiAdd.getPalletGTIN() %>">Maintain Children of <%= uiAdd.getPalletGTIN() %></a></li>
				</ul>
<%
   }
%>				
     </td>   
   </tr>  
<%
     }
   } 
    if (groupAdd.equals("110-CPACK-SUPPLY") 
      || groupAdd.equals("150-Conc-Purch")
      || groupAdd.equals("210-ProcFruit-Purch")
      || groupAdd.equals("250-Packaging")
      || groupAdd.equals("260-290-Ingredients"))
   {  
    // Supplier Summary, only needed for Purchased finished goods and supplies
%>   
     <tr>
        <td><abbr title="Attach the Supplier Summary">Attach Supplier Summary:</abbr></td>
        <td>
<%
   if (uiAdd.getSupplierSummaryURL().trim().equals(""))
   {
%>        
        <%= HTMLHelpersInput.inputBoxUrlBrowse("supplierSummaryURL", uiAdd.getSupplierSummaryURL(), "URL for the Supplier Summary", 60, 500, "N", readOnly) %>
<%  
    }else{
%>   
        <%= HTMLHelpersInput.inputBoxHidden("supplierSummaryURL", uiAdd.getSupplierSummaryURL()) %>
        <%= HTMLHelpersInput.inputCheckBox("supplierSummaryURLRemove", "", readOnly) %>&#160;Check if want to remove the attached Supplier Summary - to then attach another&#160;
        </br><b><%= HTMLHelpersLinks.basicLink(uiAdd.getSupplierSummaryURL().trim(), uiAdd.getSupplierSummaryURL().trim(), "", "", "") %></b>     
<%
    }
%>
       &#160;</td>  
       </tr>
<%
   }
%>    
     <tr>
      <td class="right" colspan="3">
       <input type="Submit" name="saveButton" value="Save Changes" >
      </td>
     </tr>   
  </table>
