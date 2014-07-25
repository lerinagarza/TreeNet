<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.services.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.net.URLEncoder" %>

<%
//comment

//---------------  listSpecificationsTable.jsp  ------------------------------------------//
//   To Be included in the listSpecifications Page
//
//   Author :  Teri Walton  3/21/05
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//                         Created in place of the listIngSpecs Pages
//------------------------------------------------------//
//-----------------------------------------------------------------------//
//********************************************************************
   String[] parameterValues1 = (String[]) request.getAttribute("parameterValues");
      
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
   String columnHeadingTo = "/web/CtlSpecification?requestType=list" + 
                            parameterValues1[1] +
                            parameterValues1[2] + "&";
   String[] sortImage = new String[9];
   String[] sortStyle = new String[9];
   String[] sortOrder = new String[9];
   sortOrder[0] = "specificationcode";
   sortOrder[1] = "specificationdate";
   sortOrder[2] = "generaldescription";
   sortOrder[3] = "resourcenumber";
   sortOrder[4] = "customerspecification";
   sortOrder[5] = "customernumber";
   sortOrder[6] = "customername";
   sortOrder[7] = "marketnumber";
   sortOrder[8] = "marketcustomer";

  //************
  //Set Defaults
   for (int x = 0; x < 9; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
   String orderBy = request.getParameter("orderBy");
   if (orderBy == null)
      orderBy = "specificationcode";
   String orderStyle = request.getParameter("orderStyle");
   if (orderStyle == null)
      orderStyle = "";
   for (int x = 0; x < 9; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (orderStyle.equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/UpArrowYellow.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/DownArrowYellow.gif\">";
     }
   }   
                       
   
   Vector reportList     = new Vector();
   int    reportListSize = 0;
   try
   {
      reportList     = (Vector) request.getAttribute("reportList");
      reportListSize = reportList.size();
   }
   catch(Exception e)
   {
   }  
 
 //exclude inventory - switch to new AS400 12/24/08 wth  
 String showinv = request.getParameter("showinventory");
 //if (showinv == null ||
     //showinv.equals("N"))
   //showinv = "N";
 //else
   //showinv = "Y";  
 //if (request.getParameter("showrevisions") != null &&
     //request.getParameter("showrevisions").equals("Show Summary"))
    //showinv = "Y";
    
 showinv = "N";
 
 String showcust = request.getParameter("showcustomer");
 if (showcust == null ||
     showcust.equals("N"))
   showcust = "N";
 else
   showcust = "Y";  
 String showvar = request.getParameter("showvariety");
 if (showvar == null ||
     showvar.equals("N"))
   showvar = "N";                 
 else
   showvar = "Y";   
//   String summLevel = request.getParameter("summaryLevel");
// if (summLevel == null)
//      summLevel = "";
      
//   String resourceUOM = "";
   String requestType = (String) request.getAttribute("requestType");
   int expandSection = 2;
   int expandImage   = 4;
  try
  {
     expandSection = ((Integer) request.getAttribute("expandCount")).intValue();
     expandImage   = ((Integer) request.getAttribute("imageCount")).intValue();
  }
  catch(Exception e)
  
  {
  //String stophere = "yes";
  }
   
  DecimalFormat decimalf = new DecimalFormat("#,###,##0.00");
  DecimalFormat integerf = new DecimalFormat("#,###,##0");   
  
  BigDecimal inventoryTotal = new BigDecimal("0");
          
%>

<html>
  <head>
     <%= JavascriptInfo.getEditButton() %>
  </head>
  <body>
  <table class="table01001" cellspacing="0" style="width:100%" align="center">
    <tr class="tr04001">
      <td class="td017CC001" style="border-left:1px solid #CCCC99;
                                    border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
     expandImage++;
%>                                    
        <%= sortImage[0] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
          Spec ID
        </a>     
<%
   }
   else
      out.println("<b>Spec ID</b>");
%>        
      </td>
<%
   if (request.getParameter("showrevisions") == null ||
       !request.getParameter("showrevisions").equals("Show Summary"))
   {    
%>       
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
%>               
        <%= sortImage[1] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[1] %>&orderStyle=<%= sortStyle[1] %>">
          Revision Date
        </a>  
<%
   }
   else
      out.println("<b>Revision Date</b>");
%>          
      </td> 
<%
   }
%>            
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
%>         
        <%= sortImage[2] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
          Description
        </a>  
<%
   }
   else
      out.println("<b>Description</b>");
%>              
      </td>                  
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
%>         
        <%= sortImage[3] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
          Item
        </a>  
<%
   }
   else
      out.println("<b>Item</b>");
%>                  
      </td>
<%
   if (showcust.equals("Y") ||
       requestType.equals("detail"))
   {
%>      
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
%>         
        <%= sortImage[4] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
          Cust Spec
        </a>   
<%
   }
   else
      out.println("<b>Cust Spec</b>");
%>                 
      </td>
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
%>         
        <%= sortImage[5] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
          Cust #
        </a>  
<%
   }
   else
      out.println("<b>Cust #</b>");
%>              
      </td>
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
      out.println("<b>Customer Name</b>");
%>         
<%
   }
   else
      out.println("<b>Customer Name</b>");
%>               
      </td>         
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
<%
   if (requestType.equals("list"))
   {
%>         
        <%= sortImage[7] %>
        <a class="a01001" href="<%= columnHeadingTo %>orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
          Market
        </a> 
<%
   }
   else
      out.println("<b>Market</b>");
%>               
      </td> 
<%
   }
   if (showvar.equals("Y") ||
       requestType.equals("detail"))
   {
%>       
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>
          Included Varieties
        </b>  
      </td>
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>
          Excluded Varieties
        </b>  
      </td>
<%
   }
   if (showinv.equals("Y") ||
       (requestType.equals("detail") && 1==2) )
   {
%>      
      <td class="td017CC001" style="border-right:1px solid #CCCC99">
        <b>
          <acronym title="*Note about Hold Inventory 
Hold Inventory does not actually have a Specification.
The quantity reflects the Intended Specification at the time of production.
The Spec may change when the pallet is taken Off Hold and assigned to a NEW Spec.">Inventory</acronym>
        </b>  
      </td> 
<%
   }
   if (requestType.equals("list"))
   {   
%>                                                                 
      <td class="td017CC001" style="border-right:1px solid #CCCC99;
                                    width:6%">
          &nbsp;
      </td>
<%
   }
%>      
    </tr>   
<%
   if (reportListSize > 0)
   {
     for (int x = 0; x < reportListSize; x++)
     {
        SpecificationView thisOne = (SpecificationView) reportList.elementAt(x);
        Specification     thisrow = thisOne.getSpecification();
%>  
    <tr class="tr00001">
      <td class="td047CL001" style="border-left:1px solid #CCCC99;
                                    border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%= HTMLHelpersLinks.routerSpecification(thisrow.getSpecificationCode().trim(), "", "", ("&specificationDate="+ thisrow.getSpecificationDate().trim())) %>                                    
        &nbsp;
<%
   if (thisrow.getDeletedRecord())
      out.println("<font color=\"#990000\">Deleted</font>");
%>                 
      </td>
<%
   if (request.getParameter("showrevisions") == null ||
       !request.getParameter("showrevisions").equals("Show Summary"))
   {    
%>           
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
		<%= thisrow.getSpecificationDate() %>&nbsp;
      </td> 
<%
   }
%>      
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%= thisrow.getGeneralDescription() %>&nbsp;
      </td>                
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                   border-top:1px solid #CCCC99">
        <%= HTMLHelpersLinks.routerItem(thisrow.getResourceNumber(),
				 "",
				 "",
				 "") %>&nbsp;
      </td>
<%
   if (showcust.equals("Y") ||
       requestType.equals("detail"))
   {
%>            
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                   border-top:1px solid #CCCC99">
        <%= thisrow.getCustomerSpecification() %>&nbsp;
      </td>
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
     	<%= thisrow.getCustomerNumber() %>&nbsp;

      </td>
<%
   String custStatus = "";
   String custName   = "";
   String market	 = "";
   try
   {
      //CustomerBillTo thisCust = new CustomerBillTo(thisrow.getCompanyNumber(), thisrow.getCustomerNumber()); //01/07/09 wth
      //if (thisCust.getCustomerName() != null && //01/07/09 wth
          //!thisCust.getCustomerName().equals("")) //01/07/09 wth
        //custName = thisCust.getCustomerName();
      //Vector theseOECust = CustomerOrderTo.findByBillToNumber(thisrow.getCompanyNumber(), thisrow.getCustomerNumber()); //01/07/09 wth
      //CustomerOrderTo thisOneCust = (CustomerOrderTo) theseOECust.elementAt(0); //01/07/09 wth
      //if (thisOneCust != null && //01/07/09 wth
          //thisOneCust.getOeRecordStatus() != null) //01/07/09 wth
      //custStatus = thisOneCust.getOeRecordStatus(); //01/07/09 wth
      Customer thisCust = new Customer(); //01/21/2009
      if (thisrow.getCustomerNumber() != null && //01/21/2009
          !thisrow.getCustomerNumber().trim().equals("") ) //01/21/2009
      { //01/21/2009
      	thisCust.setCompany("100"); //01/21/2009
      	thisCust.setNumber(thisrow.getCustomerNumber().trim()); //01/21/2009
     	thisCust = com.treetop.services.ServiceCustomer.getCustomerByNumber(thisCust); //01/21/2009
     	custName = thisCust.getName().trim(); //01/21/2009
     	market   = thisCust.getMarket().trim(); //01/22/2009
      } //01/21/2009
   }
   catch(Exception e)
   {
   }
%>   
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                   border-top:1px solid #CCCC99">
        <%= custName %>&nbsp;
      </td>
      
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
     	<%= market %>&nbsp;
      </td>
<%
   }
   if (showvar.equals("Y") ||
       requestType.equals("detail"))
   {   
%>      
      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
<%
   if (!thisrow.getFruitVariety().trim().equals(""))
   {
      String mo = "";
      try
      {
        GeneralInfoDried thisV = new GeneralInfoDried("FV", thisrow.getFruitVariety().trim());
        mo = thisV.getDescFull();
      }
      catch(Exception e)
      {
      }
        if (mo == null ||
            mo.equals(""))
          out.println(thisrow.getFruitVariety());
        else
          out.println("<acronym title=\"" + mo + "\">" + 
                      thisrow.getFruitVariety() +
                      "</acronym>");        
   }
   for (int iv = 0; iv < 10; iv++)
   {
      if (thisrow.getVarietiesAcceptable()[iv] != null &&
          !thisrow.getVarietiesAcceptable()[iv].trim().equals(""))
      {
         if (iv > 0 ||
             !thisrow.getFruitVariety().trim().equals("")) 
            out.println(", ");
        String mo = "";
        try
        {
          GeneralInfoDried thisV = new GeneralInfoDried("FV", thisrow.getVarietiesAcceptable()[iv].trim());
          mo = thisV.getDescFull();
        }
        catch(Exception e)
        {
        }
        if (mo == null ||
            mo.equals(""))
          out.println(thisrow.getVarietiesAcceptable()[iv]);
        else
          out.println("<acronym title=\"" + mo + "\">" + 
                      thisrow.getVarietiesAcceptable()[iv] +
                      "</acronym>");  
      }
   }
%>   
      &nbsp;
      </td>

      <td class="td047CL001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
<%
   for (int ev = 0; ev < 5; ev++)
   {
      if (thisrow.getVarietiesExcluded()[ev] != null &&
          !thisrow.getVarietiesExcluded()[ev].trim().equals(""))
      {
         if (ev > 0) 
            out.println(", ");
        String mo = "";
        try
        {
          GeneralInfoDried thisV = new GeneralInfoDried("FV", thisrow.getVarietiesExcluded()[ev].trim());
          mo = thisV.getDescFull();
        }
        catch(Exception e)
        {
        }
        if (mo == null ||
            mo.equals(""))
          out.println(thisrow.getVarietiesExcluded()[ev]);
        else
          out.println("<acronym title=\"" + mo + "\">" + 
                      thisrow.getVarietiesExcluded()[ev] +
                      "</acronym>");              
      }
   }
%>   
      &nbsp;
      </td>
<%
   }
   if (showinv.equals("Y") ||
       (requestType.equals("detail") && 1 ==2) )
   {
%>            
      <td class="td047CR001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
<%
  Vector inventoryInformation = new Vector();
  int    countClassifications = 0;
  try
  {
     inventoryInformation = thisOne.getInventory();
     countClassifications = inventoryInformation.size();
  }
  catch(Exception e)
  {
  }
  
  if (countClassifications > 1)
  {
     try
     {
//        InventoryLotLocationDetail totalElement = (InventoryLotLocationDetail) inventoryInformation.elementAt((countClassifications - 1));
 //       String totalLink = "<a class=\"a04001\"" +
  //                         " href=\"/web/CtlSpecification?requestType=listInventory" +
   //                        "&inqspecification=" + URLEncoder.encode(thisrow.getSpecificationCode().trim()) + 
    //                       "&specificationDate=" + thisrow.getSpecificationDate() +
     //                      "&showrevisions=" + request.getParameter("showrevisions") +
      //                     "\" target=\"_blank\">" +
       //                    integerf.format(totalElement.getQuantityAtLocation()) +
        //                   "</a>";
        String totalLink = "";
//        inventoryTotal = inventoryTotal.add(totalElement.getQuantityAtLocation());
        inventoryTotal = inventoryTotal;
%>                                    
        <%= JavascriptInfo.getExpandingSectionRight("C", totalLink, 9,  expandSection, expandImage, 2, 0) %>
<%
   expandSection++;
   expandImage++;
%>        
         <span>  	
          <table class="table01001" cellspacing="0">   
<%
   String addedStyle = " style=\"border-top:1px solid #006400\"";
     for (int inv = 0; inv < (countClassifications - 1); inv++)
     {
 //      InventoryLotLocationDetail inventoryElement = (InventoryLotLocationDetail) inventoryInformation.elementAt(inv);
  //      String qtyLink = "<a class=\"a04001\"" +
   //                        " href=\"/web/CtlSpecification?requestType=listInventory" +
    //                       "&inqspecification=" + URLEncoder.encode(thisrow.getSpecificationCode().trim()) + 
     //                      "&specificationDate=" + thisrow.getSpecificationDate() +
      //                     "&classification=" + inventoryElement.getClassification().trim() +
       //                    "&showrevisions=" + request.getParameter("showrevisions") +
        //                   "\" target=\"_blank\">" +
         //                  integerf.format(inventoryElement.getQuantityAtLocation()) +
          //                 "</a>";      
          String qtyLink = "";
       if (inv != 0)
          addedStyle = ""; 
%>
            <tr class="tr00001">
              <td class="td047CL002"<%= addedStyle %>>
                <%// inventoryElement.getClassification() %>
                &nbsp;
              </td>
              <td class="td047CR002"<%= addedStyle %>>
                <%= qtyLink %>
              </td>              
            </tr>
<%
     }
%>          
          </table>
        </span>         
<%
    }
    catch(Exception e)
    {
       out.println("&nbsp;");
    }
  }
  else
  {
%>        
      &nbsp;
<%
  } 
%>      
      </td>  
<%   
   }   
   if (requestType.equals("list"))
   {      
	    // BUILD Edit/More Button Section(Column)  
	    String[] urlLinks = new String[2];
	    String[] urlNames = new String[2];
	    String[] newPage  = new String[2];
	    for (int z = 0; z < 2; z++)
	    {
		   urlLinks[z] = "";
		   urlNames[z] = "";
	       newPage[z]  = ""; 	    
	    }

   if (request.getParameter("showrevisions") == null ||
       !request.getParameter("showrevisions").equals("Show Summary"))
   {    
		urlLinks[0] = "/web/CtlSpecification?requestType=detail" +
					  "&specification=" + URLEncoder.encode(thisrow.getSpecificationCode().trim()) +
					  "&specificationDate=" + thisrow.getSpecificationDate();
	    urlNames[0] = "Details For Spec (" + thisrow.getSpecificationCode() +
		 	                 " - " + thisrow.getSpecificationDate() + ")";
		newPage[0]  = "Y"; 
   }	
		urlLinks[1] = "/web/CtlSpecification?requestType=detail" +
					  "&specification=" + URLEncoder.encode(thisrow.getSpecificationCode().trim());
	    urlNames[1] = "Details For Current Specification (" + thisrow.getSpecificationCode() +
		 	                 ")";
		newPage[1]  = "Y"; 				
%>
     
      <td class="td044CR001" style="border-right:1px solid #CCCC99;
                                    border-top:1px solid #CCCC99">
        <%= HTMLHelpers.buttonMore(urlLinks, urlNames, newPage) %>
      </td>
<%
   }
%>      
    </tr>   
<%
     } // end of the for loop
 
   } // end of the if no records
   request.setAttribute("expandCount", new Integer(expandSection));
   request.setAttribute("imageCount", new Integer(expandImage));
  if (showinv.equals("Y") ||
       requestType.equals("detail"))
   {       
     int colspan1 = 3;
     if (request.getParameter("showrevisions") == null ||
         !request.getParameter("showrevisions").equals("Show Summary"))
   		colspan1++;
%>     
    <tr class="tr02001">

      <td class="td041CC001" style="border-bottom:1px solid #006400" colspan="<%= colspan1%>">
        <b>TOTAL</b>
      </td>
    
<%
     if (showcust.equals("Y") ||
         requestType.equals("detail"))
     {
%>      
      <td class="td017CC001" colspan="5" style="border-bottom:1px solid #006400">
        &nbsp;
      </td> 

<%
     }
     if (showvar.equals("Y") ||
         requestType.equals("detail"))
     {
%>       
      <td class="td017CC001" colspan="2" style="border-bottom:1px solid #006400">
        &nbsp;
      </td>
<%
     }
%> 
      <td class="td041CR001" style="border-bottom:1px solid #006400">
        <b>
          <%= integerf.format(inventoryTotal) %>
        </b>  
      </td>
<%      
     if (requestType.equals("list"))
     {
%>           
      <td style="border-bottom:1px solid #006400">
          &nbsp;
      </td>
<%
    }
%>      
    </tr>   
<%
  }
%>
  </table>
   </body>
</html>