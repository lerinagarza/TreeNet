<%@ page language = "java" %>
<%@ page import = "com.treetop.*" %>
<%@ page import = "com.treetop.data.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%
//---------------- updSampleLabels.jsp ---------------------------------------//
//
//  Author :  Teri Walton  1/28/04
//                                                                      
// Changes:
//   Date       Name       Comments
// --------   ---------   -------------
//  8/14/08    TWalton     Changed new Stylesheet - new Look (info on New Machine)
//  1/12/07    TWalton	   Take out the Word Chemical -- just leave Additive
//  2/26/04    TWalton     Changed comments and images for 5.0 server.
//------------------------------------------------------------------------------//
//**************************************************************************//
//********** This code has to be on every JSP (First Code)  *********//
  //****  for the headings and such to work ***//
   request.setAttribute("title", "Print Options for Labels");
   String parameterList = "&returnToPage=TreeNetInq";
   request.setAttribute("parameterList", parameterList);
   request.setAttribute("newAddWindow", "window.open(newPage1)");
   request.setAttribute("hitResult", "");
   request.setAttribute("extraOptions", "");

//**************************************************************************//
// Get Request Variables for Display on this Screen
//**************************************************************************
   String sampleNumber          = request.getParameter("sampleNumber");
   if (sampleNumber == null || sampleNumber.length() == 0)
      sampleNumber = "";
//**************************************************************************//
// Get Parameters for Use when redisplaying the screen
//***********************************************************
   String labelNumber          = request.getParameter("labelNumber");
   if (labelNumber == null || labelNumber.length() == 0)
      labelNumber = "1";

%>

<html>
<head>
 <%= JavascriptInfo.getNumericCheck() %>
 <%= JavascriptInfo.getRequiredField() %>
<title>Update Label Information</title>
</head>
<body>
<jsp:include page="../../Include/heading.jsp"></jsp:include>
    <form action="/web/CtlSampleRequest?requestType=displayLabel" method="post" target="_blank">
  <table class="table00" cellspacing="0">
   <tr>
      <td style="width:5%">&nbsp;</td>
      <td>
        <table class="table00" cellspacing="0">
          <tr>
            <td colspan="8">&nbsp;</td>
          </tr>
          <tr class="tr01">
            <td rowspan="10" style="width:3%">&nbsp;</td>
            <td style="width:2%">&nbsp;</td>
            <td class="td0416"><b>Sample Request Number:</b></td>
            <td class="td0416">
              <input type="hidden" name="sampleNumber" value="<%= sampleNumber %>">
              <b><%= sampleNumber %></b>
            </td>
            <td class="td04160102"><b>Beginning Label Number on Page:</b></td>
            <td class="td04160102">
              <select name="labelNumber">
                <option value="1">&nbsp;1&nbsp;
                <option value="2">&nbsp;2&nbsp;
                <option value="3">&nbsp;3&nbsp;
                <option value="4">&nbsp;4&nbsp;
                <option value="5">&nbsp;5&nbsp;
                <option value="6">&nbsp;6&nbsp;
                <option value="7">&nbsp;7&nbsp;
                <option value="8">&nbsp;8&nbsp;
                <option value="9">&nbsp;9&nbsp;
                <option value="10">&nbsp;10&nbsp;
              </select>&nbsp;
               <img src="https://image.treetop.com/webapp/10labelpage1.bmp">
            </td>
            <td style="width:2%">&nbsp;</td>
            <td rowspan="10" style="width:3%">&nbsp;</td>
          </tr>
          <tr class="tr01">
            <td colspan="3">&nbsp;</td>
            <td class="td0416"><b>Print Shipping Label?:</b></td>
            <td class="td0416">
              <input type="checkbox" name="shippingLabel">
            </td>
            <td>&nbsp;</td>
          </tr>
          <tr class="tr02">
            <td class="td0416" colspan="6">
              <b>Label Detail Information</b>
            </td>
          </tr>
          <tr class="tr00">
            <td class="td04160102">&nbsp;</td>
            <td class="td04160102"><b>Row 1:</b></td>
            <td class="td04160102">
              <select name="row1seq1">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Art Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg" selected>&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td044160102" colspan="2">
              <select name="row1seq2">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft" selected>&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102">&nbsp;</td>
          </tr>
          <tr class="tr00">
            <td class="td04160102">&nbsp;</td>
            <td class="td04160102"><b>Row 2:</b></td>
            <td class="td04160102">
              <select name="row2seq1">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt" selected>&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102" colspan="2">
              <select name="row2seq2">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs" selected>&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102">&nbsp;</td>
          </tr>
          <tr class="tr00">
            <td class="td04160102">&nbsp;</td>
            <td class="td04160102"><b>Row 3:</b></td>
            <td class="td04160102">
              <select name="row3seq1">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca" selected>&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102" colspan="2">
              <select name="row3seq2">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102">&nbsp;</td>
          </tr>
          <tr class="tr00">
            <td class="td04160102">&nbsp;</td>
            <td class="td04160102"><b>Row 4:</b></td>
            <td class="td04160102">
              <select name="row4seq1">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl" selected>&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102" colspan="2">
              <select name="row4seq2">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl" selected>&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102">&nbsp;</td>
          </tr>
          <tr class="tr00">
            <td class="td04160102">&nbsp;</td>
            <td class="td04160102"><b>Row 5:</b></td>
            <td class="td04160102">
              <select name="row5seq1">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102" colspan="2">
              <select name="row5seq2">
                <option value="">  &nbsp; Do Not Display
                <option value="ad">&nbsp; Additional Description &nbsp;
                <option value="bx">&nbsp; Brix
                <option value="ca">&nbsp; Additive
                <option value="cl">&nbsp; Color
                <option value="c2">&nbsp; Art Color  <% //wth 06/08/2011 %>
                <option value="c3">&nbsp; Nat Color  <% //wth 06/08/2011 %>
                <option value="ds">&nbsp; Description &nbsp;
                <option value="fl">&nbsp; Flavor
                <option value="f2">&nbsp; Atr Flavor <% //wth 06/08/2011 %>
                <option value="f3">&nbsp; Nat Flavor <% //wth 06/08/2011 %>
                <option value="ft">&nbsp; Fruit Type
                <option value="fv">&nbsp; Fruit Variety
                <option value="ln">&nbsp; Lot Number
                <option value="pg">&nbsp; Product Group
                <option value="cs">&nbsp; Product Size
                <option value="pt">&nbsp; Product Type
                <option value="re">&nbsp; Resource
                <option value="sz">&nbsp; Sample Size
                <option value="sp">&nbsp; Spec
              </select>
            </td>
            <td class="td04160102">&nbsp;</td>
          </tr>
          <tr class="tr02">
            <td class="td0416" style="text-align:center" colspan="6">
              <input type="Submit" value="Display Labels">
            </td>
          </tr>
        </table>
      </td>
      <td style="width:5%">&nbsp;</td>
    </tr>

  </table>
    </form>
 <jsp:include page="../../Include/footer.jsp"></jsp:include>   
</body>
</html>