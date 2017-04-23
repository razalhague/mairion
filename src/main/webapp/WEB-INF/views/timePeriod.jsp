<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="mrn" uri="http://mairion.penny-craal.org/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="timePeriod.title"/></title>
</head>
<body>
<h1><spring:message code="timePeriod.title"/></h1>
<a class="addGoal" href="/goal/in/${timePeriod.id}/new"><spring:message code="timePeriod.addGoal"/></a>
<div id="goalList">
	<c:forEach var="goal" items="${goals}">
		<div class="goal">
			<div class="goalTask">
				<a href="/task/${goal.task.id}"><c:out value="${goal.task.title}"/></a>
			</div>
			<c:set var="goalStatus" value="${goal.calculateStatus(now)}"/>
			<div class="goalStatus ${goalStatus}"><spring:message code="goalStatus.${goalStatus}"/></div>
			<c:set var="goalProgress" value="${goal.calculateProgress(now)}"/>
			<c:if test="${goalProgress.isPresent()}">
				<div class="goalProgress ${mrn:progressToClass(goalProgress.get())}">
					${(goalProgress.get() * 100).intValue()}%
				</div>
			</c:if>
			<c:if test="${not empty goal.configurables}">
				<c:forEach var="configurable" items="${goal.configurables}">
					<div class="configurable">
						<spring:message code="goal.configurable.${configurable.name}"/>
						<c:out value="${configurable.getConfigurableFromGoal(goal)}"/>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</c:forEach>
</div>
</body>
</html>
