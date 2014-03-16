<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <%--<meta name="google-site-verification" content="9-hlbv4ssuz-8sGFbM5L_7z7jnoLM02FEAw9PXaluCo" />  site ownership verification for google on cwsfe.pl--%>
    <%--<meta name="google-site-verification" content="PZozirK9YZvyx-DvstXsD_PdA2mH2tVBDTD1icnaEzw" />  site ownership verification for google on cwsfe.eu--%>
    <meta charset="UTF-8">
    <meta name="description" content="CWSFE - Complete Working Solution For Everyone"/>

    <meta name="keywords" content="<c:forEach var="blogKeyword" items="${keywords}">
        ${blogKeyword.name},
    </c:forEach>"/>

    <meta name="author" content="Radosław Osiński">
    <meta name="robots" content="all"/>
    <title>${headerPageTitle}</title>

    <c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">
        <link rel="canonical" href="http://cwsfe.pl"/>
    </c:if>
    <c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'en'}">
        <link rel="canonical" href="http://cwsfe.eu"/>
    </c:if>
    <%--$if($==($lang.currentLanguage(),"pl"),{--%>
    <%--<link rel="alternate" type="application/rss+xml" href="$page.url("cwsfe.rss.CWSFERSS_PL")" title="CWSFE RSS">--%>
    <%--}, {--%>
    <%--<link rel="alternate" type="application/rss+xml" href="$page.url("cwsfe.rss.CWSFERSS_EN")" title="CWSFE RSS">--%>
    <%--})--%>
    <link href="${pageContext.request.contextPath}/resources-cwsfe/img/layout/favicon.png" rel="shortcut icon" type="image/x-icon"/>

    <link href="${pageContext.request.contextPath}/resources-cwsfe/img/layout/css/style-min.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${pageContext.request.contextPath}/resources-cwsfe/img/layout/css/style-print-min.css" rel="stylesheet" type="text/css" media="print"/>

    <c:forEach var="cssUrl" items="${additionalCssCode}">
        <link href="${cssUrl}" rel="stylesheet" type="text/css" media="all"/>
    </c:forEach>

    <link href="${pageContext.request.contextPath}/resources-cwsfe/img/layout/Fonts1/Fonts1-min.css" rel="stylesheet" type="text/css"/>
    <%--$//	<link href="$page.url("cwsfe.layout.Fonts2")" rel="stylesheet" type="text/css"/>--%>

    <script src="${pageContext.request.contextPath}/resources-cwsfe/js/jquery/jquery-2.0.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources-cwsfe/js/jquery/jquery.tabify.js" type="text/javascript"></script>

    <script type="text/javascript">
        var lang = $('html').attr('lang');

        $.ajaxSetup({	// Zapobieganie dołączania parametru callback do urla
            jsonp: false,
            type: "post"
        });

        //GOOGLE PLUS INTEGRATION BELOW
        if (lang === "pl") {
            window.___gcfg = {
                lang: 'pl'
            };
        } else {
            window.___gcfg = {
                lang: 'en-US'
            };
        }
        (function () {
            var po = document.createElement('script');
            po.type = 'text/javascript';
            po.async = true;
            po.src = 'https://apis.google.com/js/plusone.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(po, s);
        })();

    </script>
    <script src="${pageContext.request.contextPath}/resources-cwsfe/js/jquery/shared_scripts.js" type="text/javascript"></script>

    <c:forEach var="jsUrl" items="${additionalJavaScriptCode}">
        <script type="text/javascript" src="${jsUrl}"></script>
    </c:forEach>

</head>
<body>

<%--$//	<noscript>--%>
<%--$//		<link rel="stylesheet" href="$page.url("cwsfe.layout.css.style-nojs.css")" type="text/css" />--%>
<%--$//		<div class="nojs-warning">--%>
<%--$//			<strong>JavaScript seems to be Disabled!</strong>--%>
<%--$//			Some of the website features are unavailable unless JavaScript is enabled.--%>
<%--$//		</div>--%>
<%--$//	</noscript>--%>

<div id="wrap">
    <div id="header" class="fixed">
        <div id="logo-header-widget-1" class="fixed" itemscope itemtype="http://schema.org/Organization">
            <spring:url value="${pageContext.request.contextPath}/" var="mainUrl" htmlEscape="true"/>
            <a id="logo" itemprop="url" href="${mainUrl}">
                <img itemprop="logo" src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/CWSFE_logo_150x150.png" height="150"
                     width="150" alt="logo"/>
            </a>
            <%@include file="SocialMedia.jsp" %>
        </div>
        <div id="menu-header-wigdet-2" class="fixed">
            <%@include file="Navigation.jsp" %>
            <div id="header-widget-2" class="col205 last">
                <h6 class="last text-right"><spring:message code="Phone"/>: +48 791 101 335</h6>
            </div>
        </div>
    </div>
    <div id="content" class="fixed">
<%--$////   CONTENT   /////////////////////////////////////////////////////////////////////////////////////////////////////////// -->--%>