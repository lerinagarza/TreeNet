<%@ page import = "com.treetop.businessobjectapplications.*" %> 
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.item.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%
//  Give you the ability to reserve a Resource number.
//				And Tie UPC, EPC and GTIN to a Resource
//------------ updNewItemMain.jsp---------------------//
//   to be incuded in updNewItem
//  Author :  Teri Walton  02/17/06
//    -- Split out fields, to make easier to Maintain  
//  Changes:
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//------------------------------------------------------------//
// String errorPage = "/APP/Resource/updNewItem.jsp";
  // Bring in the Update View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 UpdItem uiMain = new UpdItem();
 Item item = new Item();
 String groupMain = "";
 try
 {
	uiMain = (UpdItem) request.getAttribute("updViewBean");
	BeanItem bi = (BeanItem) request.getAttribute("beanItem");
	item = bi.getItemClass();
	TicklerFunctionDetail thisrow = (TicklerFunctionDetail) bi.getFunctions().elementAt(0);
	groupMain = thisrow.getGroup();
 }
 catch(Exception e)
 {
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }   
   String readOnly = "Y";
   if (UpdItem.getSecurity(request, response, item.getResponsible()).equals("Y"))
     readOnly = "N";
   request.setAttribute("readOnly", readOnly);
%>
  <table class="table00" cellspacing="0">
    <tr class="tr00">
    <td class="td04140102">Item Number / Name:</td>
    <td class="td04140102" colspan="2"><b><%= item.getItemNumber() %></b>&#160;&#160;&#160;<%= item.getItemDescription() %></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Item Number/Status:</td>
    <td class="td04140102" colspan="2"><b><%= item.getStatus() %></b>&#160;&#160;<%= item.getStatusDescription().trim() %></td>
   </tr>
   <tr class="tr00">
    <td class="td04140105">Item Description</td>
    <td class="td04140105" colspan="2"><b><%= item.getItemLongDescription() %></b>&#160;</td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Item Responsible:</td>
    <td class="td04140102"><b><%= UpdItem.determineLongName("PRD", item.getResponsible()) %></b></td>
    <td class="td04140102" style="text-align:right; width:15%">
      <input type="Submit" value="Save Changes" name="saveButton">   
    </td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Item Tickler Group:</td>
    <td class="td04140102" colspan="2"><b><%= groupMain %></b></td>
    <td class="td04140102">&#160;</td>    
   </tr>
<%
   if (groupMain.equals("100-CPGItem") 
      || groupMain.equals("110-CPACK-FG") 
      || groupMain.equals("120-Dried-Frozen")
      || groupMain.equals("125-Formulated") 
      || groupMain.equals("130-Fresh Slice") 
      || groupMain.equals("140-Puree")
      || groupMain.equals("150-Conc-Manufactured")
      || groupMain.equals("210-ProcFruit-Manufactured"))
   {
%>   
   <tr class="tr00">
    <td class="td04140102">Open Order Report:</td>
    <td class="td04140102" colspan="2">
<%
   if (!item.getM3ItemAliasOpenOrders().trim().equals(""))
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
   <tr class="tr00">
    <td class="td04140102">Fresh Pack Report:</td>
    <td class="td04140102" colspan = "2">
<%
   if (!item.getM3ItemAliasFreshPack().trim().equals(""))
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
   <tr class="tr00">
    <td class="td04140102">CAR Report:</td>
    <td class="td04140102" colspan="2">
<%
   if (!item.getM3ItemAliasCAR().trim().equals(""))
      out.println("This Item is Assigned to the CAR Report, change in M3 Alias file(MMS025) if necessary");
   else
   {
      out.println("<acronym title=\"By checking this box, the program will add the appropriate M3 alias to the " + 
                  "Alias file in M3 so this item will show up on the CAR Report\">" + 
                  HTMLHelpersInput.inputCheckBox("flagCAR", "", readOnly) + "</aronym>");
   }
%>     
      &#160;&#160; Item Group: <%= item.getItemGroup() %>&#160;&#160;<%= item.getItemGroupDescription() %>
      <%= HTMLHelpersInput.inputBoxHidden("itemGroup", item.getItemGroup()) %>
    </td>
   </tr>   
   <tr class="tr00">
    <td class="td04140102">100 Percent Juice:</td>
    <td class="td04140102" colspan = "2">
<%
   if (!item.getM3ItemAlias100PercentJuice().trim().equals(""))
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
   <tr class="tr00">
    <td class="td04140102">
      <acronym title="Choose the Planner that will be Planning this specific Item. ">Production Planner:&#160;</acronym></td>
    <td class="td04140102" colspan="2">
<% 
	if (!item.getM3ItemAliasPlanner().trim().equals(""))
	   out.println("<b>" + item.getM3ItemAliasPlannerDesc().trim() + "</b>, change in M3 Alias file(MMS025) if necessary");
    else
    {
       out.println(UpdItem.buildDropDownPlanner(item.getM3ItemAliasPlanner().trim(), ""));   
    }
%>      
    </td>
   </tr>   
   <tr class="tr00">
    <td class="td04140102">Length:</td>
    <td class="td03140102" colspan="2">
      <%= HTMLHelpersInput.inputBoxNumber("length", uiMain.getLength(), "Length", 10, 10, "", "") %>
      &#160; <%= uiMain.getLengthError().trim() %>
    </td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Width:</td>
    <td class="td03140102" colspan="2">
      <%= HTMLHelpersInput.inputBoxNumber("width", uiMain.getWidth(), "Width", 10, 10, "", "") %>
      &#160; <%= uiMain.getWidthError().trim() %>
    </td>
   </tr>   
   <tr class="tr00">
    <td class="td04140102">Height:</td>
    <td class="td04140102" colspan="2">
      <%= HTMLHelpersInput.inputBoxNumber("height", uiMain.getHeight(), "Height", 10, 10, "", "") %>
      &#160; <%= uiMain.getHeightError().trim() %>
    </td>
   </tr>      
   <tr class="tr00">
    <td class="td04140102">Manufacturer:</td>
    <td class="td04140102" colspan="2">
      <%= UpdItem.buildDropDownManufacturer(uiMain.getManufacturer(), "") %>
    </td>
    <td class="td04140102">
      &#160;
    </td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Label UPC Code:</td>
    <td class="td04140102" colspan="2">
      <%= HTMLHelpersInput.inputBoxText("labelUPCA", uiMain.getLabelUPCA(), "Label UPC Code", 1, 1, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("labelUPCB", uiMain.getLabelUPCB(), "Label UPC Code", 5, 5, "N", readOnly) %>&#160;		
      <%= HTMLHelpersInput.inputBoxText("labelUPCC", uiMain.getLabelUPCC(), "Label UPC Code", 5, 5, "N", readOnly) %>&#160;			
      <%= HTMLHelpersInput.inputBoxText("labelUPCD", uiMain.getLabelUPCD(), "Label UPC Code", 1, 1, "N", "Y") %>	
      &#160;&#160;<%= uiMain.getErrorLabelUPC() %>&#160;Current M3 Value:&#160;<%= item.getM3ItemAliasLabelUPC() %>
    </td>
    <td class="td04140102">
      &#160;
    </td>    
   </tr>
   <tr class="tr00">
    <td class="td04140102">Case UPC Code:</td>
    <td class="td04140102" colspan="2">
      <%= HTMLHelpersInput.inputBoxText("caseUPCA", uiMain.getCaseUPCA(), "Case UPC Code", 1, 1, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("caseUPCB", uiMain.getCaseUPCB(), "Case UPC Code", 5, 5, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("caseUPCC", uiMain.getCaseUPCC(), "Case UPC Code", 5, 5, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("caseUPCD", uiMain.getCaseUPCD(), "Case UPC Code", 1, 1, "N", "Y") %>
      &#160;&#160;<%= uiMain.getErrorCaseUPC() %>&#160;Current M3 Value:&#160;<%= item.getM3ItemAliasCaseUPC() %>
    </td>
    <td class="td04140102">
<%
   if (item.getM3ItemAliasCaseUPC().trim().equals(""))
     out.println(HTMLHelpers.buttonSubmit("aliasButton", "Add M3 Alias"));
%>    
      &#160;
    </td>    
   </tr>    
<%
    if (groupMain.equals("100-CPGItem") 
        || groupMain.equals("130-Fresh Slice") )
    {
%>
   <tr class="tr00">
    <td class="td04140102">Pallet GTIN:</td>
    <td class="td04140102" colspan="2">
      <%= HTMLHelpersInput.inputBoxText("palletGTINA", uiMain.getPalletGTINA(), "Pallet GTIN", 2, 2, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("palletGTINB", uiMain.getPalletGTINB(), "Pallet GTIN", 6, 6, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("palletGTINC", uiMain.getPalletGTINC(), "Pallet GTIN", 5, 5, "N", readOnly) %>&#160;
      <%= HTMLHelpersInput.inputBoxText("palletGTIND", uiMain.getPalletGTIND(), "Pallet GTIN", 1, 1, "N", "Y") %>
      &#160;&#160;<%= uiMain.getErrorPalletGTIN() %> 
<%  
   if (!uiMain.getErrorPalletGTIN().equals(""))
      out.println("<br>");
   if (uiMain.getPalletGTINDesc() != null &&
       !uiMain.getPalletGTINDesc().trim().equals(""))
      out.println(uiMain.getPalletGTINDesc());
   else
      out.println("*Check Digit is Automatically Calculated");
%> 
     &#160;Current M3 Value:&#160;<%= item.getM3ItemAliasPalletGTIN() %>
    </td>
      <td class="td04140102" style="text-align:right">
<%
  if (!uiMain.getPalletGTINC().equals(""))
    out.println(UpdTieToChildren.buildMoreButton("familyTree", uiMain.getPalletGTIN(), uiMain.getPalletGTINDesc()));
%> 
     </td>   
   </tr>  
<%
     }
   } 
   // Supplier Summary, only needed for Purchased finished goods and supplies
    if (groupMain.equals("110-CPACK-SUPPLY") 
      || groupMain.equals("150-Conc-Purch")
      || groupMain.equals("210-ProcFruit-Purch")
      || groupMain.equals("250-Packaging")
      || groupMain.equals("260-290-Ingredients"))
   {  
%>   
<tr>
        <td class="td04140102"><abbr title="Attach the Supplier Summary">Attach Supplier Summary:</abbr></td>
        <td class="td04140102">
<%
   if (uiMain.getSupplierSummaryURL().trim().equals(""))
   {
%>        
        <%= HTMLHelpersInput.inputBoxUrlBrowse("supplierSummaryURL", uiMain.getSupplierSummaryURL(), "URL for the Supplier Summary", 60, 500, "N", readOnly) %>
<%  
    }else{
%>   
        <%= HTMLHelpersInput.inputBoxHidden("supplierSummaryURL", uiMain.getSupplierSummaryURL()) %>
        <%= HTMLHelpersInput.inputCheckBox("supplierSummaryURLRemove", "", readOnly) %>&#160;Check if want to remove the attached Supplier Summary - to then attach another&#160;
        </br><b><%= HTMLHelpersLinks.basicLink(uiMain.getSupplierSummaryURL().trim(), uiMain.getSupplierSummaryURL().trim(), "", "", "") %></b>     
<%
    }
%>
       &#160;</td>  
       </tr>
<%
   }
%>        
  </table>
