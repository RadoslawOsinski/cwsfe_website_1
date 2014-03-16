<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="Search"/></span></div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="searchName"><spring:message code="Name"/></label>
                    <div class="field-box"><input type="text" id="searchName" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="searchRecipientGroup"><spring:message code="RecipientGroup"/></label>
                    <input type="hidden" id="searchRecipientGroupId" value=""/>
                    <div class="field-box"><input type="text" id="searchRecipientGroup" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="searchNewsletterMail();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="NewsletterMailsManagement"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="newsletterMailsList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="RecipientGroup"/></th>
                <th scope="col"><spring:message code="Name"/></th>
                <th scope="col"><spring:message code="Subject"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="NewsletterMailAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="addNewNewsletterMailForm">
            <div class="contents">
                <div class="row">
                    <label for="recipientGroup"><spring:message code="RecipientGroup"/></label>
                    <input type="hidden" id="recipientGroupId"/>
                    <div class="field-box"><input type="text" id="recipientGroup" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterMailName"><spring:message code="Name"/></label>
                    <div class="field-box">
                        <input type="text" id="newsletterMailName" class="w-icon medium" maxlength="100"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterMailSubject"><spring:message code="Subject"/></label>
                    <div class="field-box">
                        <input type="text" id="newsletterMailSubject" class="w-icon medium" maxlength="100"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="Add"/>" onclick="addNewsletterMail();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>