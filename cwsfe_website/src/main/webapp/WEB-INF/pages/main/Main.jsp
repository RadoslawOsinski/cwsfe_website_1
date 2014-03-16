<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/WEB-INF/pages/layout/Header.jsp" %>

<%@include file="SlideShow.jsp" %>

<div class="fixed">
    <div class="col280">
        <h5 class="hasicon">
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/icons/2.png" height="20" width="20"
                 alt="circle icon"/>
            <spring:message code="ITBussiness"/>
        </h5>

        <p class="last">
            <spring:message code="GainCompetitiveAdvantage..."/>
        </p>
    </div>
    <div class="col280">
        <h5 class="hasicon">
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/icons/1.png" height="20" width="20"
                 alt="wrench icon"/>
            <spring:message code="WorkWithPassion"/>
        </h5>

        <p class="last">
            <spring:message code="CollaborateWithPeople..."/>
        </p>
    </div>
    <div class="col280 last">
        <h5 class="hasicon">
            <img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/icons/3.png" height="20" width="20"
                 alt="list icon"/>
            <spring:message code="AnotherHappyClient"/>
        </h5>

        <p class="last">
            <spring:message code="JoinToGroupOfCustomers..."/>
        </p>
    </div>
</div>

<div class="hr"></div>

<div class="fixed">
    <div class="col580">
        <p>
            <spring:message code="DidYouAskedYourselfAnyOfTheseQuestions?"/>
        </p>
        <ul>
            <li><spring:message code="DoesYourBusinessNeedsMultimediaOrWebSolutions?"/></li>
            <li><spring:message code="YouAreNotSureHowComputerCanImproveThePerformanceOfYourBusiness?"/></li>
            <li><spring:message code="DoYouWantToGetNewCustomersOnTheInternet?"/></li>
            <li><spring:message code="YouAreAboutToStartSellingProductsOrServicesOnTheNetwork?"/></li>
            <li><spring:message
                    code="YouHaveToCatchUpWithTheCompetitionOfYourCompanyWhichIsAlreadyUsingNewTechnologies?"/></li>
            <li><spring:message code="YouDoNotWantToLookForPerfectAndExpensiveITCompanyAcrossTheCountry?"/></li>
        </ul>
        <p>
            <spring:message code="IfTheAnswerToEitherQuestionIsYes..."/>
        </p>
    </div>
    <div class="col280 last">
        <%--<p>--%>
            <%--<spring:message code="Newsletter"/>--%>
        <%--</p>--%>
        <%--<spring:url value="/addAddressToNewsletter" var="addAddressToNewsletterUrl" htmlEscape="true"/>--%>
        <%--<form class="fixed" method="post" action="${addAddressToNewsletterUrl}">--%>
            <%--<input class="text" type="email" id="email" name="email" placeholder="email"/>--%>
            <%--<button type="submit" name="requestHandler" value="addAddressToNewsletter"><spring:message--%>
                    <%--code="AddAnEmailToTheList"/></button>--%>
        <%--</form>--%>
    </div>
</div>

<div class="hr"></div>
<div class="fixed">

    <div class="col280">
        <h5><spring:message code="WhoWeAre"/>?</h5><br/>

        <p>
            <spring:message code="CWSFEIsADynamicCompany..."/>
        </p>
    </div>

    <div class="col280">
        <h5><spring:message code="Clients"/></h5><br/>

        <p>
            <spring:message code="DoYouRecognizeTheseLogos?..."/>
        </p>
        <%--<img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/LogaKlientow.png" width="280" height="150" alt="clients logos"/>--%>
        <img src="${pageContext.request.contextPath}/resources-cwsfe/img/main/LogaKlientow.png" width="227" height="109"
             alt="clients logos"/>
    </div>

    <div class="col280 last">
        <%@include file="../about/ClientOpinionsWithHeader.jsp" %>
    </div>

</div>
<%@include file="/WEB-INF/pages/layout/Footer.jsp" %>