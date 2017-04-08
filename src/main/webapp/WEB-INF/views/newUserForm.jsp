<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="newUser.title"/></title>
</head>
<body>
<h1><spring:message code="welcome"/></h1>
<form:form action="/user/new" method="POST" modelAttribute="registration" acceptCharset="UTF-8">
	<div>
		<label for="name"><spring:message code="newUser.name"/></label>
		<form:input id="name" path="name"/>
		<form:errors id="nameErrors" path="name"/>
	</div>
	<div>
		<label for="email"><spring:message code="newUser.email"/></label>
		<form:input id="email" path="email"/>
		<form:errors id="emailErrors" path="email"/>
	</div>
	<div>
		<label for="password"><spring:message code="newUser.password"/></label>
		<form:password id="password" path="password"/>
		<form:errors id="passwordErrors" path="password"/>
	<div>
	<form:button><spring:message code="newUser.submit"/></form:button>
</form:form>
</body>
</html>
