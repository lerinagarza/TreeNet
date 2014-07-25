<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import="com.treetop.businessobjectapplications.BeanSpecification" %>
<%
//---------------- dtlCPGSpecs.jsp -------------------------------------------//
//
//    Author :  Teri Walton  6/04/02                     
//      moved to Production 12/19/02
//
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//   10/28/08    TWalton    Changed to point to NEW Stylesheet,  Broke up the page
//    2/25/04    TWalton    Changed comments and images for 5.0 server.
//   06/02/03    cpaschen   Update with include header/footer 
//   05/20/03    cpaschen   update presentation style     
//   01/10/03    TWalton    Change the Way Test Brix is Done 
//   12/19/02    TWalton    Changed to use new improved files.   
//--------------------------------------------------------------------------//
//**************************************************************************//
  String errorPage = "/Specification/dtlCPGSpec.jsp";
  String detailTitle = " Tree Top Specifications";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 SpecificationNEW thisSpec = new SpecificationNEW();
 SpecificationFormula formula = new SpecificationFormula();
 Vector comments = new Vector();
 Vector urls = new Vector();
 try
 {
	DtlSpecification ds = (DtlSpecification) request.getAttribute("dtlViewBean");
	thisSpec = ds.getBeanSpec().getSpecClass();
	formula = ds.getBeanSpec().getFormulaClass();
//     urls = dg.getDetailClass().getGtinDetail().getUrls();
 //    comments = dg.getDetailClass().getGtinDetail().getComments();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",detailTitle);
     StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlSpecificationNEW\">Select Again");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************   
%>
<html>
 <head>
   <title><%= detailTitle %></title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 5, "Y", 5) %>   
 </head>
 <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
<%
  if (0 == 1)
  {   
%>  
<table class="table00" cellspacing="0" style="width:100%">
 <tr class="tr02">
  <td class="td04100105">&nbsp;</td> 
<%
//   Add this section if you want to select a new one ON this page.
  if (0 == 1)
  {
%>   
  <td class="td04100105">
   <form method="link" action="#">
    <input type="submit" value="Select New GTIN"
        onClick="ANY_NAME=window.open('/web/APP/GTIN/selGTIN.jsp?requestType=detail&location=/web/CtlGTIN', 'win1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width=300,height=300'); return false;">
  </td>
   </form>     
<%
   }
  if (thisSpec != null &&
      thisSpec.getItemWhse() != null &&
      thisSpec.getItemWhse().getItemNumber() != null &&
      !thisSpec.getItemWhse().getItemNumber().trim().equals(""))
  { 
%>        
  <td class="td04100105" style="width:6%">
   <%// InqSpecification.buildMoreButton("detail", thisSpec.getItemWhse().getItemNumber().trim(), "") %>
  </td> 
  <td class="td04100105" style="width:1%">
   &nbsp;
  </td>          
<%
   }
%>      
 </tr>
</table>
<%
  }
  if (thisSpec == null ||
      thisSpec.getItemWhse() == null ||
      thisSpec.getItemWhse().getItemNumber() == null ||
      thisSpec.getItemWhse().getItemNumber().trim().equals(""))
  { 
%>
<table class="table00" cellspacing="0" style="width:100%">
 <tr>
   <td class="td0510">There is no specification information for the selected Item</td>   
 </tr>  
</table> 
<%  
  }
  else
  {
%>
<table class="table00" cellspacing="0" cellpadding="3">
 <tr>
  <td style="width:1%">&nbsp;</td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr>
     <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102">Item Number:</td>
	 <td class="td04140102">&nbsp;<b><%= HTMLHelpersLinks.routerItem(thisSpec.getItemWhse().getItemNumber().trim(), "a0414", "", "") %></b></td>
     <td class="td04140102" style="width:2%">&nbsp;</td>
	 <td class="td04140102">Description:</td>
	 <td class="td04140102">&nbsp;<b><%= thisSpec.getItemWhse().getItemDescription().trim() %></b></td>
	 <td class="td04140102" style="width:2%">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">Spec Revision Date:</td>
	 <td class="td04140102">&nbsp;<b><%= InqSpecification.formatDateFromyyyymmdd(thisSpec.getRevisionDate()) %></b></td>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">Spec Supercedes Date:</td>
	 <td class="td04140102">&nbsp;<b><%= InqSpecification.formatDateFromyyyymmdd(thisSpec.getSupercedesDate()) %></b></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">Formula Number:</td>
	 <td class="td04140102">&nbsp;<b><%= HTMLHelpersLinks.routerFormula(thisSpec.getFormulaNumber(), "", "a0414", "", "") %></b></td>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">Formula Name:</td>
	 <td class="td04140102">&nbsp;<b><%= formula.getFormulaName() %></b></td>
	 <td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
	 <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">Formula Revision Date:</td>
	 <td class="td04140102">&nbsp;<b><%= InqSpecification.formatDateFromyyyymmdd(formula.getRevisionDate()) %></b></td>
     <td class="td04140102">&nbsp;</td>
	 <td class="td04140102">Formula Supercedes Date:</td>
	 <td class="td04140102">&nbsp;<b><%= InqSpecification.formatDateFromyyyymmdd(formula.getSupercedesDate()) %></b></td>	 
	 <td class="td04140102">&nbsp;</td>
	</tr>
   </table>
  </td>       
  <td style="width:1%">&nbsp;</td>
 </tr>    
<% 
  int imageCount  = 3;
  int expandCount = 1;
  request.setAttribute("listType", "TEST");
%>  
 <tr>
  <td></td>
  <td>
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("O", "Analytical Testing", 0, expandCount, imageCount, 1, 0) %>
         <jsp:include page="dtlCPGSpecTestProcessTable.jsp"></jsp:include>
      </span>    
     </td>
    </tr>
<% 
  imageCount++;
  expandCount++;
  request.setAttribute("listType", "PROCESS");
%>  
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("O", "Process Parameters", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlCPGSpecTestProcessTable.jsp" %></jsp:include>   
      </span>    
     </td>
    </tr>   
<% 
  imageCount++;
  expandCount++;
%>  
    <tr class="tr02">
     <td colspan="4" class="td04121405">
      <%= JavascriptInfo.getExpandingSection("O", "Additional Packaging Instructions", 0, expandCount, imageCount, 1, 0) %>
      <jsp:include page="dtlCPGSpecPacking.jsp" %></jsp:include>   
      </span>    
     </td>
    </tr>   


<%
  if (0 == 1)
  {
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
//     request.setAttribute("gtin", thisGtin.getGtinNumber());
%> 
    <tr class="tr02">
     <td class="td04121405" colspan="4">
      <%= JavascriptInfo.getExpandingSection("C", "GTIN Family Tree", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right">
       <%//include file="dtlGTINFamilyTree.jsp" %>   
       </span>    
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
      <%@ include file="../Utilities/displayIFSNew.jsp" %>  
          <jsp:include page="../Utilities/displayIFSNew.jsp"></jsp:include>
      </span>    
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
      </span>    
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
      </span>    
     </td>
    </tr>           
  </table>
  </td>
  <td></td>
 </tr>  
<%
   }
   }   
%>  
</table>  
   <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>