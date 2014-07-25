<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- 
    There is an issue copy/pasting into MS Office from secured applications.  It tries to access the
    style sheet which require a login.  It only appears to be an issue for MSIE8.  The hack around this
    is to include the style sheet references inside the body.  Browsers (including MSIE8) accept this
    and render the style appropriately.  However, when MS Office tries to look for style sheets (in the <head>,
    where they are supposed to be) it can't find them, and renders the content on its own.
--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Include/css/cupertino/jquery-ui-1.8.22.custom.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/Include/css/style-compiled.css" />