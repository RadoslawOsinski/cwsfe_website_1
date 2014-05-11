<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="Authors"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="authorsList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="LastName"/></th>
                <th scope="col"><spring:message code="FirstName"/></th>
                <th scope="col"><spring:message code="GooglePlusAuthorLink"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="AuthorsAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="firstName"><spring:message code="FirstName"/></label>
                    <div class="field-box"><input type="text" id="firstName" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="lastName"><spring:message code="LastName"/></label>
                    <div class="field-box"><input type="text" id="lastName" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="googlePlusAuthorLink"><spring:message code="GooglePlusAuthorLink"/></label>
                    <div class="field-box"><input type="text" id="googlePlusAuthorLink" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="addAuthor();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>