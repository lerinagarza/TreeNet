<%@ page import = "com.treetop.app.coa.BuildCOA" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%


//---------------- APP/COA/buildCOALot.jsp -----------------------//
//   Use with buildCOA
// Author   :  Teri Walton       12/23/09
// Changes  :
//   Date       Name          Comments
//   ----       ----          --------
//------------------------------------------------------------//
 Lot lotObject = new Lot();
 // Date Format
 String dateFormat1 = "0";
 try
 {
	BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	lotObject = ((AttributeValue)((BeanCOA) bldCOA.getListReport().elementAt(0)).getListAttrValues().elementAt(0)).getLotObject();
	if (bldCOA.getRequestType().equals(""))
	   bldCOA.setRequestType("build");
	dateFormat1 = bldCOA.getBuildDateFormat();
	if (dateFormat1 == null ||
	    dateFormat1.trim().equals(""))
	  dateFormat1 = "0";  
 }
 catch(Exception e)
 {
    System.out.println("error in buildCOALot.jsp" + e);
 }  
%>
<html>
 <head>
 </head>
 <body>
<%
  if (lotObject.getLotNumber() != null &&
      !lotObject.getLotNumber().equals(""))
  {
%>
  <table class="table00" cellspacing="0">
<%
  // Row 1
%>   
   <tr class="tr00">
    <td class="td04140902" rowspan="4" style="width:3%">&nbsp;</td>
    <td class="td04140402"><b>Lot Number:</b></td>
    <td class="td04140402">
     <%= lotObject.getLotNumber().trim() %>
     <%= HTMLHelpersInput.inputBoxHidden("inqLot", lotObject.getLotNumber()) %>&nbsp;
    </td>
    <td class="td04140402"><b>Production Date:</b></td>
    <td class="td04140402">
      <%=  BuildCOA.convertDate(lotObject.getManufactureDate(), new Integer(dateFormat1)) %>&nbsp;
    </td>
    <td class="td04141002" rowspan="4" style="width:3%">&nbsp;</td>
   </tr>
<%
  // Row 2
%>
   <tr class="tr00">
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102"><b>Expiration Date:</b></td>
    <td class="td04140102">
     <%=  BuildCOA.convertDate(lotObject.getExpirationDate(), new Integer(dateFormat1)) %>&nbsp;
    </td>
   </tr>   
<%
  // Row 3
%>
   <tr class="tr00">
    <td class="td04140102"><b>Item:</b></td>
    <td class="td04140102">
     <%= lotObject.getItemNumber().trim() %>&nbsp;
    </td>
    <td class="td04140102"><b>Description:</b></td>
    <td class="td04140102">
     <%= lotObject.getItemDescription().trim() %>&nbsp;
    </td>
   </tr>
  </table>
<%
  }else{
%>  
  <table class="table01" cellspacing="0">
   <tr>
    <td class="td044CL001">
     Lot Information Not Found, please choose Select and Option and choose another.
    </td>
   </tr>
  </table>
<%
   }
%>  
 </body>
</html>
