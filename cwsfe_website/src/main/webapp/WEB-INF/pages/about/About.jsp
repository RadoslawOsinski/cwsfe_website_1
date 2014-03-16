<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/WEB-INF/pages/layout/Header.jsp"%>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/about/photodune-2981403-computer-keyboard-and-social-media-images-s_880x200.jpg" width="880" height="200" alt="hands on keyboard image"/>
    <div id="page-header-title"><spring:message code="AboutCompany"/></div>
</div>

<div class="fixed">
    <div class="col580">
        <p>
            <spring:message code="CWSFEIsACompanyThat..."/>
        </p>

        <div class="hr"></div>
        <div class="fixed">
            <div class="col280">
                <h5><spring:message code="Motto"/></h5><br />
                <p>
                    <spring:message code="MottoOfTheCompanyIs..."/>
                </p>
            </div>
            <div class="col280 last">
                <%@include file="ClientOpinionsWithHeader.jsp"%>
            </div>
        </div>
    </div>

    <div class="col280 last">
        <h5><spring:message code="WhyCWSFE"/>?</h5><br />
        <p><spring:message code="ThereAreSeveralReasons..."/></p>
        <ul class="checklist">
            <li><spring:message code="smallSizeCompanyWithADesireToWork"/></li>
            <li><spring:message code="hasUniqueExperienceInTheConstructionOf..."/></li>
            <li><spring:message code="jPALIOPlatformKnowledge"/></li>
            <li><spring:message code="companyLocationNearCiechanowInMazovia..."/></li>
            <li><spring:message code="workPerformedRemotely..."/></li>
            <li><spring:message code="competitivePricesOfProductsAndServices..."/></li>
        </ul>
    </div>

</div>

<%@include file="/WEB-INF/pages/layout/Footer.jsp"%>