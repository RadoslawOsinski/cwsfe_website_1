<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG" width="880" height="200" alt="products image"/>
    <div id="page-header-title">${cmsNewsI18nContent.newsTitle}</div>
</div>

<div class="fixed">
    <div class="col580">
        <h3 class="last">${cmsNewsI18nContent.newsTitle}</h3>
    </div>
    <div class="col280 last">
        <ul class="pagination fixed">
            <li class="first">
                <c:choose>
                    <c:when test="${previousNews == null}">
                        &#8249; <spring:message code="Previous"/>
                    </c:when>
                    <c2:otherwise>
                        <spring:url value="/products/singleNews/${previousNews[0]}/${previousNews[1]}" var="singleNewsUrl" htmlEscape="true"/>
                        <a href="${singleNewsUrl}" class="view">
                            &#8249; <spring:message code="Previous"/>
                        </a>
                    </c2:otherwise>
                </c:choose>
            </li>
            <spring:url value="/products" var="productsListUrl" htmlEscape="true"/>
            <li><a href="${productsListUrl}"><img src="${pageContext.request.contextPath}/resources-cwsfe/img/products/products-icon.png" height="16" width="16" alt="products list icon"/></a></li>
            <li class="last">
                <c:choose>
                    <c:when test="${nextNews == null}">
                        <spring:message code="Next"/> &#8250;
                    </c:when>
                    <c2:otherwise>
                        <spring:url value="/products/singleNews/${nextNews[0]}/${nextNews[1]}" var="singleNewsUrl" htmlEscape="true"/>
                        <a href="${singleNewsUrl}" class="view">
                            <spring:message code="Next"/> &#8250;
                        </a>
                    </c2:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
</div>

<div class="hr"></div>

<div class="fixed">
    ${cmsNewsI18nContent.newsDescription}
</div>

<%@include file="/pages/layout/Footer.jsp" %>