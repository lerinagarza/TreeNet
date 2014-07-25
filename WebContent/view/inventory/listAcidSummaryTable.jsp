<%@ page import = "com.treetop.businessobjectapplications.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.app.inventory.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>

<%
InqInventory ii2 = (InqInventory) request.getAttribute("inqViewBean");
if (ii2 == null) {
	ii2 = new InqInventory();
}

Vector listLines = (Vector) ii2.getBeanInventory().getByItemVectorOfInventory();
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
	<thead> 	  
		<tr>
			<th>Facility</th>
			<th>Domestic Gallons</th>
			<th>Average Acid W/V</th>
			<th>Average Acid W/W</th>
			<th>Purchased Gallons</th>
			<th>Average Acid W/V</th>   
			<th>Average Acid W/W</th>
			<th>Total Gallons</th>
			<th>Average Acid W/V</th>   
			<th>Average Acid W/W</th>
		</tr>
	</thead>
	<tbody>
	
<%   
// to get to Fruit Solids at Actual Brix
String     saveItem           = "";
String     saveFacility       = "";
String     saveFacilityDesc   = "";
String     saveLot            = "";
String     saveWhse           = "";
String     saveLocation       = "";
String     saveQty            = "";
String     saveBrixConversion = "0";
String     saveAcidWW         = "0";
String 	   saveAcidWV         = "0";
String     saveGroup          = "";

// for totals
BigDecimal domQty             = new BigDecimal("0");
BigDecimal domFS              = new BigDecimal("0");
BigDecimal domFSAcidWV        = new BigDecimal("0");
BigDecimal domFSAcidWW        = new BigDecimal("0");
BigDecimal purchQty           = new BigDecimal("0");
BigDecimal purchFS            = new BigDecimal("0");
BigDecimal purchFSAcidWV      = new BigDecimal("0");
BigDecimal purchFSAcidWW      = new BigDecimal("0");
BigDecimal totQty             = new BigDecimal("0");
BigDecimal totFS              = new BigDecimal("0");
BigDecimal totFSAcidWV        = new BigDecimal("0");
BigDecimal totFSAcidWW        = new BigDecimal("0");

// for totals by facility
BigDecimal facDomQty          = new BigDecimal("0");
BigDecimal facDomFS           = new BigDecimal("0");
BigDecimal facDomFSAcidWV     = new BigDecimal("0");
BigDecimal facDomFSAcidWW     = new BigDecimal("0");
BigDecimal facPurchQty        = new BigDecimal("0");
BigDecimal facPurchFS         = new BigDecimal("0");
BigDecimal facPurchFSAcidWV   = new BigDecimal("0");
BigDecimal facPurchFSAcidWW   = new BigDecimal("0");
BigDecimal facTotQty          = new BigDecimal("0");
BigDecimal facTotFS           = new BigDecimal("0");
BigDecimal facTotFSAcidWV     = new BigDecimal("0");
BigDecimal facTotFSAcidWW     = new BigDecimal("0");
String     addOne             = "N";	        

for (int x = 0; x < listLines.size(); x++)
{
	AttributeValue av = (AttributeValue) listLines.elementAt(x);
	
	// test to see if it is time to add it to the total.
	if (x != 0 &&
		(!saveItem.trim().equals(av.getItemNumber().trim()) ||
        !saveFacility.trim().equals(av.getLotObject().getFacility().trim()) ||
        !saveLot.trim().equals(av.getLotObject().getLotNumber().trim()) ||
        !saveWhse.trim().equals(av.getLotObject().getWarehouse().trim()) ||
        !saveLocation.trim().equals(av.getLotObject().getLocation().trim())))
	{ // something has changed 
	
		// add information for the detail line
 		try
 		{
        	if (saveBrixConversion == null || saveBrixConversion.trim().equals(""))
           	{
            	saveBrixConversion = "0";
           	}
           	
           	BigDecimal displayFS = (new BigDecimal(saveQty)).multiply(new BigDecimal(saveBrixConversion));  
           	   
           	if (saveGroup.equals("CN4005"))
           	{ 
           		// purchased concentrate
           		
             	// facility information
             	facPurchQty      = facPurchQty.add(new BigDecimal(saveQty));
             	facPurchFS       = facPurchFS.add(displayFS);
             	facPurchFSAcidWV = facPurchFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
             	facPurchFSAcidWW = facPurchFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
             	
             	// total information
             	purchQty      = purchQty.add(new BigDecimal(saveQty));
             	purchFS       = purchFS.add(displayFS);
             	purchFSAcidWV = purchFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
             	purchFSAcidWW = purchFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));             
 		   	}
		   	else
		   	{ 
		   		// domestic concentrate (manufactured)
		   		
		    	// facility information
            	facDomQty      = facDomQty.add(new BigDecimal(saveQty));
            	facDomFS       = facDomFS.add(displayFS);
            	facDomFSAcidWV = facDomFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
            	facDomFSAcidWW = facDomFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
            	
            	// total information
             	domQty      = domQty.add(new BigDecimal(saveQty));
             	domFS       = domFS.add(displayFS);
             	domFSAcidWV = domFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
             	domFSAcidWW = domFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));             
		   	}	
		   	
		    // facility information
            facTotQty      = facTotQty.add(new BigDecimal(saveQty));
            facTotFS       = facTotFS.add(displayFS);
            facTotFSAcidWV = facTotFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
            facTotFSAcidWW = facTotFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
            
            // total information
            totQty      = totQty.add(new BigDecimal(saveQty));
            totFS       = totFS.add(displayFS);
            totFSAcidWV = totFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
            totFSAcidWW = totFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));      
            addOne      = "Y";
		}
        catch(Exception e)
        {}
	}
	// System.out.println(saveLot);

	if (x != 0 && !saveFacility.trim().equals(av.getLotObject().getFacility().trim()))
    { 
		  // print facility total
%>
	<tr>
		<td class="left"><%= saveFacility %>&nbsp;-&nbsp;<%= saveFacilityDesc.trim() %></td>
		<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(facDomQty.toString(), 0) %></td>
		<td class="right">
			<%     
			try
			{
				BigDecimal aveWV = facDomFSAcidWV.divide(facDomFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
			}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
		</td>
    	<td class="right">
			<%     
			try
			{
				BigDecimal aveWW = facDomFSAcidWW.divide(facDomFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>
    	<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(facPurchQty.toString(), 0) %></td>   
    	<td class="right">
			<%     
			try
			{
				BigDecimal aveWV = facPurchFSAcidWV.divide(facPurchFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>
		<td class="right">
			<%     
      		try
			{
				BigDecimal aveWW = facPurchFSAcidWW.divide(facPurchFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>
		<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(facTotQty.toString(), 0) %></td>   
		<td class="right">
			<%     
			try
			{
				BigDecimal aveWV = facTotFSAcidWV.divide(facTotFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>
		<td class="right">
			<%     
			try
			{
				BigDecimal aveWW = facTotFSAcidWW.divide(facTotFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>    
	</tr>      
<%     
		facDomQty        = new BigDecimal("0");
		facDomFS         = new BigDecimal("0");
		facDomFSAcidWV   = new BigDecimal("0");
		facDomFSAcidWW   = new BigDecimal("0");
		facPurchQty      = new BigDecimal("0");
		facPurchFS       = new BigDecimal("0");
		facPurchFSAcidWV = new BigDecimal("0");
		facPurchFSAcidWW = new BigDecimal("0");
		facTotQty        = new BigDecimal("0");
		facTotFS         = new BigDecimal("0");
		facTotFSAcidWV   = new BigDecimal("0");
		facTotFSAcidWW   = new BigDecimal("0");
		
	} // end of the print out for facility
	
	if (addOne.equals("Y"))
	{
		addOne             = "N"; 
        saveItem           = "";
        saveFacility       = "";
        saveFacilityDesc   = "";
        saveLot            = "";
        saveWhse           = "";
        saveLocation       = "";
        saveQty            = "";
        saveBrixConversion = "0";
        saveAcidWV         = "0";
        saveAcidWW         = "0";
        saveGroup          = "";
	}
     
    saveItem         = av.getItemNumber().trim();
    saveFacility     = av.getLotObject().getFacility().trim();
    saveFacilityDesc = av.getLotObject().getFacilityName().trim();
    saveLot          = av.getLotObject().getLotNumber().trim();
    saveWhse         = av.getLotObject().getWarehouse().trim();
    saveLocation     = av.getLotObject().getLocation().trim();
    saveQty          = av.getLotObject().getQuantity().trim();
    saveGroup        = av.getLotObject().getItemGroup().trim();
     
    if (av.getAttribute().trim().equals("BRIX"))
    {
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
	
} // end of the for loop
    
// print last facility detail line:
try
{
	if (saveBrixConversion == null || saveBrixConversion.trim().equals(""))
	{
		saveBrixConversion = "0";
	}
    
    BigDecimal displayFS = (new BigDecimal(saveQty)).multiply(new BigDecimal(saveBrixConversion));     
    
    if (saveGroup.equals("CN4005"))
    { 
    	// purchased concentrate
    	
    	// facility information
		facPurchQty      = facPurchQty.add(new BigDecimal(saveQty));
		facPurchFS       = facPurchFS.add(displayFS);
        facPurchFSAcidWV = facPurchFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
        facPurchFSAcidWW = facPurchFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
    
        // total information
        purchQty      = purchQty.add(new BigDecimal(saveQty));
        purchFS       = purchFS.add(displayFS);
        purchFSAcidWV = purchFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
        purchFSAcidWW = purchFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));             
 	}
	else
	{ 
		// domestic concentrate (manufactured)
		
		// facility information
        facDomQty      = facDomQty.add(new BigDecimal(saveQty));
        facDomFS       = facDomFS.add(displayFS);
        facDomFSAcidWV = facDomFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
        facDomFSAcidWW = facDomFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
        
        // total information
        domQty      = domQty.add(new BigDecimal(saveQty));
        domFS       = domFS.add(displayFS);
        domFSAcidWV = domFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
        domFSAcidWW = domFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));             
	}
	
	// facility information
    facTotQty      = facTotQty.add(new BigDecimal(saveQty));
    facTotFS       = facTotFS.add(displayFS);
    facTotFSAcidWV = facTotFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
    facTotFSAcidWW = facTotFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));
    
    // total information
    totQty      = totQty.add(new BigDecimal(saveQty));
    totFS       = totFS.add(displayFS);
    totFSAcidWV = totFSAcidWV.add(displayFS.multiply(new BigDecimal(saveAcidWV)));
    totFSAcidWW = totFSAcidWW.add(displayFS.multiply(new BigDecimal(saveAcidWW)));      
    addOne      = "Y";
}
catch(Exception e)
{}

%>    

	<tr>
    	<td class="left"><%= saveFacility %>&nbsp;-&nbsp;<%= saveFacilityDesc.trim() %></td>
    	<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(facDomQty.toString(), 0) %></td>
    	<td class="right">
			<%     
       		try
      		{
        		BigDecimal aveWV = facDomFSAcidWV.divide(facDomFS, 0);
        		out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
      		}
      		catch(Exception e)
      		{
         		out.println("&nbsp;");
      		}
			%>      
    	</td>
    	<td class="right">
			<%     
      		try
      		{
        		BigDecimal aveWW = facDomFSAcidWW.divide(facDomFS, 0);
        		out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
      		}
      		catch(Exception e)
      		{
         		out.println("&nbsp;");
      		}
			%>      
    	</td>
    	<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(facPurchQty.toString(), 0) %></td>   
    	<td class="right">
			<%     
      		try
      		{
        		BigDecimal aveWV = facPurchFSAcidWV.divide(facPurchFS, 0);
         		out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
    	</td>
    	<td class="right">
			<%     
      		try
      		{
        		BigDecimal aveWW = facPurchFSAcidWW.divide(facPurchFS, 0);
         		out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
		</td>
    	<td class="right"><%= HTMLHelpersMasking.maskBigDecimal(facTotQty.toString(), 0) %></td>   
    	<td class="right">
			<%     
      		try
      		{
        		BigDecimal aveWV = facTotFSAcidWV.divide(facTotFS, 0);
        		out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
      		}
      		catch(Exception e)
      		{
        		out.println("&nbsp;");
      		}
			%>      
    	</td>
    	<td class="right">
			<%     
    			try
      			{
         			BigDecimal aveWW = facTotFSAcidWW.divide(facTotFS, 0);
         			out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
      			}
      			catch(Exception e)
      			{
        			out.println("&nbsp;");
      			}
			%>      
		</td>    
	</tr>      

<%-- grand totals --%>
 
    <tr class="grand-total">
    	<td>Company Wide</td>
    	<td><%= HTMLHelpersMasking.maskBigDecimal(domQty.toString(), 0) %></td>
    	<td>
     		<%     
			try
			{
				BigDecimal aveWV = domFSAcidWV.divide(domFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
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
				BigDecimal aveWW = domFSAcidWW.divide(domFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>
		<td><%= HTMLHelpersMasking.maskBigDecimal(purchQty.toString(), 0) %></td>   
		<td>
			<%     
			try
			{
				BigDecimal aveWV = purchFSAcidWV.divide(purchFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
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
				BigDecimal aveWW = purchFSAcidWW.divide(purchFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>
		<td><%= HTMLHelpersMasking.maskBigDecimal(totQty.toString(), 0) %></td>   
		<td>
			<%     
			try
			{
				BigDecimal aveWV = totFSAcidWV.divide(totFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWV.multiply(new BigDecimal("100"))).toString(), 2));
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
				BigDecimal aveWW = totFSAcidWW.divide(totFS, 0);
				out.println(HTMLHelpersMasking.maskBigDecimal((aveWW.multiply(new BigDecimal("100"))).toString(), 2));
			}
			catch(Exception e)
			{
				out.println("&nbsp;");
			}
			%>      
		</td>    
	</tr>    
</tbody>    
