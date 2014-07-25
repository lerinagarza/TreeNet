<%@page import="com.treetop.SessionVariables"%>
<%@page import="com.treetop.utilities.html.HTMLHelpersMasking"%>
<%@page import = "java.util.Vector, java.util.Arrays, com.treetop.businessobjects.KeyValue" %>
<%  KeyValue key = (KeyValue) request.getAttribute("keys");
    if (key == null) {
        key = new KeyValue();
    }
    boolean allViewOnly = true;
    try {
        String value = request.getParameter("viewOnly");
        if (value != null) {
            allViewOnly = Boolean.parseBoolean(value);
        }
    } catch (Exception e) {}
    
    boolean sequenced = false;
    try {
        String value = request.getParameter("sequenced");
        if (value != null) {
            sequenced = Boolean.parseBoolean(value);
        }
    } catch (Exception e) {}
    
    String environment = key.getEnvironment();
 %>
<%  Vector comments = (Vector) request.getAttribute("listComments"); 
    if (comments == null) {
        comments = new Vector();
    }
    comments.insertElementAt(key, 0);
    
%>


<div data-comment=section>


	<% for (int i=0; !comments.isEmpty() && i<comments.size(); i++) {    
	KeyValue comment = (KeyValue) comments.elementAt(i);
	String currentUser = SessionVariables.getSessionttiUserID(request, null);
	
	boolean editAll = false;
	String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
    if (key.isManagedByTT() && Arrays.asList(roles).contains("1")) {
        editAll = true;
	}
	
    if (editAll || currentUser.equals(comment.getLastUpdateUser())) {

        comment.setViewOnly(false);
    }

	%>
	<div data-comment=<%if(i==0){ %>new<%}else{ %>comment<%} %>>
	
	   <%  if (key.isDescriptionAsHeader() && !(comment.getDescription() == null || comment.getDescription().trim().equals(""))) { %>
	   <h6><%=comment.getDescription() %></h6>
	   <%  } %>
	
		<form>
		    <input type=hidden name=environment value=<%=environment %>>
		    <input type=hidden name=entryType value=<%=comment.getEntryType() %>>
			<input type=hidden name=key1 value=<%=comment.getKey1() %>>
			<input type=hidden name=key2 value=<%=comment.getKey2() %>>
			<input type=hidden name=key3 value=<%=comment.getKey3() %>>
			<input type=hidden name=key4 value=<%=comment.getKey4() %>>
			<input type=hidden name=key5 value=<%=comment.getKey5() %>>
			<input type=hidden name=uniqueKey value=<%=comment.getUniqueKey() %>>
				
			<div class=clearfix>
			
			<%   if((i > 0 && comment.isViewOnly()) || allViewOnly) { %>
                <pre><%=comment.getValue() %></pre>
			<%   } else { %>
				<textarea name=value class="no-print" cols=60 rows=4 maxlength=500 readonly><%=comment.getValue() %></textarea>
            
            <%  if (sequenced) { %>    
                <div style="float:left; margin:.5em">
                   <label for="<%=comment.getUniqueKey() %>_seq">Seq:
                   <input type="text" id="<%=comment.getUniqueKey() %>_seq" pattern="[0-9]*" 
                            name="sequence" value="<%=comment.getSequence() %>" 
                            size="3" disabled>
                   </label>
                </div>
            <%  } %>
                
                <div data-comment=buttons style="float:left;" class="no-print">
                    <button data-comment=edit>Edit</button>
                    <button data-comment=save>Save</button>
                    <button data-comment=delete>Delete</button>
                 </div>
             <%  } %>
                
				<div data-comment=update>
				<%  if (i > 0) { %>
					<%=comment.getLastUpdateUser() %> - <%=comment.getLastUpdateDate() %> 
					<%=HTMLHelpersMasking.maskTime(comment.getLastUpdateTime()) %>
				<% } %>
				</div> 
			

	         </div>
		</form>
	</div>

<%  } %>

    <%  if (!allViewOnly) { %>
    <div class="no-print">
        <button data-comment=add>Add New</button>
    </div>
    <% } %>

</div>