<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/portfolio/photodune-1236907-folders-on-white-background-s.jpg"
         width="880"
         height="200" alt="portfolio image"/>

    <div id="page-header-title">Portfolio - <spring:message code="LatestWork"/></div>
</div>

<input type="hidden" id="localeLanguage" value="${localeLanguage}">
<input type="hidden" id="currentPage" value="${currentPage}" data-bind="value: currentPage"/>
<input type="hidden" id="newsFolder" value="${newsFolder}"/>

<div class="fixed">
    <div class="col580">
        <ul id="portfolio-filter">
        </ul>
    </div>
    <nav>
        <div class="col280 last">
			<span class="pages">
                <a id="prevPortfolioPageLink" class="nextprev" title="Go to Prev Page"
                   data-bind="visible: isPreviewLinkVisible">« <spring:message code="Previous"/></a>
                <a id="nextPortfolioPageLink" class="nextprev" title="Go to Next Page"
                   data-bind="visible: isNextLinkVisible"><spring:message code="Next"/> »</a>
			</span>
            &nbsp;
        </div>
    </nav>
    <br>

</div>

<div class="hr"></div>
<p>
    <spring:message code="DueToTheRapidlyGrowingNumberOfSuccessfulProjects..."/>
</p>

<p>
    <spring:message code="AtTheSameTimeTheFollowingPortfolioWillMobilize..."/>
</p>

<div class="hr"></div>

<div class="portfolio-overview">

    <div id="noProjectsMessage" class="centered" style="display: none;">
        <div class="noticemsg"><h1><spring:message code="NoProjects"/></h1></div>
    </div>

    <ul id="newsThumbnails" class="fixed">
    </ul>

</div>

<%@include file="/pages/layout/Footer.jsp" %>