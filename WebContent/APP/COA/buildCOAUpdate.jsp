<%@ page import = "com.treetop.app.coa.BuildCOA" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.businessobjects.SalesOrderLineItem" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanCOA" %>
<%
//---------------- APP/COA/listCOAUpdate.jsp -----------------------//
//Prototype:  Charlena Paschen  06/04/03 (jsp)
//Author   :  Teri Walton       11/05/03 (thrown from servlet)
//Changes  :
//Date       Name          Comments
//----       ----          --------
//9/5/07     TWalton 		 Rewrite with Movex //Split out JSP 
//------------------------------------------------------------//
//********************************************************************
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
   BuildCOA bldUpd = new BuildCOA();
   String shippingWhse = "";
  try
 {
	bldUpd = (BuildCOA) request.getAttribute("buildViewBean");
	if (bldUpd.getRequestType().equals(""))
	   bldUpd.setRequestType("build");
	BeanCOA bc = (BeanCOA) bldUpd.getListReport().elementAt(0);
	SalesOrderLineItem soli = (SalesOrderLineItem) bc.getListSOLineItems().elementAt(0);
	shippingWhse = soli.getShippingWarehouse();
 }
 catch(Exception e)
 {
 }  
%>
<html>
  <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getCheckTextareaLength() %>
   <%= JavascriptInfo.getEmailCheck() %>  
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>        
  </head>
  <body>
<% 
//**************************************************************************************
   // Update Information
//**************************************************************************************
%>
  <table class="table00" cellspacing="0" style="width:100%">
<%
   if (!bldUpd.getDisplayErrors().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102">&nbsp;</td>
        <td class="td03200102" colspan = "2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= bldUpd.getDisplayErrors().trim() %></b></td>
        <td class="td03200102">&nbsp;</td>
       </tr>    
<%
   }
%>         
<% // Row 1   %>
   <tr>
    <td class="td04140902" style="width:3%" rowspan="17">&nbsp;</td>
    <td class="td04141402" style="width:15%">
     <b>Email</b>
    </td>
    <td class="td03140402">
     <%= HTMLHelpersInput.inputBoxHidden("coaType", bldUpd.getCoaType().trim()) %>
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail1", bldUpd.getBuildEmail1().trim() , "E-Mail 1", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail1Error() %>
    </td>
    <td class="td04141402" style="width:15%">
     <b>COA Comments:</b>
    </td>
    <td class="td04140402" rowspan="6">
     <%= HTMLHelpersInput.inputBoxTextarea("buildCOAComment", bldUpd.getBuildCOAComment().trim(), 5, 40, 135, "N") %>
    </td>
    <td class="td04141002" style="width:3%" rowspan="17">&nbsp;</td>
   </tr>
<% // Row 2    %>
   <tr>
    <td class="td0414">
     <b>Addresses:</b>
    </td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail2", bldUpd.getBuildEmail2().trim(), "E-Mail 2", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail2Error() %>
    </td> 
    <td>&nbsp;</td>
   </tr>
<% // Row 3   %>   
   <tr>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail3", bldUpd.getBuildEmail3().trim(), "E-Mail 3", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail3Error() %>
    </td>
    <td>&nbsp;</td>
   </tr>
<% // Row 4   %>   
   <tr>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail4", bldUpd.getBuildEmail4(), "E-Mail 4", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail4Error() %>
    </td>
    <td>&nbsp;</td>
   </tr>
<% // Row 5   %>   
   <tr>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail5", bldUpd.getBuildEmail5().trim(), "E-Mail 5", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail5Error() %>
    </td>
    <td>&nbsp;</td>
   </tr>   
<% // Row 6    %>   
   <tr>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail6", bldUpd.getBuildEmail6().trim(), "E-Mail 6", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail6Error() %>
    </td>
    <td class="td04140102">&nbsp;</td>
   </tr>
<% // Row 7     %>         
   <tr>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail7", bldUpd.getBuildEmail7().trim(), "E-Mail 7", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail7Error() %>
    </td>
    <td class="td04140102" rowspan="2">
     <b>QA Person Signature:</b>
    </td>
    <td class="td04140102" rowspan="2">
     <%= bldUpd.buildDropDownQASignature(bldUpd.getBuildSignature(), shippingWhse) %>    
    </td>
   </tr>  
<% // Row 8   %>
   <tr>
    <td class="td04140102">&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxTextEmail("buildEmail8", bldUpd.getBuildEmail8().trim(), "E-Mail 8", 40, 50, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildEmail8Error() %>
    </td>
   </tr>                
<% // Row 9 %>   
   <tr>
    <td class="td0414">
     <b>Attn:</b>
    </td>
    <td class="td04140102">
     <%= HTMLHelpersInput.inputBoxText("buildAttn1", bldUpd.getBuildAttn1().trim(), "Attention 1", 20, 20, "N", "N") %>
    </td>
    <td class="td0414">
     <b>Fax #:</b>
    </td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxNumber("buildFax1", bldUpd.getBuildFax1(), "Fax 1", 20, 20, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildFax1Error() %>
    </td>
   </tr>  
<% // Row 10 %>   
   <tr>
    <td>&nbsp;</td>
     <td class="td04140102">
     <%= HTMLHelpersInput.inputBoxText("buildAttn2", bldUpd.getBuildAttn2().trim(), "Attention 2", 20, 20, "N", "N") %>
    </td>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxNumber("buildFax2", bldUpd.getBuildFax2(), "Fax 2", 20, 20, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildFax2Error() %>
    </td>
   </tr>   
<% // Row 11 %>   
   <tr>
    <td>&nbsp;</td>
    <td class="td04140102">
     <%= HTMLHelpersInput.inputBoxText("buildAttn3", bldUpd.getBuildAttn3().trim(), "Attention 3", 20, 20, "N", "N") %>
    </td>
    <td>&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxNumber("buildFax3", bldUpd.getBuildFax3(), "Fax 3", 20, 20, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildFax3Error() %>
    </td>
   </tr>
<% // Row 12 %>   
   <tr>
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">
     <%= HTMLHelpersInput.inputBoxText("buildAttn4", bldUpd.getBuildAttn4(), "Attention 4", 20, 20, "N", "N") %>
    </td>
    <td class="td04140102">&nbsp;</td>
    <td class="td03140102">
     <%= HTMLHelpersInput.inputBoxNumber("buildFax4", bldUpd.getBuildFax4(), "Fax 4", 20, 20, "N", "N") %>
     &nbsp;<%= bldUpd.getBuildFax4Error() %>
    </td>
   </tr>   
<% // Row 13    %>   
   <tr>
    <td class="td0516" colspan="2" rowspan="2" style="text-align:center">
     Date Format and Display Options<br>
     only work for preview and email.
    </td>
    <td class="td04140102">
     <b>Choose Date Format</b>
    </td>
    <td class="td04140102">
      <%= GetDate.dateFormatDropDown("buildDateFormat", bldUpd.getBuildDateFormat().trim()) %>
    </td>
   </tr>   
<% // Row 14    %>   
   <tr>
<%
   if (!bldUpd.getCoaType().equals("LOT"))
   {
%>  
    <td class="td04140102">
     <b>Show Additional Units</b>
    </td>
    <td class="td04140102">
     <%= bldUpd.buildDropDownShowAmount(bldUpd.getBuildShowAmount()) %>
    </td>
<%
   }
%>    
   </tr>   
<% // Row 15     
//   <tr>
//    <td class="td04160102">
 //    <b>Country Of Origin</b>
  //  </td>
  //  <td class="td04160102">
  //    Build Drop Down Country of Origin
  //  </td>
  // </tr>  
 // Row 16    %>              
   <tr>
    <td class="td04160102" colspan="4">
     <table style="width:100%">
      <tr>
<%
   if (!bldUpd.getCoaType().equals("LOT"))
   {
%>      
       <td class="td0416" style="text-align:center">
        <b>Average Idents?</b>&nbsp;
        <%= HTMLHelpersInput.inputCheckBox("buildShowAverage", bldUpd.getBuildShowAverage(), "N") %>
       </td>
<%
   }
%>       
       <td class="td0416" style="text-align:center">
        <b>Display Min/Max?</b>&nbsp;
        <%= HTMLHelpersInput.inputCheckBox("buildShowMinMax", bldUpd.getBuildShowMinMax(), "N") %>
       </td>
<%
  if (0 == 1)
  { // No Longer Using
%>       
       <td class="td0416" style="text-align:center">
        <b>Display Target?</b>&nbsp;
        <%= HTMLHelpersInput.inputCheckBox("buildShowTarget", bldUpd.getBuildShowTarget(), "N") %>
       </td>
       <td class="td0416" style="text-align:center">
        <b>Display Customer Spec?</b>
        <%= HTMLHelpersInput.inputCheckBox("buildShowCustomerSpec", bldUpd.getBuildShowCustomerSpec(), "N") %>
       </td>
       <td class="td0416" style="text-align:center">
        <b>Display Attribute Model?</b>
        <%= HTMLHelpersInput.inputCheckBox("buildShowAttributeModel", bldUpd.getBuildShowAttributeModel(), "N") %>
       </td>
<%
   }
%>       
       
      </tr>
     </table>
    </td>
   </tr>
   <tr class="tr02">
    <td class="td0416" colspan="4" style="text-align:center">
     <%= HTMLHelpers.buttonSubmit("saveChanges", "Save Changes") %>
    </td>
   </tr>
  </table>   
 </body>
</html>