<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.text.*" %>
<%  
//---------------  listInventory.jsp  ------------------------------------------//
//   To Be included in the listSpecifications Page
//
//   Author :  Teri Walton  3/31/05
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//                         Created in place of the lisPalletsbyIngSpecs and
//									listOnHoldPalletsbyIngSpecs
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
  String specification = request.getParameter("inqspecification");        
  if (specification == null)
    specification = "";
  String specificationDate = request.getParameter("specificationDate");        
  if (specificationDate == null)
    specificationDate = "";
  String classification = request.getParameter("classification");        
  if (classification == null)
    classification = "";
    
  String goldTitle = specification + " Spec Inventory";

   request.setAttribute("title", goldTitle );
   String setExtraOptions = "<option value=\"/web/CtlSpecification\">Select Another Specification";
   request.setAttribute("extraOptions", setExtraOptions);      
   
   String[] parameterValues = (String[]) request.getAttribute("parameterValues");

   String[] parameterValues1 = (String[]) request.getAttribute("parameterValues");
      
   Vector locationList   = new Vector();
   int    locationListSize = 0;
   Specification headerInfo = new Specification();
   String inventoryType = "";
   
   try
   {
   	  Vector            reportList   = (Vector) request.getAttribute("reportList");
   	  SpecificationView firstElement = (SpecificationView) reportList.elementAt(0);
   	  headerInfo       = firstElement.getSpecification();
   	  inventoryType    = headerInfo.getSpecificationType();
   	  if (inventoryType == null)
   	    inventoryType = "";
   	  locationList     = firstElement.getInventory();
      locationListSize = locationList.size();
   }
   catch(Exception e)
   {
   }  
   
  DecimalFormat decimalf = new DecimalFormat("#,###,##0.00");
  DecimalFormat integerf = new DecimalFormat("#,###,##0");   
  int totalColumnSpan = 6;
%>

<html>
  <head>
    <title><%= goldTitle %></title>
  </head>
  <body>
 <jsp:include page="../include/heading.jsp"></jsp:include> 
  <table class="table01001" cellspacing="0" style="width:100%" align="center">
    <tr>
      <td class="td041CL001">
		<b>Specification:</b>
      </td>
      <td class="td041CL001">
		<%= HTMLHelpersLinks.routerSpecification(specification.trim(), "12", "", ("&specificationDate="+ specificationDate.trim())) %>
      </td>
<%
   if (request.getParameter("showrevisions") == null ||
       !request.getParameter("showrevisions").equals("Show Summary"))
   {    
%>  
      <td class="td041CL001">
		<b>Revision Date:</b>
      </td>
      <td class="td071CL001">
		<%= specificationDate %>
      </td>
<%
   }
   if (!classification.equals(""))
   {
%>      
      <td class="td041CL001">
		<b>Classification:</b>
      </td>
      <td class="td071CL001">
		<%= classification %>
      </td>
<%
   }
%>      
    </tr>   
  </table>  
  <table class="table01001" cellspacing="0" style="width:100%" align="center">
    <tr class="tr04001">
      <td class="td017CC001" style="border-left:1px solid #CCCC99;
                                    border-right:1px solid #CCCC99">
		<b>Lot Number</b>
      </td>
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
		<b>Resource</b>
      </td>  
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
		<b>Production Date</b>
      </td>                            
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>Variety</b>
      </td>
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>Comments</b>
      </td>      
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>Location</b>
      </td>
<%
   if (classification.equals(""))
   {
     totalColumnSpan++;
%>      
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>Classification</b>
      </td>  
<%
   }
%>   
      <td class="td017CC001" colspan="6" style="border-right:1px solid #CCCC99">
        <b>Quantity</b>
      </td>
    </tr>   
<%
   BigDecimal total1 = new BigDecimal("0");
   BigDecimal total2 = new BigDecimal("0");
   BigDecimal total3 = new BigDecimal("0");   
   String     t1uom  = "";
   String     t2uom  = "";
   String     t3uom  = "";   
   
//   if (locationListSize > 0)
//   {
 //    for (int x = 0; x < locationListSize; x++)
   //  {
 //       InventoryLotLocationDetail thisrow = (InventoryLotLocationDetail) locationList.elementAt(x);
%>  
    <tr class="tr00001">
      <td class="td047CL001" style="border-left:1px solid #CCCC99;
                                    border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <% //HTMLHelpersLinks.routerLot(thisrow.getLotNumber(), thisrow.getLotType(), "", "", "") %>&nbsp;
      </td>
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%// HTMLHelpersLinks.routerResource(thisrow.getResource(), "", "", "") %>&nbsp;		
		&nbsp;
      </td> 
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%// thisrow.getFifoDate() %>&nbsp;		
		&nbsp;
      </td>       
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
<%                                    
//      if (thisrow.getLotVariety() != null &&
//          !thisrow.getLotVariety().trim().equals(""))
//      {
 //       String mo = "";
  //     try
   //     {
//          GeneralInfoDried thisV = new GeneralInfoDried("FV", thisrow.getLotVariety().trim());
//          mo = thisV.getDescFull();
 //       }
//        catch(Exception e)
//        {
 //       }
  //      if (mo == null ||
   //         mo.equals(""))
    //      out.println(thisrow.getLotVariety());
     //   else
      //    out.println("<acronym title=\"" + mo + "\">" + 
       //               thisrow.getLotVariety() +
        //              "</acronym>");              
//      }
%>                                    
        &nbsp;
      </td>    
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%// thisrow.getComment() %>&nbsp;		
		&nbsp;
      </td>                    
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                   border-top:1px solid #CCCC99">
<%       
//  thisrow.getLocationNumber(); 
//  if (thisrow.getWarehouse() != null &&
 //    !thisrow.getWarehouse().trim().equals(""))
  //{
//     String plantLocation = thisrow.getWarehouse();
 //    if (plantLocation.length() == 1)
  //     plantLocation = "0" + plantLocation;
   //  String mo = "";
    // try
//     {
 //       Plant thisP = new Plant(plantLocation);
  //      mo = thisP.getPlantDescription();
   //  }
    // catch(Exception e)
//     {
 //    }
  //   if (mo == null ||
   //      mo.equals(""))
    //    out.println(plantLocation);
//     else
 //       out.println("<acronym title=\"" + mo + "\">" + 
  //                    plantLocation +
   //                   "</acronym>");              
//   }
%>                                                  
&nbsp;
   <%// thisrow.getLocationNumber() %>
      </td>
<%
   if (classification.equals(""))
   {
%>      
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%// thisrow.getClassification() %>&nbsp;		
		&nbsp;
      </td>   
<%
   }
//-----------------------------------------------------------------------
//  Quantity Section - To Add Together to get Totals
//-----------------------------------------------------------------------
   String display1 = "&nbsp;";
   String uom1     = "";
   String display2 = "&nbsp;";
   String uom2     = "";
   String display3 = "&nbsp;";
   String uom3     = "";
//   try
//   {
   	  //get Resource Unit of Measure
   	  String uom = "";
   	  try
   	  {
//   	     Resource thisR = new Resource(thisrow.getResource());
//   	     if (thisR.getUnitOfMeasure() != null)
//   	        uom = thisR.getUnitOfMeasure();
   	  }
   	  catch(Exception e)
   	  {}
//      if (uom.equals("") ||
//          (!inventoryType.trim().equals("DR") &&
//           !inventoryType.trim().equals("RF")))
 //     {
  //       display1 = decimalf.format(thisrow.getQuantityAtLocation());
   //      total1   = total1.add(thisrow.getQuantityAtLocation()); 
    //     if (!uom.equals(""))
     //    {
      //      uom1 = uom;
       //     t1uom = uom;
//         }
//      } 
 //     else
  //    {
   //      	try
    //     	{
     //    	   UnitOfMeasureConversion thisConverted = new UnitOfMeasureConversion(thisrow.getQuantityAtLocation(),
		//																		   uom,
			//																	   thisrow.getResource(),
				//																   thisrow.getLotNumber(),
					//															   new Integer("0"));
//				if (inventoryType.trim().equals("DR") ||
	//			    inventoryType.trim().equals("RF"))
		//		{    																   	
        	//		if (thisConverted.getPounds() != null &&
         		//	    thisConverted.getPounds().compareTo(new BigDecimal("0")) != 0)
//         			{
  //       			   total1   = total1.add(thisConverted.getPounds());
    //     			   display1 = decimalf.format(thisConverted.getPounds());
      //   			   uom1     = "pounds";
        // 			   t1uom    = uom1;
         //			}
         	//	}	
         		//if (inventoryType.trim().equals("DR"))
//         		{
  //      			if (thisConverted.getCases() != null &&
    //     			    thisConverted.getCases().compareTo(new BigDecimal("0")) != 0)
      //   			{
        // 			   total2   = total2.add(thisConverted.getCases());
         //			   display2 = decimalf.format(thisConverted.getCases());
         	///		   uom2     = "cases";
         		//	   t2uom    = uom2;
//         			}
  //      			if (thisConverted.getPallets() != null &&
    //     			    thisConverted.getPallets().compareTo(new BigDecimal("0")) != 0)
      //   			{
        // 			   total3   = total3.add(thisConverted.getPallets());
         //			   display3 = decimalf.format(thisConverted.getPallets());
         	//		   uom3     = "pallets";
         		//	   t3uom    = uom3;
         			//}         			
//         		}
  //       		if (inventoryType.trim().equals("RF"))
    //     		{
      //   			if (thisConverted.getBins() != null &&
        // 			    thisConverted.getBins().compareTo(new BigDecimal("0")) != 0)
         //			{
         	//		   total2   = total2.add(thisConverted.getBins());
         		//	   display2 = decimalf.format(thisConverted.getBins());
         			//   uom2     = "bins";
         			  // t2uom    = uom2;
//         			}
  //      			if (thisConverted.getTons() != null &&
    //     			    thisConverted.getTons().compareTo(new BigDecimal("0")) != 0)
      //   			{
        // 			   total3   = total3.add(thisConverted.getTons());
         //			   display3 = decimalf.format(thisConverted.getTons());
         	//		   uom3     = "tons";
//         			   t3uom    = uom3;
  //       			}         	        		
	//	        }        		
      //   	}
        // 	catch(Exception e)
         //	{
         	//}
         
//   	  }
  // }
//   catch(Exception e)
  // {}
%>          
      <td class="td047CR001" style="border-top:1px solid #CCCC99">
        <%// display1 %>
      </td>  
      <td class="td047CL001" style="border-top:1px solid #CCCC99">
        &nbsp;<%//uom1 %>
      </td>  
      <td class="td047CR001" style="border-top:1px solid #CCCC99">
        <%// display2 %>
      </td>  
      <td class="td047CL001" style="border-top:1px solid #CCCC99">
        &nbsp;<%// uom2 %>
      </td>  
      <td class="td047CR001" style="border-top:1px solid #CCCC99">
        <%// display3 %>
      </td>    
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        &nbsp;<%// uom3 %>
      </td>                             
<%
//-----------------------------------------------------------------------
%>                               
    </tr>   
<%
//     } // end of the for loop
//   } // end of the if no records
   //------------------------------------------------------------------------------------
   // Display Totals
   //-----------------------------------------------------------------------------------
//   String display1 = "&nbsp;";
//   String display2 = "&nbsp;";
 //  String display3 = "&nbsp;";
 //  if (total1.compareTo(new BigDecimal("0")) != 0)
  //    display1 = decimalf.format(total1);
//   if (total2.compareTo(new BigDecimal("0")) != 0)
  //    display2 = decimalf.format(total2);
//   if (total3.compareTo(new BigDecimal("0")) != 0)
  //    display3 = decimalf.format(total3);
%>  
    <tr class="tr02001">  
      <td class="td044CC001" colspan="<%= totalColumnSpan %>" style="border-top:1px solid #006400;
                                    							    border-bottom:1px solid #006400">
        <b>TOTALS:</b>
      </td>      
      <td class="td047CR001" style="border-top:1px solid #006400;
                                    border-bottom:1px solid #006400">
        <b><%= display1 %></b>
      </td>  
      <td class="td047CL001" style="border-top:1px solid #006400;
                                    border-bottom:1px solid #006400">
        &nbsp;<b><%= t1uom %></b>
      </td>  
      <td class="td047CR001" style="border-top:1px solid #006400;
                                    border-bottom:1px solid #006400">
        <b><%= display2 %></b>
      </td>  
      <td class="td047CL001" style="border-top:1px solid #006400;
                                    border-bottom:1px solid #006400">
        &nbsp;<b><%= t2uom %></b>
      </td>  
      <td class="td047CR001" style="border-top:1px solid #006400;
                                    border-bottom:1px solid #006400">
        <b><%= display3 %></b>
      </td>    
      <td class="td047CL001" style="border-top:1px solid #006400;
                                    border-bottom:1px solid #006400">
        &nbsp;<b><%= t3uom %></b>
      </td>                                 
    </tr>      
  </table>
 <jsp:include page="../include/footer.jsp"></jsp:include>
   </body>
</html>