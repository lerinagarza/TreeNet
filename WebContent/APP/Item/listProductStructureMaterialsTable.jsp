<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.businessobjects.ProductStructureMaterial" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "java.util.Vector" %>
<% 
//---------------  listProductStructureMaterialsTable.jsp  ------------------------------------------//
// 
//    Author :  Teri Walton  11/8/11
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the VECTOR - on the request.getAttribute
 // Allow this jsp, to be included in any applicable area 
 Vector getData = new Vector();
 try
 {
	getData = (Vector) request.getAttribute("listMaterials");
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught 
 }    
%>

  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
// First outside Vector is for the Produced Item By Facility
  if (getData.size() > 0)
  { 
    for (int x = 0; x < getData.size(); x++)
    {
      Vector listMaterialsForFacility = (Vector) getData.elementAt(x);
      if (listMaterialsForFacility.size() > 0)
      { 
        ProductStructureMaterial thisFacility = (ProductStructureMaterial) listMaterialsForFacility.elementAt(0);
%>  
  <tr class="tr00">
   <td class="td0516" colspan="7"><b>Facility:&#160;<%= thisFacility.getFacility() %>&#160;&#160;&#160;<%= thisFacility.getProducedItem().getItemNumber() %>&#160;<%= thisFacility.getProducedItem().getItemDescription() %></b>
   </td>
  </tr>
<%
//----------------------------------------------------------------------------
// HEADING SECTION
//----------------------------------------------------------------------------
%>
   <tr class="tr02">
    <td class="td04120605" style="text-align:center"><b>Sequence</b></td>
    <td class="td04120605" style="text-align:center"><b>From Date</b></td>    
    <td class="td04120605" style="text-align:center"><b>Component Number</b></td>  
    <td class="td04120605" style="text-align:center"><b>Quantity</b></td>  
    <td class="td04120605" style="text-align:center"><b>UOM</b></td>  
    <td class="td04120605" style="text-align:center"><b>Status</b></td>   
    <td class="td04120605" style="text-align:center"><b>Description</b></td>  
    <td class="td04120605" style="text-align:center"><b>Supplier Summary</b></td>
   </tr> 
<%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------
 
    for (int y = 0; y < listMaterialsForFacility.size(); y++)
    {
      ProductStructureMaterial thisrow = (ProductStructureMaterial) listMaterialsForFacility.elementAt(y);
      String displayDate = thisrow.getFromDate();
      try{
         DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(displayDate);
         displayDate = dt.getDateFormatMMddyyyySlash();
      }catch(Exception e)
      {}
%>     
   <tr class="tr00">
    <td class="td04121524" style="text-align:center">&#160;<%= thisrow.getMaterialSequence() %>&#160;</td>
    <td class="td04121524">&#160;<%= displayDate %>&#160;</td>
    <td class="td04121524" style="text-align:center">&#160;<%= thisrow.getMaterialItem().getItemNumber() %></td> 
    <td class="td04121524" style="text-align:right">&#160;<%= thisrow.getConsumedQuantity() %></td>   
    <td class="td04121524">&#160;<%= thisrow.getConsumedUnitOfMeasure() %></td>
    <td class="td04121524" style="text-align:center">&#160;<%= thisrow.getMaterialItem().getStatus() %></td>   
    <td class="td04121524">&#160;<%= thisrow.getMaterialItem().getItemDescription().trim() %></td> 
    <td class="td04121524">&#160;
<%
   if (!thisrow.getMaterialItem().getSupplierSummaryURL().trim().equals(""))
      out.println(HTMLHelpersLinks.basicLink("To Summary", thisrow.getMaterialItem().getSupplierSummaryURL().trim(), "", "", ""));
%>    
    </td>
   </tr>   
<%
        } // end of the inside for loop
      } // end of the if no inside records chosen
    } // end of the inside for loop
  } // end of the if no inside records chosen
%>     
  </table>