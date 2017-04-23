<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="mrn" uri="http://mairion.penny-craal.org/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="newGoalInTimePeriod.title"/></title>
</head>
<body>
<h1><spring:message code="newGoalInTimePeriod.title"/></h1>
<form:form action="/goal/in/${timePeriod.id}/new" method="POST" modelAttribute="goal" acceptCharset="UTF-8">
	<div>
		<form:label path="taskId"><spring:message code="goal.task"/></form:label>
		<form:select path="taskId">
			<form:option value="">
				<spring:message code="dropDown.select"/>
			</form:option>
			<form:options items="${tasks}" itemValue="id" itemLabel="title"/>
		</form:select>
		<form:errors path="taskId"/>
	</div>
	<div>
		<form:label path="type"><spring:message code="goal.type"/></form:label>
		<form:select path="type">
			<form:option value="">
				<spring:message code="dropDown.select"/>
			</form:option>
			<c:forEach var="goalType" items="${mrn:goalTypes()}">
				<form:option value="${goalType.name()}">
					<spring:message code="goal.type.${goalType.name()}"/>
				</form:option>
			</c:forEach>
		</form:select>
	</div>
	<form:button><spring:message code="newGoalInTimePeriod.submit"/></form:button>
</form:form>
</body>
</html>
