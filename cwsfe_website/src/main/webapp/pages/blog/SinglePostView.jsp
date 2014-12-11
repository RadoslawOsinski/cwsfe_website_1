<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG" width="880" height="200" alt="services image"/>
    <div id="page-header-title"><spring:message code="Blog"/></div>
</div>

<div class="fixed">
    <div class="col580">

        <div class="blog-post">
            <div class="blog-post-date">
                <fmt:formatDate value="${blogPost.postCreationDate}" pattern="dd"/>
                <span>
                    <c:choose>
                        <c:when test="${blogPost.postCreationDate.getMonth() + 1 < 10}">
                            <spring:message code="Month0${blogPost.postCreationDate.getMonth() + 1}"/>
                        </c:when>
                        <c2:otherwise>
                            <spring:message code="Month${blogPost.postCreationDate.getMonth() + 1}"/>
                        </c2:otherwise>
                    </c:choose>
                    <fmt:formatDate value="${blogPost.postCreationDate}" pattern="yyyy"/>
                </span>
            </div>
            <h3 class="blog-post-title">${blogPostI18nContent.postTitle}</h3>
            <ul class="blog-post-info fixed">
                <c:choose>
                    <c:when test="${blogPost.getCmsAuthor().googlePlusAuthorLink == null || blogPost.getCmsAuthor().googlePlusAuthorLink.trim().isEmpty()}">
                        <li class="author">${blogPost.getCmsAuthor().getLastName()} ${blogPost.getCmsAuthor().getFirstName()}</li>
                    </c:when>
                    <c2:otherwise>
                        <li class="author"><a rel="author" href="${blogPost.getCmsAuthor().googlePlusAuthorLink}" target="_blank">${blogPost.getCmsAuthor().getLastName()} ${blogPost.getCmsAuthor().getFirstName()}</a></li>
                    </c2:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${blogPost.blogKeywords == null || blogPost.blogKeywords.isEmpty()}">
                    </c:when>
                    <c:otherwise>
                        <li class="categories">
                            <c:forEach var="j" begin="0" end="${blogPost.blogKeywords.size() - 1}" step="1">
                                <spring:url value="/blog/category/${blogPost.blogKeywords.get(j).id}" var="categoryUrl" htmlEscape="true"/>
                                <a href="${categoryUrl}">
                                        <%--$lang.getTranslation($@keyword.getKeywordName(), "blog_keyword")--%>
                                        ${blogPost.blogKeywords.get(j).keywordName}
                                </a>
                                <c:if test="${j < blogPost.blogKeywords.size() - 1}">,</c:if>
                            </c:forEach>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li class="comments">
                    <a href="#blogPostComments">
                        ${blogPostI18nContent.blogPostComments.size()} <spring:message code="Comments"/>
                    </a>
                </li>
            </ul>
            <article>
                ${blogPostI18nContent.postDescription}
            </article>
        </div>

        <div class="blog-navigation fixed">
            <spring:url value="/blog" var="blogUrl" htmlEscape="true"/>
            <a href="${blogUrl}"><spring:message code="OtherPosts"/></a>&nbsp;
        </div>
        &nbsp;

        <div class="hr"></div>
        <div class="fixed">
            <h5 class="blog-post-comments" id="blogPostComments">
                <strong><spring:message code="NumberOfComments"/>: ${blogPostI18nContent.getBlogPostComments().size()}</strong>
            </h5>
        </div>

        <c:forEach var="blogPostComment" items="${blogPostI18nContent.getBlogPostComments()}">
            <div class="blog-post-comment <c:if test="${blogPostComment.getParentCommentId() != null}">blog-post-comment-reply</c:if>">
                <img src="http://www.gravatar.com/avatar/${blogPostComment.getEmail()}?s72&d=identicon" class="img-align-left bordered" width="72" height="72" alt="user image">
                <p class="who">
                    <strong>${blogPostComment.getUsername()}</strong>
                    <span class="date">
                        <fmt:formatDate value="${blogPostComment.getCreated()}" pattern="dd"/>
                        <c:choose>
                            <c:when test="${blogPostComment.getCreated().getMonth() + 1 < 10}">
                                <spring:message code="Month0${blogPostComment.getCreated().getMonth() + 1}"/>
                            </c:when>
                            <c2:otherwise>
                                <spring:message code="Month${blogPostComment.getCreated().getMonth() + 1}"/>
                            </c2:otherwise>
                        </c:choose>
                        <fmt:formatDate value="${blogPostComment.getCreated()}" pattern="yyyy"/>
                    </span>
                </p>
                <p>
                    ${blogPostComment.getComment()}
                </p>
                <br>
            </div>
        </c:forEach>
        <br>

        <h5><spring:message code="LeaveAComment"/>&nbsp;(<spring:message code="CommentsAreModerated"/>):</h5>
        <br>
        <c:if test="${addCommentInfoMessage != null}">
            <div class="infomsg" id="addCommentInfoMessage">${addCommentInfoMessage}</div>
        </c:if>
        <c:if test="${addCommentErrorMessage != null}">
            <div class="errormsg" id="addCommentErrorMessage">${addCommentErrorMessage}</div>
        </c:if>
        <spring:url value="/blog/addBlogPostComment" var="addCommentUrl" htmlEscape="true"/>
        <form id="comment-form" class="fixed" method="post" action="${addCommentUrl}">
            <input type="hidden" name="blogPostId" value="${blogPost.id}"/>
            <input type="hidden" name="blogPostI18nContentId" value="${blogPostI18nContent.id}"/>
            <fieldset>
                <p>
                    <label for="userName"><spring:message code="YourName"/>: <span class="required">*</span></label>
                    <br>
                    <input class="text" type="text" id="userName" name="userName" value="">
                </p>
                <p>
                    <label for="email"><spring:message code="YourEmailAdress"/>: <span class="required">*</span></label>
                    <br>
                    <input type="email" class="text" id="email" name="email" value="">
                </p>
                <p>
                    <label for="comment"><spring:message code="Message"/>: </label>
                    <br>
                    <textarea id="comment" name="comment" rows="3" cols="25"></textarea>
                </p>
                <p class="last">
                    <button type="submit" name="requestHandler" value="addComment">
                        <spring:message code="Send"/>!
                    </button>
                </p>
            </fieldset>
        </form>

    </div>

    <div class="col280 last">

        <%--<h5><spring:message code="Search"/></h5><br />--%>
        <%--<form id="search" action="" method="post">--%>
            <%--<fieldset>--%>
                <%--<input type="text" name="searchText" id="search-input" value="${searchText}" placeholder="<spring:message code="Search"/>">--%>
                <%--<button type="submit" class="search-submit-btn" name="requestHandler" value="searchPost"></button>--%>
            <%--</fieldset>--%>
        <%--</form>--%>

        <%--<br />--%>

        <c:if test="${blogKeywords != null && !blogKeywords.isEmpty()}">
            <h5><spring:message code="Categories"/></h5><br />
            <ul class="side-nav">
                <c:forEach var="blogKeyword" items="${blogKeywords}" varStatus="j">
                    <li>
                        <spring:url value="/blog/category/${blogKeyword.id}" var="categoryUrl" htmlEscape="true"/>
                        <a href="${categoryUrl}">
                                <%--$lang.getTranslation($@keyword.getKeywordName(), "blog_keyword")--%>
                                ${blogKeyword.keywordName}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${postsArchiveStatistics != null && !postsArchiveStatistics.isEmpty()}">
            <h5><spring:message code="Archives"/></h5><br />
            <ul class="side-nav">
                <c:forEach var="archiveStatistic" items="${postsArchiveStatistics}" varStatus="j">
                    <li>
                        <spring:url value="/blog/date/${archiveStatistic[1]}/${archiveStatistic[2]}" var="dateUrl" htmlEscape="true"/>
                        <a href="${dateUrl}">
                            <c:choose>
                                <c:when test="${archiveStatistic[2] < 10}">
                                    <spring:message code="Month0${archiveStatistic[2]}"/>
                                </c:when>
                                <c2:otherwise>
                                    <spring:message code="Month${archiveStatistic[2]}"/>
                                </c2:otherwise>
                            </c:choose>
                                ${archiveStatistic[1]}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

    </div>

</div>

<%@include file="/pages/layout/Footer.jsp" %>