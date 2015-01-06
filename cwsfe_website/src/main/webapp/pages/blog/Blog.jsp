<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG" width="880" height="200" alt="services image"/>
    <div id="page-header-title"><spring:message code="Blog"/></div>
</div>

<input type="hidden" id="localeLanguage" value="${localeLanguage}">
<%--<input type="hidden" id="currentPage" value="${currentPage}" data-bind="value: currentPage"/>--%>
<%--<input type="hidden" id="newsFolder" value="${newsFolder}"/>--%>

<div class="fixed">

    <div class="col580">
        <c:choose>
            <c:when test="${blogPosts == null || blogPosts.isEmpty()}">
                <div class="centered"><div class="noticemsg"><h1><spring:message code="NoPublishedPosts"/></h1></div></div>
            </c:when>
            <c2:otherwise>
                <c:forEach var="i" begin="0" end="${blogPosts.size() - 1}" step="1">
                    <section>
                        <div class="blog-post">
                            <div class="blog-post-date">
                                <fmt:formatDate value="${blogPosts.get(i).postCreationDate}" pattern="dd"/>
                                <span>
                                    <c:choose>
                                        <c:when test="${blogPosts.get(i).postCreationDate.getMonth() + 1 < 10}">
                                            <spring:message code="Month0${blogPosts.get(i).postCreationDate.getMonth() + 1}"/>
                                        </c:when>
                                        <c2:otherwise>
                                            <spring:message code="Month${blogPosts.get(i).postCreationDate.getMonth() + 1}"/>
                                        </c2:otherwise>
                                    </c:choose>
                                    <fmt:formatDate value="${blogPosts.get(i).postCreationDate}" pattern="yyyy"/>
                                </span>
                            </div>
                            <spring:url value="/blog/singlePost/${blogPosts.get(i).getId()}/${blogPostI18nContents.get(i).getId()}" var="blogPostUrl" htmlEscape="true"/>
                            <a href="${blogPostUrl}">
                                <h3 class="blog-post-title">${blogPostI18nContents.get(i).getPostTitle()}</h3>
                            </a>
                            <ul class="blog-post-info fixed">
                                <c:choose>
                                    <c:when test="${blogPosts.get(i).getCmsAuthor().googlePlusAuthorLink == null || blogPosts.get(i).getCmsAuthor().googlePlusAuthorLink.trim().isEmpty()}">
                                        <li class="author">${blogPosts.get(i).getCmsAuthor().getLastName()} ${blogPosts.get(i).getCmsAuthor().getFirstName()}</li>
                                    </c:when>
                                    <c2:otherwise>
                                        <li class="author"><a rel="author" href="${blogPosts.get(i).getCmsAuthor().googlePlusAuthorLink}" target="_blank">${blogPosts.get(i).getCmsAuthor().getLastName()} ${blogPosts.get(i).getCmsAuthor().getFirstName()}</a></li>
                                    </c2:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${blogPosts.get(i).blogKeywords == null || blogPosts.get(i).blogKeywords.isEmpty()}">
                                    </c:when>
                                    <c:otherwise>
                                        <li class="categories">
                                            <c:forEach var="j" begin="0" end="${blogPosts.get(i).blogKeywords.size() - 1}" step="1">
                                                <spring:url value="/blog/category/${blogPosts.get(i).blogKeywords.get(j).id}" var="categoryUrl" htmlEscape="true"/>
                                                <a href="${categoryUrl}">
                                                    <%--$lang.getTranslation($@keyword.getKeywordName(), "blog_keyword")--%>
                                                    ${blogPosts.get(i).blogKeywords.get(j).keywordName}
                                                </a>
                                                <c:if test="${j < blogPosts.get(i).blogKeywords.size() - 1}">,</c:if>
                                            </c:forEach>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <li class="comments">
                                    <a href="${blogPostUrl}#blogPostComments">
                                        ${blogPostI18nContents.get(i).getBlogPostComments().size()}
                                        <spring:message code="Comments"/>
                                    </a>
                                </li>
                            </ul>
                            ${blogPostI18nContents.get(i).getPostShortcut()}
                            <div class="fixed">
                                <div class="blog-post-readmore">
                                    <a href="${blogPostUrl}">— <spring:message code="ReadMore"/></a>&nbsp;&nbsp;&nbsp;
                                </div>
                            </div>
                            <div class="hr"></div>
                        </div>
                    </section>
                </c:forEach>
            </c2:otherwise>
        </c:choose>
        <nav>
            <div class="pages">
                <c:choose>
                    <c:when test="${currentPage == 0}">
                        <span class="nextprev">« <spring:message code="Previous"/></span>
                    </c:when>
                    <c2:otherwise>
                        <spring:url value="/blog/list/" var="blogPostsListUrl" htmlEscape="true">
                            <spring:param name="currentPage" value="${currentPage - 1}"/>
                            <spring:param name="categoryId" value="${categoryId}"/>
                            <spring:param name="searchText" value="${searchText}"/>
                            <spring:param name="archiveYear" value="${archiveYear}"/>
                            <spring:param name="archiveMonth" value="${archiveMonth}"/>
                        </spring:url>
                        <a href="${blogPostsListUrl}" class="nextprev" title="Go to Prev Page">« <spring:message code="Previous"/></a>
                    </c2:otherwise>
                </c:choose>
                <c:forEach var="i" begin="0" end="${numberOfPages - 1}" step="1">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <span class="current">${i + 1}</span>
                        </c:when>
                        <c:otherwise>
                            <spring:url value="/blog/list/" var="blogPostsListUrl" htmlEscape="true">
                                <spring:param name="currentPage" value="${i}"/>
                                <spring:param name="categoryId" value="${categoryId}"/>
                                <spring:param name="searchText" value="${searchText}"/>
                                <spring:param name="archiveYear" value="${archiveYear}"/>
                                <spring:param name="archiveMonth" value="${archiveMonth}"/>
                            </spring:url>
                            <a href="${blogPostsListUrl}" class="nextprev" title="Go to page ${i + 1}">${i + 1}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${currentPage >= numberOfPages - 1}">
                        <span class="nextprev"><spring:message code="Next"/> »</span>
                    </c:when>
                    <c:otherwise>
                        <spring:url value="/blog/list/" var="blogPostsListUrl" htmlEscape="true">
                            <spring:param name="currentPage" value="${currentPage + 1}"/>
                            <spring:param name="categoryId" value="${categoryId}"/>
                            <spring:param name="searchText" value="${searchText}"/>
                            <spring:param name="archiveYear" value="${archiveYear}"/>
                            <spring:param name="archiveMonth" value="${archiveMonth}"/>
                        </spring:url>
                        <a href="${blogPostsListUrl}" class="nextprev" title="Go to Next Page"><spring:message code="Next"/> »</a>
                    </c:otherwise>
                </c:choose>
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