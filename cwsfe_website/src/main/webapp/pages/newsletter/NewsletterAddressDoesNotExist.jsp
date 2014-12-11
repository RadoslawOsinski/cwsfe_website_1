<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/pages/layout/Header.jsp" %>

<div class="fixed">
    <div class="col580">
        <p>
            <spring:message code="NewsletterAddressDoesNotExist"/>
        </p>
    </div>
    <div class="col280 last">
        <spring:url value="${pageContext.request.contextPath}/" var="mainUrl" htmlEscape="true"/>
        <a href="${mainUrl}"><spring:message code="ReturnToMainPage"/></a>
    </div>

</div>

<%@include file="/pages/layout/Footer.jsp" %>