<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="Search"/></span></div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="searchAuthor"><spring:message code="Author"/></label>
                    <input type="hidden" id="searchAuthorId"/>

                    <div class="field-box"><input type="text" id="searchAuthor" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="searchPostTextCode"><spring:message code="PostTextCode"/></label>

                    <div class="field-box"><input type="text" id="searchPostTextCode" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="searchBlogPosts();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="BlogPostManagement"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="blogPostsList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Author"/></th>
                <th scope="col"><spring:message code="PostTextCode"/></th>
                <th scope="col"><spring:message code="CreationDate"/></th>
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
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="addNewBlogPostForm">
            <div class="contents">
                <div class="row">
                    <label for="author"><spring:message code="Author"/></label>
                    <input type="hidden" id="authorId"/>

                    <div class="field-box"><input type="text" id="author" class="w-icon medium"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="postTextCode"><spring:message code="PostTextCode"/></label>

                    <div class="field-box">
                        <input type="text" id="postTextCode" class="w-icon medium" maxlength="100"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="Add"/>" onclick="addBlogPost();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>