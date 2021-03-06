<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="user" value="${sessionScope.get('user')}"/>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="taskList.title" arguments="${user.name}"/></title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<div class="row">
		<h1><spring:message code="taskList.title" arguments="${user.name}"/></h1>
	</div>
	<div class="row">
		<c:set target="${newTask}" property="title"><spring:message code="task.title.new"/></c:set>
		<form:form action="/task/new" method="POST" modelAttribute="newTask">
			<form:hidden path="title"/>
			<form:button><spring:message code="taskList.newTask"/></form:button>
		</form:form>
		<div id="taskList">
			<c:forEach var="task" items="${tasks}">
				<div class="task">
					<div class="taskTitle"><a href="/task/${task.id}"><c:out value="${task.title}"/></a></div>
				</div>
			</c:forEach>
		</div>
	</div>
<div>
</body>
</html>
