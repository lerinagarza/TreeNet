<%@page import = "com.treetop.businessobjects.KeyValue, com.treetop.utilities.RandomGUID" %>
<%  KeyValue keys = (KeyValue) request.getAttribute("keys");
    String contextPath = request.getContextPath();
    
    String url = contextPath +  
        "/CtlComments?requestType=listComments" +
        "&environment=" + keys.getEnvironment() +
		"&entryType=" + keys.getEntryType() + 
		"&key1=" + keys.getKey1() + 
		"&key2=" + keys.getKey2() +
		"&key3=" + keys.getKey3() +
		"&key4=" + keys.getKey4() +
		"&key5=" + keys.getKey5() +
		"&managedByTT=" + keys.isManagedByTT() +
		"&viewOnly=" + keys.isViewOnly() + 
		"&sequenced=" + keys.isSequenced();
    String guid = new RandomGUID().toString();
    
    String expandClass = "";
    if (keys.isVisibleOnLoad()) {
        expandClass = "expandOpen";
    } else {
        expandClass = "expand";
    }

    boolean heading = true;
    if (keys.getHeaderText() == null) {
        heading = false;
    }

 %>
<%  if (heading) { %>
    <h4 class=<%=expandClass %> id="<%=guid %>" onclick="commentAjaxCall(this,'<%=url %>',<%=keys.isViewOnly() %>)">
        <%=keys.getHeaderText() %>
    </h4>
<%  } else { %>
    <div id="<%=guid %>" onclick="commentAjaxCall(this,'<%=url %>',<%=keys.isViewOnly() %>)"></div>
<%  } %>

<% if(heading){ %>
<div class=collapse>
    <div class=center data-comment=loading>
        <img src="<%=request.getContextPath() %>/Include/images/ajax-loader-bar.gif">
    </div>
</div>
<% } else { %>
<div></div>

<%  } %>
<%  if (keys.isVisibleOnLoad() || !heading) { %>
<script>
    (function () {
        $('#<%=guid %>').click();
    })();
</script>
<%  } %>