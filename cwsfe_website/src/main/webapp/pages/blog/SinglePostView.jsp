<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<div id="page-header">
    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/services/photodune-1402059-fiber-optics-s_880x200.JPG"
         width="880" height="200" alt="services image"/>

    <div id="page-header-title"><spring:message code="Blog"/></div>
</div>

<input type="hidden" id="localeLanguage" value="${localeLanguage}">
<input type="hidden" id="blogPostId" value="${blogPostId}"/>
<input type="hidden" id="blogPostI18nContentId" value="${blogPostI18nContentId}"/>

<div class="fixed">
    <div class="col580">

        <div id="theTextIsMissingMessage" class="centered" style="display: none;">
            <div class="noticemsg"><h1><spring:message code="TheTextIsMissing"/></h1></div>
        </div>
        <div class="blog-post">
            <div class="blog-post-date">
                <div id="postCreationDateDay"></div>
                <span id="postCreationDateMonthAndYear"></span>
            </div>
            <h3 class="blog-post-title" id="postTitle"></h3>
            <ul class="blog-post-info fixed">
                <li class="author" id="postAuthorText"></li>
                <li class="categories" id="postCategories"></li>
                <li class="comments">
                    <a href="#blogPostComments" id="commentsCount1"></a>
                    <spring:message code="Comments"/>
                </li>
            </ul>
            <article id="postDescription">
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
                <strong><spring:message code="NumberOfComments"/>: <span id="commentsCount2"></span></strong>
            </h5>
        </div>

        <%--<c:forEach var="blogPostComment" items="${blogPostI18nContent.getBlogPostComments()}">--%>
        <%--<div class="blog-post-comment <c:if test="${blogPostComment.getParentCommentId() != null}">blog-post-comment-reply</c:if>">--%>
        <%--<img src="http://www.gravatar.com/avatar/${blogPostComment.getEmail()}?s72&d=identicon" class="img-align-left bordered" width="72" height="72" alt="user image">--%>
        <%--<p class="who">--%>
        <%--<strong>${blogPostComment.getUsername()}</strong>--%>
        <%--<span class="date">--%>
        <%--<fmt:formatDate value="${blogPostComment.getCreated()}" pattern="dd"/>--%>
        <%--<c:choose>--%>
        <%--<c:when test="${blogPostComment.getCreated().getMonth() + 1 < 10}">--%>
        <%--<spring:message code="Month0${blogPostComment.getCreated().getMonth() + 1}"/>--%>
        <%--</c:when>--%>
        <%--<c2:otherwise>--%>
        <%--<spring:message code="Month${blogPostComment.getCreated().getMonth() + 1}"/>--%>
        <%--</c2:otherwise>--%>
        <%--</c:choose>--%>
        <%--<fmt:formatDate value="${blogPostComment.getCreated()}" pattern="yyyy"/>--%>
        <%--</span>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--${blogPostComment.getComment()}--%>
        <%--</p>--%>
        <%--<br>--%>
        <%--</div>--%>
        <%--</c:forEach>--%>
        <%--<br>--%>

        <h5><spring:message code="LeaveAComment"/>&nbsp;(<spring:message code="CommentsAreModerated"/>):</h5>
        <br>

        <div class="infomsg" id="addCommentInfoMessage" style="display: none"><spring:message
                code="AddedSuccessfullyWaitForModeratorPublication"/></div>
        <div class="errormsg" id="addCommentErrorMessage" style="display: none"><spring:message
                code="ValidationError"/></div>
        <spring:url value="/CWSFE_CMS/rest/comments" var="addCommentUrl" htmlEscape="true"/>
        <form id="addCommentForm" class="fixed" method="post" action="${addCommentUrl}">
            <input type="hidden" name="blogPostI18nContentId" value="${blogPostI18nContentId}"/>
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
                    <button type="button" id="addCommentButton">
                        <spring:message code="Send"/>!
                    </button>
                </p>
            </fieldset>
        </form>

    </div>

    <div class="col280 last">

        <h5><spring:message code="Categories"/></h5><br/>
        <ul id="keywordsList" class="side-nav">
        </ul>

    </div>

</div>

<%@include file="/pages/layout/Footer.jsp" %>