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
            <c3:if test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">
                <spring:url value="/rssFeed" htmlEscape="true" var="rssFeedPL">
                    <spring:param name="language" value="pl"/>
                </spring:url>
                <a type="application/rss+xml" href="${rssFeedPL}">
                    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/social/rss.png" width="16" height="16" alt="rss icon"/>
                </a>
            </c3:if>
            <c3:if test="${pageContext.response.locale != null && pageContext.response.locale == 'en'}">
                <spring:url value="/rssFeed" htmlEscape="true" var="rssFeedEN">
                    <spring:param name="language" value="en"/>
                </spring:url>
                <a type="application/rss+xml" href="${rssFeedEN}">
                    <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/social/rss.png" width="16" height="16" alt="rss icon"/>
                </a>
            </c3:if>
        </li>
        <%--<li>--%>
            <%--<a href="#">--%>
                <%--<img src="${pageContext.request.contextPath}/resources/img/layout/images/social/facebook.png" width="16" height="16" alt="facebook icon"/>--%>
            <%--</a>--%>
        <%--</li>--%>
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
            <%--<a href="#">--%>
                <%--<img src="${pageContext.request.contextPath}/resources/img/layout/images/social/dribble.png" width="16" height="16"--%>
                     <%--alt="dribble icon"/>--%>
            <%--</a>--%>
        <%--</li>--%>
        <li>
            <spring:url value="/CWSFE_CMS" var="loginUrl" htmlEscape="true"/>
            <a href="${loginUrl}" rel="nofollow">
                <img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/bg-author.png" width="16" height="16" alt="administration icon"/>
            </a>
        </li>
        <%--$ifNotNull($user.userID(), {--%>
        <%--<li>--%>
            <%--<a href="$page.logout("cwsfe.Home")"><spring:message code="Logout")</a>--%>
            <%--<a href="#">Logout</a>--%>
        <%--</li>--%>
        <%--})--%>
    </ul>
</div>