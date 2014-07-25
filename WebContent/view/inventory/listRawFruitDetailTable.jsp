<%@ page import = "com.treetop.app.inventory.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>

<%
InqInventory ii2 = (InqInventory) request.getAttribute("inqViewBean");
Vector listLines = (Vector) ii2.getBeanInventory().getListOfInventory();
%>    

   	<colgroup span="10">
   		<col />
   		<col />
   		<col />
   		<col />
   		<col />
   		<col />
   		<col />
   		<col />
   		<col />
   		<col />
   	</colgroup>
   	<colgroup span="3">
   		<col />
   		<col />
   		<col />
   	</colgroup>
   	<thead>
		<tr>
    		<th title="TGRAD - is an Attribute Value in Movex -- (Examples: Juice, Sauce, P3)">TGRAD</th>
    		<th title="This Value Comes from the Item Master File - the Environment Group Field">Organic or Conv</th>
    		<th>Run Type</th>
    		<th>COO</th>
    		<th>Item</th>
    		<th>Lot Number</th>
    		<th title="VAR - is an Attribute Value in Movex -- (Examples: Juice, Sauce, P3)">Variety</th>
    		<th>Receiving Date</th>   
    		<th>Supplier</th>
    		<th>Warehouse Location</th>
    		<th>Quantity in Pounds</th>
    		<th>Quantity in Bins</th>
    		<th>Quantity in Tons</th>   
   		</tr>
	</thead>
	<tbody>
<%   
String     saveTGRAD       = "";
String     saveORGANIC     = "";
String     saveITEM        = "";
String     saveITEMDescription = "";

// for totals
BigDecimal binQtyTGRAD 	   = new BigDecimal("0");
BigDecimal binQtyORGANIC   = new BigDecimal("0");
BigDecimal binQtyITEM 	   = new BigDecimal("0");
BigDecimal binQty 		   = new BigDecimal("0");
BigDecimal tonQtyTGRAD 	   = new BigDecimal("0");
BigDecimal tonQtyORGANIC   = new BigDecimal("0");
BigDecimal tonQtyITEM 	   = new BigDecimal("0");
BigDecimal tonQty 		   = new BigDecimal("0");
BigDecimal poundQtyTGRAD   = new BigDecimal("0");
BigDecimal poundQtyORGANIC = new BigDecimal("0");
BigDecimal poundQtyITEM    = new BigDecimal("0");
BigDecimal poundQty 	   = new BigDecimal("0");

for (int x = 0; x < listLines.size(); x++)
{
	Inventory inv = (Inventory) listLines.elementAt(x);

	// subtotal line for organic
	if (x > 0 &&
	    (!saveTGRAD.equals(inv.getGrade()) ||
	    !saveORGANIC.equals(inv.getConventionalOrganic()) ||
	    !saveITEM.equals(inv.getItemNumber())))
	{  
%>  

	<tr class="sub-total">
    	<td colspan="10">Sub-Total for <%= saveITEM %>&nbsp;-&nbsp;<%= saveITEMDescription %>:
    	<td><%= HTMLHelpersMasking.maskBigDecimal(poundQtyITEM.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(binQtyITEM.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(tonQtyITEM.toString(), 2) %></td>
	</tr>

<%
		// reset the organic save value
		saveITEM     = "";
        saveITEMDescription = "";
        tonQtyITEM   = new BigDecimal("0");
        binQtyITEM   = new BigDecimal("0");
        poundQtyITEM = new BigDecimal("0");
        
        if (!saveTGRAD.equals(inv.getGrade()) ||
        	!saveORGANIC.equals(inv.getConventionalOrganic()))
        {
		// if either has changed then print the subtotal for the organic value  
%>  

	<tr class="light-bg sub-total">
    	<td colspan="10">Sub-Total for <%= saveORGANIC %>:</td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(poundQtyORGANIC.toString(), 0) %></td>    
    	<td><%= HTMLHelpersMasking.maskBigDecimal(binQtyORGANIC.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(tonQtyORGANIC.toString(), 2) %></td>
   	</tr>
   	
<%
			// reset the organic save value
			saveORGANIC     = "";
        	tonQtyORGANIC   = new BigDecimal("0");
        	binQtyORGANIC   = new BigDecimal("0");
        	poundQtyORGANIC = new BigDecimal("0");
        
        	if (!saveTGRAD.equals(inv.getGrade()))
        	{
        		if (saveTGRAD.trim().equals("")) 
        		{
        	         saveTGRAD = "'Blank'";
				}
%>

	<tr class="dark-bg sub-total">
    	<td colspan="10">Sub-Total for <%= saveTGRAD %>:</td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(poundQtyTGRAD.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(binQtyTGRAD.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(tonQtyTGRAD.toString(), 2) %></td>
	</tr>
	<tr class="spacer">
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
	</tr>
	
<% 
				// reset the TGRAD save value
            	saveTGRAD     = ""; 
            	tonQtyTGRAD   = new BigDecimal("0");
            	binQtyTGRAD   = new BigDecimal("0");
            	poundQtyTGRAD = new BigDecimal("0");
            
			} // end of the TGRAD subtotal
		} // end of the organic subtotal 
	}

	saveTGRAD   = inv.getGrade();
	saveORGANIC = inv.getConventionalOrganic();
	saveITEM    = inv.getItemNumber();
	saveITEMDescription = inv.getItemDescription();

	// detail for 1 row
	String     mouseOverTons  = "Pounds (" + HTMLHelpersMasking.maskBigDecimal(inv.getQuantityOnHand(),0) + ") divided by 2000 is the tons";
	BigDecimal detailBins     = new BigDecimal("0");
	
	try
	{
		if (inv.getNumberOfBins() != null && !inv.getNumberOfBins().equals(""))
	    {	
	    	detailBins = new BigDecimal(inv.getNumberOfBins());
	    }
	        
	    // adding information for the "total" lines
	    binQty        = binQty.add(detailBins);
	    binQtyTGRAD   = binQtyTGRAD.add(detailBins);
	    binQtyORGANIC = binQtyORGANIC.add(detailBins);
	    binQtyITEM    = binQtyITEM.add(detailBins);
	    
	    if (!inv.getQuantityInTons().equals(""))
		{
   	 		tonQty        = tonQty.add(new BigDecimal(inv.getQuantityInTons()));
    		tonQtyTGRAD   = tonQtyTGRAD.add(new BigDecimal(inv.getQuantityInTons()));
    		tonQtyORGANIC = tonQtyORGANIC.add(new BigDecimal(inv.getQuantityInTons()));
        	tonQtyITEM    = tonQtyITEM.add(new BigDecimal(inv.getQuantityInTons()));
		}	
		
		if (!inv.getQuantityOnHand().equals(""))
    	{
    		poundQty        = poundQty.add(new BigDecimal(inv.getQuantityOnHand()));
        	poundQtyTGRAD   = poundQtyTGRAD.add(new BigDecimal(inv.getQuantityOnHand()));
        	poundQtyORGANIC = poundQtyORGANIC.add(new BigDecimal(inv.getQuantityOnHand()));
        	poundQtyITEM    = poundQtyITEM.add(new BigDecimal(inv.getQuantityOnHand()));
		}	
	}
	catch(Exception e)
	{
		out.println("ERROR" + e);
	}
%>
    
	<tr>
    	<td><%= inv.getGrade() %></td>
    	<td><%= inv.getConventionalOrganic() %></td>
    	<td><%= inv.getRunType() %></td>
    	<td><%= inv.getCountryOfOrigin() %></td>
    	<td><%= inv.getItemNumber() %></td>
    	<td><%= inv.getLotNumber() %></td>
    	<td><%= inv.getVariety() %></td>
    	<td>
			<%
   				String showDate = inv.getReceiptDate();
   				try
   				{
    				DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(showDate);
    				showDate = dt.getDateFormatMMddyySlash();
   				}
   				catch(Exception e)
   				{}
			%>    
    		<%= showDate %>
    	</td>
    	<td><%= inv.getSupplier().getSupplierNumber() %>&nbsp;-&nbsp;<%= inv.getSupplier().getSupplierName() %></td>
    	<td><%= inv.getWarehouse() %>&nbsp;&nbsp;<%= inv.getLocation() %></td>
    	<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(inv.getQuantityOnHand(), 0) %></td>
    	<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(detailBins.toString(), 0) %></td>
    	<td class="right" title="<%= mouseOverTons %>"><%= HTMLHelpersMasking.maskBigDecimal(inv.getQuantityInTons(), 2) %></td>
	</tr>
	
<%
} // end for loop
   
// grand total rows
if (saveTGRAD.trim().equals(""))
{
	saveTGRAD = "'Blank'";
}
%>
    
	<tr class="sub-total">
    	<td colspan="10">&nbsp;Sub-Total for <%= saveITEM %>&nbsp;-&nbsp;<%= saveITEMDescription %>:</td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(poundQtyITEM.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(binQtyITEM.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(tonQtyITEM.toString(), 2) %></td>
	</tr>
	<tr class="light-bg sub-total">
    	<td colspan="10">Sub-Total for <%= saveORGANIC %>:</td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(poundQtyORGANIC.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(binQtyORGANIC.toString(), 0) %></td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(tonQtyORGANIC.toString(), 2) %></td>
	</tr>
	<tr class="dark-bg sub-total">
	    <td colspan="10">Sub-Total for <%= saveTGRAD %>:</td>
	    <td><%= HTMLHelpersMasking.maskBigDecimal(poundQtyTGRAD.toString(), 0) %></td>
	    <td><%= HTMLHelpersMasking.maskBigDecimal(binQtyTGRAD.toString(), 0) %></td>
	    <td><%= HTMLHelpersMasking.maskBigDecimal(tonQtyTGRAD.toString(), 2) %></td>
	</tr>
	<tr class="grand-total">
	    <td colspan="10">TOTAL FOR ENTIRE SELECTION:</td>
	    <td><%= HTMLHelpersMasking.maskBigDecimal(poundQty.toString(), 0) %></td>
	    <td><%= HTMLHelpersMasking.maskBigDecimal(binQty.toString(), 0) %></td>
	    <td><%= HTMLHelpersMasking.maskBigDecimal(tonQty.toString(), 2) %></td>
	</tr>
	</tbody>   