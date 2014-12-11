<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG"
         width="880" height="200" alt="products image"/>

    <div id="page-header-title"><spring:message code="Products"/></div>
</div>

<div class="fixed">
    <div class="col580">
        <ul id="products-filter">
            <c:forEach var="cmsFolder" items="${cmsNewsFolders}" varStatus="i">
                <li class="${i.index == 0 ? 'first' : ''} ${(newsFolderId == null && i.index == 0) || (newsFolderId != null && newsFolderId == cmsFolder.id) ? 'current' : ''}">
                    <c:if test="${cmsFolder.folderName == 'AllProducts'}">
                        <spring:url value="/products" var="productsWithFolder" htmlEscape="true">
                            <spring:param name="newsFolderId" value="${cmsFolder.id}"/>
                            <spring:param name="currentPage" value="0"/>
                        </spring:url>
                        <a href="${productsWithFolder}">${i18nCmsNewsFolders[cmsFolder.folderName]}</a>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
    <nav>
        <div class="col280 last">
			<span class="pages">
                <c:choose>
                    <c:when test="${currentPage == 0}">
                        <span class="nextprev">« <spring:message code="Previous"/></span>
                    </c:when>
                    <c2:otherwise>
                        <spring:url value="/products" var="listProductsUrl" htmlEscape="true">
                            <spring:param name="newsFolderId" value="${newsFolderId}"/>
                            <spring:param name="currentPage" value="${currentPage - 1}"/>
                        </spring:url>
                        <a href="${listProductsUrl}" class="nextprev" title="Go to Prev Page">« <spring:message
                                code="Previous"/></a>
                    </c2:otherwise>
                </c:choose>
                <c:forEach var="i" begin="0" end="${numberOfPages - 1}" step="1">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <span class="current">${i + 1}</span>
                        </c:when>
                        <c2:otherwise>
                            <spring:url value="/products" var="listProductsUrl" htmlEscape="true">
                                <spring:param name="newsFolderId" value="${newsFolderId}"/>
                                <spring:param name="currentPage" value="${i}"/>
                            </spring:url>
                            <a href="${listProductsUrl}" class="nextprev" title="Go to page ${i + 1}">${i + 1}</a>
                        </c2:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${currentPage >= numberOfPages - 1}">
                        <span class="nextprev"><spring:message code="Next"/> »</span>
                    </c:when>
                    <c2:otherwise>
                        <spring:url value="/products" var="listProductsUrl" htmlEscape="true">
                            <spring:param name="newsFolderId" value="${newsFolderId}"/>
                            <spring:param name="currentPage" value="${currentPage + 1}"/>
                        </spring:url>
                        <a href="${listProductsUrl}" class="nextprev" title="Go to Prev Page"><spring:message
                                code="Next"/> »</a>
                    </c2:otherwise>
                </c:choose>
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
    <ul class="fixed">

        <c:choose>
            <c:when test="${cmsNewsList == null || cmsNewsList.isEmpty()}">
                <div class="centered">
                    <div class="noticemsg"><h1><spring:message code="NoProducts"/></h1></div>
                </div>
            </c:when>
            <c2:otherwise>
                <c:forEach var="cmsNews" items="${cmsNewsList}" varStatus="j">
                    <li <c:if test="${(j.count % 3) == 0}">class="last"</c:if>>
                        <div class="products-item-preview">
                            <c:if test="${cmsNews.getThumbnailImage() != null}">
                                <img src="${pageContext.request.contextPath}/newsImages/?imageId=${cmsNews.getThumbnailImage().id}"
                                     height="${cmsNews.getThumbnailImage().height}"
                                     width="${cmsNews.getThumbnailImage().width}"
                                     alt="${cmsNews.getThumbnailImage().title} image"/>
                                <c:if test="${cmsNews.cmsNewsImages != null}">
                                    <div class="preview-options">
                                        <a href="${pageContext.request.contextPath}/newsImages/?imageId=${cmsNews.getThumbnailImage().id}"
                                           class="lightbox tip2" title=""
                                           data-gal="prettyPhoto[gallery_${cmsNews.getId()}]"><spring:message
                                                code="ViewLargeVersion"/></a>

                                        <div style="display: none;">
                                            <c:forEach var="largeImageInfo" items="${cmsNews.cmsNewsImages}">
                                                <a href="${pageContext.request.contextPath}/newsImages/?imageId=${largeImageInfo.id}"
                                                   class="lightbox tip2" title=""
                                                   data-gal="prettyPhoto[gallery_${cmsNews.getId()}]"><spring:message
                                                        code="ViewLargeVersion"/></a>
                                            </c:forEach>
                                        </div>
                                        <spring:url
                                                value="/products/singleNews/${cmsNews.getId()}/${cmsNewsI18nContents.get(j.count - 1).getId()}"
                                                var="singleNewsUrl" htmlEscape="true"/>
                                        <a href="${singleNewsUrl}" class="view">
                                            &#8212; <spring:message code="ViewProject"/>
                                        </a>
                                    </div>
                                </c:if>
                            </c:if>
                        </div>
                        <h5>
                            <a href="${singleNewsUrl}">
                                    ${cmsNewsI18nContents.get(j.count - 1).getNewsTitle()}
                            </a>
                        </h5>

                        <p class="products_paragraph">${cmsNewsI18nContents.get(j.count - 1).getNewsShortcut()}</p>
                    </li>
                </c:forEach>
            </c2:otherwise>
        </c:choose>
    </ul>

</div>

<%@include file="/pages/layout/Footer.jsp" %>