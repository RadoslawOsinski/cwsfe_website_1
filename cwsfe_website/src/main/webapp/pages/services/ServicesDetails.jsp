<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG" width="880" height="200" alt="services image"/>
    <div id="page-header-title"><spring:message code="ServicesDetails"/></div>
</div>

<div class="fixed">
    <div class="col580">
        <h3 class="last">${cmsNewsI18nContent.newsTitle}</h3>
    </div>
    <div class="col280 last">
        <ul class="pagination fixed">
            <spring:url value="/services" var="servicesUrl" htmlEscape="true"/>
            <li class="first"><a href="${servicesUrl}"><spring:message code="BackToServicePage"/></a></li>
        </ul>
    </div>
</div>

<div class="hr"></div>

<div class="fixed">
    ${cmsNewsI18nContent.newsDescription}
</div>

<%@include file="/pages/layout/Footer.jsp" %>