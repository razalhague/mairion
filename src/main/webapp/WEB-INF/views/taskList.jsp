<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mairion testisivu</title>
</head>
<body>
<h1><spring:message code="welcome"/></h1>
<c:forEach var="task" items="${taskDao.allTasks}">
	<p>${task.toString()}</p>
</c:forEach>
</body>
</html>
