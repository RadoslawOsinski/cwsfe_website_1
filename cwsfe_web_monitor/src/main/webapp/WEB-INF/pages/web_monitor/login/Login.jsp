<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <meta name="description" content="WEB MONITOR"/>
    <meta name="author" content="Radosław Osiński">
    <meta name="robots" content="none"/>
    <%--none=noindex, nofollow--%>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"/>
    <title>CWSFE CMS</title>

    <link href="${pageContext.request.contextPath}/resources-cwsfe-web-monitor/favicon.png" rel="shortcut icon"
          type="image/x-icon"/>

    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-web-monitor/css/LoginPage-min.css"--%>
    <%--type="text/css"/>--%>

    <!-- jQuery -->
    <%--<script src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jquery-2.0.3.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jqueryui/jquery-ui-1.10.3.custom.min.js"></script>--%>

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources-cwsfe-web-monitor/js/Login.js"></script>--%>

</head>

<body>

<spring:url value="/CWSFE_WEB_MONITOR/cwsfe_web_monitor_security_check" var="loginCheckUrl" htmlEscape="true"/>
<form id="login_form" action="${loginCheckUrl}" method="POST">
    <label for="username">Username</label>
    <input type="text" id="username" name="userName" class="w-icon">

    <label for="password">Password</label>
    <input type="password" id="password" name="password" class="w-icon">

    <input type="submit" value="Login" class="button blue tiny">

    <a href="${mainSiteUrl}"><spring:message code="BackToMainWebsite" text="BackToMainWebsite"/></a>
</form>

</body>
</html>
