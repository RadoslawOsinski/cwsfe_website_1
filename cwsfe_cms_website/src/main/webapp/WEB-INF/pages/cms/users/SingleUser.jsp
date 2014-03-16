<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="full-width">

<div id="tabs">
<ul>
    <li><a href="#tabBasicInfo"><spring:message code="BasicInfo"/></a></li>
    <li><a href="#rolesTab"><spring:message code="Roles"/></a></li>
</ul>

<div id="tabBasicInfo">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">

            <div class="titlebar"><span class="w-icon"><spring:message code="SelectedUser"/></span></div>
            <div id="basicInfoFormValidation" class="alert-small">
                <span class="close"></span>
            </div>
            <form id="userForm">
                <input type="hidden" id="cmsUserId" name="cmsUser.id" value="${cmsUser.id}">

                <div class="contents">
                    <div class="row">
                        <label for="username"><spring:message code="Username"/></label>
                        <div class="field-box">
                            <input type="text" id="username" class="w-icon medium" maxlength="100"
                                   value="${cmsUser.username}"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <label for="status"><spring:message code="Status"/></label>

                        <div class="field-box">
                            <select id="status" class="w-icon medium">
                                <option value="N"<c:if test="${cmsUser.status.equals('N')}"> selected</c:if>>
                                    <spring:message code="UserStatusNew"/></option>
                                <option value="L"<c:if test="${cmsUser.status.equals('L')}"> selected</c:if>>
                                    <spring:message code="UserStatusLocked"/></option>
                                <option value="D"<c:if test="${cmsUser.status.equals('D')}"> selected</c:if>>
                                    <spring:message code="UserStatusDeleted"/></option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Save"/>" onclick="saveUser();return false;">
                    <input type="reset" value="Revert"/>
                </div>
            </form>
        </div>
    </div>

</div>

<div id="rolesTab">
    <p>&nbsp;</p>

    <div class="box">
        <div class="inner">

            <spring:url value="/CWSFE_CMS/userRolesUpdate" var="userRolesUpdateUrl" htmlEscape="true"/>

            <form class="fixed" method="post" action="${userRolesUpdateUrl}" autocomplete="off">
                <input type="hidden" name="id" value="${cmsUser.id}"/>

                <div class="contents">
                    <c:forEach var="role" items="${cmsRoles}">

                        <div class="row">
                            <label class="wide">${role.roleName}</label>

                            <div class="field-box">
                                    <input type="checkbox" name="cmsUserRoles" class="w-icon medium"
                                           value="${role.id}"<c:if
                                            test="${userSelectedRoles.contains(role.id)}"> checked</c:if>/>

                            </div>
                            <div class="clear"></div>
                        </div>

                    </c:forEach>
                </div>
                <div class="bar-big">
                    <input type="submit" value="<spring:message code="Save"/>"/>
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