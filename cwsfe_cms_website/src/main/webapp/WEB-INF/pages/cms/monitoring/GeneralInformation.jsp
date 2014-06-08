<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/cms/layout/Header.jsp" %>

<div class="box">
    <div class="inner">
        Operating System: ${osName} ${osVersion}<br/>
        Architecture: ${architecture}<br/>
        Available CPUs: ${availableCPUs}<br/>
        Used Memory: <span id="usedMemoryInMb">${usedMemoryInMb}</span> Mb<br/>
        Available memory: <span id="availableMemoryInMB">${availableMemoryInMB}</span> Mb<br/>
    </div>
</div>

<%@include file="/WEB-INF/pages/cms/layout/Footer.jsp" %>