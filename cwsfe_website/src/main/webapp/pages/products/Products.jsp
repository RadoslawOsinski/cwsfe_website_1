<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG"
         width="880" height="200" alt="products image"/>

    <div id="page-header-title"><spring:message code="Products"/></div>
</div>


<input type="hidden" id="localeLanguage" value="${localeLanguage}">
<input type="hidden" id="currentPage" value="${currentPage}" data-bind="value: currentPage"/>
<input type="hidden" id="newsFolder" value="${newsFolder}"/>

<div class="fixed">
    <div class="col580">
        <ul id="products-filter">
        </ul>
    </div>
    <nav>
        <div class="col280 last">
			<span class="pages">
                <a id="prevProductsPageLink" class="nextprev" title="Go to Prev Page"
                   data-bind="visible: isPreviewLinkVisible">« <spring:message code="Previous"/></a>
                <a id="nextProductsPageLink" class="nextprev" title="Go to Next Page"
                   data-bind="visible: isNextLinkVisible"><spring:message code="Next"/> »</a>
			</span>
            &nbsp;
        </div>
    </nav>
    <br>

</div>

<div class="hr"></div>
<p>
    <spring:message code="TheseAreProductsCreatedByCWSFE"/>
</p>

<div class="hr"></div>

<div class="products-overview">

    <div id="noProductsMessage" class="centered" style="display: none;">
        <div class="noticemsg"><h1><spring:message code="NoProducts"/></h1></div>
    </div>

    <ul id="newsThumbnails" class="fixed">
    </ul>

</div>

<%@include file="/pages/layout/Footer.jsp" %>