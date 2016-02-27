<%@ taglib prefix="c3" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header-widget-1" class="col205 last">
    <ul id="social-media" class="fixed">
        <li>
            <a href="?language=pl" class="flag flag_pl"></a>
        </li>
        <li>
            <a href="?language=en" class="flag flag_en"></a>
        </li>
        <li>
            <a href="http://www.youtube.com/user/1CWSFE" target="_blank">
                <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/social/youtube_gray.png" width="16" height="16" alt="YoutubeChannel"/>
            </a>
        </li>
        <li>
            <a href="https://twitter.com/cwsfe" target="_blank">
                <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/social/twitter.png" width="16" height="16" alt="CompanyWebsiteOnTwitter"/>
            </a>
        </li>
        <li>
            <a rel="publisher" href="http://google.com/+CwsfePl" target="_blank">
                <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/social/google_plus.png" width="16" height="16" alt="CompanyWebsiteOnGoogle+"/>
            </a>
        </li>
        <%--<li>--%>
        <%--<spring:url value="/CWSFE_WEB_MONITOR" var="webMonitorLoginUrl" htmlEscape="true"/>--%>
        <%--<a href="${webMonitorLoginUrl}" rel="nofollow">--%>
        <%--<img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/analytics.png" width="16" height="16" alt="analytics icon"/>--%>
            <%--</a>--%>
        <%--</li>--%>
        <li>
            <a href="${cwsfeConfiguration.getRequiredProperty("CMS_ADDRESS")}" rel="nofollow">
                <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/bg-author.png" width="16" height="16" alt="administration icon"/>
            </a>
        </li>
    </ul>
</div>