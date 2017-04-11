<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.penny_craal.mairion.model.TaskStatus"%>
<%-- this is here because EL can't access static members --%>
<c:set var="taskStatuses" value="<%= TaskStatus.values() %>"/>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><c:out value="${task.title}"><spring:message code="task.title.empty"/></c:out></title>
</head>
<body>
<h1><c:out value="${task.title}"><spring:message code="task.title.empty"/></c:out></h1>
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
			<c:forEach var="taskStatus" items="${taskStatuses}">
				<%-- spring will automatically set selected on the appropriate option --%>
				<form:option value="${taskStatus.name()}">
					<spring:message code="task.status.${taskStatus.name()}"/>
				</form:option>
			</c:forEach>
		</form:select>
	</div>
	<form:button><spring:message code="newTask.submit"/></form:button>
</form:form>
</body>
</html>
