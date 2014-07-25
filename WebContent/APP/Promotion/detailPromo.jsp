<%@ page language = "java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "com.treetop.app.promotion.*" %>
<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.CheckDate" %>
<%
//---------------  detailPromo.jsp  ------------------------------------------//
//   Author :  Teri Walton  1/18/08
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//  9/24/13    TWalton		Adjust the screen to point out items that are not authorized to sell that that person
//----------------------------------------------------------------------------//
//********************************************************************
  String errorPage = "Promotion/detailPromo.jsp";
  String listTitle = "List Detail Promotion Information by Sales Order";  
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqPromotion irn = new InqPromotion();
 try
 {
	irn = (InqPromotion) request.getAttribute("inqViewBean");
 }
 catch(Exception e)
 {
    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",listTitle);
   
    if (irn.getInqSalesOrderError().equals("") &&
	    !irn.getInqSalesOrder().equals(""))
    {       
      StringBuffer setExtraOptions = new StringBuffer();
      setExtraOptions.append("<option value=\"/web/CtlPromotion?requestType=detail\">Choose Another Sales Order");
      request.setAttribute("extraOptions", setExtraOptions.toString());       
   }
//*****************************************************************************
%>
<html>
  <head>
    <title><%= listTitle %></title>
      <%= JavascriptInfo.getClickButtonOnlyOnce() %>
      <%= JavascriptInfo.getChangeSubmitButton() %>    
    <%= JavascriptInfo.getExpandingSectionHead("Y", 5, "Y", 5) %>       
  </head>
  <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
   if (!irn.getInqSalesOrderError().equals("") ||
	    irn.getInqSalesOrder().equals(""))
   {     
%>
  <form action="/web/CtlPromotion?requestType=detail" method="post">
  <table class="table01" cellspacing="0" style="width:100%">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
       <tr class="tr02">
        <td class="td0420" colspan = "2">
         <b> Choose:</b>
        </td>
        <td class="td0420" colspan = "2"><b>and then press</b>
         <%= HTMLHelpers.buttonGo("Get Details") %>
        </td>  
       </tr>
       <tr class="tr00">
        <td class="td04160102" style="width:2%">&nbsp;</td>
        <td class="td04160102">
         <b>Sales Order Number:</b>
        </td>
        <td class="td03160102">&nbsp;
         <%= HTMLHelpersInput.inputBoxText("inqSalesOrder", irn.getInqSalesOrder(), "Sales Order Number", 10, 10, "N", "N") %>&nbsp;&nbsp;
         <b><%= irn.getInqSalesOrderError() %></b>
        </td>
        <td class="td04160102" style="width:2%">&nbsp;</td>
       </tr>    
       <tr class="tr00">
        <td class="td04160102">&nbsp;</td>
        <td class="td04160102">
         <b>Show All:</b>
        </td>
        <td class="td03160102">&nbsp;
         <%= HTMLHelpersInput.inputCheckBox("inqShowAll", "", "") %>
        </td>
        <td class="td04160102">&nbsp;</td>
       </tr>    
      </table>
    </td>
    <td style="width:2%">&nbsp;</td>
   </tr>
  </table>
  <%= HTMLHelpersInput.inputBoxHidden("inqCompany", irn.getInqCompany()) %>
  <%= HTMLHelpersInput.inputBoxHidden("inqDivision", irn.getInqDivision()) %>
  </form>
<%
   }else{
%>   
  <table class="table00" cellspacing="0">
   <form action="/web/CtlPromotion?requestType=detail" method="post">
   <tr>
    <td style="width:2%">&nbsp;</td>
     <td>
      <table class="table00" cellspacing="0" style="width:100%">
       <tr class="tr02">
        <td class="td0420">
         <b> Choose:</b>
        </td>
        <td class="td04160102">
         <b>Show All:</b>&nbsp;&nbsp;<%= HTMLHelpersInput.inputCheckBox("inqShowAll", irn.getInqShowAll(), "") %>
           <%= HTMLHelpersInput.inputBoxHidden("inqCompany", irn.getInqCompany()) %>
  		   <%= HTMLHelpersInput.inputBoxHidden("inqDivision", irn.getInqDivision()) %>
 		   <%= HTMLHelpersInput.inputBoxHidden("inqSalesOrder", irn.getInqSalesOrder()) %>	
        </td>
        <td class="td0420" style="text-align:right"><b>and then press</b>
         <%= HTMLHelpers.buttonGo("Change Display") %>
        </td>  
       </tr>
      </table>
    </td>
    <td style="width:2%">&nbsp;</td>
   </tr>
   </form>
   <tr class="tr00">
    <td class="td0412" colspan = "13">&nbsp;&nbsp;&nbsp;&nbsp;<%= irn.buildParameterDisplay() %></td>
   </tr>  
  </table> 
  <hr>
<%
   BeanPromotion bp = new BeanPromotion();
   SalesOrder so = new SalesOrder();
   try
   {
       bp = (BeanPromotion) irn.getListReport().elementAt(0);
   	   so = (SalesOrder) bp.getSoClass();

%>    

  <table class="table00" cellspacing="0">
   <tr class="tr00">
    <td class="td04140102">Customer Number</td>
    <td class="td04140102"><b><%= so.getCustomerNumber() %></b></td>
    <td class="td04140102">Customer Order Number</td>
    <td class="td04140102"><b><%= so.getOrderNumber() %></b></td>    
   </tr>
      <tr class="tr00">
    <td class="td04140102">Plan To</td>
    <td class="td04140102"><b><%= so.getCustomerPlanTo()%>&nbsp;</b></td>
    <td class="td04140102">Customer PO Number</td>
    <td class="td04140102"><b><%= so.getCustomerPONumber() %></b></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Customer Name</td>
    <td class="td04140102"><b><%= so.getCustomerName() %></b></td>
    <td class="td04140102">Order Ship Date</td>
    <td class="td04140102"><b>
<%
   if (!so.getShipDate().equals(""))
   {
      try
      {
         DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(so.getShipDate());
         out.println(dt.getDateFormatMMddyyyySlash());
      }
      catch(Exception e)
      {}
   }
%>
    &nbsp;</b></td>
   </tr>
   <tr class="tr00">
    <td class="td04140102">Market Number</td>
    <td class="td04140102"><b><%= so.getMarket() %></b>&nbsp;</td>
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">&nbsp;</td>
   </tr>
  </table>
 <hr> 
<%
   }
   catch(Exception e)
   {
   }
%>  
  <table class="table00" cellspacing="0">
   <tr class="tr02">
    <td class="td04140102"><b>Type</b></td>
    <td class="td04140102"><b></b></td>
    <td class="td04140102"style="text-align:center"><b>Case<br>Price</b></td>
    <td class="td04140102" colspan = "2"><b>Allowance<br>Amount</b></td>    
    <td class="td04140102"><b>Allowance<br>Type</b></td>
    <td class="td04140102"><b>Allowance<br>Code</b></td>
    <td class="td04140102"><b>Performance</b></td>
    <td class="td04140102"><b>Plan<br>Type</b></td>
    <td class="td04140102"><b>Plan<br>Key</b></td>
    <td class="td04140102"><b>Promotion<br>Number</b></td>
    <td class="td04140102"><b>Identity</b></td>           
    <td class="td04140102"><b>First<br>Ship Date</b></td>
    <td class="td04140102"><b>Last<br>Ship Date</b></td>
   </tr>
<%
  try
  {
    String saveItem = "";
    String saveUnits = "0";
    String saveDollars = "0";
    for (int x = 0; x < bp.getListPromotionDetail().size(); x++)
    {
       PromotionDetail pd = (PromotionDetail) bp.getListPromotionDetail().elementAt(x);
       if (!saveItem.equals(pd.getItemNumber().trim()))
       {
       // Add in the Bottom of ONE, before the Beginning of the NEXT
          if (x != 0)
          {
%>      
    <tr class="tr01">
     <td class="td0514" colspan="6" style="text-align:center"><b><%= saveItem %>&nbsp;&nbsp;TOTALS:</b></td>   
     <td class="td0514" colspan="2" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(saveUnits, 0) %></b></td>   
     <td class="td0514" colspan="2">&nbsp;&nbsp;<b>Ordered Units</b></td>  
     <td class="td0514" colspan="2" style="text-align:right"><b><%= HTMLHelpersMasking.mask2DecimalDollar(saveDollars) %></b></td>   
     <td class="td0514" colspan="2">&nbsp;&nbsp;<b>Promo Dollars</b></td>   
    </tr>  
<%
         }
         String isValid = "td05140424";
         String isValidMessage = "";
         if (pd.getInvaildPrice().trim().equals("1"))
         {
           isValid = "td03140424";
           isValidMessage = "&nbsp; This Item is not AUTHORIZED to be sent to this Customer!!";
         }
           
%>    
    <tr class="tr01">
     <td class="<%= isValid %>" colspan="14">
        <b><%= pd.getItemNumber().trim() %>&nbsp;&nbsp;-&nbsp;&nbsp;<%= pd.getItemDescription().trim() %>
          <%= isValidMessage %>
        </b>
     </td>   
    </tr> 
<%
          saveItem = pd.getItemNumber().trim();
          saveUnits = "0";
          saveDollars = "0";
       }
%>   
   <tr class="tr00">
    <td class="td04120102">&nbsp;
<%
   if (pd.getRecordID().equals("1D"))
   { 
      out.println("Order Discount");
      saveUnits = pd.getOrderQuantity();
      saveDollars = pd.getOrderDiscountAmount();
   }
   if (pd.getRecordID().equals("3G"))
      out.println("Order Promotions");
   if (pd.getRecordID().equals("5S"))
      out.println("Scheduled Promotions");
%>    
    </td>
    <td class="td04120102">&nbsp;
    </td>
    <td class="td04120102" style="text-align:right">
<%
    if (pd.getItemPrice().trim().equals(""))
       out.println("&nbsp;");
    else
       out.println(HTMLHelpersMasking.maskBigDecimal(pd.getItemPrice().trim(), 2));
%>    
    </td>
    <td class="td04120102" style="text-align:right">
<%
   String allow = "Rate";
   if (!pd.getLumpSumAmount().trim().equals("0.00"))
   {
      out.println(pd.getLumpSumAmount().trim());
	  allow = "Dollars";
   }   
   else
   {
      out.println(pd.getRatePerUnit());
   }
%>    
    </td>
    <td class="td04120102"><%= allow %></td>
    <td class="td04120102"><%= pd.getTypeOfPromotion().trim() %>
<%
    if (pd.getTypeOfPromotion().trim().equals(""))
       out.println("&nbsp;");
%>    
    </td>
    <td class="td04120102">
<%
    if (pd.getAllowanceCode().trim().equals(""))
       out.println("&nbsp;");
    else
    {
       out.println(pd.getAllowanceCode().trim() + " - " + pd.getAllowanceCodeDescription().trim());
    }
%>    
    </td>
    <td class="td04120102">
<%
    if (pd.getMerchandisingDescription().trim().equals(""))
       out.println("&nbsp;");
    else
    {
       out.println(pd.getMerchandisingDescription().trim());
    }
%>    
    </td>   
    <td class="td04120102">
<%
    if (pd.getPlanType().trim().equals(""))
       out.println("&nbsp;");
    else
    {
       out.println(pd.getPlanType().trim() + " - " + pd.getPlanTypeDescription().trim());
    }
%>    
    </td>  
    <td class="td04120102">
<%
    if (pd.getPlanKey().trim().equals(""))
       out.println("&nbsp;");
    else
       out.println(pd.getPlanKey().trim());
%>    
    </td>      
    <td class="td04120102"><%= pd.getPromotionNumber().trim() %>
<%
    if (pd.getPromotionNumber().trim().equals(""))
       out.println("&nbsp;");
%>    
    </td>
    <td class="td04120102"><%= pd.getOrderDiscountIdentity().trim() %>
<%
    if (pd.getOrderDiscountIdentity().trim().equals(""))
       out.println("&nbsp;");
%>    
    </td>
    <td class="td04120102">
<%
    if (pd.getPromotionDateFrom().trim().equals("0"))
       out.println("&nbsp;");
    else
       out.println(CheckDate.convertYMDtoMDY(pd.getPromotionDateFrom().trim()));
%>    
    </td>
    <td class="td04120102">
<%
    if (pd.getPromotionDateFrom().trim().equals("0"))
       out.println("&nbsp;");
    else
       out.println(CheckDate.convertYMDtoMDY(pd.getPromotionDateTo().trim()));
%>    
    </td>
   <tr>                      
<%
    } 
%>    
   <tr class="tr01">
     <td class="td0514" colspan="6" style="text-align:center"><b><%= saveItem %>&nbsp;&nbsp;TOTALS:</b></td>   
     <td class="td0514" colspan="2" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(saveUnits, 0) %></b></td>   
     <td class="td0514" colspan="2">&nbsp;&nbsp;<b>Ordered Units</b></td>  
     <td class="td0514" colspan="2" style="text-align:right"><b><%= HTMLHelpersMasking.mask2DecimalDollar(saveDollars) %></b></td>   
     <td class="td0514" colspan="2">&nbsp;&nbsp;<b>Promo Dollars</b></td>   
    </tr>  
<%
   }catch(Exception e){
      out.println("Problem Found with Returned Data: " + e + "<br>Please contact IS with a printscreen of this page");
   }
%>   
   <tr class="tr00">
     <td class="td04121405" colspan="14">&nbsp;</td>
   </tr>  
  </table>
<%
   }
%>  
 <br>  
 <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>