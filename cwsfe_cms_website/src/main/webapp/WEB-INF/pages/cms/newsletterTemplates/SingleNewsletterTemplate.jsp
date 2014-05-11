<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="NewsletterTemplateEdit"/></span></div>
        <div id="newsletterTemplateEditFormValidation" class="alert-small">
            <c:if test="${updateErrors != null}">
                <p>${updateErrors}</p>
            </c:if>
            <c:if test="${updateSuccessfull != null}">
                <p>${updateSuccessfull}</p>
            </c:if>
            <span class="close"></span>
        </div>
        <spring:url value="/CWSFE_CMS/newsletterTemplates/updateNewsletterTemplate" var="updateNewsletterTemplateUrl"
                    htmlEscape="true"/>
        <form id="editNewsletterTemplateForm" method="post" action="${updateNewsletterTemplateUrl}" autocomplete="off">
            <input type="hidden" name="id" id="newsletterTemplateId" value="${newsletterTemplate.id}"/>

            <div class="contents">
                <div class="row">
                    <label for="language"><spring:message code="Language2LetterCode"/></label>
                    <input type="hidden" id="languageId" name="languageId" value="${newsletterTemplate.languageId}"/>

                    <div class="field-box"><input type="text" id="language" name="language" class="w-icon medium"
                                                  value="${newsletterTemplateLanguageCode}"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterTemplateName"><spring:message code="Name"/></label>

                    <div class="field-box">
                        <input type="text" id="newsletterTemplateName" name="name"
                               class="w-icon medium" maxlength="100"
                               value="${newsletterTemplate.name}"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterTemplateSubject"><spring:message code="Subject"/></label>

                    <div class="field-box">
                        <input type="text" id="newsletterTemplateSubject" name="subject"
                               class="w-icon medium" maxlength="100"
                               value="${newsletterTemplate.subject}"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="contents">
                <div class="row">
                    <label for="newsletterTemplateContent"><spring:message code="Content"/></label>

                    <div class="field-box">
                        <textarea id="newsletterTemplateContent" name="content"
                                  class="huge w-icon medium"
                                  cols="30"
                                  rows="15">${newsletterTemplate.content}</textarea>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" name="requestHandler" value="<spring:message code="Save"/>"/>
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="NewsletterTemplateTestSend"/></span></div>
        <div id="newsletterTemplateTestSendFormValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="newsletterTemplateTestSendForm">
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
                       onclick="newsletterTemplateTestSend();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>