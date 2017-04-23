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
	<form:hidden path="taskId"/>
	<form:hidden path="type"/>
	<c:forEach var="configurable" items="${configurables}">
		<div class="configurableRow ${configurable.name}">
			<form:label path="configurables['${configurable.name}']">
				<spring:message code="goal.configurable.${configurable.name}"/>
			</form:label>
			<form:input path="configurables['${configurable.name}']"/>
			<form:errors path="configurables['${configurable.name}']"/>
		</div>
	</c:forEach>
	<form:button><spring:message code="newGoalInTimePeriod.submit"/></form:button>
</form:form>
</body>
</html>
