<%@ page language = "java" %>
<%@ page import="com.treetop.services.ServiceItem" %>
<%@ page import="com.treetop.businessobjects.Item" %> 
<%@ page import="com.treetop.businessobjectapplications.BeanItem" %>
<%
//----------------itemRouter.jsp-------------------------------------------//
//
//    Author :  Teri Walton  6/05/08
//
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//--------------------------------------------------------------------------//
// IN Parameter Values - Potential
//--------------------------------------------------------------------------//
 //Resource Number -- All Links will Use
  String item = request.getParameter("item");
  if (item == null)
     item = "";
//---------------------------------------------------------------------------//
// Fields Needed Later in JSP
  String itemDescription   = "";
//  String resourceClassType     = "";
//  String resourceClassTypeDesc = "";
//--------
  try
  {
    BeanItem itemClass = ServiceItem.buildNewItem("", item);
    if (itemClass.getItemClass() != null &&
        itemClass.getItemClass().getItemDescription() != null)
    {
      itemDescription   = itemClass.getItemClass().getItemDescription();
//      resourceClassType     = resourceClass.getResourceClassType();
//      resourceClassTypeDesc = resourceClass.getResourceClassTypeDesc();
    }  
  }
  catch(Exception e)
  {
    //Ignore Exception
  }  
%>
<html>
 <head>
  <title>Router of Item Number (List of Links)</title>
 </head>
 <body>
 <jsp:include page="../../Include/heading.jsp"></jsp:include>
  <table class="table00">
<%
  if (itemDescription.equals(""))
  {
%> 
   <tr class="tr00">
    <td class="td0316">
     <b>NO Information found for this item: <%= item %> </b>
    </td>
   </tr>
<%
  }else{
%> 
   <tr>
    <td class="td04120102"><b>Item Selected: </b></td>
    <td class="td04120102"><b><%= item %> &nbsp;</b></td>
    <td class="td04120102"><b><%= itemDescription %></b></td>
   </tr>
  </table>
  <table class="table01" cellspacing="0" cellpadding="0">
   <tr class="tr02">
    <td style="width:5%">&nbsp;</td>
    <td class="td0320" colspan = "3"><b>Information...</b></td>
    <td style="width:2%">&nbsp;</td>
    <td style="width:5%">&nbsp;</td>
   </tr>  
   <tr class="tr00">
    <td colspan = "6">&nbsp;</td>
   </tr>     
   <tr class="tr00">
    <td rowspan = "4">&nbsp;</td>
    <td class="td04140102" style="width:2%">&nbsp;</td>
    <td class="td04140102">
     <a class="a04002" href="/web/CtlItem?requestType=update&item=<%= item %>">
       NEW ITEM - View / Update</a>
    </td>
    <td class="td04140102">
       View / Update Information relating to New Item, pallet GTIN, UPC, Descriptions, Comments.
    </td>
    <td class="td04140102">&nbsp;</td>
   </tr>   
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>   
    <td class="td04140102">
     <a class="a04002" href="/web/CtlSpecificationNEW?requestType=dtlCPGSpec&itemNumber=<%= item %>">
       CPG Item Specification</a>
     </td>
    <td class="td04140102">
       See Current Specification Relating to item&nbsp;<%= item %>.
    </td>
    <td class="td04140102">&nbsp;</td>         
   </tr>         
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>   
    <td class="td04140102">
     <a class="a04002" href="/web/CtlItem?requestType=listVariance&inqItem=<%= item %>">
       Item Related Variances</a>
     </td>
    <td class="td04140102">
       Current, Past and Future variances (Specific to this Item Number) to standard item numbers are all displayed.
    </td>
    <td class="td04140102">&nbsp;</td>         
   </tr>        
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>   
    <td class="td04140102">
     <a class="a04002" href="/web/CtlCodeDate?inqItem=<%= item %>">
       Code Date Information</a>
    </td>
    <td class="td04140102">
      Generate how a Code Date should look.  
    </td>
    <td class="td04140102">&nbsp;</td>   
   </tr>  
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">&nbsp;</td>
   </tr>     
   <tr class="tr02">
    <td style="width:5%">&nbsp;</td>
    <td class="td0320" colspan = "3"><b>Search...</b></td>
    <td style="width:2%">&nbsp;</td>
    <td style="width:5%">&nbsp;</td>
   </tr>  
   <tr class="tr00">
    <td colspan = "6">&nbsp;</td>
   </tr>     
   <tr class="tr00">
    <td rowspan = "3">&nbsp;</td>
    <td class="td04140102" style="width:2%">&nbsp;</td>
    <td class="td04140102">
     <a class="a04002" href="/web/CtlItem?requestType=inqVariance">
       Item Variances</a>
     </td>
    <td class="td04140102">
       See all Current, Past and Future variances for chosen Item Numbers (Allows you to choose the Item Number)
    </td>
    <td class="td04140102">&nbsp;</td>         
   </tr>       
      <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">
     <a class="a04002" href="/web/CtlSpecificationNEW">
       CPG Item Specifications</a>
     </td>
    <td class="td04140102">
       Search for CPG Specification
    </td>
    <td class="td04140102">&nbsp;</td>         
   </tr>               
<%
  } // End of the is there a resource description else
%>  
  </table>
  <%@ include file="../../Include/footer.jsp" %>
 </body>
</html>
