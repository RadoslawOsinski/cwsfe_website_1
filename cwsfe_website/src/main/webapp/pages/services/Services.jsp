<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/pages/layout/Header.jsp" %>

<%--@elvariable id="localeLanguage" type="java.lang.String"--%>
<input type="hidden" id="localeLanguage" value="${localeLanguage}">

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG" width="880" height="200" alt="fiber optics with earth image"/>
    <div id="page-header-title"><spring:message code="ServicesOverview"/></div>
</div>

<div class="fixed">
    <div class="col580">
        <h3 class="last" id="newsTitle"></h3>
    </div>
    <div class="col280 last">
        <ul class="pagination fixed">
            <spring:url value="/services" var="servicesUrl" htmlEscape="true"/>
            <li class="first"><a href="${servicesUrl}"><spring:message code="BackToServicePage"/></a></li>
        </ul>
    </div>
</div>

<div class="hr"></div>

<div class="fixed" id="newsDescription">
</div>

<%@include file="/pages/layout/Footer.jsp" %>