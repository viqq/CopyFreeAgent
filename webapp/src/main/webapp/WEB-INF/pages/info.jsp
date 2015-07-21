<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<p>User  <b>${user.login}</b> hello</p>
Information about  <b>${user.login}</b>

<c:if test="${user.image == null}">
    <h3> Choose File to Upload in Server </h3>

    <form action="${pageContext.request.contextPath}/user/setImage" method="post" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit" value="upload"/>
    </form>
</c:if>
<c:if test="${user.image != null}">
    <img src="${pageContext.request.contextPath}/getImage" width="100" height="150">
</c:if>

<ol>
    <li> "${user.login}"</li>
    <li> "${user.password}"</li>
    <li> "${user.email}"</li>
    <li> "${user.city}"</li>
    <c:if test="${not empty user.sports}">
        <ul>
            <c:forEach var="sport" items="${user.sports}">
                <li> "${sport.name} "</li>
            </c:forEach>
        </ul>
    </c:if>
</ol>
<a href="<c:url value="/j_spring_security_logout" />" >Logout</a> <br/>
</body>
</html>
