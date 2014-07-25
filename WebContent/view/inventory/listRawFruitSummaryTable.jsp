<%@page language="java" %>
<%@ page import = "com.treetop.app.inventory.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.businessobjects.Inventory" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
   
<%
InqInventory ii2 = (InqInventory) request.getAttribute("inqViewBean");
Vector listLines = (Vector) ii2.getBeanInventory().getListOfInventory();
%>

	<colgroup span="3">
		<col />
		<col />
		<col class="text" />
	</colgroup>
	<colgroup>
		<col class="quantity" />
	</colgroup>
	<thead>
		<tr>
    		<th title="TGRAD - is an Attribute Value in Movex -- (Examples: Juice, Sauce, P3)">TGRAD</th>
    		<th title="This Value Comes from the Item Master File - the Environment Group Field">Organic or Conventional</th>
    		<th title="VAR - is an Attribute Value in Movex -- (Examples: Juice, Sauce, P3)">Variety</th>
    		<th>Quantity in Tons</th>
   		</tr>
	</thead>
	<tbody>
<%   
// to get to fruit solids at actual brix
String     saveTGRAD     = "";
String     saveORGANIC   = "";

// for totals
BigDecimal tonQtyTGRAD   = new BigDecimal("0");
BigDecimal tonQtyORGANIC = new BigDecimal("0");
BigDecimal tonQty 	     = new BigDecimal("0");
      
for (int x = 0; x < listLines.size(); x++)
{
	Inventory inv = (Inventory) listLines.elementAt(x);
    
    // subtotal line for organic
	if (x > 0 &&
		(!saveTGRAD.equals(inv.getGrade()) ||
		!saveORGANIC.equals(inv.getConventionalOrganic())))
	{    
		// if either has changed then print the subtotal for the organic value  
%>  

	<tr class="sub-total">
		<td colspan="3">Sub-Total for <%= saveORGANIC %>:</td>
    	<td><%= HTMLHelpersMasking.maskNumber(tonQtyORGANIC.toString(), 2) %></td>
	</tr>

<%
		// reset the organic save value
		saveORGANIC   = "";
    	tonQtyORGANIC = new BigDecimal("0");
    
    	if (!saveTGRAD.equals(inv.getGrade()))
    	{
    		if (saveTGRAD.trim().equals(""))
    	    {
    	    	saveTGRAD = "'Blank'";
    	    }
%>

	<tr class="light-bg sub-total">
    	<td colspan="3">Sub-Total for <%= saveTGRAD %>:</td>
    	<td><%= HTMLHelpersMasking.maskNumber(tonQtyTGRAD.toString(), 2) %></td>
   	</tr>
   	<tr class="spacer">
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
   	</tr>
   	
<% 
			// reset the TGRAD save value
			saveTGRAD = ""; 
        	tonQtyTGRAD = new BigDecimal("0");
        	
		}
	}
    
   	saveTGRAD   = inv.getGrade();
   	saveORGANIC = inv.getConventionalOrganic();
   
   	// detail for 1 row
	String mouseOverTons = "Pounds (" + HTMLHelpersMasking.maskNumber(inv.getQuantityOnHand(),0) + ") divided by 2000 is the tons";
    try
    {  
    	// adding information for the "total" lines
		if (!inv.getQuantityInTons().equals(""))
        {
        	tonQty = tonQty.add(new BigDecimal(inv.getQuantityInTons()));
            tonQtyTGRAD = tonQtyTGRAD.add(new BigDecimal(inv.getQuantityInTons()));
            tonQtyORGANIC = tonQtyORGANIC.add(new BigDecimal(inv.getQuantityInTons()));
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
    	<td class="text"><%= inv.getVariety() %></td>
    	<td class="quantity" title="<%= mouseOverTons %>"><%= HTMLHelpersMasking.maskNumber(inv.getQuantityInTons(), 2) %></td>
   	</tr>
   	
<%
} // end for loop
   
// grand total
if (saveTGRAD.trim().equals(""))
{
	saveTGRAD = "'Blank'";
}
%>    

	<tr class="sub-total">
    	<td colspan="3">Sub-Total for <%= saveORGANIC %>:</td>
    	<td><%= HTMLHelpersMasking.maskNumber(tonQtyORGANIC.toString(), 2) %></td>
   	</tr>
   	<tr class="light-bg sub-total">
    	<td colspan="3">Sub-Total for <%= saveTGRAD %>:</td>
    	<td><%= HTMLHelpersMasking.maskNumber(tonQtyTGRAD.toString(), 2) %></td>
   	</tr>
   	<tr class="grand-total">
    	<td colspan="3">TOTAL FOR ENTIRE SELECTION:</td>
    	<td><%= HTMLHelpersMasking.maskNumber(tonQty.toString(), 2) %></td>
   	</tr> 
</tbody>