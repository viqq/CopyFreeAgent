<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Films List</title>

    <script type="text/javascript">
        function create_order(filmId) {
            if (filmId != undefined || filmId != null) {
                window.location = 'createOrder?filmId=' + filmId;
            }
        }
    </script>
</head>

<body>

<c:if test="${not empty films}">
    <ul>
        <c:forEach var="film" items="${films}">
            <li onclick="create_order(${film.id})">${film.name}</li>
        </c:forEach>
    </ul>
</c:if>


</body>
</html>
