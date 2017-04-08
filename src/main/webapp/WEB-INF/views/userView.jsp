<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><c:out value="${user.name}"/></title>
</head>
<body>
<h1><spring:message code="welcome"/></h1>
<p><c:out value="${user.name}"/></p>
<p><c:out value="${user.email}"/></p>
<p><c:out value="${user.id}"/></p>
<p><c:out value="${user.hashedPassword}"/></p>
</body>
</html>
