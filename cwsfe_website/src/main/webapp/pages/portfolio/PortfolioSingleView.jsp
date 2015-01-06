<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<input type="hidden" id="cmsNewsId" value="${cmsNewsId}"/>
<input type="hidden" id="cmsNewsI18nContentsId" value="${cmsNewsI18nContentsId}"/>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/portfolio/photodune-1236907-folders-on-white-background-s.jpg" width="880" height="200" alt="portfolio image"/>

    <div id="page-header-title"></div>
</div>

<div class="fixed">
    <div class="col580">
        <h3 class="last" id="newsTitle"></h3>
    </div>
    <div class="col280 last">
        <ul class="pagination fixed">
            <spring:url value="/portfolio" var="portfolioListUrl" htmlEscape="true"/>
            <li><a href="${portfolioListUrl}"><img src="${pageContext.request.contextPath}/resources-cwsfe/img/portfolio/portfolio-icon.png" height="16" width="16" alt="portfolio list icon"/></a></li>
        </ul>
    </div>
</div>

<div class="hr"></div>

<div id="newsDescription" class="fixed">
</div>

<%@include file="/pages/layout/Footer.jsp" %>