<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="full-width">

<div id="tabs">
<ul>
    <li><a href="#tabBasicInfo"><spring:message code="BasicInfo"/></a></li>
    <c:forEach var="cmsLang" items="${cmsLangs}">
        <li>
            <a href="#tabI18nContent_${cmsLang.getCode()}"><spring:message code="Content"/> ${cmsLang.getCode()}</a>
        </li>
    </c:forEach>
    <li><a href="#tabImages"><spring:message code="Images"/></a></li>
</ul>

<div id="tabBasicInfo">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">

            <div class="titlebar"><span class="w-icon"><spring:message code="CmsNewsAdding"/></span></div>
            <div id="basicInfoFormValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <form id="newsForm">
                <input type="hidden" id="cmsNewsId" name="cmsNews.id" value="${cmsNews.id}">

                <div class="contents">
                    <div class="row">
                        <label for="author"><spring:message code="Author"/></label>
                        <input type="hidden" id="authorId" value="${cmsNews.authorId}"/>

                        <div class="field-box"><input type="text" id="author" class="w-icon medium"
                                                      value="${cmsAuthor.lastName} ${cmsAuthor.firstName}" disabled/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="newsType"><spring:message code="NewsType"/></label>
                        <input type="hidden" id="newsTypeId" value="${cmsNews.newsTypeId}"/>

                        <div class="field-box"><input type="text" id="newsType" class="w-icon medium"
                                                      value="${newsType.type}"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="newsFolder"><spring:message code="NewsFolder"/></label>
                        <input type="hidden" id="newsFolderId" value="${cmsNews.newsFolderId}"/>

                        <div class="field-box"><input type="text" id="newsFolder"
                                                      class="w-icon medium"
                                                      value="${newsFolder.getFolderName()}"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="newsCode"><spring:message code="NewsCode"/></label>

                        <div class="field-box">
                            <input type="text" id="newsCode" class="w-icon medium" maxlength="100"
                                   value="${cmsNews.newsCode}"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="status"><spring:message code="Status"/></label>

                        <div class="field-box">
                            <select id="status" class="w-icon medium">
                                <option value="H"<c:if test="${cmsNews.status.equals('H')}"> selected</c:if>>
                                    <spring:message code="Hidden"/></option>
                                <option value="P"<c:if test="${cmsNews.status.equals('P')}"> selected</c:if>>
                                    <spring:message code="Published"/></option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="creationDate"><spring:message code="CreationDate"/></label>

                        <div class="field-box">
                            <input type="text" id="creationDate" class="w-icon medium"
                                   maxlength="100"
                                   value="<fmt:formatDate value="${cmsNews.creationDate}" pattern="yyyy-MM-dd HH:mm"/>"
                                   disabled/>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Save"/>" onclick="saveNews();return false;">
                    <input type="reset" value="Revert"/>
                </div>
            </form>
        </div>
    </div>

</div>

<c:forEach var="cmsLang" items="${cmsLangs}">
    <div id="tabI18nContent_${cmsLang.getCode()}">
        <p>&nbsp;</p>

        <c:choose>
            <c:when test="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()) == null}">
                <spring:url value="addNewsI18nContent" var="addNewsI18nContentUrl" htmlEscape="true"/>
                <form class="fixed" method="post" action="${addNewsI18nContentUrl}" autocomplete="off">
                    <input type="hidden" name="newsId" value="${cmsNews.id}">
                    <input type="hidden" name="languageId" value="${cmsLang.id}">
                    <button type="submit" name="requestHandler" value="addNewsI18nContent" class="button green medium">
                        <spring:message code="AddNewsInternationalisation"/>
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <%--<spring:message code="Image"):--%>
                <%--$text.escapeHtml("$*pl.com.cwsfe.cms.news.newsmanagement.CmsNewsImageByNewsIdAndTitle($#{cmsNews.id},--%>
                <%--\"imageCode\")")<br>--%>
                <hr/>
                <div class="box">
                    <div class="inner">

                        <spring:url value="updateNewsI18nContent" var="updateNewsI18nContentUrl"
                                    htmlEscape="true"/>
                        <form class="fixed" method="post" action="${updateNewsI18nContentUrl}" autocomplete="off">
                            <input type="hidden" name="id"
                                   value="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).id}">
                            <input type="hidden" name="newsId"
                                   value="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).newsId}">
                            <input type="hidden" name="languageId"
                                   value="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).languageId}">

                            <div class="contents">
                                <div class="row">
                                    <label for="newsTitle"><spring:message code="Title"/></label>

                                    <div class="field-box"><input type="text" id="newsTitle" name="newsTitle"
                                                                  class="w-icon medium"
                                                                  value="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).newsTitle}"
                                                                  maxlength="100"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="row">
                                    <label for="newsShortcut"><spring:message code="Shortcut"/></label>

                                    <div class="field-box">
                                        <textarea id="newsShortcut" name="newsShortcut"
                                                  class="huge w-icon medium"
                                                  cols="30"
                                                  rows="15">${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).newsShortcut}</textarea>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="row">
                                    <label for="newsDescription"><spring:message code="Description"/></label>

                                    <div class="field-box">
                                        <textarea id="newsDescription" name="newsDescription"
                                                  class="huge w-icon medium"
                                                  cols="30"
                                                  rows="15">${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).newsDescription}</textarea>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="row">
                                    <label for="i18nContentStatus"><spring:message code="Status"/></label>

                                    <div class="field-box">
                                        <select id="i18nContentStatus" name="status"
                                                class="w-icon medium">
                                            <option value="H"<c:if
                                                    test="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).status.equals('H')}"> selected</c:if>>
                                                <spring:message code="Hidden"/></option>
                                            <option value="P"<c:if
                                                    test="${cmsNews.cmsNewsI18nContents.get(cmsLang.getCode()).status.equals('P')}"> selected</c:if>>
                                                <spring:message code="Published"/></option>
                                        </select>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                            <div class="bar-big">
                                <input type="submit" value="<spring:message code="Save"/>"/>
                                <input type="reset" value="Revert"/>
                            </div>
                        </form>

                    </div>
                </div>

            </c:otherwise>
        </c:choose>

    </div>
</c:forEach>

<div id="tabImages">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">
            <div class="titlebar">
                <span class="icon awesome white table"></span>
                <span class="w-icon">
                    <spring:message code="CmsNewsImagesManagement"/>
                </span>
            </div>
            <div id="cmsNewsImagesListTableValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <table id="cmsNewsImagesList">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><spring:message code="Title"/></th>
                    <th scope="col"><spring:message code="Image"/></th>
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

            <div class="titlebar">
                <span class="w-icon">
                    <spring:message code="CmsNewsImagesManagement"/>
                </span>
            </div>
            <div id="addCmsNewsImageFormValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <spring:url value="addCmsNewsImage" var="addCmsNewsImageUrl" htmlEscape="true"/>
            <form class="fixed" method="post" action="${addCmsNewsImageUrl}" autocomplete="off"
                  id="addCmsNewsImageForm" enctype="multipart/form-data">
                <input type="hidden" name="newsId" value="${cmsNews.id}"/>

                <div class="contents">
                    <div class="row">
                        <label for="title"><spring:message code="Title"/></label>

                        <div class="field-box"><input type="text" id="title" name="title"
                                                      class="w-icon medium"
                                                      maxlength="100"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="row">
                        <label><spring:message code="File"/></label>

                        <div class="field-box">
                            <input type="file" name="file" class="file-file"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Add"/>"/>
                    <input type="reset"/>
                </div>
            </form>
        </div>
    </div>

</div>

</div>

</div>
<div class="clear"></div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>