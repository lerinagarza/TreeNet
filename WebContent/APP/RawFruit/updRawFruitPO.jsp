<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- updRawFruitPO.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/10/08
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
try
{ // Must have because this ONE page is included MANY times on a page
//**************************************************************************//
 //Vector binInfo = new Vector();
 UpdRawFruitLoad poLoad = new UpdRawFruitLoad();
 UpdRawFruitPO upRFPO = new UpdRawFruitPO();
 int expCnt = 5;
 int imgCnt = 5;
 String seqNumber = "1";
 int lotCnt = 0;
 try
 {
    poLoad = (UpdRawFruitLoad) request.getAttribute("updViewBean");
    upRFPO = (UpdRawFruitPO) request.getAttribute("updPOInfo");
     // Going to need UpdRawFruitPO As well   
    seqNumber = upRFPO.getSequenceNumber();
    imgCnt = new Integer((String) request.getAttribute("imageCount")).intValue();
    expCnt = new Integer((String) request.getAttribute("expandCount")).intValue();
    lotCnt = new Integer((String) request.getAttribute("lotCount")).intValue();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }  
   String readOnlyPO = "";
%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>  
 </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="1">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102"><acronym title="PO Number">PO Number:</acronym></td>
   <td class="td04160102"><b>
<%
   if (upRFPO.getPoNumber().trim().equals("0"))
      out.println("PO Not Yet Created");
   else
      out.println(upRFPO.getPoNumber());
   // may want to add a spot to put a Box to ADD the PO
%>
   &nbsp;</b>
   <%= HTMLHelpersInput.inputBoxHidden((seqNumber + "poNumber"), upRFPO.getPoNumber()) %>
   <%= HTMLHelpersInput.inputBoxHidden((seqNumber + "sequenceNumber"), seqNumber) %>
   </td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04160102" style="text-align:right" colspan="4">&nbsp;
   <%= UpdRawFruitLoad.buildMoreButton("updatePO", poLoad.getScaleTicket(), poLoad.getScaleTicketCorrectionSequence(), "", seqNumber, poLoad.getPoNumber(), "", "") %>
   </td> 
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>	 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Facility">Facility:</acronym></td>
   <td class="td04140102"><b><%= upRFPO.getPoInfo().getWarehouseFacility().getFacility() %>&nbsp;-&nbsp;<%= upRFPO.getPoInfo().getWarehouseFacility().getFacilityDescription() %></b></td>
   <td class="td04140102"></td>
   <td class="td04140102"><acronym title="Accepted Bins on PO">Accepted Bins on PO:</acronym></td>
   <td class="td04140102" style="text-align:right"><b>&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(upRFPO.getPoInfo().getPoAcceptedBins(), 0) %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Weight of Accepted Bins on PO">Weight Accepted on PO:</acronym></td>
   <td class="td04140102" style="text-align:right"><b>&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(upRFPO.getPoInfo().getPoAcceptedWeight(),0) %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Warehouse">Warehouse:</acronym></td>
   <td class="td04140102"><%= poLoad.buildDropDownWarehouse((seqNumber + "poWarehouse"), upRFPO.getPoWarehouse(), readOnlyPO) %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Rejected Bins on PO">Rejected Bins on PO:</acronym></td>
   <td class="td04140102" style="text-align:right"><b>&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(upRFPO.getPoInfo().getPoRejectedBins(),0) %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Weight of Rejected Bins on PO">Weight Rejected on PO:</acronym></td>
   <td class="td04140102" style="text-align:right"><b>&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(upRFPO.getPoInfo().getPoRejectedWeight(),0) %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Supplier - Packer - Who we received the Fruit From   ** Supplier number in M3  CRS620 for Number and Mileage">Supplier:</acronym></td>
<%
   String classValue = "td04140102";
   if (!upRFPO.getSupplierError().trim().equals(""))
      classValue = "td03140102";
%>
   <td class="<%= classValue %>"><%= HTMLHelpersInput.inputBoxText((seqNumber + "supplier"), upRFPO.getSupplier(), "Supplier Number", 10,10,"Y", readOnlyPO) %>&nbsp;<%= upRFPO.getSupplierError() %><%= upRFPO.getPoInfo().getPoSupplier().getSupplierName() %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Total Bins on PO">Total Bins on PO:</acronym></td>
   <td class="td04140102" style="text-align:right"><b>&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(upRFPO.getPoInfo().getPoTotalBins(),0) %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Weight of Total Bins on PO">Total Weight on PO:</acronym></td>
   <td class="td04140102" style="text-align:right"><b>&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(upRFPO.getPoInfo().getPoTotalWeight(),0) %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	   
<%
  // Roll Through the List of Lot Numbers:
  if (upRFPO.getPoInfo().getListLots().size() > 0)
  {
    for (int z = 0; z < upRFPO.getPoInfo().getListLots().size(); z++)
    {
      try
      {
        RawFruitLot rfLot = (RawFruitLot) upRFPO.getPoInfo().getListLots().elementAt(z);
        expCnt++;
        imgCnt++;
        lotCnt++;
        request.setAttribute("imageCount", (imgCnt + ""));
        request.setAttribute("expandCount", (expCnt + ""));
        request.setAttribute("rfLot", rfLot);
        String nameOfExpandingSection = "Lot Number: " + rfLot.getLotNumber();
        // Potentially add the Lot Sequence Number where needed.
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan="8">
   <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection("C", nameOfExpandingSection, 0, expCnt, imgCnt, 1, 0) %>
       <jsp:include page="dtlRawFruitLot.jsp"></jsp:include> 
       </span>    
      </td>
     </tr>
    </table>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>	 
<%
        imgCnt = new Integer((String) request.getAttribute("imageCount")).intValue();
        expCnt = new Integer((String) request.getAttribute("expandCount")).intValue();
      }
      catch(Exception elot)
      {
      }
    }
  }   
%>  
  <tr>
   <td  class="td03140102" colspan="10" style="text-align:center"><b>
<%
  if (upRFPO.getPoWarehouse().trim().equals("") ||
      upRFPO.getSupplier().trim().equals(""))
     out.println("You must choose both a Warehouse and a Supplier to have the ability to ADD a Lot Number");
  else
	 out.println(HTMLHelpers.buttonSubmit(("addLot" + seqNumber), "Add Lot To PO Sequence: " + seqNumber));
%>     
   </b>&nbsp;&nbsp;</td> 
  </tr>
    </table>  
<%
     request.setAttribute("imageCount", (imgCnt + ""));
     request.setAttribute("expandCount", (expCnt + ""));
     request.setAttribute("lotCount", (lotCnt + ""));
   }
   catch(Exception e)
   {}
  
%>
   </body>
</html>