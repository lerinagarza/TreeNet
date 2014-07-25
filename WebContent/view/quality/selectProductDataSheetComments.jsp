<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="
    com.treetop.businessobjects.KeyValue,
    java.util.Vector,
    java.util.Arrays
" %>
<%  
    Vector comments = new Vector();
    String commentTitle = "";
    String[] selectedComments = new String[]{};
    String linesFieldName = "";
    try {
    
        comments = (Vector) request.getAttribute("listKeyValues");
        commentTitle = (String) request.getAttribute("commentTitle");
        
        selectedComments = (String[]) request.getAttribute("selectedComments");
        if (selectedComments == null) {
            selectedComments = new String[] {};
        }
        
        linesFieldName = (String) request.getAttribute("linesFieldName");        
    
    } catch(Exception e) { }
%>

<%  if (!(comments == null || comments.isEmpty())) { %>

    <%  if (!(commentTitle == null || commentTitle.equals(""))) { %>
    <h4><%=commentTitle %></h4>
    <%  } %>

<%  for (int i=0; i<comments.size(); i++) { 
        KeyValue kv = (KeyValue) comments.elementAt(i);
        String checked = "";
        if (Arrays.asList(selectedComments).contains(kv.getUniqueKey())) {
            checked = "checked";
        }

%>
    <table class="styled full-width">
        <tr>
            <td style="width:5%;" class="center">
                <input type="checkbox" name="<%=linesFieldName %>" value="<%=kv.getUniqueKey() %>" <%=checked %>>
            </td>
            <td>
                <p><%=kv.getValue() %></p>
            </td>
        </tr>
    </table>

<%  } // end loop %>

<%  } // end isEmpty() %>


<%  request.setAttribute("listKeyValues", null);
    request.setAttribute("commentTitle", null);
    request.setAttribute("selectedComments", null);
    request.setAttribute("linesFieldName", null);

 %>
