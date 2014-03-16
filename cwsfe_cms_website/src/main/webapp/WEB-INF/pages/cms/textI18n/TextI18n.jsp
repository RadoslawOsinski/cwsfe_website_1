<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="Translations"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="cmsTextI18nList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Language"/></th>
                <th scope="col"><spring:message code="Category"/></th>
                <th scope="col"><spring:message code="Key"/></th>
                <th scope="col"><spring:message code="Text"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="TranslationAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="searchLanguage"><spring:message code="Language"/></label>
                    <input type="hidden" id="searchLanguageId"/>
                    <div class="field-box"><input type="text" id="searchLanguage" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="searchCategory"><spring:message code="Category"/></label>
                    <input type="hidden" id="searchCategoryId"/>
                    <div class="field-box"><input type="text" id="searchCategory" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="key"><spring:message code="Key"/></label>
                    <div class="field-box"><input type="text" id="key" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="text"><spring:message code="Text"/></label>
                    <div class="field-box"><input type="text" id="text" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="addCmsTextI18n();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>