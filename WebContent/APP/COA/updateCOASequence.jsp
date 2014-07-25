<%@ page import = "java.util.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.math.*" %>

<%

//---------------- APP/COA/updateCOASequence.jsp -----------------------//
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
 Vector   listSequence = new Vector();
 Vector   listAttributes = new Vector();
 Vector   listLots = new Vector();
 String   coaTypeSequenceScreen = "CO";
 try
 {
 	listSequence = (Vector) request.getAttribute("listSequence");
 	listAttributes = (Vector) request.getAttribute("listAttributes");
 	listLots = (Vector) request.getAttribute("listLots");
 	coaTypeSequenceScreen = (String) request.getAttribute("coaType");
 }
 catch(Exception e)
 {
 }  
%>
<html>
  <head>
  <%= JavascriptInfo.getNumericCheck() %>
  </head>
  <body>
<% 
  if (listLots.size() > 0)
  {
%>  
   <table cellspacing="0" style="width:100%" border="1">
    <tr class="tr02">
    <td class="td0412" colspan="5">
	 <b>Select Idents for COA</b>
	</td>
<%
  //    Span the columns for the number of pallets
  if (coaTypeSequenceScreen.trim().equals("LOT"))
  {
%>	
	<td class="td0412">&nbsp;</td>
<%  
  }
  else
  {  
%>	
	<td class="td0412" colspan="<%= listLots.size() %>">
	 <b>Lots Shipped</b>
	</td>
<%
   }
%>	
   </tr>
   <%= HTMLHelpersInput.inputBoxHidden("numberOfAttributes", ("" + listSequence.size())) %>
<%   
 //	  BEGIN ROW -----------------------------------------------------------------------------------  
 String s1 = "style=\"width:6%\"";
 String s2 = "style=\"width:4%\"";
  if (coaTypeSequenceScreen.trim().equals("LOT"))
  {
    s1 = "";
    s2 = "";
  }
%> 
   <tr class="td01">
	<td class="td0412" style="width:3%">
	 <b>Sequence:</b>
	</td>
	<td class="td0412" <%= s1 %>>
	 <b>Attribute Description</b>
	</td>
	<td class="td0412" <%= s2 %>>
	 <b>Attribute</b>
	</td>
	<td class="td0412" <%= s2 %>><b>
<%
  if (coaTypeSequenceScreen.trim().equals("LOT"))
    out.println("Minimum Value");
  else
    out.println("MIN");    
%>	
	</b></td>
	<td class="td0412" <%= s2 %>><b>
<%
  if (coaTypeSequenceScreen.trim().equals("LOT"))
    out.println("Maximum Value");
  else
    out.println("MAX");    
%>	
	</b></td>
<%
  if (coaTypeSequenceScreen.trim().equals("LOT"))
  {
%>	
	<td class="td0412"><b>Actual Value</b></td>
<%  
  }
  else
  {  
   // for however many pallets we have
   
    for (int y = 0; y < listLots.size(); y++)
    {
       String loty = (String) ((Vector) listLots.elementAt(y)).elementAt(0);
%>	       
	<td class="td0412" style="width:4%">
	 <b><%= (String) ((Vector) listLots.elementAt(y)).elementAt(0) %></b>
	</td>			
<%	               	  
    }
  }
%>
   </tr>
<%
//END OF ROW -----------------------------------------------------------------------------------
      if (listSequence.size() > 1)
      {
       // Number of Idents
	    for (int x = 1; x < listSequence.size(); x++)
	    {
	       com.treetop.businessobjects.AttributeValue av2 = (com.treetop.businessobjects.AttributeValue) listAttributes.elementAt(x);
           if (!av2.getFieldType().equals(""))
           {
//BEGIN ROW -----------------------------------------------------------------------------------
%>
   <tr class="tr00">  
	<td class="td0410">
	 <%= HTMLHelpersInput.inputBoxNumber(("seq" + x), ((String) listSequence.elementAt(x)), ("Sequence " + x), 3, 3, "", "N" ) %>
	</td>
	<td class="td0410">
     <%= av2.getAttributeDescription().trim() %>
	</td>
 	<td class="td0410">
     <%= av2.getAttribute().trim() %>
     <%= HTMLHelpersInput.inputBoxHidden(("attr" + x), FindAndReplace.replaceQuoteWithWord(av2.getAttribute().trim())) %>     
	</td>  	
	<td class="td0410" style="text-align:center">
<%
    if (av2.getLowValue() == null ||
       !av2.getFieldType().equals("numeric"))
       out.println("&nbsp;");
    else
       out.println(HTMLHelpersMasking.maskNumber(av2.getLowValue(), (new Integer(av2.getDecimalPlaces()).intValue())));
%>	
	</td>  
	<td class="td0410" style="text-align:center">
<%
    if (av2.getHighValue() == null ||
       !av2.getFieldType().equals("numeric"))
       out.println("&nbsp;");
    else
       out.println(HTMLHelpersMasking.maskNumber(av2.getHighValue(), (new Integer(av2.getDecimalPlaces()).intValue())));
%>	
	</td>
<%
      for (int y = 0; y < listLots.size(); y++)
      {
      		 String tdClass = "class=\"td0410\" ";

		 	 if (av2.getFieldType().equals("numeric"))
		 	 { // Only have the option to Traffic Light on a Numeric Field
		 	    try
		 	    {
		 	       BigDecimal v1 = new BigDecimal((String) ((Vector) listLots.elementAt(y)).elementAt(x));
		 	       BigDecimal l1 = new BigDecimal(av2.getLowValue());
		 	 	   BigDecimal h1 = new BigDecimal(av2.getHighValue());
		 	 	   if (v1.compareTo(l1) < 0 ||
		 	 	       v1.compareTo(h1) > 0)
		 	 	     tdClass = "class=\"td0110000003\" ";
		 	    }
		 	    catch(Exception e)
		 	    {
		 	    }
		 	 }
		 	 String dspValue = (String) ((Vector) listLots.elementAt(y)).elementAt(x);
		 	 if (dspValue.trim().equals(""))
		 	   dspValue = "&nbsp;";

%>
    <td <%= tdClass %> style="text-align:center">
	   <%= dspValue %>
	</td>
<%					  
	     } // end of the for loop for number of pallets
%>		  
   </tr>
<%   
          }					    
	    } // for the end of the Loop for Attribute Sequence
    }	 // End of the IF for Sequence
}
//	  END OF ROW -----------------------------------------------------------------------------------
%>
  </table> 
 </body>
</html>