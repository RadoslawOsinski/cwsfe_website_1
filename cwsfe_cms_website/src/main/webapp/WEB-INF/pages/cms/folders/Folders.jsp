<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        <div class="titlebar"><span class="icon awesome white table"></span><span class="w-icon"><spring:message
                code="Folders"/></span>
        </div>
        <div id="tableValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <table id="foldersList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Folder"/></th>
                <th scope="col"><spring:message code="OrderNumber"/></th>
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
        <div class="titlebar"><span class="w-icon"><spring:message code="FoldersAdding"/></span></div>
        <div id="formValidation" class="alert-small">
            <span class="close"></span>
        </div>
        <form>
            <div class="contents">
                <div class="row">
                    <label for="folderName"><spring:message code="Folder"/></label>
                    <div class="field-box"><input type="text" id="folderName" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label for="orderNumber"><spring:message code="OrderNumber"/></label>
                    <div class="field-box"><input type="text" id="orderNumber" class="w-icon medium"/></div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="bar-big">
                <input type="submit" value="Submit" onclick="addFolder();return false;">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>