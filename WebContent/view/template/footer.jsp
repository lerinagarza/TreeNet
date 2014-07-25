<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    </div>

    <footer>&copy; 2013. Confidential Property of Tree Top, Inc.</footer>

<%  //have the Google Analytics code run as the last thing to load on the page
    //Do not post data to Google Analytics when running on localhost
    if (!request.getServerName().equals("localhost")) { %> 
<script type="text/javascript">
<% String userID = com.treetop.SessionVariables.getSessionttiUserID(request, response); 
 // if the user id is null or empty set the string accordingly -->
 if(userID == null){ 
    userID="null"; 
 }else if(userID.equals("")){
    userID="empty"; 
} 
%> 
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-27858316-1']); 
    _gaq.push(['_setCustomVar', 
       1,             // This custom var is set to slot #1.  Required parameter. 
       'User ID',   // The name of the custom variable.  Required parameter. 
      '<%= userID %>',      // Sets the value of "User Type" to "Member" or "Visitor" depending on status.  Required parameter.
        2             // Sets the scope to session-level.  Optional parameter. 
    ]); 
   _gaq.push(['_setSiteSpeedSampleRate', 100]); 
   _gaq.push(['_trackPageview']); 

   (function() { 
     var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; 
     ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; 
     var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s); 
   })(); 

</script> 
<%  } %>

