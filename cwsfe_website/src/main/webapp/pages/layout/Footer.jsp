</div>

<div id="footer" class="fixed">

    <div class="fixed">

        <div id="footer-widget-1" class="col205" itemscope itemtype="http://schema.org/Organization">
            <p>
                <spring:url value="${pageContext.request.contextPath}/" var="mainUrl" htmlEscape="true"/>
                <a itemprop="url" href="${mainUrl}">
                    <img itemprop="logo"
                         src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/CWSFE_logo_150x150.png"
                         height="150" width="150" alt="logo"/>
                </a>
            </p>
            <%--<p><img src="${pageContext.request.contextPath}/resources-cwsfe/img/layout/images/CWSFE_logo_150x150.png" height="150" width="150" alt="logo"/></p>--%>
            <%--<p class="last">&copy; 2012. All right are reserved.<br /> <a href="#">Terms of Service</a> | <a href="#">Privacy Policy</a></p>--%>
            <p class="last">
                <%--<a href="http://cwsfe.pl">CWSFE</a>--%>
                <%--<br/>--%>
                &copy; 2014. <spring:message code="AllRightsAreReserved"/>.<br/>
                <%--<a href="#"><spring:message code="TermsOfService")</a> | <a href="#"><spring:message code="Privacy Policy")</a>--%>
            </p>
        </div>

        <div id="footer-widget-2" class="col205">
            <h6><span><spring:message code="Navigation"/></span></h6>
            <ul class="footer-nav">
                <spring:url value="${pageContext.request.contextPath}/services" var="servicesUrl" htmlEscape="true"/>
                <spring:url value="${pageContext.request.contextPath}/portfolio" var="portfolioUrl" htmlEscape="true"/>
                <spring:url value="${pageContext.request.contextPath}/blog" var="blogUrl" htmlEscape="true"/>
                <spring:url value="${pageContext.request.contextPath}/contact" var="contactUrl" htmlEscape="true"/>

                <li class="first"><a href="${servicesUrl}"><spring:message code="Services"/></a></li>
                <li><a href="${portfolioUrl}"><spring:message code="Portfolio"/></a></li>
                <li><a href="${blogUrl}"><spring:message code="Blog"/></a></li>
                <li class="last"><a href="${contactUrl}"><spring:message code="Contact"/></a></li>
            </ul>
        </div>

        <div id="footer-widget-3" class="col205">
            <h6><span>QR <spring:message code="Contact"/></span></h6>

            <p><img src="${pageContext.request.contextPath}/resources-cwsfe/img/contact/CWSFE_VCARD_MEDIUM.png"
                    alt="QR contact" style="margin-top:0;" width="146" height="146"/></p>
        </div>

        <div id="footer-widget-4" class="col205 last">
            <h6><span><spring:message code="Contact"/></span></h6>
            <ul id="contact-info">
                <%--<li class="adress">Nowa 12,<br/>06-430 Sońsk, <spring:message code="Poland")</li>--%>
                <li class="phone">+48 791-101-335</li>
                <li class="email">
                    <a href="mailto:info@cwsfe.pl">info@cwsfe.pl</a>
                </li>
            </ul>
        </div>

    </div>

</div>

</div>

</body>
</html>
