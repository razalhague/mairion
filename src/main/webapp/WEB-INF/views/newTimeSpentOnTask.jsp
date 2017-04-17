<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="taskName"> <%-- no need to escape when using later in the page --%>
	<c:choose>
		<c:when test="${empty task.title}"><spring:message code="task.title.empty"/></c:when>
		<c:otherwise><c:out value="${task.title}"/></c:otherwise>
	</c:choose>
</c:set>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="newTimeSpentOnTask.title" arguments="${taskName}"/></title>
</head>
<body>
<h1><spring:message code="newTimeSpentOnTask.title" arguments="${taskName}"/></h1>
<form:form action="/time-spent/on-task/${task.id}/new" modelAttribute="durationTimeSpent" method="POST" acceptCharset="UTF-8">
	<form:hidden path="userZone"/>
	<div>
		<form:label path="localEndPoint"><spring:message code="timeSpent.endPoint"/></form:label>
		<form:input path="localEndPoint"/>
		<form:errors path="localEndPoint"/>
	</div>
	<div>
		<form:label path="hours"><spring:message code="durationTimeSpent.hours"/></form:label>
		<form:input path="hours"/>
		<form:errors path="hours"/>
	</div>
	<div>
		<form:label path="message"><spring:message code="timeSpent.message"/></form:label>
		<form:input path="message"/>
		<form:errors path="message"/>
	</div>
	<form:button><spring:message code="newTimeSpentOnTask.submit"/></form:button>
</form:form>
</body>
</html>
