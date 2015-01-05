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
            <%--<li class="first">--%>
            <%--<c:choose>--%>
            <%--<c:when test="${previousNews == null}">--%>
            <%--&#8249; <spring:message code="Previous"/>--%>
            <%--</c:when>--%>
            <%--<c2:otherwise>--%>
            <%--<spring:url value="/portfolio/singleNews/${previousNews[0]}/${previousNews[1]}" var="singleNewsUrl" htmlEscape="true"/>--%>
            <%--<a href="${singleNewsUrl}" class="view">--%>
            <%--&#8249; <spring:message code="Previous"/>--%>
            <%--</a>--%>
            <%--</c2:otherwise>--%>
            <%--</c:choose>--%>
            <%--</li>--%>
            <spring:url value="/portfolio" var="portfolioListUrl" htmlEscape="true"/>
            <li><a href="${portfolioListUrl}"><img src="${pageContext.request.contextPath}/resources-cwsfe/img/portfolio/portfolio-icon.png" height="16" width="16" alt="portfolio list icon"/></a></li>
            <%--<li class="last">--%>
            <%--<c:choose>--%>
            <%--<c:when test="${nextNews == null}">--%>
            <%--<spring:message code="Next"/> &#8250;--%>
            <%--</c:when>--%>
            <%--<c2:otherwise>--%>
            <%--<spring:url value="/portfolio/singleNews/${nextNews[0]}/${nextNews[1]}" var="singleNewsUrl" htmlEscape="true"/>--%>
            <%--<a href="${singleNewsUrl}" class="view">--%>
            <%--<spring:message code="Next"/> &#8250;--%>
            <%--</a>--%>
            <%--</c2:otherwise>--%>
            <%--</c:choose>--%>
            <%--</li>--%>
        </ul>
    </div>
</div>

<div class="hr"></div>

<div id="newsDescription" class="fixed">
</div>

<%@include file="/pages/layout/Footer.jsp" %>