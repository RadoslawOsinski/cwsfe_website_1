<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="Roles"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="rolesList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="RoleCode"/></th>
                <th scope="col"><spring:message code="RoleName"/></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>