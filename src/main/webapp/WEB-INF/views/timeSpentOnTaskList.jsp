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
	<title><spring:message code="timeSpentOnTask.title" arguments="${taskName}"/></title>
</head>
<body>
<h1><spring:message code="timeSpentOnTask.title" arguments="${taskName}"/></h1>
<div class="timeSpentList">
	<c:forEach var="timeSpent" items="${task.work}">
		<div class="timeSpent">
			<div class="timeSpentStartTime">
				<spring:message code="timeRange.startPoint"/>
				<c:out value="${timeSpent.zonedStartPoint
					.withZoneSameInstant(sessionScope.get('user').timezone).toLocalDateTime()}"/>
			</div>
			<div class="timeSpentEndTime">
				<spring:message code="timeRange.endPoint"/>
				<c:out value="${timeSpent.zonedEndPoint
					.withZoneSameInstant(sessionScope.get('user').timezone).toLocalDateTime()}"/>
			</div>
			<c:if test="${not empty timeSpent.message}">
				<div class="timeSpentMessage">
					<spring:message code="timeSpent.message"/>
					<c:out value="${timeSpent.message}"/>
				</div>
			</c:if>
		</div>
	</c:forEach>
</div>
<%--form:form>

</form:form--%>
</body>
</html>
