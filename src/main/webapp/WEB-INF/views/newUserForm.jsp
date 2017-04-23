<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="newUser.title"/></title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
	<div class="row">
		<div class="col">
			<h1><spring:message code="newUser.title"/></h1>
		</div>
	</div>
	
	<form:form action="/user/new" method="POST" modelAttribute="registration" acceptCharset="UTF-8" class="row">
		<div class="col">
			<div class="row">
				<div class="col-xs-1">
					<label for="name"><spring:message code="newUser.name"/></label>
				</div>
				<div class="col-xs-2">
					<form:input id="name" path="name"/>
					<form:errors id="nameErrors" path="name"/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-1">
					<label for="email"><spring:message code="account.email"/></label>
				</div>
				<div class="col-xs-2">
					<form:input id="email" path="email"/>
					<form:errors id="emailErrors" path="email"/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-1">
					<label for="password"><spring:message code="account.password"/></label>
				</div>
				<div class="col-xs-2">
					<form:password id="password" path="password"/>
					<form:errors id="passwordErrors" path="password"/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<form:button><spring:message code="newUser.submit"/></form:button>
				</div>
			</div>
		</div>
	</form:form>

</div>
</body>
</html>
