<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import = "com.treetop.controller.sublot.DtlSubLot,
				  com.treetop.businessobjectapplications.BeanPeach,
				  com.treetop.businessobjects.TicketDetail" %>
<%
  DtlSubLot dsl = new DtlSubLot();
  BeanPeach bp = new BeanPeach();
  int secondColumn = 1;
  try{
	dsl = (DtlSubLot) request.getAttribute("dtlPeach");
	bp = dsl.getBeanPeach();
	int binCount = bp.getTicket().getTagDetail().size();
	if ((binCount % 2) == 1)
	   binCount = binCount + 1;
	secondColumn = binCount/2;
  } catch(Exception e){
  }
%>
<html>
<body>

 <jsp:include page="../../Include/heading_nobanner.jsp"></jsp:include>
 
 <jsp:include page="dtlHeader.jsp"></jsp:include>
 <br>
 <table class="table00" cellspacing="0" style="width:100%">
   <tr>
    <td class="td0416060502"><b>SUPPLIER</b></td>
    <td class="td04160605">&#160;<%= bp.getTicket().getSupplierNumber() %>&#160;-&#160;<%= bp.getTicket().getSupplierName() %></td> 
    <td class="td0416060502"><b>LOT NUMBER</b></td> 
    <td class="td04160605">&#160;<%= bp.getTicket().getLotNumber() %></td>
    <td class="td0416060502"><b>DEC</b></td> 
    <td class="td04160605">&#160;<%= bp.getTicket().getReceivingUser() %></td>  
   </tr> 
   <tr>
    <td class="td0416060502"><b>LOAD #</b></td>
    <td class="td04160605">&#160;<%= bp.getTicket().getLoadNumber()%></td> 
    <td class="td0416060502"><b>ITEM NUMBER</b></td> 
    <td class="td04160605" colspan="3">&#160;<%= bp.getTicket().getItemNumber() %>&#160;-&#160;<%= bp.getTicket().getItemDescription() %></td> 
   </tr> 
  </table>
<br> 
<div style="text-align:center">
 <table class="table00" cellspacing="0" style="width:90%;">
   <tr>
    <td class="td0416060502" style="text-align:center"><b>TAG #</b></td>
    <td class="td0416060502"><b>GROWER</b></td> 
    <td class="td0416060502" style="text-align:center"><b>TAG #</b></td> 
    <td class="td0416060502"><b>GROWER</b></td>   
   </tr>
<% 
   for (int x = 0; x < secondColumn; x++)
   {
      TicketDetail td1 = (TicketDetail) bp.getTicket().getTagDetail().elementAt(x);
      TicketDetail td2 = new TicketDetail();
      if ((x + secondColumn)< bp.getTicket().getTagDetail().size())
         td2 = (TicketDetail) bp.getTicket().getTagDetail().elementAt(x + secondColumn);
%>    
   <tr>
    <td class="td04140605">&#160;<%= td1.getTagNumber() %></td>
    <td class="td04140605">&#160;<%= td1.getGrowerName()%></td> 
    <td class="td04140605">&#160;<%= td2.getTagNumber() %></td>
    <td class="td04140605">&#160;<%= td2.getGrowerName()%></td>  
   </tr> 
<%
   }
%>   
  </table>
</div>
<%
   if (dsl.getListComments() != null &&
       !dsl.getListComments().isEmpty())
   { 
       request.setAttribute("screenType", "detailNoHeading");
  	   request.setAttribute("longFieldType", "comment");
  	   request.setAttribute("appType", "spec");  
  	   request.setAttribute("listKeyValues", dsl.getListComments());  
%>
<br>
<p><b>&#160;&#160;Comments:</b></p>
<div style="text-align:center">
 <table class="table00" cellspacing="0" style="width:90%">
  <tr>
   <td class="td03141405" colspan="2">
    <jsp:include page="../../APP/Utilities/updKeyValuesNew.jsp"></jsp:include>
   </td>
  </tr>  
 </table> 
</div>
<%
   }
%>            
<br> 
  <jsp:include page="dtlFooter.jsp"></jsp:include>

</body>
</html>