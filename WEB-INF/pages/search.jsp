<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="post" action="/search">
    <input type="text" name="sport" id="sport" placeholder="sport">
    <input type="text" name="firstName" id="firstName" placeholder="firstName">
    <input type="text" name="lastName" id="lastName" placeholder="lastName">
    <input type="text" name="dateOfBirthFrom" id="dateOfBirthFrom" placeholder="dateOfBirthFrom">
    <input type="text" name="dateOfBirthTo" id="dateOfBirthTo" placeholder="dateOfBirthTo">
    <input type="submit" value="Submit"/>
</form>
<c:if test="${not empty users}">
    <ul>
        <c:forEach var="user" items="${users}">
            <li>
                <div>"${user.firstName} "</div>
                <div> "${user.lastName}"</div>
                <div> "${user.dateOfBirth}"</div>
                <div style="display: none"> "${user.id}"</div>
            </li>
        </c:forEach>
    </ul>
</c:if>


</body>
</html>