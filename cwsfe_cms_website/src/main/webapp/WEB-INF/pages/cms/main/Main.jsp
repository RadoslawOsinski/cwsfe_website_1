<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white bar-chart"></span> <span class="w-icon"><spring:message
                code="Posts"/></span></div>
        <div class="contents">
            <div id="posts_chart_dashboard"></div>
        </div>
    </div>
</div>

<div class="two-third">
    <div class="box no-bg">
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <h2><spring:message code="NewComments"/></h2>
        <table id="blogPostCommentsList">
            <thead>
                <tr>
                    <th>#</th>
                    <th scope="col"><spring:message code="Username"/></th>
                    <th scope="col"><spring:message code="Comment"/></th>
                    <th scope="col"><spring:message code="CreationDate"/></th>
                    <th scope="col"><spring:message code="Status"/></th>
                    <th scope="col"><spring:message code="Actions"/></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>