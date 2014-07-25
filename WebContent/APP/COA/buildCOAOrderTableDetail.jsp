<%@ page import = "java.math.*" %>
<%@ page import = "java.util.*" %>
<%


//---------------- APP/COA/buildCOAOrderTableDetail.jsp -----------------------//
//Prototype:  Charlena Paschen  06/04/03 (jsp)
//Author   :  Teri Walton       11/05/03 (thrown from servlet)
//Changes  :
//Date       Name          Comments
//----       ----          --------
//9/5/07     TWalton 		 Rewrite with Movex //Split out JSP 
//------------------------------------------------------------//
//********************************************************************
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists

  int startAttribute = 0;
  String lineNumber = "";
  String basicUOM = "";
  Vector listCoa = new Vector();
  Vector listAttr = new Vector();
  com.treetop.businessobjectapplications.COADetailAttributes thisLineLot = new com.treetop.businessobjectapplications.COADetailAttributes();
 try
 {
    listCoa = (Vector) request.getAttribute("coaAttrDetail");
    listAttr = (Vector) request.getAttribute("attrDetail");
	lineNumber =  (String) request.getAttribute("SOLineNumber");
    basicUOM = (String) request.getAttribute("itemBasicUOM");
 }
 catch(Exception e)
 {
 } 
 
 
   int countAttributes = 0;
   String saveLot = "";
   String firstLotFound = "";
   Vector attrDescriptions = new Vector();
   Vector attrMinMax = new Vector();
 //  Vector attrType = new Vector();
   Vector decimals = new Vector();
   for (int x = 0; x < listAttr.size(); x++)
   {
   	  com.treetop.businessobjectapplications.COADetailAttributes cda = (com.treetop.businessobjectapplications.COADetailAttributes) listCoa.elementAt(x);
   	  com.treetop.businessobjects.AttributeValue av = (com.treetop.businessobjects.AttributeValue) listAttr.elementAt(x);
   	  
   	  if (cda.getLineNumber().equals(lineNumber))
      {
         if (firstLotFound.equals(""))
         {
             startAttribute = x;
             thisLineLot = (com.treetop.businessobjectapplications.COADetailAttributes) listCoa.elementAt(x);
             saveLot = av.getLotObject().getLotNumber().trim();
             firstLotFound = "Y";
         }
         if (firstLotFound.equals("Y") && saveLot.equals(av.getLotObject().getLotNumber().trim()) )
         {
            countAttributes++;
            attrDescriptions.addElement(cda.getAttributeClass().getAttributeDescription());
   //         attrType.addElement(cda.getAttributeClass().getFieldType());
            decimals.addElement(cda.getAttributeClass().getDecimalPlaces());
            String displayMinMax = "";
            if (av.getFieldType().equals("numeric"))
            {
               if (cda.getAttributeClass().getLowValue() != null)
                  displayMinMax = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(cda.getAttributeClass().getLowValue(), (new Integer(cda.getAttributeClass().getDecimalPlaces()).intValue()));
               if (cda.getAttributeClass().getHighValue() != null)
               {
                  if (displayMinMax.trim().equals(""))
                     displayMinMax = "0";
                  displayMinMax = displayMinMax + " / ";
                  displayMinMax = displayMinMax + com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(cda.getAttributeClass().getHighValue(), (new Integer(cda.getAttributeClass().getDecimalPlaces()).intValue()));
               }
            }            
            if (displayMinMax.trim().equals(""))
               displayMinMax = "&nbsp;";
            attrMinMax.addElement(displayMinMax);
         }
      }
  //    else
  //       x = listAttr.size();
   } // End of the for loop
   
  int columnSpan = 3;
  if (thisLineLot.getShowUnits().equals("1"))
     columnSpan++;
  if (thisLineLot.getShowUnits().equals("2"))
     columnSpan++;
%>
<html>
  <head>
  </head>
  <body>
<% 
//**************************************************************************************
   // Update Additional Information
//**************************************************************************************
%>
   <table cellspacing="0" style="width:100%" border="1">
<%   
//	  BEGIN ROW -----------------------------------------------------------------------------------
try
{
%>
    <tr class = "tr01">
     <td class="td0412" colspan="<%= columnSpan %>">
      <b> Line Item Comments:</b>
     </td>
     <td class="td0412" colspan="<%= countAttributes %>">
      <%= thisLineLot.getItemComment().trim() %>&nbsp;
     </td>
    </tr>
    <tr class="tr02">
     <td class="td0410" style="width:7%">
	  Lot
	 </td>
     <td class="td0410" style="width:7%">
	  Production Date
	 </td>
<%
  if (thisLineLot.getShowUnits().equals("1"))
  {
%>	
     <td class="td0410" style="width:7%">
	  Qty Shipped in Pounds
	 </td>
<%     
  }
  if (thisLineLot.getShowUnits().equals("2"))
  {
%>	
	 <td class="td0410" style="width:7%">
	  Qty Shipped in Kilograms
	 </td>
<%  
  } 
%>     
     <td class="td0410" style="width:7%">
	  Quantity Shipped in <%= basicUOM %>
	 </td>
<%	 
   for (int x = 0; x < attrDescriptions.size(); x++)
   {
%>            
      <td class="td0410" style="width:7%">
       <%= attrDescriptions.elementAt(x) %>
      </td>
<%
   } // End of the for loop
%>
     </tr>
<%	     
//   END OF THE ROW  --------------------------------------------------------------------------------------
//   START OF NEXT ROW  -----------------------------------------------------------------------------------
String columnName1 = "Attribute Model Min / Max --->>>";
%>
     <tr class="tr01">
      <td class="td04100405" colspan=<%= columnSpan %>>
	   <%= columnName1 %>
	  </td>
<%	  
    for (int x = 0; x < attrMinMax.size(); x++)
   {
%> 

      <td class="td04100405" style="text-align:center">
        <%= attrMinMax.elementAt(x) %>
	  </td>
<%
	}	 // END OF THE for Loop
%>
     </tr> 
<%
//  END OF THE ROW ---------------------------------------------------------------------------------------------
// START OF THE NEXT ROW --------------------------------------------------------------------------------------
    // Row for EACH Lot
   String[] aveValue = new String[countAttributes];
   int countLot = 0;
   for (int lot = startAttribute; lot < listAttr.size(); lot++)
   {
      com.treetop.businessobjectapplications.COADetailAttributes cda = (com.treetop.businessobjectapplications.COADetailAttributes) listCoa.elementAt(lot);
   	   if (cda.getLineNumber().equals(lineNumber))
      {
         com.treetop.businessobjects.AttributeValue av = (com.treetop.businessobjects.AttributeValue) listAttr.elementAt(lot);
         countLot++;  
%>             
     <tr class="tr00">
      <td class="td0410">
	   <%= av.getLotObject().getLotNumber() %>
	  </td>
      <td class="td0410">
<%
   String df2 = (String) request.getAttribute("dateFormat");
   if (df2 == null || df2.trim().equals(""))
      df2 = "0";
%>    
      <%= com.treetop.app.coa.BuildCOA.convertDate(av.getLotObject().getManufactureDate(), new Integer(df2)) %>    
	  </td>
<%
    // Quantity Information -- Manipulation
    String displayQty = av.getLotObject().getQuantity();
    try
    {
    	java.math.BigDecimal bd = new java.math.BigDecimal(displayQty); 
    	if (bd.compareTo(new java.math.BigDecimal("0")) < 0)
    	   bd = bd.multiply(new java.math.BigDecimal("-1"));
    	displayQty = bd.toString();
    }
    catch(Exception e)
    {}
  if (thisLineLot.getShowUnits().equals("1"))
  {
%>	
     <td class="td0410" style="width:7%; text-align:right">
	    <%= com.treetop.app.coa.BuildCOA.convertUOM(av.getItemNumber(), "LB", displayQty) %>
	 </td>
<%     
  }
  if (thisLineLot.getShowUnits().equals("2"))
  {
%>	
	 <td class="td0410" style="width:7%; text-align:right">
	  <%= com.treetop.app.coa.BuildCOA.convertUOM(av.getItemNumber(), "KG", displayQty) %>
	 </td>
<%  
  } 
//**Cases Shipped**
%>
      <td class="td0410" style="text-align:right">
	    <%= com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayQty, 0) %>
      </td>
     <%
    // List by Pallet and Ident
    
//For the count of the attribute
         if (lot != startAttribute)
           lot = lot - 1;
        
		 for (int x = 0; x < countAttributes; x++)
		 {
		   av = (com.treetop.businessobjects.AttributeValue) listAttr.elementAt(lot);
		     String tdClass = "class=\"td0410\" ";
		 	 String displayAttrValue = av.getValue();
		 	 String alignValue = "";
		 	 // Adding them together
		 	 if (av.getFieldType().equals("numeric"))
		 	 {
		 	 	try
		 	 	{
		 	 	   if (aveValue[x] == null)
		 	 	      aveValue[x] = "0";
		 	 	   java.math.BigDecimal xxx = new java.math.BigDecimal(aveValue[x]).add(new java.math.BigDecimal(av.getValue()));
		 	 	   aveValue[x] = xxx.toString();
		 	 	   
		 	 	   displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, new Integer((String) decimals.elementAt(x)).intValue());
		 	 	   alignValue = "style=\"text-align:right\" ";
		 	 	}
		 	 	catch(Exception e)
		 	 	{
		 	 	}
		 	 }
		 	 else
		 	 	aveValue[x] = "&nbsp;";
		 	 	
		 	 if (av.getFieldType().equals("numeric"))
		 	 { // Only have the option to Traffic Light on a Numeric Field
		 	    try
		 	    {
		 	       BigDecimal v1 = new BigDecimal(av.getValue());
		 	       BigDecimal l1 = new BigDecimal(av.getLowValue());
		 	 	   BigDecimal h1 = new BigDecimal(av.getHighValue());
		 	 	   if (v1.compareTo(l1) < 0 ||
		 	 	       v1.compareTo(h1) > 0)
		 	 	     tdClass = "class=\"td0110000003\" ";
		 	    }
		 	    catch(Exception e)
		 	    {
		 	    }
		 	 }
%>					
 	  <td <%= tdClass %><%= alignValue %>><%= displayAttrValue %></td>
<%
		   lot++;
		 } // end of the for loop for Attributes
%>
        </tr>		 
<%                  
      } // End of if sames Line Number
   } // End of the For Loop for Lots	     
   //*********************************************************************************************
   // Once more for the Average of the Attributes
   String columnName2 = "Average of Attributes --->>>";
%>   
     <tr class="tr01">
      <td class="td04100405" colspan=<%= columnSpan %>>
<%= columnName2 %>
	  </td>
<%	  
    for (int x = 0; x < aveValue.length; x++)
   {
      String displayAve = "&nbsp;";
      if (!aveValue[x].equals("&nbsp;"))
      {
         try
         {
            BigDecimal a = new BigDecimal(aveValue[x]);
            if (a.compareTo(new BigDecimal("0")) > 0 &&
                new BigDecimal(countLot).compareTo(new BigDecimal("0")) > 0)
            {
               BigDecimal ave = a.divide(new BigDecimal(countLot), 2);
               displayAve = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(ave.toString(), new Integer((String) decimals.elementAt(x)).intValue());
            }   
            else 
               displayAve = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber("0", new Integer((String) decimals.elementAt(x)).intValue());
         }
         catch(Exception e)
         {}
      }
%> 
      <td class="td04100405" style="text-align:right">
        <%= displayAve %>
	  </td>
<%
	}	 // END OF THE for Loop
%>
     </tr> 
 <%
  }
  catch(Exception e)
  {
     System.out.println("Problem Found" + e);
  }
%>  
 </table>
 </body>
</html>