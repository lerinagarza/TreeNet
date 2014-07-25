<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
import = "
    java.util.Vector,
    com.treetop.app.quality.BuildProductDataSheetAttributes,
    com.treetop.businessobjects.QaTestParameters
"
%>

<%
    Vector tests = (Vector) request.getAttribute("listSpecAttribute");
    Vector testsSel = (Vector) request.getAttribute("listSpecAttributeSel");


 %>


<%  
    boolean show = false;
    for (int i=0; i<testsSel.size(); i++) {
        BuildProductDataSheetAttributes bpdsa = (BuildProductDataSheetAttributes) testsSel.elementAt(i);
        if (!bpdsa.getSelectAttribute().equals("")) {
            show = true;
            request.setAttribute("hasAttribute", "true");
            break;
        }
    }
%>      
<%  if (show) { %>


<%
    boolean showTargetColumn = false;
    for (int i=0; i<tests.size(); i++) {
       QaTestParameters test = (QaTestParameters) tests.elementAt(i);
       BuildProductDataSheetAttributes bpdsa = (BuildProductDataSheetAttributes) testsSel.elementAt(i);
       
       if (!bpdsa.getSelectAttribute().equals("")) {
          if (!test.getTargetValue().trim().equals("")) {
              showTargetColumn = true;
              break;
          }
       }
       
    }


 %>

<table>
    <tr>
        <th></th>
        <th>Unit of Measure</th>
    <%  if (showTargetColumn) { %>
        <th>Target</th>
    <%  } %>
        <th>Minimum</th>
        <th>Maximum</th>
    </tr>
<%  for (int i=0; i<tests.size(); i++) {
       QaTestParameters test = (QaTestParameters) tests.elementAt(i);
       BuildProductDataSheetAttributes bpdsa = null;
       try {
           bpdsa = (BuildProductDataSheetAttributes) testsSel.elementAt(i);
       } catch (Exception e) {
           bpdsa = new BuildProductDataSheetAttributes();
           System.err.println("mismatch");
       }
       
       
%>          
<% if (!bpdsa.getSelectAttribute().equals("")) { %> 
    <tr>
        <td><%=test.getAttributeIdentity() %></td>
        <td class="center"><%=test.getUnitOfMeasure() %></td>
    <%  if (showTargetColumn) { %>
        <td class="right"><%=test.getTargetValue() %></td>
    <%  } %>
        <td class="right"><%=test.getMinimumStandard() %></td>
        <td class="right"><%=test.getMaximumStandard() %></td>
    </tr>
    <%  } %>
<%  } %>
</table>

<%  } %>
