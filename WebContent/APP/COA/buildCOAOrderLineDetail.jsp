<%

//---------------- APP/COA/listCOAOrderLineDetail.jsp -----------------------//
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
   int isImageCount = 3;
   int isExpandCount = 1;
   try
   {
   	  isImageCount = ((Integer) request.getAttribute("imageCount")).intValue();
   	  isExpandCount = ((Integer) request.getAttribute("expandCount")).intValue();
   }
   catch(Exception e)
   {}	
      
  int lc1 = 0;
  String coaTypeDetail = "CO";
  String environ = "PRD";
  com.treetop.businessobjectapplications.BeanCOA bc1 = new com.treetop.businessobjectapplications.BeanCOA();
 try
 {
	com.treetop.app.coa.BuildCOA bldCOA = (com.treetop.app.coa.BuildCOA) request.getAttribute("buildViewBean");
	environ = bldCOA.getEnvironment();
	if (bldCOA.getRequestType().equals(""))
	   bldCOA.setRequestType("build");
	coaTypeDetail = bldCOA.getCoaType();
	bc1 = (com.treetop.businessobjectapplications.BeanCOA) bldCOA.getListReport().elementAt(0);
    if (bldCOA.getCoaType().equals("DO"))
      lc1 = bc1.getListDOLineItems().size();
    else
  	  lc1 = bc1.getListSOLineItems().size();
 }
 catch(Exception e)
 {
 }    
%>
<%@page import="com.treetop.businessobjects.DistributionOrderLineItem"%>
<%@page import="com.treetop.utilities.html.HTMLHelpers"%>
<html>
  <head>
  </head>
  <body>
<% 
//**************************************************************************************
   // Update Additional Information
//**************************************************************************************
%>
   <table cellspacing="0" style="width:100%">
<%
//   int whichExpandingSection = 0;
   try
   {
   	  request.setAttribute("AttrCount", "0");
   	  String attrItem = "";
   	  String attrLine = "";
   	  String attrUOM = "";
   	  String skipItem = "N";
      for (int y = 0; y < lc1; y++)
      {
         if (coaTypeDetail.equals("DO"))
         {// Distribution ORDER
           DistributionOrderLineItem thisItem = (DistributionOrderLineItem) bc1.getListDOLineItems().elementAt(y);
           attrItem = thisItem.getItemClass().getItemNumber();
           attrLine = thisItem.getLineNumber();
           attrUOM  = thisItem.getItemClass().getBasicUnitOfMeasure();
%>          
    <tr class="tr01">
     <td class="td04141405" colspan="2">
       Item Number: <b><%= thisItem.getItemClass().getItemNumber() %></b>
     </td>
     <td class="td04141405">
        <b><%= thisItem.getItemClass().getItemDescription() %></b>&nbsp;
     </td>
          <td class="td04141405">
        Basic Unit of Measure: <b><%= thisItem.getItemClass().getBasicUnitOfMeasure() %></b>&nbsp;
     </td>
     <td class="td04141405">
        Attribute Model: <b><%= thisItem.getItemClass().getAttributeModel() %></b>&nbsp;
     </td>
     <td class="td04141405" style="text-align:right">
<%
    String displayLink = "Change Idents for Item " + thisItem.getItemClass().getItemNumber();
    String actualURL   = "/web/CtlCOANew?requestType=goToUpdate&inqDistributionOrder=" + thisItem.getOrderNumber() +
    			         "&lineNumber=" + thisItem.getLineNumber() + 
    			         "&lineSuffix=" + thisItem.getSuffix() + 
    			         "&coaType=" + coaTypeDetail + 
    			         "&environment=" + environ;
%>     
      <%= com.treetop.utilities.html.HTMLHelpersLinks.basicLinkSamePage(displayLink , actualURL, displayLink, "a0412", "") %>
     </td>
    </tr>           
<%
         }
         else
         {
       	   com.treetop.businessobjects.SalesOrderLineItem	thisItem = (com.treetop.businessobjects.SalesOrderLineItem) bc1.getListSOLineItems().elementAt(y);
  // 8/7/12 TWalton -- change to say,,, show all even if the item is a duplicate
//       	   if (!attrItem.trim().equals(thisItem.getItemClass().getItemNumber()))
//       	   {
       	   attrItem = thisItem.getItemClass().getItemNumber();
           attrLine = thisItem.getLineNumber();
           attrUOM  = thisItem.getItemClass().getBasicUnitOfMeasure();
//           String yesNo = "yes";
//           whichExpandingSection = 3 + y;
//        SalesOrderDetail detailLine = (SalesOrderDetail) soDetails.elementAt(y);
//           Resource thisResource = new Resource(detailLine.getResourceNumber());
//           String selectChange = "Select Idents";
//           if (yesNo.equals("yes"))
//              selectChange = "Change Idents";
%>
    <tr class="tr01">
     <td class="td04141405" colspan="2">
       Item Number: <b><%= thisItem.getItemClass().getItemNumber() %></b>
     </td>
     <td class="td04141405">
        <b><%= thisItem.getItemClass().getItemDescription() %></b>&nbsp;
     </td>
          <td class="td04141405">
        Basic Unit of Measure: <b><%= thisItem.getItemClass().getBasicUnitOfMeasure() %></b>&nbsp;
     </td>
     <td class="td04141405">
        Attribute Model: <b><%= thisItem.getItemClass().getAttributeModel() %></b>&nbsp;
     </td>
     <td class="td04141405" style="text-align:right">
<%
    String displayLink = "Change Idents for Item " + thisItem.getItemClass().getItemNumber();
    String actualURL   = "/web/CtlCOANew?requestType=goToUpdate&inqSalesOrder=" + thisItem.getOrderNumber() +
    			         "&lineNumber=" + thisItem.getLineNumber() + 
    			         "&lineSuffix=" + thisItem.getSuffix() + 
    			         "&coaType=" + coaTypeDetail + 
    			         "&environment=" + environ;
%>     
      <%= com.treetop.utilities.html.HTMLHelpersLinks.basicLinkSamePage(displayLink , actualURL, displayLink, "a0412", "") %>
     </td>
    </tr>
<%
//      }
//      else
//        skipItem = "Y";
    }
    if (skipItem.equals("N"))
    {
%>    
    <tr class="tr00">
     <td style="width:2%">&nbsp;</td>
     <td colspan="5">
<% 
  isImageCount++;
  isExpandCount++;
  // Set the specific Data for this Item.
      request.setAttribute("SOLineNumber", attrLine);
      request.setAttribute("itemBasicUOM", attrUOM);
      request.setAttribute("coaAttrDetail", bc1.getListCOADetailAttributes());
      request.setAttribute("attrDetail", bc1.getListAttrValues());
%>
      <table style="width:100%">  
       <tr class="tr02">
        <td class="td0312" style="width:100%">
         <%= com.treetop.utilities.html.JavascriptInfo.getExpandingSection("C", ("View Chosen Idents for " + attrItem), 9, isExpandCount, isImageCount, 1, 0) %>
          <%@ include file="buildCOAOrderTableDetail.jsp" %>
         <%= HTMLHelpers.endSpan() %>
        </td>
       </tr>     
      </table>            
     </td>
    </tr>
<%
     }
      } // end of 'for' lineItemCount
   }
   catch(Exception e)
   {
      out.println("Problem within the Line Item Section" + e);
   }
%>
    </table>
 </body>
</html>