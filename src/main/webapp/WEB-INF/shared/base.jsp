<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="language" scope="session" value="${not empty language ? language : 'en'}" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="translation" />


<c:choose>
    <c:when test="${pageContext.request.serverName ne 'localhost' }">
        <c:set var="appURL" value="${initParam['appURLCloud']}"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="appURL" value="${initParam['appURLLocal']}"></c:set>
    </c:otherwise>
</c:choose>
<c:set var="businessName" value="LearnX"></c:set>
<c:set var="path" value="learnx"></c:set>