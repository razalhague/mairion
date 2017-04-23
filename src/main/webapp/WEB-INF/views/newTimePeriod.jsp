<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="newTimePeriod.title"/></title>
</head>
<body>
<h1><spring:message code="newTimePeriod.title"/></h1>
<form:form action="/time-period/new" method="POST" modelAttribute="timePeriod" acceptCharset="UTF-8">
	<form:hidden path="userZone"/>
	<form:errors/>
	<div>
		<form:label path="localStartPoint"><spring:message code="timeRange.startPoint"/></form:label>
		<form:input path="localStartPoint"/>
		<form:errors path="localStartPoint"/>
	</div>
	<div>
		<form:label path="localEndPoint"><spring:message code="timeRange.endPoint"/></form:label>
		<form:input path="localEndPoint"/>
		<form:errors path="localEndPoint"/>
	</div>
	<form:button><spring:message code="newTimePeriod.submit"/></form:button>
</form:form>
</body>
</html>
