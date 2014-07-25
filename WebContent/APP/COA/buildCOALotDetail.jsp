<%@page import="com.treetop.utilities.html.HTMLHelpers"%>
<%@page import="com.treetop.businessobjects.Lot"%>
<%@page import="com.treetop.app.coa.BuildCOA"%>
<%@page import="com.treetop.businessobjects.AttributeValue"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%

//---------------- APP/COA/listCOALotDetail.jsp -----------------------//
//Author   :  Teri Walton       12/28/09 (thrown from servlet)
//Changes  :
//Date       Name          Comments
//----       ----          --------
//------------------------------------------------------------//
//********************************************************************
 // Bring in the Detail View Bean.
  BuildCOA bldCOA = new BuildCOA();
  Vector listAttributes = new Vector();
  Lot lotInfo = new Lot();
  try
  {
	 bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
     com.treetop.businessobjectapplications.BeanCOA bc = (com.treetop.businessobjectapplications.BeanCOA) bldCOA.getListReport().elementAt(0);
	 listAttributes = bc.getListAttrValues();
	 lotInfo = ((AttributeValue) listAttributes.elementAt(0)).getLotObject();

  }
  catch(Exception e)
  {
  }

%>

<html>
  <head>
  </head>
  <body>
   <table cellspacing="0" style="width:100%">
    <tr class="tr01">
     <td class="td04141405" style="width:5%">&nbsp;</td>
     <td class="td04141405">Basic Unit of Measure: <b><%= lotInfo.getBasicUnitOfMeasure() %></b>&nbsp;</td>
     <td class="td04141405">Attribute Model: <b><%= lotInfo.getAttributeModel() %></b>&nbsp;</td>    
     <td class="td04141405" style="text-align:right">
<%
    String displayLink = "Change Idents for Lot " + lotInfo.getLotNumber();
    String actualURL   = "/web/CtlCOANew?requestType=goToUpdate&environment=" + bldCOA.getEnvironment() + 
                         "&inqLot=" + lotInfo.getLotNumber().trim() +
    			         "&coaType=" + bldCOA.getCoaType();
%>     
      <%= com.treetop.utilities.html.HTMLHelpersLinks.basicLinkSamePage(displayLink , actualURL, displayLink, "a0412", "") %>
     </td>
    </tr>             
   </table>
   <table cellspacing="0" style="width:100%" border="1">
    <tr class="tr02">
     <td class="td0414"><b>Attribute</b></td> 
     <td class="td0414"><b>Minimum Value</b></td> 
     <td class="td0414"><b>Maximum Value</b></td> 
     <td class="td0414"><b>Actual Value</b></td> 
    </tr>
<%
   if (listAttributes.size() > 0)
   {
     for (int x = 0; x < listAttributes.size(); x++)
     {
        AttributeValue av = (AttributeValue) listAttributes.elementAt(x);
      //  System.out.println(av.getAttributeDescription());
      //  System.out.println(av.getFieldType());
     //   System.out.println(av.getAttributeSequence());
        if (!av.getFieldType().trim().equals(""))
         //&& 11/7/11 TWalton -- do not use the attribute sequence,  that is from the model
         //   !av.getAttributeSequence().trim().equals("0"))
        {
           String displayValue = av.getValue();
           String lowValue = "&nbsp;";
           String hiValue = "&nbsp;";
           String tdClass = "class=\"td0412\" ";
    	   String alignValue = "";
           if (av.getFieldType().equals("numeric"))
           {
              lowValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(av.getLowValue(), (new Integer(av.getDecimalPlaces()).intValue()));
              hiValue  = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(av.getHighValue(), (new Integer(av.getDecimalPlaces()).intValue()));
              alignValue = "style=\"text-align:right\" ";
              displayValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(av.getValue(), (new Integer(av.getDecimalPlaces()).intValue()));
              try
 	    	  {
   			       BigDecimal v1 = new BigDecimal(av.getValue());
 	    		   BigDecimal l1 = new BigDecimal(av.getLowValue());
		 	 	   BigDecimal h1 = new BigDecimal(av.getHighValue());
		 	 	   
		 	 	   if (v1.compareTo(l1) < 0 ||
		 	 	       v1.compareTo(h1) > 0)
 	 			   {
		 	 	     tdClass = "class=\"td0112000003\" ";
 	 			   }
		 	    }
		 	    catch(Exception e)
 	    		{}
           }
%>    
    <tr class="tr00">
     <td class="td0412"><%= av.getAttributeDescription() %></td> 
     <td class="td0412" style="text-align: right"><%= lowValue %></td>
     <td class="td0412" style="text-align: right"><%= hiValue %></td>
     <td <%= tdClass %><%= alignValue %>><%= displayValue %></td>
    </tr>   
<%
        }// if a valid Field Type for the Attribute
     } //for The list of Attributes
   }
%>    
   </table> 
 </body>
</html>