<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="full-width">

<div id="tabs">
<ul>
    <li><a href="#basicInfoTab"><spring:message code="BasicInfo"/></a></li>
    <c:forEach var="cmsLang" items="${cmsLangs}">
        <li>
            <a href="#i18nContentTab_${cmsLang.getCode()}"><spring:message code="Content"/> ${cmsLang.getCode()}</a>
        </li>
    </c:forEach>
    <li><a href="#imagesTab"><spring:message code="Images"/></a></li>
    <li><a href="#categoriesTab"><spring:message code="Categories"/></a></li>
    <li><a href="#codeFragmentsTab"><spring:message code="CodeFragments"/></a></li>
</ul>

<div id="basicInfoTab">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">

            <div class="titlebar"><span class="w-icon"><spring:message code="BlogPostEdit"/></span></div>
            <div id="basicInfoFormValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <form id="blogPostBasicInfoForm">
                <input type="hidden" id="blogPostId" name="blogPost.id" value="${blogPost.id}">

                <div class="contents">
                    <div class="row">
                        <label for="author"><spring:message code="Author"/></label>
                        <input type="hidden" id="postAuthorId" value="${blogPost.postAuthorId}"/>

                        <div class="field-box"><input type="text" id="author" class="w-icon medium"
                                                      value="${cmsAuthor.lastName} ${cmsAuthor.firstName}" disabled/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="postTextCode"><spring:message code="PostTextCode"/></label>

                        <div class="field-box"><input type="text" id="postTextCode"
                                                      class="w-icon medium"
                                                      value="${blogPost.postTextCode}"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="status"><spring:message code="Status"/></label>

                        <div class="field-box">
                            <select id="status" class="w-icon medium">
                                <option value="H"<c:if test="${blogPost.status.equals('H')}"> selected</c:if>>
                                    <spring:message code="Hidden"/></option>
                                <option value="P"<c:if test="${blogPost.status.equals('P')}"> selected</c:if>>
                                    <spring:message code="Published"/></option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="postCreationDate"><spring:message code="CreationDate"/></label>

                        <div class="field-box">
                            <input type="text" id="postCreationDate" class="w-icon medium"
                                   maxlength="16"
                                   value="<fmt:formatDate value="${blogPost.postCreationDate}" pattern="yyyy-MM-dd HH:mm"/>"
                                   disabled/>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Save"/>" onclick="saveBlogPost();return false;">
                    <input type="reset" value="Revert"/>
                </div>
            </form>
        </div>
    </div>

</div>

<c:forEach var="cmsLang" items="${cmsLangs}">
    <div id="i18nContentTab_${cmsLang.getCode()}">
        <p>&nbsp;</p>

        <c:choose>
            <c:when test="${blogPost.blogPostI18nContent.get(cmsLang.getCode()) == null}">
                <spring:url value="/addBlogPostsI18nContent" var="addBlogPostsI18nContentUrl"
                            htmlEscape="true"/>
                <form class="fixed" method="post" action="${addBlogPostsI18nContentUrl}" autocomplete="off">
                    <input type="hidden" name="postId" value="${blogPost.id}">
                    <input type="hidden" name="languageId" value="${cmsLang.id}">
                    <button type="submit" name="requestHandler" value="addBlogPostsI18nContent"
                            class="button green medium">
                        <spring:message code="AddPostInternationalisation"/>
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <%--&lt;%&ndash;<spring:message code="Image"):&ndash;%&gt;--%>
                <%--&lt;%&ndash;$text.escapeHtml("$*pl.com.cwsfe.cms.news.newsmanagement.CmsNewsImageByNewsIdAndTitle($#{blogPost.id},&ndash;%&gt;--%>
                <%--&lt;%&ndash;\"imageCode\")")<br>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<hr/>&ndash;%&gt;--%>
                <div class="box">
                    <div class="inner">

                        <spring:url value="updateBlogPostI18nContent" var="updateBlogPostI18nContentUrl"
                                    htmlEscape="true"/>
                        <form class="fixed" method="post" action="${updateBlogPostI18nContentUrl}" autocomplete="off">
                            <input type="hidden" name="id"
                                   value="${blogPost.blogPostI18nContent.get(cmsLang.getCode()).id}">
                            <input type="hidden" name="postId"
                                   value="${blogPost.blogPostI18nContent.get(cmsLang.getCode()).postId}">
                            <input type="hidden" name="languageId"
                                   value="${blogPost.blogPostI18nContent.get(cmsLang.getCode()).languageId}">

                            <div class="contents">
                                <div class="row">
                                    <label for="postTitle"><spring:message code="Title"/></label>

                                    <div class="field-box"><input type="text" id="postTitle" name="postTitle"
                                                                  class="w-icon medium"
                                                                  value="${blogPost.blogPostI18nContent.get(cmsLang.getCode()).postTitle}"
                                                                  maxlength="100"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="row">
                                    <label for="postShortcut"><spring:message code="Shortcut"/></label>

                                    <div class="field-box">
                                        <textarea id="postShortcut" name="postShortcut"
                                                  class="huge w-icon medium"
                                                  cols="30"
                                                  rows="15">${blogPost.blogPostI18nContent.get(cmsLang.getCode()).postShortcut}</textarea>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="row">
                                    <label for="postDescription"><spring:message code="Description"/></label>

                                    <div class="field-box">
                                        <textarea id="postDescription" name="postDescription"
                                                  class="huge w-icon medium"
                                                  cols="30"
                                                  rows="15">${blogPost.blogPostI18nContent.get(cmsLang.getCode()).postDescription}</textarea>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="row">
                                    <label for="blogPostI18nContentStatus"><spring:message code="Status"/></label>

                                    <div class="field-box">
                                        <select id="blogPostI18nContentStatus" name="status"
                                                class="w-icon medium">
                                            <option value="H"<c:if
                                                    test="${blogPost.blogPostI18nContent.get(cmsLang.getCode()).status.equals('H')}"> selected</c:if>>
                                                <spring:message code="Hidden"/></option>
                                            <option value="P"<c:if
                                                    test="${blogPost.blogPostI18nContent.get(cmsLang.getCode()).status.equals('P')}"> selected</c:if>>
                                                <spring:message code="Published"/></option>
                                        </select>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                            <div class="bar-big">
                                <input type="submit" value="<spring:message code="Save"/>"/>
                                <input type="reset" value="Revert"/>
                            </div>
                        </form>

                    </div>
                </div>


                <%--$ifNotNull($@blogPostI18nContent, {--%>
                <%--<table class="table table-striped table-bordered dataTable">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                <%--<th>#</th>--%>
                <%--<th><spring:message code="Username")</th>--%>
                <%--<th><spring:message code="Email")</th>--%>
                <%--<th><spring:message code="Comment")</th>--%>
                <%--<th><spring:message code="Status")</th>--%>
                <%--<th><spring:message code="Actions")</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--$=(@blogPostComments, (List) $*pl.com.cwsfe.cms.dao.BlogPostCommentsDAO("listForPostI18nContent",--%>
                <%--$#{blogPostI18nContent.id}))--%>
                <%--$if($util.isEmpty((List) $@blogPostComments), {--%>
                <%--<tr>--%>
                <%--<td colspan="4"><spring:message code="NoRecords")</td>--%>
                <%--</tr>--%>
                <%--}, {--%>
                <%--$=(@cnt, 1)--%>
                <%--$=(@blogPostComment, (pl.com.cwsfe.cms.model.BlogPostComment) null)--%>
                <%--$for(@blogPostComment, $@blogPostComments, {--%>
                <%--<tr>--%>
                <%--<td>$@cnt</td>--%>
                <%--<td>$@blogPostComment.getUsername()</td>--%>
                <%--<td>$@blogPostComment.getEmail()</td>--%>
                <%--<td>$@blogPostComment.getComment()</td>--%>
                <%--<td>--%>
                <%--$if($==($@blogPostComment.getStatus(), "N"), {--%>
                <%--<spring:message code="NewComment")--%>
                <%--})--%>
                <%--$if($==($@blogPostComment.getStatus(), "P"), {--%>
                <%--<spring:message code="PublishedComment")--%>
                <%--})--%>
                <%--$if($==($@blogPostComment.getStatus(), "B"), {--%>
                <%--<spring:message code="BlockedComment")--%>
                <%--})--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--<form method="post" action="$page.url($currentPageCode())">--%>
                <%--<input type="hidden" name="blogPost.id" value="$#{blogPost.id}">--%>
                <%--<input type="hidden" name="blogPostI18nContent.postId" value="$#{blogPost.id}">--%>
                <%--<input type="hidden" name="blogPostI18nContent.languageId" value="$@pLang[0]">--%>
                <%--<input type="hidden" name="blogPostComment.id" value="$@blogPostComment.getId()">--%>
                <%--<button type="submit" name="requestHandler" value="blogPostCommentPublish" class="btn btn-success">--%>
                <%--<spring:message code="PublishComment")--%>
                <%--</button>--%>
                <%--<button type="submit" name="requestHandler" value="blogPostCommentBlock" class="btn btn-warning">--%>
                <%--<spring:message code="BlockComment")--%>
                <%--</button>--%>
                <%--</form>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--$++(@cnt)--%>
                <%--})--%>
                <%--})--%>
                <%--</tbody>--%>
                <%--<tfoot>--%>
                <%--<tr>--%>
                <%--<th>#</th>--%>
                <%--<th><spring:message code="Username")</th>--%>
                <%--<th><spring:message code="Email")</th>--%>
                <%--<th><spring:message code="Comment")</th>--%>
                <%--<th><spring:message code="Status")</th>--%>
                <%--<th><spring:message code="Actions")</th>--%>
                <%--</tr>--%>
                <%--</tfoot>--%>
                <%--</table>--%>
                <%--})--%>

            </c:otherwise>
        </c:choose>

    </div>
</c:forEach>


<div id="imagesTab">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">
            <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                    code="BlogPostImagesManagement"/></span>
            </div>
            <div id="blogPostImagesListTableValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <table id="blogPostImagesList">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><spring:message code="Title"/></th>
                    <th scope="col"><spring:message code="Image"/></th>
                    <th scope="col"><spring:message code="Actions"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <div class="box">
        <div class="inner">

            <div class="titlebar"><span class="w-icon"><spring:message code="BlogPostImagesManagement"/></span></div>
            <div id="addBlogPostImageFormValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <spring:url value="/CWSFE_CMS/blogPosts/addBlogPostImage" var="addBlogPostImageUrl" htmlEscape="true"/>
            <form class="fixed" method="post" action="${addBlogPostImageUrl}" autocomplete="off"
                  id="addBlogPostImageForm" enctype="multipart/form-data">
                <input type="hidden" name="blogPostId" value="${blogPost.id}"/>

                <div class="contents">
                    <div class="row">
                        <label for="title"><spring:message code="Title"/></label>

                        <div class="field-box"><input type="text" id="title" name="title"
                                                      class="w-icon medium"
                                                      maxlength="100"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="row">
                        <label><spring:message code="File"/></label>

                        <div class="field-box">
                            <input type="file" name="file" class="file-file"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Add"/>"/>
                    <input type="reset"/>
                </div>
            </form>
        </div>
    </div>

</div>

<div id="categoriesTab">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">

            <spring:url value="/CWSFE_CMS/postCategoriesUpdate" var="postCategoriesUpdateUrl"
                        htmlEscape="true"/>

            <form class="fixed" method="post" action="${postCategoriesUpdateUrl}" autocomplete="off">
                <input type="hidden" name="id" value="${blogPost.id}">

                <div class="contents">
                    <c:forEach var="category" items="${blogKeywords}">

                        <div class="row">
                            <label>${category.keywordName}</label>

                            <div class="field-box">
                                <input type="checkbox" name="postCategories" value="${category.id}"<c:if
                                        test="${blogPostSelectedKeywords.contains(category.id)}"> checked</c:if>/>
                            </div>
                            <div class="clear"></div>
                        </div>

                    </c:forEach>
                </div>
                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Save"/>"/>
                    <input type="reset"/>
                </div>
            </form>

        </div>
    </div>

</div>

<div id="codeFragmentsTab">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">
            <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                    code="BlogPostCodesManagement"/></span>
            </div>
            <div id="blogPostCodesTableValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <table id="blogPostCodesList">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><spring:message code="CodeId"/></th>
                    <th scope="col"><spring:message code="Code"/></th>
                    <th scope="col"><spring:message code="Actions"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <div class="box">
        <div class="inner">

            <div class="titlebar"><span class="w-icon"><spring:message code="BlogPostAdding"/></span></div>
            <div id="addBlogPostCodeFormValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <form class="fixed" autocomplete="off" id="addBlogPostCodeForm">
                <input type="hidden" name="blogPostId" value="${blogPost.id}"/>

                <div class="contents">
                    <div class="row">
                        <label for="codeId"><spring:message code="CodeId"/></label>

                        <div class="field-box"><input type="text" id="codeId" name="codeId"
                                                      class="w-icon medium"
                                                      maxlength="100"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="row">
                        <label for="code"><spring:message code="Code"/></label>

                        <div class="field-box">
                            <textarea id="code" name="code"
                                      class="huge w-icon medium"
                                      cols="30"
                                      rows="15"></textarea>
                        </div>
                        <div class="clear"></div>
                    </div>

                </div>
                <div class="bar-big">
                    <input type="submit" onclick="addBlogPostCode();return false;"
                           value="<spring:message code="Add"/>"/>
                    <input type="reset"/>
                </div>
            </form>
        </div>
    </div>

</div>

</div>
</div>
<div class="clear"></div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>