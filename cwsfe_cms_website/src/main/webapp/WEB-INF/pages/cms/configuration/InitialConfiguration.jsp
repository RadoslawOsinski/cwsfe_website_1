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

    <div class="box">
        <div class="inner">
            <div class="titlebar"><span class="w-icon"><spring:message code="CmsConfiguration"/></span></div>
            <c:if test="${errors != null}">
                <div id="formValidation" class="alert-small">
                    <span class="close">${errors}</span>
                </div>
            </c:if>
            <spring:url value="/CWSFE_CMS/configuration/addAdminUser" var="addAdminUserUrl" htmlEscape="true"/>
            <form class="fixed" method="post" action="${addAdminUserUrl}" autocomplete="off"
                  id="addBlogPostImageForm" enctype="multipart/form-data">
                <div class="contents">
                    <div class="row">
                        <label for="username"><spring:message code="Username"/></label>

                        <div class="field-box"><input type="text" name="username" id="username" class="w-icon medium"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="contents">
                    <div class="row">
                        <label for="passwordHash"><spring:message code="Password"/></label>

                        <div class="field-box"><input type="password" name="passwordHash" id="passwordHash"
                                                      class="w-icon medium"/></div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Add"/>"/>
                    <input type="reset" value="Reset">
                </div>
            </form>
        </div>
    </div>

    <div id="login_footer">
    </div>
</section>

</body>
</html>
