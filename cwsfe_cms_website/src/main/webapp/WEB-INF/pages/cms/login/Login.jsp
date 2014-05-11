<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <meta name="description" content="CWSFE CMS- Complete Working Solution For Everyone CMS"/>
    <meta name="author" content="Radosław Osiński">
    <meta name="robots" content="none"/>
    <%--none=noindex, nofollow--%>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"/>
    <title>CWSFE CMS</title>

    <link href="${pageContext.request.contextPath}/resources-cwsfe-cms/favicon.png" rel="shortcut icon"
          type="image/x-icon"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources-cwsfe-cms/css/LoginPage-min.css"
          type="text/css"/>

    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jquery-2.0.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/jqueryui/jquery-ui-1.10.3.custom.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources-cwsfe-cms/js/Login.js"></script>

</head>

<body id="index" class="home">
<div id="loading-block"></div>

<section id="login-container">
    <div id="login_header"></div>

    <spring:url value="/j_spring_security_check" var="loginCheckUrl" htmlEscape="true"/>
    <form id="login_form" action="${loginCheckUrl}" method='POST'>
        <div id="login_wrapper">
            <div class="login_wrapper_container">
                <div class="float_center_box">

                    <div class="one-half logo-box">
                        <%--<img src="${pageContext.request.contextPath}/resources-cwsfe-cms/CWSFE_logo.png" alt="Caffeine">--%>
                    </div>

                    <div class="clear"></div>
                    <div class="box">
                        <div class="inner">

                            <div class="contents">
                                <div class="one-half username-half">
                                    <label for="username">Username</label>

                                    <div class="field-box">
                                        <input type="text" id="username" name="j_username" class="w-icon"></div>
                                    <div class="clear"></div>
                                </div>
                                <div class="one-half password-half">
                                    <label for="password">Password</label>

                                    <div class="field-box">
                                        <input type="password" id="password" name="j_password" class="w-icon">
                                    </div>
                                    <div class="clear"></div>
                                </div>

                                <%--todo forgot your password--%>
                                <div class="clear"></div>
                                <div class="line-separator"></div>

                                <div class="one-half">
                                    &nbsp;
                                    <%--<a href="#">Forgot your password?</a>--%>
                                </div>
                                <div class="one-half right">
                                    <input type="submit" value="Login" class="button blue tiny">
                                </div>

                                <div class="clear"></div>
                                <a href="${mainSiteUrl}"><spring:message code="BackToMainWebsite" text="BackToMainWebsite"/></a>
                            </div>
                            <div class="clear"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    LOGIN page TEST!
    <div id="login_footer">
    </div>
</section>

</body>
</html>
