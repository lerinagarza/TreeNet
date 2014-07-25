<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
import = "
    java.util.Vector,
    java.util.List,
    java.util.ArrayList,
    java.util.Arrays,
    com.treetop.businessobjects.KeyValue
"
%>
<%  Vector testComments = (Vector) request.getAttribute("specAttributeComments"); 
    String title = (String) request.getAttribute("specAttributeCommentsTitle");
    if (title == null) {
        title = "";
    }
    String[] lines = (String[]) request.getAttribute("specAttributeCommentsLines");
    
    
    List selectedIds = null;
    if (lines != null) {
        selectedIds = Arrays.asList(lines);
    } else {
        selectedIds = new ArrayList();
    }
    
    
    List selectedComments = new ArrayList();
    
    for (int i=0; i<testComments.size(); i++) {
        KeyValue kv = (KeyValue) testComments.elementAt(i);
        if (lines == null || selectedIds.contains(kv.getUniqueKey())) {
            selectedComments.add(kv.getValue());
        }
    }
    
    
%>
<%  if (!selectedComments.isEmpty()) { %>
    <div style="margin:.5em 3em;">
        <%  if (!title.equals("")) { %>
        <h4><%=title %></h4>
	    <% } %>
	    <div style="border:1px solid #ddd; border-radius:3px; background-color:#eee; padding:.5em 1em; ">
	    <%  for (int i=0; i<selectedComments.size(); i++) {
	        String comment = (String) selectedComments.get(i);

	     %>
	     
	    <%  if (i > 0) { %>
	        <br>
	    <%  } %>
	        <%=comment %>
	     <% } %>

	     </div>
     
     </div>
 <% } %>

 
 <% request.setAttribute("specAttributeCommentsTitle", ""); %>
 <% request.setAttribute("specAttributeCommentsLines", null); %>