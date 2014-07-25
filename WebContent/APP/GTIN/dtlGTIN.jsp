<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.gtin.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- dtlGTIN.jsp -----------------------//
//   Author :  Teri Walton  8/30/2005
//   Changes: Replaces dtlUCCNet.jsp 
//             Author :  Charlena Paschen 07/02/2004
//   Date       Name       Comments
// --------   ---------   ------------------------------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look.
//------------------------------------------------------------//
  String errorPage = "/GTIN/dtlGTIN.jsp";
   String detailTitle = " GTIN Details";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 String hasComments = "N";
 GTINDetail thisGtin = new GTINDetail();
 Vector comments = new Vector();
 Vector urls = new Vector();
 int ifsImageCount = 0;
 int chosenImageCount = 0;
 try
 {
	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	if (dg.getDetailClass() != null &&
	    dg.getDetailClass().getGtinDetail() != null &&
	    dg.getDetailClass().getGtinDetail().getGtinNumber() != null &&
	    !dg.getDetailClass().getGtinDetail().getGtinNumber().equals(""))
	{    
	    detailTitle = HTMLHelpersMasking.maskGTINNumber(dg.getDetailClass().getGtinDetail().getGtinNumber()) + detailTitle;
	    if (dg.getDetailClass().getGtinDetail().getComments() != null &&
	        dg.getDetailClass().getGtinDetail().getComments().size() > 0)
          hasComments = "Y";
        thisGtin = dg.getDetailClass().getGtinDetail();
        if (dg.getDetailClass().getGtinDetail().getUrls() != null &&
            dg.getDetailClass().getGtinDetail().getUrls().size() > 0)
            chosenImageCount = dg.getDetailClass().getGtinDetail().getUrls().size(); 
	}
     String[] imageList = (String[]) request.getAttribute("imageList");
     if (imageList != null &&
         imageList.length > 0)
         ifsImageCount = imageList.length;
     urls = dg.getDetailClass().getGtinDetail().getUrls();
     comments = dg.getDetailClass().getGtinDetail().getComments();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",detailTitle);
//*****************************************************************************   
String mouseoverHelp = "Click here to see help documents.";
%>
<html>
 <head>
   <title><%= detailTitle %></title>
   <%= JavascriptInfo.getEditButton() %>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 1, "Y", 12) %>   
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include> 
<table class="table00" cellspacing="0" style="width:100%">
 <tr class="tr02">
  <td class="td04100105">&nbsp;</td> 
<%
//   Add this section if you want to select a new one ON this page.
%>   
  <td class="td04100105">
   <form method="link" action="#">
    <input type="submit" value="Select New GTIN"
        onClick="ANY_NAME=window.open('/web/APP/GTIN/selGTIN.jsp?requestType=detail&location=/web/CtlGTIN', 'win1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width=300,height=300'); return false;">
   </form>     
  </td>
<%
  if (thisGtin != null &&
      thisGtin.getGtinNumber() != null &&
      !thisGtin.getGtinNumber().trim().equals(""))
  { 
%>        
  <td class="td04100105" style="width:6%">
   <%= InqGTIN.buildMoreButton("detail", thisGtin.getGtinNumber().trim(), "") %>
  </td> 
  <td style="width:3%">
   &nbsp;
  </td>          
<%
   }
%>      
 </tr>
</table>
<%
  if (thisGtin == null ||
      thisGtin.getGtinNumber() == null ||
      thisGtin.getGtinNumber().trim().equals(""))
  { 
%>
<table class="table00" cellspacing="0" style="width:100%">
 <tr>
   <td class="td0510">There is no information for the selected Gtin</td>   
 </tr>  
</table> 
<%  
  }
  else
  {
%>
<table class="table00" cellspacing="0" cellpadding="3">
 <tr>
  <td style="width:2%">&nbsp;</td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr02">
     <td colspan="4" class="td0520" style="font-size:12pt"><b>&nbsp;&nbsp;Description</b></td>
    </tr>
    <tr>
     <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102" style="width:30%">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Global_Trade_Item_Number" 
	     target="_blank" title="<%= mouseoverHelp %>">Global Trade Item Number (GTIN):</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= HTMLHelpersLinks.routerGTIN(thisGtin.getGtinNumber(), "a0414", "", "") %></td>
	 <td class="td04140102" style="width:2%">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	 	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/EANUCC_Code" 
	     target="_blank" title="<%= mouseoverHelp %>">EANUCC Code:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getEanUCCCode() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/EANUCC_Type" 
	     target="_blank" title="<%= mouseoverHelp %>">EANUCC Type:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getEanUCCType() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Short_Description" 
	     target="_blank" title="<%= mouseoverHelp %>">Short Description:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getGtinDescription() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Additional_Trade_Item_Description" 
	     target="_blank" title="<%= mouseoverHelp %>">Additional Description:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getAdditionalTradeItemDescription() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr> 
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Long_Description" 
	     target="_blank" title="<%= mouseoverHelp %>">GTIN Name:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getGtinLongDescription() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Functional_Name" 
	     target="_blank" title="<%= mouseoverHelp %>">Functional Name:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getFunctionalName() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>  
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Brand_Name" 
	     target="_blank" title="<%= mouseoverHelp %>">Brand Name:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getBrandName() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Classification_Category_Code" 
	     target="_blank" title="<%= mouseoverHelp %>">Alternate Item Classification Code:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getClassificationCategoryCode() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Additional_Classification_Category_Code" 
	     target="_blank" title="<%= mouseoverHelp %>">Global Item Classification:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getAdditionalClassificationCategoryCode() %></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr> 
	 <td class="td04140102">&nbsp;</td> 
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Brand_Owner_Name" 
	     target="_blank" title="<%= mouseoverHelp %>">Brand Owner Name:</a>
	 </td>
  	 <td class="td04140102">&nbsp;<%= thisGtin.getNameOfInformationProvider() %></td>
  	 <td class="td04140102">&nbsp;</td>
	</tr> 
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Brand_Owner" 
	     target="_blank" title="<%= mouseoverHelp %>">Brand Owner:</a>
	 </td>
	 <td class="td04140102">&nbsp;<%= thisGtin.getInformationProvider() %></td> 	
	 <td class="td04140102">&nbsp;</td>
	</tr>     
   </table>
  </td>       
  <td style="width:2%">&nbsp;</td>
 </tr>    
<% 
  int imageCount  = 3;
  int expandCount = 1;
%>  
 <tr>
  <td></td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "Dimension", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINDimension.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "Weight and Content", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINWeightContent.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>  
     </td>
    </tr>   
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "Relationship to other GTINS", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINRelationship.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>   
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "True and False Questions", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINTrueFalse.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>  
     </td>
    </tr>
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "Miscellaneous Fields", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINMisc.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>  
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td class="td04121405" colspan="4">
      <%= JavascriptInfo.getExpandingSection("C", "Record Status - Internal", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINRecordStatus.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr>  
<%
// Family Tree Information   
 int familyTreeSize = 0;
 try
 {
	Vector familyTree = (Vector) request.getAttribute("familyTree");
	familyTreeSize = familyTree.size();
 }
 catch(Exception e)
 {
   // Turn on IF BIG Problem, generally will catch problem in Main JSP
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
 }      
  if (familyTreeSize > 0)
  {
  imageCount++;
  expandCount++;
  //------GTIN Family Tree------
     request.setAttribute("reqType", "detail");
     request.setAttribute("gtin", thisGtin.getGtinNumber());
%> 
    <tr class="tr02">
     <td class="td04121405" colspan="4">
      <%= JavascriptInfo.getExpandingSection("C", "GTIN Family Tree", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlGTINFamilyTree.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>    
     </td>
    </tr>
<% 
   }
  imageCount++;
  expandCount++;
  request.setAttribute("imageCount", new Integer(imageCount));
%>  
    <tr class="tr02">
     <td class="td04121405" colspan="4">
      <%= JavascriptInfo.getExpandingSection("C", "Images", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="../Utilities/displayIFSNew.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>
     </td>
    </tr> 
<% 
   imageCount = ((Integer) request.getAttribute("imageCount")).intValue();
    imageCount++;
    expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "url");
  request.setAttribute("imageCount", new Integer(imageCount));
  request.setAttribute("listKeyValues", urls);    
%>
    <tr class="tr02">
     <td class="td04121405" colspan="4">
      <%= JavascriptInfo.getExpandingSection("C", "Additional Images and Documents", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %>  
     </td>
    </tr>          
<% 
   imageCount = ((Integer) request.getAttribute("imageCount")).intValue();
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", comments);  
%>  
    <tr class="tr02">
     <td class="td04121405" colspan="4">
      <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
      <%= HTMLHelpers.endSpan() %> 
     </td>
    </tr>           
  </table>
  </td>
  <td></td>
 </tr>  
<%
   }   
%>  
</table>  
  <%@ include file="../../Include/footer.jsp" %>
   </body>
</html>