<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="Search"/></span></div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="searchName"><spring:message code="NewsletterMailGroupName"/></label>
                    <div class="field-box"><input type="text" id="searchName" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="searchLanguage"><spring:message code="Language2LetterCode"/></label>
                    <input type="hidden" id="searchLanguageId" value=""/>
                    <div class="field-box"><input type="text" id="searchLanguage" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="searchNewsletterMailGroup();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="NewsletterMailGroupsManagement"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="newsletterMailGroupsList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="NewsletterMailGroupName"/></th>
                <th scope="col"><spring:message code="Language"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="NewsletterMailGroupAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="addNewNewsletterMailGroupForm">
            <div class="contents">
                <div class="row">
                    <label for="language"><spring:message code="Language2LetterCode"/></label>
                    <input type="hidden" id="languageId"/>
                    <div class="field-box"><input type="text" id="language" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterMailGroupName"><spring:message code="NewsletterMailGroupName"/></label>
                    <div class="field-box">
                        <input type="text" id="newsletterMailGroupName" class="w-icon medium" maxlength="100"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="Add"/>" onclick="addNewsletterMailGroup();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>