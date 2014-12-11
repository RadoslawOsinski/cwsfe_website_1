<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/pages/layout/Header.jsp" %>

<div itemscope itemtype="http://schema.org/LocalBusiness">

    <div id="page-header">
        <c3:if test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/contact/cup-of-coffee-and-paper_contact_us_pl.png"
                 width="880" height="200" alt="cup of coffee"/>
        </c3:if>
        <c3:if test="${pageContext.response.locale != null && pageContext.response.locale == 'en'}">
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/contact/cup-of-coffee-and-paper_contact_us_en.png"
                 width="880" height="200" alt="cup of coffee"/>
        </c3:if>
        <div id="page-header-title"><spring:message code="ContactInformation"/></div>
    </div>
    <div class="fixed">
        <div style="visibility: hidden" itemprop="name">CWSFE</div>
        <div class="col280">
            <p class="last"><img src="${pageContext.request.contextPath}/resources-cwsfe/img/contact/contact.png"
                                 class="img-align-left" alt="" style="margin-top:0;"/>
                <strong><spring:message code="WorkingHours"/></strong>:<br/>
                <span itemprop="openingHoursSpecification" itemscope
                      itemtype="http://schema.org/OpeningHoursSpecification">
                    <span itemprop="dayOfWeek" itemscope itemtype="http://schema.org/DayOfWeek">
                        <span itemprop="name">
                            <spring:message code="MondayToFriday"/>
                        </span>
                        / 8:00 - 16:00 <br/>
                    </span>
                </span>
            </p>
        </div>
        <div class="col280">
            <p class="last" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">&nbsp;
                <strong><spring:message code="Office"/></strong><br/>
                <span itemprop="streetAddress"><spring:message code="Nowa12Street"/></span><br/><span itemprop="postalCode">06-430</span> Sońsk,
                <span itemprop="addressCountry"><spring:message code="Poland"/></span>
                <span itemprop="addressRegion">Mazowsze</span>
            </p>
        </div>
        <div class="col280 last">
            <p class="last"><strong><spring:message code="ContactInfo"/></strong><br/>
                <spring:message code="Phone"/>:<span itemprop="telephone">+48 791-101-335</span><br/>
                <spring:message code="Email"/>: <a href="mailto:info@cwsfe.pl">info@cwsfe.pl</a>
            </p>
        </div>
    </div>
    <div class="hr"></div>
    <div class="fixed">
        <div class="col580">
            <spring:url value="/contact/sendEmail" var="contactUrl" htmlEscape="true"/>
            <form id="contact-form" class="fixed" method="post" action="${contactUrl}" autocomplete="off">
                <fieldset>
                    <c:if test="${mailSendOperationErrors != null}">
                        <div class="errormsg">${mailSendOperationErrors}</div>
                    </c:if>
                    <c:if test="${mailSended != null}">
                        <div class="successmsg">${mailSended}</div>
                    </c:if>
                    <p>
                        <label for="name">
                            <spring:message code="YourName"/>: <span class="required">*</span>
                        </label><br/>
                        <input class="text" type="text" id="name" name="name" value="${name}"/>
                    </p>

                    <p>
                        <label for="email">
                            <spring:message code="YourEmailAddress"/>: <span class="required">*</span>
                        </label><br/>
                        <input class="text" type="text" id="email" name="email" value="${email}"/>
                    </p>

                    <p>
                        <label for="message">
                            <spring:message code="Message"/>:
                        </label><br/>
                        <textarea id="message" name="message" rows="3" cols="25">${message}</textarea>
                    </p>

                    <p>
                        <button type="submit" name="requestHandler" value="sendMail"><spring:message code="Send"/>!
                        </button>
                    </p>
                </fieldset>
            </form>
        </div>
        <div class="col280 last">
            <%--<p>--%>
                <%--<strong><spring:message code="OfficeLocalistation"/></strong>:--%>
            <%--</p>--%>
            <%--<div id="contactMap" style="width: 280px; height: 280px; border: 1px solid black; background: gray;"></div>--%>
            <%--<div class="hr"></div>--%>
            <p>
                <strong><spring:message code="ScanQRContactByPhone"/></strong>
            </p>
            <%--generated on: http://www.moongate.ro/en/products/qr_code-vcard/--%>
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/contact/CWSFE_VCARD_LARGE.png"
                 alt="QR contact" style="margin-top:0;"/>
            <%--<div class="pdf"><a href="#">Download our <br/> Enquiry Form</a></div>--%>
        </div>
    </div>

</div>

<%@include file="/pages/layout/Footer.jsp" %>