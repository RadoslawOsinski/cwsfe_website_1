<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/WEB-INF/pages/layout/Header.jsp"%>

<a href="${mainUrl}">
    <c:choose>
        <c:when test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/DontPanic_PL.png" alt="don't panic image" width="800" height="504"/>
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/DontPanic_EN.png" alt="don't panic image" width="800" height="504"/>
        </c:otherwise>
    </c:choose>
</a>

<%@include file="/WEB-INF/pages/layout/Footer.jsp"%>