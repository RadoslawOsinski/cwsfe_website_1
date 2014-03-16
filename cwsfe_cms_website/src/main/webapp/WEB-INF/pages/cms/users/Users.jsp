<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="Users"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="usersList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Username"/></th>
                <th scope="col"><spring:message code="Status"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="UsersAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="username"><spring:message code="Username"/></label>
                    <div class="field-box"><input type="text" id="username" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="passwordHash"><spring:message code="Password"/></label>
                    <div class="field-box"><input type="password" id="passwordHash" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="addUser();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>