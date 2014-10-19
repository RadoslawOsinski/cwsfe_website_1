<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <meta name="description" content="Web monitor"/>
    <meta name="author" content="Radosław Osiński">
    <meta name="robots" content="all"/>
    <title>CWSFE WEB MONITOR</title>

    <c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">
        <link rel="canonical" href="http://cwsfe.pl"/>
    </c:if>
    <c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'en'}">
        <link rel="canonical" href="http://cwsfe.eu"/>
    </c:if>

    <link href="${pageContext.request.contextPath}/resources-cwsfe-web-monitor/favicon.png" rel="shortcut icon"
          type="image/x-icon"/>

    <c:forEach var="cssUrl" items="${additionalCssCode}">
        <link href="${cssUrl}" rel="stylesheet" type="text/css" media="all"/>
    </c:forEach>

    <c:forEach var="jsUrl" items="${additionalJavaScriptCode}">
        <script type="text/javascript" src="${jsUrl}"></script>
    </c:forEach>

    <c:forEach var="asyncJsUrl" items="${additionalAsyncJavaScriptCode}">
        <script type="text/javascript" async src="${asyncJsUrl}"></script>
    </c:forEach>

</head>
<body>
header