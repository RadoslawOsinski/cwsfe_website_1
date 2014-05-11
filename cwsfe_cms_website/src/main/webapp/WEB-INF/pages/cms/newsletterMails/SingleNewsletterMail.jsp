<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="NewsletterMailEdit"/></span></div>
        <div id="newsletterEditFormValidation" class="alert-small">
            <c:if test="${updateErrors != null}">
                <p>${updateErrors}</p>
            </c:if>
            <c:if test="${updateSuccessfull != null}">
                <p>${updateSuccessfull}</p>
            </c:if>
            <span class="close"></span>
        </div>
        <spring:url value="/CWSFE_CMS/newsletterMails/updateNewsletterMail" var="updateNewsletterMailUrl"
                    htmlEscape="true"/>
        <form id="editNewsletterForm" method="post" action="${updateNewsletterMailUrl}" autocomplete="off">
            <input type="hidden" name="id" id="newsletterMailId" value="${newsletterMail.id}"/>

            <div class="contents">
                <div class="row">
                    <label for="recipientGroup"><spring:message code="RecipientGroup"/></label>
                    <input type="hidden" id="recipientGroupId" name="recipientGroupId"
                           value="${newsletterMail.recipientGroupId}"/>

                    <div class="field-box"><input type="text" id="recipientGroup" class="w-icon medium"
                                                  value="${newsletterMailGroupName}"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterName"><spring:message code="Name"/></label>

                    <div class="field-box">
                        <input type="text" id="newsletterName" name="name"
                               class="w-icon medium" maxlength="100"
                               value="${newsletterMail.name}"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterSubject"><spring:message code="Subject"/></label>

                    <div class="field-box">
                        <input type="text" id="newsletterSubject" name="subject"
                               class="w-icon medium" maxlength="100"
                               value="${newsletterMail.subject}"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterContent"><spring:message code="Content"/></label>

                    <div class="field-box">
                        <textarea id="newsletterContent" name="mailContent"
                                  class="huge w-icon medium"
                                  cols="30"
                                  rows="15">${newsletterMail.mailContent}</textarea>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" name="requestHandler" value="<spring:message code="Save"/>"/>
                <input type="reset" value="Reset">
                <input type="button" id="confirmSendButton" value="<spring:message code="Send"/>" onclick="confirmNewsletterSend();return false;">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="NewsletterTestSend"/></span></div>
        <div id="newsletterTestSendFormValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="newsletterTestSendForm">
            <div class="contents">
                <div class="row">
                    <label for="testEmail"><spring:message code="Email"/></label>

                    <div class="field-box">
                        <input type="email" id="testEmail" class="w-icon medium" maxlength="350"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="TestSend"/>"
                       onclick="newsletterTestSend();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>