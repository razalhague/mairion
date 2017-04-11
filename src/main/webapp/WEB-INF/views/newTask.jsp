<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="newTask.title"/></title>
</head>
<body>
<h1><spring:message code="newTask.title"/></h1>
<form:form action="/task/new" method="POST" modelAttribute="task" acceptCharset="UTF-8">
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
	<form:button><spring:message code="newTask.submit"/></form:button>
</form:form>
</body>
</html>
