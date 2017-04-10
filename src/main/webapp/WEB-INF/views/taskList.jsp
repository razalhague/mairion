<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="taskList.title" arguments="${user.name}"/></title>
</head>
<body>
<h1><spring:message code="taskList.title" arguments="${user.name}"/></h1>
<div id="taskList">
	<c:forEach var="task" items="${tasks}">
		<div class="task">
			<div class="taskTitle"><a href="/task/${task.id}"><c:out value="${task.title}"/></a></div>
		</div>
	</c:forEach>
</div>
</body>
</html>
