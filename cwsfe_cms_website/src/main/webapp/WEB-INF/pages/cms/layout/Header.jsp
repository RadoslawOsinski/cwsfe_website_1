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
    <meta name="author" content="Radosław Osiński">
    <meta name="robots" content="all"/>
    <title>CWSFE CMS</title>

    <c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">
        <link rel="canonical" href="http://eu.com.cwsfem.cwsfe.pl"/>
    </c:if>
    <c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'en'}">
        <link rel="canonical" href="http://eu.com.cwsfem.cwsfe.eu"/>
    </c:if>

    <link href="${pageContext.request.contextPath}/resources-cwsfe-cms/favicon.png" rel="shortcut icon" type="image/x-icon"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/font_awesome/font-awesome-min.css" type="text/css" media="screen"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/ui-lightness/jquery-ui-1.10.3.custom-min.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/icons-min.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/forms-min.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/tables-min.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/ui-min.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/style-min.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/tabs-min.css" type="text/css"/>

    <c:forEach var="cssUrl" items="${additionalCssCode}">
        <link href="${cssUrl}" rel="stylesheet" type="text/css" media="all"/>
    </c:forEach>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jqueryui/jquery-ui-1.10.3.custom.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jquery.dataTables.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/shared.js"></script>

    <c:forEach var="jsUrl" items="${additionalJavaScriptCode}">
        <script type="text/javascript" src="${jsUrl}"></script>
    </c:forEach>

    <c:forEach var="asyncJsUrl" items="${additionalAsyncJavaScriptCode}">
        <script type="text/javascript" async src="${asyncJsUrl}"></script>
    </c:forEach>

</head>
<body id="index" class="home">
<div id="loading-block"></div>
<!-- Loading block -->

<div id="container">

    <!-- Header -->
    <header id="header">
        <spring:url value="/CWSFE_CMS/Main" var="mainUrl" htmlEscape="true"/>
        <figure id="logo"><a href="${mainUrl}" class="logo" tabindex="-1"></a></figure>
        <section id="general-options">
            <%--<a href="#" class="settings tipsy-header" title="Settings"></a>--%>
            <%--<a href="#" class="users tipsy-header" title="Users"></a>--%>
            <%--<a href="#" class="messages tipsy-header" title="Messages"></a>--%>
        </section>
        <section id="userinfo">
            <span class="welcome">Welcome <strong>${username}</strong>. It's nice having you back</span>
            <%--<span class="last-login">Last login on June 1st at 11:24hs</span>--%>
            <div class="profile">
                <div class="links">
                    <%--<a href="#">Profile</a>--%>
                    <%--<a href="#">Inbox</a>--%>
                    <spring:url value="/CWSFE_CMS_logout" var="logoutUrl" htmlEscape="true"/>
                    <a href="${logoutUrl}" class="logout" tabindex="-1"><spring:message code="Logout"/></a>
                </div>
                <%--<img src="img/profile-pict.jpg" alt="John Doe">--%>
            </div>
        </section>

        <%--<section id="responsive-nav">--%>
            <%--<select id="nav_select">--%>
                <%--<spring:url value="/authors" var="authorsUrl" htmlEscape="true"/>--%>
                <%--<option value=""><spring:message code="Navigation"/></option>--%>
                <%--<option value="${dashboardUrl}" selected="selected">Dashboard</option>--%>
                <%--<option value="${authorsUrl}"><spring:message code="Authors"/></option>--%>

                <%--<option value=""><spring:message code="NewNews"/></option>--%>
                <%--<option value=""><spring:message code="CMS"/></option>--%>
                <%--<option value=""><spring:message code="Searching"/></option>--%>
                <%--<option value=""><spring:message code="NewsTypes"/></option>--%>
                <%--<option value=""><spring:message code="Folders"/></option>--%>
                <%--<option value=""><spring:message code="Blog"/></option>--%>
                <%--<option value=""><spring:message code="NewPost"/></option>--%>
                <%--<option value=""><spring:message code="Searching"/></option>--%>
                <%--<option value=""><spring:message code="Keywords"/></option>--%>
                <%--<option value=""><spring:message code="Newsletter"/></option>--%>
                <%--<option value=""><spring:message code="NewNews"/></option>--%>
            <%--</select>--%>
        <%--</section>--%>
    </header>

    <div class="clear"></div>

    <nav id="sidebar">
        <div class="sidebar-top"></div>

        <h3>CWSFE CMS</h3>

        <spring:url value="/CWSFE_CMS/folders" var="foldersUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/news" var="newsUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/newsTypes" var="newsTypesUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/blogPosts" var="blogUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/blogKeywords" var="blogKeywordsUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/newsletterMailGroups" var="newsletterMailGroupsManagementUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/newsletterTemplates" var="newsletterTemplatesManagementUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/newsletterMails" var="newsletterMailsManagementUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/languages" var="languagesUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/cmsTextI18n" var="cmsTextI18nUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/cmsTextI18nCategories" var="cmsTextI18nCategoriesUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/authors" var="authorsUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/users" var="usersUrl" htmlEscape="true"/>
        <spring:url value="/CWSFE_CMS/roles" var="rolesUrl" htmlEscape="true"/>

        <ul class="nav">
            <li<c:if test="${pageContext.request.requestURI.contains('cms/main/Main')}"> class="active"</c:if>><a href="${mainUrl}" tabindex="-1"><spring:message code="MainWall"/></a></li>
            <li><a href="#" tabindex="-1">CMS</a>
                <ul class="submenu">
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/news/')}"> class="active"</c:if>><a href="${newsUrl}" tabindex="-1"><spring:message code="CmsNewsManagement"/></a></li>
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/newsTypes/NewsTypes')}"> class="active"</c:if>><a href="${newsTypesUrl}" tabindex="-1"><spring:message code="NewsTypes"/></a></li>
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/folders/Folders')}"> class="active"</c:if>><a href="${foldersUrl}" tabindex="-1"><spring:message code="Folders"/></a></li>
                </ul>
            </li>
            <li><a href="#" tabindex="-1"><spring:message code="Blog"/></a>
                <ul class="submenu">
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/blog/')}"> class="active"</c:if>><a href="${blogUrl}" tabindex="-1"><spring:message code="BlogPostManagement"/></a></li>
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/blogkeywords/BlogKeywords')}"> class="active"</c:if>><a href="${blogKeywordsUrl}" tabindex="-1"><spring:message code="Keywords"/></a></li>
                </ul>
            </li>
            <li><a href="#" tabindex="-1"><spring:message code="Newsletter"/></a>
                <ul class="submenu">
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/newsletterMailGroups/NewsletterMailGroups')}"> class="active"</c:if>><a href="${newsletterMailGroupsManagementUrl}" tabindex="-1"><spring:message code="NewsletterMailGroupsManagement"/></a></li>
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/newsletterTemplates/NewsletterTemplates')}"> class="active"</c:if>><a href="${newsletterTemplatesManagementUrl}" tabindex="-1"><spring:message code="NewsletterTemplatesManagement"/></a></li>
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/newsletterMails/NewsletterMails')}"> class="active"</c:if>><a href="${newsletterMailsManagementUrl}" tabindex="-1"><spring:message code="NewsletterMailsManagement"/></a></li>
                </ul>
            </li>
            <li<c:if test="${pageContext.request.requestURI.contains('cms/languages/Languages')}"> class="active"</c:if>><a href="${languagesUrl}" tabindex="-1"><spring:message code="Languages"/></a></li>
            <li><a href="#" tabindex="-1"><spring:message code="Translations"/></a>
                <ul class="submenu">
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/textI18n/TextI18n')}"> class="active"</c:if>><a href="${cmsTextI18nUrl}" tabindex="-1"><spring:message code="TextTranslations"/></a></li>
                    <li<c:if test="${pageContext.request.requestURI.contains('cms/textI18nCategories/TextI18nCategories')}"> class="active"</c:if>><a href="${cmsTextI18nCategoriesUrl}" tabindex="-1"><spring:message code="TranslationCategories"/></a></li>
                </ul>
            </li>
            <li<c:if test="${pageContext.request.requestURI.contains('cms/authors/Authors')}"> class="active"</c:if>><a href="${authorsUrl}" tabindex="-1"><spring:message code="Authors"/></a></li>
            <li<c:if test="${pageContext.request.requestURI.contains('cms/users/Users')}"> class="active"</c:if>><a href="${usersUrl}" tabindex="-1"><spring:message code="Users"/></a></li>
            <li<c:if test="${pageContext.request.requestURI.contains('cms/roles/Roles')}"> class="active"</c:if>><a href="${rolesUrl}" tabindex="-1"><spring:message code="Roles"/></a></li>
        </ul>
        <div class="blocks-separator"></div>
    </nav>

    <%--<div id="jgrowl" class="bottom-right"></div>--%>

    <!-- Playground -->
    <section id="playground">

        <div class="three-fourths breadcrumb">
            <span><a href="${mainUrl}" class="icon awesome home" tabindex="-1"></a></span>
            <c:forEach var="breadcrumb" items="${breadcrumbs}">
                <span class="middle"></span>
                <span>${breadcrumb}</span>
            </c:forEach>
            <span class="end"></span>
        </div>

        <%--<div class="one-fourth searchbar">--%>
            <%--<div class="box no-bg">--%>
                <%--<div class="search-box closed"><input type="text"/><span class="icon awesome search"></span></div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <div class="clear"></div>