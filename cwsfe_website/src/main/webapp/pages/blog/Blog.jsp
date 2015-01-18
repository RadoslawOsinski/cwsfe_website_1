<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG" width="880" height="200" alt="services image"/>
    <div id="page-header-title"><spring:message code="Blog"/></div>
</div>

<input type="hidden" id="localeLanguage" value="${localeLanguage}">
<input type="hidden" id="currentPage" value="${currentPage}" data-bind="value: currentPage"/>
<input type="hidden" id="categoryId" value="${categoryId}" data-bind="value: categoryId"/>
<input type="hidden" id="readMoreI18n" value="<spring:message code="ReadMore"/>"/>

<div class="fixed">

    <div class="col580">

        <div id="noPostsMessage" class="centered" style="display: none;">
            <div class="noticemsg"><h1><spring:message code="NoPublishedPosts"/></h1></div>
        </div>
        <div id="postsList">
        </div>

        <nav>
            <div class="pages">
                <a id="prevPageLink" class="nextprev" title="Go to Prev Page"
                   data-bind="visible: isPreviewLinkVisible">« <spring:message code="Previous"/></a>
                <a id="nextPageLink" class="nextprev" title="Go to Next Page"
                   data-bind="visible: isNextLinkVisible"><spring:message code="Next"/> »</a>
                &nbsp;
            </div>
        </nav>
        &nbsp;
    </div>

    <div class="col280 last">

        <h5><spring:message code="Categories"/></h5><br/>
        <ul id="keywordsList" class="side-nav">
        </ul>

    </div>

</div>

<%@include file="/pages/layout/Footer.jsp" %>
