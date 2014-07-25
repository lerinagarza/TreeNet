<%@ page import="com.treetop.utilities.UtilityDateTime, 
    com.treetop.utilities.html.HTMLHelpersLinks,
    com.treetop.businessobjects.DateTime,
    com.treetop.businessobjects.ProductStructureMaterial,
    java.util.Vector
" %>
<%  
//---------------  listProductStructureMaterialsTable.jsp  ------------------------------------------//
// 
//    Author :  Teri Walton  11/1/13
//   CHANGES:  Moved into view from APP
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
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
    
    <div class="row-fluid">
    <div class="span12"></div>
    <table class="styled full-width row-highlight">
        <colgroup>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col>
        </colgroup>
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
//----------------------------------
//  Header Section
%>        
   <tr>
    <th class="left" colspan="8">
     <b>Facility:&#160;<%= thisFacility.getFacility() %>&#160;&#160;&#160;<%= thisFacility.getProducedItem().getItemNumber() %>&#160;<%= thisFacility.getProducedItem().getItemDescription() %></b>
    </th>
   </tr>     
   <tr>
     <th class="center">Sequence</th>
     <th class="center">From Date</th>
     <th class="center">Component Number</th>
     <th class="center">Quantity</th>
     <th class="center">UOM</th>
     <th class="center">Status</th>
     <th class="center">Description</th>
     <th class="center">Supplier Summary</th>
   </tr>
<% 
//---------------------------------------
//  Detail Section for the Table - For each Facility

 for (int y=0; y<listMaterialsForFacility.size(); y++) {
    ProductStructureMaterial thisrow = (ProductStructureMaterial) listMaterialsForFacility.elementAt(y);
    String displayDate = thisrow.getFromDate();
    try{
       DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(displayDate);
       displayDate = dt.getDateFormatMMddyyyySlash();
    }catch(Exception e)  {}
%>     
   <tr>
     <td style="text-align:center">&#160;<%= thisrow.getMaterialSequence() %>&#160;</td>
     <td>&#160;<%= displayDate %>&#160;</td>
     <td style="text-align:center">&#160;<%= thisrow.getMaterialItem().getItemNumber() %></td> 
     <td style="text-align:right">&#160;<%= thisrow.getConsumedQuantity() %></td>   
     <td>&#160;<%= thisrow.getConsumedUnitOfMeasure() %></td>
     <td style="text-align:center">&#160;<%= thisrow.getMaterialItem().getStatus() %></td>   
     <td>&#160;<%= thisrow.getMaterialItem().getItemDescription().trim() %></td> 
     <td>&#160;
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
    </div>