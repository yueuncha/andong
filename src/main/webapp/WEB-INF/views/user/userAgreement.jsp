<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>이용약관</title>
</head>
<body>
    <c:forEach var="i" items="${agreement}">
        <h3>${i.key}</h3>
        <p>${i.value}</p>
    </c:forEach>
</body>
</html>
