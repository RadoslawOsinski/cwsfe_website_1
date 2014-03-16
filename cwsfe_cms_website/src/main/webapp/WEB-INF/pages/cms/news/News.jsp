<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="w-icon"><spring:message code="Search"/></span></div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="searchAuthor"><spring:message code="Author"/></label>
                    <input type="hidden" id="searchAuthorId"/>

                    <div class="field-box"><input type="text" id="searchAuthor" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="searchNewsCode"><spring:message code="NewsCode"/></label>

                    <div class="field-box"><input type="text" id="searchNewsCode" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="searchNews();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="CmsNewsManagement"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="newsList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Author"/></th>
                <th scope="col"><spring:message code="NewsCode"/></th>
                <th scope="col"><spring:message code="CreationDate"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="CmsNewsAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form id="addNewNewsForm">
            <div class="contents">
                <div class="row">
                    <label for="author"><spring:message code="Author"/></label>
                    <input type="hidden" id="authorId"/>

                    <div class="field-box"><input type="text" id="author" class="w-icon medium"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="newsType"><spring:message code="NewsType"/></label>
                    <input type="hidden" id="newsTypeId"/>

                    <div class="field-box"><input type="text" id="newsType" class="w-icon medium" value=""/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="newsFolder"><spring:message code="NewsFolder"/></label>
                    <input type="hidden" id="newsFolderId"/>

                    <div class="field-box"><input type="text" id="newsFolder" class="w-icon medium"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="newsCode"><spring:message code="NewsCode"/></label>

                    <div class="field-box">
                        <input type="text" id="newsCode" class="w-icon medium" maxlength="100"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="<spring:message code="Add"/>" onclick="addNews();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>