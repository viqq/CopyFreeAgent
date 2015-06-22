<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<p>User  <b>${user.login}</b> hello</p>
Information about  <b>${user.login}</b>
<li> "${user.login} , ${user.password} , ${user.email} , ${user.city} "</li>
<a href="<c:url value="/j_spring_security_logout" />" >Logout</a> <br/>
</body>
</html>
