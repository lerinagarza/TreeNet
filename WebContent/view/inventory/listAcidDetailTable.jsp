<%@page language="java" %>
<%@page import = "com.treetop.businessobjectapplications.*" %>
<%@page import = "com.treetop.businessobjects.*" %>
<%@page import = "com.treetop.app.inventory.*" %>
<%@page import = "com.treetop.utilities.html.*" %>
<%@page import = "java.util.*" %>
<%@page import = "java.math.*" %>

<%
InqInventory ii2 = (InqInventory) request.getAttribute("inqViewBean");
if (ii2 == null) {
	ii2 = new InqInventory();
}

Vector listLines = (Vector) ii2.getBeanInventory().getByItemVectorOfInventory();
%>
	<colgroup span="3">
	</colgroup>
	<colgroup span="2">
		<col />
		<col />
	</colgroup>
	<colgroup span="3">
		<col />
		<col />
		<col />
	</colgroup>
	
<%
// for totals
String     saveItem             = "";
String     saveFacility         = "";
BigDecimal totalQty             = new BigDecimal("0");
BigDecimal itemFacilityTotalQty = new BigDecimal("0");
BigDecimal totalQtyFS           = new BigDecimal("0");
BigDecimal itemFacilityTotalFS  = new BigDecimal("0");

// Acid Total is Fruit Solids multiplied by Acid added up (on total divided by total FS)
BigDecimal totalAcidWW          = new BigDecimal("0");
BigDecimal totalAcidWV          = new BigDecimal("0");
BigDecimal itemFacilityAcidWW   = new BigDecimal("0"); 
BigDecimal itemFacilityAcidWV   = new BigDecimal("0");
    
// for details 
String     saveLot              = "";
String     saveWhse             = "";
String     saveLocation         = "";
String     saveQty              = "";
String     saveBrix             = "";
String     saveBrixConversion   = "";
String     saveAcidWW           = "";
String     saveAcidWV           = "";
String     printHeading         = "Y"; // indicator flag
	
for (int x = 0; x < listLines.size(); x++)
{
   	AttributeValue av = (AttributeValue) listLines.elementAt(x);
   	
	if (printHeading.trim().equals("Y"))
	{
		if (!saveItem.equals(""))
		{
			// print item/facility total
%>

	<tr class="sub-total">
		<td colspan = "3">
			Item/Facility Sub-Total:
		</td>
    	<td>
    		<%= HTMLHelpersMasking.maskBigDecimal(itemFacilityTotalQty.toString(), 2) %>
    	</td>
    	<td>
     		<%
			try
			{
        		// BigDecimal aveBrix = itemFacilityTotalFS.divide(itemFacilityTotalQty, 0);
        		// out.println(aveBrix);
        	 	// will need to take that aveBrix then run it back into the table to come up with a Displayable Brix
        		out.println("&nbsp;");
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>     
		</td>
    	<td>
    		<%= HTMLHelpersMasking.maskBigDecimal(itemFacilityTotalFS.toString(), 0) %>
    	</td>   
    	<td>
			<%     
       		try
      		{
        		BigDecimal aveWV = itemFacilityAcidWV.divide(itemFacilityTotalFS, 0);
        	 	out.println(HTMLHelpersMasking.maskBigDecimal(aveWV.toString(), 2));
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
    	</td>
    	<td>
     		<%     
       		try
      		{
        		BigDecimal aveWW = itemFacilityAcidWW.divide(itemFacilityTotalFS, 0);
        	 	out.println(HTMLHelpersMasking.maskBigDecimal(aveWW.toString(), 2));
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
    	</td>
   	</tr>
   	      
<%
    		itemFacilityTotalQty = new BigDecimal("0");
    		itemFacilityTotalFS  = new BigDecimal("0");
   			itemFacilityAcidWW   = new BigDecimal("0"); 
   			itemFacilityAcidWV   = new BigDecimal("0");

   		} // end save item if

		// print heading for every new item or facility
%>   
	
   	<tr class="dark-bg">
    	<td colspan = "5">
    		<% 
    		// UTF-8's encoding of the degree symbol does not match up with Unicode's
    		// This is a hack to ensure they display correctly in the table.
    		String replaceDeg = av.getLotObject().getItemDescription().replace("\u00B0", "&deg;"); 
    		%>
    		Item:&nbsp;<%= av.getItemNumber() + "&nbsp;" + replaceDeg %>
    	</td>
    	<td colspan = "3">
    		Facility:&nbsp;<%= av.getLotObject().getFacility() + "&nbsp;" + av.getLotObject().getFacilityName() %>
    	</td>
   	</tr>
   	<tr>
   		<th>Lot</th>
    	<th>Warehouse</th>
    	<th>Location</th>
    	<th>Quantity</th>
    	<th>Brix</th>
    	<th>Fruit Solids</th>   
    	<th>Acid W/V</th>
    	<th>Acid W/W</th>
   	</tr>
	
<%
   		printHeading = "N";
    	
	} // end of print heading section
   	
   	// each line must be built from approximately 3 records (acid ww, acid wv and brix)
   	if (!saveItem.equals(""))
	{ 
		// not the first time through
     	if (!saveItem.trim().equals(av.getItemNumber().trim()) ||
       		!saveFacility.trim().equals(av.getLotObject().getFacility().trim()) ||
        	!saveLot.trim().equals(av.getLotObject().getLotNumber().trim()) ||
        	!saveWhse.trim().equals(av.getLotObject().getWarehouse().trim()) ||
        	!saveLocation.trim().equals(av.getLotObject().getLocation().trim()))
      	{ 
      		// something has changed, print out the detail line
%>
      
   	<tr>
    	<td ><%= saveLot %></td>
    	<td ><%= saveWhse %></td>
    	<td ><%= saveLocation %></td>
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveQty, 2) %></td>
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveBrix, 1) %></td>
			<%
   			BigDecimal displayFS = new BigDecimal("0");
   			try
   			{
    			displayFS = (new BigDecimal(saveQty)).multiply(new BigDecimal(saveBrixConversion));
   			}
   			catch(Exception e)
   			{}
			%>    
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(displayFS.toString(), 0) %></td>   
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveAcidWV, 2) %></td>
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveAcidWW, 2) %></td>
   	</tr> 
				
<%
    		try
   			{
    			totalQty             = totalQty.add(new BigDecimal(saveQty));
    			itemFacilityTotalQty = itemFacilityTotalQty.add(new BigDecimal(saveQty));
        		totalQtyFS           = totalQtyFS.add(displayFS);
        		itemFacilityTotalFS  = itemFacilityTotalFS.add(displayFS);
        		totalAcidWW          = totalAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
        		totalAcidWV          = totalAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
        		itemFacilityAcidWW   = itemFacilityAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
        		itemFacilityAcidWV   = itemFacilityAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
			}	
 		    catch(Exception e)
    		{}

    		if (!saveItem.trim().equals(av.getItemNumber()) ||
        		!saveFacility.trim().equals(av.getLotObject().getFacility()))
    		{
        		printHeading = "Y";
    		}
            			
            saveItem     = "";
    		saveFacility = "";
   			saveLot      = "";
   			saveWhse     = "";
    		saveLocation = "";
    		saveQty      = "";
    		saveBrix     = "";
    		saveAcidWV   = "";
    		saveAcidWW   = "";
    	}	
	}
   
	saveItem     = av.getItemNumber().trim();
	saveFacility = av.getLotObject().getFacility().trim();
	saveLot      = av.getLotObject().getLotNumber().trim();
	saveWhse     = av.getLotObject().getWarehouse().trim();
	saveLocation = av.getLotObject().getLocation().trim();
	saveQty      = av.getLotObject().getQuantity().trim();
   
	if (av.getAttribute().trim().equals("BRIX"))
	{
		saveBrix = av.getValue();
		saveBrixConversion = av.getLotObject().getBrixConversion().trim();
   	}
   	
   	if (av.getAttribute().trim().equals("ACID MAL W/V"))
   	{
    	saveAcidWV = av.getValue();
   	}
   
   	if (av.getAttribute().trim().equals("ACID MAL W/W"))
   	{
    	saveAcidWW = av.getValue();
   	}
   
} // end for loop
    
// print last detail line
%>  
  
	<tr>
    	<td ><%= saveLot %></td>
    	<td ><%= saveWhse %></td>
    	<td ><%= saveLocation %></td>
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveQty, 2) %></td>
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveBrix, 1) %></td>
	
<%
BigDecimal displayFS = new BigDecimal("0");

try
{
   	displayFS = (new BigDecimal(saveQty)).multiply(new BigDecimal(saveBrixConversion));
}
catch(Exception e)
{}
%>
	    
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(displayFS.toString(), 0) %></td>   
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveAcidWV, 2) %></td>
    	<td class="quantity"><%= HTMLHelpersMasking.maskBigDecimal(saveAcidWW, 2) %></td>
	</tr> 
	
<%
try
{
	totalQty             = totalQty.add(new BigDecimal(saveQty));
	itemFacilityTotalQty = itemFacilityTotalQty.add(new BigDecimal(saveQty));
	totalQtyFS           = totalQtyFS.add(displayFS);
	itemFacilityTotalFS  = itemFacilityTotalFS.add(displayFS);
	totalAcidWW          = totalAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
	totalAcidWV          = totalAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
	itemFacilityAcidWW   = itemFacilityAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
	itemFacilityAcidWV   = itemFacilityAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
}
catch(Exception e)
{}

// item/facility subtotal
%>
	     
	<tr class="sub-total">
    	<td colspan = "3">Item/Facility Sub-Total:</td>
    	<td>
    		<%= HTMLHelpersMasking.maskBigDecimal(itemFacilityTotalQty.toString(), 2) %>
    	</td>
    	<td>
			<%
    		try
    		{
    			// BigDecimal aveBrix = itemFacilityTotalFS.divide(itemFacilityTotalQty, 0);
        		// out.println(aveBrix);
        		// will need to take that aveBrix then run it back into the table to come up with a displayable brix
        		out.println("&nbsp;");
			}
    		catch(Exception e)
    		{
    			out.println("&nbsp;");
    		}
			%>     
    	</td>
    	<td>
    		<%= HTMLHelpersMasking.maskBigDecimal(itemFacilityTotalFS.toString(), 0) %>
    	</td>   
    	<td>
    		<%     
    		try
    		{
    			BigDecimal aveWV = itemFacilityAcidWV.divide(itemFacilityTotalFS, 0);
    			out.println(HTMLHelpersMasking.maskBigDecimal(aveWV.toString(), 2));
    		}
    		catch(Exception e)
    		{
    			out.println("&nbsp;");
    		}
			%>      
    	</td>
    	<td>
     		<%     
    		try
    		{
    			BigDecimal aveWW = itemFacilityAcidWW.divide(itemFacilityTotalFS, 0);
    			out.println(HTMLHelpersMasking.maskBigDecimal(aveWW.toString(), 2));
    		}
    		catch(Exception e)
    		{
    			out.println("&nbsp;");
    		}
			%>      
     	</td>
	</tr>      
   
<%
// grand totals
%>   
	 
	<tr class="grand-total">
    	<td colspan = "3">TOTALS FOR ALL:</td>
    	<td>
    		<%= HTMLHelpersMasking.maskBigDecimal(totalQty.toString(), 2) %>
    	</td>
    	<td>     
			<%
      		try
      		{
         		//BigDecimal aveBrix = itemFacilityTotalFS.divide(itemFacilityTotalQty, 0);
         		//out.println(aveBrix);
        		// will need to take that aveBrix then run it back into the table to come up with a Displayable Brix
        		out.println("&nbsp;");
      		}
      		catch(Exception e)
      		{
         		out.println("&nbsp;");
    		}
			%>     
    	</td>
    	<td>
    		<%= HTMLHelpersMasking.maskBigDecimal(totalQtyFS.toString(), 0) %>
    	</td>   
    	<td>
			<%     
       		try
      		{
         		BigDecimal aveWV = totalAcidWV.divide(totalQtyFS, 0);
         		out.println(HTMLHelpersMasking.maskBigDecimal(aveWV.toString(), 2));
      		}
      		catch(Exception e)
      		{
         		out.println("&nbsp;");
      		}
			%>      
    	</td>
    	<td>
     		<%     
       		try
      		{
        		BigDecimal aveWW = totalAcidWW.divide(totalQtyFS, 0);
        		out.println(HTMLHelpersMasking.maskBigDecimal(aveWW.toString(), 2));
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
    	</td>
	</tr>      