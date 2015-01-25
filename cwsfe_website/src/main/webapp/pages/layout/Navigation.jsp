<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c2" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col655">
    <ul id="dropdown-menu" class="fixed">
        <spring:url value="${pageContext.request.contextPath}/" var="mainUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/about" var="aboutUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/services" var="servicesUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/services/serviceDetails" var="serviceDetailsUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/services/serviceStages" var="serviceStagesUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/products" var="productsUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/portfolio" var="portfolioUrl" htmlEscape="true"/>
        <spring:url value="${pageContext.request.contextPath}/contact" var="contactUrl" htmlEscape="true"/>
        <li <c2:if test="${fn:endsWith(pageContext.request.requestURL, 'Main.jsp')}">class="current"</c2:if>>
            <a href="${mainUrl}"><spring:message code="Home"/></a>
        </li>
        <li <c2:if test="${fn:endsWith(pageContext.request.requestURL, 'About.jsp')}">class="current"</c2:if>>
            <a href="${aboutUrl}"><spring:message code="AboutCompany"/></a>
        </li>
        <li <c2:if test="${fn:endsWith(pageContext.request.requestURL, 'Services.jsp')}">class="current"</c2:if>>
            <a href="${servicesUrl}"><spring:message code="Services"/></a>
            <ul class="sub-menu">
                <li><a href="${servicesUrl}"><spring:message code="Services"/></a></li>
                <li><a href="${serviceDetailsUrl}"><spring:message code="ServicesDetails"/></a></li>
                <li><a href="${serviceStagesUrl}"><spring:message code="ServiceStages"/></a></li>
                <%--<li><a href=""><spring:message code="ProblemsAndSolutions"/></a></li>--%>
            </ul>
        </li>
        <li <c:if test="${fn:endsWith(pageContext.request.requestURL, 'Products.jsp')}">class="current"</c:if>>
            <a href="${productsUrl}"><spring:message code="Products"/></a>
        </li>
        <li <c2:if test="${fn:endsWith(pageContext.request.requestURL, 'Portfolio.jsp')}">class="current"</c2:if>>
            <a href="${portfolioUrl}"><spring:message code="Portfolio"/></a>
        </li>
        <li <c2:if test="${fn:endsWith(pageContext.request.requestURL, 'Contact.jsp')}">class="current"</c2:if>>
            <a href="${contactUrl}"><spring:message code="Contact"/></a>
        </li>
    </ul>
</div>
