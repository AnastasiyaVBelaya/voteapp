<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Ошибка</title>
</head>
<body>
    <h1>Ошибка</h1>
    <p>${errorMessage}</p>
    <a href="${pageContext.request.contextPath}/browser/vote">Вернуться на страницу голосования</a>
</body>
</html>
