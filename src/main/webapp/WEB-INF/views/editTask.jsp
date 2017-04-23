<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="mrn" uri="http://mairion.penny-craal.org/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="pageTitle"> <%-- no need to escape when using later in the page --%>
	<spring:message code="editTask.title"/>
	<c:choose>
		<c:when test="${empty task.title}"><spring:message code="task.title.empty"/></c:when>
		<c:otherwise><c:out value="${task.title}"/></c:otherwise>
	</c:choose>
</c:set>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${pageTitle}</title>
</head>
<body>
<h1>${pageTitle}</h1>
<form:form action="/task/${task.id}" method="POST" modelAttribute="task" acceptCharset="UTF-8">
	<div>
		<form:label path="title"><spring:message code="task.title"/></form:label>
		<form:input id="title" path="title"/>
		<form:errors id="titleErrors" path="title"/>
	</div>
	<div>
		<form:label path="description"><spring:message code="task.description"/></form:label>
		<form:textarea id="description" path="description"/>
		<form:errors id="descriptionErrors" path="description"/>
	<div>
	<div>
		<form:label path="status"><spring:message code="task.status"/></form:label>
		<form:select id="status" path="status">
			<c:forEach var="taskStatus" items="${mrn:taskStatuses()}">
				<%-- spring will automatically set selected on the appropriate option --%>
				<form:option value="${taskStatus.name()}">
					<spring:message code="task.status.${taskStatus.name()}"/>
				</form:option>
			</c:forEach>
		</form:select>
	</div>
	<form:button><spring:message code="editTask.submit"/></form:button>
</form:form>
</body>
</html>
