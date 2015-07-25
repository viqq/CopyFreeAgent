<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 7/6/15
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<p>User  <b>${user.firstName} + " " + ${user.lastName}</b> hello</p>
Information about  <b>${user.firstName}</b>
<ol>
  <li> "${user.login} "</li>
  <li> "  ${user.email}"</li>
  <li> "   ${user.city} "</li>
  <c:if test="${not empty user.sports}">
    <ul>
      <c:forEach var="sport" items="${user.sports}">
        <li> "${sport.name} "</li>
      </c:forEach>
    </ul>
  </c:if>
</ol>
</body>
</html>

