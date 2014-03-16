<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="MailGroupEdit"/></span></div>
        <div id="mailGroupEditFormValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="editMailGroupForm">
            <input type="hidden" id="mailGroupId" name="mailGroupId" value="${newsletterMailGroup.id}"/>
            <div class="contents">
                <div class="row">
                    <label for="newsletterMailGroupName"><spring:message code="NewsletterMailGroupName"/></label>
                    <div class="field-box">
                        <input type="text" id="newsletterMailGroupName" class="w-icon medium" maxlength="100" value="${newsletterMailGroup.name}"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="language"><spring:message code="Language2LetterCode"/></label>
                    <input type="hidden" id="languageId" value="${newsletterMailGroup.languageId}"/>
                    <div class="field-box"><input type="text" id="language" class="w-icon medium" value="${newsletterMailGroupLanguageCode}"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="Save"/>" onclick="updateNewsletterMailGroup();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="Search"/></span></div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="searchMail"><spring:message code="Mail"/></label>
                    <div class="field-box"><input type="text" id="searchMail" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="searchMailInNewsletterMailGroup();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="NewsletterMailAddressesManagement"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="newsletterMailAddressesList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Email"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="MailAddressAdding"/></span></div>
        <div id="addNewMailAddressFormValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="addNewMailAddressForm">
            <div class="contents">
                <div class="row">
                    <label for="newsletterMailAddress"><spring:message code="Email"/></label>
                    <div class="field-box">
                        <input type="email" id="newsletterMailAddress" class="w-icon medium" maxlength="350"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="Add"/>" onclick="addNewsletterMailAddress();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>